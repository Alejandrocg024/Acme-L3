

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly = "true">
	<acme:input-textbox code="authenticated.tutorial.form.label.code" path="code"/>	
	<acme:input-textbox code="authenticated.tutorial.form.label.title" path="title"/>	
	<acme:input-textbox code="authenticated.tutorial.form.label.abstract$" path="abstract$"/>	
	<acme:input-textbox code="authenticated.tutorial.form.label.goal" path="goal" />
	<acme:input-textbox code="authenticated.tutorial.form.label.assistant" path="assistant" />
	<acme:input-double code="authenticated.tutorial.form.label.estimatedTotalTime" path="estimatedTotalTime" />
		
</acme:form>