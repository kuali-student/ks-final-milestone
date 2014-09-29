'use strict';

angular.module('configuration', [])

    .value('APP_URL', 'ks-with-rice-bundled-dev/services/')
    .value('DEFAULT_TERM', 'kuali.atp.2012Fall') // Default term to select. This should be changed/removed when implemented

    // Toggles for disabling/enabling certain features
    .value('FEATURE_TOGGLES', {
        learningPlan: true // Learning plan integration. False prevents it from ever showing
    })
;
