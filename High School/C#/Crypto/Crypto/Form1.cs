using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
using System.Security.Permissions;
using System.Runtime.InteropServices;

namespace Crypto
{
    public partial class Form1 : Form
    {
        //se l'estensione del file da crittare è acz o ACZ allora l'estensione del file crittato è risp.
        //bda o BDA
        //per passare dalla z alla a uso il codice ascii, converto la lettera nel numero decimale che la
        //rappresenta nel codice ascii
        const byte lim1 = 65; //A
        const byte lim2 = 90; //Z
        const byte lim3 = 97; //a
        const byte lim4 = 122; //z
        //il buffer serve per poter caricare file di 10 GB che se caricati completamente in memoria non ci stanno
        //xkè pesano troppo, bisogna darlo al flusso di dati quando viene inizializzato l'oggetto, poi
        //il pc fa da solo
        const int buffer = 1048576; //1 MB circa
        //vettore di caratteri che contiene l'estensione del file scelto dall'utente
        char[] temp;
        //vettore che salva il risultato dell'estensione del file scelto dall'utente modificata
        char[] estensione;
        //vettore che salva le lettere della chiave
        char[] cifre;
        //vettore cge salva i byte della chiave calcolati dal vettore cifre
        byte[] chiave;
        //prende le info sul file scelto dall'utente
        FileInfo a;
        //legge i byte dal file caricato dall'utente
        FileStream apri;
        //scrive i byte nel percorso selezionato dall'utente
        FileStream salva;
        DialogResult d;
        //vettore degli scalini
        long[] f;
        //serve all'evento di fine lavoro processo x sapere cosa scrivere nella label
        bool fine = false;
        //API Windows x cancellare il file nel caso in cui l'utente stoppa l'operazione
        [DllImport("Kernel32.dll")]
        private static extern int DeleteFile(string lpFileName);


        public Form1()
        {
            InitializeComponent();
            //cancella dal form il bottone stop
            Controls.Remove(ButtonStop);
        }

        private void ButtonCarica_Click(object sender, EventArgs e)
        {
                if (openFileDialog1.ShowDialog() == DialogResult.OK)
                {
                    //oggetto che serve per scrivere o leggere i byte
                    apri = new FileStream(openFileDialog1.FileName,FileMode.Open,FileAccess.Read,FileShare.Read,buffer,FileOptions.SequentialScan);
                    //contiene tutte le informazioni sul file scelto dall'utente
                    a = new FileInfo(openFileDialog1.FileName);
                    //prendo i caratteri che contraddistinguono l'estensione
                    //mi servono dopo per crittare l'estensione del file
                    temp = a.Extension.ToCharArray();

                    TextBoxFileCaricato.Text = openFileDialog1.FileName.ToString();

                    TextBoxChiave.Enabled = true;
                    ButtonChiave.Enabled = true;
                    TextBoxChiave.Focus();
                }
        }

        private void ButtonChiave_Click(object sender, EventArgs e)
        {
            //controlla se la chiave soddisfa i requisiti
            if (ControlloChiave(TextBoxChiave.Text))
            {
                //prendo le lettere dalla txtbox della chiave e le metto in un vettore di caratteri
                cifre = TextBoxChiave.Text.ToCharArray();
                //vettore che contiene i byte che formano la chiave (numeri da 0 a 255)
                chiave = new byte[cifre.Length];
                //ciclo che converte la lettera della txtbox chiave nel suo codice ascii
                for (ushort i = 0; i < cifre.Length; i++)
                    chiave[i] = (byte)(cifre[i].GetHashCode());
                ButtonSalva.Enabled = true;
            }
            else
                Errore();
        }

