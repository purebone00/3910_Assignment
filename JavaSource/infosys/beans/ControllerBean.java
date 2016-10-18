package infosys.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.Employee;


@Named("controller")
@ConversationScoped
public class ControllerBean implements Serializable{
    @Inject Conversation conversation;
    EmployeeLister list = new EmployeeLister();
    String userName;
    String password;

    @PostConstruct
    public void init() {
        conversation.begin();
    }
    
    public String verifyLogin() {
        for(Employee e: list.getEmployees()) {
            if((getUserName().equals(e.getUserName())) &&
                    (getPassword().equals(list.getLoginCombos().get(e.getUserName())))) {
                return "next";
            }
        }
        return "stay";
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
