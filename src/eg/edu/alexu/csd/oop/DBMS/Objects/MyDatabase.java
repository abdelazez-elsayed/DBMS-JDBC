package eg.edu.alexu.csd.oop.DBMS.Objects;

import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.io.File;

public class MyDatabase {
    commandState cS = new commandState();
    File file;
    public commandState create(queryParsedData data){
        System.out.println("DataBaseName " + data.getDataBaseName());
        String databaseName = data.getDataBaseName() ;
        file = new File(databaseName);
        file.mkdirs();
        String Path = file.getAbsolutePath();
        cS.setPath(Path);
        cS.setExecuted(true);
        return cS;
    }
    public commandState drop(queryParsedData data){
        try {
            String filePath = data.getDataBaseName();
            System.out.println("Path "+filePath);
            file = new File (filePath);
            if (file.isDirectory()) {
                File[] contents = file.listFiles();
                file = new File(data.getDataBaseName());
                     if (contents != null)
                         for(File f: file.listFiles())
                             f.delete();
            }
            cS.setExecuted(true);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Drop error");
        }
        return cS;
    }
}