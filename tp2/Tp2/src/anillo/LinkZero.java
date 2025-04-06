package anillo;

public class LinkZero extends Link {
    public LinkZero() {
        super(null, null, null);
    }

    public Link get_next(Link current) {
        throw new RuntimeException();
    }

    public Link get_prev(Link current) {
        throw new RuntimeException();
    }

    public Object get_data(Link current) {
        throw new RuntimeException();
    }

    public Link add(Object cargo){
        LinkFull newNode = new LinkFull(null, null, cargo) {};
        newNode.next = newNode;
        newNode.prev = newNode;
        return newNode;
    }

    public Link remove(Link current){
        throw new RuntimeException();
    }
}