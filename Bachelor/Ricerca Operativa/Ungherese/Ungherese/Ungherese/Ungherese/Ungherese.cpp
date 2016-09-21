// Ungherese.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
using namespace std;
#include <queue>

//strutture dati
#define MAX_M 5000  //massimo numero di origini
#define MAX_N 5000  //massimo numero di destinazioni
char percorso_file[500]; //il percorso avrà meno di 500 caratteri

//dati di input
int m,n;				//#origini, #destinazioni
int a[MAX_M];			//disponibilità alle origini
int b[MAX_N];			//richieste alle destinazioni
int c[MAX_M][MAX_N];	//matrice dei costi

//variabili primali e duali
int x[MAX_M][MAX_N];	//matrice variabili primali
int u[MAX_M];			//variabili duali vincoli origini
int v[MAX_N];			//variabili duali vincoli destinazioni

//matrice dei costi ridotti
int cr[MAX_M][MAX_N];

//variabili del primale ristretto
int xo[MAX_M];			//variabili artificiali origini
int xd[MAX_N];			//variabili artificiali destinazioni

//variabili per l'etichettamento
int po[MAX_M];			//padri per origini
int pd[MAX_N];			//padri per destinazioni

//struttura che definisce se un nodo in LIST è un origine o una destinazione
//più il valore del nodo
typedef struct
{
	int tipo; //0 = origine, 1 = destinazione
	int valore;
} Nodo;

//contatore cammini aumentanti
int cont_cammini = 0;
//contatore aggiornamenti soluzione duale
int cont_sol_duale = 0;

//funzioni
void problema_slide(void);
void Ungherese(void);
void soluzione_iniziale_duale(void);
void soluzione_iniziale_primale_ristretto(void);
int origine_esposta(void);
int cammino_aumentante(int);
int incremento_flusso(int, int);
void aggiorna_primale_ristretto(int, int, int);
void aggiorna_soluzione_duale(void);
int VerificaScarti(void);
void Inizializza(void);
void stampa(unsigned short, float);
void leggi_da_file(void);
void genera_random(void);
void menu(void);

void main(void)
{
	//inizializza tutte variabili = 0 con memset
	Inizializza();
	
	//leggi istanza (una delle 3 seguenti istruzioni)
	menu();
	//problema_slide();
	//leggi_da_file();
	//genera_random();

	//parte il timer
	clock_t start = clock();

	Ungherese();

	//si ferma il timer
	clock_t end = clock();

	float tempo = (float)(end - start) / (float)CLOCKS_PER_SEC;

	//N.B. po e pd da inizializzare ogni volta a -1 

	unsigned short flag = VerificaScarti();

	//stampe finali
	stampa(flag,tempo);

	printf("\r\n\r\n");
	system("PAUSE");
	return;
}

void problema_slide(void)
{
	//inizializza variabili

	m = 4;
	n = 5;

	a[1] = 4; a[2] = 5; a[3] = 7; a[4] = 4;
    b[1] = 3; b[2] = 6; b[3] = 4; b[4] = 5; b[5] = 2;

	c[1][1] = 23; c[1][2] = 18; c[1][3] =  9; c[1][4] =  30; c[1][5] = 26;
    c[2][1] = 19; c[2][2] = 21; c[2][3] =  7; c[2][4] =  23; c[2][5] = 29;
    c[3][1] = 18; c[3][2] = 16; c[3][3] = 14; c[3][4] =  20; c[3][5] = 24;
    c[4][1] = 15; c[4][2] = 20; c[4][3] =  6; c[4][4] =  18; c[4][5] = 25;

	//inizializza matrice dei costi ridotti
	int i = 1;
	int j = 1;
	for(; i <= m; ++i)
	{
		for(j = 1; j <= n; ++j)
		{
			cr[i][j] = c[i][j];
		}
	}
}

