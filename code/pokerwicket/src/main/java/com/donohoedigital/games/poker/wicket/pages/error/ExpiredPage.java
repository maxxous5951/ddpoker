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
package com.donohoedigital.games.poker.wicket.pages.error;

import com.donohoedigital.games.poker.wicket.util.*;
import com.donohoedigital.wicket.*;
import org.apache.wicket.*;
import org.apache.wicket.request.target.basic.*;
import org.wicketstuff.annotation.mount.*;

/**
 * @author Doug Donohoe
 */
@MountPath(path = "expired")
public class ExpiredPage extends ErrorPokerPage
{
    //private static Logger logger = Logger.getLogger(ExpiredPage.class);

    private static final long serialVersionUID = 42L;

    @SuppressWarnings({"ThisEscapedInObjectConstruction"})
    public ExpiredPage(PageParameters parameters)
    {
        super(parameters);

        // clear login cookie if requested expired page (most likely would have been logout link)
        new LoginUtils(this).deleteLoginCookie();

        // redirect if we had a cookie
        ExpirationUtils exp = new ExpirationUtils();
        if (exp.getLastPath() != null)
        {
            //logger.debug("Expired page redirecting to: " + exp.getLastPath());

            // redirect
            getRequestCycle().setRequestTarget(new RedirectRequestTarget(exp.getLastPath()));
        }
    }
}
