package prando.stfs;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class ModifySTFS
{
    private static String s = File.separator;
    private static ArrayList<String> errors = new ArrayList<>();

    public static ArrayList<String> getErrors() { return errors; }

    private ModifySTFS(){}

    public static void patch(STFSPackage pkg, byte[] license)
    {
        if(!Files.exists(pkg.getPath()))
        {
            errors.add(pkg + " : Could not be found for patching.");
        }//if

        try(RandomAccessFile raf = new RandomAccessFile(pkg.toString(), "rw"))
        {
            int licenseOffset = 0x22C;
            byte[] blankLicense = new byte[16];

            raf.seek(licenseOffset);
            raf.write(license);

            for(int i = 1; i < blankLicense.length; i++)
            {
                raf.write(blankLicense);
            }//for
        }//try

        catch(IOException ex)
        {
            errors.add(pkg + " : Could not be patched properly.");
        }//catch
    }//patch

    public static STFSPackage rename(STFSPackage pkg)
    {
        Path source = pkg.getPath();
        Path destination = Paths.get(pkg.getPath().getParent() + s +
                pkg.getProperFilename());
        Path renamed;

        try
        {
            renamed = Files.move(source, destination,
               StandardCopyOption.REPLACE_EXISTING);
        }//try

        catch(IOException ex)
        {
            errors.add(pkg + " : Could not be renamed properly.");
            return pkg;
        }//catch

        return new STFSPackage(renamed.toString());
    }//rename

    public static void organize(STFSPackage pkg, String organizeDirectory)
    {
        Path dirs;
        Path source = pkg.getPath();
        Path destination;

        if(pkg.getTitleUpdateStatus() == TitleUpdateStatus.OLDUPDATE)
        {
            dirs = Paths.get(organizeDirectory + s + "Cache");
            destination = Paths.get(organizeDirectory + s + "Cache" + s +
                    pkg.getPath().getFileName());
        }//if

        else
        {
            dirs = Paths.get(organizeDirectory + s +
                    "Content" + s + "0000000000000000" + s +
                    pkg.getTitleID() + s + pkg.getContentID());
            destination = Paths.get(organizeDirectory + s +
                    "Content" + s + "0000000000000000" + s +
                    pkg.getTitleID() + s + pkg.getContentID() + s +
                    pkg.getPath().getFileName());
        }//else

        try
        {
            Files.createDirectories(dirs);
            Files.move(source, destination,
                    StandardCopyOption.REPLACE_EXISTING);
        }//try

        catch(IOException ex)
        {
            errors.add(pkg + " : Could not be organized properly.");
        }//catch
    }//organize
}//ModifySTFS