using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Input;

namespace Prova
{
    public partial class Form1 : Form
    {
        InputListener inputListener;
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            //cancella la barra dell'applicazione dalla task bar
            this.Hide();
            inputListener = new InputListener();
            inputListener.OnMouseMove += new InputListener.MouseMoveHandler(InputListener_MouseMove);
            inputListener.OnMouseButton += new InputListener.MouseButtonHandler(InputListener_MouseButton);
            inputListener.OnKeyPress += new InputListener.KeyPressHandler(InputListener_KeyPress);
            inputListener.OnKeyReleased +=new InputListener.KeyReleasedHandler(InputListener_KeyReleased);
            inputListener.OnKeyToggled += new InputListener.KeyToggledHandler(InputListener_KeyToggled);
            inputListener.Run();
            Application.ApplicationExit += new EventHandler(Application_ApplicationExit);
        }
        //funziona perfettamente
        private void InputListener_KeyPress(object sender, Input.KeyPressEventArgs e) //guardare virtual key codes msdn
        {
            if (e.KeyCodes.Length > 2)
            {

            }
        }
        //funziona perfettamente
        private void InputListener_MouseMove(object sender, Input.MouseMoveEventArgs e)
        {


        }
        //funziona perfettamente
        private void InputListener_MouseButton(object sender, Input.MouseButtonEventArgs e)
        {

        }

        private void InputListener_KeyReleased(object sender, Input.KeyReleasedEventArgs e)
        {

        }

        private void InputListener_KeyToggled(object sender, Input.KeyToggledEventArgs e)
        {

        }

        void Application_ApplicationExit(object sender, EventArgs e)
        {
            inputListener.Stop();
            //throw new NotImplementedException();
        }

        private void chiudiToolStripMenuItem_Click_1(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}
