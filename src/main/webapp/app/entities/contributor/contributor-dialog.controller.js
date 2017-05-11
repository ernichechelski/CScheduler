(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .controller('ContributorDialogController', ContributorDialogController);

    ContributorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contributor', 'User', 'Event'];

    function ContributorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Contributor, User, Event) {
        var vm = this;

        vm.contributor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.events = Event.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contributor.id !== null) {
                Contributor.update(vm.contributor, onSaveSuccess, onSaveError);
            } else {
                Contributor.save(vm.contributor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cSchedulerApp:contributorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
