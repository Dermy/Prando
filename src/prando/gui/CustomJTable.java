package prando.gui;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CustomJTable extends JTable
{
    @Override
    public boolean isCellEditable(int d, int c) { return false; }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row,
            int column)
    {
        Object value = getValueAt(row, column);
        boolean isSelected = false;

        if(!isPaintingForPrint())
        {
            isSelected = isCellSelected(row, column);
        }//if

        return renderer.getTableCellRendererComponent(this, value,
                                                      isSelected, false,
                                                      row, column);
        /*always return false for 4th parameter
         (hasFocus) so highlight around cells
         in table does not show*/
    }//prepareRenderer
}//CustomJTable