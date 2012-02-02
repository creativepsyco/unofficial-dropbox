/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ivlefilesync.util;
import  ivlefilesync.*;
import java.io.*;
import java.util.*;
import java.net.*;

/**
 * This class is going to handle all the downloading transactions
 * Assume the sync directory exists, otherwise error will be thrown.
 * @author msk
 */
public class FileDownload {

    private static IVLELogOutput ivleLogOutput = IVLELogOutput.getInstance();
    public static void Download(Device_SyncResult result, String UserID, String APIKey) {
        File syncDir = new File(IVLEOfflineStorage.GetPropertyValue(Constants.SyncDirectory));

        for (int i = 0; i < result.theFiles.length; i++) {
                    theFile aFile = result.theFiles[i];
                    try {
                        // Convert path into forward slashes
                        aFile.DirectoryPath = aFile.DirectoryPath.replace('\\', '/');
                        File fileDir = new File(syncDir, aFile.DirectoryPath);
                        if (!fileDir.exists()) {
                            try {
                                fileDir.mkdirs();
                            } catch (Exception e) {
                                ivleLogOutput.Log("Unable to create directory: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        File realFile = new File(fileDir, aFile.FileName);
                        /// Download File here:
                        if (!realFile.exists()) {
                            try {
                                realFile.createNewFile();
                                IVLECoreClient.Download(UserID, APIKey, 
                                        UUID.fromString(IVLEOfflineStorage.GetPropertyValue(Constants.DeviceID).toString())
                                        , aFile.FileID, realFile);
                            } catch (Exception e) {
                                ivleLogOutput.Log("Unable to create directory: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }

                    } catch (Exception e) {
                    }
                }
    }

}
