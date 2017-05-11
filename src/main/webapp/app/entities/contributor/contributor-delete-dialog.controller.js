(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .controller('ContributorDeleteController',ContributorDeleteController);

    ContributorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Contributor'];

    function ContributorDeleteController($uibModalInstance, entity, Contributor) {
        var vm = this;

        vm.contributor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Contributor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
