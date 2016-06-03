package com;

import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 * 想在左侧显示行号，将控件装到JScrollPane中，调用JScrollPane中setRowHeaderView方法，参数可以传JList，
 * 给JList的每个单元格设置你要显示的行号.
 */
public class RowNumBar extends JList{

	private RowNumListModel rowNumModel;
	private int rowHeight;
	public RowNumBar(){
		rowNumModel = new RowNumListModel();
		this.setModel(rowNumModel);
		this.setFixedCellWidth(30);
		this.setBackground(null);
	}
	public RowNumBar(MyTextArea textArea){
		rowNumModel = new RowNumListModel(textArea);
		this.setModel(rowNumModel);
		this.setFixedCellWidth(30);
		this.setBackground(null);
	}
	public void textAreaValueChanged(int lineCount, int rowHeight){
		rowNumModel.setRowNum(lineCount);
		this.setFixedCellHeight(rowHeight);
		//this.setBackground(null);
		updateUI();
	}
}
class RowNumListModel implements ListModel{

	MyTextArea textArea;
	int rowNum=1;
	public RowNumListModel(){
	}
	public RowNumListModel(MyTextArea textArea){
		this.textArea = textArea;
	}
	public void setRowNum(int listSize){
		
		rowNum = listSize;
		//System.out.println(rowNum);
	}
	@Override
	public int getSize() {
		if(textArea != null){
			return textArea.getRows();
		}else{
			return rowNum;
		}
	}

	@Override
	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return index+1;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}
	
}