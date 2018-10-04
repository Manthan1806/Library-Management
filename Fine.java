import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Fine
{
	public Fine(Connection con)throws SQLException
	{
		Statement stmt = con.createStatement();
		String s1 = "create table if not exists Fine(id varchar(15),book varchar(15),fine int,date date);";
		stmt.executeUpdate(s1);
		
	}
	public double fine(String id,String b,Connection con)throws SQLException
	{
		Double fine = 0.0;
		/*String s2 = "create procedure fine_cal" + 
				"    fine_amt int;" + 
				"    days int; " + 
				"    d_o_p date;   " + 
				"    begin" + 
				"    	select return_date into d_o_p from Books where stud_id = '"+id+"' and name = '"+b+"';" + 
				"	    days := round(to_number(sysdate - to_date(d_o_p)));" + 
				"    	if(days<30) then" + 
				"    		fine_amt := (days)*5;" + 
				"    	elsif(days>30) then" + 
				"   		fine_amt := (days-30)*50 + 75;" + 
				"   	else" + 
				"   		fine_amt := 0;" + 
				"   	end if;" + 
				"   	if(fine_amt>0)" + 
				"      then                                              " + 
				"   		insert into Fine values('"+id+"','"+b+"',fine_amt,sysdate);" + 
				"   	end if;" + 
				"   	update Student set books_on_rent = books_on_rent - 1 where id = '"+id+"';" + 
				"   	Exception" + 
				"   	when no_data_found then" + 
				"   		dbms_output.put_line('ID not found');" + 
				"   end;"+
				"   /";*/
		CallableStatement cs = con.prepareCall("{call fine_cal(?,?)};");
		cs.setString(1, id);
		cs.setString(2, b);
		cs.execute();
		Statement stmt = con.createStatement();
		String s1 = "select fine from Fine where id = '"+id+"' and book = '"+b+"' and date = curdate();";
		ResultSet rs = stmt.executeQuery(s1);
		if(rs.next())
			fine = rs.getDouble(1);	
		return fine;
	}
}