void Ungherese(void)
{
	int s =0;		//origine esposta
	int t =0;		//destinazione esposta
	int delta =0;	//incremento di flusso

	soluzione_iniziale_duale(); //calcola u^ e v^ di partenza
	soluzione_iniziale_primale_ristretto(); //assegna il flusso iniziale alle variabili primali che hanno il vincolo duale saturo (c^(i,j) = 0)

	while( (s = origine_esposta()) >= 1 ) //finchè c'è un origine esposta bisogna trovare un cammino aumentante
	{
		if( (t = cammino_aumentante(s)) >= 1 ) //trovato il cammino aumentante dall'origine s alla estinazione t
		{
			delta = incremento_flusso(s,t);
			aggiorna_primale_ristretto(s,t,delta);
		}
		else //se non trovo nessun cammino aumentante aggiorno la soluzione duale, per rendere saturi altri vincoli duali e usare di conseguenza altre variabili primali
		{
			aggiorna_soluzione_duale();
		}
	}


}

void soluzione_iniziale_duale(void)
{
	int i = 1;
	int j = 0;

	//per ogni riga trova il valore minimo della matrice dei costi
	//in questo usa la matrice cr dei costi ridotti perchè è stata
	//inizializzata = alla matrice dei costi originali del problema
	for(; i <= m; i++)
	{
		u[i] = cr[i][1];
		for(j = 2; j <= n; j++)
		{
			if(cr[i][j] < u[i])
			{
				u[i] = cr[i][j]; //trovo il minimo di riga
			}
		}
	}

	//aggiorna la matrice dei costi ridotti = matrice dei costi - u[i]
	//per ogni riga sottrae u[i]
	for(i = 1; i <= m; i++)
	{
		for(j = 1; j <= n; j++)
		{
			cr[i][j] -= u[i];
		}
	}

	//adesso per ogni colonna trova il minimo e lo assegna a v[j]
	for(j = 1; j <= n; j++)
	{
		v[j] = cr[1][j];
		for(i = 2; i <= m; i++)
		{
			if(cr[i][j] < v[j])
			{
				v[j] = cr[i][j]; //trovo il minimo di colonna
			}
		}
	}

	//aggiorna la matrice dei costi ridotti = matrice dei costi ridotti - u[i] - v[j]
	for(i = 1; i <= m; i++)
	{
		for(j = 1; j <= n; j++)
		{
			cr[i][j] -= v[j];
		}
	}
}

void soluzione_iniziale_primale_ristretto(void)
{
	int i = 0;
	int j = 0;

	//per prima cosa assegna il valore delle variabili artificiali
	//delle origini = quantità di merce alle origini
	for(i = 1; i <= m; i++)
	{
		xo[i] = a[i];
	}

	//stessa cosa per le variabili artificiali delle destinazioni
	for(j = 1; j <= n; j++)
	{
		xd[j] = b[j];
	}

	//adesso per ogni variabile primale x[i][j] che ha il costo ridotto cr[i][j] = 0
	//si assegna flusso = valore minimo tra xo[i] e xd[j]
	for(i = 1; i <= m; i++)
	{
		for(j = 1; j <= n; j++)
		{
			if(cr[i][j] == 0)
			{
				x[i][j] = (xo[i] < xd[j]) ? xo[i] : xd[j]; //x[i][j] = MIN(xo[i],xd[j]);
				xo[i] -= x[i][j];
				xd[j] -= x[i][j];
			}
		}
	}
}

int origine_esposta(void)
{
	//se c'è un xo[i] > 0, è un origine esposta
	//restituisce la prima origine esposta che trova
	//se non ne trova neanche una restituisce 0
	int i = 1;

	for(; i <= m; i++)
	{
		if(xo[i] > 0)
		{
			return i; //origine i esposta
		}
	}

	return 0; //nessun origine esposta
}

