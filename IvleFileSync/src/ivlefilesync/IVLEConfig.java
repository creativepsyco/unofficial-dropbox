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
    // Follow the singleton pattern
    // This class is for preferences
    // All the static strings go in here
    private static IVLEConfig ivleConfig = null;

    public final String AppURL = "http://ivlefilesync.sgcloudapp.net/";
    public final String RegisterDeviceURL = AppURL + "api/registerdevice/";
    public final String UnRegisterDeviceURL = AppURL + "api/unregisterdevice/";
    public final String SyncDeviceURL = AppURL + "api/sync/";


    private IVLEConfig(){

    }

    public static IVLEConfig getInstance(){
        if (ivleConfig == null){
            ivleConfig = new IVLEConfig();
        }
        return ivleConfig;
    }
}
