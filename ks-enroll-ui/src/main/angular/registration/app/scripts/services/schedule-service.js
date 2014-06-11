'use strict';

angular.module('regCartApp')
    .service('ScheduleService', ['ServiceUtilities', 'URLS', function ScheduleService(ServiceUtilities, URLS) {

        this.getScheduleFromServer = function () {
            return ServiceUtilities.getData(URLS.courseRegistration+'/personschedule');
        };

        this.updateScheduleItem = function () {
            return ServiceUtilities.putData(URLS.courseRegistration+'/updateScheduleItem');
        };

        this.updateWaitlistItem = function () {
            return ServiceUtilities.putData(URLS.courseRegistration+'/updateWaitlistEntry');
        };

        this.dropRegistrationGroup = function () {
            return ServiceUtilities.deleteData(URLS.courseRegistration+'/dropRegistrationGroup');
        };

        this.dropFromWaitlist = function () {
            return ServiceUtilities.deleteData(URLS.courseRegistration+'/dropFromWaitlistEntry');
        };

        this.registerForRegistrationGroup = function () {
            return ServiceUtilities.getData(URLS.courseRegistration+'/registerreggroup');
        };

        this.getRegistrationStatus = function () {
            return ServiceUtilities.getData(URLS.courseRegistration+'/getRegistrationStatus');
        };

    }]);