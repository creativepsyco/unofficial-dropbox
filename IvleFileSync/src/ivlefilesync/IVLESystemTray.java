/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ivlefilesync;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author msk
 */
public class IVLESystemTray {

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
					
					//TODO: Check first for being able to manually download or not
					
					Device_SyncResult res = IVLEClientHelper.SyncAndDownload(
							IVLEOfflineStorage
									.GetPropertyValue(Constants.UserID),
							IVLEOfflineStorage
									.GetPropertyValue(Constants.APIKey));
					//TODO: what happens on a sync failure!Must check for value
				}
			};

			ActionListener setPreferencesActionListener = new ActionListener() {

				public void actionPerformed(ActionEvent ae) {
					frmPreferences myPreferences = new frmPreferences();
					IvleFileSyncApp.getApplication().show(myPreferences);
				}
			};

			ActionListener aboutBoxActionListener = new ActionListener() {
				
				public void actionPerformed(ActionEvent ae) {
					showAboutBox();
				}
				
				private void showAboutBox() {
					throw new UnsupportedOperationException(
							"Not yet implemented");
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

		} else {
			// System Tray is not supported
			// TODO: Display message for unsupported feature
		}
	}

}
