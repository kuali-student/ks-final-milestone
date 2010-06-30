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
          'contents' => "5|0|4|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|29F4EA1240F157649C12466F01F46F60|org.kuali.student.common.ui.client.service.SecurityRpcService|getPrincipalUsername|1|2|3|4|0|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|13|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.util.List|java.util.Arrays$ArrayList/1243019747|[Ljava.lang.String;/2600011424|application.url|ks.rice.docSearch.serviceAddress|lum.application.url|ks.rice.url|ks.rice.label|ks.application.version|1|2|3|4|1|5|6|7|6|8|9|10|11|12|13|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|8|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.util.List|java.util.Arrays$ArrayList/1243019747|[Ljava.lang.String;/2600011424|ks.rice.actionList.serviceAddress|1|2|3|4|1|5|6|7|1|8|"
        }
      )
      
      @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
        {
          'method' => 'POST',
          'content_type' => 'text/x-gwt-rpc; charset=utf-8',
          'contents' => "5|0|6|#{@request.url}/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|ks.lum.ui.displayOnlyActiveLoCategories|1|2|3|4|1|5|6|"
        }
      )
      
      @request.add('/kew/ActionList.do', {}, {:rice_req => true, :external => true})
      #@request.add("#{@request.config.rice_server}/#{@request.config.rice_context}/spring_security_login")
      
      #@request.add('/')
      
    else
      ks_url_escaped = URI.escape("#{@request.url}/j_spring_cas_security_check", Regexp.new("[^#{URI::PATTERN::UNRESERVED}]"))
      @request.add("#{@request.config.sso}/login?service=#{ks_url_escaped}")
    end
    
  end
  
  # Logout of KS
  def logout
    
    @request.add('j_spring_security_logout')
    # loading login page
    @request.add('/')
    @request.add('/spring_security_login')
    
  end
  
  # Bring up homepage
  def load_homepage
    
  end
  
end