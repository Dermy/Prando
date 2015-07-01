package prando.stfs;

public enum STFSState
{
    Anonymized, Unknown, Broken_Header, OK;

    @Override
    public String toString() { return super.toString().replace("_", " "); }
}//STFSState