int cammino_aumentante(int s)
{
	//algoritmo cammino aumentante pag T15 dispense
	
	//LIST fatta come una coda FIFO con struct che ha
	//il tipo = origine / destinazione, e il numero del nodo

	//n.b. se po[i] e/o pd[j] == -1 => po[i] = NIL (non etichettato)
	memset(po,-1,sizeof(po)); //all'inizio po e pd = NIL
	memset(pd,-1,sizeof(pd));

	queue<Nodo> List;

	//mette s in List
	Nodo origine;
	origine.tipo = 0;  //0 = origine
	origine.valore = s;
	List.push(origine);

	//imposta po[s] = 0
	po[s] = 0;

	int i = 0;
	int j = 0;

	//while(/*esiste k da espandere, cioè esiste un elemento in LIST*/)
	while(!List.empty())
	{
		//estraggo k da List
		Nodo k = List.front();
		//cancella k da List
		List.pop();
		//if(/*k è un origine*/)
		if(k.tipo == 0)
		{
			for(j = 1; j<= n; j++)
			{
				if((pd[j] == -1) && (cr[k.valore][j] == 0))
				{
					pd[j] = k.valore;
					if(xd[j] == 0)
					{
						//aggiunge j ai nodi da espandere (in LIST) come destinazione
						Nodo destinazione;
						destinazione.tipo = 1;  //1 = destinazione
						destinazione.valore = j;
						List.push(destinazione);
					}
					else
					{
						//j è una destinazione esposta, ho quindi
						//trovato un cammino aumentante, restituisco
						//la destinazione esposta raggiunta
						//aggiorno contatore cammini aumentanti
						cont_cammini++;
						return j;
					}
				}
			}
		}
		else /*k è una destinazione*/
		{
			for(i = 1; i <= m; i++)
			{
				if((po[i] == -1) && (x[i][k.valore] > 0) && (cr[i][k.valore] == 0))
				{
					po[i] = k.valore; //etichetta
					//aggiunge i ai nodi da espandere come origine
					Nodo origine;
					origine.tipo = 0;  //0 = origine
					origine.valore = i;
					List.push(origine);
				}
			}
		}
	}

	//se arriva qui non trova nessun cammino aumentante
	return 0;
}

int incremento_flusso(int s, int t)
{
	int delta = (xo[s] < xd[t]) ? xo[s] : xd[t]; //delta = MIN(xo[s], xd[t]);
	//se esistono degli archi percorsi dalle destinazioni alle origini
	//(cioè in senso contrario) nel calcolo di delta bisogna considerare il
	//minimo tra xo[s], xd[t] e il flusso negli archi percorsi in senso contrario
	//quindi si fa il backtracking dalla destinazione esposta all origine esposta
	//del cammino aumentante da s a t, e ogni volta che si trova un arco percorso
	//in senso contrario si guarda se il flusso che passa in quell arco è < delta,
	//in questo caso delta = flusso arco percorso in senso contrario
	int k = t;
	while(1)
	{
		k = pd[k]; //k = origine, po[k] = destinazione
		if(k == s)
		{
			break; //finito il backtracking, sono arrivato all origine esposta
		}
		if(x[k][po[k]] < delta) 
		{
			delta = x[k][po[k]]; //aggiorno delta
		}
		k = po[k]; //torno indietro di un nodo
	}

	return delta;
}

void aggiorna_primale_ristretto(int s, int t, int delta)
{
	//sottrae alle variabili artificiali delle origini e destinazioni
	//l'incremento del flusso trovato col cammino aumentante da s a t
	xo[s] -= delta;
	xd[t] -= delta;

	//aggiorna le variabili primali x[i][j] aumentando di delta
	//gli archi percorsi dalle origini alle destinazioni (quindi in senso corretto)
	//e diminuisce di delta il flusso degli archi percorsi dalle destinazioni
	//alle origini (quindi in senso contrario)
	int k = t;
	while(1)
	{
		x[pd[k]][k] += delta; //arco origine - destinazione
		k = pd[k]; //trovo il predecessore della destinazione
		if(k == s)
		{
			break; //se il predecessore è s ho finito
		}
		x[k][po[k]] -= delta; //se il predecessore non è s allora ho percorso un arco destinazione - origine
		k = po[k]; //trovo la destinazione che ha raggiunto l'origine attuale
	}
}

