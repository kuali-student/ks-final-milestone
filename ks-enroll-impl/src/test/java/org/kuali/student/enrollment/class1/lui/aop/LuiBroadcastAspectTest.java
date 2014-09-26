package org.kuali.student.enrollment.class1.lui.aop;

import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceImpl;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This class tests the   LuiBroadcastAspect.java class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:luiBroadcastAspect-test-context.xml"})
public class LuiBroadcastAspectTest {

    @Resource
    private LuiServiceImpl luiService;

    @Resource
    private LuiBroadcastAspect luiBroadcastAspect;

    @Before
    public void setUp(){
        luiBroadcastAspect.setJmsTemplate(mock(JmsTemplate.class));
        luiBroadcastAspect.setJmsDestination("junk-dest");
        MockitoAnnotations.initMocks(this);

    }

    /**
     * This method tests to make sure the aspect gets fired when a lui is updated.
     *
     * @throws Throwable
     */
    @Test
    public void testAspectFiring() throws Throwable {

        LuiInfo lui = mock(LuiInfo.class);
        when(lui.getId()).thenReturn("Lui-1");
        ContextInfo callContext = mock(ContextInfo.class);

        luiService.updateLui("Lui-1", lui, callContext);    // should trigger aspect

        verify(luiBroadcastAspect, times(1)).updateLuiAdvise(any(JoinPoint.class), any(LuiInfo.class));


    }


    /**  I'm going to need this code for more testing
     LuiEntity luiEntity = Mockito.mock(LuiEntity.class);
     Mockito.when(luiEntity.toDto()).thenReturn(lui);

     LuiDao mockDao =     Mockito.mock(LuiDao.class);
     Mockito.when(mockDao.find(Mockito.anyString())).thenReturn(luiEntity);
     Mockito.when(mockDao.merge(luiEntity)).thenReturn(luiEntity);
     Mockito.when(mockDao.getEm()).thenReturn(Mockito.mock(EntityManager.class));

     luiService.setLuiDao(mockDao);
     */


}
