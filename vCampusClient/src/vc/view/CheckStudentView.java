package vc.view;

import java.awt.Component;
import java.awt.Container;
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

	public JFrame mainFrame;
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
	private JButton checkConfirmButton = new JButton("ȷ��");
	private JTextField textField_StuId = new JTextField();
	private JScrollPane scrollPane_StuInfo = new JScrollPane();
	private JTable table_StuInfo;
	private JFrame CheckStudentFrame;
	 JPanel CheckStudentPanel;
	
	public CheckStudentView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel1();
		this.setVisible(false);
		run();
	}

	private void setMainPanel1() {
		// TODO Auto-generated method stub
		mainFrame = new JFrame();
		mainFrame.setVisible(true);
		mainFrame.setBounds(10, 20, 640, 357);
		mainFrame.setTitle("����ѧ��ѧ����Ϣ");
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		CheckStudentPanel = new JPanel();
		
		Box box = Box.createVerticalBox();
		
		box.add(textField_StuId); 
		box.add(searchButton);  
        //box.add(checkConfirmButton);
        CheckStudentPanel.add(box);
        
        CheckStudentPanel.add(scrollPane_StuInfo);
		mainFrame.add(CheckStudentPanel);    
		
		
	}
	private void run() {
		// TODO Auto-generated method stub
		this.searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
		      {
		        System.out.println("����ѧ����Ϣ");
		        String stuId = CheckStudentView.this.textField_StuId.getText();
		        System.out.println(stuId);
		        if (stuId.equals(""))
		        {
		          JOptionPane.showMessageDialog(null, "������ѧ��ID");
		          return;
		        }
		        scrollPane_StuInfo.setViewportView(getStuTableById(stuId));
		       // CheckStudentView.this.scrollPane_StuInfo.setViewportView(CheckStudentView.this.getStuTableById(stuId));
		      }
		});
	}


	protected JTable getStuTableById(String stuId) {
		// TODO Auto-generated method stub
		
		this.table_StuInfo = new JTable();
	    String[] columns = { "ѧ��ID", "����", "����", "�Ա�", "��������", "��ַ", "רҵ", "����" };
	    DefaultTableModel model = new DefaultTableModel(columns, 0);
	    this.table_StuInfo.setModel(model);
	    StudentRollInfo stu = new StudentRollInfo(stuId, "", "", "", "", "", "", ""/*, "", "", "", ""*/);
	    List<StudentRollInfo> list = new IStudentImpl(this.sockethelper).EnquiryStuById(stu);
	    if (list.isEmpty())
	    {
	      JOptionPane.showMessageDialog(null, "��ѯ����ѧ����Ϣ");
	      return this.table_StuInfo;
	    }
	    StudentRollInfo courseList = (StudentRollInfo)list.get(0);
	    Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getAge(), courseList.getGender(), courseList.getBirthday(), courseList.getBirthPlace(), courseList.getDepartment(), courseList.getDormitory() };
	    model.addRow(rowData);
	    
	    return this.table_StuInfo;
	}

}
