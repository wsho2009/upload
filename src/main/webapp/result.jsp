<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="fax.PoRirekiBean"%>
<!DOCTYPE html>
<html>
<head>
  <title><%= request.getAttribute("title") %></title>
  <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://bossanova.uk/jspreadsheet/v4/jexcel.js"></script>
  <script src="https://jsuites.net/v4/jsuites.js"></script>
  <link rel="stylesheet" href="https://bossanova.uk/jspreadsheet/v4/jexcel.css" type="text/css" />
  <link rel="stylesheet" href="https://jsuites.net/v4/jsuites.css" type="text/css" />
  <script>
  	const unitId = <%= request.getAttribute("unitId") %>;
  	const unitStatus = "<%= request.getAttribute("unitStatus") %>";
  	const url = "<%= request.getAttribute("url") %>";
  	$(function() {
		console.log("unitId: " + unitId)
		console.log("unitStatus: " + unitStatus)
		console.log("url: " + url)
		getData();
		if (unitStatus == 'COMPLETE' || unitStatus == '') {
			document.getElementById("completeButton").style.display = "noen";
			var button = document.getElementById("completeButton");
			button.disabled = false
		}
		$('#completeButton').click(function() {
			console.log('complete unitId:' + unitId)
			$.post('resultServlet', 'type=complete&unitId=' + unitId)
			.done(function( data ) {
				windows.close();
			});
		});
		$('#refreshButton').click(function() {
			getData();
		});
		function getData() {
  	  	  	$('#spreadsheet').empty();
		    $.post('resultServlet', 'type=rireki&unitId='+unitId)
	        .done(function(data) {
	          	// 通信成功時のコールバック
	          	console.log(data);
	            jspreadsheet(document.getElementById('spreadsheet'), {
	                //data: dataArray,
	                data: data.datalist,
	                columns: data.columns,
	                text:{
	                    // noRecordsFound:'Nenhum registro encontrado',
	                    // showingPage:'Mostrando página {0} de {1} entradas',
	                    show:'Show',
	                    entries:'entradas',
	                    insertANewColumnBefore:'列追加（左）',
	                    insertANewColumnAfter:'列追加（右）',
	                    deleteSelectedColumns:'列削除',
	                    renameThisColumn:'列削除',
	                    orderAscending:'昇順ソート',
	                    orderDescending:'降順ソート',
	                    insertANewRowBefore:'行追加（上）',
	                    insertANewRowAfter:'行追加（下）',
	                    deleteSelectedRows:'行削除',
	                    copy:'コピー ...',
	                    paste:'貼付け ...',
	                    saveAs:'保存 ...',
	                    about:'情報',
	                }   
	            });
	          	/*$.each(data, function(i, val) {
	          	});*/
	        }).fail(function () {
	          // 通信失敗時のコールバック
	          alert("読み込み失敗");
	        //}).always(function (result) {
	        //  // 常に実行する処理
	        });
		};
  	});
   </script>
   <style>
    html,body {
        font-family:sans-serif;
    }
    #completeButton, #refreshButton, #closeButton {
    	font-size: 105%;
    	cursor: pointer;
    } 
   </style>
</head>
<body>
	<h1><%= request.getAttribute("title") %></h1>
	<input type="button" id="completeButton" value="確認完了"> 
	<input type="button" id="refreshButton" value="更新">
	<input type="button" id="closeButton" value="閉じる" onclick="window.close();">
	<br>
    <div id="spreadsheet"></div>

</body>  
</html>
