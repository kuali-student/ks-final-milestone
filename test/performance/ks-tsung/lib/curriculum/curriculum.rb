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
      :collaborator => @request.config.directory["users"]["collaborator"]["username"],
      :first_expected_offering => @request.config.directory["atp"]["name"],
      :subject_area => "ARTS",
      :course_suffix => "123",
      :course_short_title => "Perf Course",
      :course_title => title,
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
      :lab_var_regexp => 'draft\"\,\"unitsContentOwner\"\,\"Lab\"\,\"\([^\"]+\)',
      :lab_fee_id_name => 'cp_lab_fee_id',
      :lab_fee_id_regexp => 'kuali.enum.type.feeTypes.labFee\"\,\"\([^\"]+\)',
      :revenues_id_name => 'cp_revenues_id',
      :revenues_id_regexp => 'revenues\"\,\"\([^\"]+\)',
      :revenue_id_name => 'cp_revenue_id',
      :revenue_id_regexp => 'REVENUE\"\,\"\([^\"]+\)',
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
      #:lo_category_var_regexp => lo_cat_text + '\"\,\"plain\"\,\"id\"\,\"\([^\"]+\)',
      :lo_category_var_regexp => lo_cat_text + '\"\,\"plain\"\,\"\([^\"]+\)',
      :lo_category_id_var_name => "lo_category_id",
      :lo_category_id_var_regexp => 'lo.resultColumn.categoryId"\,\"\([^\"]+\)',
      :lo_child_id_dyn_var_name => "lo_child_id",
      :lo_child_id_dyn_var_regexp => 'childLo\"\,\"\([^\"]+\)',
      :single_use_lo_dyn_var_name => "single_use_lo",
      :single_use_lo_dyn_var_regexp => 'includedSingleUseLo\"\,\"\([^\"]+\)',
      :atp_duration_week_var_name => 'atp_duration_week',
      :atp_duration_week_var_regexp => 'kuali.atp.duration.Week\"\,\"Week\"\,\"\([^\"]+\)',
      :version_ind_id_name => 'cp_version_ind_id',
      :version_ind_id_regexp => 'versionIndId\"\,\"\([^\"]+\)',
      :affliated_orgs_id_name => 'cp_affiliated_orgs_id',
      :affliated_orgs_id_regexp => 'affiliatedOrgs\"\,\"\([^\"]+\)'
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
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B7AC280E8F60A1F596CF308193BB3CE3|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getMetadata|java.lang.String/2004016611|java.util.Map||java.util.HashMap/962170901|documentTypeName|kuali.proposal.type.course.create|1|2|3|4|2|5|6|7|8|1|5|9|5|10|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/statementRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|8570183BC24C0249EDDAE7E4CD6EC500|org.kuali.student.lum.program.client.rpc.StatementRpcService|getStatementTypesForStatementTypeForCourse|java.lang.String/2004016611|kuali.statement.type.course|1|2|3|4|1|5|6|"
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
        'contents' => "5|0|18|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.type|kuali.resultComponentType.grade.finalGrade|lrc.queryParam.resultComponent.idRestrictionList|java.lang.String/2004016611|kuali.resultComponent.grade.letter|kuali.resultComponent.grade.satisfactory|kuali.resultComponent.grade.percentage|kuali.resultComponent.grade.recitalReview|kuali.resultComponent.grade.designReview|kuali.resultComponent.grade.completedNotation|lrc.search.resultComponent|1|2|3|4|1|5|5|0|0|6|2|7|8|0|9|7|10|6|6|11|12|11|13|11|14|11|15|11|16|11|17|0|18|0|0|0|"
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
        'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.idRestrictionList|java.lang.String/2004016611|kuali.resultComponentType.credit.degree.fixed|kuali.resultComponentType.credit.degree.range|kuali.resultComponentType.credit.degree.multiple|lrc.search.resultComponentType|1|2|3|4|1|5|5|0|0|6|1|7|8|6|3|9|10|9|11|9|12|0|13|0|0|0|"
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.idRestrictionList|java.lang.String/2004016611|kuali.resultComponentType.credit.degree.fixed|kuali.resultComponentType.credit.degree.range|kuali.resultComponentType.credit.degree.multiple|lrc.search.resultComponentType|1|2|3|4|1|5|5|0|0|6|1|7|8|6|3|9|10|9|11|9|12|0|13|0|0|0|"
      }
    )
    # DUPE
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
        'contents' => "5|0|16|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|atp.advancedAtpSearchParam.atpType|java.lang.String/2004016611|kuali.atp.type.Spring|kuali.atp.type.Fall|kuali.atp.type.Session1|kuali.atp.type.Session2|kuali.atp.type.Mini-mester1A|kuali.atp.type.Mini-mester1B|atp.search.advancedAtpSearch|1|2|3|4|1|5|5|0|0|6|1|7|8|6|6|9|10|9|11|9|12|9|13|9|14|9|15|0|16|0|0|0|"
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|16|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|atp.advancedAtpSearchParam.atpType|java.lang.String/2004016611|kuali.atp.type.Spring|kuali.atp.type.Fall|kuali.atp.type.Session1|kuali.atp.type.Session2|kuali.atp.type.Mini-mester1A|kuali.atp.type.Mini-mester1B|atp.search.advancedAtpSearch|1|2|3|4|1|5|5|0|0|6|1|7|8|6|6|9|10|9|11|9|12|9|13|9|14|9|15|0|16|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.rateType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.rateType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.rateType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.rateType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.campusLocation|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    # DUPE
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
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|atp.search.atpSeasonTypes|1|2|3|4|1|5|5|0|0|6|0|7|0|0|0|"
      }
    )
    # DUPE
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
        'contents' => "5|0|18|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.type|kuali.resultComponentType.grade.finalGrade|lrc.queryParam.resultComponent.idRestrictionList|java.lang.String/2004016611|kuali.resultComponent.grade.letter|kuali.resultComponent.grade.satisfactory|kuali.resultComponent.grade.percentage|kuali.resultComponent.grade.recitalReview|kuali.resultComponent.grade.designReview|kuali.resultComponent.grade.completedNotation|lrc.search.resultComponent|1|2|3|4|1|5|5|0|0|6|2|7|8|0|9|7|10|6|6|11|12|11|13|11|14|11|15|11|16|11|17|0|18|0|0|0|"
      }
    )
    # DUPE
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
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.finalExam.status|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    #DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.finalExam.status|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CommentRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|F24FA3EECC6ED0189F13D39498589F0F|org.kuali.student.common.ui.client.service.CommentRpcService|getUserRealName|java.lang.String/2004016611|#{propose_person}|1|2|3|4|1|5|6|"
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
    
    contents1 = "5|0|33|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B7AC280E8F60A1F596CF308193BB3CE3|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.lum.lu.assembly.data.client.LuData/746540236|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|name|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|#{title}|_runtimeData|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|rationale|#{opts[:course_rationale]}|courseTitle|#{opts[:course_title]}|transcriptTitle|subjectArea|courseNumberSuffix|instructors|#{opts[:course_short_title]}|#{opts[:subject_area]}|#{opts[:course_suffix]}|descr|plain|#{opts[:course_description]}"
    contents2 = "|1|2|3|4|1|5|6|7|0|5|8|9|0|8|10|11|12|5|8|9|0|3|10|13|14|15|10|16|12|5|8|9|0|1|10|17|12|5|8|9|0|2|-9|18|19|1|10|20|18|-20|-13|-15|-7|-11|-21|14|21|-3|-5|10|22|14|23|-11|12|5|8|9|0|1|-15|12|5|8|9|0|5|-24|18|-20|10|24|18|-20|10|25|18|-20|10|26|18|-20|10|27|18|-20|-27|-15|-3|-11|-33|14|28|-35|14|29|-37|14|30|10|31|12|5|8|9|0|2|10|32|14|33|-11|12|5|8|9|0|1|-15|12|5|8|9|0|1|-48|18|-20|-51|-15|-46|-11|-3|-44|-39|12|0|0|0|8|9|0|0|0|0|"
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
          {"name" => opts[:proposal_doc_id_var_name], "regexp" => opts[:proposal_doc_id_var_regexp]},
          {"name" => opts[:version_ind_id_name], "regexp" => opts[:version_ind_id_regexp]}
        ]
      }
    )
    
    @request.add("DEBUG/proposal_dyn_var_name/%%_#{opts[:proposal_dyn_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/clu_ref_dyn_var_name/%%_#{opts[:clu_ref_dyn_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/proposal_doc_id_var_name/%%_#{opts[:proposal_doc_id_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/version_ind_id_name/%%_#{opts[:version_ind_id_name]}%%", {}, {'subst' => 'true'})
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
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
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
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
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
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
    contents1 = "5|0|93|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B7AC280E8F60A1F596CF308193BB3CE3|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|campusLocations|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|AL|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|courseTitle|#{opts[:course_title]}|creditOptions|crossListings|descr|plain|#{opts[:course_description]}|expenditure|affiliatedOrgs|fees|formats|gradingOptions|id|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|joints|level|100|metaInfo|createId|#{propose_person}|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateId|updateTime|versionInd|#{version_indicator+=1}|pilotCourse|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|revenues|specialTopicsCourse|state|draft|subjectArea|#{opts[:subject_area]}|termsOffered|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|unitsContentOwner|58|_runtimeData|id-translation|#{oversight_department}|unitsDeployment|28|#{admin_org}|variations|versionInfo|sequenceNumber|org.kuali.student.core.assembly.data.Data$LongValue/2873666547|java.lang.Long/4227064769|versionIndId|%%_#{opts[:version_ind_id_name]}%%|dirty|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|2|name|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%|collaboratorInfo|collaborators"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|32|8|9|10|5|6|7|0|1|11|12|0|13|14|-1|8|9|8|15|13|16|8|17|13|18|8|19|10|5|6|7|0|0|-1|-15|8|20|13|21|8|22|10|5|6|7|0|0|-1|-21|8|23|10|5|6|7|0|0|-1|-25|8|24|10|5|6|7|0|1|8|25|13|26|-1|-29|8|27|10|5|6|7|0|1|8|28|10|5|6|7|0|0|-37|-39|-1|-35|8|29|10|5|6|7|0|0|-1|-43|8|30|10|5|6|7|0|0|-1|-47|8|31|10|5|6|7|0|0|-1|-51|8|32|13|33|8|34|10|5|6|7|0|0|-1|-57|8|35|10|5|6|7|0|0|-1|-61|8|36|13|37|8|38|10|5|6|7|0|5|8|39|13|40|8|41|42|43|1457587257|1288490188800|57000000|8|44|13|40|8|45|42|43|1457587257|1288490188800|57000000|8|46|13|47|-1|-67|8|48|49|50|0|8|51|10|5|6|7|0|0|-1|-86|8|52|49|-85|8|53|13|54|8|55|13|56|8|57|10|5|6|7|0|0|-1|-96|8|58|13|59|8|60|13|61|8|62|10|5|6|7|0|2|11|-8|13|63|8|64|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|65|13|66|-112|-114|-106|-110|-1|8|62|8|67|10|5|6|7|0|2|11|-8|13|68|8|64|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|65|13|69|-129|-131|-123|-127|-1|8|67|8|70|10|5|6|7|0|0|-1|-138|8|71|10|5|6|7|0|2|8|72|73|74|1|0|8|75|13|76|-1|-142|8|64|10|5|6|7|0|2|8|55|10|5|6|7|0|1|8|65|13|56|-153|-155|8|77|10|5|6|7|0|3|8|9|49|50|1|8|62|49|-167|8|67|49|-167|-153|-161|-1|-151|8|78|10|5|6|7|0|10|8|32|13|79|8|38|10|5|6|7|0|5|8|39|13|40|8|41|42|43|1457587384|1288490188800|184000000|8|44|13|40|8|45|42|43|1457589228|1288490188800|28000000|8|46|13|80|-174|-178|8|81|13|21|8|82|10|5|6|7|0|1|11|-8|13|33|-174|-196|8|83|13|84|8|85|10|5|6|7|0|0|-174|-204|8|86|10|5|6|7|0|0|-174|-208|8|87|13|88|8|60|13|89|8|90|13|91|-1|-172|8|92|10|5|6|7|0|1|8|93|10|5|6|7|0|0|-220|-222|-1|-218|0|0|"
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
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    
    #
    # Course Logistics    
    # Term: Any
    # Duration Type: Semester
    # Duration Count: 2
    # Assessment Scale: Letter
    # Standard Final Exam
    # Outcome: 10 credits
    # Course Format
    # => Activity Type: Lab, Contact Hours: 5, Frequency: weekly
    # => Duration Type: Weekly
    # => Duration: 13
    # => Anticipated class size: 100
    
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
    contents1 = "5|0|120|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B7AC280E8F60A1F596CF308193BB3CE3|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|campusLocations|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|AL|_runtimeData|id-translation|All|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|courseTitle|#{opts[:course_title]}|creditOptions|dirty|type|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|fixedCreditValue|created|kuali.resultComponentType.credit.degree.fixed|10|crossListings|descr|plain|#{opts[:course_description]}|expenditure|affiliatedOrgs|fees|formats|activities|activityType|defaultEnrollmentEstimate|kuali.lu.type.activity.Lab|contactHours|unitQuantity|5|unitType|kuali.atp.duration.week|duration|atpDurationTypeKey|timeQuantity|kuali.atp.duration.Week|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|gradingOptions|kuali.resultComponent.grade.letter|id|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|joints|level|100|metaInfo|createId|#{propose_person}|createTime|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|updateId|updateTime|java.util.Date/1659716317|versionInd|#{version_indicator+=1}|pilotCourse|revenues|specialTopicsCourse|state|draft|subjectArea|#{opts[:subject_area]}|termsOffered|kuali.atp.season.Any|transcriptTitle|#{opts[:course_short_title]}|kuali.lu.type.CreditCourse|unitsContentOwner|58|#{oversight_department}|unitsDeployment|28|#{admin_org}|variations|versionInfo|sequenceNumber|org.kuali.student.core.assembly.data.Data$LongValue/2873666547|java.lang.Long/4227064769|versionIndId|%%_#{opts[:version_ind_id_name]}%%|finalExamStatus|audit|passFail|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|3|name|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%|collaboratorInfo|collaborators|kuali.atp.duration.Semester|STD"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|36|8|9|10|5|6|7|0|2|11|12|0|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-12|-14|-5|-10|-1|-3|8|18|13|19|8|20|13|21|8|22|10|5|6|7|0|0|-1|-24|8|23|13|24|8|25|10|5|6|7|0|1|11|-8|10|5|6|7|0|3|8|15|10|5|6|7|0|3|8|26|10|5|6|7|0|2|8|27|28|29|1|8|30|28|-48|-40|-42|8|31|28|-48|-38|10|5|6|7|0|1|-42|10|5|6|7|0|1|-51|28|-48|-54|-42|-40|-38|-36|-38|-46|13|32|-49|13|33|-32|-34|-1|-30|8|34|10|5|6|7|0|0|-1|-62|8|35|10|5|6|7|0|1|8|36|13|37|-1|-66|8|38|10|5|6|7|0|1|8|39|10|5|6|7|0|0|-74|-76|-1|-72|8|40|10|5|6|7|0|0|-1|-80|8|41|10|5|6|7|0|1|11|-8|10|5|6|7|0|2|8|42|10|5|6|7|0|1|11|-8|10|5|6|7|0|5|-38|10|5|6|7|0|3|-42|10|5|6|7|0|2|8|43|28|-48|8|44|28|-48|-101|-42|8|31|28|-48|-38|10|5|6|7|0|1|-42|10|5|6|7|0|1|-110|28|-48|-113|-42|-101|-38|-98|-38|-106|13|45|8|46|10|5|6|7|0|3|8|47|13|48|-38|10|5|6|7|0|1|-42|10|5|6|7|0|2|-124|28|-48|8|49|28|-48|-127|-42|-122|-38|-133|13|50|-98|-120|8|51|10|5|6|7|0|3|-38|10|5|6|7|0|1|-42|10|5|6|7|0|2|8|52|28|-48|8|53|28|-48|-141|-42|-138|-38|-146|13|54|-148|55|12|13|-98|-136|-108|55|12|100|-94|-96|-90|-92|8|15|10|5|6|7|0|2|8|31|28|-48|-38|10|5|6|7|0|1|-42|10|5|6|7|0|1|-159|28|-48|-162|-42|-157|-38|-90|-155|-86|-88|-1|-84|8|56|10|5|6|7|0|1|11|-8|13|57|-1|8|56|8|58|13|59|8|60|10|5|6|7|0|0|-1|-177|8|61|10|5|6|7|0|0|-1|-181|8|62|13|63|8|64|10|5|6|7|0|5|8|65|13|66|8|67|68|69|1457587257|1288490188800|57000000|8|70|13|66|8|71|68|72|1457939262|1288490188800|8|73|13|74|-1|-187|8|75|28|29|0|8|76|10|5|6|7|0|0|-1|-206|8|77|28|-205|8|78|13|79|8|80|13|81|8|82|10|5|6|7|0|1|11|-8|13|83|-1|8|82|8|84|13|85|8|27|13|86|8|87|10|5|6|7|0|2|11|-8|13|88|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|89|-235|-237|-229|-233|-1|-227|8|90|10|5|6|7|0|2|11|-8|13|91|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|92|-251|-253|-245|-249|-1|-243|8|93|10|5|6|7|0|0|-1|-259|8|94|10|5|6|7|0|2|8|95|96|97|1|0|8|98|13|99|-1|-263|8|15|10|5|6|7|0|2|8|80|10|5|6|7|0|1|8|16|13|81|-274|-276|-42|10|5|6|7|0|5|8|82|28|-48|8|56|28|-48|8|100|28|-48|8|101|28|-48|8|102|28|-48|-274|-42|-1|-272|8|103|10|5|6|7|0|10|8|58|13|104|8|64|10|5|6|7|0|5|8|65|13|66|8|67|68|69|1457587384|1288490188800|184000000|8|70|13|66|8|71|68|72|1457940512|1288490188800|8|73|13|105|-297|-301|8|106|13|24|8|107|10|5|6|7|0|1|11|-8|13|59|-297|-319|8|108|13|109|8|110|10|5|6|7|0|0|-297|-327|8|111|10|5|6|7|0|0|-297|-331|8|112|13|113|8|27|13|114|8|115|13|116|-1|-295|8|117|10|5|6|7|0|1|8|118|10|5|6|7|0|0|-343|-345|-1|-341|8|51|10|5|6|7|0|3|-38|10|5|6|7|0|1|-42|10|5|6|7|0|2|8|52|28|-48|8|53|28|-48|-354|-42|-351|-38|-359|13|119|-361|55|12|2|-1|-349|-289|13|120|-291|28|-205|-293|28|-205|0|0|"
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
         {"name" => opts[:lab_var_name], "regexp" => opts[:lab_var_regexp]},
         {"name" => opts[:atp_duration_week_var_name], "regexp" => opts[:atp_duration_week_var_regexp]}
       ]
     }
    )
    
    @request.add("DEBUG/enroll_est_var_name/%%_#{opts[:enroll_est_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/lab_var_name/%%_#{opts[:lab_var_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/atp_duration_week_var_name/%%_#{opts[:atp_duration_week_var_name]}%%", {}, {'subst' => 'true'})

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
    

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
       'method' => 'POST',
       'content_type' => 'text/x-gwt-rpc; charset=utf-8',
       'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
       'subst' => 'true'
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
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
      if(i == opts[:lo_category].length)
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lo.queryParam.loOptionalCategoryName|#{opts[:lo_category][0..itr]}|lo.search.loCategories|1|2|3|4|1|5|5|0|6|0|7|1|8|9|0|10|11|0|0|0|"
          },
          {
            :dyn_variables => [
              {"name" => opts[:lo_category_id_var_name], "regexp" => opts[:lo_category_id_var_regexp]}
            ]
          }
        )
        
        @request.add("DEBUG/lo_category_id_var_name/%%_#{opts[:lo_category_id_var_name]}%%", {}, {'subst' => 'true'})
        
      else
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lo.queryParam.loOptionalCategoryName|#{opts[:lo_category][0..itr]}|lo.search.loCategories|1|2|3|4|1|5|5|0|6|0|7|1|8|9|0|10|11|0|0|0|"
          }                             
        )
      end   
    end
    
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B813A779081B7679B8A9D0709A3474CF|org.kuali.student.lum.common.client.lo.rpc.LoRpcService|getLoCategory|java.lang.String/2004016611|%%_#{opts[:lo_category_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B813A779081B7679B8A9D0709A3474CF|org.kuali.student.lum.common.client.lo.rpc.LoRpcService|getLoCategoryType|java.lang.String/2004016611|loCategoryType.subject|1|2|3|4|1|5|6|"
      }
    )
    
    

    # Save & Continue
    contents1 = "5|0|146|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B7AC280E8F60A1F596CF308193BB3CE3|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|passFail|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|audit|finalExamStatus|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|STD|campusLocations|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|AL|_runtimeData|id-translation|All|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|loInfo|id|desc|formatted|#{opts[:lo_cat_text]}|plain|name|SINGLE USE LO|sequence|0|metaInfo|loCategoryInfoList|%%_#{opts[:lo_category_id_var_name]}%%|#{opts[:lo_category]}|loRepository|kuali.loRepository.key.singleUse|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|expirationDate|state|active|type|loCategoryType.subject|courseTitle|#{opts[:course_title]}|creditOptions|fixedCreditValue|10|kuali.creditType.credit.degree.10|createId|#{propose_person}|createTime|updateId|updateTime|versionInd|resultValues|kuali.resultComponentType.credit.degree.fixed|Credits, Fixed|crossListings|descr|#{opts[:course_description]}|duration|atpDurationTypeKey|kuali.atp.duration.Semester|timeQuantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|Semester|expenditure|affiliatedOrgs|fees|formats|activities|activityType|kuali.lu.type.activity.Lab|contactHours|unitQuantity|5|unitType|kuali.atp.duration.week|per week|defaultEnrollmentEstimate|kuali.atp.duration.Week|Week|%%_#{opts[:atp_duration_week_var_name]}%%|draft|unitsContentOwner|Lab|%%_#{opts[:lab_var_name]}%%|kuali.lu.type.CreditCourseFormatShell|gradingOptions|kuali.resultComponent.grade.letter|Letter|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|joints|level|100|2|pilotCourse|revenues|specialTopicsCourse|subjectArea|#{opts[:subject_area]}|termsOffered|kuali.atp.season.Any|Any|transcriptTitle|#{opts[:course_short_title]}|kuali.lu.type.CreditCourse|58|#{oversight_department}|unitsDeployment|28|#{admin_org}|variations|versionInfo|sequenceNumber|org.kuali.student.core.assembly.data.Data$LongValue/2873666547|java.lang.Long/4227064769|versionIndId|%%_#{opts[:version_ind_id_name]}%%|Standard final Exam|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|4|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%|collaboratorInfo|collaborators"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|36|8|9|10|11|0|8|12|10|-5|8|13|14|15|8|16|17|5|6|7|0|2|18|19|0|14|20|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|23|-19|-21|-12|-17|-1|-10|8|24|14|25|8|26|14|27|8|28|17|5|6|7|0|1|18|-15|17|5|6|7|0|2|8|29|17|5|6|7|0|5|8|30|14|0|8|31|17|5|6|7|0|2|8|32|14|33|8|34|14|33|-41|-45|8|35|14|36|8|37|14|38|8|39|17|0|-37|-39|8|40|17|5|6|7|0|1|18|-15|17|5|6|7|0|8|8|30|14|41|8|35|14|42|8|31|17|5|6|7|0|0|-65|-71|8|43|14|44|8|45|46|47|856619375|1288490188800|175000000|8|48|46|0|8|49|14|50|8|51|14|52|-61|-63|-37|-59|-33|-35|-1|8|28|8|53|14|54|8|55|17|5|6|7|0|1|18|-15|17|5|6|7|0|6|8|56|14|57|8|30|14|58|8|39|17|5|6|7|0|5|8|59|14|60|8|61|46|47|765890730|1288490188800|530000000|8|62|14|60|8|63|46|47|765890730|1288490188800|530000000|8|64|14|38|-95|-101|8|65|17|5|6|7|0|1|18|-15|14|57|-95|-117|8|51|14|66|8|21|17|5|6|7|0|1|8|51|17|5|6|7|0|1|8|22|14|67|-127|-129|-95|-125|-91|-93|-1|-89|8|68|17|5|6|7|0|0|-1|-135|8|69|17|5|6|7|0|1|8|34|14|70|-1|-139|8|71|17|5|6|7|0|3|8|72|14|73|8|74|75|19|2|8|21|17|5|6|7|0|1|8|72|17|5|6|7|0|1|8|22|14|76|-156|-158|-147|-154|-1|-145|8|77|17|5|6|7|0|1|8|78|17|5|6|7|0|0|-166|-168|-1|-164|8|79|17|5|6|7|0|0|-1|-172|8|80|17|5|6|7|0|1|18|-15|17|5|6|7|0|5|8|81|17|5|6|7|0|1|18|-15|17|5|6|7|0|9|8|82|14|83|8|84|17|5|6|7|0|3|8|85|14|86|8|87|14|88|8|21|17|5|6|7|0|1|8|87|17|5|6|7|0|1|8|22|14|89|-204|-206|-196|-202|-190|-194|8|90|75|19|100|8|71|17|5|6|7|0|3|8|72|14|91|8|74|75|19|13|8|21|17|5|6|7|0|1|8|72|17|5|6|7|0|1|8|22|14|92|-226|-228|-217|-224|-190|-215|8|30|14|93|8|39|17|5|6|7|0|5|8|59|14|60|8|61|46|47|1459923072|1288490188800|872000000|8|62|14|60|8|63|46|47|1459923072|1288490188800|872000000|8|64|14|38|-190|-236|8|49|14|94|8|95|17|5|6|7|0|0|-190|-254|8|21|17|5|6|7|0|1|8|82|17|5|6|7|0|1|8|22|14|96|-260|-262|-190|-258|-186|-188|-182|-184|8|30|14|97|8|39|17|5|6|7|0|5|8|59|14|60|8|61|46|47|1459923036|1288490188800|836000000|8|62|14|60|8|63|46|47|1459923036|1288490188800|836000000|8|64|14|38|-182|-270|8|49|14|94|8|51|14|98|-178|-180|-1|-176|8|99|17|5|6|7|0|2|18|-15|14|100|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|101|-298|-300|-292|-296|-1|-290|8|30|14|102|8|103|17|5|6|7|0|0|-1|-308|8|104|17|5|6|7|0|0|-1|-312|8|105|14|106|8|39|17|5|6|7|0|5|8|59|14|60|8|61|46|47|1459906692|1288490188800|492000000|8|62|14|60|8|63|46|47|1459922976|1288490188800|776000000|8|64|14|107|-1|-318|8|108|10|-5|8|109|17|5|6|7|0|0|-1|-336|8|110|10|-5|8|49|14|94|8|111|14|112|8|113|17|5|6|7|0|2|18|-15|14|114|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|115|-354|-356|-348|-352|-1|-346|8|116|14|117|8|51|14|118|8|95|17|5|6|7|0|2|18|-15|14|119|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|120|-374|-376|-368|-372|-1|-366|8|121|17|5|6|7|0|2|18|-15|14|122|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|123|-390|-392|-384|-388|-1|-382|8|124|17|5|6|7|0|0|-1|-398|8|125|17|5|6|7|0|2|8|126|127|128|1|0|8|129|14|130|-1|-402|8|21|17|5|6|7|0|2|8|111|17|5|6|7|0|1|8|22|14|112|-413|-415|8|13|17|5|6|7|0|1|8|22|14|131|-413|-421|-1|-411|8|132|17|5|6|7|0|10|8|30|14|133|8|39|17|5|6|7|0|5|8|59|14|60|8|61|46|47|1459906851|1288490188800|651000000|8|62|14|60|8|63|46|47|1459924436|1288490188800|236000000|8|64|14|134|-429|-433|8|35|14|54|8|135|17|5|6|7|0|1|18|-15|14|102|-429|-451|8|136|14|137|8|138|17|5|6|7|0|0|-429|-459|8|139|17|5|6|7|0|0|-429|-463|8|140|14|141|8|51|14|142|8|143|14|144|-1|-427|8|145|17|5|6|7|0|1|8|146|17|5|6|7|0|0|-475|-477|-1|-473|0|0|"
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
    @request.add("DEBUG/lo_category_var_name/%%_#{opts[:lo_category_var_name]}%%", {}, {'subst' => 'true'})
    #@request.add("DEBUG/joints_var_name/%%_#{opts[:joints_var_name]}%%", {}, {'subst' => 'true'})

    @request.add_thinktime(2)

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B813A779081B7679B8A9D0709A3474CF|org.kuali.student.lum.common.client.lo.rpc.LoRpcService|getLoCategoryTypes|1|2|3|4|0|"
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/statementRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|8570183BC24C0249EDDAE7E4CD6EC500|org.kuali.student.lum.program.client.rpc.StatementRpcService|getStatementTypesForStatementTypeForCourse|java.lang.String/2004016611|kuali.statement.type.course|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CourseRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|A1805F52985BBCCC04EEBC6DDFEA8145|org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService|getCourseStatements|java.lang.String/2004016611|%%_#{opts[:clu_ref_dyn_var_name]}%%|KUALI.RULE|en|1|2|3|4|3|5|5|5|6|7|8|"
      },
      {
        'subst' => 'true'
      }
    )
    
    
    #
    # Course Requisites
    # Save without editing anything
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CourseRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|9|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|A1805F52985BBCCC04EEBC6DDFEA8145|org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService|storeCourseStatements|java.lang.String/2004016611|java.util.Map|%%_#{opts[:clu_ref_dyn_var_name]}%%|java.util.HashMap/962170901|java.util.LinkedHashMap/1551059846|1|2|3|4|3|5|6|6|7|8|0|9|0|0|"
      },
      {
        'subst' => 'true'
      }
    )
    
    
    #
    # Active Dates
    # Start = Fall Sem 2008
    # End = 2nd Summer 2011
    
    # Something about this request cases the automation to error out
    
    # Save & Continue
    contents1 = "5|0|154|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B7AC280E8F60A1F596CF308193BB3CE3|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|passFail|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|audit|finalExamStatus|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|STD|campusLocations|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|AL|_runtimeData|id-translation|All|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|loCategoryInfoList|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|id|%%_#{opts[:lo_category_id_var_name]}%%|loRepository|kuali.loRepository.key.singleUse|metaInfo|createId|#{propose_person}|createTime|updateId|updateTime|versionInd|0|name|#{opts[:lo_category]}|state|active|type|loCategoryType.subject|loDisplayInfoList|loInfo|sequence|desc|formatted|#{opts[:lo_cat_text]}|plain|%%_#{opts[:lo_category_var_name]}%%|loRepositoryKey|SINGLE USE LO|kuali.lo.type.singleUse|courseTitle|#{opts[:course_title]}|creditOptions|fixedCreditValue|10|kuali.creditType.credit.degree.10|resultValues|kuali.resultComponentType.credit.degree.fixed|Credits, Fixed|crossListings|descr|#{opts[:course_description]}|duration|atpDurationTypeKey|kuali.atp.duration.Semester|timeQuantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|Semester|expenditure|affiliatedOrgs|fees|formats|activities|activityType|kuali.lu.type.activity.Lab|contactHours|unitQuantity|5|unitType|kuali.atp.duration.week|per week|defaultEnrollmentEstimate|kuali.atp.duration.Week|Week|%%_#{opts[:atp_duration_week_var_name]}%%|1|draft|unitsContentOwner|Lab|%%_#{opts[:lab_var_name]}%%|kuali.lu.type.CreditCourseFormatShell|gradingOptions|kuali.resultComponent.grade.letter|Letter|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|joints|level|100|3|pilotCourse|revenues|specialTopicsCourse|subjectArea|#{opts[:subject_area]}|termsOffered|kuali.atp.season.Any|Any|transcriptTitle|#{opts[:course_short_title]}|kuali.lu.type.CreditCourse|58|#{oversight_department}|unitsDeployment|28|#{admin_org}|variations|versionInfo|sequenceNumber|org.kuali.student.core.assembly.data.Data$LongValue/2873666547|java.lang.Long/4227064769|versionIndId|%%_#{opts[:version_ind_id_name]}%%|Standard final Exam|dirty|startTerm|endTerm|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%|collaboratorInfo|collaborators|kuali.atp.FA2008-2009|kuali.atp.SU2010-2011S2"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|38|8|9|10|11|0|8|12|10|-5|8|13|14|15|8|16|17|5|6|7|0|2|18|19|0|14|20|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|23|-19|-21|-12|-17|-1|-10|8|24|14|25|8|26|14|27|8|28|17|5|6|7|0|1|18|-15|17|5|6|7|0|3|8|29|17|5|6|7|0|1|18|-15|17|5|6|7|0|7|8|30|31|32|856619375|1288490188800|175000000|8|33|14|34|8|35|14|36|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|856619429|1288490188800|229000000|8|41|14|39|8|42|31|32|856619429|1288490188800|229000000|8|43|14|44|-45|-54|8|45|14|46|8|47|14|48|8|49|14|50|-41|-43|-37|-39|8|51|17|5|6|7|0|0|-37|-76|8|52|17|5|6|7|0|7|8|53|14|44|8|54|17|5|6|7|0|2|8|55|14|56|8|57|14|56|-82|-86|8|33|14|58|8|59|14|36|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1461728183|1288490188800|983000000|8|41|14|39|8|42|31|32|1461728183|1288490188800|983000000|8|43|14|44|-82|-98|8|45|14|60|8|49|14|61|-37|-80|-33|-35|-1|-31|8|62|14|63|8|64|17|5|6|7|0|1|18|-15|17|5|6|7|0|6|8|65|14|66|8|33|14|67|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|765890730|1288490188800|530000000|8|41|14|39|8|42|31|32|765890730|1288490188800|530000000|8|43|14|44|-126|-132|8|68|17|5|6|7|0|1|18|-15|14|66|-126|-148|8|49|14|69|8|21|17|5|6|7|0|1|8|49|17|5|6|7|0|1|8|22|14|70|-158|-160|-126|-156|-122|-124|-1|-120|8|71|17|5|6|7|0|0|-1|-166|8|72|17|5|6|7|0|1|8|57|14|73|-1|-170|8|74|17|5|6|7|0|3|8|75|14|76|8|77|78|19|2|8|21|17|5|6|7|0|1|8|75|17|5|6|7|0|1|8|22|14|79|-187|-189|-178|-185|-1|-176|8|80|17|5|6|7|0|1|8|81|17|5|6|7|0|0|-197|-199|-1|-195|8|82|17|5|6|7|0|0|-1|-203|8|83|17|5|6|7|0|1|18|-15|17|5|6|7|0|5|8|84|17|5|6|7|0|1|18|-15|17|5|6|7|0|9|8|85|14|86|8|87|17|5|6|7|0|3|8|88|14|89|8|90|14|91|8|21|17|5|6|7|0|1|8|90|17|5|6|7|0|1|8|22|14|92|-235|-237|-227|-233|-221|-225|8|93|78|19|100|8|74|17|5|6|7|0|3|8|75|14|94|8|77|78|19|13|8|21|17|5|6|7|0|1|8|75|17|5|6|7|0|1|8|22|14|95|-257|-259|-248|-255|-221|-246|8|33|14|96|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1461721583|1288490188800|383000000|8|41|14|39|8|42|31|32|1461728137|1288490188800|937000000|8|43|14|97|-221|-267|8|47|14|98|8|99|17|5|6|7|0|0|-221|-285|8|21|17|5|6|7|0|1|8|85|17|5|6|7|0|1|8|22|14|100|-291|-293|-221|-289|-217|-219|-213|-215|8|33|14|101|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1461721550|1288490188800|350000000|8|41|14|39|8|42|31|32|1461728090|1288490188800|890000000|8|43|14|97|-213|-301|8|47|14|98|8|49|14|102|-209|-211|-1|-207|8|103|17|5|6|7|0|2|18|-15|14|104|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|105|-329|-331|-323|-327|-1|-321|8|33|14|106|8|107|17|5|6|7|0|0|-1|-339|8|108|17|5|6|7|0|0|-1|-343|8|109|14|110|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1461706827|1288490188800|627000000|8|41|14|39|8|42|31|32|1461728059|1288490188800|859000000|8|43|14|111|-1|-349|8|112|10|-5|8|113|17|5|6|7|0|0|-1|-367|8|114|10|-5|8|47|14|98|8|115|14|116|8|117|17|5|6|7|0|2|18|-15|14|118|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|119|-385|-387|-379|-383|-1|-377|8|120|14|121|8|49|14|122|8|99|17|5|6|7|0|2|18|-15|14|123|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|124|-405|-407|-399|-403|-1|-397|8|125|17|5|6|7|0|2|18|-15|14|126|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|127|-421|-423|-415|-419|-1|-413|8|128|17|5|6|7|0|0|-1|-429|8|129|17|5|6|7|0|2|8|130|131|132|1|0|8|133|14|134|-1|-433|8|21|17|5|6|7|0|3|8|115|17|5|6|7|0|1|8|22|14|116|-444|-446|8|13|17|5|6|7|0|1|8|22|14|135|-444|-452|8|136|17|5|6|7|0|2|8|137|10|11|1|8|138|10|-464|-444|-458|-1|-442|8|139|17|5|6|7|0|10|8|33|14|140|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1461706968|1288490188800|768000000|8|41|14|39|8|42|31|32|1461729378|1288490188800|178000000|8|43|14|89|-469|-473|8|45|14|63|8|141|17|5|6|7|0|1|18|-15|14|106|-469|-491|8|142|14|143|8|144|17|5|6|7|0|0|-469|-499|8|145|17|5|6|7|0|0|-469|-503|8|146|14|147|8|49|14|148|8|149|14|150|-1|-467|8|151|17|5|6|7|0|1|8|152|17|5|6|7|0|0|-515|-517|-1|-513|-462|14|153|-465|14|154|0|0|"
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
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    
    # Financials
    # $100 lab fee
    # Admin org 100% rev and exp

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.feeType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.rateType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    # Revenue - Admin Org
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
    
    # Expense - Admin Org
    for i in 1..admin_org.length
      if(i == admin_org.length)
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|15|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{admin_org[0..itr]}|org.queryParam.orgOptionalType|java.lang.String/2004016611|kuali.org.Department|kuali.org.College|org.search.generic|1|2|3|4|1|5|5|0|6|0|7|2|8|9|0|10|8|11|7|2|12|13|12|14|0|15|0|0|0|"
          },
          {
            :dyn_variables => [
                {"name" => opts[:admin_dep_var_name], "regexp" => opts[:admin_dep_var_regexp]}
              ]
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
    
    @request.add("DEBUG/admin_dep_var_name/%%_#{opts[:admin_dep_var_name]}%%", {}, {'subst' => 'true'})


    # Save & Continue
    contents1 = "5|0|169|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B7AC280E8F60A1F596CF308193BB3CE3|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|passFail|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|audit|finalExamStatus|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|STD|campusLocations|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|AL|_runtimeData|id-translation|All|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|loCategoryInfoList|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|id|%%_#{opts[:lo_category_id_var_name]}%%|loRepository|kuali.loRepository.key.singleUse|metaInfo|createId|#{propose_person}|createTime|updateId|updateTime|versionInd|0|name|#{opts[:lo_category]}|state|active|type|loCategoryType.subject|loDisplayInfoList|loInfo|sequence|desc|formatted|#{opts[:lo_cat_text]}|plain|%%_#{opts[:lo_category_var_name]}%%|loRepositoryKey|1|SINGLE USE LO|kuali.lo.type.singleUse|courseTitle|#{opts[:course_title]}|creditOptions|fixedCreditValue|10|kuali.creditType.credit.degree.10|resultValues|kuali.resultComponentType.credit.degree.fixed|Credits, Fixed|crossListings|descr|#{opts[:course_description]}|duration|atpDurationTypeKey|kuali.atp.duration.Semester|timeQuantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|Semester|endTerm|kuali.atp.SU2010-2011S2|expenditure|affiliatedOrgs|percentage|org.kuali.student.core.assembly.data.Data$LongValue/2873666547|java.lang.Long/4227064769|dirty|orgId|created|28|fees|feeType|rateType|kuali.enum.type.feeTypes.labFee|fixedRateFee|feeAmounts|currencyQuantity|formats|activities|activityType|kuali.lu.type.activity.Lab|contactHours|unitQuantity|5|unitType|kuali.atp.duration.week|per week|defaultEnrollmentEstimate|kuali.atp.duration.Week|Week|%%_#{opts[:atp_duration_week_var_name]}%%|2|draft|unitsContentOwner|Lab|%%_#{opts[:lab_var_name]}%%|kuali.lu.type.CreditCourseFormatShell|gradingOptions|kuali.resultComponent.grade.letter|Letter|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|joints|level|100|4|pilotCourse|revenues|specialTopicsCourse|startTerm|kuali.atp.FA2008-2009|subjectArea|#{opts[:subject_area]}|termsOffered|kuali.atp.season.Any|Any|transcriptTitle|#{opts[:course_short_title]}|kuali.lu.type.CreditCourse|58|#{oversight_department}|unitsDeployment|#{admin_org}|variations|versionInfo|sequenceNumber|versionIndId|%%_#{opts[:version_ind_id_name]}%%|Fall Semester of 2008|Second Summer Session of 2011|Standard final Exam|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|6|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%|collaboratorInfo|collaborators|feeJustification|"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|39|8|9|10|11|0|8|12|10|-5|8|13|14|15|8|16|17|5|6|7|0|2|18|19|0|14|20|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|23|-19|-21|-12|-17|-1|-10|8|24|14|25|8|26|14|27|8|28|17|5|6|7|0|1|18|-15|17|5|6|7|0|3|8|29|17|5|6|7|0|1|18|-15|17|5|6|7|0|7|8|30|31|32|856619375|1288490188800|175000000|8|33|14|34|8|35|14|36|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|856619429|1288490188800|229000000|8|41|14|39|8|42|31|32|856619429|1288490188800|229000000|8|43|14|44|-45|-54|8|45|14|46|8|47|14|48|8|49|14|50|-41|-43|-37|-39|8|51|17|5|6|7|0|0|-37|-76|8|52|17|5|6|7|0|7|8|53|14|44|8|54|17|5|6|7|0|2|8|55|14|56|8|57|14|56|-82|-86|8|33|14|58|8|59|14|36|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1523483927|1288490188800|727000000|8|41|14|39|8|42|31|32|1523486970|1288490188800|770000000|8|43|14|60|-82|-98|8|45|14|61|8|49|14|62|-37|-80|-33|-35|-1|-31|8|63|14|64|8|65|17|5|6|7|0|1|18|-15|17|5|6|7|0|6|8|66|14|67|8|33|14|68|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|765890730|1288490188800|530000000|8|41|14|39|8|42|31|32|765890730|1288490188800|530000000|8|43|14|44|-126|-132|8|69|17|5|6|7|0|1|18|-15|14|67|-126|-148|8|49|14|70|8|21|17|5|6|7|0|1|8|49|17|5|6|7|0|1|8|22|14|71|-158|-160|-126|-156|-122|-124|-1|-120|8|72|17|5|6|7|0|0|-1|-166|8|73|17|5|6|7|0|1|8|57|14|74|-1|-170|8|75|17|5|6|7|0|3|8|76|14|77|8|78|79|19|2|8|21|17|5|6|7|0|1|8|76|17|5|6|7|0|1|8|22|14|80|-187|-189|-178|-185|-1|-176|8|81|14|82|8|83|17|5|6|7|0|1|8|84|17|5|6|7|0|1|18|-15|17|5|6|7|0|3|8|85|86|87|100|0|8|21|17|5|6|7|0|3|8|88|17|5|6|7|0|2|-209|10|11|1|8|89|10|-221|-214|-216|8|90|10|-221|-212|17|5|6|7|0|1|-216|17|5|6|7|0|1|-224|10|-221|-227|-216|-214|-212|-207|-212|-222|14|91|-203|-205|-199|-201|-1|-197|8|92|17|5|6|7|0|1|18|-15|17|5|6|7|0|4|-212|17|5|6|7|0|3|-216|17|5|6|7|0|2|8|93|10|-221|8|94|10|-221|-243|-216|8|90|10|-221|-212|17|5|6|7|0|1|-216|17|5|6|7|0|1|-252|10|-221|-255|-216|-243|-212|-240|-212|-248|14|95|-250|14|96|8|97|17|5|6|7|0|1|18|-15|17|5|6|7|0|2|8|98|79|19|100|-212|17|5|6|7|0|1|-216|17|5|6|7|0|1|-271|10|-221|-275|-216|-269|-212|-265|-267|-240|-263|-236|-238|-1|-234|8|99|17|5|6|7|0|1|18|-15|17|5|6|7|0|5|8|100|17|5|6|7|0|1|18|-15|17|5|6|7|0|9|8|101|14|102|8|103|17|5|6|7|0|3|8|104|14|105|8|106|14|107|8|21|17|5|6|7|0|1|8|106|17|5|6|7|0|1|8|22|14|108|-309|-311|-301|-307|-295|-299|8|109|79|-273|8|75|17|5|6|7|0|3|8|76|14|110|8|78|79|19|13|8|21|17|5|6|7|0|1|8|76|17|5|6|7|0|1|8|22|14|111|-330|-332|-321|-328|-295|-319|8|33|14|112|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1523479110|1288490188800|910000000|8|41|14|39|8|42|31|32|1523486928|1288490188800|728000000|8|43|14|113|-295|-340|8|47|14|114|8|115|17|5|6|7|0|0|-295|-358|8|21|17|5|6|7|0|1|8|101|17|5|6|7|0|1|8|22|14|116|-364|-366|-295|-362|-291|-293|-287|-289|8|33|14|117|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1523479096|1288490188800|896000000|8|41|14|39|8|42|31|32|1523486904|1288490188800|704000000|8|43|14|113|-287|-374|8|47|14|114|8|49|14|118|-283|-285|-1|-281|8|119|17|5|6|7|0|2|18|-15|14|120|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|121|-402|-404|-396|-400|-1|-394|8|33|14|122|8|123|17|5|6|7|0|0|-1|-412|8|124|17|5|6|7|0|0|-1|-416|8|125|14|126|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1523469878|1288490188800|678000000|8|41|14|39|8|42|31|32|1523486879|1288490188800|679000000|8|43|14|127|-1|-422|8|128|10|-5|8|129|17|5|6|7|0|1|18|-15|17|5|6|7|0|2|8|84|17|5|6|7|0|1|18|-15|17|5|6|7|0|3|8|85|86|-211|-212|17|5|6|7|0|1|-216|17|5|6|7|0|2|-456|10|-221|8|89|10|-221|-459|-216|-454|-212|-465|14|91|-450|-452|-446|-448|8|21|17|5|6|7|0|2|8|90|10|-221|-212|17|5|6|7|0|1|-216|17|5|6|7|0|1|-472|10|-221|-475|-216|-470|-212|-446|-468|-442|-444|-1|-440|8|130|10|-5|8|131|14|132|8|47|14|114|8|133|14|134|8|135|17|5|6|7|0|2|18|-15|14|136|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|137|-497|-499|-491|-495|-1|-489|8|138|14|139|8|49|14|140|8|115|17|5|6|7|0|2|18|-15|14|141|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|142|-517|-519|-511|-515|-1|-509|8|143|17|5|6|7|0|2|18|-15|14|91|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|144|-533|-535|-527|-531|-1|-525|8|145|17|5|6|7|0|0|-1|-541|8|146|17|5|6|7|0|2|8|147|86|87|1|0|8|148|14|149|-1|-545|8|21|17|5|6|7|0|4|8|131|17|5|6|7|0|1|8|22|14|150|-556|-558|8|133|17|5|6|7|0|1|8|22|14|134|-556|-564|8|81|17|5|6|7|0|1|8|22|14|151|-556|-570|8|13|17|5|6|7|0|1|8|22|14|152|-556|-576|-1|-554|8|153|17|5|6|7|0|10|8|33|14|154|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1523469958|1288490188800|758000000|8|41|14|39|8|42|31|32|1523487643|1288490188800|443000000|8|43|14|155|-584|-588|8|45|14|64|8|156|17|5|6|7|0|1|18|-15|14|122|-584|-606|8|157|14|158|8|159|17|5|6|7|0|0|-584|-614|8|160|17|5|6|7|0|0|-584|-618|8|161|14|162|8|49|14|163|8|164|14|165|-1|-582|8|166|17|5|6|7|0|1|8|167|17|5|6|7|0|0|-630|-632|-1|-628|8|168|17|5|6|7|0|2|8|57|14|169|-212|17|5|6|7|0|1|-216|17|5|6|7|0|1|-640|10|-221|-643|-216|-638|-212|-1|-636|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        'subst' => 'true',
        :dyn_variables => [
          {"name" => opts[:affliated_orgs_id_name], "regexp" => opts[:affliated_orgs_id_regexp]},
          {"name" => opts[:lab_fee_id_name], "regexp" => opts[:lab_fee_id_regexp]},
          {"name" => opts[:revenues_id_name], "regexp" => opts[:revenues_id_regexp]},
          {"name" => opts[:revenue_id_name], "regexp" => opts[:revenue_id_regexp]}
        ]
      }
    )

    @request.add("DEBUG/affliated_orgs_id_name/%%_#{opts[:affliated_orgs_id_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/lab_fee_id_name/%%_#{opts[:lab_fee_id_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/revenues_id_name/%%_#{opts[:revenues_id_name]}%%", {}, {'subst' => 'true'})
    @request.add("DEBUG/revenue_id_name/%%_#{opts[:revenue_id_name]}%%", {}, {'subst' => 'true'})

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.feeType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.rateType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.feeType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.fee.rateType|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|isAuthorizedAddReviewer|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|isAuthorizedRemoveReviewers|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    
    #
    # Authors and Collaborators
    #
    
    # Collaborator seach
    for i in 1..opts[:collaborator].length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|11|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|person.queryParam.personGivenName|#{opts[:collaborator][0..itr]}|person.search.personQuickViewByGivenName|1|2|3|4|1|5|5|0|6|0|7|1|8|9|0|10|11|0|0|0|"
        }    
      )    
    end
    
    # Save & Continue
    contents1 = "5|0|188|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|B7AC280E8F60A1F596CF308193BB3CE3|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|passFail|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|audit|finalExamStatus|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|STD|campusLocations|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|AL|_runtimeData|id-translation|All|code|#{opts[:subject_area]}#{opts[:course_suffix]}|courseNumberSuffix|#{opts[:course_suffix]}|courseSpecificLOs|loCategoryInfoList|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|id|%%_#{opts[:lo_category_id_var_name]}%%|loRepository|kuali.loRepository.key.singleUse|metaInfo|createId|#{propose_person}|createTime|updateId|updateTime|versionInd|0|name|#{opts[:lo_category]}|state|active|type|loCategoryType.subject|loDisplayInfoList|loInfo|sequence|desc|formatted|#{opts[:lo_cat_text]}|plain|%%_#{opts[:lo_category_var_name]}%%|loRepositoryKey|2|SINGLE USE LO|kuali.lo.type.singleUse|courseTitle|#{opts[:course_title]}|creditOptions|fixedCreditValue|10|kuali.creditType.credit.degree.10|resultValues|kuali.resultComponentType.credit.degree.fixed|Credits, Fixed|crossListings|descr|#{opts[:course_description]}|duration|atpDurationTypeKey|kuali.atp.duration.Semester|timeQuantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|Semester|endTerm|kuali.atp.SU2010-2011S2|expenditure|affiliatedOrgs|%%_#{opts[:affliated_orgs_id_name]}%%|orgId|%%_#{opts[:admin_dep_var_name]}%%|percentage|org.kuali.student.core.assembly.data.Data$LongValue/2873666547|java.lang.Long/4227064769|#{admin_org}|feeJustification|fees|feeAmounts|currencyQuantity|currencyTypeKey|kuali.currency.type.usdollars.cents|feeType|kuali.enum.type.feeTypes.labFee|%%_#{opts[:lab_fee_id_name]}%%|1|rateType|fixedRateFee|Fixed Rate Fee|Laboratory Fee|formats|activities|activityType|kuali.lu.type.activity.Lab|contactHours|unitQuantity|5|unitType|kuali.atp.duration.week|per week|defaultEnrollmentEstimate|kuali.atp.duration.Week|Week|%%_#{opts[:atp_duration_week_var_name]}%%|3|draft|unitsContentOwner|Lab|%%_#{opts[:lab_var_name]}%%|kuali.lu.type.CreditCourseFormatShell|gradingOptions|kuali.resultComponent.grade.letter|Letter|%%_#{opts[:clu_ref_dyn_var_name]}%%|instructors|joints|level|100|pilotCourse|revenues|%%_#{opts[:revenues_id_name]}%%|REVENUE|%%_#{opts[:revenue_id_name]}%%|specialTopicsCourse|startTerm|kuali.atp.FA2008-2009|subjectArea|#{opts[:subject_area]}|termsOffered|kuali.atp.season.Any|Any|transcriptTitle|#{opts[:course_short_title]}|kuali.lu.type.CreditCourse|58|#{oversight_department}|unitsDeployment|variations|versionInfo|sequenceNumber|versionIndId|%%_#{opts[:version_ind_id_name]}%%|Fall Semester of 2008|Second Summer Session of 2011|Standard final Exam|proposal|%%_#{opts[:proposal_dyn_var_name]}%%|7|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|#{opts[:course_rationale]}|kuali.proposal.type.course.create|workflowId|%%_#{opts[:proposal_doc_id_var_name]}%%|collaboratorInfo|collaborators|principalId|#{opts[:collaborator]}|permission|KS-SYS~Edit Document|action|F|firstName|(#{opts[:collaborator]})|lastName||actionRequestStatus|New|author"
    contents2 = "|1|2|3|4|1|5|5|6|7|0|39|8|9|10|11|0|8|12|10|-5|8|13|14|15|8|16|17|5|6|7|0|2|18|19|0|14|20|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|23|-19|-21|-12|-17|-1|-10|8|24|14|25|8|26|14|27|8|28|17|5|6|7|0|1|18|-15|17|5|6|7|0|3|8|29|17|5|6|7|0|1|18|-15|17|5|6|7|0|7|8|30|31|32|856619375|1288490188800|175000000|8|33|14|34|8|35|14|36|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|856619429|1288490188800|229000000|8|41|14|39|8|42|31|32|856619429|1288490188800|229000000|8|43|14|44|-45|-54|8|45|14|46|8|47|14|48|8|49|14|50|-41|-43|-37|-39|8|51|17|5|6|7|0|0|-37|-76|8|52|17|5|6|7|0|7|8|53|14|44|8|54|17|5|6|7|0|2|8|55|14|56|8|57|14|56|-82|-86|8|33|14|58|8|59|14|36|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1524693526|1288490188800|326000000|8|41|14|39|8|42|31|32|1524705387|1288490188800|187000000|8|43|14|60|-82|-98|8|45|14|61|8|49|14|62|-37|-80|-33|-35|-1|-31|8|63|14|64|8|65|17|5|6|7|0|1|18|-15|17|5|6|7|0|6|8|66|14|67|8|33|14|68|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|765890730|1288490188800|530000000|8|41|14|39|8|42|31|32|765890730|1288490188800|530000000|8|43|14|44|-126|-132|8|69|17|5|6|7|0|1|18|-15|14|67|-126|-148|8|49|14|70|8|21|17|5|6|7|0|1|8|49|17|5|6|7|0|1|8|22|14|71|-158|-160|-126|-156|-122|-124|-1|-120|8|72|17|5|6|7|0|0|-1|-166|8|73|17|5|6|7|0|1|8|57|14|74|-1|-170|8|75|17|5|6|7|0|3|8|76|14|77|8|78|79|19|2|8|21|17|5|6|7|0|1|8|76|17|5|6|7|0|1|8|22|14|80|-187|-189|-178|-185|-1|-176|8|81|14|82|8|83|17|5|6|7|0|1|8|84|17|5|6|7|0|1|18|-15|17|5|6|7|0|4|8|33|14|85|8|86|14|87|8|88|89|90|100|0|8|21|17|5|6|7|0|1|8|86|17|5|6|7|0|1|8|22|14|91|-218|-220|-207|-216|-203|-205|-199|-201|-1|-197|8|92|17|5|6|7|0|0|-1|-226|8|93|17|5|6|7|0|1|18|-15|17|5|6|7|0|6|8|94|17|5|6|7|0|1|18|-15|17|5|6|7|0|2|8|95|79|19|100|8|96|14|97|-240|-242|-236|-238|8|98|14|99|8|33|14|100|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1524705309|1288490188800|109000000|8|41|14|39|8|42|31|32|1524705310|1288490188800|110000000|8|43|14|101|-236|-255|8|102|14|103|8|21|17|5|6|7|0|2|8|102|17|5|6|7|0|1|8|22|14|104|-275|-277|8|98|17|5|6|7|0|1|8|22|14|105|-275|-283|-236|-273|-232|-234|-1|-230|8|106|17|5|6|7|0|1|18|-15|17|5|6|7|0|5|8|107|17|5|6|7|0|1|18|-15|17|5|6|7|0|9|8|108|14|109|8|110|17|5|6|7|0|3|8|111|14|112|8|113|14|114|8|21|17|5|6|7|0|1|8|113|17|5|6|7|0|1|8|22|14|115|-317|-319|-309|-315|-303|-307|8|116|79|-248|8|75|17|5|6|7|0|3|8|76|14|117|8|78|79|19|13|8|21|17|5|6|7|0|1|8|76|17|5|6|7|0|1|8|22|14|118|-338|-340|-329|-336|-303|-327|8|33|14|119|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1524689065|1288490188800|865000000|8|41|14|39|8|42|31|32|1524705357|1288490188800|157000000|8|43|14|120|-303|-348|8|47|14|121|8|122|17|5|6|7|0|0|-303|-366|8|21|17|5|6|7|0|1|8|108|17|5|6|7|0|1|8|22|14|123|-372|-374|-303|-370|-299|-301|-295|-297|8|33|14|124|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1524689052|1288490188800|852000000|8|41|14|39|8|42|31|32|1524705343|1288490188800|143000000|8|43|14|120|-295|-382|8|47|14|121|8|49|14|125|-291|-293|-1|-289|8|126|17|5|6|7|0|2|18|-15|14|127|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|128|-410|-412|-404|-408|-1|-402|8|33|14|129|8|130|17|5|6|7|0|0|-1|-420|8|131|17|5|6|7|0|0|-1|-424|8|132|14|133|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1524677081|1288490188800|881000000|8|41|14|39|8|42|31|32|1524705310|1288490188800|110000000|8|43|14|112|-1|-430|8|134|10|-5|8|135|17|5|6|7|0|1|18|-15|17|5|6|7|0|4|8|84|17|5|6|7|0|1|18|-15|17|5|6|7|0|4|8|33|14|136|8|86|14|87|8|88|89|90|100|0|8|21|17|5|6|7|0|1|8|86|17|5|6|7|0|1|8|22|14|91|-473|-475|-462|-471|-458|-460|-454|-456|8|98|14|137|8|33|14|138|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1524705308|1288490188800|108000000|8|41|14|39|8|42|31|32|1524705310|1288490188800|110000000|8|43|14|101|-454|-485|-450|-452|-1|-448|8|139|10|-5|8|140|14|141|8|47|14|121|8|142|14|143|8|144|17|5|6|7|0|2|18|-15|14|145|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|146|-517|-519|-511|-515|-1|-509|8|147|14|148|8|49|14|149|8|122|17|5|6|7|0|2|18|-15|14|150|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|151|-537|-539|-531|-535|-1|-529|8|152|17|5|6|7|0|2|18|-15|14|87|8|21|17|5|6|7|0|1|18|-15|17|5|6|7|0|1|8|22|14|91|-553|-555|-547|-551|-1|-545|8|153|17|5|6|7|0|0|-1|-561|8|154|17|5|6|7|0|2|8|155|89|90|1|0|8|156|14|157|-1|-565|8|21|17|5|6|7|0|4|8|140|17|5|6|7|0|1|8|22|14|158|-576|-578|8|142|17|5|6|7|0|1|8|22|14|143|-576|-584|8|81|17|5|6|7|0|1|8|22|14|159|-576|-590|8|13|17|5|6|7|0|1|8|22|14|160|-576|-596|-1|-574|8|161|17|5|6|7|0|10|8|33|14|162|8|37|17|5|6|7|0|5|8|38|14|39|8|40|31|32|1524677135|1288490188800|935000000|8|41|14|39|8|42|31|32|1524706204|1288490188800|4000000|8|43|14|163|-604|-608|8|45|14|64|8|164|17|5|6|7|0|1|18|-15|14|129|-604|-626|8|165|14|166|8|167|17|5|6|7|0|0|-604|-634|8|168|17|5|6|7|0|0|-604|-638|8|169|14|170|8|49|14|171|8|172|14|173|-1|-602|8|174|17|5|6|7|0|1|8|175|17|5|6|7|0|1|18|-15|17|5|6|7|0|7|8|176|14|177|8|178|14|179|8|180|14|181|8|182|14|183|8|184|14|185|8|186|14|187|8|188|10|-5|-654|-656|-650|-652|-1|-648|0|0|"
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
    
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/DocumentRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|376CE42A8F5B579BDA2D82CC7CB4F038|org.kuali.student.common.ui.client.service.DocumentRpcService|isAuthorizedUploadDocuments|java.lang.String/2004016611|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/DocumentRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|376CE42A8F5B579BDA2D82CC7CB4F038|org.kuali.student.common.ui.client.service.DocumentRpcService|isAuthorizedUploadDocuments|java.lang.String/2004016611|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/DocumentRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|376CE42A8F5B579BDA2D82CC7CB4F038|org.kuali.student.common.ui.client.service.DocumentRpcService|getRefDocIdsForRef|java.lang.String/2004016611|kuali.org.RefObjectType.ProposalInfo|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|2|5|5|6|7|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/DocumentRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|376CE42A8F5B579BDA2D82CC7CB4F038|org.kuali.student.common.ui.client.service.DocumentRpcService|getRefDocIdsForRef|java.lang.String/2004016611|kuali.org.RefObjectType.ProposalInfo|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|2|5|5|6|7|"
      },
      {
        'subst' => 'true'
      }
    )
    
    #
    # Support Documents
    # Nothing uploaded
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/statementRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|8570183BC24C0249EDDAE7E4CD6EC500|org.kuali.student.lum.program.client.rpc.StatementRpcService|getStatementTypesForStatementTypeForCourse|java.lang.String/2004016611|kuali.statement.type.course|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/statementRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|8570183BC24C0249EDDAE7E4CD6EC500|org.kuali.student.lum.program.client.rpc.StatementRpcService|getStatementTypesForStatementTypeForCourse|java.lang.String/2004016611|kuali.statement.type.course|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/statementRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|8570183BC24C0249EDDAE7E4CD6EC500|org.kuali.student.lum.program.client.rpc.StatementRpcService|getStatementTypesForStatementTypeForCourse|java.lang.String/2004016611|kuali.statement.type.course|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/statementRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|8570183BC24C0249EDDAE7E4CD6EC500|org.kuali.student.lum.program.client.rpc.StatementRpcService|getStatementTypesForStatementTypeForCourse|java.lang.String/2004016611|kuali.statement.type.course|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/statementRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|8570183BC24C0249EDDAE7E4CD6EC500|org.kuali.student.lum.program.client.rpc.StatementRpcService|getStatementTypesForStatementTypeForCourse|java.lang.String/2004016611|kuali.statement.type.course|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/statementRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|8570183BC24C0249EDDAE7E4CD6EC500|org.kuali.student.lum.program.client.rpc.StatementRpcService|getStatementTypesForStatementTypeForCourse|java.lang.String/2004016611|kuali.statement.type.course|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/DocumentRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|376CE42A8F5B579BDA2D82CC7CB4F038|org.kuali.student.common.ui.client.service.DocumentRpcService|getRefDocIdsForRef|java.lang.String/2004016611|kuali.org.RefObjectType.ProposalInfo|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|2|5|5|6|7|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CourseRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|A1805F52985BBCCC04EEBC6DDFEA8145|org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService|getCourseStatements|java.lang.String/2004016611|%%_#{opts[:clu_ref_dyn_var_name]}%%|KUALI.RULE|en|1|2|3|4|3|5|5|5|6|7|8|"
      },
      {
        'subst' => 'true'
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CourseRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|A1805F52985BBCCC04EEBC6DDFEA8145|org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService|getCourseStatements|java.lang.String/2004016611|%%_#{opts[:clu_ref_dyn_var_name]}%%|KUALI.RULE|en|1|2|3|4|3|5|5|5|6|7|8|"
      },
      {
        'subst' => 'true'
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CourseRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|A1805F52985BBCCC04EEBC6DDFEA8145|org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService|getCourseStatements|java.lang.String/2004016611|%%_#{opts[:clu_ref_dyn_var_name]}%%|KUALI.RULE|en|1|2|3|4|3|5|5|5|6|7|8|"
      },
      {
        'subst' => 'true'
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CourseRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|A1805F52985BBCCC04EEBC6DDFEA8145|org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService|getCourseStatements|java.lang.String/2004016611|%%_#{opts[:clu_ref_dyn_var_name]}%%|KUALI.RULE|en|1|2|3|4|3|5|5|5|6|7|8|"
      },
      {
        'subst' => 'true'
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CourseRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|A1805F52985BBCCC04EEBC6DDFEA8145|org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService|getCourseStatements|java.lang.String/2004016611|%%_#{opts[:clu_ref_dyn_var_name]}%%|KUALI.RULE|en|1|2|3|4|3|5|5|5|6|7|8|"
      },
      {
        'subst' => 'true'
      }
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CourseRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|A1805F52985BBCCC04EEBC6DDFEA8145|org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService|getCourseStatements|java.lang.String/2004016611|%%_#{opts[:clu_ref_dyn_var_name]}%%|KUALI.RULE|en|1|2|3|4|3|5|5|5|6|7|8|"
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
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|submitDocumentWithId|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {
          'subst' => 'true'
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {
          'subst' => 'true'
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|30A57CD4A0AA446B3B2B4CE52E1971E4|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:proposal_doc_id_var_name]}%%|1|2|3|4|1|5|6|"
        },
        {
          'subst' => 'true'
        }
      )
    end
    
    
    
  end
  
  
  def edit_proposal(proposal_name, opts={})
    
    defaults = {
      :proposal_id_dyn_var => 'ep_proposal_id',
      :proposal_id_regexp => 'proposal.resultColumn.proposalId\"\,\"\([^\"]+\)',
      #:proposal_dyn_var => 'ep_proposal',
      #:proposal_regexp => 'proposal\"\,\"\([^\"]+\)',
      :proposal_num_dyn_var => 'ep_proposal_num',
      :proposal_num_regexp => 'proposal\"\,\"[^\"]+\"\,\"\([^\"]+\)',
      :workflow_id_dyn_var => 'ep_workflow_id',
      :workflow_id_regexp => 'workflowId\"\,\"\([^\"]+\)',
      :id_translation_id_dyn_var => 'ep_id_translation_id',
      :id_translation_id_regexp => 'id-translation\"\,\"\([^\"]+\)',
      :code_dyn_var => 'ep_code',
      :code_regexp => 'code\"\,\"\([^\"]+\)',
      :course_num_suffix_dyn_var => 'ep_course_num_suffix',
      :course_num_suffix_regexp => 'courseNumberSuffix\"\,\"\([^\"]+\)',
      :lo_category_id_dyn_var => 'ep_lo_cat_id',
      :lo_category_id_regexp => 'expirationDate\"\,\"id\"\,\"\([^\"]+\)',
      :lo_category_dyn_var => 'ep_lo_cat',
      :lo_category_regexp => 'name\"\,\"\([^\"]+\)',
      :lo_cat_text_dyn_var => 'ep_lo_cat_text',
      :lo_cat_text_regexp => 'loInfo\"\,\"sequence\"\,\"0\"\,\"\([^\"]+\)',
      :lo_cat_id_dyn_var => 'ep_lo_cat_id',
      :lo_cat_id_regexp => '\([^\"]+\)\"\,\"loRepositoryKey',
      :create_id_dyn_var => 'ep_create_id',
      :create_id_regexp => 'createId\"\,\"\([^\"]+\)',
      :course_title_dyn_var => 'ep_course_title',
      :course_title_regexp => 'courseTitle\"\,\"\([^\"]+\)',
      :oversight_org_dyn_var => 'ep_oversight_org',
      :oversight_org_regexp => 'curriculumOversightOrgs\"\,\"[^\,]+\,\"\([^\"]+\)',
      :lab_fee_id_dyn_var => 'ep_lab_fee_id',
      :lab_fee_id_regexp => 'kuali.enum.type.feeTypes.labFee\"\,\"\([^\"]+\)',
      :atp_dur_week_id_dyn_var => 'ep_atp_dur_week_id',
      :atp_dur_week_id_regexp => 'kuali.atp.duration.Week\"\,\"Week\"\,\"\([^\"]+\)',
      :lab_id_dyn_var => 'ep_lab_id',
      :lab_id_regexp => 'Lab\"\,\"\([^\"]+\)',
      :grade_id_dyn_var => 'ep_grade_id',
      :grade_id_regexp => 'kuali.resultComponent.grade[^\,]+\,\"[^\,]+\,\"\([^\"]+\)',
      :person_id_dyn_var => 'ep_person_id',
      :person_id_regexp => 'personId\"\,\"\([^\"]+\)',
      :joints_dyn_var => 'ep_joints',
      :joints_regexp => 'joints\"\,\"\([^\"]+\)',
      :subject_area_dyn_var => 'ep_subject_area',
      :subject_area_regexp => 'subjectArea\"\,\"\([^\"]+\)',
      :title_dyn_var => 'ep_title',
      :title_regexp => 'proposal\"\,\"[^\"]+\"\,\"[^\"]+\"\,\"\([^\"]+\)',
      :rationale_dyn_var => 'ep_rationale',
      :rationale_regexp => 'rationale\"\,\"\([^\"]+\)',
      :modify_fields => {
        :course_information => {},
        :governance => {},
        :course_logistics => {},
        :learning_objectives => {},
        :active_dates => {},
        :financials => {}
      }
    }
    
    opts = defaults.merge(opts)
  
    # Search for proposal
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Integer/3438268394|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|proposal.queryParam.proposalOptionalName|#{proposal_name}|proposal.search.generic|proposal.resultColumn.proposalOptionalName|1|2|3|4|1|5|5|6|10|7|0|8|1|9|10|0|11|12|13|0|6|0|"
      },
      {
        :dyn_variables => [
          {"name" => opts[:proposal_id_dyn_var], "regexp" => opts[:proposal_id_regexp]}
        ]
      }
    )
    
    # Select
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|526F889935910B01B2508B535A13901E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|isAuthorized|org.kuali.student.core.rice.authorization.PermissionType/259370389|java.util.Map|java.util.HashMap/962170901|java.lang.String/2004016611|kualiStudentObjectWorkflowId|%%_#{opts[:proposal_id_dyn_var]}%%|1|2|3|4|2|5|6|5|1|7|1|8|9|8|10|"
      },
      {
        'subst' => 'true'
      }
    )
    
    @request.add_thinktime(2)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|526F889935910B01B2508B535A13901E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getMetadata|java.lang.String/2004016611|kualiStudentObjectWorkflowId|%%_#{opts[:proposal_id_dyn_var]}%%|1|2|3|4|2|5|5|6|7|"
      },{'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|526F889935910B01B2508B535A13901E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getData|java.lang.String/2004016611|%%_#{opts[:proposal_id_dyn_var]}%%|1|2|3|4|1|5|6|"
      },
      {
        'subst' => 'true',
        :dyn_variables => [
          {"name" => opts[:workflow_id_dyn_var], "regexp" => opts[:workflow_id_regexp]},
          {"name" => opts[:id_translation_id_dyn_var], "regexp" => opts[:id_translation_id_regexp]},
          {"name" => opts[:code_dyn_var], "regexp" => opts[:code_regexp]},
          {"name" => opts[:course_num_suffix_dyn_var], "regexp" => opts[:course_num_suffix_regexp]},
          {"name" => opts[:lo_category_id_dyn_var], "regexp" => opts[:lo_category_id_regexp]},
          {"name" => opts[:lo_category_dyn_var], "regexp" => opts[:lo_category_dyn_var]},
          {"name" => opts[:lo_cat_text_dyn_var], "regexp" => opts[:lo_cat_text_regexp]},
          {"name" => opts[:lo_cat_id_dyn_var], "regexp" => opts[:lo_cat_id_regexp]},
          {"name" => opts[:create_id_dyn_var], "regexp" => opts[:create_id_regexp]},
          {"name" => opts[:course_title_dyn_var], "regexp" => opts[:course_title_regexp]},
          {"name" => opts[:oversight_org_dyn_var], "regexp" => opts[:oversight_org_regexp]},
          {"name" => opts[:lab_fee_id_dyn_var], "regexp" => opts[:lab_fee_id_regexp]},
          {"name" => opts[:atp_dur_week_id_dyn_var], "regexp" => opts[:atp_dur_week_id_regexp]},
          {"name" => opts[:lab_id_dyn_var], "regexp" => opts[:lab_id_regexp]},
          {"name" => opts[:grade_id_dyn_var], "regexp" => opts[:grade_id_regexp]},
          {"name" => opts[:person_id_dyn_var], "regexp" => opts[:person_id_regexp]},
          {"name" => opts[:joints_dyn_var], "regexp" => opts[:joints_regexp]},
          {"name" => opts[:subject_area_dyn_var], "regexp" => opts[:subject_area_regexp]},
          {"name" => opts[:proposal_dyn_var], "regexp" => opts[:proposal_regexp]},
          {"name" => opts[:proposal_num_dyn_var], "regexp" => opts[:proposal_num_regexp]},
          {"name" => opts[:title_dyn_var], "regexp" => opts[:title_regexp]},
          {"name" => opts[:rationale_dyn_var], "regexp" => opts[:rationale_regexp]}
        ]
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
        'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|atp.search.atpSeasonTypes|1|2|3|4|1|5|5|0|0|6|0|7|0|0|0|"
      }
    )
    # DUPE
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
        'contents' => "5|0|18|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lrc.queryParam.resultComponent.type|kuali.resultComponentType.grade.finalGrade|lrc.queryParam.resultComponent.idRestrictionList|java.lang.String/2004016611|kuali.resultComponent.grade.letter|kuali.resultComponent.grade.satisfactory|kuali.resultComponent.grade.percentage|kuali.resultComponent.grade.recitalReview|kuali.resultComponent.grade.designReview|kuali.resultComponent.grade.completedNotation|lrc.search.resultComponent|1|2|3|4|1|5|5|0|0|6|2|7|8|0|9|7|10|6|6|11|12|11|13|11|14|11|15|11|16|11|17|0|18|0|0|0|"
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
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.finalExam.status|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|10|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|648421FAE6C751B6B3D6A2EC5262F586|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|enumeration.queryParam.enumerationType|kuali.lu.finalExam.status|enumeration.management.search|1|2|3|4|1|5|5|0|0|6|1|7|8|0|9|10|0|0|0|"
      }
    )
    
    @request.add_thinktime(2)
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:workflow_id_dyn_var]}%%|1|2|3|4|1|5|6|"
      },{'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:workflow_id_dyn_var]}%%|1|2|3|4|1|5|6|"
      },{'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:workflow_id_dyn_var]}%%|1|2|3|4|1|5|6|"
      },{'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:workflow_id_dyn_var]}%%|1|2|3|4|1|5|6|"
      },{'subst' => 'true'}
    )
    
    
    # Edit Proposal
    
    if(!opts[:modify_fields][:course_information].empty?)
      
      if(opts[:modify_fields][:course_information][:description])
        
        # Save changes
        contents1 = "5|0|159|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|526F889935910B01B2508B535A13901E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data/3119441076|org.kuali.student.core.assembly.data.Data|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|administeringOrgs|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|58|_runtimeData|id-translation|%%_#{opts[:id_translation_id_dyn_var]}%%|passFail|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|audit|finalExamStatus|STD|campusLocations|ALL|All|code|%%_#{opts[:code_dyn_var]}%%|courseNumberSuffix|%%_#{opts[:course_num_suffix_dyn_var]}%%|courseSpecificLOs|loCategoryInfoList|desc|formatted|&lt;p&gt;Desc&lt;/p&gt;|plain|Desc|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|expirationDate|id|%%_#{opts[:lo_category_id_dyn_var]}%%|loRepository|kuali.loRepository.key.singleUse|metaInfo|versionInd|1|name|%%_#{opts[:lo_category_dyn_var]}%%|state|active|type|loCategoryType.subject|loDisplayInfoList|loInfo|sequence|0|%%_#{opts[:lo_cat_text_dyn_var]}%%|%%_#{opts[:lo_cat_id_dyn_var]}%%|loRepositoryKey|createId|%%_#{opts[:create_id_dyn_var]}%%|createTime|updateId|updateTime|SINGLE USE LO|kuali.lo.type.singleUse|courseTitle|%%_#{opts[:course_title_dyn_var]}%%|creditOptions|fixedCreditValue|10|kuali.creditType.credit.degree.10|resultValues|kuali.resultComponentType.credit.degree.fixed|Credits, Fixed|crossListings|curriculumOversightOrgs|51|%%_#{opts[:oversight_org_dyn_var]}%%|descr|#{opts[:modify_fields][:course_information][:description]}|dirty|duration|atpDurationTypeKey|kuali.atp.duration.Year|timeQuantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|Year|expenditure|affiliatedOrgs|feeJustification|fees|feeAmounts|currencyQuantity|currencyTypeKey|kuali.currency.type.usdollars.cents|feeType|kuali.enum.type.feeTypes.labFee|%%_#{opts[:lab_fee_id_dyn_var]}%%|rateType|fixedRateFee|Fixed Rate Fee|Laboratory Fee|formats|activities|activityType|kuali.lu.type.activity.Lab|contactHours|unitQuantity|unitType|kuali.atp.duration.week|per week|defaultEnrollmentEstimate|kuali.atp.duration.Week|Week|%%_#{opts[:atp_dur_week_id_dyn_var]}%%|2|draft|Lab|%%_#{opts[:lab_id_dyn_var]}%%|kuali.lu.type.CreditCourseFormatShell|gradingOptions|kuali.resultComponent.grade.letter|Letter|%%_#{opts[:grade_id_dyn_var]}%%|instructors|personId|%%_#{opts[:person_id_dyn_var]}%%|joints|%%_#{opts[:joints_dyn_var]}%%|pilotCourse|revenues|specialTopicsCourse|subjectArea|%%_#{opts[:subject_area_dyn_var]}%%|termsOffered|kuali.atp.season.Any|Any|kuali.lu.type.CreditCourse|variations|Standard final Exam|transcriptTitle|proposal|%%_#{opts[:proposal_id_dyn_var]}%%|%%_#{opts[:proposal_num_dyn_var]}%%|%%_#{opts[:title_dyn_var]}%%|proposalReference|proposalReferenceType|kuali.proposal.referenceType.clu|proposerOrg|proposerPerson|rationale|%%_#{opts[:rationale_dyn_var]}%%|kuali.proposal.type.course.create|workflowId|%%_#{opts[:workflow_id_dyn_var]}%%|"
        contents2 = "|1|2|3|4|1|5|5|6|7|0|34|8|9|10|5|6|7|0|2|11|12|0|13|14|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|17|-12|-14|-5|-10|-1|-3|8|18|19|20|0|8|21|19|-22|8|22|13|23|8|24|10|5|6|7|0|2|11|-8|13|25|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|26|-35|-37|-29|-33|-1|-27|8|27|13|28|8|29|13|30|8|31|10|5|6|7|0|1|11|-8|10|5|6|7|0|3|8|32|10|5|6|7|0|1|11|-8|10|5|6|7|0|9|8|33|10|5|6|7|0|2|8|34|13|35|8|36|13|37|-61|-63|8|38|39|40|867724416|1198295875584|0|8|41|39|40|3896582272|1258425417728|0|8|42|13|43|8|44|13|45|8|46|10|5|6|7|0|1|8|47|13|48|-61|-81|8|49|13|50|8|51|13|52|8|53|13|54|-57|-59|-53|-55|8|55|10|5|6|7|0|0|-53|-93|8|56|10|5|6|7|0|7|8|57|13|58|8|33|10|5|6|7|0|2|8|34|13|59|8|36|13|59|-99|-103|8|42|13|60|8|61|13|45|8|46|10|5|6|7|0|5|8|62|13|63|8|64|39|40|3246181412|1284195221504|916000000|8|65|13|63|8|66|39|40|3246183944|1284195221504|448000000|8|47|13|48|-99|-115|8|49|13|67|8|53|13|68|-53|-97|-49|-51|-1|-47|8|69|13|70|8|71|10|5|6|7|0|1|11|-8|10|5|6|7|0|6|8|72|13|73|8|42|13|74|8|46|10|5|6|7|0|1|8|47|13|58|-143|-149|8|75|10|5|6|7|0|1|11|-8|13|73|-143|-155|8|53|13|76|8|15|10|5|6|7|0|1|8|53|10|5|6|7|0|1|8|16|13|77|-165|-167|-143|-163|-139|-141|-1|-137|8|78|10|5|6|7|0|0|-1|-173|8|79|10|5|6|7|0|2|11|-8|13|80|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|81|-185|-187|-179|-183|-1|-177|8|82|10|5|6|7|0|2|8|36|13|83|8|15|10|5|6|7|0|1|8|84|10|5|6|7|0|1|8|36|19|20|1|-201|-203|-195|-199|-1|-193|8|85|10|5|6|7|0|3|8|86|13|87|8|88|89|12|1|8|15|10|5|6|7|0|1|8|86|10|5|6|7|0|1|8|16|13|90|-221|-223|-212|-219|-1|-210|8|91|10|5|6|7|0|1|8|92|10|5|6|7|0|0|-231|-233|-1|-229|8|93|10|5|6|7|0|0|-1|-237|8|94|10|5|6|7|0|1|11|-8|10|5|6|7|0|5|8|95|10|5|6|7|0|1|11|-8|10|5|6|7|0|2|8|96|89|12|10|8|97|13|98|-251|-253|-247|-249|8|99|13|100|8|42|13|101|8|102|13|103|8|15|10|5|6|7|0|2|8|102|10|5|6|7|0|1|8|16|13|104|-270|-272|8|99|10|5|6|7|0|1|8|16|13|105|-270|-278|-247|-268|-243|-245|-1|-241|8|106|10|5|6|7|0|1|11|-8|10|5|6|7|0|5|8|107|10|5|6|7|0|1|11|-8|10|5|6|7|0|9|8|108|13|109|8|110|10|5|6|7|0|3|8|111|13|73|8|112|13|113|8|15|10|5|6|7|0|1|8|112|10|5|6|7|0|1|8|16|13|114|-312|-314|-304|-310|-298|-302|8|79|10|5|6|7|0|0|-298|-320|8|115|89|12|100|8|85|10|5|6|7|0|3|8|86|13|116|8|88|89|12|12|8|15|10|5|6|7|0|1|8|86|10|5|6|7|0|1|8|16|13|117|-338|-340|-329|-336|-298|-327|8|42|13|118|8|46|10|5|6|7|0|5|8|62|13|63|8|64|39|40|3246177449|1284195221504|953000000|8|65|13|63|8|66|39|40|3246183904|1284195221504|408000000|8|47|13|119|-298|-348|8|51|13|120|8|15|10|5|6|7|0|1|8|108|10|5|6|7|0|1|8|16|13|121|-368|-370|-298|-366|-294|-296|-290|-292|8|42|13|122|8|46|10|5|6|7|0|5|8|62|13|63|8|64|39|40|3246177416|1284195221504|920000000|8|65|13|63|8|66|39|40|3246183890|1284195221504|394000000|8|47|13|119|-290|-378|8|51|13|120|8|53|13|123|-286|-288|-1|-284|8|124|10|5|6|7|0|2|11|-8|13|125|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|126|-406|-408|-400|-404|-1|-398|8|42|13|127|8|128|10|5|6|7|0|1|11|-8|10|5|6|7|0|2|8|129|13|63|8|15|10|5|6|7|0|1|8|129|10|5|6|7|0|1|8|16|13|130|-428|-430|-422|-426|-418|-420|-1|8|128|8|131|10|5|6|7|0|0|-1|-437|8|46|10|5|6|7|0|5|8|62|13|63|8|64|39|40|3246166611|1284195221504|115000000|8|65|13|63|8|66|39|40|3246183834|1284195221504|338000000|8|47|13|132|-1|-441|8|133|19|-22|8|134|10|5|6|7|0|0|-1|-459|8|135|19|-22|8|51|13|120|8|136|13|137|8|138|10|5|6|7|0|2|11|-8|13|139|8|15|10|5|6|7|0|1|11|-8|10|5|6|7|0|1|8|16|13|140|-477|-479|-471|-475|-1|-469|8|53|13|141|8|142|10|5|6|7|0|0|-1|-487|8|15|10|5|6|7|0|3|8|136|10|5|6|7|0|1|8|16|13|137|-493|-495|8|22|10|5|6|7|0|1|8|16|13|143|-493|-501|-203|10|5|6|7|0|3|8|144|19|-209|8|128|19|-209|8|136|19|-209|-493|-203|-1|-491|8|145|10|5|6|7|0|10|8|42|13|146|8|46|10|5|6|7|0|5|8|62|13|63|8|64|39|40|3246166532|1284195221504|36000000|8|65|13|63|8|66|39|40|3246183375|1284195221504|879000000|8|47|13|147|-518|-522|8|49|13|148|8|149|10|5|6|7|0|1|11|-8|13|127|-518|-540|8|150|13|151|8|152|10|5|6|7|0|0|-518|-548|8|153|10|5|6|7|0|0|-518|-552|8|154|13|155|8|53|13|156|8|157|13|158|-1|-516|-510|13|159|0|0|"
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
        
      end
      
    end
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getActionsRequested|java.lang.String/2004016611|%%_#{opts[:workflow_id_dyn_var]}%%|1|2|3|4|1|5|6|"
      },{'subst' => 'true'}
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/WorkflowRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|D1DD59B8A92305DA33192DAC65F9F820|org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService|getDocumentStatus|java.lang.String/2004016611|%%_#{opts[:workflow_id_dyn_var]}%%|1|2|3|4|1|5|6|"
      },{'subst' => 'true'}
    )
    
    
    
    
    
    
  
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