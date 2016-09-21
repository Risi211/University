import GHC.Exts --serve per sortWith
import Screen

--1) es:
--sono sinonimi, i tipi si definiscono con la
--keyword "data"
type P2d = (Int, Int)
type V2d = (Int, Int)

	
class CShape a where
	perim :: a -> Float --perimetro
	move :: a -> V2d -> a --move
	area :: a -> Float --area

--3)Definire la classe Drawable che estende CShape con funzioni:
class  (CShape a) => Drawable a  where
	draw :: a -> IO()
	drawAll :: [a] -> IO()
	
--Line, Triangle, ...
--sono value constructors, come una funzione restituiscono
--una Shape
data Shape = Line P2d P2d --2 vertici
	| Triangle P2d P2d P2d  --3 vertici
	| Rectangle P2d P2d  --vertice alto sx e basso dx
	| Circle P2d Float --centro e raggio
	| Composition Shape Shape --composizione di figure


instance CShape Shape where
--uso sqrt (fromIntegral x) perchè sqrt prende in input un float, se gli passi un Int
--la fromIntegral lo converte nel tipo di base Num, che contiene anche i Float
	perim (Line p1 p2) = distanceLength p1 p2	
	perim (Triangle p1 p2 p3) = (distanceLength p1 p2) + 
		(distanceLength p2 p3) + 
		(distanceLength p3 p1)
	perim (Rectangle p1 p4) = (distanceLength p1 {-p2-}(getPtAD p1 p4)) + 
		(distanceLength p1 {-p3-}(getPtBS p1 p4)) + 
		(distanceLength {-p3-}(getPtBS p1 p4) p4) + 
		(distanceLength {-p2-}(getPtAD p1 p4) p4)
	perim (Circle _ raggio) = 2 * pi * raggio			
	perim (Composition x y) = (perim x) + (perim y)
	
	move (Line (xa,ya) (xb,yb)) (vx,vy) = Line (xa + vx,ya + vy) (xb + vx,yb + vy)
	move (Triangle (xa,ya) (xb,yb) (xc,yc)) (vx,vy) = Triangle (xa + vx,ya + vy) (xb + vx,yb + vy) (xc + vx,yc + vy)
	move (Rectangle (xa,ya) (xb,yb)) (vx,vy) = Rectangle (xa + vx,ya + vy) (xb + vx, yb + vy)
	move (Circle (xa,ya) raggio) (vx,vy) = Circle (xa + vx,ya + vy) raggio	
	move (Composition x y) v = Composition (move x v) (move y v)
	
	--l'area di una linea è 0
	area (Line _ _) = 0 
	--formula: calcola area del triangolo partendo dai 3 vertici:
	--A,B,C sono i 3 vertici, Ax, Ay coordinate x e y
	--(Ax(By - Cy) + Bx(Cy - Ay) + Cx(Ay - By))/(-2)
	--divide per -2 altrimenti restituisce un numero negativo
	area (Triangle (xa,ya) (xb,yb) (xc,yc)) = (fromIntegral (xa*(yb - yc) + xb*(yc - ya) + xc*(ya - yb)))/(-2)
	--base * altezza
	--p1p2 * p1p3
	area (Rectangle p1 p4) = (distanceLength p1 {-p2-}(getPtAD p1 p4)) *
		(distanceLength p1 {-p3-}(getPtBS p1 p4))
	area (Circle _ raggio) = pi * (raggio^2)
	area (Composition x y) = (area x) + (area y)

--calcola la distanza tra 2 punti
distanceLength :: P2d -> P2d -> Float
distanceLength (xa,ya) (xb,yb) = sqrt( fromIntegral ((xb - xa)^2 + (yb - ya)^2) )

--dati i 2 punti alto a sinistra e basso a destra del rettangolo, ritorna il punto in alto a destra
getPtAD :: P2d -> P2d -> P2d
getPtAD (xa, ya) (xb,yb) = (xb,ya)

