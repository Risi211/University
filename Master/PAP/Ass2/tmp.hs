class C a where 
    toString   :: a -> String
    draw       :: a -> String

instance C MyType1 where
    toString v = "MyType1"
    draw (MyObj11 x)   = "11"  
    draw (MyObj12 x y) = "12"

instance C MyType2 where
    toString v = "MyType2"
    draw (MyObj22 x y) = "22"

data MyType1 = MyObj11 Int | MyObj12 Int Int 
data MyType2 = MyObj21 Int | MyObj22 Int Int 