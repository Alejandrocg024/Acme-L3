
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorial-session.form.label.title" path="title"/>
	<acme:input-textbox code="assistant.tutorial-session.form.label.abstract$" path="abstract$"/>
	<acme:input-moment code="assistant.tutorial-session.form.label.start-period" path="startPeriod"/>
	<acme:input-moment code="assistant.tutorial-session.form.label.end-period" path="endPeriod"/>
	<acme:input-select code="assistant.tutorial-session.form.label.nature" path="nature" choices="${natures}"/>
	<acme:input-url code="assistant.tutorial-session.form.label.further-information-link" path="furtherInformationLink"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.tutorial-session.form.button.update" action="/assistant/tutorial-session/update"/>
			<acme:submit code="assistant.tutorial-session.form.button.delete" action="/assistant/tutorial-session/delete"/>

		</jstl:when>
				<jstl:when test="${_command == 'create' && draftMode == true}">
					<acme:submit code="assistant.tutorial-session.form.button.create" action="/assistant/tutorial-session/create?masterId=${masterId}"/>
		
					</jstl:when>
			
	</jstl:choose>		
</acme:form>
