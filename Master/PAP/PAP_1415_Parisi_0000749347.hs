import Data.List --nub, rimuove duplicati da una lista,
--mantenendo l'ordine degli elementi, mi serve nella occ

data Elem = Dot | Star

--1) conta il numero di elementi Star presenti nella lista
-- passata come parametro
countStar :: [Elem] -> Int
countStar [] = 0
countStar (Star:xs) = 1 + countStar(xs)
countStar (Dot:xs) = countStar(xs)
{- Test:
countStar [] -> 0
countStar [Dot, Dot] -> 0
countStar [Dot, Star, Star, Dot, Star ] -> 3
-}


--2)restituisce una rappresentazione testuale della sequenza
--, ove Dot è rappresentato dal carattere ‘.’ 
--e Star dal carattere ‘*’. Questa funzione è utile 
--ogni volta si voglia stampare una lista di Elem. 
printableSeq :: [Elem] -> [Char]
printableSeq [] = []
printableSeq (Star:xs) = '*' : printableSeq(xs)
printableSeq (Dot:xs)  = '.' : printableSeq(xs)
{- Test:
printableSeq [] -> “”
printableSeq [Dot, Dot] -> “..”
printableSeq [Dot, Star, Star, Dot, Star ] -> “.**.*”
-}


--3) restituisce una sequenza pari a quella passata l 
--in cui ogni valore Dot è sostituito con uno Star 
--e viceversa.
swapSeq :: [Elem] -> [Elem]
swapSeq [] = []
swapSeq (Star:xs) = Dot : swapSeq(xs)
swapSeq (Dot:xs)  = Star : swapSeq(xs)
{- Test:
printableSeq (swapSeq []) -> ""
printableSeq (swapSeq [Dot, Dot]) -> "**"
printableSeq (swapSeq [Dot, Star, Star, Dot, Star ]) -> "*..*."
-}


--4)data una lista di Elem restituisce una lista in cui 
--le sequenze di 1 o più Dot sono sostituite da un solo Dot.
zipSeq :: [Elem] -> [Elem]
zipSeq [] = []
zipSeq (Star:xs) = Star : zipSeq(xs)
zipSeq (Dot:xs)  = Dot : jumpDot(xs)

--funzione ausiliaria
--serve per saltare tutti i Dot consecutivi, quando incontra
--nuovamente una star ritorna alla funzione zipSeq:
jumpDot :: [Elem] -> [Elem]
jumpDot [] = []
jumpDot (Dot:xs) = jumpDot(xs)
jumpDot (Star:xs) = Star : zipSeq(xs)
{- Test:
zipSeq [] -> []
zipSeq [Dot, Dot] -> [Dot]
zipSeq [Dot, Star, Star, Dot, Star ] -> [Dot, Star, Star, Dot, Star ]
zipSeq [Dot, Dot, Dot, Dot] -> [Dot]
zipSeq [Dot, Dot, Star, Star, Dot, Dot, Dot, Star, Dot ] -> [Dot, Star, Star, Dot, Star, Dot ]
-}


--5) computa la lunghezza della sequenza di Star più lunga
maxStarSeq :: [Elem] -> Int
maxStarSeq [] = 0
maxStarSeq (Dot:xs) = maxStarSeq(xs)
maxStarSeq (Star:xs)
--se ho una Star, e la lunghezza della sequenza attuale 
--di Star è > di tutte le sequenze di Star successive, 
--allora ritorna in output questa lunghezza di Star
	| starLength(xs) >= maxStarSeq(xs) = 1 + starLength(xs)
--altrimenti va avanti e cerca altre sequenze di Star
	| otherwise = maxStarSeq(xs)

--funzione ausiliaria, calcola la lunghezza di una sequenza
--locale di Star. Quando incontra un Dot si ferma
starLength :: [Elem] -> Int
starLength [] = 0
starLength (Dot:xs)  = 0
starLength (Star:xs) = 1 + starLength(xs)
{- Test:
maxStarSeq [] -> 0
maxStarSeq [Dot, Dot] -> 0
maxStarSeq [Dot, Star, Star, Dot, Star ] -> 2
-}


--6) date due liste di Elem restituisce vero se le liste 
--contengono le medesime sequenze di Star in ordine, 
--a prescindere dal numero di Dot in mezzo
matchSeq :: [Elem] -> [Elem] -> Bool
matchSeq [] [] = True
matchSeq [] (Dot:ys) = matchSeq [] ys  --i Dot non vengono considerati
matchSeq (Dot:xs) [] = matchSeq xs []  --i Dot non vengono considerati
matchSeq [] (Star:ys) = False          --sequenze di Star diverse
matchSeq (Star:xs) [] = False          --sequenze di Star diverse
matchSeq (Dot:xs) (Dot:ys) = matchSeq xs ys --i Dot non vengono considerati
matchSeq (Dot:xs) (Star:ys) = matchSeq xs (Star:ys) --il Dot non viene considerati
matchSeq (Star:xs) (Dot:ys) = matchSeq (Star:xs) ys --il Dot non viene considerati
matchSeq (Star:xs) (Star:ys)
--se sono entrambe Star, controllo se la lunghezza delle Star è uguale
--in questo caso si passa alla sequenza successiva 
	| (starLength xs == starLength ys) = matchSeq (jumpStar6 xs) (jumpStar6 ys)
