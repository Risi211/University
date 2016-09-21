//FUNZIONI CHE VENGONO CHIAMATE DALLE FUNZIONI DI BATTAGLIA_NAVALE.C

#include <stdio.h>
#include <stdlib.h>
#include "TypeDefs.h"
#include "Th.h"

//funzione chiamata quando il programma viene chiuso
void End (void) 
{
	printf("Memoria insufficiente, il programma � stato terminato\r\n");
	system("PAUSE");	
}

/*
funzione che viene richiamata quando la memoria heap non
� sufficientemente grande per poter allocare un array
*/
int Memoria_Insufficiente(void)
{
	atexit(End);
	exit(EXIT_FAILURE); //il programma termina
}

/*
funzione che fornisce dal livello di difficolt� il numero
di righe e di colonne di ogni griglia di gioco
input:
	livello di difficolt�
output:
	numero di righe e di colonne di ogni griglia
*/
short Num_Righe_Colonne(Difficolta d)
{
	switch(d)
	{
		case Facile:
		{
			return (unsigned short)BASE_FACILE;
		}
		case Media:
		{
			return (unsigned short)BASE_MEDIA;
		}
		case Difficile:
		{
			return (unsigned short)BASE_DIFFICILE;
		}
	}
}

/*
funzione legge una coordinata in input dal giocatore
input:
	livello di difficolt� del gioco
output:
	coordinata letta
*/
Coordinata Leggi_Coordinata(Difficolta d)
{
	Coordinata output;
	output.numero = 0xFF;
	String lettere = "ABCDEFGHIJKLMNOPQR";
	int res = 0;
	unsigned short i = 0;
	const short base = Num_Righe_Colonne(d);
	do //ciclo eseguito finch� l'utente non inserisce 2 caratteri
	{
		res = 2;
		char cifra1 = '\0';
		char cifra2 = '\0';
		//res = scanf("%c",&in);
		output.lettera = getchar();
		if(output.lettera == '\n') //preme subito invio
		{
			printf("\r\nInput non valido, inserire una coordinata (es: A1, A12, B3, ...): ");
			res = 5; //resta nel ciclo
			continue;
		}

		cifra1 = getchar();
		if(cifra1 == '\n') //inserisce solo la lettera
		{
			printf("\r\nInput non valido, inserire una coordinata (es: A1, A12, B3, ...): ");
			res = 5; //resta nel ciclo
			continue;
		}
			
		cifra2 = getchar();
		if(cifra2 != '\n') //se c'� una seconda cifra
		{
			res = 3;
			while(getchar() != '\n') //svuota buffer
			{
				res++;//conta il numero di caratteri di troppo
			}
		}
		
		//controllo che la lettera della coordinata sia una lettera valida per la difficolt� di gioco attuale
		Bool lettera_valida = FALSE;
		for(i = 0; i < base; i++)
		{
			if(lettere[i] == output.lettera)
			{
				lettera_valida = TRUE;
				break;
			}
		}
		if(!lettera_valida)
		{
			printf("\r\nInput non valido, inserire una coordinata (es: A1, A12, B3, ...): ");
			res = 5; //resta nel ciclo
		}
		else
		{
			switch(res)
			{
				case 2: //la coordinata � formata da una lettera e una cifra (es A1, B2, ...)
				{
					//cifra1 = getchar(); //legge la cifra dopo la lettera
					//controllo che il carattere letto sia veramente una cifra
					unsigned short test = (unsigned short)(cifra1 - '0');
					//printf("%c",cifra1);
					//while(1);
					if(test > 9) //il carattere non � una cifra
					{
						printf("\r\nInput non valido, inserire una coordinata (es: A1, A12, B3, ...): ");
						res = 5; //resta nel ciclo
					}
					else
					{
						output.numero = test; //converte il carattere cifra nell'effettivo numero decimale
					}
					break;
				}
				case 3: //la coordinata � formata da una lettera e 2 cifre (es A11, B14, ...)
				{
					//se la difficolt� � facile, il numero pu� avere solo una cifra
					if(d == Facile)
					{
						printf("\r\nInput non valido, inserire una coordinata (es: A1, A12, B3, ...): ");
						res = 5; //resta nel ciclo
						break;
					}
					//char cifra1 = getchar(); //legge la prima cifra del numero dopo la lettera (le decine)
					//char cifra2 = getchar(); //legge la seconda cifra del numero dopo la lettera (le unit�)
					//controllo che i 2 caratteri letti siano 2 cifre
					unsigned short test = (unsigned short)cifra1 - '0';
					if(test > 9 || test == 0) //la cifra delle decine non pu� essere 0
					{
						printf("\r\nInput non valido, inserire una coordinata (es: A1, A12, B3, ...): ");
						res = 5; //resta nel ciclo
						break;
					}
					output.numero = test * 10; //mette la parte delle decine nel numero
					//controllo 2nd cifra
					test = (unsigned short)cifra2 - '0';
					if(test > 9)
					{
						printf("\r\nInput non valido, inserire una coordinata (es: A1, A12, B3, ...): ");
						res = 5; //resta nel ciclo
						break;
					}
					output.numero += test; //ricostruisce il numero in decimale
					//controllo che il numero sia un numero valido per la difficolt� attuale
					if(output.numero >= base) //i numeri vanno da zero a base
					{
						printf("\r\nInput non valido, inserire una coordinata (es: A1, A12, B3, ...): ");
						res = 5; //resta nel ciclo
						break;
					}
					break;
				}
				default:
				{
					printf("\r\nInput non valido, inserire una coordinata (es: A1, A12, B3, ...): ");
					break;
				}
			}
		}
			
		
	}
	while(res != 2 && res != 3);
	//ritorna la coordinata
	return output;
}

