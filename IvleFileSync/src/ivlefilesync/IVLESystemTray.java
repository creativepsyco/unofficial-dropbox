/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ivlefilesync;

import ivlefilesync.util.Utils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author msk
 */
public class IVLESystemTray {

	public static SyncThread sync_thread = new SyncThread();
	public static frmPreferences myPreferences = new frmPreferences();
	public static IvleFileSyncAboutBox aboutBox = new IvleFileSyncAboutBox(null);
	public void RunTray() {
		
		final TrayIcon trayIcon;

		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			// Image image =
			// Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/splash.png"));
			Image image = new ImageIcon(this.getClass().getResource(
					"resources/reload.png")).getImage();
			
			/* All the Action Listeners */
			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Exiting...");
					System.exit(0);
				}
			};

			ActionListener manuallySyncListener = new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					
					//FIXME: Check first for being able to manually download or not
					if(Utils.checkSyncPreconditions()){
					Device_SyncResult res = IVLEClientHelper.SyncAndDownload(
							IVLEOfflineStorage
									.GetPropertyValue(Constants.UserID),
							IVLEOfflineStorage
									.GetPropertyValue(Constants.APIKey));
					//TODO: what happens on a sync failure!Must check for value
					}
				}
			};

			ActionListener setPreferencesActionListener = new ActionListener() {

				public void actionPerformed(ActionEvent ae) {
					IvleFileSyncApp.getApplication().show(myPreferences);
				}
			};

			ActionListener aboutBoxActionListener = new ActionListener() {
				
				public void actionPerformed(ActionEvent ae) {
					showAboutBox();
				}
				
				private void showAboutBox() {
					IvleFileSyncApp.getApplication().show(aboutBox);
				}
			};

			PopupMenu popup = new PopupMenu();

			MenuItem anotherItem = new MenuItem("Manually Sync");
			anotherItem.addActionListener(manuallySyncListener);
			popup.add(anotherItem);

			MenuItem setDirItem = new MenuItem("Settings...");
			setDirItem.addActionListener(setPreferencesActionListener);
			popup.add(setDirItem);

			MenuItem aboutBoxItem = new MenuItem("About");
			aboutBoxItem.addActionListener(aboutBoxActionListener);
			popup.add(aboutBoxItem);

			MenuItem defaultItem = new MenuItem("Exit");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);

			trayIcon = new TrayIcon(image, "IVLE File Sync", popup);

			ActionListener actionListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					trayIcon.displayMessage("Action Event",
							"An Action Event Has Been Performed!",
							TrayIcon.MessageType.INFO);
				}
			};

			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(actionListener);

			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("TrayIcon could not be added.");
			}
			
			//System Tray is added successfully
			IVLELogOutput.getInstance().Log("First Time Start " + IVLEOfflineStorage.GetPropertyValue(Constants.FIRST_TIME_START));
			if(IVLEOfflineStorage.GetPropertyValue(Constants.FIRST_TIME_START)!=null){
				sync_thread.start();
			} else {
				FirstStartup frame = FirstStartup.getInstance();
				IVLELogOutput.getInstance().Log("First Time Start " + IVLEOfflineStorage.GetPropertyValue(Constants.FIRST_TIME_START));
				IvleFileSyncApp.getApplication().show(frame);	
				
				/*frame.addWindowListener(new WindowAdapter() {
		            public void windowClosing(WindowEvent evt) {
		                sync_thread.start();
		                }
				});*/
			}

		} else {
			// System Tray is not supported
			// TODO: Display message for unsupported feature
		}
	}

}
