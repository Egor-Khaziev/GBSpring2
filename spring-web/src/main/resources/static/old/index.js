angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    };

    // $scope.addToCart = function (productId) {
    //     $http.get(contextPath + '/carts/add/' + productId)
    //         .then(function (response) {
    //             $scope.loadCart();
    //         });
    // }

    // $scope.reduceToCart = function (id) {
    //     $http({
    //         url: contextPath + '/carts/reduce/' + id,
    //         method: 'GET'
    //     }).then(function (response) {
    //         $scope.loadCart();
    //     });
    // };

    // $scope.clearCart = function () {
    //     $http.get(contextPath + '/carts/clear')
    //         .then(function (response) {
    //             $scope.loadCart();
    //         });
    // }

    $scope.loadCart = function () {
        $http.get(contextPath + '/carts')
            .then(function (response) {
                $scope.Cart = response.data;
            });
    }

    $scope.loadOrders = function () {
        if (!$scope.isUserLoggedIn()) {
            return;
        }
        $http({
            url: contextPath + '/orders/user',
            method: 'GET'
        }).then(function (response) {
            $scope.orders = response.data;
        });
    }

    $scope.createOrder = function () {
        $http({
            url: contextPath + '/orders',
            method: 'POST'
        }).then(function (response) {
            alert('Order created');
            $scope.loadCart();
            $scope.loadOrders();
        });
    }

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.showCurrentUserInfo = function () {
        $http.get(contextPath + '/profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    }

    $scope.clearProductToCart = function (id, name) {
        $http({
            url: contextPath + '/carts/clear/' + id,
            method: 'GET'
        }).then(function (response) {
            alert('Product ' + name + ' clear');
            $scope.loadCart();
        });
    };

    if ($localStorage.logUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        $scope.loadOrders();
    }

    $scope.loadProducts();
    $scope.loadCart();
});