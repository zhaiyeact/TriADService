;<%--
  Created by IntelliJ IDEA.
  User: zhaiyeact
  Date: 2015/10/2
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>TriAD Query</title>
</head>
<style type="text/css">
    body{text-align:center;}
    div#container{width: 100%;margin: 50px auto;text-align:center;}
    div#cluster{text-align: center;}
    div#result{text-align: center;}
    div#foot{background-color: cadetblue;}
</style>
<body>

<h1 align="center">TriAD Cluster</h1>
<div id="container">
    <div id="cluster">
        <h2 align="center">Cluster Status</h2>
    </div>
    <form:form method="POST" commandName="query" action="/TriadService/query">
        <table>
            <tr>
                <td><form:label path="request">Request</form:label></td>
                <td><form:input path="request" /></td>
                <td colspan="2">
                    <input type="submit" value="Submit"/>
                </td>
            </tr>
        </table>
    </form:form>
    <div id="result">
    <textarea type="text" style="width:80%;overflow-y:visible;height:200px;resize:none;" name="queryResult" >

    </textarea>
    </div>
    <div id="foot">
        Provided by Zhaiye 2015
    </div>
</div>
</body>
</html>

