package net.sf.timecut.conf;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.*;
import java.util.Properties;

import net.sf.timecut.model.WorkspaceListener;

/**
 * Provides simple services to save/and retrieve user preferences.
 * Uses "Application Data" directory on Windows and 
 * user home directory on other (non-Windows) systems
 * @author rvishnyakov
 */
public class AdminConfigurationManager extends ConfigurationManager {    

    private File _confFile = null;
    private WorkspaceListener _workspaceListener = null;
    private Point timerPos;
    private Properties _properties;
    private String _separator;

    public AdminConfigurationManager(WorkspaceListener workspaceListener) {
        super(workspaceListener);
    }

    @Override
    public void load() throws FileNotFoundException, IOException {
        if (_confFile.exists()) {
            this._properties.loadFromXML(new FileInputStream(_confFile));
            int i = 0;
            String fileStr = null;
            while ((fileStr = _properties.getProperty("files." + i)) != null) {
                File file = new File(fileStr);
                if (file.exists()) {
                    _workspaceListener.addRecentlyOpenFile(file);
                }
                i ++;
            }
            int top = Integer.parseInt(_properties.getProperty(APP_WIN_TOP));
            int left = Integer.parseInt(_properties.getProperty(APP_WIN_LEFT));
            int width = Integer.parseInt(_properties.getProperty(APP_WIN_WIDTH));
            int height = Integer.parseInt(_properties.getProperty(APP_WIN_HEIGHT));
            _workspaceListener.getUIManager().setBounds(left, top, width, height);
            String lfClassName = _properties.getProperty(LOOK_AND_FEEL);
            if (lfClassName != null) {
                _workspaceListener.getUIManager().setLookAndFeel(lfClassName);
            }
            readDefaultTimerPos(_properties);
            readTimeLogColWidths();
            readTotalsColWidths();
            readTreeTabSashWeights();            
            readSelectedTab();
            readAppPreferences(_properties); 
        }
    }

    public void save() throws IOException {
        this._properties.clear();
        //
        // Recently open files
        //
        File[] recentlyOpenFiles = _workspaceListener.getRecentlyOpenFiles();
        for (int i = 0; i < recentlyOpenFiles.length; i++) {
            this._properties.setProperty("files." + i, recentlyOpenFiles[i].getAbsolutePath());
        }
        //
        // Window coordinates
        //
        Rectangle winBounds = _workspaceListener.getUIManager().getBounds();
        this._properties.setProperty(APP_WIN_TOP, Integer.toString(winBounds.y));
        this._properties.setProperty(APP_WIN_LEFT, Integer.toString(winBounds.x));
        this._properties.setProperty(APP_WIN_HEIGHT, Integer.toString(winBounds.height));
        this._properties.setProperty(APP_WIN_WIDTH, Integer.toString(winBounds.width));
        this._properties.setProperty(LOOK_AND_FEEL, _workspaceListener.getUIManager()
            .getLookAndFeel());
        saveDefaultTimerPos(_properties);
        saveAppPreferences(_properties);
        saveTimeLogColWidths();
        saveTotalsColWidths();
        saveTreeTabSashWeights();
        saveSelectedTab();
        
        this._properties.storeToXML(new FileOutputStream(_confFile), "TimeCult Configuration");
    }
    
    private String getConfigDir() {
    	StringBuffer confDir = new StringBuffer();
    	String osName = System.getProperty("os.name");
    	if (osName.startsWith("Windows")) {
    		confDir.append(System.getenv("APPDATA"));
    		confDir.append(_separator);
    		confDir.append("TimeCult");
    	}    	
    	else { // On UNIX-based systems
    		confDir.append(System.getProperty("user.home"));
    		confDir.append(_separator);
    		confDir.append(".timecult");
    	}
        System.out.println(confDir);
    	return confDir.toString();
    }
    
    public void setDefaultTimerPos(Point pos) {
        this.timerPos = pos;
    }
    
    
    public void setDefaultTimerPos(int x, int y) {
        this.timerPos = new Point(x, y);
    }
    
