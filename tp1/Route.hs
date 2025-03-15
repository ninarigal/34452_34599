module Route ( Route, newR, inOrderR )
  where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route                    -- construye una ruta segun una lista de ciudades
newR cities = Rou cities

inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR (Rou []) city1 city2 = False
inOrderR (Rou (c:cs)) city1 city2
  | c == city1 = elem city2 cs -- si la primer ciudad es la consultada, entonces la segunda ciudad debe estar en la lista de ciudades restantes
  | c == city2 = False -- si la segunda ciudad es la consultada, entonces la primer ciudad no esta antes
  | otherwise = inOrderR (Rou cs) city1 city2  -- si ninguna de las ciudades es la consultada, se sigue buscando