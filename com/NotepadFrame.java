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
 * 主窗体
 * @author Mosquito
 *
 */
public class NotepadFrame {

	private UtilTools tools;
	private JFrame frame;
	private int textAreaIndex = 1;// 新建页签标题号
	private JTabbedPane tabbedPane;
	// private JScrollPane scrollPane;
	private StatusBar statusBar;// 状态栏
	private RowNumBar rowNum;// 行号
	private JCheckBoxMenuItem menuItem_autoWrap;// 菜单栏--》自动换行
	private JCheckBoxMenuItem menuItem_statusBar;// 菜单栏--》状态栏
	private JCheckBoxMenuItem menuItem_lineNum;// 菜单栏--》行号
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
		frame = new JFrame("Notepad 我的文本编辑器");
		frame.setBounds(100, 100, 880, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabbedPane = new JTabbedPane();
		frame.getContentPane().add(tabbedPane);
		tools = new UtilTools();

		// 初始化菜单
		initJMenuBar();

		// 初始化状态栏
		rowNum = new RowNumBar();

		// 初始化行号
		statusBar = new StatusBar();

		// 初始化文本编辑框
		initJTabbedPane(rowNum, statusBar, tabbedPane);

	}

	/**
	 * 初始化菜单
	 */
	public void initJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		// 文件菜单
		JMenu menu_file = new JMenu("文件(F)");
		menuBar.add(menu_file);
		menu_file.setMnemonic('F');

