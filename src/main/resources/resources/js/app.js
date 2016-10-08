angular.module('fpwApp', ['ngRoute'])
.config(
	function($routeProvider){
		$routeProvider
		.when('/cameras', {
			templateUrl : 'template/cameras.html',
			controller  : 'camerasCtrl'
		})
		.when('/login', {
			templateUrl : 'template/login.html',
			controller  : 'loginCtrl'
		})
		.otherwise('/login');
	}
);
