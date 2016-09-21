data BSTree a = Nil | Node a (BSTree a) (BSTree a)

--Implementare una funzione print_nodes_at_dist che, 
--dato un albero binario t di interi e un valore intero d, 
--stampa gli  elementi dell'albero la cui distanza dal nodo radice è pari a d.
print_nodes_at_dist :: BSTree Int -> Int -> [Int]
print_nodes_at_dist Nil _ = [] --albero vuoto
print_nodes_at_dist (Node value l r) d
	| d == 0 = value:[]
	| otherwise = (print_nodes_at_dist l (d - 1)) ++ (print_nodes_at_dist r (d - 1))
	
--MyTest
{-
*Main> print_nodes_at_dist (Node 6 (Node 1 (Node 4 Nil (Node 2 Nil Nil)) Nil) (N
ode 3 (Node 9 (Node 3 Nil Nil) Nil) (Node 7 Nil Nil))) 0
[6]
*Main> print_nodes_at_dist (Node 6 (Node 1 (Node 4 Nil (Node 2 Nil Nil)) Nil) (N
ode 3 (Node 9 (Node 3 Nil Nil) Nil) (Node 7 Nil Nil))) 2
[4,9,7]
*Main> print_nodes_at_dist (Node 6 (Node 1 (Node 4 Nil (Node 2 Nil Nil)) Nil) (N
ode 3 (Node 9 (Node 3 Nil Nil) Nil) (Node 7 Nil Nil))) 6
[]
*Main>
-}