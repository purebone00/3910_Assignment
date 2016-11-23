package ca.bcit.infosys.access;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetRow;
import infosys.beans.EditableRow;
import infosys.beans.EditableTimesheet;


@RequestScoped
public class TimesheetManager {
    @Resource(mappedName="java:/employeeTimeSheet")
    private DataSource dataSource;
    
    /**
     * Return all Timesheets.
     * @return 
     */
    public ArrayList<EditableTimesheet> getAll() {
        ArrayList<EditableTimesheet> timesheetList = new ArrayList<EditableTimesheet>();
        Connection connection = null;
        Statement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM TimesheetLog");
                    while (result.next()) {
                    	EditableTimesheet timesheet = 
                    			new EditableTimesheet(getEmployee(result.getInt("employeeNumber")),
                    			result.getDate("endDate"),
                    			getAllRows(result.getInt("timesheetID")));
                    	timesheet.setTimesheetID(result.getInt("timesheetID"));
                    	timesheetList.add(timesheet);
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAll");
            ex.printStackTrace();
            return null;
        }
        
        
        return timesheetList;
    }
    
    public ArrayList<TimesheetRow> getAllRows(int timesheetId) {
    	ArrayList<TimesheetRow> timesheetRows = new ArrayList<TimesheetRow>();
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
            	connection = dataSource.getConnection();
                try {
                	preparedStatement = connection.prepareStatement("SELECT * FROM Timesheet WHERE timesheetID = ?");
                	
                    preparedStatement.setInt(1, timesheetId);
                    ResultSet result = preparedStatement.executeQuery();
                    while (result.next()) {
                    	int id = result.getInt("projectID");
                    	String workpackage = result.getString("wp");
                    	
                    	BigDecimal[] hours = new BigDecimal[Timesheet.DAYS_IN_WEEK];
                    	
                    	hours[0] = result.getBigDecimal("saturday");
                    	
                    	hours[1] = result.getBigDecimal("sunday");
                    	
                    	hours[2] = result.getBigDecimal("monday");
                    	
                    	hours[3] = result.getBigDecimal("tuesday");
                    	
                    	hours[4] = result.getBigDecimal("wednesday");
                    	
                    	hours[5] = result.getBigDecimal("thursday");
                    	
                    	hours[6] = result.getBigDecimal("friday");

                    	String notes = result.getString("notes");
                    	
                    	EditableRow newRow = new EditableRow(id, workpackage, hours, notes);
                    	newRow.setRowID(result.getInt("timesheetRow"));
                    	timesheetRows.add(newRow);
                    }
                } finally {
                    if (preparedStatement != null) {
                    	preparedStatement.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAllRows");
            ex.printStackTrace();
            return null;
        }
        
        return timesheetRows;
    }
    
    public Employee getEmployee(int id) {
    	Connection connection = null;
        Statement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM Employee WHERE employeeNumber = " + id);
                    if (result.next()) {
                        return new Employee(result.getInt("employeeNumber"),
                                result.getString("employeeID"),
                                result.getString("employeeName"),
                                result.getString("password")
                               );
                    } else {
                        return null;
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in find employee: " + id);
            ex.printStackTrace();
            return null;
        }
    }
    
    public void update(EditableRow timesheetRow) {
        Connection connection = null;
        PreparedStatement updateQuery = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                	updateQuery = connection.prepareStatement("UPDATE timesheet SET " +
                								"projectID=?, " +
                								"wp=?, " +
                								"saturday=?, " +
                								"sunday=?, " + 
                								"monday=?, " + 
                								"tuesday=?, " + 
                								"wednesday=?, " + 
                								"thursday=?, " + 
                								"friday=?, " + 
                								"notes=? " +
                								"WHERE timesheetRow=?;");
                	updateQuery.setInt(1, timesheetRow.getProjectID());
                	updateQuery.setString(2, timesheetRow.getWorkPackage());
                	updateQuery.setBigDecimal(3, timesheetRow.getSaturday() );

                	updateQuery.setBigDecimal(4, timesheetRow.getSunday() );

                	updateQuery.setBigDecimal(5, timesheetRow.getMonday() );

                	updateQuery.setBigDecimal(6, timesheetRow.getTuesday() );

                	updateQuery.setBigDecimal(7, timesheetRow.getWednesday() );

                	updateQuery.setBigDecimal(8, timesheetRow.getThursday() );

                	updateQuery.setBigDecimal(9, timesheetRow.getFriday() );

                    updateQuery.setString(10, timesheetRow.getNotes());
                    
                    updateQuery.setInt(11, timesheetRow.getRowID());
                    
                    updateQuery.executeUpdate();
                } finally {
                    if (updateQuery != null) {
                    	updateQuery.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in update");
            ex.printStackTrace();
        }
    }
    
    public void create(EditableTimesheet timesheet) {
    	Connection connection = null;
        PreparedStatement query = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                	query = connection.prepareStatement("INSERT INTO timesheetlog " 
                			+ "VALUES(NULL, ?, ?, ?);");
                	query.setInt(1, timesheet.getEmployee().getEmpNumber());
                	query.setDate(2, new java.sql.Date (timesheet.getEndWeek().getTime()));
                	query.setInt(3, timesheet.getWeekNumber());
                	
                	query.executeUpdate();
                	query.close();
                	
                	query = connection.prepareStatement("SELECT timesheetID FROM timesheetlog WHERE timesheetID=last_insert_id()");
                
                	ResultSet result = query.executeQuery();
                	while (result.next()) {
                		timesheet.setTimesheetID(result.getInt("timesheetID"));
                	}               	
                } finally {
                    if (query != null) {
                    	query.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in createOne");
            ex.printStackTrace();
        }
    }
    
    public void createRow(EditableRow timesheetRow, int timesheetID) {
    	Connection connection = null;
        PreparedStatement query = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                	query = connection.prepareStatement("INSERT INTO timesheet "
                			+ "(timesheetRow, projectID, wp, saturday, sunday, monday, tuesday, wednesday, thursday, friday, notes, timesheetID) "
                			+ "VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
                	query.setInt(1, timesheetRow.getProjectID());
                	query.setString(2, timesheetRow.getWorkPackage());
                	query.setBigDecimal(3, timesheetRow.getSaturday());
                	query.setBigDecimal(4, timesheetRow.getSunday());
                	query.setBigDecimal(5, timesheetRow.getMonday());
                	query.setBigDecimal(6, timesheetRow.getTuesday());
                	query.setBigDecimal(7, timesheetRow.getWednesday());
                	query.setBigDecimal(8, timesheetRow.getThursday());
                	query.setBigDecimal(9, timesheetRow.getFriday());
                	query.setString(10, timesheetRow.getNotes());
                	query.setInt(11, timesheetID);
                	
                	query.executeUpdate();
                } finally {
                    if (query != null) {
                    	query.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in createOne");
            ex.printStackTrace();
        }
    }
    
}
