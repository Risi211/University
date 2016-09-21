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
using BioLab.GUI.UserControls;

namespace PRLab.FEI
{

  [AlgorithmInfo("Morfologia: Dilatazione", Category = "FEI")]
  public class Dilatazione : MorphologyOperation
  {
    public Dilatazione(Image<byte> inputImage, Image<byte> structuringElement, byte foreground)
      : base(inputImage, structuringElement, foreground)
    {
    }

    public Dilatazione(Image<byte> inputImage, Image<byte> structuringElement)
      : base(inputImage, structuringElement)
    {
    }

    public Dilatazione()
    {
    }
    public override void Run()
    {
        Result = new Image<byte>(InputImage.Width, InputImage.Height);

        //costruisce l'array degli offset dell'elemento strutturante riflesso
        int[] elementOffsets = MorphologyStructuringElement.CreateOffsets(StructuringElement, InputImage, true);
        //crea un cursore per scorrerel'immagine escludendo i pixel di bordo
        var pixelCursor = new ImageCursor(StructuringElement.Width / 2, StructuringElement.Height / 2, InputImage.Width - 1 - StructuringElement.Width / 2, InputImage.Height - 1 - StructuringElement.Height / 2, InputImage);

        do
        {
            foreach (int offset in elementOffsets)
            {
                if (InputImage[pixelCursor + offset] == Foreground)
                {
                    Result[pixelCursor] = Foreground;
                    break;
                }
            }
        }
        while(pixelCursor.MoveNext());
    }
  }

  [AlgorithmInfo("Morfologia: Erosione", Category = "FEI")]
  public class Erosione : MorphologyOperation
  {
    public Erosione(Image<byte> inputImage, Image<byte> structuringElement, byte foreground)
      : base(inputImage, structuringElement, foreground)
    {
    }

    public Erosione(Image<byte> inputImage, Image<byte> structuringElement)
      : base(inputImage, structuringElement)
    {
    }

    public Erosione()
    {
    }

    public override void Run()
    {
        Result = new Image<byte>(InputImage.Width, InputImage.Height);

        //costruisce l'array degli offset dell'elemento strutturante riflesso
        int[] elementOffsets = MorphologyStructuringElement.CreateOffsets(StructuringElement, InputImage, true);
        //crea un cursore per scorrerel'immagine escludendo i pixel di bordo
        var pixelCursor = new ImageCursor(StructuringElement.Width / 2, StructuringElement.Height / 2, InputImage.Width - 1 - StructuringElement.Width / 2, InputImage.Height - 1 - StructuringElement.Height / 2, InputImage);

        //a differenza della dilatazione controllo se c'è almeno un pixel di background,
        //se è così il pixel nn fa parte dell'output
        bool da_canc = false;
        do
        {
            foreach (int offset in elementOffsets)
            {
                if (InputImage[pixelCursor + offset] != Foreground)
                {
                    //un pixel di background trovato
                    da_canc = true;
                    break;
                }
            }
            if (!da_canc)
            {
                Result[pixelCursor] = Foreground;
            }
            da_canc = false;
        }
        while (pixelCursor.MoveNext());
    }
  }

  [AlgorithmInfo("Morfologia: Apertura", Category = "FEI")]
  public class Apertura : MorphologyOperation
  {
    public Apertura(Image<byte> inputImage, Image<byte> structuringElement, byte foreground)
      : base(inputImage, structuringElement, foreground)
    {
    }

    public Apertura(Image<byte> inputImage, Image<byte> structuringElement)
      : base(inputImage, structuringElement)
    {
    }

    public Apertura()
    {
    }

    public override void Run()
    {
        Erosione er = new Erosione(InputImage, StructuringElement, Foreground);
        Dilatazione d = new Dilatazione(er.Execute(), StructuringElement, Foreground);
        Result = d.Execute();
    }
  }

  [AlgorithmInfo("Morfologia: Chiusura", Category = "FEI")]
  public class Chiusura : MorphologyOperation
  {
    public Chiusura(Image<byte> inputImage, Image<byte> structuringElement, byte foreground)
      : base(inputImage, structuringElement, foreground)
    {
    }

    public Chiusura(Image<byte> inputImage, Image<byte> structuringElement)
      : base(inputImage, structuringElement)
    {
    }

    public Chiusura()
    {
    }

    public override void Run()
    {
        Dilatazione d = new Dilatazione(InputImage, StructuringElement, Foreground);
        Erosione er = new Erosione(d.Execute(), StructuringElement, Foreground);
        Result = er.Execute();
    }
  }

  [AlgorithmInfo("Morfologia: Estrazione Contorno", Category = "FEI")]
  public class EstrazioneContorno : MorphologyOperation
  {
      public EstrazioneContorno(Image<byte> inputImage, Image<byte> structuringElement, byte foreground)
          : base(inputImage, structuringElement, foreground)
      {
      }

      public EstrazioneContorno(Image<byte> inputImage, Image<byte> structuringElement)
          : base(inputImage, structuringElement)
      {
      }

      public EstrazioneContorno()
      {
      }

      public override void Run()
      {
          //F - (F erosione S)
          Result = new Image<byte>(InputImage.Width, InputImage.Height);

          //faccio prima l'erosione
          Erosione er = new Erosione(InputImage, StructuringElement, Foreground);
          Image<byte> tmp = er.Execute();

          //faccio sottrazione
          for (int i = 0; i < Result.PixelCount; i++)
          {
              Result[i] = (byte)(InputImage[i] - tmp[i]);
          }
      }
  }

  [AlgorithmInfo("Morfologia: Hit Or Miss Transform", Category = "FEI")]
  public class HitOrMiss : MorphologyOperation
  {
      [AlgorithmParameter]
      public Image<byte> StructuringElement2 { get; set; }

      public HitOrMiss(Image<byte> inputImage, Image<byte> structuringElement, byte foreground)
          : base(inputImage, structuringElement, foreground)
      {
      }

      public HitOrMiss(Image<byte> inputImage, Image<byte> structuringElement)
          : base(inputImage, structuringElement)
      {
      }

      public HitOrMiss()
      {
      }

      public override void Run()
      {
          if (StructuringElement2 == null)
          {
              MessageBox.Show("NULL");
              return;
          }
          //(F erosione S1) intersezione (F_complementare erosione S2)
          //faccio 2 immagini, poi l'intersezione
          //output
          Result = new Image<byte>(InputImage.Width, InputImage.Height);
          //prima immagine
          Erosione er1 = new Erosione(InputImage, StructuringElement, Foreground);
          Image<byte> tmp1 = er1.Execute();

          //calcolo F_complementare
          Image<byte> complementare = new Image<byte>(InputImage.Width, InputImage.Height);
          for (int i = 0; i < complementare.PixelCount; i++)
          {
              if (InputImage[i] == Foreground)
              {
                  complementare[i] = (byte)(255 - Foreground);
              }
              else
              {
                  complementare[i] = Foreground;
              }
          }

          //seconda immagine
          Erosione er2 = new Erosione(complementare, StructuringElement2, Foreground);
          Image<byte> tmp2 = er2.Execute();

          //trovo l'intersezione fra le 2 immagini
          for (int i = 0; i < Result.PixelCount; i++)
          {
              if (tmp1[i] == Foreground && tmp2[i] == Foreground)
              {
                  Result[i] = Foreground;
              }
          }

      }
  }  
}
