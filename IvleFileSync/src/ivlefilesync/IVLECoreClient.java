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

    /***
     *
     * @param UserID
     * @param APIKey
     * @param DeviceSerial
     * @param DeviceName
     * @return
     */
    public static UUID RegisterDevice(String UserID, String APIKey, String DeviceSerial, String DeviceName) {

        URL u;
        Gson my = new Gson();
        InputStream is = null;
        DataInputStream dis;
        String s;
        String encoding = "UTF-8";
        StringBuilder result = new StringBuilder();
        UserID = UserID.replace('\\', '_');

        try {
            u = new URL(ivleConfig.RegisterDeviceURL + URLEncoder.encode(UserID, encoding) + "/" + URLEncoder.encode(APIKey, encoding)
                    + "/" + URLEncoder.encode(DeviceSerial, encoding) + "/" + URLEncoder.encode(DeviceName, encoding));
            is = u.openStream();
            BufferedReader d = new BufferedReader(new InputStreamReader(is));

            while ((s = d.readLine()) != null) {
                result.append(s);
                ivleLogOutput.Log(s);
            }

        } catch (Exception e) {
            ivleLogOutput.Log("Exception Occured:" + e.getMessage());
            e.printStackTrace();
        }

        if (result.length() < 1) {
            return null;
        } else {
            return UUID.fromString(result.toString());
        }
    }

    /***
     *
     * @param UserID
     * @param APIKey
     * @param DeviceID
     */
    public static void UnRegisterDevice(String UserID, String APIKey, UUID DeviceID) {
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

        } catch (Exception e) {
            ivleLogOutput.Log("Exception Occured:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     * @param UserID
     * @param APIKey
     * @param DeviceID
     * @return
     */
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
        } catch (Exception e) {
            ivleLogOutput.Log("Exception Occured:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void Download(String UserID, String APIKey, UUID DeviceID, UUID FileID, File file) {
        OutputStream outStream = null;
        URLConnection uCon = null;
        String encoding = "UTF-8";
        int size = 1024;
        UserID = UserID.replace('\\', '_');

        InputStream is = null;
        try {
            URL Url;
            byte[] buf;
            int ByteRead, ByteWritten = 0;
            Url = new URL(ivleConfig.DownloadFileURL + URLEncoder.encode(UserID, encoding)
                    + "/" + URLEncoder.encode(APIKey, encoding)
                    + "/" + URLEncoder.encode(DeviceID.toString(), encoding)
                    + "/" + URLEncoder.encode(FileID.toString(), encoding));

            outStream = new BufferedOutputStream(new FileOutputStream(file));

            uCon = Url.openConnection();
            is = uCon.getInputStream();
            buf = new byte[size];
            while ((ByteRead = is.read(buf)) != -1) {
                ivleLogOutput.Log("** ");
                outStream.write(buf, 0, ByteRead);
                ByteWritten += ByteRead;
            }

            ivleLogOutput.Log("Downloaded Successfully.");

        } catch (Exception e) {
            ivleLogOutput.Log("Error in downloading the file" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                is.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                ivleLogOutput.Log("Error in trying to close the stream" + e.getMessage());
            }
        }
    }
}
