package ivlefilesync.util;

import ivlefilesync.Constants;
import ivlefilesync.Exceptions;
import ivlefilesync.IVLEOfflineStorage;
import ivlefilesync.IvleFileSyncApp;

public class Utils {

public static boolean checkSyncPreconditions() {
		
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
}
