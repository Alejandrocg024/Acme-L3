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

		<td><acme:print value="${timeOfTutorialsStats.getLinDev()}"/></td>


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

		<td><acme:print value="${timeOfSessionsStats.getLinDev()}"/></td>


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

<acme:return/>