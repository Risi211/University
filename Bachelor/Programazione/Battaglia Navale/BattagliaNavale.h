void Chiedi_Nomi_Utenti(const short, String *, String *);

Difficolta Scelta_Difficolta(void);

void Stampa_Griglia(Griglia,Difficolta);

void Inizializza_Griglie(Difficolta, Griglia *, Griglia *, Griglia *, Griglia *);

Flotta Definisci_Navi(unsigned short,unsigned short,unsigned short,unsigned short);

void Inserisci_Navi_In_Griglia(Griglia, String, Difficolta, Flotta);

Menu Stampa_Menu(String);

Bool Turno(String, Difficolta, Griglia, Griglia);

void Rilascia_Risorse(String, String, Griglia, Griglia, Griglia, Griglia);

void Visualizza_Statistiche(unsigned int, int, int);

Bool Fine_Programma(void);
