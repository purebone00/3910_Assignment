package ca.bcit.infosys.access;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetRow;
import infosys.beans.EditableTimesheet;

public class Database {

    
    public ArrayList<Employee> getResultsEmployee() throws SQLException {
        String s = "select * from Employee";
        Connection con = null;
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            System.out.println("Driver failed to load.");
            return null;
        }

        try
        {
            con = DriverManager.getConnection("jdbc:mysql:///asn2","joealbert","joealb");
            PreparedStatement preparedStatement = con.prepareStatement(s);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int employeeNumber = rs.getInt("employeeNumber");
                String employeeId = rs.getString("employeeID");
                String employeeName = rs.getString("employeeName");
                String password = rs.getString("password");
                
                Employee supply = new Employee(employeeNumber,employeeId,employeeName,password);
                employeeList.add(supply);
            }
            System.out.println("Connected");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally {
            con.close();
        }
        
        return employeeList;
    }
    
    public Employee getEmployee(int employeeNumber) throws SQLException {
        String s = "select * from Employee WHERE employeeNumber = ?";
        Connection con = null;
        Employee selectedEmployee = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            System.out.println("Driver failed to load.");
            return null;
        }

        try
        {
            con = DriverManager.getConnection("jdbc:mysql:///asn2","joealbert","joealb");
            PreparedStatement preparedStatement = con.prepareStatement(s);
            preparedStatement.setInt(1, employeeNumber);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String employeeId = rs.getString("employeeID");
                String employeeName = rs.getString("employeeName");
                String password = rs.getString("password");
                   
                selectedEmployee = new Employee(employeeNumber,employeeId,employeeName,password);
            }
            System.out.println("Connected");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally {
            con.close();
        }
        
        return selectedEmployee;
    }
    
    public EditableTimesheet getTimesheet(int timesheetID) throws SQLException {
        String s1= "SELECT * FROM TimesheetLog WHERE timesheetID = ?";
        Connection con = null;
        EditableTimesheet timesheet = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            System.out.println("Driver failed to load.");
            return null;
        }
    
        try
        {
            con = DriverManager.getConnection("jdbc:mysql:///asn2","joealbert","joealb");
            PreparedStatement preparedStatement = con.prepareStatement(s1);
            
            preparedStatement.setInt(1, timesheetID);
                        
            ResultSet rs = preparedStatement.executeQuery();
           
            while(rs.next()) {
                
                int empNum = rs.getInt("employeeNumber");
                Employee currentEmployee = getEmployee(empNum);
                Date newDate = rs.getDate("endDate");
                
                timesheet = new EditableTimesheet(currentEmployee, newDate, getResultsTimeSheet(timesheetID));

            }
                        
            
            System.out.println("Connected");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally {
            con.close();
        }
        
        return timesheet;
    }
    
    public ArrayList<TimesheetRow> getResultsTimeSheet(int timesheetID) throws SQLException {
        String s = "select * from Timesheet WHERE timesheetID = ?";
        Connection con = null;
        ArrayList<TimesheetRow> timesheetList = new ArrayList<TimesheetRow>();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            System.out.println("Driver failed to load.");
            return null;
        }

        try
        {
            con = DriverManager.getConnection("jdbc:mysql:///asn2","joealbert","joealb");
            PreparedStatement preparedStatement = con.prepareStatement(s);
            preparedStatement.setInt(1, timesheetID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int timesheetRow = rs.getInt("timesheetRow");
                int projectId = rs.getInt("projectID");
                String wp = rs.getString("wp");
                BigDecimal hoursInWeek[] = new BigDecimal[7];
                hoursInWeek[0] =BigDecimal.valueOf(rs.getInt("saturday"));
                hoursInWeek[1] = BigDecimal.valueOf(rs.getInt("sunday"));
                hoursInWeek[2] = BigDecimal.valueOf(rs.getInt("monday"));
                hoursInWeek[3] = BigDecimal.valueOf(rs.getInt("tuesday"));
                hoursInWeek[4] = BigDecimal.valueOf(rs.getInt("wednesday"));
                hoursInWeek[5] = BigDecimal.valueOf(rs.getInt("thursday"));
                hoursInWeek[6] = BigDecimal.valueOf(rs.getInt("friday"));
                String notes = rs.getString("notes");
                
                
                TimesheetRow time = new TimesheetRow(projectId,wp,hoursInWeek,notes);
                timesheetList.add(time);
            }
            System.out.println("Connected");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally {
            con.close();
        }
        
        return timesheetList;
    }
    
    
    
    
}
