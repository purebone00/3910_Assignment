package infosys.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ca.bcit.infosys.employee.Employee;

@Named("controller")
@SessionScoped
public class ControllerBean implements Serializable{
    EmployeeLister list;
    String userName;
    String password;
    
    Integer empId;
    String oldPassword;
    String newPassword;
    String confirmPassword;

    
    public ControllerBean() {
        list = new EmployeeLister();
    }
    
    
    public String verifyLogin() {
        
        for(Employee e: list.getEmployees()) {
            if(e.getEmpNumber() == 0000) 
                if((getUserName().equals(e.getUserName())) &&
                        (getPassword().equals(list.getLoginCombos().get(e.getUserName())))) 
                    return "admin";   
        }
        
        for(Employee e: list.getEmployees()) {
            if((getUserName().equals(e.getUserName())) &&
                    (getPassword().equals(list.getLoginCombos().get(e.getUserName())))) 
                return "next"; 
        }
        
        return "stay";
    }
    
    public String changePassword() {
        String result = "";
        for(Employee e: list.getEmployees()) {
            if(e.getEmpNumber() == getEmpId()) {
                if(list.getCred(e).getPassword().equals(getOldPassword()))
                    if(getConfirmPassword().equals(getNewPassword())) {
                        list.getCred(e).setPassword(getNewPassword());
                        System.out.println(list.getCred(e).getPassword());
                        list.getLogInfo().put(e.getUserName(), getNewPassword());
                        System.out.println(list.getLogInfo().get(e.getUserName()));
                        setPassword(getNewPassword());
                        System.out.println(getPassword());
                        result = "success";
                        break;
                    }
            } else 
                result = "fail";
        }
        return result;
    }
    
    public String logOut() {
        return "log out";
    }
    
    public String goToPassword() {
        return "changePassword";
    }
    
    public String goToTimeSheet() {
        return "toTimeSheet";
    }
    
    public String goBack() {
        return "goBack";
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }
       
    
}
