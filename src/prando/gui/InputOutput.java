package prando.gui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import javax.swing.Action;
import javax.swing.JFileChooser;
import prando.Prando;
import prando.stfs.STFSPackage;
import prando.util.STFSUtilities;

public class InputOutput
{
    public Path selectFileOrDirectory(Prando p)
    {
        CustomJFileChooser cjfc = new CustomJFileChooser(p.getSettings().
                getOpenPath());
        cjfc.recursivelySetFonts(cjfc, p.getSettings().getFont());
        cjfc.setPreferredSize(new Dimension(600, 400));
        Action details = cjfc.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        cjfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        cjfc.setDialogTitle("Select A File/Directory");
        cjfc.setApproveButtonText("Choose");
        cjfc.setApproveButtonToolTipText("Open selected file/directory");

        int option = cjfc.showOpenDialog(p.getJFrame());

        if(option == JFileChooser.CANCEL_OPTION)
        {
            System.exit(0);
        }//if

        return cjfc.getSelectedFile().toPath();
    }//selectFileOrDirectory

    public ArrayList<STFSPackage> makePackageList(final Prando p)
    {
        final ArrayList<STFSPackage> packageList = new ArrayList<>();

        try
        {
            EnumSet<FileVisitOption> opts = EnumSet.of(
                    FileVisitOption.FOLLOW_LINKS);

            Files.walkFileTree(p.getSelectedDirectory(), opts,
                    p.getSettings().getSearchDepth(),
                    new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path path,
                    BasicFileAttributes attrs) throws IOException
                {
                    if(attrs.isRegularFile() &&
                            STFSUtilities.isSTFS(path.toString()))
                    {
                        if(p.getSettings().isWhitelisted(path.toString()))
                        {
                            packageList.add(new STFSPackage(path.toString()));
                        }//if
                    }//if

                    return FileVisitResult.CONTINUE;
                }//visitFile
            });
        }//try

        catch(IOException ex){}//catch

        packageList.trimToSize();
        return packageList;
    }//makePackageList

    public void saveFile(Prando p, String fileDescription, String filename,
            byte[] data)
    {
        CustomJFileChooser cjfc = new CustomJFileChooser(p.getSettings().
                getOrganizePath());
        cjfc.recursivelySetFonts(cjfc, p.getSettings().getFont());
        cjfc.setPreferredSize(new Dimension(600, 400));
        Action details = cjfc.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        cjfc.setDialogTitle("Save " + fileDescription);
        cjfc.setApproveButtonToolTipText("Select a save location");
        cjfc.setSelectedFile(new File(filename));

        int option = cjfc.showSaveDialog(p.getJFrame());

        if(option != JFileChooser.CANCEL_OPTION)
        {
            try
            {
                Files.write(Paths.get(cjfc.getSelectedFile().toString()), data);
            }//try

            catch(IOException ex){}//catch
        }//if
    }//saveFile
}//InputOutput