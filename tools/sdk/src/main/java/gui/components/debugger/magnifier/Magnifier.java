//-------------------------------------------------------------------------------------
package xpu.sw.tools.sdk.gui.components.debugger.magnifier;
//-------------------------------------------------------------------------------------
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

import org.apache.commons.configuration2.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.appender.rolling.*;

import xpu.sw.tools.sdk.common.context.*;

import xpu.sw.tools.sdk.gui.*;
import xpu.sw.tools.sdk.gui.components.common.buttons.*;
//import xpu.sw.tools.sdk.debug.debugger.core.*;

//-------------------------------------------------------------------------------------
public class Magnifier extends javax.swing.JPanel {
    private Gui gui;
    private Context context;
    private org.apache.logging.log4j.Logger log;

    private org.apache.commons.configuration2.Configuration sdkConfig;
    private org.apache.commons.configuration2.Configuration xpuConfig;

    private MemoryDataTableModel memoryDataTableModel;
    private RegistryDataTableModel registryDataTableModel;
    private int startIndex;
    private int stopIndex;
    private int minIndex;
    private int maxIndex;

//-------------------------------------------------------------------------------------
    public Magnifier(Gui _gui, Context _context) {
        gui = _gui;
        context = _context;
        log = _context.getLog();
        initComponents();
        sdkConfig = context.getSdkConfig();
        xpuConfig = context.getXpuConfig();
        init();
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setAutoscrolls(true);
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jButton1.setText("<");
        jButton1.setAlignmentY(0.0F);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setColumns(4);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField1.setText("0");
        jTextField1.setAlignmentX(0.0F);
        jTextField1.setAlignmentY(0.0F);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(5, 5, 5)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.WEST);

