// RO_23_04.cpp : Defines the entry point for the console application.
//
#include "StdAfx.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define Prec 0.000001
#define Inf 10000000000
#define BigM 10000
#define MaxVar 5000
#define MaxCon 500

char percorso_file[500]; //il percorso avrà meno di 500 caratteri
//dati input
long m;						//numero vincoli
long n;						//numero variabili
double Mat[MaxCon][MaxVar]; //tableau
int Segno [MaxCon];			//segno vincoli

//variabili simplesso duale
long m1;			//numero righe + riga variabile artificiale
long n1;			//numero colonne + variabili di scarto
long n2;			//numero colonne + variabili di scarto + variabile artificiale
long Base[MaxCon];	//colonna (variabile) in base per ogni riga (per ogni vincolo, si associa la variabile in base e di conseguenza si può sapere il valore che assume nel tableau)
long Stato[MaxVar]; //riga per cui ogni colonna è in base, 0 = fuori dalla base, n è in base nella riga n
int Artif;			//vincolo artificiale? 0 = no, 1 = sì
int Opt;			//se = 1 ottimo, se = -1 no sol ammissibile, se = -2 sol. illimitata

void Input(void);
void Risolvi_Duale(void);
void Aggiungi_Variabili_Slack(void);
void Calcolo_Base_Iniziale(void);
void Aggiungi_Variabile_Artificiale(void);
void Pivot(long, long);
int Trova_Uscente(void);
int Trova_Entrante(int);
void Output(float);

void main ( void ) 
{

	Input(); //legge i dati dal file

	//parte il timer
	clock_t start = clock();

	Risolvi_Duale();

	//si ferma il timer
	clock_t end = clock();

	float tempo = (float)(end - start) / (float)CLOCKS_PER_SEC;

	//OUTPUT
	Output(tempo);

	return;
}

void Input()
{
	//setta a zero tutte le celle della matrice e gli elementi dei vettori
	memset(Mat, 0, sizeof(Mat) );
	memset(Segno, 0, sizeof(Segno) );
	memset(Base, 0, sizeof(Base) );
	memset(Stato, 0, sizeof(Stato) );
	memset(percorso_file,0,sizeof(percorso_file));

	printf("Trascina il file.dat da elaborare, poi premi invio e leggi l output nel file output.txt\r\n\r\n");

	//leggo percorso file.dat da leggere
	char c = '\n';
	while(c == '\n')
	{
		scanf("%c",&c); //se l'utente preme subito invio aspetto che inserisca altri caratteri
	}

	int i = 0;
	do //leggo percorso, quando incontro una \ ce ne metto un'altra, perchè è un carattere speciale (\\)
	{
		if(c != '\n' && c != '"') //se ultimo carattere (\n) non lo leggo, se sono le " di inizio e fine percorso nn le leggo
		{
			percorso_file[i++] = c;
			if(c == '\\')
			{
				percorso_file[i++] = '\\'; //es: C:\ diventa C:\\ 
			}
		}
		scanf("%c",&c);
	}
	while(c != '\n');

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

	//do per scontato che il file sia formattato bene

	fscanf(fp, "%d %d", &n, &m);	//legge numero variabili e numero vincoli
	
	//servono per tutti i cicli sottostanti
	double d = 0; //numero double letto da fscanf
	int p = 0;    //numero int letto da fscanf
	
	//leggiamo termini noti = numero vincoli = m
	for(i = 1; i <= m; i++) //parte da 1 xkè la prima riga contiene la riga 0
	{		
		fscanf(fp, "%lf", &d); //legge il termine noto e lo salva in d
		Mat[i][0] = d;		   //li salva nella prima colonna
	}

	//legge il segno dei vincoli = numero dei vincoli = m
	for(i = 1; i <= m; i++) //parte da 1 così si allinea con la matrice Mat
	{		
		fscanf(fp, "%d", &p); //legge il segno
		Segno[i] = p;		   //lo salva
	}

	// ciclo che per ogni variabile, legge:
	// 1) il costo che ha nella funzione obiettivo (e lo moltiplica poi per -1)
	// 2) il numero di coefficienti diversi da zero nella colonna dei vincoli della variabile 
	// 3) per ogni coefficiente, l'indice della riga e il valore del coefficiente
	// salva il tutto nella matrice Mat come indicato a pagina 8

	for(i = 1; i <= n; i++)
	{
		// 1) leggo cj
		fscanf(fp, "%lf", &d);
		//moltiplica cj per (-1)
		d *= -1;
		//salva cj nella matrice nella riga 0
		Mat[0][i] = d;

		// 2) legge il numero dei coefficienti nella colonna delle variabile
		fscanf(fp, "%d", &p);
		
		//per ogni coefficiente, legge l'indice della riga e il valore del coefficiente aj nella riga scelta
		int k = 0; //indice nuovo ciclo
		for(k = 0; k < p; k++) //se è uguale a zero non entra neanche qui
		{
			int l = 0;
			fscanf(fp, "%d", &l); //legge indice di riga
			double valore = 0;    
			fscanf(fp, "%lf", &valore); //legge valore del coefficiente aj
			//salva nella matrice Mat il valore del coefficiente nella riga l-esima
			Mat[l][i] = valore;
		}
		//si passa alla variabile successiva
	}
	fclose(fp);
	return;
}

