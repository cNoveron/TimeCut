/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.timecut.ui.swt;

import net.sf.timecut.PlatformUtil;
import org.eclipse.swt.widgets.Display;

/**
 *
 * @author Carlos
 */
public class SWTUIAdminManager extends SWTUIManager{    
    private SWTAdminWindow _mainWindow;

    public void initUI() {
        _display = new Display();
        if (PlatformUtil.isOSLinux()) {
            _splashScreen = new SplashScreen(_display);
        }
        _mainWindow = new SWTAdminWindow(this);
    }    
}
