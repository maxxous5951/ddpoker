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
package com.donohoedigital.games.poker.wicket.util;


import com.donohoedigital.base.*;
import com.donohoedigital.games.poker.engine.*;
import com.donohoedigital.games.poker.model.*;
import org.apache.wicket.markup.html.*;
import org.apache.wicket.protocol.http.*;

/**
 * Created by IntelliJ IDEA.
 * User: donohoe
 * Date: May 4, 2008
 * Time: 3:01:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinGameResource extends DynamicWebResource
{
    private static final long serialVersionUID = 42L;

    private String name;
    private String url;

    public JoinGameResource(OnlineGame game, boolean observe)
	{
        // file name
        name = new StringBuilder().append("game")
                                  .append(game.getId())
                                  .append(observe ? "obs" : "")
                                  .append('.')
                                  .append(PokerConstants.JOIN_FILE_EXT).toString();

        // url
        StringBuilder sb = new StringBuilder(game.getUrl());
        if (observe) sb.append(PokerConstants.JOIN_OBSERVER_QUERY);
        sb.append('\n');
        url = sb.toString();
    }

	@Override
    protected void setHeaders(WebResponse response)
	{
		super.setHeaders(response);
        response.setAttachmentHeader(name);
	}

	private class PokerUrlResourceState extends ResourceState
	{
		private byte[] stream;

		private PokerUrlResourceState(byte[] data)
		{
			stream = data;
		}
		@Override
        public byte[] getData()
		{
			return stream;
		}
		@Override
        public int getLength()
		{
			return stream.length;
		}
		@Override
        public String getContentType()
		{
			return PokerConstants.CONTENT_TYPE_JOIN;
		}
	}

	@Override
    protected ResourceState getResourceState()
	{
        return new PokerUrlResourceState(Utils.encodeBasic(url));
	}
}
