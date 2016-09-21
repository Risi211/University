using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using BioLab.Common;
using BioLab.ImageProcessing;
using System.Drawing;
using BioLab.GUI.Forms;
using System.Windows.Forms;
using BioLab.ImageProcessing.Topology;
using BioLab.DataStructures;
using System.ComponentModel;

namespace PRLab.FEI
{

    [AlgorithmInfo("Calcolo Istogramma", Category = "FEI")]
    public class HistogramBuilder : ImageOperation<Image<byte>, Histogram>
    {
        [AlgorithmParameter]
        [DefaultValue(false)]
        public bool Sqrt { get; set; }

        [AlgorithmParameter]
        [DefaultValue(0)]
        public int SmoothWindowSize { get; set; }

        public HistogramBuilder(Image<byte> inputImage)
        {
            InputImage = inputImage;
        }

        public HistogramBuilder(Image<byte> inputImage, bool sqrt, int smoothWindowSize)
        {
            InputImage = inputImage;
            Sqrt = sqrt;
            SmoothWindowSize = smoothWindowSize;
        }

        public override void Run()
        {
            //throw new NotImplementedException();
            Result = BuildHistogram();

            //controllo se sqrt = true
            if (Sqrt)
            {
                //faccio la radice quadrata di ogni valore dell'istogramma
                for (int i = 0; i < 256; i++)
                {
                    Result[i] = (int)Math.Sqrt(Result[i]);
                }
            }
            //controllo se smooth > 0
            if (SmoothWindowSize > 0)
            {
                //media locale intorno 2 * smooth + 1
                for (int i = 0; i < 256; i++)
                {
                    //calcolo media
                    Result[i] = MediaLocale(i);
                }
            }
        }

        public Histogram BuildHistogram()
        {
            Histogram tmp = new Histogram();
            foreach (byte b in InputImage)
            {
                tmp[b]++;
            }
            return tmp;
        }

        private byte MediaLocale(int index)
        {
            int tmp = 0;
            byte cont = 0;
            int partenza = 0;
            int fine = 0;
            //controllo se index < smooth, allora index parte da 0
            if (index < SmoothWindowSize)
            {
                partenza = 0;
                fine = index + SmoothWindowSize;
            }
            else
            {
                //controllo se index > smooth, allora parte da index - smooth e arriva in fondo
                if (index > (255 - SmoothWindowSize))
                {
                    partenza = index - SmoothWindowSize;
                    fine = 255;
                }
                else
                {
                    partenza = index - SmoothWindowSize;
                    fine = index + SmoothWindowSize;
                }
            }
            for (int i = partenza; i <= fine; i++)
            {
                tmp += Result[i];
                cont++;
            }
            byte media = (byte)(tmp / cont);
            return media;
        }
    }

    [AlgorithmInfo("Binarizzazione Immagine My", Category = "FEI")]
    //l'output assume 2 valori, 0 e 255, però si prende sempre un immagine di byte (ogni pixel è cmq un byte)
    public class MyBinarization : ImageOperation<Image<byte>, Image<byte>>
    {
        public MyBinarization()
        {

        }

        [AlgorithmParameter]
        [DefaultValue(0)]
        public int Threshold { get; set; }

        public override void Run()
        {
            Result = new Image<byte>(InputImage.Width, InputImage.Height);
            //controllo la soglia
            for (int i = 0; i < InputImage.PixelCount; i++)
            {
                Result[i] = (InputImage[i] <= Threshold) ? (byte)0 : (byte)255;
            }
        }

    }

    [AlgorithmInfo("Grigio da 3 canali RGB", Category = "FEI")]
    //prende ogni canale e lo converte in grigio
    public class SeparaRGB : Algorithm
    {
        /*//metodo vecchio
         * //quando si fa il new di una proprietà è come se venisse chiamato il set
        private RgbImage<byte> __inputImage;

        [AlgorithmInput]
        public RgbImage<byte> InputImage 
        {
            get { return __inputImage; }
            set { __inputImage = value; }
        }
        */
        [AlgorithmInput]
        public RgbImage<byte> InputImage { get; set; }

        [AlgorithmOutput]
        public Image<byte> GrayFromBlu { get; set; }

        [AlgorithmOutput]
        public Image<byte> GrayFromRed { get; set; }

        [AlgorithmOutput]
        public Image<byte> GrayFromGreen { get; set; }

        public override void Run()
        {
            GrayFromBlu = new Image<byte>(InputImage.Width, InputImage.Height);
            GrayFromRed = new Image<byte>(InputImage.Width, InputImage.Height);
            GrayFromGreen = new Image<byte>(InputImage.Width, InputImage.Height);

            for (int i = 0; i < InputImage.PixelCount; i++)
            {
                GrayFromBlu[i] = InputImage.BlueChannel[i];
                GrayFromGreen[i] = InputImage.GreenChannel[i];
                GrayFromRed[i] = InputImage.RedChannel[i];
            }

            /*//si poteva fare anche con il clone()
              //GrayFromBlu = InputImage.BlueChannel.Clone();
             */
        }
    }

