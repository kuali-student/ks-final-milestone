package org.kuali.student.registration.server.convert;

import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationCreateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationDisplay;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationInfo;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationTypeInfo;
import org.kuali.student.registration.client.model.GwtRelationStateInfo;
import org.kuali.student.ui.personidentity.server.convert.PersonServiceConverter;
import org.kuali.student.ui.personidentity.server.convert.lu.LearningUnitAppConverter;

public class RegistrationConverter {

	public static GwtLuiPersonRelationDisplay convert(LuiPersonRelationDisplay in){
		GwtLuiPersonRelationDisplay ret = null;
		
		if(in != null){
			ret = new GwtLuiPersonRelationDisplay();
			
			ret.setLuiDisplay(LearningUnitAppConverter.convert(in.getLuiDisplay()));
			ret.setLuiPersonRelationId(in.getLuiPersonRelationId());
			ret.setLuiPersonRelationType(convert(in.getLuiPersonRelationType()));
			ret.setPersonDisplay(PersonServiceConverter.convert(in.getPersonDisplay()));
			ret.setRelationState(convert(in.getRelationState()));			
		}
		
		return ret;
	}
	public static LuiPersonRelationDisplay convert(GwtLuiPersonRelationDisplay in){
		LuiPersonRelationDisplay ret = null;
		
		if(in != null){
			ret = new LuiPersonRelationDisplay();
			
			ret.setLuiDisplay(LearningUnitAppConverter.convert(in.getLuiDisplay()));
			ret.setLuiPersonRelationId(in.getLuiPersonRelationId());
			ret.setLuiPersonRelationType(convert(in.getLuiPersonRelationType()));
			ret.setPersonDisplay(PersonServiceConverter.convert(in.getPersonDisplay()));
			ret.setRelationState(convert(in.getRelationState()));			
		}
		
		return ret;
	}
	
	public static GwtLuiPersonRelationInfo convert(LuiPersonRelationInfo in){
		GwtLuiPersonRelationInfo ret = null;
		
		if(in != null){
			ret = new GwtLuiPersonRelationInfo();
			
			ret.setEffectiveEndDate(in.getEffectiveEndDate());
			ret.setEffectiveStartDate(in.getEffectiveStartDate());
			ret.setGwtLuiDisplay(LearningUnitAppConverter.convert( in.getLuiDisplay()));
			ret.setLuiPersonRelationId(in.getLuiPersonRelationId());
			ret.setLuiPersonRelationType(convert(in.getLuiPersonRelationType()));
			ret.setPersonDisplay(PersonServiceConverter.convert(in.getPersonDisplay()));
			ret.setRelationState(convert(in.getRelationState()));
		}				
		return ret;
	}
	
	public static LuiPersonRelationCreateInfo convertToLuiPersonRelationCreateInfo(GwtLuiPersonRelationInfo in){
		LuiPersonRelationCreateInfo ret = null;
		
		if(in != null){
			ret = new LuiPersonRelationCreateInfo();
			ret.setEffectiveEndDate(in.getEffectiveEndDate());
			ret.setEffectiveStartDate(in.getEffectiveStartDate());
		}
		
		return ret;
	}
	
	public static LuiPersonRelationUpdateInfo convertToLuiPersonRelationUpdateInfo(GwtLuiPersonRelationInfo in){
		LuiPersonRelationUpdateInfo ret = null;
		
		if(in != null){
			ret = new LuiPersonRelationUpdateInfo();
			ret.setEffectiveEndDate(in.getEffectiveEndDate());
			ret.setEffectiveStartDate(in.getEffectiveStartDate());
			ret.setLuiPersonRelationType(convert(in.getLuiPersonRelationType()));
			ret.setRelationState(convert(in.getRelationState()));			
		}		
		return ret;
	}
	
	public static RelationStateInfo convert(GwtRelationStateInfo in){
		RelationStateInfo ret = null;
		
		if(in != null){
			ret = new RelationStateInfo();
			ret.setState(in.getState());
		}
		
		return ret;
	}
	public static GwtRelationStateInfo convert(RelationStateInfo in){
		GwtRelationStateInfo ret = null;
		
		if(in != null){
			ret = new GwtRelationStateInfo();
			ret.setState(in.getState());
		}
		
		return ret;
	}
	
	public static LuiPersonRelationTypeInfo convert(GwtLuiPersonRelationTypeInfo in){
		LuiPersonRelationTypeInfo ret = null;
		
		if(in != null){
			ret = new LuiPersonRelationTypeInfo();
			ret.setName(in.getName());
		}
		
		return ret;
	}
	public static GwtLuiPersonRelationTypeInfo convert(LuiPersonRelationTypeInfo in){
		GwtLuiPersonRelationTypeInfo ret = null;
		
		if(in != null){
			ret = new GwtLuiPersonRelationTypeInfo();
			ret.setName(in.getName());
		}
		
		return ret;
	}
	
	
	
}
