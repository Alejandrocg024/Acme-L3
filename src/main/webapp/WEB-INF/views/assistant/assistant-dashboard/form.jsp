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
		<jstl:out value="${numOfTutorialsByType.get(Nature.THEORETICAL)}">		
			</jstl:out>
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

		<td><jstl:out value="${timeOfTutorialsStats.getLinDev()}"></jstl:out></td>


	</tr>
	<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstuts.average" /></th>

		<td><jstl:out value="${timeOfTutorialsStats.getAverage()}"></jstl:out></td>


	</tr>
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstuts.max" /></th>

		<td><jstl:out value="${timeOfTutorialsStats.getMax()}"></jstl:out></td>


	</tr>
			<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstuts.min" /></th>

		<td><jstl:out value="${timeOfTutorialsStats.getMin()}"></jstl:out></td>


	</tr>
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstutssesions.lindev" /></th>

		<td><jstl:out value="${timeOfSessionsStats.getLinDev()}"></jstl:out></td>


	</tr>
	
	</tr>
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstutssesions.average" /></th>

		<td><jstl:out value="${timeOfSessionsStats.getAverage()}"></jstl:out></td>


	</tr>
	
	</tr>
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstutssesions.max" /></th>

		<td><jstl:out value="${timeOfSessionsStats.getMax()}"></jstl:out></td>



	
	</tr>
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstutssesions.min" /></th>

		<td><jstl:out value="${timeOfSessionsStats.getMin()}"></jstl:out></td>


	</tr>
	

</table>

<acme:return/>