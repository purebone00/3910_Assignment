package infosys.beans;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


/**
 * This is the employer list bean. This is held as an application scope and hold persists 
 * for as long as the system is running. This bean holds information regarding current 
 * employees and their credentials. 
 * @author Joe Fong
 *     Version 1.0
 */
@Named("el")
@ApplicationScoped
public class EmployeeLister implements EmployeeList {
    /** An employee number starting from 1000. */
    private static int employeeNumber = 1000;
    /** A list of credentials. */
    private static ArrayList<Credentials> cred = new ArrayList<Credentials>();
    /** A list of current employees. */
    private static ArrayList<Employee> employees = new ArrayList<Employee>();
    /** A a map of current employees. */
    private static Map<String, String> logInfo = new HashMap<String, String>();
    /** The current employee on the system. */
    private Employee currentEmployee;

    /**
     * Constructor to create an Employee list that contains all our employees. 
     */
    public EmployeeLister() { 
        Employee admin = new Employee("Admin", 0000, "Admin"); 
        addEmployee(admin);
        Credentials adminCred = new Credentials();
        adminCred.setUserName(admin.getUserName());
        adminCred.setPassword("default");
        
        logInfo.put(adminCred.getUserName(), adminCred.getPassword());
        cred.add(adminCred);   
    }
    
    /**
     * Gets an employee number and increment the number by 1. 
     * @return the employee number.
     */
    public int getEmployeeNumber() {
        employeeNumber++;
        return employeeNumber;
    }
    
    /**
     * Adds a credential to the list of credentials.
     * @param credentials to be added to the credential's list.
     */
    public void addCred(Credentials credentials) {
        cred.add(credentials);
    }
    
    /**
     * Gets the credentials of an employee.
     * @param employee to have credentials extracted. 
     * @return The employee's credentials.
     */
    public Credentials getCreds(Employee employee) {
        for (Credentials c: cred) {
            if (employee.getUserName() == c.getUserName()) {
                return c;
            }
        }
        return null;
    }

    /**
     * Gets an employee matching the parameter.
     * @param name matching the employee.
     * @return An employee matching name. 
     */
    public Employee getEmployee(String name) {
        for (Employee x : employees) {
            if (x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }

    /**
     * Gets the administrator.
     * @return the administrator.
     */
    public Employee getAdministrator() {
        for (Employee e: employees) {
            if (e.getEmpNumber() == 0000) {
                return e;
            }
        }
        return null;
    }
    
    /**
     * The list of credentials.
     * @return List of credentials.
     */
    public ArrayList<Credentials> getCred() {
        return cred;
    }
    
    /**
     * The map of users.
     * @return Map of users.
     */
    public Map<String, String> getLoginCombos() {
        return logInfo;
    }
    
    /**
     * The List of employees.
     * @return List of employees.
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Verifies the current user.
     * @return false.
     */
    @Override
    public boolean verifyUser(Credentials credential) {
        return false;
    }

    /**
     * Logs the user out. 
     * @return String for navigating.
     */
    public String logout(Employee employee) {
        return "log out";
    }

    /** 
     * Deletes a user. 
     */
    public void deleteEmpoyee(Employee userToDelete) {
        employees.remove(userToDelete);
        
    }

    /**
     * Adds a user.
     */
    public void addEmployee(Employee newEmployee) {
        employees.add(newEmployee);
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
