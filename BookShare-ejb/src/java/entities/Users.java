/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.items.UserItem;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author aaghi
 */
@Entity
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique=true)
    private String email;

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    private String name;
    private String phoneNumber;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    private String address;
    private String city;
    private String country;
    
    private String password;
    
    
    @OneToMany(mappedBy="owner")
    private List<UserItem> ownedItems = new ArrayList<UserItem>();
    
    @OneToMany(mappedBy = "sourceUser")
    private List<Friendship> frienships = new ArrayList<Friendship>();

    public List<Friendship> getFrienships() {
        return frienships;
    }

    public List<Friendship> getRequestedFriendships() {
        return requestedFriendships;
    }
    
    @OneToMany(mappedBy = "targetUser")
    private List<Friendship> requestedFriendships = new ArrayList<Friendship>();
    
    


    @OneToMany(mappedBy="borrower")
    private List<UserItem> borrowedItems = new ArrayList<UserItem>();
    
    public boolean isFriends(String username){
        for(Friendship f: frienships){
           if (( f.getTargetUser().getEmail().equalsIgnoreCase(username) ) && f.getStatus().equalsIgnoreCase("friend")) {
               return true;
           }
        }
       for(Friendship f: requestedFriendships){
           if ((f.getSourceUser().getEmail().equalsIgnoreCase(username)) && f.getStatus().equalsIgnoreCase("friend")) {
               return true;
           }
        }
        return false;
    }
    
    public List<UserItem> getOwnedItems() {
        return ownedItems;
    }

    public void setOwnedItems(List<UserItem> ownedItems) {
        this.ownedItems = ownedItems;
    }
    
    public List<UserItem> getBorrowedItems() {
        return borrowedItems;
    }
    
    public void setBorrowedItems(List<UserItem> borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    public void setPassword(String password) {
        try{
            String plaintext = password;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashtext = bigInt.toString(16);
            this.password = hashtext;
        }
        catch(Exception e){
            this.password = password;
        }
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ id=" + email + " ]";
    }
    
}
