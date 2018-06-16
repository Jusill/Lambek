/**
 * Created by Boris on 16.10.2017.
 */
public class Node {
    private Node left = null;
    private Node right = null;
    private Node parent = null;
    private String value = "";
    private int type = 0;
    private int count = -1;

    Node(){}

    Node(Node node) {
        if (node.getLeft() != null){
            this.left = node.getLeft();
        }

        if(node.getRight() != null){
            this.right = node.getRight();
        }

        if(node.getParent() != null){
            this.parent = node.getParent();
        }

        this.value = node.getValue();
        this.type = node.getType();
        this.count = node.getCount();
    }

    void createLeft(Node left){
        this.left = left;
        this.left.parent = this;
    }

    void createRight(Node right){
        this.right = right;
        this.right.parent = this;
    }

    Node getLeft(){
        if(left != null){
            return left;
        }
        else{
            return null;
        }
    }

    Node getRight(){
        if(right != null){
            return right;
        }
        else{
            return null;
        }
    }

    Node getParent(){
        if(parent != null){
            return parent;
        }
        else{
            return null;
        }
    }

    String getValue(){ return value; }

    int getType(){ return type; }

    int getCount(){ return count; }

    void setParent(Node parent){
        this.parent = parent;
    }

    /**
     * & -- 1 (par)
     * + -- 2 (tensor)
     * p -- 3 (value)
     */

    void set(String value, int count){
        this.value = value;
        this.count = count;

        switch (value) {
            case "&":
                this.type = 1;
                break;
            case "+":
                this.type = 2;
                break;
            default:
                this.type = 3;
                break;
        }
    }

    boolean isLeaf(){
        return this.left == null && this.right == null;
    }

    boolean isRoot(){
        return this.parent == null;
    }

    public void print(){
        System.out.println("\nVALUE: " + value);
        System.out.println("COUNT: " + count);
        System.out.println("TYPE: " + type);
    }

}

