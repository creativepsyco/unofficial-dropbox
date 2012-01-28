/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivlefilesync;
import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;

/**
 *
 * @author msk
 */
public class IVLECoreClient {
    
    private static IVLELogOutput ivleLogOutput = IVLELogOutput.getInstance();
    private static IVLEConfig ivleConfig = IVLEConfig.getInstance();


    public static UUID RegisterDevice(String UserID, String APIKey, String DeviceSerial, String DeviceName) {

      URL u;
      Gson my = new Gson();
      InputStream is = null;
      DataInputStream dis;
      String s;
      String encoding = "UTF-8";
      StringBuilder result = new StringBuilder();
      UserID = UserID.replace('\\','_') ;

      try {
          u = new URL(ivleConfig.RegisterDeviceURL + URLEncoder.encode(UserID, encoding) + "/" + URLEncoder.encode(APIKey, encoding)
                  + "/" + URLEncoder.encode(DeviceSerial, encoding) + "/" + URLEncoder.encode(DeviceName, encoding));
          is = u.openStream();
          BufferedReader d = new BufferedReader(new InputStreamReader(is));

          while ((s = d.readLine()) != null) {
              result.append(s);
              ivleLogOutput.Log(s);
          }

      }  catch (Exception e) {
          ivleLogOutput.Log("Exception Occured:"+ e.getMessage());
          e.printStackTrace();
      }

      if(result.length()<1) {
          return null;
      } else {
          return UUID.fromString(result.toString());
      }
    }

    public static void UnRegisterDevice (String UserID, String APIKey, UUID DeviceID) {
      URL u;
      InputStream is = null;
      DataInputStream dis;
      String s;
      String encoding = "UTF-8";
      StringBuilder result = new StringBuilder();
      UserID = UserID.replace('\\', '_');

      try {
          u = new URL(ivleConfig.UnRegisterDeviceURL + URLEncoder.encode(UserID, encoding) + "/" + URLEncoder.encode(APIKey, encoding)
                  + "/" + URLEncoder.encode(DeviceID.toString(), encoding));
          ivleLogOutput.Log(u.toString());
          is = u.openStream();
          BufferedReader d = new BufferedReader(new InputStreamReader(is));

          while ((s = d.readLine()) != null) {
              result.append(s);
              ivleLogOutput.Log(s);
          }

      }  catch (Exception e) {
          ivleLogOutput.Log("Exception Occured:"+ e.getMessage());
          e.printStackTrace();
      }
    }

    public static Device_SyncResult Sync(String UserID, String APIKey, UUID DeviceID) {
      URL u;
      InputStream is = null;
      DataInputStream dis;
      String s;
      String encoding = "UTF-8";
      StringBuilder result = new StringBuilder();
      UserID = UserID.replace('\\', '_');

      try {
          u = new URL(ivleConfig.SyncDeviceURL + URLEncoder.encode(UserID, encoding) + "/" + URLEncoder.encode(APIKey, encoding)
                  + "/" + URLEncoder.encode(DeviceID.toString(), encoding));
          ivleLogOutput.Log(u.toString());
          is = u.openStream();
          BufferedReader d = new BufferedReader(new InputStreamReader(is));

          while ((s = d.readLine()) != null) {
              result.append(s);
             
          }
          ivleLogOutput.Log(result.toString());
          Gson gson = new Gson();
          Device_SyncResult syncResult = gson.fromJson(result.toString(), Device_SyncResult.class);
          return syncResult;
      }  catch (Exception e) {
          ivleLogOutput.Log("Exception Occured:"+ e.getMessage());
          e.printStackTrace();
      }
      return null;
    }

    /*
    public static void Download(string UserID, string API, Guid DeviceID, Guid FileID, string SaveFileName, System.Windows.Forms.TextBox txt_Log) {
        using(var

        wc = new WebClient()

                 
                 )
            {
                var theURL = URL + String.Format("api/download/{0}/{1}/{2}/{3}", UserID.Replace(@"\", "

            _").URLEncode(), API.URLEncode(), DeviceID.ToString().URLEncode(), FileID.ToString().URLEncode());

            Log(txt_Log, theURL);
            wc.DownloadFile(theURL, SaveFileName);
        }
    }*/
}
