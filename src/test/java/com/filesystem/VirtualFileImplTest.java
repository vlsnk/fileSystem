package com.filesystem;

import com.filesystem.implementation.VirtualFileImpl;
import org.junit.Before;
import org.junit.Test;

public class VirtualFileImplTest {

    VirtualDirectory root;
    @Before
    public void setUp() throws Exception {
//        root = VirtualFileImpl.createRootDirectory("root");
    }

    @Test
    public void hasAccess() {

    }

    @Test
    public void setAccess() {
    }

    @Test
    public void getDefault() {
    }

    @Test
    public void createFile() {
        String name = null;
        try {
            root.createFile(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createDirectory() {
    }

    @Test
    public void getFile() {
    }

    @Test
    public void getAllFiles() {
    }

    @Test
    public void hasFile() {
    }

    @Test
    public void loadData() {
    }

    @Test
    public void getData() {
    }

    @Test
    public void rename() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void isDirectory() {
    }

    @Test
    public void createRootDirectory() {
    }
}