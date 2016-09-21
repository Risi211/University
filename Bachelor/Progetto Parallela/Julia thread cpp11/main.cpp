/* 
 * File:   main.cpp
 * Author: luca parisi
 *
 * Created on May 28, 2014, 10:56 AM
 */

#include <cstdlib>
#include <time.h>
#include <thread>
#include <mutex>
  
using namespace std;
using namespace this_thread;

//Easy BMP, per creare l'immagine in BMP
#include "lib/EasyBMP.h"
#include "lib/EasyBMP_DataStructures.h"
#include "lib/EasyBMP_VariousBMPutilities.h"
#include "lib/EasyBMP_BMP.h"

using namespace std;

//DEFAULT SETTINGS: 
//variabili globali che possono essere modificate quando si lancia
//il programma, passandogli i parametri

//real and imaginary part of the constant c, determinate shape of the Julia Set
//pick some values for the constant c, this determines the shape of the Julia Set
double c_reale = -0.7;
double c_yota = 0.27015;
//scostamento rispetto al centro
double moveX = 0;
double moveY = 0;
//zoom dell'immagine
double zoom = 1;
//nome del file bmp di output
string nome_file ("es.bmp");
//after how much iterations the function should stop
//per vedere se la funzione diverge si controlla se in un dato numero di iterazioni
//la distanza rispetto al centro non supera 2
int max_iterations = 300;
//width e height dell'immagine
int width = 400;
int height = 300;
//numero di thread generati
int num_threads = 4; //default
//(EasyBMP.h) oggetto che crea l'immagine BMP, è condiviso da tutti i thread in modalità
//shared, si usa una mutex
BMP img;
//mutex per controllare l'accesso all'oggetto bmp tra tutti i thread
mutex mtx;           // mutex for critical section

void CheckArguments(int, char**);
////HSL TO RGB per colorare i pixel che non appartengono all'insieme di julia in
//base al numero di iterazioni richieste per farlo uscire dal cerchio di raggio 2
static inline unsigned int RGB(unsigned char ,unsigned char ,unsigned char );
unsigned int HSLtoRGB(const unsigned int& , const unsigned int& , const unsigned int& );
static void HSLtoRGB_Subfunction(unsigned int& , const float& , const float& , const float& );

void JuliaThread(int, int);

int main(int argc, char** argv) {

    //controlla gli argomenti passati come parametri all'avvio del programma, 
    //se presenti, vengono modificati i valori di default delle variabili globali
    CheckArguments(argc, argv);           
    
    img.SetSize(width, height);

    //calcola tempo di esecuzione
    clock_t start,end;
    double tempo;
    start=clock();    
    
    //la zona parallela si apre nel for più esterno perchè altrimenti 
    //verrebbero creati ogni volta nel ciclo interno n^2 threads
    //più tempo per l'overhead dato dalla creazione e distruzione di thread    
    
    const int num_thread_const = num_threads;
    //creo vettore con i thread
    thread ts [num_thread_const];

    printf("Settati %d threads\r\n\r\n",num_thread_const);

    //fa partire il numero di thread selezionati 
    //dividendo il lavoro staticamente, prima di iniziare l'elaborazione
    //Per ogni thread, viene assegnata la stessa funzione,
    //cambiano solamente i valori di start e end del ciclo for
    for(int i = 0; i < num_thread_const; i++)
    {
        if(i == 0)
        {
            ts[i] = thread(JuliaThread, 0, width / num_thread_const);
            continue;
        }
        if(i == num_thread_const - 1)
        {
            ts[i] = thread(JuliaThread, (num_thread_const-1) * width / num_thread_const,width);        
            break;
        }    
        ts[i] = thread(JuliaThread, i * width / num_thread_const, (i + 1) * width / num_thread_const);
    }

    //aspetta che tutti i thread abbiano terminato il lavoro:
    for(int i = 0; i < num_thread_const; i++)
    {
        ts[i].join();
    }    

    end=clock();
    tempo=((double)(end-start))/CLOCKS_PER_SEC;

    printf("tempo calcolo parallelo: %f secondi\r\n\r\n",tempo);

    //salva immagine su file
    printf("Scritture dell'immagine su file bitmap...\r\n\r\n");
    img.WriteToFile(nome_file.c_str());
    printf("Done\r\n\r\n");
    return 0;
}