    public Point getDefaultTimerPos() {
        return this.timerPos;
    }
    
    
    private void readDefaultTimerPos(Properties props) {
        String posXStr = props.getProperty(TIMER_POS_X);
        String posYStr = props.getProperty(TIMER_POS_Y);
        if (posXStr != null && posYStr != null) {
            int posX = Integer.parseInt(posXStr);
            int posY = Integer.parseInt(posYStr);
            timerPos = new  Point(posX, posY);
            AppPreferences.getInstance().setKeepTimerPos(true);
        }
    }
    
    
    private void saveDefaultTimerPos(Properties props) throws IOException {
        if (timerPos != null && AppPreferences.getInstance().isKeepTimerPos()) {
            this._properties.setProperty(TIMER_POS_X, Integer.toString(timerPos.x));
            this._properties.setProperty(TIMER_POS_Y, Integer.toString(timerPos.y));
        }
        else {
            this._properties.remove(TIMER_POS_X);
            this._properties.remove(TIMER_POS_Y);
        }
    }
    
    
    private void saveTimeLogColWidths() {
        StringBuffer buf = new StringBuffer();
        int colWidths[] = AppPreferences.getInstance().getTimeLogColWidths();
        for (int i = 0; i < colWidths.length; i++) {
            buf.append(colWidths[i]);
            if (i < colWidths.length - 1) {
                buf.append(',');
            }
        }
        this._properties.setProperty(TIME_LOG_COL_WIDTHS, buf.toString());
    }
    
    
    private void readTimeLogColWidths() {
        String widthsStr = this._properties.getProperty(TIME_LOG_COL_WIDTHS);
        AppPreferences appPrefs = AppPreferences.getInstance();
        if (widthsStr != null) {
            String colWidths[] = widthsStr.split(",");
            for (int i = 0; i < colWidths.length; i ++) {
                appPrefs.setTimeLogColWidth(i, Integer.parseInt(colWidths[i]));
            }
        }
    }
    
    
    private void saveTotalsColWidths() {
        StringBuffer buf = new StringBuffer();
        int colWidths[] = AppPreferences.getInstance().getTotalsColWidths();
        for (int i = 0; i < colWidths.length; i++) {
            buf.append(colWidths[i]);
            if (i < colWidths.length - 1) {
                buf.append(',');
            }
        }
        this._properties.setProperty(TOTALS_COL_WIDTHS, buf.toString());
    }
    
    
    private void readTotalsColWidths() {
        String widthsStr = this._properties.getProperty(TOTALS_COL_WIDTHS);
        AppPreferences appPrefs = AppPreferences.getInstance();
        if (widthsStr != null) {
            String colWidths[] = widthsStr.split(",");
            for (int i = 0; i < colWidths.length; i++) {
                appPrefs.setTotalsColWidth(i, Integer.parseInt(colWidths[i]));
            }
        }
    }
    
    
    private void saveTreeTabSashWeights() {
        StringBuffer buf = new StringBuffer();
        int weights[] = AppPreferences.getInstance().getTreeTabSashWeights();
        for (int i = 0; i < weights.length; i++) {
            buf.append(weights[i]);
            if (i < weights.length - 1) {
                buf.append(',');
            }
        }
        this._properties.setProperty(TREE_TAB_SASH_WEIGHTS, buf.toString());
    }
    
    
    private void readTreeTabSashWeights() {
        String weightsStr = this._properties.getProperty(TREE_TAB_SASH_WEIGHTS);
        int weights[] = null;
        if (weightsStr != null) {
            String weightStr[] = weightsStr.split(",");
            weights = new int[AppPreferences.getInstance().getTreeTabSashWeights().length];
            for (int i = 0; i < weights.length; i ++) {
                weights[i] = Integer.parseInt(weightStr[i]);
            }
            AppPreferences.getInstance().setTreeTabSashWeights(weights);
        }
    }
    
    
    private void readSelectedTab() {
        String selectedTabStr = this._properties.getProperty(SELECTED_TAB);
        if (selectedTabStr != null) {
            AppPreferences.getInstance().setSelectedTab(Integer.parseInt(selectedTabStr));
        }
    }
    
    
    private void saveSelectedTab() {
        this._properties.setProperty(SELECTED_TAB, Integer.toString(AppPreferences
            .getInstance().getSelectedTab()));
    }
    
    
    private void saveAppPreferences(Properties props) {
        AppPreferences appPrefs = AppPreferences.getInstance();
        props.setProperty(AppPreferences.HIDE_WHEN_MINIMIZED, Boolean
            .toString(appPrefs.isHideWhenMinimized()));
        props.setProperty(AppPreferences.AUTO_OPEN_RECENT_FILE, Boolean
            .toString(appPrefs.isAutoOpenRecentFile()));
        props.setProperty(AppPreferences.HIDE_CLOSED, Boolean.toString(appPrefs.isHideClosed()));
        props.setProperty(AppPreferences.AUTOSAVE, Boolean.toString(appPrefs.isAutoSave()));
        props.setProperty(AppPreferences.SHOW_REC_EDIT_DIALOG, Boolean.toString(appPrefs.isShowRecEditDialog()));
        props.setProperty(AppPreferences.DONT_SAVE_EMPTY_TIME_REC, Boolean.toString(appPrefs.isDontSaveEmptyTimeRec()));
        props.setProperty(AppPreferences.RUNNING_TIMER_NOTIFICATION, Boolean.toString(appPrefs.isRunningTimerNotification()));
        props.setProperty(AppPreferences.IDLE_TIME_NOTIFICATION, Boolean.toString(appPrefs.isIdleTimeNotification()));
    }


