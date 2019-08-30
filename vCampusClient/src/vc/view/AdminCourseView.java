package vc.view;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vc.common.CourseInfo;
import vc.common.TextFieldHintListener;
import vc.helper.SocketHelper;
import vc.sendImpl.ISelectCourseImpl;
import vc.view.CourseView.JTabbedPaneDemo;

/**
 * ����Ա�γ̹�������ࡣ
 * @author 09017408
 *
 */

public class AdminCourseView extends JFrame  {
	private String AdminId;
	private SocketHelper sockethelper = new SocketHelper();
	public JFrame mainFrame;
	private JPanel mainPanel;
	private JScrollPane courseScrollPane;
	private JTable courseTbl = new JTable();;
	private JButton addButton = new JButton("���");
	private JButton deleteButton = new JButton("ɾ��");
	private JButton modifyButton = new JButton("�޸�");
	private JButton searchButton = new JButton("����");
	private JButton returnButton = new JButton("����");
	private JButton addConfirmButton = new JButton("ȷ�����");
	private JButton modifyConfirmButton = new JButton("ȷ���޸�");
	private JButton searchConfirmButton = new JButton("ȷ������");
	private JFrame addCourseFrame;
	private JPanel addCoursePanel;
	private JFrame modifyCourseFrame;
	private JPanel modifyCoursePanel;
	private JFrame searchCourseFrame;
	private JPanel searchCoursePanel;
	private String modifyID;
	private TextField newID = new TextField(20);
	private TextField newName = new TextField(20);
	private TextField newTeacher = new TextField(20);
	private TextField newPlace = new TextField(20);
	private TextField newTime = new TextField(20);
	private TextField newCredit = new TextField(20);
	private TextFieldHintListener listenerID;
	private TextFieldHintListener listenerName;
	private TextFieldHintListener listenerTeacher;
	private TextFieldHintListener listenerPlace;
	private TextFieldHintListener listenerTime;
	private TextFieldHintListener listenerCredit;

	private TextField modifyName = new TextField(20);
	private TextField modifyTeacher = new TextField(20);
	private TextField modifyPlace = new TextField(20);
	private TextField modifyTime = new TextField(20);
	private TextField modifyCredit = new TextField(20);
	private TextFieldHintListener listenerMName;
	private TextFieldHintListener listenerMTeacher;
	private TextFieldHintListener listenerMPlace;
	private TextFieldHintListener listenerMTime;
	private TextFieldHintListener listenerMCredit;
	
	private TextField searchField = new TextField(20);
	private JComboBox searchType = new JComboBox();
	
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
		mainPanel.add(searchButton);
		mainPanel.add(returnButton);
		searchButton.setEnabled(true);
		returnButton.setEnabled(false);
		mainPanel.add(courseScrollPane);
		mainFrame.add(mainPanel);
		
