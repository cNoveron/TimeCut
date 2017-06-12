/*
 * Copyright (c) Rustam Vishnyakov, 2009 (dyadix@gmail.com)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * $Id: FilterSetupDialog.java,v 1.3 2009/11/14 13:43:32 dyadix Exp $
 */
package net.sf.timecut.ui.swt.filter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import net.sf.timecut.ui.swt.SWTUIManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.sf.timecut.ResourceHelper;
import net.sf.timecut.model.TimeRecordFilter;
import net.sf.timecut.model.TimeRecordFilterFactory;
import net.sf.timecut.ui.swt.SWTDialog;
import net.sf.timecut.ui.swt.SWTWindow;
import net.sf.timecut.ui.swt.calendar.CalendarDialog;
import net.sf.timecut.ui.swt.calendar.ICalendarDialogListener;
import net.sf.timecut.util.Formatter;

public class FilterSetupDialog extends SWTDialog implements ICalendarDialogListener {
    
    private Text filterNameField;
    private TimeFilterView filterView;
    private SWTWindow Window;
    private Text startDateField;
    private Text endDateField;
    private static FilterSetupDialog instance;
    
    private FilterSetupDialog(TimeFilterView filterView) {
        super(filterView.getWindow().getShell(), false);
        this.filterView = filterView;
        this.Window = this.filterView.getWindow();
    }


    @Override
    protected Composite createContentPanel(Shell shell) {
        Composite contentPanel = new Composite(shell, SWT.None);
        GridLayout grid = new GridLayout();
        grid.numColumns = 2;
        contentPanel.setLayout(grid);
        
        Label filterLabel = new Label(contentPanel, SWT.None);
        filterLabel.setText(ResourceHelper.getString("filter.name"));
        this.filterNameField = createTextField(contentPanel, "", 100);
        
        Label startDateLabel = new Label(contentPanel, SWT.None);
        startDateLabel.setText(ResourceHelper.getString("filter.startDate"));
        this.startDateField = SWTUIManager.addDateField(this, contentPanel);
        
        Label endDateLabel = new Label(contentPanel, SWT.None);
        endDateLabel.setText(ResourceHelper.getString("filter.endDate"));
        this.endDateField = SWTUIManager.addDateField(this, contentPanel);
        
        return contentPanel;
    }

    @Override
    protected String getTitle() {
        return ResourceHelper.getString("filter.dialog.title");
    }

    public static FilterSetupDialog getInstance(TimeFilterView filterView) {
        if (instance == null) {
            instance = new FilterSetupDialog(filterView);
        }
        return instance;
    }

    @Override
    protected boolean handleOk() {
        Date startDate = null;
        Date endDate = null;
        String filterName = this.filterNameField.getText();
        try {
            startDate = Formatter.parseDateString(startDateField.getText());
        }
        catch (ParseException e) {
            errorMessage("Invalid date format!");
            return false;
        }
        try {
            endDate = Formatter.parseDateString(endDateField.getText());
        }
        catch (ParseException e) {
            errorMessage("Invalid date format!");
            return false;
        }
        TimeRecordFilter filter = TimeRecordFilterFactory.createCustomFilter(
            filterName,
            startDate,
            endDate);
        this.filterView.addCustomFilter(filter);
        return true;
    }

    public void dateSelected(Calendar data, Text dateField) {
        dateField.setText(Formatter.toDateString(data.getTime()));
    }
}
