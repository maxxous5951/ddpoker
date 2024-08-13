/*
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 * DD Poker - Source Code
 * Copyright (c) 2003-2024 Doug Donohoe
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * For the full License text, please see the LICENSE.txt file
 * in the root directory of this project.
 * 
 * The "DD Poker" and "Donohoe Digital" names and logos, as well as any images, 
 * graphics, text, and documentation found in this repository (including but not
 * limited to written documentation, website content, and marketing materials) 
 * are licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 
 * 4.0 International License (CC BY-NC-ND 4.0). You may not use these assets 
 * without explicit written permission for any uses not covered by this License.
 * For the full License text, please see the LICENSE-CREATIVE-COMMONS.txt file
 * in the root directory of this project.
 * 
 * For inquiries regarding commercial licensing of this source code or 
 * the use of names, logos, images, text, or other assets, please contact 
 * doug [at] donohoe [dot] info.
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 */
/*
 * License.java
 *
 * Created on August 30, 2003, 7:52 AM
 */

package com.donohoedigital.games.engine;

import com.donohoedigital.config.*;
import com.donohoedigital.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 *
 * @author  Doug Donohoe
 */
public class License extends DialogPhase
{
    //static Logger logger = Logger.getLogger(License.class);

    private JScrollPane scroll_;
    
    /**
     * create license ui
     */
    public JComponent createDialogContents() 
    {        
        // contents
        DDPanel base = new DDPanel();
        BorderLayout layout = (BorderLayout) base.getLayout();
        layout.setVgap(5);
        base.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                
        // top - label/nav
        DDPanel topbase = new DDPanel();
        base.add(topbase, BorderLayout.SOUTH);
        
        DDLabel label = new DDLabel("license", STYLE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        topbase.add(label, BorderLayout.CENTER);
           
        // html display
        DDHtmlArea html = new DDHtmlArea(GuiManager.DEFAULT, "Help");
        html.setPreferredSize(new Dimension(525, 400));
        html.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        html.setFocusable(true);
        html.setFocusTraversalKeysEnabled(false);
        html.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e)
            {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
            }
        });
        
        scroll_ = new DDScrollPane(html, STYLE, STYLE, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                   JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll_.setOpaque(false);
        base.add(scroll_, BorderLayout.CENTER);
                
        // can resize, set min/max
        getDialog().setResizable(true);
        getDialog().setMaximumSize(new Dimension(1200, 900));
        getDialog().setMinimumSize(new Dimension(300,300));
        
        // intro 
        HelpTopic lic = HelpConfig.getHelpTopic("license");
        html.setText(lic.getContents());
        html.setCaretPosition(0);

        return base;
    }
    
    /**
     * Focus to text field
     */
    protected Component getFocusComponent()
    {
        return scroll_.getVerticalScrollBar();
    }    
}
