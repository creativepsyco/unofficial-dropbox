/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ivlefilesync;
import java.util.*;

/**
 *
 * @author msk
 */
public class Device_SyncResult {
    public boolean Success;
    public String LastSync;
    public String Error;
    public theFile[] theFiles;

    public Device_SyncResult(){

    }
}

class theFile {
    UUID FileID;
    String DirectoryPath;
    String FileName;
    theFile() {
        
    }
}
