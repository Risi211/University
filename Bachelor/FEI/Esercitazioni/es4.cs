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

  public abstract class TrasformazioneAffine<TImage> : ImageOperation<TImage, TImage>
    where TImage : ImageBase
  {
    protected TrasformazioneAffine()
    {
    }

    protected TrasformazioneAffine(TImage inputImage, double translationX, double translationY, double rotationDegrees, double scaleFactorX, double scaleFactorY, int resultWidth, int resultHeight)
      : base(inputImage)
    {
      TranslationX = translationX;
      TranslationY = translationY;
      RotationDegrees = rotationDegrees;
      ScaleFactorX = scaleFactorX;
      ScaleFactorY = scaleFactorY;
      ResultWidth = resultWidth;
      ResultHeight = resultHeight;
    }

    protected TrasformazioneAffine(TImage inputImage, double translationX, double translationY, double rotationDegrees)
      : this(inputImage, translationX, translationY, rotationDegrees, 1, 1, inputImage.Width, inputImage.Height)
    {
    }

    protected TrasformazioneAffine(TImage inputImage, double scaleFactor)
      : this(inputImage, 0, 0, 0, scaleFactor, scaleFactor, 0, 0)
    {
    }

    private double translationX;
    private double rotationDegrees;
    private double translationY;
    private double cx = 0.5;
    private double cy = 0.5;
    private double scaleFactorX = 1.0;
    private double scaleFactorY = 1.0;

    [AlgorithmParameter]
    [DefaultValue(0)]
    public double TranslationX { get { return translationX; } set { translationX = value; } }

    [AlgorithmParameter]
    [DefaultValue(0)]
    public double TranslationY { get { return translationY; } set { translationY = value; } }

    [AlgorithmParameter]
    [DefaultValue(0)]
    public double RotationDegrees { get { return rotationDegrees; } set { rotationDegrees = value; } }

    [AlgorithmParameter]
    [DefaultValue(0.5)]
    public double RotationCenterX { get { return cx; } set { cx = value; } }

    [AlgorithmParameter]
    [DefaultValue(0.5)]
    public double RotationCenterY { get { return cy; } set { cy = value; } }

    [AlgorithmParameter]
    [DefaultValue(1)]
    public double ScaleFactorX { get { return scaleFactorX; } set { scaleFactorX = value; } }

    [AlgorithmParameter]
    [DefaultValue(1)]
    public double ScaleFactorY { get { return scaleFactorY; } set { scaleFactorY = value; } }

    [AlgorithmParameter]
    [DefaultValue(0)]
    public int ResultWidth { get; set; }

    [AlgorithmParameter]
    [DefaultValue(0)]
    public int ResultHeight { get; set; }
  }

  [AlgorithmInfo("Trasformazione affine (grayscale)", Category = "FEI")]
  public class TrasformazioneAffineGrayscale : TrasformazioneAffine<Image<byte>>
  {
    [AlgorithmParameter]
    [DefaultValue(0)]
    public byte Background { get; set; }

    public TrasformazioneAffineGrayscale(Image<byte> inputImage, double translationX, double translationY, double rotationDegrees, double scaleFactorX, double scaleFactorY, byte background, int resultWidth, int resultHeight)
      : base(inputImage, translationX, translationY, rotationDegrees, scaleFactorX, scaleFactorY, resultWidth, resultHeight)
    {
      Background = background;
    }

    public TrasformazioneAffineGrayscale(Image<byte> inputImage, double translationX, double translationY, double rotationDegrees, byte background)
      : base(inputImage, translationX, translationY, rotationDegrees)
    {
      Background = background;
    }

    public TrasformazioneAffineGrayscale(Image<byte> inputImage, double scaleFactor, byte background)
      : base(inputImage, scaleFactor)
    {
      Background = background;
    }

    public TrasformazioneAffineGrayscale()
    {
    }

    public override void Run()
    {
        Result = new Image<byte>(InputImage.Width, InputImage.Height);
        //converte angolo in radianti
        double rad = (RotationDegrees / 180) * Math.PI;
        //applica il mapping inverso su ogni pixel
        double sin = Math.Sin(rad);
        double cos = Math.Cos(rad);
        for (int y = 0; y < InputImage.Height; y++)
        {
            for (int x = 0; x < InputImage.Width; x++)
            {
                double Xold = 0;
                double Yold = 0;
                InvertedMapping(x,y,TranslationX, TranslationY, sin, cos, ScaleFactorX, ScaleFactorY, ref Xold, ref Yold);
                //adesso ho x old e y old, faccio l'interpolazione di lagrange
                int xL = (int)Xold;
                int yL = (int)Yold;
                Result[y,x] = Lagrange(Xold, Yold);
            }
        }
    }

    public void InvertedMapping(double Xnew, double Ynew, double TranslationX, double TranslationY, double sin, double cos, double scaleX, double scaleY, ref double x, ref double y)
    {
         x = (Xnew - TranslationX) * (1 / scaleX) * cos + (Ynew - TranslationY) * (1 / scaleX) * sin;
         y = (-1) * (Xnew - TranslationX) * (1 / scaleY) * sin + (Ynew - TranslationY) * (1 / scaleY) * cos;
    }

    public byte ImgOrBack(int x, int y)
    {
        if (x >= 0 && x < InputImage.Width && y >= 0 && y < InputImage.Height)
        {
            return InputImage[y, x];
        }
        else
        {
            return Background;
        }
    }

    public byte Lagrange(double Xold, double Yold)
    {
        int xL = (int)Xold;
        int yL = (int)Yold;
        double wa = (xL + 1 - Xold) * (yL + 1 - Yold);
        double wb = (Xold - xL) * (yL + 1 - Yold);
        double wc = (xL + 1 - Xold) * (Yold - yL);
        double wd = (Xold - xL) * (Yold - yL);
        byte IA = ImgOrBack(xL, yL);
        byte IB = ImgOrBack(xL + 1, yL);
        byte IC = ImgOrBack(xL, yL + 1);
        byte ID = ImgOrBack(xL + 1, yL + 1);
        return (IA * wa + IB * wb + IC * wc + ID * wd).RoundAndClipToByte();
    }

  }

  [AlgorithmInfo("Trasformazione affine (rgb)", Category = "FEI")]
  public class TrasformazioneAffineRgb : TrasformazioneAffine<RgbImage<byte>>
  {
    [AlgorithmParameter]
    public RgbPixel<byte> Background { get; set; }

    public TrasformazioneAffineRgb(RgbImage<byte> inputImage, double translationX, double translationY, double rotationDegrees, double scaleFactorX, double scaleFactorY, RgbPixel<byte> background, int resultWidth, int resultHeight)
      : base(inputImage, translationX, translationY, rotationDegrees, scaleFactorX, scaleFactorY, resultWidth, resultHeight)
    {
      Background = background;
    }

    public TrasformazioneAffineRgb(RgbImage<byte> inputImage, double translationX, double translationY, double rotationDegrees, RgbPixel<byte> background)
      : base(inputImage, translationX, translationY, rotationDegrees)
    {
      Background = background;
    }

    public TrasformazioneAffineRgb(RgbImage<byte> inputImage, double scaleFactor, RgbPixel<byte> background)
      : base(inputImage, scaleFactor)
    {
      Background = background;
    }

    public TrasformazioneAffineRgb()
    {
    }

    public override void Run()
    {
        Result = new RgbImage<byte>(InputImage.Width, InputImage.Height);
        //converte angolo in radianti
        double rad = (RotationDegrees / 180) * Math.PI;
        //applica il mapping inverso su ogni pixel
        double sin = Math.Sin(rad);
        double cos = Math.Cos(rad);
        //canale R
        for (int y = 0; y < InputImage.Height; y++)
        {
            for (int x = 0; x < InputImage.Width; x++)
            {
                double Xold = 0;
                double Yold = 0;
                
                InvertedMapping(x, y, TranslationX, TranslationY, sin, cos, ScaleFactorX, ScaleFactorY, ref Xold, ref Yold);
                //adesso ho x old e y old, faccio l'interpolazione di lagrange
                int xL = (int)Xold;
                int yL = (int)Yold;
                Result.RedChannel[y, x] = Lagrange(Xold, Yold,1);
                Result.GreenChannel[y, x] = Lagrange(Xold, Yold, 2);
                Result.BlueChannel[y, x] = Lagrange(Xold, Yold, 3);
            }
        }
    }

    public void InvertedMapping(double Xnew, double Ynew, double TranslationX, double TranslationY, double sin, double cos, double scaleX, double scaleY, ref double x, ref double y)
    {
        x = (Xnew - TranslationX) * (1 / scaleX) * cos + (Ynew - TranslationY) * (1 / scaleX) * sin;
        y = (-1) * (Xnew - TranslationX) * (1 / scaleY) * sin + (Ynew - TranslationY) * (1 / scaleY) * cos;
    }

    public byte ImgOrBack(int x, int y, int canale)
    {
        if (x >= 0 && x < InputImage.Width && y >= 0 && y < InputImage.Height)
        {
            switch (canale)
            {
                case 1:
                    {
                        return InputImage.RedChannel[y, x];
                    }
                case 2:
                    {
                        return InputImage.GreenChannel[y, x];
                    }
                default:
                    {
                        return InputImage.BlueChannel[y,x];
                    }
            }
        }
        else
        {
            switch (canale)
            {
                case 1:
                    {
                        return Background.Red;
                    }
                case 2:
                    {
                        return Background.Green;
                    }
                default:
                    {
                        return Background.Blue;
                    }
            }
        }
    }

    public byte Lagrange(double Xold, double Yold, int canale)
    {
        int xL = (int)Xold;
        int yL = (int)Yold;
        double wa = (xL + 1 - Xold) * (yL + 1 - Yold);
        double wb = (Xold - xL) * (yL + 1 - Yold);
        double wc = (xL + 1 - Xold) * (Yold - yL);
        double wd = (Xold - xL) * (Yold - yL);
        byte IA = ImgOrBack(xL, yL,canale);
        byte IB = ImgOrBack(xL + 1, yL,canale);
        byte IC = ImgOrBack(xL, yL + 1,canale);
        byte ID = ImgOrBack(xL + 1, yL + 1,canale);
        return (IA * wa + IB * wb + IC * wc + ID * wd).RoundAndClipToByte();
    }
  }

}
