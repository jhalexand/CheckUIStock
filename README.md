# Personal Ubiquiti/UniFi/store.ui.com Stock Checker with Push Notifications

This simple project checks to see if a particular product is in stock
on Ubiquiti's website, and sends a push notification via
[Pushover.net](https://pushover.net/) if it is. It is meant to run from a cron
job to schedule how often you want to check.  It is very bare-bones.

The checking logic is based on the checks in
[this project](https://github.com/rishabg/unifi_checker). Thanks!

To use this, you'll need to have a Pushover user id and define an api
token for it to use.  Then running it is simple. Build the shadowJar
with

```shell script
./gradlew shadowJar
```

Then give it the URL for a Ubiquiti store product page such as this one
for the Dream Router: https://store.ui.com/collections/unifi-network-unifi-os-consoles/products/dream-router

Anything that has the "Sold Out" tag on it should work.

Usage is as follows:

```
$ java -cp build/libs/CheckUIStock-all.jar com.landmanatee.ui.CheckUI
Usage:

Built-in Options

Char Long Name Type             Default Usage
     usage                              Write out this usage/help statement.

CheckUIOpts

Char Long Name Type             Default Usage
   p page      java.lang.String         the product page to check
   t token     java.lang.String         your pushover.net api token
   u userid    java.lang.String         your pushover.net userid

```

