function [ L,R,alfa,beta ] = LR_tridiagonale( a,b,c )
n = max(size(a));
L = zeros(n, n);
R = zeros(n, n);
alfa = zeros(1,n);
beta = zeros(1,n);
alfa(1,1) = a(1,1);
for i=2:1:n
    beta(1,i) = b(1,i) / alfa(1,i-1);
    alfa(1,i) = a(1,i) - beta(1,i)*c(i-1);
end
%creo L
o = ones(n,1);
L = diag(o) + diag(beta(2:n),-1);
R = diag(alfa) + diag(c(1:n-1),1);
end