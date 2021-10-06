package sample;

public class DataElement {

    private String fileName;
    private String fileSize;

    public DataElement(String fileName, String fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    @Override
    public String toString()  {
        return fileName + " (" + fileSize + ")";
    }
}
