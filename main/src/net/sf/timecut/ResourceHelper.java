/*
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
 * 
 * $Id: ResourceHelper.java,v 1.5 2008/04/28 18:57:18 dyadix Exp $
 */
package net.sf.timecut;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Retrieves TimeTracker resources.
 * @author rvishnyakov
 */
public class ResourceHelper {

    private static ResourceBundle bundle;
    public static final String dir;

    public static String getString(String key) {
        return bundle.getString(key);
    }

    /**
     * @param key
     *            The key by which a resource must be found.
     * @return Resource URL.
     */
    public static URL getUrl(String key) {
        return ResourceHelper.class.getResource(dir+key);
    }
    
    /**
     * Opens a stream associated with the specified resource
     * @param key The resource key.
     * @return Open resource stream or null if the stream can not be
     * opened;
     */
    public static InputStream openStream(String key) {
		InputStream in = null;
                System.out.println("dir+key\n\t"+dir+key);
		try {
			URL url = ResourceHelper.class.getResource(dir+key);
                        System.out.println("url\n\t"+url);
                        in = url.openStream();
		}
		catch (Exception e) {
		}
		return in;
    }
    
    private static ResourceBundle createBundle(Locale locale) {
        return ResourceBundle.getBundle(ResourceHelper.class.getPackage()
                .getName()
                + ".timetracker", locale);
    }
    
    static {
        Locale locale;
        String lang = System.getProperty("lang");
        System.out.println(lang);
        if (lang != null) {
            locale = new Locale(lang);
        } 
        else {
            //locale = Locale.getDefault();
            locale = new Locale("es");
        }
        bundle = createBundle(locale);
        dir = "resources/net/sf/timecut/";
        //
        // Check if we are using a localized bundle. If not, use
        // english by default.
        //
        if ("no".equals(getString("localized"))) {
            locale = new Locale("es");
            bundle = createBundle(locale);
        }
    }
}
