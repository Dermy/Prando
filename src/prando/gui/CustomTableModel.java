package prando.gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import prando.stfs.STFSPackage;

public class CustomTableModel extends AbstractTableModel
{
    private ArrayList<ViewableSTFSPackage> packageList;

    public CustomTableModel(ArrayList<ViewableSTFSPackage> packageList)
    {
        this.packageList = packageList;
    }//constructor

    @Override
    public int getColumnCount() { return 12; }

    @Override
    public int getRowCount() { return packageList.size(); }

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
    public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0:
                return "Display Name";
            case 1:
                return "Title Name";
            case 2:
                return "Location";
            case 3:
                return "Orig Filename";
            case 4:
                return "Title ID";
            case 5:
                return "Content ID";
            case 6:
                return "Media ID";
            case 7:
                return "TU Number";
            case 8:
                return "Content Type";
            case 9:
                return "State";
            case 10:
                return "File Size";
            case 11:
                return "Type";
            default:
                return null;
        }//switch
    }//getColumnName

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
                return vpkg.getLocation();
            case 3:
                return pkg.getOriginalFilename();
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
                return pkg.getState().toString();
            case 10:
                return pkg.getFileSize();
            case 11:
                return pkg.getMagic().toString();
            default:
                return "";
        }//switch
    }//getValueAt
}//CustomTableModel