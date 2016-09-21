using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Microsoft.Win32;
using System.IO;
using System.Media;
using System.Runtime.InteropServices;
using System.Collections;

//fare anche che l'utente sceglie il percorso dove installarlo, poi prendo la prima lettera che identifica l'hard disk e la scrivo in un file
//dopo SA.exe legge il file per sapere dove trovre i file e di conseguenza far funzionare il programma
//se l'utente seleziona un volume che non è un hard disk devo dare errore
//PER FARE DIVENTARE UNA RISORSA INCORPORATA DA COLLEGATA, ANDARE NELLE PROPRIETà DELL'IMMAGINE (IN PROPRIETà,APRI,RISORSE,AGGIUNGI RISORSA)
//E SELEZIONARE PERSISTANCE IN INCORPORATA
namespace SA_Installer
{
    public partial class Form1 : Form
    {
        const string percorso = "Programmi\\SA";
        string FileSA;
        const string Nome = "SA.exe";
        const int DimensioneToken = 1000;
        byte[] token = new byte[DimensioneToken];
        const string PercorsoToken = ":\\SA\\TokenUser";
        const string PercorsoToken2 = "SA\\TokenUser";
        bool messaggi = false;
        bool fine = false;
        const int msec = 2000; //2 secondi

        //variabili per messaggi windows
        const int WM_DeviceChange = 537; //cerca WM_DEVICECHANGE su msdn
        const int DeviceArrival = 32768; //0x8000
        const int NodesChanged = 7;
        const int DeviceRemoveComplete = 32772; //0x8004
        //numero che identifica il tipo di dispositivo (volumi o porte)
        const int VolumeType = 2; //chiavette usb, dvd,...
        const string floppy = "A:\\";
        bool A = false;
        DialogResult scelta;
        bool FloppyRimosso = false;

        int contTimer2 = 0;
        Image[] barre = new Image[4];

        //struttura che prende le informazioni da un device tipo volume logico
        [StructLayout(LayoutKind.Sequential)]
        public struct DEV_BROADCAST_VOLUME
        {
            public int dbcv_size;
            public int dbcv_devicetype;
            public int dbcv_reserved;
            public int dbcv_unitmask;
            public int dbcv_flags;
        }
        //struttura che prende informazioni sul tipo di device
        [StructLayout(LayoutKind.Sequential)]
        public struct DEV_BROADCAST_HDR
        {
            public int dbch_size;
            public int dbch_devicetype;
            public int dbch_reserved;

        }

        public Form1()
        {
            InitializeComponent();
            barre[0] = Properties.Resources.barra1;
            barre[1] = Properties.Resources.barra2;
            barre[2] = Properties.Resources.barra3;
            barre[3] = Properties.Resources.barra4;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            //prendo l'elenco dei volumi che si trovano su risorse del computer, nell primo hard disk che trovo
            //ci metto la cartella \\SA se non esiste, dopo l'utente se cambia destinazione devo cancellare la cartella
            DriveInfo[] drives = DriveInfo.GetDrives();
            foreach (DriveInfo d in drives)
            {
                if (d.DriveType == DriveType.Fixed)
                {
                    //controllo se esiste già la cartella \\Programmi\\SA
                    FileSA = d.Name + percorso;
                    if (!Directory.Exists(FileSA))
                    {
                        Directory.CreateDirectory(FileSA);
                    }
                    textBox1.Text = FileSA;
                    return;
                }
            }
        }

