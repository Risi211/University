namespace ProgettoDatabase
{
    partial class Form2
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
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label3 = new System.Windows.Forms.Label();
            this.dateTimePicker1 = new System.Windows.Forms.DateTimePicker();
            this.lbl1 = new System.Windows.Forms.Label();
            this.cmbContainer1 = new System.Windows.Forms.ComboBox();
            this.label2 = new System.Windows.Forms.Label();
            this.cmbCamion = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.cmbCamionista = new System.Windows.Forms.ComboBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.label6 = new System.Windows.Forms.Label();
            this.txtPeso1 = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.cmbSoa1 = new System.Windows.Forms.ComboBox();
            this.lblIva1 = new System.Windows.Forms.Label();
            this.cmbImpianto1 = new System.Windows.Forms.ComboBox();
            this.grpRimorchio = new System.Windows.Forms.GroupBox();
            this.label12 = new System.Windows.Forms.Label();
            this.txtPeso2 = new System.Windows.Forms.TextBox();
            this.label13 = new System.Windows.Forms.Label();
            this.cmbSoa2 = new System.Windows.Forms.ComboBox();
            this.label14 = new System.Windows.Forms.Label();
            this.cmbImpianto2 = new System.Windows.Forms.ComboBox();
            this.label8 = new System.Windows.Forms.Label();
            this.cmbContainer2 = new System.Windows.Forms.ComboBox();
            this.label9 = new System.Windows.Forms.Label();
            this.cmbRimorchio = new System.Windows.Forms.ComboBox();
            this.chkRimorchio = new System.Windows.Forms.CheckBox();
            this.button1 = new System.Windows.Forms.Button();
            this.txtBustaDoc = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.grpRimorchio.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.txtBustaDoc);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.dateTimePicker1);
            this.groupBox1.Controls.Add(this.lbl1);
            this.groupBox1.Controls.Add(this.cmbContainer1);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.cmbCamion);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.cmbCamionista);
            this.groupBox1.Location = new System.Drawing.Point(23, 32);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(938, 152);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(475, 54);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(30, 13);
            this.label3.TabIndex = 7;
            this.label3.Text = "Data";
            // 
            // dateTimePicker1
            // 
            this.dateTimePicker1.Location = new System.Drawing.Point(478, 83);
            this.dateTimePicker1.Name = "dateTimePicker1";
            this.dateTimePicker1.Size = new System.Drawing.Size(200, 20);
            this.dateTimePicker1.TabIndex = 6;
            // 
            // lbl1
            // 
            this.lbl1.AutoSize = true;
            this.lbl1.Location = new System.Drawing.Point(333, 54);
            this.lbl1.Name = "lbl1";
            this.lbl1.Size = new System.Drawing.Size(61, 13);
            this.lbl1.TabIndex = 5;
            this.lbl1.Text = "Container 1";
            // 
            // cmbContainer1
            // 
            this.cmbContainer1.FormattingEnabled = true;
            this.cmbContainer1.Location = new System.Drawing.Point(333, 82);
            this.cmbContainer1.Name = "cmbContainer1";
            this.cmbContainer1.Size = new System.Drawing.Size(121, 21);
            this.cmbContainer1.TabIndex = 4;
            this.cmbContainer1.SelectedIndexChanged += new System.EventHandler(this.cmbContainer1_SelectedIndexChanged);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(190, 54);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(42, 13);
            this.label2.TabIndex = 3;
            this.label2.Text = "Camion";
            // 
            // cmbCamion
            // 
            this.cmbCamion.FormattingEnabled = true;
            this.cmbCamion.Location = new System.Drawing.Point(190, 82);
            this.cmbCamion.Name = "cmbCamion";
            this.cmbCamion.Size = new System.Drawing.Size(121, 21);
            this.cmbCamion.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(37, 54);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(58, 13);
            this.label1.TabIndex = 1;
            this.label1.Text = "Camionista";
            // 
            // cmbCamionista
            // 
            this.cmbCamionista.FormattingEnabled = true;
            this.cmbCamionista.Location = new System.Drawing.Point(37, 82);
            this.cmbCamionista.Name = "cmbCamionista";
            this.cmbCamionista.Size = new System.Drawing.Size(121, 21);
            this.cmbCamionista.TabIndex = 0;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label6);
            this.groupBox2.Controls.Add(this.txtPeso1);
            this.groupBox2.Controls.Add(this.label5);
            this.groupBox2.Controls.Add(this.cmbSoa1);
            this.groupBox2.Controls.Add(this.lblIva1);
            this.groupBox2.Controls.Add(this.cmbImpianto1);
            this.groupBox2.Location = new System.Drawing.Point(23, 209);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(188, 260);
            this.groupBox2.TabIndex = 1;
            this.groupBox2.TabStop = false;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(34, 177);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(31, 13);
            this.label6.TabIndex = 7;
            this.label6.Text = "Peso";
            // 
            // txtPeso1
            // 
            this.txtPeso1.Location = new System.Drawing.Point(37, 204);
            this.txtPeso1.Name = "txtPeso1";
            this.txtPeso1.Size = new System.Drawing.Size(100, 20);
            this.txtPeso1.TabIndex = 6;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(37, 104);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(26, 13);
            this.label5.TabIndex = 5;
            this.label5.Text = "Soa";
            // 
            // cmbSoa1
            // 
            this.cmbSoa1.FormattingEnabled = true;
            this.cmbSoa1.Location = new System.Drawing.Point(16, 129);
            this.cmbSoa1.Name = "cmbSoa1";
            this.cmbSoa1.Size = new System.Drawing.Size(166, 21);
            this.cmbSoa1.TabIndex = 4;
            // 
            // lblIva1
            // 
            this.lblIva1.AutoSize = true;
            this.lblIva1.Location = new System.Drawing.Point(40, 34);
            this.lblIva1.Name = "lblIva1";
            this.lblIva1.Size = new System.Drawing.Size(56, 13);
            this.lblIva1.TabIndex = 3;
            this.lblIva1.Text = "Impianto 1";
            // 
            // cmbImpianto1
            // 
            this.cmbImpianto1.FormattingEnabled = true;
            this.cmbImpianto1.Location = new System.Drawing.Point(40, 59);
            this.cmbImpianto1.Name = "cmbImpianto1";
            this.cmbImpianto1.Size = new System.Drawing.Size(121, 21);
            this.cmbImpianto1.TabIndex = 2;
            this.cmbImpianto1.SelectedIndexChanged += new System.EventHandler(this.cmbImpianto1_SelectedIndexChanged);
            // 
            // grpRimorchio
            // 
            this.grpRimorchio.Controls.Add(this.label12);
            this.grpRimorchio.Controls.Add(this.txtPeso2);
            this.grpRimorchio.Controls.Add(this.label13);
            this.grpRimorchio.Controls.Add(this.cmbSoa2);
            this.grpRimorchio.Controls.Add(this.label14);
            this.grpRimorchio.Controls.Add(this.cmbImpianto2);
            this.grpRimorchio.Controls.Add(this.label8);
            this.grpRimorchio.Controls.Add(this.cmbContainer2);
            this.grpRimorchio.Controls.Add(this.label9);
            this.grpRimorchio.Controls.Add(this.cmbRimorchio);
            this.grpRimorchio.Location = new System.Drawing.Point(244, 268);
            this.grpRimorchio.Name = "grpRimorchio";
            this.grpRimorchio.Size = new System.Drawing.Size(572, 173);
            this.grpRimorchio.TabIndex = 2;
            this.grpRimorchio.TabStop = false;
            this.grpRimorchio.Visible = false;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(451, 109);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(31, 13);
            this.label12.TabIndex = 19;
            this.label12.Text = "Peso";
            // 
            // txtPeso2
            // 
            this.txtPeso2.Location = new System.Drawing.Point(454, 134);
            this.txtPeso2.Name = "txtPeso2";
            this.txtPeso2.Size = new System.Drawing.Size(100, 20);
            this.txtPeso2.TabIndex = 18;
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(233, 109);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(26, 13);
            this.label13.TabIndex = 17;
            this.label13.Text = "Soa";
            // 
            // cmbSoa2
            // 
            this.cmbSoa2.FormattingEnabled = true;
            this.cmbSoa2.Location = new System.Drawing.Point(233, 134);
            this.cmbSoa2.Name = "cmbSoa2";
            this.cmbSoa2.Size = new System.Drawing.Size(121, 21);
            this.cmbSoa2.TabIndex = 16;
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(40, 109);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(56, 13);
            this.label14.TabIndex = 15;
            this.label14.Text = "Impianto 2";
            // 
            // cmbImpianto2
            // 
            this.cmbImpianto2.FormattingEnabled = true;
            this.cmbImpianto2.Location = new System.Drawing.Point(40, 134);
            this.cmbImpianto2.Name = "cmbImpianto2";
            this.cmbImpianto2.Size = new System.Drawing.Size(121, 21);
            this.cmbImpianto2.TabIndex = 14;
            this.cmbImpianto2.SelectedIndexChanged += new System.EventHandler(this.cmbImpianto2_SelectedIndexChanged);
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(233, 25);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(61, 13);
            this.label8.TabIndex = 13;
            this.label8.Text = "Container 2";
            // 
            // cmbContainer2
            // 
            this.cmbContainer2.FormattingEnabled = true;
            this.cmbContainer2.Location = new System.Drawing.Point(233, 53);
            this.cmbContainer2.Name = "cmbContainer2";
            this.cmbContainer2.Size = new System.Drawing.Size(121, 21);
            this.cmbContainer2.TabIndex = 12;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(37, 25);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(54, 13);
            this.label9.TabIndex = 11;
            this.label9.Text = "Rimorchio";
            // 
            // cmbRimorchio
            // 
            this.cmbRimorchio.FormattingEnabled = true;
            this.cmbRimorchio.Location = new System.Drawing.Point(37, 53);
            this.cmbRimorchio.Name = "cmbRimorchio";
            this.cmbRimorchio.Size = new System.Drawing.Size(121, 21);
            this.cmbRimorchio.TabIndex = 10;
            // 
            // chkRimorchio
            // 
            this.chkRimorchio.AutoSize = true;
            this.chkRimorchio.Location = new System.Drawing.Point(259, 228);
            this.chkRimorchio.Name = "chkRimorchio";
            this.chkRimorchio.Size = new System.Drawing.Size(73, 17);
            this.chkRimorchio.TabIndex = 9;
            this.chkRimorchio.Text = "Rimorchio";
            this.chkRimorchio.UseVisualStyleBackColor = true;
            this.chkRimorchio.CheckedChanged += new System.EventHandler(this.chkRimorchio_CheckedChanged);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(867, 413);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 10;
            this.button1.Text = "Inserisci";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // txtBustaDoc
            // 
            this.txtBustaDoc.Location = new System.Drawing.Point(708, 82);
            this.txtBustaDoc.Name = "txtBustaDoc";
            this.txtBustaDoc.Size = new System.Drawing.Size(190, 20);
            this.txtBustaDoc.TabIndex = 8;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(705, 54);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(88, 13);
            this.label4.TabIndex = 9;
            this.label4.Text = "Busta Documenti";
            // 
            // Form2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(968, 458);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.grpRimorchio);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.chkRimorchio);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Name = "Form2";
            this.Text = "Inserisci Viaggio di Scarico";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.grpRimorchio.ResumeLayout(false);
            this.grpRimorchio.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.DateTimePicker dateTimePicker1;
        private System.Windows.Forms.Label lbl1;
        private System.Windows.Forms.ComboBox cmbContainer1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox cmbCamion;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox cmbCamionista;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox txtPeso1;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.ComboBox cmbSoa1;
        private System.Windows.Forms.Label lblIva1;
        private System.Windows.Forms.ComboBox cmbImpianto1;
        private System.Windows.Forms.GroupBox grpRimorchio;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox txtPeso2;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.ComboBox cmbSoa2;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.ComboBox cmbImpianto2;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.ComboBox cmbContainer2;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.ComboBox cmbRimorchio;
        private System.Windows.Forms.CheckBox chkRimorchio;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtBustaDoc;
    }
}