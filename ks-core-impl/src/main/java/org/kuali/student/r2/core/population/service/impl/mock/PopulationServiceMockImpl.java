package org.kuali.student.r2.core.population.service.impl.mock;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.population.service.impl.PopulationServiceImpl;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 7/24/12
 * Time: 11:44 AM
 *
 * This class is just extends the existing population service, but allows for us to return actual people ids. the
 * ids are generated, but in a way that the id will be the same Every time you start the app.    Eventually this
 * class will go away.
 */
public class PopulationServiceMockImpl extends PopulationServiceImpl implements MockService {

    private static Map<String, Set<String>> caches = new HashMap<String, Set<String>>();

    @Override
    public void clear() {
        caches.clear();
    }

    @Override
    public Boolean isMemberAsOfDate( String personId,  String populationId,  Date date,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == personId || 0 == personId.length()) {
            throw new MissingParameterException("personId");
        }
        if (null == populationId || 0 == populationId.length()) {
            throw new MissingParameterException("populationId");
        }

        Set<String> cache = caches.get(populationId);
        if (null == cache) {
            throw new DoesNotExistException("populationId '" + populationId + "' not found");
        }
        return cache.contains(personId);
    }

    @Override
    public List<String> getMembersAsOfDate( String populationId,  Date date,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == populationId || 0 == populationId.length()) {
            throw new MissingParameterException("populationId");
        }

        return this.getPopulationMembersById(populationId);

    }

    private List<String> getPopulationMembersById(String populationId)   throws DoesNotExistException {
        Set<String> cache = caches.get(populationId);
        if (null == cache) {
            int hCode = getHashCode(populationId);   // the hash is always the same
            int num = Math.abs(hCode % 5);      // get positive int between 0-5
            if(num == 0) num = 3;               // make sure it's not 0
            num *= 100;                         // how many people per population

            caches.put(populationId, new HashSet<String>()); // prime the population cache
            generateStudentPopulations(populationId,populationId,num);     // generate the ids

            cache =  caches.get(populationId);
        }

        return new ArrayList<String>(cache);
    }

    private int getHashCode(String input){
        HashCodeBuilder hcb = new HashCodeBuilder(17,37).append(input);
        return hcb.hashCode();
    }

    private void generateStudentPopulations(String populationCacheKey, String populationPrefix, int numToGenerate){
        int base =  100000000;    // start here

        for(int i = 0; i<numToGenerate; i++){
            // bc of the prefix, these people ids will always be the same
            caches.get(populationCacheKey).add(populationPrefix + (base + i));
        }
    }


}
