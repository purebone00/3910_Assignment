package infosys.beans;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;



@Named("el")
@ApplicationScoped
public class EmployeeLister implements EmployeeList {
    private static int employeeNumber = 1000;
    private static ArrayList<Credentials> cred = new ArrayList<Credentials>();
    ArrayList<Employee> employees = new ArrayList<Employee>();
    Map<String, String> logInfo = new HashMap<String, String>();
    Employee currentEmployee;
    
    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }
    
    /**
     * Creates an Employee list that contains all our employees. 
     */
    public EmployeeLister() { 
        Employee admin = new Employee("Admin", 0000, "Admin"); 
        addEmployee(admin);
        Credentials adminCred = new Credentials();
        adminCred.setUserName(admin.getUserName());
        adminCred.setPassword("default");
        
        logInfo.put(adminCred.getUserName(), adminCred.getPassword());
        cred.add(adminCred);
               
        Employee e1 = new Employee("John", getEmployeeNumber(), "employeeJohn"); 
        addEmployee(e1);
        Credentials credential = new Credentials();
        credential.setUserName(e1.getUserName());
        credential.setPassword("default");
        
        logInfo.put(credential.getUserName(), credential.getPassword());
        cred.add(credential);
        
        Employee e2 = new Employee("Jovina", getEmployeeNumber(), "employeeLow"); 
        addEmployee(e2);
        Credentials credentialsTwo = new Credentials();
        credentialsTwo.setUserName(e2.getUserName());
        credentialsTwo.setPassword("default");
        
        
        logInfo.put(credentialsTwo.getUserName(), credentialsTwo.getPassword());
        cred.add(credentialsTwo);
        
    }
    
    public int getEmployeeNumber() {
        employeeNumber++;
        return employeeNumber;
    }
    
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
     * Get the current employee. 
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
     * Gets the current administrator.
     */
    public Employee getAdministrator() {
        for (Employee e: employees) {
            if (e.getEmpNumber() == 0000) {
                return e;
            }
        }
        return null;
    }
    
    public ArrayList<Credentials> getCred() {
        return cred;
    }
    
    public Map<String, String> getLogInfo() {
        return logInfo;
    }
    
    public ArrayList<Employee> getEmployees() {
        return employees;
    }
    
    public Map<String, String> getLoginCombos() {
     
        return logInfo;
    }

    
    @Override
    public boolean verifyUser(Credentials credential) {
        return false;
    }

    public String logout(Employee employee) {
        return "log out";
    }

    public void deleteEmpoyee(Employee userToDelete) {
        employees.remove(userToDelete);
        
    }

    public void addEmployee(Employee newEmployee) {
        employees.add(newEmployee);
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    
    

}
