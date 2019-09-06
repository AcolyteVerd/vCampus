package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import vc.common.BookStatusInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryAdminImpl;
import vc.sendImpl.ILibraryComImpl;

public class BookStatusView extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel model;
	private JTable tblStatus;
	private JScrollPane scrollStatus;
	private JButton btnReturn;
	
	private LibraryAdminView myLibraryAdminView = null;
	private SocketHelper sockethelper = new SocketHelper();
	private List<BookStatusInfo> statusList;
	
	////�޸ĺ��ͼ����Ϣ
	public int bookId;
	public String name;
	public String borrower;
	public long borrowDate;
	public long returnDate;
	public long actualReturnDate;
	public boolean isOvertime;
	
	/**
	 * Create the frame.
	 */
	public BookStatusView(String bookName, LibraryAdminView tmpLibraryAdminView) {
		
		this.myLibraryAdminView = tmpLibraryAdminView;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 524);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("ͼ��״̬��ѯ���");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("΢���ź�", Font.PLAIN, 28));
		lblTitle.setBounds(201, 13, 232, 47);
		contentPane.add(lblTitle);
				                
		////////////���Ĵ������ʼ��////////////
		////////
		/* 
         * * ����JTable������ 
         */  
        String[] columnNames =  
        { "���", "����", "������ID","ʱ��","Ӧ��ʱ��","ʵ�ʹ黹ʱ��"};  
  
        
        tblStatus = new JTable();
		tblStatus.setColumnSelectionAllowed(true);
		tblStatus.setForeground(SystemColor.activeCaption);
		tblStatus.setBounds(105, 250, 250, 140);
		
		model = new DefaultTableModel(columnNames, 0);
		statusList = new ILibraryAdminImpl(this.sockethelper).EnquiryBookStatus(bookName);
		
		for(int i = 0; i < statusList.size(); i++)
		{
			System.out.println(statusList.size());
			BookStatusInfo bookStatusTemp = statusList.get(i);
			System.out.println(bookStatusTemp.getName());
			if(bookStatusTemp.getActualReturnDate()==0)
			{
				Object[] rowData = { String.valueOf(bookStatusTemp.getId()), bookStatusTemp.getName(),  bookStatusTemp.getBorrower(), String.valueOf(bookStatusTemp.getBorrowDate()), 
						String.valueOf(bookStatusTemp.getReturnDate()), "δ�黹"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { String.valueOf(bookStatusTemp.getId()), bookStatusTemp.getName(),  bookStatusTemp.getBorrower(), String.valueOf(bookStatusTemp.getBorrowDate()), 
						String.valueOf(bookStatusTemp.getReturnDate()), String.valueOf(bookStatusTemp.getActualReturnDate())};
			    model.addRow(rowData);
			}
		}
		tblStatus.setModel(model);
		scrollStatus = new JScrollPane(tblStatus);  
		scrollStatus.setBounds(94, 73, 484, 303);
		scrollStatus.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //����ˮƽ��������Ҫʱ�ɼ�
	    getContentPane().add(scrollStatus);
		contentPane.add(scrollStatus);
		
		////////////�����ĳ�ʼ��////////////
		JButton btnModify = new JButton("�޸�");
		btnModify.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnModify.setBounds(475, 389, 84, 30);
		contentPane.add(btnModify);
		
		btnReturn = new JButton("����");
		btnReturn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setBounds(596, 389, 84, 30);
		contentPane.add(btnReturn);
		
		////////////��������////////////
		
		//�޸�
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ѡ��ĳ����¼����ʼ�޸�
				int checkedRow = tblStatus.getSelectedRow();
				if(tblStatus.getCellEditor()!=null)
					tblStatus.getCellEditor().stopCellEditing();//ǿ��JTable�����༭״̬
				if (checkedRow == -1)
				{
				     JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�޸ĵ�ͼ�飡");
				     return;
				 }
				bookId = Integer.parseInt(((String)tblStatus.getValueAt(checkedRow,0)));
				name = (String) tblStatus.getValueAt(checkedRow,1);				
				borrower = (String) tblStatus.getValueAt(checkedRow,2);
				borrowDate = Long.parseLong(((String) tblStatus.getValueAt(checkedRow,3)));
				returnDate = Long.parseLong(((String) tblStatus.getValueAt(checkedRow,4)));
				if(((String) tblStatus.getValueAt(checkedRow,5)).equals("δ�黹"))
				{
					actualReturnDate = 0;
				}
				else
				{
					actualReturnDate = Long.parseLong(((String) tblStatus.getValueAt(checkedRow,5)));					
				}
				
				if(actualReturnDate>returnDate)
				{
					isOvertime = true;
				}
				else
				{
					isOvertime = false;
				}
				Object[] options ={ "ȷ��", "ȡ��" };  
				int isOk = JOptionPane.showOptionDialog(null, "ȷ���޸���", "����",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
				if(isOk==0)
				{
					boolean isModify = new ILibraryAdminImpl(sockethelper).modifyBookStatus(bookId, name, borrower, borrowDate, returnDate, actualReturnDate, isOvertime);
					if(isModify)
						JOptionPane.showMessageDialog(null, "�޸�ͼ��ɹ���");
					else
						JOptionPane.showMessageDialog(null, "�޸�ͼ��ʧ�ܣ�");
				}
			}
		});

		//����
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});

		
		//�ر��¼��ļ���
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
				myLibraryAdminView.refresh();
			 }
			}); 		
	}
	//�رմ���
	public void close()
	{
		myLibraryAdminView.refresh();
		this.dispose();
	}

}
