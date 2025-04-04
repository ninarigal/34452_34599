package anillo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Ring {

    private Node current = new NodeZero();
    //private Stack<Node> links = new Stack<>();

    public Ring next() {
        current = current.get_next();
        return this;
    }

    public Object current() {
        return current.get_data();
    }

    public Ring add( Object cargo ) {

        current = current.add(cargo);
        return this;
    }

    public Ring remove() {
        current = current.remove();
        return this;
    }
}
