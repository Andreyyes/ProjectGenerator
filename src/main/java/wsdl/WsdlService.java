package wsdl;

import javafx.scene.control.TreeItem;
import org.apache.cxf.common.util.PackageUtils;
import sample.TreeNode;

import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.xml.namespace.QName;
import java.util.*;

public class WsdlService extends TreeNode {

    private TreeItem<TreeNode> treeItem = null;

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
        QName serviceQName = service.getQName();
        serviceName = serviceQName.getLocalPart();
        namespace = serviceQName.getNamespaceURI();

        //Преобразуем названия в Java формат (такие названия формирует генератор классов CXF)
        convertedNamespace = PackageUtils.getPackageNameByNameSpaceURI(namespace);
        //String serviceClassName = NameUtil.mangleNameToClassName(serviceName);

        @SuppressWarnings("unchecked")
        Map<String, Port> ports = service.getPorts();
        wsdlPorts = new ArrayList<>();
        ports.forEach((name, port) -> {
            wsdlPorts.add(new WsdlPort(this, port));
        });

        createTreeItem();
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

    public void createTreeItem() {
        treeItem = new TreeItem<>(this);
        List<TreeItem<TreeNode>> children = treeItem.getChildren();
        wsdlPorts.forEach(wsdlPort -> {
            children.add(wsdlPort.getTreeItem());
        });
    }

    public TreeItem<TreeNode> getTreeItem() {
        return treeItem;
    }
}
