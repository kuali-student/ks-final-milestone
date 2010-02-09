#!/usr/bin/env ruby

# 
# == Synopsis
#
# Authentication class containing all operations around app authentication
#
# Author:: Kyle Campos (mailto:kcampos@rsmart.com)
#

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
      :thinktime => 5
    }
    
    opts = defaults.merge(opts)
    
    @request.add('/')
    @request.add('/spring_security_login')
    @request.add_thinktime(opts[:thinktime])
    @request.add('/j_spring_security_check', {"contents" => "j_username=#{opts[:user]}&amp;j_password=#{opts[:password]}&amp;submit=Submit",
      "content_type" => "application/x-www-form-urlencoded", "method" => "POST"})
    @request.add('/index.html')
    @request.add('kru_banner2.jpg')
    
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