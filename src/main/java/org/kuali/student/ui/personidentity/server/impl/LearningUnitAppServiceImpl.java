package org.kuali.student.ui.personidentity.server.impl;

import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;
import org.kuali.student.poc.wsdl.learningunit.lu.LuService;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuTypeInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.service.LearningUnitAppService;
import org.kuali.student.ui.personidentity.server.convert.GwtException;
import org.kuali.student.ui.personidentity.server.convert.lu.LearningUnitAppConverter;
import org.springframework.core.io.ClassPathResource;

import com.google.gwt.user.client.rpc.SerializableException;

/**
 * @author Garey
 * 
 */
public class LearningUnitAppServiceImpl implements LearningUnitAppService {

	// This is the equiv of semester id. in this case spring 2008
	// The service currently does not have a way to look this up so I
	// had to have the programmer give me this code.
	public final static String ATP_ID = "11223344-1122-1122-1111-000000000000";
	
	private LuService luService;

	public LuService getLuService(){
		return luService;
	}


	/* (non-Javadoc)
	 * @see org.kuali.student.ui.personidentity.client.service.LearningUnitAppService#searchForClus(org.kuali.student.ui.personidentity.client.model.lu.GwtCluCriteria)
	 */	
	public List<GwtCluDisplay> searchForClus(GwtCluCriteria cluCriteria)
			throws SerializableException {
		List<GwtCluDisplay> lRet = null;
		
		try{
			List<CluDisplay> tList = null;
			// TODO: put in for loop and return the correct type after convert
			tList = luService.searchForClus(LearningUnitAppConverter.convert(cluCriteria));
			if(tList != null){
				lRet = new Vector<GwtCluDisplay>();
				for(CluDisplay clu:tList){
					lRet.add(LearningUnitAppConverter.convert(clu));
				}
			}
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		
		return lRet;
	}

	
	public List<GwtCluInfo> searchForCluInfo(GwtCluCriteria cluCriteria)
		throws SerializableException {
		
		List<GwtCluInfo> lRet = null;
		List<GwtCluDisplay> tList = this.searchForClus(cluCriteria);
		if(tList != null){
			lRet = new Vector<GwtCluInfo>();
			for(GwtCluDisplay cluDisp:tList){
					GwtCluInfo info = this.fetchClu(cluDisp.getCluId());
					lRet.add(info);
			}
		}		
		return lRet;		
	}
	
	public List<GwtLuiDisplay> searchForLuis(GwtLuiCriteria luiCriteria)
			throws SerializableException {
		List<GwtLuiDisplay> lRet = null;
		
		try{
			List<LuiDisplay> tList = null;
			tList = luService.searchForLuis(LearningUnitAppConverter.convert(luiCriteria));
			if(tList != null){
				lRet = new Vector<GwtLuiDisplay>();
				for(LuiDisplay clu:tList){
					lRet.add(LearningUnitAppConverter.convert(clu));
				}
			}
		}catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		
		return lRet;
	}
	public List<GwtLuiInfo> searchForLuiInfo(GwtLuiCriteria luiCriteria)
		throws SerializableException {
	
	List<GwtLuiInfo> lRet = null;
	List<GwtLuiDisplay> tList = this.searchForLuis(luiCriteria);
	if(tList != null){
		lRet = new Vector<GwtLuiInfo>();
		for(GwtLuiDisplay luiDisp:tList){
			/*
				GwtCluInfo cluInfo = this.fetchClu(luiDisp.getCluDisplay().getCluId());
				GwtLuiInfo info = new GwtLuiInfo();
				
				
				info.setCluDisplay(luiDisp.getCluDisplay());
				info.setCluInfo(cluInfo);
				info.setLuiId(luiDisp.getLuiId());
				info.setLuiCode(luiDisp.getLuiCode());
				info.setAtpDisplay(luiDisp.getAtpDisplay());
				*/
				GwtLuiInfo info = this.fetchLui(luiDisp.getLuiId());				
				lRet.add(info);
		}
	}		
	return lRet;		
}

	public GwtCluInfo fetchClu(String cluId) throws SerializableException {
		GwtCluInfo cRet = null;

		try {
			CluInfo tmp = luService.fetchClu(cluId);
			cRet =LearningUnitAppConverter.convert( tmp);
		} catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}
		return cRet;

	}

	public GwtLuiInfo fetchLui(String luiId) throws SerializableException {
		GwtLuiInfo lRet = null;

		try {
			LuiInfo lui = luService.fetchLui(luiId);
			lRet = LearningUnitAppConverter.convert(lui);
			GwtCluInfo cInfo = this.fetchClu(lRet.getCluDisplay().getCluId());
			lRet.setCluInfo(cInfo);
		} catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}

		return lRet;
	}

	/**
	 * 
	 * @return A list of LuTypeInfo. That would be "credit_course". You need
	 *         this to search for courses.
	 */
	public List<GwtLuTypeInfo> findLuTypes() {
		List<GwtLuTypeInfo> lRet = null;

		try {
			List<LuTypeInfo> tmp = null;

			tmp = luService.findLuTypes();
			if (tmp != null) {
				lRet = new Vector<GwtLuTypeInfo>();
				for (LuTypeInfo cd : tmp) {
					lRet.add(LearningUnitAppConverter.convert(cd));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return lRet;
	}

	/**
	 * @param luTypeKey
	 *            For Example "credit_course"
	 * @return List of conical learning units. CMSC100
	 */
	public List<GwtCluInfo> findClusForLuType(String luTypeKey)
			throws SerializableException {
		List<GwtCluInfo> lRet = null;

		try {
			List<CluDisplay> tmp = null;
			tmp = luService.findClusForLuType(luTypeKey);
			if (tmp != null) {
				lRet = new Vector<GwtCluInfo>();
				for (CluDisplay cd : tmp) {
					
					
					
					lRet.add(fetchClu(cd.getCluId()));
				}
			}
		} catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}

		return lRet;
	}

	/**
	 * @param cluId
	 * @param atpId
	 * @return
	 */
	public List<GwtLuiDisplay> findLuisForClu(String cluId, String atpId) throws SerializableException {
		List<GwtLuiDisplay> lRet = null;

		if (atpId == null) {
			atpId = ATP_ID;
		}

		try {
			List<LuiDisplay> tmp = null;
			tmp = luService.findLuisForClu(cluId, atpId);
			if (tmp != null) {
				lRet = new Vector<GwtLuiDisplay>();
				for (LuiDisplay cd : tmp) {
					lRet.add(LearningUnitAppConverter.convert(cd));
				}
			}
		} catch (Exception ex) {
			throw GwtException.toGWT(ex);
		}

		return lRet;
	}


	/**
	 * @param luService the luService to set
	 */
	public void setLuService(LuService luService) {
		this.luService = luService;
	}

}
