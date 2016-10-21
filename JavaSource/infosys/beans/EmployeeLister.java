package infosys.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import ca.bcit.infosys.employee.*;

@Named("el")
@ApplicationScoped
public class EmployeeLister implements EmployeeList {


    ArrayList<Employee> employees = new ArrayList<Employee>();
    Map<String, String> logInfo = new HashMap<String, String>();
    Employee currentEmployee;
    
    public EmployeeLister() {
        Employee e1 = new Employee("John", 12345, "employeeJohn"); 
        addEmployee(e1);
        Credentials c = new Credentials();
        c.setUserName(e1.getUserName());
        c.setPassword("default");
        
        logInfo.put(c.getUserName(), c.getPassword());
        
        
        Employee e2 = new Employee("Jovina", 12346, "employeeLow"); 
        addEmployee(e2);
        Credentials c2 = new Credentials();
        c2.setUserName(e2.getUserName());
        c2.setPassword("default");
        
        logInfo.put(c2.getUserName(), c2.getPassword());
        
    }
    
    public Map<String, String> getLogInfo() {
        return logInfo;
    }
    
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public Employee getEmployee(String name) {
        for (Employee x : employees) {
            if (x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }
    

    public Map<String, String> getLoginCombos() {
        return logInfo;
    }

    public Employee getAdministrator() {
        return null;
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
    
    @Override
    public Employee getCurrentEmployee(ArrayList<Employee> e, int i) {
        return e.get(i);
    }

}
