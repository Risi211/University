namespace Cifratura_di_Vigenère
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.button2 = new System.Windows.Forms.Button();
            this.textBox3 = new System.Windows.Forms.TextBox();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.radioButtonDecifra = new System.Windows.Forms.RadioButton();
            this.radioButtonCifra = new System.Windows.Forms.RadioButton();
            this.label1 = new System.Windows.Forms.Label();
            this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // button2
            // 
            this.button2.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.button2.Location = new System.Drawing.Point(70, 568);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(106, 28);
            this.button2.TabIndex = 21;
            this.button2.Text = "Salva File";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // textBox3
            // 
            this.textBox3.Location = new System.Drawing.Point(70, 475);
            this.textBox3.Name = "textBox3";
            this.textBox3.Size = new System.Drawing.Size(616, 20);
            this.textBox3.TabIndex = 20;
            this.textBox3.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textBox3_KeyDown);
            // 
            // textBox2
            // 
            this.textBox2.AllowDrop = true;
            this.textBox2.Font = new System.Drawing.Font("Verdana", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBox2.Location = new System.Drawing.Point(70, 199);
            this.textBox2.Multiline = true;
            this.textBox2.Name = "textBox2";
            this.textBox2.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.textBox2.Size = new System.Drawing.Size(616, 208);
            this.textBox2.TabIndex = 17;
            this.textBox2.DragDrop += new System.Windows.Forms.DragEventHandler(this.textBox2_DragDrop);
            this.textBox2.DragEnter += new System.Windows.Forms.DragEventHandler(this.textBox2_DragEnter);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Verdana", 14.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(66, 119);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(540, 23);
            this.label5.TabIndex = 16;
            this.label5.Text = "scrivendo il testo o trascinando i file di testo .txt";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(24, 517);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(696, 16);
            this.label6.TabIndex = 19;
            this.label6.Text = "solo numeri da 2 a 26 separati da un ; e senza spazi, esempio:        12;15;9;3;7" +
                "       (minimo 4 numeri)";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Verdana", 14.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(66, 436);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(180, 23);
            this.label7.TabIndex = 18;
            this.label7.Text = "Inserisci chiave";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.radioButtonDecifra);
            this.groupBox1.Controls.Add(this.radioButtonCifra);
            this.groupBox1.Location = new System.Drawing.Point(70, 1);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(439, 92);
            this.groupBox1.TabIndex = 22;
            this.groupBox1.TabStop = false;
            // 
            // radioButtonDecifra
            // 
            this.radioButtonDecifra.AutoSize = true;
            this.radioButtonDecifra.Font = new System.Drawing.Font("Verdana", 14.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.radioButtonDecifra.Location = new System.Drawing.Point(219, 33);
            this.radioButtonDecifra.Name = "radioButtonDecifra";
            this.radioButtonDecifra.Size = new System.Drawing.Size(150, 27);
            this.radioButtonDecifra.TabIndex = 1;
            this.radioButtonDecifra.Text = "Decifratura";
            this.radioButtonDecifra.UseVisualStyleBackColor = true;
            // 
            // radioButtonCifra
            // 
            this.radioButtonCifra.AutoSize = true;
            this.radioButtonCifra.Checked = true;
            this.radioButtonCifra.Font = new System.Drawing.Font("Verdana", 14.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.radioButtonCifra.Location = new System.Drawing.Point(43, 33);
            this.radioButtonCifra.Name = "radioButtonCifra";
            this.radioButtonCifra.Size = new System.Drawing.Size(124, 27);
            this.radioButtonCifra.TabIndex = 0;
            this.radioButtonCifra.TabStop = true;
            this.radioButtonCifra.Text = "Cifratura";
            this.radioButtonCifra.UseVisualStyleBackColor = true;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Verdana", 14.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(66, 156);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(277, 23);
            this.label1.TabIndex = 23;
            this.label1.Text = "nella casella sottostante";
            // 
            // saveFileDialog1
            // 
            this.saveFileDialog1.Filter = "File di testo.txt|*.txt*";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(745, 613);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.textBox3);
            this.Controls.Add(this.textBox2);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label7);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "                Cifratura di Vigenère";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.TextBox textBox3;
        private System.Windows.Forms.TextBox textBox2;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RadioButton radioButtonDecifra;
        private System.Windows.Forms.RadioButton radioButtonCifra;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.SaveFileDialog saveFileDialog1;


    }
}

