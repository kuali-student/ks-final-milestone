'use strict';

angular.module('kscrPocApp')
    .value('configApp', {
        appUrl:'http://localhost:8081/ks-with-rice-embedded-dev/',
        apiBase:'http://localhost:8081/ks-with-rice-embedded-dev/services/',
        apiScheduleOfClassesBase:'ScheduleOfClassesService/',
        apiCourseRegistrationBase:'CourseRegistrationClientService/',
        apiCourseRegistrationCartBase:'CourseRegistrationCartClientService/',
        userId:'admin',
        termCode:'201201',
        query:'CHEM237'
    });
