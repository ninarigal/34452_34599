module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT )

  where

import Palet
import Stack
import Route
import Debug.Trace (trace)

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT qStacks hStacks route 
    | qStacks <= 0 || hStacks <= 0 = error "La cantidad de bahias y la altura de las mismas deben ser mayores que 0"
    | otherwise = Tru [newS hStacks | _ <- [1..qStacks]] route

freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru stacks route) = sum [freeCellsS stack | stack <- stacks]

loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion 
loadT (Tru stacks route) palet 
    | freeCellsT (Tru stacks route) == 0 = Tru stacks route
    | otherwise = Tru (cargarPalet stacks palet route) route
  where
    cargarPalet [] _ _ = []
    cargarPalet (s:ss) palet route
      | holdsS s palet route && netS s + netP palet <= 10 && freeCellsS s > 0 = (stackS s palet) : ss  
      | otherwise = s : cargarPalet ss palet route

unloadT :: Truck -> String -> Truck   -- responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT (Tru stacks route) city = Tru ([popS stack city | stack <- stacks]) route

netT :: Truck -> Int                  -- responde el peso neto en toneladas de los paletes en el camion
netT (Tru [] _) = 0
netT (Tru (s:ss) route) = netS s + netT (Tru ss route)

