package infosys.beans;


public class TestUnit {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ControllerBean cb = new ControllerBean();
        cb.setUserName("employeeJohn");
        cb.setEmpId(12345);
        cb.setPassword("default");
        cb.setNewPassword("123");
        cb.setConfirmPassword("123");
        
        
        System.out.println("Test for verifying users.\nExpected return 'next' :     " + cb.verifyLogin()); 
        
        
        System.out.println("Test for changing password.\nExpected return 'success'  :   " + cb.changePassword());
    }

}
