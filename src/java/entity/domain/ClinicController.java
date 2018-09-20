/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.domain;

import facade.ClinicFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@Named(value = "clinicController")
@SessionScoped
public class ClinicController implements Serializable {
    
    @Inject
    private Clinic clinic;
    @EJB
    private ClinicFacade clinicFacade;
    
    public Clinic getClinic() {
        return clinic;
    }
    
    public Clinic getClinic(java.lang.Long id) {
        return clinicFacade.find(id);
    }
    
    public void Edit() {
        clinicFacade.edit(clinic);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Clinic is added: " + clinic.getName()));
    }
    
    @FacesConverter(forClass = Clinic.class)
    public static class ClinicControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClinicController controller = (ClinicController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clinicController");
            return controller.getClinic(getKey(value));
        }
        
        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }
        
        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }
        
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Clinic) {
                Clinic o = (Clinic) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Clinic.class.getName());
            }
        }
        
    }
    
}
