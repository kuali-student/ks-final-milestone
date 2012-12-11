/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.InquiryForm;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
//import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.enrollment.class1.krms.dto.StudentAgendaEditor;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class that returns all agenda types that are valid for a given context.
 */
public class PropositionTypeValuesFinder extends UifKeyValuesFinderBase {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        Object dataObject;
        if (model instanceof InquiryForm) {
            InquiryForm inquiryForm = (InquiryForm) model;
            dataObject = inquiryForm.getDataObject();

        } else {
            MaintenanceForm maintenanceForm = (MaintenanceForm) model;
            dataObject = maintenanceForm.getDocument().getNewMaintainableObject().getDataObject();
        }

        String ruleTypeId;
        if (dataObject instanceof StudentAgendaEditor){
            ruleTypeId = ((StudentAgendaEditor)dataObject).getAgendaItemLine().getRule().getTypeId();
        }



       // if we have an agenda w/ a selected context
        //Collection<TypeTypeRelation> typeRelations = getTypeTypeRelationBoService().findTypeTypeRelationsByFromType(ruleTypeId);
        //for (TypeTypeRelation typeRelation : typeRelations) {
        //    keyValues.add(new ConcreteKeyValue(typeRelation.getToTypeId(), getKrmsTypeRepositoryService().getTypeById(typeRelation.getToTypeId()).getName()));
        //}

        return this.getMockKeyValues();
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

    //public TypeTypeRelationBoService getTypeTypeRelationBoService() {
    //    return KrmsRepositoryServiceLocator.getTypeTypeRelationBoService();
    //}


