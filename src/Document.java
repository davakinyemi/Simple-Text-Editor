/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import java.util.*;
import javax.swing.undo.*;

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
    private JScrollPane scrollpane;
    private ArrayList<Document> documents;
    private CompoundUndoManager undoManager;
    private JButton undo, redo;
    private PrinterJob pJob;
    private PageFormat format;
    
    public Document(JTabbedPane tb, ArrayList<Document> documents, JTextArea textarea){
        this.documents = documents;
        tabbedPane = tb;
        fileName = "Untitled Document";
        textArea = textarea;
        initialize();
    }
    
    private void initialize(){
        undoManager = new CompoundUndoManager(textArea);
        undo = new JButton(undoManager.getUndoAction());
        redo = new JButton(undoManager.getRedoAction());
        panel = new JPanel();
        JButton button = new JButton("x");  
        label = new JLabel(fileName);
        scrollpane = new JScrollPane(textArea,
                            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        button.addActionListener((ActionEvent e) -> {
            int i = tabbedPane.indexOfTabComponent(panel);
            tabbedPane.remove(i);
            documents.remove(this);   
        });
        
        //textArea.getDocument().addUndoableEditListener(undoManager);
        
        panel.add(label);        
        panel.add(button);
        
        tabbedPane.addTab(null, scrollpane);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, panel);
        tabbedPane.setSelectedComponent(this.scrollpane);
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
    
    public JScrollPane getScrollPane(){
        return scrollpane;
    }
    
    public void undo(){
        undo.doClick();
    }
    
    public void redo(){
        redo.doClick();
    }
    
    public void pageFormat(){
        pJob = PrinterJob.getPrinterJob();
        format = pJob.pageDialog(pJob.defaultPage());        
    }
    
    public void printDoc(){
        pJob = PrinterJob.getPrinterJob();
            if(pJob.printDialog()) {
                try {
                    pJob.print();
                } catch (Exception ex) {
                    
                }
            }
    }
}
