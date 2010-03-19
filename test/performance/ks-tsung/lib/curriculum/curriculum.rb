#!/usr/bin/env ruby

# 
# == Synopsis
#
# Organization class containing all operations around curriculum objects
#
# Author:: Kyle Campos (mailto:kcampos@rsmart.com)
#

class Curriculum
  
  # Load curriculum homepage
  def homepage
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp')
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/SecurityRpcService',
      {
        :method => 'POST',
        :content_type => 'text/x-gwt-rpc; charset=utf-8',
        :contents => "5|0|4|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|29F4EA1240F157649C12466F01F46F60|org.kuali.student.common.ui.client.service.SecurityRpcService|getPrincipalUsername|1|2|3|4|0|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
      {
        :method => 'POST',
        :content_type => 'text/x-gwt-rpc; charset=utf-8',
        :contents => "5|0|6|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|ks.rice.actionList.serviceAddress|1|2|3|4|1|5|6|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
      {
        :method => 'POST',
        :content_type => 'text/x-gwt-rpc; charset=utf-8',
        :contents => "5|0|6|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|ks.rice.docSearch.serviceAddress|1|2|3|4|1|5|6|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
      {
        :method => 'POST',
        :content_type => 'text/x-gwt-rpc; charset=utf-8',
        :contents => "5|0|6|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|lum.application.url|1|2|3|4|1|5|6|"
      }
    )
    
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/ServerPropertiesRpcService',
      {
        :method => 'POST',
        :content_type => 'text/x-gwt-rpc; charset=utf-8',
        :contents => "5|0|6|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|3DDEE7B5823674CCFB876EDB821C7743|org.kuali.student.common.ui.client.service.ServerPropertiesRpcService|get|java.lang.String|application.url|1|2|3|4|1|5|6|"
      }
    )
        
  end
  
  
  # Create a proposal
  #
  # Option: DEFAULT_VALUE
  # * 'mode': 'blank'
  def create_proposal(mode="blank", opts={})
    
    if(mode != "blank")
      # select template or copy course...
    end
    
    # Click Start blank proposal
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        :method => 'POST',
        :content_type => 'text/x-gwt-rpc; charset=utf-8',
        :contents => "5|0|4|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getMetadata|1|2|3|4|0|"
      }
    )
    
    # Duplicate - BUG ?
    @request.add('/org.kuali.student.lum.lu.ui.main.LUMMain/rpcservices/CreditCourseProposalRpcService',
      {
        :method => 'POST',
        :content_type => 'text/x-gwt-rpc; charset=utf-8',
        :contents => "5|0|4|http://appserv-1.ks.kuali.net/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|857D1F4CACBED76E9C114EE7D0A43D1B|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getMetadata|1|2|3|4|0|"
      }
    )
    
    # Pg1 - Governance
    
        
  end
  
end