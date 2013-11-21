package prando.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import prando.Prando;

public class OpenActionListener implements ActionListener
{
    private Prando p;

    public OpenActionListener(Prando p)
    {
        this.p = p;
    }//constructor

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        p.getJFrame().dispose();
        new Prando(new String[0]).createAndShowGUI();
    }//actionPerformed
}//OpenActionListener