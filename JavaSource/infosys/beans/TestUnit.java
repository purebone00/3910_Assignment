package infosys.beans;

import ca.bcit.infosys.employee.Employee;

public class TestUnit {

    public static void main(String[] args) {
        EmployeeLister list = new EmployeeLister();
        ControllerBean cb = new ControllerBean();
        cb.setUserName("employeeLow");
        cb.setPassword("default");
        cb.setOldPassword("default");
        cb.setNewPassword("123");
        cb.setConfirmPassword("123");
        cb.setDeleteUser("employeeLow");
        cb.setDeleteUserPassword("default");
        
        cb.deleteAUser();
        for(Employee e: list.getEmployees()) {
            System.out.println(e.getUserName());
        }
        
        
        
        //cb.deletingUser("employeeJohn");
        //System.out.println(cb.getPrintDeletingUser());
        
        //System.out.println("Test for verifying users.\nExpected return 'next' :     " + cb.verifyLogin()); 
        
        //System.out.println("Test for changing password.\nExpected return 'success'  :   " + cb.changePassword());
    }

}
