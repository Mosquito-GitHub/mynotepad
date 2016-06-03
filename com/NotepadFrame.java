package com;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
/**
 * ������
 * @author Mosquito
 *
 */
public class NotepadFrame {

	private UtilTools tools;
	private JFrame frame;
	private int textAreaIndex = 1;// �½�ҳǩ�����
	private JTabbedPane tabbedPane;
	// private JScrollPane scrollPane;
	private StatusBar statusBar;// ״̬��
	private RowNumBar rowNum;// �к�
	private JCheckBoxMenuItem menuItem_autoWrap;// �˵���--���Զ�����
	private JCheckBoxMenuItem menuItem_statusBar;// �˵���--��״̬��
	private JCheckBoxMenuItem menuItem_lineNum;// �˵���--���к�
	// private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotepadFrame window = new NotepadFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NotepadFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Notepad �ҵ��ı��༭��");
		frame.setBounds(100, 100, 880, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabbedPane = new JTabbedPane();
		frame.getContentPane().add(tabbedPane);
		tools = new UtilTools();

		// ��ʼ���˵�
		initJMenuBar();

		// ��ʼ��״̬��
		rowNum = new RowNumBar();

		// ��ʼ���к�
		statusBar = new StatusBar();

		// ��ʼ���ı��༭��
		initJTabbedPane(rowNum, statusBar, tabbedPane);

	}

	/**
	 * ��ʼ���˵�
	 */
	public void initJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		// �ļ��˵�
		JMenu menu_file = new JMenu("�ļ�(F)");
		menuBar.add(menu_file);
		menu_file.setMnemonic('F');

