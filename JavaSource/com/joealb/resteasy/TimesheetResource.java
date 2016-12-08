package com.joealb.resteasy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mysql.jdbc.Util;

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
    @GET
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
    @GET
    @Path("/employee")
    @Produces(MediaType.APPLICATION_XML)
    public Employee getEmployee(@QueryParam("id")int id) {
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
    
    @PUT
    @Path("/update")
    public Response update(@QueryParam("projectID")int projectID,
                       @QueryParam("wp") String wp, 
                       @QueryParam("saturday") int saturday,
                       @QueryParam("sunday") int sunday,
                       @QueryParam("monday") int monday,
                       @QueryParam("tuesday") int tuesday,
                       @QueryParam("wednesday") int wednesday,
                       @QueryParam("thursday") int thursday,
                       @QueryParam("friday") int friday,
                       @QueryParam("notes") String notes,
                       @QueryParam("id") int id) {
        String response = "";
        Connection connection = null;
        PreparedStatement updateQuery = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                try {
                    updateQuery = connection.prepareStatement("UPDATE timesheet SET " 
                            + "projectID=?, " 
                            + "wp=?, " 
                            + "saturday=?, " 
                            + "sunday=?, " 
                            + "monday=?, " 
                            + "tuesday=?, " 
                            + "wednesday=?, " 
                            + "thursday=?, " 
                            + "friday=?, " 
                            + "notes=? " 
                            + "WHERE timesheetRow=?;");
                    updateQuery.setInt(1, projectID);
                    updateQuery.setString(2, wp);
                    updateQuery.setBigDecimal(3, new BigDecimal(saturday));

                    updateQuery.setBigDecimal(4, new BigDecimal(sunday));

                    updateQuery.setBigDecimal(5, new BigDecimal(monday));

                    updateQuery.setBigDecimal(6, new BigDecimal(tuesday));

                    updateQuery.setBigDecimal(7, new BigDecimal(wednesday));

                    updateQuery.setBigDecimal(8, new BigDecimal(thursday));

                    updateQuery.setBigDecimal(9, new BigDecimal(friday));

                    updateQuery.setString(10, notes);

                    updateQuery.setInt(11, id);

                    updateQuery.executeUpdate();
                    response = "Success";
                } finally {
                    if (updateQuery != null) {
                        updateQuery.close();
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
            response = "error";
            System.out.println("Error in update");
            ex.printStackTrace();
        }
        
        return Response.status(200).entity(response).build();

    }
    
    
    /**
     * Creates a new timesheet Entry in the timesheetlog database.
     * @param timesheet represents the new timesheet
     */
    @POST
    @Path("/create")
    public Response create(@QueryParam("empNum") int empNum, 
                       @QueryParam("endDate") String endDate, 
                       @QueryParam("endWeek") int endWeek) {
        Connection connection = null;
        PreparedStatement query = null;
        String response = "";
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                try {
                    query = connection.prepareStatement("INSERT INTO timesheetlog " 
                            + "VALUES(NULL, ?, ?, ?);");
                    query.setInt(1, empNum);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = formatter.parse(endDate);
                    query.setDate(2, new java.sql.Date(date.getTime()));
                    query.setInt(3, endWeek);

                    query.executeUpdate();
                    query.close();        
                    response = "success";
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    if (query != null) {
                        query.close();
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
            response = "fail";
            System.out.println("Error in createOne");
            ex.printStackTrace();
        }
        return Response.status(200).entity(response).build();

    }

    
    /**
     * Creates a new entry in the Timesheet database that holds the information
     * for a row.
     * @param timesheetRow object to be stored
     * @param timesheetId timesheet it is associated with
     */
    @POST
    @Path("/createRow")
    public void createRow(@QueryParam("projectID")int projectID,
            @QueryParam("wp") String wp, 
            @QueryParam("saturday") int saturday,
            @QueryParam("sunday") int sunday,
            @QueryParam("monday") int monday,
            @QueryParam("tuesday") int tuesday,
            @QueryParam("wednesday") int wednesday,
            @QueryParam("thursday") int thursday,
            @QueryParam("friday") int friday,
            @QueryParam("notes") String notes,
            @QueryParam("id") int id) {
        Connection connection = null;
        PreparedStatement query = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                try {
                    query = connection.prepareStatement("INSERT INTO timesheet "
                            + "(timesheetRow, projectID, wp, "
                            + "saturday, sunday, monday, tuesday, wednesday, " 
                            + "thursday, friday, notes, timesheetID) "
                            + "VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
                    query.setInt(1, projectID);
                    query.setString(2, wp);
                    query.setBigDecimal(3, new BigDecimal(saturday));

                    query.setBigDecimal(4, new BigDecimal(sunday));

                    query.setBigDecimal(5, new BigDecimal(monday));

                    query.setBigDecimal(6, new BigDecimal(tuesday));

                    query.setBigDecimal(7, new BigDecimal(wednesday));

                    query.setBigDecimal(8, new BigDecimal(thursday));

                    query.setBigDecimal(9, new BigDecimal(friday));

                    query.setString(10, notes);

                    query.setInt(11, id);
                    query.executeUpdate();
                } finally {
                    if (query != null) {
                        query.close();
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
            System.out.println("Error in createOne");
            ex.printStackTrace();
        }
    }

    
}
