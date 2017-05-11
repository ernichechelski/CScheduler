(function() {
    'use strict';
    angular
        .module('cSchedulerApp')
        .factory('Event', Event);

    Event.$inject = ['$resource', 'DateUtils'];

    function Event ($resource, DateUtils) {
        var resourceUrl =  'api/events/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.start = DateUtils.convertDateTimeFromServer(data.start);
                        data.stop = DateUtils.convertDateTimeFromServer(data.stop);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
