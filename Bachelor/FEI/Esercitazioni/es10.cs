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
using BioLab.PatternRecognition.Localization;
using System.Diagnostics;
namespace PRLab.FEI
{

  [AlgorithmInfo("Correlazione", Category = "FEI")]
  [CustomAlgorithmPreviewOutput(typeof(OuputViewerCorrelazione))]
  public class Correlazione : ImageOperation<Image<byte>, Image<int>>
  {
    
    [AlgorithmInput]
    public Image<byte> Template { get; set; }

    [AlgorithmParameter]
    [DefaultValue(CorrelationMeasure.Zncc)]
    public CorrelationMeasure CorrelationMeasure { get; set; }

    public Correlazione(Image<byte> inputImage, Image<byte> template, CorrelationMeasure correlationMeasure)
      : base(inputImage)
    {
      this.Template = template;
      CorrelationMeasure = correlationMeasure;
    }

    public Correlazione()
    {
      CorrelationMeasure = CorrelationMeasure.Zncc;
    }

    public override void Run()
    {
        Result = new Image<int>(InputImage.Width, InputImage.Height);

        if (Template.Width * Template.Height > int.MaxValue / (255 * 255))
        {
            throw new Exception("Template troppo grande per il calcolo intero a 32 bit");
        }

        //esegue la correlazione
        switch (CorrelationMeasure)
        {

            case BioLab.PatternRecognition.Localization.CorrelationMeasure.CC:
                {
                    for (int y = Template.Height / 2; y <= InputImage.Height - Template.Height / 2 - 1; y++)
                    {
                        for (int x = Template.Width / 2; x <= InputImage.Width - Template.Width / 2 - 1; x++)
                        {
                            Result[y,x] = CC(x, y,0,0);
                        }
                    }
                    break;
                }

            case BioLab.PatternRecognition.Localization.CorrelationMeasure.Ncc:
                {
                    //calcola la norma del template
                    int norma_template = Norma2(0, 0, Template, 0, 0,0);

                    for (int y = Template.Height / 2; y <= InputImage.Height - Template.Height / 2 - 1; y++)
                    {
                        for (int x = Template.Width / 2; x <= InputImage.Width - Template.Width / 2 - 1; x++)
                        {
                            //calcola correlazione di base
                            int val = CC(x,y,0,0);
                            
                            //se la cross correlation ha dato 0, allora anche la norma dell'immagine da 0, quindi scrivo direttamente
                            //0 e non faccio una divisione per 0
                            if (val != 0)
                            {
                                //calcola norma
                                int norma_img = Norma2(x, y, InputImage, Template.Width / 2, Template.Height / 2,0);
                                //calcola numero compreso tra 0 e 1
                                double res = (double)val / (double)(norma_img * norma_template);
                                Result[y, x] = (int)(res * 255);
                            }
                            else
                            {
                                Result[y, x] = 0;
                            }
                        }
                    }
                        break;
                }
            case BioLab.PatternRecognition.Localization.CorrelationMeasure.Zncc:
                {
                    //a differenza della NCC gli passo anche la media del template e dell'immagine

                    //calcola la media del template
                    int media_template = Media(0, 0, Template, 0, 0);

                    //calcola la norma del template
                    int norma_template = Norma2(0, 0, Template, 0, 0,media_template);

                    for (int y = Template.Height / 2; y <= InputImage.Height - Template.Height / 2 - 1; y++)
                    {
                        for (int x = Template.Width / 2; x <= InputImage.Width - Template.Width / 2 - 1; x++)
                        {
                            //calcola media porzione immagine
                            int media_img = Media(x, y, InputImage, Template.Width / 2, Template.Height / 2);

                            //calcola correlazione di base
                            int val = CC(x, y, media_img, media_template);

                            //se la cross correlation ha dato 0, allora anche la norma dell'immagine da 0, quindi scrivo direttamente
                            //0 e non faccio una divisione per 0
                            if (val != 0)
                            {
                                //calcola norma
                                int norma_img = Norma2(x, y, InputImage, Template.Width / 2, Template.Height / 2, media_img);
                                //calcola numero compreso tra -1 e 1
                                double res = (double)val / (double)(norma_img * norma_template);
                                Result[y, x] = (int)(res * 255);
                            }
                            else
                            {
                                Result[y, x] = 0;
                            }
                        }
                    }
                    break;
                }
            default:
                {
                    break;
                }
        }
    }

      //fa correlazione
    private int CC(int x, int y, int mediaImg, int mediaTemplate)
    {
        int output = 0;
        for (int yt = 0; yt < Template.Height; yt++)
        {
            for (int xt = 0; xt < Template.Width; xt++)
            {
                output += (int)(InputImage[y - Template.Height / 2 + yt, x - Template.Width / 2 + xt] - mediaImg) * (Template[yt, xt] - mediaTemplate);
            }
        }
        return output;
    }

    private int Norma2(int x, int y, Image<byte> img, int x_offset, int y_offset, int mediaImg)
    {
        long norma = 0;
        for (int yt = 0; yt < Template.Height; yt++)
        {
            for (int xt = 0; xt < Template.Width; xt++)
            {
                int val = 0;
                val = (int)(img[y - y_offset + yt, x - x_offset + xt] - mediaImg);
                norma += val*val;
            }
        }
        return (int)Math.Sqrt(norma);
    }

      //la media serve allo ZNCC
    private int Media(int x, int y, Image<byte> img, int x_offset, int y_offset)
    {
        int media = 0;
        for (int yt = 0; yt < Template.Height; yt++)
        {
            for (int xt = 0; xt < Template.Width; xt++)
            {
                media += img[y - y_offset + yt, x - x_offset + xt];
            }
        }
        return media / Template.PixelCount;
    }
      

  }
}
