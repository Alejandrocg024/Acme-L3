
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message
		code="administrator.dashboard.form.title.general-indicators" />
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-principals" />		
		</th>
<td>
		<jstl:forEach items="${principalsByRole}" var="principal">
			<jstl:out value="${principal}"></jstl:out>
		</jstl:forEach>
		</td>

	</tr>
	<tr >
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.ratio-peeps" /></th>

		<td><jstl:out value="${peepsRatioWithLinkAndEmail}"></jstl:out></td>


	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.ratio-bulletin" /></th>
<td>
		<jstl:forEach items="${ratioOfBulletinsByCriticality}" var="bulletin">
			<jstl:out value="${bulletin}"></jstl:out>
		</jstl:forEach>
		</td>

	</tr>

	
		<jstl:forEach items="${offersByCurrencyStats}" var="offer">
<tr>
			<th><acme:message code="Stats of offers with: ${offer.key}" /></th>
			<td><jstl:out value="${offer.value.toString()}">
				</jstl:out></td>
</tr>
		</jstl:forEach>
	
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.notes" /></th>

		<td><jstl:out value="${notesInLast10WeeksStats}"></jstl:out></td>
</table>

<h2>
	<acme:message code="administrator.dashboard.form.title.application-statuses"/>
</h2>


<div>
	<canvas id="canvas"></canvas>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
				"Ratio of Peep with email and link"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${peepsRatioWithLinkAndEmail}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};

		var canvas, context;

		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return />

