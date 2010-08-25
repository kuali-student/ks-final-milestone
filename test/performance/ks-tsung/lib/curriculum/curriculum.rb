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
    
    #@request.add("DEBUG/proposal_dyn_var_name/%%_#{opts[:proposal_dyn_var_name]}%%", {}, {'subst' => 'true'})
    #@request.add("DEBUG/clu_ref_dyn_var_name/%%_#{opts[:clu_ref_dyn_var_name]}%%", {}, {'subst' => 'true'})
    #@request.add("DEBUG/proposal_doc_id_var_name/%%_#{opts[:proposal_doc_id_var_name]}%%", {}, {'subst' => 'true'})
    
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
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|15|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{admin_org[0..itr]}|org.queryParam.orgOptionalType|java.lang.String/2004016611|kuali.org.Department|kuali.org.College|org.search.generic|1|2|3|4|1|5|5|0|6|0|7|2|8|9|0|10|8|11|7|2|12|13|12|14|0|15|0|0|0|"
        }    
      )    
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
    
    #@request.add("DEBUG/enroll_est_var_name/%%_#{opts[:enroll_est_var_name]}%%", {}, {'subst' => 'true'})
    #@request.add("DEBUG/lab_var_name/%%_#{opts[:lab_var_name]}%%", {}, {'subst' => 'true'})

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
          #{"name" => opts[:result_com_var_name], "regexp" => opts[:result_com_var_regexp]},
          {"name" => opts[:lo_category_var_name], "regexp" => opts[:lo_category_var_regexp]}
          #{"name" => opts[:joints_var_name], "regexp" => opts[:joints_var_regexp]}
        ]
      }
    )

    #@request.add("DEBUG/result_com_var_name/%%_#{opts[:result_com_var_name]}%%", {}, {'subst' => 'true'})
    #@request.add("DEBUG/lo_category_var_name/%%_#{opts[:lo_category_var_name]}%%", {}, {'subst' => 'true'})
    #@request.add("DEBUG/joints_var_name/%%_#{opts[:joints_var_name]}%%", {}, {'subst' => 'true'})

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