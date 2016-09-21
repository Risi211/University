function [ x ] = indietro( R,b )
%il vettore colonna x ha la stessa dimensione di b
x = zeros(size(b));
%implementazione pseudo codice
for i=max(size(b)):-1:1
    x(i,1) = b(i,1);
    for j=i+1:1:max(size(b))
        x(i,1) = x(i,1) - R(i,j) * x(j,1);
    end
    x(i,1) = x(i,1) / R(i,i);
end
end