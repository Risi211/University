using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Runtime.InteropServices;
using System.Reflection;
using System.Collections;
//devo scrivere tanti commenti alla fine
//IMPLEMENTARE EVENTO TASTI TOGGLED (nello scheletro devo sapere lo stato di quei tasti anche quando viene
//lanciato il programma, es: CAPS toggled o no)
//GUARDARE GETKEYSTATE DELL'EVENTO MOUSEBUTTON
//20 145 144
//CAPS LOCK BLOC SCORR BLOC NUM
namespace Input
{
    //serve per l'API che restituisce le coordinate del cursore del mouse
    public struct tagPOINT
    {
        public int x;
        public int y;
    }

    public class InputListener
    {
        //serve per non chiamare più di una volta il metodo Run mentre il thread va ancora
        bool state = false;
        //serve per creare il thread che sta in ascolto in attesa di input con un ciclo infinito
        Thread thdMain;
        //   coordinate dell'istante precedente del cursore del mouse
        private int lastX = 0;
        private int lastY = 0;
        //lista che prende tutti i tati premuti
        List<int> keycodes = new List<int>();
        //lista che prende i modifiers keys
        List<int> ModifiersKeys = new List<int>();
        //memorizza i tasti premuti per il ciclo dopo, così controllo se qualche tasto è stato rilasciato
        int[] tasti = new int[0];
        //memorizza i modifiers premuti per il ciclo dopo
        int[] modificatori = new int[0];
        int[] ModificatoriReleased = new int[0];
        //virtual key code dei tasti toggled (CAPS_LOCK, BLOC_SCORR, BLOC_NUM)
        const int caps = 20;
        const int scorr = 145;
        const int num = 144;
        //vettore di booleani che si segna lo stato dei tasti toggled
        //all'inizio sono tutti e 3 false (untoggled = led spento sulla tastiera)
        //toggle[0] -->CAPS, toogle[1] --> BLOC SCORR, toggle[2] -->BLOC NUM
        bool[] toggle = new bool[3]; 
        //indica lo stato dei key toggled
        bool[] toggled = new bool[3];
        //costruttore
        public InputListener()
        {
            tagPOINT inizio = new tagPOINT();
            //prende le coordinate del cursore quando viene inizializzato l'oggetto, in questo modo l'evento
            //mouse move non parte subito all'avvio del programma anche se il cursore sta fermo
            //funziona xkè quando l'oggetto input listener viene inizializzato poi viene subito chiamato il metodo Run()
            GetCursorPos(ref inizio);
            //salva le coordinate dell'istante precedente, serve nel controllo del check for mouse move
            lastX = inizio.x;
            lastY = inizio.y;
        }
        //l'evento OnKeyPress parte quando premo un tasto. Se premo un tasto, e dopo
        //10 ms (temporizzatore) ne premo un altro mantenendo premuto il tasto precedente,
        //l'evento passa entrambi i tasti come premuti (GetKeyState, non Async)
        //con GetAsyncKeyState devo premere + pulsanti contemporaneamente, sennò 
        //riconosce i primi tasti premuti solo alla prima volta, poi no
        //N.B.B. il void nel delegate indica il tipo di ritorno dell'evento? quindi
        //un evento può essere scritto nello scheletro dentro un altro metodo
        //e prendere il dato di ritorno??? da provare
        public delegate void KeyPressHandler(object inputListener, KeyPressEventArgs KeyPressInfo);
        public event KeyPressHandler OnKeyPress;

        //evento che si verifica quando un tasto viene rilasciato (GetKeyState)
        public delegate void KeyReleasedHandler(object inputListener, KeyReleasedEventArgs e);
        public event KeyReleasedHandler OnKeyReleased;

        //evento che si verifica quando un tasto passa dallo stato toggled (led acceso)
        //allo stato untoggled (led spento)
        //tasti toggled : CAPS_LOCK, BLOC_SCORR, BLOC_NUM
        public delegate void KeyToggledHandler(object inputListener, KeyToggledEventArgs e);
        public event KeyToggledHandler OnKeyToggled;

        //guardare sull' msdn i virtual key codes (i mod keys sono identificati da più numeri, o uno uguale)
        //(per ttt i tasti sulla tastiera o uno diverso in base alla posizione sulla tastiera, destra o sinistra)
        const int Alt = 18;
        const int Shift = 16;
        const int Ctrl = 17;
        //tasti del mouse
        const int LButton = 1;
        const int RButton = 2;
        const int MButton = 4;

        //API che ritorna Int16.minvalue + 1 se il tasto del key code inserito è premuto, altrimenti un numero diverso 
        [DllImport("user32.dll")]
        public static extern int GetAsyncKeyState(int vKey);
        //esempio di utilizzo
        //if(GetAsyncKeyState(keycode) == Int16.MinValue + 1) ...

