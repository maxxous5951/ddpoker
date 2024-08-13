# DD Poker

## About

<div style="display: flex; align-items: center;">
  <img src="images/ddpoker-logo-750x978.png" alt="Image description" style="margin-right: 20px;" width="100"/>
  <p>

This repository contains all the source code for the DD Poker
computer game, the underlying game engine, the supporting 
backend server, and the companion website. The game itself is 
a Java Swing-based desktop application that is capable of running 
on Mac, Linux and Windows.  The backend-server is essentially
a Java Spring application that talks to MySQL.  The website 
(aka "Online Portal") is built on the Apache Wicket framework.
  </p>
</div>

## Installers

Unfortunately, there are currently no new installers for DD Poker, mainly because
the Windows and Linux installers were built with software that requires
a paid license and the Mac version used a tool that has since been removed.

There are still the [old installers](https://static.ddpoker.com/download/), which
were built many years ago, but may still work.

Maybe somebody can help with this in the future.  For now, you can build and
run it from source, as explained in Developer Notes.

## Developer Notes

For details on how to build and run DD Poker and
the backend server and website, please see [README-DEV.md](README-DEV.md).

## History

DD Poker was developed by Donohoe Digital LLC, a small computer
games studio founded by Doug Donohoe in 2003.  Its first game,
[War! Age of Imperialism](https://www.donohoedigital.com/war/) was
a computer version of the eponymous table-top board game, and it
was a finalist in the 2005 Independent Games Festival.  After releasing
the game in October 2003, Doug was celebrating in Las Vegas
and, while at a poker tournament, the proverbial lightbulb went 
off that there were no good poker software simulators out there,
especially for tournaments.  Leveraging the game 
engine he built for War!, Doug immediately started building
a poker game.  Less that a year later, DD Poker was ready for 
release.

DD Poker 1.0 was originally released (and sold in boxes!) in 
June 2004 under the name 
_DD Tournament Poker No Limit Texas Hold'em_ and 
later re-released in early 2005 as _DD Tournament Poker 2005 Collector's 
Edition_, featuring Annie Duke on the box.  The game featured 
Limit, Pot-Limit and No-Limit Texas Hold'em against computer
components.  There was no online play.

DD Poker 2.0 added the ability to play online against other
human opponents, a poker clock, a hand simulator and dozens
of other new features.  It was originally released in August
2005 as _DD No Limit Texas Hold'em Tournament Edition_, featuring 
Phil Gordon on the box.  To support online play, a back-end
API server and companion "Online Portal" was built and operated
by Donohoe Digital.  New functionality continued to be added
until early 2007.

DD Poker 3.0 was released as donation-ware in January 2009,
adding only minor new features while removing license-key 
validation logic. It continued to be updated sporadically until 
it was shutdown in July 2017.

See [whatsnew.html](code/poker/src/main/resources/config/poker/help/whatsnew.html) 
for a detailed release history starting with version 2.0.

## Why Open Source?

Even though DD Poker and the backend servers was shutdown
in July 2017, folks continue to play it by manually
sharing game URLs.  There was a minor revival during the 
2020 pandemic and sporadic inquiries have come in over the
years.

While Donohoe Digital LLC no longer exists and cannot
run the old servers, there might be folks out there that
want to run servers for their own local poker communities.
Releasing the code allows them to do this.

In addition, even though the core code is almost 20 years
old, it still actually works and might be useful to
somebody, somewhere.

## Copyright and Licenses

Unless otherwise noted, the contents of this repository are
Copyright (c) 2003-2024 Doug Donohoe.  All rights reserved.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

For the full License text, please see the [LICENSE.txt](LICENSE.txt) file
in the root directory of this project.

The "DD Poker" and "Donohoe Digital" names and logos, as well as any images,
graphics, text, and documentation found in this repository (including but not
limited to written documentation, website content, and marketing materials)
are licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives
4.0 International License (CC BY-NC-ND 4.0). You may not use these assets
without explicit written permission for any uses not covered by this License.
For the full License text, please see the [LICENSE-CREATIVE-COMMONS.txt](LICENSE-CREATIVE-COMMONS.txt) file.

For inquiries regarding commercial licensing of this source code or
the use of names, logos, images, text, or other assets, please contact
doug [at] donohoe [dot] info.

## Third Party Licenses and Other Open Source Code

DD Poker incorporates various other open source code, either directly as source files
or via maven dependencies as seen in the `pom.xml` files.  These are explained in 
[code/poker/src/main/resources/config/poker/help/credits.html](https://static.ddpoker.com/gamehelp/help/credits.html) and the licenses 
mentioned therein can be found in the `docs/license` directory.

Third party source code directly copied into this repository include the following:

* Zookitec Explicit Layout in `code/poker/src/main/java/com/zookitec/layout/*.java`
* `MersenneTwisterFast` random number generator in `code/common/src/main/java/com/donohoedigital/base/MersenneTwisterFast.java`
* `RandomGUID` generator in `code/common/src/main/java/com/donohoedigital/base/RandomGUID.java`
* `Base64` encode/decoder in `code/common/src/main/java/com/donohoedigital/base/Base64.java`

## Contributors

The following folks made excellent contributions to the DD Poker
code base as employees of Donohoe Digital:

+ Greg King
+ Sam Neth
+ Brian Zak
