/*
definisce il carattere usato per delimitare le celle verticalmente dalla tabella ASCII
*/
#define DELIMITATORE_VERTICALE 179

/*
definisce il carattere usato per delimitare le celle in orizzontale
*/
#define DELIMITATORE_ORIZZONTALE 95

/*
definisce il carattere usato per rappresentare una cella di nave ancora intera
*/
#define NAVE_INTERA 178

/*
definisce il carattere usato per rappresentare una cella di nave colpita
*/
#define NAVE_AFFONDATA 176

/*
definisce il carattere usato per contrassegnare una coordinata "Colpito"
*/
#define NAVE_COLPITA 254

/*
definisce il carattere usato per rappresentare una coordinata "Mancato"
*/
#define NAVE_MANCATA 88

/*
definisce il tipo booleano
*/
typedef unsigned short Bool;
#define TRUE  1
#define FALSE 0

/*
definisce il tipo stringa
*/
typedef char *String;

/*
definisce il tipo di elemento di base per la griglia di gioco
*/
typedef unsigned char ElementoBaseGriglia;

/*
definisce la griglia di gioco dove si sistemano le navi
*/
typedef ElementoBaseGriglia **Griglia;

/*
definisce la struttura coordinata (es. A0, A1, B2, ...)
*/
typedef struct {
	char lettera;
	unsigned short numero;
} Coordinata;

/*
definisce la struttura indici, indicano la riga e la colonna della matrice
*/
typedef struct {
	unsigned short riga;
	unsigned short colonna;
} Indici;

/*
definisce il tipo di base per la dimensione delle navi
*/
typedef unsigned short DimensioneNave;

/*
enumera i bordi estremi (sinistro,destro,alto,basso)
*/
typedef enum {Sinistro,Alto,Destro,Basso} Bordo;

/*
enumera il risultato di un attacco
*/
typedef enum {Mancato,Colpito,Affondato,Vittoria} Risultato;

/*
enumera le difficoltà del gioco
*/
typedef enum{Facile, Media, Difficile} Difficolta;
//definiscono il numero di righe e di colonne della griglia in
//base al livello di difficoltà
#define BASE_FACILE    10 //valori compresi tra 6 e 10 (non più di 10)
#define BASE_MEDIA     14 //valori compresi tra 11 e 14 (non più grande di BASE_DIFFICILE)
#define BASE_DIFFICILE 18 //valori compresi tra 15 e 18 (il massimo è 18)

/*
definisce il tipo base del vettore dimensioni della flotta
*/
typedef unsigned short TipoDimensione;

/*
definisce il tipo base del numero di navi della flotta
*/
typedef unsigned short TipoNumeroNavi;
/*
definisce il tipo flotta, che contiene i campi:
numero navi,
vettore che contiene le dimensioni di ogni nave
*/
typedef struct {
	TipoNumeroNavi numero_navi;
	TipoDimensione *dimensioni;
} Flotta;

typedef enum {Verticale, Orizzontale} Posizione_Nave;

/*
enumera le possibili opzioni del menù di gioco
*/
typedef enum {Attacca, Stampa, Arrenditi} Menu;
