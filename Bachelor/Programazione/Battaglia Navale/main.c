#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "TypeDefs.h"
#include "BattagliaNavale.h"

#define MAX_LETTERE_NOME 20 //il numero massimo di caratteri che possono formare i nomi dei giocatori
#define MIN_NUMERO_NAVI 5
#define MAX_NUMERO_NAVI 8
#define MIN_DIMENSIONE_NAVE 2
#define MAX_DIMENSIONE_NAVE 5

//#define DEBUG

int main(int argc, char *argv[])
{
    Bool fine = FALSE; //se false il programma viene chiuso
    //finchè gli utenti vogliono giocare il programma non si chiude
    while(!fine)
    {
		short i = 0;
		Bool partita = TRUE; //se false il gioco finisce, se gli utenti vogliono può ricominciare
		unsigned int contatore = 0;
		String giocatore1;
		String giocatore2;
	  
		Chiedi_Nomi_Utenti(MAX_LETTERE_NOME, &giocatore1, &giocatore2);
	  
		Difficolta d = Scelta_Difficolta();
	  
		Griglia navi_giocatore1;
		Griglia risultati_giocatore1;
		Griglia navi_giocatore2;
		Griglia risultati_giocatore2;
		Inizializza_Griglie(d,&navi_giocatore1,&risultati_giocatore1,&navi_giocatore2,&risultati_giocatore2);
	  
		//chiede ai giocatori quante e quali navi vogliono usare
		Flotta f = Definisci_Navi(MIN_NUMERO_NAVI,MAX_NUMERO_NAVI,MIN_DIMENSIONE_NAVE,MAX_DIMENSIONE_NAVE);

		Inserisci_Navi_In_Griglia(navi_giocatore1, "debug", d, f);
		//da fare nella funzione inserisci navi
		printf("\r\nIl giocatore 1 ha terminato l'inserimento navi, ora tocca al giocatore 2\r\n");
		system("pause");
		system("cls");
		Inserisci_Navi_In_Griglia(navi_giocatore2, "debug", d, f);
		printf("\r\nIl giocatore 2 ha terminato l'inserimento navi, ora tocca al giocatore 2\r\n");
		system("pause");
		system("cls");
	  
		String giocatori[2] = {giocatore1,giocatore2};
		Griglia posizioni[2] = {navi_giocatore2 , navi_giocatore1};
		//sono messe al contrario perchè in un turno si usano i risultati di uno e le navi dell'altro
		Griglia risultati[2] = {risultati_giocatore1 , risultati_giocatore2};
		int start_time = time(NULL);
		int end_time = 0;
		while(partita)
		{
			for(i = 0; i < 2; i++)
			{
				Menu scelta;
				Bool deciso = FALSE;
				//DA FARE IN UN UNICO CICLO I TURNI PER I 2 GIOCATORI E NON 2 IN SEQUENZA
				while(!deciso)
				{
					scelta = Stampa_Menu("g1");
					switch(scelta)
					{
						case Attacca:
						{
							if(!Turno("debug",d,posizioni[i],risultati[i]))
							{
								partita = FALSE;
							}
							deciso = TRUE;
							continue;
						}
						case Stampa:
						{
							//stampa griglia navi utente per vedere come sono messe le sue navi
							if(i == 0)
							{
								Stampa_Griglia(posizioni[1],d);
							}
							else //i == 1
							{
								Stampa_Griglia(posizioni[0],d);
							}
							break;
						}
						case Arrenditi:
						{
							//vince il giocatore 2
							printf("\r\n\r\nIl giocatore si è arreso");
							//stampa anche il tempo
							partita = FALSE;
							deciso = TRUE;
							continue;
						}
					}
				}
				if(!partita)
				{
					i = 3;
					continue;
				}
			}
			if(!partita)
			{
				continue;
			}
			contatore++;
		}
		end_time = time(NULL);
		Rilascia_Risorse(giocatore1,giocatore2,navi_giocatore1,navi_giocatore2,risultati_giocatore1,risultati_giocatore2);
		Visualizza_Statistiche(contatore,start_time,end_time);
		if(Fine_Programma())
		{
			fine = TRUE;
		}
	}
    return 0;	
}

