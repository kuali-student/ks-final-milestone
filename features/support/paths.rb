module NavigationHelpers
  # Maps a name to a path. Used by the
  #
  #   When /^I go to (.+)$/ do |page_name|
  #
  # step definition in webrat_steps.rb
  #
  def path_to(page_name)
    case page_name
    
    when /the home\s?page/
      '/'
    when /the kuali\s?homepage/
      #'http://deploy.ks.kuali.org/staging'
      'http://localhost:8080/ks-embedded/'
    when /the logout\s?path/
      #'http://deploy.ks.kuali.org/staging/j_spring_security_logout'
      'http://localhost:8080/ks-embedded/j_spring_security_logout'
    when /the proposal\s?detail/
     #'http://deploy.ks.kuali.org/staging/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?docId=3021&command=displayActionListView#t0=org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager&t0p0k=view&t0p0v=EDIT_COURSE_PROPOSAL&t1=org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseProposalController&t1p0k=view&t1p0v=AUTHORS_RATIONALE'
      'http://localhost:8080/ks-embedded/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?docId=3021&command=displayActionListView#t0=org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager&t0p0k=view&t0p0v=EDIT_COURSE_PROPOSAL&t1=org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseProposalController&t1p0k=view&t1p0v=AUTHORS_RATIONALE'
           
    #'https://test.kuali.org/ks-dev/'
    #'https://test.kuali.org/ks-dev/j_spring_security_logout'
    #'https://test.kuali.org/ks-dev/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?docId=3021&command=displayActionListView#t0=org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager&t0p0k=view&t0p0v=EDIT_COURSE_PROPOSAL&t1=org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseProposalController&t1p0k=view&t1p0v=AUTHORS_RATIONALE'
    
     # Add more mappings here.
    # Here is a more fancy example:
    #
    #   when /^(.*)'s profile page$/i
    #     user_profile_path(User.find_by_login($1))

    else
      raise "Can't find mapping from \"#{page_name}\" to a path.\n" +
        "Now, go and add a mapping in #{__FILE__}"
    end
  end
  
  def soap_path_to(service_name)
    "http://localhost:8080/ks-embedded/services/#{service_name}?wsdl"
    #"https://test.kuali.org/ks-dev/services/#{service_name}?wsdl"
  end
end

World(NavigationHelpers)