		// �ļ�-->�½���
		JMenuItem menuItem_new = new JMenuItem("�½�(N)");
		menu_file.add(menuItem_new);
		// ��ӿ�ݼ�
		menuItem_new.setMnemonic(KeyEvent.VK_N);
		menuItem_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menuItem_new.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addNewTabbedPane();
			}
		});

		// �ļ�-->����
		JMenuItem menuItem_open = new JMenuItem("��(O)");
		menu_file.add(menuItem_open);
		menuItem_open.setMnemonic(KeyEvent.VK_O);
		menuItem_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		// ��Ӽ����¼�
		menuItem_open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});

		JMenuItem menuItem_save = new JMenuItem("����(S)");
		menu_file.add(menuItem_save);
		menuItem_save.setMnemonic(KeyEvent.VK_S);
		menuItem_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuItem_save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});

		JMenuItem menuItem_saveAs = new JMenuItem("���Ϊ(A)");
		menu_file.add(menuItem_saveAs);
		menuItem_saveAs.setMnemonic(KeyEvent.VK_A);
		menuItem_saveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveFileAs();
			}
		});

		JMenuItem menuItem_quit = new JMenuItem("�˳�");
		menu_file.add(menuItem_quit);
		menu_file.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu menu_edit = new JMenu("�༭(E)");
		menuBar.add(menu_edit);
		menu_edit.setMnemonic('E');

		JMenuItem menuItem_undo = new JMenuItem("����(U)");
		menu_edit.add(menuItem_undo);
		menuItem_undo.setMnemonic(KeyEvent.VK_U);
		menuItem_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		menuItem_undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				unDo();
			}
		});

		JMenuItem menuItem_redo = new JMenuItem("�ָ�(R)");
		menu_edit.add(menuItem_redo);
		menuItem_redo.setMnemonic(KeyEvent.VK_R);
		menuItem_redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		menuItem_redo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reDo();
			}
		});

		JMenuItem menuItem_cut = new JMenuItem("����(T)");
		menu_edit.add(menuItem_cut);
		menuItem_cut.setMnemonic(KeyEvent.VK_T);
		menuItem_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		menuItem_cut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cut();
			}
		});

		JMenuItem menuItem_copy = new JMenuItem("����(C)");
		menu_edit.add(menuItem_copy);
		menuItem_copy.setMnemonic(KeyEvent.VK_C);
		menuItem_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menuItem_copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				copy();
			}
		});

		JMenuItem menuItem_past = new JMenuItem("ճ��(P)");
		menu_edit.add(menuItem_past);
		menuItem_past.setMnemonic(KeyEvent.VK_P);
		menuItem_past.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		menuItem_past.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paste();
			}
		});

		JMenuItem menuItem_delete = new JMenuItem("ɾ��(D)");
		menu_edit.add(menuItem_delete);
		menuItem_delete.setMnemonic(KeyEvent.VK_D);
		menuItem_delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Modifier.FINAL));
		menuItem_delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		JMenuItem menuItem_selectAll = new JMenuItem("ȫѡ");
		menu_edit.add(menuItem_selectAll);
		menuItem_selectAll.setMnemonic(KeyEvent.VK_L);
		menuItem_selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		menuItem_selectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectAll();
			}
		});

		JMenuItem menuItem_search = new JMenuItem("����");
		menu_edit.add(menuItem_search);
		menuItem_search.setMnemonic(KeyEvent.VK_F);
		menuItem_search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		menuItem_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});

		JMenuItem menuItem_searchNext = new JMenuItem("������һ��");
		menu_edit.add(menuItem_searchNext);

		JMenuItem menuItem_searchPrevious = new JMenuItem("������һ��");
		menu_edit.add(menuItem_searchPrevious);

		JMenuItem menuItem_replace = new JMenuItem("�滻");
		menu_edit.add(menuItem_replace);
		menuItem_replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		menuItem_replace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});

		JMenu menu_format = new JMenu("��ʽ(O)");
		menuBar.add(menu_format);
		menu_format.setMnemonic(KeyEvent.VK_O);

		JMenuItem menuItem_geshi = new JMenuItem("�����ʽ");
		menu_format.add(menuItem_geshi);

		JMenuItem menuItem_bianma = new JMenuItem("����ת��");
		menu_format.add(menuItem_bianma);

		JMenu menu_view = new JMenu("�鿴(V)");
		menuBar.add(menu_view);
		menu_view.setMnemonic(KeyEvent.VK_V);

		menuItem_autoWrap = new JCheckBoxMenuItem("�Զ�����(W)");
		menu_view.add(menuItem_autoWrap);
		menuItem_autoWrap.setMnemonic(KeyEvent.VK_W);
		menuItem_autoWrap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				autoWrap();
			}
		});

		menuItem_statusBar = new JCheckBoxMenuItem("״̬��(S)");
		menu_view.add(menuItem_statusBar);
		menuItem_statusBar.setMnemonic(KeyEvent.VK_S);
		menuItem_statusBar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showStatus();
			}
		});

		menuItem_lineNum = new JCheckBoxMenuItem("�к�(L)");
		menu_view.add(menuItem_lineNum);
		menuItem_lineNum.setMnemonic(KeyEvent.VK_L);
		menuItem_lineNum.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showRowsBar();
			}
		});

		// ��һ���ָ���
		menu_view.addSeparator();

		JMenuItem menuItem_front = new JMenuItem("����(F)");
		menu_view.add(menuItem_front);
		menuItem_front.setMnemonic(KeyEvent.VK_F);
		

		JMenuItem menuItem_wordColor = new JMenuItem("�ı���ɫ(T)");
		menu_view.add(menuItem_wordColor);
		menuItem_wordColor.setMnemonic(KeyEvent.VK_T);

		JMenuItem menuItem_backgroundColor = new JMenuItem("������ɫ(B)");
		menu_view.add(menuItem_backgroundColor);
		menuItem_backgroundColor.setMnemonic(KeyEvent.VK_B);

		JMenu mnNewMenu = new JMenu("����(A)");
		menuBar.add(mnNewMenu);
		mnNewMenu.setMnemonic(KeyEvent.VK_A);

		JMenuItem menuItem = new JMenuItem("����Notepad(A)");
		mnNewMenu.add(menuItem);
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showInternalMessageDialog(null, "Notepad�ı��༭����1.0�� Copyright(C)2015 Author:General Mosquito","����Notepad�ı��༭��", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(null, "Notepad�ı��༭����1.0�� Copyright(C)2015\n Author:General Mosquito", "����Notepad�ı��༭��", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	public MyTextArea getCurrentArea() {
		Component[] components = ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport().getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof MyTextArea) {
				return (MyTextArea) components[i];
				// System.out.println("I get it................");
			}
		}
		return null;
	}

	/**
	 * ��ʼ���ı��༭�� private StatusBar statusBar; private RowNumBar rowNum;
	 */
	public void initJTabbedPane(RowNumBar rowNum, StatusBar statusBar, JTabbedPane tabbedPane) {
		String title = "";
		if (textAreaIndex == 1) {
			title = "�ı��ĵ�.txt";
		} else {
			title = "�ı��ĵ�" + (textAreaIndex) + ".txt";
		}
		// JTextArea textArea = new JTextArea();
		MyTextArea textArea = new MyTextArea(rowNum, statusBar);
		JScrollPane scrollPane = new JScrollPane(textArea);
		//scrollPane.setRowHeaderView(rowNum);
		// ��Ҫ��ʾ���������scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// ʵʱ��ʾ���������
		//scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabbedPane.addTab(title, null, scrollPane, null);

		int tabCount = tabbedPane.getTabCount();
		tabbedPane.setTabComponentAt(tabCount-1, new ButtonTabComponent(tabbedPane));
		frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		System.out.println("ҳǩ��������������"+tabCount);
		tabbedPane.setSelectedIndex(tabCount-1);
		textAreaIndex++;
		//�Զ���������
		autoWrap();
		//״̬����ʾ����
		showStatus();
		//�����к���ʾ
		showRowsBar();

	}

	/**
	 * �½��ı�
	 */
	public void addNewTabbedPane() {
		initJTabbedPane(rowNum, statusBar, tabbedPane);
	}

	/**
	 * ���ļ�
	 */
	public void openFile() {

		JFileChooser fd = new JFileChooser();
		int chose = fd.showOpenDialog(null);
		// ���ѡ��ȡ���򷵻�
		if (chose == JFileChooser.CANCEL_OPTION) {
			return;
		}
		File f = fd.getSelectedFile();
		if (f != null) {
			// ����һ����ǩ�ṩ�������򿪵��ı��ļ�
			initJTabbedPane(rowNum, statusBar, tabbedPane);

			// �����ļ���Ϊ��ǩ��
			JScrollPane currentjsp = (JScrollPane) tabbedPane.getSelectedComponent();
			String fileName = f.getName();
			tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), fileName);

			// ��ȡ��ǰ��textArea
			MyTextArea currentArea = getCurrentArea();

			currentArea.setFile(f);
			tools.readTxtFile(f, currentArea);
		} else {
			System.out.println("û��ѡ���ļ�������������");
		}
	}

	/**
	 * ����
	 */
	public void saveFile() {
		// ��ȡ��ǰ��textArea
		MyTextArea currentArea = getCurrentArea();

		File targetFile;
		// ������Ѿ����ļ��������ֱ�ӱ���
		File temp = currentArea.getFile();
		if (temp != null) {

			try {
				if (temp.exists()) {
					temp.delete();
				}
				temp.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			targetFile = temp;
		} else {
			JFileChooser jfc = new JFileChooser();
			int chose = jfc.showSaveDialog(null);
			if (chose == JFileChooser.CANCEL_OPTION) {
				return;
			}
			// ��ȡ�����·�����ļ���
			File fileSelected = jfc.getSelectedFile();
			String fileInfo = fileSelected.toString();

			if (fileInfo.contains(".txt")) {
				targetFile = new File(fileInfo);
			} else {
				targetFile = new File(fileInfo + ".txt");
			}
		}
		tools.writeTextFile(currentArea, targetFile);
		currentArea.setFile(targetFile);
	}

	/**
	 * ���Ϊ
	 */
	public void saveFileAs() {
		MyTextArea currentArea = getCurrentArea();

		File targetFile;
		JFileChooser jfc = new JFileChooser();
		int chose = jfc.showSaveDialog(null);
		if (chose == JFileChooser.CANCEL_OPTION) {
			return;
		}
		// ��ȡ�����·�����ļ���
		File fileSelected = jfc.getSelectedFile();
		String fileInfo = fileSelected.toString();

		if (fileInfo.contains(".txt")) {
			targetFile = new File(fileInfo);
		} else {
			targetFile = new File(fileInfo + ".txt");
		}
		tools.writeTextFile(currentArea, targetFile);
		currentArea.setFile(targetFile);
	}

	/**
	 * ����
	 */
	public void unDo() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.unDo();
	}

	/**
	 * �ָ�
	 */
	public void reDo() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.reDo();
	}

	/**
	 * ����
	 */
	public void cut() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.cut();
	}

	/**
	 * ����
	 */
	public void copy() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.copy();
	}

	/**
	 * ճ��
	 */
	public void paste() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.paste();
	}

	/**
	 * ɾ��
	 */
	public void delete() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.delete();
	}

	/**
	 * ȫѡ
	 */
	public void selectAll() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.selectAll();
	}

	/**
	 * ����
	 */
	public void search() {
		MyTextArea currentArea = getCurrentArea();
		new FindReplaceDialog(currentArea);
	}

	/**
	 * �Զ�����
	 */
	public void autoWrap() {
		JScrollPane currentjsp = (JScrollPane) tabbedPane.getSelectedComponent();
		if (menuItem_autoWrap.isSelected()) {
			// ��Ҫ��ʾ���������
			currentjsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		} else {
			// ʵʱ��ʾ���������
			currentjsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
	}
	public void showStatus(){
		if(menuItem_statusBar.isSelected()){
			statusBar.setVisible(true);
		}else{
			statusBar.setVisible(false);
		}
	}
	public void showRowsBar(){
		JScrollPane currentjsp = (JScrollPane) tabbedPane.getSelectedComponent();
		if(menuItem_lineNum.isSelected()){
			currentjsp.setRowHeaderView(rowNum);
		}else{
			currentjsp.setRowHeaderView(null);
		}
	}
}
