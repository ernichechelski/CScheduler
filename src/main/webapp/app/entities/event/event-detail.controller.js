(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .controller('EventDetailController', EventDetailController);

    EventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Event', 'Contributor', 'Plan'];

    function EventDetailController($scope, $rootScope, $stateParams, previousState, entity, Event, Contributor, Plan) {
        var vm = this;

        vm.event = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cSchedulerApp:eventUpdate', function(event, result) {
            vm.event = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
