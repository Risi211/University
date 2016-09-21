using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Carrello : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["NomeUtente"] == null)
            Response.Redirect("Login.aspx");

        Response.Write("<hr>Bentornato nel tuo carrello " + Session["NomeUtente"] + "<hr>");

    }
}
