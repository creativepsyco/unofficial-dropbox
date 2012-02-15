/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivlefilesync;

import java.lang.Thread;
import com.google.gson.*;
import java.io.File;
import java.util.UUID;

/**
 *
 * @author Sukrit
 */
public class SyncThread extends Thread {

    private static final IVLELogOutput ivleLogOutput = IVLELogOutput.getInstance();
    private static final String UserID = IVLEOfflineStorage.GetPropertyValue(Constants.UserID);
    private static final String APIKey = IVLEOfflineStorage.GetPropertyValue(Constants.APIKey);

    @Override
    public void run() {
        //TODO: Do locking on the file

        Device_SyncResult res = IVLECoreClient.Sync(
                IVLEOfflineStorage.GetPropertyValue(Constants.UserID),
                IVLEOfflineStorage.GetPropertyValue(Constants.APIKey),
                UUID.fromString(IVLEOfflineStorage.GetPropertyValue(Constants.DeviceID)));
        Gson gson = new Gson();
        String jsonStringResult = gson.toJson(res, Device_SyncResult.class);
        IVLEOfflineStorage.SetProperty(Constants.FileQueue, jsonStringResult);

        String theSyncDir = IVLEOfflineStorage.GetPropertyValue(Constants.SyncDirectory);

        File syncDir = new File(theSyncDir);
        if (!syncDir.exists()) {
            try {
                syncDir.createNewFile();
            } catch (Exception e) {
                ivleLogOutput.Log("Unable to create directory: " + e.getMessage());
                e.printStackTrace();
            }
        } else {

            // Pass the control to the Downloading thread.
            for (int i = 0; i < res.theFiles.size(); i++) {
                theFile aFile = res.theFiles.get(i);
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
                                    UUID.fromString(IVLEOfflineStorage.GetPropertyValue(Constants.DeviceID).toString()), aFile.FileID, realFile);
                            //TODO: remove entry from file queue.
                            Device_SyncResult resultCopy = res;
                            resultCopy.theFiles.remove(i);
                            IVLEOfflineStorage.SetProperty(Constants.FileQueue,
                                    new Gson().toJson(resultCopy, Device_SyncResult.class));

                        } catch (Exception e) {
                            ivleLogOutput.Log("Unable to create directory: " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        // TODO: what to do in the case the file exists
                        Device_SyncResult resultCopy = res;
                        resultCopy.theFiles.remove(i);
                        IVLEOfflineStorage.SetProperty(Constants.FileQueue,
                                new Gson().toJson(resultCopy, Device_SyncResult.class));

                    }

                } catch (Exception e) {

                }

            }

            //FileDownload.Download(result, UserID, APIKey);

        }
    }
}
