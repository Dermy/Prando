package prando.listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import prando.Prando;

public class TableListSelectionListener implements ListSelectionListener
{
    private Prando p;

    public TableListSelectionListener(Prando p)
    {
        this.p = p;
    }//constructor

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        try
        {
            p.getTable().setSelectedVPackage(p.getPackageList().get(
                    p.getCustomJTable().convertRowIndexToModel(
                    p.getCustomJTable().getSelectedRow())));

            p.getInfoPanel().updateLicensesTextArea(p.getTable().
                    getSelectedVPackage());
            p.getInfoPanel().updateInfoTextFields(p.getTable().
                    getSelectedVPackage());
        }//try

        catch(IndexOutOfBoundsException ex){}//catch
    }//valueChanged
}//TableListSelectionListener