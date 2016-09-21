namespace Crypto
{
    partial class Form1
    {
        /// <summary>
        /// Variabile di progettazione necessaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Liberare le risorse in uso.
        /// </summary>
        /// <param name="disposing">ha valore true se le risorse gestite devono essere eliminate, false in caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Codice generato da Progettazione Windows Form

        /// <summary>
        /// Metodo necessario per il supporto della finestra di progettazione. Non modificare
        /// il contenuto del metodo con l'editor di codice.
        /// </summary>
        private void InitializeComponent()
        {
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.TextBoxChiave = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.ButtonChiave = new System.Windows.Forms.Button();
            this.ButtonCarica = new System.Windows.Forms.Button();
            this.TextBoxFileCaricato = new System.Windows.Forms.TextBox();
            this.ButtonSalva = new System.Windows.Forms.Button();
            this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
            this.RadioButtonCritta = new System.Windows.Forms.RadioButton();
            this.RadioButtonDecritta = new System.Windows.Forms.RadioButton();
            this.ButtonStart = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.TextBoxSalvaFile = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.LabelStato = new System.Windows.Forms.Label();
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.ButtonStop = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // TextBoxChiave
            // 
            this.TextBoxChiave.Enabled = false;
            this.TextBoxChiave.Location = new System.Drawing.Point(12, 214);
            this.TextBoxChiave.Multiline = true;
            this.TextBoxChiave.Name = "TextBoxChiave";
            this.TextBoxChiave.Size = new System.Drawing.Size(534, 71);
            this.TextBoxChiave.TabIndex = 2;
            this.TextBoxChiave.KeyDown += new System.Windows.Forms.KeyEventHandler(this.TextBoxChiave_KeyDown);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(121, 182);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(288, 16);
            this.label1.TabIndex = 3;
            this.label1.Text = "Inserisci la chiave (almeno 3 caratteri)";
            // 
            // ButtonChiave
            // 
            this.ButtonChiave.Enabled = false;
            this.ButtonChiave.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ButtonChiave.Location = new System.Drawing.Point(192, 291);
            this.ButtonChiave.Name = "ButtonChiave";
            this.ButtonChiave.Size = new System.Drawing.Size(75, 23);
            this.ButtonChiave.TabIndex = 4;
            this.ButtonChiave.Text = "ok";
            this.ButtonChiave.UseVisualStyleBackColor = true;
            this.ButtonChiave.Click += new System.EventHandler(this.ButtonChiave_Click);
            // 
            // ButtonCarica
            // 
            this.ButtonCarica.Enabled = false;
            this.ButtonCarica.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ButtonCarica.Location = new System.Drawing.Point(12, 82);
            this.ButtonCarica.Name = "ButtonCarica";
            this.ButtonCarica.Size = new System.Drawing.Size(132, 23);
            this.ButtonCarica.TabIndex = 0;
            this.ButtonCarica.Text = "carica file";
            this.ButtonCarica.UseVisualStyleBackColor = true;
            this.ButtonCarica.Click += new System.EventHandler(this.ButtonCarica_Click);
            // 
            // TextBoxFileCaricato
            // 
            this.TextBoxFileCaricato.Location = new System.Drawing.Point(12, 129);
            this.TextBoxFileCaricato.Name = "TextBoxFileCaricato";
            this.TextBoxFileCaricato.ReadOnly = true;
            this.TextBoxFileCaricato.Size = new System.Drawing.Size(534, 20);
            this.TextBoxFileCaricato.TabIndex = 1;
            // 
            // ButtonSalva
            // 
            this.ButtonSalva.Enabled = false;
            this.ButtonSalva.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ButtonSalva.Location = new System.Drawing.Point(43, 371);
            this.ButtonSalva.Name = "ButtonSalva";
            this.ButtonSalva.Size = new System.Drawing.Size(75, 23);
            this.ButtonSalva.TabIndex = 5;
            this.ButtonSalva.Text = "salva";
            this.ButtonSalva.UseVisualStyleBackColor = true;
            this.ButtonSalva.Click += new System.EventHandler(this.ButtonSalva_Click);
            // 
            // RadioButtonCritta
            // 
            this.RadioButtonCritta.AutoSize = true;
            this.RadioButtonCritta.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.RadioButtonCritta.Location = new System.Drawing.Point(192, 27);
            this.RadioButtonCritta.Name = "RadioButtonCritta";
            this.RadioButtonCritta.Size = new System.Drawing.Size(64, 20);
            this.RadioButtonCritta.TabIndex = 6;
            this.RadioButtonCritta.TabStop = true;
            this.RadioButtonCritta.Text = "critta";
            this.RadioButtonCritta.UseVisualStyleBackColor = true;
            this.RadioButtonCritta.CheckedChanged += new System.EventHandler(this.RadioButtonCritta_CheckedChanged);
            // 
            // RadioButtonDecritta
            // 
            this.RadioButtonDecritta.AutoSize = true;
            this.RadioButtonDecritta.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.RadioButtonDecritta.Location = new System.Drawing.Point(323, 27);
            this.RadioButtonDecritta.Name = "RadioButtonDecritta";
            this.RadioButtonDecritta.Size = new System.Drawing.Size(82, 20);
            this.RadioButtonDecritta.TabIndex = 7;
            this.RadioButtonDecritta.TabStop = true;
            this.RadioButtonDecritta.Text = "decritta";
            this.RadioButtonDecritta.UseVisualStyleBackColor = true;
            this.RadioButtonDecritta.CheckedChanged += new System.EventHandler(this.RadioButtonDecritta_CheckedChanged);
            // 
            // ButtonStart
            // 
            this.ButtonStart.Enabled = false;
            this.ButtonStart.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ButtonStart.Location = new System.Drawing.Point(43, 476);
            this.ButtonStart.Name = "ButtonStart";
            this.ButtonStart.Size = new System.Drawing.Size(75, 23);
            this.ButtonStart.TabIndex = 8;
            this.ButtonStart.Text = "start";
            this.ButtonStart.UseVisualStyleBackColor = true;
            this.ButtonStart.Click += new System.EventHandler(this.ButtonStart_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(175, 381);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(234, 16);
            this.label2.TabIndex = 9;
            this.label2.Text = "Scegli il percorso di salvataggio";
            // 
            // TextBoxSalvaFile
            // 
            this.TextBoxSalvaFile.Location = new System.Drawing.Point(12, 416);
            this.TextBoxSalvaFile.Name = "TextBoxSalvaFile";
            this.TextBoxSalvaFile.ReadOnly = true;
            this.TextBoxSalvaFile.Size = new System.Drawing.Size(534, 20);
            this.TextBoxSalvaFile.TabIndex = 10;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(175, 483);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(254, 16);
            this.label3.TabIndex = 11;
            this.label3.Text = "Fai iniziare il lavoro al programma";
            // 
            // LabelStato
            // 
            this.LabelStato.AutoSize = true;
            this.LabelStato.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.LabelStato.Location = new System.Drawing.Point(175, 514);
            this.LabelStato.Name = "LabelStato";
            this.LabelStato.Size = new System.Drawing.Size(132, 16);
            this.LabelStato.TabIndex = 12;
            this.LabelStato.Text = "In attesa di input";
            // 
            // progressBar1
            // 
            this.progressBar1.Location = new System.Drawing.Point(12, 553);
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(534, 23);
            this.progressBar1.TabIndex = 13;
            // 
            // backgroundWorker1
            // 
            this.backgroundWorker1.WorkerReportsProgress = true;
            this.backgroundWorker1.WorkerSupportsCancellation = true;
            this.backgroundWorker1.DoWork += new System.ComponentModel.DoWorkEventHandler(this.backgroundWorker1_DoWork);
            this.backgroundWorker1.RunWorkerCompleted += new System.ComponentModel.RunWorkerCompletedEventHandler(this.backgroundWorker1_RunWorkerCompleted);
            this.backgroundWorker1.ProgressChanged += new System.ComponentModel.ProgressChangedEventHandler(this.backgroundWorker1_ProgressChanged);
            // 
            // ButtonStop
            // 
            this.ButtonStop.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))));
            this.ButtonStop.Location = new System.Drawing.Point(43, 514);
            this.ButtonStop.Name = "ButtonStop";
            this.ButtonStop.Size = new System.Drawing.Size(75, 23);
            this.ButtonStop.TabIndex = 14;
            this.ButtonStop.Text = "Stop";
            this.ButtonStop.UseVisualStyleBackColor = true;
            this.ButtonStop.Click += new System.EventHandler(this.ButtonStop_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(558, 588);
            this.Controls.Add(this.ButtonStop);
            this.Controls.Add(this.progressBar1);
            this.Controls.Add(this.LabelStato);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.TextBoxSalvaFile);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.ButtonStart);
            this.Controls.Add(this.RadioButtonDecritta);
            this.Controls.Add(this.RadioButtonCritta);
            this.Controls.Add(this.ButtonSalva);
            this.Controls.Add(this.ButtonChiave);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.TextBoxChiave);
            this.Controls.Add(this.TextBoxFileCaricato);
            this.Controls.Add(this.ButtonCarica);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.MaximizeBox = false;
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "       Crypto :   File";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.TextBox TextBoxChiave;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button ButtonChiave;
        private System.Windows.Forms.Button ButtonCarica;
        private System.Windows.Forms.TextBox TextBoxFileCaricato;
        private System.Windows.Forms.Button ButtonSalva;
        private System.Windows.Forms.SaveFileDialog saveFileDialog1;
        private System.Windows.Forms.RadioButton RadioButtonCritta;
        private System.Windows.Forms.RadioButton RadioButtonDecritta;
        private System.Windows.Forms.Button ButtonStart;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox TextBoxSalvaFile;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label LabelStato;
        private System.Windows.Forms.ProgressBar progressBar1;
        private System.ComponentModel.BackgroundWorker backgroundWorker1;
        private System.Windows.Forms.Button ButtonStop;
    }
}

