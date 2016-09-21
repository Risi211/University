using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Data.OleDb;
using Microsoft.Win32;
using System.Data;

namespace ProgettoDatabase
{
    /// <summary>
    /// Logica di interazione per MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        //quando si fa la connessione si leggono tutte le tabelle in un dataSet che si interroga col LinQ
        DataSet database = new DataSet();
        string connection_string = "";
        string[] tables = {
                                  "CAMION",
                                  "[RIMORCHIO]",
                                  "DIPENDENTE",
                                  "CLIENTE",
                                  "IMPIANTO",
                                  "PAGAMENTO_CLIENTE",
                                  "PAGAMENTO_IMPIANTO",
                                  "VIAGGIO_DI_CARICO",
                                  "VIAGGIO_DI_SCARICO",
                                  "VIAGGIO_OTTIMIZZATO",
                                  "[CONTAINER]",
                                  "LAVAGGIO",
                                  "RIMORCHIO_IN_CARICO",
                                  "RIMORCHIO_IN_SCARICO",
                                  "TIPO_DI_SOA",
                                  "INFORMAZIONI_CARICO",
                                  "INFORMAZIONI_SCARICO",
                                  "SOA_IN_IMPIANTO",
                                  "SOA_IN_CLIENTE"
                              };

        public MainWindow()
        {
            InitializeComponent();
        }

        private void MenuItem_Click(object sender, RoutedEventArgs e)
        {
            
            MenuItem m = (MenuItem)sender;
            switch (m.Header.ToString())
            {
                case "Container":
                    {
                        int contatore = 0;
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();                       
                        for (int i = 0; i < 100; i++)
                        {
                            OleDbCommand comando = new OleDbCommand();
                            comando.Connection = connessione;
                            Container c = GeneraContainer();
                            string insert = "insert into [CONTAINER] values ('"+c.codiceISO+"', '"+c.dataAcquisto+"', '" + c.Volume.ToString() + "')";
                            comando.CommandText = insert;
                            bool inserito = true;
                            try
                            {
                                contatore += comando.ExecuteNonQuery();
                            }
                            catch //(OleDbException ex)
                            {
                                inserito = false;
                                //la chiave primaria è gia presente nella tabella CONTAINER
                            }

                            if (inserito)
                            {
                                //aggiorna database locale
                                DataRow dn = database.Tables["[CONTAINER]"].NewRow();
                                dn.SetField<string>("CodiceISO", c.codiceISO);
                                dn.SetField<string>("DataAcquisto", c.dataAcquisto);
                                database.Tables["[CONTAINER]"].Rows.Add(dn);
                            }

                       }
                        
                        connessione.Close();
                        MessageBox.Show("In tutto sono stati inseriti " + contatore.ToString() + " container","Inserimento Container",MessageBoxButton.OK, MessageBoxImage.Information);

                        break;
                    }
                case "Lavaggio":
                    {
                        //chiedo in input la settimana in cui generare i viaggi
                        OP1Form f = new OP1Form();
                        //settaggio 1 per il form dei parametri
                        Settaggio1(f,7); //chiede in input una data settimana
                        f.ShowDialog();   

                        int contatore = 0;
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();
                        for (int i = 0; i < 100; i++)
                        {
                            OleDbCommand comando = new OleDbCommand();
                            comando.Connection = connessione;
                            Lavaggio l = GeneraLavaggio(f.monthCalendar1.SelectionStart,f.monthCalendar1.SelectionEnd);
                            string insert = "insert into LAVAGGIO values ('" + l.Container + "', '" + l.Addetto + "', '" + l.Data + "')";
                            comando.CommandText = insert;
                            bool inserito = true;
                            try
                            {
                                contatore += comando.ExecuteNonQuery();
                            }
                            catch //(OleDbException ex)
                            {
                                inserito = false;
                            }
                            if (inserito)
                            {
                                //aggiorna database locale
                                DataRow dn = database.Tables["LAVAGGIO"].NewRow();
                                dn.SetField<string>("Addetto", l.Addetto);
                                dn.SetField<string>("Data", l.Data);
                                dn.SetField<string>("Container", l.Container);
                                database.Tables["LAVAGGIO"].Rows.Add(dn);
                            }

                        }
                        connessione.Close();
                        MessageBox.Show("In tutto sono stati inseriti " + contatore.ToString() + " lavaggi", "Inserimento Lavaggi", MessageBoxButton.OK, MessageBoxImage.Information);
                        break;
                    }
                case "Pagamento Cliente":
                    {
                        OP1Form f = new OP1Form();
                        //1 giorno, dalla data si prende poi il mese e l'anno
                        Settaggio1(f, 1);
                        f.ShowDialog();   

                        int contatore = 0;
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();
                        for (int i = 0; i < 30; i++)
                        {
                            OleDbCommand comando = new OleDbCommand();
                            comando.Connection = connessione;
                            Pagamento p = Genera_Pagamento(true, f.monthCalendar1.SelectionStart);
                            bool inserito = Inserisci_Pagamento(true, p, comando);
                            if (inserito)
                            {
                                Aggiorna_Pagamento(true, p);
                                contatore++;
                            }

                        }
                        connessione.Close();
                        MessageBox.Show("In tutto sono stati inseriti " + contatore.ToString() + " pagamenti cliente", "Inserimento Pagamenti Cliente", MessageBoxButton.OK, MessageBoxImage.Information);
                        break;
                    }
                case "Pagamento Impianto":
                    {
                        OP1Form f = new OP1Form();
                        //1 giorno, dalla data si prende poi il mese e l'anno
                        Settaggio1(f, 1);
                        f.ShowDialog();   

                        int contatore = 0;
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();
                        for (int i = 0; i < 20; i++)
                        {
                            OleDbCommand comando = new OleDbCommand();
                            comando.Connection = connessione;
                            Pagamento p = Genera_Pagamento(false, f.monthCalendar1.SelectionStart);
                            bool inserito = Inserisci_Pagamento(false, p, comando);
                            if (inserito)
                            {
                                Aggiorna_Pagamento(false, p);
                                contatore++;
                            }

                        }
                        connessione.Close();
                        MessageBox.Show("In tutto sono stati inseriti " + contatore.ToString() + " pagamenti impianti", "Inserimento Pagamenti Impianti", MessageBoxButton.OK, MessageBoxImage.Information);
                        break;
                    }
                case "Viaggi Di Scarico": //genera anche per RIMORCHIO_IN_SCARICO
                    {
                        //chiedo in input la settimana in cui generare i viaggi
                        OP1Form f = new OP1Form();
                        Settaggio1(f, 7);
                        f.ShowDialog();   

                        int contatore = 0;
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();
                        for (int i = 0; i < 100; i++)
                        {
                            OleDbCommand comando = new OleDbCommand();
                            comando.Connection = connessione;
                            Viaggio scarico = Genera_Viaggi(false,f.monthCalendar1.SelectionStart, f.monthCalendar1.SelectionEnd)[0]; //(0) indica il viaggio di scarico, è un vettore ma ritorna solo un viaggio di scarico, quindi [0]
                            bool inserito = Inserisci_Viaggio(false, scarico, comando);
                            if (inserito)
                            {
                                contatore++;

                                Aggiorna_Viaggio(false, scarico);                            

                                //inserisce l'eventuale rimorchio
                                if (scarico.Rimorchio.Rimorchio != "")
                                {                                    
                                    Inserisci_Rimorchio(false, scarico.Rimorchio, scarico.Codice, comando);
                                }

                                //inserise le informazioni del viaggio creato in INFORMAZIONI_SCARICO
                                Inserisci_Informazioni(false, Genera_Informazioni_Scarico(scarico), comando);
                            }

                        }
                        connessione.Close();
                        MessageBox.Show("In tutto sono stati inseriti " + contatore.ToString() + " viaggi di Scarico", "Inserimento Viaggi di Scarico", MessageBoxButton.OK, MessageBoxImage.Information);
                        break;
                    }
                case "Viaggi Di Carico": //genera anche per RIMORCHIO_IN_CARICO
                    {
                        //chiedo in input la settimana in cui generare i viaggi
                        OP1Form f = new OP1Form();
                        Settaggio1(f, 7);
                        f.ShowDialog(); 

                        int contatore = 0;
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();
                        for (int i = 0; i < 100; i++)
                        {
                            OleDbCommand comando = new OleDbCommand();
                            comando.Connection = connessione;
                            Viaggio[] viaggi = Genera_Viaggi(true,f.monthCalendar1.SelectionStart, f.monthCalendar1.SelectionEnd);
                            foreach(Viaggio carico in viaggi)
                            {
                                bool inserito = Inserisci_Viaggio(true, carico, comando);
                                if (inserito)
                                {
                                    contatore++;

                                    Aggiorna_Viaggio(true, carico);

                                    if (carico.Rimorchio.Rimorchio != "")
                                    {
                                        Inserisci_Rimorchio(true, carico.Rimorchio, carico.Codice, comando);
                                    }

                                    Inserisci_Informazioni(true, Genera_Informazioni_Carico(carico), comando);
                                }

                            }

                        }
                        connessione.Close();
                        MessageBox.Show("In tutto sono stati inseriti " + contatore.ToString() + " viaggi di Carico", "Inserimento Viaggi di  Carico", MessageBoxButton.OK, MessageBoxImage.Information);

                        break;
                    }
                case "Viaggi Ottimizzati": //genera anche per RIMORCHIO_IN_SCARICO e RIMORCHIO_IN_CARICO
                    {
                        //chiedo in input la settimana in cui generare i viaggi
                        OP1Form f = new OP1Form();
                        Settaggio1(f, 7);
                        f.ShowDialog(); 

                        int contatore = 0;
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();
                        for (int i = 0; i < 20; i++)
                        {
                            OleDbCommand comando = new OleDbCommand();
                            comando.Connection = connessione;
                            Viaggio[] viaggi = Genera_Ottimizzato(f.monthCalendar1.SelectionStart, f.monthCalendar1.SelectionEnd);

                            //prima inserisce il viaggio di scarico
                            Viaggio scarico = viaggi[0];
                            bool inserito = Inserisci_Viaggio(false, scarico, comando);

                            if (inserito)
                            {
                                Aggiorna_Viaggio(false, scarico);

                                if (scarico.Rimorchio.Rimorchio != "")
                                {
                                    Inserisci_Rimorchio(false, scarico.Rimorchio, scarico.Codice, comando);
                                }

                                Inserisci_Informazioni(false, Genera_Informazioni_Scarico(scarico), comando);

                            }

                            //poi inserisce i viaggi di carico
                            int num_carichi = (viaggi.Length == 3) ? 1 : 2;
                            for (int k = 1; k <= num_carichi; k++)
                            {
                                Viaggio carico = viaggi[k];
                                inserito = Inserisci_Viaggio(true, carico, comando);

                                if (inserito)
                                {
                                    Aggiorna_Viaggio(true, carico);

                                    if (carico.Rimorchio.Rimorchio != "")
                                    {
                                        Inserisci_Rimorchio(true, carico.Rimorchio, carico.Codice, comando);
                                    }

                                    Inserisci_Informazioni(true, Genera_Informazioni_Carico(carico), comando);
                                }
                            }

                            //infine inserisce il viaggio ottimizzato
                            Viaggio ottimizzato = viaggi[viaggi.Length - 1];
                            inserito = Inserisci_Ottimizzato(ottimizzato, comando);
                            if (inserito)
                            {
                                Aggiorna_Ottimizzato(ottimizzato);
                                contatore++;
                            }

                        }
                        connessione.Close();
                        MessageBox.Show("In tutto sono stati inseriti " + contatore.ToString() + " viaggi ottimizzati", "Inserimento Viaggi Ottimizzati", MessageBoxButton.OK, MessageBoxImage.Information);

                        break;
                    }
                default:
                    {
                        break;
                    }
            }

        }

