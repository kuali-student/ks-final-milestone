package org.kuali.student.commons.ui.viewmetadata.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataService;
import org.kuali.student.commons.ui.viewmetadata.server.impl.ViewMetaDataServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ViewMetaDataServiceGWTImpl extends RemoteServiceServlet implements ViewMetaDataService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7229885467942096136L;

	private final ViewMetaDataService impl = new ViewMetaDataServiceImpl();

	@Override
	public ViewMetaData getViewMetaData(String locale, String viewName) {
		return impl.getViewMetaData(locale, viewName);
	}

	@Override
	public Map<String, ViewMetaData> getViewMetaDataMap(String locale, List<String> viewNames) {
		return impl.getViewMetaDataMap(locale, viewNames);
	}

}
