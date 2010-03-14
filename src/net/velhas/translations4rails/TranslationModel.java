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

import javax.swing.table.AbstractTableModel;

public class TranslationModel extends AbstractTableModel {

  private String[] columnNames;
  private Object[][] data;

  public TranslationModel(String[] columnNames, Object[][] data) {
    super();
    this.columnNames = columnNames;
    this.data = data;
  }

  public int getColumnCount() {
    return columnNames.length;
  }

  public int getRowCount() {
    return data.length;
  }

  public String getColumnName(int col) {
    return columnNames[col];
  }

  public Object getValueAt(int row, int col) {
    return data[row][col];
  }


  /*
   * Don't need to implement this method unless your table's
   * editable.
   */
  public boolean isCellEditable(int row, int col) {
    //Note that the data/cell address is constant,
    //no matter where the cell appears onscreen.
      return (col > 0);
  }

  /*
   * Don't need to implement this method unless your table's
   * data can change.
   */
  @Override
  public void setValueAt(Object value, int row, int col) {
    data[row][col] = value;
    fireTableCellUpdated(row, col);

  }

}
