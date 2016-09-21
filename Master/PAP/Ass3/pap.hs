import GHC.Exts --serve per sortWith

data StarSeq = Star StarSeq | End deriving (Show)

--data una lista di StarSeq computa lunghezza e posizione della sequenza più lunga 
getMaxSeq :: [StarSeq] -> (Int,Int)
--l'indice parte da 1
getMaxSeq x = (lenMaxSeq(x),posMaxSeq x (lenMaxSeq(x)) 1)

--lenMaxSeq [(Star(End)),(Star(Star(Star(Star(End)))))]
--4
lenMaxSeq :: [StarSeq] -> Int
lenMaxSeq x = lenSeq(head(filter (\s -> lenSeq(s) == (foldr (\s2 l -> if (lenSeq(s2) > l) then lenSeq(s2) else l) 0 x) ) x))

-- posMaxSeq [(Star(End)),(Star(Star(Star(Star(End)))))] 4 1
--2
posMaxSeq :: [StarSeq] -> Int -> Int -> Int
posMaxSeq (x:xs) maxLen index
	| lenSeq(x) == maxLen = index
	| otherwise = posMaxSeq xs maxLen (index + 1)


-- getMaxSeq [(Star(End)),(Star(Star(Star(End)))),(Star(Star(End)))]
--(3,2)


lenSeq :: StarSeq -> Int
lenSeq End = 0
lenSeq (Star x) = 1 + lenSeq(x)
--lenSeq (Star(Star(Star(End))))
--output: 3


------------------------------------------------

--data una lista di StarSeq, stampa in uscita le sequenze in ordine crescente di lunghezza, 
--rappresentandole come sequenze di *, una sequenza per ogni linea
printSeqs :: [StarSeq] -> IO ()
printSeqs x = foreach (sortWith (\s -> lenSeq(s)) x) printSingleSeq

--printSingleSeq (Star(Star(End)))
-- **
printSingleSeq :: StarSeq -> IO ()
printSingleSeq End = putStr "\r\n"
printSingleSeq (Star x) = putChar '*' >> printSingleSeq x

--foreach () f
--sortWith (\s -> getMinX(s)) x

foreach :: [a] -> (a -> IO ()) -> IO ()
foreach [] _ = return ()
foreach (x:xs) act = act x >> foreach xs act

