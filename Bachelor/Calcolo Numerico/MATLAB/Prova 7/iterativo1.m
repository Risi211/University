function [ x1,c1,r1,k1,t1,x2,c2,r2,k2,t2 ] = iterativo1(n)
%matrice 1
[c,d,e]=gallery('dorr',n,0.1);
A1=gallery('tridiag',c,d,e);
A1=full(A1);
%matrice 2
A2 = gallery('frank',n,2);
%termine noto
b = zeros(n,1);
%la soluzione del sistema è il vettore unitario, quindi il termine noto è
%la somma delle righe della matrice
for ind=1:1:n
    somma = 0;
    for ind2=1:1:n
        somma = somma + A1(ind,ind2);
    end
    b(ind,1) = somma;
end
%errore (criterio di arresto)
err = 1e-6;
%pagina 118 formula

%JACOBI per matrice 1
%calcolo E,D,F
F = zeros(n,n) + triu(A1,1);  %parte superiore
E = zeros(n,n) + tril(A1,-1); %parte inferiore
D = A1 - triu(A1,1) - tril(A1,-1); %diagonale
%vettore iniziale
xiniziale = zeros(n,1);
%calcolo raggio spettrale della matrice di iterazione
M = D;
N = -(E+F);
T = M\N; %M^(-1)*N, matrice di iterazione
r1 = max(abs(eig(T))); %eig trova gli autovalori, abs fa il valore assoluto, max prende il massimo dei valori assoluti del raggio spettrale
%calcolo indice di condizionamento della matrice del sistema lineare
c1 = cond(A1);
[x1,k1,t1] = jacobi(D,E,F,b,xiniziale,err);

%JACOBI per matrice 2
%calcolo E,D,F
F = zeros(n,n) + triu(A2,1);  %parte superiore
E = zeros(n,n) + tril(A2,-1); %parte inferiore
D = A2 - triu(A2,1) - tril(A2,-1); %diagonale
%calcolo termine noto
b = zeros(n,1);
%la soluzione del sistema è il vettore unitario, quindi il termine noto è
%la somma delle righe della matrice
for ind=1:1:n
    somma = 0;
    for ind2=1:1:n
        somma = somma + A2(ind,ind2);
    end
    b(ind,1) = somma;
end
%vettore iniziale
xiniziale = zeros(n,1);
%calcolo raggio spettrale della matrice di iterazione
M = D;
N = -(E+F);
T = M\N; %M^(-1)*N, matrice di iterazione
r2 = max(abs(eig(T))); %eig trova gli autovalori, abs fa il valore assoluto, max prende il massimo dei valori assoluti del raggio spettrale
%calcolo indice di condizionamento della matrice del sistema lineare
c2 = cond(A2);

[x2,k2,t2] = jacobi(D,E,F,b,xiniziale,err);
end