--dati i 2 punti alto a sinistra e basso a destra del rettangolo, ritorna il punto in basso a sinistra
getPtBS :: P2d -> P2d -> P2d
getPtBS (xa, ya) (xb,yb) = (xa,yb)


--2) Implementare poi le seguenti funzioni, 
--usando funzioni high-order: 

--2a) moveShapes
-- data una lista di figure, computa la lista di figure 
--traslate di un vettore dv
moveShapes :: [Shape] -> V2d -> [Shape]
moveShapes x v = map (\p -> move p v) x

--2b) inBBox
--data una lista di figure, computa la lista di figure 
--contenute nel bounding box p1 p2, 
--dove p1 è il vertice in alto, a sinistra 
--e p2 è quello in basso a destra
inBBox :: [Shape] -> P2d -> P2d -> [Shape]
inBBox x p1 p2 = filter (\s -> isContained s p1 p2) x
--s è la shape della lista, alla quale applica il filtro
--se è True, rimane nella lista di ritorno, altrimenti no
--il filtro è formato dalla funzione isContained ...
	
--funzione ausiliaria
--data una Shape, controlla se è dentro il box passato
isContained :: Shape -> P2d -> P2d -> Bool
isContained (Line (xa,ya) (xb,yb)) (x1,y1) (x2,y2) 
	| checkP1 && checkP2 = True --se entrambi i punti della line appartengono al box => True
	| otherwise = False
	where
	checkP1 = xa >= x1 && xa <= x2 && ya <= y1 && ya >= y2
	checkP2 = xb >= x1 && xb <= x2 && yb <= y1 && yb >= y2

isContained (Triangle (xa,ya) (xb,yb) (xc,yc)) (x1,y1) (x2,y2) 
	| checkP1 && checkP2 && checkP3 = True --
	| otherwise = False
	where
	checkP1 = xa >= x1 && xa <= x2 && ya <= y1 && ya >= y2
	checkP2 = xb >= x1 && xb <= x2 && yb <= y1 && yb >= y2
	checkP3 = xc >= x1 && xc <= x2 && yc <= y1 && yc >= y2

isContained (Rectangle (xa,ya) (xb,yb)) (x1,y1) (x2,y2) 
	| checkP1 && checkP2 = True --
	| otherwise = False
	where
	checkP1 = xa >= x1 && xa <= x2 && ya <= y1 && ya >= y2
	checkP2 = xb >= x1 && xb <= x2 && yb <= y1 && yb >= y2
	
isContained (Circle (xa,ya) raggio) (x1,y1) (x2,y2) 
	| checkLeft && checkRight && checkTop && checkBottom = True --
	| otherwise = False
	where
	checkLeft = (fromIntegral xa) - raggio >= fromIntegral x1
	checkRight = (fromIntegral xa) + raggio <= fromIntegral x2
	checkTop = (fromIntegral ya) + raggio <= fromIntegral y1
	checkBottom = (fromIntegral ya) - raggio >= fromIntegral y2

isContained (Composition s1 s2) p1 p2
	| (isContained s1 p1 p2) && (isContained s2 p1 p2) = True --
	| otherwise = False	
	
--debug
instance Show Shape where
	show (Line p1 p2) = show p1 ++ show p2
	show (Triangle p1 p2 p3) = show p1 ++ show p2 ++ show p3
	show (Rectangle p1 p2) = show p1 ++ show p2
	show (Circle p raggio) = show p ++ "-raggio = " ++ show raggio
	show (Composition s1 s2) = show s1 ++ "---" ++ show s2


--2c)maxArea
--data una lista di figure, determina la figura 
--con area maggiore
maxArea :: [Shape] -> Shape
--la head prende il primo elemento della lista ritornata da filter
--filter prende una shape dalla lista, se l'area
--della shape è uguale all'area della shape maggiore
--allora rimane nella lista
--l'area numerica maggiore la calcolo con la foldr
maxArea x = head (filter (\s -> area(s) == (foldr (\sh a -> if (area(sh) > a) then area(sh) else a) 0 x) ) x)


