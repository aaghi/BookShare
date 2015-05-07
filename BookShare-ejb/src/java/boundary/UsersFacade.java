/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aaghi
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "BookShare-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public Users findByLogin(String login) {
        
        Query q=     em.createNativeQuery("Select * from USERS where email='"+login+"'", Users.class);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (Users)q.getSingleResult();
    }
    public List<Users> findUser(String name){
        Query q=     em.createNativeQuery("Select * from USERS where email like '%"+name+"%' "
                +"OR name like '%"+name+"%'", Users.class);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return q.getResultList();
    }
    public void refresh(Users u){
        em.refresh(u);
    }
    
}
