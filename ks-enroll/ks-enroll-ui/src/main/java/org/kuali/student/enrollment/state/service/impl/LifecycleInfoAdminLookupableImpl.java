package org.kuali.student.enrollment.state.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;

public class LifecycleInfoAdminLookupableImpl
        extends LookupableImpl {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LifecycleInfoAdminLookupableImpl.class);
    private transient StateService stateService;
    private final static String SEARCH_VALUE = "searchValue";

    @Override
    protected List<LifecycleInfo> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        String searchValue = fieldValues.get(SEARCH_VALUE);
        searchValue = searchValue.toLowerCase();
        return this.findMatching(searchValue);
    }

    private List<LifecycleInfo> findMatching(String searchValue) {
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        pList.add (PredicateFactory.equal("keywordSearch",searchValue));
        qBuilder.setPredicates(PredicateFactory.and(pList.toArray(new Predicate[pList.size()])));
        try {
            List<LifecycleInfo> list = this.getStateService().searchForLifecycles(qBuilder.build(), getContextInfo());
//            System.out.println ("Found " + list.size() + " lifecycles");
//            for (LifecycleInfo info : list) {
//                System.out.println (info.getKey() + " " + info.getName());
//            }
            return list;
        } catch (InvalidParameterException ex) {
            throw new RuntimeException(ex);
        } catch (MissingParameterException ex) {
            throw new RuntimeException(ex);
        } catch (OperationFailedException ex) {
            throw new RuntimeException(ex);
        } catch (PermissionDeniedException ex) {
            throw new RuntimeException(ex);
        }
        
//        // replaced below with a keyword search
//        List<LifecycleInfo> all = this.getAll();
//        List<LifecycleInfo> list = new ArrayList<LifecycleInfo>();
//        for (LifecycleInfo info : all) {
//            if (this.matches(info, searchValue)) {
//                list.add(info);
//            }
//        }
//        return list;
    }

    private boolean matches(LifecycleInfo info, String searchValue) {
        if (searchValue == null) {
            return true;
        }
        if (searchValue.isEmpty()) {
            return true;
        }
        if (contains(info.getKey(), searchValue)) {
            return true;
        }
        if (contains(info.getName(), searchValue)) {
            return true;
        }
        if (contains(info.getRefObjectUri(), searchValue)) {
            return true;
        }

        if (contains(info.getDescr(), searchValue)) {
            return true;
        }
        return false;
    }

    private boolean contains(RichTextInfo fieldValue, String searchValue) {
        if (fieldValue == null) {
            return false;
        }
        if (contains(fieldValue.getPlain(), searchValue)) {
            return true;
        }
        if (contains(fieldValue.getFormatted(), searchValue)) {
            return true;
        }
        return false;
    }

    private boolean contains(Object fieldValue, String searchValue) {
        if (fieldValue == null) {
            return false;
        }
        String fieldValueString = fieldValue.toString().toLowerCase();
        if (fieldValueString.contains(searchValue)) {
            return true;
        }
        return false;
    }

    private List<LifecycleInfo> getAll() {
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
//        List<Predicate> pList = new ArrayList<Predicate>();
//
//        pList.add (PredicateFactory.equal("keywordSearch","testing");
//        pList.add(p);
//
//        Predicate[] preds = new Predicate[pList.size()];
//        pList.toArray(preds);
//        qBuilder.setPredicates(PredicateFactory.and(preds));
        try {
            List<LifecycleInfo> list = this.getStateService().searchForLifecycles(qBuilder.build(), getContextInfo());
//            System.out.println ("found " + list.size() + " lifecycles");
//            for (LifecycleInfo info : list) {
//                System.out.println (info.getKey() + " " + info.getName());
//            }
            return list;
        } catch (InvalidParameterException ex) {
            throw new RuntimeException(ex);
        } catch (MissingParameterException ex) {
            throw new RuntimeException(ex);
        } catch (OperationFailedException ex) {
            throw new RuntimeException(ex);
        } catch (PermissionDeniedException ex) {
            throw new RuntimeException(ex);
        }
    }

    
    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public StateService getStateService() {
        if (stateService == null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE,
                    StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.stateService;
    }

    private ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
