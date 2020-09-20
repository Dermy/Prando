package prando.util;

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
}//STFSUtilities