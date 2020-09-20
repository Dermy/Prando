package prando.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import prando.Prando;

public class PopupMenuListener extends MouseAdapter
{
    private Prando p;
    private JPopupMenu popupMenu;

    public PopupMenuListener(Prando p, JPopupMenu popupMenu)
    {
        this.p = p;
        this.popupMenu = popupMenu;
    }//constructor

    @Override
    public void mousePressed(MouseEvent e)
    {
        showPopup(e);
    }//mousePressed

    private void showPopup(MouseEvent e)
    {
        if(e.isPopupTrigger() && (p.getCustomJTable().getSelectedRow() >= 0))
        {
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }//if
    }//showPopup
}//PopupMenuListener