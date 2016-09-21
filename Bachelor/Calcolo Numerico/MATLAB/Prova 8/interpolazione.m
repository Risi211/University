function y = interpolazione(n)
clearvars -except n; 
close all; clc
x1 = linspace(-1,1,n);
i = [0:n];
%chebischev
x2 = cos(((1 + 2.*i)./(2*(n + 1)))*pi);
%coppie di punti
y11 = f1(x1);
y12 = f1(x2);
y21 = f2(x1);
y22 = f2(x2);
y31 = f3(x1);
y32 = f3(x2);
%funzione vera con tanti punti
z = linspace(-1,1,1000);
yz1 = f1(z);
yz2 = f2(z);
yz3 = f3(z);
%differenze divise
d11 = differenze_divise(n,x1,y11);
d12 = differenze_divise(n,x2,y12);
d21 = differenze_divise(n,x1,y21);
d22 = differenze_divise(n,x2,y22);
d31 = differenze_divise(n,x1,y31);
d32 = differenze_divise(n,x2,y32);
%polinomio interpolatore
pn11 = schema_horner(d11,z,x1);
pn12 = schema_horner(d12,z,x2);
pn21 = schema_horner(d21,z,x1);
pn22 = schema_horner(d22,z,x2);
pn31 = schema_horner(d31,z,x1);
pn32 = schema_horner(d32,z,x2);
%errore
err11 = abs(pn11 - yz1);
err12 = abs(pn12 - yz1);
err21 = abs(pn21 - yz2);
err22 = abs(pn22 - yz2);
err31 = abs(pn31 - yz3);
err32 = abs(pn32 - yz3);

figure(1)
hold on
plot(z,pn11,'m');
plot(z,yz1,'b--');
plot(x1,y11,'k*');
plot(z,err11,'g');
legend('pn11','y','xp','err1');

figure(2)
hold on
plot(z,pn12,'c');
plot(z,yz1,'b--');
plot(x2,y12,'ko');
plot(z,err12,'r');
legend('pn12','y','xpC','err2');

figure(3)
hold on
plot(z,pn21,'m');
plot(z,yz2,'b--');
plot(x1,y21,'k*');
plot(z,err21,'g');
legend('pn21','y','xp','err1');

figure(4)
hold on
plot(z,pn22,'m');
plot(z,yz2,'b--');
plot(x2,y22,'ko');
plot(z,err22,'r');
legend('pn22','y','xpC','err2');

figure(5)
hold on
plot(z,pn31,'m');
plot(z,yz3,'b--');
plot(x1,y31,'k*');
plot(z,err31,'g');
legend('pn31','y','xp','err1');

figure(6)
hold on
plot(z,pn32,'c');
plot(z,yz3,'b--');
plot(x2,y32,'ko');
plot(z,err32,'r');
legend('pn32','y','xpC','err2');
end