function pn = schema_horner(d,z,x)
m = length(z);
n = length(x);
pn = zeros(1,m);
for k=1:1:m
    pn(k) = d(n);
    for i=n-1:-1:1
        pn(k) = d(i) + (z(k) - x(i)) * pn(k);
    end
end
end