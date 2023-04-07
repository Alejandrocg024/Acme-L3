<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="auditor.auditorDashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.average-num-auditing-records"/>
		</th>
		<td>
			<acme:print value="${auditStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.max-num-auditing-records"/>
		</th>
		<td>
			<acme:print value="${auditStats.getMax()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.min-num-auditing-records"/>
		</th>
		<td>
			<acme:print value="${auditStats.getMin()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.lin-dev-num-auditing-records"/>
		</th>
		<td>
			<acme:print value="${auditStats.getLinDev()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.average-time-period"/>
		</th>
		<td>
			<acme:print value="${periodStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.max-time-period"/>
		</th>
		<td>
			<acme:print value="${periodStats.getMax()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.min-time-period"/>
		</th>
		<td>
			<acme:print value="${periodStats.getMin()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.lin-dev-time-period"/>
		</th>
		<td>
			<acme:print value="${periodStats.getLinDev()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.theoretical-audits"/>
		</th>
		<td>
			<acme:print value="${numOfAuditsByType.get('THEORETICAL')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditorDashboard.form.label.hands-on-audits"/>
		</th>
		<td>
			<acme:print value="${numOfAuditsByType.get('HANDS_ON')}"/>
		</td>
	</tr>	
</table>

<jstl:choose>
<jstl:when test="${auditStats.getMax()>0.0}">
						
		
	<h3><acme:message code="auditor.auditorDashboard.form.label.audits.information"/></h3>
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
							<jstl:out value="${auditStats.getAverage()}"/>, 
							<jstl:out value="${auditStats.getMax()}"/>, 
							<jstl:out value="${auditStats.getMin()}"/>,
							<jstl:out value="${auditStats.getLinDev()}"/>
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

</jstl:when>
</jstl:choose>

<jstl:choose>
<jstl:when test="${periodStats.getMax()>0.0}">

	<h3><acme:message code="auditor.auditorDashboard.form.label.periods.information"/></h3>
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
							<jstl:out value="${periodStats.getAverage()}"/>, 
							<jstl:out value="${periodStats.getMax()}"/>, 
							<jstl:out value="${periodStats.getMin()}"/>,
							<jstl:out value="${periodStats.getLinDev()}"/>
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

	
			var canvas, context;
			canvas = document.getElementById("canvas2");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "bar",
				data : data,
			});
		});
	</script>

</jstl:when>
</jstl:choose>

<jstl:choose>
<jstl:when test="${numOfAuditsByType.get('THEORETICAL') != 0 || numOfAuditsByType.get('HANDS_ON') != 0}">

	<h3><acme:message code="auditor.auditorDashboard.form.label.audits.type.information"/></h3>
	<div>
		<canvas id="canvas3"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"THEORETICAL", "HANDS-ON"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${numOfAuditsByType.get('THEORETICAL')}"/>, 
							<jstl:out value="${numOfAuditsByType.get('HANDS_ON')}"/>, 
							
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


<acme:return/>

