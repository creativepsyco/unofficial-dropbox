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

import java.util.*;
import java.io.*;

/**
 *
 * @author msk
 */
public class IVLEOfflineStorage {
    
    // The name of properties file being used
    private static final String APPLICATIONPROPPROPERTIES = "applicationProp.properties";

    private static IVLELogOutput ivleLogOutput = IVLELogOutput.getInstance();
    private static Properties defaultProp;

    /***
     * Loads the properties from the disk
     */
    public static void LoadProperties() {
        defaultProp = new Properties();
        try{
            FileInputStream in = new FileInputStream(APPLICATIONPROPPROPERTIES);
            defaultProp.load(in);
            in.close();
        } catch (Exception e) {
            ivleLogOutput.Log("Could Not Load Properties\n Hence Creating File" + e.getMessage().toString());
            // Create the File
            Save();
        }
    }

    public static void Save(){
        try{
            ivleLogOutput.Log("Saving properties file");
            FileOutputStream out = new FileOutputStream(APPLICATIONPROPPROPERTIES);
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
    /***
     * Remove a property from the properties file
     * @param key
     */
    public static void RemoveProperty(String key){
        LoadProperties();
        defaultProp.remove(key);
        Save();
    }

    /***
     * GetPropertyValue
     * @param key => the key, use from Constants.java
     * @return null if property not found, else the value in String
     */
    public static String GetPropertyValue(String key) {
        LoadProperties();
        return defaultProp.getProperty(key);
    }
}
