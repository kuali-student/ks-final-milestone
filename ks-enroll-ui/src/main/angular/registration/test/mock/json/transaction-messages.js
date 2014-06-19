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
            "messageKey":"kuali.lpr.trans.message.exception",
            "message":"There was an internal server error while processing your registration request."
        },
        {
            "messageKey":"kuali.lpr.trans.item.message.exception",
            "message":"There was an internal server error while processing this item."
        }
    ]
);