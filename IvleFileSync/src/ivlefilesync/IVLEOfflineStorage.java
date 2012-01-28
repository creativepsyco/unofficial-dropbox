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
public class IVLEOfflineStorage {

    private static IVLELogOutput ivleLogOutput = IVLELogOutput.getInstance();
    private static Properties defaultProp;

    public static void LoadProperties() {
        defaultProp = new Properties();
        try{
            FileInputStream in = new FileInputStream("applicationProp.properties");
            defaultProp.load(in);
            in.close();
        } catch (Exception e) {
            ivleLogOutput.Log("Exception occured" + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    public static void Save(){
        try{
            ivleLogOutput.Log("Saving properties file");
            FileOutputStream out = new FileOutputStream("applicationProp.properties");
            defaultProp.store(out, "---No Comment---");
            out.close();
        } catch (Exception e) {
            ivleLogOutput.Log("Exception occured" + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    public static void SetProperty(String key, String value) {
        LoadProperties();
        defaultProp.setProperty(key, value);
        Save();
    }
    
    public static void RemoveProperty(String key){
        LoadProperties();
        defaultProp.remove(key);
        Save();
    }

    public static String GetPropertyValue(String key) {
        LoadProperties();
        return defaultProp.getProperty(key);
    }
}
