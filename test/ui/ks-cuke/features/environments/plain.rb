require "webrat/adapters/mechanize"
include Webrat::Methods
include Webrat::Matchers

Webrat.configure do |config|
  config.mode = :mechanize
end