package prando;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import prando.stfs.ContentType;
import prando.util.Utilities;

public class Settings
{
    private Font font;
    private String Open_Path = System.getProperty("user.home");
    private String Organize_Path = System.getProperty("user.home");
    private Kilobyte Kilobyte_Size = Kilobyte.KB1024;
    private int Search_Depth = Integer.MAX_VALUE;
    private boolean Enable_Safe_Mode = false;
    private boolean Select_First_Item_After_Load = false;
    private byte[] License = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, 0, 0, 0, 0};

    public Font getFont() { return font; }
    public String getOpenPath() { return Open_Path; }
    public String getOrganizePath() { return Organize_Path; }
    public Kilobyte getKilobyteSize() { return Kilobyte_Size; }
    public int getSearchDepth() { return Search_Depth; }
    public boolean getEnableSafeMode() { return Enable_Safe_Mode; }
    public boolean getSelectFirstItemAfterLoad() {
        return Select_First_Item_After_Load; }
    public byte[] getLicense() { return License; }

    public enum Kilobyte
    {
        KB1000(1000), KB1024(1024);

        private int byteCount;

        private Kilobyte(int bc)
        {
            byteCount = bc;
        }//constructor

        public int getByteCount()
        {
            return byteCount;
        }//getByteCount
    }//Kilobyte

    public enum Column
    {
        Display_Name(1),
        Title_Name(2),
        File_Location(3),
        Proper_Filename(0),
        Title_ID(0),
        Content_ID(0),
        Media_ID(0),
        Update_Number(0),
        Content_Type(0),
        Status(0),
        File_Size(0),
        STFS_Type(0);

        private int position;
        private int defaultPosition;

        private Column(int pos)
        {
            defaultPosition = pos;
            position = pos;
        }//constructor

        public int getPosition() { return position; }
        public void setPosition(int pos) { position = pos; }
        public void resetPosition() { position = defaultPosition; }
    }//Column

    public void readAllSettings()
    {
        readFontFromResource();
        readTableColumnPositionSettings();
        readOpenPathSetting();
        readOrganizePathSetting();
        readKilobyteSizeSetting();
        readSearchDepthSetting();
        readEnableSafeModeSetting();
        readSelectFirstItemAfterLoadSetting();
        readLicenseSetting();
    }//readAllSettings

    private void readFontFromResource()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/resources/Font.ttf"))
        {
            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(
                    Font.PLAIN, 13);
        }//try

        catch(FontFormatException | IOException | NullPointerException ex)
        {
            Utilities.quit(null, "Font file not found. Exiting.");
        }//catch
    }//readFontFromResource

    private void readTableColumnPositionSettings()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.properties"))
        {
            Properties config = new Properties();
            config.load(is);

            for(Column c : Column.values())
            {
                int pos = Integer.parseInt(config.getProperty(c.toString()));

                if(pos < 0 || pos > 12)
                {
                    throw new NumberFormatException();
                }//if

                c.setPosition(pos);
            }//for

            checkColumnPositionValidity();
        }//try

        catch(IOException | NullPointerException | NumberFormatException ex)
        {
            resetColumns();
        }//catch
    }//readTableColumnPositionSettings

    private void checkColumnPositionValidity()
    {
        boolean noColumnsActive = true;

        for(Column c : Column.values())
        {
            if(c.getPosition() != 0)
            {
                noColumnsActive = false;
                break;
            }//if
        }//for

        if(noColumnsActive)
        {
            resetColumns();
            return;
        }//if

        boolean hasDuplicateColumn = false;

        loop:
        for(Column c1 : Column.values())
        {
            if(c1.getPosition() != 0)
            {
                for(Column c2 : Column.values())
                {
                    if(c1 != c2 && c1.getPosition() == c2.getPosition())
                    {
                        hasDuplicateColumn = true;
                        break loop;
                    }//if
                }//for
            }//if
        }//for

        if(hasDuplicateColumn)
        {
            resetColumns();
        }//if
    }//checkColumnPositionValidity

    private void resetColumns()
    {
        for(Column c : Column.values())
        {
            c.resetPosition();
        }//for
    }//resetColumns()

    private void readOpenPathSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.properties"))
        {
            Properties config = new Properties();
            config.load(is);
            String openPath = config.getProperty("Open_Path");

            if(openPath != null)
            {
                Open_Path = openPath;
            }//if
        }//try

        catch(IOException ex){}//catch
    }//readOpenPathSetting

    private void readOrganizePathSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.properties"))
        {
            Properties config = new Properties();
            config.load(is);
            String organizePath = config.getProperty("Organize_Path");

            if(organizePath != null)
            {
                Organize_Path = organizePath;
            }//if
        }//try

        catch(IOException ex){}//catch
    }//readOrganizePathSetting

    private void readKilobyteSizeSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.properties"))
        {
            Properties config = new Properties();
            config.load(is);
            int kilobyteSize = Integer.parseInt(config.getProperty(
                    "Kilobyte_Size"));

            for(Kilobyte kilobyte : Kilobyte.values())
            {
                if(kilobyteSize == kilobyte.getByteCount())
                {
                    Kilobyte_Size = kilobyte;
                    break;
                }//if
            }//for
        }//try

        catch(IOException | NullPointerException | NumberFormatException ex){}
    }//readKilobyteSizeSetting

    private void readSearchDepthSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.properties"))
        {
            Properties config = new Properties();
            config.load(is);
            int searchDepth = Integer.parseInt(config.getProperty(
                    "Search_Depth"));

            if(searchDepth > 0)
            {
                Search_Depth = searchDepth;
            }//if
        }//try

        catch(IOException | NullPointerException | NumberFormatException ex){}
    }//readSearchDepthSetting

    private void readEnableSafeModeSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.properties"))
        {
            Properties config = new Properties();
            config.load(is);
            Enable_Safe_Mode = Boolean.parseBoolean(config.getProperty(
                    "Enable_Safe_Mode"));
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readEnableSafeModeSetting

    private void readSelectFirstItemAfterLoadSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.properties"))
        {
            Properties config = new Properties();
            config.load(is);
            Select_First_Item_After_Load = Boolean.parseBoolean(
                    config.getProperty("Select_First_Item_After_Load"));
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readSelectFirstItemAfterLoadSetting

    private void readLicenseSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.properties"))
        {
            Properties config = new Properties();
            config.load(is);
            String bytes = config.getProperty("License");

            if(bytes != null)
            {
                Scanner scan = new Scanner(bytes);
                scan.useDelimiter(",");

                byte[] license = new byte[16];

                for(int i = 0; i < license.length; i++)
                {
                    license[i] = Byte.parseByte(scan.next());
                }//for

                License = license;
            }//if
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readLicenseSetting

    public boolean isWhitelisted(String path)
    {
        byte[] b = new byte[4];
        Utilities.readHex(path, b, 0x344, 4);

        for(ContentType contentType : ContentType.values())
        {
            if(Arrays.equals(b, contentType.getByteRepresentation()))
            {
                return true;
            }//if
        }//for

        return false;
    }//isWhitelisted
}//Settings