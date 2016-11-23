package ca.bcit.infosys.access;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

import ca.bcit.infosys.employee.Employee;

@RequestScoped
public class EmployeeManager {
    @Resource(mappedName="java:/employeeTimeSheet")
    private DataSource dataSource;
    
    
    /**
     * Find Employee record from database.
     * 
     * @param user ID of the Employee record.
     * @return the Employee record with key = id, null if not found.
     */
    public Employee find(String name) {
        Connection connection = null;
        Statement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM Employee where employeeID = '"
                                    + name + "'");
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
            System.out.println("Error in find " + name);
            ex.printStackTrace();
            return null;
        }
    }
    
    
    /**
     * Select all Employees Query.
     * @return ArrayList of Employees
     */
    public ArrayList<Employee> getAll() {
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        Connection connection = null;
        Statement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM Employee");
                    while (result.next()) {
                        employeeList.add(new Employee(
                                result.getInt("employeeNumber"),
                                result.getString("employeeID"), 
                                result.getString("employeeName"), 
                                result.getString("password") 
                                ));
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
        return employeeList;
    }
    
    /**
     * Persist Employee record into database.
     * @param supplier the record to be persisted.
     */
    public void persist(Employee employee) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "INSERT INTO Employee (employeeNumber, employeeID, employeeName, password)"
                         + "VALUES (null, ?, ?, ?)");
                    
                    stmt.setString(1, employee.getUserName());
                    stmt.setString(2, employee.getName());
                    stmt.setString(3, employee.getPassword());
                    stmt.executeUpdate();
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
            System.out.println("Error in persist " + employee);
            ex.printStackTrace();
        }
    }
    
    /**
     * Remove supplier from database.
     * @param supplier record to be removed from database
     */
    public void remove(Employee employee) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "DELETE FROM Employee WHERE employeeID =  ?");
                    stmt.setString(1, employee.getUserName());
                    stmt.executeUpdate();
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
            System.out.println("Error in remove " + employee.getUserName());
            ex.printStackTrace();
        }
    }

    /**
    * merge Employee record fields into existing database record.
    * @param Employee the record to be merged.
    */
   public void changePassword(String newPass, String user) {
      
       Connection connection = null;
       PreparedStatement stmt = null;
       try {
           try {
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
    
    /**
     * merge Employee record fields into existing database record.
     * @param Employee the record to be merged.
     */
    public void resetPassword(String resetUser) {
       
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.prepareStatement("UPDATE Employee "
                            + "SET password = ? " + "WHERE employeeID = ?");
                    stmt.setString(1, "default");
                    stmt.setString(2, resetUser);
                    
                    
                    stmt.executeUpdate();
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
            System.out.println("Error in resetUser " + resetUser);
            ex.printStackTrace();
        }
    }
}
