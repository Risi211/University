#include <stdio.h>
#include <stdlib.h>
#include "TypeDefs.h"
#include "Useful.h"

/*
funzione che chiede in input il nome del giocatore
input:
	parametro 1: numero massimo di lettere che formano il nome (se l'input è troppo grande, viene troncato)
	parametro 2: numero del giocatore (es. giocatore1 o giocatore2)
output:
	puntatore a primo carattere della stringa
*/
void Chiedi_Nomi_Utenti(const short max, String *g1, String *g2)
{	
	char buffer[max + 1]; //contiene i dati letti in input temporaneamente + il terminatore di stringa
	short contatore = 0; //indice di buffer
	Bool valido = FALSE; //indica se l'input fornito dall'utente contiene almeno una lettera
	short numero_giocatore = 1; //indica il numero corrente del giocatore
	String *nomi[2] = { g1, g2 }; //salva i 2 puntatori in un vettore per poter fare un ciclo con le stesse istruzioni per ogni giocatore
	
	for(; numero_giocatore < 3; numero_giocatore++)
	{
		printf("\r\nInserisci il nome del giocatore%hi (max %hi caratteri): ",numero_giocatore,max);
		while(!valido) //finchè l'utente preme invio senza nessuna lettera
		{
			Bool enter = FALSE; //indica se viene letto dal buffer il \n
			while(contatore < max && !enter) //legge i caratteri finchè non c'è un \n o si è raggiunto il numero massimo di caratteri letti
			{
				buffer[contatore] = getchar();
				if(buffer[contatore] == '\n') //il carattere \n viene sostituito col terminatore di stringa
				{
					buffer[contatore] = '\0';
					enter = TRUE;
				}
				contatore++;
			}
			/*se l'utente ha inserito il max numero di caratteri,
			  il \n non è stato sostituito col \0 e il buffer di lettura
			  contiene almeno il carattere \n (più gli eventuali altri caratteri inseriti in più
			  dall'utente)*/
			if(contatore == max) 
			{
				buffer[contatore] = '\0';
				while(getchar() != '\n'); //svuota il buffer
			}
			//se è stata inserita almeno una lettera allora il nome è valido
			if(contatore > 1)
			{
				valido = TRUE;
			}
			else
			{
				printf("\r\nInput non valido, inserisci almeno una lettera nel nome del giocatore%hi: ",numero_giocatore);
				contatore = 0;
			}
		}
		//alloca dinamicamente la stringa per un numero di caratteri
		//esattamente uguali al numero di caratteri di buffer		
		*nomi[numero_giocatore - 1] = (String)malloc(sizeof(char)*(contatore + 1));
		//controllo che ci sia abbastanza memoria
		if(*nomi[numero_giocatore - 1] == NULL)
		{
			Memoria_Insufficiente();
		}
		short i = 0;
		//copia i caratteri da buffer alla stringa allocata dinamicamente
		for(i = 0; i <= contatore; i++)
		{
			(*nomi[numero_giocatore - 1])[i] = buffer[i];
		}
		//azzera variabili del ciclo
		valido = FALSE;
		contatore = 0;
	}
	
	return;
}

/*
funzione che chiede all'utente la difficoltà del gioco (facile, media, difficile)
output: 
	difficoltà scelta (trattata come enum)
*/
Difficolta Scelta_Difficolta(void)
{
	unsigned short scelta; //contiene il numero dato in input dall'utente
	int res;	//contiene il numero di caratteri letti dalla scanf
	Difficolta output; //risultato da dare in output
	printf("\r\nScegli il livello di difficolta (0 = facile, 1 = media, 2 = difficile): ");
	//finchè l'utente non inserisce un'opzione valida
	do
	{
		res = scanf("%hu",&scelta); //legge input
		while (getchar() != '\n'); //svuota buffer
		if(res != 1) //se è stato letto più di un carattere
		{
			printf("\r\nInput non valido, scegli il livello di difficolta (0 = facile, 1 = media, 2 = difficile): ");
		}
		else
		{
			switch(scelta)
			{
				case 0: //facile
				{
					output = Facile;
					return output;
				}
				case 1: //media
				{
					output = Media;
					return output;
				}
				case 2: //difficile
				{
					output = Difficile;
					return output;
				} 
				default: //qualsiasi carattere tranne 0,1,2
				{
					printf("\r\nInput non valido, scegli il livello di difficolta (0 = facile, 1 = media, 2 = difficile): ");
					res = 5; //per non terminale il do while cambio res da 1 a 5
					break;
				}
			}
		}
	}
	while(res != 1);
}

