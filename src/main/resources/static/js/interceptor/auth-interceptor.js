
busApp.factory('AuthInterceptor', ['$q', '$location', function ($q, $location) {

    return {
        responseError: function (response) {
            if (response.status === 401) {
                $location.path('login');
            } else if (response.status === 404) {
                $location.path('login');
            }
            return $q.reject(response);
        }
    };

}]).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
}]);