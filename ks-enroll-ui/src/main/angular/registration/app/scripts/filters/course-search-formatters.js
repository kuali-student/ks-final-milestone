'use strict';

/**
 * Contains a variety of filters for use as data formatters in course search
 */
angular.module('regCartApp')
    .filter('creditRangeFormatter', function() {

        /**
         * Format an array of credit options into a range using the min & max values.
         *
         * If only 1 option exists in the array, it is returned (e.g. '1 cr').
         * Otherwise the value returned is a represented as a range (e.g. '1-3 cr').
         *
         * @param array of credit options
         * @return string
         */
        return function(creditOptions) {
            var range = '';

            if (creditOptions && angular.isArray(creditOptions)) {
                if (creditOptions.length === 1) {
                    range = parseFloat(creditOptions[0]);
                } else {
                    var min = parseFloat(Math.min.apply(null, creditOptions)),
                        max = parseFloat(Math.max.apply(null, creditOptions));

                    range = min + '-' + max;
                }

                range += ' cr';
            }

            return range;
        };

    })

    .filter('aoFormatter', function() {
        /**
         * Format an array of activity offerings for display using the search-list directive
         *
         * @param array of schedule components
         * @return string
         */
        return function(activityOfferings) {

            var rows=[];

            angular.forEach(activityOfferings, function(ao) {
                var scheduleComponents = ao.scheduleComponents;
                var instructors = ao.instructors;

                var days = '';                                          // days column
                var time = '';                                          // time column
                var location = '';                                      // location column
                var instructorList = '';                                // instructors column
                var seatsOpen =''; // seats open column

                if (scheduleComponents && angular.isArray(scheduleComponents)) {
                    for (var i = 0; i < scheduleComponents.length; i++) {
                        days += denullify(scheduleComponents[i].days);
                        time += denullify(scheduleComponents[i].displayTime);
                        location += denullify(scheduleComponents[i].buildingCode) + " " + denullify(scheduleComponents[i].roomCode);
                        if (i < (scheduleComponents.length - 1)) {
                            days += '<br />';
                            time += '<br />';
                            location += '<br />';
                        }
                    }
                }

                if (instructors && angular.isArray(instructors)) {
                    for (var j = 0; j < instructors.length; j++) {
                        instructorList += denullify(instructors[j].firstName) + ' '+denullify(instructors[j].lastName);
                        if (j < (instructors.length - 1)) {
                            instructorList += '<br />';
                        }
                    }
                }

                seatsOpen += ao.seatsOpen + '/' + ao.seatsAvailable;
                if (ao.seatsOpen === 0) {
                    seatsOpen = '<span class="kscr-Search-results-no-seats">' + seatsOpen + '</span>';
                }
                seatsOpen = '<span class="kscr-Search-result-hidden">'+zeroPad(ao.seatsOpen)+zeroPad(ao.seatsAvailable)+'</span>'+seatsOpen;

                var row={
                    days: days,
                    time: time,
                    instructor: instructorList,
                    location: location,
                    seatsOpen: seatsOpen,
                    aoId: ao.activityOfferingId, // this is used for creating unique row ids
                    ao: ao                       // we may need the ao for further processing
                };

                rows.push(row);
            });

            return rows;
        };

    });

    function denullify(value) {
        if (value === null) {
            return '';
        } else {
            return value;
        }
    }

    function zeroPad(value) {
        console.log('padding: ' + value);
        value = '' + value; // convert to string
        while (value.length < 3) {
            value = '0' + value;
        }
        console.log('result: '+value);
        return value;
    }
