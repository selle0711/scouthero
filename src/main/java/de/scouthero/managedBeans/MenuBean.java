package de.scouthero.managedBeans;

import javax.el.ELResolver;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.separator.Separator;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

@ManagedBean
@SessionScoped
public class MenuBean {  
  
    private MenuModel model;  
  
    public MenuBean() {  
    }  
  
    public MenuModel getModel() {  
    	 model = new DefaultMenuModel();  
         
         //First submenu  
         Submenu submenu = new Submenu();  
         submenu.setLabel("Navigation");  
           
         MenuItem item = new MenuItem();  
         item.setValue("Home");  
         item.setIcon("ui-icon-home");
         item.setOutcome("index");
         submenu.getChildren().add(item);  
         
         model.addSubmenu(submenu);  
           
         //Second submenu  
         submenu = new Submenu();  
         submenu.setLabel("Transfermarkt");  
           
         item = new MenuItem();  
         item.setValue("Vereinssuche");  
         item.setOutcome("showClubs");  
         item.setIcon("ui-icon-search");
         
         submenu.getChildren().add(item);  
           
         item = new MenuItem();  
         item.setValue("Spielersuche");  
         item.setOutcome("showClubs");  
         item.setIcon("ui-icon-search");
         submenu.getChildren().add(item);  
           
         model.addSubmenu(submenu);  
//         
//         FacesContext ctx = FacesContext.getCurrentInstance();
//         HttpSession session = (HttpSession)ctx.getExternalContext().getSession(true);
//         UserHandler userHandler = (UserHandler)session.getAttribute("userHandler");
//         
         ELResolver el = FacesContext.getCurrentInstance()
         	      .getApplication().getELResolver();
         UserHandler userHandler = (UserHandler) el.getValue(FacesContext.getCurrentInstance()
         	      .getELContext(), null, "userHandler");
         
         Submenu network = new Submenu();  
         network.setLabel("Scouthero-Netzwerk");  
         if (userHandler != null && userHandler.getUser() != null && userHandler.isLoggedIn()) {
         	 item = new MenuItem();  
              item.setValue("Mein Profil");  
              item.setOutcome("userProfile");  
              item.setIcon("ui-icon-wrench");
              network.getChildren().add(item);  
              
              item = new MenuItem();  
              item.setValue("Meine Inserate");  
              item.setOutcome("inserat");  
              item.setIcon("ui-icon-calendar");
              network.getChildren().add(item); 
              
              item = new MenuItem();  
              item.setValue("Neues Inserat");  
              item.setOutcome("inserat");  
              item.setIcon("ui-icon-note");
              network.getChildren().add(item); 
              
              item = new MenuItem();  
              item.setValue("Abmelden");  
              Application app = FacesContext.getCurrentInstance().getApplication();
              MethodExpression methodExpression = app.getExpressionFactory().createMethodExpression(
             		         FacesContext.getCurrentInstance().getELContext(), "#{userHandler.logout}", null, new Class[] { });
              item.setActionExpression(methodExpression);
              item.setIcon("ui-icon-power");
              item.setUpdate("messages");
              
              network.getChildren().add(item); 
         } else {
         	 item = new MenuItem();  
              item.setValue("Anmelden");  
              item.setOutcome("login");  
              item.setIcon("ui-icon-key");
              network.getChildren().add(item);  
              
              item = new MenuItem();  
              item.setValue("Registrieren");  
              item.setOutcome("registerProfile");  
              item.setIcon("ui-icon-person");
              network.getChildren().add(item); 
         }
         model.addSubmenu(network);  
         
         model.addSeparator(new Separator());
         
         item = new MenuItem();  
         item.setValue("Impressum");  
         item.setOutcome("impressum");  
         item.setIcon("ui-icon-contact");
         model.addMenuItem(item);
         
        return model;  
    }     
            
    public void addMessage(String summary) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
}  
