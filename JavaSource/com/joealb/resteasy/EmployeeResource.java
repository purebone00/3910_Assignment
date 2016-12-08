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
    
    private int generateToken() {
        int token = (int) (Math.random() * 999);

        // String response = "Your token is: " + token + "\nFor login
        // combination: " + user + " : " + password;
        return token;
    }
    
    private Employee find(String token) {
        Connection connection = null;
        if(token.equals("loggedout"))
            return null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                PreparedStatement stmt = null;
                try {
                    stmt = connection.prepareStatement("SELECT * FROM Employee where token = ?");
                    stmt.setString(1, token);
                    ResultSet result = stmt.executeQuery();
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
            System.out.println("Error in find " + token);
            ex.printStackTrace();
            return null;
        }
        return null;
    }
    
    
    @GET
    @Path("login")
    public Response login(@QueryParam("username")String username, @QueryParam("password")String password) {
        String response = "";
        String token = "loggedout";
        boolean loggedIn = false;
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
                    loggedIn = true;
                    ResultSet result = stmt.executeQuery();                   
                    
                    if (result.next()) {
                        String username1 = result.getString("employeeName");
                        token = Integer.toString(generateToken()) + username1;
                        response = "The token is " + token;
                    } else {
                        return null;
                    }
                    stmt.close();
                    if(loggedIn) {
                        stmt = connection.prepareStatement("UPDATE Employee SET token = ? WHERE employeeID = ?");
                        stmt.setString(1, token);
                        stmt.setString(2, username);
                        stmt.executeUpdate();
                        stmt.close();
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
    
    @PUT
    @Path("logout")
    public Response logOut(@QueryParam("token")String token) {
        String response = "";
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                try {
                   
                    stmt = connection.prepareStatement("UPDATE Employee "
                            + "SET token = 'loggedout' " + "WHERE token = ?");
                    stmt.setString(1, token);
                    stmt.executeUpdate();
                    response = "logged out success";
                    
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
            response = "Error in loggedout rest";
            ex.printStackTrace();
        }
        
        return Response.status(200).entity(response).build();
    }

    
    /**
     * merge Employee record fields into existing database record.
     * @param newPass the where clause.
     * @param user user to have password changed
     */
    @PUT
    @Path("/changePassword")
    public Response changePassword(@QueryParam("token")String token, @QueryParam("newPass")String newPass, @QueryParam("userName")String user) {
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
