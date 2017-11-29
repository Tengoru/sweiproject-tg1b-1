var app = angular.module('ActivityMeterApp', ['ui.bootstrap']);

// app.config(function ($qProvider) {
// $qProvider.errorOnUnhandledRejections(false);
// });

		function loadActivities ($scope, $http){
     		$http({
            		 method : 'GET',

                      /*
                     * url: (window.location.hostname ===
                     * 'localhost' ?
                     * 'http://localhost:8080/activity' :
                     * 'https://activityexample.herokuapp.com/activity')
                       */

            		url: 'activity'

         		}).then(function (response) {
          			 $scope.activities = response.data;
       		});
       	}
       	function loadFilteredActivities ($scope, $http, tag){
             		$http({
                    		 method : 'GET',
                                    /*
								 * url: (window.location.hostname ===
								 * 'localhost' ?
								 * 'http://localhost:8080/activity' :
								 * 'https://activityexample.herokuapp.com/activity')
                                */
                    		 url: 'activity/' + tag
                    		 /* url: 'activity/{tag}' */

                 		}).then(function (response) {
                  			 $scope.activities = response.data;
               		});
               	}


app.controller('ActivityCtrl', function ($scope, $http, $dialog) {

  	loadActivities($scope, $http);


  	var addDialogOptions = {
    	controller: 'AddActivityCtrl',
    	templateUrl: './activityAdd.html'
  	};

  	$scope.add = function(){
    	$dialog.dialog(angular.extend(addDialogOptions, {})).open().then(function (){
    	    loadActivities($scope, $http);
        }) ;
  	};


  	// -------------------
  	/*
  	var filterDialogOptions = {
    	controller: 'FilterActivityCtrl',
    	templateUrl: './activityFilter.html'
  	};
*/
  	$scope.filter = function(){
  		loadFilteredActivities($scope, $http, $scope.tag);
  	};

  	// ------------------

  	var editDialogOptions = {
	    controller: 'EditActivityCtrl',
	    templateUrl: './activityEdit.html',
	};
  	$scope.edit = function(activity){
   	 	var activityToEdit = activity;
    	$dialog.dialog(angular.extend(editDialogOptions, {resolve: {activity: angular.copy(activityToEdit)}})).open().then(function (){
    	    loadActivities($scope, $http);
        }) ;
  	};

	$scope.delete = function(activity) {
		var deleteRequest = {
			method : 'DELETE',
			url: 'activity/' + activity.id
		};

  		$http(deleteRequest).then(function() {
			loadActivities($scope, $http);
		});
  		// todo handle error
	};
});



app.controller('AddActivityCtrl', function($scope, $http, dialog){

  	$scope.save = function() {
  		var postRequest = {
    	method : 'POST',
       	url: 'activity' ,
       	data: {
  				text: $scope.activity.text,
  				tags: $scope.activity.tags,
  				title: $scope.activity.title,
  				date: $scope.activity.date
			  }
		}

  		$http(postRequest).then(function (response) {
  		    $scope.activities = response.data;
  		}).then(function () {
  			$scope.close();
  		});
  	};

  	$scope.close = function(){;
    	dialog.close(undefined);
  	};
});
app.controller('EditActivityCtrl', function ($scope, $http, activity, dialog) {

	$scope.activity = activity;
  	$scope.save = function() {
  	    var putRequest = {
    	method : 'PUT',
       	url: 'activity/' + $scope.activity.id,
       	data: {
  				text: $scope.activity.text,
  				tags: $scope.activity.tags,
  				title: $scope.activity.title,
  				date: $scope.activity.date
			  }
		}

  		$http(putRequest).then(function (response) {
  		    $scope.activities = response.data;
  		}).then(function () {
			// todo handle error
			$scope.close();
		});
  	};

  	$scope.close = function(){
  		loadActivities($scope, $http);
    	dialog.close();
  	};
});

/*
app.controller('FilterActivityCtrl', function ($scope, $http, dialog) {


  	  $scope.filter = function(){
  	  	 //loadFilteredActivities($scope, $http, $scope.tag);
  	     dialog.close($scope.tag);
  	  };
  	  $scope.close = function(){
	     dialog.close();
	  };

});
*/