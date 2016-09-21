function [z,c] = hilbert()
z = zeros(14,1);
c = zeros(14,1);
    for n=2:1:15
        H = hilb(n);
        b = zeros(n,1);
        %calcolo termine noto
        for i=1:1:n
            somma = 0;
            for j=1:1:n
                somma = somma + H(i,j);
            end
            b(i,1) =  somma;
        end
        x = gauss_pivoting(H,b);
        reale = ones(n,1);
        z(i,1) = norm(reale - x) / norm(reale);
        c(i,1) = cond(H);
    end
end