package entity.domain;

import facade.ClinicFacade;
import facade.UserAuthFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "userAuthController")
@SessionScoped
public class UserAuthController implements Serializable {

    @EJB
    private UserAuthFacade userAuthFacade;
    @EJB
    private ClinicFacade clinicFacade;

    @Inject
    private Clinic clinic;
    @Inject
    private UserAuth userAuth;
    private String username;

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void edit() {
        clinicFacade.edit(clinic);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Clinic Updated"));
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login";
    }
    
    public String getUsername() {
        clinic = ((UserAuth) userAuthFacade.findUserbyName(FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal()
                .getName())).getClinic();
        username = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal()
                .getName();
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