        protected override void WndProc(ref Message m)
        {
            if (messaggi)
            {
                switch (m.Msg)
                {
                    case WM_DeviceChange:
                        {
                            switch ((int)m.WParam)
                            {
                                case DeviceArrival:
                                    {
                                        //prende informazioni sul tipo di dispositivo
                                        DEV_BROADCAST_HDR dev = (DEV_BROADCAST_HDR)Marshal.PtrToStructure(m.LParam, typeof(DEV_BROADCAST_HDR));
                                        switch (dev.dbch_devicetype)
                                        {
                                            case VolumeType:
                                                {
                                                    //prende le informazioni dal parametro del message e la salva nella struttura
                                                    DEV_BROADCAST_VOLUME volume = (DEV_BROADCAST_VOLUME)Marshal.PtrToStructure(m.LParam, typeof(DEV_BROADCAST_VOLUME));
                                                    //prendo la lettera del volume
                                                    char lettera = GetDeviceLetter(volume.dbcv_unitmask);
                                                    //prendo informazioni sul drive (se è removable ok, altrimenti non fa niente)
                                                    DriveInfo drive = new DriveInfo(lettera.ToString() + ":\\");
                                                    if (drive.DriveType == DriveType.Removable)
                                                    {
                                                        Installa(lettera.ToString() + PercorsoToken);
                                                        timer2.Stop();
                                                        pictureBox1.Image = null;
                                                        label3.Text = "";
                                                    }
                                                    break;
                                                }
                                        }
                                        break;
                                    }
                            }
                            break;
                        }
                }
            }
            base.WndProc(ref m);
        }

