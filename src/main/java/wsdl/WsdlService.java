package wsdl;

import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.tools.util.NameUtil;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import sample.TreeNode;
import javax.xml.namespace.QName;

public class WsdlService extends TreeNode {

    private WsdlFile wsdlFile;

    private String serviceName;
    private String namespace;
    private String convertedNamespace;
    private String interfaceName;
    private String convertedInterfaceName;

    public WsdlService(WsdlFile wsdlFile, Service service) {
        this.wsdlFile = wsdlFile;
        Description description = wsdlFile.getDescription();

        //Получаем сведения о сервисе
        //QName serviceQName = description.getServices().get(index).getQName();
        QName serviceQName = service.getQName();
        serviceName = serviceQName.getLocalPart();
        namespace = serviceQName.getNamespaceURI();

        //Преобразуем названия в Java формат (такие названия формирует генератор классов CXF)
        convertedNamespace = PackageUtils.getPackageNameByNameSpaceURI(namespace);
        //String serviceClassName = NameUtil.mangleNameToClassName(serviceName);

        //Получаем сведения об интерфейсе
        //interfaceName = description.getInterfaces().get(index).getQName().getLocalPart();
        try {
            //InterfaceType interfaceType = service.getInterface();
            interfaceName = service.getInterface().getQName().getLocalPart();
            convertedInterfaceName = NameUtil.mangleNameToClassName(interfaceName);
        } catch (Exception e) {
            interfaceName = "NONE";
            convertedInterfaceName = "NONE";
        }
    }

    static String pattern = "<cxf:cxfEndpoint id=\"%s\"\n" +
            "\taddress=\"/%s\"\n" +
            "\twsdlURL=\"META-INF/wsdl/%s\"\n" +
            "\tendpointName=\"a:%s\"\n" + //Сюда прописывать название порта из сервиса <port name ="">!
            "\tserviceName=\"a:%s\"\n" +
            "\tserviceClass=\"%s\"\n" +
            "\txmlns:a=\"%s\"/>";

//    String filledPattern = "<cxf:cxfEndpoint id=\"requestServices\"\n" +
//            "address=\"/srvApplTechConnectionReverseSK\"\n" +
//            "wsdlURL=\"META-INF/wsdl/srvApplTechConnectionReverseSK.wsdl\"\n" +
//            "endpointName=\"a:WebServiceASSOSoap\" \n" +
//            "serviceName=\"a:srvApplTechConnectionResultsSK\"\n" +
//            "serviceClass=\"org.datacontract.schemas._2004._07.asso_common_entities.WebServiceASSOPortType\"\n" +
//            "xmlns:a=\"http://schemas.datacontract.org/2004/07/ASSO.Common.Entities\">\n";

    @Override
    public String toString() {
        return serviceName;
    }

    public String getEndpoint() {
        String rez = String.format(pattern, serviceName, serviceName, wsdlFile.getFile().getName(), serviceName, serviceName, convertedNamespace + "." + convertedInterfaceName, namespace);
        return rez;
    }
}
