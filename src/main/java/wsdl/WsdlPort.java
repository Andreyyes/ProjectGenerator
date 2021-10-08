package wsdl;

import javafx.scene.control.TreeItem;
import org.apache.cxf.tools.util.NameUtil;
import sample.TreeNode;

import javax.wsdl.BindingOperation;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WsdlPort extends TreeNode {

    private TreeItem<TreeNode> treeItem = null;

    private final WsdlService wsdlService;
    private final List<WsdlOperation> wsdlOperations;

    private final Port port;
    private final String portName; //Interface
    private final String convertedInterfaceName;

    public WsdlPort(WsdlService wsdlService, Port port) {
        this.wsdlService = wsdlService;
        this.port = port;

        portName = port.getName();
        convertedInterfaceName = NameUtil.mangleNameToClassName(portName);

        //try {
            //InterfaceType interfaceType = service.getInterface();
            //interfaceName = service.getInterface().getQName().getLocalPart();
        //} catch (Exception e) {
            //interfaceName = "NONE";
        //    convertedInterfaceName = "NONE";
        //}

        //interfaceName = port.getBinding().

        @SuppressWarnings("unchecked")
                //List lst = port.getBinding().getBindingOperations();
        List<BindingOperation> operations = port.getBinding().getBindingOperations();
        wsdlOperations = new ArrayList<>();
        operations.forEach(operation -> {
            wsdlOperations.add(new WsdlOperation(this, operation));
        });

        createTreeItem();
    }

    public WsdlService getWsdlService() {
        return wsdlService;
    }

    public String getPortName() {
        return portName;
    }

//    String filledPattern = "<cxf:cxfEndpoint id=\"requestServices\"\n" +
//            "address=\"/srvApplTechConnectionReverseSK\"\n" +
//            "wsdlURL=\"META-INF/wsdl/srvApplTechConnectionReverseSK.wsdl\"\n" +
//            "serviceName=\"a:srvApplTechConnectionResultsSK\"\n" +
//            "endpointName=\"a:WebServiceASSOSoap\" \n" +
//            "serviceClass=\"org.datacontract.schemas._2004._07.asso_common_entities.WebServiceASSOPortType\"\n" +
//            "xmlns:a=\"http://schemas.datacontract.org/2004/07/ASSO.Common.Entities\">\n";

    static String pattern =
            "<cxf:cxfEndpoint id=\"%s\"\n" +
                    "\taddress=\"/%s\"\n" +
                    "\twsdlURL=\"META-INF/wsdl/%s\"\n" +
                    "\tserviceName=\"a:%s\"\n" +
                    "\tendpointName=\"a:%s\"\n" +
                    "\tserviceClass=\"%s\"\n" +
                    "\txmlns:a=\"%s\">\n" +
            "</cxf:cxfEndpoint>";

    public String getEndpoint() {
        String rez = String.format(pattern,
                wsdlService.getServiceName(), //id
                wsdlService.getServiceName(), //address
                wsdlService.getWsdlFile().getFile().getName(), //wsdlURL
                wsdlService.getServiceName(), //serviceName
                portName, //endpointName
                wsdlService.getConvertedNamespace() + "." + convertedInterfaceName, //serviceClass
                wsdlService.getNamespace()); //xmlns:a
        return rez;
    }

    @Override
    public String toString() {
        return portName;
    }

    public void createTreeItem() {
        treeItem = new TreeItem<>(this);
        List<TreeItem<TreeNode>> children = treeItem.getChildren();
        wsdlOperations.forEach(wsdlOperation -> {
            children.add(wsdlOperation.getTreeItem());
        });
    }

    public TreeItem<TreeNode> getTreeItem() {
        return treeItem;
    }
}