        //restituisce la lettera dalla unitmask, che è una sequenza di bit con un solo 1, la posizione in cui si trova l' 1 indica la lettera (es: 1 = A, 10 = B, 100 = C,...)
        private char GetDeviceLetter(int unitmask)
        {
            //conta in quale posizione della stringa di bit trova il bit 1 (true)
            int cont = 0;
            //vettore di lettere
            char[] lettere = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
            //mette in un array di bit (0,1) i byte ottenuti dal numero int (un int è formato da 4 byte). i bit dei byte sono raggruppati in gruppi di 8
            //es, i primi 8 bit sono il primo byte, gli 8 bit dopo rappresentano il secondo byte,...
            //in realtà è un array di bolleane, dove true indica il bit 1, false il bit 0
            BitArray b = new BitArray(BitConverter.GetBytes(unitmask));

            //spazzola tutti i bit e li controlla
            foreach (bool val in b)
            {
                if (val)
                    return lettere[cont];
                cont++;
            }
            //non dovrebbe mai venire qui, in quanto questo metodo è chiamato solo se si è verificato il messaggio WM_DeviceChange, DeviceArrival e se era un VolumeType
            //quindi l'utente deve avere inserito per forza qual cosa che ha una lettera
            //se viene qui significa che la unit mask è 0 e non identifica nessuna lettera
            return '^'; //carattere speciale, indica che non è stata trovata nessuna lettera
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (folderBrowserDialog1.ShowDialog() == DialogResult.OK)
            {
                if (folderBrowserDialog1.SelectedPath == FileSA)
                {
                    //se l'utente clicca il bottone e seleziona la stessa cartella di default, esce dall'evento e lascia e cose come stanno
                    return;
                }
                //controllo che la cartella che ha scelto l'utente sia in un hard disk fisso (drivetype = fixed)
                //separo la stringa in più stringhe senza il carattere '\', per prendere la lettera che identifica il volume
                string[] split = folderBrowserDialog1.SelectedPath.Split(new char[] { '\\' });
                string root = split[0];
                try
                {
                    DriveInfo d = new DriveInfo(root + "\\");
                    if (d.DriveType != DriveType.Fixed)
                    {
                        MessageBox.Show("Devi selezionare un hard disk fisso (interno)", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        return;
                    }
                }
                catch
                {
                    //se l'utente seleziona un volume che non ha una root directory il driveinfo da eccezione
                    MessageBox.Show("Devi selezionare un hard disk fisso (interno)", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                //se il programma viene qui vuol dire che l'utente ha selezionato una cartella giusta, e devo cancellare la cartella
                //creata nel Form1_Load
                if (Directory.Exists(FileSA))
                {
                    //se l'utente clicca 2 volte sul bottone, la cartella di default è già stata cancellata
                    Directory.Delete(FileSA,true);
                }
                FileSA = folderBrowserDialog1.SelectedPath;
                textBox1.Text = FileSA;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            //per prima cosa, genero il token da salvare nel sistema e nel dispositivo removibile con numeri casuali
            Random generatore = new Random();
            generatore.NextBytes(token);

            //controllo se nei volumi di risorse del computer c'è già un dispositivo removable
            DriveInfo[] drives = DriveInfo.GetDrives();
            foreach (DriveInfo d in drives)
            {
                if (d.DriveType == DriveType.Removable)
                {
                    if (d.Name == floppy)
                    {
                        A = true;
                    }
                    if (d.IsReady)
                    {
                        //CHIEDO ALL'UTENTE SE VUOLE SALVARE IL TOKEN IN QUESTO DISPOSITIVO, SE DICE DI NO NE DEVE INSERIRE UN'ALTRO
                        scelta = MessageBox.Show("Vuoi salvare il token in " + d.Name + "SA", "Domanda", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                        if (scelta == DialogResult.Yes)
                        {
                            Installa(d.Name + PercorsoToken2);
                        }
                    }
                }
            }
            //ho controllato tutti i volumi, se fine è true ho già installato il programma, altrimenti devo aspettare che l'utente inserisca un dispositivo removable
            if (fine)
            {
                Application.Exit();
                return;
            }
            //chiedo all'utente di inserire un dispositivo removable, tipo floppy o penna usb, per salvarci il token
            messaggi = true;
            timer2.Start();
            label3.Text = "Inserisci un dispositivo rimovibile (floppy, chiavetta usb)";
            //se esiste il lettore floppy (A = true) devo far partire un timer che ogni tot secondi controlla se viene inserito un floppy
            //i floppy non vengono avvisati da windows col DeviceArrival
            if (A)
            {
                timer1.Interval = msec;
                timer1.Start();
            }

            //parte il codice che c'è in wnd_proc

            MessageBox.Show("Inserisci un dispositivo rimovibile, tipo un floppy o una penna usb, per poterci salvare il file di riconoscimento","Importante",MessageBoxButtons.OK,MessageBoxIcon.Information);

                //ok, adesso devo copiare i file (che sono da importare, suoni, immagini, e file exe)
                //poi integro il token creator qui, mettendone uno nel percorso dove si installa il programma
                //e uno nella chiavetta usb o floppy dell'utente (drivetype = removable)


            //ORA DEVO CREARE IL TOKEN PER IL SISTEMA E PER L'UTENTE, IL PROGRAMMA RIMANE IN ATTESA FINCHè NON VIENE INSERITO UN FLOPPY O UNA CHIAVETTA USB (REMOVABLE)
            //QUANDO VIENE INSERITO, SCRIVO IL FILE DI REGISTRO, IL TOKEN E GLI ALTRI FILE


        }

        private void Installa(string TokenUtente)
        {
            timer1.Stop();
            timer2.Stop();
            pictureBox1.Image = null;
            label3.Text = "";
            //ok, salvo il token e le altre cose poi chiudo, altrimenti non faccio niente
            //DOVREI CONTROLLARE SE C'è ABBASTANZA SPAZIO PER SCRIVERE IL TOKEN (ANCHE NELL'HARD DISK)
            //token sistema
            FileStream sis = new FileStream(FileSA + "\\TokenSys", FileMode.Create, FileAccess.Write);
            //token utente
            //DEVO CONTROLLARE SE LA CARTELLA ESISTE, SE NON ESISTE DA UN'ECCEZIONE, DEVO CREARLA
            string root = TokenUtente[0].ToString();
            if (!Directory.Exists(root + PercorsoToken))
            {
                Directory.CreateDirectory(root + ":\\SA");
            }
            FileStream ute = new FileStream(TokenUtente, FileMode.Create, FileAccess.Write);
            //scrivo i byte in uno stesso ciclo
            for (int i = 0; i < DimensioneToken; i++)
            {
                sis.WriteByte(token[i]);
                ute.WriteByte(token[i]);
            }
            sis.Close();
            ute.Close();
            //ok, ora posso copiare le immagini
            CopiaImmagini();
            //il file SA.exe
            CopiaFile();
            //e cambiare il registro di sistema peravviare il programma all'avvio di windows
            Registro();
            fine = true;
            MessageBox.Show("Installazione avvenuta con succeso", "Fine", MessageBoxButtons.OK, MessageBoxIcon.Information);
            Application.Exit();
        }

        private void CopiaImmagini()
        {
            //copio immagine benvenuto.gif nel percorso di salvataggio
            FileStream f = File.Create(FileSA + "\\Benvenuto.gif");
            Properties.Resources.ImageBenvenuto.Save(f, ImageFormat.Gif);
            f.Close();

            //copio immagine errore.gif nel percorso di salvataggio
            f = File.Create(FileSA + "\\Errore.gif");
            Properties.Resources.ImageErrore.Save(f, ImageFormat.Gif);
            f.Close();

            //copio immagine inserimento.png nel percorso di salvataggio
            f = File.Create(FileSA + "\\Inserimento.png");
            Properties.Resources.ImageInserimento.Save(f, ImageFormat.Png);
            f.Close();

            //copio immagine timeout.jpg nel percorso di salvataggio
            f = File.Create(FileSA + "\\TimeOut.jpg");
            Properties.Resources.ImageTimeOut.Save(f, ImageFormat.Jpeg);
            f.Close();

            //copio immagine verifica.jpg nel percorso di salvataggio
            f = File.Create(FileSA + "\\Verifica.jpg");
            Properties.Resources.ImageVerifica.Save(f, ImageFormat.Jpeg);
            f.Close();
        }

        private void CopiaFile()
        {
            byte[] SA = Properties.Resources.SA.ToArray<byte>();
            FileStream f = new FileStream(FileSA + "\\SA.exe", FileMode.Create, FileAccess.Write);
            for (int i = 0; i < SA.Length; i++)
            {
                f.WriteByte(SA[i]);
            }
            f.Close();
        }

        private void Registro()
        {
            //imposto il registro di sistema, in modo tale che il programma si avvii all'avvio di Windows
            const string subkey = "Software\\Microsoft\\Windows\\CurrentVersion\\Run";
            RegistryKey key = Registry.CurrentUser.OpenSubKey(subkey, true);
            //se la chiave non esiste, la creo
            if (key == null)
            {
                key = Registry.CurrentUser.CreateSubKey(subkey);
            }
            key.SetValue("SA", FileSA + "\\" + Nome, RegistryValueKind.String);
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (!fine)
            {
                if (MessageBox.Show("Vuoi cancellare la directory di installazione del programma e tutti i suoi file in essa contenuti?", "Domanda", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
                {
                    //cancello la cartella creata
                    if (Directory.Exists(FileSA))
                    {
                        Directory.Delete(FileSA, true);
                    }
                }
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            DriveInfo d = new DriveInfo(floppy);
            //se la variabile Dialog Result scelta è NO, significa che l'utente ha selezionato di inserire un altro dispositivo rimovibile
            //devo controllare prima se il floppy inserito è stato cavato
            if (scelta == DialogResult.No)
            {
                if (!FloppyRimosso)
                {
                    if (!d.IsReady)
                    {
                        FloppyRimosso = true;
                    }
                }
            }
            else
            {
                FloppyRimosso = true;
            }
            if (FloppyRimosso)
            {
                if (d.IsReady)
                {
                    Installa(floppy + PercorsoToken2);
                    timer1.Stop();
                    timer2.Stop();
                    pictureBox1.Image = null;
                    label3.Text = "";
                }
            }
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            if (contTimer2 == 4)
            {
                contTimer2 = 0;
            }
            pictureBox1.Image = barre[contTimer2];
            contTimer2++;
        }

    }
}
//I SUONI SONO INTEGRATI IN SA.EXE
//ora copio i suoni. wav
//  SoundPlayer s = new SoundPlayer(Properties.Resources.SoundBenvenuto);
//  FileStream f = new FileStream(FileSA + "\\Benvenuto.wav", FileMode.Create, FileAccess.Write);
//  for (int i = 0; i < Properties.Resources.SoundBenvenuto.Length; i++)
//   {
//       f.WriteByte((byte)s.Stream.ReadByte());
//   }
//   f.Close();

//SE UNA RISORSA è COLLEGATA IN FASE DI COMPILAZIONE, QUANDO SI AVVIA IL FILE EXE COMPILATO CONTIENE LA RISORSA
//NON C'è BISOGNO DI PORTARSELA DIETRO