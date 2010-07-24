Given /^(?:|I )am on (.+)$/ do |page_name|
  @browser.goto path_to(page_name)
end

#Then /^I fill in labeled field "([^\"]*)" for id with "([^\"]*)"$/ do |field, value|
#  text_field(:id, @browser.label(:text, field).for).set(value)
#end

When /^(?:|I )fill in labeled field "([^\"]*)" with "([^\"]*)"$/ do |field, value|
  @browser.text_field(:id, @browser.label(:text, /^#{field}/).for).set(value)
end

When /^(?:|I )fill in "([^\"]*)" with "([^\"]*)"$/ do |field, value|
  @browser.text_field(:name, field).set(value)
end

When /^(?:|I )fill in field number "([^\"]*)" with "([^\"]*)"$/ do |index, content|
  @browser.text_field(:index, index).set(content)
end

When /^(?:|I )fill in next field with "([^\"]*)"$/ do |content|
  index = @inputscount + 1
  @browser.text_field(:index, index).set(content)
  @inputscount = index
end

When /^(?:|I )fill in last field with "([^\"]*)"$/ do |content|
  index = @inputscount
  @browser.text_field(:index, index).set(content)
  #@inputscount = index
  #puts "index: #{index}"
end

When /^(?:|I )fill field id "([^\"]*)" with "([^\"]*)"$/ do |id, content|
  @browser.text_field(:id, id).set(content)
end

When /^(?:|I )fill in text_area number "([^\"]*)" with "([^\"]*)"$/ do |index, content|
  @browser.text_field(:index, index).textarea.set(content)
end

Then /^(?:|I )should see "([^\"]*)"$/ do |text|
  @browser.text.should include(text)
end

Then /^I click the field number "([^\"]*)"$/ do |index|
  @browser.text_field(:index, index).click
end

Then /^I focus on the field number "([^\"]*)"$/ do |index|
  @browser.text_field(:index, index).focus
end

When /^(?:|I )click "([^\"]*)"$/ do |btng|
  @browser.button(:name, btng).click
end

When /^(?:|I )press "([^\"]*)"$/ do |btng|
  @browser.button(:text, btng).click
end

When /^(?:|I )press button number "([^\"]*)"$/ do |index|
  @browser.button(:index, index).click
end

When /^(?:|I )follow "([^\"]*)"$/ do |text|
    @browser.link(:text, text).click
end

When /^(?:|I )follow link "([^\"]*)" in frameclass "([^\"]*)"$/ do |text,frame_class|
    @browser.frame(:class, frame_class).link(:text, /^#{text}/).click
end

When /^(?:|I )follow linkid "([^\"]*)"$/ do |id|
    @browser.link(:id, id).click
end

Then /^I should see linkid "([^\"]*)" with text "([^\"]*)"$/ do |id, text|
    @browser.link(:id, id).text.should == text
end

Then /^I should see linkindex "([^\"]*)" with text "([^\"]*)"$/ do |id, text|
    @browser.link(:index, id).text.should == text
end

When /^(?:|I )set "([^\"]*)" from "([^\"]*)"$/ do |value, field|
  @browser.select_list(:name, field).set(value)
end

When /^(?:|I )select "([^\"]*)" from selectbox number "([^\"]*)"$/ do |value, index|
  @browser.select_list(:index, index).set(value)
end

When /^(?:|I )select "([^\"]*)" from selectbox class "([^\"]*)"$/ do |value, field|
  @browser.select_list(:class, field).set(value)
end

When /^(?:|I )select "([^\"]*)" from "([^\"]*)"$/ do |value, field|
  @browser.select_list(:name, field).select(value)
end

When /^(?:|I )click dropdown box "([^\"]*)"$/ do |field|
  @browser.select_list(:name, field).click
end

When /^(?:|I )set checkbox id "([^\"]*)"$/ do |checkbox_id|
  @browser.checkbox(:id, checkbox_id).set
end

When /^(?:|I )clear checkbox id "([^\"]*)"$/ do |checkbox_id|
  @browser.checkbox(:id, checkbox_id).clear
end

When /^(?:|I )set checkbox number "([^\"]*)"$/ do |checkbox_id|
  @browser.checkbox(:index, checkbox_id).set
end

When /^(?:|I )clear checkbox number "([^\"]*)"$/ do |checkbox_id|
  @browser.checkbox(:index, checkbox_id).clear
end

When /^(?:|I )click the "([^\"]*)" label$/ do |field|
	@browser.div(:text, field).click
end

When /^(?:|I )click the label "([^\"]*)" with class "([^\"]*)"$/ do |field, classid|
	@browser.div(:class => classid,:text => field).click
end

When /^(?:|I )click div with class "([^\"]*)"$/ do |classid|
	@browser.div(:class, classid).click
end

#When /^I click the "([^\"]*)"\txpath$/ do |arg1|
When /^(?:|I )click the "([^\"]*)" xpath$/ do |field|
	@browser.element_by_xpath(field).click()
end

After do
  @browser.goto path_to("the logout path")
end

Then /^(?:|I )write "([^\"]*)" into collumn "([^\"]*)" row "([^\"]*)" of the report$/ do |content, column, row|
  @book.worksheet(0).row(row.to_i-1)[column.to_i-1] = content
end

When /^I read css stylesheet number "([^\"]*)"$/ do |stylesheet_no|
  @browser.goto @browser.element_by_xpath("/html/head/link[#{stylesheet_no}]").href
  @parser.add_block!(@browser.text)
  @browser.back
end

When /^I read inline style number "([^\"]*)"$/ do |stylesheet_no|
  @parser.add_block!(@browser.element_by_xpath("/html/head/style[#{stylesheet_no}]").text)
end

Then /^the css parser should contain the selector "([^\"]*)" with rules "([^\"]*)"$/ do |selector, rule|
  @parser.find_by_selector(selector).to_s.gsub(/\s+/, "").should == rule.gsub(/\s+/, "")
end

Then /^the css parser should contain the selector "([^\"]*)" with rule "([^\"]*)"$/ do |selector, rule|
  @parser.find_by_selector(selector).to_s.gsub(/\s+/, "").split(';').include?(rule.gsub(/\s+|;/, "")).should be_true
end

Then /^the css parser should find the selector "([^\"]*)" with rule "([^\"]*)"$/ do |selector, rule|
  found = false
  links = @browser.elements_by_xpath("/html/head/link")
  styles = @browser.elements_by_xpath("/html/head/style")
  if styles
    styles.each_with_index do |style, index|
      parser = CssParser::Parser.new
      parser.add_block!(style.text)
      found = true if parser.find_by_selector(selector).to_s.gsub(/\s+/, "").split(';').include?(rule.gsub(/\s+|;/, ""))
      puts "found your selector with the rule in style no: #{index+1}" if found
    end
  elsif links
    links.each_with_index do |style, links|
      @browser.goto link.href
      parser = CssParser::Parser.new
      parser.add_block!(@browser.text)
      found = true if parser.find_by_selector(selector).to_s.gsub(/\s+/, "").split(';').include?(rule.gsub(/\s+|;/, ""))
      @browser.back
      puts "found your selector with the rule in  link no: #{index+1}" if found
    end
  end
  found.should be_true
end

Then /^I should see tag "([^\"]*)" with how "([^\"]*)" and what "([^\"]*)"$/ do |tag, how, what|
  @browser.instance_eval("#{tag} how.to_sym, '#{what}'").should_not raise_error
end

Then /^I should see image "([^\"]*)"$/ do |content|
  @browser.image(:src, content).exists?.should be_true
end

Then /^I should see image class "([^\"]*)"$/ do |content|
  @browser.image(:class, content).exists?.should be_true
end

When /^(?:|I )click back one page$/ do ||
  @browser.back
end

Then /^I should see table class "([^\"]*)"$/ do |table_c|
  @browser.table(:class, table_c.to_s).should exist
end

Then /^(?:|I )should see the "([^\"]*)" label$/ do |field|
	@browser.div(:text, field).should exist
end

Then /^(?:|I )should see the "([^\"]*)" label in frame_id "([^\"]*)"$/ do |field, frame_id|
	@browser.frame(:id, frame_id).div(:text, field).should exist
end

Then /^(?:|I )should see the "([^\"]*)" label_class$/ do |field|
	@browser.div(:class, field).should exist
end

Then /^(?:|I )should see the "([^\"]*)" text in xpath "([^\"]*)"$/ do |content, field|
	@browser.element_by_xpath(field).text.contains_text(content)
end

Then /^(?:|I )should see checkbox id "([^\"]*)" in table class "([^\"]*)"$/ do |checkbox_id, table_class|
  @browser.table(:class, table_class).checkbox(:id, checkbox_id).should exist
end

Then /^(?:|I )should see checkbox id "([^\"]*)"$/ do |checkbox_id|
  @browser.checkbox(:id, checkbox_id).should exist
end

Then /^(?:|I )should see checkbox number "([^\"]*)"$/ do |checkbox_id|
  @browser.checkbox(:index, checkbox_id).should exist
end

Then /^(?:|I )wait for "([^\"]*)" seconds?$/ do |seconds|
  tries = 0
  found = true
  numseconds = Integer(seconds)
  until false do
    sleep 1.0
    tries +=1
    if tries > numseconds
        found = true
        break
    end  
  end   
  found.should be_true
end

Then /^(?:|I )wait for div "([^\"]*)"$/ do |field|
  tries = 0
  found = true
  until @browser.div(:text, field).exists? do
    sleep 1.0
    tries +=1
    if tries > 10
        break
    end  
  end   
  found.should be_true
end

Then /^I should see frame id "([^\"]*)"$/ do |frame_id|
  @browser.frame(:id, frame_id).should exist
end

def rescue_wait_retry(exception = Watir::Exception::UnknownObjectException, times = 15, seconds=1, &block)
  begin
    return yield
  rescue exception => e
    sleep seconds
    @browser.wait
    if (times -= 1) > 0
      retry
    end 
  end 
  yield    
end

Then /^I press ajax buttons "([^\"]*)" with "([^\"]*)"$/ do |btng1, btng2|  
  @browser.button(:text, btng1).click
  rescue_wait_retry {@browser.button(:text, btng2).click}
end

def rescue_wait_enabled(exception = Watir::Exception::ObjectDisabledException, times = 15, seconds=1, &block)
  begin
    return yield
  rescue exception => e
    sleep seconds
    @browser.wait
    if (times -= 1) > 0
      retry
    end 
  end  
  yield    
end

def rescue_wait_listvalue(exception = Watir::Exception::NoValueFoundException, times = 15, seconds=2, &block)
  begin
    return yield
  rescue exception => e
    sleep seconds
    @browser.wait
    if (times -= 1) > 0
      retry
    end 
  end  
  yield    
end


When /^(?:|I )set "([^\"]*)" from ajax selectbox number "([^\"]*)"$/ do |value, index|
  Watir::Waiter::wait_until {@browser.select_list(:index, index).exists?}
  rescue_wait_listvalue {@browser.select_list(:index, index).set(value)}
end

When /^(?:|I )select "([^\"]*)" from next ajax selectbox$/ do |value|
  index = @listscount+1
  Watir::Waiter::wait_until {@browser.select_list(:index, index).exists?}
  rescue_wait_listvalue {@browser.select_list(:index, index).set(value)}
  @listscount = index
end

When /^(?:|I )select sheetvalue from next ajax selectbox$/ do ||
  index = @listscount+1
  Watir::Waiter::wait_until {@browser.select_list(:index, index).exists?}
  rescue_wait_listvalue {@browser.select_list(:index, index).set(@sheetvalue)}
  @listscount = index
end

When /^(?:|I )select "([^\"]*)" from ajax selectbox number "([^\"]*)"$/ do |value, index|
  Watir::Waiter::wait_until {@browser.select_list(:index, index).exists?}
  rescue_wait_listvalue {@browser.select_list(:index, index).select(value)}
end

Then /^I press ajax button "([^\"]*)"$/ do |btng|
  rescue_wait_retry {@browser.button(:text, btng).click}
end

Then /^I press ajax button with index "([^\"]*)"$/ do |btng|
  rescue_wait_retry {@browser.button(:index, btng).click}
end

Then /^I press ajax link "([^\"]*)"$/ do |text|
  rescue_wait_retry {@browser.link(:text, text).click}
end

Then /^I follow ajax link "([^\"]*)"$/ do |text|
  rescue_wait_retry {@browser.link(:text, text).click}
end

Then /^I follow link with class "([^\"]*)"$/ do |text|
  rescue_wait_retry {@browser.link(:class, text).click}
end

Then /^I follow link with href "([^\"]*)"$/ do |text|
  @browser.link(:href, text).click
end

Then /^I follow ajax link with href "([^\"]*)"$/ do |text|
  rescue_wait_retry {@browser.link(:href, text).click}
end

Then /^I goto href "([^\"]*)"$/ do |text|
  @browser.goto(text)
end

Then /^I press ajax label "([^\"]*)"$/ do |text|
  rescue_wait_retry {@browser.div(:text, text).click}
end

Then /^I should see ajax label "([^\"]*)"$/ do |text|
  rescue_wait_retry {@browser.div(:text, text).should exist}
end

Then /^I follow link "([^\"]*)" in div_class "([^\"]*)"$/ do |text, div_class|
  @browser.div(:class, div_class).link(:text, text).click
end

Then /^I follow link "([^\"]*)" with class "([^\"]*)"$/ do |text, link_class|
  @browser.link(:class => link_class, :text => text).click
end

Then /^I follow link "([^\"]*)" with index "([^\"]*)"$/ do |text, link_index|
  @browser.link(:index => link_index, :text => text).click
end

Then /^I follow ajax link "([^\"]*)" with class "([^\"]*)"$/ do |text, link_class|
  rescue_wait_retry {@browser.link(:class => link_class, :text => text).click}
end

Then /^I follow link_id "([^\"]*)"$/ do |link_id|
  @browser.link(:id, link_id).click
end

Then /^I follow ajax link_id "([^\"]*)"$/ do |link_id|
  rescue_wait_retry {@browser.link(:id, link_id).click}
end

Then /^I follow ajax link "([^\"]*)" with div_class "([^\"]*)"$/ do |text, div_class|
  rescue_wait_retry {@browser.link(:text, text).div(:class, div_class).click}
end

When /^(?:|I )click ajax label "([^\"]*)" with class "([^\"]*)"$/ do |field, classid|
	rescue_wait_retry {@browser.div(:class => classid,:text => field).click}
end

Then /^I press ajax enabled button "([^\"]*)"$/ do |btng|  
  rescue_wait_enabled {@browser.button(:text, btng).click}
end

Then /^I click tag "([^\"]*)" with how "([^\"]*)" and what "([^\"]*)"$/ do |tag, how, what|
  @browser.instance_eval("#{tag} how.to_sym,'#{what}'").click
end

Then /^I wait for Enter key$/ do
  puts "Please press Enter key to continue"
  while true
  	break if STDIN.gets
  	sleep 1
  end
end

Then /^(?:|I )read column "([^\"]*)" row "([^\"]*)" of the userbook$/ do |column, row|
   @sheetvalue = @userbook.worksheet(0).row(row.to_i-1)[column.to_i-1]
end

When /^(?:|I )fill in field number "([^\"]*)" with userbook value$/ do |index|
    @browser.text_field(:index, index).set(@sheetvalue)
end

When /^(?:|I )fill in next field with the userbook value$/ do ||
    index = @inputscount + 1
    @browser.text_field(:index, index).set(@sheetvalue)
    @inputscount = index
end

Then /^I refresh browser$/ do 
  @browser.refresh()
end

When /^(?:|I )press in tableclass "([^\"]*)" button number "([^\"]*)"$/ do |tableclass, index|
  @browser.table(:class, tableclass).button(:index, index).click
end

Then /^I click "([^\"]*)" in divclass "([^\"]*)" table number "([^\"]*)"$/ do |content, divclass, tableid|
  foundone = false
  found = false
  rowc = @browser.div(:class, divclass).table(:index, tableid).row_count()
  for row in 1..rowc.to_i
    cont1 = @browser.div(:class, divclass).table(:index, tableid)[row][1].to_s
    found = true if (@browser.div(:class, divclass).table(:index, tableid)[row][1].to_s == content)
    if found
      @browser.div(:class, divclass).table(:index, tableid).cell(:role=>'menuitem', :text=>@sheetvalue).click
      puts "row again #{row}: #{found}: #{cont1}"
      foundone = true
      break
    end  
  end
  foundone.should be_true
end

Then /^I click item in divclass "([^\"]*)" table number "([^\"]*)" with userbook value$/ do |divclass, tableid|
  @browser.div(:class, divclass).table(:index, tableid).cell(:role=>'menuitem', :text=>@sheetvalue).click
end 

Then /^I click item in divclass "([^\"]*)" table number "([^\"]*)" with "([^\"]*)"$/ do |divclass, tableid,content|
  @browser.div(:class, divclass).table(:index, tableid).cell(:role=>'menuitem', :text=>content).click
end

Then /^I need to find number of input fields$/ do ||
  found = true
  count =0
  inputs = @browser.text_fields
  if inputs
    inputs.each_with_index do ||
      count +=1
      #puts "found input no: #{count}"
    end
  else
      #puts "no inputs: #{count}"
  end
  @inputscount = count
  found.should be_true
end

Then /^I need to find number of selectboxes$/ do ||
  found = true
  count =0
  #inputs = @browser.locate_elements("input")
  lists = @browser.select_lists
  if lists
    lists.each_with_index do ||
      count +=1
      #puts "found selectbox no: #{count}"
    end
  else
      #puts "no selectbox: #{count}"
  end
  @listscount = count
  found.should be_true
end

When /^(?:|I )click browser$/ do ||
      @browser.click
end

Then /^I map csv users from "([^\"]*)" to "([^\"]*)" rows to organizations$/ do |from, last|
  csv_users = CSV.open('/Users/milos/kuali2rc1/features/results/kuali_users1.csv', 'r', ',') 
  csv_users.shift
  rowin = 2.to_i
  rowin1 = from.to_i
  rowin2 = last.to_i
  previousdept = "nodept" 
  csv_users.each do |row|
   if (rowin >= rowin1) && (rowin<=rowin2)
     if (previousdept != row[3])
       if (previousdept!="nodept")
         @browser.link(:text,"Save").click
          rescue_wait_retry {@browser.link(:text, "Ok").click}
          #puts "row: #{rowin}"       
       end     
         @sheetvalue = row[3]
         @browser.div(:text, "Search/Modify").click    
         previousdept = row[3] 
         sleep 1.0
         #@browser.text_field(:index, 1).set(@sheetvalue)
         #@browser.text_field(:index, 3).set(@sheetvalue)
         Findnumberofinputfields()
         Filllastfieldwiththeuserbookvalue()
         sleep 1.0
         rescue_wait_retry {@browser.div(:class, 'suggestPopupMiddleCenterInner suggestPopupContent').table(:index, 1).cell(:role=>'menuitem', :text=>@sheetvalue).click}
         sleep 1.0
         @browser.link(:text, "Modify").click
         sleep 2.0
         rescue_wait_retry {@browser.div(:text, "Positions/Members").click}
         Findnumberofinputfields()
         Findnumberofselectboxes()
         sleep 1.0
     end
     sleep 1.0
     @browser.link(:text, "Add").click
     @sheetvalue = row[0]
     sleep 1.0
     Fillnextfieldwiththeuserbookvalue()
     @sheetvalue = row[4]
     wait_table1_exists()
     @browser.div(:class, 'suggestPopupMiddleCenterInner suggestPopupContent').table(:index, 1).cell(:role=>'menuitem', :text=>@sheetvalue).click
     @sheetvalue = row[2]
     sleep 1.0
     Selectsheetvaluefromnextajaxselectbox()
     Fillinnextfieldwith("05042010")
     @inputscount +=1
     sleep 1.0
     puts "row: #{rowin}"
     rowin += 1
    else
       if (rowin<rowin1)
         rowin += 1
      else
        @browser.link(:text,"Save").click
        rescue_wait_retry {@browser.link(:text, "Ok").click}     
         break
      end         
    end
  end  
end

def wait_table1_exists()
  tries = 0
  found = false
  until found do  
    sleep 1.0
    found = @browser.div(:class, 'suggestPopupMiddleCenterInner suggestPopupContent').exists?
    tries +=1
    #if tries > 20
    if tries > 40
        #we want just wait and see
        found = true
        break
    end  
  end   
  found.should be_true
end

def Findnumberofinputfields()
  count =0
  #inputs = @browser.locate_elements("input")
  inputs = @browser.text_fields
  if inputs
    inputs.each_with_index do ||
      count +=1
      #puts "found input no: #{count}"
    end
  else
      #puts "no inputs: #{count}"
  end
  @inputscount = count
end

def Findnumberofselectboxes()
  count =0
  lists = @browser.select_lists
  if lists
    lists.each_with_index do ||
      count +=1
    end
  else
      #puts "no selectbox: #{count}"
  end
  @listscount = count
end

def Fillnextfieldwiththeuserbookvalue()
    index = @inputscount + 1
    @browser.text_field(:index, index).set(@sheetvalue)
    @inputscount = index
end

def Filllastfieldwiththeuserbookvalue()
    index = @inputscount
    @browser.text_field(:index, index).set(@sheetvalue)
    #@inputscount = index
end

def Selectsheetvaluefromnextajaxselectbox()
  index = @listscount+1
  rescue_wait_listvalue {@browser.select_list(:index, index).set(@sheetvalue)}
  @listscount = index
end

def Fillinnextfieldwith(content)
  index = @inputscount + 1
  @browser.text_field(:index, index).set(content)
  @inputscount = index
end