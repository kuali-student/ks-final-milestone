package org.kuali.student.enrollment.class1.lui.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceImpl;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

/**
 * Created by swedev on 9/5/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:luiBroadcastAspect-test-context.xml"})
public class LuiBroadcastAspectTest {

    @Resource
    private LuiServiceImpl luiService;

    @Spy
    @Resource
    private LuiBroadcastAspect luiBroadcastAspect;

    @Before
    public void setUp(){

        luiBroadcastAspect.setJmsTemplate(Mockito.mock(JmsTemplate.class));
        luiBroadcastAspect.setJmsDestination("junk-dest");

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testCompareObjects() throws Throwable {

        LuiInfo lui = Mockito.mock(LuiInfo.class);
        Mockito.when(lui.getId()).thenReturn("Lui-1");

        ContextInfo callContext = Mockito.mock(ContextInfo.class);

        LuiEntity luiEntity = Mockito.mock(LuiEntity.class);
        Mockito.when(luiEntity.toDto()).thenReturn(lui);

        LuiDao mockDao =     Mockito.mock(LuiDao.class);
        Mockito.when(mockDao.find(Mockito.anyString())).thenReturn(luiEntity);
        Mockito.when(mockDao.merge(luiEntity)).thenReturn(luiEntity);
        Mockito.when(mockDao.getEm()).thenReturn(Mockito.mock(EntityManager.class));

        luiService.setLuiDao(mockDao);

        luiService.updateLui("Lui-1", lui, callContext);    // should trigger aspect

        ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);


        Mockito.verify(luiBroadcastAspect ).updateLuiAdvise(joinPoint);


    }

    //http://forum.springsource.org/showthread.php?60216-Need-to-unwrap-a-proxy-to-get-the-object-being-proxied

    public static final Object unwrapProxy(Object bean) throws Exception {

 /*
  * If the given object is a proxy, set the return value as the object
  * being proxied, otherwise return the given object.
  */
        if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {

            Advised advised = (Advised) bean;

            bean = advised.getTargetSource().getTarget();
        }

        return bean;
    }


}
