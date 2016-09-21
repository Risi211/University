<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Login.aspx.cs" Inherits="Login" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Login</title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <asp:Label ID="Label1" runat="server" Text="Nome Utente"></asp:Label>
        <asp:TextBox ID="edtNomeUtente" runat="server"></asp:TextBox>

        <br/>

        <asp:Label ID="Label2" runat="server" Text="Password"></asp:Label>
        <asp:TextBox ID="edtPassword"   runat="server" TextMode="Password"></asp:TextBox>

        <br/>
    
        <asp:Button ID="Button1" runat="server" Text="Button" Width="119px" 
            onclick="Button1_Click" />
    </div>
    </form>
</body>
</html>
