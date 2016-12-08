package com.joealb.resteasy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetRow;
import infosys.beans.EditableRow;
import infosys.beans.EditableTimesheet;

@ApplicationScoped
@Path("/timesheet")
public class TimesheetResource {

    /**
     * Return all Timesheets that were avaliable from the database.
     * @return ArrayList of all the timesheets
     */
    public ArrayList<EditableTimesheet> getAll() {
        ArrayList<EditableTimesheet> timesheetList = new ArrayList<EditableTimesheet>();
        Connection connection = null;
        Statement stmt = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM TimesheetLog");
                    while (result.next()) {
                        EditableTimesheet timesheet = 
                                new EditableTimesheet(
                                        getEmployee(result.getInt("employeeNumber")),
                                        result.getDate("endDate"),
                                        getAllRows(result.getInt("timesheetID")));
                        timesheet.setTimesheetId(result.getInt("timesheetID"));
                        timesheetList.add(timesheet);
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } catch (NamingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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

    /**
     * Grabs all the rows corresponding with a specific timesheet.
     * @param timesheetId the id of the timesheet the rows are associated with
     * @return An ArrayList of timesheetRows
     */
    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<TimesheetRow> getAllRows(@QueryParam("timesheetId")int timesheetId) {
        ArrayList<TimesheetRow> timesheetRows = new ArrayList<TimesheetRow>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                try {
                    preparedStatement = connection.prepareStatement(
                            "SELECT * FROM Timesheet WHERE timesheetID = ?");

                    preparedStatement.setInt(1, timesheetId);
                    ResultSet result = preparedStatement.executeQuery();
                    while (result.next()) {
                        final int id = result.getInt("projectID");
                        final String workpackage = result.getString("wp");

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
                        newRow.setRowId(result.getInt("timesheetRow"));
                        timesheetRows.add(newRow);
                    }
                } finally {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                }
            } catch (NamingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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

    /**
     * Grabs the employee that is associated with an id.
     * @param id of the employee
     * @return A employee created with properties pulled from the database.
     */
    public Employee getEmployee(int id) {
        Connection connection = null;
        Statement stmt = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
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
            } catch (NamingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
        return null;
    }

    
}
