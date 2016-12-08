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

import ca.bcit.infosys.employee.Employee;

@ApplicationScoped
@Path("/employee")
public class EmployeeResource {
    
    
    /**
     * merge Employee record fields into existing database record.
     * @param newPass the where clause.
     * @param user user to have password changed
     */
    @PUT
    @Path("/reset")
     public void changePassword(@QueryParam("newPass")String newPass, @QueryParam("userName")String user) {
           
         Connection connection = null;
         PreparedStatement stmt = null;
         try {
             try {
                 InitialContext ctx = new InitialContext();
                 DataSource dataSource = (DataSource)ctx.lookup("java:/employeeTimeSheet");
                 connection = dataSource.getConnection();
                 try {
                     stmt = connection.prepareStatement("UPDATE Employee "
                             + "SET password = ? " + "WHERE employeeID = ?");
                     stmt.setString(1, newPass);
                     stmt.setString(2, user);
                    
                    
                     stmt.executeUpdate();
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
             System.out.println("Error in resetUser " + user);
             ex.printStackTrace();
         }
     }
     
     
}
