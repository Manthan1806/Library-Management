import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Books
{
	String id,name,issue_date,return_date,stud_id;
	Connection con;
	
	public Books(Connection con) throws SQLException
	{
		this.con = con;
		Statement stmt = con.createStatement();
		String s1 = "create table if not exists Books(name varchar(20), issue_date date, return_date date,stud_id varchar(20));";
		stmt.executeUpdate(s1);
	}
	
	public String insert(String n, String id, Connection con)throws SQLException
	{
		Statement stmt1 = con.createStatement();
		String s1 = "select to_days(curdate());";
		ResultSet rs1 = stmt1.executeQuery(s1);
		if(rs1.next())
			System.out.println(2);
		int issue = rs1.getInt(1);
		System.out.println(issue);
		int ret = issue+7;
		String s2 = "select from_days("+ret+");";
		Statement stmt2 = con.createStatement();
		ResultSet rs2 = stmt2.executeQuery(s2);
		if(rs2.next())
			System.out.println(3);
		System.out.println(rs2.getDate(1));
		Statement stmt3 = con.createStatement();
		String s3 = "insert into Books values('"+n+"',curdate(),'"+rs2.getString(1)+"','"+id+"');";
		stmt3.executeUpdate(s3);
		return rs2.getString(1);
	}
	
	public void delete(String n,String id,Connection con)throws SQLException
	{
		Statement stmt = con.createStatement();
		String s = "delete from Books where name = '"+n+"' and stud_id = '"+id+"';";
		stmt.executeUpdate(s);
	}
}


