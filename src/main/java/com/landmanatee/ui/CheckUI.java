/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.landmanatee.ui;

import com.oracle.labs.mlrg.olcut.config.ConfigurationManager;
import com.oracle.labs.mlrg.olcut.config.Option;
import com.oracle.labs.mlrg.olcut.config.Options;
import net.pushover.client.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class CheckUI {
    protected String apiToken;
    protected String userId;

    public CheckUI(String apiToken, String userId) {
        this.apiToken = apiToken;
        this.userId = userId;
    }
    public String getGreeting() {
        return "Hello world.";
    }

    public boolean isInStock(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String stock = doc.select(".comProduct__title--wrapper .comProductTile__inStock, .comProduct__title--wrapper .comProductTile__soldOut").text();
        if (stock.equals("In Stock")) {
            // In stock!
            return true;
        }
        return false;
    }

    public void sendNotice(String url) throws PushoverException {
        PushoverClient client = new PushoverRestClient();

        Status result = client.pushMessage(PushoverMessage.builderWithApiToken(apiToken)
                .setUserId(userId)
                .setMessage("In Stock!")
                .setPriority(MessagePriority.HIGH) // HIGH|NORMAL|QUIET
                .setTitle("UniFi Store")
                .setUrl(url)
                .setTitleForURL("store.ui.com")
                //.setSound("magic")
                .build());
    }

    public static class CheckUIOpts implements Options {
        @Option(charName='p', longName="page",usage="the product page to check")
        public String productPage;

        @Option(charName='t', longName="token", usage="your pushover.net api token")
        public String apiToken;

        @Option(charName='u', longName="userid", usage="your pushover.net userid")
        public String userId;
    }

    public static void main(String[] args) throws Exception {
        CheckUIOpts opts = new CheckUIOpts();
        ConfigurationManager cm = new ConfigurationManager(args, opts, false);
        if (opts.apiToken == null) {
            System.err.println("Must specify your pushover api token with -t");
            System.err.println(cm.usage());
            return;
        }
        if (opts.userId == null) {
            System.err.println("Must specify your pushover userId with -u");
            System.err.println(cm.usage());
            return;
        }
        if (opts.productPage == null) {
            System.err.println("Must specify a product page url with -p");
            System.err.println(cm.usage());
            return;
        }
        CheckUI a = new CheckUI(opts.apiToken, opts.userId);
        if (a.isInStock(opts.productPage)) {
            a.sendNotice(opts.productPage);
        }
    }
}
