x=linspace(-2,3,100);
f=zeros(1,100);

%funzione da studiare
f=x.^3 - 1.9*x.^2 - 1.2 .* x + 2.5;

%crea la retta y = 0 che va da -2 a 3
x1 = zeros(1,2);
x1(1,1) = -2;
x1(1,2) = 3;
x2 = zeros(1,2);

figure
plot(x,f);
hold on
%stampa retta y = 0
plot(x1,x2,'r');