        private void ButtonSalva_Click(object sender, EventArgs e)
        {
            //sceglie dove salvare il file crittato
            if (saveFileDialog1.ShowDialog() == DialogResult.OK)
            {
                //se il file ha un'estensione, viene criptata / de anche quella
                if (a.Extension != "")
                {
                    //inizializza il vettore col numero di caratteri formato dall'estensione
                    estensione = new char[a.Extension.Length];
                    //critta l'estensione carattere per carattere
                    if (RadioButtonCritta.Checked)
                    {
                        for (ushort i = 1; i < (ushort)temp.Length; i++)
                        {
                            //se il numero del codice ascii è compreso fra la a compresa e la z esclusa 
                            //o la A compresa e la Z esclusa, allora il carattere diventa quello successivo
                            //es: a--> b  B----> C
                            if ((temp[i] >= lim1 && temp[i] < lim2) || (temp[i] >= lim3 && temp[i] < lim4))
                                estensione[i] = (char)(temp[i] + 1);
                            //se è la z o la Z diventano la a o la A
                            if (temp[i] == lim2)
                                estensione[i] = (char)lim1;
                            if (temp[i] == lim4)
                                estensione[i] = (char)lim3;
                        }
                    }
                    //decritta l'estensione carattere per carattere
                    if (RadioButtonDecritta.Checked)
                    {
                        for (ushort i = 1; i < (ushort)temp.Length; i++)
                        {
                            //se il numero del codice ascii è compreso fra la a esclusa e la z compresa 
                            //o la A esclusa e la Z compresa, allora il carattere diventa quello precedente
                            //es: z--> y  B----> A
                            if ((temp[i] > lim1 && temp[i] < lim2) || (temp[i] > lim3 && temp[i] < lim4))
                                estensione[i] = (char)(temp[i] - 1);
                            //se è la a o la A diventano la z o la Z
                            if (temp[i] == lim1)
                                estensione[i] = (char)lim2;
                            if (temp[i] == lim3)
                                estensione[i] = (char)lim4;
                        }
                    }
                    saveFileDialog1.FileName += ".";
                    //al percorso viene aggiunta l'estensione
                    for (byte i = 1; i < (byte)estensione.Length; i++)
                        saveFileDialog1.FileName += estensione[i];
                }
                //viene creato il flusso di dati nel percorso completo
                salva = new FileStream(saveFileDialog1.FileName, FileMode.Create,FileAccess.Write,FileShare.Write,buffer,FileOptions.WriteThrough);
                ButtonStart.Enabled = true;
                TextBoxSalvaFile.Text = saveFileDialog1.FileName;
            }
        }

        private void RadioButtonCritta_CheckedChanged(object sender, EventArgs e)
        {
            Cambio();
        }