        jButton2.setText(">");
        jButton2.setAlignmentY(0.0F);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField2.setColumns(4);
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.setText("32");
        jTextField2.setAlignmentX(0.0F);
        jTextField2.setAlignmentY(0.0F);
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jPanel1.add(jPanel5, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setDividerSize(4);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName(""); // NOI18N
        jSplitPane1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSplitPane1PropertyChange(evt);
            }
        });

        jPanel2.setAlignmentX(0.0F);
        jPanel2.setAlignmentY(0.0F);
        jPanel2.setAutoscrolls(true);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setAlignmentX(0.0F);
        jScrollPane3.setAlignmentY(0.0F);
        jScrollPane3.setMinimumSize(null);
        jScrollPane3.setPreferredSize(null);

        jTable1.setAlignmentX(0.0F);
        jTable1.setAlignmentY(0.0F);
        jTable1.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setTopComponent(jPanel2);

        jPanel4.setAlignmentX(0.0F);
        jPanel4.setAlignmentY(0.0F);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setAlignmentX(0.0F);
        jScrollPane4.setAlignmentY(0.0F);
        jScrollPane4.setMinimumSize(null);
        jScrollPane4.setPreferredSize(null);

        jTable2.setAlignmentX(0.0F);
        jTable2.setAlignmentY(0.0F);
        jTable2.setColumnSelectionAllowed(true);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setBottomComponent(jPanel4);

        add(jSplitPane1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        move(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        move(-1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        String _text = jTextField1.getText().trim();
        if(_text.isEmpty()){
            startIndex = minIndex;
        } else {
            startIndex = Integer.parseInt(_text);            
        }
        move(0);
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        String _text = jTextField2.getText().trim();
        if(_text.isEmpty()){
            stopIndex = maxIndex;
        } else {
            stopIndex = Integer.parseInt(jTextField2.getText());            
        }
        move(0);
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jSplitPane1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSplitPane1PropertyChange
        // TODO add your handling code here:
        double _jSplitPane1Location = (double)jPanel2.getHeight()/(jPanel2.getHeight() + jPanel4.getHeight());
        sdkConfig.setProperty("debug.magnifier.jSplitPane1", _jSplitPane1Location);

    }//GEN-LAST:event_jSplitPane1PropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

//-------------------------------------------------------------------------------------
    private void init(){
        int _nCells = context.getNCells();        
        int _memDataArraySizeLog = context.getMemDataArraySizeLog();
        int _memDataArraySize = (1 << _memDataArraySizeLog);
        startIndex = sdkConfig.getInt("debug.magnifier.startIndex", 0);
        stopIndex = sdkConfig.getInt("debug.magnifier.stopIndex", 31);
        minIndex = 0;
        maxIndex = _nCells;
//        log.debug("Magnifier: _nCells=" + _nCells + ", _memSize=" + _memSize);

        memoryDataTableModel = new MemoryDataTableModel(gui, context, _memDataArraySize, _nCells);
        memoryDataTableModel.setBounds(startIndex, stopIndex);
        jTable1.setModel(memoryDataTableModel);
        TableColumnModel _columnModel1 = jTable1.getColumnModel();
        _columnModel1.getColumn(0).setPreferredWidth(50);
        _columnModel1.getColumn(0).setMaxWidth(50);
/*        _columnModel1.getColumn(1).setPreferredWidth(100);
        _columnModel1.getColumn(1).setMaxWidth(100);*/
        
        registryDataTableModel = new RegistryDataTableModel(gui, context, 6, _nCells);
        registryDataTableModel.setBounds(startIndex, stopIndex);
        jTable2.setModel(registryDataTableModel);
        TableColumnModel _columnModel2 = jTable2.getColumnModel();
        _columnModel2.getColumn(0).setPreferredWidth(70);
        _columnModel2.getColumn(0).setMaxWidth(70);
/*        _columnModel2.getColumn(1).setPreferredWidth(100);
        _columnModel2.getColumn(1).setMaxWidth(100);*/
        refreshTables();
        double _jSplitPane1Location = sdkConfig.getDouble("debug.magnifier.jSplitPane1", 0.77);
        jSplitPane1.setDividerLocation(_jSplitPane1Location);       
        setVisible(true);
    }

//-------------------------------------------------------------------------------------
    public void updateMemoryData(int[][] _memoryData){
        memoryDataTableModel.update(_memoryData);
        refreshTables();
    }

//-------------------------------------------------------------------------------------
    public void updateRegistryData(int[][] _registryData){
        registryDataTableModel.update(_registryData);
        refreshTables();
    }

//-------------------------------------------------------------------------------------
    public void move(int _inc){
        if(startIndex + _inc >= minIndex){
            startIndex += _inc;
        }
        if(stopIndex + _inc <= maxIndex){
            stopIndex += _inc;
        }
//            _tf.setText(String.valueOf(_existentValue));
//            _tf.setBorder(new LineBorder(Color.RED, 1));

        refreshTables();
    }

//-------------------------------------------------------------------------------------
    public void value(JTextField _tf, int _value, int _min, int _max){
        String _text = _tf.getText();
        int _existentValue = Integer.parseInt(_text);
        if(_value < _min) {
            _tf.setText(String.valueOf(_existentValue));
        } else if(_value > _max) {
            _tf.setText(String.valueOf(_existentValue));
        } else {
            _tf.setText(String.valueOf(_existentValue));
            _tf.setBorder(new LineBorder(Color.RED, 1));
        }
    }

//-------------------------------------------------------------------------------------
    public void refreshTables(){
        memoryDataTableModel.setBounds(startIndex, stopIndex);
        registryDataTableModel.setBounds(startIndex, stopIndex);
        jTextField1.setText(String.valueOf(startIndex));
        jTextField2.setText(String.valueOf(stopIndex));
//        memoryDataTableModel.fireTableDataChanged();
//        registryDataTableModel.fireTableDataChanged();
        sdkConfig.setProperty("debug.magnifier.startIndex", startIndex);
        sdkConfig.setProperty("debug.magnifier.stopIndex", stopIndex);
    }
/*    
//-------------------------------------------------------------------------------------
    public boolean isOptimizedDrawingEnabled(){
        return false;
    }
*/
//-------------------------------------------------------------------------------------
}
//-------------------------------------------------------------------------------------
