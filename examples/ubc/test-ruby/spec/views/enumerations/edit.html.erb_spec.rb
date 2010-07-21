require 'spec_helper'

describe "/enumerations/edit.html.erb" do
  include EnumerationsHelper

  before(:each) do
    assigns[:enumeration] = @enumeration = stub_model(Enumeration,
      :new_record? => false,
      :id => "value for id",
      :abbrev_val => "value for abbrev_val",
      :cd => "value for cd",
      :enum_key => "value for enum_key",
      :sort_key => 1,
      :val => "value for val"
    )
  end

  it "renders the edit enumeration form" do
    render

    response.should have_tag("form[action=#{enumeration_path(@enumeration)}][method=post]") do
      with_tag('input#enumeration_id[name=?]', "enumeration[id]")
      with_tag('input#enumeration_abbrev_val[name=?]', "enumeration[abbrev_val]")
      with_tag('input#enumeration_cd[name=?]', "enumeration[cd]")
      with_tag('input#enumeration_enum_key[name=?]', "enumeration[enum_key]")
      with_tag('input#enumeration_sort_key[name=?]', "enumeration[sort_key]")
      with_tag('input#enumeration_val[name=?]', "enumeration[val]")
    end
  end
end
