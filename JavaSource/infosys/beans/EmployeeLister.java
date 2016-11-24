package infosys.beans;

import ca.bcit.infosys.access.EmployeeManager;
import ca.bcit.infosys.employee.Employee;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


/**
 * This is the employer list bean. This bean holds information regarding current 
 * employees and their credentials. 
 * @author Joe Fong
 *     Version 1.0
 */
@Named("el")
@SessionScoped
public class EmployeeLister implements Serializable {    

    /** A list of current employees. */
    private ArrayList<Employee> employees = new ArrayList<Employee>();

    /** The current employee on the system. */
    private Employee currentEmployee;
    
    /** EmployeeManager for database calls. */
    @Inject EmployeeManager em;
    
    /**
     * Constructor to create an Employee list that contains all our employees. 
     */
    public EmployeeLister() {}
    

    /**
     * Gets an employee matching the parameter.
     * @param name if employee exist in list.
     * @return boolean. 
     */
    public boolean getEmployee(String name) {
        for (Employee x : employees) {
            if (x.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets an employee matching the parameter.
     * @param name if employee exist in list.
     * @return boolean. 
     */
    public int searchEmployeeNumber(String name) {
        for (Employee x : employees) {
            if (x.getUserName().equals(name)) {
                return x.getEmpNumber();
            }
        }
        return -1;
    }
    
    /**
     * The List of employees.
     * @return List of employees.
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /** 
     * Deletes a user. 
     */
    public void deleteEmpoyee(int employeeNumber) {
        em.remove(employeeNumber);
    }

    /**
     * Adds a user.
     */
    public void addEmployee(Employee newEmployee) {
        em.persist(newEmployee);
    }
    
    /**
     * Finds the employee by name from the database.
     * @param name of employee.
     * @return the employee found.
     */
    public Employee findEmployee(int employeeNumber) {
        return em.find(employeeNumber);
    }
    
    /**
     * Sets the ArrayList of Employee to correspond with the database.
     */
    public void setEmployees() {
        employees = em.getAll();
    }
    
    /**
     * Resets user to have a password of 'default'.
     * @param resetUser name of user to be reset. 
     */
    public void resetUser(String resetUser) {
        em.resetPassword(resetUser);
    }
    
    /**
     * Changes the password for the user.
     * @param password to be changed.
     * @param user to have password changed.
     */
    public void changePassword(String password, String user) {
        em.changePassword(password, user);
    }

    /**
     * Gets the current employee.
     * @return the current employee.
     */
    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    /**
     * Sets a current employee.
     * @param currentEmployee is set to be the current employee.
     */
    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

}
