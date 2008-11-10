package edu.umd.ks.poc.lum.web.scat.client.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;


public interface ScatRpcServiceAsync {

    public void findScats(String scatTableNum, AsyncCallback<List<ScatInfo>> callback);
    public void searchScats(String searchString, AsyncCallback<List<ScatTableInfo>> callback);

	public void createScatTable(ScatTableInfo scatTableInfo, AsyncCallback<ScatTableInfo> callback);

	public void updateScatTable(ScatTableInfo scatTableInfo, AsyncCallback<ScatTableInfo> callback);

	public void deleteScatTable(String scatTableId, AsyncCallback<Boolean> callback);

	public void addScatCodesToScatTable(String scatTableId,
			List<ScatInfo> scatInfoList, AsyncCallback<Integer> callback);

	public void removeScatCodesFromScatTable(String scatTableId,
			List<String> scatInfoIdList, AsyncCallback<Integer> callback);


}
