/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.UsersFacade;
import entities.Users;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author aaghi
 */
@ManagedBean
@RequestScoped
public class UserSearchView {
    
    @EJB
    private UsersFacade usersFacade;
    
    String searchName = "";
    
    List<Users> searchResults;

    public String getSearchName() {
        return searchName;
    }

    public List<Users> getSearchResults() {
        performSearch();
        return searchResults;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * Creates a new instance of UserSearchView
     * 
     */
    public UserSearchView() {
    }
    @PostConstruct
    public void init() {
    }
    public void performSearch(){
        searchResults = usersFacade.findUser(searchName);
    }
}
