#include <stdio.h>

void main()
{
	#define MAX_LEN 100
//INPUT
	char s1[] = "ciao ciao ciak";
	//char s1[] = "";
	unsigned int lungS1 = sizeof(s1) - 1;
	char s2[] = "cia";
	unsigned int lungS2 = sizeof(s2) - 1;
//OUTPUT
	unsigned int posizioni[MAX_LEN];
	unsigned int posizioniLen;

	__asm
	{
		//azzero vettore
		CLD
		MOV ECX,MAX_LEN
		ADD ECX,ECX
		ADD ECX,ECX
		LEA EDI, posizioni
		XOR AL,AL
		REP STOS
		//azzero Len
		MOV posizioniLen, 0

		//se lungS2 > lungS1 --> len = 0 e FINE
		MOV EDX, lungS2
		CMP EDX, lungS1
		JA C1

		//metto in EDX: lungS1 - lungS2 + 1
		MOV EAX, lungS1
		SUB EAX, lungS2
		INC EAX
		XOR EDX,EDX

CICLO:
		//confronta i 2 blocchi a partire dalla fine
		CLD
		MOV ECX,lungS2
		LEA ESI, s1[EDX]
		LEA EDI, s2
		REPE CMPS
		JZ UGUALI
		JMP AVANTI

UGUALI:
		MOV ECX,posizioniLen
		MOV posizioni[ECX*4], EDX
		ADD posizioniLen,1
		JMP AVANTI

AVANTI:
		INC EDX
		CMP EDX,EAX
		JE FINE
		JMP CICLO

C1:
		MOV posizioniLen, 0
		JMP FINE

FINE:

	}

//stampa su video
	{
		unsigned int i;
		if(posizioniLen == 0)
		{
			printf("passed");
			while(1);
		}
		printf("int = %d",sizeof(int));
		printf("\r\n\r\nEDX = %d",posizioni[0]);
		while(1);
		for(i = 0; i < posizioniLen; i++)
		{
			printf("sottostringa in posizione %d\r\n",posizioni[i]);
		}
	}

}