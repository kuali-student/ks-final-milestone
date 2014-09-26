'use strict';

/**
 * Learning Plan Directive
 *
 * This directive is intended to be used as the controller for the learning plan directive.
 *
 * Usage:
 *  <learning-plan></learning-plan>
 *  <div learning-plan></div>
 *  <div class="learning-plan"></div>
 *
 * Events:
 * - broadcast: none
 * - emits: none
 * - catches: none
 */
angular.module('regCartApp')
    .directive('learningPlan', function() {
        return {
            restrict: 'CAE', // Class, Attribute, Element
            scope: {

            },
            controller: 'LearningPlanCtrl',
            templateUrl: 'components/learningplan/learningPlan.html'
        };
    });