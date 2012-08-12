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