void aggiorna_soluzione_duale(void)
{
	int i = 0;
	int j = 0;
	int delta = INT_MAX; //valore minimo della matrice dei costi ridotti degli
			   //elementi che hanno i in I+ e j in J-
				//INT_MAX è il valore massimo degli int

	//trovo il valore di delta
	for(i = 1; i <= m; i++)
	{
		//if(/*i origine etichettata (i in I+)*/)
		if(po[i] > -1) //-1 = NIL, da 0 in su sono valori di etichetta
		{
			for(j = 1; j <= n; j++)
			{
				//if(/*j destinazione non etichettata (j in J-)*/)
				if(pd[j] == -1) //-1 = NIL, non etichettato
				{
					if(cr[i][j] < delta)
					{
						delta = cr[i][j];
					}
				}
			}
		}
	}

	//aggiorno variabili duali origini
	for(i = 1; i <= m; i++)
	{
		//if(/*i origine in I+*/)
		if(po[i] > -1) //-1 = NIL, da 0 in su sono etichettati
		{
			u[i] += delta;
		}
	}

	//aggiorno variabili duali destinazioni
	for(j = 1; j <= n; j++)
	{
		//if(/*j destinazione in J+*/)
		if(pd[j] > -1) //-1 = NIL, da 0 in su sono etichettati
		{
			v[j] -= delta;
		}
	}

	//aggiorno matrice dei costi ridotti sommando delta agli elementi
	//tali che i in I- e j in J+, sottraggo delta agli elementi tali che i in I+ e j in J-
	//in questo modo nella matrice dei costi ridotti c'è almeno uno 0 in più
	//quindi il nuovo primale ristretto avrà le variabili x[i][j] relative
	//ai cr[i][j] = 0 nuovi
	for(i = 1; i <= m; i++)
	{
		for(j = 1; j <= n; j++)
		{
			//if(/*i origine in I+ && j destinazione in J-*/)
			if((po[i] > -1) && (pd[j] == -1)) //-1 = NIL, da 0 in su sono etichettati
			{
				cr[i][j] -= delta;
			}
			//if(/*i origine in I- && j destinazione in J+*/)
			if((po[i] == -1) && (pd[j] > -1)) //-1 = NIL, da 0 in su sono etichettati
			{
				cr[i][j] += delta;
			}
		}
	}
	//aggirno contatore aggiornamenti soluzione duale
	cont_sol_duale++;
}

int VerificaScarti(void)
{
	int i = 0;
	int j = 0;
	
	//vincoli del primale rispettati? (variabili primali mantengono ammissibilità primale?)
	//dall'origine i partono a[i] unità?
	for(i = 1; i <= m; i++)
	{
		int somma = 0; //tiene la somma di tutte le x[i][j] con la i fissa
		for(j = 1; j <= n; j++)
		{
			somma += x[i][j];
		}
		//somma == a[i] ? se sì OK, altrimenti errore
		if(somma != a[i])
		{
			return 1; //1 indica errore primale nei vincoli origini
		}
	}
	//dalla destinazione j partono b[j] unità?
	for(j = 1; j <= n; j++)
	{
		int somma = 0;
		for(i = 1; i <= m; i++)
		{
			somma += x[i][j];
		}
		//somma == b[j] ? se si OK, altrimenti errore
		if(somma != b[j])
		{
			return 2; //2 indica errore nei vincoli primali destinazione
		}
	}

	//vincoli del duale sono rispettati?
	//u[i] + v[j] <= c[i][j] ?
	for(int i = 1; i <= m; i++)
	{
		for(int j = 1; j <= n; j++)
		{
			if((u[i] + v[j]) > c[i][j])
			{
				//se u[i] + v[j] > c[i][j] allora vincoli duali non rispettati
				return 3; //3 indica errore vincoli duali
			}
		}
	}

	//condizioni di ottimalità sono rispettate?
	//gli scarti complementari sono verificati?
	//(c[i][j] - u[i] - v[j])*x[i][j] = 0 per ogni i = 1...m, j = 1...n ???
	for(i = 1; i <= m; i++)
	{
		for(j = 1; j <= n; j++)
		{
			if(((c[i][j] - u[i] - v[j]) * x[i][j]) != 0)
			{
				//se un solo prodotto è diverso da zero allora
				//gli scarti complementari non sono verificati
				return 4; //4 indica errore negli scarti complementari
			}
		}
	}

	//se arriva qui non ci sono stati errori, allora la soluzione trovata è ottima
	return 0; //0 indica nessun errore
}

