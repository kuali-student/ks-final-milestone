'use strict';

describe('Controller: DirectRegisterCtrl', function () {

    // load the controller's module
    beforeEach(module('regCartApp', 'mockStudentScheduleTermResult'));

    var ctrl,
        scope,
        $rootScope,
        modalInstance,
        course,
        GRADING_OPTION;


    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, _$rootScope_, studentScheduleTermResult, _GRADING_OPTION_) {
        modalInstance = { // Create a mock object using spies
            close: jasmine.createSpy('modalInstance.close'),
            dismiss: jasmine.createSpy('modalInstance.dismiss'),
            result: {
                then: jasmine.createSpy('modalInstance.result.then')
            }
        };

        course = angular.copy(studentScheduleTermResult.waitlistCourseOfferings[0]); // Should be CHEM399A 1001

        scope = _$rootScope_.$new();
        ctrl = $controller('DirectRegisterCtrl', {
            $scope: scope,
            $modalInstance: modalInstance,
            course: course
        });

        $rootScope = _$rootScope_;
        GRADING_OPTION = _GRADING_OPTION_;
    }));


    describe('init', function() {
        it('should init with a course', function () {
            expect(scope.course).toBe(course);
            expect(scope.results).toEqual([]);

            expect(scope.course.credits).toBe('3.0');
            expect(scope.course.gradingOptionId).toBe(GRADING_OPTION.letter);
        });

        it('should auto-select the only credit && grading option in the list', inject(function($controller) {
            course.credits = null;
            course.gradingOptionId = null;

            course.creditOptions = ['3.0'];

            course.gradingOptions = {};
            course.gradingOptions[GRADING_OPTION.letter] = 'Letter';

            scope = $rootScope.$new();
            $controller('DirectRegisterCtrl', {
                $scope: scope,
                $modalInstance: modalInstance,
                course: course
            });

            expect(scope.course.credits).toEqual('3.0');
            expect(scope.course.gradingOptionId).toEqual(GRADING_OPTION.letter);
        }));
    });


    describe('steps', function() {
        // Step 1
        it('should show the options when appropriate', function() {
            scope.course.credits = null;
            scope.course.gradingOptionId = null;

            expect(scope.showOptions()).toBeTruthy();
            expect(scope.showConfirm()).toBeFalsy();
            expect(scope.showProcessing()).toBeFalsy();
            expect(scope.showResults()).toBeFalsy();

            // Step 1 (alt1)
            scope.course.credits = '3.0';
            expect(scope.showOptions()).toBeTruthy();
            expect(scope.showConfirm()).toBeFalsy();
            expect(scope.showProcessing()).toBeFalsy();
            expect(scope.showResults()).toBeFalsy();

            // Step 1 (alt2)
            scope.course.credits = null;
            scope.course.gradingOptionId = GRADING_OPTION.letter;
            expect(scope.showOptions()).toBeTruthy();
            expect(scope.showConfirm()).toBeFalsy();
            expect(scope.showProcessing()).toBeFalsy();
            expect(scope.showResults()).toBeFalsy();
        });

        // Step 2
        it('should show the confirm step when appropriate', function() {
            expect(scope.showOptions()).toBeFalsy();
            expect(scope.showConfirm()).toBeTruthy();
            expect(scope.showProcessing()).toBeFalsy();
            expect(scope.showResults()).toBeFalsy();
        });

        // Step 3
        it('should show the processing step when appropriate', function() {
            var promiseMock = jasmine.createSpy('event.promise.then'),
                eventMock = {
                    promise: {
                        then: promiseMock
                    }
                };

            spyOn($rootScope, '$broadcast').andReturn(eventMock);

            scope.confirm();
            expect(scope.showOptions()).toBeFalsy();
            expect(scope.showConfirm()).toBeFalsy();
            expect(scope.showProcessing()).toBeTruthy();
            expect(scope.showResults()).toBeFalsy();
        });

        // Step 4
        it('should show the results step when appropriate', function() {
            var promiseMock = jasmine.createSpy('event.promise.then'),
                eventMock = {
                    promise: {
                        then: promiseMock
                    }
                };

            var eventSpy = spyOn($rootScope, '$broadcast').andReturn(eventMock);

            scope.confirm();
            expect(scope.showOptions()).toBeFalsy();
            expect(scope.showConfirm()).toBeFalsy();
            expect(scope.showProcessing()).toBeTruthy();
            expect(scope.showResults()).toBeFalsy();

            expect(eventSpy).toHaveBeenCalled();
            expect(promiseMock).toHaveBeenCalled();
        });
    });


    it('should know how to save options', function() {
        scope.course.credits = null;
        scope.course.gradingOptionId = null;

        expect(scope.showOptions()).toBeTruthy();

        var newCourse = {
            newCredits: '3.0',
            newGrading: GRADING_OPTION.letter
        };
        scope.saveOptions(newCourse);

        expect(scope.course.credits).toBe('3.0');
        expect(scope.course.gradingOptionId).toBe(GRADING_OPTION.letter);

        expect(scope.showOptions()).toBeFalsy();
        expect(scope.showConfirm()).toBeTruthy();
    });


    describe('mock cart actions', function() {
        it('should mock addCartItemToCart', function() {
            var spy = spyOn($rootScope, '$broadcast');

            scope.addCartItemToCart();
            expect(spy).toHaveBeenCalledWith('addCourseToCart', scope.course);
        });

        it('should mock addCartItemToWaitlist', function() {
            var spy = spyOn(scope, 'confirm');

            scope.addCartItemToWaitlist();
            expect(spy).toHaveBeenCalled();
            expect(scope.results).toEqual([]);
            expect(scope.course.allowWaitlist).toBeTruthy();
        });

        it('should mock removeCartResultItem', function() {
            scope.removeCartResultItem();
            expect(modalInstance.close).toHaveBeenCalled();
        });
    });

    it('should dismiss the modal with "cancel" when process is canceled', function() {
        scope.cancel();
        expect(modalInstance.dismiss).toHaveBeenCalledWith('cancel');
    });

    it('should correctly translate the grading option ID to the label', function() {
        scope.course.gradingOptionId = GRADING_OPTION.letter;
        expect(scope.gradingOption()).toEqual('Letter');

        scope.course.gradingOptionId = GRADING_OPTION.audit;
        expect(scope.gradingOption()).toEqual('Audit');

        scope.course.gradingOptionId = GRADING_OPTION.passFail;
        expect(scope.gradingOption()).toEqual('Pass/Fail');
    });

    it('should know when to show the grading option badge', function() {
        scope.course.gradingOptionId = GRADING_OPTION.letter;
        expect(scope.showBadge()).toBeFalsy();

        scope.course.gradingOptionId = GRADING_OPTION.audit;
        expect(scope.showBadge()).toBeTruthy();

        scope.course.gradingOptionId = GRADING_OPTION.passFail;
        expect(scope.showBadge()).toBeTruthy();
    });


    describe('updating with results', function() {
        var STATE,
            STATUS,
            response;

        beforeEach(inject(function(_STATE_, _STATUS_) {
            STATE = _STATE_;
            STATUS = _STATUS_;
        }));

        function createResponse(state, type, messages) {
            if (!messages) {
                messages = [];
            } else if (!angular.isArray(messages)) {
                messages = [messages];
            }

            return {
                responseItemResults: [{
                    state: state || '',
                    type: type || '',
                    messages: messages
                }]
            };
        }

        function getThere(response) {
            var promiseMock = jasmine.createSpy('event.promise.then'),
                eventMock = {
                    promise: {
                        then: promiseMock
                    }
                };

            promiseMock.andCallFake(function(fn) {
                // Resolve the promise with the mocked response
                fn(response || createResponse());
            });

            spyOn($rootScope, '$broadcast').andReturn(eventMock);

            scope.confirm();
        }

        it('should handle no messages coming back', function() {
            var response = {};
            getThere(response);

            expect(scope.results.length).toBe(0);
            expect(modalInstance.dismiss).toHaveBeenCalledWith(response);
        });

        it('should handle a failure message coming back', function() {
            getThere(createResponse(STATE.lpr.item.failed, '??', { messageKey: 'testKey' }));

            expect(scope.results.length).toBe(1);

            var result = scope.results[0];
            expect(result.state).toBe(STATE.lpr.item.failed);
            expect(result.type).toBe('??');
            expect(result.status).toBe(STATUS.error);
            expect(result.statusMessages).toEqual([{ messageKey: 'testKey' }]);
        });

        it('should handle a simple success message coming back', function() {
            getThere(createResponse(STATE.lpr.item.succeeded, '??', []));

            expect(scope.results.length).toBe(1);

            var result = scope.results[0];
            expect(result.state).toBe(STATE.lpr.item.succeeded);
            expect(result.type).toBe('??');
            expect(result.status).toBe(STATUS.success);
            expect(result.statusMessages).toEqual([]);
        });

        it('should handle a complex success message coming back', function() {
            getThere(createResponse(STATE.lpr.item.succeeded, '??', [{ messageKey: 'testKey' }]));

            expect(scope.results.length).toBe(2);

            var result1 = scope.results[0];
            expect(result1.state).toBe(STATE.lpr.item.succeeded);
            expect(result1.type).toBe('??');
            expect(result1.status).toBe(STATUS.success);
            expect(result1.statusMessages).toEqual([]);

            var result2 = scope.results[1];
            expect(result2.state).toBe(STATE.lpr.item.succeeded);
            expect(result2.type).toBe('??');
            expect(result2.status).toBe(STATUS.info);
            expect(result2.statusMessages).toEqual([{ messageKey: 'testKey' }]);
        });

        it('should handle a waitlist message coming back', function() {
            getThere(createResponse(STATE.lpr.item.waitlist, '??', [{ messageKey: 'testKey' }]));

            expect(scope.results.length).toBe(1);

            var result = scope.results[0];
            expect(result.state).toBe(STATE.lpr.item.waitlist);
            expect(result.type).toBe('??');
            expect(result.status).toBe(STATUS.waitlist);
            expect(result.statusMessages).toEqual([{ messageKey: 'testKey' }]);
            expect(result.waitlistMessage).toEqual('If a seat becomes available you will be registered automatically');
        });
    });
});
