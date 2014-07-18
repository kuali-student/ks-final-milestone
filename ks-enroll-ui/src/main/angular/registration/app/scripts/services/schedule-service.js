'use strict';

angular.module('regCartApp')
    .service('ScheduleService', ['$q', 'ServiceUtilities', 'URLS', function ScheduleService($q, ServiceUtilities, URLS) {

        // Cache of schedules per term
        var schedules = {};

        this.getSchedule = function(termId, forceLoad) {
            var deferred = $q.defer();

            if (!forceLoad) {
                // Make sure forceLoad is set
                forceLoad = false;
            }

            if (angular.isDefined(schedules[termId]) && !forceLoad) {
                // Return the cached cart
                deferred.resolve(schedules[termId]);
            } else {
                this.getScheduleFromServer().query({termId: termId}, function(schedule) {
                    // Cache the schedule
                    schedules[termId] = schedule;
                    deferred.resolve(schedule);
                }, function(error) {
                    // Report out the error
                    deferred.reject(error);
                });
            }

            return deferred.promise;
        };


        // Server API Methods

        this.getScheduleFromServer = function () {
            return ServiceUtilities.getData(URLS.courseRegistration + '/personschedule');
        };

        this.updateScheduleItem = function () {
            return ServiceUtilities.putData(URLS.courseRegistration + '/updateScheduleItem');
        };

        this.updateWaitlistItem = function () {
            return ServiceUtilities.putData(URLS.courseRegistration + '/updateWaitlistEntry');
        };

        this.dropRegistrationGroup = function () {
            return ServiceUtilities.deleteData(URLS.courseRegistration + '/dropRegistrationGroup');
        };

        this.dropFromWaitlist = function () {
            return ServiceUtilities.deleteData(URLS.courseRegistration + '/dropFromWaitlistEntry');
        };

        this.registerForRegistrationGroup = function () {
            return ServiceUtilities.getData(URLS.courseRegistration + '/registerreggroup');
        };

        this.getRegistrationStatus = function () {
            return ServiceUtilities.getData(URLS.courseRegistration + '/getRegistrationStatus');
        };

    }]);