/*
funzione che stampa la griglia di gioco su console
input:
	griglia da stampare, livello di difficoltà (per sapere quante righe e colonne ha la griglia)
*/
void Stampa_Griglia(Griglia g, Difficolta d)
{
	//contiene le lettere che faranno parte della coordinata
	String lettere = "ABCDEFGHIJKLMNOPQR"; //al massimo 18 righe e colonne, quindi 20 lettere
	//ricavo il numero di righe e di colonne della griglia
	const short base = Num_Righe_Colonne(d);
	unsigned short i = 0;
	//stampa prima riga (4 spazi,lettera,2 spazi,lettera,...)
	printf("\r\n    %c",DELIMITATORE_VERTICALE);
	for(i = 0; i < base; i++)
	{
		printf(" %c %c",lettere[i],DELIMITATORE_VERTICALE);
	}
	printf("\r\n");
	//stampa separatori di riga (-----------)
	for(i = 0; i < 5 + (base * 4); i++)
	{
		printf("%c",DELIMITATORE_ORIZZONTALE); //DELIMITATORE_ORIZZONTALE
	}
	printf("\r\n");
	//ciclo che stampa il numero di riga della griglia e la riga della griglia
	for(i = 0; i < base; i++)
	{
		//se il numero ha 1 cifra dopo il numero della riga stampa 3 spazi
		if(i < 10)
		{
			printf("  %hu %c",i,DELIMITATORE_VERTICALE);
		}
		//altrimenti se ha 2 cifre stampa solo due spazio
		else
		{
			printf(" %hu %c",i,DELIMITATORE_VERTICALE);
		}
		//ciclo che stampa tutti gli elementi della riga della griglia
		unsigned short k = 0;
		for(k = 0; k < base; k++)
		{
			printf(" %c %c",g[i][k],DELIMITATORE_VERTICALE);
		}
		printf("\r\n"); //va a capo
		//stampa separatori di riga (-----------)
		for(k = 0; k < 5 + (base * 4); k++)
		{
			printf("%c",DELIMITATORE_ORIZZONTALE);
		}
		printf("\r\n");
	}
	return;
}

/*
funzione che inizializza le griglie di ogni giocatore
in base al livello di difficoltà
(esempio:
 se difficoltà = facile, dimensione griglia = 10X10,
 se difficoltà = media, dimensione griglia = 15X15,
 se difficoltà = difficile, dimensione griglia = 20X20)
input: 
	difficoltà
output:
	Griglia inizializzata
*/
void Inizializza_Griglie(Difficolta d, Griglia *g1, Griglia *g2, Griglia *g3, Griglia *g4)
{
	Griglia *g[4] = { g1,g2,g3,g4 }; //vettore di parametri
	short num = 0; //contatore del ciclo
	const short base = Num_Righe_Colonne(d);
/*	switch(d) //calcolo numero di righe e di colonne matrice
	{
		case Facile:
		{
			base = BASE_FACILE;
			break;
		}
		case Media:
		{
			base = BASE_MEDIA;
			break;
		}
		case Difficile:
		{
			base = BASE_DIFFICILE;
			break;
		}		
	}
	*/
	for(; num < 4; num++)
	{
		//alloca dinamicamente la griglia
		*g[num] = (Griglia)malloc(sizeof(ElementoBaseGriglia *)*base); //alloca lo spazio per contenere le righe (ogni riga è un puntatore ai valori delle colonne)
		if(*g[num] == NULL) //controllo se è stata allocata correttamente
		{
			Memoria_Insufficiente();
		}
		short i = 0;
		for(i = 0; i < base; i++) //ad ogni riga della griglia alloca lo spazio necessario per le colonne
		{
			(*g[num])[i] = (ElementoBaseGriglia *)malloc(sizeof(ElementoBaseGriglia)*base);
			if((*g[num])[i] == NULL)
			{
				Memoria_Insufficiente();
			}
		}
		//inizializza tutti gli elementi della griglia a 0
		for(i = 0; i < base; i++)
		{
			short j = 0;
			for(j = 0; j < base; j++)
			{
				(*g[num])[i][j] = '\0';
			}
		}
	}
	return;
}