    [AlgorithmInfo("RGB da 3 grigi", Category = "FEI")]
    //prende i 3 grigi e li assegna ad ogni canale
    public class AgglobaGrigi : Algorithm
    {
        [AlgorithmInput]
        public Image<byte> Grigio1 { get; set; }

        [AlgorithmInput]
        public Image<byte> Grigio2 { get; set; }

        [AlgorithmInput]
        public Image<byte> Grigio3 { get; set; }

        [AlgorithmOutput]
        public RgbImage<byte> Result { get; set; }

        public override void Run()
        {
            //controllo se i grigi in input abbiano le stesse dimensioni
            if (Grigio1.Width != Grigio2.Width || Grigio2.Width != Grigio3.Width || Grigio1.Width != Grigio3.Width)
            {
                throw new Exception("la width non è la stessa per le 3 immagini di grigio in input");
            }
            if (Grigio1.Height != Grigio2.Height || Grigio2.Height != Grigio3.Height || Grigio1.Height != Grigio3.Height)
            {
                throw new Exception("la height non è la stessa per le 3 immagini di grigio in input");
            }
            Result = new RgbImage<byte>(Grigio1.Width, Grigio1.Height);
            for (int i = 0; i < Grigio1.PixelCount; i++)
            {
                Result.BlueChannel[i] = Grigio1[i];
                Result.RedChannel[i] = Grigio2[i];
                Result.GreenChannel[i] = Grigio3[i];
            }

        }
    }

    //funziona come per le immagini di grigio, solo che lo fa su ogni canale
    [AlgorithmInfo("Modifica Luminosità RGB", Category = "FEI")]
    public class EditLightnessRGB : ImageOperation<RgbImage<byte>, RgbImage<byte>>
    {
        [AlgorithmParameter]
        public int Lightness { get; set; } //proprietà (C# 3.0)
        byte[] lut = new byte[256];

        public override void Run()
        {
            Result = new RgbImage<byte>(InputImage.Width, InputImage.Height);

            //inizializza look up table
            for (int i = 0; i < 256; i++)
            {
                //per ogni possibile pixel bianco / nero, calcola il risultato
                //così si applica alla matrice direttamente la look up table
                //senza fare più calcoli
                lut[i] = (i + Lightness * 255 / 100).ClipToByte();
            }

            for (int i = 0; i < InputImage.PixelCount; i++)
            {
                //applico la look up table su ogni canale RGB
                Result.BlueChannel[i] = lut[InputImage.BlueChannel[i]];
                Result.RedChannel[i] = lut[InputImage.RedChannel[i]];
                Result.GreenChannel[i] = lut[InputImage.GreenChannel[i]];
            }
        }

    }

    //converte un immagine RGB nei 3 canali HSL
    [AlgorithmInfo("Conversione da RGB a HSL", Category = "FEI")]
    public class RgbToHsl : Algorithm
    {
        [AlgorithmInput]
        public RgbImage<byte> InputImage { get; set; }

        [AlgorithmOutput]
        public Image<byte> Hue { get; set; }

        [AlgorithmOutput]
        public Image<byte> Saturation { get; set; }

        [AlgorithmOutput]
        public Image<byte> Lightness { get; set; }

        public override void Run()
        {
            Hue = new Image<byte>(InputImage.Width, InputImage.Height);
            Saturation = new Image<byte>(InputImage.Width, InputImage.Height);
            Lightness = new Image<byte>(InputImage.Width, InputImage.Height);

            for (int i = 0; i < InputImage.PixelCount; i++)
            {
                //ImageUtilities.ToByteHslImage(InputImage);
                byte tmph = 0;
                byte tmps = 0;
                byte tmpl = 0;
                ImageUtilities.RgbToHsl(InputImage.RedChannel[i], InputImage.GreenChannel[i], InputImage.BlueChannel[i], out tmph, out tmps, out tmpl);
                Hue[i] = tmph;
                Saturation[i] = tmps;
                Lightness[i] = tmpl;
            }
        }

    }

    //prende in input 3 immagini grigie, le considera come canali HSL e produce RGB
    [AlgorithmInfo("Conversione da 3 grigi HSL a RGB", Category = "FEI")]
    public class GrayHSLToRGB : Algorithm
    {
        [AlgorithmInput]
        public Image<byte> Hue { get; set; }

        [AlgorithmInput]
        public Image<byte> Saturation { get; set; }

        [AlgorithmInput]
        public Image<byte> Lightness { get; set; }

