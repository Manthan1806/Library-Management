import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class IssueRequest {
	public IssueRequest(Connection con)throws SQLException
	{
		Statement stmt = con.createStatement();
		String s = "create table if not exists issue_request(id varchar(15),book varchar(20))";
		stmt.executeUpdate(s);
	}
	
	public void sendEmail(String b,Connection con)throws SQLException
	{
		Statement stmt1 = con.createStatement();
		String s1 = "select * from issue_request where book = '"+b+"';";
		ResultSet rs = stmt1.executeQuery(s1);
		String id = "";
		if(rs.next())
		{
			id = rs.getString(1);
			System.out.println(rs.getString(1));
		}
		Statement stmt2 = con.createStatement();
		String s2 = "select email from StudentInfo where id = '"+id+"';";
		ResultSet rs2 = stmt2.executeQuery(s2);
		if(rs2.next())
		{
			library_management.Address = rs2.getString(1);
			System.out.println(rs2.getString(1));
		}
	}
	
	public void issue(Connection con,String id,String b)throws SQLException
	{
		Statement stmt1 = con.createStatement();
		String s = "insert into issue_request values('"+id+"','"+b+"');";
		stmt1.executeUpdate(s);
	}
}
