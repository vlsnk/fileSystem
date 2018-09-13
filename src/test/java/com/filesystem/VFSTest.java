package com.filesystem;

import com.filesystem.implementation.FileAlreadyExistException;
import com.filesystem.implementation.VirtualFileSystem;
import com.filesystem.implementation.WrongNameException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VFSTest {

    static VFS system = VirtualFileSystem.getFileSystem();
    static List<VFile> list = new ArrayList<>();
    static boolean init = false;
    /**
     * create virtual file system and its files before all tests
     */
    @BeforeAll
    public static void setUp() throws Exception {
        initVFS(list);
    }

    @Test
    public void getRoot() {
        VirtualDirectory root = system.getRoot();
        assertEquals("root", root.getName());
    }

    @Test
    public void getAccess() {
        Access access = system.getAccess();
        assertNotNull(access);
    }

    /**
     * Try to find root
     */
    @Test
    public void findFile() {
        VFile f = system.findFile("root");
        assertEquals(system.getRoot(), f);
    }

    /**
     * Find files test
     * try to search all files from list
     */
    @Test
    public void findAllFile() {
        for (VFile f : list) {
            VFile newF = system.findFile(f.getName());
            assertEquals(f, newF);
        }
    }

    /**
     * create files in virtual file system
     * @param l ArrayList object with all vfs files
     */
    public static void initVFS(List<VFile> l) {
        if (!init) {
            VirtualDirectory root = system.getRoot();

            VirtualDirectory dir1;
            VirtualDirectory dir2;
            VirtualDirectory dir11;
            VirtualDirectory dir21;
            VirtualFile file1;
            VirtualFile file11;
            VirtualFile file12;
            VirtualFile file21;
            VirtualFile file22;
            VirtualFile file111;
            VirtualFile file211;
            try {
                dir1 = root.createDirectory("new");
                file1 = root.createFile("newFile");

                dir11 = dir1.createDirectory("11");
                file11 = dir1.createFile("11_");

                file12 = dir1.createFile("12");
                file111 = dir11.createFile("111");

                l.add(dir1);
                l.add(file1);
                l.add(dir11);
                l.add(file11);
                l.add(file12);
                l.add(file111);

                dir2 = root.createDirectory("new2");

                dir21 = dir2.createDirectory("21");
                file21 = dir2.createFile("21_");

                file22 = dir2.createFile("22");
                file211 = dir21.createFile("211");

                l.add(dir2);
                l.add(dir21);
                l.add(file21);
                l.add(file22);
                l.add(file211);
                init = true;
            } catch (FileAlreadyExistException | WrongNameException e) {
                e.printStackTrace();
            }
        }
    }
}