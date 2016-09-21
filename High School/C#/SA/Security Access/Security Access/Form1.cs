using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Media;
using System.IO;
using System.Runtime.InteropServices;
using System.Diagnostics;
using Microsoft.Win32;
using System.Collections;

namespace Security_Access
{
    
    public partial class Form1 : Form
    {
        #region variabili
        //variabili per messaggi windows
        const int WM_DeviceChange = 537; //cerca WM_DEVICECHANGE su msdn
        const int DeviceArrival = 32768; //0x8000
        const int NodesChanged = 7;
        const int DeviceRemoveComplete = 32772; //0x8004
        //numero che identifica il tipo di dispositivo (volumi o porte)
        const int VolumeType = 2; //chiavette usb, dvd,...
        const int PortType = 3; //penso sia solo per le porte seriali, non usb
        //percorso dove si trova il file exe SA.exe, serve per settare il registro di sistema e fare avviare il programma quando si avvia windows
        //in HKEY_CurrentUser\\Software\\Microsoft\\Windows\\Run

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
        //struttura che prende informazioni sulla porta (COM?)
        [StructLayout(LayoutKind.Sequential)]
        public struct DEV_BROADCAST_PORT
        {
            public int dbch_size;
            public int dbch_devicetype;
            public int dbch_reserved;
            public string dbcp_name;
        }



        //API che fa disconnettere il pc
        [DllImport("User32.dll")]
        public static extern int ExitWindowsEx(uint uFlag, uint dwReserved);

        //API Register Device
        [DllImport("User32.dll")]
        public static extern IntPtr RegisterDeviceNotification(IntPtr hRecipient, IntPtr NotificationFilter, uint Flags);

        //se passano 20 sec e il token non è stato inserito il pc si spegne
        const int MaxTime = 120; //il timer1 ha un tick ogni 500 ms
        //tengono il tempo per ogni timer
        int tempo1 = 0;
        int tempo2 = 0;
        int tempo3 = 0;
        int tempo4 = 0;
        int tempo6 = 0;
        int tempoMax = 0; //conta il tempo totale del timer1, se arriva a 20 sec (cioè 40) e se il timer1 è ancora avviato, si spegne il pc

        //percorsi assoluti dei file audio .wav sul disco fisso
    //    const string percorso1 = "C:\\SA\\Inserimento.wav";
        //contiene il file audio, e ha metodi .start e .stop
        SoundPlayer inserire;
        //usate nel timer per sapere quando parte la voce e quando finisce la voce (da cambiare se cambia il file audio, la differenza in questo caso 8, è uguale alla durata della voce * 2 (tick 500ms))
        const int InizioInserire = 1;
        const int FineInserire = 7;

   //     const string percorso2 = "C:\\SA\\Verifica.wav";
        SoundPlayer convalida;
        const int InizioConvalida = 1;
        const int FineConvalida = 8;
        const string ImmagineVerifica = "\\Verifica.jpg";

    //    const string percorso3 = "C:\\SA\\Errore.wav";
        SoundPlayer errore;
        const int InizioErrore = 1;
        const int FineErrore = 9;
        const string ImmagineErrore = "\\Errore.gif";

       // const string percorso4 = "C:\\SA\\Benvenuto.wav";
        SoundPlayer benvenuto;
        const int InizioBenvenuto = 1;
        const int FineBenvenuto = 9;
        const string ImmagineBenvenuto = "\\Benvenuto.gif";

     //   const string percorso5 = "C:\\SA\\TimeOut.wav";
        SoundPlayer TimeOut;
        const int InizioTimeOut = 1;
        const int FineTimeOut = 5;
        const string ImmagineTimeOut = "\\TimeOut.jpg";

        //percorso del token del sistema, usato per controllare il token dell'utente
        const string tokensys = "\\TokenSys";
        //percorso assoluto del file exe
        string path;

        //contiene i byte del token del dispositivo utente nel percorso [Lettera]:\\SA\\Token
        byte[] utente;
        //contiene i byte del token del sistema
        byte[] sistema;

        //se il primo elemento è true, significa che l'utente ha inserito un dispositivo
        ///se il secondo è true, esiste il file SA nel dispositivo utente e deve essere controllato col metodo Confronto()
        bool[] esiste = new bool []{false, false};

