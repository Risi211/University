using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace appboard
{
    public partial class Form1 : Form
    {

        Panel [] p = new Panel [8];
        Color[] c = new Color[8]; 
        Color grigio = Color.LightGray;
        Color rosso = Color.Red;
        Color verde = Color.Green;
        Color giallo = Color.Yellow;

        int indirizzo = 0x378;
        int InMR = 0x379;
        int MotoreAcceso = 12;
        int MotoreSpento = 8;
        int RiscaldatoreAcceso = 0;
        int RiscaldatoreSpento = 2;
        bool temperatura = false;
        const int bit = 128;
        int num = 0;
        int r = 0;
        byte asd = 1;

        int cont = 0;
        sbyte puntatore = 0;
        int car = 7; 
        bool aumento = true;
        int tempo = 0;
        ushort S1 = 0;
        ushort S2 = 0;
        RadioButton[] c1 = new RadioButton[3];
        RadioButton[] c2 = new RadioButton[3];
        object sender;
        EventArgs args = new EventArgs();

        public Form1()
        {
            InitializeComponent();
            //nel costruttore inizializzo anche i vettori dei pannelli e dei colori
            InizializzaPannelli();
            InizializzaColori();
            InizializzaRadioButtonSemaforo();
        }

        private void InizializzaPannelli()
        {
            p[0] = bit0;
            p[1] = bit1;
            p[2] = bit2;
            p[3] = bit3;
            p[4] = bit4;
            p[5] = bit5;
            p[6] = bit6;
            p[7] = bit7;
        }

        private void InizializzaColori()
        {
            c[7] = Color.Red;
            c[6] = Color.Yellow;
            c[5] = Color.Green;
            c[4] = Color.Yellow;
            c[3] = Color.Red;
            c[2] = Color.Yellow;
            c[1] = Color.Green;
            c[0] = Color.Yellow;
        }

        private void InizializzaRadioButtonSemaforo()
        {
            c1[0] = RadioButtonCaso1lento;
            c1[1] = RadioButtonCaso1veloce;
            c1[2] = RadioButtonCaso1uguale;
            c2[0] = RadioButtonCaso2lento;
            c2[1] = RadioButtonCaso2veloce;
            c2[2] = RadioButtonCaso2uguale;
        }

        private void ButtonOnLed_Click(object sender, EventArgs e)
        {
            for (byte i = 0; i <= 7; i++)
                p[i].BackColor = c[i];
            PortAccess.Output(indirizzo, 255);
            ButtonOffLed.Enabled = true;
            ButtonOnLed.Enabled = false;
            PictureBoxOnOff.BackgroundImage = Image.FromFile(@".\on.bmp");
        }

        private void ButtonOffLed_Click(object sender, EventArgs e)
        {
            Cambio();
            ButtonOnLed.Enabled = true;
            ButtonOffLed.Enabled = false;
            PictureBoxOnOff.BackgroundImage = Image.FromFile(@".\off.bmp");
        }

        private void RadioContatoreLento_CheckedChanged(object sender, EventArgs e)
        {
            if (TimerContatore.Enabled)
                TimerContatore.Stop();
            TimerContatore.Interval = 1000;
            TimerContatore.Start();
            ButtonFermaContatore.Enabled = true;
            ButtonResetContatore.Enabled = true;
        }

        private void RadioContatoreMedio_CheckedChanged(object sender, EventArgs e)
        {
            if (TimerContatore.Enabled)
                TimerContatore.Stop();
            TimerContatore.Interval = 200;
            TimerContatore.Start();
            ButtonFermaContatore.Enabled = true;
            ButtonResetContatore.Enabled = true;
        }

        private void TimerContatore_Tick(object sender, EventArgs e)
        {
            ButtonFermaContatore.Enabled = true;
            ButtonResetContatore.Enabled = true;
            cont++;
            LabelContatore.Text = cont.ToString();
            //codice che visualizza i pannelli sul simulatore e che accende i led
            byte mask = 1;
            for (byte i = 0; i <= 7; i++)
            {
                if ((cont & mask) == mask)
                    p[i].BackColor = c[i];
                else
                    p[i].BackColor = grigio;
                mask <<= 1;
            }
            PortAccess.Output(indirizzo, cont);
        }

        private void RadioContatoreVeloce_CheckedChanged(object sender, EventArgs e)
        {
            if (TimerContatore.Enabled)
                TimerContatore.Stop();
            TimerContatore.Interval = 100;
            TimerContatore.Start();
            ButtonFermaContatore.Enabled = true;
            ButtonResetContatore.Enabled = true;
        }

        private void ButtonFermaContatore_Click(object sender, EventArgs e)
        {
            TimerContatore.Stop();
            RadioControlContatore();
            ButtonFermaContatore.Enabled = false;
        }

        private void ButtonResetContatore_Click(object sender, EventArgs e)
        {
            cont = 0;
            LabelContatore.Text = "0";
            Cambio();
            ButtonFermaContatore.Enabled = false;
            ButtonResetContatore.Enabled = false;
        }

        private void ButtonStopSuperCar_Click(object sender, EventArgs e)
        {
            Cambio();
            //metodo che resetta le impostazioni di SuperCar
            if (RadioSuperCarLento.Checked)
                RadioSuperCarLento.Checked = false;
            if (RadioSuperCarMedio.Checked)
                RadioSuperCarMedio.Checked = false;
            if (RadioSuperCarVeloce.Checked)
                RadioSuperCarVeloce.Checked = false;
            puntatore = 0;
            car = 7;
            aumento = true;
            TimerSuperCar.Stop();
            ButtonStopSuperCar.Enabled = false;
        }

        private void RadioSuperCarLento_CheckedChanged(object sender, EventArgs e)
        {
            if(TimerSuperCar.Enabled)
                TimerSuperCar.Stop();
            TimerSuperCar.Interval = 600;
            TimerSuperCar.Start();
            ButtonStopSuperCar.Enabled = true;
        }

        private void RadioSuperCarMedio_CheckedChanged(object sender, EventArgs e)
        {
            if (TimerSuperCar.Enabled)
                TimerSuperCar.Stop();
            TimerSuperCar.Interval = 200;
            TimerSuperCar.Start();
            ButtonStopSuperCar.Enabled = true;
        }

        private void TimerSuperCar_Tick(object sender, EventArgs e)
        {
            if (aumento)
            {
                if (puntatore == 0)
                {
                    p[0].BackColor = rosso;
                    p[1].BackColor = rosso;
                    p[2].BackColor = rosso;
                    puntatore = 2;
                    PortAccess.Output(indirizzo, car);
                    return;
                }
                if (puntatore > 1 && puntatore < 7)
                {
                    p[puntatore - 2].BackColor = grigio;
                    p[puntatore + 1].BackColor = rosso;
                    puntatore++;
                    car <<= 1;
                    PortAccess.Output(indirizzo, car);
                    return;
                }
                if (puntatore == 7)
                {
                    aumento = false;
                    p[7].BackColor = grigio;
                    p[4].BackColor = rosso;
                    puntatore = 5;
                    car = 112;
                    PortAccess.Output(indirizzo, car);
                    return;
                }
            }
            else
            {
                if (puntatore == 1)
                {
                    aumento = true;
                    p[0].BackColor = grigio;
                    p[3].BackColor = rosso;
                    puntatore = 3;
                    car = 14;
                    PortAccess.Output(indirizzo, car);
                    return;
                }
                if (puntatore > 1 && puntatore < 6)
                {
                    p[puntatore + 1].BackColor = grigio;
                    p[puntatore - 2].BackColor = rosso;
                    puntatore--;
                    car >>= 1;
                    PortAccess.Output(indirizzo, car);
                    return;
                }

            }
        }

        private void RadioSuperCarVeloce_CheckedChanged(object sender, EventArgs e)
        {
            if (TimerSuperCar.Enabled)
                TimerSuperCar.Stop();
            TimerSuperCar.Interval = 100;
            TimerSuperCar.Start();
            ButtonStopSuperCar.Enabled = true;
        }

        private void Cambio()
        {
            //metodo che spegne tutti i led e i pannelli
            for (byte i = 0; i <= 7; i++)
                p[i].BackColor = grigio;
            PortAccess.Output(indirizzo, 0);
        } 

        private void RadioControlContatore()
        {
            //metodo che toglie la "spunta" sui radio button del contatore
            if (RadioContatoreLento.Checked)
                RadioContatoreLento.Checked = false;
            if (RadioContatoreMedio.Checked)
                RadioContatoreMedio.Checked = false;
            if (RadioContatoreVeloce.Checked)
                RadioContatoreVeloce.Checked = false;
        }

        private void tabControl1_SelectedIndexChanged(object sender, EventArgs e)
        {
            //evento che si verifica quando l'utente cambia pagina, quindi si deve resettare tutto
            Cambio();
            if (TimerSuperCar.Enabled)
                ButtonStopSuperCar_Click(sender, args);
            if (TimerSemaforo.Enabled)
                TimerSemaforo.Stop();
            if (TimerContatore.Enabled)
                TimerContatore.Stop();
            if (tabControl1.SelectedTab.Text == "Accendi / Spegni")
            {
                PictureBoxOnOff.BackgroundImage = Image.FromFile(@".\off.bmp");
                ButtonOnLed.Enabled = true;
                ButtonOffLed.Enabled = false;
            }
            if (tabControl1.SelectedTab.Text == "Contatore")
            {
                LabelContatore.Text = "0";
                cont = 0;
                RadioControlContatore();
                ButtonFermaContatore.Enabled = false;
                ButtonResetContatore.Enabled = false;
            }
            if (tabControl1.SelectedTab.Text == "Super Car")
                ButtonStopSuperCar_Click(sender, args);

            if (tabControl1.SelectedTab.Text == "Semaforo")
            {
                if (TimerSemaforo.Enabled)
                    TimerSemaforo.Stop();
                if (TabControlSemaforo.SelectedTab.Text == "caso 1")
                {
                    PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                    PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                    for (byte i = 0; i < 3; i++)
                    {
                        c1[i].Enabled = true;
                        c1[i].Checked = false;
                    }
                }
                else
                {
                    PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                    PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                    for (byte i = 0; i < 3; i++)
                    {
                        c2[i].Enabled = true;
                        c2[i].Checked = false;
                    }
                }
                TextBoxSemaforo1.Text = "";
                TextBoxSemaforo2.Text = "";
                PortAccess.Output(indirizzo, 136);
                p[7].BackColor = rosso;
                p[3].BackColor = rosso;
                LabelS1.Text = "0";
                tempo = 0;
                ButtonAvviaSemaforo.Enabled = true;
                ButtonFermaSemaforo.Enabled = false;
            }
            if (tabControl1.SelectedTab.Text == "Motorino")
            {

            }
        }

        private void TimerSemaforo_Tick(object sender, EventArgs e)
        {
            tempo += TimerSemaforo.Interval;
            LabelS1.Text = (tempo / 1000).ToString();
            if (tempo == S1 * 1000) //se il tempo raggiunge i secondi del semaforo1 * 1000 (tempo è in millisecondi)
            {
                if (TabControlSemaforo.SelectedTab.Text == "caso 2")
                {
                    PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_giallo_verde.gif");
                    PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_giallo_rosso.gif");
                    p[6].BackColor = giallo;
                    p[2].BackColor = giallo;
                    PortAccess.Output(indirizzo, 108); //108 : semaforo 1 = verde e giallo, semaforo 2 = rosso e giallo
                }
                else
                {
                    PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_giallo.gif");
                    PortAccess.Output(indirizzo, 72); //72 = semaforo 1 giallo, semaforo 2 rosso
                    p[5].BackColor = grigio;
                    p[6].BackColor = giallo;
                }
            }
            if (tempo == (S1 * 1000) + 5000) //se il tempo raggiunge i 5 secondi (tempo che rimane il giallo) dopo il tempo del semaforo 1
            {
                if (TabControlSemaforo.SelectedTab.Text == "caso 2")
                {
                    p[5].BackColor = grigio;
                    p[2].BackColor = grigio;
                    PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                    PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_verde.gif");
                }
                else
                {
                    PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                    PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_verde.gif");
                }
                p[6].BackColor = grigio;
                p[3].BackColor = grigio;
                p[7].BackColor = rosso;
                p[1].BackColor = verde;
                PortAccess.Output(indirizzo, 130); //130 = semaforo 1 rosso, semaforo 2 verde
            }
            if (tempo == (S1 * 1000) + 5000 + (S2 * 1000)) //se il tempo raggiunge il tempo del semaforo 2 + la somma del precedente
            {
                if (TabControlSemaforo.SelectedTab.Text == "caso 2")
                {
                    PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_giallo_rosso.gif");
                    PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_giallo_verde.gif");
                    p[6].BackColor = giallo;
                    p[2].BackColor = giallo;
                    PortAccess.Output(indirizzo, 198); //198 : semaforo 1 = rosso e giallo, semaforo 2 = verde e giallo
                }
                else
                {
                    PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_giallo.gif");
                    PortAccess.Output(indirizzo, 132); //132 = semaforo 1 rosso, semaforo 2 giallo
                    p[1].BackColor = grigio;
                    p[2].BackColor = giallo;
                }
            }
            if (tempo == (S1 * 1000) + 5000 + (S2 * 1000) + 5000) //se finiscono i 5 secondi del giallo del semaforo 2
            {
                if (TabControlSemaforo.SelectedTab.Text == "caso 2")
                {
                    p[6].BackColor = grigio;
                    p[1].BackColor = grigio;
                    PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_verde.gif");
                    PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                }
                else
                {
                    PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_verde.gif");
                    PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                }
                PortAccess.Output(indirizzo, 40); //40 = semaforo 1 verde, semaforo 2 rosso
                tempo = 0;
                p[2].BackColor = grigio;
                p[7].BackColor = grigio;
                p[5].BackColor = verde;
                p[3].BackColor = rosso;
            }
        }

        private void TabControlSemaforo_SelectedIndexChanged(object sender, EventArgs e)
        {
            //evento che si verifica quando l'utente cambia caso nella pagina del semaforo
            //vengono resettate tutte le impostazioni che riguardano il semaforo
            if (TimerSemaforo.Enabled)
                TimerSemaforo.Stop();
            tempo = 0;
            Cambio();
            p[7].BackColor = rosso;
            p[3].BackColor = rosso;
            PortAccess.Output(indirizzo, 136); //136 = semaforo 1 rosso, semaforo 2 rosso
            ButtonAvviaSemaforo.Enabled = true;
            ButtonFermaSemaforo.Enabled = false;
            if (TabControlSemaforo.SelectedTab.Text == "caso 1")
            {
                //codice che viene eseguito se l'utente sceglie il caso 1 del semaforo
                LabelS1.Text = "0";
                PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                for (byte i = 0; i < 3; i++)
                {
                    c1[i].Enabled = true;
                    c1[i].Checked = false;
                }
            }
            else
            {
                //codice che viene eseguito se l'utente sceglie il caso 2 del semaforo
                PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                for (byte i = 0; i < 3; i++)
                {
                    c2[i].Enabled = true;
                    c2[i].Checked = false;
                }
            }
        }

        private void AvviaSemaforo()
        {
            PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_verde.gif");
            Cambio();
            TimerSemaforo.Start();
            PortAccess.Output(indirizzo, 40);
            p[5].BackColor = verde;
            p[3].BackColor = rosso;
            ButtonFermaSemaforo.Enabled = true;
            ButtonAvviaSemaforo.Enabled = false;

        }

        private void ArrestaSemaforo()
        {
            TimerSemaforo.Stop();
            tempo = 0;
            LabelS1.Text = "0";
            Cambio();
            PortAccess.Output(indirizzo, 136);
            p[7].BackColor = rosso;
            p[3].BackColor = rosso;
            ButtonFermaSemaforo.Enabled = false;
            ButtonAvviaSemaforo.Enabled = true;
            if (TabControlSemaforo.SelectedTab.Text == "caso 1")
            {
                PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                for (byte i = 0; i < 3; i++)
                    c1[i].Enabled = true;
            }
            else
            {
                PictureBoxCaso1Semaforo1.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                PictureBoxCaso1Semaforo2.BackgroundImage = Image.FromFile(@".\semaforo_rosso.gif");
                for (byte i = 0; i < 3; i++)
                    c2[i].Enabled = true;
            }
        }

        private bool AcquisisciTempoSemafori()
        {
            if (TextBoxSemaforo1.Text == "" || TextBoxSemaforo2.Text == "")
            {
                MessageBox.Show("Inserisci il tempo in entrambi i semafori (in secondi)", "Errore nei tempi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }
            try
            {
                S1 = ushort.Parse(TextBoxSemaforo1.Text);
                S2 = ushort.Parse(TextBoxSemaforo2.Text);
            }
            catch
            {
                MessageBox.Show("Inserisci un tempo in entrambi i semafori (in secondi)", "Errore nei tempi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                TextBoxSemaforo1.Clear();
                TextBoxSemaforo2.Clear();
                TextBoxSemaforo1.Focus();
                return false;
            }
            if (S1 <= 0 || S2 <= 0)
            {
                MessageBox.Show("I tempi dei semafori devono essere un numero positivo (in secondi)", "Errore nei tempi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                TextBoxSemaforo1.Focus();
                return false;
            }
            if (TabControlSemaforo.SelectedTab.Text == "caso 1")
            {
                if (RadioButtonCaso1lento.Checked)
                if (S1 <= S2)
                {
                    MessageBox.Show("Il tempo del semaforo 1 deve essere maggiore di quello del semaforo 2", "Errore nei tempi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    TextBoxSemaforo1.Focus();
                    return false;
                }
                if (RadioButtonCaso1veloce.Checked)
                    if (S1 >= S2)
                    {
                        MessageBox.Show("Il tempo del semaforo 1 deve essere minore di quello del semaforo 2", "Errore nei tempi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        TextBoxSemaforo1.Focus();
                        return false;
                    }
                if (RadioButtonCaso1uguale.Checked)
                    if (S1 != S2)
                    {
                        MessageBox.Show("Il tempo del semaforo 1 deve essere uguale a quello del semaforo 2", "Errore nei tempi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        TextBoxSemaforo1.Focus();
                        return false;
                    }
            }
            if (TabControlSemaforo.SelectedTab.Text == "caso 2")
            {
                if (RadioButtonCaso2lento.Checked)
                if (S1 <= S2)
                {
                    MessageBox.Show("Il tempo del semaforo 1 deve essere maggiore di quello del semaforo 2", "Errore nei tempi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    TextBoxSemaforo1.Focus();
                    return false;
                }
                if (RadioButtonCaso2veloce.Checked)
                    if (S1 >= S2)
                    {
                        MessageBox.Show("Il tempo del semaforo 1 deve essere minore di quello del semaforo 2", "Errore nei tempi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        TextBoxSemaforo1.Focus();
                        return false;
                    }
                if (RadioButtonCaso2uguale.Checked)
                    if (S1 != S2)
                    {
                        MessageBox.Show("Il tempo del semaforo 1 deve essere uguale a quello del semaforo 2", "Errore nei tempi", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        TextBoxSemaforo1.Focus();
                        return false;
                    }
            }
            return true;
        }

        private void ButtonLeggiTemperatura_Click(object sender, EventArgs e)
        {
            TimerTemperatura.Start();
        }

        private void TimerTemperatura_Tick(object sender, EventArgs e)
        {
            PortAccess.Output(indirizzo, num);
            r = PortAccess.Input(InMR);
            if ((r & bit) == 0)
            {
                label19.Text = num.ToString();
            }
            num++;
          //  label19.Text = PortAccess.Input(InMR).ToString();
        }

        private void ButtonSpegniTimerTemperatura_Click(object sender, EventArgs e)
        {
            TimerTemperatura.Stop();
            Cambio();
            asd = 1;
            num = 0;
            label19.Text = "0";
        }

        private void ButtonAvviaSemaforo_Click(object sender, EventArgs e)
        {
            if (TabControlSemaforo.SelectedTab.Text == "caso 1")
            {
                if (!RadioButtonCaso1lento.Checked && !RadioButtonCaso1veloce.Checked && !RadioButtonCaso1uguale.Checked)
                {
                    MessageBox.Show("Imposta il funzionamento dei 2 semafori", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                if (AcquisisciTempoSemafori())
                {
                    AvviaSemaforo();
                    for (byte i = 0; i < 3; i++)
                        c1[i].Enabled = false;
                    return;
                }
            }
            else
            {
                if (!RadioButtonCaso2lento.Checked && !RadioButtonCaso2veloce.Checked && !RadioButtonCaso2uguale.Checked)
                {
                    MessageBox.Show("Imposta il funzionamento dei 2 semafori", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                if (AcquisisciTempoSemafori())
                {
                    AvviaSemaforo();
                    for (byte i = 0; i < 3; i++)
                        c1[i].Enabled = false;
                    return;
                }
            }

        }

        private void ButtonFermaSemaforo_Click(object sender, EventArgs e)
        {
            ArrestaSemaforo();
        }

        private void RadioButtonCaso1lento_CheckedChanged(object sender, EventArgs e)
        {
            TextBoxSemaforo1.Focus();
        }

        private void RadioButtonCaso1veloce_CheckedChanged(object sender, EventArgs e)
        {
            TextBoxSemaforo1.Focus();
        }

        private void RadioButtonCaso1uguale_CheckedChanged(object sender, EventArgs e)
        {
            TextBoxSemaforo1.Focus();
        }

        private void RadioButtonCaso2lento_CheckedChanged(object sender, EventArgs e)
        {
            TextBoxSemaforo1.Focus();
        }

        private void RadioButtonCaso2veloce_CheckedChanged(object sender, EventArgs e)
        {
            TextBoxSemaforo1.Focus();
        }

        private void RadioButtonCaso2uguale_CheckedChanged(object sender, EventArgs e)
        {
            TextBoxSemaforo1.Focus();
        }

        private void extra_Click_1(object sender, EventArgs e)
        {
            MessageBox.Show("Programma creato da Parisi Luca classe 4E col c# usando Visual Studio 2008", "Informazioni sul programma", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void PortaParallela_Click_1(object sender, EventArgs e)
        {
            Form2 porta = new Form2();
            porta.Show();
        }

    }
}
