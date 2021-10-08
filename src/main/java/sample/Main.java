package sample;

//import com.predic8.wsdl.WSDLParser;

import javax.wsdl.Definition;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import java.io.File;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
//        System.out.println("ddd");
//        WSDLParser parser = new WSDLParser();
//        System.out.println("kkk");

        //File wsdlFile = new File()
        /*

        //--Get the WSDLFactory. You must specifiy Oracle's implementation class name
        WSDLFactory wsdlFactory = WSDLFactory.newInstance();

//--Create a reader and register the standard extensions
        WSDLReader wsdlReader = wsdlFactory.newWSDLReader();
        ExtensionRegistry extensionRegistry = wsdlFactory.newPopulatedExtensionRegistry();
        wsdlReader.setExtensionRegistry(extensionRegistry);

        //--Set a sixty-second timeout for reading the WSDL definition
        //System.setProperty(oracle.webservices.wsdl.WSDLFactoryImpl.WSDL_READ_TIMEOUT, "60" );

//--Read a WSDL file, including any imports
//        Definition def = wsdlReader.readWSDL("http://some.com/someservice?WSDL");
        Definition def = wsdlReader.readWSDL("C:\\Downloads\\P1-portal-wsdl.wsdl");

//--You can now explore the WSDL definition, for example,
        Map services = def.getServices();
        String targetNamespace = def.getTargetNamespace();

        def.getServices().forEach((servs, k) -> {
            System.out.println(servs.toString() + "=" + k.toString());
        });*/

        App.main(args);
    }
}