void Risolvi_Duale()
{
	Opt = 1;

	Aggiungi_Variabili_Slack();
	
	Calcolo_Base_Iniziale();
	
	if (Opt != 1)
	{
		return;
	}
	
	Aggiungi_Variabile_Artificiale();

	int i = 0;
	int j = 0;

	while( ( i = Trova_Uscente() ) > 0 ) //esiste uno zj - cj > 0, i è l'indice di riga del tableau della variabile
	{
		j = Trova_Entrante(i); //esiste un rapporto minimo positivo
		if (j == 0)
		{
			Opt = -1;
			return;
		}
		Stato[Base[i]] = 0; //variabile i esce dalla base
		Base[i] = j;		//variabile j entra in base nella riga i
		Stato[j] = i;		//variabile j in base, si segna la riga i del tableau per indicare dove si trova
		Pivot(i,j);			//modifica il tableau
	}
	//verifiche soluzione illimitata
	//controllo il valore della variabile artificiale, se è = 0 allora
	//la soluzione è illimitata perchè il vincolo che si pensava fosse ridondante
	//limita invece il valore di z
	//Quindi nella soluzione finale trovata la variabile artificiale deve
	//essere in base e con un valore > 0 affinchè la soluzione trovata sia
	//giusta e non illimitata

	int f = 0;
	int index = 0;
	for(f = 1; f <= m1; f++)
	{
		if(Base[f] == n2)
		{
			index = f; //indice variabile artificiale in base
			break;
		}
	}
	if(index == 0) //se la variabile artificiale è fuori dalla base ha valore = 0, quindi il vincolo artificiale viene saturato con le variabili del problema
	{
		Opt = -2;
	}
	/*
	else
	{
		if(Mat[index][0] < Prec)
		{
			Opt = -2;
		}
	}
	*/
	
}

void Aggiungi_Variabili_Slack(void)
{
	n1 = n;	//all'inizio le variabili di scarto non ci sono, quindi var + numero var slack = var
	int i =0;
	int k = 0;

	for( i = 1; i <= m; ++i )
	{
		if(Segno[i] != 0) //se segno disequazione != da '=' c'è una variabile di scarto
		{
			++n1; //se il segno è <= o >= si aggiunge una variabile di scarto
			for( k = 0; k <= m; ++k)
			{
				Mat[k][n1] = 0.0; //aggiunge nel Tableau la colonna relativa alla nuova var di scarto
			}
			//nella riga i (cioè nell' i-esimo vincolo) si aggiunge nella colonna
			//della var di scarto il suo coefficiente, 
			//1 se il vincolo è <=, -1 se è >=
			//in questo caso se Segno[i] = 1 => il vincolo è <=, e viceversa
			Mat[i][n1] = Segno[i];
		}
	}
	n2 = n1; //var + var di scarto + var artificiale (per il momento la var artificiale non c'è)
	m1 = m; //numero righe + riga var artificiale (per il momento la var artificiale nn c'è)
}

