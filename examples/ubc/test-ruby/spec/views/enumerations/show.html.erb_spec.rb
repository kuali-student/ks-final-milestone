require 'spec_helper'

describe "/enumerations/show.html.erb" do
  include EnumerationsHelper
  before(:each) do
    assigns[:enumeration] = @enumeration = stub_model(Enumeration,
      :id => "value for id",
      :abbrev_val => "value for abbrev_val",
      :cd => "value for cd",
      :enum_key => "value for enum_key",
      :sort_key => 1,
      :val => "value for val"
    )
  end

  it "renders attributes in <p>" do
    render
    response.should have_text(/value\ for\ id/)
    response.should have_text(/value\ for\ abbrev_val/)
    response.should have_text(/value\ for\ cd/)
    response.should have_text(/value\ for\ enum_key/)
    response.should have_text(/1/)
    response.should have_text(/value\ for\ val/)
  end
end
