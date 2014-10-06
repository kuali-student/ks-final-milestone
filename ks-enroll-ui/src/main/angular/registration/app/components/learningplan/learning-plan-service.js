'use strict';

// Learning Plan REST Resource Factory
angular.module('regCartApp').factory('LearningPlan', ['$resource', 'APP_URL', 'URLS', function($resource, APP_URL, URLS) {
    return $resource(APP_URL + URLS.courseRegistration + '/learningPlan');
}]);

angular.module('regCartApp')
    .service('LearningPlanService', ['$q', 'LearningPlan', function LearningPlanService($q, LearningPlan) {

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
            return LearningPlan.query({
                termId: termId
            }).$promise;
        };

    }]);