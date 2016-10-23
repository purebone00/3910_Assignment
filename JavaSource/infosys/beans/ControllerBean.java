package infosys.beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;

@Named("controller")
@SessionScoped
public class ControllerBean implements Serializable {
    @Inject EmployeeLister list;
    @Inject TimeSheetCollector timesheetCollection;
    @Inject Credentials currentCredential;
    
    String oldPassword;
    String newPassword;
    String confirmPassword;
    
    String addUser;
    String addUserPassword;
    String addName;
    
    String deleteUser;
    String deleteUserPassword;
    String deleteUserPasswordConfirm;
    
    String searchUser;
    String searchUserMsg;
    String searchConfirmUser;
    
    
    public ControllerBean() {
        list = new EmployeeLister();
        //currentCredential = new Credentials();
    }
    
    /**
     * Verifies user logging In. Differentiates between Admin and Users.
     * @return String for navigation. 
     */
    public String verifyLogin() {  
        //Login for admin who has ID of 0000
        for (Employee e: list.getEmployees()) {
            if (e.getEmpNumber() == 0000) {
                if ((getUserName().equals(e.getUserName())) 
                        && (getPassword().equals(list.getLoginCombos().get(e.getUserName())))) {
                    list.setCurrentEmployee(e);
                    return "admin";   
                }
            }
        }
        //Login for regular employees
        for (Employee e: list.getEmployees()) {
            if ((getUserName().equals(e.getUserName())) 
                    && (getPassword().equals(list.getLoginCombos().get(e.getUserName())))) {
                list.setCurrentEmployee(e);
                return "next"; 
            }
        }
        return "stay";
    }
    
    /**
     * Allows Users to change password.
     * @return String for navigation.
     */
    public String changePassword() {
        String result = "";
        for (Employee e: list.getEmployees()) {
            if (e.getEmpNumber() == getEmpId()) {
                if (list.getCreds(e).getPassword().equals(getOldPassword())) {
                    if (getConfirmPassword().equals(getNewPassword())) {
                        list.getCreds(e).setPassword(getNewPassword());
                        list.getLogInfo().put(e.getUserName(), getNewPassword());
                        setPassword(getNewPassword());
                        result = "success";
                        break;
                    }
                }
            } else {
                result = "fail";
            }
        }
        return result;
    }
    
    /**
     * Logs the user out, ending the current session.
     * @return String for navigation.
     */
    public String logOut() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
 
        final HttpServletRequest request = (HttpServletRequest)ec.getRequest();
        request.getSession( false ).invalidate();
      
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
    
    /**
     * Adds a user to our user list.
     */
    public void addingUser() {
        int employeeNumber = list.getEmployeeNumber();
        Credentials cred = new Credentials();
        cred.setUserName(getAddUser());
        cred.setPassword(getAddUserPassword());
        Employee employee = new Employee(getAddName(), employeeNumber, getAddUser());
        list.getEmployees().add(employee);
        list.getCred().add(cred);
        list.getLogInfo().put(getAddUser(), getAddUserPassword());
    }
    
    /**
     * A message notifying a user has been added.
     * @return a string notification.
     */
    public String getAddingUser() {
        if (("".equals(getAddUser()) || getAddUser() == null)) {
            return "";
        } else {
            return "User, " + getAddUser() + ", has been added into the system.";
        }
    }
    
    /**
     * Prints a message notifying the user has been deleted and deletes the user.
     * @param user is the user being deleted.
     * @return String notifying that a existing user has been deleted.
     */
    public String getPrintDeletingUser(String user) {
        boolean render = false;
        for (Employee e: list.getEmployees()) {
            if (e.getUserName().equals(getDeleteUser())) { 
                list.getEmployees().remove(e);
                render = true;
            }
        }
        for (Credentials c: list.getCred()) {
            if (c.getUserName() == getDeleteUser()) {
                c = null;
            }
        }
        list.getLogInfo().remove(getDeleteUser());
        
        if (("".equals(getDeleteUser()) || getDeleteUser() == null)) {
            return "";
        } else {
            return (!list.getLogInfo().containsKey(getDeleteUser()) && render) ? "User, " 
                + getDeleteUser() + ", has been deleted from the system." 
                : "User, " + getDeleteUser() + ", does not exist in the system.";
        }
    }
    
    /**
     * Resets a users password.
     * @return A message notifying that the password has been reseted.
     */
    public String getChangingUser() {
        for (Employee e: list.getEmployees()) {
            if (e.getUserName().equals(getSearchUser())
                    && (getSearchUser().equals(getSearchConfirmUser()))) {
                list.getLogInfo().put(getSearchUser(),"default");
            }
        }
        if (("".equals(getSearchUser()) || getSearchUser() == null)) {
            return "";
        } else {
            return (list.getLogInfo().containsKey(getSearchUser()))
                    ? "User, " + getSearchUser() + "'s password has been reseted to 'default'."
                            : "User does not exist.";
        }
    }
    
    /**
     * Returns information about the currently searched user.
     * @return A message about the user information.
     */
    public String getSearchingUser() {
        String password = "";
        String name = "";
        int employeeNumber = 0;
        
        for (Employee e: list.getEmployees()) {
            if (e.getUserName().equals(getSearchUser())) {
                employeeNumber = e.getEmpNumber();
                name = e.getName();
                break;
            }
        }
        
        password = list.getLogInfo().get(getSearchUser());
        
        
        if (("".equals(getSearchUser()) || getSearchUser() == null)) {
            return "";
        } else {
            return (list.getLogInfo().containsKey(getSearchUser()))
                    ? "User: " + getSearchUser() + "\n" + "Name: " + name + "\n" 
                    + "EmployeeID: " + employeeNumber + "\n"
                    + "Password: " + password : "User does not exist.";
        }
    }
    
    public String getSearchConfirmUser() {
        return searchConfirmUser;
    }

    public void setSearchConfirmUser(String searchConfirmUser) {
        this.searchConfirmUser = searchConfirmUser;
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }
    
    public String deletedUserMsg() {
        return "You have deleted user, " + getDeleteUser() + ".";
    }
    
    public String addedUserMsg() {
        return "You have added user, " + getAddUser() + ".";
    }
    
    public String getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(String deleteUser) {
        this.deleteUser = deleteUser;
    }

    public String getDeleteUserPassword() {
        return deleteUserPassword;
    }

    public void setDeleteUserPassword(String deleteUserPassword) {
        this.deleteUserPassword = deleteUserPassword;
    }

    public String getDeleteUserPasswordConfirm() {
        return deleteUserPasswordConfirm;
    }

    public void setDeleteUserPasswordConfirm(String deleteUserPasswordConfirm) {
        this.deleteUserPasswordConfirm = deleteUserPasswordConfirm;
    }
    
    
    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }
    
    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getAddUserPassword() {
        return addUserPassword;
    }

    public void setAddUserPassword(String addUserPassword) {
        this.addUserPassword = addUserPassword;
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


}