/*
funzione che ricava dai 2 campi della coordinata i 2 relativi indici che
indicano la riga e la colonna nella griglia
input:
	coordinata
output:
	indici
*/
Indici Indici_Da_Coordinata(Coordinata c)
{
	Indici output;
	String lettere = "ABCDEFGHIJKLMNOPQR";
	unsigned short i = 0;
	//l'indice della riga � uguale al numero della coordinata
	output.riga = c.numero;
	//trova indice della colonna
	for(i = 0; i < BASE_DIFFICILE; i++)
	{
		if(lettere[i] == c.lettera)
		{
			output.colonna = i;
			break;
		}
	}
	return output;
}

/*
funzione che controlla se la cella della griglia a sinistra della cella passata
� occupata da una nave
input:
	Indici cella attuale, griglia da controllare, difficolt� atuale
output:
	TRUE se la cella a sinistra � libera o se � fuori dai confini della griglia
	FALSE se la cella a sinistra � occupata da un'altra parte di nave
*/
Bool Controllo_Sx(Indici i, Griglia g)
{
	//se la cella � all'estrema sinistra della griglia non controlla niente
	if(i.colonna == 0)
	{
		return TRUE; //la cella � ok
	}
	//se la cella a sinistra non � 0, contiene sicuramente una nave, quindi la cella
	//non pu� ospitare un'altra nave
	if(g[i.riga][i.colonna - 1] != '\0')
	{
		return FALSE;
	}
	return TRUE;
}

/*
controlla se la cella sopra alla cella passata pu� contenere la nave
input:
	indici della cella attuale, griglia da controllare
output:
	TRUE se la cella pu� contenere la nave
	FALSE se la cella contiene un'altra nave
*/
Bool Controllo_Up(Indici i, Griglia g)
{
	//se la cella � nella riga pi� alta della griglia, non controlla niente
	if(i.riga == 0)
	{
		return TRUE;
	}
	if(g[i.riga - 1][i.colonna] != '\0')
	{
		return FALSE;
	}
	return TRUE;
}

/*
controlla se la cella a destra della griglia pu� contenere la nave
input:
	indici della cella attuale, griglia da controllare, difficolt� attuale
output:
	TRUE se la cella pu� contenere un'altra nave
	FALSE se la cella contiene gi� un'altra nave
*/
Bool Controllo_Dx(Indici i, Griglia g, Difficolta d)
{
	short n = Num_Righe_Colonne(d);
	if(i.colonna == (n - 1))
	{
		return TRUE;
	}
	if(g[i.riga][i.colonna + 1] != '\0')
	{
		return FALSE;
	}
	return TRUE;
}

/*
funzione che controlla se la cella attuale pu� contenere la nave
input:
	indici della cella attuale, griglia da controllare, difficolt� attuale
output:
	TRUE se la cella pu� contenere un'altra nave
	FALSE se la cella contiene gi� un'altra nave
*/
Bool Controllo_Down(Indici i, Griglia g, Difficolta d)
{
	short n = Num_Righe_Colonne(d);
	if(i.riga == (n - 1))
	{
		return TRUE;
	}
	if(g[i.riga + 1][i.colonna] != '\0')
	{
		return FALSE;
	}
	return TRUE;
}

/*
funzione che controlla se il giocatore ha gi� colpito la coordinata
input:
	coordinata, griglia risultati attaccante, difficolt�
output:
		TRUE se � gi� stata colpita
		FALSE se non � ancora stata colpita
*/
Bool Gia_Colpita(Coordinata c, Griglia g, Difficolta d)
{
	unsigned short i = 0;
	Indici in = Indici_Da_Coordinata(c);
	if(g[in.riga][in.colonna] == NAVE_COLPITA || g[in.riga][in.colonna] == NAVE_MANCATA)
	{
		return TRUE;
	}
	return FALSE;
}

/*
funzione che colpisce una coordinata nella griglia navi_attaccato
input:
	nome giocatore, difficolt�, navi_attaccato, risultati_attaccante
output:
	esito del colpo:
		Mancato, Colpito, Affondato, Vittoria
*/
Risultato Colpisci_Coordinata(Coordinata c, Difficolta d, Griglia navi_attaccato, Griglia risultati_attaccante)
{
	Risultato output;
	unsigned char res = '\0';
	short n = Num_Righe_Colonne(d);
	//controlla se colpisce la nave
	//Bool colpito = FALSE;
	Indici in = Indici_Da_Coordinata(c);
	if(navi_attaccato[in.riga][in.colonna] == NAVE_INTERA)
	{
		//colpita una nave
		navi_attaccato[in.riga][in.colonna] = NAVE_AFFONDATA;
		
		//controllo se � affondata tutta la nave
		if(Controllo_Affondato(navi_attaccato,n,in))
		{
			//controllo vittoria
			if(Controllo_Vittoria(navi_attaccato,n))
			{
				output = Vittoria;
			}
			else
			{
				output = Affondato;
			}
		}
		else
		{
			output = Colpito;
		}
		
		//controllo se ha vinto
		//Vittoria();
		res = NAVE_COLPITA;
	}
	else
	{
		output = Mancato;
		res = NAVE_MANCATA;
	}
	
	
	//segna risultato nei risultati attaccante
	risultati_attaccante[in.riga][in.colonna] = res;
	return output;
}
