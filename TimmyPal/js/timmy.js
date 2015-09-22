var myApp = angular.module('timmy', []);

myApp.controller('timmyController', ['$scope', function($scope){
	$scope.tmp;
	$scope.toggle = function() {
		$scope.output = $scope.hourStayAwake*10.5 + $scope.weightNumber*0.05 + $scope.ageNumber*2 + $scope.hourSleep*6 + $scope.amountYears*10  + $scope.timeAwake*3;
		$scope.output2 = "mg";
		if (isNaN($scope.output)) {
			$scope.output = "Enter Numbers!";
			$scope.output2 = " ";
		}

	
		$scope.tmp = $scope.output;
	};


	$scope.hashList = [
		{
			type: 'Original Blend Coffee - Small',
			number: '140'
		},
		{
			type: 'Original Blend Coffee - Medium',
			number: '205'
		},
		{
			type: 'Original Blend Coffee - Large',
			number: '270'
		},
		{
			type: 'Original Blend Coffee - XLarge',
			number: '330'
		},
		{
			type: 'Dark Roast Coffee - Small',
			number: '135'
		},
		{
			type: 'Dark Roast Coffee - Medium',
			number: '200'
		},
		{
			type: 'Dark Roast Coffee - Large',
			number: '265'
		},
		{
			type: 'Dark Roast Coffee - XLarge',
			number: '320'
		},

		{
			type: 'French Vanilla Cap- Small',
			number: '60'
		},
		{
			type: 'French Vanilla Cap- Medium',
			number: '80'
		},
		{
			type: 'French Vanilla Cap- Large',
			number: '110'
		},
		{
			type: 'French Vanilla Cap- XLarge',
			number: '135'
		},

		{
			type: 'Iced Cap - Small',
			number: '90'
		},

		{
			type: 'Iced Cap - Medium', 
			number: '120'
		},

		{
			type: 'Iced Cap - Large',
			number: '150'
		},

		{
			type: 'Steeped Tea w/ Whole Leaf - Small',
			number: '90'
		},
		{
			type: 'Steeped Tea w/ Whole Leaf - Medium',
			number: '125'
		},
		{
			type: 'Steeped Tea w/ Whole Leaf - Large',
			number: '175'
		},
		{
			type: 'Steeped Tea w/ Whole Leaf - XLarge',
			number: '210'
		},

	];


	// $scope.output_drink = $scope.hashList;
	// $scope.output_drink = $scope.hashList[1].number;
	   $scope.output_drink = $scope.tmp;
	   $scope.i = 0;

	$scope.search = function() {
		$scope.prev = $scope.tmp;
		for ($scope.i = 0;  $scope.i < $scope.hashList.length; $scope.i++){
			$scope.diff = Math.abs($scope.output - $scope.hashList[$scope.i].number);
			if ($scope.diff < $scope.prev) {
				$scope.prev = $scope.diff;
				$scope.valueFinal = $scope.i;
			}
		}
		$scope.discover = $scope.hashList[$scope.valueFinal].type;	
	}
}]);