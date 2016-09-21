function [z,c] = mgc()
z = zeros(5,1);
c = zeros(5,1);
cont = 1;
    for n=3:2:11
        M = magic(n);
        b = zeros(n,1);
        %calcolo termine noto
        for i=1:1:n
            somma = 0;
            for j=1:1:n
                somma = somma + M(i,j);
            end
            b(i,1) =  somma;
        end
        x = gauss_pivoting(M,b);
        reale = ones(n,1);
        z(cont,1) = norm(reale - x) / norm(reale);
        c(cont,1) = cond(M);
        cont = cont + 1;
    end
end