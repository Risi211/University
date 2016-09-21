%calcolo prima sequenza:
f = zeros(1,71);
f(1,1) = 1;
for in=2:1:71
    %valore della y
    f(1,in) = 2^(in - 1)*(sqrt(1 + f(1,in - 1) / 2^(in - 2)) - 1);
end
in = 1:1:71; %valore della x
figure
plot(in,f,'.');
%calcolo seconda sequenza:
g = zeros(1,71);
g(1,1) = 1;
for in=2:1:71
    %valore della y
    g(1,in) = (2*g(1,in - 1)) / (sqrt(1 + g(1,in - 1) / 2^(in - 2)) + 1); 
end
in = 1:1:71; %valore della x
figure
plot(in,g,'.');