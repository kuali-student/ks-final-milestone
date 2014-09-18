'use strict';

describe('Directive: ValidationMessage', function() {

    // load the module
    beforeEach(module('regCartApp', 'mockTransactionMessages'));

    var $compile,
        scope,
        el,
        messages,
        VALIDATION_ERROR_TYPE,
        baseCourseId = 'BASE_COURSE_ID';

    var mockMessageService = {
        getMessage : function(messageKey) {
            var message = '';
            for (var i=0; i<messages.length; i++) {
                if (messages[i].messageKey === messageKey) {
                    message = messages[i].message;
                    break;
                }
            }
            return message;
        }
    };

    var mockTermsService = {
        getSelectedTerm: function() {
            return {
                termName: 'Fall 2012'
            };
        }
    };

    // provide a mock MessageService
    beforeEach(function() {
        module(function ($provide) {
            $provide.value('MessageService', mockMessageService);
            $provide.value('TermsService', mockTermsService);
        });
    });

    // instantiate the filter
    beforeEach(inject(function(_$compile_, _$rootScope_, _VALIDATION_ERROR_TYPE_, transactionMessages) {
        $compile = _$compile_;
        scope = _$rootScope_.$new();
        VALIDATION_ERROR_TYPE = _VALIDATION_ERROR_TYPE_;
        messages = transactionMessages;
    }));


    function compile(data, course) {
        scope.data = data;
        scope.course = course || null;

        // Compile the template and apply the scope
        el = $compile('<validation-message message="data" course="course"></validation-message>')(scope);
        scope.$digest();

        return el.text().trim();
    }

    function filterWithCourse(errorType, course) {
        if (!course) {
            course = {};
        }

        if (!course.masterLprId) {
            course.masterLprId = baseCourseId;
        }

        return compileWithKey(errorType, course);
    }

    function compileWithKey(errorType, course) {
        return compile({ messageKey: errorType }, course);
    }


    it('should handle garbage input elegantly', function() {
        expect(compile()).toBe('');
        expect(compile(false)).toBe('');
        expect(compile(true)).toBe('');
        expect(compile(true, 'string course???')).toBe('');
        expect(filterWithCourse(true)).toBe('');
        expect(compile(['nonExistentMessageKey'])).toBe('');
        expect(compile({messageKey: 'nonExistentMessageKey'})).toBe('');
    });

    it('should return a string message straight out', function() {
        expect(compile('test message')).toBe('test message');
    });


    describe('parameterized messages', function() {
        it('should format the {{courseCode}} parameter in a message string correctly', function() {
            expect(compile('Course {{courseCode}} Should Be \'code1\'', { courseCode: 'code1' })).toContain('code1');
        });

        it('should a variety of parameters in the message string correctly', function() {
            messages.push({
                messageKey: 'dummyKey',
                message: '{{p1}} {{p2}} {{p3 | date:"yyyy/MM/dd"}} {{p4 | ordinal}} {{2 | multiplicativeAdverb}}'
            });

            var data = { messageKey: 'dummyKey', p1: 'code1', p2: 'abcdefg', p3: '2014-09-08', p4: 3 };

            expect(compile(data)).toBe('code1 abcdefg 2014/09/08 3rd twice');
        });
    });


    describe('course already taken', function() {
        it('should format the message correctly', function() {
            var data = {
                    messageKey: VALIDATION_ERROR_TYPE.courseAlreadyTaken
                },
                course = { courseCode: 'code1' };

            // Base case
            expect(compile(data, course)).toContain('code1 has already been taken');


            // With parameters
            data.attempts = 2;
            data.maxRepeats = 2;
            expect(compile(data, course)).toBe('code1 has already been taken twice. This course cannot be repeated more than once.');

            data.attempts = 3;
            data.maxRepeats = 3;
            expect(compile(data, course)).toBe('code1 has already been taken 3 times. This course cannot be repeated more than twice.');

            // Validate that we are truncating course suffixes
            course = { courseCode: 'CHEM399A'};
            expect(compile(data, course)).toBe('CHEM399 has already been taken 3 times. This course cannot be repeated more than twice.');
        });
    });


    describe('course repeatability warning', function() {
        it('should format the message correctly', function() {
            var data = {
                    messageKey: VALIDATION_ERROR_TYPE.repeatabilityWarning
                },
                course = { courseCode: 'code1' };

            // Base case
            expect(compile(data, course)).toContain('attempt of code1.');


            // With parameters
            data.attempts = 1;
            data.maxRepeats = 2;
            expect(compile(data, course)).toBe('This will be your 2nd attempt of code1. This course cannot be attempted more than twice.');

            data.attempts = 2;
            data.maxRepeats = 3;
            expect(compile(data, course)).toBe('This will be your 3rd attempt of code1. This course cannot be attempted more than 3 times.');

            // Validate that we are truncating course suffixes
            course = { courseCode: 'CHEM399A'};
            expect(compile(data, course)).toBe('This will be your 3rd attempt of CHEM399. This course cannot be attempted more than 3 times.');
        });
    });


    describe('max credits', function() {
        it('should format the message correctly', function() {
            // Base case
            expect(compileWithKey(VALIDATION_ERROR_TYPE.maxCredits)).toContain('Exceeded maximum credit limit');


            // With maxCredits included
            var max = '20.5',
                msg = compile({messageKey: VALIDATION_ERROR_TYPE.maxCredits, maxCredits: max});

            expect(msg).toContain('Exceeded maximum credit limit');
            expect(msg).toContain(max + ' credits');
        });

        it('should handle a null course', function() {
            expect(compileWithKey(VALIDATION_ERROR_TYPE.maxCredits)).toContain('Exceeded maximum credit limit');
        });
    });


    describe('time conflict', function() {
        it('should return the correct message for garbage data', function() {
            expect(compileWithKey(VALIDATION_ERROR_TYPE.timeConflict)).toBe('Time conflict');
            expect(filterWithCourse(VALIDATION_ERROR_TYPE.timeConflict, {})).toBe('Time conflict');
            expect(filterWithCourse(VALIDATION_ERROR_TYPE.timeConflict, {conflictingCourses: []})).toBe('Time conflict');
        });

        it('should handle a courseCode on the root object', function() {
            var data = {
                messageKey: VALIDATION_ERROR_TYPE.timeConflict,
                courseCode: 'code1',
                masterLprId: 'id1'
            };

            // Base case
            expect(compile(data, {})).toBe('Time conflict (code1)');

            // Malformed conflictingCourses array
            data.conflictingCourses = {};
            expect(compile(data, {})).toBe('Time conflict (code1)');

            // Existing but empty conflictingCourses array
            data.conflictingCourses = [];
            expect(compile(data, {})).toBe('Time conflict (code1)');

            // Populated conflicting items array
            data.conflictingCourses.push({courseCode: 'code2'});
            expect(compile(data, {})).toBe('Time conflict (code1, code2)');

            // Duplicate item in conflictingCourses array
            data.conflictingCourses.push({courseCode: 'code1', masterLprId: 'id1'});
            expect(compile(data, {})).toBe('Time conflict (code1, code2)');
        });

        it('should not show a conflicting courseCode that matches the current course', function() {
            var data = {
                    messageKey: VALIDATION_ERROR_TYPE.timeConflict,
                    courseCode: 'code1',
                    masterLprId: baseCourseId
                },
                course = {
                    courseCode: 'code1',
                    masterLprId: baseCourseId
                };

            expect(compile(data, course)).toBe('Time conflict');
        });

        it('should handle an array of conflictingCourses', function() {
            var data = {
                messageKey: VALIDATION_ERROR_TYPE.timeConflict,
                conflictingCourses: [
                    { courseCode: 'code1', masterLprId: 'id1' }
                ]
            };

            // Single item base case
            expect(compile(data, {})).toBe('Time conflict (code1)');

            // Multiple items
            data.conflictingCourses.push({courseCode: 'code2', masterLprId: 'id2'});
            expect(compile(data, {})).toBe('Time conflict (code1, code2)');

            // Duplicate item in conflictingCourses
            data.conflictingCourses.push({courseCode: 'code1', masterLprId: 'id1'});
            expect(compile(data, {})).toBe('Time conflict (code1, code2)');
        });

        it('should not show the course code in a conflictingItem that matches the current course', function() {
            var data = {
                    messageKey: VALIDATION_ERROR_TYPE.timeConflict,
                    conflictingCourses: [
                        { courseCode: 'code1', masterLprId: 'id1' }
                    ]
                },
                course = {
                    courseCode: 'BASE_COURSE_CODE',
                    masterLprId: baseCourseId
                };

            // Single item base case
            expect(compile(data, course)).toBe('Time conflict (code1)');

            // Multiple items
            data.conflictingCourses.push({courseCode: 'BASE_COURSE_CODE', masterLprId: baseCourseId});
            expect(compile(data, course)).toBe('Time conflict (code1)');
        });
    });

    describe('registration not open', function() {
        it('should format the message correctly', function() {
            var data = {
                    messageKey: VALIDATION_ERROR_TYPE.courseNotOpen
                },
                course = { courseCode: 'code1' },
                appointmentSlot = 'March 14, 2012, 3:15 PM',
                startDate = '9/10/2014',
                endDate = '10/10/2014';

            // Too Early for Reg Appt
            data.details = {};
            data.details.appointmentSlot = appointmentSlot;
            expect(compile(data, course)).toBe('Registration Appointment is '+appointmentSlot);

            // No Appointment
            data.details = {};
            data.details.noAppointment = true;
            expect(compile(data, course)).toBe('No Registration Appointment Scheduled');

            // Registration not yet available
            data.details = {};
            data.details.startDate = startDate;
            expect(compile(data, course)).toBe('First day of Registration is not until '+startDate);

            // Registration window has passed
            data.details = {};
            data.details.endDate = endDate;
            expect(compile(data, course)).toBe('Last day of Registration was '+endDate);
        });
    });

    describe('registration group not offered', function() {
        it('should format the message correctly', inject(function(STATE) {
            var data = {
                    messageKey: VALIDATION_ERROR_TYPE.regGroupNotOffered
                },
                course = {
                    courseCode: 'code1',
                    regGroupCode: 'rgCode1'
                };

            // Base case
            data.state = '';
            expect(compile(data, course)).toBe('code1 (rgCode1) is not offered for Fall 2012');

            data.state = STATE.lui.canceled;
            expect(compile(data, course)).toBe('code1 (rgCode1) is cancelled for Fall 2012');

            data.state = STATE.lui.invalid;
            expect(compile(data, course)).toBe('code1 (rgCode1) does not exist for Fall 2012');

            data.state = STATE.lui.pending;
            expect(compile(data, course)).toBe('code1 (rgCode1) is not offered for Fall 2012');

            data.state = STATE.lui.suspended;
            expect(compile(data, course)).toBe('code1 (rgCode1) is suspended for Fall 2012');
        }));
    });

});
