#!/usr/bin/env ruby

# 
# == Synopsis
#
# Authentication class containing all operations around app authentication
#
# Author:: Kyle Campos (mailto:kcampos@rsmart.com)
#

require File.dirname(__FILE__) + '/../organization/organization.rb'
require 'uri'

class Authentication
  
  attr_accessor :request
  
  def initialize(request_obj)
    @request = request_obj
  end
  
  # Login to ks
  def login(opts={})
    
    defaults = {
      :user => 'admin',
      :password => 'admin',
      :thinktime => 3
    }
    
    opts = defaults.merge(opts)
    
    @request.add('/')
    
    if(@request.config.sso == false)
      
      @request.add('/spring_security_login')
      @request.add_thinktime(opts[:thinktime])
      @request.add('/j_spring_security_check', {"contents" => "j_username=#{opts[:user]}&amp;j_password=#{opts[:password]}&amp;submit=Submit",
      "content_type" => "application/x-www-form-urlencoded", "method" => "POST"})
    
      @request.add('/index.html')
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp')
      #@request.add('http://www.google.com/jsapi')
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SecurityRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|622819583F55A8652E94BDA48D0FF476|org.kuali.student.common.ui.client.service.SecurityRpcService|getPrincipalUsername|1|2|3|4|0|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|isAuthorized|org.kuali.student.core.rice.authorization.PermissionType/259370389|java.util.Map|java.util.HashMap/962170901|1|2|3|4|2|5|6|5|8|7|0|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5002099C37B5D013EC8762ACCBD03E69|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.util.List|java.util.Arrays$ArrayList/1243019747|[Ljava.lang.String;/2600011424|application.url|ks.rice.docSearch.serviceAddress|lum.application.url|ks.rice.url|ks.rice.label|ks.application.version|ks.gwt.codeServer|1|2|3|4|1|5|6|7|7|8|9|10|11|12|13|14|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|isAuthorized|org.kuali.student.core.rice.authorization.PermissionType/259370389|java.util.Map|java.util.HashMap/962170901|1|2|3|4|2|5|6|5|8|7|0|"
        }
      )
      # DUPLICATE - BUG
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5B9DBA4B9E724BDDC195984D10832B02|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|isAuthorized|org.kuali.student.core.rice.authorization.PermissionType/259370389|java.util.Map|java.util.HashMap/962170901|1|2|3|4|2|5|6|5|8|7|0|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5002099C37B5D013EC8762ACCBD03E69|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.util.List|java.util.Arrays$ArrayList/1243019747|[Ljava.lang.String;/2600011424|ks.rice.actionList.serviceAddress|1|2|3|4|1|5|6|7|1|8|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/MetadataRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|119C364DBB6DD767CBEB2DB5ED41CAE8|org.kuali.student.common.ui.client.service.MetadataRpcService|getMetadata|java.lang.String/2004016611|search||1|2|3|4|3|5|5|5|6|7|7|"
                                               
        }
      )
      # DUPLICATE - BUG
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/MetadataRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|119C364DBB6DD767CBEB2DB5ED41CAE8|org.kuali.student.common.ui.client.service.MetadataRpcService|getMetadata|java.lang.String/2004016611|search||1|2|3|4|3|5|5|5|6|7|7|"
                                               
        }
      )
      # DUPLICATE - BUG
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/MetadataRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|7|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|119C364DBB6DD767CBEB2DB5ED41CAE8|org.kuali.student.common.ui.client.service.MetadataRpcService|getMetadata|java.lang.String/2004016611|search||1|2|3|4|3|5|5|5|6|7|7|"
                                               
        }
      )
      
      
      @request.add('/kew/ActionList.do', {}, {:rice_req => true, :external => true})
      
      
      
      # Rice
      #<request><http url='/ks-rice/j_spring_security_check' version='1.1'  contents='j_username=admin&amp;j_password=admin&amp;submit=Submit+Query' content_type='application/x-www-form-urlencoded' method='POST'></http></request>
      #<request><http url='/ks-rice/kew/ActionList.do' version='1.1' method='GET'></http></request>

      #<thinktime random='true' value='1'/>

      #<request><http url='/ks-rice/dwr/engine.js' version='1.1' if_modified_since='Tue, 24 Aug 2010 19:31:49 GMT' method='GET'></http></request>
      #<request><http url='/ks-rice/dwr/util.js' version='1.1' if_modified_since='Tue, 24 Aug 2010 19:31:49 GMT' method='GET'></http></request>
      #<request><http url='/ks-rice/dwr/interface/PersonService.js' version='1.1' method='GET'></http></request>
      #<request><http url='/ks-rice/kew/images/my_route_log.gif' version='1.1' method='GET'></http></request>
      #<request><http url='/ks-rice/kr/images/th-back1.gif' version='1.1' method='GET'></http></request>
      
      
      
    else
      ks_url_escaped = URI.escape("#{@request.url}/j_spring_cas_security_check", Regexp.new("[^#{URI::PATTERN::UNRESERVED}]"))
      @request.add("#{@request.config.sso}/login?service=#{ks_url_escaped}")
    end
    
  end
  
  # Logout of KS
  def logout
    
    @request.add('j_spring_security_logout')
    @request.add('org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp')
    # loading login page
    @request.add('/spring_security_login')
    
  end
  
  # Bring up homepage
  def load_homepage
    
  end
  
end