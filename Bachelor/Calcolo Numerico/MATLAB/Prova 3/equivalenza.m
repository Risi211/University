function [ str1,str2,str3,str4,str5,str6,str7,str8,str9,str10 ] = equivalenza(n)
%crea 10 matrici random nxn
m1 = round(rand(n).*100);
m2 = round(rand(n).*100);
m3 = round(rand(n).*100);
m4 = round(rand(n).*100);
m5 = round(rand(n).*100);
m6 = round(rand(n).*100);
m7 = round(rand(n).*100);
m8 = round(rand(n).*100);
m9 = round(rand(n).*100);
m10 = round(rand(n).*100);

%per ogni matrice calcola la norma 1 e la norma infinito, infine controlla
%la relazione di equivalenza
%a è la norma 1, b è la norma infinito
[a1,b1] = norma_mat(m1);
[a2,b2] = norma_mat(m2);
[a3,b3] = norma_mat(m3);
[a4,b4] = norma_mat(m4);
[a5,b5] = norma_mat(m5);
[a6,b6] = norma_mat(m6);
[a7,b7] = norma_mat(m7);
[a8,b8] = norma_mat(m8);
[a9,b9] = norma_mat(m9);
[a10,b10] = norma_mat(m10);

%stampa tutte le matrici generate
m1
m2
m3
m4
m5
m6
m7
m8
m9
m10

%confonta la relazione di equivalenza per ogni matrice
str1 = strcat('1/n * norma inf = ',num2str(b1*(1/n)),' <= norma 1 = ',num2str(a1),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b1));
str2 = strcat('1/n * norma inf = ',num2str(b2*(1/n)),' <= norma 1 = ',num2str(a2),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b2));
str3 = strcat('1/n * norma inf = ',num2str(b3*(1/n)),' <= norma 1 = ',num2str(a3),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b3));
str4 = strcat('1/n * norma inf = ',num2str(b4*(1/n)),' <= norma 1 = ',num2str(a4),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b4));
str5 = strcat('1/n * norma inf = ',num2str(b5*(1/n)),' <= norma 1 = ',num2str(a5),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b5));
str6 = strcat('1/n * norma inf = ',num2str(b6*(1/n)),' <= norma 1 = ',num2str(a6),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b6));
str7 = strcat('1/n * norma inf = ',num2str(b7*(1/n)),' <= norma 1 = ',num2str(a7),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b7));
str8 = strcat('1/n * norma inf = ',num2str(b8*(1/n)),' <= norma 1 = ',num2str(a8),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b8));
str9 = strcat('1/n * norma inf = ',num2str(b9*(1/n)),' <= norma 1 = ',num2str(a9),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b9));
str10 = strcat('1/n * norma inf = ',num2str(b10*(1/n)),' <= norma 1 = ',num2str(a10),' <= n^(1/2)*norma inf = ',num2str(sqrt(n)*b10));

end