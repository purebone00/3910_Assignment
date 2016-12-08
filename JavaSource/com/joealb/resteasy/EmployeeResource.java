package com.joealb.resteasy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.bcit.infosys.employee.Employee;

@ApplicationScoped
@Path("/employee")
public class EmployeeResource {
    
    public Employee find(int number) {
        Connection connection = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                Statement stmt = null;
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM Employee where employeeNumber = " + number);
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
            System.out.println("Error in find " + number);
            ex.printStackTrace();
            return null;
        }
        return null;
    }
    
    
    @GET
    @Path("login")
    public Response login(@QueryParam("username")String username, @QueryParam("password")String password) {
        String response = "";
        Connection connection = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                PreparedStatement stmt = null;
                try {
                    stmt = connection.prepareStatement("SELECT * FROM Employee WHERE employeeID = ? AND password = ?");
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    
                    ResultSet result = stmt.executeQuery();                   
                    
                    if (result.next()) {
                        int empID = result.getInt("employeeNumber");
                        response = "Your token is " + empID;
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
            response = "login failed";
            System.out.println("Error in find " + username);
            ex.printStackTrace();
            return null;
        }
        
        return Response.status(200).entity(response).build();
    }
    
    @GET
    @Path("login")
    public Response logOut(@QueryParam("username")String username, @QueryParam("password")String password) {
        String response = "";
        Connection connection = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                PreparedStatement stmt = null;
                try {
                    stmt = connection.prepareStatement("SELECT * FROM Employee WHERE employeeID = ? AND password = ?");
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    
                    ResultSet result = stmt.executeQuery();                   
                    
                    if (result.next()) {
                        String username1 = result.getString("employeeID");
                        int empID = result.getInt("employeeNumber");
                        response = username1 + " " + empID;
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
            response = "login failed";
            System.out.println("Error in find " + username);
            ex.printStackTrace();
            return null;
        }
        
        return Response.status(200).entity(response).build();
    }
    
    
    /**
     * merge Employee record fields into existing database record.
     * @param newPass the where clause.
     * @param user user to have password changed
     */
    @PUT
    @Path("/reset")
    public Response changePassword(@QueryParam("token")int token, @QueryParam("newPass")String newPass, @QueryParam("userName")String user) {
         String response = "";
         Connection connection = null;
         PreparedStatement stmt = null;
         try {
             try {
                 InitialContext ctx = new InitialContext();
                 DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                 connection = dataSource.getConnection();
                 try {
                     
                     if(find(token) != null) {
                         stmt = connection.prepareStatement("UPDATE Employee "
                                 + "SET password = ? " + "WHERE employeeID = ?");
                         stmt.setString(1, newPass);
                         stmt.setString(2, user);
                         stmt.executeUpdate();
                         response = "Success";
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
             response = "Error";
             System.out.println("Error in resetUser " + user);
             ex.printStackTrace();
         }
         
         return Response.status(200).entity(response).build();
     }
     
     
}
