package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import vc.common.DormChargeInf;
import vc.common.DormLivingInf;
import vc.common.DormRepairInf;
import vc.common.DormUtilityBillsInf;
import vc.common.DormVisitInf;
import vc.helper.SocketHelper;
import vc.sendImpl.IDormImpl;


public class DormView extends JFrame {

	private JPanel contentPane;
	private JLabel titleLable;
	private JLabel billsLabel;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JButton btn6;
	private JButton btn7;
	private JPanel titlePanel;
	private JPanel btn1Panel;
	private JPanel btn2Panel;
	private JPanel btn3Panel;
	private JPanel btn456Panel;
	private JPanel btn7Panel;
	private JPanel tablePanel;
	private JTable table;
	private MyTableModel mytablemodel;
	private SocketHelper sockethelper = new SocketHelper();
	private String stuId;
	private DormLivingInf livingInfo = new DormLivingInf();
	private DormChargeInf chargeInfo = new DormChargeInf();
	private DormRepairInf repairInfo = new DormRepairInf();
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
////					DormView  mainWindow = new DormView();
////					mainWindow.setTitle("DormInfDisplay");
////					mainWindow.setSize(700,400);
////					mainWindow.setResizable(false);
////					mainWindow.setLocationRelativeTo(null);
////					mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////					mainWindow.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

//	public DormView() {
//		super();
//		initCompoent();
//		initUI();
//		addButtonListener();
//	}

