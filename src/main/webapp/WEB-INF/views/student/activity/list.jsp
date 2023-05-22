<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="student.activity.list.label.title" path="title" />
	<acme:list-column code="student.activity.list.label.startPeriod" path="startPeriod"/>
	<acme:list-column code="student.activity.list.label.endPeriod" path="endPeriod"/>
</acme:list>

<acme:button code="student.activity.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>


