/*
 * Translations4Rails
 * Copyright (C) 2010 Sergei Velhas
 * http://velhas.net
 * sergei@velhas.net

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.velhas.translations4rails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;

public class TranslateUtils {

    public static List<String> getLocaleFilesList(FileObject fileInProject) {
        Project p = FileOwnerQuery.getOwner(fileInProject);
        FileObject locales = p.getProjectDirectory().getFileObject("config/locales"); //TODO: hardcoded path
        if (locales != null) {
            List<String> names = new ArrayList<String>();
            addFileNames(locales, names);
            return names;
        }

        return Collections.emptyList();
    }

    private static final String FILE_SUFFIX = ".yml";

    private static void addFileNames(FileObject file, List<String> names) {
        final String filename = file.getPath();
        if (filename.endsWith(FILE_SUFFIX)) {
            names.add(filename);
        }

        for (FileObject child : file.getChildren()) {
            addFileNames(child, names);
        }
    }

    public static String removeExtension(String s) {
      if (s.endsWith(FILE_SUFFIX)) {
        return s.substring(0, s.length() - FILE_SUFFIX.length());
      } else return s;
    }
}
