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

  [AlgorithmInfo("Convoluzione (da immagine byte a immagine int)", Category = "FEI")]
  [CustomAlgorithmPreviewParameterControl(typeof(SimpleConvolutionParameterControl))]
  public class ConvoluzioneByteInt : Convolution<byte, int, ConvolutionFilter<int>>
  {
    public override void Run()
    {
        Result = new Image<int>(InputImage.Width, InputImage.Height);
        int m2 = Filter.Size / 2;
        //per saltare i bordi
        int y1 = m2;
        int y2 = InputImage.Height - m2 - 1;
        int x1 = m2;
        int x2 = InputImage.Width - m2 - 1;

        int nM = Filter.Size * Filter.Size;
        int[] FOff = new int[nM];
        int[] FVal = new int[nM];
        int maskLen = 0;
        for (int y = 0; y < Filter.Size; y++)
        {
            for (int x = 0; x < Filter.Size; x++)
            {
                FOff[maskLen] = (m2 - y) * InputImage.Width + (m2 - x);
                FVal[maskLen] = Filter[y, x];
                maskLen++;
            }
        }
        int index = m2 * (InputImage.Width + 1); //indice lineare all'interno dell'immagine
        int indexStepRow = m2 * 2; //aggiustamento indice a fine riga (salta bordi)
        for (int y = y1; y <= y2; y++, index += indexStepRow)
        {
            for (int x = x1; x <= x2; x++)
            {
                int val = 0;
                for (int k = 0; k < maskLen; k++)
                {
                    val += InputImage[index + FOff[k]] * FVal[k];
                }
                Result[index++] = val / Filter.Denominator;
                
            }
        }
    }
  }

   [AlgorithmInfo("Smoothing Es 5", Category = "FEI")]
  public class Smoothing : ImageOperation<Image<byte>,Image<int>>
  {
       [AlgorithmParameter]
       public int Size { get; set; }

       int denominator = 0;

       public Smoothing()
       {
 
       }
       public Smoothing(int dimensione_filtro)
       {
           Size = dimensione_filtro;   
       }
    public override void Run()
    {
        //calcolo denominatore = numero elementi matrice quadrata filtro
        denominator = Size * Size;
        //usa la classe di prima impostando il filtro con tutti 1 (smoothing, elimina il rumore)
        ConvoluzioneByteInt performer = new ConvoluzioneByteInt();
        //crea il filtro con la dimensione e il denominatore
        performer.Filter = new ConvolutionFilter<int>(Size, denominator);
        //assegno valori al filtro
        for (int i = 0; i < denominator; i++)
        {
            performer.Filter[i] = 1;
        }
        //se non si fa la result da nullReferenceException
        performer.InputImage = InputImage.Clone();
        performer.Execute();
        Result = performer.Result;
    }
  }

   [AlgorithmInfo("Sharpening (Affinamento) Es 5", Category = "FEI")]
   public class Sharpening : ImageOperation<Image<byte>, Image<byte>>
   {
       [AlgorithmOutput]
       public Image<int> Intermedia { get; set; }

       [AlgorithmParameter]
       public int Size { get; set; }

       [AlgorithmParameter]
       public float K { get; set; }

       public override void Run()
       {
           Result = new Image<byte>(InputImage.Width, InputImage.Height);
           //calcolo denominatore = numero elementi matrice quadrata filtro
           int denominator = Size * Size;
           //usa la classe di prima impostando il filtro con tutti 1 (smoothing, elimina il rumore)
           ConvoluzioneByteInt performer = new ConvoluzioneByteInt();
           //crea il filtro con la dimensione e il denominatore (nel caso dello sharpening è = 1 la dimensione)
           performer.Filter = new ConvolutionFilter<int>(Size, 1);
           //assegno valori al filtro
           for (int i = 0; i < denominator; i++)
           {
               performer.Filter[i] = -1;
           }
           //il valore centrale del filtro è la dimensione - 1 (es size = 3, valore centrale = 8)
           performer.Filter[(denominator + 1) / 2] = denominator - 1;
           //se non si fa la result da nullReferenceException
           performer.InputImage = InputImage.Clone();
           performer.Execute();
           //formula dello sharpening = img_originale + k * img_connvoluzione
           //k è un parametro
           for (int i = 0; i < InputImage.PixelCount; i++)
           {
               Result[i] = ((int)(InputImage[i] + K * performer.Result[i])).ClipToByte();
           }
           //immagine intermedia
           Intermedia = performer.Result.Clone();
       }

   }

}
