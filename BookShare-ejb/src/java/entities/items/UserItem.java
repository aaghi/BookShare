/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.items;

import entities.Users;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author macadada
 */
@Entity
public class UserItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Item masterItem;
    @ManyToOne
    private Location location;

    @ManyToOne
    private ItemStatus status;

    @ManyToOne
    private Users owner;

    @ManyToOne
    private Users borrower;
    
    @ManyToOne
    private Visibility visibility;

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
   
    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
    
    public Users getBorrower() {
        return borrower;
    }
    
    public void setBorrower(Users borrower) {
        this.borrower = borrower;
    }

    public Item getMasterItem() {
        return masterItem;
    }

    public void setMasterItem(Item masterItem) {
        this.masterItem = masterItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void setLocation( Location location ) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserItem)) {
            return false;
        }
        UserItem other = (UserItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserItem[ id=" + id + " ]";
    }
    
}
