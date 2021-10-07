package wsdl;

import javafx.scene.control.TreeItem;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.tools.util.NameUtil;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.InterfaceType;
import org.ow2.easywsdl.wsdl.api.Service;
import org.ow2.easywsdl.wsdl.api.WSDLException;
import sample.TreeNode;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class WsdlService extends TreeNode {

    private WsdlFile wsdlFile;
    private List<WsdlPort> wsdlPorts;

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

        wsdlPorts = new ArrayList<>();
        service.getEndpoints().forEach(endpoint -> {
            wsdlPorts.add(new WsdlPort(this, endpoint));
        });
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

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getConvertedInterfaceName() {
        return convertedInterfaceName;
    }

    public TreeItem<TreeNode> getAsTreeItem() {
        TreeItem<TreeNode> treeItem = new TreeItem<>(this);
        List<TreeItem<TreeNode>> children = treeItem.getChildren();
        wsdlPorts.forEach(wsdlPort -> {
            children.add(wsdlPort.getAsTreeItem());
        });
        return treeItem;
    }
}
