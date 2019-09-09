package vc.view;

import java.awt.Component;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import vc.common.StudentRollInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IStudentImpl;

public class CheckStudentView extends JFrame{

	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private TextField checkID = new TextField(20);
	private TextField checkName = new TextField(20);
	private TextField checkSex = new TextField(20);
	private TextField checkAge = new TextField(20);
	private TextField checkBirthDate = new TextField(20);
	private TextField checkAddress = new TextField(20);
	private TextField checkMajor = new TextField(20);
	private TextField checkDorm = new TextField(20);
	private TextField searchIDText = new TextField(50);
	private JButton searchButton = new JButton("����");
	private JButton searchAll = new JButton("��ѯ����");
	private JTextField textField_StuId = new JTextField();
	private JScrollPane scrollPane_StuInfo = new JScrollPane();
	private JTable table_StuInfo;
	private JFrame CheckStudentFrame;
	private JPanel CheckStudentPanel;
	private JTable table_StuInfo2 = new JTable();
	JScrollPane scrollPane_StuInfo2 = new JScrollPane();
	
	public CheckStudentView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel1();
		this.setVisible(false);
		run();
	}

	private void setMainPanel1() {
		
		new JFrame();
		setVisible(true);
		setBounds(10, 20, 640, 357);
		setTitle("����ѧ��ѧ����Ϣ");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		CheckStudentPanel = new JPanel();
		
		Box box = Box.createVerticalBox();
		
		box.add(textField_StuId); 
		box.add(searchButton);  
        box.add(searchAll);
        CheckStudentPanel.add(box);
        
        CheckStudentPanel.add(scrollPane_StuInfo);
		add(CheckStudentPanel);    
			
	}
	private void run() {
		
		this.searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
		      {
		        System.out.println("��ѧ�Ų���ѧ����Ϣ");
		        String stuId = CheckStudentView.this.textField_StuId.getText();
		        System.out.println(stuId);
		        if (stuId.equals(""))
		        {
		          JOptionPane.showMessageDialog(null, "������ѧ��ID");
		          return;
		        }
		        scrollPane_StuInfo.setViewportView(getStuTableById(stuId));
		      }
		});
		
		this.searchAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
		      {
		        System.out.println("��������ѧ����Ϣ");
		        		        
		        scrollPane_StuInfo2.setViewportView(getStudentTable());
		      }
		});
	}


	protected JTable getStudentTable() {

		table_StuInfo2 = new JTable();
		String[] columns = { "ѧ��ID", "����", "����", "�Ա�", "��������", "��ַ", "רҵ", "����" };
		DefaultTableModel model = new DefaultTableModel(columns, 0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table_StuInfo2.setModel(model);
		List<StudentRollInfo> list = new IStudentImpl(this.sockethelper).EnquiryAllStu(null);

		for (int i = 0; i < list.size(); i++)
	    {
	      StudentRollInfo studentList = (StudentRollInfo)list.get(i);
	      Object[] rowData = { studentList.getId(), studentList.getName(), studentList.getAge(), studentList.getGender(), studentList.getBirthday(), studentList.getBirthPlace(), studentList.getDepartment(), studentList.getDormitory() };
	      model.addRow(rowData);
	    }
	    return this.table_StuInfo2;
	
	}

	protected JTable getStuTableById(String stuId) {
		
		this.table_StuInfo = new JTable();
	    String[] columns = { "ѧ��ID", "����", "����", "�Ա�", "��������", "��ַ", "רҵ", "����" };
	    DefaultTableModel model = new DefaultTableModel(columns, 0);
	    this.table_StuInfo.setModel(model);
	    StudentRollInfo stu = new StudentRollInfo(stuId, "", "", "", "", "", "", "");
	    List<StudentRollInfo> list = new IStudentImpl(this.sockethelper).EnquiryStuById(stu);
	    if (list.isEmpty())
	    {
	      JOptionPane.showMessageDialog(null, "��ѯ����ѧ����Ϣ");
	      return this.table_StuInfo;
	    }
	    StudentRollInfo studentList = (StudentRollInfo)list.get(0);
	    Object[] rowData = { studentList.getId(), studentList.getName(), studentList.getAge(), studentList.getGender(), studentList.getBirthday(), studentList.getBirthPlace(), studentList.getDepartment(), studentList.getDormitory() };
	    model.addRow(rowData);
	    
	    return this.table_StuInfo;
	}

}
