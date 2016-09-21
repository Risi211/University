using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Collections;
using System.IO;

namespace Cifratura_di_Vigenère
{
    public partial class Form1 : Form
    {
        //alfabeto di riferimento usato per inizializzare la matrice
        char[] alfabeto = new char[26] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

        List<string> chiave = new List<string>();

        //l'estensione del file di testo (la textbox legge solo i file txt)
        const string ext1 = "txt"; 

        //vettore che contiene tutti i possibili valori della chiave
        string[] vettore = new string[]{"2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26",""};

        //contiene il testo da cifrare/decifrare
        string testo = "";

        //risultato output del programma
        string risultato = "";

        //matrice che contiene i 26 alfabeti
        char[,] matrice = new char[26, 26];

        public Form1()
        {
            InitializeComponent();
            InizializzaMatrice();
        }

        private void textBox2_DragEnter(object sender, DragEventArgs e)
        {
            e.Effect = DragDropEffects.Copy;
        }

        private void textBox2_DragDrop(object sender, DragEventArgs e)
        {
            string[] data = (string[])e.Data.GetData("FileName");
                int indice = data[0].LastIndexOf('.');
                string ext = data[0][indice + 1].ToString() + data[0][indice + 2].ToString() + data[0][indice + 3].ToString();
                //l'estensione da TXT diventa txt
                if (ext.ToLower() != ext1)
                {
                    MessageBox.Show("Inserisci un file di testo .txt", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
                else
                {
                    StreamReader sr = new StreamReader(data[0]);
                    //se c'è già scritto qualcosa nella text box inserisce uno spazio fra i 2 testi
                    //altrimenti sarebbero appiccicati
                    if (textBox2.Text == "")
                    {
                        textBox2.Text = sr.ReadToEnd();
                    }
                    else
                    {
                        textBox2.Text += " " + sr.ReadToEnd();
                    }
                    sr.Close();
                }
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (!Controlla())
            {
                return;
            }

            //converte il testo in minuscolo lasciando invariati i caratteri speciali
            testo = textBox2.Text.ToLowerInvariant();

            //resetta il risultato, così se l'utente fa 2 cifrazioni consecutive l'output vecchio viene cancellato
            risultato = "";

            //incomincia la cifratura / decifratura di Vigenère
            if (radioButtonCifra.Checked)
            {
                Cifra();
            }
            else
            {
                Decifra();
            }

            //salvataggio su file di testo della stringa di output
            if (saveFileDialog1.ShowDialog() == DialogResult.OK)
            {
                StreamWriter sw = new StreamWriter(saveFileDialog1.FileName + ".txt");
                for (int i = 0; i < risultato.Length; i++)
                {
                    sw.Write(risultato[i].ToString());
                }
                //IMPORTANTE: se non si chiude lo stream non rimane scritto niente nel file di testo!!!!
                sw.Close();

            }

        }

        private void textBox3_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                button2.Focus();
                button2.PerformClick();
                //attiva l'evento click del bottone e fa vedere il bottone cliccato, così l'utente capisce quale
                //bottone ha cliccato con Invio
            }
        }

        private void InizializzaMatrice()
        {
            //le seguenti righe di codice riempiono la matrice con i 26 alfabeti e risparmiano 26*26 righe di
            //codice che si sarebbe formato se avessi scritto per ogni casella della matrice la lettera
            //corrispondente, risparmia ore e ore di lavoro :-D

            int x = 0, y = 0, t = 0;
            //ciclo che scannerizza le righe
            for (; x < 26; x++)
            {
                t = x;
                //ciclo che riempie le righe
                for (; y < 26; y++)
                {
                    if (t == 26)
                    {
                        t = 0;
                    }
                    matrice[x, y] = alfabeto[t];
                    t++;
                }
                y = 0;
            }

        }

