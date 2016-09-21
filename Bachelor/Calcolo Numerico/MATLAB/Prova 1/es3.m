format long
ni = [1e-1, 1e-3, 1e-6, 1e-9, 1e-12, 1e-15, 1e-16, 1e-18];
x = 5;
y = 5 - ni;
err = ((x - y) - ni) ./ (ni)