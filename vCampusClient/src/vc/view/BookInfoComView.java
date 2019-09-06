package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import vc.common.BookInfo;
import vc.common.BookStatusInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryComImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class BookInfoComView extends JFrame {

	
	private String userId;
	private String bookName;
	private int bookId;
	private int page = 0;
	private SocketHelper sockethelper = new SocketHelper();
	private LibraryComView myLibraryComView = null;
	private List<BookInfo> booklist = null;


	private JPanel contentPane;
	private JTable tblBookInfo;
	private JLabel lblCurrentP;
	
	/**
	 * Create the frame.
	 */
	public BookInfoComView( String tmpUserId, String tmpBookName, LibraryComView tmpLibraryComView) {
		this.userId = tmpUserId;
		this.bookName = tmpBookName;
		this.myLibraryComView = tmpLibraryComView;
		sockethelper.getConnection();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("ͼ���ѯ���");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("΢���ź�", Font.PLAIN, 28));
		lblTitle.setBounds(180, 13, 194, 47);
		contentPane.add(lblTitle);
		
		////////////���Ĵ������ʼ��////////////       
        tblBookInfo = new JTable(8,2);
        tblBookInfo.setEnabled(false);//���ñ�񲻿ɱ༭
      
        tblBookInfo.setFont(new Font("΢���ź� Light", Font.PLAIN, 16));
		tblBookInfo.setColumnSelectionAllowed(true);
		tblBookInfo.setForeground(Color.DARK_GRAY);
		tblBookInfo.setSelectionBackground(Color.LIGHT_GRAY);     // ѡ�к����屳��
		tblBookInfo.setGridColor(Color.GRAY);                     // ������ɫ
		tblBookInfo.setBounds(105, 250, 250, 140);
		tblBookInfo.setRowHeight(30);

        // ��һ���п�����Ϊ80���ڶ����п�����Ϊ320
		tblBookInfo.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblBookInfo.getColumnModel().getColumn(1).setPreferredWidth(400);
		tblBookInfo.setValueAt("����", 0, 0);
		tblBookInfo.setValueAt("���", 1, 0);
		tblBookInfo.setValueAt("ISBN", 2, 0);
		tblBookInfo.setValueAt("����", 3, 0);
		tblBookInfo.setValueAt("������", 4, 0);
		tblBookInfo.setValueAt("��������", 5, 0);
		tblBookInfo.setValueAt("�ݲ�", 6, 0);
		tblBookInfo.setValueAt("�Ƿ����", 7, 0);

		//���ر�ͷ��Ϊ��ͷ����һ�� CellRenderer, ��� CellRenderer ��Ԥѡ�߶�Ϊ 0
		tblBookInfo.getTableHeader().setVisible(false);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setPreferredSize(new Dimension(0, 0));
		tblBookInfo.getTableHeader().setDefaultRenderer(renderer);
		tblBookInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
		
		//������Ϣ������ʾ
		booklist = new ILibraryComImpl(userId, this.sockethelper).EnquiryAllBook(bookName);
		System.out.println("booklist.size:"+booklist.size());
		if(booklist.size()==0)
		{
			JOptionPane.showMessageDialog(null, "û���ҵ��Ȿ��Ӵ~");
			this.dispose();
			return;
		}
		System.out.println(booklist.size());
		lblCurrentP = new JLabel("Ŀǰ 0/");
		lblCurrentP.setForeground(Color.DARK_GRAY);
		lblCurrentP.setFont(new Font("΢���ź� Light", Font.PLAIN, 15));
		lblCurrentP.setBounds(417, 390, 84, 30);
		contentPane.add(lblCurrentP);
		
		String str = "Ŀǰ  "+1+"/"+(booklist.size());	
		lblCurrentP.setText(str);
		
		//�õ�һ����ѯ������г�ʼ��
		tblBookInfo.setValueAt(booklist.get(0).getName(), 0, 1);
		tblBookInfo.setValueAt(booklist.get(0).getId(), 1, 1);
		tblBookInfo.setValueAt(booklist.get(0).getIsbn(), 2, 1);
		tblBookInfo.setValueAt(booklist.get(0).getAuthor(), 3, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPub(), 4, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPubDate(), 5, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPos(), 6, 1);
		if(booklist.get(0).isBorrowed())
			tblBookInfo.setValueAt("�ѽ�", 7, 1);
		else
			tblBookInfo.setValueAt("�ɽ�", 7, 1);		
										
		JScrollPane scrollBookInfo = new JScrollPane(tblBookInfo);
		scrollBookInfo.setBounds(113, 73, 388, 275);				
		
		scrollBookInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //����ˮƽ���������ǿɼ�
	    getContentPane().add(scrollBookInfo);
		contentPane.add(scrollBookInfo);

		////////////�����ĳ�ʼ��////////////
		JButton btnBorrow = new JButton("����");
		btnBorrow.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnBorrow.setBounds(394, 450, 84, 30);
		contentPane.add(btnBorrow);
		
		JButton btnReturn = new JButton("����");
		btnReturn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setBounds(501, 450, 84, 30);
		contentPane.add(btnReturn);
		
		JButton btnFirstP = new JButton("��ҳ");
		btnFirstP.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		btnFirstP.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnFirstP.setBounds(123, 361, 84, 27);
		contentPane.add(btnFirstP);
		
		JButton btnPreviousP = new JButton("��һҳ");
		btnPreviousP.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		btnPreviousP.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnPreviousP.setBounds(221, 361, 84, 27);
		contentPane.add(btnPreviousP);
		
		JButton btnNextP = new JButton("��һҳ");
		btnNextP.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		btnNextP.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnNextP.setBounds(319, 361, 84, 27);
		contentPane.add(btnNextP);
		
		JButton btnLastP = new JButton("βҳ");
		btnLastP.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		btnLastP.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnLastP.setBounds(417, 361, 84, 27);
		contentPane.add(btnLastP);
		
		////////////��������////////////	
		
		//����
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//�ж��Ƿ�ɽ裬���û�id����Ŵ���������ˣ��������˽��н��ģ��޸������Ϣ���ͻ���ˢ�½��
				bookId = (int)tblBookInfo.getValueAt( 1, 1);
				Date borrowDate=new Date();
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		        String borrowDateStr=dateFormat.format(borrowDate);
				long borrowDateLong =  Long.parseLong(borrowDateStr);
				//����黹����
				Calendar rightNow = Calendar.getInstance();		
				rightNow.add(Calendar.DAY_OF_YEAR,30);//���ڼ�30��
				Date returnDate=rightNow.getTime();
				String returnDateStr=dateFormat.format(returnDate);
				long returnDateLong =  Long.parseLong(returnDateStr);
				System.out.println("����Ĺ黹���ڣ�"+returnDateStr);
				boolean isBorrowed = new ILibraryComImpl(userId, sockethelper).BorrowBook(0,bookName,borrowDateLong,returnDateLong);
				if(!isBorrowed)
					JOptionPane.showMessageDialog(null, "�Ȿ���Ѿ�������Ŷ~");
				else
				{
					JOptionPane.showMessageDialog(null, "���ĳɹ�~");
					//�޸Ľ�����Ϣ
					tblBookInfo.setValueAt("�ѽ�",7,1);
				}
				
			}
		});
		
		//����
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		
		//��ҳ
		btnFirstP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page = 0;
				lblCurrentP.setText("Ŀǰ  "+(page+1)+"/"+(booklist.size()));
				refresh();
			}
		});
		
		//��һҳ
		btnPreviousP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(page==0)
					JOptionPane.showMessageDialog(null, "�Ѿ��ǵ�һҳ��Ӵ~");
				else
				{
					page--;
					lblCurrentP.setText("Ŀǰ  "+(page+1)+"/"+(booklist.size()));
					refresh();
				}
			}
		});

		//��һҳ
		btnNextP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(page==booklist.size()-1)
					JOptionPane.showMessageDialog(null, "�Ѿ������һҳ��Ӵ~");
				else
				{
					page++;
					lblCurrentP.setText("Ŀǰ  "+(page+1)+"/"+(booklist.size()));
					refresh();
				}
			}
		});

		//βҳ		
		btnLastP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page = booklist.size()-1;
				lblCurrentP.setText("Ŀǰ  "+(page+1)+"/"+(booklist.size()));
				refresh();
			}
		});

		//�ر��¼��ļ���
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
				myLibraryComView.refresh();
			 }
			}); 
	}
	//ˢ�±��
	public void refresh()
	{
		tblBookInfo.setValueAt(booklist.get(page).getName(), 0, 1);
		tblBookInfo.setValueAt(booklist.get(page).getId(), 1, 1);
		tblBookInfo.setValueAt(booklist.get(page).getIsbn(), 2, 1);
		tblBookInfo.setValueAt(booklist.get(page).getAuthor(), 3, 1);
		tblBookInfo.setValueAt(booklist.get(page).getPub(), 4, 1);
		tblBookInfo.setValueAt(booklist.get(page).getPubDate(), 5, 1);
		tblBookInfo.setValueAt(booklist.get(page).getPos(), 6, 1);
		if(booklist.get(page).isBorrowed())
			tblBookInfo.setValueAt("�ѽ�", 7, 1);
		else
			tblBookInfo.setValueAt("�ɽ�", 7, 1);
	}
	//�رմ���
	public void close()
	{
		myLibraryComView.refresh();
		this.dispose();
	}
}
