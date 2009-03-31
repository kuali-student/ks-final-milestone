package org.kuali.student.core.person.ui.server.gwt;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.dto.PersonNameInfo;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.core.person.ui.client.service.PersonRpc;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PersonRpcGwtServlet extends RemoteServiceServlet implements PersonRpc {

	private static final long serialVersionUID = 3797505861921543183L;
	
	private PersonService service;
	
	@Override
	public List<Result> searchForResults(String searchTypeKey,
			List<QueryParamValue> queryParamValues) {
		// TODO Auto-generated method stub
//		try {
			List<Result> results = new ArrayList<Result>();
			
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
//			service.searchForResults(searchTypeKey, queryParamValues);
//		} catch (DoesNotExistException e) {
//			e.printStackTrace();
//		} catch (InvalidParameterException e) {
//			e.printStackTrace();
//		} catch (MissingParameterException e) {
//			e.printStackTrace();
//		} catch (OperationFailedException e) {
//			e.printStackTrace();
//		} catch (PermissionDeniedException e) {
//			e.printStackTrace();
//		}
//		return null;
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
		
		String givenName="";
		if("KIM-1".equals(personId)){
			givenName="Bob Roberts";
		}else if("KIM-2".equals(personId)){
			givenName="Joe Plumber";
		}else if("KIM-3".equals(personId)){
			givenName="John Adams";
		}

		nameInfo.setGivenName(givenName);
		person.getPersonNameInfoList().add(nameInfo);
		return person;
	}



}
