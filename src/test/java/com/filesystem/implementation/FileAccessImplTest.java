package com.filesystem.implementation;

import com.filesystem.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FileAccessImplTest {

    static VFS system = VirtualFileSystem.getFileSystem();
    static List<VFile> list = new ArrayList<>();
    static Access manager;

    @BeforeAll
    public static void setUp() throws WrongNameException, FileAlreadyExistException {
        VFSTest.initVFS(list);
        manager = system.getAccess();
    }

    /**
     * Check that subfolders will have different access object, but with the same rules
     * After subfolder gets new rules parent folder doesn't get it
     */
    @Test
    public void copyAccess() {
        try {
            FileAccess access = new FileAccessImpl();
            User dUser = manager.getDefaultUser();
            User user =  manager.createUser("user");

            access.setAccess(Permission.READ, dUser);
            access.setAccess(Permission.READ, user);
            access.setAccess(Permission.WRITE, user);
            access.setAccess(Permission.EXECUTE, user);

            FileAccess newAccess = FileAccessImpl.copyAccess(access);
            newAccess.setAccess(Permission.WRITE, dUser);

            boolean accessTrue = newAccess.hasAccess(Permission.WRITE, dUser);
            boolean accessFalse = access.hasAccess(Permission.WRITE, dUser);
            assertNotEquals(accessTrue, accessFalse);
        } catch (WrongNameException e) {
            e.printStackTrace();
        }
    }
}
