/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dav
 */
public class Document {
    private String text;
    
    public Document(String text){
        this.text = text;
    }
    
    public Document(){
        
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public String getText(){
        return text;
    }
}