--altrimenti ritorna False e si ferma la ricorsione	
	| otherwise = False								   --altrimenti False

--funzione ausiliaria:
--salta le Star già controllate nella stessa sequenza:
jumpStar6 :: [Elem] -> [Elem]
jumpStar6 [] = []
jumpStar6 (Star:xs) = jumpStar6(xs)
jumpStar6 (Dot:xs) = Dot:xs
{- Test:
matchSeq [] [Dot, Star] -> False
matchSeq [Star, Dot, Dot, Star, Star, Dot] [Star, Dot, Star, Star, Dot, Dot]  -> True
matchSeq [Dot, Dot, Star, Star, Dot, Star, Dot] [Star, Star, Dot, Star]  -> True
matchSeq [Star, Star, Dot, Star] [Star, Star, Dot, Star, Star]  -> False
-}


--7) determina una lista delle tuple a 2 elementi, 
--in cui il primo indica la lunghezza di una sequenza 
--di Star presente nella lista e il secondo la lista 
--delle posizioni in cui tale sequenza compare nella lista
-- (la prima posizione è la numero 1). 
occ :: [Elem] -> [(Int, [Int])]
occ [] = []
occ x = getStarSequenceIndex x (nub(getStarSequenceLength x)) 1

--funzione ausiliaria: getStarSequenceLength
--calcola il numero di star presenti in ogni sequenza
--di Star della lista originale.
--Ritorna una lista di interi, in cui il primo elemento
--indica quante star ha la prima sequenza,
--il secondo elemento la seconda, ...
--es: 
--getStarSequenceLength [Star,Dot,Star,Star,Dot,Star,Star]
--output: [1,2,2]
getStarSequenceLength :: [Elem] -> [Int]
getStarSequenceLength [] = []
--se ho una Star, restituisce la lunghezza di quella sequenza
--locale di Star, poi le salta e concatena la lunghezza
--delle sequenze successive
getStarSequenceLength (Star:xs) = (1 + starLength(xs)) : getStarSequenceLength (jumpStar6(xs))
getStarSequenceLength (Dot:xs) = getStarSequenceLength(xs)

--funzione ausiliaria: getStarSequenceIndex
--ritorna in output la lista di tuple richiesta dall'esercizio
--in input prende: 
--  la lista di Elem di partenza,
--  la lista della getStarSequenceLength senza elementi doppi (si usa il nub)
--  l'indice di partenza (in questo caso 1)
getStarSequenceIndex :: [Elem] -> [Int] -> Int -> [(Int, [Int])]
--i primi 3 sono pattern del caso base, restituisce la lista vuota
getStarSequenceIndex [] [] _ = []
getStarSequenceIndex x [] _ = []
getStarSequenceIndex [] y _ = []
--se ho un Dot scorro la lista
getStarSequenceIndex (Dot:xs) y index = getStarSequenceIndex xs y (index + 1)
--se ho una Star, ci sono 2 casi:
--a) se la sequenza locale di Star è uguale 
--   al numero di Star della sequenza da controllare,
--   allora restituisce la tupla, dove nel primo elemento c'è
--   il numero di Star della sequenza da controllare,
--   mentre nel secondo elemento c'è l'indice di partenza
--   della sequenza locale di Star, il quale viene concatenato
--   con gli elementi della funzione ausiliaria listIndex,
--   che restituisce gli altri indici di partenza delle altre
--   sequenze locali con la stessa lunghezza di Star
--   Infine, la tuplaviene concatenata con le altre tuple
--   richiamando la funzione ricorsivamente, ma saltando prima
--   le Star della sequenza locale
--b) chiama ricorsivamente la funzione, saltando prima le
--   stelle appartenenti alla sequenza locale di Star
getStarSequenceIndex (Star:xs) (y:ys) index
	| y == (1 + starLength(xs)) = (y,index : listIndex xs y (index + 1) ) : jumpStar2 xs ys (index + 1)
	| otherwise = jumpStar2 xs (y:ys) (index + 1)

--funzione ausiliaria: jumpStar2
--funzione ausiliaria per saltare tutte le Star consecutive
--poi richiamala funzione getStarSequenceIndex
--Per ogni stella che salta aumenta l'indice di scorrimento
--della lista
jumpStar2 :: [Elem] -> [Int] -> Int -> [(Int, [Int])]
jumpStar2 [] [] _ = []
jumpStar2 (Star:xs) y index = jumpStar2 xs y (index + 1)
jumpStar2 (Dot:xs) y index  = getStarSequenceIndex xs y (index + 1)

