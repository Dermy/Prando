package prando.listeners;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JFileChooser;
import prando.Prando;
import prando.gui.CustomJFileChooser;
import prando.gui.ViewableSTFSPackage;
import prando.stfs.ModifySTFS;
import prando.stfs.STFSPackage;
import prando.stfs.TitleUpdateStatus;
import prando.util.Utilities;

public class StartButtonActionListener implements ActionListener
{
    private Prando p;

    public StartButtonActionListener(Prando p)
    {
        this.p = p;
    }//constructor

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String organizeDirectory = null;
        int option = JFileChooser.APPROVE_OPTION;

        if(p.getFunctionPanel().getOrganize().isSelected())
        {
            CustomJFileChooser cjfc = new CustomJFileChooser(
                    p.getSettings().getOrganizePath());
            cjfc.recursivelySetFonts(cjfc, p.getSettings().getFont());
            cjfc.setPreferredSize(new Dimension(600, 400));
            Action details = cjfc.getActionMap().get("viewTypeDetails");
            details.actionPerformed(null);
            cjfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            cjfc.setDialogTitle("Organize File(s)");
            cjfc.setApproveButtonText("Choose");
            cjfc.setApproveButtonToolTipText("Select a location to put your "
                    + "organized files");

            option = cjfc.showOpenDialog(p.getJFrame());

            if(option == JFileChooser.APPROVE_OPTION)
            {
                organizeDirectory = cjfc.getSelectedFile().getPath();
            }//if
        }//if

        if(option == JFileChooser.APPROVE_OPTION)
        {
            for(ViewableSTFSPackage pkg : p.getPackageList())
            {
                STFSPackage temp = pkg.getBasePackage();

                if(p.getFunctionPanel().getPatch().isSelected() &&
                        (temp.getTitleUpdateStatus() ==
                        TitleUpdateStatus.NOTUPDATE))
                {
                    ModifySTFS.patch(temp, p.getSettings().getLicense());
                }//if

                if(p.getFunctionPanel().getRename().isSelected() &&
                        (temp.getTitleUpdateStatus() ==
                        TitleUpdateStatus.NOTUPDATE))
                {
                    temp = ModifySTFS.rename(temp);
                }//if

                if(organizeDirectory != null)
                {
                    ModifySTFS.organize(temp, organizeDirectory);
                }//if
            }//for

            if(p.getFunctionPanel().getPatch().isSelected() ||
                p.getFunctionPanel().getRename().isSelected() ||
                p.getFunctionPanel().getOrganize().isSelected())
            {
                Utilities.operationsComplete(p);
            }//if
        }//if
    }//actionPerformed
}//StartButtonActionListener