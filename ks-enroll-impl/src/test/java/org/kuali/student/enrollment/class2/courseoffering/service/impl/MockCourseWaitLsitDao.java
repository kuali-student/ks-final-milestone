package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.student.enrollment.class2.coursewaitlist.dao.CourseWaitListDaoApi;
import org.kuali.student.enrollment.class2.coursewaitlist.model.CourseWaitListEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;

/**
 * This class provides a mock implementation of CourseWaitListDao
 */
public class MockCourseWaitLsitDao implements CourseWaitListDaoApi{

    public List<CourseWaitListEntity> getCourseWaitListsByActivityOfferingIds(List<String> activityOfferingIds) {
        return new ArrayList<CourseWaitListEntity>();
    }

    @Override
    public CourseWaitListEntity find(String primaryKey) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseWaitListEntity> findByIds(List<String> primaryKeys) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseWaitListEntity> findByIds(String primaryKeyMemberName, List<String> primaryKeys) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<CourseWaitListEntity> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void persist(CourseWaitListEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(CourseWaitListEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CourseWaitListEntity merge(CourseWaitListEntity entity) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(CourseWaitListEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EntityManager getEm() throws UnsupportedOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setEm(EntityManager em) throws UnsupportedOperationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
