package org.appfuse.dao;

import org.appfuse.model.User;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * This class tests the generic DAO and BaseDAO implementation.
 */
public class GenericDAOTest extends BaseDAOTestCase {
    private DAO dao;
    private Class clazz;
    
    protected void setUp() throws Exception {
        super.setUp();
        dao = (DAO) ctx.getBean("dao");
    }
    
    protected void tearDown() throws Exception {
        dao = null;
    }
    
    /**
     * Convenience method so this class can be subclassed to test CRUDing
     * other entities.
     * @param clazz
     */
    protected void setClass(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * Simple test to verify BaseDAO works.
     */
    public void testCRUD() {
        setClass(User.class);
        User user = new User();
        // set required fields
        user.setUsername("foo");
        user.setPassword("bar");
        user.setFirstName("first");
        user.setLastName("last");
        user.getAddress().setCity("Denver");
        user.getAddress().setPostalCode("80465");
        user.setEmail("foo@bar.com");
        
        // create
        dao.saveObject(user);
        assertNotNull(user.getVersion());
        
        // retrieve
        user = (User) dao.getObject(User.class, user.getUsername());
        assertNotNull(user);
        assertEquals(user.getLastName(), "last");
        
        // update
        user.getAddress().setCountry("USA");
        dao.saveObject(user);
        assertEquals(user.getAddress().getCountry(), "USA");
        
        // delete
        dao.removeObject(User.class, user.getUsername());
        try {
            dao.getObject(User.class, "foo");
            fail("User 'foo' found in database");
        } catch (ObjectRetrievalFailureException e) {
            assertNotNull(e.getMessage());
        }
    }
}
