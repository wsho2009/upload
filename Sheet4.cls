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
    '�������ʂ͈̔͂̂�
    r = Target.Row
    If r < 2 Or r > Cells(Rows.Count, 1).End(xlUp).Row Then Exit Sub
    C = Target.Column
    maxC = Cells(1, Columns.Count).End(xlToLeft).Column
    If C < 2 Or C > Cells(1, Columns.Count).End(xlToLeft).Column Then Exit Sub
    '�t�H�[���\��
    Cancel = True   '�_�u���N���b�N���������s���Ȃ����L�����Z��
    Set currentCell = Target
    
    If C = 4 Then
        'ZOOM
        UserForm3.Show vbModeless   '���[�h���X�ŊJ��
    Else
        '�V�[�g�}�X�^
        UserForm2.Show vbModeless   '���[�h���X�ŊJ��
    End If
    
End Sub

Private Sub Worksheet_Change(ByVal Target As Range)
'�Z�����ω������Ƃ��̃C�x���g
    '���f�[�^�ƍ��ق��o��΃Z���F��ύX����
    'Debug.Print Target.Address(False, False)
    '�������ʂ͈̔͂̂�
    r = Target.Row
    maxR = Cells(Rows.Count, 1).End(xlUp).Row
    If r < 2 Or r > maxR Then Exit Sub
    C = Target.Column
    maxC = Cells(1, Columns.Count).End(xlToLeft).Column
        If C < 2 Or C > Cells(1, Columns.Count).End(xlToLeft).Column Then Exit Sub
    
    'chnage�C�x���g�Aautofit�̎��G���[���o��
    On Error Resume Next
    If Target.Value = "" Then
        'autofit���m�F�FTBD
        For r2 = r To maxR
            i = 1
            v = Target.Value(i)
            No = Cells(r2, 1)    'org�s��
            v2 = Worksheets("���f�[�^").Cells(No, C)
            If v = v2 Then
                Target.Interior.ColorIndex = 0      '// �ΏۃZ���̔w�i�F���N���A
            Else
                Target.Interior.ColorIndex = 45     '// �ΏۃZ���̔w�i�F��ݒ�
            End If
            i = i + 1
        Next
    Else
        v = Target.Value
        No = Cells(r, 1)    'org�s��
        v2 = Worksheets("���f�[�^").Cells(No, C)
        If v = v2 Then
            Target.Interior.ColorIndex = 0      '// �ΏۃZ���̔w�i�F���N���A
        Else
            Target.Interior.ColorIndex = 45     '// �ΏۃZ���̔w�i�F��ݒ�
        End If
    End If
End Sub

