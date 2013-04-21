/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 10/26/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.student.enrollment.class2.courseoffering.dao.ActivityOfferingClusterDaoApi;
import org.kuali.student.enrollment.class2.courseoffering.model.ActivityOfferingClusterEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class MockActivityOfferingClusterDao implements ActivityOfferingClusterDaoApi {
    public MockActivityOfferingClusterDao() {

    }

    protected Class<?> getEntityClass() {
        return ActivityOfferingClusterEntity.class;
    }

    @Override
    public List<ActivityOfferingClusterEntity> getByFormatOffering(String formatOfferingId) {
        return new ArrayList<ActivityOfferingClusterEntity>();
    }

    @Override
    public List<String> getActivityOfferingIdsByClusterId(String activityOfferingClusterId) {
        return new ArrayList<String>();
    }

    @Override
    public ActivityOfferingClusterEntity find(String primaryKey) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingClusterEntity> findByIds(List<String> primaryKeys) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingClusterEntity> findByIds(String primaryKeyMemberName, List<String> primaryKeys) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ActivityOfferingClusterEntity> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void persist(ActivityOfferingClusterEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(ActivityOfferingClusterEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ActivityOfferingClusterEntity merge(ActivityOfferingClusterEntity entity) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(ActivityOfferingClusterEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EntityManager getEm() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Mocks do not have entity managers");
    }

    @Override
    public void setEm(EntityManager em) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Mocks do not have entity managers");
    }

    @Override
    public List<ActivityOfferingClusterEntity> getByCourseOffering(String courseOfferingId){
        return null;
    }
}
