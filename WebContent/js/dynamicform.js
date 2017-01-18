function parameterForm(index) {
	var selection = document.getElementById("type" + index);
	var type = selection.options[selection.selectedIndex].text;
	var html = '';
	if(type == "enumerate"){
		html += '<td><label>Values:</label></td>';
		html += '<td><input type="text" name="values' + index + '" /></td>';
	}
	if(type == "integer"){
		html += '<td><label>Min value:</label></td>';
		html += '<td><input type="text" name="min' + index + '" /></td>';
		html += '<td><label>Max value:</label></td>';
		html += '<td><input type="text" name="max' + index + '" /></td>';
	}
	if(type == "text"){
		html += '<td><label>Pattern:</label></td>';
		html += '<td><select name="pattern' + index + '">';
		html += '<option value="[a-z]">from a to z</option>';
		html += '<option value="[A-Z]">from A to Z</option>';
		html += '<option value="[0-9]">from 0 to 9</option>';
		html += '<option value="[a-Z]">from a to Z</option>';
		html += '<option value="[a-9]">from a to 9</option>';
		html += '<option value="[A-9]">from A to 9</option>';
   		html += '</select></td>';
		html += '<td><label>Length:</label></td>';
		html += '<td><input type="text" name="length' + index + '" /></td>';
	}
	document.getElementById('parameter' + index).innerHTML = html;
}