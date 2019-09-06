package vc.view;

import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vc.common.StudentRollInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IStudentImpl;

public class ModifyStudentView extends JFrame{

	public JFrame mainFrame;
	private JPanel AddStudentPanel;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private TextField modifyID = new TextField(20);
	private TextField modifyName = new TextField(20);
	private TextField modifySex = new TextField(20);
	private TextField modifyAge = new TextField(20);
	private TextField modifyBirthDate = new TextField(20);
	private TextField modifyAddress = new TextField(20);
	private TextField modifyMajor = new TextField(20);
	private TextField modifyDorm = new TextField(20);
	private JButton modifyConfirmButton = new JButton("ȷ���޸�");
	
	JLabel IDLabel = new JLabel("ѧ��");
	JLabel nameLabel = new JLabel("���� ");
	JLabel sexLabel = new JLabel("�Ա� ");
	JLabel ageLabel = new JLabel("����  ");
	JLabel birthLabel = new JLabel("�������� ");
	JLabel addressLabel = new JLabel("��ͥסַ ");
	JLabel majorLabel = new JLabel("רҵ");
	JLabel dormLabel = new JLabel("���� ");
	
	public ModifyStudentView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel();
		this.setVisible(false);
		run();
	}

	private void setMainPanel() {
		// TODO Auto-generated method stub
		mainFrame = new JFrame();
		mainFrame.setVisible(true);
		mainFrame.setBounds(10, 20, 640, 357);
		mainFrame.setTitle("ѧ����Ϣ");
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		AddStudentPanel = new JPanel();
        
		Box box = Box.createVerticalBox();
		Box box2 = Box.createVerticalBox();
		box2.add(IDLabel);
	    box2.add(nameLabel);
	    box2.add(sexLabel);
	    box2.add(ageLabel);
	    box2.add(birthLabel);
	    box2.add(addressLabel);
	    box2.add(majorLabel);
	    box2.add(dormLabel);         
		box.add(modifyID);
		box.add(modifyName);
		box.add(modifySex);
		box.add(modifyAge);
		box.add(modifyBirthDate);
		box.add(modifyAddress);
		box.add(modifyMajor);
		box.add(modifyDorm);           
		box.add(modifyConfirmButton);
        AddStudentPanel.add(box2);
        AddStudentPanel.add(box);
		AddStudentPanel.add(modifyConfirmButton);
		mainFrame.add(AddStudentPanel);    		
	}	
	
	private void run() {
		this.modifyConfirmButton.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	        String sId = ModifyStudentView.this.modifyID.getText();
	        String sAge = ModifyStudentView.this.modifyAge.getText();
	        String sBirthdate = ModifyStudentView.this.modifyBirthDate.getText();
	        String sName = ModifyStudentView.this.modifyName.getText();
	        String sSex = ModifyStudentView.this.modifySex.getText();
	        String sPlace = ModifyStudentView.this.modifyAddress.getText();
	        String sDepart = ModifyStudentView.this.modifyMajor.getText();
	        String sDormitory = ModifyStudentView.this.modifyDorm.getText();
	        if ((sId.length() != 0) && (sName.length() != 0) && 
	          (sAge.length() != 0) && (sBirthdate.length() != 0) && 
	          (sSex.length() != 0) && (sPlace.length() != 0) && (sDepart.length() != 0) && (sDormitory.length() != 0))
	        {
	          StudentRollInfo s = new StudentRollInfo("", "", "", "", "", "", "", ""/*, "", "", "", ""*/);
	          s.setId(sId);
	          s.setName(sName);
	          s.setGender(sSex);
	          s.setAge(sAge);
	          s.setBirthday(sBirthdate);
	          s.setDepartment(sDepart);
	          s.setDormitory(sDormitory);
	          s.setBirthPlace(sPlace);
	          
	          boolean isSave = new IStudentImpl(ModifyStudentView.this.sockethelper).ModifyStudentView(s);
	          if (isSave)
	          {
	            JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
	            ModifyStudentView.this.dialogClose();
	          }
	          else
	          {
	            JOptionPane.showMessageDialog(null, "�޸�ʧ��");
	          }
	        }
	        else
	        {
	          JOptionPane.showMessageDialog(null, "���п���δ����");
	        }
	      }
	    });		
	}

	protected void dialogClose() {
		// TODO Auto-generated method stub
		this.mainFrame.dispose();
	}

}
