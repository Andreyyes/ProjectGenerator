package wsdl;

import javafx.scene.control.TreeItem;
import sample.TreeNode;

import javax.wsdl.BindingOperation;

public class WsdlOperation extends TreeNode {

    private TreeItem<TreeNode> treeItem = null;

    private final WsdlPort wsdlPort;
    private final BindingOperation operation;
    private final String operationName;

    public WsdlOperation (WsdlPort wsdlPort, BindingOperation operation) {
        this.wsdlPort = wsdlPort;
        this.operation = operation;
        operationName = operation.getName();
        createTreeItem();
    }



    @Override
    public String toString() {
        return operationName + "()";
    }

    public void createTreeItem() {
        treeItem = new TreeItem<>(this);
    }

    public TreeItem<TreeNode> getTreeItem() {
        return treeItem;
    }
}