void Inizializza(void)
{
	//tutti i vettori / matrici = 0
	memset(a,0,sizeof(a));
	memset(b,0,sizeof(b));
	memset(c,0,sizeof(c));

	memset(x,0,sizeof(x));
	memset(u,0,sizeof(u));
	memset(v,0,sizeof(v));

	memset(cr,0,sizeof(cr));

	memset(xo,0,sizeof(xo));
	memset(xd,0,sizeof(xd));

	//i vettori po e pd vengono inizializzati tutti a -1
	//prima di fare un cammino aumentante
}

void stampa(unsigned short flag, float tempo)
{
	//calcola costo funzione obiettivo:
	int i = 0;
	int j = 0;
	unsigned long int somma = 0;
	for(i = 1; i <= m; i++)
	{
		for(j = 1; j <= n; j++)
		{
			somma += c[i][j]*x[i][j];
		}
	}

	//creo il file di testo nella stessa cartella dell'exe
	FILE *fp = fopen("output.txt","w"); //se il file esiste lo sovrascrive

	//stampa valore costo funzione
	fprintf(fp,"costo funzione = %lu\r\n\r\n",somma);

	fprintf(fp,"variabili primali positive:\r\n\r\n"); 

	//stampo x[i][j] positive
	for(i = 1; i <= m; i++)
	{
		for(j = 1; j <= n; j++)
		{
			if(x[i][j] > 0)
			{
				fprintf(fp,"x%d,%d = %d\r\n",i,j,x[i][j]);
			}
		}
	}

	fprintf(fp,"\r\nvariabili duali:\r\n\r\n");

	//stampo valore variabili duali
	for(i = 1; i <= m; i++)
	{
		fprintf(fp,"u%d = %d\r\n",i,u[i]);
	}
	for(j = 1; j <= n; j++)
	{
		fprintf(fp,"v%d = %d\r\n",j,v[j]);
	}

	fprintf(fp,"\r\n");

	//stampo esito della corretteza
	switch(flag)
	{
	case 0:
		{
			fprintf(fp,"Scarti complementari verificati");
			break;
		}
	case 1:
		{
			fprintf(fp,"Vincolo primale origini non soddisfatto");
			break;
		}
	case 2:
		{
			fprintf(fp,"Vincolo primale destinazioni non soddisfatto");
			break;
		}
	case 3:
		{
			fprintf(fp,"Vincoli duali non soddisfatti");
			break;
		}
	case 4:
		{
			fprintf(fp,"Scarti complementari non soddisfatti");
			break;
		}
	default:
		{
			fprintf(fp,"Unknown flag");
			break;
		}
	}

	//stampo numero di cammini aumentanti calcolati
	fprintf(fp,"\r\n\r\nnumero cammini aumentanti = %d",cont_cammini);

	//stampo numero di aggiornamenti della soluzione duale
	fprintf(fp,"\r\n\r\nnumero aggiornamenti soluzione duale = %d",cont_sol_duale);

	//stampo tempo di calcolo
	fprintf(fp,"\r\n\r\ntempo = %f secondi", tempo);

	fclose(fp);

}

