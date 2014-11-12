package de.scouthero.managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ImageSwitchBean {
		  
    private List<String> images  = new ArrayList<String>();  
  
    public ImageSwitchBean() {  
    }  
  
    public List<String> getIndexPageImages() {  
        images.add("searchTeam.png");  
        images.add("searchOne.png");  
        images.add("foundTeam.png");  
        return images;  
    }  
}
