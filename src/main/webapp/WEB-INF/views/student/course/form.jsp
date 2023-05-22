
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<div style="text-align: center;">
		<h2>
			<acme:message code="student.course.form.label.course"/>
		</h2>
	</div>
	<acme:input-textbox code="student.course.form.label.code" path="code"/>	
	<acme:input-textbox code="student.course.form.label.title" path="title"/>	
	<acme:input-textbox code="student.course.form.label.abstract$" path="abstract$"/>	
	<acme:input-money code="student.course.form.label.price" path="price"/>	
	<acme:input-url code="student.course.form.label.furtherInformationLink" path="furtherInformationLink"/>	
	<acme:input-textbox code="student.course.form.label.lecturer" path="almaMater"/>	
	<acme:input-textbox code="student.course.form.label.courseType" path="nature"/>
	<acme:input-money code="student.course.form.label.money" path="money" readonly="true"/>
	<acme:button code="student.course.button.lectures" action="/student/lecture/list?courseId=${id}"/>
			
</acme:form>
