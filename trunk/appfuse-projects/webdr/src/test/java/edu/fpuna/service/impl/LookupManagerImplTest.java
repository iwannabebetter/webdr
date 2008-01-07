package edu.fpuna.service.impl;

import edu.fpuna.dao.LookupDao;
import edu.fpuna.model.Role;
import edu.fpuna.model.LabelValue;
import edu.fpuna.Constants;
import org.jmock.Mock;

import java.util.ArrayList;
import java.util.List;


public class LookupManagerImplTest extends BaseManagerMockTestCase {
    private LookupManagerImpl mgr = new LookupManagerImpl();
    private Mock lookupDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        lookupDao = new Mock(LookupDao.class);
        mgr.setLookupDao((LookupDao) lookupDao.proxy());
    }

    public void testGetAllRoles() {
        log.debug("entered 'testGetAllRoles' method");

        // set expected behavior on dao
        Role role = new Role(Constants.ADMIN_ROLE);
        List<Role> testData = new ArrayList<Role>();
        testData.add(role);
        lookupDao.expects(once()).method("getRoles").withNoArguments().will(returnValue(testData));

        List<LabelValue> roles = mgr.getAllRoles();
        assertTrue(roles.size() > 0);
    }
}
