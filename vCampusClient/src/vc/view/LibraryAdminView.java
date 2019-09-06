package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import vc.common.BookInfo;
import vc.common.BookStatusInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryAdminImpl;
import vc.sendImpl.ILibraryComImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class LibraryAdminView extends JFrame {

	private String userId;
	private SocketHelper sockethelper = new SocketHelper();
	
	private JPanel contentPane;
	private JTextField txtBookName;
	private JTable tblAllBook;
	private DefaultTableModel model;
	private JScrollPane scrollAllBook;
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LibraryAdminView frame = new LibraryAdminView("");
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	

	/**
	 * Create the frame.
	 */
	public LibraryAdminView(String id) {
		userId = id;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 876, 589);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel(" ͼ��ݹ���ϵͳ");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("΢���ź�", Font.PLAIN, 28));
		lblTitle.setBounds(344, 13, 233, 48);
		contentPane.add(lblTitle);
		
		JLabel lblQuery = new JLabel("ͼ���ѯ");
		lblQuery.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		lblQuery.setBounds(47, 72, 84, 24);
		contentPane.add(lblQuery);
		
		txtBookName = new JTextField();
		txtBookName.setForeground(SystemColor.activeCaptionBorder);
		txtBookName.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		txtBookName.setText("������ͼ����");
		txtBookName.setBounds(86, 109, 346, 37);
		contentPane.add(txtBookName);
		txtBookName.setColumns(10);
		
		
		
		JLabel lblManage = new JLabel("ͼ����Ϣ����");
		lblManage.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		lblManage.setBounds(47, 196, 119, 24);
		contentPane.add(lblManage);
		
		
		////////////���Ĵ������ʼ��////////////
		/////
		/* 
         * * ����JTable������ 
         */  
        String[] columnNames =  
        { "���", "ISBN","����", "����","������","��������","�ݲ�λ��","�Ƿ����"};  
  
        
        tblAllBook = new JTable();
        tblAllBook.setFont(new Font("΢���ź� Light", Font.PLAIN, 15));
		tblAllBook.setColumnSelectionAllowed(true);
		tblAllBook.setForeground(SystemColor.activeCaption);
		tblAllBook.setBounds(86, 257, 620, 212);
		
		
		model = new DefaultTableModel(columnNames, 0){
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		        // ���������� false���κε�Ԫ�񶼲��ñ༭��
		        return false;
		    }
		};
		List<BookInfo> bookList = new ILibraryAdminImpl(this.sockethelper).EnquiryAllBook();
		
		for(int i = 0; i < bookList.size(); i++)
		{
			System.out.println(bookList.size());
			BookInfo bookTemp = bookList.get(i);
			System.out.println(bookTemp.getName());
			if(bookTemp.isBorrowed())
			{
				Object[] rowData = { bookTemp.getId(), bookTemp.getIsbn(), bookTemp.getName(),  bookTemp.getAuthor(), 
						bookTemp.getPub(),  bookTemp.getPubDate(), bookTemp.getPos(),"�ѽ�"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { bookTemp.getId(), bookTemp.getIsbn(), bookTemp.getName(),  bookTemp.getAuthor(), 
						bookTemp.getPub(),  bookTemp.getPubDate(), bookTemp.getPos(),"�ɽ�"};
			    model.addRow(rowData);
			}
		}
		tblAllBook.setModel(model);
		
		tblAllBook.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblAllBook.getColumnModel().getColumn(1).setPreferredWidth(200);
		tblAllBook.getColumnModel().getColumn(2).setPreferredWidth(200);
		tblAllBook.getColumnModel().getColumn(3).setPreferredWidth(200);
		tblAllBook.getColumnModel().getColumn(4).setPreferredWidth(200);
		tblAllBook.getColumnModel().getColumn(5).setPreferredWidth(80);
		tblAllBook.getColumnModel().getColumn(6).setPreferredWidth(200);
		tblAllBook.getColumnModel().getColumn(7).setPreferredWidth(80);
		tblAllBook.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		
		scrollAllBook = new JScrollPane(tblAllBook);  
		scrollAllBook.setBounds(80, 250, 635, 225);
		scrollAllBook.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //����ˮƽ��������Ҫʱ�ɼ�
	    getContentPane().add(scrollAllBook);
		contentPane.add(scrollAllBook);

		////////////�����ĳ�ʼ��////////////
		JButton btnInfoQuery = new JButton("��Ϣ��ѯ");
		btnInfoQuery.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnInfoQuery.setBounds(458, 109, 119, 37);
		contentPane.add(btnInfoQuery);
		
		JButton btnStatusQuery = new JButton("״̬��ѯ");
		btnStatusQuery.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnStatusQuery.setBounds(596, 109, 119, 37);
		contentPane.add(btnStatusQuery);
		
		JButton btnRefesh = new JButton("ˢ��");
		btnRefesh.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnRefesh.setBackground(SystemColor.inactiveCaption);
		btnRefesh.setBounds(559, 488, 84, 30);
		contentPane.add(btnRefesh);
		
		JButton btnReturn = new JButton("����");
		btnReturn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setBounds(721, 488, 84, 30);
		contentPane.add(btnReturn);
		
		JButton btnAdd = new JButton("����");
		btnAdd.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnAdd.setBounds(729, 271, 76, 24);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("ɾ��");
		btnDelete.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnDelete.setBounds(729, 330, 76, 24);
		contentPane.add(btnDelete);
		
		JButton btnModify = new JButton("�޸�");
		btnModify.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnModify.setBounds(729, 392, 76, 24);
		contentPane.add(btnModify);
		
		////////////��������////////////
		
		//��Ϣ��ѯ
		btnInfoQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��ͼ����Ϣ����
				openBookInfoView();
			}
		});

		//״̬��ѯ
		btnStatusQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��ͼ��״̬��Ϣ����
				openBookStatusView();
			}
		});

		//����ͼ��
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//������ͼ�����
				openAddBookView();
			}
		});

		//ɾ��ͼ��
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblAllBook.getSelectedRow() == -1)
				{
				     JOptionPane.showMessageDialog(null, "��ѡ����Ҫɾ����ͼ�飡");
				     return;
				 }
				Object[] options ={ "ȷ��", "ȡ��" };  
				int isOk = JOptionPane.showOptionDialog(null, "ȷ��ɾ���Ȿ����", "����",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
				if(isOk==0)
				{
					if(tblAllBook.getValueAt(tblAllBook.getSelectedRow(), 7).equals("�ѽ�"))
					{
						JOptionPane.showMessageDialog(null, "��ͼ�鴦�ڽ����У�����ɾ����");
					}
					else
					{
						int bookId = (int)tblAllBook.getValueAt(tblAllBook.getSelectedRow(), 0);
						boolean isDelete = new ILibraryAdminImpl(sockethelper).deleteBook(bookId);
						if(isDelete)
							JOptionPane.showMessageDialog(null, "ɾ��ͼ��ɹ���");						
						else
							JOptionPane.showMessageDialog(null, "ɾ��ͼ��ʧ�ܣ�");
						refresh();
					}

				}
				
			}
		});
		
		//�޸�ͼ��
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblAllBook.getSelectedRow() == -1)
				{
				     JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�޸ĵ�ͼ�飡");
				     return;
				 }			
				//��ͼ���޸Ľ���
				openModifyBookView();
			}
		});
		
		//ˢ��
		btnRefesh.addActionListener(new ActionListener() {
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
		System.out.println("kaishiye��"+bookName);
		BookInfoAdminView	myBookInfoView = new BookInfoAdminView(userId, bookName,this);
		myBookInfoView.setVisible(true);
		myBookInfoView.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	//��ͼ��״̬���棬��������Ϣ��ͼ������������������ˣ����ܷ������˻ش�����Ϣ��������ʾ
	public void openBookStatusView()
	{
		String bookName = txtBookName.getText();
		BookStatusView	myBookStatusView = new BookStatusView(bookName,this);
		myBookStatusView.setVisible(true);
		myBookStatusView.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	//������ͼ�����
	public void openAddBookView()
	{
		BookAddView	myBookAddView = new BookAddView(this);
		myBookAddView.setVisible(true);
		myBookAddView.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	//��ͼ���޸Ľ���
	public void openModifyBookView()
	{
		int bookId = (int)tblAllBook.getValueAt(tblAllBook.getSelectedRow(), 0);
		BookModifyView	myBookModifyView = new BookModifyView(bookId, this);
		myBookModifyView.setVisible(true);
		myBookModifyView.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	//�رմ���
	public void close()
	{
		this.dispose();
	}
	
	//ˢ�±��
	public void refresh()
	{
		//��ձ������
		while(model.getRowCount()>0)
		{
			model.removeRow(model.getRowCount()-1);
		}
		List<BookInfo> bookList = new ILibraryAdminImpl(this.sockethelper).EnquiryAllBook();		
		for(int i = 0; i < bookList.size(); i++)
		{
			System.out.println(bookList.size());
			BookInfo bookTemp = bookList.get(i);
			System.out.println(bookTemp.getName());
			if(bookTemp.isBorrowed())
			{
				Object[] rowData = { bookTemp.getId(), bookTemp.getIsbn(), bookTemp.getName(),  bookTemp.getAuthor(), 
						bookTemp.getPub(),  bookTemp.getPubDate(), bookTemp.getPos(),"�ѽ�"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { bookTemp.getId(), bookTemp.getIsbn(), bookTemp.getName(),  bookTemp.getAuthor(), 
						bookTemp.getPub(),  bookTemp.getPubDate(), bookTemp.getPos(),"�ɽ�"};
			    model.addRow(rowData);
			}
		}
		tblAllBook.setModel(model);
	}
}
