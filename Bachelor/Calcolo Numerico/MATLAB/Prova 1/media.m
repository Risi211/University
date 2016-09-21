function output=media(n,x)

s=0;
for i = 1:1:n
    s=s+x(i);
end
output = s/n;