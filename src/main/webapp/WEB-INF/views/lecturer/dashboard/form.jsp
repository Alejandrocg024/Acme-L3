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

<h2>
	<acme:message code="lecturer.lecturerDashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.average-lecture-learning-time"/>
		</th>
		<td>
			<acme:print value="${averageLectureLearningTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.max-lecture-learning-time"/>
		</th>
		<td>
			<acme:print value="${maxLectureLearningTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.min-lecture-learning-time"/>
		</th>
		<td>
			<acme:print value="${minLectureLearningTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.lecturerDashboard.form.label.lin-dev-lecture-learning-time"/>
		</th>
		<td>
			<acme:print value="${linDevOfLecturesLearningTime}"/>
		</td>
	</tr>		
</table>

<div>
	<canvas id="canvas"></canvas>
</div>


<acme:return/>

