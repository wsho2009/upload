<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="fax.PoRirekiBean"%>
<!DOCTYPE html>
<html>
<head>
  <title><%= request.getAttribute("title") %></title>
  <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://bossanova.uk/jspreadsheet/v3/jexcel.js"></script>
  <script src="https://jsuites.net/v3/jsuites.js"></script>
  <link rel="stylesheet" href="https://bossanova.uk/jspreadsheet/v3/jexcel.css" type="text/css" />
  <link rel="stylesheet" href="https://jsuites.net/v3/jsuites.css" type="text/css" />
  <script>
  	const unitId = <%= request.getAttribute("unitId") %>;
  	const unitStatus = "<%= request.getAttribute("unitStatus") %>";
  	const url = "<%= request.getAttribute("url") %>";
  	$(function() {
		console.log("unitId: " + unitId)
		console.log("unitStatus: " + unitStatus)
		console.log("url: " + url)
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
  	});
   </script>
   <style>
    html,body {
        font-family:sans-serif;
    }
    #completeButton, #closeButton {
    	font-size: 105%;
    	cursor: pointer;
    } 
   </style>
</head>
<body>
	<h1><%= request.getAttribute("title") %></h1>
	<input type="button" id="completeButton" value="確認完了"> 
	<input type="button" id="closeButton" value="閉じる" onclick="window.close();">
	<br>
    <div id="spreadsheet"></div>

    <script>
    <% List<PoRirekiBean> list = (List<PoRirekiBean>)request.getAttribute("list"); %> var dataArray = [
        <% for (int i=0; i<list.size(); i++ ) {
        	PoRirekiBean rireki = (PoRirekiBean)list.get(i);
            out.print("[");
            for (int j=0; j<4; j++ ) {
            	out.print('"' + rireki.getCOL(j) + '"' + ',');
            }
            out.print("],");
        }%>
    ];
    <% List<String[]> columns_list = (List<String[]>)request.getAttribute("columns"); %>  var columnsArray = [
		<% for (int i = 0; i < columns_list.size(); i++ ) {
			String[] col_array = (String[])columns_list.get(i);
			out.print("{");
			out.print(col_array[0] + ',' + col_array[1] + ',' + col_array[2]);	//title,width,typeの３カラムは固定
			out.print("},");
		}%>
	];
    mySpreadsheet = jexcel(document.getElementById('spreadsheet'), {
        data: dataArray,
        columns: columnsArray,
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
   </script>

</body>  
</html>
