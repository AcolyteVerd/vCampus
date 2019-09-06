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
import javax.swing.UIManager;
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

public class BookInfoAdminView extends JFrame {

	private String userId;
	private String bookName;
	private int page = 0; //��ǰҳ������ҳ
	private SocketHelper sockethelper = new SocketHelper();
	private LibraryAdminView myLibraryAdminView = null;	
	private List<BookInfo> booklist = null;
	
	private JPanel contentPane;	
	private JTable tblBookInfo;
	private JLabel lblCurrentP;
	
	////�޸ĺ��ͼ����Ϣ
	public String name;
	public String author;
	public int id;
	public String isbn;
	public String pub;
	public long pubDate;
	public String pos;	
	public boolean isBorrowed;

	/**
	 * Create the frame.
	 */
	public BookInfoAdminView(String tmpUserId, String tmpBookName, LibraryAdminView tmpLibraryAdminView) {
		this.userId = tmpBookName;
		this.bookName = tmpBookName;
		this.myLibraryAdminView = tmpLibraryAdminView;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 552);
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
		booklist = new ILibraryAdminImpl(this.sockethelper).EnquiryABook(bookName);
		if(booklist.size()==0)
		{
			JOptionPane.showMessageDialog(null, "û���ҵ��Ȿ��Ӵ~");
			this.dispose();
		}
		
		lblCurrentP = new JLabel("Ŀǰ 0/");
		lblCurrentP.setForeground(Color.DARK_GRAY);
		lblCurrentP.setFont(new Font("΢���ź� Light", Font.PLAIN, 15));
		lblCurrentP.setBounds(409, 406, 84, 30);
		contentPane.add(lblCurrentP);
		
		String str = "Ŀǰ  "+1+"/"+(booklist.size());	
		lblCurrentP.setText(str);
		
		//�õ�һ����ѯ������г�ʼ��
		tblBookInfo.setValueAt(booklist.get(0).getName(), 0, 1);
		tblBookInfo.setValueAt( String.valueOf(booklist.get(0).getId()) , 1, 1);
		tblBookInfo.setValueAt(booklist.get(0).getIsbn(), 2, 1);
		tblBookInfo.setValueAt(booklist.get(0).getAuthor(), 3, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPub(), 4, 1);
		tblBookInfo.setValueAt( String.valueOf(booklist.get(0).getPubDate()), 5, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPos(), 6, 1);
		if(booklist.get(0).isBorrowed())
			tblBookInfo.setValueAt("�ѽ�", 7, 1);
		else
			tblBookInfo.setValueAt("�ɽ�", 7, 1);	
		
		JScrollPane scrollBookInfo = new JScrollPane(tblBookInfo);  
		scrollBookInfo.setBounds(94, 73, 399, 284);				
		
		scrollBookInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //����ˮƽ��������Ҫʱ�ɼ�
	    getContentPane().add(scrollBookInfo);
		contentPane.add(scrollBookInfo);
		
		////////////�����ĳ�ʼ��////////////
		JButton btnReturn = new JButton("����");
		btnReturn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setBounds(507, 462, 84, 30);
		contentPane.add(btnReturn);
		
		JButton btnFirstP = new JButton("��ҳ");
		btnFirstP.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		btnFirstP.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnFirstP.setBounds(104, 377, 84, 27);
		contentPane.add(btnFirstP);
		
		JButton btnPreviousP = new JButton("��һҳ");
		
		btnPreviousP.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		btnPreviousP.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnPreviousP.setBounds(202, 377, 84, 27);
		contentPane.add(btnPreviousP);
		
		JButton btnNextP = new JButton("��һҳ");
		btnNextP.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		btnNextP.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnNextP.setBounds(300, 377, 84, 27);
		contentPane.add(btnNextP);
		
		JButton btnLastP = new JButton("βҳ");
		
		btnLastP.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		btnLastP.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnLastP.setBounds(398, 377, 84, 27);
		contentPane.add(btnLastP);
		
		JButton btnModify = new JButton("�޸�");
		
		btnModify.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnModify.setBackground(SystemColor.inactiveCaption);
		btnModify.setBounds(507, 172, 84, 30);
		contentPane.add(btnModify);
		
		////////////��������////////////
		
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
		
		//�޸�
		btnModify.addActionListener(new ActionListener() {
			private boolean flag = true;

			public void actionPerformed(ActionEvent e) {
				if(tblBookInfo.getCellEditor()!=null)
					tblBookInfo.getCellEditor().stopCellEditing();//ǿ��JTable�����༭״̬
				if((tblBookInfo.getValueAt(0,1).equals(""))||(tblBookInfo.getValueAt(1,1).equals(""))||(tblBookInfo.getValueAt(2,1).equals(""))
						||(tblBookInfo.getValueAt(3,1).equals(""))||(tblBookInfo.getValueAt(4,1).equals(""))||(tblBookInfo.getValueAt(5,1).equals(""))
						||(tblBookInfo.getValueAt(6,1).equals("")))
				{
					JOptionPane.showMessageDialog(null, "�пհ���Ϣ���뽫��Ϣ��д������");
				}
				else
				{
					name = (String) tblBookInfo.getValueAt(0,1);
					id =  Integer.parseInt(((String) tblBookInfo.getValueAt(1,1)));
					isbn = (String) tblBookInfo.getValueAt(2,1);
					author = (String) tblBookInfo.getValueAt(3,1);
					pub = (String) tblBookInfo.getValueAt(4,1);
					pubDate = Long.parseLong(((String) tblBookInfo.getValueAt(5,1)));
					pos = (String) tblBookInfo.getValueAt(6,1);
					if(tblBookInfo.getValueAt(7,1).equals("�ѽ�"))
						isBorrowed = true;
					else if(tblBookInfo.getValueAt(7,1).equals("�ɽ�"))
						isBorrowed = false;
					else
					{
						flag  = false;
						JOptionPane.showMessageDialog(null, "����ȷ��дͼ�����״̬����д���ѽ衱���ߡ��ɽ衱��");
						return;
					}
					Object[] options ={ "ȷ��", "ȡ��" };  
					int isOk = JOptionPane.showOptionDialog(null, "ȷ���޸���", "����",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
					if(isOk==0)
					{
						boolean isAdd = new ILibraryAdminImpl(sockethelper).modifyBook(id, isbn, name, author, pub, pubDate, pos, isBorrowed);
						if(isAdd)
							JOptionPane.showMessageDialog(null, "�޸�ͼ��ɹ���");
						else
							JOptionPane.showMessageDialog(null, "�޸�ͼ��ʧ�ܣ�");
					}
					
				}
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
	//ˢ�±��
	public void refresh()
	{
		tblBookInfo.setValueAt(booklist.get(page).getName(), 0, 1);
		tblBookInfo.setValueAt( String.valueOf(booklist.get(page).getId()) , 1, 1);
		tblBookInfo.setValueAt(booklist.get(page).getIsbn(), 2, 1);
		tblBookInfo.setValueAt(booklist.get(page).getAuthor(), 3, 1);
		tblBookInfo.setValueAt(booklist.get(page).getPub(), 4, 1);
		tblBookInfo.setValueAt( String.valueOf(booklist.get(page).getPubDate()), 5, 1);
		tblBookInfo.setValueAt(booklist.get(page).getPos(), 6, 1);
		if(booklist.get(page).isBorrowed())
			tblBookInfo.setValueAt("�ѽ�", 7, 1);
		else
			tblBookInfo.setValueAt("�ɽ�", 7, 1);	
	}
	//�رմ���
	public void close()
	{
		myLibraryAdminView.refresh();
		this.dispose();
	}
}
