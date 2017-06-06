/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.timecut.ui.swt;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

/**
 *
 * @author Carlos
 */
public interface SWTWindow {
    public IconSet getIconSet();
    public Shell getShell();
    public void updateTitle();
    public MenuFactory getMenuFactory();
    public SWTTreeView getTreeView();
    public Composite getFilterContainer();
    public void restoreWindow();
    public Menu createInProgressStartMenu(MenuItem parentItem,SelectionListener l);
    public SWTMainTabFolder getMainTabFolder();
    public SWTDataView getDataView();
    public Font getLcdFont();
    public void showPopupMessage(String message);
    public SashForm getMainTabFolderSash();
    public void showError(String message);
    public Composite getProjectTreeContainer();
    public SWTStatusLine getStatusLine();
}
