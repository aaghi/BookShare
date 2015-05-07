/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.items.ItemStatus;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author macadada
 */
@Stateless
public class ItemStatusFacade extends AbstractFacade<ItemStatus> {
    @PersistenceContext(unitName = "BookShare-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemStatusFacade() {
        super(ItemStatus.class);
    }
    
}
