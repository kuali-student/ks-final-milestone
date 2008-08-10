package org.kuali.student.ui.personidentity.server.convert;

import java.util.List;
import java.util.Vector;

import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCitizenshipInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonNameInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonReferenceIdInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonUpdateInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCitizenshipInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCreateInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCriteria;
import org.kuali.student.ui.personidentity.client.model.GwtPersonDisplay;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonNameInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonReferenceIdInfo;

public class PersonServiceConverter {

	
	public static GwtPersonDisplay convert(PersonDisplay in){
		GwtPersonDisplay ret = null;
		if (in != null){
			ret = new GwtPersonDisplay();
			ret.setName(convert(in.getName()));
			ret.setPersonId(in.getPersonId());
		}
		return ret;		
	}
	public static PersonDisplay convert(GwtPersonDisplay in){
		PersonDisplay ret = null;
		if (in != null){
			ret = new PersonDisplay();
			ret.setName(convert(in.getName()));
			ret.setPersonId(in.getPersonId());
		}
		return ret;		
	}
	
	public static PersonUpdateInfo convertPersonUpdateInfo(GwtPersonInfo pci){
		PersonUpdateInfo gRet = null;
		
		if(pci != null){
			List<GwtPersonNameInfo> pciPniList = null;
			List<PersonNameInfo> pniList = null;
			List<PersonReferenceIdInfo> priList = null;
			List<GwtPersonReferenceIdInfo> pciPriList = null;
			
			gRet = new PersonUpdateInfo();
									
			gRet.setAttributes(pci.getAttributes());
			gRet.setBirthDate(pci.getBirthDate());
			gRet.setCitizenship(convert(pci.getCitizenship()));
			
			
			
			gRet.setUpdateTime(pci.getUpdateTime());
			gRet.setUpdateUserComment(pci.getUpdateUserComment());
			gRet.setUpdateUserId(pci.getUpdateUserId());
			gRet.setGender(pci.getGender());
			
			pciPniList = pci.getName(); 			
			if(pciPniList != null){
				pniList = new Vector<PersonNameInfo>();
				for(int i=0; i< pciPniList.size(); i++){
					pniList.add(convert(pciPniList.get(i)));					
				}
			}
			gRet.setName(pniList);
			
			pciPriList = pci.getReferenceId();
			if(pciPriList != null){
				priList = new Vector<PersonReferenceIdInfo>();
				for(int i=0; i< pciPriList.size(); i++){
					priList.add(convert(pciPriList.get(i)));					
				}
			}			
			gRet.setReferenceId(priList);		
		}
		
		return gRet;
	}
	
	
	public static GwtPersonCriteria convert(PersonCriteria pCriteria){
		GwtPersonCriteria pRet = null;
		
		if(pCriteria != null){
			pRet = new GwtPersonCriteria();
			pRet.setFirstName(pCriteria.getFirstName());
			pRet.setLastName(pCriteria.getLastName());
		}
		
		return pRet;
	}
	public static PersonCriteria convert(GwtPersonCriteria pCriteria){
		PersonCriteria pRet = null;
		
		if(pCriteria != null){
			pRet = new PersonCriteria();
			pRet.setFirstName(pCriteria.getFirstName());
			pRet.setLastName(pCriteria.getLastName());
		}
		
		return pRet;
	}
	
	public static GwtPersonInfo convert(PersonInfo pci){
		GwtPersonInfo gRet = null;
		
		if(pci != null){
			List<PersonNameInfo> pciPniList = null;
			List<GwtPersonNameInfo> pniList = null;
			List<GwtPersonReferenceIdInfo> priList = null;
			List<PersonReferenceIdInfo> pciPriList = null;
			
			gRet = new GwtPersonInfo();
			
			gRet.setPersonId(pci.getPersonId());
			
			gRet.setAttributes(pci.getAttributes());
			gRet.setBirthDate(pci.getBirthDate());
			gRet.setCitizenship(convert(pci.getCitizenship()));
			
			gRet.setCreateTime(pci.getCreateTime());
			gRet.setCreateUserComment(pci.getCreateUserComment());
			gRet.setCreateUserId(pci.getCreateUserId());
			
			gRet.setUpdateTime(pci.getUpdateTime());
			gRet.setUpdateUserComment(pci.getUpdateUserComment());
			gRet.setUpdateUserId(pci.getUpdateUserId());
			
			gRet.setGender(pci.getGender());
			
			pciPniList = pci.getName(); 			
			if(pciPniList != null){
				pniList = new Vector<GwtPersonNameInfo>();
				for(int i=0; i< pciPniList.size(); i++){
					pniList.add(convert(pciPniList.get(i)));					
				}
			}
			gRet.setName(pniList);
			
			pciPriList = pci.getReferenceId();
			if(pciPriList != null){
				priList = new Vector<GwtPersonReferenceIdInfo>();
				for(int i=0; i< pciPriList.size(); i++){
					priList.add(convert(pciPriList.get(i)));					
				}
			}			
			gRet.setReferenceId(priList);											
		}
				
		return gRet;				
	}
	