        [AlgorithmOutput]
        public RgbImage<byte> Result { get; set; }

        public override void Run()
        {
            //controllo che le 3 immagini in input abbiano le stesse dimensioni
            if (Hue.Width != Saturation.Width || Saturation.Width != Lightness.Width || Hue.Width != Lightness.Width)
            {
                throw new Exception("immagini di dimensioni diverse");
            }
            if (Hue.Height != Saturation.Height || Saturation.Height != Lightness.Height || Hue.Height != Lightness.Height)
            {
                throw new Exception("immagini di dimensioni diverse");
            }
            Result = new RgbImage<byte>(Hue.Width, Hue.Height);
            for (int i = 0; i < Hue.PixelCount; i++)
            {
                byte tmpR = 0;
                byte tmpG = 0;
                byte tmpB = 0;
                //conversione pixel per pixel
                HslToRgb(Hue[i], Saturation[i], Lightness[i], out tmpR, out tmpG, out tmpB);
                Result.RedChannel[i] = tmpR;
                Result.GreenChannel[i] = tmpG;
                Result.BlueChannel[i] = tmpB;
            }
        }

        public void HslToRgb(byte H, byte S, byte L, out byte R, out byte G, out byte B)
        {
            //converte H nell'intervallo [0, 2*pigreco]
            double dH = ((double)H / 255) * (Math.PI * 2);
            //converte S ed L nell'intervallo [0,1]
            double dS = (double)S / 255;
            double dL = (double)L / 255;
            //controllo caso acromatico
            if (dS == 0)
            {
                //R,G,B passano da [0,1] a [0,255]
                R = L;
                G = L;
                B = L;
                return;
            }
            else
            {
                //prendo t2
                double t2 = getT2(dL, dS);
                //calcolo t1
                double t1 = 2 * dL - t2;
                //calcolo H1
                double H1 = dH / (Math.PI * 2);
                //calcolo tR
                double tR = getTR(H1);
                //calcolo tG
                double tG = H1;
                //calcolo tB
                double tB = getTB(H1);
                //calcolo R,G,B nell'intervallo [0,1]
                double tmpR = getC(t1, t2, tR);
                double tmpG = getC(t1, t2, tG);
                double tmpB = getC(t1, t2, tB);
                //converto R,G,B dall'intervallo [0,1] a [0,255]
                R = (byte)(tmpR * 255);
                G = (byte)(tmpG * 255);
                B = (byte)(tmpB * 255);
            }
        }

        private double getT2(double L, double S)
        {
            //calcola t2
            if (L < 0.5)
            {
                return L * (1 + S);
            }
            else
            {
                return L + S - L * S;
            }
        }

        private double getTR(double H1)
        {
            double tr = H1 + ((double)1 / 3);
            if (tr > 1)
            {
                return tr - 1;
            }
            return tr;
        }

        private double getTB(double H1)
        {
            double tb = H1 - ((double)1 / 3);
            if (tb < 0)
            {
                return tb + 1;
            }
            return tb;
        }

        private double getC(double t1, double t2, double tc)
        {
            if (tc < ((double)1 / 6))
            {
                return t1 + 6 * (t2 - t1) * tc;
            }
            if (tc >= ((double)1 / 6) && tc < 0.5)
            {
                return t2;
            }
            if (tc >= 0.5 && tc < ((double)2 / 3))
            {
                return t1 + 6 * (t2 - t1) * (((double)2 / 3) - tc);
            }
            return t1;
        }
    }

    //prende in input 3 immagini grigie, le considera come canali HSL e produce RGB
    //il colore viene scelto da 2 parametri, saturation e hue, nell'intervallo [0,255]
    //la luminosità viene letta dal valore del pixel nell'immagine grigia
    [AlgorithmInfo("Da Grigio a RGB sfumatura stesso colore", Category = "FEI")]
    public class GrayToRGB_OneColor : Algorithm
    {
        [AlgorithmInput]
        public Image<byte> InputImage { get; set; }

        [AlgorithmOutput]
        public RgbImage<byte> Result { get; set; }

        [AlgorithmParameter]
        public int Hue { get; set; }

        [AlgorithmParameter]
        public int Saturation { get; set; }

        public override void Run()
        {
            Result = new RgbImage<byte>(InputImage.Width, InputImage.Height);
            GrayHSLToRGB performer = new GrayHSLToRGB();
            for (int i = 0; i < InputImage.PixelCount; i++)
            {
                byte R = 0;
                byte G = 0;
                byte B = 0;
                performer.HslToRgb((byte)Hue, (byte)Saturation, InputImage[i], out R, out G, out B);
                Result.RedChannel[i] = R;
                Result.GreenChannel[i] = G;
                Result.BlueChannel[i] = B;
            }
        }

    }

}
