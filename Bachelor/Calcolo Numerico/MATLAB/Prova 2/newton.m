%[a,b] � l'intervallo
%x � il punto iniziale
%f � la funzione, d � la derivata
%emax � l'errore assoluto
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
    disp('errore: la x � fuori dall intervallo di studio');
    toc %fine timer
    return
end
%ciclo, finisce quando la differenza fra xk + 1 e xk in modulo � <
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
        disp('errore: la x � fuori dall intervallo di studio');
        toc %fine timer
        return
    end
end
zero = k;
toc %fine timer