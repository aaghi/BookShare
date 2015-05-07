/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author albert
 */


import boundary.LocationFacade;
import entities.items.Location;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


@FacesConverter(forClass=Location.class)
public class LocationConverter implements Converter {

    // Init ---------------------------------------------------------------------------------------
    @EJB
    

    // Actions ------------------------------------------------------------------------------------
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Foo to the actual Foo object.
       Context ctx;
       LocationFacade locationFacade;
       Location loc = null;
        try {
            ctx = new InitialContext();
            locationFacade = (LocationFacade) ctx.lookup("java:global/BookShare/BookShare-ejb/LocationFacade");
            if ((value != null) && (!value.equals(""))) {       
                 loc = locationFacade.find(Long.valueOf(value));       
            }
        } catch (NamingException ex) {
            Logger.getLogger(LocationConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        return loc;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        Location tmp = ((Location) value);
        return ""+tmp.getId();
    }

}