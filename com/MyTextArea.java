package com;

import java.io.File;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

public class MyTextArea extends JTextArea {

	private UndoManager undo;
	private RowNumBar rowNumBar;
	private StatusBar statusBar;
	private File file = null;
	private Document doc;

	public MyTextArea(RowNumBar rowNumBar, StatusBar statusBar) {

		// super();
		undo = new UndoManager();
		this.rowNumBar = rowNumBar;
		this.statusBar = statusBar;
		doc = this.getDocument();
		doc.addUndoableEditListener(undo);
		this.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				// TODO Auto-generated method stub

				try {
					changeStatusBar(e);
					changeRowNumBar();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}

	/**
	 * TextArea内容变化时修改行数的数据
	 */
	private void changeRowNumBar() {
		// int rowCount = this.getRows();
		int rowHeight = this.getRowHeight();
		int rowCount = this.getLineCount();
		// System.out.println("当前行数"+rowCount);
		rowNumBar.textAreaValueChanged(rowCount, rowHeight);
	}

	/**
	 * StatusBar设置数据
	 * 
	 * @param e
	 * @throws BadLocationException
	 */
	private void changeStatusBar(CaretEvent e) throws BadLocationException {

		// 字节数
		int wordsCount = this.getDocument().getLength();
		// 总行数
		int rowCount = this.getLineCount();
		// 返回文本组件的文本插入符的位置
		int pos = this.getCaretPosition();
		// 将组件文本中的偏移量转换为行号,即光标所在行数
		int dotRowNum = this.getLineOfOffset(pos) + 1;
		// 获取光标所在列数
		int dotColumnNum = pos - this.getLineStartOffset(dotRowNum - 1) + 1;

		statusBar.setCurrentValues(wordsCount, rowCount, dotRowNum, dotColumnNum);

	}

	/**
	 * 设置关联文件
	 * 
	 * @param f
	 */
	public void setFile(File f) {
		file = f;
	}

	/**
	 * 获取关联文件
	 * 
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 撤销
	 */
	public void unDo() {
		if (undo.canUndo()) {
			undo.undo();
		}
	}

	/**
	 * 恢复
	 */
	public void reDo() {
		if (undo.canRedo()) {
			undo.redo();
		}
	}

	/**
	 * 删除
	 */
	public void delete() {
		int start = this.getSelectionStart();
		int end = this.getSelectionEnd();
		int length = end - start;
		if(length == 0) length=1;
		try {
			doc.remove(start, length);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
