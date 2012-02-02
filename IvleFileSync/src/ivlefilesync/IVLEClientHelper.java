/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivlefilesync;

import com.google.gson.Gson;
import java.util.*;
import java.io.*;

/**
 *
 * @author msk
 */
public class IVLEClientHelper {

    private static IVLELogOutput ivleLogOutput = IVLELogOutput.getInstance();

    /***
     * Register the device against the user's account
     * @param UserID
     * @param APIKey
     * @return
     * @throws Exception
     */
    public static boolean RegisterDevice(String UserID, String APIKey) throws Exception {
        java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
        String DeviceSerial = localMachine.getHostName().toString();
        String DeviceName = localMachine.getHostName().toString();
        UUID result = IVLECoreClient.RegisterDevice(UserID, APIKey, DeviceSerial, DeviceName);

        IVLEOfflineStorage.SetProperty("DeviceID", result.toString());

        return result != null;
    }

    /***
     * Unregister the device against the user's account
     * @param UserID
     * @param APIKey
     */
    public static void UnRegisterDevice(String UserID, String APIKey) {
        IVLECoreClient.UnRegisterDevice(UserID, APIKey, UUID.fromString(IVLEOfflineStorage.GetPropertyValue("DeviceID")));
        IVLEOfflineStorage.SetProperty("DeviceID", "");
    }

    /***
     * Synchronize and download latest files
     * @param UserID
     * @param APIKey
     * @return
     */
    public static Device_SyncResult SyncAndDownload(String UserID, String APIKey) {
        Device_SyncResult result = IVLECoreClient.Sync(UserID, APIKey, UUID.fromString(IVLEOfflineStorage.GetPropertyValue("DeviceID")));
        if (result.Success) {
            
            // Set the necessary properties
            IVLEOfflineStorage.SetProperty("LastSync", new Date().toString());
            Gson gson = new Gson();
            String jsonStringForFileQueue = gson.toJson(result, Device_SyncResult.class);
            IVLEOfflineStorage.SetProperty(Constants.FileQueue, jsonStringForFileQueue);
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

                
            }
        }

        return result;
    }
}
