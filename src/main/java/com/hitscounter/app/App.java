package com.hitscounter.app;

import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hitscounter.app.config.Configurations;
import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.service.HitsServices;
import com.hitscounter.app.service.impl.HitsServiceImpl;
import com.hitscounter.app.service.impl.RedisCacheServiceImpl;

public class App {
    public static void main(String[] args) {
        String hostName = args[0];
        Configurations.init(hostName);

        Gson gson = new Gson();
        HitsServices services =
            new HitsServiceImpl(new RedisCacheServiceImpl(Configurations.get()));

        // Using Scanner for Getting Input from User
        Scanner in = new Scanner(System.in);
        while(true) {
            String json = in.nextLine();
            try {
                Hit hit = gson.fromJson(json, Hit.class);
                services.addNewHit(hit);
                System.out.println("");
            } catch (JsonSyntaxException jsonSyntaxException) {
                System.out.println("Invalid JSON syntax");
            }
        }
    }
}
