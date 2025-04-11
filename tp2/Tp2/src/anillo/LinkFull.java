package anillo;

public class LinkFull extends Link {
    public LinkFull(Link next, Link prev, Object data) {
        super(next, prev, data);
    }

    public Link getNextFrom(Link current) {
        return current.getNext();
    }

    public Object getDataFrom(Link current) {
        return current.getData();
    }

    public Link add( Object cargo ) {
        LinkFull newNode = new LinkFull(this, this.getPrev(), cargo);
        this.getPrev().setNext(newNode);
        this.setPrev(newNode);
        return newNode;
    }

    public Link removeFrom( Link current) {
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        return current.getNext();
    }
}
