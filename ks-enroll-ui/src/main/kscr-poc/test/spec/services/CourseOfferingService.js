'use strict';

describe('Service: CourseOfferingService', function () {

  // load the service's module
  beforeEach(module('kscrPocApp'));

  // instantiate service
  var CourseOfferingService;
  beforeEach(inject(function (_CourseOfferingService_) {
    CourseOfferingService = _CourseOfferingService_;
  }));

  it('should do something', function () {
    expect(!!CourseOfferingService).toBe(true);
  });

});
