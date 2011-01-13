require 'spec_helper'
require 'mocha'
require 'java'
include_class  'ca.ubc.student.kuali.lum.workflow.qualifierresolver.DepartmentQualifierResolver'
include_class 'org.kuali.student.core.organization.dto.OrgInfo'
include_class 'java.util.ArrayList'
include_class 'org.kuali.rice.kim.bo.types.dto.AttributeSet'
include_class 'org.kuali.rice.kew.engine.RouteContext'
include_class 'org.kuali.rice.student.bo.KualiStudentKimAttributes'
include_class 'org.kuali.student.lum.workflow.qualifierresolver.AbstractCocOrgQualifierResolver'
include_class 'org.kuali.rice.kew.role.XPathQualifierResolver'

describe "DepartmentQualifierResolver" do

  it "should parse the doc id of a URL" do
    url =  '/cdm-embedded/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?docId=3007&command=displayActionListView#/HOME'
    sub_string = /docId=([0-9]*)\&?/.match(url)
    assert sub_string[1].should == "3007"
  end

  it "should return the department organization attributes" do


    o = OrgInfo.new
    id = "1000"


    a = AttributeSet.new
    a.put("orgId", id)

    list = ArrayList.new
    list.add(a)


    dqr = DepartmentQualifierResolver.new
    short_name = "org name"

    o.set_id(id)
    o.set_short_name(short_name)



    o.get_id.class.should be String
    o.get_id.should == id
    o.get_short_name.should == short_name

    org_list = ArrayList.new
    org_list.add(o)

    dqr.expects(:get_organizations_from_list).returns(org_list)

    org_id_list = ArrayList.new
    org_id_list.add("1")

    attributes = dqr.attribute_department_set_from_search_result(dqr.get_organizations_from_list(org_id_list))

    attributes.size.should == 1
    attributes.get(0).is_empty.should be false

    attributes.get(0).should_not be(nil)

    attributes.get(0).class.should be(AttributeSet)

    attributes.get(0).get('department').should == short_name
    attributes.get(0).get('departmentId').should == id

    r = RouteContext.new

    #DepartmentQualifierResolver.any_instance.stubs(:get_qualification_id).with(anything).returns(id)
#    DepartmentQualifierResolver.any_instance.stubs("getQualificationId").returns(id)
    #XPathQualifierResolver.any_instance.stubs(:resolve).returns(list)
    dqr.expects(:get_qualification_id).returns(id)


#    dqr.expects("getQualificationId").returns(id)
#    dqr.expects(:get_qualification_id).with(r).returns(id)
    #dqr.getQualificationId(r).should == id

    #partial = flexmock(dqr)
    #partial.should_receive(:get_qualification_id).and_return(id)

    dqr.get_qualification_id(r).should == id

    #resolveAttributes = dqr.resolve(r)
#    resolveAttributes.get(0).get('department').should == short_name
#    resolveAttributes.get(0).get('departmentId').should == id

  end

end