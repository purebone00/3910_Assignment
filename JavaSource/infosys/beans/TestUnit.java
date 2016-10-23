package infosys.beans;


public class TestUnit {

    public static void main(String[] args) {

        ControllerBean cb = new ControllerBean();
        cb.setUserName("employeeJohn");
        cb.setPassword("default");
        cb.setOldPassword("default");
        cb.setNewPassword("123");
        cb.setConfirmPassword("123");
        cb.setDeleteUser("employeeJohn");
        cb.setDeleteUserPassword("default");
        
        //cb.deletingUser("employeeJohn");
        //System.out.println(cb.getPrintDeletingUser());
        
        //System.out.println("Test for verifying users.\nExpected return 'next' :     " + cb.verifyLogin()); 
        
        //System.out.println("Test for changing password.\nExpected return 'success'  :   " + cb.changePassword());
    }

}