    private void readAppPreferences(Properties props) {
        AppPreferences appPrefs = AppPreferences.getInstance();
        if (props.getProperty(AppPreferences.HIDE_WHEN_MINIMIZED) != null) {
            appPrefs.setHideWhenMinimized(Boolean.parseBoolean(props
                .getProperty(AppPreferences.HIDE_WHEN_MINIMIZED)));
        }
        if (props.getProperty(AppPreferences.AUTO_OPEN_RECENT_FILE) != null) {
            appPrefs.setAutoOpenRecentFile(Boolean.parseBoolean(props
                .getProperty(AppPreferences.AUTO_OPEN_RECENT_FILE)));
        }
        if (props.getProperty(AppPreferences.HIDE_CLOSED) != null) {
            appPrefs.setHideClosed(Boolean.parseBoolean(props
                .getProperty(AppPreferences.HIDE_CLOSED)));
        }
        if (props.getProperty(AppPreferences.AUTOSAVE) != null) {
            appPrefs.setAutoSave(Boolean.parseBoolean(props
                .getProperty(AppPreferences.AUTOSAVE)));
        }
        if (props.getProperty(AppPreferences.SHOW_REC_EDIT_DIALOG) != null) {
            appPrefs.setShowRecEditDialog(Boolean.parseBoolean(props
                .getProperty(AppPreferences.SHOW_REC_EDIT_DIALOG)));
        }
        if (props.getProperty(AppPreferences.DONT_SAVE_EMPTY_TIME_REC) != null) {
            appPrefs.setDontSaveEmptyTimeRec(Boolean.parseBoolean(props
                .getProperty(AppPreferences.DONT_SAVE_EMPTY_TIME_REC)));
        }
        if (props.getProperty(AppPreferences.RUNNING_TIMER_NOTIFICATION) != null) {
            appPrefs.setRunningTimerNotification(Boolean.parseBoolean(props
                .getProperty(AppPreferences.RUNNING_TIMER_NOTIFICATION)));
        }
        if (props.getProperty(AppPreferences.IDLE_TIME_NOTIFICATION) != null) {
            appPrefs.setIdleTimeNotification(Boolean.parseBoolean(props
                .getProperty(AppPreferences.IDLE_TIME_NOTIFICATION)));
        }
    }

}