--funzione ausiliaria: listIndex
--ritorna in output la lista con gli indici di partenza
--di tutte le sequenze di Star di una determinata lunghezza.
--Parametri:
--  lista di elementi da controllare
--  lunghezza della sequenza
--  indice di partenza 
--  (l'indice fa riferimento al primo elemento della lista di Elem,
--  dato che si lavora ricorsivamente, la lista iniziale viene sempre
--  di più ridotta, tengo traccia dell'indice in questo modo)
listIndex :: [Elem] -> Int -> Int -> [Int]
listIndex [] _ _ = []
--se trova un Dot richiama la funzione ricorsivamente,
--deve controllare tutte le possibili sequenze
listIndex (Dot:xs) y index  = listIndex xs y (index + 1)
--se trova una Star:
--a)  se la lunghezza della sequenza locale di Star è uguale
--    al parametro lunghezza, allora ritorna l'indice attuale
--    e richiama la funzione per controllare tutta la lista
--b)  altrimenti salta tutte le Star consecutive prima di
--    richiamare la funzione ricorsivamente 
listIndex (Star:xs) y index 
	| y == (1 + starLength(xs)) = index : listIndex xs y (index + 1)
	| otherwise = jumpStar3 xs y (index + 1)

--funzione ausiliaria: jumpStar3
--funzione ausiliaria per saltare tutte le Star consecutive
--richiama poi listIndex
jumpStar3 :: [Elem] -> Int -> Int -> [Int]
jumpStar3 [] _ _ = []
jumpStar3 (Star:xs) y index = jumpStar3 xs y (index + 1)
jumpStar3 (Dot:xs) y index  = listIndex xs y (index + 1)



--8) conta il numero di elementi Star presenti 
--nell’albero passato come parametro
data BSTree a = Nil | Node a (BSTree a) (BSTree a)
countStarInTree :: BSTree Elem -> Int
countStarInTree Nil = 0
countStarInTree (Node Star l r) = 1 + (countStarInTree l) + (countStarInTree r)
countStarInTree (Node Dot l r) = (countStarInTree l) + (countStarInTree r)
{-Test:
countStarInTree Nil -> 0
countStarInTree (Node Dot Nil Nil )  -> 0
countStarInTree (Node Star (Node Dot Nil Nil) (Node Star Nil Nil))   -> 2
-}



--9) Determina la lunghezza del percorso più profondo, 
-- a partire dalla radice, fino ad un nodo Dot 
--o ad una foglia - composto da soli elementi Star. 
pathTree :: BSTree Elem -> Int
pathTree Nil = 0
--appena trova un Dot ferma la ricorsione
pathTree (Node Dot l r) = 0
--se trova una Star, deve fare 2 controlli:
pathTree (Node Star l r) 
	| (localStarLength l) >= (localStarLength r)  = 1 + (localStarLength l)
	| (localStarLength l) < (localStarLength r)  = 1 + (localStarLength r)

--funzione ausiliaria: localStarLength
localStarLength :: BSTree Elem -> Int
localStarLength Nil = 0
localStarLength (Node Dot l r) = 0
localStarLength (Node Star l r)
	| (localStarLength l) >= (localStarLength r) = (1 + (localStarLength l))
	| otherwise = (1 + (localStarLength r))
	
	
{-
Questo codice trova il percorso di Star più lungo senza 
necessariamente partire dalla radice, la radice può essere anche un Dot
(vedi testBS2)

pathTree :: BSTree Elem -> Int
pathTree Nil = 0
pathTree (Node Dot l r)
	| (pathTree l) >= (pathTree r) = (pathTree l)
	| otherwise = (pathTree r)
pathTree (Node Star l r) 
	| (localStarLength l) >= (localStarLength r) && (localStarLength l) >= (pathTree l) && (localStarLength l) >= (pathTree r) = 1 + (localStarLength l)
	| (localStarLength l) < (localStarLength r) && (localStarLength r) >= (pathTree l) && (localStarLength r) >= (pathTree r) = 1 + (localStarLength r)
	| (pathTree l) >= (pathTree r) = (pathTree l)
	| (pathTree l) < (pathTree r) = (pathTree r)
-}

	
	
--MyTest
{-
testBS :: BSTree Elem
testBS = Node Star (Node Dot (Node Star Nil Nil) Nil) (Node Star Nil Nil)
--countStarInTree testBS
--output: 3
--pathTree (partendo non necessariamente dalla radice) testBS
--output: 2
testBS2 :: BSTree Elem
testBS2 = Node Star (Node Dot (Node Star (Node Star (Node Dot Nil Nil) (Node Star Nil Nil)) Nil) Nil) (Node Star Nil Nil)
--countStarInTree testBS2
--output: 5
--pathTree (partendo non necessariamente dalla radice) testBS2
--output: 3
-}