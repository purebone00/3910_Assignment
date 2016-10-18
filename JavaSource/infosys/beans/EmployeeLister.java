package infosys.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ca.bcit.infosys.employee.*;

public class EmployeeLister implements EmployeeList {


    ArrayList<Employee> employees = new ArrayList<Employee>();
    Map<String, String> logInfo = new HashMap<String, String>();
    
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

    @Override
    public Employee getAdministrator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean verifyUser(Credentials credential) {
        // TODO Auto-generated method stub
        for(Employee e: employees) {
            return (credential.getUserName() == e.getUserName()) && 
                    (credential.getPassword() == logInfo.get(e.getUserName()));
        }
        return false;
    }

    @Override
    public String logout(Employee employee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteEmpoyee(Employee userToDelete) {
        // TODO Auto-generated method stub
        employees.remove(userToDelete);
        
    }

    @Override
    public void addEmployee(Employee newEmployee) {
        // TODO Auto-generated method stub
        employees.add(newEmployee);
    }

    @Override
    public Employee getCurrentEmployee() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Employee getCurrentEmployee(ArrayList<Employee> e, int i) {
        return e.get(i);
    }

}
