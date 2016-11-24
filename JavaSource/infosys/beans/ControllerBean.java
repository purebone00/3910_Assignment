package infosys.beans;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;

import java.io.Serializable;
import java.util.Iterator;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


/**
 * This is the master controller java bean. This class holds all essential methods to process 
 * users and admins in their current session. 
 * @author Joe Fong
 */
@SuppressWarnings("serial")
@Named("controller")
@SessionScoped
public class ControllerBean implements Serializable {
    @Inject TimeSheetCollector timesheetCollection;
    @Inject Credentials currentCredential;
    @Inject EmployeeLister list;
    
    /** An editable timesheet. */
    EditableTimesheet currentTimesheet;
    
    /** The old password. */
    String oldPassword;
    /** The new password to change to. */
    String newPassword;
    /** Confirming the new password. */
    String confirmPassword;
    
    /** Newly added user name. */
    String addUser;
    /** Password for added user. */
    String addUserPassword;
    /** Name of newly added user. */
    String addName;
    
    /** User name of user to be deleted. */
    String deleteUser;
    /** Password of user to be deleted. */
    String deleteUserPassword;
    /** Confirmed password of user to be deleted. */
    String deleteUserPasswordConfirm;
   
    /** The user currently searched. */
    String searchUser;
    /** The message returned to the user. */
    String searchUserMsg;
    /** The user name confirmed. */
    String searchConfirmUser;
    /** Determines if user is be deleted. */
    boolean render;
   
    /**
     * The constructor for the bean. 
     * Creates a list of employees and editable time sheets.
     */
    public ControllerBean() {
        list = new EmployeeLister();
        currentTimesheet = new EditableTimesheet();
        currentCredential = new Credentials();
    }
    
