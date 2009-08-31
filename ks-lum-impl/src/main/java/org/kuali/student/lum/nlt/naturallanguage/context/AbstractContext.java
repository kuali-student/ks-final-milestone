package org.kuali.student.lum.nlt.naturallanguage.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.nlt.naturallanguage.util.CustomCluSet;
import org.kuali.student.lum.nlt.naturallanguage.util.ReqComponentTypes;

public abstract class AbstractContext<T> implements Context<T> {
    private LuDao luDao;

	/**
	 * <p>These common shared tokens are needed since velocity doesn't 
	 * allow periods in tokens.</p>
	 * <p>E.g. reqCompFieldType.totalCredits must either be convert to 
	 * totalCredits or reqCompFieldType_totalCredits so a template would look 
	 * like:</p>
	 * <p>'Student must take $totalCredits of MATH 100'</p>
	 * or
	 * <p>'Student must take $reqCompFieldType_totalCredits of MATH 100'</p>
	 */
	protected final static String CLU_TOKEN = "clu";
	protected final static String CLU_SET_TOKEN = "cluSet";
	protected final static String EXPECTED_VALUE_TOKEN = "expectedValue";
	protected final static String OPERATOR_TOKEN = "relationalOperator";

	/*
	 * Constructor.
	 */
	public AbstractContext() {
	}

	/**
     * Sets the LU DAO.
     * 
     * @param luDao LU DAO
     */
    public void setLuDao(LuDao luDao) {
        this.luDao = luDao;
    }

    /**
     * Gets a CLU.
     * 
     * @param cluId CLU id
     * @return CLU
     * @throws DoesNotExistException If CLU does not exist
     */
    public Clu getClu(String cluId) throws DoesNotExistException {
        Clu clu = this.luDao.fetch(Clu.class, cluId);
        return clu;
    }

    /**
     * Gets the CLU set.
     * 
     * @param cluSetId CLU set id
     * @return CLU set
     * @throws DoesNotExistException If CLU set does not exist
     */
    public CustomCluSet getCluSet(String cluSetId) throws DoesNotExistException {
        CluSet cluSet = this.luDao.fetch(CluSet.class, cluSetId);
        return new CustomCluSet(cluSet);
    }

    /**
     * Gets a new CLU set from comma separated list of CLU ids.
     * 
     * @param cluIds Comma separated list of CLU ids
     * @return A new CLU set
     * @throws DoesNotExistException If CLU does not exist
     */
    public CustomCluSet getClusAsCluSet(String cluIds) throws DoesNotExistException {
    	String[] cluIdArray = cluIds.split("\\s*,\\s*");
    	CluSet cluSet = new CluSet();
    	List<Clu> list = new ArrayList<Clu>();
    	for(String cluId : cluIdArray) {
    		Clu clu = this.luDao.fetch(Clu.class, cluId.trim());
    		list.add(clu);
    	}
    	cluSet.setClus(list);
        return new CustomCluSet(cluSet);
    }

    /**
     * Gets a custom CLU set from a requirement component.
     * 
     * @param reqComponent Requirement component
     * @return custom CLU set
     * @throws DoesNotExistException
     */
    public CustomCluSet getCluSet(ReqComponent reqComponent) throws DoesNotExistException {
        Map<String, String> map = getReqCompField(reqComponent);
    	CustomCluSet cluSet = null;
    	if(map.containsKey(ReqComponentTypes.ReqCompFieldTypes.CLU_KEY.getKey())) {
        	String cluIds = map.get(ReqComponentTypes.ReqCompFieldTypes.CLU_KEY.getKey());
        	cluSet = getClusAsCluSet(cluIds);
        } else if(map.containsKey(ReqComponentTypes.ReqCompFieldTypes.CLUSET_KEY.getKey())) {
        	String cluSetId = map.get(ReqComponentTypes.ReqCompFieldTypes.CLUSET_KEY.getKey());
            cluSet = getCluSet(cluSetId);
        }
    	return cluSet;
    }

    /**
     * Gets requirement component fields as a map.
     * 
     * @param reqComponent Requirement Component
     * @return Map of requirement component fields
     */
    private Map<String, String> getReqCompField(ReqComponent reqComponent) {
        List<ReqComponentField> fields = reqComponent.getReqCompField();
        Map<String, String> map = new HashMap<String, String>();
        for (ReqComponentField field : fields) {
            String key = field.getKey();
            String value = field.getValue();
            map.put(key, value);
        }
        return map;
    }

    /**
     * Gets the value of the ReqComponentField key. 
     * See {@link ReqComponentField#getKey()} 
     * 
     * @param reqComponent Requirement Component
     * @param key <code>ReqComponentField</code> key
     * @return Value of <code>ReqComponentField</code>
     */
    public String getReqCompFieldValue(ReqComponent reqComponent, String key) {
        return getReqCompField(reqComponent).get(key);
    }

    /**
     * Creates the Velocity context map (template data) for a specific context.
     * 
     * @param context Context to create the map from
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
	public abstract Map<String, Object> createContextMap(T context) throws DoesNotExistException;
}
