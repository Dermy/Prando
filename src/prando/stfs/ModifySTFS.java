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

    public static void anonymizeCON(STFSPackage pkg)
    {
        if(!Files.exists(pkg.getPath()))
        {
            errors.add(pkg + " : Could not be found for anonymization.");
        }//if

        else
        {
            try(RandomAccessFile raf = new RandomAccessFile(pkg.toString(),
                    "rw"))
            {
                int headerOffset = 0x04;
                int consoleIDOffset = 0x36C;
                int profileIDOffset = 0x371;
                int deviceIDOffset = 0x3FD;
                byte[] blankHeader = new byte[552];
                byte[] blankConsoleID = new byte[5];
                byte[] blankProfileID = new byte[8];
                byte[] blankDeviceID = new byte[20];

                raf.write(blankHeader, headerOffset, blankHeader.length);
                raf.write(blankConsoleID, consoleIDOffset,
                        blankConsoleID.length);
                raf.write(blankProfileID, profileIDOffset,
                        blankProfileID.length);
                raf.write(blankDeviceID, deviceIDOffset, blankDeviceID.length);
            }//try

            catch(IOException ex)
            {
                errors.add(pkg + " : Could not be anonymized properly.");
            }//catch
        }//else
    }//anonymizeCON

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
                pkg.getOriginalFilename());
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

    public static void organize(STFSPackage pkg, String organizeDirectory,
            boolean fullyOrganize)
    {
        Path dirs;
        Path source = pkg.getPath();
        Path destination;

        if(pkg.getTitleUpdateStatus() == TitleUpdateStatus.OLDUPDATE)
        {
            if(!fullyOrganize)
            {
                dirs = Paths.get(organizeDirectory + s + "Cache");

                destination = Paths.get(organizeDirectory + s + "Cache" + s +
                        pkg.getPath().getFileName());
            }//if

            else
            {
                dirs = Paths.get(organizeDirectory + s + "Organized" + s +
                        "Cache");

                destination = Paths.get(organizeDirectory + s + "Organized" +
                        s + "Cache" + s + pkg.getPath().getFileName());
            }//else
        }//if

        else
        {
            if(!fullyOrganize)
            {
                dirs = Paths.get(organizeDirectory + s + pkg.getTitleID() + s +
                        pkg.getContentID());

                destination = Paths.get(organizeDirectory + s +
                        pkg.getTitleID() + s + pkg.getContentID() + s +
                        pkg.getPath().getFileName());
            }//if

            else
            {
                dirs = Paths.get(organizeDirectory + s + "Organized" + s +
                        "Content" + s + "0000000000000000" + s +
                        pkg.getTitleID() + s + pkg.getContentID());

                destination = Paths.get(organizeDirectory + s + "Organized" +
                        s + "Content" + s + "0000000000000000" + s +
                        pkg.getTitleID() + s + pkg.getContentID() + s +
                        pkg.getPath().getFileName());
            }//else
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