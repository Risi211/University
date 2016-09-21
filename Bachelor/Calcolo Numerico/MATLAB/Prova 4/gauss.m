function [ L,R ] = gauss( A )
%inizializza L alla matrice identità
L = eye(max(size(A)));
%inizializza R uguale ad A
R = A;
n = size(A)
%implementazione pseudo codice
for k=1:1:n - 1
    for i = k + 1:1:n
        if(R(k,k) == 0)
            break;
        end
        L(i,k) = R(i,k) / R(k,k);
        for j=k+1:1:n
            R(i,j) = R(i,j) - L(i,k) * R(k,j);
        end
    end
end
R = triu(R);
end