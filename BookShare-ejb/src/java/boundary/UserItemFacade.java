/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import java.util.List;
import entities.items.UserItem;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author macadada
 */
@Stateless
public class UserItemFacade extends AbstractFacade<UserItem> {
    @PersistenceContext(unitName = "BookShare-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserItemFacade() {
        super(UserItem.class);
    }
    
    public List<UserItem> findItem( String itemName, String location, String user, String me ) {
        Query q= em.createNativeQuery("SELECT * from USERITEM ui "
                                    + "join ITEM i on ui.MASTERITEM_ID=i.ID "
                                    + "join USERS u on ui.OWNER_ID=u.ID "
                                    + "join LOCATION l on ui.LOCATION_ID=l.ID "
                                    + "WHERE i.NAME like '%" + itemName + "%' "
                                    + "AND l.NAME like '%" + location + "%' "
                                    + "AND u.EMAIL like '%" + user + "%'", UserItem.class);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<UserItem> result=q.getResultList();
        List<UserItem> ret = new ArrayList<UserItem>();
        for(UserItem it: result){
            if(!(it.getOwner().getEmail().equalsIgnoreCase(me))){
                if(it.getVisibility().getId()==1){
                    ret.add(it);
                }else if(it.getVisibility().getId()==2){
                    if(it.getOwner().isFriends(me)){
                        ret.add(it);
                    }
                } 
            }
        }
        
        return ret;
    }
    
    public List<UserItem> findBorrowedItems( Long user ) {
        Query q = em.createNativeQuery("SELECT * from USERITEM ui WHERE ui.BORROWER_ID=" + user, UserItem.class);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return q.getResultList();
    }
}
