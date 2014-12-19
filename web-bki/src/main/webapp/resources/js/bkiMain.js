(function () {
    'use strict';
    angular.module('bki', ['ngRoute'])
        .controller('ClientsListController', function ($scope, $http) {
            $scope.clients = [];
            $http.get('api/clients').success(function (data) {
                $scope.clients = data.list;
            });
        })

        .controller('ClientDetailsController', function($scope, $http, $routeParams){
            $http.get('api/clients/' + $routeParams.clientId).success(function (data) {
                $scope.client = data;
            });
            $scope.saveClient = function() {
               $http.post('api/clients', $scope.client).success(function(data, status){
                   alert(status + " " + data);
               });
            };
        })

        .config(function ($routeProvider) {
            $routeProvider.when('/clients', {
                templateUrl: 'resources/templates/clientslist.html',
                controller: 'ClientsListController'
            });

            $routeProvider.when('/clients/:clientId', {
                templateUrl: 'resources/templates/clientdetails.html',
                controller: 'ClientDetailsController'
            });
        })

        .config(['$httpProvider', function ($httpProvider) {
            $httpProvider.interceptors.push(function ($q, $rootScope) {
                $rootScope.alert = {
                    show: false,
                    msg: "",
                    hide: function () {
                        this.show = false;
                    }
                };
                return {
                    'responseError': function (rejection) {
                        $rootScope.alert.msg = rejection.data.message;
                        $rootScope.alert.show = true;
                        console.log(rejection);
                        return $q.reject(rejection);
                    }
                };
            });
        }]);
})();
