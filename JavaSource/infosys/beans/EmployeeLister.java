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
    private static int employeeNumber = 1000;
    private static ArrayList<Credentials> cred = new ArrayList<Credentials>();
    ArrayList<Employee> employees = new ArrayList<Employee>();
    Map<String, String> logInfo = new HashMap<String, String>();
    Employee currentEmployee;
    
    public void setCurrentEmployee(Employee currentEmployee) {
		this.currentEmployee = currentEmployee;
	}

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
        Credentials c = new Credentials();
        c.setUserName(e1.getUserName());
        c.setPassword("default");
        
        logInfo.put(c.getUserName(), c.getPassword());
        cred.add(c);
        
        Employee e2 = new Employee("Jovina", getEmployeeNumber(), "employeeLow"); 
        addEmployee(e2);
        Credentials c2 = new Credentials();
        c2.setUserName(e2.getUserName());
        c2.setPassword("default");
        
        
        logInfo.put(c2.getUserName(), c2.getPassword());
        cred.add(c2);
        
    }
    
	public int getEmployeeNumber() {
	    employeeNumber++;
	    return employeeNumber;
	}
	
    public void addCred(Credentials c) {
        cred.add(c);
    }
    
    public Credentials getCreds(Employee e) {
        for(Credentials c: cred) {
            if(e.getUserName() == c.getUserName()) {
                return c;
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
        for(Employee e: employees) {
            if(e.getEmpNumber() == 0000) {
                return e;
            }
        }
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
