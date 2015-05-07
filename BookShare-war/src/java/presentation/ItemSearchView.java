/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.ItemRequestFacade;
import boundary.LocationFacade;
import boundary.UserItemFacade;
import boundary.UsersFacade;
import entities.Users;
import entities.items.Item;
import entities.items.ItemRequest;
import entities.items.Location;
import entities.items.UserItem;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author albert
 */
@ManagedBean
@RequestScoped
public class ItemSearchView {
    @EJB
    private UserItemFacade userItemFacade;
    @EJB
    private LocationFacade locationFacade;
    @EJB
    private ItemRequestFacade itemRequestFacade;
    @EJB
    private UsersFacade usersFacade;
    
    private UserItem userItem;
    private Item masterItem;
    private Users user;
    
    private ItemRequest request;
    
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    
    private List<UserItem> searchResults;

    /**
     * Creates a new instance of ItemSearchView
     */
    public ItemSearchView() {
        this.userItem = new UserItem();
        this.user = new Users();
        this.masterItem = new Item();
        this.request = new ItemRequest();
    }
    
    /* accessors for dummy objs */
    public UserItem getItem() {
       return userItem;
    }   
    public Users getUser() {
       return user;
    }
    public Item getMasterItem() {
        return masterItem;
    }
    
    public List<UserItem> getSearchResults() {
        return this.searchResults;
    }
    
    public List<UserItem> getAllUserItems() {
        return userItemFacade.findAll();
    }
    
    public List<UserItem> searchUserItems() { 
       searchResults = userItemFacade.findItem(masterItem.getName(), userItem.getLocation().getName(), user.getEmail(),externalContext.getRemoteUser());     
       // searchResults = userItemFacade.findItem("","", "");     
       return searchResults;
    }
    
    public List getAllLocations() {
        List selectItems = new ArrayList();
        SelectItem c;
        List<Location> locs = locationFacade.findAll();
        for(Location loc : locs){
           selectItems.add(new SelectItem(loc, loc.getName())); 
        }
        return selectItems;
    }
 
}