--2d) makeShapeTree
--data una lista di figure, 
--costruisce un albero binario di ricerca 
--con le figure ordinate lungo l'asse x
data BSTree a = Nil | Node a (BSTree a) (BSTree a) deriving Show

--la sortWith ordina la lista di Shape secondo il parametro
--"orderable" passato, in questo caso la coordinata x minima
makeShapeTree :: [Shape] -> BSTree Shape
makeShapeTree x = buildBSTree ( sortWith (\s -> getMinX(s)) x )

--restituisce la coordinata x minima data ogni figura
getMinX :: Shape -> Int
getMinX (Line (xa,_) (xb,_)) = min xa xb
getMinX (Triangle (xa,_) (xb,_) (xc,_)) = min (min xa xb) xc
getMinX (Rectangle (xa,_) (xb,_)) = xa --è il punto in alto a sinistra
getMinX (Circle (xa,_) raggio) = xa - truncate(raggio)
getMinX (Composition a b) = min (getMinX a) (getMinX b)

--sono già ordinate
buildBSTree :: [Shape] -> BSTree Shape
buildBSTree [] = Nil
buildBSTree (x:[]) = Node x Nil Nil
--prendo la metà della lista, quello è il nodo padre
--ricorsivamente costruisco i figli con la prima metà
--della lista per il figlio sinistro 
--e la seconda metà per il figlio destro
buildBSTree x = Node (x !! (div(length x) 2)) (buildBSTree (take (div (length x) 2) x)) (buildBSTree (drop ((div (length x) 2) + 1) x))


--3) computer graphics

instance Drawable Shape where
	draw (Line p1 p2) = drawLine p1 p2 >> goto (0,40)
	draw (Triangle p1 p2 p3) = drawLine p1 p2 >> drawLine p2 p3 >> drawLine p1 p3 >> goto (0,40)
	draw (Rectangle p1 p4) = drawLine p1 {-p2-}(getPtAD p1 p4) >> drawLine p1 {-p3-}(getPtBS p1 p4) >> drawLine {-p3-}(getPtBS p1 p4) p4 >> drawLine {-p2-}(getPtAD p1 p4) p4 >> goto (0,40)
	draw (Circle c raggio) = drawCircle c raggio >> goto (0,40)
	draw (Composition s1 s2) = draw s1 >> draw s2
	
	drawAll [] = return()
	drawAll (x:xs) = draw x >> drawAll xs
	
--stampa una linea dati 2 punti, secondo l'algoritmo di Bresenham
--se i 2 punti sono sulla stessa linea, non applica l'algoritmo di Bresenham
--ma genera la lista dei punti da stampare direttamente
--con le list comprehension.
--una volta che ho la lista dei punti, li stampo tutti
--con il foreach e la printPoint
drawLine :: P2d -> P2d -> IO()
drawLine (x1,y1) (x2,y2)
	| (x1 == x2) && (y1 >= y2) = foreach ([(i,j) | i <- [x1], j <- [y2..y1]]) printPoint
	| (x1 == x2) && (y1 < y2) = foreach ([(i,j) | i <- [x1], j <- [y1..y2]]) printPoint
	| (y1 == y2) && (x1 < x2) = foreach ([(i,j) | i <- [x1..x2], j <- [y1]]) printPoint
	| (y1 == y2) && (x1 >= x2) = foreach ([(i,j) | i <- [x2..x1], j <- [y1]]) printPoint
	| abs(x2 - x1) < abs(y2 - y1) = foreach (swapFor (x1,y1) (x2,y2) d1 cont1) printPoint
	| otherwise = foreach (cicloFor (x1,y1) (x2,y2) d2 cont2) printPoint
	where
	d1 = 2 * abs(x2 - x1) + abs(y2 - y1)
	d2 = 2 * abs(y2 - y1) + abs(x2 - x1)
	cont1 = abs(y2 - y1)
	cont2 = abs(x2 - x1)
	
