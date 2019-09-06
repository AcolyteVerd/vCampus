package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import vc.common.BookInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryAdminImpl;
import vc.sendImpl.ILibraryComImpl;
import java.awt.SystemColor;

public class BookAddView extends JFrame {

	private JPanel contentPane;
	private SocketHelper sockethelper = new SocketHelper();
	private LibraryAdminView myLibraryAdminView = new LibraryAdminView("");
	private  JTable tblBookInfo;

	////������ͼ����Ϣ
	protected String name;
	protected String isbn;
	protected String author;
	protected String pub;
	protected long pubDate;
	protected String pos;
	protected boolean isBorrowed;
	protected int bookNum;
	
	/**
	 * Create the frame.
	 */
	public BookAddView(LibraryAdminView tmpLibraryAdminView) {

		this.myLibraryAdminView = tmpLibraryAdminView;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("����ͼ��");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("΢���ź�", Font.PLAIN, 28));
		lblTitle.setBounds(196, 13, 194, 47);
		contentPane.add(lblTitle);
		
		////////////���Ĵ������ʼ��////////////       
        tblBookInfo = new JTable(7,2);
        
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
		tblBookInfo.setValueAt("ISBN", 1, 0);
		tblBookInfo.setValueAt("����", 2, 0);
		tblBookInfo.setValueAt("������", 3, 0);
		tblBookInfo.setValueAt("��������", 4, 0);
		tblBookInfo.setValueAt("�ݲ�", 5, 0);
		tblBookInfo.setValueAt("�鼮����", 6, 0);
		tblBookInfo.setValueAt("", 0, 1);
		tblBookInfo.setValueAt("", 1, 1);
		tblBookInfo.setValueAt("", 2, 1);
		tblBookInfo.setValueAt("", 3, 1);
		tblBookInfo.setValueAt("", 4, 1);
		tblBookInfo.setValueAt("", 5, 1);
		tblBookInfo.setValueAt("", 6, 1);
		//���ر�ͷ��Ϊ��ͷ����һ�� CellRenderer, ��� CellRenderer ��Ԥѡ�߶�Ϊ 0
		tblBookInfo.getTableHeader().setVisible(false);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setPreferredSize(new Dimension(0, 0));
		tblBookInfo.getTableHeader().setDefaultRenderer(renderer);
		tblBookInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);       
		
		
		JScrollPane scrollBookInfo = new JScrollPane(tblBookInfo);  
		scrollBookInfo.setBounds(113, 73, 368, 275);				
		
		scrollBookInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //����ˮƽ��������Ҫʱ�ɼ�
	    getContentPane().add(scrollBookInfo);
		contentPane.add(scrollBookInfo);
		
		////////////�����ĳ�ʼ��////////////		
		JButton btnSubmit = new JButton("�ύ");		
		btnSubmit.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnSubmit.setBounds(385, 381, 84, 30);
		contentPane.add(btnSubmit);
		
		JButton btnReturn = new JButton("����");		
		btnReturn.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setBounds(496, 381, 84, 30);
		contentPane.add(btnReturn);
		
		////////////��������////////////
		
		//����
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		
		//�ύ
		btnSubmit.addActionListener(new ActionListener() {
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
					isbn = (String) tblBookInfo.getValueAt(1,1);
					author = (String) tblBookInfo.getValueAt(2,1);
					pub = (String) tblBookInfo.getValueAt(3,1);
					pubDate = Long.parseLong(((String) tblBookInfo.getValueAt(4,1)));
					pos = (String) tblBookInfo.getValueAt(5,1);
					bookNum = Integer.parseInt(((String)tblBookInfo.getValueAt(6,1)));
					if((bookNum!=0))
					{
						Object[] options ={ "ȷ��", "ȡ��" };  
						int isOk = JOptionPane.showOptionDialog(null, "ȷ���ύ��", "����",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
						if(isOk==0)
						{
							boolean isAdd = new ILibraryAdminImpl(sockethelper).addBook(name, isbn, author, pub, pubDate, pos, bookNum);
							if(isAdd)
								JOptionPane.showMessageDialog(null, "����ͼ��ɹ���");
							else
								JOptionPane.showMessageDialog(null, "����ͼ��ʧ�ܣ�");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "ͼ����������Ϊ0��");
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
	//�رմ���
	public void close()
	{
		myLibraryAdminView.refresh();
		this.dispose();
	}
}