        private bool Controlla()
        {
            if (textBox2.Text == "")
            {
                MessageBox.Show("Non c'è nessun testo da " + (radioButtonCifra.Checked ? "cifrare" : "decifrare"), "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBox2.Focus();
                return false;
            }
            if (textBox3.Text == "")
            {
                MessageBox.Show("Inserisci una chiave", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBox3.Focus();
                return false;
            }
            //se l'utente ha sbagliato chiave e la reinserisce pigiando tante volte il bottone, il .count della
            //lista aumentava e quindi se dopo inseriva 3 numeri era come se ne avesse messi 3 + quelli di prima
            chiave.Clear();
            string[] chiavetmp = textBox3.Text.Split(';');
            for (int i = 0; i < chiavetmp.Length; i++)
            {
                if (!vettore.Contains<string>(chiavetmp[i]))
                {
                    MessageBox.Show("Inserisci una chiave valida come nell'esempio sottostante", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    textBox3.Clear();
                    textBox3.Focus();
                    return false;
                }
                //se ci sono degli "" non gli aggiunge nella lista della chiave vera, gli "" si creano nel .split
                //se l'utente inserisce nella chiave 2; o 3;;4;;;;6;; tra un ; e l'altro il.split estrae un ""
                if (chiavetmp[i] != "")
                {
                    chiave.Add(chiavetmp[i]);
                }
            }
            //se la chiave ha meno di 4 numeri non è valida
            if (chiave.Count < 4)
            {
                MessageBox.Show("Inserisci almeno 4 numeri nella chiave", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBox3.Clear();
                textBox3.Focus();
                return false;
            }
            //controllo che i numeri della chiave non siano tutti uguali
            List<string> possibilita = new List<string>();
            string tmp = "";
            int g = 0;
            //riempio la lista con tutti i possibili valori della chiave con numeri ripetuti, per esempio se la
            //chiave ha 4 numeri il primo elemento della lista sarà "2222" il secondo "3333" e così via
            for (int k = 0; k < 24; k++)
            {
                tmp = "";
                for (int i = 0; i < chiave.Count; i++)
                {
                    tmp += vettore[g];
                }
                possibilita.Add(tmp);
                    g++;
            }
            string controllo = "";
            //in controllo ci metto la stringa formata da tutti gli elementi della chiave, mi serve per fare il
            //controllo dopo
            for (int i = 0; i < chiave.Count; i++)
            {
                controllo += chiave[i];
            }
            if (possibilita.Contains<string>(controllo))
            {
                MessageBox.Show("Non puoi inserire tutti numeri uguali", "Errore", MessageBoxButtons.OK, MessageBoxIcon.Error);
                textBox3.Clear();
                textBox3.Focus();
                return false;
            }
            return true;

        }

        private void Cifra()
        {
            //puntatore della lista chiave
            int t = 0;
            for (int i = 0; i < testo.Length; i++)
            {
                //se il carattere da cifrare non è una lettera aggiunge il carattere speciale (es spazio)
                //alla stringa risultato
                if(!alfabeto.Contains<char>(testo[i]))
                {
                    risultato += testo[i];
                }
                else
                {
                    //fa ripartire la chiave da capo
                    if (t == chiave.Count)
                    {
                        t = 0;
                    }

                    int posizione = 0;
                    //ciclo che prende il numero di posizione della lettera del testo nell'alfabeto di riferimento
                    //che sarebbe la prima riga della matrice
                    for (int f = 0; f < 26; f++)
                        {
                            if (testo[i] == matrice[0, f])
                            {
                               posizione = f;
                            }
                        }
                    risultato += matrice[int.Parse(chiave[t]) - 1, posizione];
                    t++;
                }
            }
        }

        private void Decifra()
        {
            int t = 0;
            for (int i = 0; i < testo.Length; i++)
            {
                if (!alfabeto.Contains<char>(testo[i]))
                {
                    risultato += testo[i];
                }
                else
                {
                    int posizione = 0;
                    if (t == chiave.Count)
                    {
                        t = 0;
                    }
                    for (int k = 0; k < 26; k++)
                    {
                        if (matrice[int.Parse(chiave[t]) - 1, k] == testo[i])
                        {
                            posizione = k;
                        }
                    }
                    //il riferimento è sempre la prima riga della matrice, in alternativa si poteva prendere
                    //il vettore alfabeto
                    risultato += matrice[0, posizione];
                    t++;
                }
            }
        }

    }
}
