package anillo;

public class LinkFull extends Link {
    public LinkFull(Link next, Link prev, Object data) {
        super(next, prev, data);
    }

    public Link get_next(Link current) {
        return current.next;
    }

    public Link get_prev(Link current) {
        return current.prev;
    }

    public Object get_data(Link current) {
        return current.data;
    }

    public Link add( Object cargo ) {
        LinkFull newNode = new LinkFull(this, this.prev, cargo);
        this.prev.next = newNode;
        this.prev = newNode;
        return newNode;
    }

    public Link remove( Link current) {
        current.prev.next = current.next;
        current.next.prev = current.prev;
        return current.next;
    }
}
