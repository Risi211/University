function zero = bisezione(a,b,f, emax)
k = 1;
errore = 2*emax;
while(errore > emax)
    c = a + (b - a) / 2; %vedi nelle dispense meglio calcolarlo così
    c1 = feval(f,c);
    if(c1 == 0)
        zero = c; %fine
        return
    end
   %controllo segno
   a1 = feval(f,a);
   
   b1 = feval(f,b);
   
   if((a1 * c1) < 0)
        b = c;
   else %((b1 * c1) < 0)
       a = c;
   end
   %se f(a) e f(b) sono concordi (stesso segno) errore
   if(a1 > 0 && b1 > 0)
       
       disp('errore: il metodo di bisezione non si può applicare ')
       return;
   end
   %aggiorno errore
   errore = abs(b - a)/(2^(k - 1));
   k = k + 1;
   zero = c;                          
end