        private void Connessione_Click(object sender, RoutedEventArgs e)
        {
            
            OpenFileDialog op = new OpenFileDialog();
            op.Filter = "Database Access|*.accdb";
            op.Multiselect = false;
            op.Title = "apri database access";
            if (op.ShowDialog() == true)
            {
               connection_string = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" + op.FileName + "; Persist Security Info=False;";
            }
            //legge tutte le tabelle e le memorizza nel dataset
            LeggiDatabase();
            //abilita la generazione dei dati
            ((MenuItem)(menu1.Items[1])).IsEnabled = true;
            MenuItem m = new MenuItem();
        }

        private void Operazioni_Click(object sender, RoutedEventArgs e)
        {
            MenuItem m = (MenuItem)sender;
            switch (m.Uid.ToString())
            {
                case "v1": //clienti serviti in un giorno
                    {
                        OP1Form f = new OP1Form();
                        Settaggio1(f, 7);
                        f.ShowDialog();
                        //filtra dal Dataset tutti i viaggi di carico del giorno selezionato
                        var viaggio_di_carico = database.Tables["VIAGGIO_DI_CARICO"].AsEnumerable();
                        var informazioni_carico = database.Tables["INFORMAZIONI_CARICO"].AsEnumerable();
                        var cliente = database.Tables["CLIENTE"].AsEnumerable();
                        //table è una enumerableRowCollection<DataRow>
                        
                        //filtro viaggi di carico del giorno selezionato
                        var tmp_clienti = from v in viaggio_di_carico
                                          join i in informazioni_carico on v.Field<int>("Codice") equals i.Field<int>("Viaggio")
                                          join c in cliente on i.Field<string>("Cliente") equals c.Field<string>("PartitaIva") 
                                          where v.Field<DateTime>("Data").CompareTo(f.monthCalendar1.SelectionStart) >= 0 && v.Field<DateTime>("Data").CompareTo(f.monthCalendar1.SelectionEnd) <= 0
                                          select new { Iva = i.Field<string>("Cliente"), Nome = c.Field<string>("Nome"), Referente = c.Field<string>("Referente"), Telefono = c.Field<string>("Telefono"), Sede = c.Field<string>("Sede") };

                        var clienti = tmp_clienti.Distinct();

                        //crea grid view per visualizzare l'output
                        gridView1.ColumnHeaderToolTip = "Clienti";

                        GridViewColumn column1 = new GridViewColumn();
                        column1.DisplayMemberBinding = new Binding("Iva");
                        column1.Header = "Iva";
                        column1.Width = 100;

                        GridViewColumn column2 = new GridViewColumn();
                        column2.DisplayMemberBinding = new Binding("Nome");
                        column2.Header = "Nome";
                        column2.Width = 100;

                        GridViewColumn column3 = new GridViewColumn();
                        column3.DisplayMemberBinding = new Binding("Referente");
                        column3.Header = "Referente";
                        column3.Width = 100;

                        GridViewColumn column4 = new GridViewColumn();
                        column4.DisplayMemberBinding = new Binding("Telefono");
                        column4.Header = "Telefono";
                        column4.Width = 100;

                        GridViewColumn column5 = new GridViewColumn();
                        column5.DisplayMemberBinding = new Binding("Sede");
                        column5.Header = "Sede";
                        column5.Width = 100;

                        gridView1.Columns.Clear();

                        gridView1.Columns.Add(column1);
                        gridView1.Columns.Add(column2);
                        gridView1.Columns.Add(column3);
                        gridView1.Columns.Add(column4);
                        gridView1.Columns.Add(column5);

                        listView1.ItemsSource = clienti;
    
                        break;
                    }
                case "v2": //viaggi di scarico fatti da un camionista in un periodo
                    {
                        OP1Form f = new OP1Form(database.Tables["DIPENDENTE"]);
                        Settaggio2(f);
                        f.ShowDialog();
                        
                        //prende il nome del camionista dal codice fiscale:
                        string camionista = f.SelectedNomeCamionista();

                        var viaggio_di_scarico = database.Tables["VIAGGIO_DI_SCARICO"].AsEnumerable();
                        var rimorchi_in_scarico = database.Tables["RIMORCHIO_IN_SCARICO"].AsEnumerable();
                        var informazioni_scarico = database.Tables["INFORMAZIONI_SCARICO"].AsEnumerable();
                        var impianto = database.Tables["IMPIANTO"].AsEnumerable();

                        
                        var scarichi = from vs in viaggio_di_scarico
                                       //left join, anche chi non ha il rimorchio rimane
                                       join rs in rimorchi_in_scarico on vs.Field<int>("Codice") equals rs.Field<int>("Viaggio") into vrs
                                       join ins in informazioni_scarico on vs.Field<int>("Codice") equals ins.Field<int>("Viaggio")
                                       join imp in impianto on ins.Field<string>("Impianto") equals imp.Field<string>("PartitaIVA")
                                       where vs.Field<DateTime>("Data").CompareTo(f.dateTimePicker1.Value) >= 0 && vs.Field<DateTime>("Data").CompareTo(f.dateTimePicker2.Value) <= 0 && vs.Field<string>("Camionista") == f.SelectedCamionista()
                                       from vrs2 in vrs.DefaultIfEmpty()
                                       select new { Camionista = camionista, Data = vs.Field<DateTime>("Data"), Camion = vs.Field<string>("Camion"), Container1 = vs.Field<string>("Container1"), BustaDocumenti = vs.Field<string>("BustaDocumenti"), Rimorchio = (vrs2 == null) ? "" : vrs2.Field<string>("Rimorchio"), Container2 = (vrs2 == null) ? "" : vrs2.Field<string>("Container2"), Impianto = imp.Field<string>("Nome"), Soa = ins.Field<string>("Soa"), PesoSoa = ins.Field<int>("PesoSoa") };

                        //crea grid view per visualizzare l'output
                        gridView1.ColumnHeaderToolTip = "Viaggi Di Scarico";

                        GridViewColumn column1 = new GridViewColumn();
                        column1.DisplayMemberBinding = new Binding("Camionista");
                        column1.Header = "Camionista";
                        column1.Width = 100;

                        GridViewColumn column2 = new GridViewColumn();
                        //column2.DisplayMemberBinding = new Binding("Data");
                        //DisplayMemberBinding da in output una semplice stringa
                        //in questo caso stampa dd/mm/yyy 00:00 AM
                        //per cambiare l'output si usa la celTemplate (è la seconda scelta del compilatore)
                        //e si imposta lo string format quando si fa il Binding col campo DateTime

                        column2.Header = "Data";
                        column2.Width = 100;
                        //in xaml bastava mettere
                        //<DataTemplate>
                        //<TextBlock ..../> 
                        //</DataTemplate>
                        //il TextBlock è associato alla proprietà VisualTree del DataTemplate
                        var tb = new FrameworkElementFactory(typeof(TextBlock));
                        tb.Name = "tb";
                        //
                        Binding b = new Binding();
                        b.StringFormat = "dd/MM/yyyy";
                        b.Path = new PropertyPath("Data");
                        tb.SetBinding(TextBlock.TextProperty, b);
                        //
                        DataTemplate ddd = new DataTemplate();
                        ddd.VisualTree = tb;
                        //
                        column2.CellTemplate = ddd;

                        GridViewColumn column3 = new GridViewColumn();
                        column3.DisplayMemberBinding = new Binding("Camion");
                        column3.Header = "Camion";
                        column3.Width = 100;

                        GridViewColumn column4 = new GridViewColumn();
                        column4.DisplayMemberBinding = new Binding("Container1");
                        column4.Header = "Container 1";
                        column4.Width = 100;

                        GridViewColumn column5 = new GridViewColumn();
                        column5.DisplayMemberBinding = new Binding("Rimorchio");
                        column5.Header = "Rimorchio";
                        column5.Width = 100;

                        GridViewColumn column6 = new GridViewColumn();
                        column6.DisplayMemberBinding = new Binding("Container2");
                        column6.Header = "Container2";
                        column6.Width = 100;

                        GridViewColumn column7 = new GridViewColumn();
                        column7.DisplayMemberBinding = new Binding("Impianto");
                        column7.Header = "Impianto";
                        column7.Width = 100;

                        GridViewColumn column8 = new GridViewColumn();
                        column8.DisplayMemberBinding = new Binding("Soa");
                        column8.Header = "Soa";
                        column8.Width = 100;

                        GridViewColumn column9 = new GridViewColumn();
                        column9.DisplayMemberBinding = new Binding("PesoSoa");
                        column9.Header = "PesoSoa";
                        column9.Width = 100;

                        GridViewColumn column10 = new GridViewColumn();
                        column10.DisplayMemberBinding = new Binding("BustaDocumenti");
                        column10.Header = "Busta Documenti";
                        column10.Width = 100;

                        gridView1.Columns.Clear();

                        gridView1.Columns.Add(column1);
                        gridView1.Columns.Add(column2);
                        gridView1.Columns.Add(column3);
                        gridView1.Columns.Add(column4);
                        gridView1.Columns.Add(column5);
                        gridView1.Columns.Add(column6);
                        gridView1.Columns.Add(column7);
                        gridView1.Columns.Add(column8);
                        gridView1.Columns.Add(column9);
                        gridView1.Columns.Add(column10);

                        listView1.ItemsSource = scarichi;
                        
                        break;
                    }
                case "v3": //viaggi di carico fatti da un camionista in un periodo
                    {
                        OP1Form f = new OP1Form(database.Tables["DIPENDENTE"]);
                        Settaggio2(f);
                        f.ShowDialog();

                        //prende il nome del camionista dal codice fiscale:
                        string camionista = f.SelectedNomeCamionista();

                        var viaggio_di_carico = database.Tables["VIAGGIO_DI_CARICO"].AsEnumerable();
                        var rimorchi_in_carico = database.Tables["RIMORCHIO_IN_CARICO"].AsEnumerable();
                        var informazioni_carico = database.Tables["INFORMAZIONI_CARICO"].AsEnumerable();
                        var cliente = database.Tables["CLIENTE"].AsEnumerable();


                        var carichi = from vs in viaggio_di_carico
                                       //left join, anche chi non ha il rimorchio rimane
                                       join rs in rimorchi_in_carico on vs.Field<int>("Codice") equals rs.Field<int>("Viaggio") into vrs
                                       join ins in informazioni_carico on vs.Field<int>("Codice") equals ins.Field<int>("Viaggio")
                                       join cl in cliente on ins.Field<string>("Cliente") equals cl.Field<string>("PartitaIVA")
                                       where vs.Field<DateTime>("Data").CompareTo(f.dateTimePicker1.Value) >= 0 && vs.Field<DateTime>("Data").CompareTo(f.dateTimePicker2.Value) <= 0 && vs.Field<string>("Camionista") == f.SelectedCamionista()
                                       from vrs2 in vrs.DefaultIfEmpty()
                                       select new { Camionista = camionista, Data = vs.Field<DateTime>("Data"), NumeroViaggio = vs.Field<int>("NumeroViaggio"), Camion = vs.Field<string>("Camion"), Container1 = vs.Field<string>("Container1"), BustaDocumenti = vs.Field<string>("BustaDocumenti"), Rimorchio = (vrs2 == null) ? "" : vrs2.Field<string>("Rimorchio"), Container2 = (vrs2 == null) ? "" : vrs2.Field<string>("Container2"), Cliente = cl.Field<string>("Nome"), Soa = ins.Field<string>("Soa"), PesoSoa = ins.Field<int>("PesoSoa") };

                        //crea grid view per visualizzare l'output
                        gridView1.ColumnHeaderToolTip = "Viaggi Di Carico";

                        GridViewColumn column1 = new GridViewColumn();
                        column1.DisplayMemberBinding = new Binding("Camionista");
                        column1.Header = "Camionista";
                        column1.Width = 100;

                        GridViewColumn column2 = new GridViewColumn();
                        //column2.DisplayMemberBinding = new Binding("Data");
                        //DisplayMemberBinding da in output una semplice stringa
                        //in questo caso stampa dd/mm/yyy 00:00 AM
                        //per cambiare l'output si usa la celTemplate (è la seconda scelta del compilatore)
                        //e si imposta lo string format quando si fa il Binding col campo DateTime

                        column2.Header = "Data";
                        column2.Width = 100;
                        //in xaml bastava mettere
                        //<DataTemplate>
                        //<TextBlock ..../> 
                        //</DataTemplate>
                        //il TextBlock è associato alla proprietà VisualTree del DataTemplate
                        var tb = new FrameworkElementFactory(typeof(TextBlock));
                        tb.Name = "tb";
                        //
                        Binding b = new Binding();
                        b.StringFormat = "dd/MM/yyyy";
                        b.Path = new PropertyPath("Data");
                        tb.SetBinding(TextBlock.TextProperty, b);
                        //
                        DataTemplate ddd = new DataTemplate();
                        ddd.VisualTree = tb;
                        //
                        column2.CellTemplate = ddd;

                        GridViewColumn column3 = new GridViewColumn();
                        column3.DisplayMemberBinding = new Binding("Camion");
                        column3.Header = "Camion";
                        column3.Width = 100;

                        GridViewColumn column4 = new GridViewColumn();
                        column4.DisplayMemberBinding = new Binding("Container1");
                        column4.Header = "Container 1";
                        column4.Width = 100;

                        GridViewColumn column5 = new GridViewColumn();
                        column5.DisplayMemberBinding = new Binding("Rimorchio");
                        column5.Header = "Rimorchio";
                        column5.Width = 100;

                        GridViewColumn column6 = new GridViewColumn();
                        column6.DisplayMemberBinding = new Binding("Container2");
                        column6.Header = "Container2";
                        column6.Width = 100;

                        GridViewColumn column7 = new GridViewColumn();
                        column7.DisplayMemberBinding = new Binding("Cliente");
                        column7.Header = "Cliente";
                        column7.Width = 100;

                        GridViewColumn column8 = new GridViewColumn();
                        column8.DisplayMemberBinding = new Binding("Soa");
                        column8.Header = "Soa";
                        column8.Width = 100;

                        GridViewColumn column9 = new GridViewColumn();
                        column9.DisplayMemberBinding = new Binding("PesoSoa");
                        column9.Header = "PesoSoa";
                        column9.Width = 100;

                        GridViewColumn column10 = new GridViewColumn();
                        column10.DisplayMemberBinding = new Binding("BustaDocumenti");
                        column10.Header = "Busta Documenti";
                        column10.Width = 100;

                        GridViewColumn column11 = new GridViewColumn();
                        column11.DisplayMemberBinding = new Binding("NumeroViaggio");
                        column11.Header = "Numero Viaggio";
                        column11.Width = 100;

                        gridView1.Columns.Clear();

                        gridView1.Columns.Add(column1);
                        gridView1.Columns.Add(column2);
                        gridView1.Columns.Add(column11);
                        gridView1.Columns.Add(column3);
                        gridView1.Columns.Add(column4);
                        gridView1.Columns.Add(column5);
                        gridView1.Columns.Add(column6);
                        gridView1.Columns.Add(column7);
                        gridView1.Columns.Add(column8);
                        gridView1.Columns.Add(column9);
                        gridView1.Columns.Add(column10);

                        listView1.ItemsSource = carichi;
                        break;
                    }
                case "v4": //container utilizzati almeno in un viaggio (carico o scarico) che non sono stati lavati nell'ultima settimana
                    {
                        OP1Form f = new OP1Form();
                        Settaggio1(f, 7);
                        f.ShowDialog();   

                        //prende elenco di tutti i viaggi di carico della settimana selezionata
                        //con tutti i container trasportati nei rimorchi (se presenti)
                        //in pratica fa un left outer join tra la tabella VIAGGIO_DI_CARICO e RIMORCHIO_IN_CARICO
                        var viaggio_di_carico = database.Tables["VIAGGIO_DI_CARICO"].AsEnumerable();
                        var rimorchi_in_carico = database.Tables["RIMORCHIO_IN_CARICO"].AsEnumerable();
                        var carichi = from c in viaggio_di_carico
                                      join r in rimorchi_in_carico on c.Field<int>("Codice") equals r.Field<int>("Viaggio") into cr
                                      where c.Field<DateTime>("Data").CompareTo(f.monthCalendar1.SelectionStart) >= 0 && c.Field<DateTime>("Data").CompareTo(f.monthCalendar1.SelectionEnd) <= 0
                                      from c2 in cr.DefaultIfEmpty()
                                      select new { Container1 = c.Field<string>("Container1"), Container2 = (c2 == null) ? "" : c2.Field<string>("Container2") };

                        
                        //stessa cosa per i viaggi di scarico
                        var viaggio_di_scarico = database.Tables["VIAGGIO_DI_SCARICO"].AsEnumerable();
                        var rimorchi_in_scarico = database.Tables["RIMORCHIO_IN_SCARICO"].AsEnumerable();
                        var scarichi = from c in viaggio_di_scarico
                                      join r in rimorchi_in_scarico on c.Field<int>("Codice") equals r.Field<int>("Viaggio") into cr
                                      where c.Field<DateTime>("Data").CompareTo(f.monthCalendar1.SelectionStart) >= 0 && c.Field<DateTime>("Data").CompareTo(f.monthCalendar1.SelectionEnd) <= 0
                                      from c2 in cr.DefaultIfEmpty()
                                      select new { Container1 = c.Field<string>("Container1"), Container2 = (c2 == null) ? "" : c2.Field<string>("Container2") };
                        
                        //prende l'elenco di tutti i container che sono stati lavati l'ultima settimana
                        var lavaggio = database.Tables["LAVAGGIO"].AsEnumerable();
                        var lavati = from l in lavaggio
                                     where l.Field<DateTime>("Data").CompareTo(f.monthCalendar1.SelectionStart) >= 0 && l.Field<DateTime>("Data").CompareTo(f.monthCalendar1.SelectionEnd) <= 0
                                     select l.Field<string>("Container");


                        //fa la differenza fra i container impegnati in almeno un viaggio e i container lavati nell'ultima settimana
                        //così si trovano quelli che devono essere ancora lavati

                        List<string> tmp_non_lavati = new List<string>();
                        //prima va a vedere i container nei viaggio di carico
                        //per ogni viaggio ci possono essere 2 container
                        //se un container non è stato lavato lo aggiunge alla lista di
                        //output (se non era già stato inserito)
                        foreach (var container in carichi)
                        {
                            //prima controllo container1
                            if (!lavati.Contains<string>(container.Container1))
                            {
                                tmp_non_lavati.Add(container.Container1);
                            }
                            //poi il container2, se diverso da ""
                            if (container.Container2 != "")
                            {
                                if (!lavati.Contains<string>(container.Container2))
                                {
                                    tmp_non_lavati.Add(container.Container2);
                                }
                            }
                        }
                        //stessa cosa per i container nei viaggi di scarico
                        foreach (var container in scarichi)
                        {
                            //prima controllo container1
                            if (!lavati.Contains<string>(container.Container1))
                            {
                                tmp_non_lavati.Add(container.Container1);
                            }
                            //poi il container2
                            if (container.Container2 != "")
                            {
                                if (!lavati.Contains<string>(container.Container2))
                                {
                                    tmp_non_lavati.Add(container.Container2);
                                }
                            }
                        }

                        //elimina eventuali container duplicati
                        IEnumerable<string> non_lavati = tmp_non_lavati.Distinct<string>();

                        //visualizza output
                        gridView1.ColumnHeaderToolTip = "Container";

                        GridViewColumn column1 = new GridViewColumn();
                        column1.DisplayMemberBinding = new Binding("");
                        column1.Header = "Container Non Lavati";
                        column1.Width = 200;

                        gridView1.Columns.Clear();

                        gridView1.Columns.Add(column1);


                        listView1.ItemsSource = non_lavati;

                        break;
                    }
                case "v5": //bilancio netto di un mese / anno
                    {
                        OP1Form f = new OP1Form();
                        Settaggio1(f,1); //dal giorno selezionato ricava il mese
                        f.ShowDialog();

                        var pagamento_cliente = database.Tables["PAGAMENTO_CLIENTE"].AsEnumerable();
                        var pagamento_impianto = database.Tables["PAGAMENTO_IMPIANTO"].AsEnumerable();

                        //legge prima tutti gli incassi e ne fa la somma
                        var incassi = from p in pagamento_cliente
                                      where p.Field<int>("Anno") == f.monthCalendar1.SelectionStart.Year && p.Field<int>("Mese") == f.monthCalendar1.SelectionStart.Month
                                      select p.Field<int>("Incasso");

                        int somma_incassi = incassi.Sum();

                        //legge tutte le spese e ne fa la somma
                        var spese = from p in pagamento_impianto
                                      where p.Field<int>("Anno") == f.monthCalendar1.SelectionStart.Year && p.Field<int>("Mese") == f.monthCalendar1.SelectionStart.Month
                                      select p.Field<int>("Spesa");

                        int somma_spese = spese.Sum();

                        //fa la differenza fra incassi e spese:
                        int bilancio = somma_incassi - somma_spese;

                        //output
                        int[] output = new int[] { somma_incassi, somma_spese, bilancio };
                        List<int[]> righe = new List<int[]>();
                        righe.Add(output);

                        gridView1.ColumnHeaderToolTip = "Bilancio";

                        GridViewColumn column1 = new GridViewColumn();
                        column1.DisplayMemberBinding = new Binding("[0]");
                        column1.Header = "Incassi";
                        column1.Width = 100;

                        GridViewColumn column2 = new GridViewColumn();
                        column2.DisplayMemberBinding = new Binding("[1]");
                        column2.Header = "Spese";
                        column2.Width = 100;

                        GridViewColumn column3 = new GridViewColumn();
                        column3.DisplayMemberBinding = new Binding("[2]");
                        column3.Header = "Bilancio";
                        column3.Width = 100;

                        gridView1.Columns.Clear();

                        gridView1.Columns.Add(column1);
                        gridView1.Columns.Add(column2);
                        gridView1.Columns.Add(column3);

                        listView1.ItemsSource = righe;

                        break;
                    }
                case "i1": //inserisci viaggio di scarico
                    {
                        Form2 f = new Form2(database);
                        f.ShowDialog();
                        //inserisce il viaggio, l'eventuale rimorchio e le informazioni_in_scarico
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();                       
                        OleDbCommand comando = new OleDbCommand();
                        comando.Connection = connessione;
                        
                        bool inserito = Inserisci_Viaggio(false, f.Scarico, comando);
                        if (inserito)
                        {
                            Aggiorna_Viaggio(false, f.Scarico);
                            if (f.Scarico.Rimorchio.Rimorchio != "")
                            {
                                Inserisci_Rimorchio(false, f.Scarico.Rimorchio, f.Scarico.Codice, comando);                                
                            }
                            Inserisci_Informazioni(false, f.Infos, comando);
                        }

                        connessione.Close();
                        break;
                    }
                case "i2": //inserisci viaggio di carico 
                    {
                        Form_Carico f = new Form_Carico(database);
                        f.ShowDialog();
                        //inserisce il viaggio, l'eventuale rimorchio e le informazioni_in_scarico
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();
                        OleDbCommand comando = new OleDbCommand();
                        comando.Connection = connessione;

                        bool inserito = Inserisci_Viaggio(true, f.Carico, comando);
                        if (inserito)
                        {
                            Aggiorna_Viaggio(true, f.Carico);
                            if (f.Carico.Rimorchio.Rimorchio != "")
                            {
                                Inserisci_Rimorchio(true, f.Carico.Rimorchio, f.Carico.Codice, comando);
                            }
                            Inserisci_Informazioni(true, f.Infos, comando);
                        }

                        connessione.Close();
                        break;
                    }
                case "i3": //inserisci viaggio ottimizzato
                    {
                        //prima chiede viaggio di scarico e poi viaggio di carico
                        Form2 f = new Form2(database);
                        f.ShowDialog();
                        //il camionista, il camion, la data, il rimorchio sono uguali al viaggio di scarico
                        //i container possono cambiare
                        Form_Carico f2 = new Form_Carico(database, f.Scarico);
                        f2.ShowDialog();

                        //inserisce il viaggio, l'eventuale rimorchio e le informazioni_in_scarico
                        OleDbConnection connessione = new OleDbConnection(connection_string);
                        connessione.Open();
                        OleDbCommand comando = new OleDbCommand();
                        comando.Connection = connessione;

                        //inserisce viaggio di scarico
                        bool inserito = Inserisci_Viaggio(false, f.Scarico, comando);
                        if (inserito)
                        {
                            Aggiorna_Viaggio(false, f.Scarico);
                            if (f.Scarico.Rimorchio.Rimorchio != "")
                            {
                                Inserisci_Rimorchio(false, f.Scarico.Rimorchio, f.Scarico.Codice, comando);
                            }
                            Inserisci_Informazioni(false, f.Infos, comando);
                        }

                        //inserisce viaggio di carico
                        inserito = Inserisci_Viaggio(true, f2.Carico, comando);
                        if (inserito)
                        {
                            Aggiorna_Viaggio(true, f2.Carico);
                            if (f2.Carico.Rimorchio.Rimorchio != "")
                            {
                                Inserisci_Rimorchio(true, f2.Carico.Rimorchio, f2.Carico.Codice, comando);
                            }
                            Inserisci_Informazioni(true, f2.Infos, comando);
                        }

                        //inserisce viaggio ottimizzato
                        Inserisci_Ottimizzato(f2.Carico, comando);

                        connessione.Close();
                        break;
                    }
                default:
                    {
                        break;
                    }
            }
        }

