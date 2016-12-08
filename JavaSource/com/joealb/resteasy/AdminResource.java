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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.bcit.infosys.employee.Employee;

@ApplicationScoped
@Path("/admin")
public class AdminResource {
    
    /**
     * Resets Employee record fields to have 'default' as password.
     * @param resetUser the where clause.
     */
    @PUT
    @Path("/reset")
    @Produces(MediaType.APPLICATION_XML)
    public Response resetPassword(@QueryParam("token")int token, @QueryParam("user")String resetUser) {
        String response = "";
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                try {
                    if(find(token).getEmpNumber() == 3) {
                        stmt = connection.prepareStatement("UPDATE Employee "
                                + "SET password = ? " + "WHERE employeeID = ?");
                        stmt.setString(1, "default");
                        stmt.setString(2, resetUser);
                        stmt.executeUpdate();
                        response = "Success, user: " + resetUser + " reseted.";
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
            response = "Error in resetUser";
            System.out.println("Error in resetUser " + resetUser);
            ex.printStackTrace();
        }
        
        return Response.status(200).entity(response).build();
    }

    
    
    
    /**
     * Remove Employee from database.
     * @param employeeNumber id
     */
    @DELETE
    @Path("/delete")
    public Response remove(@QueryParam("token")int token, @QueryParam("empno")int employeeNumber) {
        Connection connection = null;
        PreparedStatement stmt = null;
        String response = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                
                try {
                    if(find(token).getEmpNumber() == 3) {
                        stmt = connection.prepareStatement(
                                "DELETE FROM Employee WHERE employeeNumber = " + employeeNumber);
                        stmt.executeUpdate();
                        response = "Employee delete where is " + employeeNumber;
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
            response = "Error in remove " + employeeNumber;
            System.out.println("Error in remove " + employeeNumber);
            ex.printStackTrace();
        }
        return Response.status(200).entity(response).build();
    }
    
    
    /**
     * Persist Employee record into database.
     * @param employee the record to be persisted.
    */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_XML)
    public Response persist(
            @QueryParam("token")int token,
            @QueryParam("name")String employeeId,
            @QueryParam("user")String employeeName) {
        Connection connection = null;
        PreparedStatement stmt = null;
        String response = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                try {
                    if(find(token).getEmpNumber() == 3) {
                        stmt = connection.prepareStatement(
                                "INSERT INTO Employee "
                                + "(employeeNumber, employeeID, employeeName, password)"
                                + "VALUES (null, ?, ?, ?)");
                        stmt.setString(1, employeeId);
                        stmt.setString(2, employeeName);
                        stmt.setString(3, "default");
                        stmt.executeUpdate();
                        response = "Employee persisted " + employeeId;
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
            response = "Error in persist " + employeeId;
            System.out.println("Error in persist " + employeeId);
            ex.printStackTrace();
        }
        return Response.status(200).entity(response).build();
    }
    
   
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
    @Path("find")
    @Produces(MediaType.APPLICATION_XML)
    public Employee find(@QueryParam("token")int token,@QueryParam("number") int number) {
        Connection connection = null;
        try {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                connection = dataSource.getConnection();
                Statement stmt = null;
                try {
                    stmt = connection.createStatement();
                    
                    if(find(token).getEmpNumber() == 3) {
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
    @Path("view")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Employee> viewAllEmployee(@QueryParam("token")int token) {
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        Connection con = null;
        
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
            con = dataSource.getConnection();
            Statement stmt = null;    
            try{
                
               stmt = con.createStatement();
               
               if(find(token).getEmpNumber() == 3) {
                   ResultSet result = stmt.executeQuery("SELECT * FROM Employee");
                   System.out.println("connection successful.....");
                   while (result.next()) {
                       employeeList.add(new Employee(
                               result.getInt("employeeNumber"),
                               result.getString("employeeID"), 
                               result.getString("employeeName"), 
                               result.getString("password") 
                               ));
                   }
               }
                   
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(stmt != null)
                        stmt.close();
                    } catch(Exception e) {}
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        finally {
            try {
                if(con != null)
                    con.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return employeeList;
    }
    
}
