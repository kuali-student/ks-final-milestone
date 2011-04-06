package org.kuali.rice.student.bo;


import java.util.LinkedHashMap;


import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;
import org.kuali.student.lum.lu.service.LuService;

public class CourseUpdate extends PersistableBusinessObjectBase {
    private static final long serialVersionUID = 5076936070286472632L;
    
    private Integer updateId; // TODO: map this to a sequence in db 
    private String courseId; 
    private String courseTitle;
    private String courseCode;
    private String subjectArea;
    
    private CourseTransient courseTransient;    
   
//    private LuService luService;
    
    

       
    @Override
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap m = new LinkedHashMap();
        m.put("updateId", updateId);
        m.put("courseId", courseId);
        m.put("courseCode", courseCode);
        m.put("courseTitle", courseTitle);
        m.put("subjectArea", subjectArea);
        return m;
    }

    /**
     * Gets the updateId
     * 
     * @return updateId
     */
    public Integer getUpdateId() {
        return updateId;
    }

    /**
     * Sets the updateId
     * 
     * @param updateId
     */
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    /**
     * Gets the courseId
     * 
     * @return courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Sets the courseId
     * 
     * @param courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the courseTitle
     * 
     * @return courseTitle
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Sets the courseTitle
     * 
     * @param courseTitle
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    
    /**
     * Gets the courseCode
     * 
     * @return the courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the courseCode
     * 
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    /**
     * Gets the subjectArea
     * 
     * @return subjectArea
     */
    public String getSubjectArea() {
        return subjectArea;
    }

    /**
     * Sets the subjectArea
     * 
     * @param subjectArea
     */
    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }
    
    /**
     * Gets the courseTransient
     * 
     * @return courseTransient
     */
    public CourseTransient getCourseTransient() {
//        courseTransient = retrieveACourse();
        return courseTransient;
    }
/*    
    private CourseTransient retrieveACourse(){

        List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
        SearchParam searchParam = new SearchParam();
        searchParam.setKey("lu.queryParam.luOptionalId");
        searchParam.setValue(getCourseId());
        queryParamValues.add(searchParam);
        
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchKey("lu.search.generic");
        searchRequest.setParams(queryParamValues);
        try {
                getTheLuService();
                if (luService == null){
                        System.out.println(">>>failed to get luService through getTheLuService()...");                  
                }
              
                SearchResult results = luService.search(searchRequest);
                List<SearchResultRow> rows = results.getRows();     
                if(rows.size()!= 1){
                    System.out.println(">>>Error, should only get one record");
                }
                SearchResultRow row  = rows.get(0);
                CourseTransient courseLookupResult = new CourseTransient();
                List<SearchResultCell> cells = row.getCells();                    
                for(SearchResultCell cell : cells) {                        
                    if(cell.getKey().equals("lu.resultColumn.cluId")) {
                        courseLookupResult.setCourseId(cell.getValue());
                    }
                    else if(cell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                        courseLookupResult.setCourseTitle(cell.getValue());
                    }
                    else if(cell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                        courseLookupResult.setCourseCode(cell.getValue());
                    }
                    else if(cell.getKey().equals("lu.resultColumn.luOptionalDivision")) {
                        courseLookupResult.setSubjectArea(cell.getValue());
                    }                        
                }
                return courseLookupResult;
                
            
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
    protected LuService getTheLuService() {
        if (this.luService == null) {
            this.luService = (LuService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lu","LuService")); 
        }
        return this.luService;
    }
*/
}
