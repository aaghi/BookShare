/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.User_GroupsFacade;
import boundary.UsersFacade;
import entities.User_Groups;
import entities.Users;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author aaghi
 */
@ManagedBean
@RequestScoped
public class NewUserView {
    @EJB
    private UsersFacade usersFacade;
    
    @EJB
    private User_GroupsFacade user_GroupsFacade;
    
    private Users user;

    public Users getUser() {
        return user;
    }
    private User_Groups userGroup;

    /**
     * Creates a new instance of NewUserView
     */
    public NewUserView() {
        this.user = new Users();
        this.userGroup = new User_Groups();
    }
    
    public String createUser(){
        this.userGroup.setEmail(this.user.getEmail());
        this.userGroup.setUser_role("ADMIN");
        this.usersFacade.create(user);
        this.user_GroupsFacade.create(userGroup);
        return "creationSuccess";
    }
}
