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
package com.donohoedigital.games.tools;

import com.donohoedigital.base.*;
import com.donohoedigital.server.*;

import java.io.*;


/**
 * Generates a random key for use in hashing and encryption.
 */
public class EncryptKey
{
    /**
     * Sets up application-specific command line options.
     */
    private static void setupCommandLineOptions() {

        CommandLine.setUsage("EncryptKey [options]");

        CommandLine.addStringOption("rawKey", null);
        CommandLine.setDescription("rawKey", "File containing the raw key value", "filename");
        CommandLine.setRequired("rawKey");

        CommandLine.addStringOption("out", null);
        CommandLine.setDescription("out", "File to which the encrypted string is written", "filename");
        CommandLine.setRequired("out");

        return;
    }


    /**
     * Parse the args and do as told.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        // Use the server security provider.
        SecurityUtils.setSecurityProvider(new ServerSecurityProvider());

        setupCommandLineOptions();
        CommandLine.parseArgs(args);
        TypedHashMap hmOptions = CommandLine.getOptions();

        String rawKeyPath = hmOptions.getString("rawKey");
        String outPath = hmOptions.getString("out");

        byte[] key = null;

        FileInputStream fis = null;
        File rawKeyFile = new File(rawKeyPath);
        key = new byte[(int) rawKeyFile.length()];

        try
        {
            fis = new FileInputStream(rawKeyFile);
            fis.read(key);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        finally
        {
            try { if (fis != null) fis.close(); } catch (IOException e) { }
        }


        String encryptedKey = SecurityUtils.encryptKey(key);
        FileOutputStream fos = null;

        try
        {
            fos = new FileOutputStream(outPath);

            fos.write(Utils.encode(encryptedKey));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        finally
        {
            try { if (fos != null) fos.close(); } catch (IOException e) { }
        }
    }
}
