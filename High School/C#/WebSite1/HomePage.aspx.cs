using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


public partial class _Default : System.Web.UI.Page 
{
    string DISPLAY_VUOTO = "0";

    enum Stati { NonDefinito, Idle, PrimoOperando, SecondoOperando }
    enum Operatori { NonDefinito, Add, Sub, Mul, Div }

    protected void Page_Load(object sender, EventArgs e)
    {
        //if (Session["NomeUtente"] == null)
        //    Response.Redirect("Login.aspx");

        if (!IsPostBack)
        {
            Response.Write("<hr>Bentornato " + Session["NomeUtente"] + "<hr>");

            Operatore = Operatori.NonDefinito;
            StatoAttuale = Stati.Idle;
        }

    }

    protected void Button1_Click(object sender, EventArgs e)
    {
        Session.Abandon();
        Response.Redirect("Login.aspx");
    }

    Operatori Operatore
    {
        get
        {
            if (Session["operatore"] != null)
                return (Operatori)Session["operatore"];
            else
                return Operatori.NonDefinito;
        }

        set
        {
            Session["operatore"] = value;
        }
    }

    Stati StatoAttuale
    {
        get
        {
            if (Session["stato"] != null)
                return (Stati)Session["stato"];
            else
                return Stati.NonDefinito;
        }

        set
        {
            Session["stato"] = value;
        }
    }


    protected void NumberEventHandler(object sender, EventArgs e)
    {
        Button btn = sender as Button;
        if (btn != null)
            GestioneStato( btn.Text );

    }

    protected void OperatorEventHandler(object sender, EventArgs e)
    {
        Button btn = sender as Button;
        if (btn != null)
        {
            switch (btn.Text)
            {
                case "+":
                    Operatore = Operatori.Add;
                    break;

                case "-":
                    Operatore = Operatori.Sub;
                    break;

                default:
                    Operatore = Operatori.NonDefinito;
                    break;

            }
        }
    }

    private void Visual(string digit)
    {
        if (Display.Text == DISPLAY_VUOTO)
            Display.Text = digit;
        else
            Display.Text += digit;

    }

    private void GestioneStato(string tasto)
    {
        switch (StatoAttuale)
        {
            case Stati.Idle:
                GestioneStatoIdle(tasto);
                break;

            case Stati.PrimoOperando:
                GestioneStatoPrimoOperando(tasto);
                break;
            
            case Stati.SecondoOperando:
                GestioneStatoSecondoOperando(tasto);
                break;

            default:
                StatoAttuale = Stati.Idle;
                break;
        }

        Response.Write("Stato attuale:" + StatoAttuale.ToString());

    }

    void GestioneStatoIdle( string tasto )
    {
        if (IsDigit(tasto))
        {
            Visual(tasto);
            StatoAttuale = Stati.PrimoOperando;
        }
    }

    void GestioneStatoPrimoOperando(string tasto)
    {
        if (IsDigit(tasto))
        {
            Visual(tasto);
            StatoAttuale = Stati.PrimoOperando;
        }

        if (IsOperatore(tasto))
        {
            MemOperatore(tasto);
            MemorizzaPrimoOperando();
            StatoAttuale = Stati.SecondoOperando;
        }
    }
    void MemOperatore(string tasto)
    {
        switch (tasto)
        {
            case "+":
                Operatore = Operatori.Add;
                break;

            case "-":
                Operatore = Operatori.Sub;
                break;
            
            case "*":
                Operatore = Operatori.Mul;
                break;

            case "/":
                Operatore = Operatori.Div;
                break;

            default:
                Operatore = Operatori.NonDefinito;
        }

    }

    void MemorizzaPrimoOperando()
    {

    }

    void GestioneStatoSecondoOperando(string tasto)
    {

    }

    bool IsDigit(string tasto)
    {
        return "0123456789".Contains(tasto);
    }

    bool IsOperatore(string tasto)
    {
        return "+-*/".Contains(tasto);
    }

}
