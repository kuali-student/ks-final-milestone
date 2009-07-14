package org.kuali.student.core.person.ui.server.gwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

//import org.apache.cxf.databinding.DataBinding;
//import org.apache.cxf.frontend.ClientProxyFactoryBean;
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
		
		aquireIdentityService();
		
		if (identityService != null) {
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
		}

		Result result = new Result();
		ResultCell cell = new ResultCell();
		cell.setKey("Person Id");
		cell.setValue("KIM-1");
		result.getResultCells().add(cell);
		cell = new ResultCell();
		cell.setKey("Person Name");
		cell.setValue("Bob Roberts");
		result.getResultCells().add(cell);
		results.add(result);

		result = new Result();
		cell = new ResultCell();
		cell.setKey("Person Id");
		cell.setValue("KIM-2");
		result.getResultCells().add(cell);
		cell = new ResultCell();
		cell.setKey("Person Name");
		cell.setValue("Joe Plumber");
		result.getResultCells().add(cell);
		results.add(result);

		result = new Result();
		cell = new ResultCell();
		cell.setKey("Person Id");
		cell.setValue("KIM-3");
		result.getResultCells().add(cell);
		cell = new ResultCell();
		cell.setKey("Person Name");
		cell.setValue("John Adams");
		result.getResultCells().add(cell);
		results.add(result);

		return results;
		// service.searchForResults(searchTypeKey, queryParamValues);
		// } catch (DoesNotExistException e) {
		// e.printStackTrace();
		// } catch (InvalidParameterException e) {
		// e.printStackTrace();
		// } catch (MissingParameterException e) {
		// e.printStackTrace();
		// } catch (OperationFailedException e) {
		// e.printStackTrace();
		// } catch (PermissionDeniedException e) {
		// e.printStackTrace();
		// }
		// return null;
	}

	private void aquireIdentityService() {
//		if(identityService==null){
//			try{
//				//{KIM}kimIdentityServiceSOAPUnsecure http://localhost:8081/ks-rice-dev/remoting/kimIdentityServiceSOAPUnsecure  
//				ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
//				factory.setServiceClass(IdentityService.class);
//				factory.setAddress(identityServiceAddress);
//				factory.setWsdlLocation(identityServiceAddress+"?wsdl");
//				factory.setServiceName(new QName("KIM", "kimIdentityServiceSOAPUnsecure"));
//				factory.getServiceFactory().setDataBinding((DataBinding) Class.forName("org.apache.cxf.aegis.databinding.AegisDatabinding").newInstance());
//				identityService = (IdentityService) factory.create();
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
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

		String givenName = "";
		if ("KIM-1".equals(personId)) {
			givenName = "Bob Roberts";
		} else if ("KIM-2".equals(personId)) {
			givenName = "Joe Plumber";
		} else if ("KIM-3".equals(personId)) {
			givenName = "John Adams";
		}

		nameInfo.setGivenName(givenName);
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
