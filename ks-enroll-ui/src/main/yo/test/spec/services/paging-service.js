'use strict';

describe('Service: pagingService', function () {

  // load the service's module
  beforeEach(module('kscrPocApp'));

  // instantiate service
  var pagingService;
  beforeEach(inject(function (_pagingService_) {
    pagingService = _pagingService_;
  }));

  it('should do something', function () {
    expect(!!pagingService).toBe(true);
  });

});