    private List<KeyValue> getMockKeyValues(){
        List<KeyValue> labels = new ArrayList<KeyValue>();
        labels.add(new ConcreteKeyValue("0", "kuali.krms.proposition.type.cant.add.to.activity.offering.due.to.state"));
        labels.add(new ConcreteKeyValue("1", "kuali.krms.proposition.type.no.repeat.course"));
        labels.add(new ConcreteKeyValue("2", "kuali.krms.proposition.type.no.repeat.courses"));
        labels.add(new ConcreteKeyValue("3", "kuali.krms.proposition.type.permission.instructor.required"));
        labels.add(new ConcreteKeyValue("4", "kuali.krms.proposition.type.freeform.text"));
        labels.add(new ConcreteKeyValue("5", "kuali.krms.proposition.type.course.offering.drop.instructor.permission.required"));
        labels.add(new ConcreteKeyValue("6", "kuali.krms.proposition.type.course.drop.org.permission.required"));
        labels.add(new ConcreteKeyValue("7", "kuali.krms.proposition.type.drop.min.credit.hours.due.to.attribute"));
        labels.add(new ConcreteKeyValue("8", "kuali.krms.proposition.type.drop.min.credit.hours"));
        labels.add(new ConcreteKeyValue("9", "kuali.krms.proposition.type.exceeds.minutes.overlap.allowed"));
        labels.add(new ConcreteKeyValue("10", "kuali.krms.proposition.type.time.conflict.start.end"));
        labels.add(new ConcreteKeyValue("11", "kuali.krms.proposition.type.max.limit.courses.for.program"));
        labels.add(new ConcreteKeyValue("12", "kuali.krms.proposition.type.max.limit.credits.for.program"));
        labels.add(new ConcreteKeyValue("13", "kuali.krms.proposition.type.max.limit.courses.at.org.for.program"));
        labels.add(new ConcreteKeyValue("14", "kuali.krms.proposition.type.max.limit.courses.at.org.duration.for.program"));
        labels.add(new ConcreteKeyValue("15", "kuali.krms.proposition.type.avail.seat"));
        labels.add(new ConcreteKeyValue("16", "kuali.krms.proposition.type.success.compl.course"));
        labels.add(new ConcreteKeyValue("17", "kuali.krms.proposition.type.success.compl.course.as.of.term"));
        labels.add(new ConcreteKeyValue("18", "kuali.krms.proposition.type.success.compl.prior.to.term"));
        labels.add(new ConcreteKeyValue("19", "kuali.krms.proposition.type.success.compl.course.between.terms"));
        labels.add(new ConcreteKeyValue("20", "kuali.krms.proposition.type.success.compl.course.offer"));
        labels.add(new ConcreteKeyValue("21", "kuali.krms.proposition.type.success.course.courseset.completed.nof"));
        labels.add(new ConcreteKeyValue("22", "kuali.krms.proposition.type.success.course.courseset.completed.all"));
        labels.add(new ConcreteKeyValue("23", "kuali.krms.proposition.type.success.credit.courseset.completed.nof"));
        labels.add(new ConcreteKeyValue("24", "kuali.krms.proposition.type.success.credit.courseset.completed.nof.org"));
        labels.add(new ConcreteKeyValue("25", "kuali.krms.proposition.type.cumulative.gpa.min"));
        labels.add(new ConcreteKeyValue("26", "kuali.krms.proposition.type.duration.cumulative.gpa.min"));
        labels.add(new ConcreteKeyValue("27", "kuali.krms.proposition.type.course.courseset.gpa.min"));
        labels.add(new ConcreteKeyValue("28", "kuali.krms.proposition.type.course.courseset.grade.min"));
        labels.add(new ConcreteKeyValue("29", "kuali.krms.proposition.type.course.courseset.nof.grade.min"));
        labels.add(new ConcreteKeyValue("30", "kuali.krms.proposition.type.course.courseset.grade.max"));
        labels.add(new ConcreteKeyValue("31", "kuali.krms.proposition.type.course.test.score.min"));
        labels.add(new ConcreteKeyValue("32", "kuali.krms.proposition.type.course.test.score.max"));
        labels.add(new ConcreteKeyValue("33", "kuali.krms.proposition.type.course.notcompleted"));
        labels.add(new ConcreteKeyValue("34", "kuali.krms.proposition.type.course.courseset.completed.none"));
        labels.add(new ConcreteKeyValue("35", "kuali.krms.proposition.type.course.courseset.credits.completed.none"));
        labels.add(new ConcreteKeyValue("36", "kuali.krms.proposition.type.course.courseset.credits.completed.nof"));
        labels.add(new ConcreteKeyValue("37", "kuali.krms.proposition.type.course.courseset.completed.nof"));
        labels.add(new ConcreteKeyValue("38", "kuali.krms.proposition.type.course.enrolled"));
        labels.add(new ConcreteKeyValue("39", "kuali.krms.proposition.type.course.courseset.enrolled.nof"));
        labels.add(new ConcreteKeyValue("40", "kuali.krms.proposition.type.course.courseset.enrolled.all"));
        labels.add(new ConcreteKeyValue("41", "kuali.krms.proposition.type.credits.earned.min"));
        labels.add(new ConcreteKeyValue("42", "kuali.krms.proposition.type.course.notccompleted"));
        labels.add(new ConcreteKeyValue("43", "kuali.krms.proposition.type.course.credits.repeat.max"));
        labels.add(new ConcreteKeyValue("44", "kuali.krms.proposition.type.max.limit.courses.for.campus.duration"));
        labels.add(new ConcreteKeyValue("45", "kuali.krms.proposition.type.max.limit.credits.for.campus.duration"));
        labels.add(new ConcreteKeyValue("46", "kuali.krms.proposition.type.admitted.to.program"));
        labels.add(new ConcreteKeyValue("47", "kuali.krms.proposition.type.admitted.to.program.campus"));
        labels.add(new ConcreteKeyValue("48", "kuali.krms.proposition.type.notadmitted.to.program"));
        labels.add(new ConcreteKeyValue("49", "kuali.krms.proposition.type.notadmitted.to.program.in.class.standing"));
        labels.add(new ConcreteKeyValue("50", "kuali.krms.proposition.type.admitted.to.program.org"));
        labels.add(new ConcreteKeyValue("51", "kuali.krms.proposition.type.in.class.standing"));
        labels.add(new ConcreteKeyValue("52", "kuali.krms.proposition.type.greater.than.class.standing"));
        labels.add(new ConcreteKeyValue("53", "kuali.krms.proposition.type.less.than.class.standing"));
        labels.add(new ConcreteKeyValue("54", "kuali.krms.proposition.type.notin.class.standing"));
        labels.add(new ConcreteKeyValue("55", "kuali.krms.proposition.type.course.offering.enrolled"));
        labels.add(new ConcreteKeyValue("56", "kuali.krms.proposition.type.course.courseset.enrolled"));
        labels.add(new ConcreteKeyValue("57", "kuali.krms.proposition.type.no.repeat.course.nof"));
        labels.add(new ConcreteKeyValue("58", "kuali.krms.proposition.type.test.score.between.values"));
        labels.add(new ConcreteKeyValue("59", "kuali.krms.proposition.type.test.score"));

        return labels;
    }

}
