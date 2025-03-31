/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assessor.util;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Toryang
 */
public class RefreshData {
    private final ReportLoader reportLoader;
    private boolean isRefreshing;

    public RefreshData(ReportLoader loader, JFrame frame) {
        this.reportLoader = loader;
        setupKeyBinding(frame);
    }

    private void setupKeyBinding(JFrame frame) {
        JRootPane rootPane = frame.getRootPane();
        KeyStroke f5 = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
        
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(f5, "refresh");
        rootPane.getActionMap().put("refresh", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRefreshing) {
                    isRefreshing = true;
                    reportLoader.loadData();
                }
            }
        });
    }
    
    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }
}