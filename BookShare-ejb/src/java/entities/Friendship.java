/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author aaghi
 */
@Entity
public class Friendship implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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
        if (!(object instanceof Friendship)) {
            return false;
        }
        Friendship other = (Friendship) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Friendship[ id=" + id + " ]";
    }
    
        // ...
    /**
     * the user who asked the friendship
     */
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Users sourceUser;

    public void setSourceUser(Users sourceUser) {
        this.sourceUser = sourceUser;
    }

    public void setTargetUser(Users targetUser) {
        this.targetUser = targetUser;
    }

    public Users getSourceUser() {
        return sourceUser;
    }

    public Users getTargetUser() {
        return targetUser;
    }

    /**
     * the user to whom the friendship was asked
     */
    @ManyToOne()
    @JoinColumn(name = "friend_id")
    private Users targetUser;
    
}
