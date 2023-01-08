<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
  	const unitId = <%= request.getAttribute("id") %>;
  	const unitStatus = <%= request.getAttribute("status") %>;
  	$(function() {
		console.log("unitId: " + unitId)
		console.log("unitStatus: " + unitStatus)
		if (unitStatus == 'COMPLETE' || unitStatus == '') {
			document.getElementById("completeButton").style.display = "noen";
			var button = document.getElementById("completeButton");
			button.disabled = false
		}
		$('#completeButton').click(function() {
			console.log('/complete/' + unitId)
			$.post('/complete/' + unitId)
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
    data = [
        ["2021/12/30", 3000, "2021/11/30", 3000],
        ["2021/12/30", 3000, "2021/11/30", 3000],
        ["2021/12/15", 3000, "2021/11/15", 3000],
        ["2021/12/15", 3000, "2021/11/15", 3000],
    ];
    mySpreadsheet = jexcel(document.getElementById('spreadsheet'), {
        data:data,
        columns:[
            { title:'客先要求納期', width:120, type:'text'},
            { title:'数量', width:80, type:'numeric'  },
            { title:'営業希望納期', width:120, type:'text'  },
            { title:'数量', width:80, type:'numeric' }
        ],

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
