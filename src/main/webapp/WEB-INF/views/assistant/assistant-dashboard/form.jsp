<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="assistant.assistant-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.assistant-dashboard.form.label.numOfTutorials"/>
		</th>
		<td>
		<jstl:forEach items="${numOfTutorialsByType}" var="tutorials">
			<jstl:out value="${tutorials}"></jstl:out>
		</jstl:forEach>
		</td>
	</tr>
	<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstuts" /></th>

		<td><jstl:out value="${timeOfTutorialsStats}"></jstl:out></td>


	</tr>
		<tr >
		<th scope="row"><acme:message
				code="assistant.assistant-dashboard.form.label.statstutssesions" /></th>

		<td><jstl:out value="${timeOfSessionsStats}"></jstl:out></td>


	</tr>
</table>

<acme:return/>