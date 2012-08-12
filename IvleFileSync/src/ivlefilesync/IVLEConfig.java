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

/**
 *
 * @author msk
 */
public class IVLEConfig {
	// Updated class in github
    // Follow the singleton pattern
    // This class is for preferences
    // All the static strings go in here
    private static IVLEConfig ivleConfig = null;

    public final String AppURL = "http://ivlefilesync.sgcloudapp.net/";
    public final String RegisterDeviceURL = AppURL + "api/registerdevice/";
    public final String UnRegisterDeviceURL = AppURL + "api/unregisterdevice/";
    public final String SyncDeviceURL = AppURL + "api/sync/";
    public final String DownloadFileURL = AppURL + "api/download/";
    
    public final int CLIENT_FREQ_IN_SECONDS = 30*60; //30 mins


    private IVLEConfig(){

    }

    public static IVLEConfig getInstance(){
        if (ivleConfig == null){
            ivleConfig = new IVLEConfig();
        }
        return ivleConfig;
    }
}