/*
funzione che chiede ai giocatori quante e quali navi vogliono usare
input:
	numero minimo e massimo di navi
	dimensione minima e massima di ogni nave
output:
	Flotta (numero di navi + dimensione per ogni nave)
*/
Flotta Definisci_Navi(unsigned short min_num_navi,unsigned short max_num_navi,unsigned short min_dimensione_nave,unsigned short max_dimensione_nave)
{
	int res = 0;
	unsigned short num = 0;
	Flotta output;
	//controllo che min_num_navi sia effettivamente minore di max_num_navi
	if(min_num_navi > max_num_navi)
	{
		//scambio valori tra i 2 parametri
		unsigned short scambio = min_num_navi;
		min_num_navi = max_num_navi;
		max_num_navi = scambio;
	}
	//stesso controllo per le dimensioni delle navi
	if(min_dimensione_nave > max_dimensione_nave)
	{
		//scambio valori tra i 2 parametri
		unsigned short scambio = min_dimensione_nave;
		min_dimensione_nave = max_dimensione_nave;
		max_dimensione_nave = scambio;
	}
	printf("\r\nInserisci il numero di navi da posizionare nelle griglie (compreso tra %hu e %hu): ",min_num_navi,max_num_navi);
	do
	{
		res = scanf("%hu",&num);
		while(getchar() != '\n'); //svuota buffer di input
		if(res == 1)
		{
			//controllo che il numero scelto dall'utente sia compreso fra il numero minimo e massimo
			if(num < min_num_navi || num > max_num_navi)
			{
				//numero non valido, richiede all'utente di inserirne un altro
				printf("\r\nInput non valido, inserisci il numero di navi da posizionare nelle griglie (compreso tra %hu e %hu): ",min_num_navi,max_num_navi);
				res = 5; //cambio valore di res per restare nel ciclo	
			}
		}
		else
		{
			//numero di caratteri in input non valido, richiede all'utente di inserire un altro numero
			printf("\r\nInput non valido, inserisci il numero di navi da posizionare nelle griglie (compreso tra %hu e %hu): ",min_num_navi,max_num_navi);
		}
	}
	while(res != 1);
	//inizializzo il vettore dimensioni di output
	output.dimensioni = (TipoDimensione *)malloc(sizeof(TipoDimensione)*num);
	//controllo che il puntatore non sia NULL
	if(output.dimensioni == NULL)
	{
		Memoria_Insufficiente();
	}
	//ora chiedo all'utente di inserire la dimensione di ogni nave
	short i = 0;
	for(i = 0; i < num; i++)
	{
		unsigned short in = 0;
		printf("\r\nInserisci la dimensione della nave %hu, compresa tra %hu e %hu: ",(i + 1),min_dimensione_nave, max_dimensione_nave);
		do
		{
			res = scanf("%hu",&in);
			while(getchar() != '\n'); //svuota buffer di input
			if(res == 1)
			{
				//controllo che la dimensione inserita sia compresa fra la dimensione minima e massima
				if(in < min_dimensione_nave || in > max_dimensione_nave)
				{
					printf("\r\nIjnput non valido, inserisci la dimensione della nave %hu, compresa tra %hu e %hu: ",(i + 1),min_dimensione_nave, max_dimensione_nave);			
					res = 5; //cambio valore per restare dentro al ciclo
				}
			}
			else
			{
				//numero di caratteri in input non valido
				printf("\r\nIjnput non valido, inserisci la dimensione della nave %hu, compresa tra %hu e %hu: ",(i + 1),min_dimensione_nave, max_dimensione_nave);	
			}
		}
		while(res != 1);
		//salva la dimensione nel vettore dimensioni
		output.dimensioni[i] = (TipoDimensione)in;
	}
	//salva il numero delle navi in output
	output.numero_navi = (TipoNumeroNavi)num;
	//ritorna la flotta
	return output;
}

