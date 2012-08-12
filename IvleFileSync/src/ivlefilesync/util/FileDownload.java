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

        for (int i = 0; i < result.theFiles.size(); i++) {
                    theFile aFile = result.theFiles.get(i);
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