void CheckArguments(int argc, char** argv)
{
    //tutti i parametri iniziano per -<nome parametro> <valore>
    //N.B. NON c'è nessun controllo se i parametri sono passati nella maniera
    //corretta o nel giusto formato (es string o double)
    string zoomS ("-zoom");
    string moveXS ("-moveX");
    string moveYS ("-moveY");
    string bmp_file ("-bmp");
    string w ("-width");
    string h ("-height");
    string mi ("-max_i");       //max_iteration
    string cr ("-c_reale");
    string cy ("-c_yota");
    string nt ("-num_threads");
    string help ("--help");     //stampa elenco parametri
    
     
    for(int i = 0; i < argc; i++)
    {
        //printf("\r\n Parametro: %s \r\n",argv[i]);
        string parametro (argv[i]);
        if(parametro.compare(zoomS) == 0)
        {          
            zoom = atof(argv[i + 1]);
        }
        if(parametro.compare(moveXS) == 0)
        {          
            moveX = atof(argv[i + 1]);
        }
         
        if(parametro.compare(moveYS) == 0)
        {          
            moveY = atof(argv[i + 1]);
        }
         
        if(parametro.compare(bmp_file) == 0)
        {          
            nome_file = argv[i + 1];
        } 
         
        if(parametro.compare(w) == 0)
        {          
            width = atoi(argv[i + 1]);
        }
         
        if(parametro.compare(h) == 0)
        {          
            height = atoi(argv[i + 1]);
        }
 
        if(parametro.compare(mi) == 0)
        {          
            max_iterations = atoi(argv[i + 1]);
        }

        if(parametro.compare(cr) == 0)
        {          
            c_reale = atoi(argv[i + 1]);
        }

        if(parametro.compare(cy) == 0)
        {          
            c_yota = atoi(argv[i + 1]);
        }
        
        if(parametro.compare(nt) == 0)
        {          
            num_threads = atoi(argv[i + 1]);
        }        

        if(parametro.compare(help) == 0)
        {          
            printf("parametri disponibili: \r\n\r\n");
            printf("  -zoom <value> setti lo zoom da fare sull'immagine di Julia (positivo / negativo, double)\r\n\r\n");
            printf("  -moveX <value> setti lo scostamento da dare all'asse X (positivo / negativo, double)\r\n\r\n");
            printf("  -moveY <value> setti lo scostamento da dare all'asse Y (positivo / negativo, double)\r\n\r\n");
            printf("  -bmp <value> setti il percorso di salvataggio del file di output bmp (se metti solo il nome del file lo salva nella cartella dove viene eseguito il programma)\r\n\r\n");
            printf("  -width <value> setti la width dell'immagine di output (positivo, int)\r\n\r\n");
            printf("  -height <value> setti la height dell'immagine di output (positivo, int)\r\n\r\n");
            printf("  -max_i <value> setti il massimo numero di iterazioni per stabilire se il punto appartiene all'insieme di Julia o no (positivo, int)\r\n\r\n");
            printf("  -c_reale <value> setti la parte reale del numero complesso che fa da seme per l'insieme di Julia (positivo / negativo, double)\r\n\r\n");
            printf("  -c_yota <value> setti la parte immaginaria del numero complesso che fa da seme per l'insieme di Julia (positivo / negativo, double)\r\n\r\n");
            exit(0);
        }        
    }
     
    return;
}