        private int RandomNumber(int min, int max)
        {
            Random r = new Random(Guid.NewGuid().GetHashCode());
            return r.Next(min, max + 1);
        }

        private int[] RandomNumber(int min, int max, int length)
        {
            Random r = new Random(Guid.NewGuid().GetHashCode());
            int[] vet = new int[length];
            for (int i = 0; i < length; i++)
            {
                vet[i] = r.Next(min, max + 1);
            }
            return vet;
        }

        //al max è una settimana
        private string RandomDate(DateTime start, DateTime end)
        {
            int anno = 0;
            int mese = 0;
            int giorno = 0;

            //genera anno
            if (start.Year == end.Year)
            {
                anno = start.Year;

                //genera mese e giorno
                if (start.Month == end.Month)
                {
                    mese = start.Month;
                    //se il mese è uguale, allora il giorno dello start è < del giorno dell'end sicuremente
                    giorno = RandomNumber(start.Day, end.Day);
                }
                else //è una sola settimana, al max cambia di 1
                {
                    //lancio della moneta
                    mese = (RandomNumber(1, 2)) == 1 ? start.Month : end.Month;
                    //il giorno dipende dal mese estratto, es:
                    //se estratto mese precedente => giorni € [(start.day)...(max day of month)]
                    //se estratto mese successivo => giorni € [1...(end.day)]

                    if (mese == start.Month) //in questo caso sicuramente il giorno dello start è > del giorno dell'end
                    {
                        giorno = RandomNumber(start.Day, DateTime.DaysInMonth(start.Year, start.Month));
                    }
                    else
                    {
                        giorno = RandomNumber(1, end.Day);
                    }
                }
            }
            else //è una sola settimana, al max cambia di 1
            {
                int moneta = RandomNumber(1, 2);
                anno = (moneta == 1) ? start.Year : end.Year;
                //il mese è uguale a quello dell'anno generato
                mese = (moneta == 1) ? start.Month : end.Month;
                //il giorno € [(start.day) ... maxDay(start.month)]
                //oppure    € [1 ... end.day]
                giorno = (moneta == 1) ? RandomNumber(start.Day, DateTime.DaysInMonth(start.Year, start.Month)) : RandomNumber(1, end.Day);
            }            

            return giorno.ToString() + "/" + mese.ToString() + "/" + anno.ToString();
        }

