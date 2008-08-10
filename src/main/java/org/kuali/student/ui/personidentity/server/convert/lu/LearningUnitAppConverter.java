/**
 * 
 */
package org.kuali.student.ui.personidentity.server.convert.lu;

import org.kuali.student.poc.xsd.learningunit.lu.dto.AtpDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuTypeInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtAtpDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;

/**
 * @author Garey
 *
 */
public class LearningUnitAppConverter {

	
	public static CluCriteria convert(GwtCluCriteria in){
		CluCriteria ret = null;
		
		if(in != null){
			ret = new CluCriteria();
			ret.setLuTypeKey(in.getLuTypeKey());
			ret.setSearchKeyValue(in.getSearchKeyValue());
		}
		
		return ret;
	}
	
	public static LuiCriteria convert(GwtLuiCriteria in){
		LuiCriteria ret = null;
		
		if(in != null){
			ret = new LuiCriteria();
			ret.setDescription(in.getDescription());
			ret.setLuTypeKey(in.getLuTypeKey());
		}
		
		return ret;
	}
	
	public static GwtLuiInfo convert(LuiInfo in){
		GwtLuiInfo ret = null;
		
		if(in != null){
			ret = new GwtLuiInfo();
			ret.setAtpDisplay(convert(in.getAtpDisplay()));
			ret.setAttributes(in.getAttributes());
			ret.setCluDisplay(convert(in.getCluDisplay()));
			ret.setLuiCode(in.getLuiCode());
			ret.setLuiId(in.getLuiId());
			ret.setLuTypeKey(in.getLuTypeKey());
			ret.setMaxSeats(in.getMaxSeats());
		}
		
		return ret;
	}
	public static LuiInfo convert(GwtLuiInfo in){
		LuiInfo ret = null;
		
		if(in != null){
			ret = new LuiInfo();
			ret.setAtpDisplay(convert(in.getAtpDisplay()));
			ret.setAttributes(in.getAttributes());
			ret.setCluDisplay(convert(in.getCluDisplay()));
			ret.setLuiCode(in.getLuiCode());
			ret.setLuiId(in.getLuiId());
			ret.setLuTypeKey(in.getLuTypeKey());
			ret.setMaxSeats(in.getMaxSeats());
		}
		
		return ret;
	}
	
	public static GwtCluInfo convert(CluInfo in){
		GwtCluInfo ret = null;
		
		if(in != null){
			ret = new GwtCluInfo();
			ret.setApprovalStatus(in.getApprovalStatus());
			ret.setApprovalStatusTime(in.getApprovalStatusTime());
			ret.setAttributes(in.getAttributes());
			ret.setCluId(in.getCluId());
			ret.setCluLongName(in.getCluLongName());
			ret.setCluShortName(in.getCluShortName());
			ret.setCreateTime(in.getCreateTime());
			ret.setCreateUserComment(in.getCreateUserComment());
			ret.setCreateUserId(in.getCreateUserId());
			ret.setDescription(in.getDescription());
			ret.setEffectiveEndCycle(in.getEffectiveEndCycle());
			ret.setEffectiveEndDate(in.getEffectiveEndDate());
			ret.setEffectiveStartCycle(in.getEffectiveStartCycle());
			ret.setEffectiveStartDate(in.getEffectiveStartDate());
			ret.setLuTypeId(in.getLuTypeId());
			ret.setUpdateTime(in.getUpdateTime());
			ret.setUpdateUserComment(in.getUpdateUserComment());
			ret.setUpdateUserId(in.getUpdateUserId());
		}		
		return ret;
	}
	
	public static CluInfo convert(GwtCluInfo in){
		CluInfo ret = null;
		
		if(in != null){
			ret = new CluInfo();
			ret.setApprovalStatus(in.getApprovalStatus());
			ret.setApprovalStatusTime(in.getApprovalStatusTime());
			ret.setAttributes(in.getAttributes());
			ret.setCluId(in.getCluId());
			ret.setCluLongName(in.getCluLongName());
			ret.setCluShortName(in.getCluShortName());
			ret.setCreateTime(in.getCreateTime());
			ret.setCreateUserComment(in.getCreateUserComment());
			ret.setCreateUserId(in.getCreateUserId());
			ret.setDescription(in.getDescription());
			ret.setEffectiveEndCycle(in.getEffectiveEndCycle());
			ret.setEffectiveEndDate(in.getEffectiveEndDate());
			ret.setEffectiveStartCycle(in.getEffectiveStartCycle());
			ret.setEffectiveStartDate(in.getEffectiveStartDate());
			ret.setLuTypeId(in.getLuTypeId());
			ret.setUpdateTime(in.getUpdateTime());
			ret.setUpdateUserComment(in.getUpdateUserComment());
			ret.setUpdateUserId(in.getUpdateUserId());
		}		
		return ret;
	}
	
