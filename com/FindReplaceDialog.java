package com;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Segment;

public class FindReplaceDialog extends JDialog {
	//private JTextField textField_findWords;
	public static final int FIND_OPERATION = 0;//����
    public static final int REPLACE_OPERATION = 1;//�����滻
    public static final int REPLACEALL_OPERATION = 2;//�滻����
    public static final int GOTO_OPERATION = 3;
	private JTextField textField_searchWords;
	private JTextField textField_replaceWords;
	private JCheckBox checkBox_allChars;
	private JCheckBox checkBox_ignoreCase;
	private JRadioButton radioButton_up;
	private JRadioButton radioButton_down;
	private MyTextArea textArea;
	private String key;
	private boolean ignoreCase;
	private boolean down;
	private int operationOption;
    private String replaceKey;//�滻�ַ���

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		try {
			FindReplaceDialog dialog = new FindReplaceDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	/**
	 * Create the dialog.
	 */
	public FindReplaceDialog(MyTextArea theTextArea) {
		
		//super(parent,true);
		
		textArea = theTextArea;
		String selectWords = textArea.getSelectedText();
		setBounds(200, 200, 500, 160);
		setTitle("����&�滻");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Container pane = getContentPane();
		//pane.setLayout(new GridLayout(4,3));
		
		GridBagLayout layout = new GridBagLayout();
		layout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        pane.setLayout(layout);
        
        JLabel lblNewLabel = new JLabel("��������(N)");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        getContentPane().add(lblNewLabel, gbc_lblNewLabel);
        
        textField_searchWords = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 1;
        gbc_textField.gridy = 0;
        gbc_textField.gridwidth= 6;
        getContentPane().add(textField_searchWords, gbc_textField);
        textField_searchWords.setColumns(10);
        textField_searchWords.setText(selectWords);
        
        JButton btnNewButton = new JButton("������һ��");
        //btnNewButton.setPreferredSize(new Dimension(200,80));
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton.anchor = GridBagConstraints.EAST;
        gbc_btnNewButton.gridx = 7;
        gbc_btnNewButton.gridy = 0;
        gbc_btnNewButton.gridwidth=2;
        getContentPane().add(btnNewButton, gbc_btnNewButton);
        btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				operationOption = 0;
				searchAndreplace();
			}
        });
        
        JLabel lblNewLabel_1 = new JLabel("�滻����");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 1;
        getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
        
        textField_replaceWords = new JTextField();
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.insets = new Insets(0, 0, 5, 5);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 1;
        gbc_textField_1.gridy = 1;
        gbc_textField_1.gridwidth=6;
        getContentPane().add(textField_replaceWords, gbc_textField_1);
        textField_replaceWords.setColumns(10);
        
        JButton btnNewButton_1 = new JButton("�滻");
        //btnNewButton_1.setPreferredSize(new Dimension(200,80));
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton_1.gridx = 7;
        gbc_btnNewButton_1.gridy = 1;
        gbc_btnNewButton_1.gridwidth=2;
        getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				operationOption = 1;
				searchAndreplace();
			}
        });
        
        checkBox_allChars = new JCheckBox("ȫ��ƥ��");
        GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
        gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
        gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxNewCheckBox.gridx = 0;
        gbc_chckbxNewCheckBox.gridy = 2;
        getContentPane().add(checkBox_allChars, gbc_chckbxNewCheckBox);
        
        JButton btnNewButton_2 = new JButton("ȫ���滻");
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton_2.gridx = 7;
        gbc_btnNewButton_2.gridy = 2;
        gbc_btnNewButton_2.gridwidth=2;
        getContentPane().add(btnNewButton_2, gbc_btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				operationOption = 2;
				searchAndreplace();
			}
        });
        
        checkBox_ignoreCase = new JCheckBox("���ִ�Сд");
        GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
        gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 0, 5);
        gbc_chckbxNewCheckBox_1.gridx = 0;
        gbc_chckbxNewCheckBox_1.gridy = 3;
        getContentPane().add(checkBox_ignoreCase, gbc_chckbxNewCheckBox_1);
        
        ButtonGroup bgroup=new ButtonGroup();
        
        radioButton_up = new JRadioButton("���ϲ���");
        GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
        gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 0, 5);
        gbc_rdbtnNewRadioButton.gridx = 3;
        gbc_rdbtnNewRadioButton.gridy = 3;
        getContentPane().add(radioButton_up, gbc_rdbtnNewRadioButton);
        bgroup.add(radioButton_up);
        
        radioButton_down = new JRadioButton("���²���");
        GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
        gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 0, 5);
        gbc_rdbtnNewRadioButton_1.gridx = 4;
        gbc_rdbtnNewRadioButton_1.gridy = 3;
        getContentPane().add(radioButton_down, gbc_rdbtnNewRadioButton_1);
        radioButton_down.setSelected(true);
        bgroup.add(radioButton_down);
        
        JButton btnNewButton_3 = new JButton("ȡ��");
        GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
        gbc_btnNewButton_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_3.gridx = 7;
        gbc_btnNewButton_3.gridy = 3;
        gbc_btnNewButton_3.gridwidth=2;
        getContentPane().add(btnNewButton_3, gbc_btnNewButton_3);
        btnNewButton_3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
        });

		GridBagConstraints s= new GridBagConstraints();//����һ��GridBagConstraints
		//this.pack();
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		
	}

	public void searchAndreplace(){
		key = textField_searchWords.getText();
		ignoreCase = !checkBox_ignoreCase.isSelected();
		down = radioButton_down.isSelected();
		replaceKey = textField_replaceWords.getText();
		int length = key.length();
        if (textArea == null || key == null || length <= 0 || operationOption > REPLACEALL_OPERATION) {
            return;
        }
        
        //��ȡ�༭����
        Document doc = textArea.getDocument();
        //�����ı�������ı��������λ��
        int offset = textArea.getCaretPosition();
        //��ȡ��ֹλ��
        int charsLeft = doc.getLength() - offset;
        
        //�滻����
        if (operationOption == REPLACE_OPERATION || operationOption == REPLACEALL_OPERATION) {
            offset = 0;
            down = true;
            charsLeft = doc.getLength();
        }
        int flag = 0;//for operationOption == 1 //Replace Once
        if (down == false) {
        	//��������滻������
            offset -= length;
            offset--;
            charsLeft = offset;
        }
        
        //�����ı�Ƭ�����飬��String���鿪��С
        Segment text = new Segment();
        //ָʾ���ַ�����Ч�ı��
        text.setPartialReturn(true);
        try {
            while (charsLeft > 0) {
            	//��ȡ�ĵ��и������ְ������ı������ı�Ƭ������
                doc.getText(offset, length, text);
                if ((ignoreCase == true && text.toString().equalsIgnoreCase(key))
                        || (ignoreCase == false && text.toString().equals(key))) {
                	
                	//����ҵ������������ַ������ڱ༭����ѡ��
                    textArea.setSelectionStart(offset);
                    textArea.setSelectionEnd(offset + length);
                    
                    if (operationOption == FIND_OPERATION) {
                    	//����ǲ��Ҳ�������������
                        return;
                    } else if (operationOption == REPLACE_OPERATION) {
                    	//����ǵ����滻��������flag�滻һ�κ�����滻����
                        flag++;
                        if (flag == 1) {
                            textArea.replaceSelection(replaceKey);
                        }
                        if (flag == 2) {
                            return;
                        }
                    } else if (operationOption == REPLACEALL_OPERATION) {
                    	//�����ȫ���滻����ѭ�����Ҳ��滻
                        textArea.replaceSelection(replaceKey);
                    }
                }
                charsLeft--;
                //�������¿���
                if (down) {
                    offset++;
                } else {
                    offset--;
                }
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
	}
}
