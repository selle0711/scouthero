package de.scouthero.managedBeans;

import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean
@SessionScoped
public class MenuBean {  
  
    private MenuModel model;  
  
    public MenuBean() {  
    }  
  
    public MenuModel getModel() {  
    	 model = new DefaultMenuModel();  
         
         //First submenu  
    	 DefaultSubMenu submenu = new DefaultSubMenu("Navigation");  
         submenu.addElement(new DefaultMenuItem("Home", "ui-icon-home", "/index"));  
         
         model.addElement(submenu);  
           
         //Second submenu  
         submenu = new DefaultSubMenu("Transfermarkt");  
         submenu.addElement(new DefaultMenuItem("Vereinssuche","ui-icon-search", "/showClubs"));  
         submenu.addElement(new DefaultMenuItem("Spielersuche", "ui-icon-search", "/showClubs"));  
           
         model.addElement(submenu);  
//         
//         FacesContext ctx = FacesContext.getCurrentInstance();
//         HttpSession session = (HttpSession)ctx.getExternalContext().getSession(true);
//         UserHandler userHandler = (UserHandler)session.getAttribute("userHandler");
//         
         ELResolver el = FacesContext.getCurrentInstance()
         	      .getApplication().getELResolver();
         UserHandler userHandler = (UserHandler) el.getValue(FacesContext.getCurrentInstance()
         	      .getELContext(), null, "userHandler");
         
         DefaultSubMenu network = new DefaultSubMenu("Scouthero-Netzwerk");  
         if (userHandler != null && userHandler.getUser() != null && userHandler.isLoggedIn()) {
              network.addElement(new DefaultMenuItem("Mein Profil", "ui-icon-wrench", "/userProfile"));  
              
              network.addElement( new DefaultMenuItem("Meine Inserate","ui-icon-calendar", "/inserat")); 
              
              network.addElement( new DefaultMenuItem("Neues Inserat", "ui-icon-note", "/inserat"));  
              
              DefaultMenuItem item = new DefaultMenuItem("Abmelden", "ui-icon-power");  
//              Application app = FacesContext.getCurrentInstance().getApplication();
//              MethodExpression methodExpression = app.getExpressionFactory().createMethodExpression(
//             		         FacesContext.getCurrentInstance().getELContext(), "#{userHandler.logout}", null, new Class[] { });
              item.setCommand("#{userHandler.logout}");
              
              network.getElements().add(item); 
         } else {
			network.getElements().add(new DefaultMenuItem("Anmelden", "ui-icon-key", "/login"));
			network.getElements().add(new DefaultMenuItem("Registrieren", "ui-icon-person", "/registerProfile")); 
         }
         model.addElement(network);  
         
         model.addElement(new DefaultSeparator());
         
         model.addElement(new DefaultMenuItem("Impressum", "ui-icon-contact", "/impressum"));
         
        return model;  
    }     
            
    public void addMessage(String summary) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
}  
