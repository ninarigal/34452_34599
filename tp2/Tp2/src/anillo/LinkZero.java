package anillo;

public class LinkZero extends Link {
    public LinkZero() {
        super(null, null, null);
    }

    public Link getNextFrom(Link current) {
        throw new RuntimeException();
    }

    public Object getDataFrom(Link current) {
        throw new RuntimeException();
    }

    public Link add(Object cargo){
        LinkFull newNode = new LinkFull(null, null, cargo) {};
        newNode.setNext(newNode);
        newNode.setPrev(newNode);
        return newNode;
    }

    public Link removeFrom(Link current){
        throw new RuntimeException();
    }
}