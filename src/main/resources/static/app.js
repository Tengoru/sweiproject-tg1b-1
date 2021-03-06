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

       	function loadFilteredActivities ($scope, $http, tag) {
			$http({
                method: 'GET',
                /*
             * url: (window.location.hostname ===
             * 'localhost' ?
             * 'http://localhost:8080/activity' :
             * 'https://activityexample.herokuapp.com/activity')
            */
                url: 'activity/filter/' + tag
                /* url: 'activity/{tag}' */

            }).then(function (response) {
                $scope.activities = response.data;
            });

               	}

//               	function loadDetailsOfActivitiy ($scope, $http, id){
//                             		$http({
//                                    		 method : 'GET',
//                                                    /*
//                								 * url: (window.location.hostname ===
//                								 * 'localhost' ?
//                								 * 'http://localhost:8080/activity' :
//                								 * 'https://activityexample.herokuapp.com/activity')
//                                                */
//                                    		 url: 'activity/' + id
//                                    		 /* url: 'activity/{tag}' */
//
//                                 		}).then(function (response) {
//                                  			 $scope.activities = response.data;
//                               		});
//                               	}



app.controller('VerifyEmailCtrl', function($scope, $http, dialog){
	$scope.send = function() {
		var postRequest = {
			method : 'POST',
			url:  'email',
			data: {
				email: $scope.email.email
			}
		};

		$http(postRequest).then(function (response) {
			if (response.data["msg"] != "ok") {
				alert("E-Mail verification error: " + response.data["msg"]);
			}
		}).then(function () {
			$scope.close();
		});
	};

	$scope.close = function(){
		dialog.close(undefined);
	};
});

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

  	var addMailOptions = {
  		controller: 'VerifyEmailCtrl',
		templateUrl: './getVerificationCode.html'

	};
  	$scope.verifyEmail = function(){
  		$dialog.dialog(angular.extend(addMailOptions, {})).open().then(function (){
            loadActivities($scope, $http);
        }) ;
	}

  	var detailsDialogOptions = {
        	controller: 'DetailActivityCtrl',
        	templateUrl: './activityDetail.html'
      	};
    $scope.details = function(activity){
       	 	var activityToShowDetails = activity;
        	$dialog.dialog(angular.extend(detailsDialogOptions, {resolve: {activity: angular.copy(activityToShowDetails)}})).open().then(function (){
        	    loadActivities($scope, $http);
            }) ;
      	};


  	$scope.filter = function(){
  		if($scope.tag.length==0)
  			loadActivities($scope,$http);
  		else
        loadFilteredActivities($scope, $http, $scope.tag);
  	};

  	var editDialogOptions = {
	    controller: 'EditActivityCtrl',
	    templateUrl: './activityEdit.html'
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
  				date: $scope.activity.date,
            verificationCode: $scope.activity.verificationCode
		}
		};
  		$http(postRequest).then(function (response) {
  		    $scope.activities = response.data;
  		}).then(function () {
  			$scope.close();
  		});
  	};

  	$scope.close = function(){
    	dialog.close(undefined);
  	};
});

app.controller('DetailActivityCtrl', function ($scope, $http, activity, dialog) {
    $scope.activity = activity;

	$http({
              method: 'GET',
              url: 'activity/' + $scope.activity.id

              }).then(function (response) {
      		    $scope.activities = response.data;
    });
  	$scope.close = function(){
  		loadActivities($scope, $http);
    	dialog.close();
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
		};

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