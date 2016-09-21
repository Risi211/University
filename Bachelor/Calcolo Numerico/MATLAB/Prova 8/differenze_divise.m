function d = differenze_divise(n,x,y)
d = zeros(n + 1);
for k=0:1:n-1
    d(k+1) = y(k+1);
end

for i=1:1:n
    for k=n-1:-1:i
        d(k + 1) = (d(k + 1) - d(k)) / (x(k + 1) - x(k + 1 - i));
    end
end
end