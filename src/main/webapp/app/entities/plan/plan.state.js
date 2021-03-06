(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('plan', {
            parent: 'entity',
            url: '/plan',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cSchedulerApp.plan.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plan/plans.html',
                    controller: 'PlanController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('plan');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('plan-detail', {
            parent: 'plan',
            url: '/plan/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cSchedulerApp.plan.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plan/plan-detail.html',
                    controller: 'PlanDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('plan');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Plan', function($stateParams, Plan) {
                    return Plan.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'plan',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('plan-detail.edit', {
            parent: 'plan-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plan/plan-dialog.html',
                    controller: 'PlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Plan', function(Plan) {
                            return Plan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plan.new', {
            parent: 'plan',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plan/plan-dialog.html',
                    controller: 'PlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('plan', null, { reload: 'plan' });
                }, function() {
                    $state.go('plan');
                });
            }]
        })
        .state('plan.edit', {
            parent: 'plan',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plan/plan-dialog.html',
                    controller: 'PlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Plan', function(Plan) {
                            return Plan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plan', null, { reload: 'plan' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plan.delete', {
            parent: 'plan',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plan/plan-delete-dialog.html',
                    controller: 'PlanDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Plan', function(Plan) {
                            return Plan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plan', null, { reload: 'plan' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
