package sample;

public class TreeNode {

    protected String nodeName;

    public TreeNode() {
    }

    public TreeNode(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public String toString() {
        return nodeName;
    }
}
