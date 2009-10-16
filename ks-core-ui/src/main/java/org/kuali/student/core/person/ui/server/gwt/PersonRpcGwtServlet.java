/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.core.person.ui.server.gwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.bo.entity.KimPrincipal;
import org.kuali.rice.kim.bo.entity.dto.KimEntityDefaultInfo;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.dto.PersonNameInfo;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.core.person.ui.client.service.PersonRpcService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PersonRpcGwtServlet extends RemoteServiceServlet implements
		PersonRpcService {
	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(PersonRpcGwtServlet.class);
	private static final long serialVersionUID = 3797505861921543183L;

	private PersonService service;

	private IdentityService identityService;
	private String identityServiceAddress;
	  
	@Override
	public List<Result> searchForResults(String searchTypeKey,
			List<QueryParamValue> queryParamValues) {
		// TODO Auto-generated method stub
		// try {
		List<Result> results = new ArrayList<Result>();
		
		if (null == identityService) {
			identityService = (IdentityService) GlobalResourceLoader.getService(new QName("KIM","kimIdentityServiceSOAPUnsecure"));
		}
		
		if (identityService != null) {
			try{
				@SuppressWarnings("unchecked")
				List<KimEntityDefaultInfo> entities = (List<KimEntityDefaultInfo>) identityService
						.lookupEntityDefaultInfo(new HashMap<String, String>(),
								true);
				for (KimEntityDefaultInfo entity : entities) {
					if (entity.getPrincipals() != null) {
						for (KimPrincipal principal : entity.getPrincipals()) {
							Result result = new Result();
							ResultCell cell = new ResultCell();
							cell.setKey("Person Id");
							cell.setValue(principal.getPrincipalId());
							result.getResultCells().add(cell);
							cell = new ResultCell();
							cell.setKey("Person Name");
							cell.setValue(principal.getPrincipalName());
							result.getResultCells().add(cell);
							results.add(result);
						}
					}
				}
				return results;
			}catch(Exception e){
				LOG.error("Error getting identityService", e);
			}
		}
		
		String[] kimPrincipalIds= new String[]{
				"1",
				"admin",
				"admin1",
				"admin2",
				"dev1",
				"dev2",
				"director",
				"doug",
				"earl",
				"edna",
				"employee",
				"eric",
				"erin",
				"fran",
				"frank",
				"fred",
				"idm1",
				"idm2",
				"idm3",
				"kuluser",
				"newaccountuser",
				"notsys",
				"notsysadm",
				"quickstart",
				"supervisor",
				"test1",
				"test2",
				"testadmin1",
				"testadmin2",
				"testuser1",
				"testuser2",
				"testuser3",
				"testuser4",
				"testuser5",
				"testuser6",
				"user1",
				"user2",
				"user3",
				"user4"};
		for(int i = 0;i<kimPrincipalIds.length;i++){
			
			Result result = new Result();
			ResultCell cell = new ResultCell();
			cell.setKey("Person Id");
			cell.setValue(kimPrincipalIds[i]);
			result.getResultCells().add(cell);
			cell = new ResultCell();
			cell.setKey("Person Name");
			cell.setValue(kimPrincipalIds[i]);
			result.getResultCells().add(cell);
			results.add(result);
		}

		return results;
	}

	public PersonService getService() {
		return service;
	}

	public void setService(PersonService service) {
		this.service = service;
	}

	@Override
	public PersonInfo fetchPerson(String personId) {
		PersonInfo person = new PersonInfo();
		person.setId(personId);
		PersonNameInfo nameInfo = new PersonNameInfo();
		nameInfo.setGivenName(personId);
		person.getPersonNameInfoList().add(nameInfo);
		return person;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public String getIdentityServiceAddress() {
		return identityServiceAddress;
	}

	public void setIdentityServiceAddress(String identityServiceAddress) {
		this.identityServiceAddress = identityServiceAddress;
	}

}
