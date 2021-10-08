package wsdl;

import javafx.scene.control.TreeItem;
import sample.TreeNode;

import javax.wsdl.Definition;
import javax.wsdl.Service;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WsdlFile extends TreeNode {

    private TreeItem<TreeNode> treeItem = null;

    private final File file;
    private final String fileContent;

    private final Definition definition;
    private final List<WsdlService> wsdlServices;

    public WsdlFile(File file) throws Exception {
        this.file = file;
        nodeName = file.getName();
        fileContent = new String(Files.readAllBytes(file.toPath()));

        WSDLFactory wsdlFactory = WSDLFactory.newInstance();

        //--Create a reader and register the standard extensions
        WSDLReader wsdlReader = wsdlFactory.newWSDLReader();
        ExtensionRegistry extensionRegistry = wsdlFactory.newPopulatedExtensionRegistry();
        wsdlReader.setExtensionRegistry(extensionRegistry);

        //--Set a sixty-second timeout for reading the WSDL definition
        //System.setProperty(oracle.webservices.wsdl.WSDLFactoryImpl.WSDL_READ_TIMEOUT, "60");

        //--Read a WSDL file, including any imports
        //Definition def = wsdlReader.readWSDL("C:\\Downloads\\P1-portal-wsdl.wsdl");
        definition = wsdlReader.readWSDL(file.getAbsolutePath());

        //System.out.println("TNS = " + definition.getTargetNamespace());

        //Считываем все сервисы
        @SuppressWarnings("unchecked")
        Map<QName, Service> services = definition.getServices();

        wsdlServices = new ArrayList<>();
        services.forEach((name, service) -> {
            wsdlServices.add(new WsdlService(this, service));
        });

        createTreeItem();
    }

    public File getFile() {
        return file;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void createTreeItem() {
        treeItem = new TreeItem<>(this);
        List<TreeItem<TreeNode>> children = treeItem.getChildren();
        treeItem.setExpanded( true );
        wsdlServices.forEach(wsdlService -> {
            TreeItem<TreeNode> wsdlServiceTreeItem = wsdlService.getTreeItem();
            wsdlServiceTreeItem.setExpanded( true );
            children.add(wsdlServiceTreeItem);
        });
    }

    public TreeItem<TreeNode> getTreeItem() {
        return treeItem;
    }

    public List<WsdlService> getWsdlServices() {
        return wsdlServices;
    }

    /*
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
    */
    private static void out(String str) {
        System.out.println(str);
    }
}
