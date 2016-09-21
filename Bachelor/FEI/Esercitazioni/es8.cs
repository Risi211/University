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


  [AlgorithmInfo("Etichettatura delle componenti connesse", Category = "FEI")]
  public class EtichettaturaComponentiConnesse : TopologyOperation<ConnectedComponentImage>
  {
    public EtichettaturaComponentiConnesse(Image<byte> inputImage, byte foreground, MetricType metric)
      : base(inputImage, foreground)
    {
      Metric = metric;
    }

    public EtichettaturaComponentiConnesse(Image<byte> inputImage)
      : this(inputImage, 255, MetricType.Chessboard)
    {
    }

    public EtichettaturaComponentiConnesse()
    {
      Metric = MetricType.Chessboard;
    }

    public override void Run()
    {
      Result = new ConnectedComponentImage(InputImage.Width, InputImage.Height, -1);
      var cursor = new ImageCursor(InputImage, 1); // per semplicità ignora i bordi (1 pixel)            
      int[] neighborLabels = new int[Metric == MetricType.CityBlock ? 2 : 4];
      int nextLabel = 0;
      var equivalences = new DisjointSets(InputImage.PixelCount);
      do
      { // prima scansione
        if (InputImage[cursor] == Foreground)
        {
          int labelCount = 0;
          if (Result[cursor.West] >= 0) neighborLabels[labelCount++] = Result[cursor.West];
          if (Result[cursor.North] >= 0) neighborLabels[labelCount++] = Result[cursor.North];
          if (Metric == MetricType.Chessboard)
          {   // anche le diagonali
            if (Result[cursor.Northwest] >= 0) neighborLabels[labelCount++] = Result[cursor.Northwest];
            if (Result[cursor.Northeast] >= 0) neighborLabels[labelCount++] = Result[cursor.Northeast];
          }
          if (labelCount == 0)
          {
            equivalences.MakeSet(nextLabel); // crea un nuovo set
            Result[cursor] = nextLabel++; // le etichette iniziano da 0
          }
          else
          {
            int l = Result[cursor] = neighborLabels[0]; // seleziona la prima
            for (int i = 1; i < labelCount; i++) // equivalenze
              if (neighborLabels[i] != l)
                equivalences.MakeUnion(neighborLabels[i], l); // le rende equivalenti
          }
        }
      } while (cursor.MoveNext());

        //rende le etichette numeri consecutivi
      int totalLabels;
      int[] corresp = equivalences.Renumber(nextLabel, out totalLabels);
        
        //seconda e ultima scansione
      cursor.Restart();
        do
        {
            int l = Result[cursor];
            if (l >= 0)
            {
                Result[cursor] = corresp[l];
            }
        }
        while(cursor.MoveNext());
        Result.ComponentCount = totalLabels;

    }

    [AlgorithmParameter]
    [DefaultValue(MetricType.Chessboard)]
    public MetricType Metric { get; set; }
  }

  [AlgorithmInfo("Informazioni delle componenti connesse", Category = "FEI")]
  public class InformazioniComponentiConnesse : Algorithm
  {
      [AlgorithmInput]
      public Image<byte> InputImage { get; set; }

      [AlgorithmParameter]
      public byte fg { get; set; }

      [AlgorithmParameter]
      public MetricType metrica { get; set; }

      [AlgorithmOutput]
      public int numComponentiConnesse { get; set; }

      [AlgorithmOutput]
      public int areaMin { get; set; }

      [AlgorithmOutput]
      public int areaMax { get; set; }

      [AlgorithmOutput]
      public double areaMedia { get; set; }

      [AlgorithmOutput]
      public double perimetroMedio { get; set; }

      int[] aree;

      public override void Run()
      {
          EtichettaturaComponentiConnesse e1 = new EtichettaturaComponentiConnesse(InputImage,fg, metrica);
          e1.Execute();
          //numero totale compponenti connesse
          numComponentiConnesse = e1.Result.ComponentCount;

          //trovo l'area delle componenti connesse
          aree = new int[numComponentiConnesse]; //vettore di aree delle componenti connesse
          //inizializzo elementi a 0
          for (int i = 0; i < numComponentiConnesse; i++)
          {
              aree[i] = 0;
          }
          //calcolo le aree di tutte le componenti connesse
          ImageCursor cursor = new ImageCursor(e1.Result);
          do
          {
              if (e1.Result[cursor] != -1)
              {
                    //incremento l'area di quella componente connessa che è l'indice del
                  //vettore, quindi è molto semplice
                  aree[e1.Result[cursor]]++;
              }
          }
          while(cursor.MoveNext());

          //trovo area minima, area massima e area media
          areaMin = int.MaxValue;
          areaMax = 0;
          areaMedia = 0;
          for (int i = 0; i < numComponentiConnesse; i++)
          {
              if (aree[i] < areaMin) //minimo
              {
                  areaMin = aree[i];
              }
              if (aree[i] > areaMax) //massimo
              {
                  areaMax = aree[i];
              }
          }
          //calcolo area media
          areaMedia = Media(aree);

          //trovo lunghezza media dei perimetri
          int[] perimetri = new int[numComponentiConnesse];
          for (int i = 0; i < numComponentiConnesse; i++)
          {
              perimetri[i] = 0;
          }

          //stesso cursore di prima, escludo il pixel di bordo per gli indici
          cursor = new ImageCursor(e1.Result, 1);
          do
          {
              if (e1.Result[cursor] != -1)
              {
                  //controllo se è un pixel di contorno, cioè se ha almeno un pixel di background
                  //come vicino. In base alla metrice scelta controllo le direzioni
                  if (e1.Result[cursor.East] == -1 || e1.Result[cursor.West] == -1 || e1.Result[cursor.South] == -1 || e1.Result[cursor.North] == -1)
                  {
                      perimetri[e1.Result[cursor]]++;
                      continue;
                  }
                  
                  //se la metrica è chessboard controllo anche le direzioni diagonali,
                  //per non aumentare 2 volte il perimetro con lo stesso pixel entra qui
                  //se non è entrato nel primo if
                  if (metrica == MetricType.Chessboard)
                  {
                      if (e1.Result[cursor.Northeast] == -1 || e1.Result[cursor.Northwest] == -1 || e1.Result[cursor.Southeast] == -1 || e1.Result[cursor.Southwest] == -1)
                      {
                          perimetri[e1.Result[cursor]]++;
                      }
                  }
              }
          }
          while(cursor.MoveNext());

          //calcolo media dei perimetri
          perimetroMedio = Media(perimetri);

      }

      private double Media(int[] valori)
      {
          int somma = 0;
          for (int i = 0; i < valori.Length; i++)
          {
              somma += valori[i];
          }
          return (double)(somma / valori.Length);
      }

      public int[] getAree()
      {
          return aree;
      }

  }

  [AlgorithmInfo("Eliminazione delle componenti connesse", Category = "FEI")]
  public class EliminaComponentiConnesse : TopologyOperation<Image<byte>>
  {
      [AlgorithmParameter]
      public byte fg { get; set; }

      [AlgorithmParameter]
      public MetricType metrica { get; set; }

      [AlgorithmParameter]
      public int AreaMinima  { get; set; }

      public override void Run()
      {
            EtichettaturaComponentiConnesse e1 = new EtichettaturaComponentiConnesse(InputImage, fg, metrica);
            e1.Execute();
            InformazioniComponentiConnesse i1 = new InformazioniComponentiConnesse();
            i1.InputImage = InputImage;
            i1.fg = fg;
            i1.metrica = metrica;
            i1.Run();
            int[] aree = i1.getAree();

            Result = new Image<byte>(InputImage.Width, InputImage.Height,0);

            //controllo quali aree sono maggiori
            ImageCursor cursor = new ImageCursor(InputImage);
            do
            {
                //controllo le etichette, se il pixel fa parte di una componente connessa
                //con area > areaMinima allora lo mette bianco, altrimenti lo lascia nero
                if (e1.Result[cursor] != -1)
                {
                    if (aree[e1.Result[cursor]] > AreaMinima)
                    {
                        Result[cursor] = fg;
                    }
                }
            }
            while(cursor.MoveNext());
            
      }

  }
  
}