		// 文件-->新建项
		JMenuItem menuItem_new = new JMenuItem("新建(N)");
		menu_file.add(menuItem_new);
		// 添加快捷键
		menuItem_new.setMnemonic(KeyEvent.VK_N);
		menuItem_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menuItem_new.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addNewTabbedPane();
			}
		});

		// 文件-->打开项
		JMenuItem menuItem_open = new JMenuItem("打开(O)");
		menu_file.add(menuItem_open);
		menuItem_open.setMnemonic(KeyEvent.VK_O);
		menuItem_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		// 添加监听事件
		menuItem_open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});

		JMenuItem menuItem_save = new JMenuItem("保存(S)");
		menu_file.add(menuItem_save);
		menuItem_save.setMnemonic(KeyEvent.VK_S);
		menuItem_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuItem_save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});

		JMenuItem menuItem_saveAs = new JMenuItem("另存为(A)");
		menu_file.add(menuItem_saveAs);
		menuItem_saveAs.setMnemonic(KeyEvent.VK_A);
		menuItem_saveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveFileAs();
			}
		});

		JMenuItem menuItem_quit = new JMenuItem("退出");
		menu_file.add(menuItem_quit);
		menu_file.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu menu_edit = new JMenu("编辑(E)");
		menuBar.add(menu_edit);
		menu_edit.setMnemonic('E');

		JMenuItem menuItem_undo = new JMenuItem("撤销(U)");
		menu_edit.add(menuItem_undo);
		menuItem_undo.setMnemonic(KeyEvent.VK_U);
		menuItem_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		menuItem_undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				unDo();
			}
		});

		JMenuItem menuItem_redo = new JMenuItem("恢复(R)");
		menu_edit.add(menuItem_redo);
		menuItem_redo.setMnemonic(KeyEvent.VK_R);
		menuItem_redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		menuItem_redo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reDo();
			}
		});

		JMenuItem menuItem_cut = new JMenuItem("剪切(T)");
		menu_edit.add(menuItem_cut);
		menuItem_cut.setMnemonic(KeyEvent.VK_T);
		menuItem_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		menuItem_cut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cut();
			}
		});

		JMenuItem menuItem_copy = new JMenuItem("复制(C)");
		menu_edit.add(menuItem_copy);
		menuItem_copy.setMnemonic(KeyEvent.VK_C);
		menuItem_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menuItem_copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				copy();
			}
		});

		JMenuItem menuItem_past = new JMenuItem("粘贴(P)");
		menu_edit.add(menuItem_past);
		menuItem_past.setMnemonic(KeyEvent.VK_P);
		menuItem_past.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		menuItem_past.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paste();
			}
		});

		JMenuItem menuItem_delete = new JMenuItem("删除(D)");
		menu_edit.add(menuItem_delete);
		menuItem_delete.setMnemonic(KeyEvent.VK_D);
		menuItem_delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Modifier.FINAL));
		menuItem_delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		JMenuItem menuItem_selectAll = new JMenuItem("全选");
		menu_edit.add(menuItem_selectAll);
		menuItem_selectAll.setMnemonic(KeyEvent.VK_L);
		menuItem_selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		menuItem_selectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectAll();
			}
		});

		JMenuItem menuItem_search = new JMenuItem("查找");
		menu_edit.add(menuItem_search);
		menuItem_search.setMnemonic(KeyEvent.VK_F);
		menuItem_search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		menuItem_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});

		JMenuItem menuItem_searchNext = new JMenuItem("查找下一个");
		menu_edit.add(menuItem_searchNext);

		JMenuItem menuItem_searchPrevious = new JMenuItem("查找上一个");
		menu_edit.add(menuItem_searchPrevious);

		JMenuItem menuItem_replace = new JMenuItem("替换");
		menu_edit.add(menuItem_replace);
		menuItem_replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		menuItem_replace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});

		JMenu menu_format = new JMenu("格式(O)");
		menuBar.add(menu_format);
		menu_format.setMnemonic(KeyEvent.VK_O);

		JMenuItem menuItem_geshi = new JMenuItem("编码格式");
		menu_format.add(menuItem_geshi);

		JMenuItem menuItem_bianma = new JMenuItem("编码转换");
		menu_format.add(menuItem_bianma);

		JMenu menu_view = new JMenu("查看(V)");
		menuBar.add(menu_view);
		menu_view.setMnemonic(KeyEvent.VK_V);

		menuItem_autoWrap = new JCheckBoxMenuItem("自动换行(W)");
		menu_view.add(menuItem_autoWrap);
		menuItem_autoWrap.setMnemonic(KeyEvent.VK_W);
		menuItem_autoWrap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				autoWrap();
			}
		});

		menuItem_statusBar = new JCheckBoxMenuItem("状态栏(S)");
		menu_view.add(menuItem_statusBar);
		menuItem_statusBar.setMnemonic(KeyEvent.VK_S);
		menuItem_statusBar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showStatus();
			}
		});

		menuItem_lineNum = new JCheckBoxMenuItem("行号(L)");
		menu_view.add(menuItem_lineNum);
		menuItem_lineNum.setMnemonic(KeyEvent.VK_L);
		menuItem_lineNum.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showRowsBar();
			}
		});

		// 加一条分割线
		menu_view.addSeparator();

		JMenuItem menuItem_front = new JMenuItem("字体(F)");
		menu_view.add(menuItem_front);
		menuItem_front.setMnemonic(KeyEvent.VK_F);
		

		JMenuItem menuItem_wordColor = new JMenuItem("文本颜色(T)");
		menu_view.add(menuItem_wordColor);
		menuItem_wordColor.setMnemonic(KeyEvent.VK_T);

		JMenuItem menuItem_backgroundColor = new JMenuItem("背景颜色(B)");
		menu_view.add(menuItem_backgroundColor);
		menuItem_backgroundColor.setMnemonic(KeyEvent.VK_B);

		JMenu mnNewMenu = new JMenu("关于(A)");
		menuBar.add(mnNewMenu);
		mnNewMenu.setMnemonic(KeyEvent.VK_A);

		JMenuItem menuItem = new JMenuItem("关于Notepad(A)");
		mnNewMenu.add(menuItem);
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showInternalMessageDialog(null, "Notepad文本编辑器，1.0版 Copyright(C)2015 Author:General Mosquito","关于Notepad文本编辑器", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(null, "Notepad文本编辑器，1.0版 Copyright(C)2015\n Author:General Mosquito", "关于Notepad文本编辑器", JOptionPane.ERROR_MESSAGE);
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
	 * 初始化文本编辑框 private StatusBar statusBar; private RowNumBar rowNum;
	 */
	public void initJTabbedPane(RowNumBar rowNum, StatusBar statusBar, JTabbedPane tabbedPane) {
		String title = "";
		if (textAreaIndex == 1) {
			title = "文本文档.txt";
		} else {
			title = "文本文档" + (textAreaIndex) + ".txt";
		}
		// JTextArea textArea = new JTextArea();
		MyTextArea textArea = new MyTextArea(rowNum, statusBar);
		JScrollPane scrollPane = new JScrollPane(textArea);
		//scrollPane.setRowHeaderView(rowNum);
		// 不要显示横向滚动条scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// 实时显示横向滚动条
		//scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabbedPane.addTab(title, null, scrollPane, null);

		int tabCount = tabbedPane.getTabCount();
		tabbedPane.setTabComponentAt(tabCount-1, new ButtonTabComponent(tabbedPane));
		frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		
		System.out.println("页签个数。。。。。"+tabCount);
		tabbedPane.setSelectedIndex(tabCount-1);
		textAreaIndex++;
		//自动换行设置
		autoWrap();
		//状态栏显示设置
		showStatus();
		//设置行号显示
		showRowsBar();

	}

	/**
	 * 新建文本
	 */
	public void addNewTabbedPane() {
		initJTabbedPane(rowNum, statusBar, tabbedPane);
	}

	/**
	 * 打开文件
	 */
	public void openFile() {

		JFileChooser fd = new JFileChooser();
		int chose = fd.showOpenDialog(null);
		// 如果选择取消则返回
		if (chose == JFileChooser.CANCEL_OPTION) {
			return;
		}
		File f = fd.getSelectedFile();
		if (f != null) {
			// 创建一个标签提供给即将打开的文本文件
			initJTabbedPane(rowNum, statusBar, tabbedPane);

			// 设置文件名为标签名
			JScrollPane currentjsp = (JScrollPane) tabbedPane.getSelectedComponent();
			String fileName = f.getName();
			tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), fileName);

			// 获取当前的textArea
			MyTextArea currentArea = getCurrentArea();

			currentArea.setFile(f);
			tools.readTxtFile(f, currentArea);
		} else {
			System.out.println("没有选择文件。。。。。。");
		}
	}

	/**
	 * 保存
	 */
	public void saveFile() {
		// 获取当前的textArea
		MyTextArea currentArea = getCurrentArea();

		File targetFile;
		// 如果是已经打开文件，则可以直接保存
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
			// 获取保存的路径和文件名
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
	 * 另存为
	 */
	public void saveFileAs() {
		MyTextArea currentArea = getCurrentArea();

		File targetFile;
		JFileChooser jfc = new JFileChooser();
		int chose = jfc.showSaveDialog(null);
		if (chose == JFileChooser.CANCEL_OPTION) {
			return;
		}
		// 获取保存的路径和文件名
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
	 * 撤销
	 */
	public void unDo() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.unDo();
	}

	/**
	 * 恢复
	 */
	public void reDo() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.reDo();
	}

	/**
	 * 剪切
	 */
	public void cut() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.cut();
	}

	/**
	 * 复制
	 */
	public void copy() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.copy();
	}

	/**
	 * 粘贴
	 */
	public void paste() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.paste();
	}

	/**
	 * 删除
	 */
	public void delete() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.delete();
	}

	/**
	 * 全选
	 */
	public void selectAll() {
		MyTextArea currentArea = getCurrentArea();
		currentArea.selectAll();
	}

	/**
	 * 查找
	 */
	public void search() {
		MyTextArea currentArea = getCurrentArea();
		new FindReplaceDialog(currentArea);
	}

	/**
	 * 自动换行
	 */
	public void autoWrap() {
		JScrollPane currentjsp = (JScrollPane) tabbedPane.getSelectedComponent();
		if (menuItem_autoWrap.isSelected()) {
			// 不要显示横向滚动条
			currentjsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		} else {
			// 实时显示横向滚动条
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
