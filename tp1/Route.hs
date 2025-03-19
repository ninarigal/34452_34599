module Route ( Route, newR, inOrderR, inRouteR )
  where

data Route = Rou [ String ] deriving (Eq, Show)

newR :: [ String ] -> Route                    -- construye una ruta segun una lista de ciudades
newR [] = error "La lista de ciudades no puede ser vacía"
newR cities = Rou (removeDuplicates cities [])

removeDuplicates :: [String] -> [String] -> [String]
removeDuplicates [] _ = []
removeDuplicates (x:xs) seen
    | elem x seen = removeDuplicates xs seen  
    | otherwise = x : removeDuplicates xs (x:seen)

inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR (Rou cities) city1 city2
  | inRouteR (Rou cities) city1 == False = error ("La ciudad " ++ city1 ++ " no está en la ruta.")
  | inRouteR (Rou cities) city2 == False = error ("La ciudad " ++ city2 ++ " no está en la ruta.")
  | otherwise = checkOrder cities city1 city2
  where
    checkOrder [] _ _ = False
    checkOrder (c:cs) city1 city2
      | c == city1 = elem city2 cs
      | c == city2 = False 
      | otherwise = checkOrder cs city1 city2  

inRouteR :: Route -> String -> Bool -- indica si la ciudad consultada está en la ruta
inRouteR (Rou cities) city = elem city cities 