        private string LocalDate()
        {
            return DateTime.Now.Day.ToString() + "/" + DateTime.Now.Month.ToString() + "/" + DateTime.Now.Year.ToString();
        }

        private string ConvertDate(DateTime data)
        {
            return data.Day.ToString() + "/" + data.Month.ToString() + "/" + data.Year.ToString();
        }

        private void Aggiorna_Ottimizzato(Viaggio ottimizzato)
        { 
            //aggiorno database locale
            DataRow dn2 = database.Tables["VIAGGIO_OTTIMIZZATO"].NewRow();
            dn2.SetField<string>("Camionista", ottimizzato.Camionista);
            dn2.SetField<string>("Data", ottimizzato.Data);
            dn2.SetField<int>("NumeroViaggio", ottimizzato.NumeroViaggio);
            database.Tables["VIAGGIO_OTTIMIZZATO"].Rows.Add(dn2);
        }

        private bool Inserisci_Ottimizzato(Viaggio ottimizzato, OleDbCommand comando)
        {
            string insert = "insert into VIAGGIO_OTTIMIZZATO (Camionista, Data, NumeroViaggio) values ('" + ottimizzato.Camionista + "', '" + ottimizzato.Data + "', '" + ottimizzato.NumeroViaggio + "')";
            comando.CommandText = insert;
            try
            {
                comando.ExecuteNonQuery();
            }
            catch //(OleDbException ex)
            {
                return false;
                //se va qui il viaggio ottimizzato già la coppia
                //univoca (camionista, data)
            }
            return true;
        }

