'use strict';

describe('Service: termsService', function () {

  // load the service's module
  beforeEach(module('kscrPocApp'));

  // instantiate service
  var termsService;
  beforeEach(inject(function (_termsService_) {
    termsService = _termsService_;
  }));

  it('should do something', function () {
    expect(!!termsService).toBe(true);
  });

});
