require 'spec_helper'

describe "/enumerations/index.html.erb" do
  include EnumerationsHelper

  before(:each) do
    assigns[:enumerations] = [
      stub_model(Enumeration,
        :id => "value for id",
        :abbrev_val => "value for abbrev_val",
        :cd => "value for cd",
        :enum_key => "value for enum_key",
        :sort_key => 1,
        :val => "value for val"
      ),
      stub_model(Enumeration,
        :id => "value for id",
        :abbrev_val => "value for abbrev_val",
        :cd => "value for cd",
        :enum_key => "value for enum_key",
        :sort_key => 1,
        :val => "value for val"
      )
    ]
  end

  it "renders a list of enumerations" do
    render
    response.should have_tag("tr>td", "value for id".to_s, 2)
    response.should have_tag("tr>td", "value for abbrev_val".to_s, 2)
    response.should have_tag("tr>td", "value for cd".to_s, 2)
    response.should have_tag("tr>td", "value for enum_key".to_s, 2)
    response.should have_tag("tr>td", 1.to_s, 2)
    response.should have_tag("tr>td", "value for val".to_s, 2)
  end
end
