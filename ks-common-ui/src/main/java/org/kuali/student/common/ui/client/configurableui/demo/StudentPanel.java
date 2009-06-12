package org.kuali.student.common.ui.client.configurableui.demo;

import org.kuali.student.common.ui.client.configurableui.PropertyBinder;
import org.kuali.student.common.ui.client.configurableui.PropertyField;
import org.kuali.student.common.ui.client.configurableui.Section;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.dto.Idable;

public class StudentPanel extends Section{

    
    public StudentPanel(Student student){
        FirstNameBinder studentFirstNameBinder = new FirstNameBinder(student);
        
        PropertyField studentFirstNameField = new PropertyField();
        studentFirstNameField.setBinding(studentFirstNameBinder);
        
        this.addField(studentFirstNameField);
    }
    
    class FirstNameBinder implements PropertyBinder<Idable,String>{
        
        private Student student;
        
        public FirstNameBinder(Student student){
            this.student = student;
        }
        
        @Override
        public Object getValue(Idable object) {
            return student;
        }

        @Override
        public void setValue(Idable object, String value) {
            student.setFirstName(value);
        }
    } 
    
    
    
    @Override
    public Section addField(PropertyField field) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public void populate(Idable obj) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        
    }

    @Override
    public void updateObject() {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        
    }

    @Override
    public void validate(Callback callback) {
        // TODO joeyin - THIS METHOD NEEDS JAVADOCS
        
    }

}
