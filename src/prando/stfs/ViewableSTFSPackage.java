package prando.stfs;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.bind.DatatypeConverter;

public class ViewableSTFSPackage
{
    private STFSPackage pkg;
    private String fileSize;
    private String location;
    private String licenses;
    private ImageIcon contentThumbnail;
    private ImageIcon titleThumbnail;
    public final static ImageIcon BLANKICON = new ImageIcon(new
            BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));

    public STFSPackage getBasePackage() { return pkg; }

    public String getFileSize() { return fileSize; }
    public String getLocation() { return location; }
    public String getLicenses() { return licenses; }
    public ImageIcon getContentThumbnail() { return contentThumbnail; }
    public ImageIcon getTitleThumbnail() { return titleThumbnail; }

    public ViewableSTFSPackage(STFSPackage pkg, double divisor,
            Path selectedDirectory)
    {
        this.pkg = pkg;
        
        setFileSize(divisor);
        setLocation(selectedDirectory);
        setLicenses();
        setContentThumbnail();
        setTitleThumbnail();
    }//constructor

    private void setFileSize(double divisor)
    {
        double numBytes = (double) pkg.getFileSize();
        double kb = numBytes / divisor;
        double mb = kb / divisor;
        double gb = mb / divisor;

        if(divisor == 1000.0)
        {
            if(numBytes < 1000000)
            {
                fileSize = String.format("%.2f", kb) + " KB";
            }//if

            else if(numBytes < 1000000000)
            {
                fileSize = String.format("%.2f", mb) + " MB";
            }//else if

            else
            {
                fileSize = String.format("%.2f", gb) + " GB";
            }//else
        }//if

        else if(divisor == 1024.0)
        {
            if(numBytes < 1048576)
            {
                fileSize = String.format("%.2f", kb) + " KB";
            }//if

            else if(numBytes < 1073741824)
            {
                fileSize = String.format("%.2f", mb) + " MB";
            }//else if

            else
            {
                fileSize = String.format("%.2f", gb) + " GB";
            }//else
        }//if
    }//setFileSize

    private void setLocation(Path selectedDirectory)
    {
        String path = pkg.toString();
        int directoryLength = selectedDirectory.toString().length();
        int nameLength = selectedDirectory.toFile().getName().length() + 1;
        int difference = directoryLength - nameLength;
        String remove = selectedDirectory.toString().substring(0, difference);
        location = path.replace(remove, "");
    }//setLocation

    private void setLicenses()
    {
        licenses = "";
        String s;
        byte[][] temp = pkg.getLicenses();

        for(int i=0; i < temp.length; i++)
        {
            s = DatatypeConverter.printHexBinary(temp[i]);
            licenses += " " + String.format("%02d", i+1) + ": " +
                    s.substring(0, 16) + " " + s.substring(16, 24) + " " +
                    s.substring(24, 32) + System.getProperty("line.separator");
        }//for
    }//setLicenses

    private void setContentThumbnail()
    {
        try
        {
            contentThumbnail = new ImageIcon(ImageIO.read(
                    new ByteArrayInputStream(pkg.getContentThumbnail())));
        }//try

        catch(NullPointerException | IOException ex)
        {
            contentThumbnail = BLANKICON;
        }//catch
    }//setContentThumbnail

    private void setTitleThumbnail()
    {
        try
        {
            titleThumbnail = new ImageIcon(ImageIO.read(
                    new ByteArrayInputStream(pkg.getTitleThumbnail())));
        }//try

        catch(NullPointerException | IOException ex)
        {
            titleThumbnail = BLANKICON;
        }//catch
    }//setTitleThumbnail
}//ViewableSTFSPackage