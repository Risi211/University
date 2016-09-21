function [trapezi,simpson, ktrapezi, ksimpson] = calcola_integrale(f,a,b,err)
%h = 0.01; %passo
%calcolo primo integrale
h = (b - a);
x = a:h:b;
y = feval(f,x);
area1 = h*(y(1)/2 + sum(y(2:end-1)) + y(end)/2);
%calcolo secondo integrale
h = (b - a)/2;
x = a:h:b;
y = feval(f,x);
area2 = h*(y(1)/2 + sum(y(2:end-1)) + y(end)/2);
%calcolo errore
errore = abs(area2 - area1);
k = 2;
%ciclo finchè non si trova un errore accettabile
while(errore > err)
area1 = area2;
h = (b - a)/2^k;
x = a:h:b;
y = feval(f,x);
area2 = h*(y(1)/2 + sum(y(2:end-1)) + y(end)/2);
%calcolo errore
errore = abs(area2 - area1);
k = k + 1;
end

trapezi = area1;
ktrapezi = k;

%stessa cosa per simpson
%calcolo primo integrale
h = (b - a);
x = a:h:b;
y = feval(f,x);
area1 = h/3*(y(1) + 4*sum(y(2:2:end-1)) + 2*sum(y(3:2:end-1)) + y(end));
%calcolo secondo integrale
h = (b - a)/2;
x = a:h:b;
y = feval(f,x);
area2 = h/3*(y(1) + 4*sum(y(2:2:end-1)) + 2*sum(y(3:2:end-1)) + y(end));
%calcolo errore
errore = abs(area2 - area1);
k = 2;
%ciclo finchè non si trova un errore accettabile
while(errore > err)
area1 = area2;
h = (b - a)/2^k;
x = a:h:b;
y = feval(f,x);
area2 = h/3*(y(1) + 4*sum(y(2:2:end-1)) + 2*sum(y(3:2:end-1)) + y(end));
%calcolo errore
errore = abs(area2 - area1);
k = k + 1;
end

simpson = h/3*(y(1) + 4*sum(y(2:2:end-1)) + 2*sum(y(3:2:end-1)) + y(end));
ksimpson = k;

end