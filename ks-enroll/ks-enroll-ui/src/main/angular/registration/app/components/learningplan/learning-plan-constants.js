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
        notConfigured: 'kuali.cr.learningplan.message.learningplan.not.configured',
        courseNotActive: 'kuali.cr.learningplan.message.learningplan.course.not.active',
        courseNotOffered: 'kuali.cr.learningplan.message.learningplan.course.not.offered',
        regGroupNotOffered: 'kuali.cr.learningplan.message.learningplan.reggroup.not.offered'
    });