/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.*;
import entities.Users;
import entities.items.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author macadada
 */
@ManagedBean
@RequestScoped
public class ItemView {
    @EJB
    private VisibilityFacade visibilityFacade;
    @EJB
    private LocationFacade locationFacade;
    @EJB
    private UserItemFacade userItemFacade1;
    @EJB
    private ItemStatusFacade itemStatusFacade;
    @EJB
    private UserItemFacade userItemFacade;
    @EJB
    private UsersFacade usersFacade;
    
    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ItemFacade itemFacade; 
    
    
    
    private String searchQuery;   
    private String username;    
    private String result;
    
    private long delItem;
    
    private Item item;
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    
    
    private UserItem myItem;
    private Users me;

        /** Creates a new instance of ItemView */
    public ItemView() {
        this.item = new Item();
        result="moo";
        this.myItem = new UserItem();

    }
    
    @PostConstruct
    public void init() {
        username=externalContext.getRemoteUser();
        if(usersFacade==null){
            username="cow";
        }else if (username!=null){
            me=usersFacade.findByLogin(username);    
        }
        
  
        
    }

    public long getDelItem() {
        return delItem;
    }

    public void setDelItem(long delItem) {
        this.delItem = delItem;
    }
    
 // <editor-fold defaultstate="collapsed" desc="Getters and setters">   
    
        public Users getMe() {
        return me;
    }

    public void setMe(Users me) {
        this.me = me;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserItem getMyItem() {
        return myItem;
    }

    public void setMyItem(UserItem myItem) {
        this.myItem = myItem;
    }
    
    public Item getItem() {
       return item;
    }

    // Returns the total number of messages
    public int getNumberOfItems(){
       return itemFacade.findAll().size();
    }
    
     public List<Item> getItems(){
         if(searchQuery!=null ){
             return itemFacade.findByName(searchQuery);
         }
       return itemFacade.findAll();
    }
// </editor-fold>
     
    // Saves the message and then returns the string "theend"
    public String addItem(){
       
       this.itemFacade.create(item);
       myItem.setMasterItem(item);       
       myItem.setOwner(me);
       this.userItemFacade.create(myItem);
       List<UserItem> ls=me.getOwnedItems();
       ls.add(myItem);
       me.setOwnedItems(ls);
         

       return "result";
    }
    public String doNothing(){
       return "searchMasterItem";
    }
    
    public String goToAddItem(){
       return "searchMasterItem";
    }
    
    public void ajaxNothing(AjaxBehaviorEvent event) {      
        item=item;
    }
    
    public void rezChange(AjaxBehaviorEvent event) {      
        result=((HttpServletRequest)externalContext.getRequest()).getParameter("rezId");
    }
    
    public void updateItemSelect(AjaxBehaviorEvent event) { 
        result ="hamburger";
        for(UserItem it: me.getOwnedItems()){
            userItemFacade.edit(it);
        }
        
    }
    
    public void delItem(AjaxBehaviorEvent event) {      
        long delId=Long.parseLong(((HttpServletRequest)externalContext.getRequest()).getParameter("rezId"));
        UserItem tmp =userItemFacade.find(delId);
        me.getOwnedItems().remove(tmp);
        userItemFacade.remove(tmp);
    }
    
    public String addMasterItem(){
       Long masterId= Long.parseLong(((HttpServletRequest)externalContext.getRequest()).getParameter("masterId"));
       item = itemFacade.find(masterId);
       myItem.setMasterItem(item);       
       myItem.setOwner(me);
       this.userItemFacade.create(myItem);
       List<UserItem> ls=me.getOwnedItems();
       ls.add(myItem);
       me.setOwnedItems(ls);

       return "result";
    }
    
    
    public List getSelectCategories() {
        List selectItems = new ArrayList();
        SelectItem c;
        List<Category> cats =categoryFacade.findAll();
        for(Category cat : cats){
           selectItems.add(new SelectItem(cat, cat.getName())); 
        }
        
        // The values are the keys passed to the selectItem property.
        // The labels are those what you see on the menu.

        return selectItems;
    }
    
    public List getSelectStatus() {
        List selectItems = new ArrayList();
        SelectItem c;
        List<ItemStatus> stats =itemStatusFacade.findAll();
        for(ItemStatus stat : stats){
           selectItems.add(new SelectItem(stat, stat.getName())); 
        }
        
        // The values are the keys passed to the selectItem property.
        // The labels are those what you see on the menu.

        return selectItems;
    }
    
    public List getSelectLocations() {
        List selectItems = new ArrayList();
        SelectItem c;
        List<Location> locs = locationFacade.findAll();
        for(Location loc : locs){
           selectItems.add(new SelectItem(loc, loc.getName())); 
        }
        return selectItems;
    }
    
    public List getSelectVisibility() {
        List selectItems = new ArrayList();
        SelectItem c;
        List<Visibility> viss = visibilityFacade.findAll();
        for(Visibility vis : viss){
           selectItems.add(new SelectItem(vis, vis.getName())); 
        }
        return selectItems;
    }
    
    public List<UserItem> getBorrowedItemsForUser() {
        return this.userItemFacade.findBorrowedItems(me.getId());
    }
}
