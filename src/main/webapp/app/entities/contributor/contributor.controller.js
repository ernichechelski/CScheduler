(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .controller('ContributorController', ContributorController);

    ContributorController.$inject = ['Contributor'];

    function ContributorController(Contributor) {

        var vm = this;

        vm.contributors = [];

        loadAll();

        function loadAll() {
            Contributor.query(function(result) {
                vm.contributors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
