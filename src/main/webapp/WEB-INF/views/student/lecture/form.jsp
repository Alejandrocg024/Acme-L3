

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<div style="text-align: center;">
		<h2>
			<acme:message code="student.lecture.form.label.lecture"/>
		</h2>
	</div>
	<acme:input-textbox code="student.lecture.form.label.title" path="title"/>	
	<acme:input-textbox code="student.lecture.form.label.summary" path="summary"/>	
	<acme:input-double code="student.lecture.form.label.estimatedLearningTime" path="estimatedLearningTime"/>	
	<acme:input-textbox code="student.lecture.form.label.body" path="body"/>	
	<acme:input-textbox code="student.lecture.form.label.nature" path="nature"/>
	<acme:input-url code="student.lecture.form.label.furtherInformationLink" path="furtherInformationLink"/>
	
</acme:form>
