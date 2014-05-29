'use strict';

describe('Directive: CourseOptions', function () {

    // load the service's module
    beforeEach(module('regCartApp'));

    // load the template
    beforeEach(module('partials/courseOptions.html'));


    var $compile,
        scope,
        el;


    beforeEach(inject(function(_$compile_, _$rootScope_) {
        $compile = _$compile_;
        scope = _$rootScope_.$new();
    }));


    function compileDirective(course, tpl) {
        // Ensure the course is properly initialized
        course = course || {};
        course.creditOptions = course.creditOptions || [];
        course.gradingOptions = course.gradingOptions || {};
        course.credits = course.credits || (course.creditOptions ? course.creditOptions[0] : null);
        course.grading = course.grading || (course.gradingOptions ? course.gradingOptions[Object.keys(course.gradingOptions)[0]] : null);
        course.courseCode = course.courseCode || 'COURSECODE';
        course.regGroupCode = course.regGroupCode || 'REGGROUPCODE';
        // console.log(course);

        scope.course = course;

        // Ensure the template is set
        tpl = tpl || '<course-options course="course"></course-options>';


        // Compile the template and apply the scope
        el = $compile(tpl)(scope);
        scope.$digest();
    }


    describe('initialization', function() {
        it('should inject the appropriate html contents', function() {
            compileDirective();
            expect(el.html()).toContain('<div class="kscr-Card-detailsText">');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE');
        });

        it('should transpose the grading options', function() {
            var course = {
                gradingOptions: {'a': 'OptionA', 'b': 'OptionB'}
            };

            compileDirective(course);

            var options = el.isolateScope().gradingOptions;
            expect(options.length).toBe(2);

            expect(options[0].key).toBe('a');
            expect(options[0].label).toBe('OptionA');

            expect(options[1].key).toBe('b');
            expect(options[1].label).toBe('OptionB');
        });
    });

    describe('configuration', function() {
        it('should initialize the parameters', function() {
            compileDirective(null, '<course-options course="course" prefix="TESTPREFIX_" max="3" show-all="true"></course-options>');

            expect(el.isolateScope().prefix).toBe('TESTPREFIX_');
            expect(el.isolateScope().maxOptions).toBe(3);
            expect(el.isolateScope().showAll).toBeTruthy();
            expect(el.isolateScope().showAllCreditOptions).toBeTruthy();
            expect(el.isolateScope().showAllGradingOptions).toBeTruthy();
        });

        it('should create the correct number of credit options', function() {
            var course = {
                creditOptions: ['1', '2', '3']
            };

            compileDirective(course);

            expect(el.find('input').length).toBe(3);
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_1');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_2');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_3');
        });

        it('should create the correct number of grading options', function() {
            var course = {
                gradingOptions: {'a': 'OptionA', 'b': 'OptionB'}
            };

            compileDirective(course);

            expect(el.find('input').length).toBe(2);
            expect(el.html()).toContain('OptionA');
            expect(el.html()).toContain('OptionB');
        });

        it('should correctly handle the prefix option', function() {
            compileDirective(null, '<course-options course="course" prefix="TESTPREFIX_"></course-options>');

            expect(el.html()).toContain('TESTPREFIX_credits_COURSECODE_REGGROUPCODE');
            expect(el.html()).toContain('TESTPREFIX_grading_COURSECODE_REGGROUPCODE');
        });
    });

    describe('visibility of options', function() {
        it('should correctly determine whether or not to show the More credit options toggle', function() {
            var course = {
                creditOptions: ['1', '2', '3', '4', '5', '6']
            };

            compileDirective(course);

            expect(el.isolateScope().shouldShowMoreCreditOptionsToggle()).toBeTruthy();
            expect(el.find('input').length).toBe(4);

            el.isolateScope().showAllCreditOptions = true;
            el.isolateScope().$digest();
            expect(el.isolateScope().shouldShowMoreCreditOptionsToggle()).toBeFalsy();
            expect(el.find('input').length).toBe(6);
        });

        it('should correctly determine whether or not to show the More grading options toggle', function() {
            var course = {
                gradingOptions: {
                    'a': 'OptionA', 'b': 'OptionB', 'c': 'OptionC', 'd': 'OptionD', 'e': 'OptionE', 'f': 'OptionF'
                }
            };

            compileDirective(course);

            expect(el.isolateScope().shouldShowMoreGradingOptionsToggle()).toBeTruthy();
            expect(el.find('input').length).toBe(4);

            el.isolateScope().showAllGradingOptions = true;
            el.isolateScope().$digest();
            expect(el.isolateScope().shouldShowMoreGradingOptionsToggle()).toBeFalsy();
            expect(el.find('input').length).toBe(6);
        });

        it('should show the correct options based on the selected credits option', function() {
            var course = {
                credits: '1',
                creditOptions: ['1', '2', '3', '4', '5', '6']
            };

            compileDirective(course);

            expect(el.html()).toContain('More');

            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_1');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_2');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_3');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_4');

            scope.course.credits = '2';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_1');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_2');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_3');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_4');

            scope.course.credits = '3';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_1');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_2');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_3');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_4');

            scope.course.credits = '4';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_2');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_3');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_4');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_5');

            scope.course.credits = '5';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_3');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_4');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_5');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_6');

            scope.course.credits = '6';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_3');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_4');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_5');
            expect(el.html()).toContain('credits_COURSECODE_REGGROUPCODE_6');
        });

        it('should show the correct options based on the selected grading option', function() {
            var course = {
                grading: 'a',
                gradingOptions: {
                    'a': 'OptionA',
                    'b': 'OptionB',
                    'c': 'OptionC',
                    'd': 'OptionD',
                    'e': 'OptionE',
                    'f': 'OptionF'
                }
            };

            compileDirective(course);

            expect(el.html()).toContain('More');

            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionA');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionB');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionC');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionD');

            scope.course.grading = 'b';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionA');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionB');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionC');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionD');

            scope.course.grading = 'c';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionA');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionB');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionC');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionD');

            scope.course.grading = 'd';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionB');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionC');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionD');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionE');

            scope.course.grading = 'e';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionC');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionD');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionE');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionF');

            scope.course.grading = 'f';
            scope.$digest();
            expect(el.find('input').length).toBe(4);
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionC');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionD');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionE');
            expect(el.html()).toContain('grading_COURSECODE_REGGROUPCODE_OptionF');
        });
    });

    describe('submit action', function() {
        it('should call a specified submit callback', function() {
            var fn = jasmine.createSpy('fn');
            scope.fn = fn;

            compileDirective(null, '<course-options course="course" on-submit="fn(course)"></course-options>');

            el.isolateScope().submit();
            expect(fn).toHaveBeenCalled();
        });

        it('should return the edited course on submit', function() {
            var newCourse = null;
            scope.fn = function(c) {
                newCourse = c;
            };

            compileDirective(null, '<course-options course="course" on-submit="fn(course)"></course-options>');

            scope.course.newCredits = '1';
            scope.course.newGrading = 'a';
            scope.$digest();

            el.isolateScope().submit();

            expect(newCourse).not.toBeNull();
            expect(newCourse.newCredits).toBe('1');
            expect(newCourse.newGrading).toBe('a');
        });
    });

    describe('cancel action', function() {
        it('should call a specific cancel callback', function() {
            var fn = jasmine.createSpy('fn');
            scope.fn = fn;

            compileDirective(null, '<course-options course="course" on-cancel="fn(course)"></course-options>');

            el.isolateScope().cancel();
            expect(fn).toHaveBeenCalled();
        });

        it('should reset the course values on cancel', function() {
            var course = {
                credits: '2',
                grading: 'b',
                newCredits: '1',
                newGrading: 'a',
                status: 'editing',
                editing: true
            };

            compileDirective(course);
            el.isolateScope().cancel();

            expect(course.newCredits).toBe('2');
            expect(course.newGrading).toBe('b');
            expect(course.status).toBe('');
            expect(course.editing).toBeFalsy();
        });
    });
});