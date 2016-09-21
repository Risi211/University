function [ x ] = tridiagonale( n )
a = zeros(1,n) + 2;
b = zeros(1,n) - 1;
c = zeros(1,n) - 1;
f = zeros(1,n); %termine noto
f(1) = 1;
f(n) = 1;
x = zeros(1,n);
[L,R,alfa,beta] = LR_tridiagonale(a,b,c);
%trovo il vettore y
%L*b=y
y = zeros(1,n);
y(1,1) = f(1,1);
for i=2:1:n
    y(1,i) = f(1,i) - beta(1,i)*y(1,i-1);
end
%DEBUG
%y_mat=L\f'      y fatto da matlab
%x_mat=R\y_mat   x fatto da matlab
%y               y mia

%R*x=y
%trovo il vettore x
x(1,n) = y(1,n) / alfa(1,n);
for i=n-1:-1:1
    x(1,i) = (y(1,i) - c(1,i)*x(1,i+1))/alfa(1,i);
end

end