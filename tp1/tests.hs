import Palet
import Route
import Stack

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
        testF(netS (stackS (stackS (newS 1) (newP "Madrid" 1)) (newP "Barcelona" 2))),
        holdsS (newS 2) (newP "Madrid" 1) ruta_corta == True,
        holdsS (stackS (newS 2) (newP "Madrid" 1)) (newP "Barcelona" 2) ruta_corta == False
    ]
