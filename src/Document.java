/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author dav
 */
public class Document {
    private String fileName;
    private String filePath;
    private JTabbedPane tabbedPane;
    private JTextArea textArea;
    private JPanel panel;
    private JLabel label;
    private ArrayList<Document> documents;
    
    public Document(JTabbedPane tb, ArrayList<Document> documents){
        this.documents = documents;
        tabbedPane = tb;
        fileName = "Untitled Document";
        textArea = new JTextArea();
        initialize();
    }
    
    private void initialize(){
        panel = new JPanel();
        JButton button = new JButton("x");  
        label = new JLabel(fileName);
        
        button.addActionListener((ActionEvent e) -> {
            int i = tabbedPane.indexOfTabComponent(panel);
            tabbedPane.remove(i);
            documents.remove(this);   
        });
        
        panel.add(label);        
        panel.add(button);
        
        tabbedPane.addTab(null, new JScrollPane(textArea,
                            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, panel);
        textArea.requestFocus();
    }
    
    public void setFileName(String fileName){
        this.fileName = fileName;
        
        label.setText(fileName);
    }
    
    public void setPath(String filePath){
        this.filePath = filePath;
    }
    
    public String getFilePath(){
        return filePath;
    }
    
    public JTextArea getTextArea(){
        return textArea;
    }
}
