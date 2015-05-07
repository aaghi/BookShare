/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author macadada
 */

import boundary.ItemFacade;
import boundary.ItemStatusFacade;
import entities.items.Item;
import entities.items.ItemStatus;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


@FacesConverter(forClass=ItemStatus.class)
public class ItemStatusConverter implements Converter {

    // Init ---------------------------------------------------------------------------------------
    @EJB
    

    // Actions ------------------------------------------------------------------------------------
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Foo to the actual Foo object.
       Context ctx;
       ItemStatusFacade itemStatusFacade;
       ItemStatus stat = null;
        try {
            ctx = new InitialContext();
            itemStatusFacade = (ItemStatusFacade) ctx.lookup("java:global/BookShare/BookShare-ejb/ItemStatusFacade");
            if ((value != null) && (!value.equals(""))) {       
                 stat = itemStatusFacade.find(Long.valueOf(value));       
            }
        } catch (NamingException ex) {
            Logger.getLogger(ItemStatusConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        return stat;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        ItemStatus tmp = ((ItemStatus) value);
        return ""+tmp.getId();
    }

}