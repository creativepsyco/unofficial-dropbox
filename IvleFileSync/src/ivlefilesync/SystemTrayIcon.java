/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package ivlefilesync;

import java.awt.*;


/**
 *
 * @author sukrit
 */
public class SystemTrayIcon {

	private static TrayIcon trayIcon;

	public static void setInstance(TrayIcon iconObject){
		trayIcon = iconObject;
	}

	public static TrayIcon getInstance(){
		return trayIcon;
	}

}
