package prando.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JFileChooser;

public class CustomJFileChooser extends JFileChooser
{
    public CustomJFileChooser(String path) { super(path); }

    public void recursivelySetFonts(Component comp, Font font)
    {
        comp.setFont(font);

        if(comp instanceof Container)
        {
            Container cont = (Container) comp;

            for(int i = 0; i < cont.getComponentCount(); i++)
            {
                recursivelySetFonts(cont.getComponent(i), font);
            }//for
        }//if
    }//recursivelySetFonts
}//CustomJFileChooser