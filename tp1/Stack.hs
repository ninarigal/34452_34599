module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS )

  where

import Palet
import Route

data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
newS capacity
  | capacity <= 0 = error "La capacidad de la pila debe ser mayor que 0"
  | otherwise = Sta [] capacity

freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta palets capacity) = capacity - length palets

stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta palets capacity) palet 
  | freeCellsS (Sta palets capacity) == 0 = Sta palets capacity
  | netS (Sta palets capacity) + netP palet > 10 = Sta palets capacity
  | otherwise = Sta (palet:palets) capacity

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta [] capacity) = 0
netS (Sta (p:ps) capacity) = netP p + netS (Sta ps capacity)

holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta [] capacity) palet route = inRouteR route (destinationP palet)
holdsS (Sta (topPalet:palets) capacity) palet route
    | inRouteR route (destinationP palet) == False = False
    | destinationP topPalet == destinationP palet = True 
    | inOrderR route (destinationP palet) (destinationP topPalet) == True = True 
    | otherwise = False

popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
popS (Sta [] capacity) city = Sta [] capacity
popS (Sta (p:ps) capacity) city
    | destinationP p == city = popS (Sta ps capacity) city 
    | otherwise = Sta (p:ps) capacity 

