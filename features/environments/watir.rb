require 'spec'
require 'firewatir'
require 'spreadsheet'
require 'css_parser'
require 'open-uri'
require 'csv'
$input_hash = {}

browser = FireWatir::Firefox.new
book = Spreadsheet.open File.join(File.dirname(__FILE__), '..', 'results', 'kuali_template.xls')
userbook = Spreadsheet.open File.join(File.dirname(__FILE__), '..', 'results', 'kuali_users1.xls')
parser = CssParser::Parser.new

Before do
  @browser = browser
  @book = book
  @userbook = userbook
  @parser = parser
end

Before('@timer') do
  @time = Time.now
end

at_exit do
  browser.close
  book.write File.join(File.dirname(__FILE__), '..', 'results', 'kuali_results.xls')
  userbook.write File.join(File.dirname(__FILE__), '..', 'results', 'kuali_users1blank.xls')
end

After('@timer') do
  puts "Time to complete scenario: #{Time.now-@time}s"
end