/*
funzione che inserisce le navi in Griglia di un giocatore
input:
	Griglia del giocatore dove vengono posizionate le navi
	stringa nome giocatore
*/
void Inserisci_Navi_In_Griglia(Griglia g, String nome_giocatore, Difficolta d, Flotta f)
{
	//debug
	unsigned short dim = 0;
	unsigned short x = 0; //contatore
	printf("\r\nGiocatore Inserisci le navi in griglia\r\n");
	for(x = 0; x < f.numero_navi; x++)
	{
		Stampa_Griglia(g,d);
		dim = f.dimensioni[x];
		printf("\r\nLa prossima nave da inserire ha dimensione = %hu\r\n",dim);
		Bool avanti = FALSE;
		Posizione_Nave pn = Verticale; //default
		Indici i1,i2;
		while(!avanti)
		{
			avanti = TRUE;
			printf("\r\nInserisci la coordinata d'inizio della nave: ");
			Coordinata c1 = Leggi_Coordinata(d); //fa anche il controllo che la coordinata sia compresa nella griglia
			//stampa coordinata //DEBUG
			//printf("\r\n%c%hu",c1.lettera,c1.numero);
			printf("\r\nInserisci la coordinata di fine della nave: ");
			Coordinata c2 = Leggi_Coordinata(d);
			//printf("\r\n%c%hu",c2.lettera,c2.numero);
			//controllo che la coordinata non sia la stessa
			if(c1.lettera == c2.lettera && c1.numero == c2.numero)
			{
				printf("\r\nErrore: hai inserito la stessa coordinata per l'inizio e per la fine\r\n");
				avanti = FALSE;
				continue; //rincomincia dal ciclo
				//while(1);
			}
			//controllo che la nave sia verticale o orizzontale
			if(c1.lettera != c2.lettera && c1.numero != c2.numero)
			{
				printf("\r\nErrore: la nave non e' ne verticale nè orizzontale\r\n");
				avanti = FALSE;
				continue;
				//while(1);
			}
			//se c2 + piccolo di c1, inverto le coordinate
			if(c2.numero < c1 .numero || c2.lettera < c1.lettera)
			{
				Coordinata tmp = c2;
				c2 = c1;
				c1 = tmp;
			}
			//controllo che la dimensione sia quella giusta
			//controllo se è verticale o orizzontale
			//se hanno lo stesso numero è orizzontale, se hanno la stessa lettera è verticale
			if(c1.numero == c2.numero)
			{
				if(c2.lettera - c1.lettera + 1 != dim)
				{
					printf("\r\nErrore: la dimensione della nave e' diversa da quella chiesta in input\r\n");
					avanti = FALSE;
					continue;
					//while(1);
				}
				pn = Orizzontale;
			}
			else
			{
				if(c2.numero - c1.numero + 1 != dim)
				{
					printf("\r\nErrore: la dimensione della nave e' diversa da quella chiesta in input\r\n");
					avanti = FALSE;
					continue;
					//while(1);
				}
			}
			//controllo che nelle coordinate della nave non ci sia un'altra nave	
			//trovo indici da coordinate
			i1 = Indici_Da_Coordinata(c1);
			i2 = Indici_Da_Coordinata(c2);
			//se posizione_nave è verticale scorre la griglia in verticale
			//altrimenti in orizzontale
			if(pn == Verticale)
			{
				//prima di controllare le celle della nave, controllo gli estremi sopra e sotto
				if(!Controllo_Up(i1,g) || !Controllo_Down(i2,g,d))
				{
					//c'è già un'altra nave, non è possibile aggiungere la nuova nave in queste coordinate
					printf("\r\nErrore: In queste coordinate c'e' gia' un'altra nave\r\n");
					avanti = FALSE;
					continue;
					//while(1);
				}
				unsigned short i = 0;
				for(i = (unsigned short)i1.riga; i <= i2.riga; i++)
				{
					//DEVO CONTROLLARE ANCHE A SINISTRA E A DESTRA DUI OGNI CASELLA
					//DEVO CONTROLLARE ANCHE LA CASELLA SOPRA E SOTTO DELLE COORDINATE LIMITE
					//IN OGNI CASO SE LA CASELLA è FUORI DALLA GRIGLIA NON CONTROLLO NIENTE E LO PRENDO PER BUONO
					if(g[i][i1.colonna] != '\0')
					{
						//se c'è uin qualche carattere diverso dal crattere nullo allora c'è una nave
						printf("\r\nErrore: In queste coordinate c'e' gia' un'altra nave\r\n");
						avanti = FALSE;
						continue;
						//while(1);
					}
					//controllo che a destra e a sinistra le celle siano libere e non già occupate
					Indici tmp;
					tmp.riga = i; //cambia riga in base alla i del for
					tmp.colonna = i1.colonna;
					if(!Controllo_Sx(tmp,g) || !Controllo_Dx(tmp,g,d))
					{
						printf("\r\nErrore: In queste coordinate c'e' gia' un'altra nave\r\n");
						avanti = FALSE;
						continue;
						//while(1);
					}
				}
			}
			else //orizzontale
			{
				//prima controllo gli estremi dx e sx
				if(!Controllo_Sx(i1,g) || !Controllo_Dx(i2,g,d))
				{
					//c'è già un'altra nave, non è possibile aggiungere la nuova nave in queste coordinate
					printf("\r\nErrore: In queste coordinate c'e' gia' un'altra nave\r\n");
					avanti = FALSE;
					continue;
					//while(1);
				}
				unsigned short i = 0;
				for(i = i1.colonna; i <= i2.colonna; i++)
				{
					//DEVO CONTROLLARE ANCHE SOPRA E SOTTO OGNI CASELLA
					//DEVO CONTROLLARE ANCHE LA CASELLA A DESTRA E A SINISTRA DELLE COORDINATE LIMITE
					//IN OGNI CASO SE LA CASELLA è FUORI DALLA GRIGLIA NON CONTROLLO NIENTE E LO PRENDO PER BUONO
					if(g[i1.riga][i] != '\0')
					{
						//se c'è uin qualche carattere diverso dal crattere nullo allora c'è una nave
						printf("\r\nErrore: In queste coordinate c'e' gia' un'altra nave\r\n");
						avanti = FALSE;
						continue;
						//while(1);
					}
					//controllo celle up e down
					Indici tmp;
					tmp.riga = i1.riga;
					tmp.colonna = i;
					if(!Controllo_Up(tmp,g) || !Controllo_Down(tmp,g,d))
					{
						printf("\r\nErrore: In queste coordinate c'e' gia' un'altra nave\r\n");
						avanti = FALSE;
						continue;
						//while(1);
					}
				}
			}
		}
		//se arrivo qui indenne da errori posso sistemare nella griglia la nave
		//(2 NAVI NON POSSONO ESSERE IN CASELLE TOCCANTI PERCHè LO CONTROLLO PRIMA)
		if(pn == Verticale)
		{
			unsigned short i = 0;
			for(i = i1.riga; i <= i2.riga; i++)
			{
				//inserisce nave in griglia
				g[i][i1.colonna] = NAVE_INTERA;
			}
		}
		else //orizzontale
		{
			unsigned short i = 0;
			for(i = i1.colonna; i <= i2.colonna; i++)		
			{
				//inserisce nave in griglia
				g[i1.riga][i] = NAVE_INTERA;
			}
		}
	
		//while(1);
	}
	Stampa_Griglia(g,d);
}

