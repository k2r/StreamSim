<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<h1 class="sectionTitle">Stream live control</h1>

<table class="form">
	<tr>
		<td>
			<form action="/streamsim/livecontrol" method="POST">
				<table>

					<tr>
						<td class="label"><label>Select a stream output rate</label>:</td>
						<td class="input"><input class="rangeSelect" type="range"
							id="rateInput" value="0" min="0" max="1000" step="50" name="rate"
							oninput="rateOutput.value = rateInput.value"></input> <output
								id="rateOutput">0</output></td>
					</tr>

					<tr>
						<td><input type="hidden" name="command" value="PLAY" /></td>
						<td><input type="hidden" name="frequency" value="1" /></td>
					</tr>

					<tr>
						<td class="submit"><input class="button" type="submit"
							value="Set rate and generate" name="generate"></input></td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
	<tr>
		<td class="submit">
			<form action="/streamsim/livecontrol" method="POST">
				<input class="buttonStop" type="submit" value="Stop the emission"
					name="stop"></input>
			</form>
		</td>
	</tr>
</table>