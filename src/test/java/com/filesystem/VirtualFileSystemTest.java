package com.filesystem; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import static junit.framework.TestCase.assertTrue;

/** 
* VirtualFileSystem Tester. 
* 
* @author elena
* @since <pre>Sep 11, 2018</pre>
* @version 1.0 
*/ 
public class VirtualFileSystemTest {

    VirtualFileSystem system;

    @Before
    public void before() throws Exception {
        system = VirtualFileSystem.getFileSystem();
    } 
    
    @After
    public void after() throws Exception { 
    } 
    
    /** 
    * 
    * Method: getFileSystem() 
    * 
    */ 
    @Test
    public void testGetFileSystem() throws Exception { 
        VirtualFileSystem vSystem = VirtualFileSystem.getFileSystem();
        assertTrue(system.equals(vSystem));
    } 
    
    /** 
    * 
    * Method: getUserGroups() 
    * 
    */ 
    @Test
    public void testGetUserGroups() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * 
    * Method: getRoot() 
    * 
    */ 
    @Test
    public void testGetRoot() throws Exception { 
    //TODO: Test goes here... 
    } 
    
    /** 
    * 
    * Method: setGroups() 
    * 
    */ 
    @Test
    public void testSetGroups() throws Exception { 
    //TODO: Test goes here... 
    } 


} 
