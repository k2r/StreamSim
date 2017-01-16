google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {

	var data = new google.visualization.DataTable();
	data.addColumn('number', 'timestamp');
	data.addColumn('number', 'variation');

	for (i = 0; i < timestamps.length; i++) { 
		data.addRow([timestamps[i], values[i]]);
	}

	var options = {
			hAxis: {
				title: 'Timestamp (in s)'
			},
			vAxis: {
				title: 'Rate (items/s)'
			},
	};

	var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
	chart.draw(data, options);
}


