/*
 * Copyright (c) Rustam Vishnyakov, 2010 (dyadix@gmail.com)
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
 * $Id: PdfTaskListExporter.java,v 1.5 2010/06/08 10:38:04 dyadix Exp $
 */
package net.sf.timecut.export.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.TreeMap;

import net.sf.timecut.ResourceHelper;
import net.sf.timecut.model.Task;
import net.sf.timecut.model.TaskStatus;
import net.sf.timecut.model.Workspace;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import net.sf.timecut.TimeTracker;
import net.sf.timecut.model.WorkspaceListener;


public class PdfTaskListExporter {

    private static Font titleFont;
    private static Font sectionFont;
    private static Font textFont;
    private static Font commentFont;
    
    static {
        try {
            BaseFont baseFont = BaseFont.createFont(
                    "/net/sf/timecult/fonts/LiberationSans-Regular.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.NOT_EMBEDDED);
            titleFont = new Font(baseFont, 14, Font.BOLD);
            sectionFont = new Font(baseFont, 12, Font.BOLD);
            textFont = new Font(baseFont, 10, Font.NORMAL);
            commentFont = new Font(baseFont, 10, Font.ITALIC);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void export(Workspace ws, String fileName) throws IOException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Paragraph title = new Paragraph(ResourceHelper.getString("tasklist.title"), titleFont);
            title.setSpacingAfter(10);
            document.add(title);
            createTaskList(document, new TaskStatus(TaskStatus.IN_PROGRESS), Task.class);
            createTaskList(document, new TaskStatus(TaskStatus.FlagColor.RED), Task.class);
            createTaskList(document, new TaskStatus(TaskStatus.FlagColor.ORANGE), Task.class);
            createTaskList(document, new TaskStatus(TaskStatus.FlagColor.BLUE), Task.class);
            createTaskList(document, new TaskStatus(TaskStatus.FlagColor.GREEN), Task.class);
            createTaskList(document, new TaskStatus(TaskStatus.FlagColor.MAGENTA), Task.class);
            createTaskList(document, new TaskStatus(TaskStatus.NOT_STARTED), Task.class);
            createTaskList(document, new TaskStatus(TaskStatus.WAITING), Task.class);
        }
        catch (DocumentException e) {
            throw new IOException(e);
        }
        document.close();
    }      
    
    private static void createTaskList(Document doc, TaskStatus taskStatus, Class<Task> taskSubtype) throws DocumentException, IOException {
        String tag = "tasklist." + taskStatus.toString();
        if (taskStatus.getId() == TaskStatus.FLAGGED) {
            tag = taskStatus.getFlagColor().toString().toLowerCase() + "Flag";
        }
        String titleText = ResourceHelper.getString("button." + tag + ".tooltip");
        Paragraph subTitle = new Paragraph(titleText, sectionFont);
        subTitle.setSpacingAfter(10);
        subTitle.setSpacingBefore(10);
        doc.add(subTitle);
        Task filteredTasks[] = TimeTracker.getInstance().getWorkspace()
            .getTasksByStatus(taskStatus);
        TreeMap<String, Task> sortedItems = new TreeMap<String, Task>();
        for (int i = 0; i < filteredTasks.length; i++) {
            sortedItems.put(filteredTasks[i].toString(), filteredTasks[i]);
        }
        Collection<Task> tasks = sortedItems.values();
        com.itextpdf.text.List taskList = new List();
        for (Task task : tasks) {
            //
            // Add flagged items regardless of their subtype (task or activity)
            //
            if ((taskStatus.getId() == TaskStatus.FLAGGED && task.getStatus().getId() == TaskStatus.FLAGGED)
                || task.getClass().equals(taskSubtype)) {
                
                Paragraph namePar = new Paragraph(task.getName(), textFont);                
                ListItem taskItem = new ListItem();
                taskItem.add(namePar);
                if (taskStatus.getId() == TaskStatus.WAITING) {
                    Paragraph reasonPar = new Paragraph(task.getWaitReason().getText(), commentFont);
                    taskItem.add(reasonPar);
                }
                taskList.add(taskItem);
            }
        }
        doc.add(taskList);        
    }
}
