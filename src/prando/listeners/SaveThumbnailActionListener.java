package prando.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import prando.Prando;

public class SaveThumbnailActionListener implements ActionListener
{
    private Prando p;
    private String filename;

    public SaveThumbnailActionListener(Prando p, String filename)
    {
        this.p = p;
        this.filename = filename;
    }//constructor

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        p.getInputOutput().saveFile(p, "Thumbnail", filename,
                p.getTable().getSelectedVPackage().getBasePackage().
                getContentThumbnail());
    }//actionPerformed
}//SaveThumbnailActionListener