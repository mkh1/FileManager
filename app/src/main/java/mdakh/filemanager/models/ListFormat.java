package mdakh.filemanager.models;

/**
 * Created by mdakh on 11/27/2017.
 */

public class ListFormat {

    public String FileExeption;
    public String FileType;
    public String CommendeType;
    public String Link;
    public int Papularity;

    public ListFormat(String fileExeption,String fileType,String CommendeType,String link,int papularity){
        this.FileExeption=fileExeption;
        this.FileType=fileType;
        this.CommendeType=CommendeType;
        this.Link=link;
        this.Papularity=papularity;
    }

}
