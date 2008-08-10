package org.kuali.student.ui.personidentity.server.impl;

import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuTypeInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;

public class LearningUnitAppServiceImplTest {

	@Test
	public void testFindLuTypes(){		
//		LearningUnitAppServiceImpl lu = new LearningUnitAppServiceImpl();		
//		try{
//			List<GwtLuTypeInfo> lRet = lu.findLuTypes();
//			
//			if(lRet != null){
//				for(GwtLuTypeInfo lti: lRet){
//					System.out.println(lti.getDescription() + " " + lti.getLuTypeKey() );
//				}
//			}
//		}catch(Exception ex){
//			fail(ex.getMessage());
//		}				
	}
	/*
	@Test
	public void findClusForLuType(){
		LearningUnitAppServiceImpl lu = new LearningUnitAppServiceImpl();
		
		
		List<LuTypeInfo> lTypes = lu.findLuTypes();
		
		if(lTypes != null){
			for(LuTypeInfo lti: lTypes){
				List<GwtCluDisplay> lClu = lu.findClusForLuType(lti.getLuTypeKey());
				if(lClu != null){
					for(GwtCluDisplay cld: lClu){						
						System.out.println(cld.getCluShortName() + " " + cld.getCluId());
					}
				}
				
			}
		}			
	}
	
	
	
	@Test
	public void fetchClu(){
		LearningUnitAppServiceImpl lu = new LearningUnitAppServiceImpl();
		
		
		List<GwtLuTypeInfo> lTypes = lu.findLuTypes();
		
		if(lTypes != null){
			for(LuTypeInfo lti: lTypes){
				List<GwtCluDisplay> lClu = lu.findClusForLuType(lti.getLuTypeKey());
				if(lClu != null){
					for(GwtCluDisplay cld: lClu){		
						try{
							CluInfo cInfo = lu.fetchClu(cld.getCluId());						
							System.out.println(cld.getCluShortName());
							System.out.println("   " + cInfo.getApprovalStatus());
							System.out.println("   " + cInfo.getApprovalStatusTime());
							System.out.println("   " + cInfo.getCluLongName());
							System.out.println("   " + cInfo.getCluShortName());							
							System.out.println("   " + cInfo.getDescription());
							
							Map<String, String> sMap = cInfo.getAttributes();
							
							Iterator<String> i = sMap.keySet().iterator();
							while(i.hasNext()){
								String key = i.next();
								System.out.println("   Attributes:   " + key + " : " + sMap.get(key));
							}
							System.out.println("  ");
							System.out.println("  ");
							
						}catch(Exception ex){
							fail(ex.getMessage());
						}
					}
				}
				
			}
		}			
	}
	
	
	@Test
	public void findLuisForClu(){
		LearningUnitAppServiceImpl lu = new LearningUnitAppServiceImpl();		
		
		List<GwtLuTypeInfo> lTypes = lu.findLuTypes();
		
		if(lTypes != null){
			for(LuTypeInfo lti: lTypes){
				List<GwtCluDisplay> lClu = lu.findClusForLuType(lti.getLuTypeKey());
				if(lClu != null){
					for(GwtCluDisplay cld: lClu){
						
						List<LuiDisplay> lLd = lu.findLuisForClu(cld.getCluId(), "11223344-1122-1122-1111-000000000000");
						if(lLd != null){
							for(LuiDisplay ldi: lLd){
								
								System.out.println(cld.getCluShortName());
								
								System.out.println("    Start:" + cld.getAtpDisplayStart().getAtpName());
								System.out.println("    End:  " + cld.getAtpDisplayEnd().getAtpName());
								
							}
						}
					}
				}
				
			}
		}			
	}
	*/
	
}
