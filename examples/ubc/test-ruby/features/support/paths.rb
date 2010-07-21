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
      'org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=curriculum'
    when /organization management/
      'org.kuali.student.core.organization.ui.OrgEntry/OrgEntry.jsp'
    
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
end

World(NavigationHelpers)