Menu Stampa_Menu(String giocatore)
{
	printf("\r\nScegli cosa vuoi fare:\r\n\r\n1) Attacca una coordinata\r\n2)Stampa griglia delle tue navi per vederne lo stato\r\n3)Arrenditi\r\n\r\nIn attesa di input: ");
	char scelta = '\0';
	Bool avanti = TRUE;
	Menu output;
	//legge finchè l'input non è corretto
	do
	{
		avanti = TRUE;
		Bool input_ok = TRUE;
		scelta = getchar();
		if(scelta == '\n')
		{
			input_ok = FALSE;
			while(getchar() != '\n'); //svuota il buffer
		}
		else
		{
			if(getchar() != '\n')
			{
				input_ok = FALSE;
				while(getchar() != '\n'); //svuota il buffer
			}
		}
		if(!input_ok) //controllo se ha inserito + di un carattere
		{
			printf("\r\nInput non valido, le opzioni possibili sono:\r\n\r\n1) Attacca una coordinata\r\n2)Stampa griglia delle tue navi per vederne lo stato\r\n3)Arrenditi\r\n\r\nIn attesa di input: ");
			avanti = FALSE;
			continue;
		}
		//controllo il carattere inserito
		switch(scelta)
		{
			case '1':
			{
				output = Attacca;
				return output;
				break;
			}
			case '2':
			{
				output = Stampa;
				return output;
				break;
			}
			case '3':
			{
				output = Arrenditi;
				return output;
				break;
			}
			default:
			{
				printf("\r\nInput non valido, le opzioni possibili sono:\r\n\r\n1) Attacca una coordinata\r\n2)Stampa griglia delle tue navi per vederne lo stato\r\n3)Arrenditi\r\n\r\nIn attesa di input: ");
				avanti = FALSE;
				continue;
			}
		}
	}
	while(!avanti);
}

