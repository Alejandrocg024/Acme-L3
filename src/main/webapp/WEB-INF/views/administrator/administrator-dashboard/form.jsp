
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

		<td><acme:print value="${peepsRatioWithLinkAndEmail}"/></td>


	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.ratio-bulletin" /></th>
<td>
			<acme:print value="${ratioOfBulletinsByCriticality.get(true)}"/>
	
		</td>

	</tr>
	
	<th scope="row"><acme:message
				code="administrator.dashboard.form.label.ratio-bulletin2" /></th>
<td>
			<acme:print value="${ratioOfBulletinsByCriticality.get(false)}"/>
	
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
	<acme:message code="administrator.dashboard.form.title.EUR"/>
</h2>


<div>
	<canvas id="canvas"></canvas>
</div>
<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"AVERAGE", "MAX", "MIN","LINEAL DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${offersByCurrencyStats.get('EUR').getAverage()}"/>, 
							<jstl:out value="${offersByCurrencyStats.get('EUR').getMax()}"/>, 
							<jstl:out value="${offersByCurrencyStats.get('EUR').getMin()}"/>,
							<jstl:out value="${offersByCurrencyStats.get('EUR').getLinDev()}"/>
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
<h2>
	<acme:message code="administrator.dashboard.form.title.USD"/>
</h2>


<div>
	<canvas id="canvas2"></canvas>
</div>
<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"AVERAGE", "MAX", "MIN","LINEAL DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${offersByCurrencyStats.get('USD').getAverage()}"/>, 
							<jstl:out value="${offersByCurrencyStats.get('USD').getMax()}"/>, 
							<jstl:out value="${offersByCurrencyStats.get('USD').getMin()}"/>,
							<jstl:out value="${offersByCurrencyStats.get('USD').getLinDev()}"/>
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
	
<h2>
	<acme:message code="administrator.dashboard.form.title.GBP"/>
</h2>


<div>
	<canvas id="canvas3"></canvas>
</div>
<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"AVERAGE", "MAX", "MIN","LINEAL DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${offersByCurrencyStats.get('GBP').getAverage()}"/>, 
							<jstl:out value="${offersByCurrencyStats.get('GBP').getMax()}"/>, 
							<jstl:out value="${offersByCurrencyStats.get('GBP').getMin()}"/>,
							<jstl:out value="${offersByCurrencyStats.get('GBP').getLinDev()}"/>
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
	
			canvas = document.getElementById("canvas3");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "bar",
				data : data,
				options : options
			});
		});
	</script>
	
	<h3><acme:message code="administrator.dashboard.form.label.ratio-bulletin"/></h3>
	<div>
		<canvas id="canvas4"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"NON CRITICAL", "CRITICAL"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${ratioOfBulletinsByCriticality.get(false)}"/>, 
							<jstl:out value="${ratioOfBulletinsByCriticality.get(true)}"/>, 
						],
						backgroundColor: [
					      'rgb(40, 180, 99)',
					      'rgb(54, 162, 235)'
					    ]
					}
				]
			};

	
			var canvas, context;
	
			canvas = document.getElementById("canvas4");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "doughnut",
				data : data,
			});
		});
	</script>
	
	<h3><acme:message code="administrator.dashboard.form.label.number-principals"/></h3>
	<div>
		<canvas id="canvas5"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"LECTURERS", "STUDENTS","ASSISTANTS","COMPANYS","AUDITORS"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${principalsByRole.get('Lecturer')}"/>, 
							<jstl:out value="${principalsByRole.get('Student')}"/>, 
							<jstl:out value="${principalsByRole.get('Assistant')}"/>, 
							<jstl:out value="${principalsByRole.get('Company')}"/>, 
							<jstl:out value="${principalsByRole.get('Auditor')}"/>, 
								
						],
						backgroundColor: [
					      'rgb(40, 180, 99)',
					      'rgb(54, 162, 235)',
					      'rgb( 244, 255, 79 )',
					      'rgb( 255, 87, 79)',
					      'rgb(255, 79, 244)'
					    ]
					}
				]
			};

	
			var canvas, context;
	
			canvas = document.getElementById("canvas5");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "doughnut",
				data : data,
			});
		});
	</script>


<acme:return />

