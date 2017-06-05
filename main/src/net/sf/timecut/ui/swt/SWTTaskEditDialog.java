/*
 * Copyright (c) Rustam Vishnyakov, 2005-2009 (dyadix@gmail.com)
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
 * $Id: SWTTaskEditDialog.java,v 1.13 2010/02/20 12:55:20 dyadix Exp $
 */
package net.sf.timecut.ui.swt;

import net.sf.timecut.ResourceHelper;
import net.sf.timecut.TimeTracker;
import net.sf.timecut.model.Task;
import net.sf.timecut.model.ProjectTreeItem.ItemType;

public class SWTTaskEditDialog extends ProjectItemEditDialog {
    
	public SWTTaskEditDialog(SWTWindow parent, Task task) {
		super(parent, task);
		_task = task;
	}
    
    
    public SWTTaskEditDialog(SWTWindow parent) {
        super(parent, ItemType.TASK);
    }
	
			
    @Override
    protected String getTitle() {        
        if (_task == null) {
            return ResourceHelper.getString("dialog.newTask");
        }
        else {
            return ResourceHelper.getString("dialog.properties.task");
        }
    }
    
        
    @Override
    protected boolean afterUpdate() {
        TimeTracker.getInstance().updateTask(_task);
        return true;        
    }


    private Task          _task;

}