--stampo solamente il centro e i 4 punti relativi all'asse x e y
drawCircle :: P2d -> Float -> IO()
drawCircle (x,y) raggio = do
	writeAt (x,y) "*"
	writeAt (x + truncate(raggio) ,y) "*"
	writeAt (x - truncate(raggio),y) "*"
	writeAt (x,y + truncate(raggio)) "*"
	writeAt (x,y - truncate(raggio)) "*"
	
	
foreach :: [a] -> (a -> IO ()) -> IO ()
foreach [] _ = return ()
foreach (x:xs) act = act x >> foreach xs act

printPoint :: P2d -> IO()
printPoint p = writeAt p "-"

--implementa l'algoritmo di Bresenham con lo swap
swapFor :: P2d -> P2d -> Int -> Int -> [P2d]
swapFor _ (x2,y2) _ 0 = [(x2,y2)] --ultimo punto
--fa deltaY iterazioni
swapFor (x1,y1) (x2,y2) d cont
	| d > 0 = (x1,y1) : swapFor (xNew,yNew) (x2,y2) dNew1 (cont - 1)
	| otherwise = (x1,y1) : swapFor ((xNew - q),yNew) (x2,y2) dNew2 (cont - 1)
	where
	xNew = x1 + q
	yNew = y1 + s
	dNew1 = d + 2 * (a - b)
	dNew2 = d + (2 * a)
	q = if (x1 > x2) then (-1) else (1)
	s = if (y1 > y2) then (-1) else (1)
	a = abs deltaY
	b = abs deltaX
	deltaX = (y2 - y1) 
	deltaY = (x2 - x1)