	public static GwtLuiDisplay convert(LuiDisplay in){
		GwtLuiDisplay ret = null;
		
		if(in != null){
			ret = new GwtLuiDisplay();
			ret.setAtpDisplay(convert(in.getAtpDisplay()));
			ret.setCluDisplay(convert(in.getCluDisplay()));
			ret.setLuiCode(in.getLuiCode());
			ret.setLuiId(in.getLuiId());
			ret.setLuTypeKey(in.getLuTypeKey());
		}		
		return ret;
	}
	public static LuiDisplay convert(GwtLuiDisplay in){
		LuiDisplay ret = null;
		
		if(in != null){
			ret = new LuiDisplay();
			ret.setAtpDisplay(convert(in.getAtpDisplay()));
			ret.setCluDisplay(convert(in.getCluDisplay()));
			ret.setLuiCode(in.getLuiCode());
			ret.setLuiId(in.getLuiId());
			ret.setLuTypeKey(in.getLuTypeKey());
		}		
		return ret;
	}
	
	public static GwtCluDisplay convert(CluDisplay in){
		GwtCluDisplay ret = null;
		
		if(in != null){
			ret = new GwtCluDisplay();
			ret.setAtpDisplayEnd(convert(in.getAtpDisplayEnd()));
			ret.setAtpDisplayStart(convert(in.getAtpDisplayStart()));
			ret.setCluId(in.getCluId());
			ret.setCluShortName(in.getCluShortName());
			ret.setLuTypeId(in.getLuTypeId());
		}		
		return ret;			
	}
	public static CluDisplay convert(GwtCluDisplay in){
		CluDisplay ret = null;
		
		if(in != null){
			ret = new CluDisplay();
			ret.setAtpDisplayEnd(convert(in.getAtpDisplayEnd()));
			ret.setAtpDisplayStart(convert(in.getAtpDisplayStart()));
			ret.setCluId(in.getCluId());
			ret.setCluShortName(in.getCluShortName());
			ret.setLuTypeId(in.getLuTypeId());
		}		
		return ret;			
	}
	
	public static GwtLuTypeInfo convert(LuTypeInfo in){
		GwtLuTypeInfo ret = null;
		
		if(in != null){
			ret = new GwtLuTypeInfo();
			ret.setCreateTime(in.getCreateTime());
			ret.setCreateUserComment(in.getCreateUserComment());
			ret.setCreateUserId(in.getCreateUserId());
			ret.setDescription(in.getDescription());
			ret.setLuTypeKey(in.getLuTypeKey());
			ret.setUpdateTime(in.getUpdateTime());
			ret.setUpdateUserComment(in.getUpdateUserComment());
			ret.setUpdateUserId(in.getUpdateUserId());
		}		
		return ret;		
	}
	public static LuTypeInfo convert(GwtLuTypeInfo in){
		LuTypeInfo ret = null;
		
		if(in != null){
			ret = new LuTypeInfo();
			ret.setCreateTime(in.getCreateTime());
			ret.setCreateUserComment(in.getCreateUserComment());
			ret.setCreateUserId(in.getCreateUserId());
			ret.setDescription(in.getDescription());
			ret.setLuTypeKey(in.getLuTypeKey());
			ret.setUpdateTime(in.getUpdateTime());
			ret.setUpdateUserComment(in.getUpdateUserComment());
			ret.setUpdateUserId(in.getUpdateUserId());
		}		
		return ret;		
	}
	
	public static GwtAtpDisplay convert(AtpDisplay in){
		GwtAtpDisplay ret = null;
		
		if(in != null){
			ret = new GwtAtpDisplay();
			ret.setAtpId(in.getAtpId());
			ret.setAtpName(in.getAtpName());
		}		
		return ret;		
	}
	public static AtpDisplay convert(GwtAtpDisplay in){
		AtpDisplay ret = null;
		
		if(in != null){
			ret = new AtpDisplay();
			ret.setAtpId(in.getAtpId());
			ret.setAtpName(in.getAtpName());
		}		
		return ret;		
	}
	
	
}
