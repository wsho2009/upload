<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fax.faxBean" %>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><%= request.getAttribute("title") %></title>
  <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.31.0/js/jquery.tablesorter.min.js"></script>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>
  <script type="text/javascript" src="https://bossanova.uk/jspreadsheet/v3/jexcel.js"></script>
  <script type="text/javascript" src="https://jsuites.net/v3/jsuites.js"></script>
  
  <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
  <link rel="stylesheet" href="https://bossanova.uk/jspreadsheet/v3/jexcel.css" type="text/css" />
  <link rel="stylesheet" href="https://jsuites.net/v3/jsuites.css" type="text/css" />
  <script type="text/javascript">
    $(document).ready(function() {
		$("table").tablesorter({
			theme: 'blue',
			widthFixed: true,
			widgets: ['zebra', 'columns', 'resizable', 'sticyHeaders'],
		});
	});
  	$(function() {
		$(".datepicker").datepicker();
	});
   </script>
  <style>
    html,body {
        font-family:sans-serif;
    }
    header {
    	background: #333;
    	color: #fff;
    	padding: .5rem;
		font-size: 1.5rem;
		text-align: left;
    }
    #aa {
    	color: #fff;
		text-decoration: none;
		white-space: nowrap;
    	border: 1px solid blue;
    	font-weight: normal;
    }
    li {
		margin: 2rem 0;
    }
	/*ボタン*/
    .btn-menu {
    	border: 1px solid #999;
		background: transparent;
    	color: #fff;
    	padding: .5rem 1rem;
		cursor: pointer;
		line-height: 1;
    }
	/*メニュー*/
	nav {
		background: #0bd;
		position: absolute;
		z-index: 1;
		top: 3.125rem;
		left: 0;
		overflow-x: hidden;
		text-align: center;
		width: 0;
		transition: .1s;
	}
	/*メニューを開いたとき*/
	nav.open-menu {
		width: 20%;
	}
	chuui {
		font-weight: bold;	/* 太字 */
		color: #FF0000; 	/* 赤字 */
	}
  </style>
</head>
<body>
  <header>
  	<button class="btn-menu" id="btn-menu">メニュー</button>
  	<nav>
  		<ul>
  			<li><a href="/ocr/site1" id="aa">サイト1</a>
  			<li><a href="/fax/site2" id="aa">サイト2</a>
  			<li><a href="/fax/site3" id="aa">サイト3</a>
  		</ul>
  	</nav>
  	<label><%= request.getAttribute("title") %></label>
  </header>
  <script type="text/javascript">
  	const btn = document.querySelector('.btn-menu');
  	const nav = document.querySelector('nav');

  	btn.addEventListener('click', btnClick, false);
  	function btnClick() {
  	  	nav.classList.toggle('open-menu');
  	  	if (btn.innerHTML == 'メニュー') {
  	  	  	btn.innerHTML = '閉じる';
  	  	} else {
  	  	  	btn.innerHTML = 'メニュー';
  	  	}
  	};
  </script>
  <form action=<%= request.getAttribute("path") %> method="post">
     Form: <input type="text" name="form">
     Date:  <input type="date" name="form">
     <button type="submit">Find</button><br>
     <chuui>IEサポート終了に伴い...</chuui>
  </form>
  <table id="table" class="tablesorter">
  	<thead><tr align=center>
<!--   		<th style="font-size: 11pt;">No</th>
  		<th style="font-size: 11pt;">FaxNo</th>
  		<th style="font-size: 11pt;">Date</th>
  		<th style="font-size: 11pt;">File</th>
  		<th style="font-size: 11pt;">FAX</th>
  		<th style="font-size: 11pt;">OCR</th> -->
  		<th style="font-size: 11pt;">ID</th>
  		<th style="font-size: 11pt;">TODO</th>
  		<th style="font-size: 11pt;">TIMELIMIT</th>
  	</tr></thead>
   	<tbody>
   		<% 
   		List<faxBean> list = (List<faxBean>)request.getAttribute("list");
   		for (int i=0; i<list.size(); i++) {
   			faxBean fax = (faxBean)list.get(i);
   		%>
 		<tr>
  			<td><%= fax.getId() %></td>
  			<td><%= fax.getTodo() %></td>
  			<td><%= fax.getTimeLimit() %></td>
  		</tr>
  		<%
  		}
   		%>
  	</tbody>

  </table>
</body>  
</html>
