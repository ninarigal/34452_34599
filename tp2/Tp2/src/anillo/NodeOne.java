package anillo;

public class NodeOne extends Node {

    public NodeOne(Object cargo) {
        super(null, null, cargo);
        this.prev = this;
        this.next = this;
    }

    @Override
    public Node add(Object cargo){

        Node sameNode = new Node(null, null, this.data);
        Node newNode = new Node(sameNode, sameNode, cargo);
        sameNode.prev = newNode;
        sameNode.next = newNode;

        return newNode;
    }

    @Override
    public Node remove(){
        return new NodeZero();
    }

}
