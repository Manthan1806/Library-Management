import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Student 
{
	String id,name;
	int books_on_rent;
	Connection con = null;
	
	public Student(Connection con) throws SQLException
	{
		this.con = con;
		Statement stmt = con.createStatement();
		String s1 = "create table if not exists Student(id varchar(15),name varchar(20),books_on_rent int);";
		stmt.executeUpdate(s1);
	}
	
	public boolean insert(String i,String n,Connection con) throws SQLException
	{
		boolean ans = false;
		Statement stmt1 = con.createStatement();
		String s1 = "select * from Student where id = '"+i+"';";
		ResultSet rs = stmt1.executeQuery(s1);
		if(rs.next())
		{
			int b = rs.getInt(3);
			if(b==3)
			{
				ans = false;
				return ans;
			}
			Statement stmt = con.createStatement();
			String s = "update Student set books_on_rent = "+(b+1)+" where id = '"+i+"';";
			stmt.executeUpdate(s);
			ans = true;
		}
		else
		{
			Statement stmt2 = con.createStatement();
			String s2 = "insert into Student values('"+i+"', '"+n+"', 1);";
			stmt2.executeUpdate(s2);
			ans = true;
		}
		return ans;
	}
	
	public void delete(String i,Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		String s = "update Student set books_on_rent = books_on_rent - 1 where id = '"+i+"';";
		stmt.executeUpdate(s);
	}
}                              


