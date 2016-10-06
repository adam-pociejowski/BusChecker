
busApp.controller('CheckerController', function($scope, $http, $filter, loggedUser) {
    dates = [];
    infos = [];
    sitters = [];
    var editingObj = null;

    var username = loggedUser.getUsername();
    console.log("User: "+loggedUser.getUsername());
    $http.post('/secured/rest/checkerdata', username).
    then(function(response) {
        setData(response.data);
    });

    $scope.edit = function(obj) {
        $scope.modalInput = obj.value;
        editingObj = obj;
    };


    $scope.save = function() {
        editingObj.value = $scope.modalInput;

        var data = {
            busName : '',
            sideNumber : '',
            rejestrNumber : '',
            firstname : '',
            lastname : '',
            umberOfSeats : 0,
            technicalReviewDate : '',
            liftReviewDate : '',
            extinguisherReviewDate : '',
            tachographReviewDate : '',
            insuranceDate : '',
            notificationBetweenEventDays : 0,
            sitters : []
        };

        data.busName = getObject('Bus');
        data.sideNumber = getObject('Nr boczny');
        data.rejestrNumber = getObject('Nr rejestracyjny');
        data.numberOfSeats = getObject('Liczba miejsc');
        data.notificationBetweenEventDays = parseInt(getObject('Powiadomienie na telefon').split(' '));
        data.technicalReviewDate = getObject('Przegląd techniczny');
        data.liftReviewDate = getObject('Przegląd windy');
        data.technicalReviewDate = getObject('Przegląd gaśnicy');
        data.tachographReviewDate = getObject('Przegląd tachografu');
        data.insuranceDate = getObject('Ubezpieczenie wozu');
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
        console.log(data);

        $http.post('/secured/rest/save', data).
        then(function(response) {
            console.log(response.data);
        });

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
        return $filter('date')(date,'dd-MM-yyyy');
    };

    var createObject = function(description, val) {
        return {
            label : description,
            value : val
        };
    };

    var getObject = function(key) {
        var list = $scope.infos.concat($scope.dates.concat($scope.sitters));

        for (var j = 0; j < list.length; j++) {
            var o = list[j];
            if (o.label === key) {
                return o.value;
            }
        }
        return '';
    }
});