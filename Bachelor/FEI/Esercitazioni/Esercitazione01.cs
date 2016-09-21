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

namespace PRLab.FEI
{

  [AlgorithmInfo("Negativo Grayscale", Category = "FEI")]
  public class NegativeImage : ImageOperation<Image<byte>,Image<byte>>
  {
    public override void Run()
    {
      Result = new Image<byte>(InputImage.Width, InputImage.Height);
      // TODO: impostare l'immagine Result come negativo dell'immagine InputImage
      for (int i = 0; i < InputImage.PixelCount; i++)
      {
          Result[i] = (byte)~InputImage[i]; //negativo grayscale
      }
    }
  }

  [AlgorithmInfo("Negativo RGB", Category = "FEI")]
  public class NegativeRgbImage : ImageOperation<RgbImage<byte>, RgbImage<byte>>
  {
    public override void Run()
    {
      Result = new RgbImage<byte>(InputImage.Width, InputImage.Height);
      // TODO: impostare l'immagine Result come negativo dell'immagine InputImage
      for (int i = 0; i < InputImage.PixelCount; i++)
      {
          Result.BlueChannel[i] = (byte)~InputImage.BlueChannel[i];
          Result.RedChannel[i] = (byte)~InputImage.RedChannel[i];
          Result.GreenChannel[i] = (byte)~InputImage.GreenChannel[i];
      }
    }
  }

  [AlgorithmInfo("Modifica Luminosità", Category = "FEI")]
  public class EditLightness : ImageOperation<Image<byte>, Image<byte>>
  {
      [AlgorithmParameter]
      public int Lightness { get; set; } //proprietà (C# 3.0)
      byte[] lut = new byte[256];

      public override void Run()
      {
          Result = new Image<byte>(InputImage.Width, InputImage.Height);
          // TODO: impostare l'immagine Result come negativo dell'immagine InputImage
          
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
                //applico la look up table
                Result[i] = lut[InputImage[i]];
            }
      }
  }

  [AlgorithmInfo("Conversione da grayscale a RGB", Category = "FEI")]
  public class GrayScale_To_RGB : ImageOperation<Image<byte>, RgbImage<byte>>
  {
      public override void Run()
      {
          Result = new RgbImage<byte>(InputImage.Width, InputImage.Height);
          // TODO: impostare l'immagine Result come negativo dell'immagine InputImage

          //uso la look up table già fatta nella classe statica LookupTables, poi converto
          //l'immagine per avere il tipo RgbImage<byte> in output
          LookupTableTransform<RgbPixel<byte>> lt = new LookupTableTransform<RgbPixel<byte>>(InputImage, LookupTables.Spectrum);
          Image<RgbPixel<byte>> tmp = lt.Execute();
          for (int i = 0; i < InputImage.PixelCount; i++)
          {
              Result.BlueChannel[i] = tmp[i].Blue;
              Result.RedChannel[i] = tmp[i].Red;
              Result.GreenChannel[i] = tmp[i].Green;
          }
            
      }
  }
  
}
