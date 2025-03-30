package anillo;

import java.util.ArrayList;
import java.util.List;

public class Ring {
    private List<Object> cargas = new ArrayList<>();
    private int current = 0;

    public Ring next() {
        if ( cargas.isEmpty() ) {
            throw new RuntimeException();
        }
        current = (current + 1) % cargas.size();
        return this;
    }

    public Object current() {

        if ( cargas.isEmpty() ) {
            throw new RuntimeException();
        }
        return cargas.get(current);
    }

    public Ring add( Object cargo ) {
        cargas.add(cargo);
        current = cargas.size() - 1;
        return this;
    }

    public Ring remove() {
        if ( cargas.isEmpty() ) {
            throw new RuntimeException();
        }
        cargas.remove(current);
        if ( cargas.isEmpty() ) {
            current = 0;
        } else {
            current = current % cargas.size();
        }
        return this;
    }
}
