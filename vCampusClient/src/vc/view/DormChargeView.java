package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vc.common.DormChargeInf;
import vc.helper.SocketHelper;
import vc.sendImpl.IDormImpl;
import vc.view.DormModifyLivingInfView.GBC;

public class DormChargeView extends JFrame {

	private JLabel titleLabel;
	private JLabel label1;
	private JLabel label2;
	private JTextField text1;
	private JTextField text2;
	private JPanel titlePanel;
	private JPanel label1Panel;
	private JPanel label2Panel;
	private JPanel btnPanel;
	private JPanel black1Panel;
	private JPanel black2Panel;
	private JPanel black3Panel;
	private JButton btn;
	private int money;
	private String secret;
	private SocketHelper sockethelper;	
	private DormChargeInf theStudentChargeInfo;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					DormChargeView  mainWindow = new DormChargeView();
//					mainWindow.setTitle("Charge");
//					mainWindow.setSize(500,200);
//					mainWindow.setResizable(true);
//					mainWindow.setLocationRelativeTo(null);
//					mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//					mainWindow.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	public DormChargeView() {
//		initCompoent();
//		initUI();
//		this.theStudentChargeInfo = new DormChargeInf();
//	}
	
	/**
	 * Create the frame.
	 */
	public DormChargeView(SocketHelper mysockethelper, DormChargeInf info) {
		this.sockethelper = mysockethelper;
		this.theStudentChargeInfo =info;
		this.setTitle("Charge");
		this.setSize(500,200);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initCompoent();
		initUI();
		btnListener();
	}

	private void initCompoent() {
		// TODO Auto-generated method stub
		titleLabel = new JLabel("����ˮ���");
		label1 = new JLabel("���ɷ���");
		label2 = new JLabel("һ��ͨ����");
		btn = new JButton("ȷ���ύ");
		titlePanel = new JPanel();
		btnPanel = new JPanel();
		label1Panel = new JPanel();
		label2Panel = new JPanel();
		black1Panel = new JPanel();
		black2Panel = new JPanel();
		black3Panel = new JPanel();
		text1 = new JTextField();
		text2 = new JTextField();
		money = 0;
		secret = "";
	}


	private void initUI() {
		//���û������ַ�ʽ 
		GridBagLayout gridbaglayout = new GridBagLayout();
		this.setLayout(gridbaglayout);
		
		//����
		Font font = new Font("΢���ź�", Font.PLAIN, 25);
		titleLabel.setFont(font);
		titlePanel.add(titleLabel);
//		titlePanel.setBackground(Color.GREEN);
        this.add(titlePanel, new GBC(0,0,4,1).  
                setFill(GBC.BOTH).setIpad(0,30).setWeight(100,0));
        //"�ɷѽ��"��ǩ��λ��
        Font font1 = new Font("΢���ź�", Font.PLAIN, 15);
        label1.setFont(font1);
        label1Panel.add(label1);
//        label1Panel.setBackground(Color.PINK);
        getContentPane().add(label1Panel, new GBC(0,1,1,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));  
        //"���ɽ��"������λ��
        getContentPane().add(text1, new GBC(1,1,3,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //�м��һ��
//        black1Panel.setBackground(Color.BLUE);
        getContentPane().add(black1Panel, new GBC(0,2,4,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //"һ��ͨ��������"��ǩ��λ��
        label2.setFont(font1);
        label2Panel.add(label2);
//       label2Panel.setBackground(Color.YELLOW);
        getContentPane().add(label2Panel, new GBC(0,3,1,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100)); 
        //"һ��ͨ��������"������λ��
        getContentPane().add(text2, new GBC(1,3,3,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //����һ�з�button
        btnPanel.add(btn);
        getContentPane().add(btnPanel, new GBC(0,4,4,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //����ٿ�һ��
        getContentPane().add(black2Panel, new GBC(0,5,4,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));        
	}
	
	public class GBC extends GridBagConstraints  
	{  
	   //��ʼ�����Ͻ�λ��  
	   public GBC(int gridx, int gridy)  
	   {  
	      this.gridx = gridx;  
	      this.gridy = gridy;  
	   }  
	  
	   //��ʼ�����Ͻ�λ�ú���ռ����������  
	   public GBC(int gridx, int gridy, int gridwidth, int gridheight)  
	   {  
	      this.gridx = gridx;  
	      this.gridy = gridy;  
	      this.gridwidth = gridwidth;  
	      this.gridheight = gridheight;  
	   }  
	  
	   //���뷽ʽ  
	   public GBC setAnchor(int anchor)  
	   {  
	      this.anchor = anchor;  
	      return this;  
	   }  
	  
	   //�Ƿ����켰���췽��  
	   public GBC setFill(int fill)  
	   {  
	      this.fill = fill;  
	      return this;  
	   }  
	  
	   //x��y�����ϵ�����  
	   public GBC setWeight(double weightx, double weighty)  
	   {  
	      this.weightx = weightx;  
	      this.weighty = weighty;  
	      return this;  
	   }  
	  
	   //�ⲿ���  
	   public GBC setInsets(int distance)  
	   {  
	      this.insets = new Insets(distance, distance, distance, distance);  
	      return this;  
	   }  
	  
	   //�����  
	   public GBC setInsets(int top, int left, int bottom, int right)  
	   {  
	      this.insets = new Insets(top, left, bottom, right);  
	      return this;  
	   }  
	  
	   //�����  
	   public GBC setIpad(int ipadx, int ipady)  
	   {  
	      this.ipadx = ipadx;  
	      this.ipady = ipady;  
	      return this;  
	   }  
	}  
	
	//��ȡ����������
	public void GetTextField() {
		money = Integer.parseInt(text1.getText().trim());
		System.out.println("money");
		System.out.println(money);
		secret = text2.getText().trim();
	}
	
	//btn�ļ�����ȷ�Ϻ�������Ϣ
	public void btnListener() {
		btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("�����ȷ���ύ");
				// TODO Auto-generated method stub
				GetTextField();
				//��ȡ��ǰϵͳʱ��
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String strDate = df.format(System.currentTimeMillis());
				System.out.println(strDate);
				DormChargeInf tempChargeInfo = new DormChargeInf(theStudentChargeInfo.getChargeID(),
						strDate,theStudentChargeInfo.getStudentID(),money,theStudentChargeInfo.getArreas(),
						0,theStudentChargeInfo.getBalance());
				System.out.println("ID:"+theStudentChargeInfo.getChargeID());
				if(new IDormImpl(sockethelper).AddDormChargeInf(tempChargeInfo)) {
					JOptionPane.showMessageDialog(null, "��ʾ", "���ĳɹ�", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "error", "������", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
