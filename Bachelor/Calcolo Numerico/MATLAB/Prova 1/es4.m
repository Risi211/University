%creo vettori
s = zeros(1,100);
p = zeros(1,100);
q = zeros(1,100);
%primi elementi della successione
s(1,1) = 1;
s(1,2) = 1/3;
p(1,1) = 1;
p(1,2) = 1/3;
q(1,1) = 1;

%calcolo la successione 1, 1/3, 1/9, ...
for in=3:1:100
    s(1,in) = (1/3)^(in - 1);
end
%calcolo la successione di sopra generata con la prima relazione
for in=3:1:100
    p(1,in) = 10/3*p(1, in-1) - p(1,in-2);
end
%calcolo la successione di sopra con la seconda relazione
for in=2:1:100
    q(1,in) = 1/3*q(1, in-1);
end
%calcolo errore per ogni valore di entrambe le relazioni
err1 = (p - s)./s;

err2 = (q - s)./s;