package vc.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vc.common.CourseInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ISelectCourseImpl;
import vc.view.CourseView.JTabbedPaneDemo;

public class AdminCourseView extends JFrame  {
	private String AdminId;
	private SocketHelper sockethelper = new SocketHelper();
	public JFrame mainFrame;
	private JPanel mainPanel;
	private JScrollPane courseScrollPane;
	private JTable courseTbl;
	private JButton addButton = new JButton("���");
	private JButton deleteButton = new JButton("ɾ��");
	private JButton modifyButton = new JButton("�޸�");
	private JButton confirmButton = new JButton("ȷ��");
	
	public AdminCourseView(String id) {
		AdminId = id;
		sockethelper.getConnection();
		setMainPanel();
		this.setVisible(false);
		action();
	}
	
	private void setMainPanel() {
		mainFrame = new JFrame();
		mainFrame.setVisible(true);
		mainFrame.setBounds(10, 20, 640, 357);
		mainFrame.setTitle("����γ�");
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		mainPanel = new JPanel();
		courseScrollPane = new JScrollPane();
		courseScrollPane.setViewportView(getCourseTable());
		mainPanel.add(addButton);
		mainPanel.add(deleteButton);
		mainPanel.add(modifyButton);
		confirmButton.setEnabled(false);
		mainPanel.add(confirmButton);
		mainPanel.add(courseScrollPane);
		mainFrame.add(mainPanel);
	}
	
	private JTable getCourseTable() {

		courseTbl = new JTable();
		String[] columns = { "����", "�γ�����", "�ڿν�ʦ", "�ڿεص�", "�ڿ�ʱ��", "ѧ��", "��ѡ����" };
		DefaultTableModel model = new DefaultTableModel(columns, 0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		courseTbl.setModel(model);
		List<CourseInfo> list = new ISelectCourseImpl(this.sockethelper).EnquiryAllCourse();

		for (int i = 0; i < list.size(); i++) {
			CourseInfo courseList = (CourseInfo) list.get(i);
			String[] studentList = new ISelectCourseImpl(this.sockethelper).EnquiryStudent(courseList);
			int count = 0; // number of students who select certain course
			for (int j = 0; j < studentList.length; j++) {
				count++;
			}
			Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
					courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), count };
			model.addRow(rowData);
		}
		return courseTbl;
	}
	private void action() {
		this.addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		this.deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "ȷ��ɾ����?", "ȷ��ɾ����", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {

				}
				else if (n == JOptionPane.NO_OPTION) {
					return;
				}	
			}
		});
		
		this.modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
	}
}
