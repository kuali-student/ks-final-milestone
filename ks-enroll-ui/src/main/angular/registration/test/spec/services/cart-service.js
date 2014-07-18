'use strict';

describe('Service: CartService', function () {

    // load the service's module
    beforeEach(module('regCartApp'));

    var CartService,
        $httpBackend;

    // instantiate service
    beforeEach(inject(function(_$httpBackend_, _CartService_) {
        $httpBackend = _$httpBackend_;
        CartService = _CartService_;
    }));


    it('should get the cart', inject(function (CartService, APP_URL) {
       $httpBackend.expectGET(APP_URL + 'CourseRegistrationCartClientService/searchForCart?termId=kuali.atp.2012Fall')
           .respond(200, {
               cartId: "a399078d-31e4-4a92-9319-c5cd02f48ca2",
               termId: "kuali.atp.2012Fall",
               items: [{}, {}],
               state: null
           });

        CartService.getCart("kuali.atp.2012Fall").then(function(response) {
            expect(response.cartId).toBe('a399078d-31e4-4a92-9319-c5cd02f48ca2');
            expect(response.termId).toBe('kuali.atp.2012Fall');
            expect(response.items.length).toBe(2);
            expect(response.state).toBe(null);
        });
        $httpBackend.flush();
    }));

    describe('CartService.addCourseToCart', function() {
        it('should add a course to the cart', inject(function (CartService, APP_URL) {
            var courseCode = 'CHEM232',
                regGroupCode = '1001';

            $httpBackend.expectPOST(APP_URL + 'CourseRegistrationCartClientService/addCourseToCart')
                .respond(200, {
                    cartItemId:"81fd8bbd-7382-42fb-a3cb-52feed4e5fbc",
                    courseCode:"CHEM232",
                    regGroupId:"58353152-6f25-44d9-8ec0-802956bebe0f",
                    regGroupCode:"1001",
                    courseTitle:"Organic Chemistry Laboratory I",
                    credits:"1.0",
                    grading:"kuali.resultComponent.grade.letter",
                    state:"kuali.lpr.trans.item.state.new",
                    cartId:"a399078d-31e4-4a92-9319-c5cd02f48ca2"
                });

            var response = CartService.addCourseToCart().query({
                courseCode: courseCode,
                regGroupCode: regGroupCode
            });
            $httpBackend.flush();

            expect(response.cartId).toBe('a399078d-31e4-4a92-9319-c5cd02f48ca2');
            expect(response.cartItemId).toBe('81fd8bbd-7382-42fb-a3cb-52feed4e5fbc');
            expect(response.courseCode).toBe(courseCode);
            expect(response.regGroupCode).toBe(regGroupCode);
            expect(response.courseTitle).toBe('Organic Chemistry Laboratory I');
        }));
    });
});
