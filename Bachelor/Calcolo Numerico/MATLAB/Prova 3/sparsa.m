function [ prod ] = sparsa( r, c, i , vet )
% Restituisce la norma uno dato un vettore in input

% vettore risultato, bisogna controllare col vettore i se bisogna fare
% evenuali somme tra gli elementi del vettore tmp
tmp = r.*vet(c);
count = 1;
% vettore che contiene il risultato finale
prod = zeros(1,length(i)-1);
% scorre il vettore i
for k=1:1:length(i)-1
    stmp = 0;
    % controlla se la differenza dei 2 elementi consecutivi è > 1
    if i(k+1) - i(k) > 1
        % somma tanti elementi quant'è la differenza
        for j=count:1:count + i(k+1)-i(k) - 1
            stmp = stmp + tmp(j);       
        end
        count = count + i(k+1)-i(k);
        prod(k) = stmp;
    else
        prod(k) = tmp(count);
        count = count + 1;
    end
end
end

