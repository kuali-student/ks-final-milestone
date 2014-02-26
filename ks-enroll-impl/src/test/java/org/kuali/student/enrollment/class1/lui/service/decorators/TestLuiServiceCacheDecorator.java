package org.kuali.student.enrollment.class1.lui.service.decorators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiSetDao;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiTestDataLoader;
import org.kuali.student.enrollment.class1.lui.service.impl.TestLuiServiceImpl;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: Peggy
 * Date: 2013/12/04
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lui-cache-decorator-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestLuiServiceCacheDecorator {

    @Resource(name = "LuiService")
    private LuiService luiService;

    @Resource(name = "luiDao")
    private LuiDao luiDao;

    @Resource(name = "luiSetDao")
    private LuiSetDao luiSetDao;

    @Resource(name= "criteriaLookupService")
    private CriteriaLookupService criteriaLookupService;


    public LuiLuiRelationDao getLuiLuiRelationDao() {
        return luiLuiRelationDao;
    }

    public void setLuiLuiRelationDao(LuiLuiRelationDao luiLuiRelationDao) {
        this.luiLuiRelationDao = luiLuiRelationDao;
    }

    @Resource(name = "luiLuiRelationDao")
    private LuiLuiRelationDao luiLuiRelationDao;

    public LuiService getLuiService() {
        return  luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public LuiDao getLuiDao() {
        return luiDao;
    }

    public void setLuiDao(LuiDao luiDao) {
        this.luiDao = luiDao;
    }

    public LuiSetDao getLuiSetDao() {
        return luiSetDao;
    }

    public void setLuiSetDao(LuiSetDao luiSetDao) {
        this.luiSetDao = luiSetDao;
    }

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() throws Exception {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        new LuiTestDataLoader(luiDao, luiLuiRelationDao).loadData();
        luiDao.getEm().flush();
    }

    @Test
    public void testLuiServiceSetup() {
        assertNotNull(luiService);
    }

    @Test
    public void testLuiLuiRelationCache() throws Exception {
        List<LuiLuiRelationInfo> objs = this.getLuiService().getLuiLuiRelationsByLui("Lui-1", callContext);
        assertNotNull(objs);
        assertEquals(1, objs.size());

        LuiLuiRelationInfo relationInfo = KSCollectionUtils.getOptionalZeroElement(objs);
        this.getLuiService().deleteLuiLuiRelation(relationInfo.getId(), callContext);

        objs = this.getLuiService().getLuiLuiRelationsByLui("Lui-1", callContext);
        assertNotNull(objs);
        assertEquals(0, objs.size());
    }
}
