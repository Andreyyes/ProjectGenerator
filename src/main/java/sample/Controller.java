package sample;

//import com.predic8.wsdl.Definitions;
//import com.predic8.wsdl.WSDLParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import wsdl.WsdlFile;
import wsdl.WsdlPort;
import wsdl.WsdlService;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
/*
    @FXML
    Button testButton;

    @FXML
    public void onClickMethod() {
        Handler handler = new Handler();
        handler.operate("C:\\satel\\rosseti\\Services_Karaf_4.2.7");
    }

 */

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //winMainList.setItems(FXCollections.observableArrayList(List<String> something);
        TreeItem<TreeNode> rootElem = new TreeItem<>(new TreeNode("Project"));

        rootElem.setExpanded(true);
        //rootElem.getChildren().addAll(firstFile, secondFile);

        projectTree.setRoot(rootElem);
    }

    @FXML
    private Button btnTest;

    @FXML
    private Button btnGetData;

    @FXML
    private TextArea previewArea;

    @FXML
    private TreeView<TreeNode> projectTree;

    @FXML
    void btnTestClick(ActionEvent event) {
        /*

        TreeItem<DataElement> firstFile = new TreeItem<>(new DataElement("firstFile.xml", "100 kB"));
        TreeItem<DataElement> secondFile = new TreeItem<>(new DataElement("secondFile.xml", "250 kB"));
        TreeItem<DataElement> thirdFile = new TreeItem<>(new DataElement("thirdFile.xml", "340 kB"));

        thirdFile.setExpanded(true);
        thirdFile.getChildren().addAll(firstFile, secondFile);

        projectTree.setRoot(thirdFile);

*/

        //projectTree.getCh = new TreeView<>(thirdFile);

        //TreeItem<String> rootItem = new TreeItem<String>("Root");
        //rootItem.setExpanded(true);

        //rootItem.getChildren().addAll(firstFile, secondFile);


        //projectTree.get



        //projectTree.
    }

    @FXML
    void btnGetDataClick(ActionEvent event) {
        //projectTree.selec
        //new Alert(Alert.AlertType.INFORMATION, "Hello!").showAndWait();

    }

    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles())  {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void handleDragDrop(DragEvent event) throws Exception {
        List<File> files =  event.getDragboard().getFiles();
        File file = files.get(0);

        WsdlFile wsdlFile = new WsdlFile(file);

        //Добавляем в дерево WSDL-файл
        projectTree.getRoot().getChildren().add(wsdlFile.getAsTreeItem());

        //Handler handler = new Handler();
        //handler.parseWsdlFile(file);


        //WSDLParser parser = new WSDLParser();

        //Definitions defs = parser.parse("http://www.thomas-bayer.com/axis2/services/BLZService?wsdl");

        //Definitions defs = parser.parse(new FileInputStream(file));
    }

    @FXML
    void treeViewMouseClicked() {

        if (projectTree.getSelectionModel().getSelectedItem() != null) {

            TreeNode treeNode = projectTree.getSelectionModel().getSelectedItem().getValue();

            if (treeNode.getClass() == WsdlFile.class) {
                WsdlFile wsdlFile = (WsdlFile) treeNode;
                //previewArea.setText(wsdlFile.getWsdlData().toString());

                previewArea.setText(wsdlFile.getFileContent());
            }

//            if (treeNode.getClass() == WsdlService.class) {
//                WsdlService wsdlService = (WsdlService) treeNode;
//                //previewArea.setText(wsdlFile.getWsdlData().toString());
//
//                previewArea.setText(wsdlService.getEndpoint());
//            }

            if (treeNode.getClass() == WsdlPort.class) {
                WsdlPort wsdlPort = (WsdlPort) treeNode;
                //previewArea.setText(wsdlFile.getWsdlData().toString());

                previewArea.setText(wsdlPort.getEndpoint());
            }
        }
    }


    @FXML
    void treeViewContextMenuRequest() {
        //JPopupMenu popup = new JPopupMenu();

        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("open a file");
        final MenuItem item2 = new MenuItem("quit");

        contextMenu.getItems().addAll(item1, item2);

        contextMenu.show(projectTree, 10, 10);

    }
}
