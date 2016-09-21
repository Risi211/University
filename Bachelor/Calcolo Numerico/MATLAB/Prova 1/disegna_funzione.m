clear all
close all
n = input('numero di punti in cui disegnare la funzione: ');
x=linspace(-1,1,n);
f=zeros(n,1);

%for i = 1:n
%    f(i)=1/(1+25*x(i)^2);
%end

f=1./(1+25*x.^2);

figure
plot(x,f);
hold on
plot(x,x,'r^');
xlabel('Ascisse');
ylabel('Ordinate');
title('Grafico');