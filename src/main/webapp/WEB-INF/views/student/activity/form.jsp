<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<acme:form>
	<div style="text-align: center;">
		<h2>
			<acme:message code="student.enrolment.form.label.activity"/>
		</h2>
	</div>
	<acme:input-textbox code="student.activity.form.label.title" path="title"/>
	<acme:input-textbox code="student.activity.form.label.abstract$" path="abstract$"/>
	<acme:input-moment code="student.activity.form.label.startPeriod" path="startPeriod"/>
	<acme:input-moment code="student.activity.form.label.endPeriod" path="endPeriod"/>
	<acme:input-select code="student.activity.form.label.nature" path="nature" choices="${natures}"/>
	<acme:input-url code="student.activity.form.label.furtherInformationLink" path="furtherInformationLink"/>
	
	<jstl:choose>	 	
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
			<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.activity.form.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>
		</jstl:when>			
	</jstl:choose>

</acme:form>
