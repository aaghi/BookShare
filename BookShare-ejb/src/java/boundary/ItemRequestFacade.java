/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.items.ItemRequest;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author albert
 */
@Stateless
public class ItemRequestFacade extends AbstractFacade<ItemRequest> {
    @PersistenceContext(unitName = "BookShare-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemRequestFacade() {
        super(ItemRequest.class);
    }
    
    public boolean checkRequestable(Long itemId, Long borrowerId ) {
        Query q = em.createNativeQuery("SELECT * from ITEMREQUEST ir JOIN USERITEM ui ON ir.ITEM_ID=ui.ID where "
                                        + "(ir.ITEM_ID="+itemId+" AND ir.BORROWER_ID="+borrowerId+")"
                                        + " OR ((ui.STATUS_ID=1 OR ui.STATUS_ID=2) AND ir.ITEM_ID="+itemId+")"); 
        return (q.getResultList().size() >= 1) ? false : true;
    }
    
    public List<ItemRequest> getAllItemRequestsForUser( Long userId ) {
        Query q = em.createNativeQuery("SELECT ir.ID, ir.COMMENTS, ir.ITEM_ID, ir.BORROWER_ID FROM USERITEM ui "
                                        + "JOIN USERS u ON ui.OWNER_ID=u.ID "
                                        + "JOIN ITEMREQUEST ir ON ui.ID=ir.ITEM_ID "
                                        + "WHERE u.ID="+userId, ItemRequest.class);
        
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (List<ItemRequest>) q.getResultList();
    }
    
    public List<ItemRequest> getAllPendingItemRequestsForUser( Long userId ) {
        Query q = em.createNativeQuery("SELECT * FROM ITEMREQUEST "
                                        + "WHERE ITEMREQUEST.BORROWER_ID="+userId, ItemRequest.class);
        
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (List<ItemRequest>) q.getResultList();
    }
    
    public void removeItemRequest( Long itemId, Long userId ) {
        Query q = em.createNativeQuery("DELETE FROM ITEMREQUEST "
                                        + "WHERE ITEMREQUEST.ITEM_ID="+itemId
                                        + " AND ITEMREQUEST.BORROWER_ID="+userId);
        
        q.executeUpdate();
    }
}
