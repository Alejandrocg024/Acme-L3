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

<acme:form>
	<acme:input-textbox code="assistant.tutorial-session.form.label.title" path="title"/>
	<acme:input-textbox code="assistant.tutorial-session.form.label.abstract$" path="abstract$"/>
	<acme:input-moment code="assistant.tutorial-session.form.label.start-period" path="startPeriod"/>
	<acme:input-moment code="assistant.tutorial-session.form.label.end-period" path="endPeriod"/>
	<acme:input-select code="assistant.tutorial-session.form.label.course" path="nature" choices="${natures}"/>
	<acme:input-url code="assistant.tutorial-session.form.label.further-information-link" path="furtherInformationLink"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.tutorial-session.form.button.update" action="/assistant/tutorial-session/update"/>
			<acme:submit code="assistant.tutorial-session.form.button.delete" action="/assistant/tutorial-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && draftMode == true}">
			<acme:submit code="assistant.tutorial-session.form.button.create" action="/assistant/tutorial-session/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && draftMode == false}">
			<acme:input-checkbox code="assistant.tutorial-session.form.label.confirmation" path="confirmation"/>
			<acme:submit code="assistant.tutorial-session.form.button.create-exceptional" action="/assistant/tutorial-session/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>
