package prando.util;

import java.nio.file.Path;
import java.util.Arrays;
import prando.stfs.STFSType;

public class STFSUtilities
{
    public static boolean isSTFS(String path)
    {
        byte[] b = new byte[4];
        Utilities.readHex(path, b, 0x0, 4);

        for(STFSType t : STFSType.values())
        {
            if(Arrays.equals(b, t.getByteRepresentation()))
            {
                return true;
            }//if
        }//for

        return false;
    }//isSTFS

    public static boolean isAnonymized(Path path)
    {
        int headerOffset = 0x04;
        int consoleIDOffset = 0x36C;
        int profileIDOffset = 0x371;
        int deviceIDOffset = 0x3FD;
        byte[] header = new byte[552];
        byte[] consoleID = new byte[5];
        byte[] profileID = new byte[8];
        byte[] deviceID = new byte[20];
        byte[] blankHeader = new byte[552];
        byte[] blankConsoleID = new byte[5];
        byte[] blankProfileID = new byte[8];
        byte[] blankDeviceID = new byte[20];

        Utilities.readHex(path.toString(), header, headerOffset,
                header.length);
        if(!Arrays.equals(header, blankHeader))
        {
            return false;
        }//if

        Utilities.readHex(path.toString(), consoleID, consoleIDOffset,
                consoleID.length);
        if(!Arrays.equals(consoleID, blankConsoleID))
        {
            return false;
        }//if

        Utilities.readHex(path.toString(), profileID, profileIDOffset,
                profileID.length);
        if(!Arrays.equals(profileID, blankProfileID))
        {
            return false;
        }//if

        Utilities.readHex(path.toString(), deviceID, deviceIDOffset,
                deviceID.length);
        if(!Arrays.equals(deviceID, blankDeviceID))
        {
            return false;
        }//if

        return true;
    }//isAnonymized
}//STFSUtilities