        //il metodo Confronto() deve essere chiamato solo una volta dal timer2, che sennò lo chiamerebbe ogni volta, visto che controlla il file audio della voce
        bool GiaChiamato = false;
        //alla fine del Confronto(), se il token utente è uguale al token del sistema, diventa true e il form si chiude, sennò si spegne il pc
        bool avanti = false;
        //true quando il Confronto() è finito, di conseguenza quando finisce la voce del timer2, o si va al timer 3 o al timer4 e si chiude il timer2
        bool FineConfronto = false;

        //costante che indica il percorso assoluto del token dell'utente dopo la lettera del volume logico (viene fornita dal metodo GetDeviceLetter)
        const string percorso = ":\\SA\\TokenUser";

        //lista di strinche che contiene le lettere dei volumi logici trovati in risorse del computer
        List<string> volumi = new List<string>();
        #endregion

        //cambia la cartella .\\ in C:\Documents and Settings\Luca e cerca tokensys quando windows avvia il programa dal registro
        public Form1()
        {
            InitializeComponent();

            //inizializzo le variabili Sound
            inserire = new SoundPlayer(Properties.Resources.SoundInserimento);
            convalida = new SoundPlayer(Properties.Resources.SoundVerifica);
            errore = new SoundPlayer(Properties.Resources.SoundErrore);
            benvenuto = new SoundPlayer(Properties.Resources.SoundBenvenuto);
            TimeOut = new SoundPlayer(Properties.Resources.SoundTimeOut);

            //carico i byte del token del sistema nel vettore (Da fare con il Buffer se il file è molto pesante)
          //  sistema = File.ReadAllBytes(tokensys);

            //carica i file .wav, dopo sono pronti per essere eseguiti (se pesano molto è meglio caricarli prima di utilizzarli e rilasciarli quando non servono più)
            inserire.Load();
            convalida.Load();
            errore.Load();
            benvenuto.Load();
            TimeOut.Load();
            
        }
        //metodo che viene chiamato da Windows ogni volta che si verifica un messaggio Windows (spostamento del mouse, apertura finestra,...) vedere l'elenco da internet
        protected override void WndProc(ref Message m) //parametro che contiene info sul messaggio Windows
        {

            switch (m.Msg) //int che si riferisce al messaggio (vedere l'elenco per vedere i rispettivi numeri ai nomi)
            {
                case WM_DeviceChange:
                    {

                        switch ((int)m.WParam)
                        {
                            //CI METTE CIRCA 10 SECONDI AD ARRIVARE QUESTO MESSAGGIO WINDOWS DOPO CHE è STATA INSERITA LA CHIAVETTA
                            case DeviceArrival:
                                {
                                    //prende informazioni sul tipo di dispositivo
                                    DEV_BROADCAST_HDR dev = (DEV_BROADCAST_HDR)Marshal.PtrToStructure(m.LParam, typeof(DEV_BROADCAST_HDR));
                                    switch (dev.dbch_devicetype)
                                    {
                                        //se è un volume logico identificato da una lettera
                                        case VolumeType:
                                            {
                                                //prende le informazioni dal parametro del message e la salva nella struttura
                                                DEV_BROADCAST_VOLUME volume = (DEV_BROADCAST_VOLUME)Marshal.PtrToStructure(m.LParam, typeof(DEV_BROADCAST_VOLUME));
                                                //prendo la lettera del volume
                                                char lettera = GetDeviceLetter(volume.dbcv_unitmask);
                                                
                                                try
                                                {
                                                    //prova a leggere i byte del token utente, se non esiste va nel catch
                                                    utente = File.ReadAllBytes(lettera.ToString() + percorso);
                                                    
                                                    //questo codice lo esegue solo se riesce a leggere i byte e non va nel catch
                                                    esiste[1] = true;
                                                }
                                                catch
                                                {
                                                    //non c'è il token dell'utente  
                                                    
                                                }
                                                esiste[0] = true;
                                                break;
                                            }
                                    //case PortType: ... a me non serve

                                    }



                                    break;
                                }

                                //se mentre c'è il form si stacca qualche dispositivo (mouse, tastiera,...) parte questo messaggio
                            case DeviceRemoveComplete:
                                {

                                    break;
                                }
                                
                                //SE INSERISCO UN DVD NON VA NEL NODES CHANGED, MA VA NEL DEVICEARRIVAL DIRETTAMENTE (volume che era già in risorse del computer ma non era IsReady perchè non era inserito il dvd)
                            case NodesChanged:
                                {

                                    break;
                                }

                        }

                        break;
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

        //controlla la vocina che si ripete e dice: inserire token di riconoscimento
        //se parte questo timer, significa che nessun floppy o dvd era già inserito quando il pc si è acceso, oppure che era inserito ma non conteneva nessun token 
        private void timer1_Tick(object sender, EventArgs e)
        {
            if (esiste[0])
            {
                timer1.Stop();
                try
                {
                    this.BackgroundImage = Image.FromFile(path + ImmagineVerifica);
                }
                catch
                {

                }
                timer2.Start();
                return;
            }
            //controllo che non si sia raggiunto il tempo massimo
            if (tempoMax == MaxTime)
            {
                timer1.Stop();
                try
                {
                    this.BackgroundImage = Image.FromFile(path + ImmagineTimeOut);
                }
                catch
                {
                }
                timer6.Start();
                return;
            }
            //dopo mezzo sec che il form si è caricato parte la voce 1, parla finchè non viene inserite la chiavetta
            if(tempo1 == InizioInserire)
                {
                    inserire.Play();
                }
            if (tempo1 == FineInserire)
            {
                //Controllo se è stato inserito un floppy
                if (volumi.Contains("A"))
                {
                    DriveInfo d = new DriveInfo("A:\\");
                    if (d.IsReady)
                    {
                        try
                        {
                            utente = File.ReadAllBytes("A" + percorso);
                            esiste[1] = true;
                        }
                        catch
                        {

                        }
                        esiste[0] = true;
                    }
                }
                //se è stato trovato un dispositivo e la voce ha finito di parlare, il timer si chiude e parte la convalida
             //   if (esiste[0])
             //   {
             //       timer1.Stop();
             //       timer2.Start();
             //   }
                inserire.Stop();
                tempo1 = 0;
                return;
            }

            tempo1++;
            tempoMax++;

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            //carico i byte del token del sistema nel vettore (Da fare con il Buffer se il file è molto pesante)
            path = Application.ExecutablePath;
            path = path.Remove(path.LastIndexOf('\\'));
            sistema = File.ReadAllBytes(path + tokensys);

            //Parte il timer che rende inchiudibile il form, in quanto fa il focus sulla finestra sempre, ed è TopMost, quindi oscura il TaskManager
            timer5.Start();

            //controllo che il token non sia già inserito, prendendo la lista dei volumo logici trovati su risorse del computer
            //(A,C,D,F,...)
            DriveInfo[] drives = DriveInfo.GetDrives();

            int cont = 0; //conta il numero dei volumi che sono IsReady (), cioè il dvd o il floppy è inserito
            foreach (DriveInfo drive in drives)
            {
                //non devo aggiungere il volume C:\\ e nessun hard disk fisso (DriveType = fixed)
                //sennò prenderebbe come token utente quello del sistema e se li confronta sono sempre uguali
                if (drive.DriveType != DriveType.Fixed)
                {
                    //devo estrarre la lettera, ad esempio D da D:\\
                    string tmp = drive.Name[0].ToString();
                    volumi.Add(tmp);
   
                    if(drive.IsReady)
                    {
                        cont++;
                    }
                }
            }
            
            //se non esiste neanche un volume IsReady, il cont è = a 0
            if (cont == 0) //c'è solo l'hard disk C:\\
            {
                //NON SI SPEGNE COME QUANDO INSERISCI UN FLOPPY COL TOKEN SBAGLIATO QUANDO IL PROGRAMMA è GIà ATTIVO
                //parte la parte dell'inserimento token (timer1)
                timer1.Start();
                return;
            }
            //c'è un altro volume logico pronto (es dvd inserito, floppy inserito,...)
            else
            {
                //per ogni volume trovato IsReady, controllo se esiste il percorso (Lettera):\\SA\\Token
                //se esiste, faccio partire la convalida in corso
                //sennò chiedo all'utente di inserire il token
                bool stato = false;
                int j = 1;
                foreach (string lettera in volumi)
                {
                    //LIMITAZIONE
                    //il controllo si ferma al primo volume che ha il token utente
                    if (!stato)
                    {
                        if (lettera != null)
                        {
                            try
                            {
                                utente = File.ReadAllBytes(lettera + percorso);
                                //se esiste il percorso, allora cambia anche le variabili booleane, altrimenti va direttamente al catch
                                esiste[0] = true;
                                esiste[1] = true;
                                stato = true;
                                timer2.Start();
                                try
                                {
                                    this.BackgroundImage = Image.FromFile(ImmagineVerifica);
                                }
                                catch
                                {

                                }
                                return;
                            }
                            catch
                            {
                                //POSSIBILE DA FARE: COMPARIRE UNA MBOX CON SCRITTO I VOLUMI CON DENTRO DISCHI (DVD O FLOPPY)
                                //se è l'ultimo volume logico della lista e non ha trovato nessun token nei volumi,
                                //faccio partire il timer1 (inserimento token)
                                if (j == volumi.Count)
                                {
                                    timer1.Start();
                                    return;
                                }
                            }
                        }
                    }
                    j++;
                }
            }
           
        }
        
        //timer che confronta il token dell'utente
        private void timer2_Tick(object sender, EventArgs e)
        {
            //chiama metodo confronta token quando la voce ha finito di parlare
            if (!GiaChiamato)
            {
                Confronto();
                GiaChiamato = true;
            }
            if(tempo2 == InizioConvalida)
            {
                convalida.Play();
            }
            if(tempo2 == FineConvalida)
            {
                //se FineConfronto == true, controllo lo stato di avanti: true posso accedere, false spegne il pc
                if (FineConfronto)
                {
                    //vado in: benvenuto, buon lavoro
                    if (avanti)
                    {
                        timer2.Stop();
                        try
                        {
                            this.BackgroundImage = Image.FromFile(path + ImmagineBenvenuto);
                        }
                        catch
                        {

                        }
                        timer4.Start();
                    }
                    else
                    {
                        //errore, spegne pc
                        timer2.Stop();
                        try
                        {
                            this.BackgroundImage = Image.FromFile(path + ImmagineErrore);
                        }
                        catch
                        {

                        }
                        timer3.Start();
                    }
                }
                convalida.Stop();
                tempo2 = 0;

            }
            tempo2++;

        }

        //può essere chiamato solo una volta dal timer2, in quanto verrebbe chiamato ad ogni tick del timer
        //finche non finisce di parlare la voce
        private void Confronto()
        {
            //se non esiste il token utente nel dispositivo non faccio neanche il controllo ed esco
            if (!esiste[1])
            {
                //INTERROMPE TUTTO E VAI AL TIMER 3
                //avanti rimane false
                FineConfronto = true;
                return;
            }
            else
            {
                if (sistema.Length != utente.Length)
                {
                    //token diversi, hanno un peso diverso, non faccio neanche il confronto
                    //si spegne il pc
                    //avanti rimane false
                    FineConfronto = true;
                    return;
                }
                for (int i = 0; i < sistema.Length; i++)
                {
                    if (sistema[i] != utente[i])
                    {
                        //token diversi, in quanto il byte confrontato è diverso
                        //si spegne il pc
                        //avanti rimane false
                        FineConfronto = true;
                        return;
                    }

                }
                //fine ciclo
                //token uguali
                avanti = true;
                FineConfronto = true;
              
            }

        }

        private void timer3_Tick(object sender, EventArgs e)
        {
            if(tempo3 == InizioErrore)
            {
                errore.Play();
            }
            if(tempo3 == FineErrore)
            {
                errore.Stop();
                timer3.Stop();
                Application.Exit();
                //rilascia le risorse (il Garbage Collector) e spegne pc
                ExitWindowsEx(0, 0);
            }
            tempo3++;
        }

        private void timer4_Tick(object sender, EventArgs e)
        {
            if(tempo4 == InizioBenvenuto)
            {
                benvenuto.Play();
            }
            if(tempo4 == FineBenvenuto)
            {
                benvenuto.Stop();
                timer4.Stop();

                Application.Exit();
            }
            tempo4++;
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            //se il form si chiude per un motivo diverso dalla chiamata di uscita dell'applicazione, non si chiude
            if(e.CloseReason != CloseReason.ApplicationExitCall)
            {
                e.Cancel = true;
            }
        }

        private void timer5_Tick(object sender, EventArgs e)
        {
            this.Focus();
            //ottengo tutti i processi che hanno quel nome
            Process[] processi = Process.GetProcessesByName("taskmgr");
            foreach(Process p in processi)
            {
                //il task manager se chiamato velocemente aumenta di sicurezza e il p.Kill() da 
                //l'eccezione accesso negato
                //da provare in un servizio con autorità di Local System a killare il task manager
                try    
                {
                    p.Kill();
                }
                catch
                {

                }
  
            }
        }

        //carica la voce del tempo scaduto e spegne il pc
        private void timer6_Tick(object sender, EventArgs e)
        {
            if(tempo6 == InizioTimeOut)
            {
                TimeOut.Play();
            }
            if(tempo6 == FineTimeOut)
            {

                TimeOut.Stop();
                timer6.Stop();
                Application.Exit();
                ExitWindowsEx(0,0);
            }
            tempo6++;
        }
    }
}
