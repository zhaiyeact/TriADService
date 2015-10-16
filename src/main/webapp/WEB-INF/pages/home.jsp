<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: zhaiyeact
  Date: 2015/10/16
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
  <title>TriAD Service</title>
  <link href="css/style.css" rel="stylesheet"/>
</head>
<body>
<!-- Header -->
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
        <li><a href="/TriadService/cluster" class="active"><span>Cluster</span></a></li>
        <li><a href="/TriadService/query"><span>Query Task</span></a></li>
      </ul>
    </div>
    <!-- End Main Nav -->
  </div>
</div>
<!-- End Header -->

<!-- Container -->
<div id="container">
  <div class="shell">
    <!-- Small Nav -->
    <!--
    <div class="small-nav">
      <a href="#">Dashboard</a>
      <span>&gt;</span>
      Current Articles
    </div>
    -->
    <!-- End Small Nav -->

    <!-- Message OK -->
    <!--
    <div class="msg msg-ok">
      <p><strong>Your file was uploaded succesifully!</strong></p>
      <a href="#" class="close">close</a>
    </div>
    -->
    <!-- End Message OK -->

    <!-- Message Error -->
    <!--
    <div class="msg msg-error">
      <p><strong>You must select a file to upload first!</strong></p>
      <a href="#" class="close">close</a>
    </div>
    -->
    <!-- End Message Error -->
    <br />
    <!-- Main -->
    <div id="main">
      <div class="cl">&nbsp;</div>

      <!-- Content -->
      <div id="content">

        <!-- Box -->
        <div class="box">
          <!-- Box Head -->
          <div class="box-head">
            <h2 class="left">Running Cluster</h2>
            <div class="right">
              <label>search machines</label>
              <input type="text" class="field small-field" />
              <input type="submit" class="button" value="search" />
            </div>
          </div>
          <!-- End Box Head -->

          <!-- Table -->
          <div class="table">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <th>Ip</th>
                <th>HostName</th>
                <th>Status</th>
                <th>Identity</th>
              </tr>
              <tr>
                <td><h3><a href="#">192.168.7.1</a></h3></td>
                <td><a href="#">test01</a></td>
                <td>Running</td>
                <td>Master</td>
              </tr>
              <tr class="odd">
                <td><h3><a href="#">192.168.7.3</a></h3></td>
                <td><a href="#">test03</a></td>
                <td>Running</td>
                <td>Slave</td>
              </tr>
            </table>


            <!-- Pagging -->
            <div class="pagging">
              <div class="left">Showing 1-12 of 44</div>
              <div class="right">
                <a href="#">Previous</a>
                <a href="#">1</a>
                <a href="#">2</a>
                <span>...</span>
                <a href="#">Next</a>
                <a href="#">View all</a>
              </div>
            </div>
            <!-- End Pagging -->

          </div>
          <!-- Table -->

        </div>
        <!-- End Box -->
      </div>
      <!-- End Content -->



      <div class="cl">&nbsp;</div>
    </div>
    <!-- Main -->
  </div>
</div>
<!-- End Container -->

<!-- Footer -->
<div id="footer">
  <div class="shell">
    <span class="left">&copy; provided by Zhaiye</span>
  </div>
</div>
<!-- End Footer -->

</body>
</html>
