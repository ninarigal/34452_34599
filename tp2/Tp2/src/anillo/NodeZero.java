package anillo;

public class NodeZero extends Node {

    public NodeZero() {
        super(null, null, null);
    }

    @Override
    public Node get_next() {
        throw new RuntimeException();
    }
    @Override
    public Node get_prev() {
        throw new RuntimeException();
    }
    @Override
    public Object get_data() {
        throw new RuntimeException();
    }

    @Override
    public Node add(Object cargo){
        return new NodeOne(cargo);
    }

    @Override
    public Node remove(){
        throw new RuntimeException();
    }
}
