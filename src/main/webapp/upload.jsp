<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="fax.poFormBean" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><%= request.getAttribute("title") %></title>
  <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script>
  	$(function() {
  	  	var id = "<%= request.getAttribute("id") %>";
	    $.post('uploadServlet', 'type=select', 'id='+id)
        .done(function(data) {
          // 通信成功時のコールバック
          	console.log(data);
          	//ループ
			$.each(data, function(i, data){
				var value = data[1] + " " + data[3];	// code address
				var name = data[1] + " " + data[2];		// oode name (表示部分)
				console.log(i + " " + value + " " + name);
				var zokusei = {value:value, text:name};	// 属性を生成
				var yoso = $('<option>', zokusei);		// 要素を生成
				$('#select-1').append(yoso);			// セレクトボックスを追加
			});
	    	var value = "EXCEL";
	    	var name = "EXCEL_COMMON_FORMAT";
	    	console.log("COMMON " + value + " " + name);
	    	var zokusei = {value:value, text:name};	// 属性を生成
			var yoso = $('<option>', zokusei);		// 要素を生成
			$('#select-1').append(yoso);			// セレクトボックスを追加
        }).fail(function () {
          // 通信失敗時のコールバック
          alert("読み込み失敗");
        //}).always(function (result) {
        //  // 常に実行する処理
        });

   	    $.post('uploadServlet', 'type=rireki', 'id='+id)
        .done(function (data) {
            // 通信成功時のコールバック
  	  	  	//既存テーブルクリア
  	  	  	$('#listtable').empty();
  	  	  	var html = '<table border="1" />'
  	  	  	$('#listtable').append(html);
  	  	  	//テーブルヘッダ
  	  	  	var thead = '<thead><tr align="center"><th>REG</th><th>DATE</th><th>NAME</th><th>FORM</th></tr></thead>'
  	  	  	$('#listtable').append(thead);
			$.each(data, function(i, registdata){
				var tbody = $('<tbody />');
				var tr = $('<tr />');
				var id = $('<td />').text(registdata[0]);
				var registered = $('<td />').text(registdata[1]);
				var org_file_name = $('<td />').text(registdata[2]);
				var form_id = $('<td />').text(registdata[3]);
				tr.apped(id);
				tr.apped(registered);
				tr.apped(org_file_name);
				tr.apped(form_id);
				tbody.appped(tr);
				$('#listtable').append(tbody);
			});
        }).fail(function () {
            // 通信失敗時のコールバック
            alert("読み込み失敗");
          //}).always(function (result) {
          //  // 常に実行する処理
          });

  	  	//クリックで画像を選択する場合
		$('#drop_area').on('click', function() {
			$('#input_file').click();
		});
		//
		$('#input_file').on('change', function() {
			//ファイルが複数選択されていた場合
			if (this.files.length > 1)	{
				alert('アップロードできるファイルは一つだけです');
				$('#input_file').val('');
				return;
			}
			handleFiles(this.files);
		});
		//ドラッグしている要素がドロップ領域に入ったとき・領域にある間
		$('#drop_area').on('dragenter dragover', function(event) {
			event.stopPropagation();
			event.preventDefault();
			$('#drop_area').css('border', '1px solid #333');	//枠を実線にする
		});
		//ドラッグしている要素がドロップ領域から外れたとき
		$('#drop_area').on('dragleave', function(event) {
			event.stopPropagation();
			event.preventDefault();
			$('#drop_area').css('border', '1px solid #333');	//枠を点線に戻す
		});
		//ドラッグしている要素がドロップ領域されたとき
		$('#drop_area').on('drop', function(event) {
			event.stopPropagation();
			
			$('#input_file')[0].files = event.orginalEvent.dataTransfer.files;
			console.log($('#input_file')[0].files);
			if ($('#input_file')[0].files.length == 0) {
				alert('申し訳ありません。ＩＥではドラッグ＆ドロップをサポートしていません。\n Edgeブラウザをご利用ください')
				$('#input_file').val('');
				return;
			}
			//ファイルが複数選択されていた場合
			if (this.files.length > 1)	{
				alert('アップロードできるファイルは一つだけです');
				$('#input_file').val('');
				return;
			}
			handleFiles($('#input_file')[0].files);
		});
		//選択されたファイルの操作
		function handleFiles(files) {
			var file = files[0];
			//var imageType = 'image.*';
			if (file.type == ""){
				ext = file.name.split('.').pop(); //MIME typeが種痘できない場合、拡張子を入れる。
				file_type = ext;
			} else {
				file_type =file.type;
			}
			console.log(file_type);
			var pdfType= 'pdf';
			if (!(file_type.match('pdf')||file_type.match('tsv')||file_type.match(
					'application/vnd.ms-excel')||file_type.match('text/plain')||file_type.match('text/csv')
					||file_type.match('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'))) {
				alert('ファイルを選択してください');
				$('#input_file').val('');
				$('#drop_area').css('border', '1px dashed #aaa');
				return;
			} 
			$('#drop_area').hide();			// いちばん上のdrop_areを非表示にします。
			$('#icon_clear_button').show();	//　icon_clear_buttonを表示させる。
			$('#preview_field').append(file.name);
		}
		//アイコン画像を消去するボタン
		$('#icon_clear_button').on('click', function() {
			$('#preview_field').empty();	//表示していた画像を消去
			$('#input_file').val('');		//inputの中身を消去
			$('#drop_area').show();			//drop_areaを前面に表示
			$('#icon_clear_button').hide();	//icon_clear_buttonを非表示
			$('#drop_area').css('border', '1px dashed #aaa');		//枠を点線に変更
		});
		//drop以外でファイルがドロップされた場合、ファイルが開いてしまうのを防ぐ
		$(document).on('dragenter', function(envet) {
			event.stopPropagation();
			event.preventDefault();
		});
		//
		$(document).on('dragover', function(envet) {
			event.stopPropagation();
			event.preventDefault();
		});
		//
		$(document).on('drop', function(envet) {
			event.stopPropagation();
			event.preventDefault();
		});
		//アップロードボタンクリック時のアクション
		$('#uploadButton').click(function() {
			if ($('#input_file')[0].files.length == 0) {
				alert('ファイルを選択してください')
				return;
			}
			const files = $('#input_file')[0].files;
			var fileName = files[0].name;
			var formName = $('#select-1 option:selected').text();
			console.log("formName: " + formName);
			if (formName == "") {
				alert("帳票を選択してください")
				return;
			}
			var formId =  $('#select-1 option:selected').val();
			console.log("formId: " + formId);
			var toriCd = formId.substr(0, 5);

			if (fileName.indexOf(toriCd) == -1) {
				alert("ファイル名に含まれていません。確認してください。")
				return;
			}
			console.log("fileName:" + fileName);
			var dt = new Date();
			var dtStr = formatDate(dt, "yyyy/MM/dd HH:mm:ss");
			console.log("dt:" + dtStr);
			var id = $('#text_id').val();
			console.log("id:" + id);
			var user = $('#text_user').val();
			console.log("user:" + user);
			var code = $('#text_code').val();
			console.log("code:" + code);

			var result = window.confirm("以下の条件でアプロード実行していますか？\n 帳票名:" + formName + "\n ファイル名: " + fileName);
			if (result != true) {
				//キャンセル
				return;
			}
			//登録処理開始
			const formData = new FormData();
			formData.append('type', "upload");
			formData.append('id', id);
			formData.append('user', user);
			formData.append('code', code);
			formData.append('dt', dtStr);
			formData.append('file', files[0]);
			formData.append('formId', formId);
			formData.append('formName', formName);
			console.log(formData);

	        $.ajax({
	            url: 'uploadServlet',
	            type: 'post',
	            processData: false,
	            contentType: false, // 送信するデータをFormDataにする場合、こうしないといけない。
	            cache: false,
	            dataType: 'json',
	            data: formData,
	        }).done(function(res) {
				console.log(res);
				$('#preview_field').empty();	//表示していた画像を消去
				$('#input_file').val('');		//inputの中身を消去
				$('#drop_area').show();			//drop_areaを前面に表示
				$('#icon_clear_button').hide();	//icon_clear_buttonを非表示
				$('#drop_area').css('border', '1px dashed #aaa');		//枠を点線に変更
				//var id = "<%= request.getAttribute("id") %>";
				alert("アップロードしました。\n システムに登録が完了したらログインID宛にメールを送信します。\n　宛先: " + id);
				$("#listtable tr.last").remove();
				//1行目に追加
				var addline = '<tr><td>' + user + '/td><td>' + dtStr + '</td><td>' + fileName + '</td><td>' + formName + '</td></tr>'
				$('#listtable td:first').after(addline);
				console.log("add: " + addline);
			}).fail(function(err) {
				console.log(err);
			});
        });
		function formatDate(date, format) {
			format = format.replace(/yyyy/g, date.getFullYear());
			format = format.replace(/MM/g, ('0'+ (date.getMonth()+1)).slice(-2));
			format = format.replace(/dd/g, ('0'+ date.getDate()).slice(-2));
			format = format.replace(/HH/g, ('0'+ date.getHours()).slice(-2));
			format = format.replace(/mm/g, ('0'+ date.getMinutes()).slice(-2));
			format = format.replace(/ss/g, ('0'+ date.getSeconds()).slice(-2));
			format = format.replace(/SSS/g, ('0'+ date.getMilliseconds()).slice(-3));
			return format;
		};
		//いらん
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
    	font-size: 10pt;
        font-family:sans-serif;
        background-color: #ffffff;
    }
    .user-icon-dnd-wrapper {
    	position: relative;
    	width: 250px;
    	height: 20px;
    }
    #preview_field {
    	position: absolute;
    	top: 0;
    	left: 0;
    	width: 250px;
    	height: 20px;
    }
    #drop_area {
    	position: absolute;
    	top: 0;
    	left: 0;
    	width: 250px;
    	height: 20px;
    	border-radius: 1rem;
    	cursor: pointer;
    }
    #icon_clear_button {
    	display: none;
    	position: absolute;
    	top: -4px;
    	right: -8px;
    	width: 24px;
    	height: 24px;
    	border: 1px solid #777;
		border-radius: 50%;
    	cursor: pointer;
    }
    #input_file {
    	width: 250px;
    	height: 20px;
    	opacity: 0;
    }
    #input_file:focus {
    	opacity: 1;
    }
    #control {
    	background-color: rgb(178 224, 208);
    	border-collapse: collapse;
    	width: auto;
    	white-space: nowrap;
    }
    #control th {
    	background-color: #F9E2A0;
    	border: 1px solid blue;
    	color: black;
    	font-weight: normal;
    	padding: 0px 5px;
    }
    #control td {
    	background-color: #F4B7AF;
    	border: 1px solid blue;
    	padding: 0px 5px;
    }
    #listtable {
    	border-collapse: collapse;
    	width: auto;
    	white-space: nowrap;
    }
    #listtable th {
    	background-color: #B2C4DD;
    	border: 1px solid blue;
    	color: black;
    	font-weight: normal;
    	padding: 0px 5px;
    }
    #control td {
    	border: 1px solid blue;
    	padding: 0px 5px;
    }
    #text_user {
    	font-size: 105%
   	}
    #select-1 {
    	font-size: 105%
   	}
    #uploadButton {
    	font-size: 105%
   	}
        
    #completeButton, #closeButton {
    	font-size: 105%;
    	cursor: pointer;
    } 
   </style>
