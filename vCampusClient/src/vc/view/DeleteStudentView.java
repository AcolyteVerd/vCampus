package vc.view;

import java.awt.Component;
import java.awt.Font;
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

public class DeleteStudentView extends JFrame{

	private JPanel DeleteStudentPanel;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	
	private JButton deleteConfirmButton = new JButton("ȷ��ɾ��");
	private JTextField textField_StuId = new JTextField();

	private JTable table_StuInfo = new JTable();
	JScrollPane scrollPane_StuInfo = new JScrollPane();

	public DeleteStudentView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel();
		this.setVisible(false);
		run();
	}


	private void setMainPanel() {
		new JFrame();
		setVisible(true);
		setBounds(10, 20, 640, 357);
		setTitle("ɾ��ѧ����Ϣ");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		DeleteStudentPanel = new JPanel();
		scrollPane_StuInfo = new JScrollPane();
		scrollPane_StuInfo.setViewportView(getStudentTable());		
		DeleteStudentPanel.add(deleteConfirmButton);	
		DeleteStudentPanel.add(scrollPane_StuInfo);
		add(DeleteStudentPanel);     	
	}
	
	private JTable getStudentTable() {

		table_StuInfo = new JTable();
		String[] columns = { "ѧ��ID", "����", "����", "�Ա�", "��������", "��ַ", "רҵ", "����" };
		DefaultTableModel model = new DefaultTableModel(columns, 0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table_StuInfo.setModel(model);
		List<StudentRollInfo> list = new IStudentImpl(this.sockethelper).EnquiryAllStu(null);

		for (int i = 0; i < list.size(); i++)
	    {
	      StudentRollInfo studentList = (StudentRollInfo)list.get(i);
	      Object[] rowData = { studentList.getId(), studentList.getName(), studentList.getAge(), studentList.getGender(), studentList.getBirthday(), studentList.getBirthPlace(), studentList.getDepartment(), studentList.getDormitory() };
	      model.addRow(rowData);
	    }
	    return this.table_StuInfo;
	}
	private void run() {
		this.deleteConfirmButton.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	  
	        System.out.println("ɾ��ѧ����Ϣ");
	       
	        if (table_StuInfo.getSelectedRow() == -1) {
	          JOptionPane.showMessageDialog(null, "��δѡ���κ�ѧ��");
	          return;
	        }
	        StudentRollInfo stu = new StudentRollInfo("", "", "", "", "", "", "", "");
	        String str = (String)DeleteStudentView.this.table_StuInfo
	          .getValueAt(DeleteStudentView.this.table_StuInfo
	          .getSelectedRow(), 0);
	        stu.setId(str);
	        System.out.println(str);
	        boolean flag = new IStudentImpl(DeleteStudentView.this.sockethelper).DeleteStudentView(stu);
	        if (flag)
	        {
	          JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
	          scrollPane_StuInfo.setViewportView(getStudentTable());
	        }
	        else
	        {
	          JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
	        }
	      }
	    });
	}


}
