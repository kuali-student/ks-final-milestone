require 'spec_helper'

describe "/enumeration_contexts/new.html.erb" do
  include EnumerationContextsHelper

  before(:each) do
    assigns[:enumeration_context] = stub_model(EnumerationContext,
      :new_record? => true
    )
  end

  it "renders new enumeration_context form" do
    render

    response.should have_tag("form[action=?][method=post]", enumeration_contexts_path) do
    end
  end
end
