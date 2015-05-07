/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author aaghi
 */
@Entity
public class User_Groups implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String email;
    private String user_role;

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_role() {
        return user_role;
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
        if (!(object instanceof User_Groups)) {
            return false;
        }
        User_Groups other = (User_Groups) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User_Groups[ id=" + email + " ]";
    }
    
}
