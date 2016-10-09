
busApp.controller('CheckerController', function($scope, $http, $filter, $location, loggedUser) {
    dates = [];
    infos = [];
    sitters = [];
    var editingObj = null;
    $scope.loggedUser = {};


    $.fn.datepicker.defaults.format = "dd/mm/yyyy";
    $('.datepicker').datepicker({
        startDate: '-3d'
    });


    $scope.$on('$routeChangeSuccess', function() {
        $http.get('/loggeduser').then(function(response) {
            if (response.data.username) {
                loggedUser.setUsername(response.data.username);
                loggedUser.setRoles(response.data.role);
                $scope.loggedUser.username = loggedUser.getUsername();
                var username = loggedUser.getUsername();
                if (username) {
                    $http.post('/secured/rest/checkerdata', username).
                    then(function(resp) {
                        setData(resp.data);
                    });
                }
            }
            else {
                $location.path('/login');
            }
        });
    });



    $scope.nextReview = function(obj) {
        if (obj.label == 'Przegląd techniczny') {
            return getFutureDate(obj.value, 12);
        }
        else if (obj.label == 'Przegląd windy') {
            return getFutureDate(obj.value, 24);
        }
        else if (obj.label == 'Przegląd gaśnicy') {
            return getFutureDate(obj.value, 30);
        }
        else if (obj.label == 'Przegląd tachografu') {
            return getFutureDate(obj.value, 18);
        }
        else if (obj.label == 'Ubezpieczenie wozu') {
            return getFutureDate(obj.value, 12);
        }
    };

    var getFutureDate = function(date, monthsInFuture) {
        var dateParam = date.split('/');
        var year = parseInt(dateParam[2]) + parseInt((monthsInFuture / 12));
        var month = parseInt(dateParam[1]);
        if (month + (monthsInFuture % 12) > 12) {
            month = (month + (monthsInFuture % 12)) % 12;
            year++;
        }
        else {
            month += monthsInFuture % 12;
        }
        var monthString = month;
        if (month < 10) {
            monthString = '0' + monthString;
        }
        var day = getCorrectDay(parseInt(dateParam[0]), month);
        return day + '/' + monthString +'/' + year;
    };

    var getCorrectDay = function(day, month) {
        var max = getMonthMaxDay(month);
        if (day > max) {
            day = max;
        }
        if (day < 10) {
            return '0'+day;
        }
        return day;
    };

    var getMonthMaxDay = function(month) {
        if (month == 2) return 29;
        else if (month == 4 || month == 6 || month == 9 || month == 11) return 30;
        else return 31;
    };

    $scope.edit = function(obj) {
        $scope.modalInput = obj.value;
        editingObj = obj;
    };


    $scope.save = function() {
        if (editingObj != null) {
            editingObj.value = $scope.modalInput;
        }

        var data = {
            loggedUser : loggedUser.getUsername(),
            busName : '',
            sideNumber : '',
            rejestrNumber : '',
            firstname : '',
            lastname : '',
            umberOfSeats : 0,
            technicalReviewDate : null,
            liftReviewDate : null,
            extinguisherReviewDate : null,
            tachographReviewDate : null,
            insuranceDate : null,
            notificationBetweenEventDays : 0,
            sitters : []
        };

        data.busName = getObject('Bus');
        data.sideNumber = getObject('Nr boczny');
        data.rejestrNumber = getObject('Nr rejestracyjny');
        data.numberOfSeats = getObject('Liczba miejsc');
        data.notificationBetweenEventDays = parseInt(getObject('Powiadomienie na telefon').split(' '));
        data.technicalReviewDate = getDate('Przegląd techniczny');
        data.liftReviewDate = getDate('Przegląd windy');
        data.extinguisherReviewDate = getDate('Przegląd gaśnicy');
        data.tachographReviewDate = getDate('Przegląd tachografu');
        data.insuranceDate = getDate('Ubezpieczenie wozu');
        for (var k = 0; k < $scope.sitters.length; k++) {
            var sitter = $scope.sitters[k].value.split(' ');
            var sitterObj = {
                  firstname : sitter[0],
                  lastname : sitter[1]
            };
            data.sitters.push(sitterObj)
        }
        var driverData = $scope.driver.split(' ');
        var driverObj = {
            firstname : driverData[0],
            lastname : driverData[1]
        };
        data.firstname = driverObj.firstname;
        data.lastname = driverObj.lastname;

        $http.post('/secured/rest/save', data).
        then(function(response) {});
    };

    var setData = function(responseData) {
        $scope.driver = responseData.firstname+' '+responseData.lastname;

        for (var i = 0; i < responseData.sitters.length; i++) {
            var sitter = responseData.sitters[i].firstname +' '+responseData.sitters[i].lastname;
            sitters.push(createObject('sitter '+i, sitter));
        }
        $scope.sitters = sitters;

        infos.push(createObject('Bus', responseData.busName));
        infos.push(createObject('Nr boczny', responseData.sideNumber));
        infos.push(createObject('Nr rejestracyjny', responseData.rejestrNumber));
        infos.push(createObject('Liczba miejsc', responseData.numberOfSeats));
        infos.push(createObject('Powiadomienie na telefon', responseData.notificationBetweenEventDays+' dni wcześniej'));
        $scope.infos = infos;

        dates.push(createObject('Przegląd techniczny', dateFormat(responseData.technicalReviewDate)));
        dates.push(createObject('Przegląd windy', dateFormat(responseData.liftReviewDate)));
        dates.push(createObject('Przegląd gaśnicy', dateFormat(responseData.technicalReviewDate)));
        dates.push(createObject('Przegląd tachografu', dateFormat(responseData.tachographReviewDate)));
        dates.push(createObject('Ubezpieczenie wozu', dateFormat(responseData.insuranceDate)));
        $scope.dates = dates;
    };

    var dateFormat = function(date) {
        return $filter('date')(date,'dd/MM/yyyy');
    };

    var createObject = function(description, val) {
        return {
            label : description,
            value : val
        };
    };

    var getDate = function(key) {
        for (var j = 0; j < $scope.dates.length; j++) {
            var o = $scope.dates[j];
            if (o.label === key) {
                if (o.value !== null || o.value !== '') {
                    var dateArray = o.value.split('/');
                    var date = dateArray[2]+'-'+dateArray[1]+'-'+dateArray[0];
                    return new Date(date);
                }
            }
        }
        return null;
    };

    var getObject = function(key) {
        var list = $scope.infos.concat($scope.sitters);

        for (var j = 0; j < list.length; j++) {
            var o = list[j];
            if (o.label === key) {
                return o.value;
            }
        }
        return '';
    }
});