(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .controller('PlanDialogController', PlanDialogController);

    PlanDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Plan', 'Event', 'User'];

    function PlanDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Plan, Event, User) {
        var vm = this;

        vm.plan = entity;
        vm.clear = clear;
        vm.save = save;
        vm.events = Event.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.plan.id !== null) {
                Plan.update(vm.plan, onSaveSuccess, onSaveError);
            } else {
                Plan.save(vm.plan, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cSchedulerApp:planUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
