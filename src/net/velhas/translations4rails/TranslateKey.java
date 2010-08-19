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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.cookies.EditorCookie;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public final class TranslateKey implements ActionListener {

  private static final Logger logger = Logger.getLogger(TranslateKey.class.getName());
  
  private class Locale {
    private String localeName; //locale file name
    private String keyName; //key name (with top-level locale name)
    private Map yaml; //locale Map - whole YAML tree including top-level (locale name)

    public Locale(String localeName, Map yaml, String keyName) {
      this.localeName = localeName;
      this.keyName = keyName;
      this.yaml = yaml;
    }
    
    /**
     * @return the locale file name
     */
    public String getLocaleName() {
      return localeName;
    }
    /**
     * @return the key name (with top-level locale name)
     */
    public String getKeyName() {
      return keyName;
    }
    /**
     * @return the locale Map - whole YAML tree including top-level (locale name)
     */
    public Map getYaml() {
      return yaml;
    }    
  }

  private Locale[] locales;

  private Map getKey(Map m, StringTokenizer t) {
    String s = t.nextToken();
    if (t.hasMoreTokens()) {
      if (m.get(s) == null) {//missing? - Create one
        m.put(s, new LinkedHashMap());
      }
      return getKey((Map) m.get(s), t);//next level
    } else { //last token
      if (m.get(s) == null) {//missing? - Create one
        m.put(s, "");
      }
      return m;
    }
  }

  public void actionPerformed(ActionEvent e) { //TODO: consider splitting to several methods
    Node[] n = TopComponent.getRegistry().getActivatedNodes();
    if (n.length == 1) {
      EditorCookie ec = n[0].getCookie(EditorCookie.class);
      if (ec != null) {
        JEditorPane[] panes = ec.getOpenedPanes();
        if (panes.length > 0) {
          //int cursor = panes[0].getCaret().getDot();
          //TODO: just put the cursor onto the key that is going to be localized
          String selection = panes[0].getSelectedText();
          if (selection != null) {

            List<String> localefiles = getProjectLocaleFiles(n);

            // USE selection
            //Translations table header
            String[] columnNames = {"Locale", "Translation"};
            Object[][] rows = new Object[localefiles.size()][2];
            locales = new Locale[localefiles.size()];
            int i = 0;
            for (String filename : localefiles) {
              //get the key value from each locale file. If the key is not found - create one.
              try {
                File f = new File(filename);
                InputStream input = new FileInputStream(f);
                //Fill the locales table
                //prefix selection with locale name (filename without extension)
                String localename = TranslateUtils.removeExtension(f.getName());
                String keyname = localename + "." + selection; //key in file (full key)
                Map yaml = null;
                yaml = (Map) (new Yaml()).load(input); // TODO: Catch error
                if (yaml == null) {
                  yaml = new LinkedHashMap();
                } //make sure that yaml is created

                locales[i] = new Locale(filename, yaml, keyname);
                rows[i][0] = localename;
                rows[i][1] = getTranslation(keyname, locales[i].getYaml());
                i++;
              } catch (Exception ex) {
                NotifyDescriptor.Message msg = new NotifyDescriptor.Message(
                        "An error occured while loading YAML file " + filename
                        + ":\n" + ex.getMessage()
                        + "\nThe file is skipped. Please fix the errors in the YAML file."
                        + "\nYou might find some suggestions on the plugin homepage.",
                        NotifyDescriptor.ERROR_MESSAGE);
                msg.setTitle("An error occured while loading YAML file " + filename);
                DialogDisplayer.getDefault().notifyLater(msg);
              }
            }

            TranslationModel model = new TranslationModel(columnNames, rows);

            TranslatePanel panel = new TranslatePanel(model, selection);
            DialogDescriptor d = new DialogDescriptor(panel, 
                    "Translations4Rails - '"+selection+"'");

            Object result = DialogDisplayer.getDefault().notify(d);

            //Update locale files
            if (result == DialogDescriptor.OK_OPTION) {
              for (i = 0; i < locales.length; i++) {
                //update i18n value
                Map key = getKey(locales[i].getYaml(), new StringTokenizer(locales[i].getKeyName(), ".") );
                key.put(getLastPartOfKey(locales[i].getKeyName()), model.getValueAt(i, 1));
                
                try { //save translations
                  OutputStream outputStream = new FileOutputStream(new File(locales[i].getLocaleName()));
                  Writer writer = new OutputStreamWriter(outputStream);
                  DumperOptions options = new DumperOptions();
                  options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                  (new Yaml(options)).dump(locales[i].getYaml(), writer);
                } catch (FileNotFoundException ex) {
                  Exceptions.printStackTrace(ex);
                }
              }
            }
          }
        }
      }
    }
  }

  private String getLastPartOfKey(String keyname) {
    StringTokenizer st2 = new StringTokenizer(keyname, ".");
    String key = "";
    while (st2.hasMoreTokens()) {
      key = st2.nextToken();
    }
    return key;
  }

  private List<String> getProjectLocaleFiles(Node[] n) {
    //get current file
    DataObject dataObject = n[0].getLookup().lookup(org.openide.loaders.DataObject.class);
    //get project the file belongs to
    FileObject fileObject = dataObject.getPrimaryFile();
    List<String> localefiles = TranslateUtils.getLocaleFilesList(fileObject);
    return localefiles;
  }

  private Object getTranslation(String keyname, Map yamlmap) {
    try {
      //get the key value
      String key = getLastPartOfKey(keyname);
      return (getKey(yamlmap, new StringTokenizer(keyname, "."))).get(key);
    } catch (Exception ex) {
      Exceptions.printStackTrace(ex); //TODO: better error handling
      return null;
    }
  }
}
