package edu.umd.ks.poc.lum.scat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.scat.service.ScatService;

@Daos( { @Dao(value = "edu.umd.ks.poc.lum.scat.dao.impl.ScatDaoImpl") })
@PersistenceFileLocation("classpath:META-INF/scat-persistence.xml")
public class TestScatServiceImpl extends AbstractServiceTest {
	@Client(value = "edu.umd.ks.poc.lum.scat.service.impl.ScatServiceImpl", port = "8181")
	public ScatService client;
	
	@Test
	public void TestService(){
		
		ScatTableInfo scatTableReq1=new ScatTableInfo();
		scatTableReq1.setTableDescription("States");
		ScatTableInfo scatTableResp1 = client.createScatTable(scatTableReq1);
		
		ScatTableInfo scatTableReq2=new ScatTableInfo();
		scatTableReq1.setTableDescription("Months");
		ScatTableInfo scatTableResp2 = client.createScatTable(scatTableReq2);
		

		List<ScatInfo> scats = new ArrayList<ScatInfo>();

		ScatInfo scat = new ScatInfo();
		scat.setCode("MD");
		scat.setShortDesc("Maryland");
		scats.add(scat);
		
		scat = new ScatInfo();
		scat.setCode("DE");
		scat.setShortDesc("Deleware");
		scats.add(scat);
		
		client.addScatCodesToScatTable(scatTableResp1.getTableId(), scats);
		
		scats = new ArrayList<ScatInfo>();

		scat = new ScatInfo();
		scat.setCode("JAN");
		scat.setShortDesc("January");
		scats.add(scat);
		
		scat = new ScatInfo();
		scat.setCode("AUG");
		scat.setShortDesc("August");
		scats.add(scat);
		
		client.addScatCodesToScatTable(scatTableResp1.getTableId(), scats);
		
		
	}
}