		setAddCourseFrame();
		addCourseFrame.setVisible(false);
		setModifyCourseFrame();
		modifyCourseFrame.setVisible(false);
		setSearchCourseFrame();
		searchCourseFrame.setVisible(false);
	}
	
	private JTable getCourseTable() {

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
				resetAddCourseFrame();
				addCourseFrame.setVisible(true);
			}
		});
		
		this.deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (courseTbl.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "δѡ���κογ̣�");
					return;
				}
				searchButton.setEnabled(true);
				returnButton.setEnabled(false);
				addButton.setEnabled(true);
				int currentRow = courseTbl.getSelectedRow();
				int n = JOptionPane.showConfirmDialog(null, "ȷ��ɾ����?", "ȷ��ɾ����", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					String courseID = (String) courseTbl.getValueAt(currentRow, 0);
					String courseName = (String) courseTbl.getValueAt(currentRow, 1);
					String courseTeacher = (String) courseTbl.getValueAt(currentRow, 2);
					String coursePlace = (String) courseTbl.getValueAt(currentRow, 3);
					String courseTime = (String) courseTbl.getValueAt(currentRow, 4);
					double courseCredit = (double) courseTbl.getValueAt(currentRow, 5);
					CourseInfo courseInfo = new CourseInfo(courseID, courseName, 
							courseTeacher, coursePlace, courseTime, courseCredit);
					if (new ISelectCourseImpl(sockethelper).deleteCourse(courseInfo)) {
						JOptionPane.showMessageDialog(null, "�ѳɹ�ɾ���γ̣�");
					} else {
						JOptionPane.showMessageDialog(null, "ɾ���γ�ʧ�ܣ�");
					}
					// refresh
					courseScrollPane.setViewportView(getCourseTable());
				}
				else if (n == JOptionPane.NO_OPTION) {
					return;
				}	
			}
		});
		
		this.modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (courseTbl.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "δѡ���κογ̣�");
					return;
				}
				searchButton.setEnabled(true);
				returnButton.setEnabled(false);
				addButton.setEnabled(true);
				int currentRow = courseTbl.getSelectedRow();
				resetModifyCourseFrame(currentRow);
				modifyCourseFrame.setVisible(true);
				courseScrollPane.setViewportView(getCourseTable());
			}
		});
		
		this.searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetSearchCourseFrame();
				searchCourseFrame.setVisible(true);
			}
		});
		
		this.returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchButton.setEnabled(true);
				returnButton.setEnabled(false);
				addButton.setEnabled(true);
				courseScrollPane.setViewportView(getCourseTable());
			}
		});
	}
	
	void setAddCourseFrame()
	{
		addCourseFrame = new JFrame();
		addCoursePanel = new JPanel();
		addCourseFrame.setVisible(true);
		addCourseFrame.setBounds(500, 100, 500, 350);
		addCourseFrame.setTitle("��ӿγ�");
		addCourseFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
	}
	
	void resetAddCourseFrame()
	{
		// when
		newID.addFocusListener(new TextFieldHintListener(newID, "�γ̴���"));
		newName.addFocusListener(new TextFieldHintListener(newName, "�γ�����"));
		newTeacher.addFocusListener(new TextFieldHintListener(newTeacher, "�ڿν�ʦ"));
		newPlace.addFocusListener(new TextFieldHintListener(newPlace, "�ڿεص�"));
		newTime.addFocusListener(new TextFieldHintListener(newTime, "�ڿ�ʱ��"));
		newCredit.addFocusListener(new TextFieldHintListener(newCredit, "�γ�ѧ��"));
		
		Box box = Box.createVerticalBox();
		box.add(newID);
		box.add(newName);
		box.add(newTeacher);
		box.add(newPlace);
		box.add(newTime);
		box.add(newCredit);
		addCoursePanel.add(box);
		addCoursePanel.add(addConfirmButton);
		addCourseFrame.add(addCoursePanel);
	
		addAction();
	}
	
	void addAction()
	{
		this.addConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String courseID;
				String courseName;
				String courseTeacher;
				String coursePlace;
				String courseTime;
				double courseCredit;
				
				courseID = newID.getText();
				courseName = newName.getText();
				courseTeacher = newTeacher.getText();
				coursePlace = newPlace.getText();
				courseTime = newTime.getText();
				courseCredit = Double.parseDouble(newCredit.getText());

				List<CourseInfo> list = new ISelectCourseImpl(sockethelper).EnquiryCourseById(courseID);
				if (!list.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "����Ŀγ̴����Ѿ����ڣ�");
					return;
				}
				
				CourseInfo courseInfo = new CourseInfo(courseID, courseName, 
						courseTeacher, coursePlace, courseTime, courseCredit);
				if (new ISelectCourseImpl(sockethelper).addCourse(courseInfo)) {
					JOptionPane.showMessageDialog(null, "�ѳɹ���ӿγ̣�");
				} else {
					JOptionPane.showMessageDialog(null, "��ӿγ�ʧ�ܣ�");
				}
				addCourseFrame.setVisible(false);
				// refresh
				courseScrollPane.setViewportView(getCourseTable());
			}
		});
	}
	
	void setModifyCourseFrame()
	{
		modifyCourseFrame = new JFrame();
		modifyCoursePanel = new JPanel();
		modifyCourseFrame.setVisible(true);
		modifyCourseFrame.setBounds(500, 100, 500, 350);
		modifyCourseFrame.setTitle("�޸Ŀγ�");
		modifyCourseFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	void resetModifyCourseFrame(int currentRow)
	{
		modifyID = (String) courseTbl.getValueAt(currentRow, 0);
		listenerMName = new TextFieldHintListener(modifyName, (String) courseTbl.getValueAt(currentRow, 1));
		listenerMTeacher = new TextFieldHintListener(modifyTeacher, (String) courseTbl.getValueAt(currentRow, 2));
		listenerMPlace = new TextFieldHintListener(modifyPlace, (String) courseTbl.getValueAt(currentRow, 3));
		listenerMTime = new TextFieldHintListener(modifyTime, (String) courseTbl.getValueAt(currentRow, 4));
		listenerMCredit = new TextFieldHintListener(modifyCredit, String.valueOf(courseTbl.getValueAt(currentRow, 5)));
		
		Box box = Box.createVerticalBox();
		box.add(modifyName);
		box.add(modifyTeacher);
		box.add(modifyPlace);
		box.add(modifyTime);
		box.add(modifyCredit);
		modifyCoursePanel.add(box);
		modifyCoursePanel.add(modifyConfirmButton);
		modifyCourseFrame.add(modifyCoursePanel);
		
		modifyAction();
		
	}
	
	void modifyAction()
	{
		this.modifyConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String courseName = modifyName.getText();
				String courseTeacher = modifyTeacher.getText();
				String coursePlace = modifyPlace.getText();
				String courseTime = modifyTime.getText();
				double courseCredit = Double.parseDouble(modifyCredit.getText());
				
				CourseInfo courseInfo = new CourseInfo(modifyID, courseName, 
						courseTeacher, coursePlace, courseTime, courseCredit);
				if (new ISelectCourseImpl(sockethelper).modifyCourse(courseInfo)) {
					JOptionPane.showMessageDialog(null, "�ѳɹ��޸Ŀγ̣�");
				} else {
					JOptionPane.showMessageDialog(null, "�޸Ŀγ�ʧ�ܣ�");
				}
				modifyCourseFrame.setVisible(false);
				// refresh
				courseScrollPane.setViewportView(getCourseTable());
			}
		});
	}
	
	void setSearchCourseFrame()
	{
		searchCourseFrame = new JFrame();
		searchCoursePanel = new JPanel();
		searchCourseFrame.setVisible(true);
		searchCourseFrame.setBounds(500, 100, 500, 350);
		searchCourseFrame.setTitle("�����γ�");
		searchCourseFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		searchType.addItem("�γ̴���");
		searchType.addItem("�ڿν�ʦ");
		searchType.addItem("�γ�����");
		searchType.setBounds(30, 100, 161, 21);

	}
	
	void resetSearchCourseFrame()
	{
		searchField.setText("");
		searchCoursePanel.add(searchType);
		searchCoursePanel.add(searchField);
		searchCoursePanel.add(searchConfirmButton);
		searchCourseFrame.add(searchCoursePanel);
		
		this.searchConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchAction();
			}
		});
	}
	
	void searchAction()
	{
		String searchContent = searchField.getText();
		String searchTypes = (String) searchType.getSelectedItem();
		if(searchTypes == "�γ̴���")
		{
			List<CourseInfo> list = new ISelectCourseImpl(sockethelper).EnquiryCourseById(searchContent);
			searchCourseFrame.setVisible(false);
			if (!list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "�����ɹ���");
				searchButton.setEnabled(false);
				returnButton.setEnabled(true);
				addButton.setEnabled(false);
				String[] columns = { "����", "�γ�����", "�ڿν�ʦ", "�ڿεص�", "�ڿ�ʱ��", "ѧ��", "��ѡ����" };
				DefaultTableModel model = new DefaultTableModel(columns, 0);
				model.setRowCount(0);
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
				courseTbl.setModel(model);
			} else {
				JOptionPane.showMessageDialog(null, "û��ƥ��Ŀγ̣�");
			}
			return;
		}

		if(searchTypes == "�γ�����")
		{
			List<CourseInfo> list = new ISelectCourseImpl(sockethelper).EnquiryCourseByName(searchContent);
			searchCourseFrame.setVisible(false);
			if (!list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "�����ɹ���");
				System.out.println("Show message Dialog.");
				searchButton.setEnabled(false);
				returnButton.setEnabled(true);
				addButton.setEnabled(false);
				String[] columns = { "����", "�γ�����", "�ڿν�ʦ", "�ڿεص�", "�ڿ�ʱ��", "ѧ��", "��ѡ����" };
				DefaultTableModel model = new DefaultTableModel(columns, 0);
				model.setRowCount(0);
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
				courseTbl.setModel(model);
			} else {
				JOptionPane.showMessageDialog(null, "û��ƥ��Ŀγ̣�");
			}
			return;
		}
		
		if(searchTypes == "�ڿν�ʦ")
		{
			List<CourseInfo> list = new ISelectCourseImpl(sockethelper).EnquiryCourseByTeacher(searchContent);
			searchCourseFrame.setVisible(false);
			if (!list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "�����ɹ���");
				searchButton.setEnabled(false);
				returnButton.setEnabled(true);
				addButton.setEnabled(false);
				String[] columns = { "����", "�γ�����", "�ڿν�ʦ", "�ڿεص�", "�ڿ�ʱ��", "ѧ��", "��ѡ����" };
				DefaultTableModel model = new DefaultTableModel(columns, 0);
				model.setRowCount(0);
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
				courseTbl.setModel(model);
			} else {
				JOptionPane.showMessageDialog(null, "û��ƥ��Ŀγ̣�");
			}
			return;
		}
	}
}
