
busApp.controller('RegisterController', function($scope, $http, $location) {
    $scope.user = {};

    $scope.register = function() {
        var errorMessage = null;

        $http.post('registerUser', $scope.user).
        catch(function (response) {
            errorMessage = response.data[0];
        }).finally(function () {
            if (errorMessage == null) {
                $location.path("login");
            }
            else {
                appController.addErrorAlert(errorMessage);
            }
        });
    };
});