package prando.gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import prando.stfs.STFSPackage;

public class CustomTableModel extends AbstractTableModel
{
    private String[] columnNames;
    private ArrayList<ViewableSTFSPackage> packageList;

    public CustomTableModel(ArrayList<ViewableSTFSPackage> packageList)
    {
        this.columnNames = new String[]{"Display Name", "Title Name",
            "File Location", "Proper Filename", "Title ID", "Content ID",
            "Media ID", "Update Number", "Content Type", "STFS State",
            "File Size", "STFS Type"};
        this.packageList = packageList;
    }//constructor

    @Override
    public int getRowCount() { return packageList.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int columnIndex)
    {
        return columnNames[columnIndex];
    }//getColumnName

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        if(columnIndex == 7)
        {
            return Byte.class;
        }//if

        if(columnIndex == 10)
        {
            return Long.class;
        }//if

        return String.class;
    }//getColumnClass

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ViewableSTFSPackage vpkg = packageList.get(rowIndex);
        STFSPackage pkg = vpkg.getBasePackage();

        switch(columnIndex)
        {
            case 0:
                return pkg.getDisplayName();
            case 1:
                return pkg.getTitleName();
            case 2:
                return vpkg.getFileLocation();
            case 3:
                return pkg.getProperFilename();
            case 4:
                return pkg.getTitleID();
            case 5:
                return pkg.getContentID();
            case 6:
                return pkg.getMediaID();
            case 7:
                return pkg.getUpdateNumber();
            case 8:
                return pkg.getContentType().toString();
            case 9:
                return pkg.getSTFSState().toString();
            case 10:
                return pkg.getFileSize();
            case 11:
                return pkg.getMagic().toString();
            default:
                return "";
        }//switch
    }//getValueAt
}//CustomTableModel