        private bool Inserisci_Viaggio(bool carico, Viaggio v, OleDbCommand comando)
        {
            string insert = "";
            if (carico)
            {
                insert = "insert into VIAGGIO_DI_CARICO (Camion, Camionista, Codice, Container1, Data, BustaDocumenti, NumeroViaggio) values ('" + v.Camion + "', '" + v.Camionista + "', '" + v.Codice.ToString() + "', '" + v.Container1 + "', '" + v.Data + "', '" + v.BustaDocumenti + "', '"+ v.NumeroViaggio +"')";
            }
            else
            {
                insert = "insert into VIAGGIO_DI_SCARICO (Camion, Camionista, Codice, Container1, Data, BustaDocumenti) values ('" + v.Camion + "', '" + v.Camionista + "', '" + v.Codice.ToString() + "', '"+ v.Container1 +"', '" + v.Data + "', '" + v.BustaDocumenti + "')";
            }
            
            comando.CommandText = insert;
            try
            {
                comando.ExecuteNonQuery();
            }
            catch
            {
                //se va qui ha generato già una coppia camionista,data o camionista,data,numeroViaggio
                return false;
            }
            return true;
        }

        private bool Inserisci_Pagamento(bool carico, Pagamento p, OleDbCommand comando)
        {
            string table = "";
            string attribute1 = "";
            string attribute2 = "";

            if (carico)
            {
                table = "PAGAMENTO_CLIENTE";
                attribute1 = "Cliente";
                attribute2 = "Incasso";
            }
            else
            {
                table = "PAGAMENTO_IMPIANTO";
                attribute1 = "Impianto";
                attribute2 = "Spesa";
            }

            string insert = "insert into " + table + " (" + attribute1 + ", Anno, Mese, Pagato, " + attribute2 + ") values ('" + p.PartitaIva + "', '" + p.Anno + "', '" + p.Mese + "', '" + p.Pagato + "', '" + p.Prezzo + "')";
            comando.CommandText = insert;
            try
            {
                 comando.ExecuteNonQuery();
            }
            catch //(OleDbException ex)
            {
                return false;
            }
            return true;
        }

