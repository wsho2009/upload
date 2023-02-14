
httprequest()

Function httprequest()
    Dim httpReq
    Dim Url
    Url = "http://localhost:8080/upload/watchdog"
    Set httpReq = CreateObject("MSXML2.XMLHTTP")
    'リクエスト処理
    httpReq.Open "GET", Url, False ' FALSE:同期処理
    httpReq.send
    
    If httpReq.Status <> 200 Then
        'MsgBox "HTTPレスポンスエラー"
        httprequest = -1
        Exit Function
    End If
    '-----------------------------------------------------
    Dim adoStRes
    Set adoStRes = CreateObject("ADODB.Stream")
    With adoStRes
        .Open
        .Position = 0
        .Type = 1    'adTypeBinary
        .Write httpReq.responseBody
        
        .Position = 0
        .Type = 2    'adTypeText
        .Charset = "UTF-8"  'SJISの場合、"shift_jis"
        TextLine = .ReadText
        'MsgBox TextLine
        .Close
    End With
    '-----------------------------------------------------
    
    Set httpReq = Nothing
    httprequest = 0
    
End Function

