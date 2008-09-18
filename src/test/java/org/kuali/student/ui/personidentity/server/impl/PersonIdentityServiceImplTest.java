/**
 * 
 */
package org.kuali.student.ui.personidentity.server.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria;

/**
 * @author Garey
 *
 */
public class PersonIdentityServiceImplTest {

    private static PersonService pService = null;
    
	/**
	 * Test method for {@link org.kuali.student.ui.personidentity.server.impl.PersonIdentityServiceImpl#getTime()}.
	 */
	@Test
	public void testGetTime() {
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		String dt = pi.getTime();
//		try{
//			DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.FULL).parse(dt);																		
//		}catch(Exception e){
//			fail(e.getMessage());
//		}
	}

	   @Test
	    public void testSearchById() {
	      /* 
	      PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
	     
	      try{
	          PersonCriteria pc = new PersonCriteria();
	          pc.setFirstName("%");
	          pi.searchForPersonIds(pc);
	      }catch(Exception e){
	          fail(e.getMessage());
	      }*/
	    }
	
//
//
//	
//	/**
//	 * Test method for {@link org.kuali.student.ui.personidentity.server.impl.PersonIdentityServiceImpl#getPersonService()}.
//	 */
//	@Test
//	public void testGetPersonService() {
//		assert(true);
//	}
//	
	
/*	protected static PersonService getPersonService(){
	    if(pService == null){
	        pService = (PersonService) BeanFactory.getInstance().getBean("personClient");
	    }
	    return pService;
	}
	
	@Test
	public void testCreatePersonTypeInfo(){
		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
		pi.setPersonService(getPersonService());
		PersonTypeInfo pio = new PersonTypeInfo();
		
		// gets an immutable list that you can add to
		List<PersonAttributeSetTypeInfo> setTypeInfoList = pio.getAttributeSets();
		
		// the set object we add the dynamic attributes to
		PersonAttributeSetTypeInfo setTypeInfo = new PersonAttributeSetTypeInfo();
		
		setTypeInfo.setName("demographic"); // set the set name
		List<PersonAttributeTypeInfo> aTypes = setTypeInfo.getAttributeTypes();
		
		PersonAttributeTypeInfo p1 = new PersonAttributeTypeInfo();		
		p1.setName("eye_color");
		p1.setLabel("Eye Color");
		p1.setType("String");
		
		PersonAttributeTypeInfo p2 = new PersonAttributeTypeInfo();		
		p2.setName("race");
		p2.setLabel("Race");
		p2.setType("String");
		
		aTypes.add(p1);
		aTypes.add(p2);		
		pio.setName("Administrator");
		
		setTypeInfoList.add(setTypeInfo);
		
		try
		{
			pi.createPersonTypeInfo(pio);						
							
		}catch(AlreadyExistsException ex){
			// this is fine.
		}catch(Exception ex){
		    
		    ex.printStackTrace();
			// the exception aren't mapped properly 
			if(ex.getMessage().indexOf("ConstraintViolationException") == 0)
				fail(ex.getMessage());
		}
		
		pio.setName("Student");
		
		
		try
		{
			pi.createPersonTypeInfo(pio);						
							
		}catch(AlreadyExistsException ex){
			// this is fine.
		}catch(Exception ex){
			// the exception aren't mapped properly 
			if(ex.getMessage().indexOf("ConstraintViolationException") == 0)
				fail(ex.getMessage());
		}
		
		pio.setName("Faculty");
		
		try
		{
			pi.createPersonTypeInfo(pio);						
							
		}catch(AlreadyExistsException ex){
			// this is fine.
		}catch(Exception ex){
			// the exception aren't mapped properly 
			if(ex.getMessage().indexOf("ConstraintViolationException") == 0)
				fail(ex.getMessage());
		}
	}*/
//	
//	@Test
//	public void testFindPersonAttributeSetTypes(){
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		/* Not Implemented in version 0.0.2
//		try
//		{
//			List <PersonAttributeSetTypeDisplay> pList = pi.findPersonAttributeSetTypes();			
//			PersonAttributeSetTypeDisplay p = pList.get(0);
//			Long.valueOf(p.getId().toString());				
//		}catch(Exception ex){
//			fail(ex.getMessage());
//		}
//		*/
//	}
//	
//	
//	public void testCreateAttributeTypeDefinition(){
//	
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		
//		PersonAttributeTypeInfo p1 = new PersonAttributeTypeInfo();		
//		p1.setName("eye_color");
//		p1.setLabel("Eye Color");
//		
//		PersonAttributeTypeInfo p2 = new PersonAttributeTypeInfo();		
//		p1.setName("race");
//		p1.setLabel("Race");
//		
//		
//
//		try
//		{
//			pi.createAttributeDefinition(p1);		
//			pi.createAttributeDefinition(p2);
//							
//		}catch(AlreadyExistsException ex){
//			// this is fine.
//		}catch(Exception ex){
//			// the exception aren't mapped properly 
//			if(ex.getMessage().indexOf("ConstraintViolationException") == 0)
//				fail(ex.getMessage());
//		}
//	}	
//	
//	@Test
//	public void testCreatePersonAttributeSetType(){
//	
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		PersonAttributeSetTypeInfo pasto = new PersonAttributeSetTypeInfo();
//		
//		pasto.setName("firstName");		
//
//		try
//		{
//			pi.createPersonAttributeSetType(pasto);				
//							
//		}catch(AlreadyExistsException ex){
//			// this is fine.
//		}catch(Exception ex){
//			// the exception aren't mapped properly 
//			if(ex.getMessage().indexOf("ConstraintViolationException") == 0)
//				fail(ex.getMessage());
//		}
//	}	
//	
//
//	public void testCreatePerson(){
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		PersonCreateInfo pci = new PersonCreateInfo();
//		
//		PersonNameInfo pni = new PersonNameInfo();
//		
//		pni.setGivenName("Sam");		
//		pni.setMiddleName("Patrick");
//		pni.setSurname("Smith");
//		pni.setPersonTitle("Programmer");
//		pni.setPreferredName(true);
//		
//		List <PersonNameInfo> pList = new Vector<PersonNameInfo>();
//		pList.add(pni);
//		
//		pci.setBirthDate(new Date());
//		pci.setGender('M');
//		pci.setName(pList);
//		
//		
//		try
//		{
//			List<PersonTypeDisplay> pTypeList = pi.findPersonTypes();
//			if(pTypeList != null && pTypeList.size() > 0){
//				Vector<String> pLongTypeList = new Vector<String>();
//				pLongTypeList.add(pTypeList.get(0).getId());
//				pi.createPerson(PersonServiceConverter.convert(pci), pLongTypeList);
//			}
//							
//		}catch(AlreadyExistsException ex){
//			// this is fine.
//		}catch(Exception ex){
//			// the exception aren't mapped properly 
//			if(ex.getMessage().indexOf("ConstraintViolationException") == 0)
//				fail(ex.getMessage());
//		}
//	}
//	
//	
//	@Test
//	public void testSearchForPeopleByPersonType(){
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		PersonCriteria pC = new PersonCriteria();
//		
//		List<PersonInfo> lRet = new Vector<PersonInfo>();
//
//		try{
//			List <PersonTypeDisplay> pList = pi.findPersonTypes();		
//			
//			if(pList != null && pList.size() > 0){
//				for(int i =0; i< pList.size(); i++){
//					String pKey = pList.get(i).getId();
//					lRet.addAll(pi.searchForPeopleByPersonType(pKey, null));				
//				}
//			}
//		}catch(Exception ex){
//			fail(ex.getMessage());
//		}
//	}
//	
//	@Test
//	public void testFindPersonIdsForPersonType(){
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		PersonCriteria pC = new PersonCriteria();
//		
//		List<String> lRet = new Vector<String>();
//
//		try{
//			List <PersonTypeDisplay> pList = pi.findPersonTypes();		
//			
//			if(pList != null && pList.size() > 0){
//				for(int i =0; i< pList.size(); i++){
//					String pKey = pList.get(i).getId();
//					lRet.addAll(pi.findPersonIdsForPersonType(pKey, null));				
//				}
//			}
//		}catch(Exception ex){
//			fail(ex.getMessage());
//		}
//	}
//	
//	/**
//	 * Test method for {@link org.kuali.student.ui.personidentity.server.impl.PersonIdentityServiceImpl#findPersonTypes()}.
//	 */
//	@Test
//	public void testFindPersonTypes() {
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		try
//		{
//			List <PersonTypeDisplay> pList = pi.findPersonTypes();			
//			PersonTypeDisplay p = pList.get(0);
//			p.getId().toString();				
//		}catch(Exception ex){
//			fail(ex.getMessage());
//		}
//		
//	}
//	
//	@Test
//	public void testSerachForPeople(){
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		try
//		{
//			
//			
//			List <GwtPersonInfo> pList = pi.searchForPeople(new GwtPersonCriteria());	
//							
//		}catch(Exception ex){
//			fail(ex.getMessage());
//		}
//	}
//	
//	@Test
//	public void testFindCreatablePersonTypes() {
//		PersonIdentityServiceImpl pi = new PersonIdentityServiceImpl();
//		try
//		{
//			List <PersonTypeDisplay> pList = pi.findCreatablePersonTypes();			
//			PersonTypeDisplay p = pList.get(0);
//			p.getId().toString();				
//		}catch(Exception ex){
//			fail(ex.getMessage());
//		}
//		
//	}
}
