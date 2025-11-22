package com.buspass.gui;

import javax.swing.*;
import java.awt.*;

import com.buspass.auth.UserLoginSession;
import com.buspass.gui.app_gui.MainPanel;
import com.buspass.gui.auth_gui.AuthPanel;


public class AppPanel extends JPanel implements PanelSwitcher {
    public static final String AUTH = "auth";
    public static final String MAIN = "main";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    private final AuthPanel authPanel;
    private final MainPanel mainPanel;

    public AppPanel(UserLoginSession userLoginSession) {
        setLayout(new BorderLayout());

        authPanel = new AuthPanel(userLoginSession);
        mainPanel = new MainPanel(userLoginSession);

        // give child panels the callback so they can ask the parent to switch
        authPanel.setPanelSwitcher(this);
        mainPanel.setPanelSwitcher(this);

        cards.add(authPanel, AUTH);
        cards.add(mainPanel, MAIN);

        add(cards, BorderLayout.CENTER);

        // show login by default
        cardLayout.show(cards, AUTH);
        // cardLayout.show(cards, MAIN);
    }

    @Override
    public void showPanel(String name) {
        if (name == MAIN)
            mainPanel.updateButtons();
        // ensure change happens on EDT
        if (SwingUtilities.isEventDispatchThread()) {
            cardLayout.show(cards, name);
        } else {
            SwingUtilities.invokeLater(() -> cardLayout.show(cards, name));
        }
    }
}
