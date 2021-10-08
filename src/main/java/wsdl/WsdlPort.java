package wsdl;

//import com.predic8.schema.*;
//import com.predic8.wsdl.*;
import javafx.scene.control.TreeItem;
//import org.ow2.easywsdl.wsdl.api.Endpoint;
import org.apache.cxf.tools.util.NameUtil;
import sample.TreeNode;

import javax.wsdl.Port;
import java.util.List;

public class WsdlPort extends TreeNode {

    private final WsdlService wsdlService;

    private Port port;
    private final String portName;

    //private String interfaceName;
    private String convertedInterfaceName;

    public WsdlPort(WsdlService wsdlService, Port port) {
        this.wsdlService = wsdlService;
        this.port = port;

        portName = port.getName();
        //interfaceName = port.getBinding().

        try {
            //InterfaceType interfaceType = service.getInterface();
            //interfaceName = service.getInterface().getQName().getLocalPart();
            convertedInterfaceName = NameUtil.mangleNameToClassName(portName);
        } catch (Exception e) {
            //interfaceName = "NONE";
            convertedInterfaceName = "NONE";
        }
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
                wsdlService.getConvertedNamespace() + "." + convertedInterfaceName,//wsdlService.getConvertedInterfaceName(), //serviceClass
                wsdlService.getNamespace()); //xmlns:a
        return rez;
    }

    @Override
    public String toString() {
        return portName;
    }

    public TreeItem<TreeNode> getAsTreeItem() {
        TreeItem<TreeNode> treeItem = new TreeItem<>(this);
        return treeItem;
    }
}
