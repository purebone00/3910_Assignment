package infosys.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.Employee;

@Named("controller")
@ConversationScoped
public class ControllerBean implements Serializable{
    @Inject Conversation conversation;
    @Inject EmployeeLister list;
    @Inject TimeSheetCollector timesheetCollection;
	String userName;
    String password;
    
    Integer empId;
    String oldPassword;
    String newPassword;
    String confirmPassword;

    @PostConstruct
    public void init() {
        conversation.begin();
    }
    
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
    
    public String logOut() {
        conversation.end();
        return "log out";
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
       
    public String goToPassword() {
        return "changePassword";
    }
    
    public String timeSheet() {
    	return "timeSheet";
    }
    
    public TimeSheetCollector getTimesheetCollection() {
		return timesheetCollection;
	}
    
    public String changePassword() {
        String result = "";
        for(Employee e: list.getEmployees()) {
            if(e.getEmpNumber() == getEmpId()) {
                if(list.getCred(e).getPassword().equals(getPassword()))
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
    
}
