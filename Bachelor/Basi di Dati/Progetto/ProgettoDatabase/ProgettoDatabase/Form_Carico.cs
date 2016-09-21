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
    public partial class Form_Carico : Form
    {
        DataSet database;
        //proprietà di output
        public Viaggio Carico { get; set; }
        public List<Informazioni_In_Viaggio> Infos { get; set; }

        public Form_Carico(DataSet database)
        {
            InitializeComponent();
            this.database = database;
            Inizializza();
        }

        public Form_Carico(DataSet database, Viaggio scarico)
        {
            InitializeComponent();
            this.database = database;
            Inizializza_Ottimizzato(scarico);
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
            foreach (DataRow c in camion.Rows)
            {
                cmbCamion.Items.Add(c.Field<string>("Targa"));
            }
            //iniizializza combobox camion col primo valore
            cmbCamion.SelectedItem = cmbCamion.Items[0];

            //legge container per container1
            var container = database.Tables["[CONTAINER]"];
            foreach (DataRow c in container.Rows)
            {
                cmbContainer1.Items.Add(c.Field<string>("CodiceIso"));
                cmbContainer2.Items.Add(c.Field<string>("CodiceIso"));
            }
            cmbContainer1.SelectedItem = cmbContainer1.Items[0];
            cmbContainer2.SelectedItem = cmbContainer2.Items[0];

            //legge lista clienti
            var clienti = database.Tables["CLIENTE"];
            foreach (DataRow c in clienti.Rows)
            {
                cmbCliente1.Items.Add(c.Field<string>("Nome"));
                cmbCliente2.Items.Add(c.Field<string>("Nome"));
            }
            cmbCliente1.SelectedItem = cmbCliente1.Items[0];
            cmbCliente2.SelectedItem = cmbCliente2.Items[0];

            //legge i Soa all'inizio
            List<string> soa1 = Soa_In_Cliente(cmbCliente1.SelectedItem.ToString());
            List<string> soa3 = Soa_In_Cliente(cmbCliente2.SelectedItem.ToString());
            cmbSoa1.Items.Clear();
            cmbSoa3.Items.Clear();
            foreach (string s in soa1)
            {
                cmbSoa1.Items.Add(s);
            }
            foreach (string s in soa3)
            {
                cmbSoa3.Items.Add(s);
            }
            cmbSoa1.SelectedItem = cmbSoa1.Items[0];
            cmbSoa3.SelectedItem = cmbSoa3.Items[0];

            //aggiorna anche soa per separatore stessa categoria
            cmbSoa2.Items.Clear();
            //prende soa della stessa categoria e li mette nella combobox
            List<string> soa2 = Soa_Stessa_Categoria(cmbCliente1.SelectedItem.ToString(), cmbSoa1.SelectedItem.ToString());
            foreach (string s in soa2)
            {
                cmbSoa2.Items.Add(s);
            }
            cmbSoa2.SelectedItem = cmbSoa2.Items[0];

            cmbSoa4.Items.Clear();
            //prende soa della stessa categoria e li mette nella combobox
            List<string> soa4 = Soa_Stessa_Categoria(cmbCliente2.SelectedItem.ToString(), cmbSoa3.SelectedItem.ToString());
            foreach (string s in soa2)
            {
                cmbSoa4.Items.Add(s);
            }
            cmbSoa4.SelectedItem = cmbSoa4.Items[0];

            //legge i rimorchi
            var rimorchi = database.Tables["[RIMORCHIO]"];
            foreach (DataRow r in rimorchi.Rows)
            {
                cmbRimorchio.Items.Add(r.Field<string>("Targa"));
            }
            cmbRimorchio.SelectedItem = cmbRimorchio.Items[0];
        }

        private void Inizializza_Ottimizzato(Viaggio scarico)
        {
            //il camionista è fissato
            cmbCamionista.Items.Add(scarico.Camionista);
            cmbCamionista.SelectedItem = cmbCamionista.Items[0];
            cmbCamionista.Enabled = false;

            //il camion è fissato
            cmbCamion.Items.Add(scarico.Camion);
            cmbCamion.SelectedItem = cmbCamion.Items[0];
            cmbCamion.Enabled = false;

            //legge container per container1
            var container = database.Tables["[CONTAINER]"];
            foreach (DataRow c in container.Rows)
            {
                cmbContainer1.Items.Add(c.Field<string>("CodiceIso"));
                cmbContainer2.Items.Add(c.Field<string>("CodiceIso"));
            }
            cmbContainer1.SelectedItem = cmbContainer1.Items[0];
            cmbContainer2.SelectedItem = cmbContainer2.Items[0];

            //legge lista clienti
            var clienti = database.Tables["CLIENTE"];
            foreach (DataRow c in clienti.Rows)
            {
                cmbCliente1.Items.Add(c.Field<string>("Nome"));
                cmbCliente2.Items.Add(c.Field<string>("Nome"));
            }
            cmbCliente1.SelectedItem = cmbCliente1.Items[0];
            cmbCliente2.SelectedItem = cmbCliente2.Items[0];

            //legge i Soa all'inizio
            List<string> soa1 = Soa_In_Cliente(cmbCliente1.SelectedItem.ToString());
            List<string> soa3 = Soa_In_Cliente(cmbCliente2.SelectedItem.ToString());
            cmbSoa1.Items.Clear();
            cmbSoa3.Items.Clear();
            foreach (string s in soa1)
            {
                cmbSoa1.Items.Add(s);
            }
            foreach (string s in soa3)
            {
                cmbSoa3.Items.Add(s);
            }
            cmbSoa1.SelectedItem = cmbSoa1.Items[0];
            cmbSoa3.SelectedItem = cmbSoa3.Items[0];

            //aggiorna anche soa per separatore stessa categoria
            cmbSoa2.Items.Clear();
            //prende soa della stessa categoria e li mette nella combobox
            List<string> soa2 = Soa_Stessa_Categoria(cmbCliente1.SelectedItem.ToString(), cmbSoa1.SelectedItem.ToString());
            foreach (string s in soa2)
            {
                cmbSoa2.Items.Add(s);
            }
            cmbSoa2.SelectedItem = cmbSoa2.Items[0];

            cmbSoa4.Items.Clear();
            //prende soa della stessa categoria e li mette nella combobox
            List<string> soa4 = Soa_Stessa_Categoria(cmbCliente2.SelectedItem.ToString(), cmbSoa3.SelectedItem.ToString());
            foreach (string s in soa2)
            {
                cmbSoa4.Items.Add(s);
            }
            cmbSoa4.SelectedItem = cmbSoa4.Items[0];

            //il rimorchio se c'è è fissato
            chkRimorchio.Enabled = false;
            cmbRimorchio.Enabled = false;
            if (scarico.Rimorchio.Rimorchio != "")
            {
                chkRimorchio.Checked = true;
                grpRimorchio.Visible = true;
                cmbRimorchio.Items.Add(scarico.Rimorchio.Rimorchio);
                cmbRimorchio.SelectedItem = cmbRimorchio.Items[0];
            }
            else
            {
                grpRimorchio.Visible = false;
            }

            //la data è fissata
            dateTimePicker1.Value = DateTime.Parse(scarico.Data);
            dateTimePicker1.Enabled = false;

            //la busta documenti è fissata
            txtBustaDoc.Text = scarico.BustaDocumenti;
            txtBustaDoc.Enabled = false;
        }

        private void chkSeparatore1_CheckedChanged(object sender, EventArgs e)
        {
            if (chkSeparatore1.Checked)
            {
                cmbSoa2.Enabled = true;
                txtPeso2.Enabled = true;
            }
            else
            {
                cmbSoa2.Enabled = false;
                txtPeso2.Enabled = false;
            }
        }

        private void chkSeparatore2_CheckedChanged(object sender, EventArgs e)
        {
            if (chkSeparatore2.Checked)
            {
                cmbSoa4.Enabled = true;
                txtPeso4.Enabled = true;
            }
            else
            {
                cmbSoa4.Enabled = false;
                txtPeso4.Enabled = false;
            }
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

        private void cmbCliente1_SelectedIndexChanged(object sender, EventArgs e)
        {
            cmbSoa1.Items.Clear();
            //al cambio dell'impianto cambiano anche i relativi soa_in_impianto
            List<string> soa1 = Soa_In_Cliente(cmbCliente1.SelectedItem.ToString());
            foreach (string s in soa1)
            {
                cmbSoa1.Items.Add(s);
            }
            cmbSoa1.SelectedItem = cmbSoa1.Items[0];

            cmbSoa2.Items.Clear();
            //prende soa della stessa categoria e li mette nella combobox
            List<string> soa2 = Soa_Stessa_Categoria(cmbCliente1.SelectedItem.ToString(), cmbSoa1.SelectedItem.ToString());
            foreach (string s in soa2)
            {
                cmbSoa2.Items.Add(s);
            }
            cmbSoa2.SelectedItem = cmbSoa2.Items[0];
        }

        private List<string> Soa_Stessa_Categoria(string cliente, string soa)
        {          
            //prende l'iva dal nome cliente
            var tmp_iva = from s in database.Tables["CLIENTE"].AsEnumerable()
                          where s.Field<string>("Nome") == cliente
                          select new { Iva = s.Field<string>("PartitaIva") };

            string iva_cliente = tmp_iva.ElementAt(0).Iva;

            //prende la categoria del soa passato come parametro
            var cat = from t in database.Tables["TIPO_DI_SOA"].AsEnumerable()
                      where t.Field<string>("Nome") == soa
                      select t.Field<int>("Categoria");

            int categoria = cat.ElementAt(0);

            //prende soa dal cliente passato come parametro della stessa categoria del soa passato come parametro
            var tmp_soa = from s in database.Tables["SOA_IN_CLIENTE"].AsEnumerable()
                          join t in database.Tables["TIPO_DI_SOA"].AsEnumerable() on s.Field<string>("Soa") equals t.Field<string>("Nome")
                          where s.Field<string>("Cliente") == iva_cliente && t.Field<int>("Categoria") == categoria
                          select t.Field<string>("Nome");

            return tmp_soa.ToList<string>();

        }

        private List<string> Soa_In_Cliente(string cliente)
        {
            //dal nome del cliente prendo l'iva
            var tmp_iva = from s in database.Tables["CLIENTE"].AsEnumerable()
                          where s.Field<string>("Nome") == cliente
                          select new { Iva = s.Field<string>("PartitaIva") };

            string iva = tmp_iva.ElementAt(0).Iva;

            var table = database.Tables["SOA_IN_CLIENTE"].AsEnumerable();
            var soa = from t in table
                      where t.Field<string>("Cliente") == iva
                      select t.Field<string>("Soa");

            return soa.ToList<string>();
        }

        private void cmbSoa1_SelectedIndexChanged(object sender, EventArgs e)
        {
            //nella combobox soa2 ci stanno solo soa della stessa categoria
            cmbSoa2.Items.Clear();
            //prende soa della stessa categoria e li mette nella combobox
            List<string> soa2 = Soa_Stessa_Categoria(cmbCliente1.SelectedItem.ToString(), cmbSoa1.SelectedItem.ToString());
            foreach (string s in soa2)
            {
                cmbSoa2.Items.Add(s);
            }
            cmbSoa2.SelectedItem = cmbSoa2.Items[0];
        }

        private void cmbCliente2_SelectedIndexChanged(object sender, EventArgs e)
        {
            cmbSoa3.Items.Clear();
            //al cambio dell'impianto cambiano anche i relativi soa_in_impianto
            List<string> soa3 = Soa_In_Cliente(cmbCliente2.SelectedItem.ToString());
            foreach (string s in soa3)
            {
                cmbSoa3.Items.Add(s);
            }
            cmbSoa3.SelectedItem = cmbSoa3.Items[0];

            cmbSoa4.Items.Clear();
            //prende soa della stessa categoria e li mette nella combobox
            List<string> soa4 = Soa_Stessa_Categoria(cmbCliente2.SelectedItem.ToString(), cmbSoa3.SelectedItem.ToString());
            foreach (string s in soa4)
            {
                cmbSoa4.Items.Add(s);
            }
            cmbSoa4.SelectedItem = cmbSoa4.Items[0];
        }

        private void cmbSoa3_SelectedIndexChanged(object sender, EventArgs e)
        {
            //nella combobox soa2 ci stanno solo soa della stessa categoria
            cmbSoa4.Items.Clear();
            //prende soa della stessa categoria e li mette nella combobox
            List<string> soa4 = Soa_Stessa_Categoria(cmbCliente2.SelectedItem.ToString(), cmbSoa3.SelectedItem.ToString());
            foreach (string s in soa4)
            {
                cmbSoa4.Items.Add(s);
            }
            cmbSoa4.SelectedItem = cmbSoa4.Items[0];
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

        private void button1_Click(object sender, EventArgs e)
        {
            Viaggio carico = new Viaggio();
            if (txtBustaDoc.Text == "")
            {
                MessageBox.Show("Inserisci Busta Documenti", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            carico.BustaDocumenti = txtBustaDoc.Text;
            carico.Camion = cmbCamion.SelectedItem.ToString();

            //se è un viaggio ottimizzato il camionista ha già il codice fiscale
            if (cmbCamionista.Enabled)
            {
                //dal nome del camionista prende il codice fiscale
                var cf = from d in database.Tables["DIPENDENTE"].AsEnumerable()
                         where d.Field<string>("Nome") == cmbCamionista.SelectedItem.ToString()
                         select new { CF = d.Field<string>("CodiceFiscale") };

                carico.Camionista = cf.ElementAt(0).CF;
            }
            else
            {
                carico.Camionista = cmbCamionista.SelectedItem.ToString();
            }
            
            //prende ultimo codice
            if (database.Tables["VIAGGIO_DI_CARICO"].Rows.Count == 0)
            {
                carico.Codice = 1;
            }
            else
            {
                //leggo il massimo codice inserito nel database
                var codici = from v in database.Tables["VIAGGIO_DI_CARICO"].AsEnumerable()
                             orderby v.Field<int>("Codice") descending
                             select v.Field<int>("Codice");

                int max = codici.ElementAt(0);

                carico.Codice = max + 1;
            }

            carico.Container1 = cmbContainer1.SelectedItem.ToString();
            carico.Data = dateTimePicker1.Value.Day.ToString() + "/" + dateTimePicker1.Value.Month.ToString() + "/" + dateTimePicker1.Value.Year.ToString();

            //riempie informazioni per container 1
            List<Informazioni_In_Viaggio> info = new List<Informazioni_In_Viaggio>();
            Informazioni_In_Viaggio in1 = new Informazioni_In_Viaggio();
            //prende l'iva dal nome dell'impianto 1
            var iva = from i in database.Tables["CLIENTE"].AsEnumerable()
                      where i.Field<string>("Nome") == cmbCliente1.SelectedItem.ToString()
                      select new { Iva = i.Field<string>("PartitaIva") };

            in1.Iva = iva.ElementAt(0).Iva;
            in1.Viaggio = carico.Codice;
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

            //controllo separatore per il container 1
            if (chkSeparatore1.Checked)
            {
                //aggiunge un'altra informazione carico
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

            //controllo rimorchio
            if (chkRimorchio.Checked)
            {
                carico.Rimorchio.Container2 = cmbContainer2.SelectedItem.ToString();
                carico.Rimorchio.Rimorchio = cmbRimorchio.SelectedItem.ToString();
                //inserisce le informazioni anche per il rimorchio
                //prende l'iva dal nome dell'impianto 2
                var iva2 = from i in database.Tables["CLIENTE"].AsEnumerable()
                           where i.Field<string>("Nome") == cmbCliente2.SelectedItem.ToString()
                           select new { Iva = i.Field<string>("PartitaIva") };

                in1.Iva = iva2.ElementAt(0).Iva;
                in1.Soa = cmbSoa3.SelectedItem.ToString();
                //controllo se il peso è inserito correttamente
                try
                {
                    in1.PesoSoa = int.Parse(txtPeso3.Text);
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

                //controllo separatore per il container 2 nel rimorchio
                if (chkSeparatore2.Checked)
                {
                    //aggiungo un'altra informazione carico
                    in1.Soa = cmbSoa4.SelectedItem.ToString();
                    //controllo se il peso è inserito correttamente
                    try
                    {
                        in1.PesoSoa = int.Parse(txtPeso4.Text);
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

            }
            else
            {
                carico.Rimorchio.Rimorchio = "";
            }
            
            //ottiene giorno precedente per il confronto, perchè il datetimepicker ha anche l'ora
            //nel database l'ora nn c'è 
            DateTime precedente = dateTimePicker1.Value.AddDays(-1);
            DateTime attuale = dateTimePicker1.Value;
            //genero il numero viaggio = numeroviaggio di camionista e data più alto + 1
            //leggo il massimo codice inserito nel database
            var numeri_viaggio = from v in database.Tables["VIAGGIO_DI_CARICO"].AsEnumerable()
                                 where v.Field<string>("Camionista") == carico.Camionista && v.Field<DateTime>("Data").CompareTo(precedente) > 0 && v.Field<DateTime>("Data").CompareTo(attuale) <= 0
                                 orderby v.Field<int>("NumeroViaggio") descending
                                 select v.Field<int>("NumeroViaggio");

            int max_num = 0;
            if (numeri_viaggio.Count() > 0)
            {
                //c'era almeno un viaggio di carico con lo stesso camionista e data
                max_num = numeri_viaggio.ElementAt(0);
            }

            carico.NumeroViaggio = max_num + 1; //è progressivo

            //assegna alle proprietà di output i valori
            Carico = carico;
            Infos = info;
            //chiude form
            this.Close();
        }
    }
}
