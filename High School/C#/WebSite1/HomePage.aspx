<%@ Page Language="C#" AutoEventWireup="true"  CodeFile="HomePage.aspx.cs" Inherits="_Default" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Home Page</title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
     
     <h1>
     
        <asp:Label 
            runat="server" 
            ID="lblTitolo" 
            Text="Home"
            OnLoad="NumberEventHandler"
            ></asp:Label>
     </h1>
     
    </div>
    <asp:HyperLink ID="HyperLink1" runat="server" NavigateUrl="Login.aspx">Vai al Login</asp:HyperLink>
    <asp:Button ID="ButtonLogOut" runat="server" onclick="Button1_Click" Text="Logout" />
    
    <br />
    <p>
        <asp:Label ID="Display" runat="server" >0</asp:Label>
    </p>
    
    <p>
        <asp:Button ID="Button1" runat="server" Text="1" onclick="NumberEventHandler" />
        <asp:Button ID="Button2" runat="server" Text="2" onclick="NumberEventHandler" />
        <asp:Button ID="Button3" runat="server" Text="3" onclick="NumberEventHandler" />
    </p>
    <p>
        <asp:Button ID="ButtonAdd" runat="server" Text="+" onclick="OperatorEventHandler" />
        <asp:Button ID="ButtonSub" runat="server" Text="-" onclick="OperatorEventHandler"/>
        <asp:Button ID="ButtonEqual" runat="server" Text="=" onclick="OperatorEventHandler"/>
    </p>
    </form>
</body>
</html>
