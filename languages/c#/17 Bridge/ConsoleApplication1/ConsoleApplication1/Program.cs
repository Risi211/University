using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace ConsoleApplication1
{
    public class TimeImp
    {
        protected int hr_;
        protected int min_;
        public TimeImp(int hr, int min)
        {
            hr_ = hr;
            min_ = min;
        }
        public virtual void tell()
        {
            Console.WriteLine( "time is " + hr_ + min_ + "\r\n");
        }
    }

    public class CivilianTimeImp : TimeImp {

        protected string whichM_;
        public CivilianTimeImp(int hr, int min, int pm) : base(hr, min)
        {
            if (pm > 0)
                whichM_ = " PM";
            else
                whichM_ = " AM";
        }

		/* virtual */
		public override void tell()
		{
			Console.WriteLine("time is " + hr_ + ":" + min_ + whichM_ + "\r\n");
		}
    
	}

	public class ZuluTimeImp : TimeImp {

        protected string zone_;
        public ZuluTimeImp(int hr, int min, int zone): base(hr, min)
        {
            if (zone == 5)
                zone_ = " Eastern Standard Time";
            else if (zone == 6)
                zone_ = " Central Standard Time";
        }

		/* virtual */
		public override void tell()
		{
				Console.WriteLine("time is " + hr_ + min_ + zone_ + "\r\n");
		}
    
	}

	public class Time
	{
		protected TimeImp imp_;
		public Time() { }
		public Time(int hr, int min)
		{
			imp_ = new TimeImp(hr, min);
		}
		public virtual void tell()
		{
			imp_.tell();
		}    
	}

	public class CivilianTime : Time {
	  
		public CivilianTime(int hr, int min, int pm)
		{
			imp_ = new CivilianTimeImp(hr, min, pm);
		}
	}

	public class ZuluTime : Time {
	  
		public ZuluTime(int hr, int min, int zone)
		{
			imp_ = new ZuluTimeImp(hr, min, zone);
		}
	}


    class Program
    {
        static void Main(string[] args)
        {
            Time[] times = new Time[3];
            times[0] = new Time(14, 30);
            times[1] = new CivilianTime(2, 30, 1);
            times[2] = new ZuluTime(14, 30, 6);
            for (int i = 0; i < 3; i++)
                times[i].tell();

            Console.Read();
        }
    }
}
