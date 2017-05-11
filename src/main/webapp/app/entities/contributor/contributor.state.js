(function() {
    'use strict';

    angular
        .module('cSchedulerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contributor', {
            parent: 'entity',
            url: '/contributor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cSchedulerApp.contributor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contributor/contributors.html',
                    controller: 'ContributorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contributor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('contributor-detail', {
            parent: 'contributor',
            url: '/contributor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cSchedulerApp.contributor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contributor/contributor-detail.html',
                    controller: 'ContributorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contributor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Contributor', function($stateParams, Contributor) {
                    return Contributor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'contributor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('contributor-detail.edit', {
            parent: 'contributor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contributor/contributor-dialog.html',
                    controller: 'ContributorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Contributor', function(Contributor) {
                            return Contributor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contributor.new', {
            parent: 'contributor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contributor/contributor-dialog.html',
                    controller: 'ContributorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                hash: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('contributor', null, { reload: 'contributor' });
                }, function() {
                    $state.go('contributor');
                });
            }]
        })
        .state('contributor.edit', {
            parent: 'contributor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contributor/contributor-dialog.html',
                    controller: 'ContributorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Contributor', function(Contributor) {
                            return Contributor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contributor', null, { reload: 'contributor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contributor.delete', {
            parent: 'contributor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contributor/contributor-delete-dialog.html',
                    controller: 'ContributorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Contributor', function(Contributor) {
                            return Contributor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contributor', null, { reload: 'contributor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
