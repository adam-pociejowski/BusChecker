
busApp.controller('RegisterController', function($scope, $http, $location) {
    $scope.user = {};

    $.fn.datepicker.defaults.format = "dd/mm/yyyy";
    $('.datepicker').datepicker({
        startDate: '-3d'
    });

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