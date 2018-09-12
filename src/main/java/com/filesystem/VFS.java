package com.filesystem;

public interface VFS {

    /**
     * @return root directory for virtual file system
     */
    VirtualDirectory getRoot();

    /**
     *
     * @return access manager
     */
    Access getAccess();

    /**
     * Search file/directory inside root directory antd its sub-directories
     * @param name file/directory name
     * @return found virtual file/directory or null
     */
    VFile findFile(String name);

}
