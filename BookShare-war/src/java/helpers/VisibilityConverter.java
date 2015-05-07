/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author macadada
 */

import boundary.VisibilityFacade;
import entities.items.Visibility;
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


@FacesConverter(forClass=Visibility.class)
public class VisibilityConverter implements Converter {

    // Init ---------------------------------------------------------------------------------------
    @EJB
    

    // Actions ------------------------------------------------------------------------------------
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Foo to the actual Foo object.
       Context ctx;
       VisibilityFacade visFacade;
       Visibility vis = null;
        try {
            ctx = new InitialContext();
            visFacade = (VisibilityFacade) ctx.lookup("java:global/BookShare/BookShare-ejb/VisibilityFacade");
            if ((value != null) && (!value.equals(""))) {       
                 vis = visFacade.find(Long.valueOf(value));       
            }
        } catch (NamingException ex) {
            Logger.getLogger(VisibilityConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        return vis;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        Visibility tmp = ((Visibility) value);
        return ""+tmp.getId();
    }

}