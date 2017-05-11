(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .controller('PlanDetailController', PlanDetailController);

    PlanDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Plan', 'Event', 'User'];

    function PlanDetailController($scope, $rootScope, $stateParams, previousState, entity, Plan, Event, User) {
        var vm = this;

        vm.plan = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cSchedulerApp:planUpdate', function(event, result) {
            vm.plan = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
