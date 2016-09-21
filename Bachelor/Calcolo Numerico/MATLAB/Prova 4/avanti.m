function [ x ] = avanti( L,b )
%il vettore colonna x ha la stessa dimensione di b
x = zeros(size(b));
%implementazione pseudo codice
for i=1:1:max(size(b))
    x(i,1) = b(i,1);
    for j=1:1:i-1
        x(i,1) = x(i,1) - L(i,j) * x(j,1);
    end
    x(i,1) = x(i,1) / L(i,i);
end
end