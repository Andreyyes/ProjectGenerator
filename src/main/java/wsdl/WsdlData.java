package wsdl;

import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.tools.util.NameUtil;
import org.ow2.easywsdl.wsdl.WSDLFactory;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.WSDLReader;
import org.xml.sax.InputSource;

import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileInputStream;

public class WsdlData {
    private File file;
    private String serviceName;
    private String namespace;
    private String convertedNamespace;
    private String interfaceName;
    private String convertedInterfaceName;

    //Парсить WSDL
    public WsdlData(File file) throws Exception {

        this.file = file;

        //File file = new File(filePath);

        InputSource inputSource = new InputSource(new FileInputStream(file));
        //inputSource.setByteStream();

        // Read a WSDL 1.1 or 2.0
        WSDLReader reader = WSDLFactory.newInstance().newWSDLReader();
        Description desc = reader.read(inputSource);

        //AbsItfService service = desc.getServices().get(0);

        //Получаем сведения о сервисе
        QName serviceQName = desc.getServices().get(0).getQName();
        serviceName = serviceQName.getLocalPart();
        namespace = serviceQName.getNamespaceURI();

        //Преобразуем названия в Java формат (такие названия формирует генератор классов CXF)
        convertedNamespace = PackageUtils.getPackageNameByNameSpaceURI(namespace);
        //String serviceClassName = NameUtil.mangleNameToClassName(serviceName);

        //Получаем сведения об интерфейсе
        interfaceName = desc.getInterfaces().get(0).getQName().getLocalPart();
        convertedInterfaceName = NameUtil.mangleNameToClassName(interfaceName);

        //System.out.println(rez);

    }

    String pattern = "<cxf:cxfEndpoint \"id=\"%s\"\n" +
            "\taddress=\"/%s\"\n" +
            "\twsdlURL=\"META-INF/wsdl/%s\"\n" +
            "\tendpointName=\"a:%s\"\n" +
            "\tserviceName=\"a:%s\"\n" +
            "\tserviceClass=\"%s\"\n" +
            "\txmlns:a=\"%s\">";

//    String filledPattern = "<cxf:cxfEndpoint id=\"requestServices\"\n" +
//            "address=\"/srvApplTechConnectionReverseSK\"\n" +
//            "wsdlURL=\"META-INF/wsdl/srvApplTechConnectionReverseSK.wsdl\"\n" +
//            "endpointName=\"a:WebServiceASSOSoap\" \n" +
//            "serviceName=\"a:srvApplTechConnectionResultsSK\"\n" +
//            "serviceClass=\"org.datacontract.schemas._2004._07.asso_common_entities.WebServiceASSOPortType\"\n" +
//            "xmlns:a=\"http://schemas.datacontract.org/2004/07/ASSO.Common.Entities\">\n";

    @Override
    public String toString() {
        String rez = String.format(pattern, serviceName, serviceName, file.getName(), serviceName, serviceName, convertedNamespace + "." + convertedInterfaceName, namespace);
        return rez;
    }
}
