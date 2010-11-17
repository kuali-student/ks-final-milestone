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
      @request.add('org.kuali.student.lum.lu.ui.main.LUMMain/org.kuali.student.lum.lu.ui.main.LUMMain.nocache.js')
      @request.add('http://www.google.com/jsapi', {}, {:external => true})
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5002099C37B5D013EC8762ACCBD03E69|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|getContextPath|1|2|3|4|0|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SecurityRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|622819583F55A8652E94BDA48D0FF476|org.kuali.student.common.ui.client.service.SecurityRpcService|getPrincipalUsername|1|2|3|4|0|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/MetadataRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|063131BF3C7CD9299BE6BC86D4D8C72F|org.kuali.student.common.ui.client.service.MetadataRpcService|getMetadata|java.lang.String/2004016611|search|1|2|3|4|3|5|5|5|6|0|0|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|14|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5002099C37B5D013EC8762ACCBD03E69|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.util.List|java.util.Arrays$ArrayList/1243019747|[Ljava.lang.String;/2600011424|application.url|ks.rice.docSearch.serviceAddress|lum.application.url|ks.rice.url|ks.rice.label|ks.application.version|ks.gwt.codeServer|1|2|3|4|1|5|6|7|7|8|9|10|11|12|13|14|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|5002099C37B5D013EC8762ACCBD03E69|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.util.List|java.util.Arrays$ArrayList/1243019747|[Ljava.lang.String;/2600011424|ks.rice.actionList.serviceAddress|1|2|3|4|1|5|6|7|1|8|"
        }
      )
      
      
      
      # Rice
      @request.add('/kew/ActionList.do', {}, {:rice_req => true, :external => true})
      @request.add('/spring_security_login', {}, {:rice_req => true, :external => true})
      @request.add('/j_spring_security_check', {"contents" => "j_username=#{opts[:user]}&amp;j_password=#{opts[:password]}&amp;submit=Submit",
      "content_type" => "application/x-www-form-urlencoded", "method" => "POST"}, {:rice_req => true, :external => true})
      #<request><http url='http://appserv-2.ks.kuali.net:9080/ks-rice/spring_security_login;jsessionid=7D49071C57E9E4D5375F71B934D9216D' version='1.1' method='GET'></http></request>
      
      
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