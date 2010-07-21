require 'spec_helper'

describe "/enumeration_contexts/index.html.erb" do
  include EnumerationContextsHelper

  before(:each) do
    assigns[:enumeration_contexts] = [
      stub_model(EnumerationContext),
      stub_model(EnumerationContext)
    ]
  end

  it "renders a list of enumeration_contexts" do
    render
  end
end
