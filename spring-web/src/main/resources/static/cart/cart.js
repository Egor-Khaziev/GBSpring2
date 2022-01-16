angular.module('market-front').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/app/';

    $scope.loadCart = function () {
        $http({
            url: contextPath + 'api/v1/cart',
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }


    $scope.reduceToCart = function (productId) {
        $http({
            url: contextPath + 'api/v1/cart/reduce/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.addToCart = function (productId) {
        $http.get(contextPath + 'api/v1/cart/add/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.clearCart = function () {
        $http.get(contextPath + 'api/v1/cart/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.clearProductToCart = function (id, name) {
        $http({
            url: contextPath + 'api/v1/cart/clear/' + id,
            method: 'GET'
        }).then(function (response) {
            alert('Product ' + name + ' clear');
            $scope.loadCart();
        });
    };

    $scope.checkOut = function () {
        $http({
            url: contextPath + 'api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            $scope.loadCart();
            $scope.orderDetails = null
        });
    };

    $scope.loadCart();
});