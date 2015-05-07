/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.LocationFacade;
import entities.items.Location;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author albert
 */
@ManagedBean
@RequestScoped
public class LocationView {
    @EJB
    private LocationFacade locationFacade;
    
    private Location location;
    private List<Location> allLocations;

    public Location getLocation() {
        return this.location;
    }
    
    public void setLocation(Location location){
        this.location = location;
    }
    
    public List<Location> getAllLocations() {
        allLocations = locationFacade.findAll();
        return allLocations;
    }
    
    public void addLocation() {
       this.locationFacade.create(location);
       allLocations.add(location);
    }
    
    /**
     * Creates a new instance of LocationView
     */
    public LocationView() {
        this.location = new Location();
    }
}
