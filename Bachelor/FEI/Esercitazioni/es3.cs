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

  [AlgorithmInfo("Stretch del contrasto", Category = "FEI")]
  public class ContrastStretch : ImageOperation<Image<byte>, Image<byte>>
  {
    private int stretchDiscardPerc;

    [AlgorithmParameter]
    [DefaultValue(0)]
    public int StretchDiscardPercentage
    {
      get { return stretchDiscardPerc; }
      set
      {
        if (value < 0 || value > 100)
          throw new ArgumentOutOfRangeException("Inserire un valore fra 0 and 100.");
        stretchDiscardPerc = value;
      }
    }

    public ContrastStretch()
    {
    }

    public ContrastStretch(Image<byte> inputImage)
      : base(inputImage)
    {
      StretchDiscardPercentage = 0;
    }

    public ContrastStretch(Image<byte> inputImage, int stretchDiscard)
      : base(inputImage)
    {
      StretchDiscardPercentage = stretchDiscard;
    }

    public override void Run()
    {
        Result = new Image<byte>(InputImage.Width, InputImage.Height);
        //calcolo istogramma
        HistogramBuilder hb = new HistogramBuilder(InputImage);
        Histogram h = hb.BuildHistogram();
        //elimino la % scritta in StretchDiscardPerc di pixel in ogni bordo
        //contanto tutti i pixel dell'istogramma
        int da_eliminare = InputImage.PixelCount / 100 * stretchDiscardPerc;
        int min = 0;
        int max = 0;
        Find_MinMax(ref min, ref max, da_eliminare, h);
        //ho trovato max e min, adesso faccio contrast stretching considerando quei
        //valori di max e min
        int diff = max - min;
        if (diff > 0)
        {
            LookupTableTransform<byte> lut = new LookupTableTransform<byte>(InputImage, p => (255 * (p - min) / diff).ClipToByte());
            Result = lut.Execute();
        }
        else
        {
            Result = InputImage.Clone();
        }
    }

    public void Find_MinMax(ref int min, ref int max, int to_delete, Histogram h)
    {
        bool soglia = false;
        int cont = 0; //cont per min
        int i = 0;  //indice per min
        while (!soglia)
        {
            cont += h[i++];
            //controllo se ho superato il numero di pixel da eliminare
            if (cont >= to_delete)
            {
                min = i;
                soglia = true;
            }
        }
        cont = 0;
        i = 255;
        soglia = false;
        while (!soglia)
        {
            cont += h[i--];
            if (cont >= to_delete)
            {
                max = i;
                soglia = true;
            }
        }
    }
  }


  [AlgorithmInfo("Equalizzazione istogramma", Category = "FEI")]
  public class HistogramEqualization : ImageOperation<Image<byte>, Image<byte>>
  {
    public HistogramEqualization()
    {
    }

    public HistogramEqualization(Image<byte> inputImage)
      : base(inputImage)
    {
    }

    public override void Run()
    {
        Result = new Image<byte>(InputImage.Width, InputImage.Height);
        //calcola l'istogramma
        HistogramBuilder hb = new HistogramBuilder(InputImage);
        Histogram h = hb.Execute();
        //ricalcola ogni elemento dell'istogramma come somma dei precedenti
        for (int i = 1; i < 256; i++)
        {
            h[i] += h[i - 1];
        }
        //definisce la funzione di mapping e applica la lut
        LookupTableTransform<byte> lut = new LookupTableTransform<byte>(InputImage, p => (byte)(255 * h[p] / InputImage.PixelCount));
        Result = lut.Execute();
    }
  }

  [AlgorithmInfo("Operazione aritmetica", Category = "FEI")]
  public class ImageArithmetic : ImageOperation<Image<byte>, Image<byte>, Image<byte>>
  {
    [AlgorithmParameter]
    [DefaultValue(defaultOperation)]
    public ImageArithmeticOperation Operation { get; set; }
    const ImageArithmeticOperation defaultOperation = ImageArithmeticOperation.Difference;

    public ImageArithmetic()
    {
    }

    public ImageArithmetic(Image<byte> image1, Image<byte> image2, ImageArithmeticOperation operation)
      : base(image1, image2)
    {
      Operation = operation;
    }

    public ImageArithmetic(Image<byte> image1, Image<byte> image2)
      : this(image1, image2, defaultOperation)
    {
    }
      
    public override void Run()
    {
        Result = new Image<byte>(InputImage1.Width, InputImage1.Height);
        //implemento tutti gli enum
        
        //somma
        if (Operation == ImageArithmeticOperation.Add)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                int tmp = InputImage1[i] + InputImage2[i];
                if (tmp > 255)
                {
                    tmp = 255;
                }
                Result[i] = (byte)tmp;
            }
            return;
        }
        
        //AND binario bit per bit
        if (Operation == ImageArithmeticOperation.And)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result[i] = (byte)(InputImage1[i] & InputImage2[i]);
            }
            return;
        }

        //media fra i 2 pixel
        if (Operation == ImageArithmeticOperation.Average)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result[i] = (byte)((InputImage1[i] + InputImage2[i]) / 2);
            }
            return;
        }

        //darkest, vince il valore minore
        if (Operation == ImageArithmeticOperation.Darkest)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result[i] = (InputImage1[i] < InputImage2[i]) ? InputImage1[i] : InputImage2[i];
            }
            return;
        }
        
        //calcola il modulo della differenza
        if (Operation == ImageArithmeticOperation.Difference)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                int diff = InputImage1[i] - InputImage2[i];
                if (diff < 0)
                {
                    diff *= (-1);
                }
                Result[i] = (byte)diff;
            }
            return;
        }

        //lightest, vince il più chiaro
        if (Operation == ImageArithmeticOperation.Lightest)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result[i] = (InputImage1[i] < InputImage2[i]) ? InputImage2[i] : InputImage1[i];
            }
            return;
        }

        //or binario bit per bit per ogni pixel
        if (Operation == ImageArithmeticOperation.Or)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result[i] = (byte)(InputImage1[i] | InputImage2[i]);
            }
            return;
        }

        //substract, differenza, se fuori dai limiti ci mette 0
        if (Operation == ImageArithmeticOperation.Subtract)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                int diff = InputImage1[i] - InputImage2[i];
                if (diff < 0)
                {
                    diff = 0;
                }
                Result[i] = (byte)diff;
            }
            return;
        }

        //xor binario bit per bit per ogni pixel
        if (Operation == ImageArithmeticOperation.Xor)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result[i] = (byte)(InputImage1[i] ^ InputImage2[i]);
            }
            return;
        }

    }
  }

  [AlgorithmInfo("Operazione aritmetica RGB", Category = "FEI")]
  public class ImageArithmeticRGB : ImageOperation<RgbImage<byte>, RgbImage<byte>, RgbImage<byte>>
  {
      [AlgorithmParameter]
      [DefaultValue(defaultOperation)]
      public ImageArithmeticOperation Operation { get; set; }
      const ImageArithmeticOperation defaultOperation = ImageArithmeticOperation.Difference;

    public ImageArithmeticRGB()
    {
    }

    public ImageArithmeticRGB(RgbImage<byte> image1, RgbImage<byte> image2, ImageArithmeticOperation operation)
      : base(image1, image2)
    {
      Operation = operation;
    }

    public ImageArithmeticRGB(RgbImage<byte> image1, RgbImage<byte> image2)
      : this(image1, image2, defaultOperation)
    {
    }

    public override void Run()
    {
        Result = new RgbImage<byte>(InputImage1.Width, InputImage1.Height);
        //implemento tutti gli enum per ciascun canale RGB

        //somma
        if (Operation == ImageArithmeticOperation.Add)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                int tmp = InputImage1.BlueChannel[i] + InputImage2.BlueChannel[i];
                if (tmp > 255)
                {
                    tmp = 255;
                }
                Result.BlueChannel[i] = (byte)tmp;

                tmp = InputImage1.RedChannel[i] + InputImage2.RedChannel[i];
                if (tmp > 255)
                {
                    tmp = 255;
                }
                Result.RedChannel[i] = (byte)tmp;

                tmp = InputImage1.GreenChannel[i] + InputImage2.GreenChannel[i];
                if (tmp > 255)
                {
                    tmp = 255;
                }
                Result.GreenChannel[i] = (byte)tmp;
            }
            return;
        }

        //AND binario bit per bit
        if (Operation == ImageArithmeticOperation.And)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result.BlueChannel[i] = (byte)(InputImage1.BlueChannel[i] & InputImage2.BlueChannel[i]);
                Result.RedChannel[i] = (byte)(InputImage1.RedChannel[i] & InputImage2.RedChannel[i]);
                Result.GreenChannel[i] = (byte)(InputImage1.GreenChannel[i] & InputImage2.GreenChannel[i]);
            }
            return;
        }

        //media fra i 2 pixel
        if (Operation == ImageArithmeticOperation.Average)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result.BlueChannel[i] = (byte)((InputImage1.BlueChannel[i] + InputImage2.BlueChannel[i]) / 2);
                Result.RedChannel[i] = (byte)((InputImage1.RedChannel[i] + InputImage2.RedChannel[i]) / 2);
                Result.GreenChannel[i] = (byte)((InputImage1.GreenChannel[i] + InputImage2.GreenChannel[i]) / 2);
            }
            return;
        }

        //darkest, vince il valore minore
        if (Operation == ImageArithmeticOperation.Darkest)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result.BlueChannel[i] = (InputImage1.BlueChannel[i] < InputImage2.BlueChannel[i]) ? InputImage1.BlueChannel[i] : InputImage2.BlueChannel[i];
                Result.RedChannel[i] = (InputImage1.RedChannel[i] < InputImage2.RedChannel[i]) ? InputImage1.RedChannel[i] : InputImage2.RedChannel[i];
                Result.GreenChannel[i] = (InputImage1.GreenChannel[i] < InputImage2.GreenChannel[i]) ? InputImage1.GreenChannel[i] : InputImage2.GreenChannel[i];
            }
            return;
        }

        //calcola il modulo della differenza
        if (Operation == ImageArithmeticOperation.Difference)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                int diff = InputImage1.BlueChannel[i] - InputImage2.BlueChannel[i];
                if (diff < 0)
                {
                    diff *= (-1);
                }
                Result.BlueChannel[i] = (byte)diff;

                diff = InputImage1.RedChannel[i] - InputImage2.RedChannel[i];
                if (diff < 0)
                {
                    diff *= (-1);
                }
                Result.RedChannel[i] = (byte)diff;

                diff = InputImage1.GreenChannel[i] - InputImage2.GreenChannel[i];
                if (diff < 0)
                {
                    diff *= (-1);
                }
                Result.GreenChannel[i] = (byte)diff;
            }
            return;
        }

        //lightest, vince il più chiaro
        if (Operation == ImageArithmeticOperation.Lightest)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result.BlueChannel[i] = (InputImage1.BlueChannel[i] < InputImage2.BlueChannel[i]) ? InputImage2.BlueChannel[i] : InputImage1.BlueChannel[i];
                Result.RedChannel[i] = (InputImage1.RedChannel[i] < InputImage2.RedChannel[i]) ? InputImage2.RedChannel[i] : InputImage1.RedChannel[i];
                Result.GreenChannel[i] = (InputImage1.GreenChannel[i] < InputImage2.GreenChannel[i]) ? InputImage2.GreenChannel[i] : InputImage1.GreenChannel[i];
            }
            return;
        }

        //or binario bit per bit per ogni pixel
        if (Operation == ImageArithmeticOperation.Or)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result.BlueChannel[i] = (byte)(InputImage1.BlueChannel[i] | InputImage2.BlueChannel[i]);
                Result.RedChannel[i] = (byte)(InputImage1.RedChannel[i] | InputImage2.RedChannel[i]);
                Result.GreenChannel[i] = (byte)(InputImage1.GreenChannel[i] | InputImage2.GreenChannel[i]);
            }
            return;
        }

        //substract, differenza, se fuori dai limiti ci mette 0
        if (Operation == ImageArithmeticOperation.Subtract)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                int diff = InputImage1.BlueChannel[i] - InputImage2.BlueChannel[i];
                if (diff < 0)
                {
                    diff = 0;
                }
                Result.BlueChannel[i] = (byte)diff;

                diff = InputImage1.RedChannel[i] - InputImage2.RedChannel[i];
                if (diff < 0)
                {
                    diff = 0;
                }
                Result.RedChannel[i] = (byte)diff;

                diff = InputImage1.GreenChannel[i] - InputImage2.GreenChannel[i];
                if (diff < 0)
                {
                    diff = 0;
                }
                Result.GreenChannel[i] = (byte)diff;
            }
            return;
        }

        //xor binario bit per bit per ogni pixel
        if (Operation == ImageArithmeticOperation.Xor)
        {
            for (int i = 0; i < InputImage1.PixelCount; i++)
            {
                Result.BlueChannel[i] = (byte)(InputImage1.BlueChannel[i] ^ InputImage2.BlueChannel[i]);
                Result.RedChannel[i] = (byte)(InputImage1.RedChannel[i] ^ InputImage2.RedChannel[i]);
                Result.GreenChannel[i] = (byte)(InputImage1.GreenChannel[i] ^ InputImage2.GreenChannel[i]);
            }
            return;
        }
    }

  }

}
