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
package com.donohoedigital.games.poker.wicket;

import com.donohoedigital.config.*;
import com.donohoedigital.games.poker.service.*;
import com.donohoedigital.games.poker.wicket.admin.*;
import com.donohoedigital.games.poker.wicket.pages.error.*;
import com.donohoedigital.games.poker.wicket.pages.home.*;
import com.donohoedigital.games.server.service.*;
import com.donohoedigital.mail.*;
import com.donohoedigital.wicket.*;
import com.donohoedigital.wicket.annotations.*;
import org.apache.wicket.*;
import org.apache.wicket.protocol.http.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: donohoe
 * Date: Apr 17, 2008
 * Time: 1:31:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class PokerWicketApplication extends BaseWicketApplication
{
    public static final String SEARCH_HIGHLIGHT = "search-highlight";
    public static final Date START_OF_TIME = new GregorianCalendar(2005, 7, 1).getTime(); // august 1st 2005 (1st month of DD Poker online)

    private static final String hostName = ConfigUtils.getLocalHost(false);

    @Autowired
    private OnlineProfileService profileService;

    @Autowired
    private BannedKeyService banService;

    @Autowired
    private DDPostalService postalService;

    /**
     * Static get for PokerWicketApplication
     */
    @SuppressWarnings({"MethodOverridesStaticMethodOfSuperclass"})
    public static PokerWicketApplication get()
    {
        return (PokerWicketApplication) WebApplication.get();
    }

    @Override
    protected void init()
    {
        super.init();

        new DDMountScanner().scanPackage("com.donohoedigital.games.poker.wicket").mount(this);

        getSecuritySettings().setAuthorizationStrategy(new AdminAuthorizationStrategy());

        getApplicationSettings().setPageExpiredErrorPage(ExpiredPage.class);

        // I can't figure out (in August 2024) how to get IntelliJ to copy .html files
        // from src/main/java to target when they change.  A copy happens on start of
        // PokerJetty, but if it is already running, changes made are not copied, which makes
        // the edit cycle very slow (since you have to restart PokerJetty).  This code
        // seems to tell Wicket to look i the source location for resources.
        // Enable in development mode only.
        if (getConfigurationType().equals(Application.DEVELOPMENT)) {
            getResourceSettings().addResourceFolder("code/pokerwicket/src/main/java");
        }
    }

    @Override
    protected void onDestroy()
    {
        postalService.destroy();
        super.onDestroy();
    }

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomeHome.class;
    }

    @Override
    protected Page getExceptionPage(RuntimeException e)
    {
        return new ErrorPage(e);
    }


    /**
     * Production if running on a donohoe digital machine
     * FIX: do this smarter (when we fix the JDBC url too!)
     */
    @Override
    public String getConfigurationType()
    {
        if (hostName != null && (hostName.contains("donohoedigital.com") || hostName.contains("ddpoker.com")))
            return Application.DEPLOYMENT;

        return super.getConfigurationType();
    }

    /**
     * our session
     */
    @Override
    public Session newSession(Request request, Response response)
    {
        return new PokerSession(request);
    }

    /**
     * Get ban key service
     */
    public BannedKeyService getBanService()
    {
        return banService;
    }

    /**
     * Get online profile service
     */
    public OnlineProfileService getProfileService()
    {
        return profileService;
    }
}
