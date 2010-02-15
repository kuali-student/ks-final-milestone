ENV["RAILS_ENV"] ||= "cucumber"
require File.expand_path(File.dirname(__FILE__) + '/../../config/environment')
require 'cucumber/rails/rspec'
require 'cucumber/rails/world'

Webrat.configure do |config|
  config.mode = :selenium
  config.application_framework = :external
  config.application_port = 8080
end
class ActiveSupport::TestCase
  setup do |session|
    session.host! "localhost:8080"
  end
end