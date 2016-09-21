#include <cstdlib>
 
//Easy BMP
#include "lib/EasyBMP.h"
#include "lib/EasyBMP_DataStructures.h"
#include "lib/EasyBMP_VariousBMPutilities.h"
#include "lib/EasyBMP_BMP.h"
 
using namespace std;

//default settings
double moveX = -0.5;
double moveY = 0;
double zoom = 1;
string nome_file ("es.bmp");
//after how much iterations the function should stop
unsigned int max_iterations = 300;

int width = 400;
int height = 300;

void CheckArguments(int, char**);
////HSL TO RGB per colorare i pixel che non appartengono all'insieme di julia in
//base al numero di iterazioni richieste per farlo uscire dal cerchio di raggio 2
static inline unsigned int RGB(unsigned char ,unsigned char ,unsigned char );
unsigned int HSLtoRGB(const unsigned int& , const unsigned int& , const unsigned int& );
static void HSLtoRGB_Subfunction(unsigned int& , const float& , const float& , const float& );
 
int main(int argc, char** argv) {
 
    CheckArguments(argc, argv);
     
    printf("%f %f %f %s %d %d %d",moveX,moveY,zoom,nome_file.c_str(),width,height,max_iterations);    
    
    BMP img;
    img.SetSize(width, height);    
 
    //each iteration, it calculates: new = old*old + c, where c is a constant and old starts at current pixel
 
//each iteration, it calculates: newz = oldz*oldz + p, where p is the current pixel, and oldz stars at the origin
    double pr, pi;                   //real and imaginary part of the pixel p
    double newRe, newIm, oldRe, oldIm;   //real and imaginary parts of new and old z
    RGBApixel color; //the RGB color value for the pixel
 
    //loop through every pixel
    for(int x = 0; x < width; x++)
    for(int y = 0; y < height; y++)
    {
        //calculate the initial real and imaginary part of z, based on the pixel location and zoom and position values
         pr = 1.5 * (x - width / 2) / (0.5 * zoom * width) + moveX;
        pi = (y - height / 2) / (0.5 * zoom * height) + moveY;
        newRe = newIm = oldRe = oldIm = 0; //these should start at 0,0
        //"i" will represent the number of iterations
        int i;
        //start the iteration process
        for(i = 0; i < max_iterations; i++)
        {
            //remember value of previous iteration
            oldRe = newRe;
            oldIm = newIm;
            //the actual iteration, the real and imaginary part are calculated
            newRe = oldRe * oldRe - oldIm * oldIm + pr;
            newIm = 2 * oldRe * oldIm + pi;
            //if the point is outside the circle with radius 2: stop
            if((newRe * newRe + newIm * newIm) > 4) break;
        }       
        if(i == max_iterations) //il punto appartiene all'insieme di Julia, lo faccio nero
        {
            color.Alpha = 0;
            color.Blue = 0;
            color.Green = 0;
            color.Red = 0;
        }
        else //il pixel ha il colore = distanza dall'origine
        {
            unsigned int h = i % 360; //0 < hue < 360, quindi prendo un qualsiasi colore rgb in base al numero di iterazioni fatte
            //il vantaggio Ã¨ che la hue Ã¨ circolare, quindi si hanno colori belli anche facendo il modulo
            unsigned int s = 100;
            unsigned int l = 50;
             
            //la funzione divide la hue per 360, la saturation e la value per 100
            unsigned int rgb = HSLtoRGB(h,s,l);
             
            color.Alpha = 0;
            color.Blue = (unsigned char)((rgb>>16)&0xFF);
            color.Green = (unsigned char)((rgb>>8)&0xFF);
            color.Red = (unsigned char)(rgb&0xFF);
        }   
        img.SetPixel(x,y,color);
    }
 
    img.WriteToFile(nome_file.c_str());
     
    return 0;
}

void CheckArguments(int argc, char** argv)
{
    string zoomS ("-zoom");
    string moveXS ("-moveX");
    string moveYS ("-moveY");
    string bmp_file ("-bmp");
    string w ("-width");
    string h ("-height");
    string mi ("-max_i");
     
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
    }
     
    return;
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
