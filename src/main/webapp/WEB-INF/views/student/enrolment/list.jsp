<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.enrolment.list.label.code" path="code" />
	<acme:list-column code="student.enrolment.list.label.motivation" path="motivation"/>
	<acme:list-column code="student.enrolment.list.label.course-code" path="course"/>
</acme:list>

<acme:button code="student.enrolment.register" action="/student/enrolment/create"/>


