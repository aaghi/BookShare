/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.ItemRequestFacade;
import boundary.UserItemFacade;
import boundary.UsersFacade;
import entities.Users;
import entities.items.ItemRequest;
import entities.items.UserItem;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author albert
 */
@ManagedBean
@RequestScoped
public class UserItemView {
    @EJB
    private UserItemFacade userItemFacade;
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private ItemRequestFacade itemRequestFacade;
    
    private UserItem userItem;
    private Users borrower;
    private ItemRequest request;
    
    private boolean requestable;

    public boolean isRequestable() {
        return requestable;
    }

    public Users getBorrower() {
        return borrower;
    }

    public void setBorrower(Users borrower) {
        this.borrower = borrower;
    }

    public UserItem getUserItem() {
        return userItem;
    }

    public ItemRequest getRequest() {
        return request;
    }

    public void setRequest(ItemRequest request) {
        this.request = request;
    }
    
    public void setUserItem(UserItem userItem) {
        this.userItem = userItem;
    }
    
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    
    /**
     * Creates a new instance of UserItemView
     */
    public UserItemView() {
        this.userItem = new UserItem();
        this.request = new ItemRequest();
    }
    
    @PostConstruct
    public void init() {
        Long userItemId = Long.parseLong(((HttpServletRequest)externalContext.getRequest()).getParameter("userItemId"));
        Long borrowerId = Long.parseLong(((HttpServletRequest)externalContext.getRequest()).getParameter("borrowerId"));
        
        userItem = userItemFacade.find(userItemId);
        borrower = usersFacade.find(borrowerId);
        
        checkIfRequestable();
    }
    
    public void requestToBorrowItem() {
        String comment = ((HttpServletRequest)externalContext.getRequest()).getParameter("commentText");
        request.setItem(userItem);
        request.setBorrower(borrower);
        request.setComments(comment);
        this.itemRequestFacade.create(request); 
        this.requestable = false;
    }
    
    public void checkIfRequestable() {
        this.requestable = this.itemRequestFacade.checkRequestable(this.userItem.getId(), this.borrower.getId());
    }
}
