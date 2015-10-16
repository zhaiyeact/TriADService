<%--
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
    <link href="css/style.css" rel="stylesheet"/>
</head>
<body>
<div id="header">
    <div class="shell">
        <!-- Logo + Top Nav -->
        <div id="top">
            <h1><a href="#">TriAD</a></h1>
        </div>
        <!-- End Logo + Top Nav -->

        <!-- Main Nav -->
        <div id="navigation">
            <ul>
                <li><a href="/TriadService/cluster"><span>Cluster</span></a></li>
                <li><a href="/TriadService/query" class="active"><span>Query Task</span></a></li>
            </ul>
        </div>
        <!-- End Main Nav -->
    </div>
</div>
<div id="container">
    <div class="shell">
        <div id="main">
            <div class="cl">&nbsp;</div>
            <div id="content">
                <div class="box">
                    <div class="box-head">
                        <h2 class="left">Query Execution</h2>
                    </div>
                    <form:form action="/TriadService/queryexecute" method="post" commandName="query">

                        <!-- Form -->
                        <div class="form">
                            <p>
                                <label>Master <span>(Required Field)</span></label>
                                <input type="text" class="field size1" />
                            </p>

                            <p>
                                <label>SPARQL Query <span>(Required Field)</span></label>
                                <textarea class="field size1" rows="10" cols="30" name="request"></textarea>
                            </p>

                        </div>
                        <!-- End Form -->

                        <!-- Form Buttons -->
                        <div class="buttons">
                            <input type="submit" class="button" value="submit" />
                        </div>
                        <!-- End Form Buttons -->
                    </form:form>
                    <!-- Result div-->
                    <div class="form">
                        <p>
                            <label>Result</label>
                            <textarea class="field size1" rows="10" cols="30" name="queryResult" contenteditable="false"></textarea>
                        </p>
                    </div>
                </div>
            </div>
            <div class="cl">&nbsp;</div>
        </div>
    </div>
</div>
<!-- Footer -->
<div id="footer">
    <div class="shell">
        <span class="left">&copy; provided by Zhaiye</span>
    </div>
</div>
<!-- End Footer -->
</body>
</html>

