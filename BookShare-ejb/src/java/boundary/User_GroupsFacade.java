/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.User_Groups;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aaghi
 */
@Stateless
public class User_GroupsFacade extends AbstractFacade<User_Groups> {
    @PersistenceContext(unitName = "BookShare-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public User_GroupsFacade() {
        super(User_Groups.class);
    }
    
}
