
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.displayPeriodBegin" path="displayPeriodBegin" />
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" />



</acme:list>

<acme:button code="administrator.banner.create" action="/administrator/banner/create"/>

