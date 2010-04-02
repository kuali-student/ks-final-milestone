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
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp')
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SecurityRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|29F4EA1240F157649C12466F01F46F60|org.kuali.student.common.ui.client.service.SecurityRpcService|getPrincipalUsername|1|2|3|4|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|ks.rice.actionList.serviceAddress|1|2|3|4|1|5|6|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/clear.cache.gif')
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|ks.rice.docSearch.serviceAddress|1|2|3|4|1|5|6|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|lum.application.url|1|2|3|4|1|5|6|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|application.url|1|2|3|4|1|5|6|"
      }
    )
        
  end
  
  
  # Create a proposal
  # TODO: Org ID is hardcoded to Fisheries Department in requests, need to use dyn_variable
  #
  # Option: DEFAULT_VALUE
  # * 'mode': 'blank'
  def create_proposal(title, oversight_department, admin_org, propose_person, opts={})
    
    defaults = {
      :mode => 'blank',
      :submit => true,
      :append_unique_id => false, #tell tsung to append unique id on title
      :instructor => propose_person,
      :first_expected_offering => @request.config.directory["atp"]["name"],
      :subject_area => "PTCP",
      :course_suffix => "123",
      :course_short_title => "Perf Course",
      :course_title => "Performance Course",
      :course_description => "My fake description.",
      :course_rationale => "My fake rationale.",
      :lo_create => FALSE,
      :lo_category => "Test Category 2 - Subject", # Test Category 2 - Subject
      :lo_name => @request.config.directory["lo"]["name"],
      :admin_dep_var_name => "admin_dep_org_id",
      :admin_dep_var_regexp => 'org.resultColumn.orgId\"\,\"\([^\"]+\)',
      :proposal_dyn_var_name => "proposal_id",
      :proposal_dyn_var_regexp => '\"id\"\,[^\,]+\,\"\([^\"]+\)',
      :clu_ref_dyn_var_name => "clu_ref_id",
      :clu_ref_dyn_var_regexp => 'proposalReference&gt;\([^&lt;]+\)',
      :lu_dto_clu_format_dyn_var_name => "lu_dto_clu_format",
      :lu_dto_clu_format_dyn_var_regexp =>     'org.kuali.student.lum.lu.dto.CluInfo\"\,\"formats\"\,\"\([^\"]+\)',
      :lu_dto_clu_activities_dyn_var_name => "lu_dto_clu_activites",
      :lu_dto_clu_activities_dyn_var_regexp => 'org.kuali.student.lum.lu.dto.CluInfo\"\,\"formats\"\,\"[^\"]+\"\,\"0\"\,\"activities\"\,\"\([^\"]+\)',
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
    
    # Click Start blank proposal
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/clear.cache.gif')
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getMetadata|1|2|3|4|0|"
      }
    )
    
    # Duplicate - BUG ?
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getMetadata|1|2|3|4|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/clear.cache.gif')
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/scrollTableLoading.gif')
    
    # Pg1 - Governance
    
    # Curriculum oversight
    # AJAX popup while typing in org name
    for i in 1..oversight_department.length
      itr = i-1
      if(itr == oversight_department.length-1)
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{oversight_department[0..itr]}|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
          },
          {
            :dyn_variables => [{"name" => opts[:admin_dep_var_name], "regexp" => opts[:admin_dep_var_regexp]}]
          }                               
        )
        
        # Test the dyn variable
        #@request.add("/fake_url/admin_org_id/%%_#{opts[:admin_dep_var_name]}%%", {}, {'subst' => 'true'})
      else
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{oversight_department[0..itr]}|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
          }                                      
          )
      end
      
      # On first load these images
      if(itr == 0)
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/gwt/standard/images/corner.png')
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/gwt/standard/images/vborder.png')
      end
    end
    
    # Admin Org
    # AJAX popup while typing in org name
    for i in 1..admin_org.length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalLongName|#{admin_org[0..itr]}|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
        }                                      
      )
    end
    
    # Save proposal
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|30|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|course|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|_runtimeData|dirty|academicSubjectOrgs|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|campusLocations|department|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:admin_dep_var_name]}%%|AllCampuses|proposal|title|#{title}|proposerPerson|#{propose_person}|java.util.ArrayList/3821976829|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|4|10|13|12|8|5|9|0|1|10|14|12|8|5|9|0|3|10|15|16|17|1|10|18|16|-19|10|19|16|-19|-11|-13|-7|-9|-17|12|8|5|9|0|1|20|21|0|22|23|-7|10|15|-20|12|8|5|9|0|1|20|-28|22|24|-7|10|18|-22|22|23|-3|-5|10|25|12|8|5|9|0|3|10|26|22|27|-9|12|8|5|9|0|1|-13|12|8|5|9|0|2|-42|16|-19|10|28|16|-19|-45|-13|-40|-9|-51|12|8|5|9|0|1|20|-28|22|29|-40|-51|-3|-38|0|0|30|0|5|9|0|0|0|0|"
      },
      {
        'subst' => 'true',
        :dyn_variables => [{"name" => opts[:proposal_dyn_var_name], "regexp" => opts[:proposal_dyn_var_regexp]}]
      }                                 
    )
    
    # Test the dyn variable
    #@request.add("/fake_url/proposal_id/%%_#{opts[:proposal_dyn_var_name]}%%", {}, {'subst' => 'true'})
    
    # Get the clu ref id
    @request.add('/services/ProposalService',
      {
        'method' => 'POST',
        'content_type' => 'text/xml; charset=UTF-8',
        'contents' => "&lt;soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:prop='http://student.kuali.org/wsdl/proposal'&gt;&lt;soapenv:Header/&gt;&lt;soapenv:Body&gt;&lt;prop:getProposal&gt;&lt;proposalId&gt;%%_#{opts[:proposal_dyn_var_name]}%%&lt;/proposalId&gt;&lt;/prop:getProposal&gt;&lt;/soapenv:Body&gt;&lt;/soapenv:Envelope&gt;"
      },
      {
        :dyn_variables => [{"name" => opts[:clu_ref_dyn_var_name], "regexp" => opts[:clu_ref_dyn_var_regexp]}],
        'subst' => 'true'
      }
    )
    
    #@request.add("/fake_url/clu_ref_id/%%_#{opts[:clu_ref_dyn_var_name]}%%", {}, {'subst' => 'true'})
    
    
    # thinktime? trace had 7
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalId|%%_#{opts[:admin_dep_var_name]}%%|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
      }                                      
    )
    # Dupe
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalId|%%_#{opts[:admin_dep_var_name]}%%|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
      }                                      
    )
    # Dupe
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalId|%%_#{opts[:admin_dep_var_name]}%%|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
      }                                      
    )
    # Dupe
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalId|%%_#{opts[:admin_dep_var_name]}%%|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
      }                                      
    )
    
    # Save & Continue
    
    contents1 = "5|0|70|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|courseTitle|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|termsOffered|firstExpectedOffering||primaryInstructor|state|draft|subjectArea|transcriptTitle|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|AllCampuses|org.kuali.student.lum.lu.dto.CluInfo|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|fees|attributes|DeptOrgName|#{oversight_department}|CourseCode|nullnull|CourseType|Credit Course|OversightName|courseSpecificLOs|java.util.ArrayList/3821976829"
    contents2 = '|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|7|10|13|14|15|10|16|14|0|10|17|12|8|5|9|0|0|-7|-13|10|18|14|19|10|20|14|21|10|22|12|8|5|9|0|1|23|24|0|14|25|-7|-21|10|26|12|8|5|9|0|1|10|27|12|8|5|9|0|1|23|-26|12|8|5|9|0|3|10|28|14|29|10|13|14|15|10|30|14|31|-34|-36|-30|-32|-7|-28|-3|-5|10|32|12|8|5|9|0|19|10|13|14|25|10|33|14|0|10|34|14|0|10|35|36|0|10|37|36|0|10|38|14|39|10|40|12|8|5|9|0|2|10|41|14|0|10|42|14|0|-48|-62|10|43|12|8|5|9|0|0|-48|-70|10|44|14|45|10|46|14|0|10|47|14|48|10|49|14|0|10|50|14|0|10|51|14|52|10|53|12|8|5|9|0|1|23|24|0|14|39|-48|10|53|10|54|12|8|5|9|0|1|23|-26|14|55|-48|-94|10|26|12|8|5|9|0|2|10|27|12|8|5|9|0|1|23|-26|12|8|5|9|0|3|10|28|14|56|10|13|14|25|10|30|14|31|-106|-108|-102|-104|10|57|12|8|5|9|0|2|10|53|58|59|1|10|38|58|-124|-102|-118|-48|-100|10|60|12|8|5|9|0|1|23|-26|12|8|5|9|0|1|10|61|12|8|5|9|0|4|10|62|14|63|10|64|14|65|10|66|14|67|10|68|14|63|-133|10|61|-129|-131|-48|-127|10|69|12|8|5|9|0|0|-48|-148|-3|-46|0|0|70|0|5|9|0|0|0|0|'
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {'subst' => 'true'}
    )
    
    
    
    
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalId|%%_#{opts[:admin_dep_var_name]}%%|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
      }                                      
    )
    
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalId|%%_#{opts[:admin_dep_var_name]}%%|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
      }                                      
    )
    
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalId|%%_#{opts[:admin_dep_var_name]}%%|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
      }                                      
    )
    
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|org.queryParam.orgOptionalId|%%_#{opts[:admin_dep_var_name]}%%|org.queryParam.orgOptionalType|kuali.org.Department|org.search.generic|1|2|3|4|1|5|6|0|7|0|8|2|9|10|0|11|9|12|0|13|14|0|0|0|"
      }                                      
    )
    
    
    # Pg2 - Course logistics
    
    # Expected Offering
    for i in 1..opts[:first_expected_offering].length
      itr = i-1
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|12|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|atp.advancedAtpSearchParam.atpShortName|#{opts[:first_expected_offering][0..itr]}|atp.search.advancedAtpSearch|1|2|3|4|1|5|6|0|7|0|8|1|9|10|0|11|12|0|0|0|"
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
          'contents' => "5|0|12|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|person.queryParam.personGivenName|#{opts[:instructor][0..itr]}|person.search.personQuickViewByGivenName|1|2|3|4|1|5|6|0|7|0|8|1|9|10|0|11|12|0|0|0|"
        }                                      
      )
    end
    
    # Save
    contents1 = "5|0|89|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|courseTitle|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|termsOffered|firstExpectedOffering|kuali.atp.CLASS2012Y1|primaryInstructor|#{opts[:instructor]}|state|draft|subjectArea|transcriptTitle|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|AllCampuses|org.kuali.student.lum.lu.dto.CluInfo|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|fees|attributes|DeptOrgName|#{oversight_department}|CourseCode|nullnull|CourseType|Credit Course|OversightName|courseSpecificLOs|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|atpType.duration.week|formats|deleted|activities|activityType|kuali.lu.type.activity.Lab|defaultEnrollmentEstimate|created|timeUnit|atpType.duration.month|contactHours|hrs|per|atpType.duration.weekly|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|7|10|13|14|15|10|16|14|0|10|17|12|8|5|9|0|0|-7|-13|10|18|14|19|10|20|14|21|10|22|12|8|5|9|0|1|23|24|0|14|25|-7|-21|10|26|12|8|5|9|0|1|10|27|12|8|5|9|0|1|23|-26|12|8|5|9|0|3|10|28|14|29|10|13|14|15|10|30|14|31|-34|-36|-30|-32|-7|-28|-3|-5|10|32|12|8|5|9|0|21|10|13|14|25|10|33|14|0|10|34|14|0|10|35|36|0|10|37|36|0|10|38|14|39|10|40|12|8|5|9|0|2|10|41|14|0|10|42|14|0|-48|-62|10|43|12|8|5|9|0|0|-48|-70|10|44|14|45|10|46|14|47|10|48|14|49|10|50|14|0|10|51|14|0|10|52|14|53|10|54|12|8|5|9|0|1|23|24|0|14|39|-48|10|54|10|55|12|8|5|9|0|1|23|-26|14|56|-48|-94|10|26|12|8|5|9|0|2|10|27|12|8|5|9|0|1|23|-26|12|8|5|9|0|3|10|28|14|57|10|13|14|25|10|30|14|31|-106|-108|-102|-104|10|58|12|8|5|9|0|4|10|54|59|60|1|10|38|59|-124|10|44|59|-124|10|46|59|-124|-102|-118|-48|-100|10|61|12|8|5|9|0|1|23|-26|12|8|5|9|0|1|10|62|12|8|5|9|0|4|10|63|14|64|10|65|14|66|10|67|14|68|10|69|14|64|-137|10|62|-133|-135|-48|-131|10|70|12|8|5|9|0|0|-48|-152|10|71|12|8|5|9|0|3|10|72|73|24|1|10|26|12|8|5|9|0|1|-118|12|8|5|9|0|2|-160|59|-124|10|74|59|-124|-165|-118|-158|-163|-171|14|75|-48|-156|10|76|12|8|5|9|0|2|23|24|0|12|8|5|9|0|1|10|26|12|8|5|9|0|2|10|77|59|-124|-163|12|8|5|9|0|1|-118|12|8|5|9|0|1|-187|59|-124|-190|-118|-185|-163|-181|-183|-176|-178|23|24|1|12|8|5|9|0|2|10|78|12|8|5|9|0|1|23|24|0|12|8|5|9|0|5|10|79|14|80|-163|12|8|5|9|0|3|-118|12|8|5|9|0|2|-210|59|-124|10|81|59|-124|-213|-118|10|82|59|-124|-163|12|8|5|9|0|1|-118|12|8|5|9|0|1|-221|59|-124|-224|-118|-213|-163|-208|-163|10|71|12|8|5|9|0|3|10|72|73|-162|-163|12|8|5|9|0|1|-118|12|8|5|9|0|2|-234|59|-124|10|83|59|-124|-237|-118|-232|-163|-243|14|84|-208|-230|10|85|12|8|5|9|0|3|10|86|73|24|8|-163|12|8|5|9|0|1|-118|12|8|5|9|0|2|-250|59|-124|10|87|59|-124|-254|-118|-248|-163|-260|14|88|-208|-246|-219|73|24|100|-203|-205|-199|-201|10|26|12|8|5|9|0|2|10|82|59|-124|-163|12|8|5|9|0|1|-118|12|8|5|9|0|1|-269|59|-124|-272|-118|-267|-163|-199|-265|-176|-196|-48|-174|-3|-46|0|0|89|0|5|9|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        :dyn_variables => [ 
          { "name" => opts[:lu_dto_clu_format_dyn_var_name], "regexp" => opts[:lu_dto_clu_format_dyn_var_regexp] },
          { "name" => opts[:lu_dto_clu_activities_dyn_var_name], "regexp" => opts[:lu_dto_clu_activities_dyn_var_regexp] }
        ], 
        'subst' => 'true' 
      }
    )
    
    @request.add("/debug/lu_dto_clu_format_dyn_var_name/%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%",{},{'subst'=>'true'})
    @request.add("/debug/lu_dto_clu_activities_dyn_var_name/%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%",{},{'subst'=>'true'})
    
    
    # Pg3 - Course Information

    # thinktime 3 ?

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|12|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|person.queryParam.personId|#{propose_person}|person.search.personQuickViewByGivenName|1|2|3|4|1|5|6|0|7|0|8|1|9|10|0|11|12|0|0|0|"
      }                                      
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|12|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|atp.advancedAtpSearchParam.atpId|kuali.atp.CLASS2012Y1|atp.search.advancedAtpSearch|1|2|3|4|1|5|6|0|7|0|8|1|9|10|0|11|12|0|0|0|"
      }                                      
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|12|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|atp.advancedAtpSearchParam.atpId|kuali.atp.CLASS2012Y1|atp.search.advancedAtpSearch|1|2|3|4|1|5|6|0|7|0|8|1|9|10|0|11|12|0|0|0|"
      }                                      
    )
    # propose_person var in post contents
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|12|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|person.queryParam.personId|#{propose_person}|person.search.personQuickViewByGivenName|1|2|3|4|1|5|6|0|7|0|8|1|9|10|0|11|12|0|0|0|"
      }                                      
    )

    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LuRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|2F7C2AADA9B2BAC276256FE24CAFAFBA|org.kuali.student.lum.lu.ui.course.client.service.LuRpcService|getSearchType|java.lang.String|lu.search.clus|1|2|3|4|1|5|6|"
      }                                      
    )
    # DUPE
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LuRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|2F7C2AADA9B2BAC276256FE24CAFAFBA|org.kuali.student.lum.lu.ui.course.client.service.LuRpcService|getSearchType|java.lang.String|lu.search.clus|1|2|3|4|1|5|6|"
      }                                      
    )

    # Save & Continue
    contents1 = "5|0|100|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|#{opts[:course_title]}|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|atpType.duration.week|termsOffered|firstExpectedOffering|kuali.atp.CLASS2012Y1|primaryInstructor|#{opts[:instructor]}|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|AllCampuses|org.kuali.student.lum.lu.dto.CluInfo|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|0|updated|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Lab|contactHours|hrs|per|atpType.duration.weekly|timeUnit|atpType.duration.month|defaultEnrollmentEstimate|fees|attributes|DeptOrgName|#{oversight_department}|CourseCode|nullnull|CourseType|Credit Course|OversightName|courseSpecificLOs|joints|courseId|created|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|7|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|-13|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-21|10|27|12|8|5|9|0|2|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|30|10|13|14|15|10|31|14|32|-34|-36|-30|-32|10|33|12|8|5|9|0|1|10|16|34|35|1|-30|-46|-7|-28|-3|-5|10|36|12|8|5|9|0|22|10|13|14|26|10|37|14|38|10|39|14|40|10|41|42|0|10|43|42|0|10|44|14|45|10|46|12|8|5|9|0|3|10|47|14|0|10|48|14|49|10|27|12|8|5|9|0|1|-46|12|8|5|9|0|1|10|48|34|-52|-79|-46|-71|-77|-55|-69|10|50|12|8|5|9|0|2|10|51|52|25|1|10|53|14|54|-55|-86|10|55|12|8|5|9|0|0|-55|-95|10|56|14|57|10|58|14|59|10|60|14|61|10|62|14|63|10|64|14|65|10|66|14|67|10|68|12|8|5|9|0|1|24|25|0|14|45|-55|10|68|10|69|12|8|5|9|0|1|24|-26|14|70|-55|-119|10|27|12|8|5|9|0|2|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|71|10|13|14|26|10|31|14|32|-131|-133|-127|-129|-46|12|8|5|9|0|8|10|62|34|-52|10|37|34|-52|10|64|34|-52|10|39|34|-52|10|68|34|-52|10|44|34|-52|10|56|34|-52|10|58|34|-52|-127|-46|-55|-125|10|72|12|8|5|9|0|1|24|-26|12|8|5|9|0|4|10|13|14|73|10|60|14|61|10|27|12|8|5|9|0|3|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|71|10|13|14|73|10|31|14|74|-180|-182|-176|-178|10|75|34|-52|-77|12|8|5|9|0|1|-46|12|8|5|9|0|1|-192|34|-52|-195|-46|-176|-77|-168|-174|10|76|12|8|5|9|0|1|24|-26|12|8|5|9|0|7|10|13|14|77|10|78|14|79|10|80|12|8|5|9|0|2|10|81|52|25|8|10|82|14|83|-207|-213|10|50|12|8|5|9|0|2|10|51|52|25|1|10|84|14|85|-207|-222|10|86|52|25|100|10|60|14|61|10|27|12|8|5|9|0|3|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|71|10|13|14|77|10|31|14|74|-242|-244|-238|-240|10|75|34|-52|-77|12|8|5|9|0|1|-46|12|8|5|9|0|1|-254|34|-52|-257|-46|-238|-77|-207|-236|-203|-205|-168|-201|-164|-166|-55|-162|10|87|12|8|5|9|0|1|24|-26|12|8|5|9|0|1|10|88|12|8|5|9|0|4|10|89|14|90|10|91|14|92|10|93|14|94|10|95|14|90|-269|10|88|-265|-267|-55|-263|10|96|12|8|5|9|0|0|-55|-284|10|97|12|8|5|9|0|1|24|25|0|12|8|5|9|0|2|10|98|14|0|10|27|12|8|5|9|0|2|10|99|34|-52|-77|12|8|5|9|0|1|-46|12|8|5|9|0|1|-303|34|-52|-306|-46|-301|-77|-295|-299|-290|-292|-55|-288|-3|-53|0|0|100|0|5|9|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      { 'subst' => 'true' }                          
    )
    
    
    # Pg4 - Learning Objectives

    if(opts[:lo_create])
      # Add a LO
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/ArrowUp.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/ArrowDown.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/IncreaseIndent.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/DecreaseIndent.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/delete_blue_12px.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/IncreaseIndentDisabled.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/ArrowUpDisabled.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/DecreaseIndentDisabled.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/delete_gray_12px.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/ArrowDownDisabled.png')
    
      # type in search
      for i in 1..opts[:lo_category].length
        itr = i-1
        @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
          {
            'method' => 'POST',
            'content_type' => 'text/x-gwt-rpc; charset=utf-8',
            'contents' => "5|0|12|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|org.kuali.student.core.search.dto.SearchParam/3876231949|lo.queryParam.startsWith.loCategoryName|#{opts[:lo_category][0..itr]}|lo.search.loCategories|1|2|3|4|1|5|6|0|7|0|8|1|9|10|0|11|12|0|0|0|"
          }                                      
        )
      end
      
      # Save & Continue
      contents1 = "5|0|92|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|#{propose_person}|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|01e6e38d-4ba5-44c1-ba1d-088fdf685d10|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|#{opts[:course_title]}|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|atpType.duration.week|termsOffered|firstExpectedOffering|kuali.atp.CLASS2012Y1|primaryInstructor|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|AllCampuses|org.kuali.student.lum.lu.dto.CluInfo|formats|3d3bec3b-427c-4c35-86d9-b14088f7ac1a|1|updated|activities|647ba4ec-d8cc-460f-b8e4-e4ed8c7b307f|activityType|kuali.lu.type.activity.Lecture|contactHours|hrs|per|atpType.duration.monthly|timeUnit|defaultEnrollmentEstimate|fees|attributes|DeptOrgName|#{oversight_department}|CourseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|CourseType|Credit Course|OversightName|courseSpecificLOs|sequence|0|includedSingleUseLo|#{opts[:lo_name]}|name|SINGLE USE LO|categories|java.util.ArrayList/3821976829"
      contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|7|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|1|19|20|0|14|21|-7|10|18|10|22|14|23|10|24|14|25|10|26|12|8|5|9|0|1|19|20|0|14|27|-7|-25|10|28|12|8|5|9|0|2|10|29|12|8|5|9|0|1|19|-30|12|8|5|9|0|3|10|30|14|31|10|13|14|15|10|32|14|33|-38|-40|-34|-36|10|34|12|8|5|9|0|1|-20|35|36|1|-34|-50|-7|-32|-3|-5|10|37|12|8|5|9|0|21|10|13|14|27|10|38|14|39|10|40|14|41|10|42|43|0|10|44|43|0|10|45|14|46|10|47|12|8|5|9|0|2|10|48|14|0|10|49|14|50|-58|-72|10|51|12|8|5|9|0|2|10|52|53|20|5|10|54|14|55|-58|-80|10|56|12|8|5|9|0|0|-58|-89|10|57|14|58|10|59|14|21|10|60|14|61|10|62|14|63|10|64|14|65|10|66|14|67|10|68|12|8|5|9|0|1|19|-18|14|46|-58|10|68|10|69|12|8|5|9|0|1|19|-18|14|70|-58|10|69|10|28|12|8|5|9|0|2|10|29|12|8|5|9|0|1|19|-30|12|8|5|9|0|3|10|30|14|71|10|13|14|27|10|32|14|33|-125|-127|-121|-123|-50|12|8|5|9|0|6|10|68|35|-55|10|69|35|-55|10|45|35|-55|10|57|35|-55|10|59|35|-55|10|62|35|-55|-121|-50|-58|-119|10|72|12|8|5|9|0|1|19|-30|12|8|5|9|0|4|10|13|14|73|10|60|14|61|10|28|12|8|5|9|0|3|10|29|12|8|5|9|0|1|19|-30|12|8|5|9|0|3|10|30|14|71|10|13|14|73|10|32|14|74|-170|-172|-166|-168|10|75|35|-55|10|28|12|8|5|9|0|1|-50|12|8|5|9|0|1|-182|35|-55|-186|-50|-166|-184|-158|-164|10|76|12|8|5|9|0|1|19|-30|12|8|5|9|0|7|10|13|14|77|10|78|14|79|10|80|12|8|5|9|0|2|10|81|53|-86|10|82|14|83|-198|-204|10|51|12|8|5|9|0|2|10|52|53|20|5|10|84|14|55|-198|-212|10|85|53|20|100|10|60|14|61|10|28|12|8|5|9|0|3|10|29|12|8|5|9|0|1|19|-30|12|8|5|9|0|3|10|30|14|71|10|13|14|77|10|32|14|74|-232|-234|-228|-230|10|75|35|-55|-184|12|8|5|9|0|1|-50|12|8|5|9|0|1|-244|35|-55|-247|-50|-228|-184|-198|-226|-194|-196|-158|-192|-154|-156|-58|-152|10|86|12|8|5|9|0|1|19|-30|12|8|5|9|0|1|10|87|12|8|5|9|0|4|10|88|14|89|10|90|14|91|10|92|14|93|10|94|14|89|-259|10|87|-255|-257|-58|-253|10|95|12|8|5|9|0|1|19|-18|12|8|5|9|0|5|10|13|14|0|10|42|43|0|10|60|14|0|10|96|14|97|10|98|12|8|5|9|0|4|10|13|14|0|10|47|12|8|5|9|0|2|10|48|14|99|10|49|14|99|-292|-296|10|100|14|101|10|102|12|8|5|9|0|0|-292|-306|-280|-290|-276|-278|-58|10|95|-3|-56|0|0|103|0|5|9|0|0|0|0|"
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "#{contents1}#{contents2}"
        },
        { 'subst' => 'true' }                         
      )
      
    else
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/964D71DCD6E18D8B9E01245AD3C5701C.cache.png')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/bg_header_gradient.gif')
      # all category search
      # This search returns :lo_id and :lo_clu_id for dyn variables
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SearchRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|9|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|9C6F936F4CEB3E5CBB16043EFF7A3F6A|org.kuali.student.common.ui.client.service.SearchRpcService|search|org.kuali.student.core.search.dto.SearchRequest|org.kuali.student.core.search.dto.SearchRequest/3917446114|java.lang.Boolean/476441737|java.util.ArrayList/3821976829|lo.search.loByCategory|1|2|3|4|1|5|6|0|7|0|8|0|9|0|0|0|"
        }           
      )
      
      # Save & Continue
      contents1 = "5|0|92|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|#{opts[:course_title]}|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|atpType.duration.week|termsOffered|firstExpectedOffering|kuali.atp.CLASS2012Y1|primaryInstructor|#{opts[:instructor]}|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|AllCampuses|org.kuali.student.lum.lu.dto.CluInfo|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|fees|attributes|DeptOrgName|#{oversight_department}|CourseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|CourseType|Credit Course|OversightName|courseSpecificLOs|sequence|0|includedSingleUseLo|#{opts[:lo_name]}|name|SINGLE USE LO|categories|joints|courseId|created|java.util.ArrayList/3821976829"
      contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|7|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|-13|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-21|10|27|12|8|5|9|0|1|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|30|10|13|14|15|10|31|14|32|-34|-36|-30|-32|-7|-28|-3|-5|10|33|12|8|5|9|0|21|10|13|14|26|10|34|14|35|10|36|14|37|10|38|39|0|10|40|39|0|10|41|14|42|10|43|12|8|5|9|0|2|10|44|14|0|10|45|14|46|-48|-62|10|47|12|8|5|9|0|2|10|48|49|25|5|10|50|14|51|-48|-70|10|52|12|8|5|9|0|0|-48|-79|10|53|14|54|10|55|14|56|10|57|14|58|10|59|14|60|10|61|14|62|10|63|14|64|10|65|12|8|5|9|0|1|24|25|0|14|42|-48|10|65|10|66|12|8|5|9|0|1|24|-26|14|67|-48|-103|10|27|12|8|5|9|0|2|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|68|10|13|14|26|10|31|14|32|-115|-117|-111|-113|10|69|12|8|5|9|0|3|10|65|70|71|1|10|41|70|-133|10|59|70|-133|-111|-127|-48|-109|10|72|12|8|5|9|0|1|24|-26|12|8|5|9|0|1|10|73|12|8|5|9|0|4|10|74|14|75|10|76|14|77|10|78|14|79|10|80|14|75|-144|10|73|-140|-142|-48|-138|10|81|12|8|5|9|0|1|24|-100|12|8|5|9|0|5|10|13|14|0|10|38|39|0|10|57|14|0|10|82|14|83|10|84|12|8|5|9|0|4|10|13|14|0|10|43|12|8|5|9|0|2|10|44|14|85|10|45|14|85|-177|-181|10|86|14|87|10|88|12|8|5|9|0|0|-177|-191|-165|-175|-161|-163|-48|10|81|10|89|12|8|5|9|0|1|24|25|0|12|8|5|9|0|2|10|90|14|0|10|27|12|8|5|9|0|2|10|91|70|-133|10|27|12|8|5|9|0|1|-127|12|8|5|9|0|1|-211|70|-133|-215|-127|-209|-213|-203|-207|-198|-200|-48|-196|-3|-46|0|0|92|0|5|9|0|0|0|0|"
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "#{contents1}#{contents2}"
        },
        {
          :dyn_variables => [
            { "name" => opts[:single_use_lo_dyn_var_name], "regexp" => opts[:single_use_lo_dyn_var_regexp] }
          ],
          'subst' => 'true' 
        }     
      )
      
    end
    
    
    
    # Pg5 - Course Requisites
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|C9490AE03B6BEB9F529BA0FC689E8EC2|org.kuali.student.lum.lu.ui.course.client.service.LoRpcService|getLoCategoryTypes|1|2|3|4|0|"
      }                                      
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/images/Home-selected-button.jpg')
    #dupe
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/LoRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|C9490AE03B6BEB9F529BA0FC689E8EC2|org.kuali.student.lum.lu.ui.course.client.service.LoRpcService|getLoCategoryTypes|1|2|3|4|0|"
      }                                      
    )
    
    # Save & Continue
    contents1 = "5|0|115|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|java.lang.String/2004016611|kuali.luStatementType.prereqAcademicReadiness.rationale||kuali.luStatementType.coreqAcademicReadiness.rationale|kuali.luStatementType.antireqAcademicReadiness.rationale|kuali.luStatementType.enrollAcademicReadiness.rationale|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|#{opts[:course_title]}|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|atpType.duration.week|termsOffered|firstExpectedOffering|kuali.atp.CLASS2012Y1|primaryInstructor|#{opts[:instructor]}|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|AllCampuses|org.kuali.student.lum.lu.dto.CluInfo|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|1|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Lab|contactHours|hrs|per|atpType.duration.weekly|timeUnit|atpType.duration.month|defaultEnrollmentEstimate|fees|attributes|DeptOrgName|#{oversight_department}|CourseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|CourseType|Credit Course|OversightName|courseSpecificLOs|sequence|0|java.sql.Timestamp/1769758459|kuali.lu.lo.relation.type.includes|includedSingleUseLo|%%_#{opts[:single_use_lo_dyn_var_name]}%%|#{opts[:lo_name]}|loRepository|kuali.loRepository.key.singleUse|active|kuali.lo.type.singleUse|categories|childSingleUseLos|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|4|8|9|8|10|8|11|-4|8|12|-4|8|13|-4|14|5|15|0|2|16|17|18|14|5|15|0|7|16|19|20|21|16|22|20|23|16|24|18|14|5|15|0|0|-12|-18|16|25|20|26|16|27|20|28|16|29|18|14|5|15|0|1|30|31|0|20|32|-12|-26|16|33|18|14|5|15|0|1|16|34|18|14|5|15|0|1|30|-31|18|14|5|15|0|3|16|35|20|36|16|19|20|21|16|37|20|38|-39|-41|-35|-37|-12|-33|-8|-10|16|39|18|14|5|15|0|21|16|19|20|32|16|40|20|41|16|42|20|43|16|44|45|0|16|46|45|0|16|47|20|48|16|49|18|14|5|15|0|2|16|50|20|0|16|51|20|52|-53|-67|16|53|18|14|5|15|0|2|16|54|55|31|5|16|56|20|57|-53|-75|16|58|18|14|5|15|0|0|-53|-84|16|59|20|60|16|61|20|62|16|63|20|64|16|65|20|66|16|67|20|68|16|69|20|70|16|71|18|14|5|15|0|1|30|31|0|20|48|-53|16|71|16|72|18|14|5|15|0|1|30|-31|20|73|-53|-108|16|33|18|14|5|15|0|2|16|34|18|14|5|15|0|1|30|-31|18|14|5|15|0|3|16|35|20|74|16|19|20|32|16|37|20|38|-120|-122|-116|-118|16|75|18|14|5|15|0|2|16|71|76|77|1|16|47|76|-138|-116|-132|-53|-114|16|78|18|14|5|15|0|1|30|-31|18|14|5|15|0|4|16|19|20|79|16|63|20|64|16|33|18|14|5|15|0|1|16|34|18|14|5|15|0|1|30|-31|18|14|5|15|0|3|16|35|20|74|16|19|20|79|16|37|20|80|-159|-161|-155|-157|-147|-153|16|81|18|14|5|15|0|1|30|-31|18|14|5|15|0|7|16|19|20|82|16|83|20|84|16|85|18|14|5|15|0|2|16|86|55|31|8|16|87|20|88|-177|-183|16|53|18|14|5|15|0|2|16|54|55|31|1|16|89|20|90|-177|-192|16|91|55|31|100|16|63|20|64|16|33|18|14|5|15|0|1|16|34|18|14|5|15|0|1|30|-31|18|14|5|15|0|3|16|35|20|74|16|19|20|82|16|37|20|80|-212|-214|-208|-210|-177|-206|-173|-175|-147|-171|-143|-145|-53|-141|16|92|18|14|5|15|0|1|30|-31|18|14|5|15|0|1|16|93|18|14|5|15|0|4|16|94|20|95|16|96|20|97|16|98|20|99|16|100|20|95|-230|16|93|-226|-228|-53|-224|16|101|18|14|5|15|0|1|30|-31|18|14|5|15|0|6|16|102|20|103|16|44|45|104|3054243788|1267015352320|108000000|16|19|20|0|16|63|20|64|16|69|20|105|16|106|18|14|5|15|0|8|16|19|20|107|16|49|18|14|5|15|0|2|16|50|20|108|16|51|20|108|-266|-270|16|109|20|110|16|44|45|-257|16|63|20|111|16|69|20|112|16|113|18|14|5|15|0|0|-266|-286|16|114|18|14|5|15|0|0|-266|-290|-251|-264|-247|-249|-53|-245|-8|-51|0|0|115|0|5|15|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      {
        :dyn_variables => [
          { "name" => opts[:lo_child_id_dyn_var_name], "regexp" => opts[:lo_child_id_dyn_var_regexp] }
        ],
        'subst' => 'true' 
      }
    )
    
    
    # Pg6 - Active Dates
    # save & continue
    contents1 = "5|0|110|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|saveData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|#{opts[:course_title]}|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.util.Date/1659716317|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|atpType.duration.week|termsOffered|firstExpectedOffering|kuali.atp.CLASS2012Y1|primaryInstructor|#{opts[:instructor]}|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|AllCampuses|org.kuali.student.lum.lu.dto.CluInfo|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|1|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Lab|contactHours|hrs|per|atpType.duration.weekly|timeUnit|atpType.duration.month|defaultEnrollmentEstimate|fees|attributes|DeptOrgName|#{oversight_department}|CourseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|CourseType|Credit Course|OversightName|courseSpecificLOs|sequence|0|java.sql.Timestamp/1769758459|kuali.lu.lo.relation.type.includes|includedSingleUseLo|%%_#{opts[:single_use_lo_dyn_var_name]}%%|#{opts[:lo_name]}|loRepository|kuali.loRepository.key.singleUse|active|kuali.lo.type.singleUse|categories|childSingleUseLos|java.util.ArrayList/3821976829"
    contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|7|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|-13|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-21|10|27|12|8|5|9|0|1|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|30|10|13|14|15|10|31|14|32|-34|-36|-30|-32|-7|-28|-3|-5|10|33|12|8|5|9|0|21|10|13|14|26|10|34|14|35|10|36|14|37|10|38|39|40|3003447680|1267015352320|10|41|39|40|1214080384|1271310319616|10|42|14|43|10|44|12|8|5|9|0|2|10|45|14|0|10|46|14|47|-48|-64|10|48|12|8|5|9|0|2|10|49|50|25|5|10|51|14|52|-48|-72|10|53|12|8|5|9|0|0|-48|-81|10|54|14|55|10|56|14|57|10|58|14|59|10|60|14|61|10|62|14|63|10|64|14|65|10|66|12|8|5|9|0|1|24|25|0|14|43|-48|10|66|10|67|12|8|5|9|0|1|24|-26|14|68|-48|-105|10|27|12|8|5|9|0|2|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|69|10|13|14|26|10|31|14|32|-117|-119|-113|-115|10|70|12|8|5|9|0|4|10|42|71|72|1|10|66|71|-135|10|38|71|-135|10|41|71|-135|-113|-129|-48|-111|10|73|12|8|5|9|0|1|24|-26|12|8|5|9|0|4|10|13|14|74|10|58|14|59|10|27|12|8|5|9|0|1|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|69|10|13|14|74|10|31|14|75|-160|-162|-156|-158|-148|-154|10|76|12|8|5|9|0|1|24|-26|12|8|5|9|0|7|10|13|14|77|10|78|14|79|10|80|12|8|5|9|0|2|10|81|50|25|8|10|82|14|83|-178|-184|10|48|12|8|5|9|0|2|10|49|50|25|1|10|84|14|85|-178|-193|10|86|50|25|100|10|58|14|59|10|27|12|8|5|9|0|1|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|69|10|13|14|77|10|31|14|75|-213|-215|-209|-211|-178|-207|-174|-176|-148|-172|-144|-146|-48|-142|10|87|12|8|5|9|0|1|24|-26|12|8|5|9|0|1|10|88|12|8|5|9|0|4|10|89|14|90|10|91|14|92|10|93|14|94|10|95|14|90|-231|10|88|-227|-229|-48|-225|10|96|12|8|5|9|0|1|24|-26|12|8|5|9|0|6|10|97|14|98|10|38|39|99|3060588299|1267015352320|619000000|10|13|14|0|10|58|14|59|10|64|14|100|10|101|12|8|5|9|0|8|10|13|14|102|10|44|12|8|5|9|0|2|10|45|14|103|10|46|14|103|-267|-271|10|104|14|105|10|38|39|-258|10|58|14|106|10|64|14|107|10|108|12|8|5|9|0|0|-267|-287|10|109|12|8|5|9|0|0|-267|-291|-252|-265|-248|-250|-48|-246|-3|-46|0|0|110|0|5|9|0|0|0|0|"
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        'method' => 'POST',
        'content_type' => 'text/x-gwt-rpc; charset=utf-8',
        'contents' => "#{contents1}#{contents2}"
      },
      { 'subst' => 'true' }                                      
    )
    

    # Submit to worflow
    if(opts[:submit])
      
      # Author & Collaborators
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|ks.rice.personLookup.serviceAddress|1|2|3|4|1|5|6|"
        }                                   
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
        },
        { 'subst' => 'true' }                                   
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getActionsRequested|java.lang.String|%%_#{opts[:proposal_dyn_var_name]}%%|1|2|3|4|1|5|6|"
        },
        { 'subst' => 'true' }                                  
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getCollaborators|java.lang.String|3070|1|2|3|4|1|5|6|"
        }                                   
      )
      
      # Submit
      contents1 = "5|0|109|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|submitDocumentWithData|org.kuali.student.core.assembly.data.Data|org.kuali.student.lum.lu.assembly.data.client.LuData/2660362736|java.util.HashMap/962170901|org.kuali.student.core.assembly.data.Data/3119441076|java.util.LinkedHashMap/1551059846|org.kuali.student.core.assembly.data.Data$StringKey/1742996354|proposal|org.kuali.student.core.assembly.data.Data$DataValue/4040075329|id|org.kuali.student.core.assembly.data.Data$StringValue/3696151110|%%_#{opts[:proposal_dyn_var_name]}%%|rationale|#{opts[:course_rationale]}|proposerPerson|title|#{title}|referenceType|kuali.proposal.referenceType.clu|references|org.kuali.student.core.assembly.data.Data$IntegerKey/2690592210|java.lang.Integer/3438268394|%%_#{opts[:clu_ref_dyn_var_name]}%%|_runtimeData|versions|typeName|org.kuali.student.core.proposal.dto.ProposalInfo|versionIndicator|#{version_indicator+=1}|course|courseNumberSuffix|#{opts[:course_suffix]}|courseTitle|#{opts[:course_title]}|effectiveDate|org.kuali.student.core.assembly.data.Data$DateValue/3833457837|java.sql.Timestamp/1769758459|expirationDate|department|%%_#{opts[:admin_dep_var_name]}%%|description|formatted|plain|#{opts[:course_description]}|duration|quantity|org.kuali.student.core.assembly.data.Data$IntegerValue/991919491|termType|atpType.duration.week|termsOffered|firstExpectedOffering|kuali.atp.CLASS2012Y1|primaryInstructor|#{opts[:instructor]}|state|draft|subjectArea|#{opts[:subject_area]}|transcriptTitle|#{opts[:course_short_title]}|type|kuali.lu.type.CreditCourse|academicSubjectOrgs|campusLocations|AllCampuses|org.kuali.student.lum.lu.dto.CluInfo|dirty|org.kuali.student.core.assembly.data.Data$BooleanValue/268767974|java.lang.Boolean/476441737|formats|%%_#{opts[:lu_dto_clu_format_dyn_var_name]}%%|1|activities|%%_#{opts[:lu_dto_clu_activities_dyn_var_name]}%%|activityType|kuali.lu.type.activity.Lab|contactHours|hrs|per|atpType.duration.weekly|timeUnit|atpType.duration.month|defaultEnrollmentEstimate|fees|attributes|DeptOrgName|#{oversight_department}|CourseCode|#{opts[:subject_area]}#{opts[:course_suffix]}|CourseType|Credit Course|OversightName|courseSpecificLOs|sequence|0|kuali.lu.lo.relation.type.includes|includedSingleUseLo|%%_#{opts[:single_use_lo_dyn_var_name]}%%|#{opts[:lo_name]}|loRepository|kuali.loRepository.key.singleUse|active|kuali.lo.type.singleUse|categories|childSingleUseLos|java.util.ArrayList/3821976829"
      contents2 = "|1|2|3|4|1|5|6|7|0|8|5|9|0|2|10|11|12|8|5|9|0|7|10|13|14|15|10|16|14|17|10|18|12|8|5|9|0|0|-7|-13|10|19|14|20|10|21|14|22|10|23|12|8|5|9|0|1|24|25|0|14|26|-7|-21|10|27|12|8|5|9|0|1|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|30|10|13|14|15|10|31|14|32|-34|-36|-30|-32|-7|-28|-3|-5|10|33|12|8|5|9|0|21|10|13|14|26|10|34|14|35|10|36|14|37|10|38|39|40|3003447680|1267015352320|0|10|41|39|40|1214080384|1271310319616|0|10|42|14|43|10|44|12|8|5|9|0|2|10|45|14|0|10|46|14|47|-48|-64|10|48|12|8|5|9|0|2|10|49|50|25|5|10|51|14|52|-48|-72|10|53|12|8|5|9|0|0|-48|-81|10|54|14|55|10|56|14|57|10|58|14|59|10|60|14|61|10|62|14|63|10|64|14|65|10|66|12|8|5|9|0|1|24|25|0|14|43|-48|10|66|10|67|12|8|5|9|0|1|24|-26|14|68|-48|-105|10|27|12|8|5|9|0|2|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|69|10|13|14|26|10|31|14|32|-117|-119|-113|-115|10|70|12|8|5|9|0|4|10|66|71|72|1|10|42|71|-135|10|54|71|-135|10|56|71|-135|-113|-129|-48|-111|10|73|12|8|5|9|0|1|24|-26|12|8|5|9|0|4|10|13|14|74|10|58|14|59|10|27|12|8|5|9|0|1|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|69|10|13|14|74|10|31|14|75|-160|-162|-156|-158|-148|-154|10|76|12|8|5|9|0|1|24|-26|12|8|5|9|0|7|10|13|14|77|10|78|14|79|10|80|12|8|5|9|0|2|10|81|50|25|8|10|82|14|83|-178|-184|10|48|12|8|5|9|0|2|10|49|50|25|1|10|84|14|85|-178|-193|10|86|50|25|100|10|58|14|59|10|27|12|8|5|9|0|1|10|28|12|8|5|9|0|1|24|-26|12|8|5|9|0|3|10|29|14|69|10|13|14|77|10|31|14|75|-213|-215|-209|-211|-178|-207|-174|-176|-148|-172|-144|-146|-48|-142|10|87|12|8|5|9|0|1|24|-26|12|8|5|9|0|1|10|88|12|8|5|9|0|4|10|89|14|90|10|91|14|92|10|93|14|94|10|95|14|90|-231|10|88|-227|-229|-48|-225|10|96|12|8|5|9|0|1|24|-26|12|8|5|9|0|6|10|97|14|98|10|38|39|40|3126318658|1267015352320|978000000|10|13|14|0|10|58|14|59|10|64|14|99|10|100|12|8|5|9|0|8|10|13|14|101|10|44|12|8|5|9|0|2|10|45|14|102|10|46|14|102|-267|-271|10|103|14|104|10|38|39|-258|10|58|14|105|10|64|14|106|10|107|12|8|5|9|0|0|-267|-287|10|108|12|8|5|9|0|0|-267|-291|-252|-265|-248|-250|-48|-246|-3|-46|0|0|109|0|5|9|0|0|0|0|"
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "#{contents1}#{contents2}"
        },
        { 'subst' => 'true' }                                      
      )
      
    end
    
  end
  
  
  
  def scratch
    
    #:lo_id_dyn_var_name => "lo_id",
    #:lo_id_dyn_var_regexp => 'lo.resultColumn.loId\"\,\"\([^\"]+\)',
    #:lo_clu_id_dyn_var_name => "lo_clu_id",
    #:lo_clu_id_dyn_var_regexp => 'lo.resultColumn.loCluId\"\,\"\([^\"]+\)',
    
    
  end
    
end