void Calcolo_Base_Iniziale(void)
{
	int i = 0;
	int j = 0;

	for(j = 0; j <= n1; ++j)
	{
		Stato[j] = 0;
	}
	for(i = 1; i <= m1; ++i) //scorre tutti i vincoli
	{
		//parte dal fondo della riga per mettere le variabili di scarto come base iniziale del simplesso 
		for(j = n1; j >= 1; --j)
		{
			if( (Mat[i][j] < -Prec) || (Mat[i][j] > Prec) ) 
			{
				//nella riga i,
				//finchè trova degli zeri nella riga i partendo da n1 fino a 1
				//non fa niente, quando ne trova uno diverso da 0 esce e aggiunge
				//la variabile in base
				break;
			}
		}
		if(j > 0)
		{
			Base[i] = j;  //la var j entra in base 
			Stato[j] = i;
			Pivot(i,j);   //si fa il pivot per rendere 1 il coefficiente della variabile in base
			//e per rendere 0 gli elementi sopra e sotto, in questo modo all'iterazione successiva del
			//for sono sicuro di trovare un'altra variabile, perchè quella che è già entrata vale zero,
			//quindi il for precedente la salta.
			//Così si forma la matrice identità e si ha una base iniziale
		}
		else //se tutti i coefficienti sono = 0
		{
			Base[i] = 0;
			if( (Mat[i][0] < -Prec) || (Mat[i][0] > Prec) )
			{
				Opt = -1;
				//avendo tutti i coefficienti delle variabili = 0,
				//devo controllare se il termine noto del vincolo è
				//uguale a zero, in caso contrario è un vincolo del tipo
				//0 = -5 che rende impossibile tutto il sistema
				//e quindi il problema originale non ha soluzione ammissibile
				return;
			}
		}
	}
}

void Aggiungi_Variabile_Artificiale(void)
{
	Artif = 0;
	double max = Prec;
	int i = 0;
	int j = 0;
	
	//controllo tutti gli zj - cj, se uno è > 0 lo salva in max per
	//aggiornare il confronto e si salva l'indice del massimo
	for(j = 1; j <= n1; ++j)
	{
		if(Mat[0][j] > max)
		{
			max = Mat[0][j];
			Artif = j;
		}
	}
	//se c'è uno zj - cj > 0 => si aggiunge il vincolo e la variabile artificiale
	if(Artif != 0)
	{
		++m1;
		++n2;
		Mat[m1][0] = BigM; //termine noto
		Mat[m1][n2] = 1.0; //coefficiente variabile artificiale nel vincolo artificiale
		//per ogni variabile non base, mette il coefficiente 1 nel vincolo artificiale
		for(j = 1; j < n2; ++j)
		{
			if(Stato[j] == 0) //fuori base
			{
				Mat[m1][j] = 1.0;
			}
			else //in base
			{
				Mat[m1][j] = 0.0;
			}
		}
		//rende = 0 l'ultima colonna del tableau (vedi p. 8 dispense)
		for( i = 0; i < m1; ++i)
		{
			Mat[i][n2] = 0.0;
		}
		//fa uscire la variabile artificiale dalla base e fa entrare la
		//variabile che ha lo zj - cj massimo e > 0
		Base[m1] = Artif; //Artif è l'indice della variabile entrante
		Stato[Artif] = m1; //lo stato della variabile entrante si imposta "in base"
		Stato[n2] = 0; //imposta lo stato della variabile artificiale come "fuori base"
		Pivot(m1,Artif); //modifica il tableau per avere tutti gli zj - cj < 0
		//adesso tutti gli zj - cj sono <= 0
	}
}

void Pivot(long i, long j)
{
	//Dato l'indice di riga i e l'indice di colonna j
	//questa funzione deve fare il pivoting nella Mat[][]
	//sull'elemento Mat[i][j]
	
	//per prima cosa divido la riga per il pivot (così il pivot diventa 1)
	int k = 0;
	for(k=0; k <= n2; k++) //parte dal termine noto e arriva fino al coefficiente dell'ultima variabile
	{
		if(k != j) //il pivot lo setta a 1 dopo il ciclo for
		{
			Mat[i][k] /= Mat[i][j]; //Mat[i][j] è il pivot
		}
	}
	Mat[i][j] = 1.0; //pivot = 1;

	//poi aggiorno le altre righe del tableau per rendere 0 gli elementi sopra e sotto il pivot
	for(k = 0; k <= m1; k++)
	{
		if(k != i) //non considera la riga del pivot
		{
			//per ogni riga per prima cosa guardo il valore dell'elemento nella colonna pivot
			if(Mat[k][j] != 0) //se l'elemento è già uguale a zero non devo fare niente
			{
				//moltiplico la riga pivot per -Mat[k][j]
				//e sommo la riga pivot modificata con la riga attuale
				//del tableau per aggiornare la riga
				int p = 0;
				for(p = 0; p <= n2; p++)
				{
					if(p != j) //l'elemento della colonna pivot si azzera dopo il ciclo for
					{
						double tmp = 0; //salva il valore dell'elemento della riga pivot moltiplicato per -Mat[k][j]
						tmp = Mat[i][p] * Mat[k][j] * (-1); //moltiplico
						Mat[k][p] += tmp;				   //sommo
					}
				}
				Mat[k][j] = 0.0; //elemento della colonna pivot si azzera
			}
		}
	}
}

