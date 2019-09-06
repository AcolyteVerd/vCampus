package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import vc.common.BookStatusInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryComImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

/*
 * �򿪴�ҳ��ʱ�������û�id��ѯ������Ϣ��������ʾ
 */
public class LibraryComView extends JFrame {

	private JPanel contentPane;
	private JTextField txtBookName;
	private JTable tblBorrow;
	private JScrollPane scrollBorrow;
	private DefaultTableModel model;
	
	private String userId;
	private SocketHelper sockethelper = new SocketHelper();

	/**
	 * Create the frame.
	 */
	public LibraryComView(String id) {
		userId = id;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 666);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel(" ͼ���");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("΢���ź�", Font.PLAIN, 28));
		lblTitle.setBounds(176, 24, 233, 48);
		contentPane.add(lblTitle);
		
		JLabel lblQuery = new JLabel("ͼ���ѯ");
		lblQuery.setFont(new Font("΢���ź�", Font.PLAIN, 19));
		lblQuery.setBounds(67, 83, 84, 24);
		contentPane.add(lblQuery);
		
		txtBookName = new JTextField();
		txtBookName.setText("������ͼ����");
		txtBookName.setForeground(SystemColor.activeCaptionBorder);
		txtBookName.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		txtBookName.setColumns(10);
		txtBookName.setBounds(106, 137, 412, 26);
		contentPane.add(txtBookName);
		
		
		JLabel lblBorrow = new JLabel("�ҵĽ���");
		lblBorrow.setFont(new Font("΢���ź�", Font.PLAIN, 19));
		lblBorrow.setBounds(67, 193, 84, 24);
		contentPane.add(lblBorrow);
		
		
		
			
		////////////���Ĵ������ʼ��////////////    
		/* 
         * * ����JTable������ 
         */  
        String[] columnNames =  
        { "���", "����", "ʱ��","Ӧ��ʱ��","ʵ�ʹ黹ʱ��"};  
  
        
        tblBorrow = new JTable();
		tblBorrow.setColumnSelectionAllowed(true);
		tblBorrow.setForeground(SystemColor.activeCaption);
		tblBorrow.setBounds(105, 250, 250, 140);
		
		model = new DefaultTableModel(columnNames, 0){
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		        // ���������� false���κε�Ԫ�񶼲��ñ༭��
		        return false;
		    }
		};
		List<BookStatusInfo> borrowlist = new ILibraryComImpl(userId, this.sockethelper).EnquiryRecord(this.userId);
		
		for(int i = 0; i < borrowlist.size(); i++)
		{
			System.out.println(borrowlist.size());
			BookStatusInfo bookStatusTemp = borrowlist.get(i);
			System.out.println(bookStatusTemp.getName());
			if(bookStatusTemp.getActualReturnDate()==0)
			{
				Object[] rowData = { bookStatusTemp.getId(), bookStatusTemp.getName(),  bookStatusTemp.getBorrowDate(), 
						bookStatusTemp.getReturnDate(), "δ�黹"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { bookStatusTemp.getId(), bookStatusTemp.getName(),  bookStatusTemp.getBorrowDate(), 
					bookStatusTemp.getReturnDate(), bookStatusTemp.getActualReturnDate()};
			    model.addRow(rowData);
			}
		}
		tblBorrow.setModel(model);
		scrollBorrow = new JScrollPane(tblBorrow);  
		scrollBorrow.setBounds(106, 245, 412, 282);
	    getContentPane().add(scrollBorrow);
		contentPane.add(scrollBorrow);
		
		////////////�����ĳ�ʼ��////////////	
		JButton btnQuery = new JButton("����");
		btnQuery.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnQuery.setBounds(548, 138, 119, 24);
		contentPane.add(btnQuery);
		
		JButton btnReturnBook = new JButton("�黹");
		btnReturnBook.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnReturnBook.setBounds(548, 315, 119, 24);
		contentPane.add(btnReturnBook);
	
		JButton btnRefresh = new JButton("ˢ��");
		btnRefresh.setBackground(SystemColor.inactiveCaption);
		btnRefresh.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnRefresh.setBounds(421, 561, 84, 30);
		contentPane.add(btnRefresh);
		
		JButton btnReturn = new JButton("����");
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnReturn.setBounds(583, 561, 84, 30);
		contentPane.add(btnReturn);
		
		////////////��������////////////
		
		//����
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openBookInfoView();				
			}			
		});

		//�黹
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//����黹��ť�����黹��Ϣ������������ѧ�ţ�����������ˣ��������˽��й黹�жϣ�ɾ������������Ϣ���ͻ���ɾ��������Ϣ��ˢ��ҳ��
				
				if (tblBorrow.getSelectedRow() == -1)
				{
				     JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�黹��ͼ�飡");
				     return;
				 }
				if (!tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 4).equals("δ�黹"))
				{
				     JOptionPane.showMessageDialog(null, "��ͼ���ѹ黹��");
				     return;
				}
				
				int bookId = (int)tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 0);				
				String bookName = (String) tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 1);
				long borrowDate = (long)tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 2);
				long returnDate = (long)tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 3);
				Date actualReturnDate=new Date();
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		        String actualReturnDateStr=dateFormat.format(actualReturnDate);
				long actualReturnDateLong =  Long.parseLong(actualReturnDateStr);
				boolean flag = new ILibraryComImpl(userId, sockethelper).ReturnBook(bookId,bookName,borrowDate,returnDate,actualReturnDateLong);
				if(flag)
				{
					JOptionPane.showMessageDialog(null, "�黹�ɹ���");					
					tblBorrow.setValueAt(actualReturnDateStr,tblBorrow.getSelectedRow(),4);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "�黹ʧ�ܣ�");
				}
			}
		});

		//ˢ��
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		//����	
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});


        
	}
	
	//��������Ϣ��ͼ������������ͼ����棬��ͼ����Ϣ���棬��ͼ����潫��Ϣ����������ˣ����ܷ������˻ش�����Ϣ��������ʾ
	public void openBookInfoView()
	{
		String bookName = txtBookName.getText();
		BookInfoComView	myBookInfoView = new BookInfoComView(userId, bookName,this);
		myBookInfoView.setVisible(true);
		myBookInfoView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//refresh();
	}
	
	//ˢ�±��
	public void refresh()
	{
		List<BookStatusInfo> borrowlist = new ILibraryComImpl(userId, this.sockethelper).EnquiryRecord(this.userId);
		//��ձ������
		while(model.getRowCount()>0)
		{
			model.removeRow(model.getRowCount()-1);
		}
		for(int i = 0; i < borrowlist.size(); i++)
		{
			BookStatusInfo bookStatusTemp = borrowlist.get(i);
			if(bookStatusTemp.getActualReturnDate()==0)
			{
				Object[] rowData = { bookStatusTemp.getId(), bookStatusTemp.getName(),  bookStatusTemp.getBorrowDate(), 
						bookStatusTemp.getReturnDate(), "δ�黹"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { bookStatusTemp.getId(), bookStatusTemp.getName(),  bookStatusTemp.getBorrowDate(), 
					bookStatusTemp.getReturnDate(), bookStatusTemp.getActualReturnDate()};
			    model.addRow(rowData);
			}
		}
		tblBorrow.setModel(model);
	}
	
	//�رմ���
	public void close()
	{
		this.dispose();
	}

}
