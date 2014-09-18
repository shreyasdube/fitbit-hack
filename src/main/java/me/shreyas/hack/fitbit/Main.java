/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.shreyas.hack.fitbit;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Run this class to insert running activity records to your account. A method
 * to quickly add an incredible amount of steps (think millions) without having
 * to involve any laws of physics!
 *
 * @author sdube
 */
public class Main {

    public static void main(String[] args) {
        OAuthService service = new ServiceBuilder()
                .provider(FitBitApi.class)
                .apiKey("072da8f40cc44adc96b9f5193eba0f3d")
                .apiSecret("95b29592f1994d15b15099ea29556aaa")
                .build();

        // this token is obtained by running RunFirst.java
        final Token t = new Token("17194bedb7843f7f287debc55baecf4b", "68e4736d0a7e8b5f99c477b1868bce94");
        // pick a date
        final String date = "2014-09-16";

        // iterate through hours .. note that you could run into rate limiting issues from 
        // fitbit if you run this frequently 
        for (int h = 0; h < 1; h++) {
            // insert data point every 5 minutes
            for (int m = 0; m < 56; m += 5) {
                log(service, t, h, m, date);
            }
        }
    }

    private static void log(OAuthService service, Token t,
            int hour, int min, String date) {
        System.out.println(date + " @ " + hour + ":" + min);
        OAuthRequest request = new OAuthRequest(Verb.POST, "http://api.fitbit.com/1/user/-/activities.json");
        // activityId for running
        request.addBodyParameter("activityId", "90009");
        request.addBodyParameter("startTime", String.format("%02d:%02d", hour, min));
        request.addBodyParameter("durationMillis", "299000");
        request.addBodyParameter("date", date);
        // i can't figure out the max value the API allows .. no documentation, 
        // so, just trial and error
        request.addBodyParameter("distance", "149.99");
        request.addBodyParameter("distanceUnit", "Mile");

        service.signRequest(t, request);
        Response response = request.send();
        System.out.println("Status: " + response.getCode());
        System.out.println();
        System.out.println(response.getBody());
        System.out.println();
    }
}
