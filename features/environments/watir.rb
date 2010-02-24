require 'spec'
require 'firewatir'
require 'spreadsheet'

browser = FireWatir::Firefox.new
book = Spreadsheet.open File.join(File.dirname(__FILE__), '..', 'results', 'kuali_template.xls')

Before do
  @browser = browser
  @book = book
end

at_exit do
  browser.close
  book.write File.join(File.dirname(__FILE__), '..', 'results', 'kuali_results.xls')
end