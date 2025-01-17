//-------------------------------------------------------------------------------------
package xpu.sw.tools.sdk.gui.components.menu.file.preferences.sections.general;

//-------------------------------------------------------------------------------------
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.*;
//import com.formdev.flatlaf.*;

import org.apache.commons.configuration2.*;
import org.apache.logging.log4j.*;

import xpu.sw.tools.sdk.common.context.*;

import xpu.sw.tools.sdk.gui.*;
import xpu.sw.tools.sdk.gui.components.menu.file.preferences.*;
import xpu.sw.tools.sdk.gui.components.terminal.*;

//-------------------------------------------------------------------------------------
public class General extends javax.swing.JPanel {
    private Gui gui;
    private Context context;
    private Preferences preferences;



    private Logger log;
    private Configuration sdkConfig;
    private Terminal term;
    private Preferences pref;
    private String themeString;
    private Menu menu;
    private boolean initDone;

    /**
     * Creates new form General
     */

    public General(Gui _gui, Context _context, Preferences _preferences) {
        gui = _gui;
        context = _context;
        preferences = _preferences;

        log = _context.getLog();
        sdkConfig = _context.getSdkConfig();
        initDone = false;

        initComponents();
        init();
        setVisible(true);        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox2 = new javax.swing.JComboBox<>();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jCheckBox1.setText("Automatically check for updates ");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Every day", "Every week", "Every month" }));

        jCheckBox2.setText("Automatically install new updates");

        jLabel2.setText("Theme:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)))
                        .addGap(25, 25, 25)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addGap(9, 9, 9))
        );

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private synchronized void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
//        String _className = ((javax.swing.UIManager.LookAndFeelInfo)jComboBox1.getSelectedItem()).getClassName();
        if(initDone){
            String _lf = (String)jComboBox1.getSelectedItem();
    //        log.info("Selecting [" + _themeString + "]...");
            gui.getServices().getUtils().selectLF(_lf, gui);
            gui.getServices().getUtils().selectLF(_lf, preferences);
//            String _themeClass = gui.getServices().getUtils().getThemeClass(String _theme);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables


//-------------------------------------------------------------------------------------
    protected synchronized void init(){
        String _lf = context.getSdkConfig().getString("lf");
        java.util.List<String> _lfs = gui.getServices().getUtils().getAvailableLFs();
        for(int i = 0; i < _lfs.size();i++) {
            jComboBox1.addItem(_lfs.get(i));
        }
        if((_lf != null) && (!_lf.isEmpty())){
            jComboBox1.setSelectedItem(_lf);            
        }

        jCheckBox1.setSelected(sdkConfig.getBoolean("gui.menu.file.preferences.general.automaticallyCheckForUpdates.enabled", true));
        jComboBox2.setSelectedItem(sdkConfig.getString("gui.menu.file.preferences.general.automaticallyCheckForUpdates.interval", "Every Day"));
        jCheckBox2.setSelected(sdkConfig.getBoolean("gui.menu.file.preferences.general.automaticallyInstallUpdates.enabled", true));
        initDone = true;
/*
        String _librariesPath = context.getSdkConfig().getString("librariesPath", "~/projects/archacc/sw/libraries/");
        jTextField1.setText(_librariesPath);
        
        String _appsPath = context.getSdkConfig().getString("appsPath", "~/.xpu/projects/");
        jTextField2.setText(_appsPath);
*/
        //Theme _selectedTheme = null;
//        for (javax.swing.UIManager.LookAndFeelInfo _l : javax.swing.UIManager.getInstalledLookAndFeels()) {
///*            if ("Mac OS X".equals(info.getName())) {
//                javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                break;
//            }
//                   log.debug(info.getName());
//*/
//            Theme _theme = new Theme(_l);
//            jComboBox1.addItem(_theme);
//            if(_theme.getClassName().equals(sdkConfig.getString("theme"))){
//                _selectedTheme = _theme;
//           }
//        }
//       // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        if(_selectedTheme != null){
//            jComboBox1.setSelectedItem(_selectedTheme);
//        }

        
    }

//-------------------------------------------------------------------------------------
    public boolean getAutomaticallyCheckForUpdatesEnabled(){
        return jCheckBox1.isSelected();
    }

//-------------------------------------------------------------------------------------
    public String getAutomaticallyCheckForUpdatesInterval(){
        return (String)jComboBox2.getSelectedItem();
    }

//-------------------------------------------------------------------------------------
    public boolean getAutomaticallyInstallUpdatesEnabled(){
        return jCheckBox2.isSelected();
    }

//-------------------------------------------------------------------------------------
    public String getSelectedLF(){        
        String _lf = (String)jComboBox1.getSelectedItem();
        return _lf;
    }

//-------------------------------------------------------------------------------------
}
//-------------------------------------------------------------------------------------
