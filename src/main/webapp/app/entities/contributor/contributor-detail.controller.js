(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .controller('ContributorDetailController', ContributorDetailController);

    ContributorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Contributor', 'User', 'Event'];

    function ContributorDetailController($scope, $rootScope, $stateParams, previousState, entity, Contributor, User, Event) {
        var vm = this;

        vm.contributor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cSchedulerApp:contributorUpdate', function(event, result) {
            vm.contributor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
