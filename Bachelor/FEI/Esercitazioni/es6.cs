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

  //TODO: public class CalcolaModuloGradiente ...
    [AlgorithmInfo("Operatori Prewitt", Category = "FEI")]
    public class CalcolaModuloGradiente : ImageOperation<Image<byte>, Image<int>>
    {
        /*
        [AlgorithmOutput]
        public Image<int> X { get; set; }

        [AlgorithmOutput]
        public Image<int> Y { get; set; }
        */

        public CalcolaModuloGradiente()
        {

        }

        public override void Run()
        {
            Result = new Image<int>(InputImage.Width, InputImage.Height);
            //filtro Delta X
            ConvoluzioneByteInt dX = new ConvoluzioneByteInt();
            dX.Filter = new ConvolutionFilter<int>(3, 3);
            dX.Filter[0, 0] = 1;
            dX.Filter[1, 0] = 1;
            dX.Filter[2, 0] = 1;
            dX.Filter[0, 1] = 0;
            dX.Filter[1, 1] = 0;
            dX.Filter[2, 1] = 0;
            dX.Filter[0, 2] = -1;
            dX.Filter[1, 2] = -1;
            dX.Filter[2, 2] = -1;
            //Filtro Delta Y
            ConvoluzioneByteInt dY = new ConvoluzioneByteInt();
            dY.Filter = new ConvolutionFilter<int>(3,3);
            dY.Filter[0, 0] = 1;
            dY.Filter[0, 1] = 1;
            dY.Filter[0, 2] = 1;
            dY.Filter[1, 0] = 0;
            dY.Filter[1, 1] = 0;
            dY.Filter[1, 2] = 0;
            dY.Filter[2, 0] = -1;
            dY.Filter[2, 1] = -1;
            dY.Filter[2, 2] = -1;
            dX.InputImage = InputImage;
            dY.InputImage = InputImage;
            dX.Execute();
            dY.Execute();
            /*
            X = dX.Result.Clone();
            Y = dY.Result.Clone();
            */
            AlgorithmIntermediateResultEventArgs x1 = new AlgorithmIntermediateResultEventArgs(dX.Result, "Delta X");
            AlgorithmIntermediateResultEventArgs y1 = new AlgorithmIntermediateResultEventArgs(dY.Result, "Delta Y");
            OnIntermediateResult(x1);
            OnIntermediateResult(y1);
            //calcola modulo gradiente per ogni pixel
            for (int i = 0; i < InputImage.PixelCount; i++)
            {
                Result[i] = (int)Math.Sqrt(dX.Result[i] * dX.Result[i] + dY.Result[i] * dY.Result[i]);
            }
        }
        
    }


  [AlgorithmInfo("Trasformata distanza", Category = "FEI")]
  public class TrasformataDistanza : TopologyOperation<Image<int>>
  {
    public TrasformataDistanza()
    {
    }

    public TrasformataDistanza(Image<byte> inputImage, byte foreground, MetricType metric)
      : base(inputImage, foreground)
    {
      Metric = metric;
    }

    public override void Run()
    {
        Result = new Image<int>(InputImage.Width, InputImage.Height);
        var r = Result;
        var cursor = new ImageCursor(r,1);
        
        if (Metric == MetricType.CityBlock) //d4
        {
            do //scansione diretta
            {
                if (InputImage[cursor] == Foreground)
                {
                    r[cursor] = Min(r[cursor.West], r[cursor.North]) + 1;
                }
            }
            while (cursor.MoveNext());

            do //scansione inversa
            {
                if (InputImage[cursor] == Foreground)
                {
                    r[cursor] = Min(r[cursor.East] + 1, r[cursor.South] + 1, r[cursor]);
                }
            }
            while (cursor.MovePrevious());
        }
        else //d8
        {
            do //scansione diretta
            {
                if (InputImage[cursor] == Foreground)
                {
                    r[cursor] = Min(r[cursor.West], r[cursor.North], r[cursor.Northeast], r[cursor.Northwest]) + 1;
                }
            }
            while (cursor.MoveNext());

            do //scansione inversa
            {
                if (InputImage[cursor] == Foreground)
                {
                    r[cursor] = Min(r[cursor.East] + 1, r[cursor.Southwest] + 1, r[cursor.South] + 1, r[cursor.Southeast] + 1, r[cursor]);
                }
            }
            while (cursor.MovePrevious());
        }
    }

    private int Min(int a, int b)
    {
        if (a < b)
        {
            return a;
        }
        return b;
    }

    private int Min(int a, int b, int c)
    {
        return Math.Min(a,Math.Min(b,c));
    }

    private int Min(int a, int b, int c, int d)
    {
        return Math.Min(a, Math.Min(b, Math.Min(c, d)));
    }

    private int Min(int a, int b, int c, int d, int e)
    {
        return Math.Min(a,Math.Min(b,Math.Min(c,Math.Min(d,e))));
    }

    [AlgorithmParameter]
    public MetricType Metric { get; set; }
  }


}
