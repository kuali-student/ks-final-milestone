package ca.ubc.student.kuali.lum.workflow;
  
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.service.WorkflowInfo;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.workflow.CluPostProcessor;

import ca.ubc.student.cdm.service.course.client.Course;
import ca.ubc.student.cdm.service.course.client.CourseException_Exception;
import ca.ubc.student.cdm.service.course.client.CourseRequest;
import ca.ubc.student.cdm.service.course.client.CourseResponse;
import ca.ubc.student.cdm.service.course.client.ICourseService;
import ca.ubc.student.kuali.lum.cdm.CdmConstants;

public class CdmPostProcessor extends CluPostProcessor {
  private static Logger LOG = Logger.getLogger(CdmPostProcessor.class);

  /**
   * overrides the route status change event handler to insert a course when approved
   * @param statusChangeEvent
   * @return ProcessDocReport
   */
  @Override
  public ProcessDocReport doRouteStatusChange(
      DocumentRouteStatusChange statusChangeEvent) throws Exception {
    LOG.info("doRouteStatusChange: Processing appDocId: " + statusChangeEvent.getAppDocId() + " New Status: " + statusChangeEvent.getNewRouteStatus() + " Old Status: " + statusChangeEvent.getOldRouteStatus());    
    ProcessDocReport cluRouteStatusChangeResult = super.doRouteStatusChange(statusChangeEvent);    
    ProcessDocReport processApproveResult = processApproveAction(statusChangeEvent);
    return new ProcessDocReport(cluRouteStatusChangeResult.isSuccess() && processApproveResult.isSuccess(), cluRouteStatusChangeResult + processApproveResult.getMessage());
  }
  
  /**
   * processes the approve event to insert a course
   * @param statusChangeEvent
   * @return ProcessDocReport
   */
  public ProcessDocReport processApproveAction(DocumentRouteStatusChange statusChangeEvent){
    ProcessDocReport docReport = new ProcessDocReport(true, "");
    if (statusChangeEvent.getNewRouteStatus().equals(KEWConstants.ROUTE_HEADER_APPROVED_CD)){
      LOG.info(statusChangeEvent.getAppDocId() + ": Document Approved");
      try {
        String response = insertCourse(statusChangeEvent.getRouteHeaderId());
        LOG.info("Course Approved for appDocID: " + statusChangeEvent.getAppDocId()+ " Successfully inserted course! " + response);        
        docReport = new ProcessDocReport(true, "Course Inserted Successfully for appDocId:" + statusChangeEvent.getAppDocId());
      }catch(Exception e){
        docReport = new ProcessDocReport(false, "Error insertig course for appDocId:" + statusChangeEvent.getAppDocId());
        LOG.error("Could not insert course with AppDocId: " + statusChangeEvent.getAppDocId(), e);
      }
    }
    return docReport;
  }
  

  /**
   * Returns a course service from the KSB
   * @return Course
   */
  public Course getCourseService(){
    QName serviceName = new QName(CdmConstants.NAMESPACE, CdmConstants.COURSE_SERVICE);
    ICourseService service = (ICourseService)GlobalResourceLoader.getService(serviceName);
    Course course = service.getCoursePort();
    return course;
  }
  
  /**
   * Inserts a course via the SIS web service but does so in a synchronous manner
   * @param routeHeaderId
   * @return
   * @throws CourseException_Exception
   * @throws WorkflowException
   * @throws DoesNotExistException
   * @throws InvalidParameterException
   * @throws MissingParameterException
   * @throws OperationFailedException
   */
  public String insertCourse(Long routeHeaderId) 
    throws CourseException_Exception, WorkflowException, DoesNotExistException, 
           InvalidParameterException, MissingParameterException, OperationFailedException{    
    CourseResponse response = getCourseService().insertNewCourse(getCourseRequest(routeHeaderId));
    return response.getResponse();
  }
  
  /**
   * Assumptions, only terms are Winter (W) and Summer (S) for SIS
   * @note Logic for determining start term is very rough, needs updating
   * @param effectiveDate
   * @return StartTerm
   */
  public String getStartTerm(Date effectiveDate){
    // TODO get requirements for start term and update logic for determining start term
    String startTerm = "";
    if (effectiveDate != null){
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(effectiveDate);
      
      String session = "W";
      if (calendar.get(Calendar.MONTH) < 9 && calendar.get(Calendar.MONTH) >= 5){
        session = "S";
      }
      startTerm = (Integer.valueOf(calendar.get(Calendar.YEAR)).toString()) + session;
    }
    return startTerm;
  }
  
  /**
   * Creates a course request from information in clu, overwriting some of the fields 
   * in the sample course request.
   * @param routeHeaderId
   * @return CourseRequest
   * @throws WorkflowException
   * @throws DoesNotExistException
   * @throws InvalidParameterException
   * @throws MissingParameterException
   * @throws OperationFailedException
   */
  private CourseRequest getCourseRequest(Long routeHeaderId ) throws WorkflowException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
     CourseRequest request = getSampleCourseRequest();
     WorkflowInfo workflowInfo = new WorkflowInfo();
     DocumentContentDTO document = workflowInfo.getDocumentContent(routeHeaderId);
     LuService luService = (LuService) GlobalResourceLoader.getService(new QName(CdmConstants.LU_SERVICE_NAMESPACE,CdmConstants.LU_SERVICE));
     
     Pattern pattern = Pattern.compile("<cluId>\\s*([^<\\s]+)");
     Matcher matcher = pattern.matcher(document.getApplicationContent());
     matcher.find();
     String cluId = matcher.group(1);

     CluInfo clu = luService.getClu(cluId);
     
     request.setCode(clu.getOfficialIdentifier().getCode());
     request.setShortTitle(clu.getOfficialIdentifier().getShortName());
     request.setLongTitle(clu.getOfficialIdentifier().getLongName());
     request.setStartTerm(getStartTerm(clu.getEffectiveDate()));              
     
     return request;     
  }

  /**
   * Creates a sample course request with default values, useful for testing
   * @return CourseRequest
   */
  private CourseRequest getSampleCourseRequest(){
    CourseRequest request = new CourseRequest();
    request.setChgStatus("A");
    request.setCode("JOSE332");
    request.setCourseType("ACAD");
   
    Calendar calendar = new GregorianCalendar();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       
    request.setFacApprDt(sdf.format(calendar.getTime()));
    request.setGradeType("PCNT");
    request.setLevel("U");
    request.setLocation("UBC");
    request.setLongTitle("Introduction to Mathematics II");
    request.setShortTitle("Intro to Math II");
    
    request.setSnteApprDt(sdf.format(calendar.getTime()));
    request.setStartTerm( Integer.valueOf(calendar.get(Calendar.YEAR)).toString() + "W" );
    
    return request;
  }  
  
}
