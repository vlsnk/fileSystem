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
        VFSTest.initVFS(system, list);
        manager = system.getAccess();
    }

    @Test
    public void copyAccess() throws WrongNameException {
        FileAccess access = new FileAccessImpl();
        User dUser = manager.getDefaultUser();
        User user = manager.createUser("user");
        access.setAccess(Permission.READ, dUser);
        access.setAccess(Permission.READ, user);
        access.setAccess(Permission.WRITE, user);
        access.setAccess(Permission.EXECUTE, user);
        FileAccess newAccess = FileAccessImpl.copyAccess(access);
        newAccess.setAccess(Permission.WRITE, dUser);
        boolean accessTrue = newAccess.hasAccess(Permission.WRITE, dUser);
        boolean accessFalse = access.hasAccess(Permission.WRITE, dUser);
        assertNotEquals(accessTrue, accessFalse);
    }
}
