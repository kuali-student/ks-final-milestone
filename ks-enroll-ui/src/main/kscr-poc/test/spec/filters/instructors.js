'use strict';

describe('Filter: instructors', function () {

  // load the filter's module
  beforeEach(module('kscrPocApp'));

  // initialize a new instance of the filter before each test
  var instructors;
  beforeEach(inject(function ($filter) {
    instructors = $filter('instructors');
  }));

  it('should return the input prefixed with "instructors filter:"', function () {
    var text = 'angularjs';
    expect(instructors(text)).toBe('instructors filter: ' + text);
  });

});
