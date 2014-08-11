'use strict';

angular.module('regCartApp')
    .service('ScheduleService', ['$q', '$timeout', 'URLS', 'STATUS', 'ServiceUtilities', 'GlobalVarsService', function ScheduleService($q, $timeout, URLS, STATUS, ServiceUtilities, GlobalVarsService) {

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


        // Schedule Poller
        this.pollRegistrationRequestStatus = function(registrationRequestId, interval, deferred) {
            // Make sure the interval is defined
            if (!angular.isNumber(interval)) {
                interval = 1000;
            }

            // Make sure the promise is set up.
            if (!deferred) {
                deferred = $q.defer();
            }

            var me = this; // Get a handle on the service so we can refer to it within the $timeout.
            $timeout(function() {
                // Query for the registration status
                me.getRegistrationStatus().query({regReqId: registrationRequestId}, function (result) {
                    var status = GlobalVarsService.getCorrespondingStatusFromState(result.state);
                    switch (status) {

                        case STATUS.new:
                        case STATUS.processing:
                            // The request is still new or processing, first make sure at least 1 of the items are still processing.
                            var processing = false;
                            angular.forEach(result.responseItemResults, function(item) {
                                if (processing) {
                                    return;
                                }

                                var itemStatus = GlobalVarsService.getCorrespondingStatusFromState(item.state);
                                switch (itemStatus) {
                                    case STATUS.new:
                                    case STATUS.processing:
                                        processing = true; // At least 1 item is still processing.
                                        break;
                                }
                            });

                            deferred.notify(result); // Notify out as well to give any itemized processors full coverage of all items.

                            if (processing) {
                                // The request is still new or processing, reschedule the poller
                                me.pollRegistrationRequestStatus(registrationRequestId, interval, deferred);
                            } else {
                                // A state mismatch exists on the request, all items have finished
                                // successfully even though the request is still marked as new or processing.
                                deferred.resolve(result);
                            }
                            break;

                        case STATUS.success:
                            // The request has finished successfully
                            deferred.notify(result); // Notify out as well to give any itemized processors full coverage of all items.
                            deferred.resolve(result);
                            break;

                        case STATUS.error:
                            // The request has finished with an error state
                            deferred.reject(result);
                            break;

                    }
                }, function(error) {
                    // Return out the error
                    deferred.reject(error);
                });
            }, interval);

            return deferred.promise;
        };


        // Server API Methods

        this.getScheduleFromServer = function () {
            return ServiceUtilities.getData(URLS.courseRegistration + '/studentSchedule');
        };

        this.dropRegistrationGroup = function () {
            return ServiceUtilities.deleteData(URLS.courseRegistration + '/registrationRequest');
        };

        this.registerForRegistrationGroup = function () {
            return ServiceUtilities.postData(URLS.courseRegistration + '/registrationRequest');
        };

        this.updateScheduleItem = function () {
            return ServiceUtilities.putData(URLS.courseRegistration + '/registrationRequest');
        };

        this.dropFromWaitlist = function () {
            return ServiceUtilities.deleteData(URLS.courseRegistration + '/waitlistRegistrationRequest');
        };

        this.updateWaitlistItem = function () {
            return ServiceUtilities.putData(URLS.courseRegistration + '/waitlistRegistrationRequest');
        };

        this.getRegistrationStatus = function () {
            return ServiceUtilities.getData(URLS.courseRegistration + '/registrationStatus');
        };

    }]);