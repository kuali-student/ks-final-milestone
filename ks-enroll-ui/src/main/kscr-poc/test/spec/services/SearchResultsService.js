'use strict';

describe('Service: SearchResultsService', function () {

  // load the service's module
  beforeEach(module('kscrPocApp'));

  // instantiate service
  var SearchResultsService;
  beforeEach(inject(function (_SearchResultsService_) {
    SearchResultsService = _SearchResultsService_;
  }));

  it('should do something', function () {
    expect(!!SearchResultsService).toBe(true);
  });

});
