/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.timecut.ui.swt;

import net.sf.timecut.model.Project;
import net.sf.timecut.model.Project.SortCriteria;
import net.sf.timecut.model.ProjectTreeItem;
import org.eclipse.swt.widgets.Tree;

/**
 *
 * @author Carlos
 */
public interface SWTTreeView {    
    public Tree getTree();
    public void setCurrentSelection(Object object);
    public boolean isVisible(ProjectTreeItem treeItem);
    public SortCriteria getSortCriteria();
}
