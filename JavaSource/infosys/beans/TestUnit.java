package infosys.beans;


public class TestUnit {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ControllerBean cb = new ControllerBean();
        cb.setUserName("employeeJohn");
        cb.setPassword("default");
        ControllerBean cb2 = new ControllerBean();
        cb2.setUserName("emplohhhhyeeLow");
        cb2.setPassword("default");
        
        System.out.println(cb.verifyLogin()); 
    }

}
