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


    //TODO: public class EstrazioneContorni ...

    [AlgorithmInfo("Estrazione contorni", Category = "FEI")]
    [BioLab.GUI.Forms.CustomAlgorithmPreviewOutput(typeof(BioLab.GUI.Forms.ContourExtractionViewer))]
    public class EstrazioneContorni : TopologyOperation<List<CityBlockContour>>
    {
        public override void Run()
        {
            Result = new List<CityBlockContour>();
            var pixelstart = new ImageCursor(InputImage);
            var direction = new CityBlockDirection();

            bool[] visitato = new bool[InputImage.PixelCount];

            do
            {

                if (InputImage[pixelstart] == Foreground
                  && !visitato[pixelstart]
                  && InputImage[pixelstart.West] != Foreground)
                {
                        //??? si fa tutto

                                        //aggiungo il primo pixel alla lista dei contorni
                    var c = new CityBlockContour(pixelstart.X, pixelstart.Y);
                    Result.Add(c);
                    visitato[pixelstart] = true;

                    // inseguimento del contorno a partire da (x,y),
                    // aggiungendo le direzioni a c
                    var cursor = new ImageCursor(pixelstart);
                    direction = CityBlockDirection.West;
                    do
                    {
                        for (int j = 1; j <= 4; j++)
                        {
                            direction = CityBlockMetric.GetNextDirection(direction);
                            if (InputImage[cursor.GetAt(direction)] == Foreground)
                            {
                                break;
                            }
                        }
                        cursor.MoveTo(direction);
                        c.Add(direction);
                        visitato[cursor] = true;
                        direction = CityBlockMetric.GetOppositeDirection(direction);
                    }
                    while (cursor != pixelstart); //si ferma quando incontra il pixel iniziale

                }
            } while (pixelstart.MoveNext());


        }
    }

}
