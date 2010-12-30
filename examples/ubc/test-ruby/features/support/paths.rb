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
      ''
    when /logout/
      'j_spring_security_logout'
    when /curriculum management/
      'org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp#/HOME/CURRICULUM_HOME'
    when /organization management/
      'org.kuali.student.core.organization.ui.OrgEntry/OrgEntry.jsp'
    when /course proposal/
      'org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp#/HOME/CURRICULUM_HOME/COURSE_PROPOSAL'
    when /action list/
      'org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp#/HOME'        


    # Add more mappings here.
    # Here is an example that pulls values out of the Regexp:
    #
    #   when /^(.*)'s profile page$/i
    #     user_profile_path(User.find_by_login($1))

    else
      begin
        page_name =~ /the (.*) page/
        path_components = $1.split(/\s+/)
        self.send(path_components.push('path').join('_').to_sym)
      rescue Object => e
      raise "Can't find mapping from \"#{page_name}\" to a path.\n" +
        "Now, go and add a mapping in #{__FILE__}"
      end
    end
  end
end

World(NavigationHelpers)
