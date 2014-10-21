'use strict';

angular.module('mockData').value('transactionMessages',
    [
        {
            "messageKey": "kuali.cr.cart.message.course.code.and.section.required",
            "message": "Course code and section required"
        },
        {
            "messageKey": "kuali.cr.cart.message.section.required",
            "message": "Section required"
        },
        {
            "messageKey": "kuali.cr.cart.message.course.code.required",
            "message": "Course code required"
        },
        {
            "messageKey": "kuali.lpr.trans.message.credit.load.exceeded",
            "message": "Exceeded maximum credit limit <strong ng-if='maxCredits'>({{maxCredits}} credits)</strong>"
        },
        {
            "messageKey": "kuali.lpr.trans.message.credit.load.reached",
            "message": "Reached maximum credit limit <span ng-if='maxCredits'>(<strong>{{maxCredits}} credits</strong>)</span>"
        },
        {
            "messageKey": "kuali.lpr.trans.message.time.conflict",
            "message": "Time conflict"
        },
        {
            "messageKey": "kuali.lpr.trans.message.reggroup.notoffered",
            "message": "<span ng-if='state === states.lui.canceled'>{{courseCode}} ({{regGroupCode}}) is cancelled for {{termName}}</span>\
                        <span ng-if='state === states.lui.invalid'>{{courseCode}} ({{regGroupCode}}) does not exist for {{termName}}</span>\
                        <span ng-if='!state || state === states.lui.pending'>{{courseCode}} ({{regGroupCode}}) is not offered for {{termName}}</span>\
                        <span ng-if='state === states.lui.suspended'>{{courseCode}} ({{regGroupCode}}) is suspended for {{termName}}</span>"
        },
        {
            "messageKey": "kuali.lpr.trans.message.waitlist.available",
            "message": "No seats available."
        },
        {
            "messageKey": "kuali.lpr.trans.message.waitlist.full",
            "message": "No seats available.<br/>(Waitlist full)"
        },
        {
            "messageKey": "kuali.lpr.trans.message.waitlist.not.offered",
            "message": "No seats available.<br/>(Waitlist not offered)"
        },
        {
            "messageKey": "kuali.lpr.trans.message.exception",
            "message": "There was an internal server error while processing your registration request."
        },
        {
            "messageKey": "kuali.lpr.trans.message.course.not.open",
            "message": "<span ng-if='details.appointmentSlot'>Registration Appointment is {{details.appointmentSlot}}</span>\
                        <span ng-if='details.noAppointment'>No Registration Appointment Scheduled</span>\
                        <span ng-if='details.startDate'>First day of Registration is not until {{details.startDate}}</span>\
                        <span ng-if='details.endDate'>Last day of Registration was {{details.endDate}}</span>"
        },
        {
            "messageKey": "kuali.lpr.trans.message.drop.period.closed",
            "message": "Last day to drop was {{endDate}}"
        },
        {
            "messageKey": "kuali.lpr.trans.message.edit.period.closed",
            "message": "Last day to modify was {{endDate}}"
        },
        {
            "messageKey": "kuali.lpr.trans.message.course.grade.incomplete",
            "message": "{{cluCode}} has already been taken. Cannot repeat a course with a mark of 'I'."
        },
        {
            "messageKey": "kuali.lpr.trans.message.course.already.registered",
            "message": "You are already registered for this course."
        },
        {
            "messageKey": "kuali.lpr.trans.message.course.already.taken",
            "message": "{{cluCode}} has already been taken {{attempts | multiplicativeAdverb : ' times'}}. This course cannot be repeated more than {{maxRepeats - 1 | multiplicativeAdverb : ' times'}}."
        },
        {
            "messageKey": "kuali.lpr.trans.message.repeatability.warning",
            "message": "This will be your {{attempts + 1 | ordinal}} attempt of {{cluCode}}. This course cannot be attempted more than {{maxRepeats | multiplicativeAdverb : ' times'}}."
        },
        {
            "messageKey": "kuali.lpr.trans.item.message.exception",
            "message": "There was an internal server error while processing this item."
        }
    ]
);