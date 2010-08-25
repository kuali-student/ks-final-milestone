#!/usr/bin/env ruby

# 
# == Synopsis
#
# Organization class containing all operations around curriculum objects
#
# Author:: Kyle Campos (mailto:kcampos@rsmart.com)
#

class Curriculum
  
  attr_accessor :request
  
  def initialize(request_obj)
    @request = request_obj
  end
    
  # Load curriculum homepage
  def homepage
    
            
  end
  
  
  # Create a proposal
  # TODO: Org ID is hardcoded to Fisheries Department in requests, need to use dyn_variable
  #
  # Option: DEFAULT_VALUE
  # * 'mode': 'blank'
  def create_proposal(title, oversight_department, admin_org, propose_person, opts={})
    
    lo_cat = "Test Category 2"
    lo_cat_text = "LO Cat Text"
    
    defaults = {
      :mode => 'blank',
      :nav_homepage => true,
      :submit => true,
      :append_unique_id => false, #tell tsung to append unique id on title
      :instructor => propose_person,
      :first_expected_offering => @request.config.directory["atp"]["name"],
      :subject_area => "ARTS",
      :course_suffix => "123",
      :course_short_title => "Perf Course",
      :course_title => "Performance Course",
      :course_description => "My fake description.",
      :course_rationale => "My fake rationale.",
      :lo_create => FALSE,
      :lo_category => lo_cat,
      :lo_cat_text => lo_cat_text,
      :lo_name => @request.config.directory["lo"]["name"],
      :admin_dep_var_name => "admin_dep_org_id",
      :admin_dep_var_regexp => 'org.resultColumn.orgId\"\,\"\([^\"]+\)',
      :proposal_dyn_var_name => "proposal_id",
      :proposal_dyn_var_regexp => '\"proposal\"\,\"\([^\"]+\)',
      :proposal_doc_id_var_name => "proposal_doc_id",
      :proposal_doc_id_var_regexp => 'workflowId\"\,\"\([^\"]+\)\"',
      :clu_ref_dyn_var_name => "clu_ref_id",
      :clu_ref_dyn_var_regexp => '\"id\"\,\"\([^\"]+\)',
      :result_com_var_name => "result_component_id",
      :result_com_var_regexp => '\"ResultComponent 1\"\,\"\([^\"]+\)',
      :enroll_est_var_name => "default_enrollment_estimate_id",
      :enroll_est_var_regexp => 'defaultEnrollmentEstimate\"\,\"\([^\"]+\)',
      :lab_var_name => "lab_id",
      :lab_var_regexp => 'draft\"\,\"Lab\"\,\"\([^\"]+\)',
      :joints_var_name => "joints_num",
      :joints_var_regexp => 'joints\"\,\"[^\"]+\"\,\"\([^\"]+\)',
      :fee_info_id_dyn_var_name => 'fee_info_id',
      :fee_info_id_dyn_var_regexp => '\"fees\"\,\"\([^\"]+\)',
      :fee_info_dyn_var_name => 'fee_info',
      :fee_info_dyn_var_regexp => 'org.kuali.student.lum.lu.dto.CluFeeInfo\"\,\"\([^\"]+\)',
      :clu_info_dyn_var_name => 'clu_info',
      :clu_info_dyn_var_regexp => 'org.kuali.student.lum.lu.dto.CluInfo\"\,\"\([^\"]+\)',
      :lu_dto_clu_format_dyn_var_name => "lu_dto_clu_format",
      :lu_dto_clu_format_dyn_var_regexp => 'org.kuali.student.lum.lu.dto.CluInfo\"\,\"Credit Course\"\,\"[^\"]+\"\,\"formats\"\,\"\([^\"]+\)',
      :lu_dto_clu_activities_dyn_var_name => "lu_dto_clu_activites",
      :lu_dto_clu_activities_dyn_var_regexp => 'org.kuali.student.lum.lu.dto.CluInfo\"\,\"Credit Course\"\,\"[^\"]+\"\,\"formats\"\,\"[^\"]+\"\,\"0\"\,\"activities\"\,\"\([^\"]+\)',
      :outcome_id_var_name => "outcome_id",
      :outcome_id_var_regexp => 'outcomeId\"\,\"\([^\"]+\)',
      :lo_category_var_name => "lo_category",
      :lo_category_var_regexp => lo_cat_text + '\"\,\"plain\"\,\"id\"\,\"\([^\"]+\)',
      :lo_child_id_dyn_var_name => "lo_child_id",
      :lo_child_id_dyn_var_regexp => 'childLo\"\,\"\([^\"]+\)',
      :single_use_lo_dyn_var_name => "single_use_lo",
      :single_use_lo_dyn_var_regexp => 'includedSingleUseLo\"\,\"\([^\"]+\)'
    }
    
    # Version for the doc at each step. We'll increment on each usage
    # So first usage should eval to 0
    version_indicator = -1
    
    opts = defaults.merge(opts)
    
    title << '_%%ts_user_server:get_unique_id%%' if(opts[:append_unique_id])
    
    if(opts[:mode] != "blank")
      # select template or copy course...
    end
    
    # Navigate to Curriculum Mgmt
    self.homepage() unless(!opts[:nav_homepage])
    
    # Click Start blank proposal
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|5|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getMetadata|java.lang.String/2004016611|1|2|3|4|2|5|5|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.campusLocation|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|18|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.type|kuali.resultComponentType.grade.finalGrade|lrc.queryParam.resultComponent.idRestrictionList|java.lang.String/2004016611|kuali.resultComponent.grade.letter|kuali.resultComponent.grade.satisfactory|kuali.resultComponent.grade.percentage|kuali.resultComponent.grade.recitalReview|kuali.resultComponent.grade.designReview|kuali.resultComponent.grade.completedNotation|lrc.search.resultComponent|1|2|3|4|1|5|5|0|0|6|2|7|8|0|9|7|10|6|6|11|12|11|13|11|14|11|15|11|16|11|17|0|18|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|atp.search.atpSeasonTypes|1|2|3|4|1|5|5|0|0|6|0|7|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|atp.search.atpDurationTypes|1|2|3|4|1|5|5|0|0|6|0|7|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.finalExam.status|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|16|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|atp.advancedAtpSearchParam.atpType|java.lang.String/2004016611|kuali.atp.type.Spring|kuali.atp.type.Fall|kuali.atp.type.Session1|kuali.atp.type.Session2|kuali.atp.type.Mini-mester1A|kuali.atp.type.Mini-mester1B|atp.search.advancedAtpSearch|1|2|3|4|1|5|5|0|0|6|1|7|8|6|6|9|10|9|11|9|12|9|13|9|14|9|15|0|16|0|0|0|"
      }
    )
    # DUPLICATE - BUG
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|16|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|atp.advancedAtpSearchParam.atpType|java.lang.String/2004016611|kuali.atp.type.Spring|kuali.atp.type.Fall|kuali.atp.type.Session1|kuali.atp.type.Session2|kuali.atp.type.Mini-mester1A|kuali.atp.type.Mini-mester1B|atp.search.advancedAtpSearch|1|2|3|4|1|5|5|0|0|6|1|7|8|6|6|9|10|9|11|9|12|9|13|9|14|9|15|0|16|0|0|0|"
      }
    )
    
    
    #
    # Pg1 - Course Information
    #
    
    # Course Subject Area
    # AJAX popup while typing in subject area
    for i in 1..opts[:subject_area].length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationOptionalCode|#{opts[:subject_area][0..itr]}|enumeration.queryParam.enumerationType|kuali.lu.subjectArea|enumeration.management.search|1|2|3|4|1|5|5|0|6|0|7|2|8|9|0|10|8|11|0|12|13|0|0|0|"
        }                             
      )        
    end
    
    # Instructor
    for i in 1..opts[:instructor].length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|person.queryParam.personGivenName|#{opts[:instructor][0..itr]}|person.search.personQuickViewByGivenName|1|2|3|4|1|5|5|0|6|0|7|1|8|9|0|10|11|0|0|0|"
        }                             
      )        
    end
    
        
    # Save & Continue
    
    contents1 = "5|0|39|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|name|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|#{title}|_runtimeData|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|rationale|#{opts[:course_rationale]}|courseTitle|transcriptTitle|subjectArea|courseNumberSuffix|instructors|#{opts[:course_short_title]}|#{opts[:subject_area]}|#{opts[:course_suffix]}|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|personId|#{opts[:instructor]}|id-translation|(#{opts[:instructor]})|descr|plain|#{opts[:course_description]}|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|5|8|9|0|8|10|11|12|5|8|9|0|3|10|13|14|15|10|16|12|5|8|9|0|1|10|17|12|5|8|9|0|2|-9|18|19|1|10|20|18|-20|-13|-15|-7|-11|-21|14|21|-3|-5|10|22|14|15|-11|12|5|8|9|0|1|-15|12|5|8|9|0|5|-24|18|-20|10|23|18|-20|10|24|18|-20|10|25|18|-20|10|26|18|-20|-27|-15|-3|-11|-33|14|27|-35|14|28|-37|14|29|-39|12|5|8|9|0|1|30|31|0|12|5|8|9|0|2|10|32|14|33|10|16|12|5|8|9|0|1|10|32|12|5|8|9|0|1|10|34|14|35|-56|-58|-50|-54|-45|-47|-3|10|26|10|36|12|5|8|9|0|2|10|37|14|38|-11|12|5|8|9|0|1|-15|12|5|8|9|0|1|-69|18|-20|-72|-15|-67|-11|-3|-65|0|0|39|0|8|9|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        :dyn_variables => [
          {"name" => opts[:proposal_dyn_var_name], "regexp" => opts[:proposal_dyn_var_regexp]},
          {"name" => opts[:clu_ref_dyn_var_name], "regexp" => opts[:clu_ref_dyn_var_regexp]},
          {"name" => opts[:proposal_doc_id_var_name], "regexp" => opts[:proposal_doc_id_var_regexp]}
        ]
      }
    )
    
    @request.add("DEBUG/proposal_dyn_var_name/%%_#{opts[:proposal_dyn_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/clu_ref_dyn_var_name/%%_#{opts[:clu_ref_dyn_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/proposal_doc_id_var_name/%%_#{opts[:proposal_doc_id_var_name]}%%", {}, {'subst' => 'true'})
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    # DUPLICATE - BUG
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    # DUPLICATE - BUG
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    # Save & Continue
    contents1 = "5|0|82|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|administeringOrgs|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|campusLocations|code|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|courseTitle|#{title}|creditOptions|crossListings|curriculumOversightOrgs|descr|plain|#{opts[:course_description]}|expenditure|affiliatedOrgs|fees|formats|gradingOptions|id|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|personId|#{opts[:instructor]}|_runtimeData|id-translation|(#{opts[:instructor]})|joints|metaInfo|createId|#{propose_person}|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.util.Date/1659716317|updateId|updateTime|versionInd|#{version_indicator+=1}|pilotCourse|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|revenues|specialTopicsCourse|state|draft|subjectArea|#{opts[:subject_area]}|termsOffered|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|variations|dirty|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|java.sql.Timestamp/1769758459|1|name|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|29|8|9|10|5|6|7|0|0|-1|-3|8|11|10|5|6|7|0|0|-1|-7|8|12|13|14|8|15|13|16|8|17|10|5|6|7|0|0|-1|-15|8|18|13|19|8|20|10|5|6|7|0|0|-1|-21|8|21|10|5|6|7|0|0|-1|-25|8|22|10|5|6|7|0|0|-1|-29|8|23|10|5|6|7|0|1|8|24|13|25|-1|-33|8|26|10|5|6|7|0|1|8|27|10|5|6|7|0|0|-41|-43|-1|-39|8|28|10|5|6|7|0|0|-1|-47|8|29|10|5|6|7|0|0|-1|-51|8|30|10|5|6|7|0|0|-1|-55|8|31|13|32|8|33|10|5|6|7|0|1|34|35|0|10|5|6|7|0|2|8|36|13|37|8|38|10|5|6|7|0|1|8|36|10|5|6|7|0|1|8|39|13|40|-74|-76|-68|-72|-63|-65|-1|8|33|8|41|10|5|6|7|0|0|-1|-83|8|42|10|5|6|7|0|5|8|43|13|44|8|45|46|47|2783736332|1279900254208|8|48|13|44|8|49|46|47|2783736332|1279900254208|8|50|13|51|-1|-87|8|52|53|54|0|8|55|10|5|6|7|0|0|-1|-106|8|56|53|-105|8|57|13|58|8|59|13|60|8|61|10|5|6|7|0|0|-1|-116|8|62|13|63|8|64|13|65|8|66|10|5|6|7|0|0|-1|-124|8|38|10|5|6|7|0|2|8|59|10|5|6|7|0|1|8|39|13|60|-130|-132|8|67|10|5|6|7|0|2|8|33|53|54|1|8|59|53|-144|-130|-138|-1|-128|8|68|10|5|6|7|0|10|8|31|13|69|8|42|10|5|6|7|0|5|8|43|13|44|8|45|46|70|2783736260|1279900254208|468000000|8|48|13|44|8|49|46|47|2783737706|1279900254208|8|50|13|71|-149|-153|8|72|13|19|8|73|10|5|6|7|0|1|34|-66|13|32|-149|-171|8|74|13|75|8|76|10|5|6|7|0|0|-149|-179|8|77|10|5|6|7|0|0|-149|-183|8|78|13|79|8|64|13|80|8|81|13|82|-1|-147|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add_thinktime(2)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    
    #
    # Governance
    #

    # COC Org
    for i in 1..oversight_department.length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|15|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{oversight_department[0..itr]}|org.queryParam.orgOptionalType|java.lang.String/2004016611|kuali.org.Department|kuali.org.College|org.search.generic|1|2|3|4|1|5|5|0|6|0|7|2|8|9|0|10|8|11|7|2|12|13|12|14|0|15|0|0|0|"
        }                             
      )        
    end

    # Admin Org
    for i in 1..admin_org.length
      itr = i-1
      if(itr == admin_org.length-1)
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|15|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{admin_org[0..itr]}|org.queryParam.orgOptionalType|java.lang.String/2004016611|kuali.org.Department|kuali.org.College|org.search.generic|1|2|3|4|1|5|5|0|6|0|7|2|8|9|0|10|8|11|7|2|12|13|12|14|0|15|0|0|0|"
          },
          {
            :dyn_variables => [{"name" => opts[:admin_dep_var_name], "regexp" => opts[:admin_dep_var_regexp]}]
          }     
        )
      else
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|15|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{admin_org[0..itr]}|org.queryParam.orgOptionalType|java.lang.String/2004016611|kuali.org.Department|kuali.org.College|org.search.generic|1|2|3|4|1|5|5|0|6|0|7|2|8|9|0|10|8|11|7|2|12|13|12|14|0|15|0|0|0|"
          }    
        )
      end     
    end

    # Save & Continue
    contents1 = "5|0|83|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|administeringOrgs|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|58|_runtimeData|id-translation|#{admin_org}|campusLocations|ALL|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|courseTitle|#{title}|creditOptions|crossListings|curriculumOversightOrgs|descr|plain|#{opts[:course_description]}|expenditure|affiliatedOrgs|fees|formats|gradingOptions|id|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|personId|#{opts[:instructor]}|(#{opts[:instructor]})|joints|metaInfo|createId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateId|updateTime|versionInd|#{version_indicator+=1}|pilotCourse|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|revenues|specialTopicsCourse|state|draft|subjectArea|#{opts[:subject_area]}|termsOffered|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|variations|dirty|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|2|name|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|29|8|9|10|5|6|7|0|2|11|12|0|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-12|-14|-5|-10|-1|8|9|8|18|10|5|6|7|0|1|11|-8|13|19|-1|8|18|8|20|13|21|8|22|13|23|8|24|10|5|6|7|0|0|-1|-32|8|25|13|26|8|27|10|5|6|7|0|0|-1|-38|8|28|10|5|6|7|0|0|-1|-42|8|29|10|5|6|7|0|2|11|-8|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-54|-56|-48|-52|-1|8|29|8|30|10|5|6|7|0|1|8|31|13|32|-1|-63|8|33|10|5|6|7|0|1|8|34|10|5|6|7|0|0|-71|-73|-1|-69|8|35|10|5|6|7|0|0|-1|-77|8|36|10|5|6|7|0|0|-1|-81|8|37|10|5|6|7|0|0|-1|-85|8|38|13|39|8|40|10|5|6|7|0|1|11|-8|10|5|6|7|0|2|8|41|13|42|8|15|10|5|6|7|0|1|8|41|10|5|6|7|0|1|8|16|13|43|-103|-105|-97|-101|-93|-95|-1|8|40|8|44|10|5|6|7|0|0|-1|-112|8|45|10|5|6|7|0|5|8|46|13|42|8|47|48|49|2785260101|1279900254208|309000000|8|50|13|42|8|51|48|49|2785262975|1279900254208|183000000|8|52|13|53|-1|-116|8|54|55|56|0|8|57|10|5|6|7|0|0|-1|-135|8|58|55|-134|8|59|13|60|8|61|13|62|8|63|10|5|6|7|0|0|-1|-145|8|64|13|65|8|66|13|67|8|68|10|5|6|7|0|0|-1|-153|8|15|10|5|6|7|0|2|8|61|10|5|6|7|0|1|8|16|13|62|-159|-161|8|69|10|5|6|7|0|5|8|18|55|56|1|8|29|55|-173|8|9|55|-173|8|40|55|-173|8|61|55|-173|-159|-167|-1|-157|8|70|10|5|6|7|0|10|8|38|13|71|8|45|10|5|6|7|0|5|8|46|13|42|8|47|48|49|2785260064|1279900254208|272000000|8|50|13|42|8|51|48|49|2785262727|1279900254208|935000000|8|52|13|72|-184|-188|8|73|13|26|8|74|10|5|6|7|0|1|11|-8|13|39|-184|-206|8|75|13|76|8|77|10|5|6|7|0|0|-184|-214|8|78|10|5|6|7|0|0|-184|-218|8|79|13|80|8|66|13|81|8|82|13|83|-1|-182|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true'
      }
    )

    @request.add_thinktime(2)

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    #
    # Course Logistics    
    #
    
    # Outcome
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.idRestrictionList|java.lang.String/2004016611|kuali.resultComponentType.credit.degree.fixed|kuali.resultComponentType.credit.degree.range|kuali.resultComponentType.credit.degree.multiple|lrc.search.resultComponentType|1|2|3|4|1|5|5|0|0|6|1|7|8|6|3|9|10|9|11|9|12|0|13|0|0|0|"
      }
    )
    
    # Course Format
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lu.queryParam.luOptionalLuTypeStartsWith|kuali.lu.type.activity.|lu.search.all.lu.Types|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.atptype.duration|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|atp.search.atpDurationTypes|1|2|3|4|1|5|5|0|0|6|0|7|0|0|0|"
      }
    )

    # Save & Continue
    contents1 = "5|0|107|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|administeringOrgs|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|58|_runtimeData|id-translation|#{admin_org}|campusLocations|ALL|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|courseTitle|#{title}|creditOptions|fixedCreditValue|10|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|type|created|kuali.resultComponentType.credit.degree.fixed|crossListings|curriculumOversightOrgs|descr|plain|#{opts[:course_description]}|expenditure|affiliatedOrgs|fees|formats|activities|activityType|defaultEnrollmentEstimate|kuali.lu.type.activity.Lab|contactHours|unitQuantity|5|unitType|kuali.atp.duration.week|duration|atpDurationTypeKey|timeQuantity|kuali.atp.duration.Semester|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|gradingOptions|kuali.resultComponent.grade.letter|id|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|personId|#{opts[:instructor]}|(#{opts[:instructor]})|joints|metaInfo|createId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateId|updateTime|versionInd|#{version_indicator+=1}|pilotCourse|revenues|specialTopicsCourse|state|draft|subjectArea|#{opts[:subject_area]}|termsOffered|kuali.atp.season.Any|transcriptTitle|#{opts[:course_short_title]}|kuali.lu.type.CreditCourse|variations|finalExamStatus|audit|passFail|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|3|name|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%|STD"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|33|8|9|10|5|6|7|0|2|11|12|0|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-12|-14|-5|-10|-1|8|9|8|18|10|5|6|7|0|1|11|-8|13|19|-1|8|18|8|20|13|21|8|22|13|23|8|24|10|5|6|7|0|0|-1|-32|8|25|13|26|8|27|10|5|6|7|0|1|11|-8|10|5|6|7|0|3|8|28|13|29|8|15|10|5|6|7|0|3|8|30|10|5|6|7|0|2|-46|31|32|1|8|33|31|-57|-50|-52|8|34|31|-57|-48|10|5|6|7|0|1|-52|10|5|6|7|0|1|-60|31|-57|-63|-52|-50|-48|-44|-48|-58|13|35|-40|-42|-1|-38|8|36|10|5|6|7|0|0|-1|-70|8|37|10|5|6|7|0|2|11|-8|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-82|-84|-76|-80|-1|8|37|8|38|10|5|6|7|0|1|8|39|13|40|-1|-91|8|41|10|5|6|7|0|1|8|42|10|5|6|7|0|0|-99|-101|-1|-97|8|43|10|5|6|7|0|0|-1|-105|8|44|10|5|6|7|0|1|11|-8|10|5|6|7|0|2|8|45|10|5|6|7|0|1|11|-8|10|5|6|7|0|5|-48|10|5|6|7|0|3|-52|10|5|6|7|0|2|8|46|31|-57|8|47|31|-57|-126|-52|8|34|31|-57|-48|10|5|6|7|0|1|-52|10|5|6|7|0|1|-135|31|-57|-138|-52|-126|-48|-123|-48|-131|13|48|8|49|10|5|6|7|0|3|8|50|13|51|-48|10|5|6|7|0|1|-52|10|5|6|7|0|2|-149|31|-57|8|52|31|-57|-152|-52|-147|-48|-158|13|53|-123|-145|8|54|10|5|6|7|0|3|-48|10|5|6|7|0|1|-52|10|5|6|7|0|2|8|55|31|-57|8|56|31|-57|-166|-52|-163|-48|-171|13|57|-173|58|12|1|-123|-161|-133|58|12|100|-119|-121|-115|-117|8|15|10|5|6|7|0|2|8|34|31|-57|-48|10|5|6|7|0|1|-52|10|5|6|7|0|1|-184|31|-57|-187|-52|-182|-48|-115|-180|-111|-113|-1|-109|8|59|10|5|6|7|0|1|11|-8|13|60|-1|8|59|8|61|13|62|8|63|10|5|6|7|0|1|11|-8|10|5|6|7|0|2|8|64|13|65|8|15|10|5|6|7|0|1|8|64|10|5|6|7|0|1|8|16|13|66|-214|-216|-208|-212|-204|-206|-1|-202|8|67|10|5|6|7|0|0|-1|-222|8|68|10|5|6|7|0|5|8|69|13|65|8|70|71|72|2789013534|1279900254208|742000000|8|73|13|65|8|74|71|72|2789022524|1279900254208|732000000|8|75|13|76|-1|-226|8|77|31|32|0|8|78|10|5|6|7|0|0|-1|-245|8|79|31|-244|8|80|13|81|8|82|13|83|8|84|10|5|6|7|0|1|11|-8|13|85|-1|8|84|8|86|13|87|8|33|13|88|8|89|10|5|6|7|0|0|-1|-266|8|15|10|5|6|7|0|2|8|82|10|5|6|7|0|1|8|16|13|83|-272|-274|-52|10|5|6|7|0|8|8|84|31|-57|8|59|31|-57|8|90|31|-57|-27|31|-57|-90|31|-57|-20|31|-57|8|91|31|-57|8|92|31|-57|-272|-52|-1|-270|8|93|10|5|6|7|0|10|8|61|13|94|8|68|10|5|6|7|0|5|8|69|13|65|8|70|71|72|2789013475|1279900254208|683000000|8|73|13|65|8|74|71|72|2789022311|1279900254208|519000000|8|75|13|95|-298|-302|8|96|13|26|8|97|10|5|6|7|0|1|11|-8|13|62|-298|-320|8|98|13|99|8|100|10|5|6|7|0|0|-298|-328|8|101|10|5|6|7|0|0|-298|-332|8|102|13|103|8|33|13|104|8|105|13|106|-1|-296|8|54|10|5|6|7|0|3|-48|10|5|6|7|0|1|-52|10|5|6|7|0|2|8|55|31|-57|8|56|31|-57|-347|-52|-344|-48|-352|13|57|-354|58|-177|-1|-342|-287|13|107|-292|31|-244|-294|31|-244|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
     {
       'method' => 'POST',
       'content_type' => 'text/x-gwt-rpc; charset=utf-8',
       'contents' => "#{contents1}#{contents2}"
     },
     {
       'subst' => 'true',
       :dyn_variables => [
         {"name" => opts[:enroll_est_var_name], "regexp" => opts[:enroll_est_var_regexp]},
         {"name" => opts[:lab_var_name], "regexp" => opts[:lab_var_regexp]}
       ]
     }
    )
    
    @request.add("DEBUG/enroll_est_var_name/%%_#{opts[:enroll_est_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/lab_var_name/%%_#{opts[:lab_var_name]}%%", {}, {'subst' => 'true'})

    @request.add_thinktime(2)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.idRestrictionList|java.lang.String/2004016611|kuali.resultComponentType.credit.degree.fixed|kuali.resultComponentType.credit.degree.range|kuali.resultComponentType.credit.degree.multiple|lrc.search.resultComponentType|1|2|3|4|1|5|5|0|0|6|1|7|8|6|3|9|10|9|11|9|12|0|13|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|atp.search.atpDurationTypes|1|2|3|4|1|5|5|0|0|6|0|7|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lu.queryParam.luOptionalLuTypeStartsWith|kuali.lu.type.activity.|lu.search.all.lu.Types|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.atptype.duration|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
       'method' => 'POST',
       'content_type' => 'text/x-gwt-rpc; charset=utf-8',
       'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
       'subst' => 'true'
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    
    #
    # Learning Objectives
    #
    
    # Category
    for i in 1..opts[:lo_category].length
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lo.queryParam.loOptionalCategoryName|#{opts[:lo_category][0..itr]}|lo.search.loCategories|1|2|3|4|1|5|5|0|6|0|7|1|8|9|0|10|11|0|0|0|"
        }                             
      )        
    end

    # Save & Continue
    contents1 = "5|0|125|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|administeringOrgs|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|58|_runtimeData|id-translation|#{admin_org}|passFail|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|audit|finalExamStatus|STD|campusLocations|ALL|All|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|loInfo|id|desc|formatted|#{opts[:lo_cat_text]}|plain|name|SINGLE USE LO|sequence|0|metaInfo|loCategoryInfoList|courseTitle|#{title}|creditOptions|fixedCreditValue|10|kuali.creditType.credit.degree.10|versionInd|resultValues|type|kuali.resultComponentType.credit.degree.fixed|Credits, Fixed|crossListings|curriculumOversightOrgs|descr|#{opts[:course_description]}|duration|atpDurationTypeKey|kuali.atp.duration.Semester|timeQuantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|Semester|expenditure|affiliatedOrgs|fees|formats|activities|activityType|kuali.lu.type.activity.Lab|contactHours|unitQuantity|5|unitType|kuali.atp.duration.week|per week|defaultEnrollmentEstimate|%%_#{opts[:enroll_est_var_name]}%%|createId|#{propose_person}|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateId|updateTime|state|draft|Lab|%%_#{opts[:lab_var_name]}%%|kuali.lu.type.CreditCourseFormatShell|gradingOptions|kuali.resultComponent.grade.letter|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|personId|(#{opts[:instructor]})|joints|3|pilotCourse|revenues|specialTopicsCourse|subjectArea|#{opts[:subject_area]}|termsOffered|kuali.atp.season.Any|Any|transcriptTitle|#{opts[:course_short_title]}|kuali.lu.type.CreditCourse|variations|Standard final Exam|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|4|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|33|8|9|10|5|6|7|0|2|11|12|0|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-12|-14|-5|-10|-1|-3|8|18|19|20|0|8|21|19|-22|8|22|13|23|8|24|10|5|6|7|0|2|11|-8|13|25|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|26|-35|-37|-29|-33|-1|-27|8|27|13|28|8|29|13|30|8|31|10|5|6|7|0|1|11|-8|10|5|6|7|0|2|8|32|10|5|6|7|0|5|8|33|13|0|8|34|10|5|6|7|0|2|8|35|13|36|8|37|13|36|-57|-61|8|38|13|39|8|40|13|41|8|42|10|0|-53|-55|8|43|10|5|6|7|0|0|-53|-75|-49|-51|-1|8|31|8|44|13|45|8|46|10|5|6|7|0|1|11|-8|10|5|6|7|0|6|8|47|13|48|8|33|13|49|8|42|10|5|6|7|0|1|8|50|13|41|-88|-94|8|51|10|5|6|7|0|1|11|-8|13|48|-88|-100|8|52|13|53|8|15|10|5|6|7|0|1|8|52|10|5|6|7|0|1|8|16|13|54|-110|-112|-88|-108|-84|-86|-1|-82|8|55|10|5|6|7|0|0|-1|-118|8|56|10|5|6|7|0|2|11|-8|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-130|-132|-124|-128|-1|-122|8|57|10|5|6|7|0|1|8|37|13|58|-1|-138|8|59|10|5|6|7|0|3|8|60|13|61|8|62|63|12|1|8|15|10|5|6|7|0|1|8|60|10|5|6|7|0|1|8|16|13|64|-155|-157|-146|-153|-1|-144|8|65|10|5|6|7|0|1|8|66|10|5|6|7|0|0|-165|-167|-1|-163|8|67|10|5|6|7|0|0|-1|-171|8|68|10|5|6|7|0|1|11|-8|10|5|6|7|0|5|8|69|10|5|6|7|0|1|11|-8|10|5|6|7|0|9|8|70|13|71|8|72|10|5|6|7|0|3|8|73|13|74|8|75|13|76|8|15|10|5|6|7|0|1|8|75|10|5|6|7|0|1|8|16|13|77|-203|-205|-195|-201|-189|-193|8|56|10|5|6|7|0|0|-189|-211|8|78|63|12|100|8|59|10|5|6|7|0|3|8|60|13|61|8|62|63|-152|8|15|10|5|6|7|0|1|8|60|10|5|6|7|0|1|8|16|13|64|-228|-230|-220|-226|-189|-218|8|33|13|79|8|42|10|5|6|7|0|5|8|80|13|81|8|82|83|84|2790196090|1279900254208|298000000|8|85|13|81|8|86|83|84|2790196090|1279900254208|298000000|8|50|13|41|-189|-238|8|87|13|88|8|15|10|5|6|7|0|1|8|70|10|5|6|7|0|1|8|16|13|89|-258|-260|-189|-256|-185|-187|-181|-183|8|33|13|90|8|42|10|5|6|7|0|5|8|80|13|81|8|82|83|84|2790196053|1279900254208|261000000|8|85|13|81|8|86|83|84|2790196053|1279900254208|261000000|8|50|13|41|-181|-268|8|87|13|88|8|52|13|91|-177|-179|-1|-175|8|92|10|5|6|7|0|2|11|-8|13|93|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|0|-296|-298|-290|-294|-1|-288|8|33|13|94|8|95|10|5|6|7|0|1|11|-8|10|5|6|7|0|2|8|96|13|81|8|15|10|5|6|7|0|1|8|96|10|5|6|7|0|1|8|16|13|97|-318|-320|-312|-316|-308|-310|-1|-306|8|98|10|5|6|7|0|0|-1|-326|8|42|10|5|6|7|0|5|8|80|13|81|8|82|83|84|2790185984|1279900254208|192000000|8|85|13|81|8|86|83|84|2790196013|1279900254208|221000000|8|50|13|99|-1|-330|8|100|19|-22|8|101|10|5|6|7|0|0|-1|-348|8|102|19|-22|8|87|13|88|8|103|13|104|8|105|10|5|6|7|0|2|11|-8|13|106|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|107|-366|-368|-360|-364|-1|-358|8|108|13|109|8|52|13|110|8|111|10|5|6|7|0|0|-1|-378|8|15|10|5|6|7|0|2|8|103|10|5|6|7|0|1|8|16|13|104|-384|-386|8|22|10|5|6|7|0|1|8|16|13|112|-384|-392|-1|-382|8|113|10|5|6|7|0|10|8|33|13|114|8|42|10|5|6|7|0|5|8|80|13|81|8|82|83|84|2790185925|1279900254208|133000000|8|85|13|81|8|86|83|84|2790195763|1279900254208|971000000|8|50|13|115|-400|-404|8|38|13|45|8|116|10|5|6|7|0|1|11|-8|13|94|-400|-422|8|117|13|118|8|119|10|5|6|7|0|0|-400|-430|8|120|10|5|6|7|0|0|-400|-434|8|121|13|122|8|52|13|123|8|124|13|125|-1|-398|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true',
        :dyn_variables => [
          {"name" => opts[:result_com_var_name], "regexp" => opts[:result_com_var_regexp]},
          {"name" => opts[:lo_category_var_name], "regexp" => opts[:lo_category_var_regexp]},
          {"name" => opts[:joints_var_name], "regexp" => opts[:joints_var_regexp]}
        ]
      }
    )

    @request.add("DEBUG/result_com_var_name/%%_#{opts[:result_com_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/lo_category_var_name/%%_#{opts[:lo_category_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/joints_var_name/%%_#{opts[:joints_var_name]}%%", {}, {'subst' => 'true'})

    @request.add_thinktime(2)

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|2624D6B633265979660626EFB501C382|org.kuali.student.lum.lu.ui.course.client.service.LoRpcService|getLoCategoryTypes|1|2|3|4|0|"
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    #
    # Active Dates
    #
    
    # Save & Continue
    contents1 = "5|0|134|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|administeringOrgs|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|58|_runtimeData|id-translation|#{admin_org}|passFail|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|audit|finalExamStatus|STD|campusLocations|ALL|All|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|loCategoryInfoList|loDisplayInfoList|loInfo|sequence|0|desc|formatted|#{opts[:lo_cat_text]}|plain|id|%%_#{opts[:lo_category_var_name]}|loRepositoryKey|kuali.loRepository.key.singleUse|metaInfo|createId|#{propose_person}|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateId|updateTime|versionInd|name|SINGLE USE LO|type|kuali.lo.type.singleUse|courseTitle|#{title}|creditOptions|fixedCreditValue|10|kuali.creditType.credit.degree.10|resultValues|kuali.resultComponentType.credit.degree.fixed|Credits, Fixed|crossListings|curriculumOversightOrgs|descr|#{opts[:course_description]}|duration|atpDurationTypeKey|kuali.atp.duration.Semester|timeQuantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|Semester|expenditure|affiliatedOrgs|fees|formats|activities|activityType|kuali.lu.type.activity.Lab|contactHours|unitQuantity|5|unitType|kuali.atp.duration.week|per week|defaultEnrollmentEstimate|%%_#{opts[:enroll_est_var_name]}%%|1|state|draft|Lab|%%_#{opts[:lab_var_name]}%%|kuali.lu.type.CreditCourseFormatShell|gradingOptions|kuali.resultComponent.grade.letter|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|personId|(#{opts[:instructor]})|joints|4|pilotCourse|revenues|specialTopicsCourse|subjectArea|#{opts[:subject_area]}|termsOffered|kuali.atp.season.Any|Any|transcriptTitle|#{opts[:course_short_title]}|kuali.lu.type.CreditCourse|variations|Standard final Exam|dirty|startTerm|endTerm|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%|kuali.atp.FA2010-2011"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|35|8|9|10|5|6|7|0|2|11|12|0|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-12|-14|-5|-10|-1|-3|8|18|19|20|0|8|21|19|-22|8|22|13|23|8|24|10|5|6|7|0|2|11|-8|13|25|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|26|-35|-37|-29|-33|-1|-27|8|27|13|28|8|29|13|30|8|31|10|5|6|7|0|1|11|-8|10|5|6|7|0|3|8|32|10|5|6|7|0|0|-53|-55|8|33|10|5|6|7|0|0|-53|-59|8|34|10|5|6|7|0|7|8|35|13|36|8|37|10|5|6|7|0|2|8|38|13|39|8|40|13|39|-65|-69|8|41|13|42|8|43|13|44|8|45|10|5|6|7|0|5|8|46|13|47|8|48|49|50|2795477485|1279900254208|693000000|8|51|13|47|8|52|49|50|2795477485|1279900254208|693000000|8|53|13|36|-65|-81|8|54|13|55|8|56|13|57|-53|-63|-49|-51|-1|-47|8|58|13|59|8|60|10|5|6|7|0|1|11|-8|10|5|6|7|0|6|8|61|13|62|8|41|13|63|8|45|10|5|6|7|0|1|8|53|13|36|-109|-115|8|64|10|5|6|7|0|1|11|-8|13|62|-109|-121|8|56|13|65|8|15|10|5|6|7|0|1|8|56|10|5|6|7|0|1|8|16|13|66|-131|-133|-109|-129|-105|-107|-1|-103|8|67|10|5|6|7|0|0|-1|-139|8|68|10|5|6|7|0|2|11|-8|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-151|-153|-145|-149|-1|-143|8|69|10|5|6|7|0|1|8|40|13|70|-1|-159|8|71|10|5|6|7|0|3|8|72|13|73|8|74|75|12|1|8|15|10|5|6|7|0|1|8|72|10|5|6|7|0|1|8|16|13|76|-176|-178|-167|-174|-1|-165|8|77|10|5|6|7|0|1|8|78|10|5|6|7|0|0|-186|-188|-1|-184|8|79|10|5|6|7|0|0|-1|-192|8|80|10|5|6|7|0|1|11|-8|10|5|6|7|0|5|8|81|10|5|6|7|0|1|11|-8|10|5|6|7|0|9|8|82|13|83|8|84|10|5|6|7|0|3|8|85|13|86|8|87|13|88|8|15|10|5|6|7|0|1|8|87|10|5|6|7|0|1|8|16|13|89|-224|-226|-216|-222|-210|-214|8|68|10|5|6|7|0|0|-210|-232|8|90|75|12|100|8|71|10|5|6|7|0|3|8|72|13|73|8|74|75|-173|8|15|10|5|6|7|0|1|8|72|10|5|6|7|0|1|8|16|13|76|-249|-251|-241|-247|-210|-239|8|41|13|91|8|45|10|5|6|7|0|5|8|46|13|47|8|48|49|50|2795473322|1279900254208|530000000|8|51|13|47|8|52|49|50|2795477458|1279900254208|666000000|8|53|13|92|-210|-259|8|93|13|94|8|15|10|5|6|7|0|1|8|82|10|5|6|7|0|1|8|16|13|95|-279|-281|-210|-277|-206|-208|-202|-204|8|41|13|96|8|45|10|5|6|7|0|5|8|46|13|47|8|48|49|50|2795473295|1279900254208|503000000|8|51|13|47|8|52|49|50|2795477428|1279900254208|636000000|8|53|13|92|-202|-289|8|93|13|94|8|56|13|97|-198|-200|-1|-196|8|98|10|5|6|7|0|2|11|-8|13|99|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|0|-317|-319|-311|-315|-1|-309|8|41|13|100|8|101|10|5|6|7|0|1|11|-8|10|5|6|7|0|2|8|102|13|47|8|15|10|5|6|7|0|1|8|102|10|5|6|7|0|1|8|16|13|103|-339|-341|-333|-337|-329|-331|-1|-327|8|104|10|5|6|7|0|0|-1|-347|8|45|10|5|6|7|0|5|8|46|13|47|8|48|49|50|2795462840|1279900254208|48000000|8|51|13|47|8|52|49|50|2795477396|1279900254208|604000000|8|53|13|105|-1|-351|8|106|19|-22|8|107|10|5|6|7|0|0|-1|-369|8|108|19|-22|8|93|13|94|8|109|13|110|8|111|10|5|6|7|0|2|11|-8|13|112|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|113|-387|-389|-381|-385|-1|-379|8|114|13|115|8|56|13|116|8|117|10|5|6|7|0|0|-1|-399|8|15|10|5|6|7|0|3|8|109|10|5|6|7|0|1|8|16|13|110|-405|-407|8|22|10|5|6|7|0|1|8|16|13|118|-405|-413|8|119|10|5|6|7|0|2|8|120|19|20|1|8|121|19|-425|-405|-419|-1|-403|8|122|10|5|6|7|0|10|8|41|13|123|8|45|10|5|6|7|0|5|8|46|13|47|8|48|49|50|2795462800|1279900254208|8000000|8|51|13|47|8|52|49|50|2795477038|1279900254208|246000000|8|53|13|86|-430|-434|8|54|13|59|8|124|10|5|6|7|0|1|11|-8|13|100|-430|-452|8|125|13|126|8|127|10|5|6|7|0|0|-430|-460|8|128|10|5|6|7|0|0|-430|-464|8|129|13|130|8|56|13|131|8|132|13|133|-1|-428|-423|13|134|-426|13|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add_thinktime(2)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    
    # Submit to worflow
    if(opts[:submit])
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|submitDocumentWithId|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {
          'subst' => 'true'
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {
          'subst' => 'true'
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {
          'subst' => 'true'
        }
      )
    end
 
    
  end
  
  
  
  def scratch
    
    
    
     


     


     
    
    
    
    
    
    
    

    
    
    
    
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/common/lightbox_close.png')
    @request.add_thinktime(10)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|28|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|title|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|#{title}|_runtimeData|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|rationale|proposerPerson|#{opts[:course_rationale]}|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|#{propose_person}|course|courseTitle|java.util.ArrayList/3821976829|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|4|10|13|14|15|10|16|12|8|5|9|0|1|10|17|12|8|5|9|0|3|-9|18|19|1|10|20|18|-20|10|21|18|-20|-13|-15|-7|-11|-21|14|22|-23|12|8|5|9|0|1|23|24|0|14|25|-7|-23|-3|-5|10|26|12|8|5|9|0|1|10|27|14|15|-3|-32|0|0|28|0|5|9|0|0|0|0|"
      },
      {
        :dyn_variables => [
          {"name" => opts[:proposal_dyn_var_name], "regexp" => opts[:proposal_dyn_var_regexp]},
          {"name" => opts[:clu_ref_dyn_var_name], "regexp" => opts[:clu_ref_dyn_var_regexp]}
        ]
      }
    )
    
    #@request.add("/fake_url/clu_ref_id/%%_#{opts[:clu_ref_dyn_var_name]}%%", {}, {'subst' => 'true'})
    #@request.add("/fake_url/proposal_id/%%_#{opts[:proposal_dyn_var_name]}%%", {}, {'subst' => 'true'})
    
    @request.add_thinktime(2)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true',
        :dyn_variables => [{"name" => opts[:proposal_doc_id_var_name], "regexp" => opts[:proposal_doc_id_var_regexp]}]
      }
    )
    
    #@request.add("/fake_url/proposal_doc_id/%%_#{opts[:proposal_doc_id_var_name]}%%", {}, {'subst' => 'true'})
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )

    @request.add_thinktime(5)
    
    # Save & Continue
    
    contents1 = "5|0|70|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.util.Date/1659716317|updateTime|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|courseTitle|effectiveDate|expirationDate|department|duration|termType|termsOffered|firstExpectedOffering||primaryInstructor|state|draft|subjectArea|transcriptTitle|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|fees|org.kuali.student.lum.lu.dto.CluInfo|id-translation|Credit Course|courseCode|nullnull|gradingOptions|outcomeOptions|courseSpecificLOs|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|8|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|10|18|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-22|10|27|12|8|5|9|0|4|10|28|14|29|10|30|14|29|10|31|32|33|1033351408|1275605286912|10|34|32|33|1033351408|1275605286912|-7|-29|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|38|10|13|14|15|10|39|14|40|-49|-51|-45|-47|-7|-43|-3|-5|10|41|12|8|5|9|0|22|10|13|14|26|10|42|14|0|10|43|14|20|10|44|32|0|10|45|32|0|10|46|14|0|10|47|12|8|5|9|0|1|10|48|14|0|-63|-77|10|49|12|8|5|9|0|0|-63|-83|10|50|14|51|10|52|14|0|10|53|14|54|10|55|14|0|10|56|14|0|10|57|14|58|10|59|12|8|5|9|0|0|-63|-99|10|60|12|8|5|9|0|0|-63|-103|10|61|12|8|5|9|0|0|-63|-107|10|35|12|8|5|9|0|2|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|62|10|13|14|26|10|39|14|40|-117|-119|-113|-115|10|57|12|8|5|9|0|1|10|63|14|64|-113|-129|-63|-111|10|65|14|66|10|67|12|8|5|9|0|0|-63|-137|10|68|12|8|5|9|0|0|-63|-141|10|69|12|8|5|9|0|0|-63|-145|-3|-61|0|0|70|0|5|9|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add_thinktime(1)
    
    
    # Loading Pg 2 - Course Information
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    #
    # Pg 2 - Course Information
    #
    
    # Course Subject Area
    # AJAX popup while typing in subject area
    for i in 1..opts[:subject_area].length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationOptionalCode|#{opts[:subject_area][0..itr]}|enumeration.queryParam.enumerationType|kuali.lu.subjectArea|enumeration.management.search|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
        }                             
      )        
    end
    
    # Save & Continue
    
    contents1 = "5|0|80|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateTime|java.util.Date/1659716317|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|effectiveDate|expirationDate|department|duration|termType|termsOffered|firstExpectedOffering||primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|fees|org.kuali.student.lum.lu.dto.CluInfo|id-translation|Credit Course|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|courseCode|nullnull|gradingOptions|outcomeOptions|courseSpecificLOs|description|plain|#{opts[:course_description]}|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|8|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|10|18|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-22|10|27|12|8|5|9|0|4|10|28|14|29|10|30|14|29|10|31|32|33|1033351408|1275605286912|320000000|10|34|32|35|1105114111|1275605286912|-7|-29|10|36|12|8|5|9|0|1|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|39|10|13|14|15|10|40|14|41|-49|-51|-45|-47|-7|-43|-3|-5|10|42|12|8|5|9|0|23|10|13|14|26|10|43|14|44|10|45|14|20|10|46|32|0|10|47|32|0|10|48|14|0|10|49|12|8|5|9|0|1|10|50|14|0|-63|-77|10|51|12|8|5|9|0|0|-63|-83|10|52|14|53|10|54|14|0|10|55|14|56|10|57|14|58|10|59|14|60|10|61|14|62|10|63|12|8|5|9|0|0|-63|-99|10|64|12|8|5|9|0|0|-63|-103|10|65|12|8|5|9|0|0|-63|-107|10|36|12|8|5|9|0|3|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|66|10|13|14|26|10|40|14|41|-117|-119|-113|-115|10|61|12|8|5|9|0|1|10|67|14|68|-113|-129|10|69|12|8|5|9|0|3|10|57|70|71|1|10|43|70|-141|10|59|70|-141|-113|-135|-63|-111|10|72|14|73|10|74|12|8|5|9|0|0|-63|-148|10|75|12|8|5|9|0|0|-63|-152|10|76|12|8|5|9|0|0|-63|-156|10|77|12|8|5|9|0|2|10|78|14|79|10|36|12|8|5|9|0|1|-135|12|8|5|9|0|1|-164|70|-141|-168|-135|-162|-166|-63|-160|-3|-61|0|0|80|0|5|9|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add_thinktime(1)
    
    # Loading Pg3 - Course Logistics
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.type|java.lang.String/2004016611|kuali.resultComponentType.credit.degree.fixed|kuali.resultComponentType.credit.degree.range|lrc.search.resultComponent|1|2|3|4|1|5|6|0|0|7|1|8|9|7|2|10|11|10|12|0|13|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    #
    # Pg 3 - Course Logistics
    #
    
    # Instructor
    for i in 1..opts[:instructor].length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|12|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|person.queryParam.personGivenName|#{opts[:instructor][0..itr]}|person.search.personQuickViewByGivenName|1|2|3|4|1|5|6|0|7|0|8|1|9|10|0|11|12|0|0|0|"
        }                             
      )        
    end
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|atp.search.atpDurationTypes|1|2|3|4|1|5|6|0|0|7|0|8|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.atptype.duration|enumeration.management.search|1|2|3|4|1|5|6|0|0|7|1|8|9|0|10|11|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lu.queryParam.luOptionalLuTypeStartsWith|kuali.lu.type.activity.|lu.search.all.lu.Types|1|2|3|4|1|5|6|0|0|7|1|8|9|0|10|11|0|0|0|"
      }
    )
    
    @request.add_thinktime(10)
    
    
    # Save & Continue
    
    contents1 = "5|0|97|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateTime|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|effectiveDate|expirationDate|department|description|formatted|plain|#{opts[:course_description]}|termsOffered|firstExpectedOffering||primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|fees|org.kuali.student.lum.lu.dto.CluInfo|id-translation|Credit Course|dirty|gradingOptions|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|courseCode|ARTS123|kuali.resultComponent.grade.letter|outcomeOptions|outcomeType|created|kuali.creditType.credit.degree.1|courseSpecificLOs|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|kuali.atp.duration.Period|formats|activities|activityType|defaultEnrollmentEstimate|kuali.lu.type.activity.Discussion|timeUnit|contactHours|per|hrs|kuali.atp.duration.day|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|8|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|10|18|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-22|10|27|12|8|5|9|0|4|10|28|14|29|10|30|14|29|10|31|32|33|1033351408|1275605286912|320000000|10|34|32|33|1105318058|1275605286912|970000000|-7|-29|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|38|10|13|14|15|10|39|14|40|-49|-51|-45|-47|-7|-43|-3|-5|10|41|12|8|5|9|0|24|10|13|14|26|10|42|14|43|10|44|14|20|10|45|32|0|10|46|32|0|10|47|14|0|10|48|12|8|5|9|0|2|10|49|14|0|10|50|14|51|-63|-77|10|52|12|8|5|9|0|0|-63|-85|10|53|14|54|10|55|14|29|10|56|14|57|10|58|14|59|10|60|14|61|10|62|14|63|10|64|12|8|5|9|0|0|-63|-101|10|65|12|8|5|9|0|0|-63|-105|10|66|12|8|5|9|0|0|-63|-109|10|35|12|8|5|9|0|4|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|67|10|13|14|26|10|39|14|40|-119|-121|-115|-117|10|62|12|8|5|9|0|1|10|68|14|69|-115|-131|10|58|12|8|5|9|0|1|10|68|14|59|-115|-137|10|70|12|8|5|9|0|2|10|71|72|73|1|10|55|72|-149|-115|-143|-63|-113|10|74|14|75|10|71|12|8|5|9|0|1|24|25|0|14|76|-63|10|71|10|77|12|8|5|9|0|1|24|25|0|12|8|5|9|0|2|10|35|12|8|5|9|0|3|-143|12|8|5|9|0|1|10|78|72|-149|-173|-143|10|79|72|-149|-171|12|8|5|9|0|1|-143|12|8|5|9|0|1|-180|72|-149|-183|-143|-173|-171|-169|-171|-178|14|80|-164|-166|-63|-162|10|81|12|8|5|9|0|0|-63|-190|10|82|12|8|5|9|0|3|10|83|84|25|2|-171|12|8|5|9|0|1|-143|12|8|5|9|0|2|-198|72|-149|10|85|72|-149|-202|-143|-196|-171|-208|14|86|-63|-194|10|87|12|8|5|9|0|1|24|25|0|12|8|5|9|0|2|10|88|12|8|5|9|0|1|24|25|0|12|8|5|9|0|5|-171|12|8|5|9|0|3|-143|12|8|5|9|0|2|10|89|72|-149|10|90|72|-149|-230|-143|10|79|72|-149|-171|12|8|5|9|0|1|-143|12|8|5|9|0|1|-239|72|-149|-242|-143|-230|-171|-227|-171|-235|14|91|10|82|12|8|5|9|0|3|10|83|84|25|1|-171|12|8|5|9|0|1|-143|12|8|5|9|0|2|-253|72|-149|10|92|72|-149|-257|-143|-251|-171|-263|14|86|-227|-249|10|93|12|8|5|9|0|3|-171|12|8|5|9|0|1|-143|12|8|5|9|0|2|10|94|72|-149|10|95|72|-149|-271|-143|-268|-171|-276|14|96|-278|84|-255|-227|-266|-237|84|25|100|-222|-224|-218|-220|10|35|12|8|5|9|0|2|10|79|72|-149|-171|12|8|5|9|0|1|-143|12|8|5|9|0|1|-288|72|-149|-291|-143|-286|-171|-218|-284|-213|-215|-63|-211|-3|-61|0|0|97|0|5|9|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true',
        :dyn_variables => [
          {"name" => opts[:lu_dto_clu_format_dyn_var_name], "regexp" => opts[:lu_dto_clu_format_dyn_var_regexp]},
          {"name" => opts[:lu_dto_clu_activities_dyn_var_name], "regexp" => opts[:lu_dto_clu_activities_dyn_var_regexp]},
          {"name" => opts[:outcome_id_var_name], "regexp" => opts[:outcome_id_var_regexp]}
        ]
      }
    )
    
    @request.add_thinktime(1)
    
    # Loading Pg4 - Learning Objectives
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.type|java.lang.String/2004016611|kuali.resultComponentType.credit.degree.fixed|kuali.resultComponentType.credit.degree.range|lrc.search.resultComponent|1|2|3|4|1|5|6|0|0|7|1|8|9|7|2|10|11|10|12|0|13|0|0|0|"
      }
    )
    # Dupe of above request
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.type|java.lang.String/2004016611|kuali.resultComponentType.credit.degree.fixed|kuali.resultComponentType.credit.degree.range|lrc.search.resultComponent|1|2|3|4|1|5|6|0|0|7|1|8|9|7|2|10|11|10|12|0|13|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.atptype.duration|enumeration.management.search|1|2|3|4|1|5|6|0|0|7|1|8|9|0|10|11|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|atp.search.atpDurationTypes|1|2|3|4|1|5|6|0|0|7|0|8|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lu.queryParam.luOptionalLuTypeStartsWith|kuali.lu.type.activity.|lu.search.all.lu.Types|1|2|3|4|1|5|6|0|0|7|1|8|9|0|10|11|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.atptype.duration|enumeration.management.search|1|2|3|4|1|5|6|0|0|7|1|8|9|0|10|11|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|atp.search.atpDurationTypes|1|2|3|4|1|5|6|0|0|7|0|8|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lu.queryParam.luOptionalLuTypeStartsWith|kuali.lu.type.activity.|lu.search.all.lu.Types|1|2|3|4|1|5|6|0|0|7|1|8|9|0|10|11|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    #
    # Pg 4 - Learning Objectives
    #
    
    #@request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/bg_header_gradient.gif') #404
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|0EB70C49567108E978910E869F1F8153|org.kuali.student.lum.lu.ui.course.client.service.LoRpcService|getLoCategoryTypes|1|2|3|4|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|0EB70C49567108E978910E869F1F8153|org.kuali.student.lum.lu.ui.course.client.service.LoRpcService|getLoCategories|java.lang.String|kuali.loRepository.key.singleUse|1|2|3|4|1|5|6|"
      },
      {
        :dyn_variables => [{"name" => opts[:lo_category_var_name], "regexp" => opts[:lo_category_var_regexp]}]
      }
    )
    
    @request.add_thinktime(3)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|0EB70C49567108E978910E869F1F8153|org.kuali.student.lum.lu.ui.course.client.service.LoRpcService|getLoCategoryType|java.lang.String|loCategoryType.subject|1|2|3|4|1|5|6|"
      }
    )
    
    # Save & Continue
    contents1 = "5|0|116|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateTime|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|effectiveDate|expirationDate|department|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|kuali.atp.duration.Period|id-translation|Period|termsOffered|firstExpectedOffering||primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|fees|org.kuali.student.lum.lu.dto.CluInfo|Credit Course|(#{propose_person})|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|0|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Discussion|contactHours|hrs|per|kuali.atp.duration.day|per day|timeUnit|defaultEnrollmentEstimate|Discussion|courseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|gradingOptions|kuali.resultComponent.grade.letter|Letter|outcomeOptions|outcomeId|%%_#{opts[:outcome_id_var_name]}%%|outcomeType|kuali.creditType.credit.degree.1|1 Credit|courseSpecificLOs|sequence|includedSingleUseLo|#{opts[:lo_name]}|name|SINGLE USE LO|categories|%%_#{opts[:lo_category_var_name]}%%|#{opts[:lo_category]}|desc|loRepository|active|loCategoryType.subject|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|8|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|10|18|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-22|10|27|12|8|5|9|0|4|10|28|14|29|10|30|14|29|10|31|32|33|1300453499|1275605286912|411000000|10|34|32|33|1300520570|1275605286912|482000000|-7|-29|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|38|10|13|14|15|10|39|14|40|-49|-51|-45|-47|-7|-43|-3|-5|10|41|12|8|5|9|0|24|10|13|14|26|10|42|14|43|10|44|14|20|10|45|32|0|10|46|32|0|10|47|14|0|10|48|12|8|5|9|0|2|10|49|14|0|10|50|14|51|-63|-77|10|52|12|8|5|9|0|3|10|53|54|25|2|10|55|14|56|10|35|12|8|5|9|0|1|10|55|12|8|5|9|0|1|10|57|14|58|-96|-98|-87|-94|-63|-85|10|59|12|8|5|9|0|0|-63|-104|10|60|14|61|10|62|14|29|10|63|14|64|10|65|14|66|10|67|14|68|10|69|14|70|10|71|12|8|5|9|0|0|-63|-120|10|72|12|8|5|9|0|0|-63|-124|10|73|12|8|5|9|0|0|-63|-128|10|35|12|8|5|9|0|4|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|74|10|13|14|26|10|39|14|40|-138|-140|-134|-136|10|69|12|8|5|9|0|1|10|57|14|75|-134|-150|10|62|12|8|5|9|0|1|10|57|14|76|-134|-156|10|65|12|8|5|9|0|1|10|57|14|66|-134|-162|-63|-132|10|77|12|8|5|9|0|1|24|-27|12|8|5|9|0|4|10|13|14|78|10|63|14|64|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|74|10|13|14|78|10|39|14|79|-186|-188|-182|-184|-174|-180|10|80|12|8|5|9|0|1|24|-27|12|8|5|9|0|7|10|13|14|81|10|82|14|83|10|84|12|8|5|9|0|3|10|85|54|25|1|10|86|14|87|10|35|12|8|5|9|0|1|10|86|12|8|5|9|0|1|10|57|14|88|-221|-223|-212|-219|-204|-210|10|52|12|8|5|9|0|3|10|53|54|25|1|10|89|14|56|10|35|12|8|5|9|0|1|10|89|12|8|5|9|0|1|10|57|14|58|-240|-242|-231|-238|-204|-229|10|90|54|25|100|10|63|14|64|10|35|12|8|5|9|0|2|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|74|10|13|14|81|10|39|14|79|-259|-261|-255|-257|10|82|12|8|5|9|0|1|10|57|14|91|-255|-271|-204|-253|-200|-202|-174|-198|-170|-172|-63|-168|10|92|14|93|10|94|12|8|5|9|0|2|24|-27|14|95|10|35|12|8|5|9|0|1|24|-27|12|8|5|9|0|1|10|57|14|96|-287|-289|-281|-285|-63|-279|10|97|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|98|14|99|10|100|14|101|10|35|12|8|5|9|0|1|10|100|12|8|5|9|0|1|10|57|14|102|-309|-311|-301|-307|-297|-299|-63|-295|10|103|12|8|5|9|0|1|24|25|0|12|8|5|9|0|5|10|13|14|0|10|45|32|0|10|63|14|0|10|104|14|79|10|105|12|8|5|9|0|4|10|13|14|0|10|48|12|8|5|9|0|2|10|49|14|106|10|50|14|106|-336|-340|10|107|14|108|10|109|12|8|5|9|0|1|24|-322|12|8|5|9|0|8|10|13|14|110|10|107|14|111|10|112|12|8|5|9|0|0|-356|-362|10|113|14|0|10|45|32|0|10|46|32|0|10|63|14|114|10|69|14|115|-352|-354|-336|-350|-324|-334|-319|-321|-63|10|103|-3|-61|0|0|116|0|5|9|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true',
        :dyn_variables => [
          {"name" => opts[:single_use_lo_dyn_var_name], "regexp" => opts[:single_use_lo_dyn_var_regexp]}
        ]
      }
    )
    
    @request.add_thinktime(3)
    
    
    # Loading Pg 5 - Course Requisites
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|0EB70C49567108E978910E869F1F8153|org.kuali.student.lum.lu.ui.course.client.service.LoRpcService|getLoCategoryTypes|1|2|3|4|0|"
      }
    )
    # Dupe
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|0EB70C49567108E978910E869F1F8153|org.kuali.student.lum.lu.ui.course.client.service.LoRpcService|getLoCategoryTypes|1|2|3|4|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    # Save & Continue
    contents1 = "5|0|129|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|java.lang.String/2004016611|kuali.luStatementType.prereqAcademicReadiness.rationale||kuali.luStatementType.coreqAcademicReadiness.rationale|kuali.luStatementType.antireqAcademicReadiness.rationale|kuali.luStatementType.enrollAcademicReadiness.rationale|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateTime|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|effectiveDate|expirationDate|department|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|kuali.atp.duration.Period|id-translation|Period|termsOffered|firstExpectedOffering|primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|fees|org.kuali.student.lum.lu.dto.CluInfo|Credit Course|(#{propose_person})|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|0|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Discussion|contactHours|hrs|per|kuali.atp.duration.day|per day|timeUnit|defaultEnrollmentEstimate|Discussion|courseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|gradingOptions|kuali.resultComponent.grade.letter|Letter|outcomeOptions|outcomeId|%%_#{opts[:outcome_id_var_name]}%%|outcomeType|kuali.creditType.credit.degree.1|1 Credit|courseSpecificLOs|sequence|kuali.lu.lo.relation.type.includes|includedSingleUseLo|%%_#{opts[:single_use_lo_dyn_var_name]}%%|#{opts[:lo_name]}|loRepository|kuali.loRepository.key.singleUse|active|kuali.lo.type.singleUse|name|SINGLE USE LO|categories|%%_#{opts[:lo_category_var_name]}%%|#{opts[:lo_category]}|desc|&lt;p&gt;Desc&lt;/p&gt;|Desc|attributes|loCategoryType.subject|childSingleUseLos|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|4|8|9|8|10|8|11|-4|8|12|-4|8|13|-4|14|5|15|0|2|16|17|18|14|5|15|0|8|16|19|20|21|16|22|20|23|16|24|18|14|5|15|0|0|-12|16|24|16|25|20|26|16|27|20|28|16|29|18|14|5|15|0|1|30|31|0|20|32|-12|-27|16|33|18|14|5|15|0|4|16|34|20|35|16|36|20|35|16|37|38|39|1545584322|1275605286912|234000000|16|40|38|39|1545672857|1275605286912|769000000|-12|-34|16|41|18|14|5|15|0|1|16|42|18|14|5|15|0|1|30|-32|18|14|5|15|0|3|16|43|20|44|16|19|20|21|16|45|20|46|-54|-56|-50|-52|-12|-48|-8|-10|16|47|18|14|5|15|0|24|16|19|20|32|16|48|20|49|16|50|20|26|16|51|38|0|16|52|38|0|16|53|20|0|16|54|18|14|5|15|0|2|16|55|20|0|16|56|20|57|-68|-82|16|58|18|14|5|15|0|3|16|59|60|31|2|16|61|20|62|16|41|18|14|5|15|0|1|16|61|18|14|5|15|0|1|16|63|20|64|-101|-103|-92|-99|-68|-90|16|65|18|14|5|15|0|0|-68|-109|16|66|20|10|16|67|20|35|16|68|20|69|16|70|20|71|16|72|20|73|16|74|20|75|16|76|18|14|5|15|0|0|-68|-125|16|77|18|14|5|15|0|0|-68|-129|16|78|18|14|5|15|0|0|-68|-133|16|41|18|14|5|15|0|4|16|42|18|14|5|15|0|1|30|-32|18|14|5|15|0|3|16|43|20|79|16|19|20|32|16|45|20|46|-143|-145|-139|-141|16|74|18|14|5|15|0|1|16|63|20|80|-139|-155|16|67|18|14|5|15|0|1|16|63|20|81|-139|-161|16|70|18|14|5|15|0|1|16|63|20|71|-139|-167|-68|-137|16|82|18|14|5|15|0|1|30|-32|18|14|5|15|0|4|16|19|20|83|16|68|20|69|16|41|18|14|5|15|0|1|16|42|18|14|5|15|0|1|30|-32|18|14|5|15|0|3|16|43|20|79|16|19|20|83|16|45|20|84|-191|-193|-187|-189|-179|-185|16|85|18|14|5|15|0|1|30|-32|18|14|5|15|0|7|16|19|20|86|16|87|20|88|16|89|18|14|5|15|0|3|16|90|60|31|1|16|91|20|92|16|41|18|14|5|15|0|1|16|91|18|14|5|15|0|1|16|63|20|93|-226|-228|-217|-224|-209|-215|16|58|18|14|5|15|0|3|16|59|60|31|1|16|94|20|62|16|41|18|14|5|15|0|1|16|94|18|14|5|15|0|1|16|63|20|64|-245|-247|-236|-243|-209|-234|16|95|60|31|100|16|68|20|69|16|41|18|14|5|15|0|2|16|42|18|14|5|15|0|1|30|-32|18|14|5|15|0|3|16|43|20|79|16|19|20|86|16|45|20|84|-264|-266|-260|-262|16|87|18|14|5|15|0|1|16|63|20|96|-260|-276|-209|-258|-205|-207|-179|-203|-175|-177|-68|-173|16|97|20|98|16|99|18|14|5|15|0|2|30|-32|20|100|16|41|18|14|5|15|0|1|30|-32|18|14|5|15|0|1|16|63|20|101|-292|-294|-286|-290|-68|-284|16|102|18|14|5|15|0|1|30|-32|18|14|5|15|0|3|16|103|20|104|16|105|20|106|16|41|18|14|5|15|0|1|16|105|18|14|5|15|0|1|16|63|20|107|-314|-316|-306|-312|-302|-304|-68|-300|16|108|18|14|5|15|0|1|30|-32|18|14|5|15|0|6|16|109|20|84|16|51|38|39|1545672655|1275605286912|567000000|16|19|20|0|16|68|20|69|16|74|20|110|16|111|18|14|5|15|0|9|16|19|20|112|16|54|18|14|5|15|0|2|16|55|20|113|16|56|20|113|-343|-347|16|114|20|115|16|51|38|-334|16|68|20|116|16|74|20|117|16|118|20|119|16|120|18|14|5|15|0|1|30|-32|18|14|5|15|0|9|16|19|20|121|16|118|20|122|16|123|18|14|5|15|0|2|16|55|20|124|16|56|20|125|-371|-377|16|114|20|115|16|51|38|39|867724416|1198295875584|0|16|52|38|39|3896582272|1258425417728|0|16|126|18|14|5|15|0|0|-371|-393|16|68|20|116|16|74|20|127|-367|-369|-343|-365|16|128|18|14|5|15|0|0|-343|-401|-328|-341|-324|-326|-68|-322|-8|-66|0|0|129|0|5|15|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add_thinktime(3)
    
    
    # Loading Pg 6 - Governance
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    
    #
    # Pg 6 - Governance
    #
    
    # COC Org
    for i in 1..oversight_department.length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|16|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{oversight_department[0..itr]}|org.queryParam.orgOptionalType|java.lang.String/2004016611|kuali.org.Department|kuali.org.College|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|8|2|13|14|13|15|0|16|0|0|0|"
        }                             
      )        
    end
    
    # Admin Org
    for i in 1..admin_org.length
      itr = i-1
      if(itr == admin_org.length-1)
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|16|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{admin_org[0..itr]}|org.queryParam.orgOptionalType|java.lang.String/2004016611|kuali.org.Department|kuali.org.College|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|8|2|13|14|13|15|0|16|0|0|0|"
          },
          {
            :dyn_variables => [{"name" => opts[:admin_dep_var_name], "regexp" => opts[:admin_dep_var_regexp]}]
          }     
        )
      else
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|16|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{admin_org[0..itr]}|org.queryParam.orgOptionalType|java.lang.String/2004016611|kuali.org.Department|kuali.org.College|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|8|2|13|14|13|15|0|16|0|0|0|"
          }    
        )
      end     
    end
    
    @request.add_thinktime(3)
    
    # Save & Continue
    # admin org ID: b4d99575-97a3-491a-aed2-8faafeb6340b
    contents1 = "5|0|130|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateTime|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|effectiveDate|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|kuali.atp.duration.Period|id-translation|Period|termsOffered|firstExpectedOffering||primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|NORTH|SOUTH|fees|org.kuali.student.lum.lu.dto.CluInfo|Credit Course|(#{propose_person})|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|0|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Discussion|contactHours|hrs|per|kuali.atp.duration.day|per day|timeUnit|defaultEnrollmentEstimate|Discussion|courseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|gradingOptions|kuali.resultComponent.grade.letter|Letter|outcomeOptions|outcomeId|%%_#{opts[:outcome_id_var_name]}%%|outcomeType|kuali.creditType.credit.degree.1|1 Credit|courseSpecificLOs|sequence|kuali.lu.lo.relation.type.includes|includedSingleUseLo|%%_#{opts[:single_use_lo_dyn_var_name]}%%|#{opts[:lo_name]}|loRepository|kuali.loRepository.key.singleUse|active|kuali.lo.type.singleUse|name|SINGLE USE LO|categories|%%_#{opts[:lo_category_var_name]}%%|#{opts[:lo_category]}|desc|" + '&lt;p&gt;Desc&lt;/p&gt;' + "|Desc|attributes|loCategoryType.subject|childSingleUseLos|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|8|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|10|18|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-22|10|27|12|8|5|9|0|4|10|28|14|29|10|30|14|29|10|31|32|33|1630076629|1275605286912|541000000|10|34|32|33|1630166764|1275605286912|676000000|-7|-29|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|38|10|13|14|15|10|39|14|40|-49|-51|-45|-47|-7|-43|-3|-5|10|41|12|8|5|9|0|24|10|13|14|26|10|42|14|43|10|44|14|20|10|45|32|0|10|46|32|0|10|47|14|48|10|49|12|8|5|9|0|2|10|50|14|0|10|51|14|52|-63|-77|10|53|12|8|5|9|0|3|10|54|55|25|2|10|56|14|57|10|35|12|8|5|9|0|1|10|56|12|8|5|9|0|1|10|58|14|59|-96|-98|-87|-94|-63|-85|10|60|12|8|5|9|0|0|-63|-104|10|61|14|62|10|63|14|29|10|64|14|65|10|66|14|67|10|68|14|69|10|70|14|71|10|72|12|8|5|9|0|1|10|35|12|8|5|9|0|0|-122|-124|-63|10|72|10|73|12|8|5|9|0|2|24|25|0|14|74|24|25|1|14|75|-63|10|73|10|76|12|8|5|9|0|0|-63|-140|10|35|12|8|5|9|0|5|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|77|10|13|14|26|10|39|14|40|-150|-152|-146|-148|10|70|12|8|5|9|0|1|10|58|14|78|-146|-162|10|63|12|8|5|9|0|1|10|58|14|79|-146|-168|10|66|12|8|5|9|0|1|10|58|14|67|-146|-174|10|80|12|8|5|9|0|3|10|73|81|82|1|10|47|81|-186|-128|81|-186|-146|-180|-63|-144|10|83|12|8|5|9|0|1|24|-27|12|8|5|9|0|4|10|13|14|84|10|64|14|65|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|77|10|13|14|84|10|39|14|85|-208|-210|-204|-206|-196|-202|10|86|12|8|5|9|0|1|24|-27|12|8|5|9|0|7|10|13|14|87|10|88|14|89|10|90|12|8|5|9|0|3|10|91|55|25|1|10|92|14|93|10|35|12|8|5|9|0|1|10|92|12|8|5|9|0|1|10|58|14|94|-243|-245|-234|-241|-226|-232|10|53|12|8|5|9|0|3|10|54|55|25|1|10|95|14|57|10|35|12|8|5|9|0|1|10|95|12|8|5|9|0|1|10|58|14|59|-262|-264|-253|-260|-226|-251|10|96|55|25|100|10|64|14|65|10|35|12|8|5|9|0|2|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|77|10|13|14|87|10|39|14|85|-281|-283|-277|-279|10|88|12|8|5|9|0|1|10|58|14|97|-277|-293|-226|-275|-222|-224|-196|-220|-192|-194|-63|-190|10|98|14|99|10|100|12|8|5|9|0|2|24|-27|14|101|10|35|12|8|5|9|0|1|24|-27|12|8|5|9|0|1|10|58|14|102|-309|-311|-303|-307|-63|-301|10|103|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|104|14|105|10|106|14|107|10|35|12|8|5|9|0|1|10|106|12|8|5|9|0|1|10|58|14|108|-331|-333|-323|-329|-319|-321|-63|-317|10|109|12|8|5|9|0|1|24|-27|12|8|5|9|0|6|10|110|14|85|10|45|32|33|1630160179|1275605286912|91000000|10|13|14|0|10|64|14|65|10|70|14|11|10|112|12|8|5|9|0|9|10|13|14|113|10|49|12|8|5|9|0|2|10|50|14|114|10|51|14|114|-360|-364|10|115|14|116|10|45|32|-351|10|64|14|117|10|70|14|118|10|119|14|120|10|121|12|8|5|9|0|1|24|-27|12|8|5|9|0|9|10|13|14|122|10|119|14|123|10|124|12|8|5|9|0|2|10|50|14|125|10|51|14|126|-388|-394|10|115|14|116|10|45|32|33|867724416|1198295875584|0|10|46|32|33|3896582272|1258425417728|0|10|127|12|8|5|9|0|0|-388|-410|10|64|14|117|10|70|14|128|-384|-386|-360|-382|10|129|12|8|5|9|0|0|-360|-418|-345|-358|-341|-343|-63|-339|-3|-61|0|0|130|0|5|9|0|0|0|0|"
                
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add_thinktime(3)
    
    # Loading Pg 7 - Active Dates
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    #
    # Pg 7 - Active Dates
    #
    
    # Time to fill out form
    @request.add_thinktime(5)
    
    # Save & Continue
    # admin org ID: b4d99575-97a3-491a-aed2-8faafeb6340b
    contents1 = "5|0|135|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateTime|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|effectiveDate|java.util.Date/1659716317|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|kuali.atp.duration.Period|id-translation|Period|termsOffered|firstExpectedOffering|kuali.atp.FA2010-2011|primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|NORTH|SOUTH|North Campus|South Campus|fees|org.kuali.student.lum.lu.dto.CluInfo|7|#{admin_org}|Credit Course|(#{propose_person})|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|0|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Discussion|contactHours|hrs|per|kuali.atp.duration.day|per day|timeUnit|defaultEnrollmentEstimate|Discussion|courseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|gradingOptions|kuali.resultComponent.grade.letter|Letter|outcomeOptions|outcomeId|%%_#{opts[:outcome_id_var_name]}%%|outcomeType|kuali.creditType.credit.degree.1|1 Credit|courseSpecificLOs|sequence|kuali.lu.lo.relation.type.includes|includedSingleUseLo|%%_#{opts[:single_use_lo_dyn_var_name]}%%|#{opts[:lo_name]}|loRepository|kuali.loRepository.key.singleUse|active|kuali.lo.type.singleUse|name|SINGLE USE LO|categories|%%_#{opts[:lo_category_var_name]}%%|#{opts[:lo_category]}|desc|&lt;p&gt;Desc&lt;/p&gt;|Desc|attributes|loCategoryType.subject|childSingleUseLos|java.util.ArrayList/3821976829"                                        ""
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|8|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|10|18|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-22|10|27|12|8|5|9|0|4|10|28|14|29|10|30|14|29|10|31|32|33|1631977501|1275605286912|413000000|10|34|32|33|1632071069|1275605286912|981000000|-7|-29|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|38|10|13|14|15|10|39|14|40|-49|-51|-45|-47|-7|-43|-3|-5|10|41|12|8|5|9|0|24|10|13|14|26|10|42|14|43|10|44|14|20|10|45|32|46|1584713088|1275605286912|10|47|32|46|3010734464|1357209665536|10|48|14|49|10|50|12|8|5|9|0|2|10|51|14|0|10|52|14|53|-63|-79|10|54|12|8|5|9|0|3|10|55|56|25|2|10|57|14|58|10|35|12|8|5|9|0|1|10|57|12|8|5|9|0|1|10|59|14|60|-98|-100|-89|-96|-63|-87|10|61|12|8|5|9|0|0|-63|-106|10|62|14|63|10|64|14|29|10|65|14|66|10|67|14|68|10|69|14|70|10|71|14|72|10|73|12|8|5|9|0|0|-63|-122|10|74|12|8|5|9|0|3|24|-27|14|75|24|25|1|14|76|10|35|12|8|5|9|0|2|24|-27|12|8|5|9|0|1|10|59|14|77|-137|-139|24|-133|12|8|5|9|0|1|10|59|14|78|-137|-145|-128|-135|-63|-126|10|79|12|8|5|9|0|0|-63|-151|10|35|12|8|5|9|0|6|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|80|10|13|14|26|10|39|14|81|-161|-163|-157|-159|10|48|12|8|5|9|0|1|10|59|14|82|-157|-173|10|71|12|8|5|9|0|1|10|59|14|83|-157|-179|10|64|12|8|5|9|0|1|10|59|14|84|-157|-185|10|67|12|8|5|9|0|1|10|59|14|68|-157|-191|10|85|12|8|5|9|0|3|10|62|86|87|1|10|45|86|-203|10|47|86|-203|-157|-197|-63|-155|10|88|12|8|5|9|0|1|24|-27|12|8|5|9|0|4|10|13|14|89|10|65|14|66|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|80|10|13|14|89|10|39|14|90|-226|-228|-222|-224|-214|-220|10|91|12|8|5|9|0|1|24|-27|12|8|5|9|0|7|10|13|14|92|10|93|14|94|10|95|12|8|5|9|0|3|10|96|56|-133|10|97|14|98|10|35|12|8|5|9|0|1|10|97|12|8|5|9|0|1|10|59|14|99|-260|-262|-252|-258|-244|-250|10|54|12|8|5|9|0|3|10|55|56|25|1|10|100|14|58|10|35|12|8|5|9|0|1|10|100|12|8|5|9|0|1|10|59|14|60|-279|-281|-270|-277|-244|-268|10|101|56|25|100|10|65|14|66|10|35|12|8|5|9|0|2|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|80|10|13|14|92|10|39|14|90|-298|-300|-294|-296|10|93|12|8|5|9|0|1|10|59|14|102|-294|-310|-244|-292|-240|-242|-214|-238|-210|-212|-63|-208|10|103|14|104|10|105|12|8|5|9|0|2|24|-27|14|106|10|35|12|8|5|9|0|1|24|-27|12|8|5|9|0|1|10|59|14|107|-326|-328|-320|-324|-63|-318|10|108|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|109|14|110|10|111|14|112|10|35|12|8|5|9|0|1|10|111|12|8|5|9|0|1|10|59|14|113|-348|-350|-340|-346|-336|-338|-63|-334|10|114|12|8|5|9|0|1|24|-27|12|8|5|9|0|6|10|115|14|90|10|45|32|33|1632053731|1275605286912|643000000|10|13|14|0|10|65|14|66|10|71|14|116|10|117|12|8|5|9|0|9|10|13|14|118|10|50|12|8|5|9|0|2|10|51|14|119|10|52|14|119|-377|-381|10|120|14|121|10|45|32|-368|10|65|14|122|10|71|14|123|10|124|14|125|10|126|12|8|5|9|0|1|24|-27|12|8|5|9|0|9|10|13|14|127|10|124|14|128|10|129|12|8|5|9|0|2|10|51|14|130|10|52|14|131|-405|-411|10|120|14|121|10|45|32|33|867724416|1198295875584|0|10|47|32|33|3896582272|1258425417728|0|10|132|12|8|5|9|0|0|-405|-427|10|65|14|122|10|71|14|133|-401|-403|-377|-399|10|134|12|8|5|9|0|0|-377|-435|-362|-375|-358|-360|-63|-356|-3|-61|0|0|135|0|5|9|0|0|0|0|"
                
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true'
      }
    )
    
    # Loading Pg 8 - Financials
    
    @request.add_thinktime(3)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    #
    # Pg 8 - Financials
    #
    
    # Time to fill out form
    @request.add_thinktime(5)
    
    # Revenue Org
    for i in 1..admin_org.length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|16|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{admin_org[0..itr]}|org.queryParam.orgOptionalType|java.lang.String/2004016611|kuali.org.Department|kuali.org.College|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|8|2|13|14|13|15|0|16|0|0|0|"
        }                             
      )        
    end
    
    # Save & Continue
    contents1 = "5|0|146|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateTime|java.util.Date/1659716317|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|effectiveDate|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|kuali.atp.duration.Period|id-translation|Period|termsOffered|firstExpectedOffering|kuali.atp.FA2010-2011|primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|NORTH|SOUTH|North Campus|South Campus|fees|justification||dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|org.kuali.student.lum.lu.dto.CluInfo|9|#{admin_org}|Credit Course|(#{propose_person})|Fall Semester of 2010|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|0|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Discussion|contactHours|hrs|per|kuali.atp.duration.day|per day|timeUnit|defaultEnrollmentEstimate|Discussion|courseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|gradingOptions|kuali.resultComponent.grade.letter|Letter|outcomeOptions|outcomeId|%%_#{opts[:outcome_id_var_name]}%%|outcomeType|kuali.creditType.credit.degree.1|1 Credit|courseSpecificLOs|sequence|kuali.lu.lo.relation.type.includes|includedSingleUseLo|%%_#{opts[:single_use_lo_dyn_var_name]}%%|#{opts[:lo_name]}|loRepository|kuali.loRepository.key.singleUse|active|kuali.lo.type.singleUse|name|SINGLE USE LO|categories|%%_#{opts[:lo_category_var_name]}%%|#{opts[:lo_category]}|desc|&lt;p&gt;Desc&lt;/p&gt;|Desc|attributes|loCategoryType.subject|childSingleUseLos|revenueInfo|revenueOrg|orgId|percentage|created|updated|org.kuali.student.core.assembly.data.Data$LongValue/2873666547|java.lang.Long/4227064769|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|8|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|10|18|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-22|10|27|12|8|5|9|0|4|10|28|14|29|10|30|14|29|10|31|32|33|1631977501|1275605286912|413000000|10|34|32|35|1632405476|1275605286912|-7|-29|10|36|12|8|5|9|0|1|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|39|10|13|14|15|10|40|14|41|-49|-51|-45|-47|-7|-43|-3|-5|10|42|12|8|5|9|0|25|10|13|14|26|10|43|14|44|10|45|14|20|10|46|32|35|1584713088|1275605286912|10|47|32|35|3010734464|1357209665536|10|48|14|49|10|50|12|8|5|9|0|2|10|51|14|0|10|52|14|53|-63|-79|10|54|12|8|5|9|0|3|10|55|56|25|2|10|57|14|58|10|36|12|8|5|9|0|1|10|57|12|8|5|9|0|1|10|59|14|60|-98|-100|-89|-96|-63|-87|10|61|12|8|5|9|0|0|-63|-106|10|62|14|63|10|64|14|29|10|65|14|66|10|67|14|68|10|69|14|70|10|71|14|72|10|73|12|8|5|9|0|0|-63|-122|10|74|12|8|5|9|0|3|24|-27|14|75|24|25|1|14|76|10|36|12|8|5|9|0|2|24|-27|12|8|5|9|0|1|10|59|14|77|-137|-139|24|-133|12|8|5|9|0|1|10|59|14|78|-137|-145|-128|-135|-63|-126|10|79|12|8|5|9|0|1|24|25|0|12|8|5|9|0|2|10|80|14|81|10|36|12|8|5|9|0|1|10|82|12|8|5|9|0|1|-160|83|84|1|-164|-166|-158|-162|-153|-155|-63|-151|10|36|12|8|5|9|0|7|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|85|10|13|14|26|10|40|14|86|-178|-180|-174|-176|10|48|12|8|5|9|0|1|10|59|14|87|-174|-190|10|71|12|8|5|9|0|1|10|59|14|88|-174|-196|10|64|12|8|5|9|0|1|10|59|14|89|-174|-202|10|67|12|8|5|9|0|1|10|59|14|68|-174|-208|10|62|12|8|5|9|0|1|10|59|14|90|-174|-214|-166|12|8|5|9|0|1|10|62|83|-171|-174|-166|-63|-172|10|91|12|8|5|9|0|1|24|-27|12|8|5|9|0|4|10|13|14|92|10|65|14|66|10|36|12|8|5|9|0|1|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|85|10|13|14|92|10|40|14|93|-243|-245|-239|-241|-231|-237|10|94|12|8|5|9|0|1|24|-27|12|8|5|9|0|7|10|13|14|95|10|96|14|97|10|98|12|8|5|9|0|3|10|99|56|-133|10|100|14|101|10|36|12|8|5|9|0|1|10|100|12|8|5|9|0|1|10|59|14|102|-277|-279|-269|-275|-261|-267|10|54|12|8|5|9|0|3|10|55|56|25|1|10|103|14|58|10|36|12|8|5|9|0|1|10|103|12|8|5|9|0|1|10|59|14|60|-296|-298|-287|-294|-261|-285|10|104|56|25|100|10|65|14|66|10|36|12|8|5|9|0|2|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|85|10|13|14|95|10|40|14|93|-315|-317|-311|-313|10|96|12|8|5|9|0|1|10|59|14|105|-311|-327|-261|-309|-257|-259|-231|-255|-227|-229|-63|-225|10|106|14|107|10|108|12|8|5|9|0|2|24|-27|14|109|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|1|10|59|14|110|-343|-345|-337|-341|-63|-335|10|111|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|112|14|113|10|114|14|115|10|36|12|8|5|9|0|1|10|114|12|8|5|9|0|1|10|59|14|116|-365|-367|-357|-363|-353|-355|-63|-351|10|117|12|8|5|9|0|1|24|-27|12|8|5|9|0|6|10|118|14|93|10|46|32|33|1632053731|1275605286912|643000000|10|13|14|0|10|65|14|66|10|71|14|119|10|120|12|8|5|9|0|9|10|13|14|121|10|50|12|8|5|9|0|2|10|51|14|122|10|52|14|122|-394|-398|10|123|14|124|10|46|32|-385|10|65|14|125|10|71|14|126|10|127|14|128|10|129|12|8|5|9|0|1|24|-27|12|8|5|9|0|9|10|13|14|130|10|127|14|131|10|132|12|8|5|9|0|2|10|51|14|133|10|52|14|134|-422|-428|10|123|14|124|10|46|32|33|867724416|1198295875584|0|10|47|32|33|3896582272|1258425417728|0|10|135|12|8|5|9|0|0|-422|-444|10|65|14|125|10|71|14|136|-418|-420|-394|-416|10|137|12|8|5|9|0|0|-394|-452|-379|-392|-375|-377|-63|-373|10|138|12|8|5|9|0|1|10|139|12|8|5|9|0|1|24|25|0|12|8|5|9|0|3|-162|12|8|5|9|0|4|-166|12|8|5|9|0|2|10|140|83|-171|10|141|83|-171|-470|-166|10|142|83|-171|-162|12|8|5|9|0|1|-166|12|8|5|9|0|2|-479|83|-171|10|143|83|-171|-482|-166|-470|-162|-488|83|-171|-467|-162|-475|14|49|-477|144|145|100|0|-462|-464|-458|-460|-63|-456|-3|-61|0|0|146|0|5|9|0|0|0|0|"
                
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        :dyn_variables => [
          {"name" => opts[:clu_ref_dyn_var_name], "regexp" => opts[:clu_ref_dyn_var_regexp]},
          {"name" => opts[:fee_info_id_dyn_var_name], "regexp" => opts[:fee_info_id_dyn_var_regexp]},
          {"name" => opts[:fee_info_dyn_var_name], "regexp" => opts[:fee_info_dyn_var_regexp]},
          {"name" => opts[:clu_info_dyn_var_name], "regexp" => opts[:clu_info_dyn_var_regexp]}
        ],
        'subst' => 'true'
      }
    )
    
    # Re-Loading Pg 8 - Financials
    
    @request.add_thinktime(3)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    
    # Submit to worflow
    if(opts[:submit])
      
      #@request.add("/fake_url/clu_ref_fee_id/%%_#{opts[:clu_ref_dyn_var_name]}%%", {}, {'subst' => 'true'})
      #@request.add("/fake_url/fee_info_id/%%_#{opts[:fee_info_id_dyn_var_name]}%%", {}, {'subst' => 'true'})
      
      # Submit to workflow
      #contents1 = "5|0|152|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|submitDocumentWithData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateTime|java.util.Date/1659716317|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|effectiveDate|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|kuali.atp.duration.Period|id-translation|Period|termsOffered|firstExpectedOffering|kuali.atp.FA2010-2011|primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|NORTH|SOUTH|North Campus|South Campus|fees|%%_#{opts[:fee_info_id_dyn_var_name]}%%|justification||org.kuali.student.lum.lu.dto.CluFeeInfo|2|revenueInfo|feeType|Revenue|totalPercentage|100.00|revenueOrg|orgId|percentage|org.kuali.student.core.assembly.data.Data$LongValue/2873666547|java.lang.Long/4227064769|#{admin_org}|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|updated|org.kuali.student.lum.lu.dto.CluInfo|11|Credit Course|(#{propose_person})|Fall Semester of 2010|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|0|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Discussion|contactHours|hrs|per|kuali.atp.duration.day|per day|timeUnit|defaultEnrollmentEstimate|Discussion|courseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|gradingOptions|kuali.resultComponent.grade.letter|Letter|outcomeOptions|outcomeId|%%_#{opts[:outcome_id_var_name]}%%|outcomeType|kuali.creditType.credit.degree.1|1 Credit|courseSpecificLOs|sequence|kuali.lu.lo.relation.type.includes|includedSingleUseLo|%%_#{opts[:single_use_lo_dyn_var_name]}%%|#{opts[:lo_name]}|loRepository|kuali.loRepository.key.singleUse|active|kuali.lo.type.singleUse|name|SINGLE USE LO|categories|%%_#{opts[:lo_category_var_name]}%%|#{opts[:lo_category]}|desc|&lt;p&gt;Desc&lt;/p&gt;|Desc|attributes|loCategoryType.subject|childSingleUseLos|java.util.ArrayList/3821976829"
      contents1 = "5|0|151|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|submitDocumentWithData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|metaInfo|createId|#{propose_person}|updateId|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateTime|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|effectiveDate|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|kuali.atp.duration.Period|id-translation|Period|termsOffered|firstExpectedOffering|kuali.atp.FA2010-2011|primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|NORTH|SOUTH|North Campus|South Campus|fees|%%_#{opts[:fee_info_id_dyn_var_name]}%%|justification||org.kuali.student.lum.lu.dto.CluFeeInfo|%%_#{opts[:fee_info_dyn_var_name]}%%|revenueInfo|feeType|Revenue|totalPercentage|100.00|revenueOrg|orgId|percentage|org.kuali.student.core.assembly.data.Data$LongValue/2873666547|java.lang.Long/4227064769|#{admin_org}|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|updated|org.kuali.student.lum.lu.dto.CluInfo|%%_#{opts[:clu_info_dyn_var_name]}%%|Credit Course|(#{propose_person})|Fall Semester of 2010|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|0|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Discussion|contactHours|hrs|per|kuali.atp.duration.day|per day|timeUnit|defaultEnrollmentEstimate|Discussion|courseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|gradingOptions|kuali.resultComponent.grade.letter|Letter|outcomeOptions|outcomeId|%%_#{opts[:outcome_id_var_name]}%%|outcomeType|kuali.creditType.credit.degree.1|1 Credit|courseSpecificLOs|sequence|kuali.lu.lo.relation.type.includes|includedSingleUseLo|%%_#{opts[:single_use_lo_dyn_var_name]}%%|#{opts[:lo_name]}|loRepository|kuali.loRepository.key.singleUse|active|kuali.lo.type.singleUse|name|SINGLE USE LO|categories|%%_#{opts[:lo_category_var_name]}%%|#{opts[:lo_category]}|desc|&lt;p&gt;Desc&lt;/p&gt;|Desc|attributes|loCategoryType.subject|childSingleUseLos|java.util.ArrayList/3821976829"
      #contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|8|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|10|18|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-22|10|27|12|8|5|9|0|4|10|28|14|29|10|30|14|29|10|31|32|33|1631977501|1275605286912|413000000|10|34|32|35|1633365750|1275605286912|-7|-29|10|36|12|8|5|9|0|1|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|39|10|13|14|15|10|40|14|41|-49|-51|-45|-47|-7|-43|-3|-5|10|42|12|8|5|9|0|25|10|13|14|26|10|43|14|44|10|45|14|20|10|46|32|35|1584713088|1275605286912|10|47|32|35|3010734464|1357209665536|10|48|14|49|10|50|12|8|5|9|0|2|10|51|14|0|10|52|14|53|-63|-79|10|54|12|8|5|9|0|3|10|55|56|25|2|10|57|14|58|10|36|12|8|5|9|0|1|10|57|12|8|5|9|0|1|10|59|14|60|-98|-100|-89|-96|-63|-87|10|61|12|8|5|9|0|0|-63|-106|10|62|14|63|10|64|14|29|10|65|14|66|10|67|14|68|10|69|14|70|10|71|14|72|10|73|12|8|5|9|0|0|-63|-122|10|74|12|8|5|9|0|3|24|-27|14|75|24|25|1|14|76|10|36|12|8|5|9|0|2|24|-27|12|8|5|9|0|1|10|59|14|77|-137|-139|24|-133|12|8|5|9|0|1|10|59|14|78|-137|-145|-128|-135|-63|-126|10|79|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|13|14|80|10|81|14|82|10|36|12|8|5|9|0|1|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|83|10|13|14|80|10|40|14|84|-169|-171|-165|-167|-157|-163|-153|-155|-63|-151|10|85|12|8|5|9|0|3|10|86|14|87|10|88|14|89|10|90|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|91|14|49|10|92|93|94|100|0|10|36|12|8|5|9|0|4|10|91|12|8|5|9|0|1|10|59|14|95|-204|-206|10|96|12|8|5|9|0|1|10|91|97|98|1|-204|-212|10|99|97|-218|10|36|12|8|5|9|0|1|-212|12|8|5|9|0|1|-219|97|-218|-223|-212|-204|-221|-195|-202|-191|-193|-183|-189|-63|-181|10|36|12|8|5|9|0|7|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|100|10|13|14|26|10|40|14|101|-235|-237|-231|-233|10|48|12|8|5|9|0|1|10|59|14|95|-231|-247|10|71|12|8|5|9|0|1|10|59|14|102|-231|-253|10|64|12|8|5|9|0|1|10|59|14|103|-231|-259|10|67|12|8|5|9|0|1|10|59|14|68|-231|-265|10|62|12|8|5|9|0|1|10|59|14|104|-231|-271|-212|12|8|5|9|0|1|10|62|97|-218|-231|-212|-63|-229|10|105|12|8|5|9|0|1|24|-27|12|8|5|9|0|4|10|13|14|106|10|65|14|66|10|36|12|8|5|9|0|1|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|100|10|13|14|106|10|40|14|107|-300|-302|-296|-298|-288|-294|10|108|12|8|5|9|0|1|24|-27|12|8|5|9|0|7|10|13|14|109|10|110|14|111|10|112|12|8|5|9|0|3|10|113|56|-133|10|114|14|115|10|36|12|8|5|9|0|1|10|114|12|8|5|9|0|1|10|59|14|116|-334|-336|-326|-332|-318|-324|10|54|12|8|5|9|0|3|10|55|56|25|1|10|117|14|58|10|36|12|8|5|9|0|1|10|117|12|8|5|9|0|1|10|59|14|60|-353|-355|-344|-351|-318|-342|10|118|56|25|100|10|65|14|66|10|36|12|8|5|9|0|2|10|37|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|38|14|100|10|13|14|109|10|40|14|107|-372|-374|-368|-370|10|110|12|8|5|9|0|1|10|59|14|119|-368|-384|-318|-366|-314|-316|-288|-312|-284|-286|-63|-282|10|120|14|121|10|122|12|8|5|9|0|2|24|-27|14|123|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|1|10|59|14|124|-400|-402|-394|-398|-63|-392|10|125|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|126|14|127|10|128|14|129|10|36|12|8|5|9|0|1|10|128|12|8|5|9|0|1|10|59|14|130|-422|-424|-414|-420|-410|-412|-63|-408|10|131|12|8|5|9|0|1|24|-27|12|8|5|9|0|6|10|132|14|107|10|46|32|33|1632053731|1275605286912|643000000|10|13|14|0|10|65|14|66|10|71|14|133|10|134|12|8|5|9|0|9|10|13|14|135|10|50|12|8|5|9|0|2|10|51|14|136|10|52|14|136|-451|-455|10|137|14|138|10|46|32|-442|10|65|14|139|10|71|14|140|10|141|14|142|10|143|12|8|5|9|0|1|24|-27|12|8|5|9|0|9|10|13|14|144|10|141|14|145|10|146|12|8|5|9|0|2|10|51|14|147|10|52|14|148|-479|-485|10|137|14|138|10|46|32|33|867724416|1198295875584|0|10|47|32|33|3896582272|1258425417728|0|10|149|12|8|5|9|0|0|-479|-501|10|65|14|139|10|71|14|150|-475|-477|-451|-473|10|151|12|8|5|9|0|0|-451|-509|-436|-449|-432|-434|-63|-430|-3|-61|0|0|152|0|5|9|0|0|0|0|"
      contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|8|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|10|18|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-22|10|27|12|8|5|9|0|4|10|28|14|29|10|30|14|29|10|31|32|33|4230353756|1275605286912|668000000|10|34|32|33|4230430305|1275605286912|217000000|-7|-29|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|38|10|13|14|15|10|39|14|40|-49|-51|-45|-47|-7|-43|-3|-5|10|41|12|8|5|9|0|25|10|13|14|26|10|42|14|43|10|44|14|20|10|45|32|33|1584713088|1275605286912|0|10|46|32|33|3010734464|1357209665536|0|10|47|14|48|10|49|12|8|5|9|0|2|10|50|14|0|10|51|14|52|-63|-79|10|53|12|8|5|9|0|3|10|54|55|25|2|10|56|14|57|10|35|12|8|5|9|0|1|10|56|12|8|5|9|0|1|10|58|14|59|-98|-100|-89|-96|-63|-87|10|60|12|8|5|9|0|0|-63|-106|10|61|14|62|10|63|14|29|10|64|14|65|10|66|14|67|10|68|14|69|10|70|14|71|10|72|12|8|5|9|0|0|-63|-122|10|73|12|8|5|9|0|3|24|-27|14|74|24|25|1|14|75|10|35|12|8|5|9|0|2|24|-27|12|8|5|9|0|1|10|58|14|76|-137|-139|24|-133|12|8|5|9|0|1|10|58|14|77|-137|-145|-128|-135|-63|-126|10|78|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|13|14|79|10|80|14|81|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|82|10|13|14|79|10|39|14|83|-169|-171|-165|-167|-157|-163|-153|-155|-63|-151|10|84|12|8|5|9|0|3|10|85|14|86|10|87|14|88|10|89|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|90|14|48|10|91|92|93|100|0|10|35|12|8|5|9|0|4|10|90|12|8|5|9|0|1|10|58|14|94|-204|-206|10|95|12|8|5|9|0|1|10|90|96|97|1|-204|-212|10|98|96|-218|10|35|12|8|5|9|0|1|-212|12|8|5|9|0|1|-219|96|-218|-223|-212|-204|-221|-195|-202|-191|-193|-183|-189|-63|-181|10|35|12|8|5|9|0|6|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|99|10|13|14|26|10|39|14|100|-235|-237|-231|-233|10|47|12|8|5|9|0|1|10|58|14|94|-231|-247|10|70|12|8|5|9|0|1|10|58|14|101|-231|-253|10|63|12|8|5|9|0|1|10|58|14|102|-231|-259|10|66|12|8|5|9|0|1|10|58|14|67|-231|-265|10|61|12|8|5|9|0|1|10|58|14|103|-231|-271|-63|-229|10|104|12|8|5|9|0|1|24|-27|12|8|5|9|0|4|10|13|14|105|10|64|14|65|10|35|12|8|5|9|0|1|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|99|10|13|14|105|10|39|14|106|-295|-297|-291|-293|-283|-289|10|107|12|8|5|9|0|1|24|-27|12|8|5|9|0|7|10|13|14|108|10|109|14|110|10|111|12|8|5|9|0|3|10|112|55|-133|10|113|14|114|10|35|12|8|5|9|0|1|10|113|12|8|5|9|0|1|10|58|14|115|-329|-331|-321|-327|-313|-319|10|53|12|8|5|9|0|3|10|54|55|25|1|10|116|14|57|10|35|12|8|5|9|0|1|10|116|12|8|5|9|0|1|10|58|14|59|-348|-350|-339|-346|-313|-337|10|117|55|25|100|10|64|14|65|10|35|12|8|5|9|0|2|10|36|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|37|14|99|10|13|14|108|10|39|14|106|-367|-369|-363|-365|10|109|12|8|5|9|0|1|10|58|14|118|-363|-379|-313|-361|-309|-311|-283|-307|-279|-281|-63|-277|10|119|14|120|10|121|12|8|5|9|0|2|24|-27|14|122|10|35|12|8|5|9|0|1|24|-27|12|8|5|9|0|1|10|58|14|123|-395|-397|-389|-393|-63|-387|10|124|12|8|5|9|0|1|24|-27|12|8|5|9|0|3|10|125|14|126|10|127|14|128|10|35|12|8|5|9|0|1|10|127|12|8|5|9|0|1|10|58|14|129|-417|-419|-409|-415|-405|-407|-63|-403|10|130|12|8|5|9|0|1|24|-27|12|8|5|9|0|6|10|131|14|106|10|45|32|33|4230411398|1275605286912|310000000|10|13|14|0|10|64|14|65|10|70|14|132|10|133|12|8|5|9|0|9|10|13|14|134|10|49|12|8|5|9|0|2|10|50|14|135|10|51|14|135|-446|-450|10|136|14|137|10|45|32|-437|10|64|14|138|10|70|14|139|10|140|14|141|10|142|12|8|5|9|0|1|24|-27|12|8|5|9|0|9|10|13|14|143|10|140|14|144|10|145|12|8|5|9|0|2|10|50|14|146|10|51|14|147|-474|-480|10|136|14|137|10|45|32|33|867724416|1198295875584|0|10|46|32|33|3896582272|1258425417728|0|10|148|12|8|5|9|0|0|-474|-496|10|64|14|138|10|70|14|149|-470|-472|-446|-468|10|150|12|8|5|9|0|0|-446|-504|-431|-444|-427|-429|-63|-425|-3|-61|0|0|151|0|5|9|0|0|0|0|"

      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "#{contents1}#{contents2}"
        },
        {
          'subst' => 'true'
        }
      )
      
      
      @request.add_thinktime(5)

      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )

      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )

      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )

      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )

      @request.add_thinktime(3)

      @request.add("org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?docId=%%_#{opts[:proposal_doc_id_var_name]}%%&amp;command=displayActionListView",{},{'subst' => 'true'})
      @request.add('org.kuali.student.lum.lu.ui.main.LUMMain/org.kuali.student.lum.lu.ui.main.LUMMain.nocache.js')

      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SecurityRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|29F4EA1240F157649C12466F01F46F60|org.kuali.student.common.ui.client.service.SecurityRpcService|getPrincipalUsername|1|2|3|4|0|"
        }
      )
      
      @request.add('org.kuali.student.lum.lu.ui.main.LUMMain/clear.cache.gif')
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.util.List|java.util.Arrays$ArrayList/1243019747|[Ljava.lang.String;/2600011424|application.url|ks.rice.docSearch.serviceAddress|lum.application.url|ks.rice.url|ks.rice.label|ks.application.version|1|2|3|4|1|5|6|7|6|8|9|10|11|12|13|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|isAuthorized|org.kuali.student.core.rice.authorization.PermissionType|java.util.Map|org.kuali.student.core.rice.authorization.PermissionType/259370389|java.util.HashMap/962170901|java.lang.String/2004016611|documentNumber|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|2|5|6|7|1|8|1|9|10|9|11|"
        },
        {'subst' => 'true'}
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|ks.lum.ui.displayOnlyActiveLoCategories|1|2|3|4|1|5|6|"
        }
      )
      
      @request.add('org.kuali.student.lum.lu.ui.main.LUMMain/images/common/twiddler3.gif')
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getMetadata|java.lang.String|documentNumber|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|2|5|5|6|7|"
        },
        {'subst' => 'true'}
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDataFromWorkflowId|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )
      
      @request.add_thinktime(2)
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )
      
      @request.add('org.kuali.student.lum.lu.ui.main.LUMMain/images/common/node.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )
      
      # Dupe of 2nd above request
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )

      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowToolRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|CC382D5431E425533D6BB1904030DB89|org.kuali.student.lum.lu.ui.course.client.service.WorkflowToolRpcService|getCollaborators|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )

      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getDocumentStatus|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )

      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowNodes|java.lang.String|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {'subst' => 'true'}
      )
      
    end
      
      
      # IGNORE BELOW
    
    
    
    # Get propoer clu for fee id
    @request.add('/services/ProposalService',
      {
        'method' => 'POST',
        'content_type' => 'text/xml; charset=UTF-8',
        'contents' => "&lt;soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:prop='http://student.kuali.org/wsdl/proposal'&gt;&lt;soapenv:Header/&gt;&lt;soapenv:Body&gt;&lt;prop:getProposal&gt;&lt;proposalId&gt;%%_#{opts[:proposal_dyn_var_name]}%%&lt;/proposalId&gt;&lt;/prop:getProposal&gt;&lt;/soapenv:Body&gt;&lt;/soapenv:Envelope&gt;"
      },
      {
        #:dyn_variables => [{"name" => opts[:clu_ref_dyn_var_name], "regexp" => opts[:clu_ref_dyn_var_regexp]}],
        'subst' => 'true'
      }
    )
    
    # Get Fee ID
    @request.add('/services/LuService',
      {
        'method' => 'POST',
        'content_type' => 'text/xml; charset=UTF-8',
        'contents' => "&lt;soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:lu='http://student.kuali.org/wsdl/lu'&gt;&lt;soapenv:Header/&gt;&lt;soapenv:Body&gt;&lt;lu:getClu&gt;&lt;cluId&gt;%%_#{opts[:clu_ref_dyn_var_name]}%%&lt;/cluId&gt;&lt;/lu:getClu&gt;&lt;/soapenv:Body&gt;&lt;/soapenv:Envelope&gt;"
      },
      {
        #:dyn_variables => [{"name" => opts[:fee_info_id_dyn_var_name], "regexp" => opts[:fee_info_id_dyn_var_regexp]}],
        'subst' => 'true'
      }
    )
    
    
    
    
  end
  
  
  # Find Course or Proposal
  def find(type, name, opts={})
    
    defaults = {
      :nav_homepage => true,
      :course_description => '',
      :course_number => ''
    }
    
    opts = defaults.merge(opts)
    
    # Navigate to Curriculum Mgmt
    self.homepage() unless(!opts[:nav_homepage])
    
    
    # Search Criteria
    if(type == "proposal")
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|proposal.queryParam.proposalOptionalName|#{name}|proposal.search.generic|proposal.resultColumn.proposalOptionalName|1|2|3|4|1|5|6|0|7|0|8|1|9|10|0|11|12|13|0|0|"
        }
      )
    elsif(type == "course")
      # Only searching by title/name
      contents = (name == '' ? "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Integer/3438268394|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lu.queryParam.luOptionalType|kuali.lu.type.CreditCourse|lu.search.generic|org.resultColumn.orgShortName|1|2|3|4|1|5|5|6|10|7|0|8|1|9|10|0|11|12|13|0|6|0|" : 
                               "5|0|15|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Integer/3438268394|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lu.queryParam.luOptionalLongName|#{name}|lu.queryParam.luOptionalType|kuali.lu.type.CreditCourse|lu.search.generic|org.resultColumn.orgShortName|1|2|3|4|1|5|5|6|10|7|0|8|2|9|10|0|11|9|12|0|13|14|15|0|6|0|")
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => contents
        }
      )

    end
    
  end
    
end