'use strict';

describe('Filter: timeRange', function () {

  // load the filter's module
  beforeEach(module('kscrPocApp'));

  // initialize a new instance of the filter before each test
  var timeRange;
  beforeEach(inject(function ($filter) {
    timeRange = $filter('timeRange');
  }));

  it('should return the input prefixed with "timeRange filter:"', function () {
    var text = 'angularjs';
    expect(timeRange(text)).toBe('timeRange filter: ' + text);
  });

});
