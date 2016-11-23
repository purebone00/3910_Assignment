package infosys.beans;

import ca.bcit.infosys.access.EmployeeManager;
import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


/**
 * This is the employer list bean. This is held as an application scope and hold persists 
 * for as long as the system is running. This bean holds information regarding current 
 * employees and their credentials. 
 * @author Joe Fong
 *     Version 1.0
 */
@Named("el")
@SessionScoped
public class EmployeeLister implements Serializable{    

    /** A list of current employees. */
    private ArrayList<Employee> employees = new ArrayList<Employee>();

    /** The current employee on the system. */
    private Employee currentEmployee;

    @Inject EmployeeManager em;
    
    /**
     * Constructor to create an Employee list that contains all our employees. 
     */
    public EmployeeLister() {}
    

    /**
     * Gets an employee matching the parameter.
     * @param boolean if employee exist in list.
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
     * The List of employees.
     * @return List of employees.
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /** 
     * Deletes a user. 
     */
    public void deleteEmpoyee(Employee userToDelete) {
        em.remove(userToDelete);
    }

    /**
     * Adds a user.
     */
    public void addEmployee(Employee newEmployee) {
        em.persist(newEmployee);
    }
    
    public Employee findEmployee(String name) {
        return em.find(name);
    }
    
    public void setEmployees() {
        employees = em.getAll();
    }
    
    public void resetUser(String resetUser) {
        em.resetPassword(resetUser);
    }
    
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