        private void Aggiorna_Pagamento(bool carico, Pagamento p)
        {
            string table = "";
            string attribute1 = "";
            string attribute2 = "";

            if (carico)
            {
                table = "PAGAMENTO_CLIENTE";
                attribute1 = "Cliente";
                attribute2 = "Incasso";
            }
            else
            {
                table = "PAGAMENTO_IMPIANTO";
                attribute1 = "Impianto";
                attribute2 = "Spesa";
            }
            //aggiorna database locale
            DataRow dn = database.Tables[table].NewRow();
            dn.SetField<int>("Anno", p.Anno);
            dn.SetField<int>("Mese", p.Mese);
            dn.SetField<string>("Pagato", p.Pagato);
            dn.SetField<string>(attribute1, p.PartitaIva);
            dn.SetField<int>(attribute2, p.Prezzo);
            database.Tables[table].Rows.Add(dn);
        }

        private void Aggiorna_Viaggio(bool carico, Viaggio v)
        {
            string table = "";
            
            if (carico)
            {
                table = "VIAGGIO_DI_CARICO";
            }
            else
            {
                table = "VIAGGIO_DI_SCARICO";
            }
            //aggiorna database locale
            DataRow dn = database.Tables[table].NewRow();
            dn.SetField<int>("Codice", v.Codice);
            dn.SetField<string>("Camionista", v.Camionista);
            dn.SetField<string>("Camion", v.Camion);
            dn.SetField<string>("Data", v.Data);
            dn.SetField<string>("Container1", v.Container1);
            dn.SetField<string>("BustaDocumenti", v.BustaDocumenti);
            if (carico)
            {
                dn.SetField<int>("NumeroViaggio", v.NumeroViaggio);
            }
            database.Tables[table].Rows.Add(dn);
            database.AcceptChanges();
        }

        private void Inserisci_Rimorchio(bool carico, Rimorchio_In_Viaggio rim, int CodViaggio, OleDbCommand comando)
        {
            string insert = "";
            string table = "";
            if (carico)
            {
                insert = "insert into RIMORCHIO_IN_CARICO (Rimorchio, Container2, Viaggio) values ('" + rim.Rimorchio + "', '" + rim.Container2 + "', '" + CodViaggio.ToString() + "')";
                table = "RIMORCHIO_IN_CARICO";
            }
            else
            {
                insert = "insert into RIMORCHIO_IN_SCARICO (Rimorchio, Container2, Viaggio) values ('" + rim.Rimorchio + "', '" + rim.Container2 + "', '" + CodViaggio.ToString() + "')";
                table = "RIMORCHIO_IN_SCARICO";
            }
            //non dovrebbe mai dare eccezioni
            comando.CommandText = insert;
            comando.ExecuteNonQuery();

            //aggiorno database locale
            DataRow dn2 = database.Tables[table].NewRow();
            dn2.SetField<string>("Rimorchio", rim.Rimorchio);
            dn2.SetField<string>("Container2", rim.Container2);
            dn2.SetField<int>("Viaggio", CodViaggio);
            database.Tables[table].Rows.Add(dn2);

        }

        private void Inserisci_Informazioni(bool carico, List<Informazioni_In_Viaggio> infos, OleDbCommand comando)
        {
            for (int k = 0; k < infos.Count; k++)
            {
                string insert = "";
                string table = "";
                string attribute = "";
                if (carico)
                {
                    insert = "insert into INFORMAZIONI_CARICO (Viaggio, Cliente, Soa, PesoSoa) values ('" + infos[k].Viaggio + "', '" + infos[k].Iva + "', '" + infos[k].Soa + "', '" + infos[k].PesoSoa + "')";
                    table = "INFORMAZIONI_CARICO";
                    attribute = "Cliente";
                }
                else
                {
                    insert = "insert into INFORMAZIONI_SCARICO (Viaggio, Impianto, Soa, PesoSoa) values ('" + infos[k].Viaggio + "', '" + infos[k].Iva + "', '" + infos[k].Soa + "', '" + infos[k].PesoSoa + "')";
                    table = "INFORMAZIONI_SCARICO";
                    attribute = "Impianto";
                }
                comando.CommandText = insert;
                bool inserito = true;
                try
                {
                    comando.ExecuteNonQuery();
                }
                catch
                {
                    inserito = false;
                    //se finisce qui è perchè ha generato 2 volte lo stesso Soa
                }
                if (inserito)
                {
                    //aggiorna database locale
                    DataRow dn = database.Tables[table].NewRow();
                    dn.SetField<string>(attribute, infos[k].Iva);
                    dn.SetField<int>("PesoSoa", infos[k].PesoSoa);
                    dn.SetField<string>("Soa", infos[k].Soa);
                    dn.SetField<int>("Viaggio", infos[k].Viaggio);
                    database.Tables[table].Rows.Add(dn);
                }

            }
        }

        private Container GeneraContainer()
        {
            Container c = new Container();
            //genero codice ISO col seguente pattern: "AAA1xxxxxxy"
            c.codiceISO = "AAA1";
            int[] numbers = RandomNumber(0, 9, 7);
            foreach (int n in numbers)
            {
                c.codiceISO += n.ToString();
            }
            //genero data acquisto
            DateTime start = new DateTime(1980, 1, 1);
            DateTime end = new DateTime(2013, 12, 31);
            c.dataAcquisto = RandomDate(start,end);
            c.Volume = RandomNumber(400,500);
            return c;

        }

        private Lavaggio GeneraLavaggio(DateTime start, DateTime end)
        {
            //genera un lavaggio
            //formato da container - addetto - data
            
            //estrae un addetto al lavaggio
            string addetto = Estrai_Addetto_Al_Lavaggio();
            //estrae un container
            string container = Estrai_Container();
            //ottiene data locale
            string Data = RandomDate(start, end);

            //crea lavaggio
            Lavaggio l = new Lavaggio();
            l.Addetto = addetto;
            l.Container = container;
            l.Data = Data;
            return l;
        }

        private string Estrai_Addetto_Al_Lavaggio()
        {
            //legge col LinQ dal Dataset tutti gli addetti al lavaggio e ne estrae uno a caso
            var table = database.Tables["DIPENDENTE"].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var addetti = from d in table
                          where d.Field<string>("Ruolo") == "Lavaggio"
                          select d.Field<string>("CodiceFiscale");
            
            //genero numero random dell'indice della lista degli addetti
            int index = RandomNumber(0, addetti.Count() - 1);
            return addetti.ElementAt<string>(index);
            
        }

        private string Estrai_Container()
        {
            //legge col LinQ dal Dataset tutti i container e ne estrae uno a caso
            var table = database.Tables["[CONTAINER]"].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var containers = from d in table
                          select d.Field<string>("CodiceISO");

            //genero numero random dell'indice della lista dei container
            int index = RandomNumber(0, containers.Count() - 1);
            return containers.ElementAt<string>(index);
        }

        //cliente = true =>  cliente
        //cliente = false => impianto
        //data: scelta dall'utente, si prende mese e anno
        private Pagamento Genera_Pagamento(bool cliente, DateTime data)
        {
            string iva = "";
            if (cliente)
            {
                iva = Estrai_Cliente();
            }
            else
            {
                iva = Estrai_Impianto();
            }
            //genero stato del pagamento (va bene sia per i clienti che per gli impianti)
            string[] stati = {
                                 "Pagato",
                                 "Da Pagare"
                             };
            //è una distribuzione non uniforme, lo stato da pagare è molto più raro
            string pagato = (RandomNumber(0,11) <= 10) ? stati[0] : stati[1];
            //genero incasso o spesa, da 100 a 3000 €
            int prezzo = RandomNumber(100,3000);
            Pagamento p = new Pagamento();
            p.Anno = data.Year;
            p.Mese = data.Month;
            p.Pagato = pagato;
            p.PartitaIva = iva;
            p.Prezzo = prezzo;
            return p;
        }

