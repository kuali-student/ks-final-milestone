'use strict';

/**
 * Learning Plan Constants
 */
angular.module('regCartApp')
    // Learning Plan Categories
    .constant('LEARNING_PLAN_CATEGORIES', {
        planned: 'PLANNED',
        backup: 'BACKUP'
    })

    // Learning Plan-Specific Errors
    .constant('LEARNING_PLAN_ERRORS', {
        notConfigured: 'kuali.cr.learningplan.message.learningplan.not.configured'
    });