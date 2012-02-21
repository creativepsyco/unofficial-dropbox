/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package ivlefilesync;

import java.util.Date;


/**
 *
 * @author msk
 */
public class IVLELogOutput {

    private static IVLELogOutput ivleLogOutput;
    private void IVLELogOutput(){

    }

    public static IVLELogOutput getInstance(){
        if(ivleLogOutput == null) {
            ivleLogOutput = new IVLELogOutput();
        }
        return ivleLogOutput;
    }

    public void Log(String s){
        // Currently prints on the Debug Window
        System.out.println("[ivleFileSync] ["+ (new Date()).toString() +"]: "+ s);
    }
}
