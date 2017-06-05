/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.timecut.ui.swt.filter;

import net.sf.timecut.model.TimeRecordFilter;
import net.sf.timecut.ui.swt.SWTWindow;

/**
 *
 * @author Carlos
 */
public interface TimeFilterView {
    public SWTWindow getWindow();
    public void deleteSelectedFilter();
    public void addCustomFilter(TimeRecordFilter filter);
}
