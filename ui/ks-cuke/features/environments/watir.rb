require 'spec'
require 'firewatir'

browser = FireWatir::Firefox.new

Before do
  @browser = browser
end

at_exit do
  browser.close
end