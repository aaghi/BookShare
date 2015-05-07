/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author macadada
 */
import boundary.CategoryFacade;
import boundary.ItemFacade;
import entities.items.Item;
import entities.items.Category;
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


@FacesConverter(forClass=Category.class)
public class CategoryConverter implements Converter {

    // Init ---------------------------------------------------------------------------------------
    @EJB
    

    // Actions ------------------------------------------------------------------------------------
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Foo to the actual Foo object.
       Context ctx;
       CategoryFacade categoryFacade;
       Category cat = null;
        try {
            ctx = new InitialContext();
            categoryFacade = (CategoryFacade) ctx.lookup("java:global/BookShare/BookShare-ejb/CategoryFacade");
            if ((value != null) && (!value.equals(""))) {       
                 cat = categoryFacade.find(Long.valueOf(value));       
            }
        } catch (NamingException ex) {
            Logger.getLogger(CategoryConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
       

        return cat;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        Category tmp = ((Category) value);
        return ""+tmp.getId();
    }

}