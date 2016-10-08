angular.module('fpwApp')
.controller('loginCtrl', function($scope, $http, $location){
	$scope.status = 'Unknown';

	$scope.login = function(user){
		$http.post('/fpw/login.json',user)
		.then(function(goodResponse){
				$scope.status = 'Success ' + goodResponse.status;
				$location.path('/cameras');
		}, function(badResponse){
				$scope.status = 'Fail ' + badResponse.status;
			
		});
	}
});


