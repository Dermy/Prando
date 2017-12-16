package prando.stfs;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import prando.util.Utilities;

public class STFSPackage
{
    private Path path;
    private STFSType magic;
    private ContentType contentType;
    private TitleUpdateStatus titleUpdateStatus;
    private STFSState stfsState;
    private byte[][] licenses;
    private byte[] properFilename;
    private byte[] displayName;
    private byte[] titleName;
    private byte[] titleID;
    private byte[] mediaID;
    private byte[] contentThumbnail;
    private byte[] titleThumbnail;
    private byte updateNumber;
    private long fileSize;

    public STFSPackage(String path)
    {
        this.path = Paths.get(path);

        readMagic();
        readLicenses();
        readContentType();
        setTitleUpdateStatus();

        properFilename = new byte[21];

        if(titleUpdateStatus == TitleUpdateStatus.NOTUPDATE)
        {
            readProperFilename();
        }//if

        readMediaID();
        readTitleID();
        readDisplayName();
        readTitleName();
        readContentThumbnail();
        readTitleThumbnail();
        readUpdateNumber();

        readSTFSState();
        readFileSize();
    }//constructor

    @Override
    public String toString()
    {
        return path.toString();
    }//toString

    public Path getPath()
    {
        return path;
    }//getPath

    public STFSType getMagic()
    {
        return magic;
    }//getMagic

    public byte[][] getLicenses()
    {
        return licenses;
    }//getLicenses

    public ContentType getContentType()
    {
        return contentType;
    }//getContentType

    public String getContentID()
    {
        return contentType.getHexString();
    }//getContentType

    public TitleUpdateStatus getTitleUpdateStatus()
    {
        return titleUpdateStatus;
    }//getTitleUpdateStatus

    public String getProperFilename()
    {
        return DatatypeConverter.printHexBinary(properFilename);
    }//getProperFilename

    public String getMediaID()
    {
        return DatatypeConverter.printHexBinary(mediaID);
    }//getMediaID

    public String getTitleID()
    {
        return DatatypeConverter.printHexBinary(titleID);
    }//getTitleID

    public String getDisplayName()
    {
        try
        {
            return new String(displayName, "UNICODE").trim();
        }//try

        catch(UnsupportedEncodingException ex)
        {
            return "";
        }//catch
    }//getDisplayName

    public String getTitleName()
    {
        try
        {
            return new String(titleName, "UNICODE").trim();
        }//try

        catch(UnsupportedEncodingException ex)
        {
            return "";
        }//catch
    }//getTitleName

    public byte[] getContentThumbnail()
    {
        return contentThumbnail;
    }//getContentThumbnail

    public byte[] getTitleThumbnail()
    {
        return titleThumbnail;
    }//getTitleThumbnail

    public byte getUpdateNumber()
    {
        return updateNumber;
    }//getUpdateNumber

    public STFSState getSTFSState()
    {
        return stfsState;
    }//getSTFSState

    public long getFileSize()
    {
        return fileSize;
    }//getFileSize

    private void readMagic()
    {
        byte[] b = new byte[4];
        Utilities.readHex(path.toString(), b, 0x0, 4);

        for(STFSType m : STFSType.values())
        {
            if(Arrays.equals(b, m.getByteRepresentation()))
            {
                magic = m;
                break;
            }//if
        }//for
    }//readMagic

    private void readLicenses()
    {
        licenses = new byte[16][16];

        int licenseOffset = 0x22C;

        for(int i = 0; i < licenses.length; i++)
        {
            Utilities.readHex(path.toString(), licenses[i], licenseOffset, 16);
            licenseOffset += 0x10;
        }//for
    }//readLicenses

    private void readContentType()
    {
        byte[] b = new byte[4];
        Utilities.readHex(path.toString(), b, 0x344, 4);

        for(ContentType ct : ContentType.values())
        {
            if(Arrays.equals(b, ct.getByteRepresentation()))
            {
                contentType = ct;
                break;
            }//if
        }//for
    }//readContentType

    private void setTitleUpdateStatus()
    {
        if(ContentType.Installer == contentType)
        {
            if(path.getFileName().toString().startsWith("TU_"))
            {
                titleUpdateStatus = TitleUpdateStatus.OLDUPDATE;
            }//if

            else
            {
                titleUpdateStatus = TitleUpdateStatus.NEWUPDATE;
            }//else
        }//if

        else
        {
            titleUpdateStatus = TitleUpdateStatus.NOTUPDATE;
        }//else
    }//setTitleUpdateStatus

    private void readProperFilename()
    {
        Utilities.readHex(path.toString(), properFilename, 0x32C, 20);
        Utilities.readHex(path.toString(), properFilename, 0x360, 1, 20);
    }//readProperFilename

    private void readMediaID()
    {
        mediaID = new byte[4];
        Utilities.readHex(path.toString(), mediaID, 0x354, 4);
    }//readMediaID

    private void readTitleID()
    {
        titleID = new byte[4];
        Utilities.readHex(path.toString(), titleID, 0x360, 4);
    }//readTitleID

    private void readDisplayName()
    {
        displayName = new byte[128];
        Utilities.readHex(path.toString(), displayName, 0x411, 128);
    }//readDisplayName

    private void readTitleName()
    {
        titleName = new byte[128];
        Utilities.readHex(path.toString(), titleName, 0x1691, 128);
    }//readTitleName

    private void readContentThumbnail()
    {
        int contentThumbnailImageSize = 0;
        int contentThumbnailOffset = 0x171A;

        try(RandomAccessFile raf = new RandomAccessFile(path.toString(), "r"))
        {
            raf.seek(0x1712);
            contentThumbnailImageSize = raf.readInt();
        }//try

        catch(IOException ex){}//catch

        contentThumbnail = new byte[contentThumbnailImageSize];
        Utilities.readHex(path.toString(), contentThumbnail,
                contentThumbnailOffset, contentThumbnailImageSize);
    }//readContentThumbnail

    private void readTitleThumbnail()
    {
        int titleThumbnailImageSize = 0;
        int titleThumbnailOffset = 0x571A;

        try(RandomAccessFile raf = new RandomAccessFile(path.toString(), "r"))
        {
            raf.seek(0x1716);
            titleThumbnailImageSize = raf.readInt();
        }//try

        catch(IOException ex){}//catch

        titleThumbnail = new byte[titleThumbnailImageSize];
        Utilities.readHex(path.toString(), titleThumbnail,
                titleThumbnailOffset, titleThumbnailImageSize);
    }//readTitleThumbnail

    private void readUpdateNumber()
    {
        try(RandomAccessFile raf = new RandomAccessFile(path.toString(), "r"))
        {
            raf.seek(0x9724);
            updateNumber = raf.readByte();
        }//try

        catch(IOException ex){}//catch
    }//readUpdateNumber

    private void readSTFSState()
    {
        byte[] b = new byte[6];
        byte[] blank = new byte[6];
        Utilities.readHex(path.toString(), b, 0x6, 6);
        stfsState = Arrays.equals(b, blank) ?
                STFSState.Broken_Header : STFSState.OK;
    }//readSTFSState

    private void readFileSize()
    {
        try
        {
            fileSize = Files.size(path);
        }//try

        catch(IOException ex){}//catch
    }//readFileSize
}//STFSPackage