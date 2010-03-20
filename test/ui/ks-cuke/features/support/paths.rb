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
      'http://localhost:8080/ks-embedded'
    when /the logout\s?path/
      'http://localhost:8080/ks-embedded/j_spring_security_logout'
    
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
  end
end

World(NavigationHelpers)
