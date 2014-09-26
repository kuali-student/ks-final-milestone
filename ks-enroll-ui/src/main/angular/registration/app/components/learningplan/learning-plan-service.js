'use strict';

angular.module('regCartApp')
    .service('LearningPlanService', ['$q', 'URLS', 'ServiceUtilities', function LearningPlanService($q, URLS, ServiceUtilities) {

        // Cache of learning plans per term
        var learningPlans = {};

        this.getLearningPlan = function(termId) {
            var deferred = $q.defer();

            if (angular.isDefined(learningPlans[termId])) {
                // Return the cached learning plan
                deferred.resolve(learningPlans[termId]);
            } else {
                this.getLearningPlanFromServer(termId)
                    .then(function(plan) {
                        // Cache the plan
                        learningPlans[termId] = plan;
                        deferred.resolve(plan);
                    }, function(error) {
                        // Report out the error
                        deferred.reject(error.data);
                    });
            }

            return deferred.promise;
        };


        // Server API Methods

        this.getLearningPlanFromServer = function getLearningPlanFromServer(termId) {
            console.log(termId);
            return ServiceUtilities.getArray(URLS.courseRegistration + '/learningPlan').query({
                termId: termId
            }).$promise;
        };

    }]);