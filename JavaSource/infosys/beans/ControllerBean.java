package infosys.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;

@Named("controller")
@SessionScoped
public class ControllerBean implements Serializable{
    @Inject EmployeeLister list;
    @Inject TimeSheetCollector timesheetCollection;
	@Inject Credentials currentCredential;
	@Inject Timesheet currentTimesheet;
    
    String oldPassword;
    String newPassword;
    String confirmPassword;

    
    
    public String verifyLogin() {
        
        for(Employee e: list.getEmployees()) {
            if(e.getEmpNumber() == 0000) 
                if((getUserName().equals(e.getUserName())) &&
                        (getPassword().equals(list.getLoginCombos().get(e.getUserName())))) {
                    list.setCurrentEmployee(e);
                	return "admin";   
                }
        }
        
        for(Employee e: list.getEmployees()) {
            if((getUserName().equals(e.getUserName())) &&
                    (getPassword().equals(list.getLoginCombos().get(e.getUserName())))) {
                list.setCurrentEmployee(e);
            	return "next"; 
            }
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
        return currentCredential.getUserName();
    }
    public void setUserName(String userName) {
        currentCredential.setUserName(userName);;
    }
    public String getPassword() {
        return currentCredential.getPassword();
    }
    public Integer getEmpId() {
        return list.getCurrentEmployee().getEmpNumber();
    }

    public void setEmpId(Integer empId) {
        list.getCurrentEmployee().setEmpNumber(empId);
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
        currentCredential.setPassword(password);
    }

    public String timeSheet() {
    	return "timeSheet";
    }   

    public TimeSheetCollector getTimesheetCollection() {
		return timesheetCollection;
	}
    
    public Timesheet getCurrentTimesheet() {
    	currentTimesheet = timesheetCollection.getCurrentTimesheet(list.getCurrentEmployee());
    	return currentTimesheet;
    }


}
