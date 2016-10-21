package infosys.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("controller")
@ConversationScoped
public class ControllerBean implements Serializable{
    @Inject Conversation conversation;
    EmployeeLister list;
    String userName;
    String password;

    @PostConstruct
    public void init() {
        conversation.begin();
    }
    
    public ControllerBean() {
        list = new EmployeeLister();
    }
    
    /*
    public String verifyLogin() {
        for(Employee e: list.getEmployees()) {
            if((getUserName().equals(e.getUserName())) &&
                    (getPassword().equals(list.getLoginCombos().get(e.getUserName())))) {
                return "next";
            }
        }
        return "stay";
    }
    */
    
    public String verifyLogin() {
        String verified = "";
        try {
            if(list.getLogInfo().get(getUserName()).equals(getPassword())) {
                verified = "next";
            }
        } catch(NullPointerException n) {
            return "stay";
        }
        return verified;
    }
    
    public String logOut() {
        conversation.end();
        return "log out";
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
    public void setPassword(String password) {
        this.password = password;
    }
       
    
    
}
