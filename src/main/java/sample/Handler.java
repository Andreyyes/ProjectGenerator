package sample;

import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.tools.util.NameUtil;
//import org.ow2.easywsdl.wsdl.WSDLFactory;
//import org.ow2.easywsdl.wsdl.api.Description;
//import org.ow2.easywsdl.wsdl.api.WSDLReader;
//import org.ow2.easywsdl.wsdl.api.abstractItf.AbsItfService;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Handler {

    public List<String> getFileTree(String root) {
        File rootDir = new File(root);
        ArrayList<String> list = new ArrayList<>();
        Queue<File> fileTree = new PriorityQueue<>();
        Collections.addAll(fileTree, Objects.requireNonNull(rootDir.listFiles()));

        while (!fileTree.isEmpty()) {
            File currentFile = fileTree.remove();
            if (currentFile.isDirectory()) {
                Collections.addAll(fileTree, Objects.requireNonNull(currentFile.listFiles()));
            } else {
                list.add(currentFile.getAbsolutePath());
            }
        }
        return list;
    }

    public Handler() {
    }

    public void operate(String folderPath) {
        List<String> files = getFileTree(folderPath); //Получить список файлов из указанной папки

        List<String> correctFiles = files.stream().filter(f -> f.toLowerCase(Locale.ROOT).endsWith("blueprint.xml"))
                .collect(Collectors.toList());

        List<MyNode> nodes = new ArrayList<>();

        correctFiles.forEach(f -> {
            try {
                nodes.add(parseFile(f));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        nodes.forEach(node -> {
            StringBuilder result = new StringBuilder();
            String offsetString = "";
            node.recursiveToString(result, offsetString);
            System.out.println(result.toString());
            System.out.println("*************************************\n");
        });
    }


    public MyNode parseFile(String filePath) throws Exception {
        File file = new File(filePath);

        //Создается построитель документа
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        //Создается дерево DOM документа из файла
        Document document = documentBuilder.parse(file);

        //Получаем корневой элемент
        Node rootNode = document.getDocumentElement();

        MyNode root = new MyNode(filePath);

        //Просматриваем все дочерние ноды корневой ноды
        NodeList nodes = rootNode.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            //Если это элементы - обрабатываем
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                if (nodeName.equals("camelContext")) {

                    MyNode contextNode = new MyNode("camelContext");
                    // String id = node.getAttributes().getNamedItem("id").getNodeValue();
                    contextNode.tryAddAttribute(node, "id");
                    //contextNode.setAttributeName("id");
                    //contextNode.setAttributeValue(id);

                    root.addChild(contextNode);

                    //Если есть дочерние элементы (свойства) у camelContext'а - обходим их
                    NodeList contextChildren = node.getChildNodes();
                    for (int j = 0; j < contextChildren.getLength(); j++) {
                        Node contextChildrenNode = contextChildren.item(j);
                        if (contextChildrenNode.getNodeType() == Node.ELEMENT_NODE) {
                            //Получаем имя ноды
                            String contextChildrenNodeName = contextChildrenNode.getNodeName();

                            //Если это свойство - добавляем его в свойства
                            if (contextChildrenNodeName.equals("route")) {


                                //String routeId = contextChildrenNode.getAttributes().getNamedItem("id").getNodeValue();
                                MyNode childrenContextNode = new MyNode(contextChildrenNodeName);
                                childrenContextNode.tryAddAttribute(contextChildrenNode, "id");
                                contextNode.addChild(childrenContextNode);
//                                childrenContextNode.setAttributeName("id");
//                                childrenContextNode.setAttributeValue(routeId);


                                NodeList routeChildren = contextChildrenNode.getChildNodes();
                                for (int k = 0; k < routeChildren.getLength(); k++) {
                                    Node routeChildNode = routeChildren.item(k);
                                    if (routeChildNode.getNodeType() == Node.ELEMENT_NODE) {
                                        //Получаем имя ноды
                                        String rootChildNodeName = routeChildNode.getNodeName();
                                        if (rootChildNodeName.equals("from") || rootChildNodeName.equals("to") || rootChildNodeName.equals("wireTap")) {
                                            //String childUri = rootChildNode.getAttributes().getNamedItem("uri").getNodeValue();
                                            MyNode childrenRouteNode = new MyNode(rootChildNodeName);
                                            childrenRouteNode.tryAddAttribute(routeChildNode, "uri");
                                            //childrenRouteNode.setAttributeName("uri");
                                            //childrenRouteNode.setAttributeValue(childUri);

                                            childrenContextNode.addChild(childrenRouteNode);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //int l = 1;
        return root;
    }



    /*
    public String getClassName(String namespace) {
        //org.apache.cxf.tools.util.mangleNameToClassName
        //org.apache.cxf.tools
        return PackageUtils.getPackageNameByNameSpaceURI(namespace);

        //return NameUtil.mangleNameToClassName(namespace);
    }
*/

    public String getAddressFromServiceName(String serviceName) {
        return "";
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


    //Парсить WSDL
    public void parseWsdlFile(File file) throws Exception {

        //File file = new File(filePath);

        InputSource inputSource = new InputSource(new FileInputStream(file));
        //inputSource.setByteStream();

        // Read a WSDL 1.1 or 2.0
        /*WSDLReader reader = WSDLFactory.newInstance().newWSDLReader();
        Description desc = reader.read(inputSource);

        //AbsItfService service = desc.getServices().get(0);

        //Получаем сведения о сервисе
        QName serviceQName = desc.getServices().get(0).getQName();
        String serviceName = serviceQName.getLocalPart();
        String namespace = serviceQName.getNamespaceURI();

        //Преобразуем названия в Java формат (такие названия формирует генератор классов CXF)
        String convertedNamespace = PackageUtils.getPackageNameByNameSpaceURI(namespace);
        //String serviceClassName = NameUtil.mangleNameToClassName(serviceName);

        //Получаем сведения об интерфейсе
        String interfaceName = desc.getInterfaces().get(0).getQName().getLocalPart();
        String convertedInterfaceName = NameUtil.mangleNameToClassName(interfaceName);


        String rez = String.format(pattern, serviceName, serviceName, file.getName(), serviceName, serviceName, convertedNamespace + "." + convertedInterfaceName, namespace);

        System.out.println(rez);
*/

        int k = 0;

//        File file = new File(filePath);
//
//        //Создается построитель документа
//        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//
//        //Создается дерево DOM документа из файла
//        Document document = documentBuilder.parse(file);
//
//        //Получаем корневой элемент
//        Node rootNode = document.getDocumentElement();
//
//        if (rootNode.getNodeName())
//
//        MyNode root = new MyNode(filePath);
//
//        //Просматриваем все дочерние ноды корневой ноды
//        NodeList nodes = rootNode.getChildNodes();
//        for (int i = 0; i < nodes.getLength(); i++) {
//            Node node = nodes.item(i);
//
//            //Если это элементы - обрабатываем
//            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                String nodeName = node.getNodeName();
//                System.out.println("== "+nodeName);
//            }
//        }
    }


}
