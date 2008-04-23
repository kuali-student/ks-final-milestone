package org.kuali.student.poc.learningunit.lu;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.kuali.student.poc.learningunit.lu.entity.Atp;
import org.kuali.student.poc.learningunit.lu.entity.LuAttributeType;
import org.kuali.student.poc.learningunit.lu.entity.LuType;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("HibernatePU");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		LuType luType = new LuType();
		
		LuAttributeType luAtTyp1=new LuAttributeType();
		luAtTyp1.setName("AtType1");
		luAtTyp1.getLuTypes().add(luType);
		
		luType.setCreateTime(new Date());
		luType.setCreateUserComment("Created");
		luType.setCreateUserId("USER-12345");
		luType.setUpdateTime(new Date());
		luType.setUpdateUserComment("Created");
		luType.setUpdateUserId("USER-12345");
		luType.getLuAttributeTypes().add(luAtTyp1);
		
		em.persist(luType);
		Atp atpStart = new Atp();
		atpStart.setAtpName("Atp Start!");
		em.persist(atpStart);
		Atp atpEnd = new Atp();
		atpEnd.setAtpName("Atp End!");
		em.persist(atpEnd);
		
		
		tx.commit();
	}

}
