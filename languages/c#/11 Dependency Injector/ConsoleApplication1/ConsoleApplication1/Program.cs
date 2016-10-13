using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public interface MsgService
    {
        void Send(string msg);
    }
    public class SMSServiceImpl : MsgService
    {
        public void Send(string msg)
        {
            Console.WriteLine("send sms");
        }
    }
    public class EmailServiceImpl : MsgService
    {
        public void Send(string msg)
        {
            Console.WriteLine("send email");
        }
    }
    public interface App
    {
        void SendMsg(string s);
    }
    public class MyApp : App
    {
        MsgService service;
        public MyApp(MsgService service)
        {
            this.service = service;
        }
        public void SendMsg(string s)
        {
            service.Send(s);
        }
    }
    public interface Injector
    {
        App GetApp();
    }
    public class EmailInjector : Injector
    {
        public App GetApp()
        {
            return new MyApp(new EmailServiceImpl());
        }
    }
    public class SmsInjector : Injector
    {
        public App GetApp()
        {
            return new MyApp(new SMSServiceImpl());
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            Injector inj = new EmailInjector();
            App a1 = inj.GetApp();
            a1.SendMsg("a1");
            Injector inj2 = new SmsInjector();
            App a2 = inj2.GetApp();
            a2.SendMsg("a2");

            Console.Read();
        }
    }
}
