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

import java.net.URI;
import javax.swing.table.TableColumn;

public class TranslatePanel extends javax.swing.JPanel {
  private TranslationModel model;
  private String keyname;
  /** Creates new form TranslatePanel */
  public TranslatePanel(TranslationModel m, String keyname) {
    model = m;
    this.keyname = keyname;
    initComponents();
    // Set the width of first column
    TableColumn col = translationsTable.getColumnModel().getColumn(0);
    col.setResizable(false);
    col.setMaxWidth(60);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    keyNameLabel = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    translationsTable = new javax.swing.JTable();
    jLabel2 = new javax.swing.JLabel();

    jLabel1.setText(org.openide.util.NbBundle.getMessage(TranslatePanel.class, "TranslatePanel.jLabel1.text")); // NOI18N

    keyNameLabel.setText(keyname);

    translationsTable.setFont(new java.awt.Font("Arial", 0, 13));
    translationsTable.setModel(model);
    jScrollPane1.setViewportView(translationsTable);

    jLabel2.setForeground(java.awt.Color.blue);
    jLabel2.setText(org.openide.util.NbBundle.getMessage(TranslatePanel.class, "TranslatePanel.jLabel2.text")); // NOI18N
    jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jLabel2MouseClicked(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(keyNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE))
          .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(keyNameLabel))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel2))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
    // TODO add your handling code here:
    try {
    (java.awt.Desktop.getDesktop()).browse(new URI("http://www.velhas.net"));
    } catch (Exception ex) {};
  }//GEN-LAST:event_jLabel2MouseClicked

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JLabel keyNameLabel;
  private javax.swing.JTable translationsTable;
  // End of variables declaration//GEN-END:variables
}
