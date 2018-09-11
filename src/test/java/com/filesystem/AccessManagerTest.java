package com.filesystem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccessManagerTest {

    AccessManager manager;

    @Before
    public void setUp() throws Exception {
        manager = AccessManager.getInstance();
    }

    @Test
    public void createUser() {
        String name = "default";
        User user1 = new User(name);
        User user2 = new User(name);
        assertEquals(user1, user2);
    }

    @Test
    public void createUser1() {
    }

    @Test
    public void createGroup() {
    }

    @Test
    public void createGroup1() {
    }
}