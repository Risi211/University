using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace ProgettoDatabase
{
    public partial class Form2 : Form
    {
        DataSet database;

        //proprietà di output
        public Viaggio Scarico { get; set; }
        public List<Informazioni_In_Viaggio> Infos { get; set; }

        public Form2(DataSet database)
        {
            InitializeComponent();
            this.database = database;
            //legge i dati dal database e li mette nelle combobox
            Inizializza();
        }

        private void Inizializza()
        {
            //legge camionisti
            var dipendenti = database.Tables["DIPENDENTE"].AsEnumerable();
            var camionisti = from d in dipendenti
                             where d.Field<string>("Ruolo") == "Camionista"
                             select new { Nome = d.Field<string>("Nome") };

            foreach (var c in camionisti)
            {
                cmbCamionista.Items.Add(c.Nome);
            }
            //iniizializza combobox camionisti col primo valore
            cmbCamionista.SelectedItem = cmbCamionista.Items[0];

            //legge camion
            var camion = database.Tables["CAMION"];
            foreach (DataRow  c in camion.Rows)
            {
                cmbCamion.Items.Add(c.Field<string>("Targa"));
            }
            //iniizializza combobox camion col primo valore
            cmbCamion.SelectedItem = cmbCamion.Items[0];

            //legge container per container1
            var container = database.Tables["[CONTAINER]"];
            foreach(DataRow c in container.Rows)
            {
                cmbContainer1.Items.Add(c.Field<string>("CodiceIso"));
                cmbContainer2.Items.Add(c.Field<string>("CodiceIso"));
            }
            cmbContainer1.SelectedItem = cmbContainer1.Items[0];
            cmbContainer2.SelectedItem = cmbContainer2.Items[0];

            //legge lista impianti
            var impianti = database.Tables["IMPIANTO"];
            foreach (DataRow i in impianti.Rows)
            {
                cmbImpianto1.Items.Add(i.Field<string>("Nome"));
                cmbImpianto2.Items.Add(i.Field<string>("Nome"));
            }
            cmbImpianto1.SelectedItem = cmbImpianto1.Items[0];
            cmbImpianto2.SelectedItem = cmbImpianto2.Items[0];

            //legge i Soa all'inizio
            List<string> soa1 = Soa_In_Impianto(cmbImpianto1.SelectedItem.ToString());
            List<string> soa2 = Soa_In_Impianto(cmbImpianto2.SelectedItem.ToString());
            cmbSoa1.Items.Clear();
            cmbSoa2.Items.Clear();
            foreach(string s in soa1)
            {                
                cmbSoa1.Items.Add(s);
            }
            foreach (string s in soa2)
            {
                cmbSoa2.Items.Add(s);
            }
            cmbSoa1.SelectedItem = cmbSoa1.Items[0];
            cmbSoa2.SelectedItem = cmbSoa2.Items[0];

            //legge i rimorchi
            var rimorchi = database.Tables["[RIMORCHIO]"];
            foreach(DataRow r in rimorchi.Rows)
            {
                cmbRimorchio.Items.Add(r.Field<string>("Targa"));
            }
            cmbRimorchio.SelectedItem = cmbRimorchio.Items[0];
        }

        private List<string> Soa_In_Impianto(string impianto)
        {
            //dal nome dell'impianto prendo l'iva
            var tmp_iva = from s in database.Tables["IMPIANTO"].AsEnumerable()
                      where s.Field<string>("Nome") == impianto
                      select new { Iva = s.Field<string>("PartitaIva")};

            string iva = tmp_iva.ElementAt(0).Iva;

            var table = database.Tables["SOA_IN_IMPIANTO"].AsEnumerable();
            var soa = from t in table
                      where t.Field<string>("Impianto") == iva
                      select t.Field<string>("Soa");

            return soa.ToList<string>();
        }

        private void chkRimorchio_CheckedChanged(object sender, EventArgs e)
        {
            if (chkRimorchio.Checked)
            {
                //visualizza anche container 2
                grpRimorchio.Visible = true;
            }
            else
            {
                //nasconde container 2
                grpRimorchio.Visible = false;
            }
        }

        private void cmbImpianto1_SelectedIndexChanged(object sender, EventArgs e)
        {
            cmbSoa1.Items.Clear();
            //al cambio dell'impianto cambiano anche i relativi soa_in_impianto
            List<string> soa1 = Soa_In_Impianto(cmbImpianto1.SelectedItem.ToString());
            foreach (string s in soa1)
            {
                cmbSoa1.Items.Add(s);
            }
            cmbSoa1.SelectedItem = cmbSoa1.Items[0];
        }

        private void cmbImpianto2_SelectedIndexChanged(object sender, EventArgs e)
        {
            cmbSoa2.Items.Clear();
            //al cambio dell'impianto cambiano anche i relativi soa_in_impianto
            List<string> soa2 = Soa_In_Impianto(cmbImpianto1.SelectedItem.ToString());
            foreach (string s in soa2)
            {
                cmbSoa2.Items.Add(s);
            }
            cmbSoa2.SelectedItem = cmbSoa2.Items[0];
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Viaggio scarico = new Viaggio();
            if (txtBustaDoc.Text == "")
            {
                MessageBox.Show("Inserisci Busta Documenti","Errore",MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            scarico.BustaDocumenti = txtBustaDoc.Text;
            scarico.Camion = cmbCamion.SelectedItem.ToString();
            //dal nome del camionista prende il codice fiscale
            var cf = from d in database.Tables["DIPENDENTE"].AsEnumerable()
                     where d.Field<string>("Nome") == cmbCamionista.SelectedItem.ToString()
                     select new { CF = d.Field<string>("CodiceFiscale") };

            scarico.Camionista = cf.ElementAt(0).CF;
            //prende ultimo codice
            if(database.Tables["VIAGGIO_DI_SCARICO"].Rows.Count == 0)
            {
                scarico.Codice = 1;
            }
            else
            {
                //leggo il massimo codice inserito nel database
                var codici = from v in database.Tables["VIAGGIO_DI_SCARICO"].AsEnumerable()
                             orderby v.Field<int>("Codice") descending
                             select v.Field<int>("Codice");

                int max = codici.ElementAt(0);

                scarico.Codice = max + 1;
            }

            scarico.Container1 = cmbContainer1.SelectedItem.ToString();
            scarico.Data = dateTimePicker1.Value.Day.ToString() + "/" + dateTimePicker1.Value.Month.ToString() + "/" + dateTimePicker1.Value.Year.ToString();
            
            //riempie informazioni per container 1
            List<Informazioni_In_Viaggio> info = new List<Informazioni_In_Viaggio>();
            Informazioni_In_Viaggio in1 = new Informazioni_In_Viaggio();
            //prende l'iva dal nome dell'impianto 1
            var iva = from i in database.Tables["IMPIANTO"].AsEnumerable()
                      where i.Field<string>("Nome") == cmbImpianto1.SelectedItem.ToString()
                      select new { Iva = i.Field<string>("PartitaIva")};

            in1.Iva = iva.ElementAt(0).Iva;
            in1.Viaggio = scarico.Codice;
            in1.Soa = cmbSoa1.SelectedItem.ToString();
            //controllo se il peso è inserito correttamente
            try
            {
                in1.PesoSoa = int.Parse(txtPeso1.Text);
            }
            catch
            {
                MessageBox.Show("Inserisci un peso > 0", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            //se <= 0 errore
            if (in1.PesoSoa <= 0)
            {
                MessageBox.Show("Inserisci un peso > 0", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            info.Add(in1);

            //controllo rimorchio
            if (chkRimorchio.Checked)
            {
                scarico.Rimorchio.Container2 = cmbContainer2.SelectedItem.ToString();
                scarico.Rimorchio.Rimorchio = cmbRimorchio.SelectedItem.ToString();
                //inserisce le informazioni anche per il rimorchio
                //prende l'iva dal nome dell'impianto 2
                var iva2 = from i in database.Tables["IMPIANTO"].AsEnumerable()
                           where i.Field<string>("Nome") == cmbImpianto2.SelectedItem.ToString()
                           select new { Iva = i.Field<string>("PartitaIva") };

                in1.Iva = iva2.ElementAt(0).Iva;
                in1.Soa = cmbSoa2.SelectedItem.ToString();
                //controllo se il peso è inserito correttamente
                try
                {
                    in1.PesoSoa = int.Parse(txtPeso2.Text);
                }
                catch
                {
                    MessageBox.Show("Inserisci un peso > 0", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                //se <= 0 errore
                if (in1.PesoSoa <= 0)
                {
                    MessageBox.Show("Inserisci un peso > 0", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                info.Add(in1);

            }
            else
            {
                scarico.Rimorchio.Rimorchio = "";
            }

            //assegna alle proprietà di output i valori
            Scarico = scarico;
            Infos = info;
            //chiude form
            this.Close();
        }

        private void cmbContainer1_SelectedIndexChanged(object sender, EventArgs e)
        {
            //il container 2 deve essere diverso dal container 1
            var conatiner = from c in database.Tables["[CONTAINER]"].AsEnumerable()
                            where c.Field<string>("CodiceIso") != cmbContainer1.SelectedItem.ToString()
                            select c.Field<string>("CodiceIso");

            cmbContainer2.Items.Clear();
            foreach (string s in conatiner)
            {
                cmbContainer2.Items.Add(s);
            }
            cmbContainer2.SelectedItem = cmbContainer2.Items[0];
        }
    }
}
