CDM_CONFIGURED = " - UBC CDM Configured"
@old_campus_val
Before ('@campusdata') do
  # configure a current campus
  e = Enumeration.find_by_enum_key("kuali.lu.campusLocation")
  @old_campus_val = e.val
  e.val = e.val + CDM_CONFIGURED
  e.save
  
  # add a new campus
  Enumeration.create :enumerationid => "1000000", :abbrev_val => "UBCTEST", :cd => "UBCTEST", :enum_key => "kuali.lu.campusLocation", :sort_key => 1, :val => "UBC Test Campus"

end

After ('@campusdata') do |scenario|
  # remove new campus
  Enumeration.destroy("1000000")

  # change campus value back
  e = Enumeration.find(:all, :conditions => ["val like ?", "%#{CDM_CONFIGURED}%"]).first
  e.should_not be_nil
  e.val = @old_campus_val
  e.save
end