</head>
<body>
  <h1>アップロード</h1>
  <table id="control">
  	<tr>
  		<th>User</th>
  		<td><label><%= request.getAttribute("name") %></label></td>
  		<input type="hidden" id="text_id" size="30" value=<%= request.getAttribute("id") %>>
  		<input type="hidden" id="text_user" size="30" value=<%= request.getAttribute("name") %>>
  		<input type="hidden" id="text_code" size="30" value=<%= request.getAttribute("code") %>>
  		<th>Form</th>
  		<td><select name="form" id="select-1" class="target">
  			<option value="" label="" selected></option>
       		<% 
       		List<poFormBean> select = (List<poFormBean>)request.getAttribute("select");
       		for (int i=0; i<select.size(); i++) {
       			poFormBean poForm = (poFormBean)select.get(i);
       		%>
       		<option value=<%= poForm.getCode() %> label=<%= poForm.getFormId() %>><%= poForm.getFormId() %></option>
      		<%
      		}
       		%>
       		<option value="EXCEL" label="EXCEL_COMMON_FORMAT" selected>EXCEL_COMMON_FORMAT</option>
  		</select></td>
  		<th>File</th>
  		<td>
			<div class="user-icon-dnd-wrapper">
			<input type="file" name="icon" id="input_file" 
  				accept="application/pdf,.txt,.csv,.tsv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
  			<div id="preview_field"></div>
  			<div id="drop_area">drop & drop or clieck here.</div>
  			<div id="icon_clear_button">X</div>
  		</td>
  	</tr>
  </table>
  <br>
  <input type="button" id="uploadButton" value="アップロード">
  
  <h3>History</h3>
  <div id='listtable'></div>
  	
</body>
</html>
