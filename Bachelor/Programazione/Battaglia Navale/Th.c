//funzioni che vengono chiamate da useful.c

#include <stdlib.h>
#include "TypeDefs.h"

/*
funzione che controlla se una nave è stata affondata in seguito al colpo di un bersaglio
input:
	griglia navi attaccato, livello di difficoltà, indici della coordinata colpita
output:
	TRUE se la nave è stata affondata
	FALSE se no
*/
Bool Controllo_Affondato(Griglia g, short n, Indici in)
{
	Posizione_Nave pn = Verticale; //default
	Bool avanti = FALSE;
	//trovo in quale direzione è la nave (verticale od orizzontale)
	//controllo se dir è orizzontale
	//controllo se a sinistra della coordinata della griglia c'è un pezzo di nave
	if(in.colonna != 0) //se è all'estremo sinistro non posso controllare a sinistra ma solo a destra
	{
		if(g[in.riga][in.colonna - 1] == NAVE_INTERA) //può essere NAVE_INTERA o NAVE_AFFONDATA se è già stata affondata in precedenza
		{
			return FALSE; //la nave è orizzontale ma intera
		}
		if(g[in.riga][in.colonna - 1] == NAVE_AFFONDATA)
		{
			pn = Orizzontale;
			avanti = TRUE;
		}
	}
	if(!avanti) //se a sinistra della nave si è trovato un altro pezzo di nave non controllo a destra, la nave è già orizzontale
	{
		//controllo a destra per vedere se è orizzontale
		if(in.colonna < n - 1) //la nave è all'estrema destra, non controllo a destra
		{
			if(g[in.riga][in.colonna + 1] == NAVE_INTERA)
			{
				return FALSE; //la nave è orizzontale ma intera
			}
			if(g[in.riga][in.colonna + 1] == NAVE_AFFONDATA)
			{
				pn = Orizzontale;
			}
		}
	}
	//ora so in che direzione è la nave che è stata colpita
	//adesso controllo in base alla direzione della nave se è completamente affondata
	if(pn == Verticale)
	{
		//controllo gli indici della griglia sopra e sotto a quelli passati per vedere com è messa la nave
		if(in.riga != 0)
		{
			//se la nave non è all'estremo superiore della griglia, controllo in alto fino ad arrivare alla fine della nave
			Bool estremo_nave_sup = FALSE;
			unsigned short cont = 1; //non controllo la coordinata di partenza, so già che è uguale a NAVE_AFFONDATA xkè è stata colpita
			while(!estremo_nave_sup)
			{
				//se sono arrivato all'estremo superiore della griglia mi fermo e controllo in basso
				if(in.riga - cont < 0) // == -1
				{
					//non è stato trovato nessun pezzo di nave intera in alto
					estremo_nave_sup = TRUE;
					continue;
				}
				//altrimenti controllo il contenuto della griglia in alto
				if(g[in.riga - cont][in.colonna] == NAVE_INTERA)
				{
					return FALSE;
				}
				if(g[in.riga - cont][in.colonna] == '\0')
				{
					//la nave in alto è finita
					estremo_nave_sup = TRUE;
					continue;
				}
				//altrimenti è sicuramente uguale a NAVE_AFFONDATA, nel qual caso aumento il contatore per vedere la cella della griglia più in alto
				cont++;
			}
		}
		//se in alto non ho trovato nessun pezzo di nave intera, controllo in basso
		//se sono all'estremo inferiore della griglia la nave è stata completamente distrutta
		if(in.riga == n - 1)
		{
			return TRUE;
		}
		//altrimenti controllo in basso finchè non finisce la nave
		unsigned short cont = 1; //non controlla la cella di partenza
		while(1) //esce sicuramente da questo ciclo con un return
		{
			//se sono arrivato all'estremo inferiore della griglia la nave è affondata sicuramente
			if(in.riga + cont == n)
			{
				return TRUE;
			}
			//altrimenti controllo se c'è un pezzo di nave intera
			if(g[in.riga + cont][in.colonna] == NAVE_INTERA)
			{
				return FALSE;
			}
			if(g[in.riga + cont][in.colonna] == '\0')
			{
				//la nave in basso è finita
				return TRUE;
			}
			//altrimenti il contenuto della cella è sicuramente = a NAVE_AFFONDATA, quindi aumento il contatore per controllare la cella sotto
			cont++;
		}
	}
	else //pn == Orizzontale
	{
		if(in.colonna != 0)
		{
			//se la coordinata di partenza non è all'estremo sinistro controllo alla sinistra della coordinata di partenza
			Bool estremo_nave_sx = FALSE;
			unsigned short cont = 1; //non conta la coordinata di partenza
			while(!estremo_nave_sx)
			{
				//finchè non arrivo all'estremo sinitro della nave
				if(in.colonna - cont < 0) //== -1
				{
					//sono arrivato all'estremo sinistro della nave
					estremo_nave_sx = TRUE;
					continue;
				}
				if(g[in.riga][in.colonna - cont] == NAVE_INTERA)
				{
					return FALSE; //la nave non p completamente affondata
				}
				if(g[in.riga][in.colonna - cont] == '\0')
				{
					//sono arrivato all'estremo sinistro della nave senza incontrare un pezzo di nave intera
					estremo_nave_sx = TRUE;
					continue;
				}
				//se arrivo qui significa che il valore della griglia è NAVE_AFFONDATA, quindi incremento il contatore
				cont++;
			}
		}
		//se a sinistra della coordinata di partenza non trova pezzi di nave intera, controllaa destra
		if(in.colonna == n - 1)
		{
			return TRUE; //la nave è affondata sicuramente
		}
		//altrimenti controlla a destra
		unsigned short cont = 1; //non conta la coordinata di partenza
		while(1) //esce dal ciclo sicuramente con un return
		{
			if(in.colonna + cont == n)
			{
				//la nave è affondata, non si sono visti pezzi di nave intera
				return TRUE;
			}
			if(g[in.riga][in.colonna + cont] == NAVE_INTERA)
			{
				//la nave non è completamente affondata
				return FALSE;
			}
			if(g[in.riga][in.colonna + cont] == '\0')
			{
				//la nave ha raggiunto l'estremo destro, non si sono visti pezzi di nave intera
				return TRUE;
			}
			//se arrivo qui significa che il contenuto della cella è sicuramente NAVE_AFFONDATA, quindi aumento il contatore di 1
			cont++;
		}
	}
}

/*
funzione che controlla se non c'è rimasta più nessuna nave nella griglia dell'attaccato
input:
	griglia navi attaccato,livello di difficoltà
output:
	TRUE se non ci sono più navi (vittoria dell'attaccante)
	FALSE se no
*/
Bool Controllo_Vittoria(Griglia navi_attaccato, short n)
{
	unsigned short i = 0;
	for(i = 0; i < n; i++)
	{
		unsigned short k = 0;
		for(k = 0; k < n; k++)
		{
			if(navi_attaccato[i][k] == NAVE_INTERA)
			{
				return FALSE;
			}
		}
	}
	return TRUE;
}
