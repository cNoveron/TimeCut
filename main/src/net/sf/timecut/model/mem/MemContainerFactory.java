/*
 * File: MemContainerFactory.java
 * Created: 23.05.2005
 * 
 * Copyright (c) Rustam Vishnyakov, 2005-2006 (rvishnyakov@yahoo.com)
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
 */
package net.sf.timecut.model.mem;
import net.sf.timecut.model.*;

/**
 * A factory for project/task containers residing in memory.
 * @author rvishnyakov (rvishnyakov@yahoo.com)
 */
public class MemContainerFactory implements ContainerFactory
{

    /**
     * @return Memory project container.
     * @see dyadix.timetracker.model.ContainerFactory#createProjectContainer()
     */
    public ProjectContainer createProjectContainer()
    {
        return new MemProjectContainer();
    }

    /**
     * @return Memory task container.
     * @see dyadix.timetracker.model.ContainerFactory#createTaskContainer()
     */
    public TaskContainer createTaskContainer()
    {
        return new MemTaskContainer();
    }

    /**
     * @return Time log.
     * @see dyadix.timetracker.model.ContainerFactory#createTimeLog()
     */
    public TimeLog createTimeLog()
    {
        return new MemTimeLog();
    }

}
