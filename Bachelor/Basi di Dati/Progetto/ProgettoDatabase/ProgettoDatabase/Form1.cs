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
    public partial class OP1Form : Form
    {
        public DataTable table { get; set; }
        
        public OP1Form() //operazione 1
        {
            InitializeComponent();
            //imposta dimensioni
            Size = new Size(344, 271);

        }

        public OP1Form(DataTable dt) //operazione 2
        {
            InitializeComponent();

            table = dt;

            //legge tutti i camionisti dai dipendenti
            var camionisti = from d in dt.AsEnumerable()
                             where d.Field<string>("Ruolo") == "Camionista"
                             select new { Nome = d.Field<string>("Nome")};

            //inizializza valori combobox
            foreach (var c in camionisti)
            {
                comboBox1.Items.Add(c.Nome);
            }
            comboBox1.SelectedItem = comboBox1.Items[0];

            //imposta dimensioni
            Size = new Size(507, 271);


        }

        public string GetDate()
        {
            return monthCalendar1.SelectionStart.Day.ToString() + "/" + monthCalendar1.SelectionStart.Month.ToString() + "/" + monthCalendar1.SelectionStart.Year.ToString();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            //se datatimepicker sono visibili
            if (dateTimePicker1.Visible && dateTimePicker2.Visible)
            {
                //la data del primo dataTimePicker deve essere minore del secondo
                if (dateTimePicker1.Value.CompareTo(dateTimePicker2.Value) > 0)
                {
                    MessageBox.Show("La prima data deve essere minore della seconda", "Errore Inserimento", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
            }
            this.Close();
        }

        public string SelectedCamionista()
        {
            //dal nome selezionato dall'utente ritorno il codice fiscale
            var cf = from t in table.AsEnumerable()
                     where t.Field<string>("Nome") == comboBox1.SelectedItem.ToString()
                     select new { Cf = t.Field<string>("CodiceFiscale")};
            return cf.ElementAt(0).Cf;
        }

        public string SelectedNomeCamionista()
        {
            return comboBox1.SelectedItem.ToString();
        }
    }
}
