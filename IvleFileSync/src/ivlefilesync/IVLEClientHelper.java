/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ivlefilesync;
import java.util.*;
import java.io.*;

/**
 *
 * @author msk
 */
public class IVLEClientHelper {
    private static IVLELogOutput ivleLogOutput = IVLELogOutput.getInstance();

        /// <summary>
        /// Register the device against the user's account
        /// </summary>
        /// <param name="UserID"></param>
        /// <param name="API"></param>
        /// <param name="txt_Log"></param>
        /// <returns></returns>
        public static boolean RegisterDevice(String UserID, String APIKey) throws Exception
        {
            java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
            String DeviceSerial = localMachine.getHostName().toString();
            String DeviceName = localMachine.getHostName().toString();
            UUID result = IVLECoreClient.RegisterDevice(UserID, APIKey, DeviceSerial, DeviceName);
            
            IVLEOfflineStorage.SetProperty("DeviceID", result.toString());

            return result != null;
        }

        /// <summary>
        /// Unregister the device against the user's account
        /// </summary>
        /// <param name="UserID"></param>
        /// <param name="API"></param>
        /// <param name="txt_Log"></param>
        public static void UnRegisterDevice(String UserID, String APIKey)
        {
            IVLECoreClient.UnRegisterDevice(UserID, APIKey, UUID.fromString(IVLEOfflineStorage.GetPropertyValue("DeviceID")));
            IVLEOfflineStorage.SetProperty("DeviceID", "");
        }

        /// <summary>
        /// Synchronize and download latest files
        /// </summary>
        /// <param name="UserID"></param>
        /// <param name="API"></param>
        /// <param name="txt_Log"></param>
        /// <returns></returns>
        public static Device_SyncResult SyncAndDownload(String UserID, String APIKey)
        {
            Device_SyncResult result = IVLECoreClient.Sync(UserID, APIKey,UUID.fromString(IVLEOfflineStorage.GetPropertyValue("DeviceID")) );
            if (result.Success)
            {
                IVLEOfflineStorage.SetProperty("LastSync", new Date().toString());
                String theSyncDir = IVLEOfflineStorage.GetPropertyValue("SyncDirectory");
                File syncDir = new File (theSyncDir);
                if(!syncDir.exists()) {
                    try{
                        syncDir.createNewFile();
                    } catch (Exception e) {
                        ivleLogOutput.Log("Unable to create directory: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                for (int i=0; i < result.theFiles.length; i++){
                    theFile aFile = result.theFiles[i];
                    try{
                       File fileDir = new File(syncDir, aFile.DirectoryPath);
                       if (!fileDir.exists()) {
                           try{
                               fileDir.createNewFile();
                           } catch (Exception e) {
                               ivleLogOutput.Log("Unable to create directory: " + e.getMessage());
                                e.printStackTrace();
                           }
                       }
                       File realFile = new File (fileDir, aFile.FileName);
                       /// Download File here:

                    } catch (Exception e) {

                    }
                }
            }

            return result;
        }
}

