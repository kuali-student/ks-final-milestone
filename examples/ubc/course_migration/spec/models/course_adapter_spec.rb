require 'spec_helper'

describe CourseAdapter do

  before(:each) do
    @ca = CourseAdapter.new
  end

  it "should create a course into CDM" do
    sisCourse = CrrcCrsCatalogt.new :crs_code => 'CHEM300',
                                    :crs_shorttitle => 'AQUEOUS ENVIRMNT',
                                    :crs_longtitle => 'Aqueous Environmental Chemistry',
                                    :crs_dpt_cd => 'CHEM',
                                    :crs_no => '300',
                                    :crs_description => "Introduction to basic structures, properties, and reactions of organic compounds; bonding, stereochemistry, aromaticity, substitutions, eliminations, oxidation-reductions; structural determinations of hydrocarbons, alcohols and related compounds. Credit will be given for only one pair of CHEM 203, 204 or 231, 232.",
                                    :crs_snte_appr_dt => Date.new(2009,11,3),
                                    :crs_startterm => "2010S",
                                    :crs_credit => "3.0"

    assert @ca.create_course(sisCourse)

  end

  it "should calculate the effective date" do
    sisCourse = CrrcCrsCatalogt.new :crs_startterm => "1998W"
    @ca.effective_date(sisCourse).should == Date.new(1998, 1, 1)    
  end

  it "should calculate the first expected atp from start term" do
    sisCourse = CrrcCrsCatalogt.new :crs_startterm => "1998W"
    @ca.expected_first_atp(sisCourse).should == "Winter"
    sisCourse.crs_startterm = "1998S"
    @ca.expected_first_atp(sisCourse).should == "Summer"
  end

  it "should calculate atp duration weeks" do
    # assumption, credits determine number of weeks of course
    # assumption, duration is only 12 and 24 weeks
    sisCourse = CrrcCrsCatalogt.new :crs_credit => "3"
    @ca.std_duration_time_quantity(sisCourse).should == 12
    sisCourse.crs_credit = "6"
    @ca.std_duration_time_quantity(sisCourse).should == 24
    sisCourse.crs_credit = "4"
    @ca.std_duration_time_quantity(sisCourse).should == 24
    sisCourse.crs_credit = "7"
    @ca.std_duration_time_quantity(sisCourse).should == 24
    sisCourse.crs_credit = "2"
    @ca.std_duration_time_quantity(sisCourse).should == 12
    sisCourse.crs_credit = nil
    @ca.std_duration_time_quantity(sisCourse).should == 12
    sisCourse.crs_credit = "3.0"
    @ca.std_duration_time_quantity(sisCourse).should == 12
  end

  #assumption that end term will be last day of the year
  it "should calculate expiration date from endterm" do
    sisCourse = CrrcCrsCatalogt.new :crs_endterm => "2004S"
    @ca.expiration_date(sisCourse).should == Date.new(2004, 12, 31)
  end

  it "should return the year from a term" do
    @ca.year_from_term("2009S").should == "2009"
  end

  it "should calculate the level from a course number" do
    sisCourse = CrrcCrsCatalogt.new :crs_no => "483"
    @ca.level(sisCourse).should == "400"
    sisCourse.crs_no = nil
    @ca.level(sisCourse).should == ""
  end

  it "should create courses" do
    sisCourse1 = CrrcCrsCatalogt.new :crs_code => 'CHEM301',
                                    :crs_shorttitle => 'AQUEOUS ENVIRMNT',
                                    :crs_longtitle => 'Aqueous Environmental Chemistry',
                                    :crs_dpt_cd => 'CHEM',
                                    :crs_no => '301',
                                    :crs_description => "Introduction to basic structures, properties, and reactions of organic compounds; bonding, stereochemistry, aromaticity, substitutions, eliminations, oxidation-reductions; structural determinations of hydrocarbons, alcohols and related compounds. Credit will be given for only one pair of CHEM 203, 204 or 231, 232.",
                                    :crs_snte_appr_dt => Date.new(2009,11,3),
                                    :crs_startterm => "2010S",
                                    :crs_credit => "3.0"
    sisCourse2 = CrrcCrsCatalogt.new :crs_code => 'CHEM302',
                                    :crs_shorttitle => 'ENVIRMNT CHEM',
                                    :crs_longtitle => 'Environmental Chemistry',
                                    :crs_dpt_cd => 'CHEM',
                                    :crs_no => '302',
                                    :crs_description => "Introduction to basic structures, properties, and reactions of organic compounds; bonding, stereochemistry, aromaticity, substitutions, eliminations, oxidation-reductions; structural determinations of hydrocarbons, alcohols and related compounds. Credit will be given for only one pair of CHEM 203, 204 or 231, 232.",
                                    :crs_snte_appr_dt => Date.new(2009,11,3),
                                    :crs_startterm => "2010S",
                                    :crs_credit => "3.0"
    courses = [sisCourse1, sisCourse2]
    @ca.create_courses(courses)
  end
end
