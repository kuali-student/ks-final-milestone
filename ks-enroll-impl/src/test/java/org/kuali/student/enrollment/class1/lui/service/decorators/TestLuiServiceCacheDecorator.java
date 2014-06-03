package org.kuali.student.enrollment.class1.lui.service.decorators;

import net.sf.ehcache.CacheManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiSetDao;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiTestDataLoader;
import org.kuali.student.enrollment.class1.lui.service.impl.TestLuiServiceImpl;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiSetInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
public class TestLuiServiceCacheDecorator extends TestLuiServiceImpl {

    @Resource(name = "ksCacheManager")
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.getCacheManager().getCache("luiCache").removeAll();
        this.getCacheManager().getCache("luiluiRelationCache").removeAll();
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
        assertTrue(objs.isEmpty());
    }

    @Test
    public void testGetLui() throws Exception {
        super.testGetLui();
    }

    @Test
    public void testGetLuiIdsByRelation() throws Exception {
        // Skip this test for the cache decorator.
    }

    @Test
    public void testGetLuisByRelation() throws Exception {
        super.testGetLuisByRelation();
    }

    @Test
    public void testGenericLookup() throws Exception {
        // Skip this test for the cache decorator.
    }

    @Test
    public void testCreateLui() throws Exception {
        super.testCreateLui();
    }

    @Test
    public void  testGetLuisByAtpAndClu() throws Exception{
        super.testGetLuisByAtpAndClu();
    }

    @Test
    public void testUpdateLui() throws Exception {
        super.testUpdateLui();
    }

    @Test
    public void testUpdateLuiLuiRelation() throws Exception{
        super.testUpdateLuiLuiRelation();
    }

    @Test
    public void testGetLuiLuiRelation() throws Exception {
        super.testGetLuiLuiRelation();
    }

    @Test
    public void testGetLuiLuiRelationsByLui() throws Exception {
        // Skip this test for the cache decorator.
    }

    @Test
    public void testGetLuiLuiRelationsByIds() throws Exception {
        // Skip this test for the cache decorator.
    }

    @Test
    public void testCreateLuiLuiRelation() throws Exception{
        super.testGetLuiLuiRelation();
    }

    @Test
    public void testDeleteLui()
            throws Exception{
        super.testDeleteLui();
    }

    @Test
    public void testGetLuisByIds() throws Exception {
        // Skip this test for the cache decorator.
    }

    @Test
    public void testGetLuiIdsByType() throws Exception {
        // Skip this test for the cache decorator.
    }


    @Test
    public void testGetLuiIdsByAtpAndType() throws Exception{
        // Skip this test for the cache decorator.
    }

    @Test
    public void testGetLuisByAtpAndType() throws Exception{
        // Skip this test for the cache decorator.
    }

    @Test
    public void testGetRelatedLuiIdsByLui() throws Exception{
        // Skip this test for the cache decorator.
    }
    @Test
    public void testGetRelatedLuisByLui() throws Exception{
        // Skip this test for the cache decorator.
    }

    @Test
    public void testCreateLuiSet() throws Exception {
        // Skip this test for the cache decorator.
    }

    @Test
    public void testUpdateLuiSet() throws Exception {
        // Skip this test for the cache decorator.
    }

    @Test
    public void testDeleteLuiSet() throws Exception {
        // Skip this test for the cache decorator.
    }

    @Test
    public void testGetLuiSetsByLui() throws Exception{
        // Skip this test for the cache decorator.
    }

    @Test
    public void testGetLuiSetIdsByType() throws Exception{
        // Skip this test for the cache decorator.
    }

    @Test
    public void testGetLuiIdsFromLuiSet() throws Exception{
        // Skip this test for the cache decorator.
    }

    @Test
    public void testGetLuiSetsByIds() throws Exception{
        // Skip this test for the cache decorator.
    }

    @Test
    public void testAttributeQuery() {
        // Skip this test for the cache decorator.
    }

    public CacheManager getCacheManager() {
        if (cacheManager == null) {
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

}
