<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="company.companyDashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.companyDashboard.form.label.numberOfPracticaPerMonth"/>
		</th>
		<td>
			<acme:print value="${numberOfPracticaPerMonth}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.companyDashboard.form.label.maxLengthOfASession"/>
		</th>
		<td>
			<acme:print value="${periodLengthOfSessionsStats.getMax()}"/>
			<acme:message code="company.companyDashboard.form.hours"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.companyDashboard.form.label.minLengthOfASession"/>
		</th>
		<td>
			<acme:print value="${periodLengthOfSessionsStats.getMin()}"/>
			<acme:message code="company.companyDashboard.form.hours"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.companyDashboard.form.label.linDevLengthOfASession"/>
		</th>
		<td>
			<acme:print value="${periodLengthOfSessionsStats.getLinDev()}"/>
			<acme:message code="company.companyDashboard.form.hours"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.companyDashboard.form.label.averageLengthOfASession"/>
		</th>
		<td>
			<acme:print value="${periodLengthOfSessionsStats.getAverage()}"/>
			<acme:message code="company.companyDashboard.form.hours"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.companyDashboard.form.label.maxLengthOfAPracticum"/>
		</th>
		<td>
			<acme:print value="${periodLengthOfPracticaStats.getMax()}"/>
			<acme:message code="company.companyDashboard.form.hours"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.companyDashboard.form.label.minLengthOfAPracticum"/>
		</th>
		<td>
			<acme:print value="${periodLengthOfPracticaStats.getMin()}"/>
			<acme:message code="company.companyDashboard.form.hours"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.companyDashboard.form.label.linDevLengthOfAPracticum"/>
		</th>
		<td>
			<acme:print value="${periodLengthOfPracticaStats.getLinDev()}"/>
			<acme:message code="company.companyDashboard.form.hours"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.companyDashboard.form.label.averageLengthOfAPracticum"/>
		</th>
		<td>
			<acme:print value="${periodLengthOfPracticaStats.getAverage()}"/>
			<acme:message code="company.companyDashboard.form.hours"/>
		</td>
	</tr>
</table>

</br>

<jstl:choose>
	<jstl:when test="${periodLengthOfSessionsStats.getMax() != 0 || periodLengthOfSessionsStats.getMin() != 0 || periodLengthOfSessionsStats.getLinDev() != 0 || periodLengthOfSessionsStats.getAverage() != 0}">
		<h2>
			<acme:message code="company.companyDashboard.form.title.practicumSessionsChart"/>
		</h2>
		
		<div>
			<canvas id="canvas"></canvas>
		</div>

		<script type="text/javascript">
			$(document).ready(function() {
				var data = {
					labels : [
							"MAX", "MIN", "LINEAR DEVIATION", "AVERAGE"
					],
					datasets : [
						{
							data : [
								<jstl:out value="${periodLengthOfSessionsStats.getMax()}"/>, 
								<jstl:out value="${periodLengthOfSessionsStats.getMin()}"/>, 
								<jstl:out value="${periodLengthOfSessionsStats.getLinDev()}"/>,
								<jstl:out value="${periodLengthOfSessionsStats.getAverage()}"/>
							],
							backgroundColor: [
							      'rgb(255, 202, 58)',
							      'rgb(25, 130, 196)',
							      'rgb(138, 201, 38)',
							      'rgb(255, 89, 94)'
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
									suggestedMax : 100.0
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
	</jstl:when>
</jstl:choose>

</br>
</br>

<jstl:choose>
	<jstl:when test="${periodLengthOfPracticaStats.getMax() != 0 || periodLengthOfPracticaStats.getMin() != 0 || periodLengthOfPracticaStats.getLinDev() != 0 || periodLengthOfPracticaStats.getAverage() != 0}">
		<h2>
			<acme:message code="company.companyDashboard.form.title.practicaChart"/>
		</h2>
		
		<div>
			<canvas id="canvas2"></canvas>
		</div>

		<script type="text/javascript">
			$(document).ready(function() {
				var data = {
					labels : [
							"MAX", "MIN", "LINEAR DEVIATION", "AVERAGE"
					],
					datasets : [
						{
							data : [
								<jstl:out value="${periodLengthOfPracticaStats.getMax()}"/>, 
								<jstl:out value="${periodLengthOfPracticaStats.getMin()}"/>, 
								<jstl:out value="${periodLengthOfPracticaStats.getLinDev()}"/>,
								<jstl:out value="${periodLengthOfPracticaStats.getAverage()}"/>
							],
							backgroundColor: [
							      'rgb(255, 202, 58)',
							      'rgb(25, 130, 196)',
							      'rgb(138, 201, 38)',
							      'rgb(255, 89, 94)'
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
									suggestedMax : 100.0
								}
							}
						]
					},
					legend : {
						display : false
					}
				};
			
				var canvas, context;
			
				canvas = document.getElementById("canvas2");
				context = canvas.getContext("2d");
				new Chart(context, {
					type : "bar",
					data : data,
					options : options
				});
			});
		</script>
	</jstl:when>
</jstl:choose>

<acme:return/>