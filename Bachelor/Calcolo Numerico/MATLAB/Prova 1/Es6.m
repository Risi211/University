format long
f(1,1) = 1; %primo termine della successione
x = 1;
errore = 1e-5;
somma = f(1,1) * 4;
dif = somma - pi;
cont = 2;
while(abs(dif) > errore)
    f(1,cont) = (-1)^(cont - 1) * (x^(2*(cont - 1) + 1) / (2*(cont - 1) + 1));
    somma = somma + 4*f(1,cont);
    dif = somma - pi;
    cont = cont + 1;
    %somma %stampo il risultato della successione, sta in un ciclo infinito
    %perchè non arriva mai alla precisione di 10^-6
end
cont %stampo il numero di cicli che ha fatto l'algoritmo
somma %stampo il valore di pigreco calcolato con la successione