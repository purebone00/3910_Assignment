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
 * This is the master controller java bean. This class holds all essential
 *  methods to process users and admins in their current session.
 * @author Joe Fong
 */
@Named("controller")
@SessionScoped
public class ControllerBean implements Serializable {
    /** Injecting a Timesheet collection. */
    @Inject private TimeSheetCollector timesheetCollection;
    /** Injecting the user credentials. */
    @Inject private Credentials currentCredential;
    /** Injecting an employee list. */
    @Inject private EmployeeLister list;

    /** An editable timesheet. */
    private EditableTimesheet currentTimesheet;

    /** The old password. */
    private String oldPassword;
    /** The new password to change to. */
    private String newPassword;
    /** Confirming the new password. */
    private String confirmPassword;
    /** Newly added user name. */
    private String addUser;
    /** Password for added user. */
    private String addUserPassword;
    /** Name of newly added user. */
    private String addName;
    /** User name of user to be deleted. */
    private String deleteUser;
    /** Password of user to be deleted. */
    private String deleteUserPassword;
    /** Confirmed password of user to be deleted. */
    private String deleteUserPasswordConfirm;
    /** The user currently searched. */
    private String searchUser;
    /** The message returned to the user. */
    private String searchUserMsg;
    /** The user name confirmed. */
    private String searchConfirmUser;
    /** Determines if user is be deleted. */
    private boolean render;
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
    public final String verifyLogin() {
        //Login for admin who has ID of 0000
        for (Employee e: list.getEmployees()) {
            if (e.getEmpNumber() == 0000) {
                if ((getUserName().equals(e.getUserName()))
                        && (getPassword().equals(list.getLoginCombos()
                                .get(e.getUserName())))) {
                    list.setCurrentEmployee(e);
                    return "admin";
                }
            }
        }
        //Login for regular employees
        for (Employee e: list.getEmployees()) {
            if ((getUserName().equals(e.getUserName()))
                    && (getPassword().equals(list.getLoginCombos()
                            .get(e.getUserName())))) {
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
    public final String changePassword() {
        String result = "";
        for (Employee e: list.getEmployees()) {
            if (e.getUserName().equals(getUserName())) {
                if (list.getCreds(e).getPassword().equals(getOldPassword())) {
                    if (getConfirmPassword().equals(getNewPassword())) {
                        list.getCreds(e).setPassword(getNewPassword());
                        list.getLoginCombos().put(e.getUserName(),
                                getNewPassword());
                        System.out.println(list.getLoginCombos()
                                .get(e.getUserName()));
                        setPassword(getNewPassword());
                        System.out.println(getPassword());
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
    public final String logOut() {
        FacesContext.getCurrentInstance().
            getExternalContext().invalidateSession();
        return "log out";
    }

    /**
     * Adds a user to our user list.
     */
    public final void addingUser() {
        int employeeNumber = list.getEmployeeNumber();
        Credentials cred = new Credentials();
        cred.setUserName(getAddUser());
        cred.setPassword(getAddUserPassword());
        Employee employee = new Employee(getAddName(),
                employeeNumber, getAddUser());
        list.getEmployees().add(employee);
        list.getCred().add(cred);
        list.getLoginCombos().put(getAddUser(), getAddUserPassword());
    }

    /**
     * A message notifying a user has been added.
     * @return a string notification.
     */
    public final String getAddingUser() {
        if (("".equals(getAddUser()) || getAddUser() == null)) {
            return "";
        } else {
            return "User, " + getAddUser()
            + ", has been added into the system.";
        }
    }

    /**
     * Deletes a user specified by the submitted form.
     */
    public final void deleteAUser() {
        this.render = false;
        Iterator<Employee> it = list.getEmployees().iterator();
        Iterator<Credentials> cit = list.getCred().iterator();
        while (it.hasNext()) {
            Employee employee = it.next();
            if (employee.getUserName().equals(getDeleteUser())) {
                it.remove();
                this.render = true;
            }
        }
        while (cit.hasNext()) {
            Credentials credential = cit.next();
            if (credential.getUserName().equals(getDeleteUser())) {
                cit.remove();
                this.render = true;
            }
        }
        list.getLoginCombos().remove(getDeleteUser());
    }

    /**
     * Prints a message notifying the user has been deleted
     * and deletes the user.
     * @param user is the user being deleted.
     * @return String notifying that a existing user has been deleted.
     */
    public final String getPrintDeletingUser(final String user) {
        if (("".equals(getDeleteUser()) || getDeleteUser() == null)) {
            return "";
        } else {
            return (!list.getLoginCombos().containsKey(getDeleteUser())
                    && render)
                    ? "User, "
                + getDeleteUser() + ", has been deleted from the system."
                : "User, " + getDeleteUser() +
                ", does not exist in the system.";
        }
    }

    /**
     * Resets a users password.
     * @return A message notifying that the password has been reseted.
     */
    public final String getChangingUser() {
        for (Employee e: list.getEmployees()) {
            if (e.getUserName().equals(getSearchUser())
                    && (getSearchUser().equals(getSearchConfirmUser()))) {
                list.getLoginCombos().put(getSearchUser(), "default");
            }
        }
        if (("".equals(getSearchUser()) || getSearchUser() == null)) {
            return "";
        } else {
            return (list.getLoginCombos().containsKey(getSearchUser()))
                    ? "User, "
                    + getSearchUser()
                    + "'s password has been reseted to 'default'."
                            : "User does not exist.";
        }
    }

    /**
     * Returns information about the currently searched user.
     * @return A message about the user information.
     */
    public final String getSearchingUser() {
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

        password = list.getLoginCombos().get(getSearchUser());


        if (("".equals(getSearchUser()) || getSearchUser() == null)) {
            return "";
        } else {
            return (list.getLoginCombos().containsKey(getSearchUser()))
                    ? "User: "
                    + getSearchUser() + "\n" + "Name: " + name + "\n"
                    + "EmployeeID: " + employeeNumber + "\n"
                    + "Password: " + password : "User does not exist.";
        }
    }

    /**
     * Navigating String.
     * @return change password.
     */
    public final String goToPassword() {
        return "changePassword";
    }
    /**
     * Navigating String.
     * @return go to time sheet.
     */
    public final String goToTimeSheet() {
        return "toTimeSheet";
    }

    /**
     * Navigating String.
     * @return go back to previous page.
     */
    public final String goBack() {
        return "goBack";
    }    
    
    /**
     * Navigating String.
     * @return
     */
    public String getSearchConfirmUser() {
        return searchConfirmUser;
    }

    /**
     * 
     * @param searchConfirmUser
     */
    public void setSearchConfirmUser(String searchConfirmUser) {
        this.searchConfirmUser = searchConfirmUser;
    }

    /**
     * Navigating String.
     * @return
     */
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
        currentCredential.setUserName(userName);
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
        currentTimesheet =
                timesheetCollection.
                getCurrentTimesheet(list.getCurrentEmployee());
        return currentTimesheet;
    }

    public Credentials getCurrentCredential() {
        return currentCredential;
    }

    public void setCurrentCredential(Credentials currentCredential) {
        this.currentCredential = currentCredential;
    }

    public EmployeeLister getList() {
        return list;
    }

    public void setList(EmployeeLister list) {
        this.list = list;
    }

    public String getSearchUserMsg() {
        return searchUserMsg;
    }

    public void setSearchUserMsg(String searchUserMsg) {
        this.searchUserMsg = searchUserMsg;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public void setTimesheetCollection(TimeSheetCollector timesheetCollection) {
        this.timesheetCollection = timesheetCollection;
    }

    public void setCurrentTimesheet(EditableTimesheet currentTimesheet) {
        this.currentTimesheet = currentTimesheet;
    }
}
