package com.filesystem;

import com.filesystem.implementation.User;
import com.filesystem.implementation.UserGroup;
import com.filesystem.implementation.VirtualFileSystem;
import com.filesystem.implementation.WrongNameException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccessTest {

    static VFS system = VirtualFileSystem.getFileSystem();
    static List<VFile> list = new ArrayList<>();
    static Access manager;

    @BeforeAll
    public static void setUp() throws Exception {
        VFSTest.initVFS(system, list);
        manager = system.getAccess();
    }

    /**
     * if username is equals, than access return one User object
     */
    @Test
    public void createUsersWithEqualNames() {
        try {
            User user1 = manager.createUser("user");
            User user2 = manager.createUser("user");
            assertEquals(user1, user2);
        } catch (WrongNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * try to create user with empty or null name
     */
    @Test
    public void createUser_NullEmpty() {
        String name = null;
        assertThrows(WrongNameException.class, () -> manager.createUser(name));
        String nameEmpty = "   ";
        assertThrows(WrongNameException.class, () -> manager.createUser(nameEmpty));
    }


    /**
     * find user by user name successfully
     */
    @Test
    public void findUserSuccess() {

        try {
            User user1 = manager.createUser("findUserTest1");
            User u = manager.findUser("FINDuserTeSt1");
            assertEquals(user1, u);
        } catch (WrongNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * don't find user by user name
     */
    @Test
    public void findUserFail() {
        User user = manager.findUser("findUserTest");
        assertNull(user);
    }

    /**
     * try to find user with null username
     */
    @Test
    public void findNullUser() {
        String name = null;
        assertThrows(NullPointerException.class, () -> manager.findUser(name));
    }

    /**
     * try to find user with empty username
     */
    @Test
    public void findEmptyUser() {
        String name = " ";
        assertNull(manager.findUser(name));
    }

    /**
     * if group name is equals, than access return one UserGroup object
     */
    @Test
    public void createGroup() {
        try {
            UserGroup group1 = manager.createGroup("group");
            UserGroup group2 = manager.createGroup("group");
            assertEquals(group1, group2);
        } catch (WrongNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * try to create group with empty or null name
     */
    @Test
    public void createGroup1() {
        String name = null;
        assertThrows(WrongNameException.class, () -> manager.createGroup(name));
        String nameEmpty = "   ";
        assertThrows(WrongNameException.class, () -> manager.createGroup(nameEmpty));
    }

    /**
     * find group by name successfully
     */
    @Test
    public void findGroupSuccess() {
        UserGroup group = manager.findGroup("findGroupTest");
        assertNull(group);
        try {
            UserGroup group1 = manager.createGroup("findGroupTest1");
            UserGroup group2 = manager.findGroup("FINDgroupTeSt1");
            assertEquals(group1, group2);
        } catch (WrongNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * don't find group by name
     */
    @Test
    public void findGroupFail() {
        UserGroup group = manager.findGroup("findGroupTest");
        assertNull(group);
    }


    @Test
    public void getUsers() {
        Collection<User> users = manager.getUsers();
        assertNotNull(users);
    }

    @Test
    public void getGroups() {
        Collection<UserGroup> groups = manager.getGroups();
        assertNotNull(groups);
    }

    @Test
    public void getDefaultUser() {
        User defUser = manager.getDefaultUser();
        assertEquals("defaultuser", defUser.getName());
    }

    @Test
    public void getDefaultGroup() {
        UserGroup group = manager.getDefaultGroup();
        assertEquals("defaultgroup", group.getName());

    }

    @Test
    public void consistIn() {
        User user = manager.getDefaultUser();
        UserGroup group = manager.getDefaultGroup();
        assertTrue(manager.consistIn(user, group));

        try {
            User newUser = manager.createUser("default1");
            assertFalse(manager.consistIn(newUser, group));
        } catch (WrongNameException e) {
            e.printStackTrace();
        }
    }
}