        //API che assegna le coordinate del cursore alla struttura passata per riferimento
        //guardare sull' msdn il valore int di ritorno se la funzione fallisce o va
        [DllImport("user32.dll")]
        public static extern int GetCursorPos(ref tagPOINT coordinate);

        //API che dice se il tasto relativo al key code passato alla funzione è premuto o no
        //ritorna un numero col bit + significativo a 1 se è premuto, il bit - significativo a 1 se è toggled
        //(es il CAP MAIUSC è toggled de attivo)
        [DllImport("user32.dll")]
        public static extern short GetKeyState(int KeyCode);

        //evento relativo alla pressione dei tasti del mouse
        public delegate void MouseButtonHandler(object inputListener, MouseButtonEventArgs mouseButtonInfo);
        public event MouseButtonHandler OnMouseButton;
        //evento relativo al movimento del mouse
        public delegate void MouseMoveHandler(object inputListener, MouseMoveEventArgs mouseMoveInfo);
        public event MouseMoveHandler OnMouseMove;

        //metodo che fa partire il thread
        public void Run()
        {
            //il metodo Run può essere chiamato solo una volta dall'oggetto,
            //può essere premuto dopo solo se prima è stato premuto il metodo Stop
            if (!state)
            {
                thdMain = new Thread(new ThreadStart(RunThread));
                thdMain.IsBackground = true;
                thdMain.Start();
            }
            state = true;
        }

        //termina il thread
        public void Stop()
        {
            thdMain.Abort();
            state = false;
        }