int Trova_Uscente(void)
{
	//deve restituire l'indice di riga della
	//variabile da far uscire dalla base

	//controllo la colonna dei termini noti, se sono tutti positivi
	//ritorno 0 e così il simplesso duale termina, altrimenti ritorno l'indice
	//del termine noto più negativo
	int i = 1;
	double min = -Prec; //valore di partenza per il minimo (essendo double sarebbe 0)
	int index = 0;		//salva l'indice del termine noto minimo
	for(i = 1; i <= m1; i++)
	{
		if(Mat[i][0] < min)
		{
			//aggiorno il minimo
			min = Mat[i][0];
			//aggiorno indice
			index = i;
		}
	}
	//controllo se il ciclo sopra ha trovato almeno un termine noto negativo
	//e quindi una variabile uscente, basta controllare se index assume un valore
	//maggiore di zero (index in questo caso assume i valori da 1 a m1 compresi)
	return index; //se index = 0 tutti i termini noti erano positivi o uguali a zero
}

int Trova_Entrante(int i)
{
	//data la riga i deve restituire
	//l'indice della colonna da far entrare in base

	//faccio tutti i rapporti tra zj - cj e gli yj, se yj è < 0
	//significa che almeno un yj è minore di zero, 
	//quindi si può procedere col simplesso duale e
	//aggiorno il minimo positivo con il relativo
	//indice (da far ritornare poi in output)
	//se tutti gli yj sono > 0 => il primale non ha soluzione ammissiile
	//e l'indice di colonna rimane = 0

	double min = 100*BigM;
	int index = 0;
	int k = 1;
	for(k = 1; k <= n2; k++)
	{
		//controllo che yj sia < 0
		if(Mat[i][k] < -Prec)
		{
			double tmp = Mat[0][k] / Mat[i][k]; //rapporto
			//controllo se il rapporto è minore del minimo attuale
			if(tmp < min)
			{
				//aggiorna rapporto minimo e indice
				min = tmp;
				index = k;
			}
		}
		//se yj >= 0 non fa niente
	}
	//alla fine index vale 0 se tutti gli yj sono >= 0, altrimenti
	//vale un numero tra 1 e n2 compresi. Se index = 0 il controllo
	//lo fa la funzione Risolvi_Duale()

	return index;
}

void Output(float tempo)
{
	//da controllare Opt se è = 1 -1 o -2
	//il costo di z è in Mat[0][0]
	
	//creo il file di testo nella stessa cartella dell'exe
	FILE *fp = fopen("output.txt","w"); //se il file esiste lo sovrascrive

	switch(Opt) //scrive stato della soluzione
	{
		case 1:
		{
			fprintf(fp,"Soluzione Ottima (Opt = 1)\r\n\r\n");
			break;
		}
		case -1:
		{
			fprintf(fp,"Soluzione Non Ammissibile (Opt = -1)\r\n\r\n");
			break;
		}
		case -2:
		{
			fprintf(fp,"Soluzione Illimitata (Opt = -2)\r\n\r\n");
			break;
		}
	}
	//scrive valore della funzione obiettivo
	fprintf(fp,"z = %lf\r\n\r\n",Mat[0][0]);

	//scrive valore di tutte le variabili:
	//prima scorro il vettore base e scrivo valore var base
	//poi scorro vettore stato e scrivo var fuori base = 0
	//nel vettore var che si segna il valore di tutte le variabili in ordine
	double var[MaxVar];
	int i = 0;
	for(i = 1; i <= m1; i++)
	{
		var[Base[i]] = Mat[i][0];
	}
	for(i = 1; i <= n2; i++)
	{
		if(Stato[i] == 0)
		{
			var[i] = 0.0;
		}
	}

	//scrive il valore delle variabili nel file di testo
	for(i = 1; i <= n2; i++)
	{
		fprintf(fp,"X%d = %lf\r\n\r\n",i,var[i]);
	}

	//scrive il tempo totale di calcolo in secondi
	fprintf(fp,"%f secondi\r\n\r\n",tempo);

	fclose(fp);
}
