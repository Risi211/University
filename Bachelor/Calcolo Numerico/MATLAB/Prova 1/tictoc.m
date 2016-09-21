n = input('nserisci numero di componenti del vettore: ')
disp('calcolo con allocazione automatica')
x(1) = 5;
h = 0.5;
tic
for i = 2:n
    x(i) =  x (i - 1)+h;
end
toc

disp('calcolo con allocazione manuale')

y = zeros(n,1);

y(1) = 5;
tic
for i = 2:n
    y(i) =  y (i - 1)+h;
end
toc