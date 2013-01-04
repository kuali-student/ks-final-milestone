/*
 * Copyright 2012 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import javax.persistence.EntityManager;

import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lu.dao.impl.LuDaoImpl;
import org.kuali.student.r2.lum.lu.entity.CluResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * In the JPA case we need to setup some course result types.
 * 
 * @author Kuali Student Team 
 *
 */
public class CourseOfferingServiceJPADataLoader extends
        CourseOfferingServiceTestDataLoader {
    private static final Logger log = LoggerFactory
            .getLogger(CourseOfferingServiceJPADataLoader.class);

    protected LuDaoImpl luDaoImpl;
    
    /**
     * 
     */
    public CourseOfferingServiceJPADataLoader() {
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader#initializeData()
     */
    @Override
    protected void initializeData() throws Exception {
        
        createCluResultTypes();
        
        super.initializeData();
    }
    
    private void createCluResultTypes() {
        if(luDaoImpl != null){
            EntityManager em = luDaoImpl.getEm();
            if(em != null){
                if(em.find(CluResultType.class,CourseAssemblerConstants.COURSE_RESULT_TYPE_CREDITS) == null){
                    CluResultType cluResultType = new CluResultType();
                    cluResultType.setId(CourseAssemblerConstants.COURSE_RESULT_TYPE_CREDITS);
                    em.persist(cluResultType);
                }
                if(em.find(CluResultType.class,CourseAssemblerConstants.COURSE_RESULT_TYPE_GRADE) == null){
                    CluResultType cluResultType = new CluResultType();
                    cluResultType.setId(CourseAssemblerConstants.COURSE_RESULT_TYPE_GRADE);
                    em.persist(cluResultType);
                }
            }
        }
    }
    

    public void setLuDaoImpl(LuDaoImpl luDaoImpl) {
        this.luDaoImpl = luDaoImpl;
    }
}
