/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.items.Item;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aaghi
 */
@Stateless
public class ItemFacade extends AbstractFacade<Item> {
    @PersistenceContext(unitName = "BookShare-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemFacade() {
        super(Item.class);
    }
    
    public List<Item> findByName(String name) {
        Query q = em.createNativeQuery("Select * from ITEM where NAME like'%"+name+"%'", Item.class);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (List<Item>) q.getResultList();
    }
    
}
