package wsdl;

import javafx.scene.control.TreeItem;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.xml.sax.InputSource;
import sample.TreeNode;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class WsdlFile extends TreeNode {
    private final File file;
    private String fileContent;
    //private WsdlData wsdlData;
    private Description description;
    private List<WsdlService> wsdlServices;

    public WsdlFile(File file) throws Exception {
        this.file = file;
        nodeName = file.getName();
        fileContent = new String(Files.readAllBytes(file.toPath()));

        InputSource inputSource = new InputSource(new FileInputStream(file));

        // Read a WSDL 1.1 or 2.0
        WSDLReader reader = WSDLFactory.newInstance().newWSDLReader();
        description = reader.read(inputSource);

        //Service srv = description.getServices().get(0);

        wsdlServices = new ArrayList<>();

        description.getServices().forEach(service -> {
            wsdlServices.add(new WsdlService(this, service));
        });

//        wsdlServices.add(new WsdlService())

    }

    public File getFile() {
        return file;
    }

//    public WsdlData getWsdlData() {
//        return wsdlData;
//    }

    public Description getDescription() {
        return description;
    }

    public String getFileContent() {
        return fileContent;
    }

    public TreeItem<TreeNode> getTreeNode() {
        TreeItem<TreeNode> treeItem = new TreeItem<>(this);
        wsdlServices.forEach(wsdlService -> {
            treeItem.getChildren().add(new TreeItem<>(wsdlService));
        });
        return treeItem;
    }
}
