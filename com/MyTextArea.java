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
	 * TextArea���ݱ仯ʱ�޸�����������
	 */
	private void changeRowNumBar() {
		// int rowCount = this.getRows();
		int rowHeight = this.getRowHeight();
		int rowCount = this.getLineCount();
		// System.out.println("��ǰ����"+rowCount);
		rowNumBar.textAreaValueChanged(rowCount, rowHeight);
	}

	/**
	 * StatusBar��������
	 * 
	 * @param e
	 * @throws BadLocationException
	 */
	private void changeStatusBar(CaretEvent e) throws BadLocationException {

		// �ֽ���
		int wordsCount = this.getDocument().getLength();
		// ������
		int rowCount = this.getLineCount();
		// �����ı�������ı��������λ��
		int pos = this.getCaretPosition();
		// ������ı��е�ƫ����ת��Ϊ�к�,�������������
		int dotRowNum = this.getLineOfOffset(pos) + 1;
		// ��ȡ�����������
		int dotColumnNum = pos - this.getLineStartOffset(dotRowNum - 1) + 1;

		statusBar.setCurrentValues(wordsCount, rowCount, dotRowNum, dotColumnNum);

	}

	/**
	 * ���ù����ļ�
	 * 
	 * @param f
	 */
	public void setFile(File f) {
		file = f;
	}

	/**
	 * ��ȡ�����ļ�
	 * 
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * ����
	 */
	public void unDo() {
		if (undo.canUndo()) {
			undo.undo();
		}
	}

	/**
	 * �ָ�
	 */
	public void reDo() {
		if (undo.canRedo()) {
			undo.redo();
		}
	}

	/**
	 * ɾ��
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
