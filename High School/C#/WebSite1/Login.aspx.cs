using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Login : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if ( !IsPostBack )
        {
            // Vengo qui solo la prima volta che la pagina viene creata!!!
            // quindi inizializzo le variabili...
        }

        // Qui ci vengo sempre...

    }

    protected void Button1_Click(object sender, EventArgs e)
    {
        // Qui ci vengo al click del pulsante!!!
        if (edtPassword.Text == "password")
        {
            Session["NomeUtente"] = edtNomeUtente.Text;
            Response.Redirect("HomePage.aspx");
        }
        else
            Session.Clear();
    }

}
