var pageApi = (function() {

	var _num = "";

	function _getTimeAndDate() {
		let currentDate = "";
		var today = new Date();
		currentDate = today.getDate()+"/"+(today.getMonth()+1)+"/"+today.getFullYear()+" - "+
					  today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
		return currentDate;
	}

	function _isValidNumber(service) {
		let isValid = false;
		let string = $("#"+service+"Number").val();
		if (!( string === "" || string == null )) {
			isValid = true;
			_num = string;
		}
		return isValid;
	}

	function _getSqrt() {
		console.log("IN GET SQRT: "+_num);
		var getPromise = $.get("/sqrt?value="+_num);
		getPromise.then(
			function(data) {
				console.log("Done! (Sqrt)");
				console.log("  Data: "+JSON.stringify(data));
			},
			function(data) {
				console.log("Error! (Sqrt)");
				console.log("  Data: "+JSON.stringify(data));
			}
		);
	}

	function _getExp() {
		console.log("IN GET EXP: "+_num);
		var getPromise = $.get("/exp?value="+_num);
		getPromise.then(
			function(data) {
				console.log("Done! (Exp)");
				console.log("  Data: "+JSON.stringify(data));
			},
			function(data) {
				console.log("Error! (Exp)");
				console.log("  Data: "+JSON.stringify(data));
			}
		);
	}

	function calculateSqrt() {
		if (_isValidNumber("sqrt")) {
			console.log("Send at: "+_getTimeAndDate());
			_getSqrt();
		}
		else {
			window.alert("The 'Number' field cannot be empty");
		}
	}

	function calculateExp() {
		if (_isValidNumber("exp")) {
			console.log("Send at: "+_getTimeAndDate());
			_getExp();
		}
		else {
			window.alert("The 'Number' field cannot be empty");
		}
	}

	return {
		calculateSqrt: calculateSqrt,
		calculateExp: calculateExp
	}
})();