package vc.view;
/** ע�����
* @author 09017406
* ���ˡ���ը��
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vc.common.UserInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILoginImpl;

public class RegisterFrame extends JFrame{
	private JPanel registerPane;
	private JTextField txtUserID;
	private JTextField txtName;
	private JPasswordField txtPassword;
	private JPasswordField txtPassword2;
	private JComboBox comboType;
	private JButton btnOk;
	private JButton btnback;
	private JButton btnClean;

	
	public RegisterFrame() {
   
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 314, 256);
	registerPane = new JPanel();
	registerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(registerPane);
	registerPane.setLayout(null);
	
	JLabel lblUserName = new JLabel("�û���");
	lblUserName.setBounds(30, 10, 54, 15);
	registerPane.add(lblUserName);
	
	txtName = new JTextField();
	txtName.setBounds(94, 10, 163, 21);
	registerPane.add(txtName);
	txtName.setColumns(10);
	
	JLabel lblUserID = new JLabel("�˺�");
	lblUserID.setBounds(30, 36, 54, 15);
	registerPane.add(lblUserID);
	
	txtUserID = new JTextField();
	txtUserID.setBounds(94, 36, 163, 21);
	registerPane.add(txtUserID);
	txtUserID.setColumns(10);
	
	JLabel lblPassword = new JLabel("����");
	lblPassword.setBounds(30, 66, 54, 15);
	registerPane.add(lblPassword);
	
	txtPassword = new JPasswordField();
	txtPassword.setBounds(96, 66, 161, 21);
	registerPane.add(txtPassword);
	
	JLabel lblPassword2 = new JLabel("ȷ������");
	lblPassword2.setBounds(30, 90, 54, 15);
	registerPane.add(lblPassword2);
	
	txtPassword2 = new JPasswordField();
	txtPassword2.setBounds(96, 90, 161, 21);
	registerPane.add(txtPassword2);
	
	comboType = new JComboBox();
	comboType.addItem("--��ѡ��--");
    comboType.addItem("ѧ��");
    comboType.addItem("����Ա");
    comboType.setBounds(30, 130, 161, 21);
    registerPane.add(comboType);
    
    btnOk = new JButton("ȷ��");
    btnOk.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		SocketHelper sockethelper = new SocketHelper();
    		sockethelper.getConnection();
    		
    		String cName = RegisterFrame.this.txtName.getText();
	        String cId = RegisterFrame.this.txtUserID.getText();
	        
	        String cPwd = RegisterFrame.this.txtPassword.getText();
	        System.out.println(cPwd);
	        String cPwd2 = RegisterFrame.this.txtPassword2.getText();
	        String type = (String) comboType.getSelectedItem();
	        String typeE = null;
	        if (type == "ѧ��")
		      {
		    	typeE = "student";
		      }
		      else if (type == "����Ա")
		      {
		    	  typeE = "admin";
		      }
		      else
		      {
		    	  JOptionPane.showMessageDialog(null, "ע������ת��ʧ��");
		    	  typeE = "student";
		      }
	        if ((cId.length() != 0) && (cName.length() != 0) && (cPwd.length() != 0) && (cPwd2.length() != 0))
	        {
	          if (cPwd.equals(cPwd2))
	          {
	            UserInfo s = new UserInfo("", "", "", "", "");
	            s.setStuId(cId);
	            s.setName(cName);
	            s.setPwd(cPwd);
	            s.setType(typeE);
	            
	            boolean isSave = ( new ILoginImpl(sockethelper)).register(s);
	            if (isSave)
	            {
	              JOptionPane.showMessageDialog(null, "ע��ɹ�");
	              JOptionPane.showMessageDialog(null, "��¼�ɹ�");
	              if (type == "ѧ��")
	    	      {
	    	        JOptionPane.showMessageDialog(null, "ѧ�������¼�ɹ�");
	    	        Function funtion = new Function(cId, 0);
	    	        funtion.setVisible(true);
	    	        dispose();
	    	      }
	              else if (type == "����Ա")
	    	      {
	    	        JOptionPane.showMessageDialog(null, "����Ա�����¼�ɹ�");
	    	        Function funtion = new Function(cId, 1);
	    	        funtion.setVisible(true);
	    	        dispose();
	    	      }

	              return;
	            }
	            JOptionPane.showMessageDialog(null, "ע��ʧ��");
	            //RegisterFrame.this.Frame_Login.dispose();
	            return;
	          }
	          JOptionPane.showMessageDialog(null, "������ȷ������");
	        }
	        else
	        {
	          JOptionPane.showMessageDialog(null, "���пհ���δ����");
	          return;
	        }
    		
    		
    		
    		}
		});
    btnOk.setBounds(5, 190, 93, 23);
    registerPane.add(btnOk);
    
    
    btnClean=new JButton("���");
    btnClean.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		SocketHelper sockethelper = new SocketHelper();
    		sockethelper.getConnection();
    		txtName.setText("");
    		txtUserID.setText("");
    		txtPassword.setText("");
    		txtPassword2.setText("");
    		}
    	});
    btnClean.setBounds(110, 190, 93, 23);
    registerPane.add(btnClean);
    
    
    btnback = new JButton("����");
    btnback.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		SocketHelper sockethelper = new SocketHelper();
    		sockethelper.getConnection();
    		MainFrame register = new MainFrame();
	        register.setVisible(true);
	        dispose();
    		}
		});
    btnback.setBounds(210, 190, 93, 23);
    registerPane.add(btnback);
	}

}
