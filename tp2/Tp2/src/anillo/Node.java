package anillo;

public class Node {
    protected Node next;
    protected Node prev;
    protected Object data;

    public Node( Node next, Node prev, Object data ) {
        this.next = next;
        this.prev = prev;
        this.data = data;
    }

    public Node get_next() {
        return next;
    }
    public Node get_prev() {
        return prev;
    }
    public Object get_data() {
        return data;
    }

    public Node add( Object cargo ) {
        Node newNode = new Node(this, this.prev, cargo);
        this.prev.next = newNode;
        this.prev = newNode;

        return newNode;
    }

    public Node remove() {

        this.prev.next = this.next;
        this.next.prev = this.prev;
        return this.next;
    }
}
