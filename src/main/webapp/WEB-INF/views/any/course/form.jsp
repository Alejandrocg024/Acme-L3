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
	<acme:input-textbox code="any.course.form.label.code" path="code"/>	
	<acme:input-textbox code="any.course.form.label.title" path="title"/>	
	<acme:input-textbox code="any.course.form.label.abstract$" path="abstract$"/>	
	<acme:input-textbox code="any.course.form.label.price" path="price"/>	
	<acme:input-textbox code="any.course.form.label.furtherInformationLink" path="furtherInformationLink"/>	
	<acme:input-textbox code="any.course.form.label.lecturer" path="almaMater"/>	
	<acme:input-textbox code="lecturer.course.form.label.courseType" path="nature"/>
	<acme:input-money code="lecturer.course.form.label.money" path="money" readonly="true"/>
	
	<acme:check-access test="isAuthenticated()">
		<acme:button code="any.practicum.form.button.list" action="/authenticated/practicum/list?masterId=${id}"/>
		<acme:button code="any.audit.form.button.list" action="/authenticated/audit/list?masterId=${id}"/>
		<acme:button code="any.tutorial.form.button.list" action="/authenticated/tutorial/list?masterId=${id}"/>
	</acme:check-access>
</acme:form>
