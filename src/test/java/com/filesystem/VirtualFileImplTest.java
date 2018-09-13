package com.filesystem;

import com.filesystem.implementation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class VirtualFileImplTest {

    static VFS system = VirtualFileSystem.getFileSystem();
    static List<VFile> list = new ArrayList<>();
    static List<UserInterface> userList = new ArrayList<>();

    @BeforeAll
    public static void setUp() throws Exception {
        VFSTest.initVFS(list);
        Access access = system.getAccess();
        // in group
        User user1 = access.createUser("user1");
        //in default group
        User user2 = access.createUser("user2");
        // without group
        User user3 = access.createUser("user3");

        UserGroup group = access.createGroup("group");
        group.addUser(user1);
        access.getDefaultGroup().addUser(user2);

        User user6 = access.createUser("user6");
        UserGroup group7 = access.createGroup("group7");
        group7.addUser(user6);

        userList.add(access.getDefaultUser());
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(access.getDefaultGroup());
        userList.add(group);
        userList.add(user6);
        userList.add(group7);
    }

    /**
     * user1 has access Permission.WRITE to all files and other users don't
     */
    @Test
    public void hasAccessUser() {
        system.getRoot().setAccess(Permission.WRITE, userList.get(1));
        for (VFile file : list) {
            boolean b;
            for (UserInterface user : userList) {
                if (user.getName().equalsIgnoreCase("user1")) {
                    b = true;
                } else {
                    b = false;
                }
                boolean b1 = file.hasAccess(Permission.WRITE, user);
                assertEquals(b, b1);
            }
        }
    }

    /**
     * group1(with user1) has access Permission.READ to all files and other users don't
     */
    @Test
    public void hasAccessGroup() {
        system.getRoot().setAccess(Permission.READ, userList.get(5));
        for (VFile file : list) {
            boolean b;
            for (UserInterface user : userList) {
                if (user.getName().equals("group") || user.getName().equals("user1")) {
                    b = true;
                } else {
                    b = false;
                }
                boolean b1 = file.hasAccess(Permission.READ, user);
                assertEquals(b, b1);
            }
        }
    }

    /**
     * user3 has access Permission.EXECUTE to directory dir2 than all its children has the same access
     */
    @Test
    public void hasAccessChildren() {
        VirtualDirectory dir = (VirtualDirectory) system.getRoot().getFile("new2");
        Collection<VFile> children = dir.getAllFiles();
        for (VFile f : children) {
            assertFalse(f.hasAccess(Permission.EXECUTE, userList.get(3)));
        }
        dir.setAccess(Permission.EXECUTE, userList.get(3));
        for (VFile f : children) {
            assertTrue(f.hasAccess(Permission.EXECUTE, userList.get(3)));
        }
    }

    /**
     * defaultGroup has access Permission.EXECUTE to directory dir2 than all its children has the same access
     * then newUser will be added to defaultGroup and will have the same access
     */
    @Test
    public void hasAccessAfterCreation() {
        VirtualDirectory dir = (VirtualDirectory) system.getRoot().getFile("new2");;
        UserGroup group = system.getAccess().getDefaultGroup();
        Permission p = Permission.EXECUTE;
        dir.setAccess(p, group);
        assertTrue(dir.hasAccess(p, group));
        User newUser = (User) userList.get(2);
        system.getAccess().addToGroup(newUser, group);
        assertTrue(dir.hasAccess(p, newUser));
    }

    /**
     * check if group7(with user6) has access to dir1(and its children) than user6 has the same access to dir1
     */
    @Test
    @SuppressWarnings("unchecked")
    public void setAccessGroup() {
        VirtualDirectory file = (VirtualDirectory) system.getRoot().getFile("new");;
        Permission p = Permission.EXECUTE;
        User u = (User) userList.get(6);
        UserGroup g = (UserGroup) userList.get(7);
        file.setAccess(p, g);
        for (VFile c : file.getAllFiles()) {
            assertTrue(c.hasAccess(p, u));
        }
    }

    /**
     * check if user2(in group) has access to dir2(and its children) than default group has no access
     */
    @Test
    @SuppressWarnings("unchecked")
    public void setAccess() {
        VirtualDirectory file = (VirtualDirectory) system.getRoot().getFile("new2");
        Permission p = Permission.EXECUTE;
        User u = (User) userList.get(2);
        file.setAccess(p, u);
        for (VFile c : file.getAllFiles()) {
            assertFalse(c.hasAccess(p, userList.get(5)));
        }
    }

    /**
     * if user has been deleted from virtual file system
     * than it will be deleted from all access objects
     */
    @Test
    public void removeAccess(){
        VirtualDirectory file = (VirtualDirectory) system.getRoot().getFile("new");
        Permission p = Permission.EXECUTE;
        User u = system.getAccess().getDefaultUser();
        file.setAccess(p, system.getAccess().getDefaultGroup());
        for (VFile c : file.getAllFiles()) {
            assertTrue(c.hasAccess(p,  u));
        }
        system.removeUser(u);

        //no user found in AccessManager
        assertNull(system.getAccess().findUser(u.getName()));

        //user has no access to files
        assertFalse(system.getRoot().hasAccess(p, u));
        for (VFile c : file.getAllFiles()) {
            assertFalse(c.hasAccess(p, u));
        }
    }

    /**
     * Create file with name = null
     * It will throw WrongNameException
     */
    @Test
    public void createFileThrowsWrongNameExc() {
        String name = null;
        assertThrows(WrongNameException.class, () -> {system.getRoot().createFile(name);});
    }

    /**
     * Create file with empty string name
     * It will throw WrongNameException
     */
    @Test
    public void createFileThrowsWrongNameExc_EmptyName() {
        String name = " ";
        assertThrows(WrongNameException.class, () -> {system.getRoot().createFile(name);});
    }

    /**
     * Create file with string name that is already exists
     * It will throw FileAlreadyExistException
     */
    @Test
    public void createFileThrowsFileExistsExc() {
        String name = "newFile";
        assertThrows(FileAlreadyExistException.class, () -> {system.getRoot().createFile(name);});
    }


    /**
     * Create directory with name = null
     * It will throw WrongNameException
     */
    @Test
    public void createDirThrowsWrongNameExc() {
        String name = null;
        assertThrows(WrongNameException.class, () -> {system.getRoot().createDirectory(name);});
    }

    /**
     * Create directory with empty string name
     * It will throw WrongNameException
     */
    @Test
    public void createDirThrowsWrongNameExc_EmptyName() {
        String name = " ";
        assertThrows(WrongNameException.class, () -> {system.getRoot().createDirectory(name);});
    }

    /**
     * Create directory with string name that is already exists
     * It will throw FileAlreadyExistException
     */
    @Test
    public void createDirThrowsFileExistsExc() {
        String name = "newFile";
        assertThrows(FileAlreadyExistException.class, () -> {system.getRoot().createDirectory(name);});
    }

}