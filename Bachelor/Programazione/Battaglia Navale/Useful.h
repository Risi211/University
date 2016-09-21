void End (void);

int Memoria_Insufficiente(void);

short Num_Righe_Colonne(Difficolta);

Coordinata Leggi_Coordinata(Difficolta);

Indici Indici_Da_Coordinata(Coordinata);

Bool Controllo_Sx(Indici, Griglia);

Bool Controllo_Dx(Indici, Griglia, Difficolta);

Bool Controllo_Up(Indici, Griglia);

Bool Controllo_Down(Indici, Griglia, Difficolta);

Bool Gia_Colpita(Coordinata, Griglia);

Risultato Colpisci_Coordinata(Coordinata, Difficolta, Griglia, Griglia);
