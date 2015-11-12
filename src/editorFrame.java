
import javax.swing.*;
import java.io.*;
import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dav
 */
public class editorFrame extends javax.swing.JFrame {
    private final ArrayList<Document> documents = new ArrayList<>(); // list to store existing documents
    private Document temp; // placeholder for dealing with documents
    /**
     * Creates new form editorFrame
     */
    
    public editorFrame() {
        initComponents();
        createNewFile();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");
        jMenu1.setName("openMenuItem"); // NOI18N

        jMenuItem1.setText("new");
        jMenuItem1.setName("addNewDoc"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("save");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("open");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem4.setText("undo");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("redo");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setText("cut");
        jMenu2.add(jMenuItem6);

        jMenuItem7.setText("copy");
        jMenu2.add(jMenuItem7);

        jMenuItem8.setText("paste");
        jMenu2.add(jMenuItem8);

        jMenuItem9.setText("select all");
        jMenu2.add(jMenuItem9);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        createNewFile();
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    // eventhandling method for saving files
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:        
        File file;
        
        getSelectedDoc();
        
        if(temp.getFilePath() == null){ // if the document doesn't have a file path i.e. has not been saved yet
            JFileChooser fileSave = new JFileChooser();
            fileSave.showOpenDialog(new JFrame());      
            file = fileSave.getSelectedFile();
            temp.setPath(file.getPath());
        } else {
            file = new File(temp.getFilePath()); // otherwise get its file path
        }
        
        saveFile(file);
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    // eventhandling method for opening files
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileOpen = new JFileChooser();
	fileOpen.showOpenDialog(new JFrame());
	openFile(fileOpen.getSelectedFile());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    // undo
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        getSelectedDoc();
        temp.undo();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    // redo
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        getSelectedDoc();
        temp.redo();
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    
    private void openFile(File file){  
        boolean exists = false;
        JTextArea textarea;
        if(docEmpty()){ // check if a tab with an empty text area exists                               
            textarea = temp.getTextArea();
            exists = true;
        } else{
            textarea = new JTextArea(); 
            temp = new Document(jTabbedPane1, documents, textarea);
        }
                
        temp.setPath(file.getPath());
        updateDocName(file);
        
        readToFile(file, textarea);
        
        if(exists == false){            
            documents.add(temp);            
        }          
        
        jTabbedPane1.setSelectedComponent(temp.getScrollPane());
    }
    
    private void readToFile(File file, JTextArea textarea){
        try{
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder sb = new StringBuilder();
                while((line = reader.readLine()) != null){
                    sb.append(line).append("\n");
                }
                textarea.setText(sb.toString());
            }
            } catch (Exception ex){
                JOptionPane.showMessageDialog(new JFrame(), "couldn't read the file");
            }
    }
    
    private void createNewFile(){
        Document doc = new Document(jTabbedPane1, documents, new JTextArea());
        documents.add(doc);
    }    
    
    private void saveFile(File file){
        try{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(temp.getTextArea().getText());
            }
        } catch(IOException ex){
            JOptionPane.showMessageDialog(new JFrame(), "An Error has occured!" + "\n" + "File could not be saved.");
        }
        
        updateDocName(file);
        
    }
    
    private void updateDocName(File file){        
        temp.setFileName(file.getName());
    }
    
    private void getSelectedDoc(){
        JScrollPane sp = (JScrollPane) jTabbedPane1.getSelectedComponent(); // get the currently selected tab by its component
        for(Document doc : documents){ // loop through the documents list and find the document whose scrollbar component corresponds to the one currently selected 
            if(doc.getScrollPane() == sp){
                temp = doc;
                break;
            }
        }
    }
    
    private boolean docEmpty(){
        temp = null;
        if(documents.isEmpty())
            return false;
        
        for(Document doc : documents){
            if(doc.getFilePath() == null && (doc.getTextArea().getText() == null || "".equals(doc.getTextArea().getText()))){
                temp = doc;
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(editorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editorFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
