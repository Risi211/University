function [ muno,minf ] = norma_mat( vet )
% Restituisce la norma uno dato un vettore in input
muno = max(sum(abs(vet)));    %somma per colonne
minf = max(sum(abs(vet),2));  %somma per righe
end

