package anillo;

public abstract class Link {
    public Link next;
    public Link prev;
    public Object data;

    public Link(Link next, Link prev, Object data) {
        this.next = next;
        this.prev = prev;
        this.data = data;
    }

    public abstract Link get_next(Link current);
    public abstract Link get_prev(Link current);
    public abstract Object get_data(Link current);
    public abstract Link add(Object cargo);
    public abstract Link remove(Link current);
}