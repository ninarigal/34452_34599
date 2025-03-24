import Palet
import Route
import Stack
import Truck

import Control.Exception
import System.IO.Unsafe


testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()


tests_palet = [ 
        testF(newP "" 1),
        testF(newP "Madrid" 0),
        testF(newP "Madrid" (-1)),
        destinationP (newP "Madrid" 1) == "Madrid",
        netP (newP "Madrid" 1) == 1
        ]


--Variables: ciudades
ciudades_corta = ["Madrid", "Barcelona", "Paris"]
ciudades_repetidos = ["Madrid", "Barcelona", "Paris", "Madrid"]

-- rutas
ruta_corta = newR ciudades_corta
ruta_repetidos = newR ciudades_repetidos

tests_route = [
    testF(newR []),
    testF(inOrderR ruta_corta "Roma" "Berlin"),
    testF(inOrderR ruta_corta "Madrid" "Berlin"),
    testF(inOrderR ruta_corta "Berlin" "Madrid"),
    inOrderR ruta_corta "Madrid" "Barcelona" == True,
    inOrderR ruta_repetidos "Madrid" "Barcelona" == True,
    inOrderR ruta_repetidos "Barcelona" "Madrid" == False,
    inRouteR ruta_corta "Madrid" == True,
    inRouteR ruta_corta "Barcelona" == True,
    inRouteR ruta_corta "Paris" == True,
    inRouteR ruta_corta "Lisboa" == False
    ]

tests_stack = [
        testF(newS 0),
        testF(newS (-1)),
        freeCellsS (newS 1) == 1,
        freeCellsS (stackS (newS 1) (newP "Madrid" 1)) == 0,
        netS (newS 1) == 0,
        netS (stackS (newS 1) (newP "Madrid" 1)) == 1,
        netS (stackS (stackS (newS 1) (newP "Madrid" 1)) (newP "Barcelona" 2)) == netS (stackS (newS 1) (newP "Madrid" 1)),
        netS (stackS (stackS (newS 2) (newP "Madrid" 10)) (newP "Madrid" 2)) == netS (stackS (newS 1) (newP "Madrid" 10)),
        holdsS (newS 2) (newP "Madrid" 1) ruta_corta == True,
        holdsS (stackS (newS 2) (newP "Madrid" 1)) (newP "Barcelona" 2) ruta_corta == False,
        netS (popS (stackS (stackS (newS 2) (newP "Barcelona" 2)) (newP "Madrid" 1)) "Madrid") == netS (stackS (newS 2) (newP "Barcelona" 2)),
        freeCellsS (popS (stackS (stackS (newS 2) (newP "Barcelona" 2)) (newP "Madrid" 1)) "Paris") == freeCellsS (stackS (stackS (newS 2) (newP "Barcelona" 2)) (newP "Madrid" 1))
    ]

tests_truck = [
        testF(newT 0 0 ruta_corta),
        testF(newT 0 1 ruta_corta),
        testF(newT 1 0 ruta_corta),
        freeCellsT (newT 1 1 ruta_corta) == 1,
        freeCellsT (loadT (newT 1 1 ruta_corta) (newP "Madrid" 1)) == 0,
        freeCellsT (loadT (loadT (newT 1 1 ruta_corta) (newP "Madrid" 1)) (newP "Barcelona" 2)) == freeCellsT (loadT (newT 1 1 ruta_corta) (newP "Madrid" 1)), 
        netT (newT 1 1 ruta_corta) == 0,
        netT (loadT (newT 1 1 ruta_corta) (newP "Madrid" 1)) == 1,
        netT (loadT(loadT (loadT (newT 2 2 ruta_corta) (newP "Madrid" 1)) (newP "Barcelona" 2)) (newP "Paris" 3)) == netT (loadT (loadT (newT 2 2 ruta_corta) (newP "Madrid" 1)) (newP "Barcelona" 2)),
        netT(loadT( loadT (loadT (newT 2 2 ruta_corta) (newP "Paris" 1)) (newP "Barcelona" 2)) (newP "Madrid" 3)) == 6,
        netT(unloadT(loadT( loadT (loadT (newT 2 2 ruta_corta) (newP "Paris" 1)) (newP "Barcelona" 2)) (newP "Madrid" 3)) "Madrid") == netT (loadT (loadT (newT 2 2 ruta_corta) (newP "Paris" 1)) (newP "Barcelona" 2)),
        netT (unloadT(loadT (loadT (newT 2 2 ruta_corta) (newP "Madrid" 1)) (newP "Barcelona" 2)) "Paris") == netT (loadT (loadT (newT 2 2 ruta_corta) (newP "Madrid" 1)) (newP "Barcelona" 2)),
        netT (unloadT (loadT (newT 1 1 ruta_corta) (newP "Madrid" 1)) "Madrid") == 0,
        freeCellsT(loadT (newT 1 1 ruta_corta) (newP "Madrid" 11)) == 1,
        netT(loadT (loadT (loadT (loadT (loadT (newT 3 3 ruta_corta) (newP "Paris" 5)) (newP "Paris" 5)) (newP "Paris" 5)) (newP "Barcelona" 7)) (newP "Londres" 1)) == netT(loadT (loadT (loadT (loadT (newT 3 3 ruta_corta) (newP "Paris" 5)) (newP "Paris" 5)) (newP "Paris" 5)) (newP "Barcelona" 7))
    ]   
