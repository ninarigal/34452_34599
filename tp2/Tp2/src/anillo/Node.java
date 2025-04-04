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

    public Node get_next(Node current) {
        return current.next;
    }
    public Node get_prev(Node current) { return current.prev; }
    public Object get_data(Node current) {
        return current.data;
    }

    public Node add( Object cargo ) {
        Node newNode = new Node(this, this.prev, cargo);
        this.prev.next = newNode;
        this.prev = newNode;
        return newNode;
    }

    public Node remove( Node current) {

        current.prev.next = current.next;
        current.next.prev = current.prev;
        return current.next;
    }
}
