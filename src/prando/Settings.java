package prando;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Properties;
import java.util.Scanner;
import prando.stfs.ContentType;
import prando.util.Utilities;

public class Settings
{
    private Font font;
    private String Open_Path = System.getProperty("user.home");
    private String Organize_Path = System.getProperty("user.home");
    private ColorScheme Color_Scheme = ColorScheme.GRAYGREEN;
    private Kilobyte Kilobyte_Size = Kilobyte.KB1024;
    private int Search_Depth = Integer.MAX_VALUE;
    private boolean Anonymize_CON = true;
    private boolean Fully_Organize = true;
    private boolean Ignore_Whitelist = false;
    private boolean Safe_Mode = false;
    private boolean Select_First_Item = false;
    private EnumSet<ContentType> Whitelist =
            EnumSet.of(ContentType.Marketplace_Content, ContentType.Installer,
            ContentType.Arcade_Title, ContentType.Theme);
    private byte[] License = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, 0, 0, 0, 0};

    public Font getFont() { return font; }
    public String getOpenPath() { return Open_Path; }
    public String getOrganizePath() { return Organize_Path; }
    public ColorScheme getColorScheme() { return Color_Scheme; }
    public Kilobyte getKilobyteSize() { return Kilobyte_Size; }
    public int getSearchDepth() { return Search_Depth; }
    public boolean getAnonymizeCON() { return Anonymize_CON; }
    public boolean getFullyOrganize() { return Fully_Organize; }
    public boolean getIgnoreWhitelist() { return Ignore_Whitelist; }
    public boolean getSafeMode() { return Safe_Mode; }
    public boolean getSelectFirstItem() { return Select_First_Item; }
    public EnumSet<ContentType> getWhitelist() { return Whitelist; }
    public byte[] getLicense() { return License; }

    public enum ColorScheme {
        NONE, GRAYGREEN, BLACKGREEN, GRAYWHITE, BLACKWHITE }

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
        Title_Name(0),
        Location(2),
        Orig_Filename(0),
        Title_ID(0),
        Content_ID(0),
        Media_ID(0),
        TU_Number(4),
        Content_Type(0),
        Status(3),
        File_Size(0),
        Type(0);

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
        readColorSchemeSetting();
        readKilobyteSizeSetting();
        readSearchDepthSetting();
        readAnonymizeCONSetting();
        readFullyOrganizeSetting();
        readIgnoreWhitelistSetting();
        readSafeModeSetting();
        readSelectFirstItemSetting();
        readWhitelistSetting();
        readLicenseSetting();
    }//readAllSettings

    private void readFontFromResource()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/resources/LiberationMono-Regular.ttf"))
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
                "/settings/Settings.prando"))
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
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            Open_Path = config.getProperty("Open_Path");
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readOpenPathSetting

    private void readOrganizePathSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            Organize_Path = config.getProperty("Organize_Path");
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readOrganizePathSetting

    private void readColorSchemeSetting()
    {
        try(InputStream is = Prando.class.getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            int color = Integer.parseInt(config.getProperty("Color_Scheme"));

            for(ColorScheme c : ColorScheme.values())
            {
                if(color == c.ordinal())
                {
                    Color_Scheme = c;
                    break;
                }//if
            }//for
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readColorSchemeSetting

    private void readKilobyteSizeSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            int kilobyte = Integer.parseInt(config.getProperty("Kilobyte"));

            for(Kilobyte kb : Kilobyte.values())
            {
                if(kilobyte == kb.getByteCount())
                {
                    Kilobyte_Size = kb;
                    break;
                }//if
            }//for
        }//try

        catch(IOException | NullPointerException | NumberFormatException ex){}
    }//readKilobyteSizeSetting

    private void readSearchDepthSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            int temp = Integer.parseInt(config.getProperty("Search_Depth"));

            if(temp > 0)
            {
                Search_Depth = temp;
            }//if
        }//try

        catch(IOException | NullPointerException | NumberFormatException ex){}
    }//readSearchDepthSetting

    private void readAnonymizeCONSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            String setting = config.getProperty("Anonymize_CON");

            if(setting.toLowerCase().equals("false"))
            {
               Anonymize_CON = false;
            }//if
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readAnonymizeCONSetting

    private void readFullyOrganizeSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            Fully_Organize = Boolean.parseBoolean(config.getProperty(
                    "Fully_Organize"));
        }//try

        catch(NullPointerException | IOException ex){}//catch
    }//readFullyOrganizeSetting

    private void readIgnoreWhitelistSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            Ignore_Whitelist = Boolean.parseBoolean(config.getProperty(
                    "Ignore_Whitelist"));
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readIgnoreWhitelistSetting

    private void readSafeModeSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            Safe_Mode = Boolean.parseBoolean(config.getProperty("Safe_Mode"));
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readSafeModeSetting

    private void readSelectFirstItemSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            Select_First_Item = Boolean.parseBoolean(config.getProperty(
                    "Select_First_Item"));
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readSelectFirstItemSetting

    private void readWhitelistSetting()
    {
        if(!Ignore_Whitelist)
        {
            try(InputStream is = getClass().getResourceAsStream(
                    "/settings/Settings.prando"))
            {
                Properties config = new Properties();
                config.load(is);
                String contentIDs = config.getProperty("Whitelist");
                Scanner scan = new Scanner(contentIDs);
                scan.useDelimiter(",");

                EnumSet<ContentType> set = EnumSet.noneOf(ContentType.class);

                while(scan.hasNext())
                {
                    String s = scan.next();

                    for(ContentType ct : ContentType.values())
                    {
                        if(ct.getHexString().equals(s))
                        {
                            set.add(ct);
                            break;
                        }//if
                    }//for
                }//while

                Whitelist = set;
            }//try

            catch(IOException | NullPointerException ex){}//catch
        }//if
    }//readWhitelistSetting

    private void readLicenseSetting()
    {
        try(InputStream is = getClass().getResourceAsStream(
                "/settings/Settings.prando"))
        {
            Properties config = new Properties();
            config.load(is);
            String bytes = config.getProperty("License");
            Scanner scan = new Scanner(bytes);
            scan.useDelimiter(",");

            byte[] license = new byte[16];

            for(int i = 0; i < license.length; i++)
            {
                license[i] = Byte.parseByte(scan.next());
            }//for

            License = license;
        }//try

        catch(IOException | NullPointerException ex){}//catch
    }//readLicenseSetting

    public boolean isWhitelisted(String path)
    {
        byte[] b = new byte[4];
        Utilities.readHex(path, b, 0x344, 4);

        for(ContentType ct : Whitelist)
        {
            if(Arrays.equals(b, ct.getByteRepresentation()))
            {
                return true;
            }//if
        }//for

        return false;
    }//isWhitelisted
}//Settings