	/**
	 * Create the frame.
	 */
	public DormView(SocketHelper sockethelper, String id) {
		super();
		this.setTitle("DormInfDisplay");
		this.setSize(700,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		initCompoent();
		initUI();
		addButtonListener();
		this.stuId = id;
		System.out.println("DormView:"+id);
		this.sockethelper = sockethelper;
	}

	public SocketHelper getsockethelper() {
		return this.sockethelper;
	}
	
	public void initCompoent() {
		btn1 = new JButton("ס����Ϣ��ѯ");
		btn2 = new JButton("ˮ��ѽ��������ѯ");
		btn3 = new JButton("����ά�������ѯ");
		btn4 = new JButton("�޸�ס����Ϣ");
		btn5 = new JButton("����");
		btn6 = new JButton("�ɷ�");
		btn7 = new JButton("ÿ��ˮ��Ѳ�ѯ");
		titleLable = new JLabel("������Ϣ����");
		mytablemodel = new MyTableModel();
		table = new JTable(mytablemodel);
		titlePanel = new JPanel();
		btn1Panel = new JPanel();
		btn2Panel = new JPanel();
		btn3Panel = new JPanel();
		btn456Panel = new JPanel();
		btn7Panel = new JPanel();
		tablePanel = new JPanel();
	}
	
	public void initUI() {
		//���û������ַ�ʽ 
		GridBagLayout gridbaglayout = new GridBagLayout();
		this.setLayout(gridbaglayout);
		Font font = new Font("΢���ź�", Font.PLAIN, 25);
		titleLable.setFont(font);
		titlePanel.add(titleLable);
        this.add(titlePanel, new GBC(0,0,3,1).  
                setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));  
        //����м�ľ��幤�����  
        btn1Panel.add(btn1);
        this.add(btn1Panel,new GBC(0,1).  
                     setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100)); 
        //����·��ľ��幤�����  
        btn2Panel.add(btn2); 
        this.add(btn2Panel,new GBC(0,2).  
                     setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100)); 
        //����·��ľ��幤�����  
        btn3Panel.add(btn3);
        this.add(btn3Panel,new GBC(0,3).  
                     setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100)); 
        btn7Panel.add(btn7);
        this.add(btn7Panel,new GBC(0,4).  
                setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        //�м�ı��
        setTableFont(table);
        JScrollPane scrollPane = new JScrollPane (table);
        tablePanel.add(scrollPane);
        this.add(tablePanel,new GBC(1,1,3,3).setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        
        //��������һ�����
        btn456Panel.add(btn4);
        btn456Panel.add(btn5);
        btn456Panel.add(btn6);
        btn4.setVisible(false);
        btn5.setVisible(false);
        btn6.setVisible(false);
        this.add(btn456Panel,new GBC(2,4,1,1).setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
	}
	
	public void  setTableFont(JTable table) {
		// ���ñ�ͷ���־�����ʾ,������ݾ�����ʾ
        DefaultTableCellRenderer  renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r	= new DefaultTableCellRenderer();   
        r.setHorizontalAlignment(JLabel.CENTER);   
        table.setDefaultRenderer(Object.class, r);
		table.setShowHorizontalLines(true);
		table.setRowSelectionAllowed(true);
		table.setVisible(true);
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
	
	/**
  	���ģ��ʵ�֣������ʾ����ʱ������ģ���е���Ӧ������ȡ���ݽ��б�����ݵ���ʾ
    */
	public class MyTableModel extends AbstractTableModel {
		private Vector<String> tableTitle;//���� �б���
		private Vector<Object[]> tableData;//������ű�����ݵ����Ա�

		public MyTableModel() {
	        tableTitle= new Vector<String>();
	    	tableData = new Vector<Object[]>();
	    	String [] titleData =new String[] {"ѧ��","����","��Ԫ��","¥��","�����","����","��ϵ��ʽ"};
//	    	Object[] rowData = new Object[]{"÷԰",8,"D",401,4,119};
	    	setTitleModel(titleData);
//			tableData.add(rowData);
		}
	    //�޸ı���
	    public void setTitleModel(String[] title) {
	    	tableTitle.clear();
	    	for(String str: title) {
	    		tableTitle.add(str);
	    	}
	    }
	    
	    //�޸ı������
	    public void setDataModel(Object[] data,boolean clear) {
	    	if(clear) {
	    		tableData.clear();
	    	}
	    	tableData.add(data);
	    }
	    
	    @Override
	    public String getColumnName(int columnIndex) {
	    	return tableTitle.elementAt(columnIndex);
	    }
	    
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return tableData.size();
		}
	
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return tableTitle.size();
		}
	
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object temp[]=tableData.elementAt(rowIndex);
			return temp[columnIndex];
		}
		
		//���ñ������༭
		@Override
	    public boolean isCellEditable(int rowIndex, int columnIndex){
			return true;
		}
		
	    @Override
	    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
	    	//����Ԫ������ݷ����ı��ʱ����øú������赥Ԫ�������
	    	((String[])this.tableData.get(rowIndex))[columnIndex]=(String)aValue;
	    	super.setValueAt(aValue, rowIndex, columnIndex);
	    }
	    
	    public void setTableDataClear() {
	    	tableData.clear();
	    }
	}
	//��ȡ��ǰѧ��ˮ�������Ϣ
	public void getDormUtilityBillsInf() {
		DormUtilityBillsInf myInfo = new DormUtilityBillsInf();
		System.out.println("getDormChargeInf()");
		System.out.println(stuId);
		myInfo.setStudentID(stuId);
		System.out.println(myInfo.getStudentID());
		java.util.List<DormUtilityBillsInf> DormUtilityBillsInflist =  
				new IDormImpl(sockethelper).QueryDormUtilityBillsInf(myInfo);
		mytablemodel.setTableDataClear();
		System.out.println("DormUtilityBillsInflist.size():");
		System.out.println(DormUtilityBillsInflist.size());
		 for (int i = 0; i < DormUtilityBillsInflist.size()-1; i++)
		    {
			 DormUtilityBillsInf bList = (DormUtilityBillsInf)DormUtilityBillsInflist.get(i); 
				Object[] tableData = new Object[]{bList.getBillsID(),bList.getBillsTime(),bList.getStudentID(),
						bList.getUtilityBills()};
				mytablemodel.setDataModel(tableData, false);	
		    }
			String tableHead[] = new String[]{"��¼����","ʱ��","ѧ��","����ˮ���"};
			mytablemodel.setTitleModel(tableHead);
			mytablemodel.fireTableStructureChanged();
	}
	
	//��ȡ��ǰ��¼ѧ����ˮ�������Ϣ��ʹ������ı�
	public void getDormChargeInf() {
		DormChargeInf myInfo = new DormChargeInf();
		myInfo.setStudentID(stuId);
		java.util.List<DormChargeInf> DormChargeInflist =  new IDormImpl(sockethelper).QueryDormChargeInf(myInfo);
		mytablemodel.setTableDataClear();
		System.out.println("DormChargeInflist.size():");
		System.out.println(myInfo.getStudentID());
	    for (int i = 0; i < DormChargeInflist.size(); i++)
	    {
	    	DormChargeInf bList = (DormChargeInf)DormChargeInflist.get(i); 
			Object[] tableData = new Object[]{bList.getChargeID(),bList.getChargeTime(),bList.getStudentID(),
					bList.getChargeMoney(),bList.getArreas(),bList.getUtilityBills(),bList.getBalance()};
			chargeInfo = new DormChargeInf(bList.getChargeID(),bList.getChargeTime(),bList.getStudentID(),
					bList.getChargeMoney(),bList.getArreas(),bList.getUtilityBills(),bList.getBalance());
			mytablemodel.setDataModel(tableData, false);	
	    }
		String tableHead[] = new String[]{"�ɷѵ���","�ɷ�����","������","���ɷ���","��ǰǷ�ɷ�","ǰ��ˮ���","���"};
		mytablemodel.setTitleModel(tableHead);
		mytablemodel.fireTableStructureChanged();
	}
	
	//��ȡ��ǰ��¼ѧ���ĸ���ס����Ϣ��ʹ������ı�
	public void getDormLivingInf() {
		DormLivingInf myInfo = new DormLivingInf();
		myInfo.setStudentID(stuId);
		java.util.List<DormLivingInf> DormLivingInflist =  new IDormImpl(sockethelper).QueryDormLivingInf(myInfo);
		mytablemodel.setTableDataClear();
		System.out.println(DormLivingInflist.size());
	    for (int i = 0; i < DormLivingInflist.size(); i++)
	    {
	    	DormLivingInf bList = (DormLivingInf)DormLivingInflist.get(i); 
			Object[] tableData = new Object[]{bList.getStudentID(),bList.getregion(),bList.getunit(),
					bList.getbuilding(),bList.getroom(),bList.getbed(),bList.getphone()};
			livingInfo = new DormLivingInf(bList.getStudentID(),bList.getregion(),bList.getunit(),
					bList.getbuilding(),bList.getroom(),bList.getbed(),bList.getphone());
			mytablemodel.setDataModel(tableData, false);	
			System.out.println("��ȡ��ǰ��¼ѧ���ĸ���ס����Ϣ��ʹ������ı�:");
			System.out.println(bList.getphone());
	    }
		String tableHead[] = new String[]{"ѧ��","����","��Ԫ��","¥��","�����","����","��ϵ��ʽ"};
		mytablemodel.setTitleModel(tableHead);
		mytablemodel.fireTableStructureChanged();
	}

	//��ȡ��ǰ��¼ѧ���ĸ�������ά����Ϣ��ʹ������ı�
	public void getDormRepairInf() {
		DormRepairInf myInfo = new DormRepairInf();
		myInfo.setStudentID(stuId);
		java.util.List<DormRepairInf> DormRepairInflist =  new IDormImpl(sockethelper).QueryDormRepairInf(myInfo);
		mytablemodel.setTableDataClear();
	    for (int i = 0; i < DormRepairInflist.size(); i++)
	    {
	    	DormRepairInf bList = (DormRepairInf)DormRepairInflist.get(i); 
			Object[] tableData = new Object[]{bList.getRepairID(),bList.getTime(),bList.getStudentID(),
					bList.getReason(),bList.getState()};
			repairInfo = new DormRepairInf(bList.getRepairID(),bList.getTime(),bList.getStudentID(),
					bList.getReason(),bList.getState());
			System.out.println("DormView��"+bList.getReason());
			mytablemodel.setDataModel(tableData, false);	
	    }
		String tableHead[] = new String[]{"���޼�¼���","��������","������","����ԭ��","����״��"};
		mytablemodel.setTitleModel(tableHead);
		mytablemodel.fireTableStructureChanged();
	}

	public void getDormVisitInf() {
		DormVisitInf myInfo = new DormVisitInf();
		myInfo.setVisitName("��֮��");
		java.util.List<DormVisitInf> DormVisitInflist =  new IDormImpl(sockethelper).QueryDormVisitInf(myInfo);
		mytablemodel.setTableDataClear();
	    for (int i = 0; i < DormVisitInflist.size(); i++)
	    {
	    	DormVisitInf bList = (DormVisitInf)DormVisitInflist.get(i); 
			Object[] tableData = new Object[]{bList.getVisitID(),bList.getVisitName(),bList.getTimeIn(),
					bList.getTimeOut(),bList.getVisitReason()};
			mytablemodel.setDataModel(tableData, false);	
	    }
		String tableHead[] = new String[]{"���ü�¼����","������","����ʱ��","�뿪ʱ��","����"};
		mytablemodel.setTitleModel(tableHead);
		mytablemodel.fireTableStructureChanged();
		
	}
	
	public void addButtonListener() {
		//��ѯס����Ϣ�ļ���
		btn1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("�����btn1");
				btn4.setVisible(true);
				btn5.setVisible(false);
				btn6.setVisible(false);
				getDormLivingInf();
//				getDormVisitInf();
			}
		});
		
		//ˮ��ѽ��������ѯ�ļ���
		btn2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("�����btn2");
				btn4.setVisible(false);
				btn5.setVisible(false);
				btn6.setVisible(true);
				getDormChargeInf();
			}
		});
	/*
	 *������Ա
	 * */		
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("�����btn2");
//				btn4.setVisible(true);
//				btn5.setVisible(false);
//				DormVisitInf myInfo = new DormVisitInf();
//				myInfo.setVisitName("��֮��");
//				java.util.List<DormVisitInf> DormVisitInflist =  new IDormImpl(sockethelper).QueryDormVisitInf(myInfo);
//				mytablemodel.setTableDataClear();
//			    for (int i = 0; i < DormVisitInflist.size()-1; i++)
//			    {
//			    	DormVisitInf bList = (DormVisitInf)DormVisitInflist.get(i); 
//					Object[] tableData = new Object[]{bList.getVisitID(),bList.getVisitName(),bList.getTimeIn(),
//							bList.getTimeOut(),bList.getVisitReason()};
//					mytablemodel.setDataModel(tableData, false);	
//			    }
//				String tableHead[] = new String[]{"���ü�¼����","������","����ʱ��","�뿪ʱ��","����"};
//				mytablemodel.setTitleModel(tableHead);
//				mytablemodel.fireTableStructureChanged();	
//			}

		
		//����ά����Ϣ��ѯ
		btn3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("�����btn3");
				btn4.setVisible(false);
				btn5.setVisible(true);
				btn6.setVisible(false);
				getDormRepairInf();
			}	
		});
		
		//�޸�ס����Ϣ
		btn4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DormModifyLivingInfView modifyView = new DormModifyLivingInfView(sockethelper,livingInfo);
				modifyView.setVisible(true);
			}
		});
		
		btn5.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DormAddRepairView addview = new DormAddRepairView(sockethelper,repairInfo);
				addview.setVisible(true);
			}
			
		});
		
		//�����ɷѵļ���
		btn6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("�����btn6");
				DormChargeView dormchargeview = new DormChargeView(sockethelper,chargeInfo);
				dormchargeview.setVisible(true);
			}
			
		});
		
		//"��ѯˮ���"�ļ���
		btn7.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("�����btn7");
				btn4.setVisible(false);
				btn5.setVisible(false);
				btn6.setVisible(false);
				getDormUtilityBillsInf();
			}
		});
	}
}