void leggi_da_file(void)
{
	//legge percorso file da shell (si può trascinare il file col mouse nella shell)
	printf("\r\nTrascina il file .txt da elaborare, premi invio e poi leggi il file output.txt\r\n\r\n");

	//leggo percorso file
	char ccc = '\n';
	while(ccc == '\n')
	{
		scanf("%c",&ccc); //se l'utente preme subito invio aspetto che inserisca altri caratteri
	}

	int i = 0;
	do //leggo percorso, quando incontro una \ ce ne metto un'altra
	{
		if(ccc != '\n' && ccc != '"') //se ultimo carattere non lo leggo, se sono le " di inizio e fine percorso nn le leggo
		{
			percorso_file[i++] = ccc;
			if(ccc == '\\')
			{
				percorso_file[i++] = '\\'; /*es: C:\ diventa C:\\*/
			}
		}
		scanf("%c",&ccc);
	}
	while(ccc != '\n');

	//lettura file input
	FILE *fp = fopen(percorso_file,"r");

	//controllo che il file esista:
	if(fp == NULL)
	{
		printf(strerror(errno)); //stampa errore
		printf("\r\n\r\n");
		system("pause");
		exit(1);
	}

	//legge numero origini e destinazioni
	fscanf(fp, "%d %d", &m, &n);

	//per ogni origine legge la disponibilità a[i]
	int j = 0;
	int letto = 0; //valore letto che finisce nel vettore

	for(i = 1; i <= m; i++)
	{
		fscanf(fp, "%d", &letto);
		a[i] = letto;
	}

	//per ogni destinazione legge la richiesta b[j]
	for(j = 1; j <= n; j++)
	{
		fscanf(fp, "%d", &letto);
		b[j] = letto;
	}

	//legge la matrice dei costi c[i][j] riportata per righe
	for(i = 1; i <= m; i++)
	{
		for(j = 1; j <= n; j++)
		{
			fscanf(fp, "%d", &letto);
			c[i][j] = letto;
			cr[i][j] = c[i][j]; //imposta la matrice dei costi ridotti = matrice dei costi originali
		}
	}

	fclose(fp);
}

