package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

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

import vc.common.BookInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryAdminImpl;
import vc.sendImpl.ILibraryComImpl;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class BookModifyView extends JFrame {

	private int bookId;
	private SocketHelper sockethelper = new SocketHelper();
	private LibraryAdminView myLibraryAdminView = null;
	
	////�޸ĺ��ͼ����Ϣ
	public String name;
	public String author;
	public int id;
	public String isbn;
	public String pub;
	public long pubDate;
	public String pos;	
	public boolean isBorrowed;

	private JPanel contentPane;
	private JTable tblBookInfo;
	/**
	 * Create the frame.
	 */
	public BookModifyView(int tmpBookId, LibraryAdminView tmpLibraryAdminView) {
		this.bookId = tmpBookId;
		this.myLibraryAdminView = tmpLibraryAdminView;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("ͼ����Ϣ�޸�");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("΢���ź�", Font.PLAIN, 28));
		lblTitle.setBounds(209, 13, 194, 47);
		contentPane.add(lblTitle);
		
		////////////���Ĵ������ʼ��////////////
        /* 
         * * ����JTable����Ĭ�ϵĿ�Ⱥ͸߶� 
         */  
        tblBookInfo = new JTable(8,2);
        tblBookInfo.setFont(new Font("΢���ź� Light", Font.PLAIN, 16));
		tblBookInfo.setColumnSelectionAllowed(true);
		tblBookInfo.setForeground(Color.DARK_GRAY);
		tblBookInfo.setSelectionBackground(Color.LIGHT_GRAY);     // ѡ�к����屳��
		tblBookInfo.setGridColor(Color.GRAY);                     // ������ɫ
		tblBookInfo.setBounds(105, 250, 250, 140);
		tblBookInfo.setRowHeight(30);

		// ��һ���п�����Ϊ80���ڶ����п�����Ϊ320
		tblBookInfo.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblBookInfo.getColumnModel().getColumn(1).setPreferredWidth(320);
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
		List<BookInfo> booklist = new ILibraryAdminImpl(this.sockethelper).EnquiryABookById(bookId);
		System.out.println("booklist.size:"+booklist.size());
		if(booklist.size()==0)
		{
			JOptionPane.showMessageDialog(null, "û���ҵ��Ȿ��Ӵ~");
			this.dispose();
		} 
		//����String����������ת��ΪString������ʾ
		tblBookInfo.setValueAt(booklist.get(0).getName(), 0, 1);
		tblBookInfo.setValueAt( String.valueOf(booklist.get(0).getId()) , 1, 1);
		tblBookInfo.setValueAt(booklist.get(0).getIsbn(), 2, 1);
		tblBookInfo.setValueAt(booklist.get(0).getAuthor(), 3, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPub(), 4, 1);
		tblBookInfo.setValueAt( String.valueOf(booklist.get(0).getPubDate()), 5, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPos(), 6, 1);
		if(booklist.get(0).isBorrowed())
		{
			tblBookInfo.setValueAt("�ѽ�", 7, 1);
		}
		else
		{
			tblBookInfo.setValueAt("�ɽ�", 7, 1);
		}	
		
		JScrollPane scrollBookInfo = new JScrollPane(tblBookInfo);  
		scrollBookInfo.setBounds(106, 73, 399, 284);				
		
		scrollBookInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //����ˮƽ��������Ҫʱ�ɼ�
	    getContentPane().add(scrollBookInfo);
		contentPane.add(scrollBookInfo);

		////////////�����ĳ�ʼ��////////////
		JButton btnModify = new JButton("�޸�");
		btnModify.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnModify.setBounds(410, 386, 84, 30);
		contentPane.add(btnModify);
		
		JButton btnReturn = new JButton("����");
		btnReturn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setBounds(508, 386, 84, 30);
		contentPane.add(btnReturn);
		
		////////////��������////////////
		
		//�޸�
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tblBookInfo.getCellEditor()!=null)
					tblBookInfo.getCellEditor().stopCellEditing();//ǿ��JTable�����༭״̬
				name = (String) tblBookInfo.getValueAt(0,1);
				id = Integer.parseInt((String) (tblBookInfo.getValueAt(1,1)));
				isbn = (String) tblBookInfo.getValueAt(2,1);
				author = (String) tblBookInfo.getValueAt(3,1);
				pub = (String) tblBookInfo.getValueAt(4,1);
				pubDate = Long.parseLong((String) (tblBookInfo.getValueAt(5,1)));
				pos = (String) tblBookInfo.getValueAt(6,1);
				if(((String) tblBookInfo.getValueAt(7,1)).equals("�ɽ�"))
					isBorrowed = false;
				else
					isBorrowed = true;
				System.out.println(pubDate);
				Object[] options ={ "ȷ��", "ȡ��" };  
				int isOk = JOptionPane.showOptionDialog(null, "ȷ���޸���", "����",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
				if(isOk==0)
				{
					boolean isModifi = new ILibraryAdminImpl(sockethelper).modifyBook(id, isbn, name, author, pub, pubDate, pos, isBorrowed);
					if(isModifi)
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
