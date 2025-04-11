package anillo;

public abstract class Link {
    private Link next;
    private Link prev;
    private Object data;

    public Link(Link next, Link prev, Object data) {
        this.next = next;
        this.prev = prev;
        this.data = data;
    }

    public Link getNext() {
        return next;
    }

    public void setNext(Link next) {
        this.next = next;
    }

    public Link getPrev() {
        return prev;
    }

    public void setPrev(Link prev) {
        this.prev = prev;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public abstract Link getNextFrom(Link current);
    public abstract Object getDataFrom(Link current);
    public abstract Link add(Object cargo);
    public abstract Link removeFrom(Link current);
}