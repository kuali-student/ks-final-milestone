require 'spec_helper'

describe "/enumeration_contexts/edit.html.erb" do
  include EnumerationContextsHelper

  before(:each) do
    assigns[:enumeration_context] = @enumeration_context = stub_model(EnumerationContext,
      :new_record? => false
    )
  end

  it "renders the edit enumeration_context form" do
    render

    response.should have_tag("form[action=#{enumeration_context_path(@enumeration_context)}][method=post]") do
    end
  end
end
