function [ vuno,vinf ] = norma_uno( vet )
% Restituisce la norma uno dato un vettore in input
vuno = sum(abs(vet));
vinf = max(abs(vet));
end

