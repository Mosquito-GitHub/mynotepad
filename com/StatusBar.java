package com;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class StatusBar extends JToolBar{

	static int TEXTFIELD_WIDTH = 4;
	private JLabel label_wordCount;
	private JLabel label_lineCount;
	private JLabel label_currentRow;
	private JLabel label_currentColumn;
	private JTextField textfield_wordCount;
	private JTextField textfield_lineCount;
	private JTextField textfield_currentRow;
	private JTextField textfield_currentColumn;

	
	public StatusBar(){
		//����ʾ����ߵı�־
		this.setFloatable(false);
		//frame.getContentPane().add(this, BorderLayout.SOUTH);

		//��ʽ���֣���������ʾ
		FlowLayout flowlayout = new FlowLayout();
		flowlayout.setAlignment(FlowLayout.RIGHT);
		this.setLayout(flowlayout);

		// �ֽ���
		label_wordCount = new JLabel("�ֽ���:");
		this.add(label_wordCount);
		textfield_wordCount = new JTextField();
		this.add(textfield_wordCount);
		textfield_wordCount.setColumns(TEXTFIELD_WIDTH);
		textfield_wordCount.setEditable(false);
		textfield_wordCount.setBorder(null);
		// ������
		label_lineCount = new JLabel("������:");
		this.add(label_lineCount);
		textfield_lineCount = new JTextField();
		this.add(textfield_lineCount);
		textfield_lineCount.setColumns(TEXTFIELD_WIDTH);
		textfield_lineCount.setEditable(false);
		textfield_lineCount.setBorder(null);
		// ��ǰ��
		label_currentRow = new JLabel("��ǰ��:");
		this.add(label_currentRow);
		textfield_currentRow = new JTextField();
		this.add(textfield_currentRow);
		textfield_currentRow.setColumns(TEXTFIELD_WIDTH);
		textfield_currentRow.setEditable(false);
		textfield_currentRow.setBorder(null);
		// ��ǰ��
		label_currentColumn = new JLabel("��ǰ��:");
		this.add(label_currentColumn);
		textfield_currentColumn = new JTextField();
		this.add(textfield_currentColumn);
		textfield_currentColumn.setColumns(TEXTFIELD_WIDTH);
		textfield_currentColumn.setEditable(false);
		textfield_currentColumn.setBorder(null);
	}
	public void setCurrentValues(int wordsCount,int lineCount,int currentRow,int currentColumn){
		textfield_wordCount.setText(wordsCount + "");
		textfield_lineCount.setText(lineCount + "");
		textfield_currentRow.setText(currentRow + "");
		textfield_currentColumn.setText(currentColumn + "");
	}
}
