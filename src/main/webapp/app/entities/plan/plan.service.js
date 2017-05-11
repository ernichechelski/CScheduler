(function() {
    'use strict';
    angular
        .module('cSchedulerApp')
        .factory('Plan', Plan);

    Plan.$inject = ['$resource'];

    function Plan ($resource) {
        var resourceUrl =  'api/plans/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
