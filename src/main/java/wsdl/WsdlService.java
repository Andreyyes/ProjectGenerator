package wsdl;

import javafx.scene.control.TreeItem;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.tools.util.NameUtil;
import sample.TreeNode;

import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.xml.namespace.QName;
import java.util.*;

public class WsdlService extends TreeNode {

    private final WsdlFile wsdlFile;
    private final List<WsdlPort> wsdlPorts;

    private String serviceName;
    private String namespace;
    private String convertedNamespace;
    //private String interfaceName;
    //private String convertedInterfaceName;

    public WsdlService(WsdlFile wsdlFile, Service service) {
        this.wsdlFile = wsdlFile;
        //Definitions definitions = wsdlFile.getDefinitions();
        //Description description = wsdlFile.getDescription();

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
        /*try {
            //InterfaceType interfaceType = service.getInterface();
            //interfaceName = service.getInterface().getQName().getLocalPart();
            convertedInterfaceName = NameUtil.mangleNameToClassName(interfaceName);
        } catch (Exception e) {
            //interfaceName = "NONE";
            convertedInterfaceName = "NONE";
        }*/








        //Map ports1 = service.getPorts();
/*
        Map data = new HashMap<>();
        data.put(100, "ABCD");
        data.put(200, "EFGH");

        Set<String> keys = data.keySet();

        System.out.println(keys.stream().count());

        keys.forEach(k->{
            System.out.println(k);
        });


        data.forEach((key, val)->{
            System.out.println(key + " = " + val);
        });

*/



        //Map<QName, Port> ports = service.getPorts();
        //Set<QName> keys1 = ports.keySet();

        //keys1.forEach(k -> {
            //System.out.println(k);
        //});

        //ports.keySet().forEach(f->{
        //    System.out.println(f);
            //System.out.println(ports.get(f));
            //wsdlPorts.add(new WsdlPort(this, ports.get(f)));
        //});

        /*ports.entrySet().forEach(f -> {
            wsdlPorts.add(new WsdlPort(this, f.getValue()));
        });*/

        Map<String, Port> ports = service.getPorts();
        wsdlPorts = new ArrayList<>();
        ports.forEach((name, port) -> {
            wsdlPorts.add(new WsdlPort(this, port));
        });

/*
        Map data = new HashMap<>();
        data.put(100, "ABCD");
        data.put(200, "EFGH");

        data.forEach((key, val)->{
            System.out.println(key + " = " + val);
        });*/

    }

    @Override
    public String toString() {
        return serviceName;
    }

    public WsdlFile getWsdlFile() {
        return wsdlFile;
    }

    public List<WsdlPort> getWsdlPorts() {
        return wsdlPorts;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getConvertedNamespace() {
        return convertedNamespace;
    }

    //public String getInterfaceName() {
    //    return interfaceName;
    //}

    //public String getConvertedInterfaceName() {
    //    return convertedInterfaceName;
    //}

    public TreeItem<TreeNode> getAsTreeItem() {
        TreeItem<TreeNode> treeItem = new TreeItem<>(this);
        List<TreeItem<TreeNode>> children = treeItem.getChildren();
        wsdlPorts.forEach(wsdlPort -> {
            children.add(wsdlPort.getAsTreeItem());
        });
        return treeItem;
    }
}
