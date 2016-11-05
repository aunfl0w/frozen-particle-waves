angular.module('fpwApp', ['ngRoute', 'ngAnimate'])
.config(
	function($routeProvider){
		$routeProvider
		.when('/cameras', {
			templateUrl : 'template/cameras.html',
			controller  : 'camerasCtrl'
		})
		.when('/camera/:cameraId/recent', {
			templateUrl : 'template/camera.html',
			controller  : 'cameraCtrl'
			
		})
		.when('/login', {
			templateUrl : 'template/login.html',
			controller  : 'loginCtrl'
		})
		.otherwise('/login');
	}
);
