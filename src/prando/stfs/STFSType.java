package prando.stfs;

public enum STFSType
{
    LIVE(new byte[]{76,73,86,69}),
    PIRS(new byte[]{80,73,82,83});

    private byte[] byteRepresentation;

    private STFSType(byte[] b) { byteRepresentation = b; }
    public byte[] getByteRepresentation() { return byteRepresentation; }
}//STFSType