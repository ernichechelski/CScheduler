(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .controller('PlanController', PlanController);

    PlanController.$inject = ['Plan'];

    function PlanController(Plan) {

        var vm = this;

        vm.plans = [];

        loadAll();

        function loadAll() {
            Plan.query(function(result) {
                vm.plans = result;
                vm.searchQuery = null;
            });
        }
    }
})();
