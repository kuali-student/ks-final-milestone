'use strict';

angular.module('mockTransactionMessages', []).value('transactionMessages',
    [
        {
            "messageKey":"kuali.lpr.trans.message.credit.load.exceeded",
            "message":"Reached maximum credit limit"
        },
        {
            "messageKey":"kuali.lpr.trans.message.time.conflict",
            "message":"Time conflict"
        },
        {
            "messageKey":"kuali.lpr.trans.message.waitlist.available",
            "message":"No seats available."
        },
        {
            "messageKey":"kuali.lpr.trans.message.waitlist.full",
            "message":"No seats available.<br/>(Waitlist full)"
        },
        {
            "messageKey":"kuali.lpr.trans.message.waitlist.not.offered",
            "message":"No seats available.<br/>(Waitlist not offered)"
        },
        {
            "messageKey":"kuali.lpr.trans.message.course.not.open",
            "message":"Registration is not currently open"
        },
        {
            "messageKey":"kuali.lpr.trans.message.exception",
            "message":"There was an internal server error while processing your registration request."
        },
        {
            "messageKey":"kuali.lpr.trans.message.course.not.open",
            "message":"Registration is not currently open"
        },
        {
            "messageKey":"kuali.lpr.trans.message.course.grade.incomplete",
            "message":"{{courseCode}} has already been taken. Cannot repeat a course with a mark of 'I'."
        },
        {
            "messageKey": "kuali.lpr.trans.message.course.already.taken",
            "message": "{{courseCode}} has already been taken {{attempts}}. This course cannot be repeated more than {{maxRepeats}}."
        },
        {
            "messageKey": "kuali.lpr.trans.message.repeatability.warning",
            "message": "This will be your {{attempts}} attempt of {{courseCode}}. This course cannot be attempted more than {{maxRepeats}}."
        },
        {
            "messageKey":"kuali.lpr.trans.item.message.exception",
            "message":"There was an internal server error while processing this item."
        }
    ]
);