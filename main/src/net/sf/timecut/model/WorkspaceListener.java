package net.sf.timecut.model;

import java.io.File;
import net.sf.timecut.ui.GenericUIManager;

/**
 * Must be implemented to recieve workspace notifications.
 * 
 * @author rvishnyakov
 */
public interface WorkspaceListener {

    void workspaceChanged(WorkspaceEvent we);
    
    void addRecentlyOpenFile(File file);
    
    File[] getRecentlyOpenFiles();
    
    GenericUIManager getUIManager();
}
