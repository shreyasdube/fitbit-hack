package me.shreyas.hack.fitbit;

import java.util.Scanner;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * Run this class first to get your access token, which you can then use in
 * {@link Main.java}
 *
 */
public class RunFirst {

    public static void main(String[] args) {
        final OAuthService service = new ServiceBuilder()
                .provider(FitBitApi.class)
                .apiKey("072da8f40cc44adc96b9f5193eba0f3d")
                .apiSecret("95b29592f1994d15b15099ea29556aaa")
                .build();

        Scanner in = new Scanner(System.in);
        System.out.println("=== FitBit's OAuth Workflow ===");
        System.out.println();

        // Obtain the Request Token
        System.out.println("Fetching the Request Token...");
        final Token requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println();

        System.out.println("Now go and authorize Scribe here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        final Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        final Token accessToken = service.getAccessToken(requestToken, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if you're curious, it looks like this: " + accessToken + " )");
        System.out.println("Please use these values in Main.java");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        // hf6bj041apffduav7fqnjj4nk
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.fitbit.com/1/activities.json");
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getBody());
        System.out.println();
    }
}
