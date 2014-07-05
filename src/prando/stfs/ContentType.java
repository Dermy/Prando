package prando.stfs;

public enum ContentType
{
    Arcade_Title("000D0000", new byte[]{0,13,0,0}),
    Avatar_Item("00009000", new byte[]{0,0,-112,0}),
    Cache_File("00040000", new byte[]{0,4,0,0}),
    Community_Game("02000000", new byte[]{2,0,0,0}),
    Game_Demo("00080000", new byte[]{0,8,0,0}),
    Game_Picture("00020000", new byte[]{0,2,0,0}),
    Game_Title("000A0000", new byte[]{0,10,0,0}),
    Game_Trailer("000C0000", new byte[]{0,12,0,0}),
    Game_Video("00400000", new byte[]{0,64,0,0}),
    Installed_Game("00004000", new byte[]{0,0,64,0}),
    Installer("000B0000", new byte[]{0,11,0,0}), // Title Update
    IPTV_Pause_Buffer("00002000", new byte[]{0,0,32,0}),
    License_Store("000F0000", new byte[]{0,15,0,0}),
    Marketplace_Content("00000002", new byte[]{0,0,0,2}),
    Movie("00100000", new byte[]{0,16,0,0}),
    Music_Video("00300000", new byte[]{0,48,0,0}),
    Podcast_Video("00500000", new byte[]{0,80,0,0}),
    Profile("00010000", new byte[]{0,1,0,0}),
    Publisher("00000003", new byte[]{0,0,0,3}),
    Saved_Game("00000001", new byte[]{0,0,0,1}),
    Storage_Download("00050000", new byte[]{0,5,0,0}),
    Theme("00030000", new byte[]{0,3,0,0}),
    TV("00200000", new byte[]{0,32,0,0}),
    Video("00090000", new byte[]{0,9,0,0}),
    Viral_Video("00600000", new byte[]{0,96,0,0}),
    Xbox_Download("00070000", new byte[]{0,7,0,0}),
    Xbox_Original_Game("00005000", new byte[]{0,0,80,0}),
    Xbox_Saved_Game("00060000", new byte[]{0,6,0,0}),
    Xbox_360_Title("00001000", new byte[]{0,0,16,0}),
    Xbox_Title("00005000", new byte[]{0,0,80,0}),
    XNA("000E0000", new byte[]{0,14,0,0});

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