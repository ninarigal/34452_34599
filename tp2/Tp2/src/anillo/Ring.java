package anillo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Ring {


    private Node current = new NodeZero();
    private Stack<Node> links = new Stack<>(){{ push(current);}};

    public Ring next() {
        current = links.peek().get_next(current);
        return this;
    }

    public Object current() {
        return links.peek().get_data(current);
    }

    public Ring add( Object cargo ) {

        current = current.add(cargo);
        links.push(current);
        return this;
    }

    public Ring remove() {
        current = links.peek().remove(current);
        links.pop();
        return this;
    }
}