	public static PersonInfo convert(GwtPersonInfo pci){
		PersonInfo gRet = null;
		
		if(pci != null){
			List<GwtPersonNameInfo> pciPniList = null;
			List<PersonNameInfo> pniList = null;
			List<PersonReferenceIdInfo> priList = null;
			List<GwtPersonReferenceIdInfo> pciPriList = null;
			
			gRet = new PersonInfo();
			
			gRet.setPersonId(pci.getPersonId());
			
			gRet.setAttributes(pci.getAttributes());
			gRet.setBirthDate(pci.getBirthDate());
			gRet.setCitizenship(convert(pci.getCitizenship()));
			
			gRet.setCreateTime(pci.getCreateTime());
			gRet.setCreateUserComment(pci.getCreateUserComment());
			gRet.setCreateUserId(pci.getCreateUserId());
			
			gRet.setUpdateTime(pci.getUpdateTime());
			gRet.setUpdateUserComment(pci.getUpdateUserComment());
			gRet.setUpdateUserId(pci.getUpdateUserId());
			
			gRet.setGender(pci.getGender());
			
			pciPniList = pci.getName(); 			
			if(pciPniList != null){
				pniList = new Vector<PersonNameInfo>();
				for(int i=0; i< pciPniList.size(); i++){
					pniList.add(convert(pciPniList.get(i)));					
				}
			}
			gRet.setName(pniList);
			
			pciPriList = pci.getReferenceId();
			if(pciPriList != null){
				priList = new Vector<PersonReferenceIdInfo>();
				for(int i=0; i< pciPriList.size(); i++){
					priList.add(convert(pciPriList.get(i)));					
				}
			}			
			gRet.setReferenceId(priList);											
		}
				
		return gRet;				
	}
	
	public static GwtPersonCreateInfo convert(PersonCreateInfo pci){
		GwtPersonCreateInfo gRet = null;
		
		if(pci != null){
			List<PersonNameInfo> pciPniList = null;
			List<GwtPersonNameInfo> pniList = null;
			List<GwtPersonReferenceIdInfo> priList = null;
			List<PersonReferenceIdInfo> pciPriList = null;
			
			gRet = new GwtPersonCreateInfo();
			
			gRet.setAttributes(pci.getAttributes());
			gRet.setBirthDate(pci.getBirthDate());
			gRet.setCitizenship(convert(pci.getCitizenship()));
			gRet.setCreateTime(pci.getCreateTime());
			gRet.setCreateUserComment(pci.getCreateUserComment());
			gRet.setCreateUserId(pci.getCreateUserId());
			gRet.setGender(pci.getGender());
			
			pciPniList = pci.getName(); 			
			if(pciPniList != null){
				pniList = new Vector<GwtPersonNameInfo>();
				for(int i=0; i< pciPniList.size(); i++){
					pniList.add(convert(pciPniList.get(i)));					
				}
			}
			gRet.setName(pniList);
			
			pciPriList = pci.getReferenceId();
			if(pciPriList != null){
				priList = new Vector<GwtPersonReferenceIdInfo>();
				for(int i=0; i< pciPriList.size(); i++){
					priList.add(convert(pciPriList.get(i)));					
				}
			}			
			gRet.setReferenceId(priList);											
		}
				
		return gRet;				
	}
	
	public static PersonCreateInfo convert(GwtPersonCreateInfo pci){
		PersonCreateInfo gRet = null;
		
		if(pci != null){
			List<GwtPersonNameInfo> pciPniList = null;
			List<PersonNameInfo> pniList = null;
			List<PersonReferenceIdInfo> priList = null;
			List<GwtPersonReferenceIdInfo> pciPriList = null;
			
			gRet = new PersonCreateInfo();
			
			gRet.setAttributes(pci.getAttributes());
			gRet.setBirthDate(pci.getBirthDate());
			gRet.setCitizenship(convert(pci.getCitizenship()));
			gRet.setCreateTime(pci.getCreateTime());
			gRet.setCreateUserComment(pci.getCreateUserComment());
			gRet.setCreateUserId(pci.getCreateUserId());
			gRet.setGender(pci.getGender());
			
			pciPniList = pci.getName(); 			
			if(pciPniList != null){
				pniList = new Vector<PersonNameInfo>();
				for(int i=0; i< pciPniList.size(); i++){
					pniList.add(convert(pciPniList.get(i)));					
				}
			}
			gRet.setName(pniList);
			
			pciPriList = pci.getReferenceId();
			if(pciPriList != null){
				priList = new Vector<PersonReferenceIdInfo>();
				for(int i=0; i< pciPriList.size(); i++){
					priList.add(convert(pciPriList.get(i)));					
				}
			}			
			gRet.setReferenceId(priList);											
		}
				
		return gRet;				
	}
	
	
	public static GwtPersonReferenceIdInfo convert(PersonReferenceIdInfo pri){
		GwtPersonReferenceIdInfo gRet = null;
		
		if(pri != null){
			
			gRet = new GwtPersonReferenceIdInfo();
			gRet.setOrganizationReferenceId(pri.getOrganizationReferenceId());
			gRet.setPersonId(pri.getPersonId());
			gRet.setReferenceId(pri.getReferenceId());
			gRet.setRestrictedAccess(pri.isRestrictedAccess());
			gRet.setUpdateTime(pri.getUpdateTime());
			gRet.setUpdateUserComment(pri.getUpdateUserComment());
			gRet.setUpdateUserId(pri.getUpdateUserId());
		}
		
		return gRet;
	}
	