        private Viaggio[] Genera_Ottimizzato(DateTime start, DateTime end)
        {
            //nel caso del viaggio ottimizzato, si crea un viaggio di scarico e poi da uno a due viaggi di carico
            //con gli stessi camion, rimorchio, busta documenti, camionista e data
            Viaggio[] scarico = Genera_Viaggi(false, start, end);
            Viaggio[] carico = Genera_Viaggi(true, start, end);

            int num_carichi = 0;
            if(carico.Length > 1)
            {
                num_carichi = RandomNumber(1,2); //numero di viaggi di carico che un camionista piò fare lo stesso giorno del viaggio ottimizzato (uno fa parte del viaggio ottimizzato)
            }
            else
            {
                num_carichi = 1;
            }

            Viaggio[] ottimizzato = new Viaggio[2 + num_carichi]; //1 scarico + num_carichi + 1 ottimizzato
            ottimizzato[0] = scarico[0];
            for (int i = 0; i < num_carichi; i++)
            {
                //il camionista e la data devono essere gli stessi sia per il viaggio di carico che per il viaggio di scarico,
                //quindi si prendono camionista e data dal viaggio di scarico generato prima
                carico[i].Camionista = scarico[0].Camionista;
                carico[i].Data = scarico[0].Data;
                ottimizzato[i + 1] = carico[i];
            }
            //crea viaggio ottimizzato
            Viaggio ott = new Viaggio();
            ott.Camionista = scarico[0].Camionista;
            ott.Data = scarico[0].Data;
            ott.NumeroViaggio = RandomNumber(1,num_carichi);
            ottimizzato[ottimizzato.Length - 1] = ott;

            return ottimizzato;
        }

        
        //tipo:
        //0 = viaggio di scarico => VIAGGI.LENGTH = 1
        //1 = viaggio di carico  => 1 <= VIAGGI.LENGTH <= 4
        //=> 3 <= VIAGGI.LENGTH <= 4 perchè lo stesso giorno che un camionista fa un viaggio ottimizzato al massimo può fare 2 viaggi di carico 
        //
        //start = data minima
        //end = data massima
        private Viaggio[] Genera_Viaggi(bool carico, DateTime start, DateTime end)
        {
            int codice = 0;
            string camionista = "";
            string data = "";
            string camion = "";
            string container1 = "";
            Rimorchio_In_Viaggio rimorchio = new Rimorchio_In_Viaggio();
            //inizializza struct:
            rimorchio.Rimorchio = "";
            rimorchio.Container2 = "";
            string busta_doc = "";

            //in comune per ogni viaggio di carico e scarico ci sono il camionista e la data, quindi prima si generano questi valori:
            camionista = Estrai_Camionista();
            data = RandomDate(start,end);

            if (!carico) //scarico
            {
                if (database.Tables["VIAGGIO_DI_SCARICO"].Rows.Count == 0)
                {
                    //la tabella è vuota
                    codice = 1;
                }
                else
                {
                    //leggo il massimo codice inserito nel database
                    var codici = from v2 in database.Tables["VIAGGIO_DI_SCARICO"].AsEnumerable()
                                 orderby v2.Field<int>("Codice") descending
                                 select v2.Field<int>("Codice");

                    int max = codici.ElementAt(0);

                    codice = max + 1;
                }
                camion = Estrai_Camion();
                container1 = Estrai_Container();
                //il rimorchio è opzionale
                if (RandomNumber(0, 9) < 9) //9 viaggi di scarico su 10 hanno il rimorchio
                {
                    rimorchio = new Rimorchio_In_Viaggio();
                    rimorchio.Rimorchio = Estrai_Rimorchio();
                    do //estrae un container finchè è diverso dal container della tabella VIAGGIO_DI_SCARICO appena generato
                    {
                        rimorchio.Container2 = Estrai_Container();
                    }
                    while(rimorchio.Container2 == container1);
                }
                busta_doc = Genera_BustaDocumenti();

                //ritorno il viaggio di scarico creato
                Viaggio[] v = new Viaggio[1];
                v[0].BustaDocumenti = busta_doc;
                v[0].Camion = camion;
                v[0].Camionista = camionista;
                v[0].Codice = codice;
                v[0].Container1 = container1;
                v[0].Data = data;
                v[0].Rimorchio = rimorchio;
                return v;
            }
            else
            {
                //nel caso di più viaggi di carico effettuati nello stesso giorno, il camionista potrebbe avere
                //cambiato camion o rimorchio
                int num_viaggi = RandomNumber(1, 4); //si suppone al massimo 4 viaggi di carico in un giorno per un camionista
                Viaggio[] v = new Viaggio[num_viaggi];
                for (int i = 0; i < num_viaggi; i++)
                {
                    if (database.Tables["VIAGGIO_DI_CARICO"].Rows.Count == 0)
                    {
                        //la tabella è vuota
                        codice = 1;
                    }
                    else
                    {
                        //leggo il massimo codice inserito nel database
                        var codici = from v2 in database.Tables["VIAGGIO_DI_CARICO"].AsEnumerable()
                                     orderby v2.Field<int>("Codice") descending
                                     select v2.Field<int>("Codice");

                        int max = codici.ElementAt(0);

                        codice = max + 1;
                    } 
                    camion = Estrai_Camion();
                    container1 = Estrai_Container();
                    //il rimorchio è opzionale
                    if (RandomNumber(0, 9) < 7) //7 viaggi di carico su 10 hanno il rimorchio
                    {
                        rimorchio.Rimorchio = Estrai_Rimorchio();
                        do //estrae un container finchè è diverso dal container della tabella VIAGGIO_DI_SCARICO appena generato
                        {
                            rimorchio.Container2 = Estrai_Container();
                        }
                        while (rimorchio.Container2 == container1);
                    }
                    busta_doc = Genera_BustaDocumenti();

                    v[i].BustaDocumenti = busta_doc;
                    v[i].Camion = camion;
                    v[i].Camionista = camionista;
                    v[i].Codice = codice;
                    v[i].Container1 = container1;
                    v[i].Data = data;
                    v[i].NumeroViaggio = i + 1;
                    v[i].Rimorchio = rimorchio;
                }
                return v;
            }

            

        }

        private List<Informazioni_In_Viaggio> Genera_Informazioni_Scarico(Viaggio scarico)
        {
            int num_impianti = 1;
            DataTable table;

            //se c'è il rimorchio si può andare da 2 impianti (può essere anche lo stesso con 2 container)
            if (scarico.Rimorchio.Rimorchio != "")
            {
                num_impianti = 2;
            }

            table = database.Tables["VIAGGIO_DI_SCARICO"];

            List<Informazioni_In_Viaggio> info = new List<Informazioni_In_Viaggio>();

            for (int i = 0; i < num_impianti; i++)
            {
                Informazioni_In_Viaggio tmp_info = new Informazioni_In_Viaggio();
                tmp_info.Viaggio = scarico.Codice;
                tmp_info.Iva = Estrai_Impianto();
                tmp_info.Soa = Estrai_Soa(false, tmp_info.Iva); //se viene estratto lo stesso Soa vengono generate 2 info uguali, ma di questo se ne preoccupa chi fa la chiamata
                tmp_info.PesoSoa = RandomNumber(300, 600);   
                //aggiunge alla lista
                info.Add(tmp_info);
            }

            return info;
        }

        private List<Informazioni_In_Viaggio> Genera_Informazioni_Carico(Viaggio carico)
        {
            int num_clienti = 1;
            DataTable table;

            if (carico.Rimorchio.Rimorchio != "")
            {
                //se nel viaggio di carico c'è il rimorchio si sono serviti 2 clienti (può essere anche lo stesso)
                num_clienti = 2;
            }

            table = database.Tables["VIAGGIO_DI_CARICO"];

            List<Informazioni_In_Viaggio> info = new List<Informazioni_In_Viaggio>();
            //per ogni cliente
            for (int i = 0; i < num_clienti; i++)
            {
                Informazioni_In_Viaggio tmp_info = new Informazioni_In_Viaggio();
                tmp_info.Viaggio = carico.Codice;
                tmp_info.Iva = Estrai_Cliente();
                tmp_info.Soa = Estrai_Soa(true, tmp_info.Iva); //se carico = true => è un cliente
                tmp_info.PesoSoa = RandomNumber(300, 600);
                //aggiunge alla lista queste info
                info.Add(tmp_info);

                //per ogni cliente ci può essere il separatore, che consente di trasportare un altro soa della stessa categoria
                int separatore = RandomNumber(0,1); //testa / croce
                if (separatore == 1) //se c'è allora estrae un altro Soa della stessa categoria (se uguali genera righe duplicate, ma di questo se ne preoccupa il chiamante)
                {
                    tmp_info.Soa = Estrai_Soa(tmp_info.Iva, tmp_info.Soa);
                    tmp_info.PesoSoa = RandomNumber(300, 600);
                    //aggiunge alla lista queste info
                    info.Add(tmp_info);
                }
            }

            return info;
        }