--implementa l'algoritmo di Bresenham senza swap
cicloFor :: P2d -> P2d -> Int -> Int -> [P2d]
cicloFor _ (x2,y2) _ 0 = [(x2,y2)] --ultimo punto
--fa deltaY iterazioni
cicloFor (x1,y1) (x2,y2) d cont
	| d > 0 = (x1,y1) : cicloFor (xNew,yNew) (x2,y2) dNew1 (cont - 1)
	| otherwise = (x1,y1) : cicloFor (xNew,y1) (x2,y2) dNew2 (cont - 1)
	where
	xNew = x1 + q
	yNew = y1 + s
	dNew1 = d + 2 * (a - b)
	dNew2 = d + (2 * a)
	q = if (x1 > x2) then (-1) else (1)
	s = if (y1 > y2) then (-1) else (1)
	a = abs deltaY
	b = abs deltaX
	deltaX = (x2 - x1)
	deltaY = (y2 - y1)

	
{- --Test:

-----------move-------------

move (Circle (0,0) 2) (1,1)
output: (1,1), raggio = 2.0

move(Rectangle  (13,26) (45,13)) (1,1)
output: (14,27) (46,14)

move(Triangle (10,12) (23,30) (52,15)) (1,1)
output: (11,13),(24,31)(53,16)

move (Line (1,1) (2,1)) (1,1)
output: (2,2),(3,2)

move(Composition (Circle (0,0) 2) (Rectangle (13,26) (45,13))) (1,1)
output: (1,1), raggio = 2.0---(14,27) (46,14)

-----------perim-------------

perim (Line (1,1) (1,2))
output: 1.0

perim (Triangle (10,12) (23,30) (52,15))
output: 96.960266

(rif: http://www.mathopenref.com/coordrectareaperim.html)
perim (Rectangle  (13,26) (45,13))
output: 90.0

perim (Circle (0,0) 2)
output: 12.566371

perim (Composition (Circle (0,0) 2) (Rectangle (13,26) (45,13)))
output: 102.56637

-----------area-------------

area (Line (1,1) (5,7))
output: 0.0

(Rif: http://www.mathopenref.com/coordtrianglearea.html)
area (Triangle (10,12) (23,30) (52,15))
output: 358.5

area (Rectangle (13,26) (45,13))
output: 416.0

area (Circle (0,0) 2)
output: 12.566371

area (Composition (Circle (0,0) 2) (Rectangle (13,26) (45,13)))
output: 428.56638

-----------moveShapes-------------

moveShapes [(Rectangle (13,26) (45,13)), (Triangle (10,12) (23,30) (52,15)),(Circle (0,0) 2)] (1,1)
output: [(14,27)(46,14),(11,13)(24,31)(53,16),(1,1)-raggio = 2.0]

-----------inBBox-------------

inBBox [(Rectangle (13,26) (45,13)), (Triangle (10,12) (23,30) (52,15)),(Circle (0,0) 2)] (-2,4) (4,-2)
output: [(0,0)-raggio = 2.0] --solo il cerchio

inBBox [(Rectangle (13,26) (45,13)), (Triangle (10,12) (23,30) (52,15)),(Circle (0,0) 2)] (10,30) (52,12)
output: [(13,26)(45,13),(10,12)(23,30)(52,15)] --solo rettangolo e triangolo

-----------maxArea-------------

maxArea [(Rectangle (13,26) (45,13)), (Triangle (10,12) (23,30) (52,15)),(Circle (0,0) 2)]
output: (13,26) (45,13)

maxArea [(Circle (0,0) 2), (Rectangle (13,26) (45,13)), (Triangle (10,12) (23,30) (52,15))]
output: (13,26) (45,13)

maxArea [(Circle (0,0) 2), (Triangle (10,12) (23,30) (52,15))]
output: (10,12),(23,30)(52,15)

-----------buildBSTree-------------

prende in input una lista ordinata.

In questo esempio, è come se la lista fosse [0,1,2,3,4]
Il primo Node è la root dell'albero

buildBSTree [(Line (0,0) (0,0)), (Circle (1,1) 2), (Line (2,2) (2,2)), (Line (3,3) (3,3)), Line (4,4) (4,4)]
output: Node (2,2)(2,2) (Node (1,1)-raggio = 2.0 (Node (0,0)(0,0) Nil Nil) Nil) (Node (4,4)(4,4) (Node (3,3)(3,3) Nil Nil) Nil)

-----------makeShapeTree-------------

prende una lista di shape, la ordina secondo la x minima
e costruisce poi un albero binario di ricerca

makeShapeTree  [(Line (2,2) (2,2)), (Line (1,1) (1,1)), Circle (1,1) 2]
output: Node (1,1)(1,1) (Node (1,1)-raggio = 2.0 Nil Nil) (Node (2,2)(2,2) Nil Nil)

-}




{-
Algoritmo della linea di Bresenham
(preso da wikipedia)

swap = 0;
DX = x2 - x1;
DY = y2 - y1;

//siccome scambio DY e DX ho sempre DX>=DY allora per sapere quale coordinata occorre cambiare uso una variabile
if (abs(DX) < abs(DY)) {
   swap(DX, DY);
   swap = 1;
}

//per non scrivere sempre i valori assoluti cambio DY e DX con altre variabili
a = abs(DY);
b = -abs(DX);

//assegna le coordinate iniziali
x = x1;
y = y1;

//il nostro valore d0
d = 2 * a + b;

//s e q sono gli incrementi/decrementi di x e y
q = 1;
s = 1;
if (x1 > x2) q = -1;
if (y1 > y2) s = -1;
disegna_punto(x, y);
disegna_punto(x2, y2);
for (k = 0; k < -b; k+=1) {
   if (d > 0) {
       x= x + q; y= y + s;
       d= d + 2 * (a + b);
   }
   else {
       x = x + q;
       if (swap==1) { y = y + s; x = x - q; }
       d = d + 2 * a;
   }
   disegna_punto(x, y);
}

-}