/*
funzione che gestisce il turno di gioco
input:
	parametri necessari alle altre funzioni
output:
	TRUE il gioco va avanti
	FALSE il gioco è finito (un giocatore ha vinto)
*/
Bool Turno(String giocatore, Difficolta d, Griglia navi_attaccato, Griglia risultati_attaccante)
{

	printf("\r\nAl turno n i risultati dei tuoi attacchi sono i seguenti:\r\n\r\n");
	Stampa_Griglia(risultati_attaccante,d);
	printf("\r\nInserisci una coordinata da colpire: ");
	Coordinata c = Leggi_Coordinata(d);
	if(Gia_Colpita(c,risultati_attaccante))
	{
		printf("hai gia' colpito questa coordinata, il tuo turno e' finito");
		return TRUE;
	}
	Risultato r = Colpisci_Coordinata(c,d,navi_attaccato,risultati_attaccante);
	switch(r)
	{
		case Mancato:
		{
			printf("\r\nMancato\r\n");
			return TRUE;
		}
		case Colpito:
		{
			printf("\r\nColpito\r\n");
			return TRUE;
		}
		case Affondato:
		{
			printf("\r\nColpito e affondato\r\n");
			return TRUE;
		}
		case Vittoria:
		{
			printf("\r\nHai vinto\r\n");
			return FALSE;
		}
	}
	
}

/*
funzione che rilascia la memoria heap allocata dinamicamente per
i vettori dinamici dei nomo dei giocastori e delle 4 griglie
input:
	nomi dei giocatori (String)
	griglie di gioco (Griglia)
*/
void Rilascia_Risorse(String s1, String s2, Griglia g1, Griglia g2, Griglia g3, Griglia g4)
{
	free(s1);
	free(s2);
	free(g1);
	free(g2);
	free(g3);
	free(g4);
	return;
}

/*
funzione che visualizza le statistiche della partita
input:
	contatore turni, tempo di inizio, tempo di fine 
*/
void Visualizza_Statistiche(unsigned int cont, int st, int et)
{
	system("cls");
	printf("\r\nStatistiche di gioco:\r\n");
	printf("\r\nNumero di turni totali: %d\r\n",cont + 1);
	printf("\r\nTempo totale di gioco: %d\r\n", et - st);
	//cont++ xkè il cont aumenta alla fine del turno, se finisce prima non ci arriva alla fine
}

/*
funzione che chiede agli utenti se vogliono fare un'altra partita
*/
Bool Fine_Programma(void)
{
	char scelta = '\0';
	Bool avanti = FALSE;
	printf("\r\nVolete fare un'altra partita?(s = si, n = no): ");
	do
	{
		scelta = getchar();
		if(scelta == '\n')
		{
			printf("\r\nInput non valido, inserisci solo un carattere per indicare la risposta (s = si, n = no): ");
			continue;
		}
		else
		{
			if(getchar() != '\n')
			{
				//ha inserito più di un carattere				
				while(getchar() != '\n'); //svuota buffer
				printf("\r\nInput non valido, inserisci solo un carattere per indicare la risposta (s = si, n = no): ");				
				continue;
			}
			else
			{
				//input di un carattere, bisogna controllare che sia giusto
				switch(scelta)
				{
					case 's':
					{
						printf("\r\nOra inizia un'altra partita\r\n\r\n");
						system("pause");
						system("cls");
						return FALSE;
						continue;
					}
					case 'n':
					{
						printf("\r\nIl programma termina\r\n\r\n");
						system("pause");
						return TRUE;
						continue;
					}
					default:
					{
						printf("\r\nInput non valido, inserisci solo un carattere per indicare la risposta (s = si, n = no): ");					
						continue;
					}
				}
			}
		}
	}
	while(!avanti);
}
