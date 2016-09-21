using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Runtime.InteropServices;
using System.Reflection;
using System.Collections;
//devo scrivere tanti commenti alla fine

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
        //numero keycode premuto
        int keycode = 0;
        //numero precedente del keycode premuto
        int PreviousKeyCode = 0;
        //numero modkey premuto
        int modkey = 0;
        //quanti tasti possono essere memorizzati al massimo, 4 di default
        int MaxKeyCodeLength = 4;
        //serve per non chiamare più di una volta il metodo Run mentre il thread va ancora
        bool state = false;
        //serve per creare il thread che sta in ascolto in attesa di input con un ciclo infinito
        Thread thdMain;
        //   coordinate dell'istante precedente del cursore del mouse
        private int lastX = 0;
        private int lastY = 0;
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

        public delegate void KeyPressHandler(object inputListener, KeyPressEventArgs KeyPressInfo);
        public event KeyPressHandler OnKeyPress;

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

        //API che assegna le coordinate del cursore alla struttura passata per riferimento
        //guardare sull' msdn il valore int di ritorno se la funzione fallisce o va
        [DllImport("user32.dll")]
        public static extern int GetCursorPos(ref tagPOINT coordinate);

        //API che dice se il tasto relativo al key code passato alla funzione è premuto o no
        //ritorna un numero col bit + significativo a 1 se è premuto, il bit - significativo a 1 se è toggled
        //(es il CAP MAIUSC è toggled de attivo)
        [DllImport("user32.dll")]
        public static extern int GetKeyState(int KeyCode);

        public delegate void MouseButtonHandler(object inputListener, MouseButtonEventArgs mouseButtonInfo);
        public event MouseButtonHandler OnMouseButton;

        public delegate void MouseMoveHandler(object inputListener, MouseMoveEventArgs mouseMoveInfo);
        public event MouseMoveHandler OnMouseMove;

        //metodo che fa partire il thread
        public void Run()
        {
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
            //ciclo infinito
            while (true)
            {
                //System.Threading, temporizza il threaed corrente (in questo caso il ciclo while)
                //altrimenti verrebbe eseguito ad ogni ciclo di clock del processore
                Thread.Sleep(10);
                // check for key presses
                #region check for key presses

                #region check for key code (tutti i tasti tranne i modifiers keys)

                //l'indice parte da 8 xkè i primi numeri o sn relativi al mouse o nn servono
                //si ferma allo shift xkè i mod keys non si controllano qui
                for (int i = 8; i < Shift; i++)
                {
                    //se il tasto è premuto si salva il key code
                    if (GetAsyncKeyState(i) == Int16.MinValue + 1)
                    {
                        keycode = i;
                    }

                }
                //riparte dal numero dopo il key code dell'ultimo mod key e arriva fino al key code che indica
                //i key codes dei tasti diversi in base alla posizione destra o sinistra
                for (int i = 19; i < 160; i++)
                {
                    if (GetAsyncKeyState(i) == Int16.MinValue + 1)
                    {
                        keycode = i;
                    }

                }
                //poi arriva fino alla fine dei tasti
                for (int i = 166; i < Byte.MaxValue; i++)
                {
                    if (GetAsyncKeyState(i) == Int16.MinValue + 1)
                    {
                        keycode = i;
                    }

                }
                #endregion

                #region controllo mod keys e lancio evento key press

                //cavare il commento se si vuole che l'evento parte solo una volta per un tasto premuto, e parte
                //per lo stesso tasto solo se viene premuto un altro tasto prima
                //se rimane premuto lo stesso tasto nel ciclo infinito non parte l'evento perchè si azzera il keycode
                //   if (keycode == PreviousKeyCode)
                //   {
                //       keycode = 0;
                //   }

                //se è stato premuto un tasto normale può partire l'evento key press, dopo aver controllato se 
                //qualche mod keys è premuto (viene considerato che ne può essere premuto solo 1)
                if (keycode != 0)
                {
                    //controlla se qualche mod keys è premuto

                    for (int i = Shift; i <= Alt; i++)
                    {
                        if (GetAsyncKeyState(i) == Int16.MinValue + 1)
                        {
                            modkey = i;
                        }

                    }

                    KeyPressEventArgs KeyPressInfo = new KeyPressEventArgs(modkey, keycode);
                    if (OnKeyPress != null)
                    {
                        OnKeyPress(this, KeyPressInfo);
                    }
                    PreviousKeyCode = keycode;
                    keycode = 0;
                    modkey = 0;

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
                    if (OnMouseMove != null)
                    {
                        OnMouseMove(this, mouseMoveInfo);
                    }

                }
                #endregion


                //check for mouse click
                #region Check for Mouse Click
                //stesso controllo del key press
                if (GetAsyncKeyState(LButton) == Int16.MinValue + 1)
                {
                    MouseButtonEventArgs mouseButton = new MouseButtonEventArgs(LButton);
                    if (OnMouseButton != null)
                    {
                        OnMouseButton(this, mouseButton);
                    }

                }
                if (GetAsyncKeyState(RButton) == Int16.MinValue + 1)
                {
                    MouseButtonEventArgs mouseButton = new MouseButtonEventArgs(RButton);
                    if (OnMouseButton != null)
                    {
                        OnMouseButton(this, mouseButton);
                    }

                }
                if (GetAsyncKeyState(MButton) == Int16.MinValue + 1)
                {
                    MouseButtonEventArgs mouseButton = new MouseButtonEventArgs(MButton);
                    if (OnMouseButton != null)
                    {
                        OnMouseButton(this, mouseButton);
                    }

                }
                #endregion

            }

        }
    }

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
    //può essere migliorata con lo stato pressed / released
    public class MouseButtonEventArgs : EventArgs
    {
        public MouseButtonEventArgs(int Button)//
        {
            this.Button = Button;
        }
        public readonly int Button;//

    }
    //può essere migliorata con lo stato pressed / released
    public class KeyPressEventArgs : EventArgs
    {
        public KeyPressEventArgs(int ModifierKeys,//
         int KeyCodes)
        {
            this.ModifierKeys = ModifierKeys;
            this.KeyCodes = KeyCodes;
        }
        public readonly int ModifierKeys;//
        public readonly int KeyCodes;

    }



}