    /**
     * Verifies user logging In. Differentiates between Admin and Users.
     * @return String for navigation. 
     */
    public String verifyLogin() {  
        list.setEmployees();
        //Login for admin who has Employee Number of 3 
        for (Employee e: list.getEmployees()) {
            if (e.getEmpNumber() == 3) {
                if ((getUserName().equals(e.getUserName())) 
                        && (getPassword().equals(e.getPassword()))) {
                    list.setCurrentEmployee(e);
                    return "admin";   
                }
            }
        }
        //Login for regular employees
        for (Employee e: list.getEmployees()) {
            if ((getUserName().equals(e.getUserName())) 
                    && (getPassword().equals(e.getPassword()))) {
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
        list.setEmployees();
        for (Employee e: list.getEmployees()) {
            if (e.getUserName().equals(getUserName())) {
                if (e.getPassword().equals(getOldPassword())) {
                    if (getConfirmPassword().equals(getNewPassword())) {
                        list.changePassword(getNewPassword(), e.getUserName());
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
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "log out";
    }
  
    /**
     * Adds a user to our user list.
     */
    public void addingUser() {
        list.setEmployees();
        this.render = false;
        
        for (Employee e :list.getEmployees()) {
            if (e.getUserName().equals(getAddUser())) {
                this.render = true;
            }
        }
        
        if (!render) {
            Employee employee = new Employee(0, getAddUser(), getAddName(), "default");
            list.addEmployee(employee);
        }
        
    }
    
    /**
     * A message notifying a user has been added.
     * @return a string notification.
     */
    public String getAddingUser() {
        if (("".equals(getAddUser()) || getAddUser() == null)) {
            return "";
        } else {
            return (!render) ? "User, " + getAddUser() + ", has been added into the system." 
                    : "User Exists In System.";
        }
    }
    
    /**
     * Deletes a user specified by the submitted form.
     */
    public void deleteAUser() {
        this.render = false;
        
        Iterator<Employee> it = list.getEmployees().iterator();
        while (it.hasNext()) {
            Employee employee = it.next();
            if (employee.getUserName().equals(getDeleteUser())) {
                it.remove();
                list.deleteEmpoyee(employee.getEmpNumber());
                this.render = true;
            }
        }
    }
    
    /**
     * Prints a message notifying the user has been deleted and deletes the user.
     * @param user is the user being deleted.
     * @return String notifying that a existing user has been deleted.
     */
    public String getPrintDeletingUser(String user) {
        if (("".equals(getDeleteUser()) || getDeleteUser() == null)) {
            return "";
        } else {
            return (!list.getEmployee(getDeleteUser()) && render) ? "User, " 
                + getDeleteUser() + ", has been deleted from the system." 
                : "User, " + getDeleteUser() + ", does not exist in the system.";
        }
    }

    
    /**
     * Resets a users password.
     * @return A message notifying that the password has been reseted.
     */
    public String getChangingUser() {
        int employeeNumber = -1;
        for (Employee e: list.getEmployees()) {
            if (e.getUserName().equals(getSearchUser())
                    && (getSearchUser().equals(getSearchConfirmUser()))) {
                try {
                    list.resetUser(getSearchConfirmUser());
                    employeeNumber = e.getEmpNumber();
                } catch (NullPointerException nullP) {
                    this.render = false;
                }
                
            }
        }
        if (("".equals(getSearchUser()) || getSearchUser() == null)) {
            return "";
        } else {
            return (list.findEmployee(employeeNumber) != null
                    && getSearchUser().equals(getSearchConfirmUser()))
                    ? "User, " + getSearchUser() + "'s password has been reseted to 'default'."
                            : "User Does Not Exist.";
        }
    }
    
    /**
     * Returns information about the currently searched user.
     * @return A message about the user information.
     */
    public String getSearchingUser() {
        Employee employee = null;
        try {
            employee = list.findEmployee(list.searchEmployeeNumber(getSearchUser()));
            this.render = true;
        } catch (NullPointerException exception) {
            this.render = false;
        }
        if (("".equals(getSearchUser()) || getSearchUser() == null)) {
            return "";
        } else {
            return (employee != null)
                    ? "User: " + getSearchUser() + "\n" + "Name: " + employee.getName() + "\n" 
                    + "EmployeeID: " + employee.getEmpNumber() + "\n"
                    + "Password: " + employee.getPassword() : "User does not exist.";
        }
    }
    
    /**
     * Navigating method.
     * @return Navigational string.
     */
    public String goBack() {
        if (getEmpId() == 3) {
            return "goBackAdmin";
        }
        return "goBack";
    }    
    
    /**
     * Navigation to go to change password page.
     * @return navigation rule to changePassword outcome.
     */
    public String goToPassword() {
        return "changePassword";
    }
    
    /**
     * Navigation to go to time sheets page.
     * @return navigation rule to timesheets outcome.
     */
    public String goToTimeSheet() {
        timesheetCollection.setTimesheets();
        return "toTimeSheet";
    }
    
    /**
     * Confirmed user string.
     * @return the confirmed user string.
     */
    public String getSearchConfirmUser() {
        return searchConfirmUser;
    }

    /**
     * Set confirmed user string.
     * @param searchConfirmUser set to searchConfirmUser.
     */
    public void setSearchConfirmUser(String searchConfirmUser) {
        this.searchConfirmUser = searchConfirmUser;
    }

    /**
     * The searchedUser.
     * @return searchUser.
     */
    public String getSearchUser() {
        return searchUser;
    }

    /**
     * Sets the setSearchUser.
     * @param searchUser to be set.
     */
    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }
    
    
    /**
     * A user deleted message. 
     * @return the message.
     */
    public String deletedUserMsg() {
        return "You have deleted user, " + getDeleteUser() + ".";
    }
    
    /**
     * Added user messaged.
     * @return the message.
     */
    public String addedUserMsg() {
        return "You have added user, " + getAddUser() + ".";
    }
    
    /**
     * Deleted user.
     * @return the deleteUser.
     */
    public String getDeleteUser() {
        return deleteUser;
    }

    /**
     * Sets deletedUser
     * @param deleteUser to be set.
     */
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
    
    /**
     * User's name to be added.
     * @return User's name of added user.
     */
    public String getAddName() {
        return addName;
    }

    /**
     * sets the user's name of the user to be added.
     * @param addName to be set.
     */
    public void setAddName(String addName) {
        this.addName = addName;
    }
    
    /**
     * Get the added user's id.
     * @return the added user's id.
     */
    public String getAddUser() {
        return addUser;
    }

    /**
     * Sets the user's id to be added.
     * @param addUser to be set.
     */
    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    /**
     * Get password of new user.
     * @return password of new user.
     */
    public String getAddUserPassword() {
        return addUserPassword;
    }

    /**
     * Sets the password of new user.
     * @param addUserPassword to be set.
     */
    public void setAddUserPassword(String addUserPassword) {
        this.addUserPassword = addUserPassword;
    }
    
    /**
     * The current user's user name.
     * @return the user name.
     */
    public String getUserName() {
        return currentCredential.getUserName();
    }
    
    /**
     * Sets the current user's user name.
     * @param userName to be set.
     */
    public void setUserName(String userName) {
        currentCredential.setUserName(userName);;
    }
    
    /**
     * Current user's password.
     * @return password of current user.
     */
    public String getPassword() {
        return currentCredential.getPassword();
    }
    
    /**
     * Current user's employee number.
     * @return user's employee number.
     */
    public Integer getEmpId() {
        return list.getCurrentEmployee().getEmpNumber();
    }

    /**
     * Sets the user's employee number.
     * @param empId to be set.
     */
    public void setEmpId(Integer empId) {
        list.getCurrentEmployee().setEmpNumber(empId);
    }

    /**
     * Old password being changed.
     * @return old password.
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Set old password being changed.
     * @param oldPassword to be set.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * New password to be changed into.
     * @return the new password.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password.
     * @param newPassword to be set.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Get the confirmed password. 
     * @return the confirmed password.
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Set the confirmed password.
     * @param confirmPassword to be set.
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Set the password of the current user.
     * @param password to be set.
     */
    public void setPassword(String password) {
        currentCredential.setPassword(password);
    }

    /**
     * Navigation string to traverse to time sheets page. 
     * @return String for navigation outcome.
     */
    public String timeSheet() {
        return "timeSheet";
    }   

    /**
     * A collection of time sheets.
     * @return timesheetCollection.
     */
    public TimeSheetCollector getTimesheetCollection() {
        return timesheetCollection;
    }
    
    /**
     * Get the current employee's time sheet.
     * @return employee's time sheet.
     */
    public Timesheet getCurrentTimesheet() {
        currentTimesheet = timesheetCollection.getCurrentTimesheet(list.getCurrentEmployee());
        return currentTimesheet;
    }
    
}
