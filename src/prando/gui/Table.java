package prando.gui;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import prando.Prando;
import prando.Settings.Column;

public class Table
{
    private Prando p;
    private ViewableSTFSPackage currentlySelected;

    public Table(Prando p)
    {
        this.p = p;
    }//constructor

    public ViewableSTFSPackage getSelectedVPackage()
    {
        return currentlySelected;
    }//getSelectedVPackge

    public void setSelectedVPackage(ViewableSTFSPackage currentlySelected)
    {
        this.currentlySelected = currentlySelected;
    }//setSelectedVPackage

    public CustomJTable build()
    {
        CustomJTable customJTable = new CustomJTable();
        CustomTableModel customTableModel = new CustomTableModel(
                p.getPackageList());
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
                customTableModel);
        TableColumn[] columns = new TableColumn[customTableModel.
                getColumnCount()];

        customJTable.setModel(customTableModel);
        customJTable.setRowSorter(sorter);
        customJTable.setFont(p.getSettings().getFont());
        customJTable.setFillsViewportHeight(true);
        customJTable.setPreferredScrollableViewportSize(
                new Dimension(650, 160));
        customJTable.getTableHeader().setReorderingAllowed(false);
        customJTable.getTableHeader().setFocusable(false);
        customJTable.getTableHeader().setFont(p.getSettings().getFont());
        customJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        arrangeColumns(customJTable, columns);
        alignDataToLeftOfCell(customJTable);

        if(customJTable.getColumnCount() > 0)
        {
            customJTable.getColumnModel().getColumn(0).setPreferredWidth(395);
        }//if

        return customJTable;
    }//build

    private void arrangeColumns(CustomJTable customJTable,
            TableColumn[] columns)
    {
        for(Column c : Column.values())
        {
            if(c.getPosition() > 0)
            {
                columns[c.getPosition() - 1] = customJTable.
                        getColumnModel().getColumn(c.ordinal());
            }//if
        }//for

        for(int i = 0; i < columns.length; i++)
        {
            customJTable.removeColumn(customJTable.getColumnModel().
                    getColumn(0));
        }//for

        for(int i = 0; i < columns.length; i++)
        {
            if(columns[i] != null)
            {
                customJTable.addColumn(columns[i]);
            }//if
        }//for
    }//arrangeColumns

    private void alignDataToLeftOfCell(CustomJTable customJTable)
    {
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        try
        {
            customJTable.getColumn("TU Number").setCellRenderer(leftRenderer);
        }//try

        catch(IllegalArgumentException ex){}//catch

        try
        {
            customJTable.getColumn("File Size").setCellRenderer(leftRenderer);
        }//try

        catch(IllegalArgumentException ex){}//catch
    }//alignDataToLeftOfCell
}//Table