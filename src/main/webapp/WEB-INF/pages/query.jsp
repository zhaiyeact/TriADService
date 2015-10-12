<%--
  Created by IntelliJ IDEA.
  User: zhaiyeact
  Date: 2015/10/2
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TriAD Query</title>
</head>
<style type="text/css">
    body{text-align:center;}
    div#container{width: 100%;margin: 50px auto;text-align:center;}
    div#cluster{text-align: center;}
    div#result{text-align: center;}
    div#query{background-color: cadetblue;text-align:center;margin: 0 auto;}
    div#foot{background-color: cadetblue;}
</style>
<body>
<h1 align="center">TriAD Cluster</h1>
<div id="container">
    <div id="cluster">
        <h2 align="center">Cluster Status</h2>
    </div>
    <div id="query" >
        <table align="center" style="width:100%;">
            <tr >
                <td style="text-align:right;">
                    Query:
                </td>

                <td style="width:80%;">
      <textarea  type="text" style="width:100%;overflow-y:visible;height:200px;resize:none;" name="queryText" onclick="this.value=''">
Edit your query here
      </textarea>
                </td>
                <td style="text-align:left;">
                    <input type="submit" name="querySubmit"/>
                </td>
            </tr>
        </table>
    </div>
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

