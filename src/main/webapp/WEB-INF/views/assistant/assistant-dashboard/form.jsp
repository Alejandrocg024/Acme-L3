<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="assistant.assistant-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.assistant-dashboard.form.label.numOfTutorials.theory"/>
		</th>
		<td>
		<acme:print value='${numOfTutorialsByType.get("THEORETICAL")}'/>		
			
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.assistant-dashboard.form.label.numOfTutorials.hands_on"/>
		</th>
		<td>
		<jstl:out value='${numOfTutorialsByType.get("HANDS_ON")}'>
			</jstl:out>

		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.assistant-dashboard.form.label.numOfTutorials.balanced"/>
		</th>
		<td>
		<jstl:out value='${numOfTutorialsByType.get("BALANCED")}'>
			</jstl:out>

		</td>
	</tr>
	<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstuts.linDev" /></th>

		<td><acme:print value="${timeOfTutorialsStats.getDev()}"/></td>


	</tr>
	<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstuts.average" /></th>

		<td><acme:print value="${timeOfTutorialsStats.getAverage()}"/></td>


	</tr>
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstuts.max" /></th>

		<td><acme:print value="${timeOfTutorialsStats.getMax()}"/></td>


	</tr>
			<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstuts.min" /></th>

		<td><acme:print value="${timeOfTutorialsStats.getMin()}"/></td>


	</tr>
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstutssesions.lindev" /></th>

		<td><acme:print value="${timeOfSessionsStats.getDev()}"/></td>


	</tr>
	
	
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstutssesions.average" /></th>

		<td><acme:print value="${timeOfSessionsStats.getAverage()}"/></td>


	</tr>
	
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstutssesions.max" /></th>

		<td><acme:print value="${timeOfSessionsStats.getMax()}"/></td>



	
	</tr>
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstutssesions.min" /></th>

		<td><acme:print value="${timeOfSessionsStats.getMin()}"/></td>


	</tr>
	

</table>
<jstl:choose>
<jstl:when test="${timeOfSessionsStats.getMax()>0.0}">
						
		
	<h3><acme:message code="assistant.dashboard.form.label.sessions.information"/></h3>
	<div>
		<canvas id="canvas"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"AVERAGE", "MAX", "MIN","STANDAR DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${timeOfSessionsStats.getAverage()}"/>, 
							<jstl:out value="${timeOfSessionsStats.getMax()}"/>, 
							<jstl:out value="${timeOfSessionsStats.getMin()}"/>,
							<jstl:out value="${timeOfSessionsStats.getDev()}"/>
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
								suggestedMin : 0.0
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
<jstl:choose>
<jstl:when test="${timeOfTutorialsStats.getMax()>0.0}">

	<h3><acme:message code="assistant.dashboard.form.label.tutorials.information"/></h3>
	<div>
		<canvas id="canvas2"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"AVERAGE", "MAX", "MIN","DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${timeOfTutorialsStats.getAverage()}"/>, 
							<jstl:out value="${timeOfTutorialsStats.getMax()}"/>, 
							<jstl:out value="${timeOfTutorialsStats.getMin()}"/>,
							<jstl:out value="${timeOfTutorialsStats.getDev()}"/>
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
									suggestedMin : 0.0
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
<jstl:choose>
<jstl:when test="${numOfTutorialsByType.get('THEORETICAL') > 0 || numOfTutorialsByType.get('HANDS_ON') > 0 || numOfTutorialsByType.get('BALANCED') > 0}">

	<h3><acme:message code="assistant.dashboard.form.label.tutorials.type.information"/></h3>
	<div>
		<canvas id="canvas3"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"THEORETICAL", "HANDS-ON", "BALANCED"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${numOfTutorialsByType.get('THEORETICAL')}"/>, 
							<jstl:out value="${numOfTutorialsByType.get('HANDS_ON')}"/>, 
							<jstl:out value="${numOfTutorialsByType.get('BALANCED')}"/>, 
						],
						backgroundColor: [
					      'rgb(40, 180, 99)',
					      'rgb(54, 162, 235)',
					      'rgb(255, 205, 86)'
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
<acme:return/>