'use strict';

angular.module('kscrPocApp')
  .value('configApp', {
    apiBase: 'http://env2.ks.kuali.org/services/',
    apiScheduleOfClassesBase: 'ScheduleOfClassesService/',
    apiCourseRegistrationBase: 'CourseRegistrationClientService/',
    userId: 'admin',
    termCode: '201201',
    query: 'CHEM237'
  });
