function [ x ] = cholesky( A )
n = max(size(A));
L = zeros(n,n);
b = zeros(n,1);
for j=1:1:n
    somma = 0;
    for k=1:1:j-1
        somma = somma + L(j,k) * L (j,k);
    end
    if (A(j,j) - somma < 0)
        error('matrice non definita positiva');
    end
    L(j,j) = sqrt(A(j,j) - somma);
    for i=j+1:1:n
        somma2 = 0;
        for k=1:1:j-1
            somma2 = somma2 + L(i,k)*L(j,k);
        end
        L(i,j) = (A(i,j) - somma2)/L(j,j);
    end
end
LT = L';
%DEBUG prova L*LT = hilbert

%calcolo termine noto
for i=1:1:n
    somma = 0;
    for j=1:1:n
        somma = somma + A(i,j);
    end
    b(i,1) = somma;
end
y = avanti(L,b);
x = indietro(LT,y);
end