package prando.stfs;

public enum ContentType
{
    Arcade_Title("000D0000", new byte[]{0,13,0,0}), // XBLA
    Installer("000B0000", new byte[]{0,11,0,0}), // Title Update
    Marketplace_Content("00000002", new byte[]{0,0,0,2}); // DLC

    private String hex;
    private byte[] byteRepresentation;

    private ContentType(String h, byte[] b)
    {
        hex = h;
        byteRepresentation = b;
    }//constructor

    public String getHexString() { return hex; }
    public byte[] getByteRepresentation() { return byteRepresentation; }

    @Override
    public String toString() { return super.toString().replace("_", " "); }
}//ContentType