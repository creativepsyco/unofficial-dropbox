/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivlefilesync;

import java.lang.Thread;
import com.google.gson.*;

import java.awt.TrayIcon;
import java.io.File;
import java.util.UUID;

/**
 * 
 * @author Sukrit
 */
public class SyncThread extends Thread {

	private static final IVLELogOutput ivleLogOutput = IVLELogOutput
			.getInstance();
	private static final String UserID = IVLEOfflineStorage
			.GetPropertyValue(Constants.UserID);
	private static final String APIKey = IVLEOfflineStorage
			.GetPropertyValue(Constants.APIKey);

	private boolean checkSyncPreconditions() {

		String deviceID = IVLEOfflineStorage
				.GetPropertyValue(Constants.DeviceID);
		String syncDir = IVLEOfflineStorage
				.GetPropertyValue(Constants.SyncDirectory);
		String apiKey = IVLEOfflineStorage.GetPropertyValue(Constants.APIKey);

		//TODO: Make more detailed checks than just checking for value of property strings

		if (deviceID == null || deviceID == "") {
			Exceptions.DeviceIDNotFoundException("SyncThread");
			return false;
		}

		if (syncDir == null || syncDir == "") {
			Exceptions.SyncDirNotFoundException("SyncThread");
			return false;
		}

		if (apiKey == null || apiKey == "") {
			Exceptions.APIKeyNotFoundException("SyncThread");
			return false;
		}

		if (IvleFileSyncApp.SYNC_PENDING) {
			return false;
		}

		return true;
	}

	@Override
	public void run() {
		ivleLogOutput.Log("Thread is entering Run Stage");

		while (true) {
			if (checkSyncPreconditions()) 
			{
				IvleFileSyncApp.SYNC_PENDING = true;
				TrayIcon trayIcon = SystemTrayIcon.getInstance();
				trayIcon.displayMessage("IVLESync",
						"Synchronizing Folder",
						TrayIcon.MessageType.INFO);
				ivleLogOutput.Log("Sync starting...");

				Device_SyncResult res = IVLECoreClient.Sync(IVLEOfflineStorage
						.GetPropertyValue(Constants.UserID), IVLEOfflineStorage
						.GetPropertyValue(Constants.APIKey), UUID
						.fromString(IVLEOfflineStorage
								.GetPropertyValue(Constants.DeviceID)));

				Gson gson = new Gson();
				String jsonStringResult = gson.toJson(res,
						Device_SyncResult.class);
				IVLEOfflineStorage.SetProperty(Constants.FileQueue,
						jsonStringResult);

				String theSyncDir = IVLEOfflineStorage
						.GetPropertyValue(Constants.SyncDirectory);

				File syncDir = new File(theSyncDir);
				if (!syncDir.exists()) {
					try {
						syncDir.createNewFile();
					} catch (Exception e) {
						ivleLogOutput.Log("Unable to create Sync directory: "
								+ e.getMessage());
						e.printStackTrace();
					}
				} else {
					// Pass the control to the Downloading thread.
					for (int i = 0; i < res.theFiles.size(); i++) {
						theFile aFile = res.theFiles.get(i);
						try {
							// Convert path into forward slashes
							aFile.DirectoryPath = aFile.DirectoryPath.replace(
									'\\', '/');
							File fileDir = new File(syncDir,
									aFile.DirectoryPath);
							if (!fileDir.exists()) {
								try {
									fileDir.mkdirs();
								} catch (Exception e) {
									ivleLogOutput
									.Log("Unable to create directory: "
											+ e.getMessage());
									e.printStackTrace();
								}
							}
							File realFile = new File(fileDir, aFile.FileName);
							// / Download File here:
							if (!realFile.exists()) {
								try {
									realFile.createNewFile();
									//Download the File
									trayIcon.displayMessage("IVLESync",
											"Downloading Files",
											TrayIcon.MessageType.INFO);
									IVLECoreClient.Download(UserID, APIKey,
											UUID.fromString(IVLEOfflineStorage
													.GetPropertyValue(
															Constants.DeviceID)
															.toString()), aFile.FileID,
															realFile);
									trayIcon.displayMessage("IVLESync",
											"Downloading Completed",
											TrayIcon.MessageType.INFO);
									Device_SyncResult resultCopy = res;
									resultCopy.theFiles.remove(i);
									IVLEOfflineStorage.SetProperty(
											Constants.FileQueue,
											new Gson().toJson(resultCopy,
													Device_SyncResult.class));

								} catch (Exception e) {
									ivleLogOutput
									.Log("Unable to create directory: "
											+ e.getMessage());
									e.printStackTrace();
								}
							} else {
								// In case file exists
								Device_SyncResult resultCopy = res;
								resultCopy.theFiles.remove(i);
								IVLEOfflineStorage.SetProperty(
										Constants.FileQueue, new Gson().toJson(
												resultCopy,
												Device_SyncResult.class));

							}

						} catch (Exception e) {
							ivleLogOutput.Log("Could not download the file" + e.getMessage());
						}

					}
					// FileDownload.Download(result, UserID, APIKey);
				}
				ivleLogOutput.Log("Sync done");
				trayIcon.displayMessage("IVLESync",
						"Synchronization Completed",
						TrayIcon.MessageType.INFO);
				IvleFileSyncApp.SYNC_PENDING = false;

			} else {
				ivleLogOutput.Log("Cannot Start Sync Since preconditions were not met...");
			}

			try {
				sleep(1000 * IVLEConfig.getInstance().CLIENT_FREQ_IN_SECONDS);
			} catch (Exception e) {
				// Something is causing not to sleep?
			}
		}
	}
}
