package anillo;

import java.util.Stack;

public class Ring {
    private Link current = new LinkZero();
    private Stack<Link> links = new Stack<>(){{ push(current);}};

    public Ring next() {
        current = links.peek().getNextFrom(current);
        return this;
    }

    public Object current() {
        return links.peek().getDataFrom(current);
    }

    public Ring add( Object cargo ) {
        current = current.add(cargo);
        links.push(current);
        return this;
    }

    public Ring remove() {
        current = links.peek().removeFrom(current);
        links.pop();
        return this;
    }
}