        private string Genera_BustaDocumenti()
        {
            //ritorna una scritta del tipo:
            //armadietto numero: xx
            return "Armadietto n. " + RandomNumber(1, 200).ToString();
        }

        private string Estrai_Soa(bool cliente, string iva)
        {
            string tabella = "";
            string selezione = "";
            if (cliente)
            {
                tabella = "SOA_IN_CLIENTE";
                selezione = "Cliente";
            }
            else
            {
                tabella = "SOA_IN_IMPIANTO";
                selezione = "Impianto";
            }
            //legge col LinQ dal Dataset tutti i clienti e ne estrae uno a caso
            var table = database.Tables[tabella].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var soa = from d in table
                      where d.Field<string>(selezione) == iva
                          select d.Field<string>("Soa");

            //genero numero random dell'indice della lista degli addetti
            int index = RandomNumber(0, soa.Count() - 1);
            return soa.ElementAt<string>(index);
        }

        //il soa_già_estratto serve per sapere la categoria del soa da estrarre
        //metodo valido solo per i clienti, non per gli impianti
        private string Estrai_Soa(string iva, string soa_già_estratto)
        {
            string tabella = "";

            tabella = "TIPO_DI_SOA";

            var tipo_di_soa = database.Tables[tabella].AsEnumerable();

            //legge la categoria del soa_già_estratto
            var tmp_cat = from t in tipo_di_soa
                      where t.Field<string>("Nome") == soa_già_estratto
                      select new { Categoria = t.Field<int>("Categoria") };

            int categoria = tmp_cat.ElementAt(0).Categoria;

            tabella = "SOA_IN_CLIENTE";

            //legge col LinQ dal Dataset tutti i clienti e ne estrae uno a caso
            var soa_in_cliente = database.Tables[tabella].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var soa = from d in soa_in_cliente
                      join t in tipo_di_soa on d.Field<string>("Soa") equals t.Field<string>("Nome")
                      where d.Field<string>("Cliente") == iva && t.Field<int>("Categoria") == categoria
                      select d.Field<string>("Soa");

            int aaa = soa.Count();

            //genero numero random dell'indice della lista degli addetti
            int index = RandomNumber(0, soa.Count() - 1);
            return soa.ElementAt<string>(index);
            //potrebbe ritornare lo stesso soa, in questo caso se ne preoccupa il chiamante, in pratica è come se il separatore non ci fosse
        }

        private int Estrai_Viaggio(bool carico)
        {
            string tabella = "";
            if (carico)
            {
                tabella = "VIAGGIO_DI_CARICO";
            }
            else
            {
                tabella = "VIAGGIO_DI_SCARICO";
            }
            //legge col LinQ dal Dataset tutti i clienti e ne estrae uno a caso
            var table = database.Tables[tabella].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var viaggi = from d in table
                         select d.Field<int>("Codice");

            //genero numero random dell'indice della lista degli addetti
            int index = RandomNumber(0, viaggi.Count() - 1);
            return viaggi.ElementAt<int>(index);
            
        }

        private string Estrai_Cliente()
        {
            //legge col LinQ dal Dataset tutti i clienti e ne estrae uno a caso
            var table = database.Tables["CLIENTE"].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var clienti = from d in table
                          select d.Field<string>("partitaIVA");

            //genero numero random dell'indice della lista degli addetti
            int index = RandomNumber(0, clienti.Count() - 1);
            return clienti.ElementAt<string>(index);
        }

        private string Estrai_Impianto()
        {
            //legge col LinQ dal Dataset tutti gli impianti e ne estrae uno a caso
            var table = database.Tables["IMPIANTO"].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var impianti = from d in table
                          select d.Field<string>("partitaIVA");

            //genero numero random dell'indice della lista degli addetti
            int index = RandomNumber(0, impianti.Count() - 1);
            return impianti.ElementAt<string>(index);
        }

        private string Estrai_Camionista()
        {
            //legge col LinQ dal Dataset tutti gli impianti e ne estrae uno a caso
            var table = database.Tables["DIPENDENTE"].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var camionisti = from d in table
                           where d.Field<string>("Ruolo") == "Camionista"
                           select d.Field<string>("CodiceFiscale");

            //genero numero random dell'indice della lista degli addetti
            int index = RandomNumber(0, camionisti.Count() - 1);
            return camionisti.ElementAt<string>(index);
        }

        private string Estrai_Camion()
        {
            //legge col LinQ dal Dataset tutti gli impianti e ne estrae uno a caso
            var table = database.Tables["CAMION"].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var camion = from d in table
                           select d.Field<string>("Targa");

            //genero numero random dell'indice della lista degli addetti
            int index = RandomNumber(0, camion.Count() - 1);
            return camion.ElementAt<string>(index);
        }

        private string Estrai_Rimorchio()
        {
            //legge col LinQ dal Dataset tutti gli impianti e ne estrae uno a caso
            var table = database.Tables["[RIMORCHIO]"].AsEnumerable();
            //table è una enumerableRowCollection<DataRow>
            var rimorchi = from d in table
                           select d.Field<string>("Targa");

            //genero numero random dell'indice della lista degli addetti
            int index = RandomNumber(0, rimorchi.Count() - 1);
            return rimorchi.ElementAt<string>(index);
        }

        private void Settaggio1(OP1Form f, int MaxCount)
        {
            f.dateTimePicker1.Visible = false;
            f.dateTimePicker2.Visible = false;
            f.monthCalendar1.Visible = true;
            f.monthCalendar1.MaxSelectionCount = MaxCount;
            f.LabelCamionista.Visible = false;
            f.comboBox1.Visible = false;
        }

        private void Settaggio2(OP1Form f)
        {
            f.dateTimePicker1.Visible = true;
            f.dateTimePicker2.Visible = true;
            f.LabelCamionista.Visible = true;
            f.monthCalendar1.Visible = false;
            f.comboBox1.Visible = true;
        }

        private void LeggiDatabase()
        {
            //resetta dataset
            database.Tables.Clear();
            //legge tutte le tabelle dal database e le salva nel dataset
            OleDbConnection sql_connection = new OleDbConnection();
            sql_connection.ConnectionString = connection_string;
            try
            {
                sql_connection.Open();
            }
            catch (OleDbException ex)
            {
                MessageBox.Show(ex.Message,"Connessione Database", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }
            OleDbCommand cm = new OleDbCommand();
            cm.Connection = sql_connection;
            for (int i = 0; i < tables.Length; i++)
            {
                //crea tabella
                DataTable table = new DataTable(tables[i]);
                //legge tabella dal database access
                string leggi = "select * from " + tables[i];
                cm.CommandText = leggi;
                OleDbDataAdapter da = new OleDbDataAdapter(cm);
                da.Fill(table);
                //aggiunge tabella al dataset
                database.Tables.Add(table);
            }
            sql_connection.Close();
            return;
        }

    }

    public struct Container
    {
        public string codiceISO;
        public string dataAcquisto;
        public int Volume;
    }

    public struct Lavaggio
    {
        public string Container;
        public string Addetto;
        public string Data;
    }

    //questa serve sia a Pagamento_Cliente che a Pagamento_Impianto
    public struct Pagamento
    {
        public string PartitaIva; //cliente o impianto
        public int Anno;
        public int Mese;
        public string Pagato;
        public int Prezzo; //incasso o spesa
    }

    //serve sia per il VIAGGIO_DI_CARICO che per il VIAGGIO_DI_SCARICO
    public struct Viaggio
    {
        public int Codice;
        public string Data;
        public string BustaDocumenti;
        public string Camionista;
        public string Camion;
        public Rimorchio_In_Viaggio Rimorchio;
        public int NumeroViaggio;
        public string Container1;
    }

    public struct Rimorchio_In_Viaggio //vale sia per RIMORCHIO_IN_CARICO che per RIMORCHIO_IN_SCARICO
    {
        //public int Viaggio; //sia VIAGGIO_DI_CARICO che VIAGGIO_DI_SCARICO
        public string Container2; //deve essere diverso dal container del viaggio
        public string Rimorchio;
    }

    public struct Informazioni_In_Viaggio //vale sia per INFORMAZIONI_CARICO che per INFORMAZIONI_SCARICO
    {
        public int Viaggio;
        public string Soa;
        public string Iva; //partita iva cliente o impianto
        public int PesoSoa;
    }
}