	public static PersonReferenceIdInfo convert(GwtPersonReferenceIdInfo pri){
		PersonReferenceIdInfo gRet = null;
		
		if(pri != null){
			
			gRet = new PersonReferenceIdInfo();
			
			gRet.setOrganizationReferenceId(pri.getOrganizationReferenceId());
			gRet.setPersonId(pri.getPersonId());
			gRet.setReferenceId(pri.getReferenceId());
			gRet.setRestrictedAccess(pri.isRestrictedAccess());
			gRet.setUpdateTime(pri.getUpdateTime());
			gRet.setUpdateUserComment(pri.getUpdateUserComment());
			gRet.setUpdateUserId(pri.getUpdateUserId());
		}
		
		return gRet;
	}
	
	public static GwtPersonNameInfo convert(PersonNameInfo pni){
		GwtPersonNameInfo gRet = null;
		
		if(pni != null){
			gRet = new GwtPersonNameInfo();
			
			gRet.setEffectiveEndDate(pni.getEffectiveEndDate());
			gRet.setEffectiveStartDate(pni.getEffectiveStartDate());
			gRet.setGivenName(pni.getGivenName());
			gRet.setMiddleName(pni.getMiddleName());
			gRet.setNameType(pni.getNameType());
			gRet.setNonStandardName(pni.getNonStandardName());
			gRet.setPersonId(pni.getPersonId());
			gRet.setPersonTitle(pni.getPersonTitle());
			gRet.setPreferredName(pni.getPreferredName());
			gRet.setSuffix(pni.getSuffix());
			gRet.setSurname(pni.getSurname());
			gRet.setUpdateTime(pni.getUpdateTime());
			gRet.setUpdateUserComment(pni.getUpdateUserComment());
			gRet.setUpdateUserId(pni.getUpdateUserId());						
		}
		return gRet;		
	}
	
	public static PersonNameInfo convert(GwtPersonNameInfo pni){
		PersonNameInfo gRet = null;
		
		if(pni != null){
			gRet = new PersonNameInfo();
			
			gRet.setEffectiveEndDate(pni.getEffectiveEndDate());
			gRet.setEffectiveStartDate(pni.getEffectiveStartDate());
			gRet.setGivenName(pni.getGivenName());
			gRet.setMiddleName(pni.getMiddleName());
			gRet.setNameType(pni.getNameType());
			gRet.setNonStandardName(pni.getNonStandardName());
			gRet.setPersonId(pni.getPersonId());
			gRet.setPersonTitle(pni.getPersonTitle());
			gRet.setPreferredName(pni.getPreferredName());
			gRet.setSuffix(pni.getSuffix());
			gRet.setSurname(pni.getSurname());
			gRet.setUpdateTime(pni.getUpdateTime());
			gRet.setUpdateUserComment(pni.getUpdateUserComment());
			gRet.setUpdateUserId(pni.getUpdateUserId());						
		}
		return gRet;		
	}
	
	
	public static GwtPersonCitizenshipInfo convert(PersonCitizenshipInfo gpci){
		GwtPersonCitizenshipInfo gRet = null;
		
		if(gpci != null){
			gRet = new GwtPersonCitizenshipInfo();
			gRet.setCitizenStatusCode(gpci.getCitizenStatusCode());
			gRet.setCountryOfCitizenshipCode(gpci.getCountryOfCitizenshipCode());
			gRet.setEffectiveEndDate(gpci.getEffectiveEndDate());
			gRet.setEffectiveStartDate(gpci.getEffectiveStartDate());
			gRet.setPersonId(gpci.getPersonId());
			gRet.setUpdateTime(gpci.getUpdateTime());
			gRet.setUpdateUserComment(gpci.getUpdateUserComment());
			gRet.setUpdateUserId(gpci.getUpdateUserId());
		}
		return gRet;
	}
	
	public static PersonCitizenshipInfo convert(GwtPersonCitizenshipInfo gpci){
		PersonCitizenshipInfo gRet = null;
		
		if(gpci != null){
			gRet = new PersonCitizenshipInfo();
			gRet.setCitizenStatusCode(gpci.getCitizenStatusCode());
			gRet.setCountryOfCitizenshipCode(gpci.getCountryOfCitizenshipCode());
			gRet.setEffectiveEndDate(gpci.getEffectiveEndDate());
			gRet.setEffectiveStartDate(gpci.getEffectiveStartDate());
			gRet.setPersonId(gpci.getPersonId());
			gRet.setUpdateTime(gpci.getUpdateTime());
			gRet.setUpdateUserComment(gpci.getUpdateUserComment());
			gRet.setUpdateUserId(gpci.getUpdateUserId());
		}
		return gRet;
	}
	
	
	
}
