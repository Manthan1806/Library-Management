import java.util.*;
import java.util.Date;
import java.io.*;
import java.sql.*;
import java.util.concurrent.*;

import com.mysql.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import javafx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

interface OnCallBack
{
	void onCallBack(String s);
}

public class library_management
{
	public static Set<String> result = new HashSet<>();
	public static String category;
	public static void main(String [] args)
	{
		JFrame frame = new JFrame("LIBRARY");
		frame.add( new server(args));
		frame.getContentPane().setBackground(Color.DARK_GRAY	);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //to close when exit button is pressed
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);			//set screen with given size
		frame.setVisible(true);  
	}
	
	
}

class server extends JComponent
implements MouseListener,ActionListener
{
	String [] args;
	user<Integer> uid1 = null;
	user<String> uid2 = null;
	library<Double> obj1 = null;
	Student student = null;
	Books books = null;
	Fine fine = null;
	JsoupGoogleSearch search = null;
	HyperlinkDemo link = null;
	OnCallBack ocb = null;
	ThreadPool tp = null;
	JButton new_user,add_info,buy,returnBook;
	JPanel panel1,panel2,panel3,panel4,panel5,panel6,panel7,panel8,panel9,panel10;
	JTable table;
	Connection con = null;
	JScrollPane scrollPane;
	public server(String [] args)
	{
		this.args = args;
		try
		{
			panel1 = new JPanel();
			panel1.setBounds(0, 0, 200, 350);
			panel1.setBackground(Color.GREEN);
			panel2 = new JPanel();
			panel2.setBounds(250, 0, 200, 350);
			panel2.setBackground(Color.RED);
			panel3 = new JPanel();
			panel3.setBounds(500, 0, 200, 350);
			panel3.setBackground(Color.BLUE);
			panel4 = new JPanel();
			panel4.setBounds(750,0,600,350);
			panel4.setBackground(Color.LIGHT_GRAY);
			panel5 = new JPanel();
			panel5.setBounds(0, 400, 200, 350);
			panel9 = new JPanel();
			panel9.setBounds(1400, 0, 150, 1000);
			panel9.setBackground(Color.ORANGE);
			panel10 = new JPanel();
			panel10.setBounds(750,0,600,350);
			panel10.setBackground(Color.LIGHT_GRAY);
			new_user = new JButton("New User");
			new_user.setBounds(0, 0, 100, 30);
			add_info = new JButton("Add Information");
			add_info.setBounds(300,0,200,30);
			buy = new JButton("Issue a Book");
			buy.setBounds(700,0,100,30);
			returnBook = new JButton("Return a book");
			returnBook.setBounds(0, 400, 100, 30);
			panel1.add(new_user);
			panel2.add(add_info);
			panel3.add(buy);
			panel5.add(returnBook);
			add(panel1);  
			add(panel2);
			add(panel3);
			add(panel4);
			add(panel9);
			add(panel10);
			add(panel5);
			panel4.setVisible(false);
			new_user.addActionListener(this);
			add_info.addActionListener(this);
			buy.addActionListener(this);
			returnBook.addActionListener(this);
			addMouseListener(this);
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignments","root","password");
			obj1 = new library<Double>(con);
			//Scanner in = new Scanner(System.in);
			uid1 = new user<Integer>(con);
			uid2 = new user<String>(con);
		    ocb = new thread();
			tp = new ThreadPool(ocb);
			student = new Student(con);
			books = new Books(con);
			fine = new Fine(con);
			search = new JsoupGoogleSearch();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == new_user)
		{
			String n = JOptionPane.showInputDialog(null, "User Name : ");
			String t = JOptionPane.showInputDialog(null, "Id type?\n1.Integer\n2.String\n\n");
			if(t.equals("Integer"))
			{
				String t1 = JOptionPane.showInputDialog(null,"Id : ");
				Integer id = Integer.parseInt(t1);
				try 
				{
					uid1.new_user(n, id, tp, t);
				}
				catch (SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(t.equals("String"))
			{
				String t1 = JOptionPane.showInputDialog(null,"Id : ");
				try 
				{
					uid2.new_user(n, t1, tp, t);
				}
				catch (SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Invalid Input");
			}
		}
		else if(e.getSource() == buy)
		{
			int count = 0;
			try 
			{
				ResultSet cnt = this.obj1.display(con);
				while(cnt.next())
				{
					count++;
				}
			} 
			catch (SQLException e2) 
			{
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			//JFrame tFrame = new JFrame("BOOKS");
			String column[] = {"Name","Author","Price","Stock"};
			String data[][] = new String[count][4];
			int i = 0;
			try 
			{
				ResultSet rs = this.obj1.display(this.con);
				while(rs.next())
				{
					data[i][0] = rs.getString(1);
					data[i][1] = rs.getString(2);
					data[i][2] = rs.getString(3);
					data[i][3] = rs.getString(4);
					i++;
				}
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			JTable table = new JTable(data,column);
			table.setBounds(30,40,700,1000);
			table.setBackground(Color.LIGHT_GRAY);
			JScrollPane sp = new JScrollPane(table);
			sp.setBounds(850, 0, 400, 1000);
			//tFrame.getContentPane().add(sp);
			//tFrame.setSize(700, 700);
			//tFrame.setVisible(true);
			//tFrame.setLocation(800, 40);
			//tFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			panel4.add(sp);
			panel4.setVisible(true);
			String name = JOptionPane.showInputDialog(null,"Enter Book name : ");
			String stud_id = JOptionPane.showInputDialog(null, "Enter student id : ");
			String stud_name = JOptionPane.showInputDialog(null, "Enter student name : ");
			try
			{
				if(student.insert(stud_id, stud_name, con))
				{
					String ans = this.obj1.buy(name, this.con);
					JOptionPane.showMessageDialog(null,ans);
					String check = "Book not available";
					if(ans.equalsIgnoreCase(check))
					{
						student.delete(stud_id, con);
					}
					else
					{
						System.out.println(1);
						String msg = books.insert(name, stud_id, con);
						String msg2 = "The return date is : "+msg;
						JOptionPane.showMessageDialog(null, msg2);
					}
				}
				else
				{
					String ans = "You have maximum number of books issued, cannot issue another";
					JOptionPane.showMessageDialog(null,ans);
				}
			}
			catch(Exception ex)
			{
				
			}
			panel4.remove(sp);
			panel4.setVisible(false);
		}
		else if(e.getSource()==add_info)
		{
			try
			{
				String n = JOptionPane.showInputDialog(null, "User Name : ");
				String t = JOptionPane.showInputDialog(null, "Id type?\n1.Integer\n2.String\n\n");
				if(t.equals("Integer"))
				{
					String t1 = JOptionPane.showInputDialog(null,"Id : ");
					Integer id = Integer.parseInt(t1);
					if(uid1.exists(n,id,t))
					{
						String b = JOptionPane.showInputDialog(null, "Enter book Name : ");
						String a = JOptionPane.showInputDialog(null, "Enter author : ");
						String n1 = JOptionPane.showInputDialog(null, "Enter price : ");
						String n2 = JOptionPane.showInputDialog(null, "Enter stock : ");
						Integer p=0;
						Double s=0.0;
						if(n2!=null)
						{
							p = Integer.parseInt(n2);
						}
						if(n1!=null)
						{
							s = Double.parseDouble(n1);
						}
						obj1.accept(b,a,s,p);
					}
				}
				else if(t.equals("String"))
				{
					String t1 = JOptionPane.showInputDialog(null,"Id : ");
					if(uid2.exists(n,t1,t))
					{
						String b = JOptionPane.showInputDialog(null, "Enter book Name : ");
						String a = JOptionPane.showInputDialog(null, "Enter author : ");
						String n1 = JOptionPane.showInputDialog(null, "Enter price : ");
						String n2 = JOptionPane.showInputDialog(null, "Enter stock : ");
						Integer p=0;
						Double s=0.0;
						if(n2!=null)
						{
							p = Integer.parseInt(n2);
						}
						if(n1!=null)
						{
							s = Double.parseDouble(n1);
						}
						obj1.accept(b,a,s,p);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
			}
			catch(SQLException s1)
			{
				s1.printStackTrace();
			}
		}
		else if(e.getSource() == returnBook)
		{
			String id = JOptionPane.showInputDialog(null,"Enter your id : ");
			String b = JOptionPane.showInputDialog(null,"Enter Book name : ");
			try 
			{
				
				Statement stmt = con.createStatement();
				String s = "select category from library where name = '"+b+"';";
				ResultSet rs = stmt.executeQuery(s);
				try 
				{
					if(rs.next())
					{
						library_management.result = search.getLinks(rs.getString(1));
						library_management.category = rs.getString(1);
					}
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Double f = fine.fine(id, b, con);
				if(f!=0)
				{
					String s1 = "You have crossed the return date. Please pay fine of "+f+" rupees";
					JOptionPane.showMessageDialog(null, s1);
				}
				JOptionPane.showMessageDialog(null, "Book returned successfully");
				books.delete(b, id, con);
				link = new HyperlinkDemo();
				link.launchStage(args);
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}	
		}

		
	}
}

class library<T>
{
	Connection con = null;
	public library(Connection con) throws SQLException
	{
		this.con = con;
		Statement stmt = con.createStatement();
		stmt.executeUpdate("create table if not exists library(name varchar(20),author varchar(20),price float,stock int);");
	}
	public void accept(String s1, String s2, Double d, Integer i) throws SQLException
	{
		// TODO Auto-generated method stub
		Statement pre = con.createStatement();
		String s3 = "select price,stock from library where name = '"+s1+"';";
		ResultSet rs = pre.executeQuery(s3);
		if(rs.next())
		{
			Statement stmt = con.createStatement();
			String s = "update library set stock = "+(i+rs.getInt(2))+" where name = '"+s1+"';";
			stmt.executeUpdate(s);
		}
		else
		{
			Statement stmt = con.createStatement();
			String s = "insert into library values('"+s1+"','"+s2+"',"+d+","+i+");";
			stmt.executeUpdate(s);
		}
	}

	public String buy(String b,Connection con)throws SQLException
	{
		// TODO Auto-generated method stub
		Statement stmt = con.createStatement();
		String s1 = "select price,stock from library where name = '"+b+"';";
		ResultSet rs = stmt.executeQuery(s1);
		//System.out.println(1);
		if(rs.next())
		{
			double price = rs.getDouble(1);
			int copies = rs.getInt(2) - 1;
			String s2 = "update library set stock = "+copies+" where name = '"+b+"';";
			Statement stmt2 = con.createStatement();
			stmt2.executeUpdate(s2);
			String s3 = "delete from library where stock = 0;";
			Statement stmt3 = con.createStatement();
			stmt3.executeUpdate(s3);
			return ("Book has been issued");
		}
		else
		{
			return ("Book not available");
		}
	}

	public ResultSet display(Connection con) throws SQLException
	{
		// TODO Auto-generated method stub
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from library;");
		return rs;
		/*int cnt = 0;
		while(rs.next())
		{
			System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3)+"\t"+rs.getInt(4));
			cnt++;
		}
		if(cnt==0)
			return false;
		return true;*/
	}
	
}
class user<T>
{
	Connection con = null;
	public user(Connection con) throws SQLException
	{
		 this.con = con;
		 Statement stmt = con.createStatement();
		 stmt.executeUpdate("Create table if not exists userInt(uname varchar(20),uid int);");
		 Statement stmt1 = con.createStatement();
		 stmt1.executeUpdate("Create table if not exists userStr(uname varchar(20),uid varchar(20));");
	}
	public void new_user(String s, T t,ThreadPool tp,String type) throws SQLException
	{
		if(type.equals("Integer"))
		{
			Statement pre = con.createStatement();
			String s3 = "select * from userInt where uname = '"+s+"';";
			ResultSet rs = pre.executeQuery(s3);
			if(rs.next())
			{
				JOptionPane.showMessageDialog(null,"Username already exists!!");
			}
			else
			{
				Statement stmt = con.createStatement();
				String s1 = "insert into userInt values('"+s+"', "+t+");";
				stmt.executeUpdate(s1);
			}
		}
		else
		{
			Statement pre = con.createStatement();
			String s3 = "select * from userStr where uname = '"+s+"';";
			ResultSet rs = pre.executeQuery(s3);
			if(rs.next())
			{
				JOptionPane.showMessageDialog(null,"Username already exists!!");
			}
			else
			{
				Statement stmt = con.createStatement();
				String s1 = "insert into userStr values('"+s+"', '"+t+"');";
				stmt.executeUpdate(s1);
			}
		}
	}
	public boolean exists(String s, T t, String type)throws SQLException
	{
		boolean ans = false;
		if(type.equals("Integer"))
		{
			Statement stmt = con.createStatement();
			String s1 = "select * from userInt where uname = '"+s+"';";
			ResultSet rs = stmt.executeQuery(s1);
			if(rs.next())
			{
				ans=true;
			}
		}
		else
		{
			Statement stmt = con.createStatement();
			String s1 = "select * from userStr where uname = '"+s+"';";
			ResultSet rs = stmt.executeQuery(s1);
			if(rs.next())
			{
				ans=true;
			}
		}
		return ans;
	}
}

class thread extends Thread implements OnCallBack
{
	
	public void run() {
		
		//System.out.println(Thread.currentThread().getName() + " added to the system");
	}

	public void onCallBack(String s) {
		// TODO Auto-generated method stub
		System.out.println("\n" + s + " added to the system");
	}
}
class ThreadPool
{
	private OnCallBack ocb;
	ExecutorService executor;
	public ThreadPool(OnCallBack ocb)
	{
		executor = Executors.newFixedThreadPool(2);
		this.ocb = ocb;
	}
	public void addToPool(String s)
	{
		Thread user = new thread();
		user.setName(s);
		executor.execute(user);
		ocb.onCallBack(s);
	}
	public void end()
	{
		executor.shutdown();
	}
}
