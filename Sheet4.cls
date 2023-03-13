VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
END
Attribute VB_Name = "Sheet4"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = True
Private Sub Worksheet_BeforeDoubleClick(ByVal Target As Range, Cancel As Boolean)
    '検索結果の範囲のみ
    r = Target.Row
    If r < 2 Or r > Cells(Rows.Count, 1).End(xlUp).Row Then Exit Sub
    C = Target.Column
    maxC = Cells(1, Columns.Count).End(xlToLeft).Column
    If C < 2 Or C > Cells(1, Columns.Count).End(xlToLeft).Column Then Exit Sub
    'フォーム表示
    Cancel = True   'ダブルクリック処理を実行しない※キャンセル
    Set currentCell = Target
    
    If C = 4 Then
        'ZOOM
        UserForm3.Show vbModeless   'モードレスで開く
    Else
        'シートマスタ
        UserForm2.Show vbModeless   'モードレスで開く
    End If
    
End Sub

Private Sub Worksheet_Change(ByVal Target As Range)
'セルが変化したときのイベント
    '元データと差異が出ればセル色を変更する
    'Debug.Print Target.Address(False, False)
    '検索結果の範囲のみ
    r = Target.Row
    maxR = Cells(Rows.Count, 1).End(xlUp).Row
    If r < 2 Or r > maxR Then Exit Sub
    C = Target.Column
    maxC = Cells(1, Columns.Count).End(xlToLeft).Column
        If C < 2 Or C > Cells(1, Columns.Count).End(xlToLeft).Column Then Exit Sub
    
    'chnageイベント、autofitの時エラーが出る
    On Error Resume Next
    If Target.Value = "" Then
        'autofitを確認：TBD
        For r2 = r To maxR
            i = 1
            v = Target.Value(i)
            No = Cells(r2, 1)    'org行数
            v2 = Worksheets("元データ").Cells(No, C)
            If v = v2 Then
                Target.Interior.ColorIndex = 0      '// 対象セルの背景色をクリア
            Else
                Target.Interior.ColorIndex = 45     '// 対象セルの背景色を設定
            End If
            i = i + 1
        Next
    Else
        v = Target.Value
        No = Cells(r, 1)    'org行数
        v2 = Worksheets("元データ").Cells(No, C)
        If v = v2 Then
            Target.Interior.ColorIndex = 0      '// 対象セルの背景色をクリア
        Else
            Target.Interior.ColorIndex = 45     '// 対象セルの背景色を設定
        End If
    End If
End Sub


