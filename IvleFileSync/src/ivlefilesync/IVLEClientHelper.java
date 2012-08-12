/*
 * Copyright (c) 2012. $author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivlefilesync;
import ivlefilesync.util.*;
import com.google.gson.Gson;
import ivlefilesync.util.FileDownload;
import java.util.*;
import java.awt.TrayIcon;
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

        if(result != null){
	        IVLEOfflineStorage.SetProperty(Constants.DeviceID, result.toString());
	        IVLEOfflineStorage.SetProperty(Constants.APIKey, APIKey);
	        IVLEOfflineStorage.SetProperty(Constants.UserID, UserID);
        }

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
    	
    	TrayIcon trayIcon = SystemTrayIcon.getInstance();
		trayIcon.displayMessage("IVLESync",
			    "Synchronizing Folder",
			    	TrayIcon.MessageType.INFO);
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

        		trayIcon.displayMessage("IVLESync",
        			    "Downloading Files",
        			    	TrayIcon.MessageType.INFO);
                // Pass the control to the Downloading thread.
                FileDownload.Download(result, UserID, APIKey);
                
                trayIcon.displayMessage("IVLESync",
        			    "Downloading Completed",
        			    	TrayIcon.MessageType.INFO);
                
            }
        }
		trayIcon.displayMessage("IVLESync",
			    "Synchronization Completed",
			    	TrayIcon.MessageType.INFO);
        return result;
    }
}
