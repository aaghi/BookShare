/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.*;
import entities.Friendship;
import entities.Users;
import entities.items.ItemRequest;
import entities.items.UserItem;
import java.util.ArrayList;
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
 * @author aaghi
 */
@ManagedBean
@RequestScoped
public class UserView {
    
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private FriendshipFacade friendshipFacade;
    @EJB
    private ItemRequestFacade itemRequestFacade;
    @EJB
    private UserItemFacade userItemFacade;
    @EJB
    private ItemStatusFacade itemStatusFacade;
    
    private Users user;
    private String oldPassword, newPassword, confirmPassword,message;
    private String newFriendEmail;

    public void setNewFriendEmail(String newFriendEmail) {
        this.newFriendEmail = newFriendEmail;
    }

    public String getNewFriendEmail() {
        return newFriendEmail;
    }
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

    public void  logout(){
        HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
            try {
                request.logout();
                externalContext.dispatch("/faces/index.xhtml");
            } catch (Exception e) {
                
            }
    }
    public String getMessage() {
        return message;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * Creates a new instance of EditItemView
     */
    public UserView() {
        message = "";
    }
    public Users getUser(){
        return user;
    }
    private List<Friendship> getFriends(String status){
        List<Friendship> ret = new ArrayList<Friendship>();
        for(Friendship f:user.getFrienships()){
            if(f.getStatus().equals(status))
                ret.add(f);
        }
        return ret;
    }
    private List<Friendship> getFriends2(String status){
        List<Friendship> ret = new ArrayList<Friendship>();
        for(Friendship f:user.getRequestedFriendships()){
            if(f.getStatus().equals(status))
                ret.add(f);
        }
        return ret;
    }
    public List<Friendship> getFriendsList(){
        return getFriends("friend");
    }
    public List<Friendship> getFriendsList2(){
        return getFriends2("friend");
    }
    public List<Friendship> getAntiFriendsList(){
        return getFriends("antiFriend");
    }
    public List<Friendship> getAntiFriendsList2(){
        return getFriends2("antiFriend");
    }
    public List<Friendship> getFriendRequests(){
        return getFriends2("request");
    }
    public List<Friendship> getPendingRequests(){
        List<Friendship> ret = new ArrayList<Friendship>();
        ret.addAll(getFriends("request"));
        ret.addAll(getFriends("decline"));
        return ret;
    }
    
    public List<ItemRequest> getBorrowItemRequests() {
        List<ItemRequest> itemRequests = (List<ItemRequest>)this.itemRequestFacade.getAllItemRequestsForUser(user.getId());
        return itemRequests;
    }
    
    public List<ItemRequest> getPendingItemRequests() {
        List<ItemRequest> itemRequests = (List<ItemRequest>)this.itemRequestFacade.getAllPendingItemRequestsForUser(user.getId());
        return itemRequests;
    }
    
    
    @PostConstruct
    public void init() {
        String username=externalContext.getRemoteUser();
        if(username!=null){
        user = usersFacade.findByLogin(username);  
        }
    }
    public void saveUser(){
        usersFacade.edit(user);
    }
    public String savePassword(){
        if(!confirmPassword.equals(newPassword)){
            message = "New Password and Confirm Password fields do not match";
            return "changePassword";
        }
        message = "Password changed successfully";
        user.setPassword(newPassword);
        return "editUser";
    }
    public List<Users> getUsersSearchResults(){
        return null;
    }
    public String sendFriendRequest(Long fid){
        Users targetUser = usersFacade.find(fid);
        Friendship f = new Friendship();
        f.setSourceUser(user);
        f.setTargetUser(targetUser);
        f.setStatus("request");
        friendshipFacade.create(f);
        message = "Friendship request successfully sent to "+targetUser.getEmail();
        init();
        return "welcomeUser";
    }
    
    public String makeFriend(Long friendshipId){
        Friendship f = friendshipFacade.find(friendshipId);
        f.setStatus("friend");
        friendshipFacade.edit(f);
        message = f.getSourceUser().getName()+" was successfully added to your friend list";
        init();
        return "welcomeUser";
    }
    public String declineFrienship(Long friendshipId){
        Friendship f = friendshipFacade.find(friendshipId);
        f.setStatus("decline");
        friendshipFacade.edit(f);
        message = f.getSourceUser().getName()+" frienship request was successfully declined";
        init();
        return "welcomeUser";
    }
    public String deleteFriendship(Long friendshipId){
        Friendship f = friendshipFacade.find(friendshipId);
        friendshipFacade.remove(f);
        message = f.getSourceUser().getName()+" was successfully removed from your contacts";
        init();
        return "welcomeUser";
    }
    public String makeAntiFriend(Long friendshipId){
        Friendship f = friendshipFacade.find(friendshipId);
        f.setStatus("antiFriend");
        friendshipFacade.edit(f);
        message = f.getSourceUser().getName()+" was successfully added to your anti-friend list";
        init();
        return "welcomeUser";
    }
    public String acceptBorrowRequest( Long itemId, Long borrowerId ) {
        UserItem userItem = userItemFacade.find(itemId);
        userItem.setStatus(itemStatusFacade.find(new Long(2)));
        userItem.setBorrower(usersFacade.find(borrowerId));
        userItemFacade.edit(userItem);
        itemRequestFacade.removeItemRequest(itemId, borrowerId);
        message = "You've accepted " + userItem.getBorrower().getName() + "'s request to borrow " + userItem.getMasterItem().getName();
        init();
        return "welcomeUser"; 
    }
    public String refuseBorrowRequest( Long itemId, Long borrowerId ) {
        UserItem userItem = userItemFacade.find(itemId);
        itemRequestFacade.removeItemRequest(itemId, borrowerId);
        //message = "You've rejected " + userItem.getBorrower().getName() + "'s request to borrow " + userItem.getMasterItem().getName();
        //init();
        return "welcomeUser"; 
    }
    public String cancelBorrowRequest( Long itemId, Long borrowerId ) {
        UserItem userItem = userItemFacade.find(itemId);
        itemRequestFacade.removeItemRequest(itemId, borrowerId);
        message = "You've canceled your request to borrow " + userItem.getMasterItem().getName();
        init();
        return "welcomeUser"; 
    }
}
