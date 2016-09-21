function [ x,k,t ] = jacobi(D,E,F,b,xiniziale,err)
%k parte da zero
k = 0;
t = 0;
%la x-iniziale è la x ^ k-1, x è la x^k
%inizializzo vettore di output
x = zeros(max(size(b)),1);
%faccio il primo passo
x = (-D\(E+F))*(xiniziale) + D\b;
% aggiorno k
k = 1;
%calcolo errore
errore = norm(x - xiniziale);
tic
while(errore > err)
    %eseguo gli stessi passi finchè l'errore è minore dell'errore massimo
    %(err-max)
    %aggiorno il vettore iniziale
    xiniziale = x;
    %calcolo la nuova x^k
    x = (-D\(E+F))*(xiniziale) + D\b;
    %calcolo il nuovo errore
    errore = norm(x - xiniziale);
    %aggiorno k
    k = k + 1;
end
t = toc;   
end