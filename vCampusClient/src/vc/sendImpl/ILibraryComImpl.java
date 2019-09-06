package vc.sendImpl;

import vc.common.BookInfo;
import vc.common.BookStatusInfo;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import vc.helper.SocketHelper;
import vc.send.ILibraryCom;

public class ILibraryComImpl
  implements ILibraryCom
{
  SocketHelper socketHelper = new SocketHelper();
  ObjectInputStream is;
  ObjectOutputStream os;
  String id;
  
  public ILibraryComImpl(String ComId, SocketHelper sockethelper)
  {
    this.is = sockethelper.getIs();
    this.os = sockethelper.getOs();
    this.id = ComId;
  }
  //����������ͼ��
  public List EnquiryAllBook(String bookName)
  {
  	System.out.println("�û�:"+id+"����ͼ�飺"+bookName);
    try
    {
      BookInfo bookTemp = new BookInfo(0, "", bookName, "", "", 0, "", false);
      this.os.writeInt(401);
      this.os.flush();
      this.os.writeObject(bookTemp);
      this.os.flush();
      try
      {
    	//�����ɹ�
        if (this.is.readInt() == 4011) {
            return Arrays.asList((BookInfo[])this.is.readObject());
        }
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      return null;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  //����ͼ��
  public boolean BorrowBook(int bookId, String bookName, long borrowDate, long returnDate)
  {
	  System.out.println("�û�:"+id+"����ͼ�飺��ţ�"+bookId);
    try
    {
      BookStatusInfo bookStatusTemp = new BookStatusInfo(bookId, bookName, id, borrowDate, returnDate, 0, false);
      this.os.writeInt(411);
      this.os.flush();
      this.os.writeObject(bookStatusTemp);
      this.os.flush();
      //�ɹ�����
      if (this.is.readInt() == 4111) {
        return true;
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  //���û�id���Ľ��ļ�¼
  public List EnquiryRecord(String userId)
  {
	System.out.println("�û�:"+id+"���Ľ��ļ�¼");
    try
    {
      BookStatusInfo bookStatusTemp = new BookStatusInfo(0, "", userId, 0, 0, 0, false);
      this.os.writeInt(413);
      this.os.flush();
      this.os.writeObject(bookStatusTemp);
      this.os.flush();
      try
      {
    	//��ѯ�ɹ�
        if (this.is.readInt() == 4131) {
          return Arrays.asList((BookStatusInfo[])this.is.readObject());
        }
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      return null;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  //�黹ͼ�飬��������ͼ��״̬��Ϣ
  public boolean ReturnBook(int bookId, String bookName,long borrowDate,long returnDate, long actualReturnDate)
  {
	System.out.println("�û�:"+id+"�黹ͼ�飺"+bookId+" "+bookName+" "+id+" "+borrowDate+" "+returnDate+" "+actualReturnDate);
    try
    {
      BookStatusInfo bookStatusTemp;
      if(actualReturnDate>returnDate)
    	  bookStatusTemp = new BookStatusInfo(bookId, bookName, id, borrowDate, returnDate, actualReturnDate, true);
      else
    	  bookStatusTemp = new BookStatusInfo(bookId, bookName, id, borrowDate, returnDate, actualReturnDate, false);     
      this.os.writeInt(412);
      this.os.flush();
      this.os.writeObject(bookStatusTemp);
      this.os.flush();
      if (this.is.readInt() == 4121) {
        return true;
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