        private void RadioButtonDecritta_CheckedChanged(object sender, EventArgs e)
        {
            Cambio();
        }
        private void Cambio()
        {
            //resetta l'interfaccia
            ButtonCarica.Enabled = true;
            TextBoxChiave.Enabled = false;
            ButtonChiave.Enabled = false;
            ButtonSalva.Enabled = false;
            ButtonStart.Enabled = false;
            TextBoxFileCaricato.Text = "";
            TextBoxChiave.Text = "";
            TextBoxSalvaFile.Text = "";
            LabelStato.Text = "In attesa di input";
            progressBar1.Value = 0;
            label3.Text = "Fai iniziare il lavoro al programma";
        }
        private void Errore()
        {
            MessageBox.Show("Inserisci una chiave di almeno 3 caratteri", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
            TextBoxChiave.Focus();
        }
        private bool ControlloChiave(string text)
        {
            //il controllo si basa solo sulla lunghezza della chiave (almeno 3 caratteri)
            if (text.ToCharArray().Length < 3)
                return false;
            return true;
        }
        private void Chiudi()
        {
            //il programma ha terminato il lavoro e chiude i flussi di dati
            apri.Close();
            salva.Close();
            //se l'utente ha selezionato sì prima dell'avvio del lavoro l'applicazione si chiude automaticamente
            if (d == DialogResult.Yes)
                this.Close();
            else
            {
                RadioButtonCritta.Enabled = true;
                RadioButtonDecritta.Enabled = true;
                ButtonCarica.Enabled = true;
                ButtonChiave.Enabled = true;
                ButtonSalva.Enabled = true;
                label3.Text = "Fai iniziare il lavoro al programma";
            }
        }

        private void ButtonStart_Click(object sender, EventArgs e)
        {
            label3.Text = "";
            RadioButtonCritta.Enabled = false;
            RadioButtonDecritta.Enabled = false;
            ButtonCarica.Enabled = false;
            ButtonChiave.Enabled = false;
            ButtonSalva.Enabled = false;

            //numero che idenrtifica il numero di volte che si aggiorna la progress bar
			const byte scalino = 10;
            //vettore che contiene i vari scalini di byte, divisori del numero totale dei byte del file
			f = new long[scalino];
            //è il primo scalino
			f[0] = a.Length/scalino;
            //ultima parte che contiene il numero totale dei byte
			f[scalino - 1] = a.Length - 1;
            //ciclo che setta i restanti scalini nel mezzo, come se fosse una percentuale
			for(byte i = 2; i < scalino; i++)
			{
				f[i-1] = a.Length/scalino * i;
			}
            d = MessageBox.Show("Vuoi chiudere automaticamente il programma terminato il lavoro?", "Chiusura automatica", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
            LabelStato.Text = "Sto lavorando...";
            Controls.Remove(ButtonStart);
            //parte il lavoro nel processo separato
            backgroundWorker1.RunWorkerAsync();
            Controls.Add(ButtonStop);
            //parte l'evento DoWork
            //il bottone viene disabilitato xkè se l'utente clicca sul bottone mentre il lavoro non è finito
            //viene generata un'eccezione
            //viene abilitato il bottone stop se l'utente vuole fermare il lavoro
        }

        //evento generato dalla pressione di un tasto quando il tasto è ancora "giù"
        private void TextBoxChiave_KeyDown(object sender, KeyEventArgs e)
        {
            //se dopo aver scritto la chiave l'utente non clicca il bottone ma spinge "invio"
            //l'evento richiama il codice del bottone
            EventArgs b = null;
            if (e.KeyCode == Keys.Enter)
            {
                if(ControlloChiave(TextBoxChiave.Text))
                {
                    ButtonChiave_Click(sender, b);
                    ButtonChiave.Focus();
                }
                else
                    Errore();
            }
        }

        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            //non si possono controllare i controlli del form in quest'evento
            byte file;
            //indice che serve al vettore f per indicare i vari scalini
            byte n = 0;
            //indice che tiene conto del numero dei caratteri della chiave
            ushort k = 0;
            //ciclo che scrive tutti i byte del file
            for (long i = 0; i < a.Length; i++)
            {
                //quando l'indice i è uguale al numero dei byte dello scalino, viene aggiornata la progress bar
                if (i == f[n])
                {
                    //viene eseguito l'evento Progress Changed
                    backgroundWorker1.ReportProgress(n);
                    n++;
                }
                if (k == chiave.Length)
                {
                    //se la chiave raggiunge l'ultimo carattere rincomincia dall'inizio
                    k = 0;
                }
                //legge il byte dal flusso apri e lo cripta
                file = (byte)(apri.ReadByte() ^ chiave[k]);
                //scrive il byte criptato
                salva.WriteByte(file);
                //passa al carattere successivo della chiave
                k++;
                //se l'utente ha spinto il bottone stop, l'evento cancella lavoro ha messo la prroprietà cancel pending a true
                if(backgroundWorker1.CancellationPending)
                {
                    //esce dall'evento e va nell'evento lavoo completato
                    return;
                }
            }
            fine = true;
        }

        private void backgroundWorker1_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            //scalino: aggiorna la progress bar
            progressBar1.Value += progressBar1.Step;
        }

        private void backgroundWorker1_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            //avvisa l'utente che l'operazione è finita, qui si possono modificare i controlli
            Chiudi();
            if (!fine)
            {
                LabelStato.Text = "Lavoro interrotto dall'utente";
                DeleteFile(saveFileDialog1.FileName);
            }
            else
            {
                LabelStato.Text = "Lavoro terminato";
            }
            Controls.Remove(ButtonStop);
            Controls.Add(ButtonStart);
        }

        //se l'utente chiude il form mentre il prog sta ancora lavorando, il form nn si kiude
        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if(backgroundWorker1.IsBusy)
            {
                e.Cancel = true;
            }
        }

        private void ButtonStop_Click(object sender, EventArgs e)
        {
            Controls.Remove(ButtonStop);
            Controls.Add(ButtonStart);
            //setta il cancellation pending del backgroundworker a true
            backgroundWorker1.CancelAsync();
        }

    }
 }

