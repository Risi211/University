%ultimo
R = [1,2,3,4,5; 0,3,4,5,6; 0,0,5,6,7; 0,0,0,7,8; 0,0,0,0,9]
b = [15;18;18;15;9]
x = indietro(R,b)
deltab=rand(5,1)*1e-03
bp = b + deltab
x2 = indietro(R,bp)
err = abs((x - x2)./x)
cond(R)