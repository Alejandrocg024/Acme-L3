<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<div style="text-align: center;">
			<h2>
				<acme:message code="student.studentDashboard.form.label.numberOfActivitiesByNature"/>
			</h2>
		</div>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.theoretical-activities"/>
			
		</th>
		<td>
			<acme:print value="${numberOfTheoreticalActivities}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.balanced-activities"/>
		</th>
		<td>
			<acme:print value="${numberOfBalancedActivities}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.hands-on-activities"/>
		</th>
		<td>
			<acme:print value="${numberOfHandsOnActivities}"/>
		</td>
	</tr>	
</table>
<jstl:choose>
<jstl:when test="${numberOfTheoreticalActivities != 0 || numberOfBalancedActivities != 0 || numberOfHandsOnActivities != 0}">

	<div>
		<canvas id="canvas3"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"THEORETICAL", "BALANCED","HANDS-ON"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${numberOfTheoreticalActivities}"/>, 
							<jstl:out value="${numberOfBalancedActivities}"/>, 
							<jstl:out value="${numberOfHandsOnActivities}"/>, 
						],
						backgroundColor: [
					      'rgb(40, 180, 99)',
					      'rgb(54, 162, 235)'
					    ]
					}
				]
			};

	
			var canvas, context;
	
			canvas = document.getElementById("canvas3");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "doughnut",
				data : data,
			});
		});
	</script>
</jstl:when>
</jstl:choose>
</br>
</br>
<table class="table table-sm">
	<tr>
		<div style="text-align: center;">
			<h2>
				<acme:message code="student.studentDashboard.form.label.periods-of-activities"/>
			</h2>
		</div>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.min-periods-of-activities"/>
		</th>
		<td>
			<acme:print value="${periodsOfActivitiesStats.getMin()} h."/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.average-periods-of-activities"/>
		</th>
		<td>
			<acme:print value="${periodsOfActivitiesStats.getAverage()} h."/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.max-periods-of-activities"/>
		</th>
		<td>
			<acme:print value="${periodsOfActivitiesStats.getMax()} h."/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.lin-dev-periods-of-activities"/>
		</th>
		<td>
			<acme:print value="${periodsOfActivitiesStats.getLinDev()} h."/>
		</td>
	</tr>
</table>
<jstl:choose>
<jstl:when test="${periodsOfActivitiesStats.getMax()>0.0}">
						

	<div>
		<canvas id="canvas"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"MIN","AVERAGE", "MAX", "LINEAL DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${periodsOfActivitiesStats.getMin()}"/>,
							<jstl:out value="${periodsOfActivitiesStats.getAverage()}"/>, 
							<jstl:out value="${periodsOfActivitiesStats.getMax()}"/>, 
							<jstl:out value="${periodsOfActivitiesStats.getLinDev()}"/>
						],
						backgroundColor: [
						      'rgb(40, 180, 99)',
					    	  'rgb(54, 162, 235)',
					    	  'rgb(255, 205, 86)',
					      	  'rgb(230, 170, 243)'
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
								suggestedMax : 1600.0
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
</br></br></br>
<table class="table table-sm">
	<tr>
		<div style="text-align: center;">
			<h2>
				<acme:message code="student.studentDashboard.form.label.times-of-enrolled-courses"/>
			</h2>
		</div>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.min-times-of-enrolled-courses"/>
		</th>
		<td>
			<acme:print value="${timesOfEnrolledCoursesStats.getMin()} h."/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.times-of-enrolled-courses"/>
		</th>
		<td>
			<acme:print value="${timesOfEnrolledCoursesStats.getAverage()} h."/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.max-times-of-enrolled-courses"/>
		</th>
		<td>
			<acme:print value="${timesOfEnrolledCoursesStats.getMax()} h."/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.studentDashboard.form.label.lin-dev-times-of-enrolled-courses"/>
		</th>
		<td>
			<acme:print value="${timesOfEnrolledCoursesStats.getLinDev()} h."/>
		</td>
	</tr>
</table>
<jstl:choose>
<jstl:when test="${timesOfEnrolledCoursesStats.getMax()>0.0}">
	<div>
		<canvas id="canvas2"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
					"MIN", "AVERAGE", "MAX" ,"LINEAL DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${timesOfEnrolledCoursesStats.getMin()}"/>,
							<jstl:out value="${timesOfEnrolledCoursesStats.getAverage()}"/>, 
							<jstl:out value="${timesOfEnrolledCoursesStats.getMax()}"/>, 
							<jstl:out value="${timesOfEnrolledCoursesStats.getLinDev()}"/>
						],
						backgroundColor: [
						      'rgb(40, 180, 99)',
						      'rgb(54, 162, 235)',
						      'rgb(255, 205, 86)',
						      'rgb(230, 170, 243)'
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
									suggestedMax : 2200.0
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

