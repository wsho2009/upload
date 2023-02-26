<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="konyurireki.KonyuBean" %>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><%= request.getAttribute("title") %></title>
  <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
  <script type="text/javascript" src="js/jquery-ui.min.js"></script>
  <script type="text/javascript" src="js/jquery.tablesorter.combined.min.js"></script>
  <script src="js/extras/jquery.tablesorter.pager.min.js"></script>
  <script type="text/javascript" src="js/widgets/widget-scroller.min.js"></script>
  <%--<script type="text/javascript" src="js/jquery.ui.datepicker-ja.min.js"></script>--%>
  
  <link rel="stylesheet" href="css/theme.blue.css">
  <link rel="stylesheet" href="css/jquery-ui.css">
  <script type="text/javascript">
    $(document).ready(function() {
    	$("table").tablesorter({
			theme: 'blue',
			widthFixed: true,
			//zebra:1行ごとに色を変える
			//columns:選択した列の色を変える
			//filter:列にフィルタ機能を追加する
			//resizable:列のリサイズをする
			//stickyHeaders:スクロールの際にヘッダを固定する	????
			//scroller:ヘッダを固定
            //widgets: ['zebra', 'columns', 'filter', 'pager', 'resizable', 'stickyHeaders'],
			widgets: ['zebra', 'columns', 'resizable', 'pager', 'sticyHeaders', 'scroller'],
	        widgetOptions: {
	            // テーブルの髙さの指定
	            scroller_height : 500
	        }
		});
		$("table").tablesorterPager({
			container: $(".pager"),
		});
	});
  	$(function() {
  	  	var userId = "<%= request.getAttribute("userId") %>";
  	  	console.log('userId='+userId);
  	  	var userName = "<%= request.getAttribute("userName") %>";
  	  	console.log('userName='+userName);
		$(".datepicker").datepicker();
/*
   	    $.post('fax', 'type=list&id='+userId)
        .done(function(data) {
			console.log(data);
  	  	  	//既存テーブルクリア
  	  	  	$('#listtable').empty();
  	  	  	var html = '<table id="table" class="tablesorter">'
  	  	  	$('#listtable').append(html);
  	  	  	//テーブルヘッダ
  	  	  	var thead = '<thead><tr align="center"><th>No</th><th>日付</th><th>購入先</th><th>種別</th><th>品名</th><th>価格</th><th>送料</th><th>合計</th></tr></thead>'
  	  	  	$('#listtable').append(thead);
			$.each(data, function(i, json){
				var tbody = $('<tbody />');
				var tr = $('<tr />');
				var No = $('<td />').text(json.no);
				var Hizuke = $('<td />').text(json.hizuke);
				var Konnyusaki = $('<td />').text(json.konnyusaki);
				var Syubetsu = $('<td />').text(json.syubetsu);
				var Hinmei = $('<td />').text(json.hinmei);
				var Kakaku = $('<td />').text(json.kakaku);
				var Soryo = $('<td />').text(json.soryo);
				var KakakuKie = $('<td />').text(json.kakakuKie);
				tr.append(No);
				tr.append(Hizuke);
				tr.append(Konnyusaki);
				tr.append(Syubetsu);
				tr.append(Hinmei);
				tr.append(Kakaku);
				tr.append(Soryo);
				tr.append(KakakuKie);
				tbody.append(tr);
				$('#listtable').append(tbody);
			});
            
        }).fail(function () {
            // 通信失敗時のコールバック
            alert("読み込み失敗");
          //}).always(function (result) {
          //  // 常に実行する処理
        });*/
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
  <form action=fax?type=list method="post">
     購入先: <input type="text" name="konyusaki">
     種別: <input type="text" name="syubetsu">
     日付  <input type="date" name="date_fr">～<input type="date" name="date_to"><br>
     <button type="submit">検索</button>
     <button type="">クリア</button><br>
  </form>
  <div id="pager" class="pager">
    <button type="button" class="first"><<</button>
    <button type="button" class="prev"><</button>
    <span class="pagedisplay"></span>
    <button type="button" class="next">></button>
    <button type="button" class="last">>></button>
    <select class="pagesize" title="Select page size">
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="30">30</option>
        <option value="40">40</option>
    </select>
    <select class="gotoPage" title="Select page number"></select>
  </div>
<!--     <div id='listtable'></div> -->
  <table id="table" class="tablesorter">	 <!--tablesorter-blue-->
  	<thead><tr align=center>
   		<th style="font-size: 11pt;">No</th>
  		<th style="font-size: 11pt;">日付</th>
  		<th style="font-size: 11pt;">購入先</th>
  		<th style="font-size: 11pt;">種別</th>
  		<th style="font-size: 11pt;">品名</th>
  		<th style="font-size: 11pt;">価格</th>
  		<th style="font-size: 11pt;">送料</th>
  		<th style="font-size: 11pt;">合計</th>
  	</tr></thead>
    	<tbody>
   		<% 
   		List<KonyuBean> list = (List<KonyuBean>)request.getAttribute("list");
   		for (int i=0; i<list.size(); i++) {
   			KonyuBean konyu = (KonyuBean)list.get(i);
   		%>
 		<tr>
  			<td><%= konyu.getNo() %></td>
  			<td><%= konyu.getHizuke() %></td>
  			<td><%= konyu.getKonnyusaki() %></td>
  			<td><%= konyu.getSyubetsu() %></td>
  			<td><%= konyu.getHinmei() %></td>
  			<td><%= konyu.getKakaku() %></td>
  			<td><%= konyu.getSoryo() %></td>
  			<td><%= konyu.getKakakuKie() %></td>
  		</tr>
  		<%
  		}
   		%>
  	</tbody>
  </table>
</body>  
</html>
