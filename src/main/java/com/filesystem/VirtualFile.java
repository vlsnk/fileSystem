package com.filesystem;

public interface VirtualFile extends VFile {

    /**
     * Add content to virtual file
     * @param data byte array
     */
    void loadData(byte[] data);

    /**
     * @return virtual file content
     */
    byte[] getData();

}
