using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public interface MediaPlayer
    {
        void Play(string path, string format);
    }
    public interface AdvancedMediaPlayer
    {
        void PlayVlc(string path);
        void PlayMp4(string path);
    }
    public class VlcPlayer : AdvancedMediaPlayer
    {
        public void PlayMp4(string path)
        {
            Console.WriteLine("ERROR: vlc cannot play mp4");
        }

        public void PlayVlc(string path)
        {
            Console.WriteLine("play vlc");
        }
    }
    public class Mp4Player : AdvancedMediaPlayer
    {
        public void PlayMp4(string path)
        {
            Console.WriteLine("play mp4");
        }

        public void PlayVlc(string path)
        {
            Console.WriteLine("ERROR: mp4 cannot play vlc");
        }
    }
    public class MediaAdapter : MediaPlayer
    {
        AdvancedMediaPlayer adv;
        public void Play(string path, string format)
        {
            switch (format)
            {
                case "vlc":
                    {
                        adv = new VlcPlayer();
                        adv.PlayVlc(path);
                        break;
                    }
                case "mp4":
                    {
                        adv = new Mp4Player();
                        adv.PlayMp4(path);
                        break;
                    }
                default:
                    {
                        break;
                    }
            }
        }
    }
    public class SimplePlayer : MediaPlayer
    {       
        public void Play(string path, string format)
        {
            switch (format)
            {
                case "mp3":
                    {
                        Console.WriteLine("mp3 native player");
                        break;
                    }
                default:
                    {
                        MediaAdapter adapter = new MediaAdapter();
                        adapter.Play(path, format);
                        break;
                    }
            }
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            SimplePlayer sp = new SimplePlayer();
            sp.Play("p1", "mp3");
            sp.Play("p2", "vlc");
            sp.Play("p3", "mp4");

            Console.Read();
        }
    }
}
