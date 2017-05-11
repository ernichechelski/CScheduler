'use strict';

describe('Controller Tests', function() {

    describe('Event Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvent, MockContributor, MockPlan;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvent = jasmine.createSpy('MockEvent');
            MockContributor = jasmine.createSpy('MockContributor');
            MockPlan = jasmine.createSpy('MockPlan');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Event': MockEvent,
                'Contributor': MockContributor,
                'Plan': MockPlan
            };
            createController = function() {
                $injector.get('$controller')("EventDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cSchedulerApp:eventUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
