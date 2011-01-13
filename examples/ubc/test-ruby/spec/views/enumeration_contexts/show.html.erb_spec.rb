require 'spec_helper'

describe "/enumeration_contexts/show.html.erb" do
  include EnumerationContextsHelper
  before(:each) do
    assigns[:enumeration_context] = @enumeration_context = stub_model(EnumerationContext)
  end

  it "renders attributes in <p>" do
    render
  end
end
