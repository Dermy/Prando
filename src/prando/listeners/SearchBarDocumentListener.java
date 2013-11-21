package prando.listeners;

import java.util.regex.PatternSyntaxException;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import prando.Prando;

public class SearchBarDocumentListener implements DocumentListener
{
    private Prando p;

    public SearchBarDocumentListener(Prando p)
    {
        this.p = p;
    }//constructor

    @Override
    public void changedUpdate(DocumentEvent de)
    {
        newFilter();
    }//changedUpdate

    @Override
    public void insertUpdate(DocumentEvent de)
    {
        newFilter();
    }//insertUpdate

    @Override
    public void removeUpdate(DocumentEvent de)
    {
        newFilter();
    }//removeUpdate

    @SuppressWarnings("unchecked")
    private void newFilter()
    {
        String text = p.getMenuBar().getSearchBar().getText();
        TableRowSorter trs = ((TableRowSorter) p.getCustomJTable().
                getRowSorter());

        try
        {
            if(text.length() == 0)
            {
                trs.setRowFilter(null);
            }//if

            else
            {
                trs.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }//else
        }//try

        catch(PatternSyntaxException ex){}//catch
    }//newFilter
}//SearchBarDocumentListener