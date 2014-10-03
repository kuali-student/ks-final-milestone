'use strict';

describe('Filter: meetingTimes', function () {

  // load the filter's module
  beforeEach(module('kscrPocApp'));

  // initialize a new instance of the filter before each test
  var meetingTimes;
  beforeEach(inject(function ($filter) {
    meetingTimes = $filter('meetingTimes');
  }));

  it('should return the input prefixed with "meetingTimes filter:"', function () {
    var text = 'angularjs';
    expect(meetingTimes(text)).toBe('meetingTimes filter: ' + text);
  });

});
