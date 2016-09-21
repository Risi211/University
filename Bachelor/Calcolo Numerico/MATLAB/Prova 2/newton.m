%[a,b] è l'intervallo
%x è il punto iniziale
%f è la funzione, d è la derivata
%emax è l'errore assoluto
function zero = newton(a,b,x,f,d,emax)
%parte il timer
tic
%calcolo f(x)
y = feval(f,x);
%calcolo f'(x)
m = feval(d,x);
%calcolo xk + 1
k = x - y / m;
%controllo se sono finito fuori dall'intervallo di studio
if(k < a || k > b)
    disp('errore: la x è fuori dall intervallo di studio');
    toc %fine timer
    return
end
%ciclo, finisce quando la differenza fra xk + 1 e xk in modulo è <
%dell'errore massimo
while(abs(x - k) > emax)
    x = k; % xk + 1 = xk
    %calcolo f(x)
    y = feval(f,x);
    %calcolo f'(x)
    m = feval(d,x);
    %calcolo xk + 1
    k = x - y / m;
    %controllo se sono finito fuori dall'intervallo di studio
    if(k < a || k > b)
        disp('errore: la x è fuori dall intervallo di studio');
        toc %fine timer
        return
    end
end
zero = k;
toc %fine timer