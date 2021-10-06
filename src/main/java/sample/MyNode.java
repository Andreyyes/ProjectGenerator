package sample;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class MyNode {
    private List<MyNode> children;
    private String name;
    private String attributeName;
    private String attributeValue;

    public MyNode(String name) {
        children = new ArrayList<>();
        this.name = name;
    }

    public void addChild(MyNode child) {
        children.add(child);
    }

    public List<MyNode> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public String toString() {
        if (name == null) {name = "";}

        if (attributeValue != null && !attributeValue.isEmpty()) {
            if (attributeName == null) {attributeName = "";}
            return "<" + name + " " + attributeName + "=" + attributeValue + "/>";
        } else {
            return "<" + name + "/>";
        }
    }

    public void tryAddAttribute(Node node, String attribName) {
        try {
            String attribValue = node.getAttributes().getNamedItem(attribName).getNodeValue();
            setAttributeName(attribName);
            setAttributeValue(attribValue);
        } catch (Exception e) {}
    }

    public void recursiveToString(StringBuilder result, String offsetString) {
        result.append(offsetString);
        result.append(toString());
        result.append("\n");

        offsetString += "\t";

        for (MyNode child : children) {
            child.recursiveToString(result, offsetString);
        }

//        children.forEach(child->{
//            child.recursiveToString(result, offsetString);
//        });


    }
}
