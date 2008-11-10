package edu.umd.ks.poc.lum.scat.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;


@WebService(name = "ScatService", targetNamespace = "http://edu.umd.ks/poc/lum/scat")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ScatService {

	@WebMethod
	public List<ScatInfo> findScats(@WebParam(name = "scatTableNum") String scatTableNum);

	@WebMethod
	public List<ScatTableInfo> searchScats(@WebParam(name = "searchString") String searchString);
	
	@WebMethod
	public ScatTableInfo createScatTable(@WebParam(name = "scatTableInfo") ScatTableInfo scatTableInfo);
	
	@WebMethod
	public ScatTableInfo updateScatTable(@WebParam(name = "scatTableInfo") ScatTableInfo scatTableInfo);
	
	@WebMethod
	public boolean deleteScatTable(@WebParam(name = "scatTableId") String scatTableId);
	
	@WebMethod
	public int addScatCodesToScatTable(
			@WebParam(name = "scatTableId") String scatTableId,
			@WebParam(name = "scatInfoList") List<ScatInfo> scatInfoList);
	
	@WebMethod
	public int removeScatCodesFromScatTable(
			@WebParam(name = "scatTableId") String scatTableId,
			@WebParam(name = "scatInfoIdList") List<String> scatInfoIdList);
}
