/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.items;

import entities.Users;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author albert
 */
@Entity
public class ItemRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private UserItem item;
    @OneToOne
    private Users borrower;

    private String comments;

    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    public Users getBorrower() {
        return borrower;
    }
    
    public void setBorrower(Users borrower) {
        this.borrower = borrower;
    }

    public UserItem getItem() {
        return item;
    }

    public void setItem(UserItem item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemRequest)) {
            return false;
        }
        ItemRequest other = (ItemRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ItemRequest[ id=" + id + " ]";
    }
    
}
