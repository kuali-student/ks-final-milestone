'use strict';

angular.module('kscrPocApp')
  .value('configApp', {
    apiBase: 'http://localhost:8081/ks-with-rice-embedded-dev/services/',
    apiScheduleOfClassesBase: 'ScheduleOfClassesService/',
    apiCourseRegistrationBase: 'CourseRegistrationClientService/',
    userId: 'admin',
    termCode: '201201',
    query: 'CHEM237'
  });