void genera_random(void)
{
	//chiedo numero di origini:
	do
	{
		printf("\r\nNumero origini [1 - 10000] = ");
		scanf("%d",&m);
	}
	while((m < 1) || (m > 10000));

	//chiedo numero di destinazioni
	do
	{
		printf("\r\nNumero destinazioni [1 - 10000] = ");
		scanf("%d",&n);
	}
	while((n < 1) || (n > 10000));

	//quantità minima disponibile in ciascuna origine
	int minimo_origine = 0;
	do
	{
		printf("\r\nQuantità minima disponibile in ciascuna origine = ");
		scanf("%d",&minimo_origine);
	}
	while(minimo_origine < 1);

	//quantità massima disponibile per ogni origine
	int massimo_origine = 0;
	do
	{
		printf("\r\nQuantità massima disponibile in ciascuna origine = ");
		scanf("%d",&massimo_origine);
	}
	while(massimo_origine < minimo_origine);

	//quantità minima richiesta da ogni destinazione
	int minimo_destinazione = 0;
	do
	{
		printf("\r\nQuantità minima richiesta da ogni destinazione  = ");
		scanf("%d",&minimo_destinazione);
	}
	while(minimo_destinazione < 1);

	//quantità minima richiesta da ogni destinazione
	int massimo_destinazione = 0;
	do
	{
		printf("\r\nQuantità massima richiesta da ogni destinazione  = ");
		scanf("%d",&massimo_destinazione);
	}
	while(massimo_destinazione < minimo_destinazione);

	//costo unitario minimo
	int minimo_costo = 0;
	do
	{
		printf("\r\nCosto unitario minimo  = ");
		scanf("%d",&minimo_costo);
	}
	while(minimo_costo < 1);

	//costo unitario massimo
	int massimo_costo = 0;
	do
	{
		printf("\r\nCosto unitario massimo  = ");
		scanf("%d",&massimo_costo);
	}
	while(massimo_costo < minimo_costo);

	//generazione random:
	int i = 0;
	int j = 0;
	int somma_origini = 0;
	int somma_destinazioni = 0;
	srand(time(NULL));  //serve per generare numeri casuali
	
	//a[i], disponibilità origini
	for(i = 1; i <= m; i++)
	{
		//es. se minimo = 10, massimo = 20, genera numeri da 10 a 20 compresi
		//% (massimo_origine - minimo_origine + 1) genera numeri da 0 a 10
		//+ minimo_origine somma il numero di prima a 10
		a[i] = rand() % (massimo_origine - minimo_origine + 1) + minimo_origine;
		somma_origini += a[i];
	}

	//b[j] richieste destinazioni
	for(j = 1; j <= n; j++)
	{
		//come prima range minimo - massimo compresi
		b[j] = rand() % (massimo_destinazione - minimo_destinazione + 1) + minimo_destinazione;
		somma_destinazioni += b[j];
	}

	//prima genero la matrice dei costi casuale
	//origini e destinazioni fasulle hanno costi = 0
	for(i = 1; i <= m; i++)
	{
		for(j = 1; j <= n; j++)
		{
			//come prima range minimo - massimo compresi
			c[i][j] = rand() % (massimo_costo - minimo_costo + 1) + minimo_costo;
			//matrice dei costi ridotti = matrice dei costi originali all inizio
			cr[i][j] = c[i][j];
		}
	}

	//controllo se il problema è bilanciato o no (somma a[i] = somma b[j] ???)
	if(somma_origini < somma_destinazioni)
	{
		//se le origini hanno meno merce di quanto chiedono le destinazioni
		//aggiungo ciclicamente una nuova origine fasulla con merce = 
		//min[(somma_destinazioni - somma_origini) , massima quantità origine]
		int differenza = somma_destinazioni - somma_origini;
		while(differenza > 0)
		{
			//aggiungo origine fasulla
			m++;
			a[m] = (differenza < massimo_origine) ? differenza : massimo_origine;
			//aggiorno differenza
			differenza -= a[m];
		}
	}
	if(somma_origini > somma_destinazioni)
	{
		//stesso discorso per le origini, aggiungo una destinazione ciclicamente...
		int differenza = somma_origini - somma_destinazioni;
		while(differenza > 0)
		{
			//aggiungo origine fasulla
			n++;
			b[n] = (differenza < massimo_destinazione) ? differenza : massimo_destinazione;
			//aggiorno differenza
			differenza -= b[n];
		}
	}

	return;
}

void menu(void)
{

	//finchè l'utente non sceglie un opzione corretta rimango nel menu
	while(1)
	{
		printf("\r\n\r\nScegli cosa fare:\r\n\r\n");
		printf("1) problema slide\r\n");
		printf("2) leggi da file\r\n");
		printf("3) genera random\r\n\r\n");
		printf("In attesa: ");
		//leggo numero inserito dall'utente
		char c = '\n';
		scanf("%c",&c);
		if(c == '\n') //se l'utente preme subito invio
		{
			continue; //torno all'inizio del menu
		}

		//se dopo l'utente ha inserito più di un carattere, svuoto il
		//buffer di lettura e torno in attesa:

		char c2 = 0;
		scanf("%c",&c2);
		if(c2 == '\n') //se invio
		{
			//controllo l'opzione
			switch(c)
			{
			case '1':
				{
					problema_slide();
					return;
				}
			case '2':
				{
					leggi_da_file();
					return;
				}
			case '3':
				{
					genera_random();
					return;
				}
			default:
				{
					//torno in attesa
					break;
				}
			}
		}
		else //l'utente ha inserito 2 caratteri
		{
			//svuoto il buffer
			while(c2 != '\n')
			{
				scanf("%c",&c2);
			}
		}
	}
}
