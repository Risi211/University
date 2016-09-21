using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Input;
using System.Data;
using System.Data.OleDb;
using System.Data.Odbc;
using System.Data.SqlClient;
using System.Data.Sql;

namespace Prova
{
    public partial class Form1 : Form
    {
        InputListener inputListener;
        bool show = false;
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
            Leggi();
        }
        //funziona perfettamente
        private void InputListener_KeyPress(object sender, Input.KeyPressEventArgs e) //guardare virtual key codes msdn
        {
            if (e.KeyCodes.Length == 2)
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

        private void Leggi()
        {
            /*
            string p = "Data Source=LUCA-6E7201775B\\SQLSERVER; Initial Catalog=SpeedUp; User Id=Luca; Password=; Integrated Security=SSPI";
            SqlConnection c = new SqlConnection(p);
            c.Open();
            SqlCommand d = new SqlCommand("select * from TASTI", c);
            SqlDataReader r = d.ExecuteReader();
            List<string> s = new List<string>();
            while (r.Read() != false)
            {
                s.Add(r["NOME"].ToString());
            }
            c.Close();
            string g = "";
            foreach (string i in s)
            {
                g += "\n" + i;
            }
            MessageBox.Show(g);
             * */
            string connessione = "Data Source=LUCA-6E7201775B\\SQLSERVER; Initial Catalog=SpeedUp; User Id=Luca; Password=; Integrated Security=SSPI";
            SqlConnection SQLconnessione = new SqlConnection(connessione);

        }

        private void impostazioniToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (!show)
            {
                //se voglio far vedere la finestra facendola passare darettamente dalla system tray al
                //desktop, prima devo impostare il form maximized, fare show(), metterlo
                //normale e aggiornarlo
                show = true;
                this.WindowState = FormWindowState.Minimized;
                this.ResizeRedraw = true;
                this.Show();
                this.WindowState = FormWindowState.Normal;
                this.Refresh();
            }
        }

        private void notifyIcon1_DoubleClick(object sender, EventArgs e)
        {
            if (!show)
            {
                //se voglio far vedere la finestra facendola passare darettamente dalla system tray al
                //desktop, prima devo impostare il form maximized, fare show(), metterlo
                //normale e aggiornarlo
                show = true;
                this.WindowState = FormWindowState.Minimized;
                this.ResizeRedraw = true;
                this.Show();
                this.WindowState = FormWindowState.Normal;
                this.Refresh();
            }
            
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (e.CloseReason == CloseReason.UserClosing)
            {
                e.Cancel = true;
                this.WindowState = FormWindowState.Minimized;
                this.Hide();
                show = false;
            }
        }

        private void Form1_HelpButtonClicked(object sender, CancelEventArgs e)
        {
            //faccio vedere il form che faccio veere in about SpeddUp
        }
    }
}
