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
 * IvleFileSyncApp.java
 */

package ivlefilesync;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class IvleFileSyncApp extends SingleFrameApplication {
	
	//Constants to be kept in the memory during the application lifetime
	public static boolean SYNC_PENDING = false;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        //new IvleFileSyncView(this);
        //show(new IvleFileSyncView(this));
        new IVLESystemTray().RunTray();
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of IvleFileSyncApp
     */
    public static IvleFileSyncApp getApplication() {
        return Application.getInstance(IvleFileSyncApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(IvleFileSyncApp.class, args);
    }
}
