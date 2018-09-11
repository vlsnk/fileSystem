package com.filesystem;

public interface VirtualFile extends VFile {

    void loadData(byte[] data);
    byte[] getData();

}
