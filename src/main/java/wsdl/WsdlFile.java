package wsdl;

import com.predic8.schema.*;
import com.predic8.wsdl.*;
import javafx.scene.control.TreeItem;
//import org.ow2.easywsdl.wsdl.WSDLFactory;
//import org.ow2.easywsdl.wsdl.api.Description;
//import org.ow2.easywsdl.wsdl.api.Service;
//import org.ow2.easywsdl.wsdl.api.WSDLReader;
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
    //private Description description;
    private Definitions definitions;
    private List<WsdlService> wsdlServices;

    public WsdlFile(File file) throws Exception {
        this.file = file;
        nodeName = file.getName();
        fileContent = new String(Files.readAllBytes(file.toPath()));

        WSDLParser parser = new WSDLParser();
        definitions = parser.parse(new FileInputStream(file));

        debugPrint();

        definitions.getServices().forEach(service -> {
            wsdlServices.add(new WsdlService(this, service));
        });

        // Read a WSDL 1.1 or 2.0
        /*
        InputSource inputSource = new InputSource(new FileInputStream(file));
        WSDLReader reader = WSDLFactory.newInstance().newWSDLReader();
        description = reader.read(inputSource);

        //Service srv = description.getServices().get(0);

        wsdlServices = new ArrayList<>();

        description.getServices().forEach(service -> {
            wsdlServices.add(new WsdlService(this, service));
        });
*/
    }

    public File getFile() {
        return file;
    }

//    public WsdlData getWsdlData() {
//        return wsdlData;
//    }

//    public Description getDescription() {
//        return description;
//    }


    public Definitions getDefinitions() {
        return definitions;
    }

    public String getFileContent() {
        return fileContent;
    }

//    public TreeItem<TreeNode> getAsTreeNode() {
//        TreeItem<TreeNode> treeItem = new TreeItem<>(this);
//        wsdlServices.forEach(wsdlService -> {
//            treeItem.getChildren().add(new TreeItem<>(wsdlService));
//        });
//        return treeItem;
//    }

    public TreeItem<TreeNode> getAsTreeItem() {
        TreeItem<TreeNode> treeItem = new TreeItem<>(this);
        List<TreeItem<TreeNode>> children = treeItem.getChildren();
        wsdlServices.forEach(wsdlService -> {
            children.add(wsdlService.getAsTreeItem());
        });
        return treeItem;
    }


    public void debugPrint() {
        out("-------------- WSDL Details --------------");
        out("TargenNamespace: \t" + definitions.getTargetNamespace());
        //out("Style: \t\t\t" + defs.getSt.getStyle());
        if (definitions.getDocumentation() != null) {
            out("Documentation: \t\t" + definitions.getDocumentation());
        }
        out("\n");

        // For detailed schema information see the FullSchemaParser.java sample.
        out("Schemas: ");
        for (Schema schema : definitions.getSchemas()) {
            out("  TargetNamespace: \t" + schema.getTargetNamespace());
        }
        out("\n");

        out("Messages: ");
        for (Message msg : definitions.getMessages()) {
            out("  Message Name: " + msg.getName());
            out("  Message Parts: ");
            for (Part part : msg.getParts()) {
                out("    Part Name: " + part.getName());
                out("    Part Element: " + ((part.getElement() != null) ? part.getElement() : "not available!"));
                out("    Part Type: " + ((part.getType() != null) ? part.getType() : "not available!" ));
                out("");
            }
        }
        out("");

        out("PortTypes: ");
        for (PortType pt : definitions.getPortTypes()) {
            out("  PortType Name: " + pt.getName());
            out("  PortType Operations: ");
            for (Operation op : pt.getOperations()) {
                out("    Operation Name: " + op.getName());
                out("    Operation Input Name: "
                        + ((op.getInput().getName() != null) ? op.getInput().getName() : "not available!"));
                out("    Operation Input Message: "
                        + op.getInput().getMessage().getQname());
                out("    Operation Output Name: "
                        + ((op.getOutput().getName() != null) ? op.getOutput().getName() : "not available!"));
                out("    Operation Output Message: "
                        + op.getOutput().getMessage().getQname());
                out("    Operation Faults: ");
                if (op.getFaults().size() > 0) {
                    for (Fault fault : op.getFaults()) {
                        out("      Fault Name: " + fault.getName());
                        out("      Fault Message: " + fault.getMessage().getQname());
                    }
                } else out("      There are no faults available!");

            }
            out("");
        }
        out("");

        out("Bindings: ");
        for (Binding bnd : definitions.getBindings()) {
            out("  Binding Name: " + bnd.getName());
            out("  Binding Type: " + bnd.getPortType().getName());
            out("  Binding Protocol: " + bnd.getBinding().getProtocol());
            if(bnd.getBinding() instanceof AbstractSOAPBinding) out("  Style: " + (((AbstractSOAPBinding)bnd.getBinding()).getStyle()));
            out("  Binding Operations: ");
            for (BindingOperation bop : bnd.getOperations()) {
                out("    Operation Name: " + bop.getName());
                if(bnd.getBinding() instanceof AbstractSOAPBinding) {
                    out("    Operation SoapAction: " + bop.getOperation().getSoapAction());
                    out("    SOAP Body Use: " + bop.getInput().getBindingElements().get(0).getUse());
                }
            }
            out("");
        }
        out("");

        out("Services: ");
        for (Service service : definitions.getServices()) {
            out("  Service Name: " + service.getName());
            out("  Service Potrs: ");
            for (Port port : service.getPorts()) {
                out("    Port Name: " + port.getName());
                out("    Port Binding: " + port.getBinding().getName());
                out("    Port Address Location: " + port.getAddress().getLocation()
                        + "\n");
            }
        }
        out("");
    }

    private static void out(String str) {
        System.out.println(str);
    }
}
