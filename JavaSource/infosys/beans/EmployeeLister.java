package infosys.beans;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ca.bcit.infosys.employee.*;

public class EmployeeLister implements EmployeeList {

    @Inject Employee currentEmployee;
    List<Employee> employees;
    
  
    public List<Employee> getEmployees() {
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
        
        return null;
    }

    @Override
    public Employee getCurrentEmployee() {
        
        return currentEmployee;
    }

    @Override
    public Employee getAdministrator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean verifyUser(Credentials credential) {
        // TODO Auto-generated method stub
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
        
    }

    @Override
    public void addEmployee(Employee newEmployee) {
        // TODO Auto-generated method stub
        
    }

}
