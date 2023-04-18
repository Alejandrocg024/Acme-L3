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
	<acme:message code="administrator.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-principals"/>
		</th>
		
		<jstl:forEach items="${principalsByRole}" var="principal">
		<td>
		<jstl:out value="${principal}"></jstl:out>
		</td>
		</jstl:forEach>
		
	</tr>
		<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.ratio-peeps"/>
		</th>
	
		<td>
		<jstl:out value="${peepsRatioWithLinkAndEmail}"></jstl:out>
		</td>
		
		
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.ratio-bulletin"/>
		</th>
		
		<jstl:forEach items="${ratioOfBulletinsByCriticality}" var="bulletin">
		<td>
		<jstl:out value="${bulletin}"></jstl:out>
		</td>
		</jstl:forEach>
		
	</tr>
	
	<tr>
	<jstl:forEach items="${currentsOffersStats}" var="offer">
	
	<th>
	<acme:message code="${offer.key}"/>
	</th>
		<td>
		<jstl:out value="${offer.value.toString()}">
		</jstl:out>
		</td>
	
	</jstl:forEach>
	</tr>
</table>
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

<acme:return/>
