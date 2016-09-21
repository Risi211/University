function [ x ] = gauss_pivoting( A,b )
%inizializza L alla matrice identità
L = eye(max(size(A)));
%inizializza R uguale ad A
R = A;
n = size(A);
%implementazione pseudo codice
for k=1:1:n - 1
    for i = k + 1:1:n
        if(R(k,k) == 0)
            %scambia la riga k esima con la riga l esima
            %trovo elemento massimo a partire da k fino ad n
            ind = k + 1;
            mass = R(k+1,k);
            for g = k + 2:1:n
                if(mass < R(g,k))
                    ind = g;
                    mass = R(g,k);
                end
            end
            %ho trovato l'indice della riga da scambiare con la riga k
            %esima
            tmp = R(k,:);
            R(k,:) = R(ind,:);
            R(ind,:) = tmp;
        end
        L(i,k) = R(i,k) / R(k,k);
        for j=k+1:1:n
            R(i,j) = R(i,j) - L(i,k) * R(k,j);
        end
        %aggiorno termine noto
        b(i,1) = b(i,1) - L(i,k)*b(k,1);
    end
end
R = triu(R);
%calcolo 
x = indietro(R,b);
end