        //metodo che gira sempre grazie al ciclo infinito e ascolta l'input
        private void RunThread()
        {
            //inizializzo lo stato dei tasti toggled
            //CAPS LOCK
            short f = GetKeyState(caps);
            BitArray x = new BitArray(BitConverter.GetBytes(f));
            toggle[0] = x[0];
            //BLOC SCORR
            f = GetKeyState(scorr);
            x = new BitArray(BitConverter.GetBytes(f));
            toggle[1] = x[0];
            //BLOC NUM
            f = GetKeyState(num);
            x = new BitArray(BitConverter.GetBytes(f));
            toggle[2] = x[0];
            //ciclo infinito
            while (true)
            {
                //System.Threading, temporizza il threaed corrente (in questo caso il ciclo while)
                //altrimenti verrebbe eseguito ad ogni ciclo di clock del processore
                Thread.Sleep(10);
                // check for key presses e key released
                #region check for key presses

                #region check for key code (tutti i tasti tranne i modifiers keys)

                //l'indice parte da 8 xkè i primi numeri o sn relativi al mouse o nn servono
                //si ferma allo shift xkè i mod keys non si controllano qui
                for (int i = 8; i < Shift; i++)
                {
                    //se il tasto è premuto si salva il key code
                    short j = GetKeyState(i);
                    BitArray b = new BitArray(BitConverter.GetBytes(j));
                    //controllo che il bit + significativo sia a 1
                    if (b[b.Count - 1])
                    {
                        keycodes.Add(i);
                    }                    

                }
                //riparte dal numero dopo il key code dell'ultimo mod key e arriva fino al key code che indica
                //i key codes dei tasti diversi in base alla posizione destra o sinistra
                for (int i = 19; i < 160; i++)
                {
                    //se il tasto è premuto si salva il key code
                    short j = GetKeyState(i);
                    BitArray b = new BitArray(BitConverter.GetBytes(j));
                    //controllo che il bit + significativo sia a 1
                    if (b[b.Count - 1])
                    {
                        keycodes.Add(i);
                    } 
                }
                //poi arriva fino alla fine dei tasti
                for (int i = 166; i < Byte.MaxValue; i++)
                {
                    //se il tasto è premuto si salva il key code
                    short j = GetKeyState(i);
                    BitArray b = new BitArray(BitConverter.GetBytes(j));
                    //controllo che il bit + significativo sia a 1
                    if (b[b.Count - 1])
                    {
                        keycodes.Add(i);
                    } 
                }
                //controllo se era stato premuto qualche tasto nel ciclo prima (all'inizio è vuoto il vettore)
                if (tasti.Length != 0)
                {
                    foreach (int k in tasti)
                    {
                        //controllo se i tasti che prima erano premuti ora sono nella lista dei tasti premuti di questo ciclo
                        if (!keycodes.Contains(k))
                        {
                            //se l'evento è implementato nella classe che usa l'oggetto
                            if (OnKeyReleased != null)
                            {
                                OnKeyReleased(this, new KeyReleasedEventArgs(k));
                            }
                        }
                    }
                }
                //azzero il vettore tasti, se non entro nell'if dopo risulta che il tasto che
                //è stato rilasciato è sempre rilasciato
                tasti = new int[0];
                List<int> mod = new List<int>();
                for (int i = Shift; i <= Alt; i++)
                {
                       //se il tasto non è premuto mentre prima lo era, attivo l'evento
                       //N.B. se tengo premuto un tasto per molto tempo, attiva l'evento
                      //anche se non ho rilasciato il tasto (col metodo GetAsyncKeyState)
                    short k = GetKeyState(i);
                    BitArray b = new BitArray(BitConverter.GetBytes(k));
                    if(b[b.Count - 1])
                    {
                        //tasto premuto
                        mod.Add(i);
                    }
                }
                if (modificatori.Length != 0)
                {
                    //controllo che i tasti premuti siano stati rilasciati
                    foreach (int k in modificatori)
                    {
                        for (int j = 0; j < modificatori.Length; j++)
                        {
                            if (!mod.Contains(k))
                            {
                                //il tasto è stato rilasciato
                                if (OnKeyReleased != null)
                                {
                                    OnKeyReleased(this, new KeyReleasedEventArgs(k));
                                }
                            }
                        }
                    }
                }
                //salvo lo stato attuale dei tasti modificatori nel vettore e pulisco la lista
                //per poterci mettere i tasti aggiornati al ciclo dopo
                modificatori = mod.ToArray();
                mod.Clear();

                #endregion

                #region controllo mod keys e lancio evento key press

                //se è stato premuto un tasto normale può partire l'evento key press, dopo aver controllato se 
                //qualche mod keys è premuto (viene considerato che ne può essere premuto solo 1)
                if (keycodes.Count != 0)
                {
                    //trasporto i tasti dalle liste a un vettore di int
                    tasti = keycodes.ToArray();
                  //  modificatori = ModifiersKeys.ToArray();
                    KeyPressEventArgs KeyPressInfo = new KeyPressEventArgs(modificatori, tasti);
                    if (OnKeyPress != null)
                    {
                        OnKeyPress(this, KeyPressInfo);
                    }
                    //azzero le liste
                    keycodes.Clear();
                    ModifiersKeys.Clear();
                }
                #endregion

                #endregion

                // Check for Mouse Move Events    
                #region Check for Mouse Move Events

                //salva le coordinate del cursore
                tagPOINT coordinate = new tagPOINT();
                GetCursorPos(ref coordinate);
                //se almeno una delle 2 coordinate è diversa 
                if (lastX != coordinate.x || lastY != coordinate.y)
                {
                    //aggiorna le coordinate dello stato precedente con quelle dello stato attuale
                    lastX = coordinate.x;
                    lastY = coordinate.y;
                    MouseMoveEventArgs mouseMoveInfo = new MouseMoveEventArgs(lastX, lastY);
                    //forse la condizione si riferisce al programma "scheletro", 
                    //che usa questa libreria di classi, cioè:
                    //se l'evento è stato scritto lo chiama, sennò no
                    if (OnMouseMove != null)
                    {
                        OnMouseMove(this, mouseMoveInfo);
                    }

                }
                #endregion


                //check for mouse click
                #region Check for Mouse Click
                //stesso controllo del key press e key released per il tasto sinistro
                short t = GetKeyState(LButton);
                BitArray u = new BitArray(BitConverter.GetBytes(t));
                if (u[u.Count - 1])
                {
                    MouseButtonEventArgs mouseButton = new MouseButtonEventArgs(LButton);
                    if (OnMouseButton != null)
                    {
                        OnMouseButton(this, mouseButton);
                    }

                }
                //ancora per il tasto destro
                t = GetKeyState(RButton);
                u = new BitArray(BitConverter.GetBytes(t));
                if (u[u.Count - 1])
                {
                    MouseButtonEventArgs mouseButton = new MouseButtonEventArgs(RButton);
                    if (OnMouseButton != null)
                    {
                        OnMouseButton(this, mouseButton);
                    }

                }
                //ancora per il tasto medio
                t = GetKeyState(MButton);
                u = new BitArray(BitConverter.GetBytes(t));
                if (u[u.Count - 1])
                {
                    MouseButtonEventArgs mouseButton = new MouseButtonEventArgs(MButton);
                    if (OnMouseButton != null)
                    {
                        OnMouseButton(this, mouseButton);
                    }

                }
                #endregion

                #region Check for Key Toggled / Untoggled
                //controllo lo stato di CAPS LOCK
                short g = GetKeyState(caps);
                BitArray n = new BitArray(BitConverter.GetBytes(g));
                //se entra qui il tasto è toggled
                if (n[0])
                {
                    //se entra qui il tasto prima era untoggled, ora è toggled
                    if (!toggle[0])
                    {
                        if (OnKeyToggled != null)
                        {
                            OnKeyToggled(this, new KeyToggledEventArgs(caps, true));
                        }
                    }

                }
                //se entra qui il tasto è untoggled
                else
                {
                    //se entra qui il tasto è untoggled
                    if (!n[0])
                    {
                        //se prima il tasto era toggled, attivo l'evento
                        if (toggle[0])
                        {
                            if (OnKeyToggled != null)
                            {
                                OnKeyToggled(this, new KeyToggledEventArgs(caps, false));
                            }
                        }
                    }
                }
                //salvo lo stato di toggled / untoggled di questo ciclo per controllarlo
                //con il prossimo ciclo
                toggle[0] = n[0];
                //stessa cosa per il BLOC SCORR
                g = GetKeyState(scorr);
                n = new BitArray(BitConverter.GetBytes(g));
                //se entra qui il tasto è toggled
                if (n[0])
                {
                    //se entra qui il tasto prima era untoggled, ora è toggled
                    if (!toggle[1])
                    {
                        if (OnKeyToggled != null)
                        {
                            OnKeyToggled(this, new KeyToggledEventArgs(scorr, true));
                        }
                    }

                }
                //se entra qui il tasto è untoggled
                else
                {
                    //se entra qui il tasto è untoggled
                    if (!n[0])
                    {
                        //se prima il tasto era toggled, attivo l'evento
                        if (toggle[1])
                        {
                            if (OnKeyToggled != null)
                            {
                                OnKeyToggled(this, new KeyToggledEventArgs(scorr, false));
                            }
                        }
                    }
                }
                //salvo lo stato di toggled / untoggled di questo ciclo per controllarlo
                //con il prossimo ciclo
                toggle[1] = n[0];
                //stessa cosa per il BLOC NUM
                g = GetKeyState(num);
                n = new BitArray(BitConverter.GetBytes(g));
                //se entra qui il tasto è toggled
                if (n[0])
                {
                    //se entra qui il tasto prima era untoggled, ora è toggled
                    if (!toggle[2])
                    {
                        if (OnKeyToggled != null)
                        {
                            OnKeyToggled(this, new KeyToggledEventArgs(num, true));
                        }
                    }

                }
                //se entra qui il tasto è untoggled
                else
                {
                    //se entra qui il tasto è untoggled
                    if (!n[0])
                    {
                        //se prima il tasto era toggled, attivo l'evento
                        if (toggle[2])
                        {
                            if (OnKeyToggled != null)
                            {
                                OnKeyToggled(this, new KeyToggledEventArgs(num, false));
                            }
                        }
                    }
                }
                //salvo lo stato di toggled / untoggled di questo ciclo per controllarlo
                //con il prossimo ciclo
                toggle[2] = n[0];
                #endregion
            }

        }
    }
    //classe che serve all'evento OnMouseMove per passare i parametri
    public class MouseMoveEventArgs : EventArgs
    {
        public MouseMoveEventArgs(int X, int Y)
        {
            this.X = X;
            this.Y = Y;
        }
        public readonly int X;
        public readonly int Y;

    }
    //classe che serve all'evento OnMouseButton per passare i parametri
    public class MouseButtonEventArgs : EventArgs
    {
        public MouseButtonEventArgs(int Button)//
        {
            this.Button = Button;
        }
        public readonly int Button;//

    }
    //classe che serve all'evento OnKeyPress per passare i parametri
    public class KeyPressEventArgs : EventArgs
    {
        public KeyPressEventArgs(int[] ModifierKeys,//
         int[] KeyCodes)
        {
            this.ModifierKeys = ModifierKeys;
            this.KeyCodes = KeyCodes;
        }
        public readonly int[] ModifierKeys;//
        public readonly int[] KeyCodes;

    }
    //classe che serve all'evento OnKeyReleased per passare i parametri
    public class KeyReleasedEventArgs : EventArgs
    {
        public readonly int KeyCode;

        public KeyReleasedEventArgs(int KeyCode)
        {
            this.KeyCode = KeyCode;
        }

    }
    //classe che serve all'evento OnKeyToggled per passare i parametri
    public class KeyToggledEventArgs : EventArgs
    {
        public readonly int KeyToggled;
        public readonly bool Toggled;

        public KeyToggledEventArgs(int KeyToggled, bool Toggled)
        {
            this.KeyToggled = KeyToggled;
            this.Toggled = Toggled;
        }
    }
}