//funzione che elabora l'insieme di Julia
//start = indice di partenza nel ciclo for più esterno
//end = indice di fine nel ciclo for più esterno
void JuliaThread(int start, int end)
{
    
    //real and imaginary parts of new and old
    double newRe = 0, newIm = 0, oldRe = 0, oldIm = 0;   
    //(EasyBMP.h) the RGB color value for the pixel
    RGBApixel color;     
    
    //each iteration, it calculates: new = old*old + c, where c is a constant and old starts at current pixel        
    for(int x = start; x < end; x++)
    for(int y = 0; y < height; y++)
    {
        //calculate the initial real and imaginary part of z, based on the pixel location and zoom and position values
        newRe = 1.5 * (x - width / 2) / (0.5 * zoom * width) + moveX;
        newIm = (y - height / 2) / (0.5 * zoom * height) + moveY;
        //i will represent the number of iterations
        int i;
        //start the iteration process
        for(i = 0; i < max_iterations; i++)
        {
            //remember value of previous iteration
            oldRe = newRe;
            oldIm = newIm;
            //the actual iteration, the real and imaginary part are calculated
            //Z new = Z old ^2 + C
            //vengono separati i 2 calcoli, parte reale e complessa dei numeri complessi
            newRe = oldRe * oldRe - oldIm * oldIm + c_reale;
            newIm = 2 * oldRe * oldIm + c_yota;
            //if the point is outside the circle with radius 2: stop
            if((newRe * newRe + newIm * newIm) > 4) 
                break;
        }
        if(i == max_iterations) 
        {
            //se il punto appartiene all'insieme di Julia, lo faccio nero
            color.Alpha = 0;
            color.Blue = 0;
            color.Green = 0;
            color.Red = 0;
        }
        else 
        {
            //il pixel ha il colore = numero di iterazioni eseguite 
            //necessarie per verificare che il punto non apparteneva all'insieme
            //di Julia. 
            //Si assegna il colore secondo il formato HSL (più comprensibile dall'occhio umano)
            //poi lo si converte in RGB
            
            //il vantaggio è che la hue è circolare, quindi si hanno colori belli anche facendo il modulo
            unsigned int h = i % 360; //0 < hue < 360 , indica la scelta del colore (vedi tavolozza su wiki)          
            unsigned int s = 100;     //0 < saturation < 100, 0 = grigio, 100 = massimo colore 
            unsigned int l = 50;      //0 < lightness < 100,  0 = nero, 100 = bianco
            
            //la funzione internamente divide la hue per 360, la saturation e la value per 100
            //int sono 4 byte, nei 3 byte meno significativi ci sono i 3 valori rgb
            unsigned int rgb = HSLtoRGB(h,s,l);
            
            color.Alpha = 0;
            color.Blue = (unsigned char)((rgb>>16)&0xFF);
            color.Green = (unsigned char)((rgb>>8)&0xFF);
            color.Red = (unsigned char)(rgb&0xFF);
        }
        //accesso esclusivo alla variabile condivisa bmp fra tutti i thread
        mtx.lock();
            //setto il valore del pixel nell'oggetto bmp
            img.SetPixel(x,y,color);
        mtx.unlock();
    }    
}

// This is a subfunction of HSLtoRGB
static void HSLtoRGB_Subfunction(unsigned int& c, const float& temp1, const float& temp2, const float& temp3)
{
	if((temp3 * 6) < 1)
		c = (unsigned int)((temp2 + (temp1 - temp2)*6*temp3)*100);
	else
		if((temp3 * 2) < 1)
			c = (unsigned int)(temp1*100);
		else
			if((temp3 * 3) < 2)
				c = (unsigned int)((temp2 + (temp1 - temp2)*(.66666 - temp3)*6)*100);
			else
				c = (unsigned int)(temp2*100);
	return;
}

// This function converts the "color" object to the equivalent RGB values of
// the hue, saturation, and luminance passed as h, s, and l respectively
unsigned int HSLtoRGB(const unsigned int& h, const unsigned int& s, const unsigned int& l)
{
	unsigned int r = 0;
	unsigned int g = 0;
	unsigned int b = 0;

	float L = ((float)l)/100;
	float S = ((float)s)/100;
	float H = ((float)h)/360;

	if(s == 0)
	{
		r = l;
		g = l;
		b = l;
	}
	else
	{
		float temp1 = 0;
		if(L < .50)
		{
			temp1 = L*(1 + S);
		}
		else
		{
			temp1 = L + S - (L*S);
		}

		float temp2 = 2*L - temp1;

		float temp3 = 0;
		for(int i = 0 ; i < 3 ; i++)
		{
			switch(i)
			{
			case 0: // red
				{
					temp3 = H + .33333f;
					if(temp3 > 1)
						temp3 -= 1;
					HSLtoRGB_Subfunction(r,temp1,temp2,temp3);
					break;
				}
			case 1: // green
				{
					temp3 = H;
					HSLtoRGB_Subfunction(g,temp1,temp2,temp3);
					break;
				}
			case 2: // blue
				{
					temp3 = H - .33333f;
					if(temp3 < 0)
						temp3 += 1;
					HSLtoRGB_Subfunction(b,temp1,temp2,temp3);
					break;
				}
			default:
				{

				}
			}
		}
	}
	r = (unsigned int)((((float)r)/100)*255);
	g = (unsigned int)((((float)g)/100)*255);
	b = (unsigned int)((((float)b)/100)*255);
	return RGB(r,g,b);
}

static inline unsigned int RGB(unsigned char r,unsigned char g,unsigned char b)
{
	unsigned int color = ((unsigned int)r<<16) | ((unsigned int)g<<8) | b;
	return color;
}
