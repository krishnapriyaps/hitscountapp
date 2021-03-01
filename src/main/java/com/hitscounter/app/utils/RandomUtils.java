package com.hitscounter.app.utils;

import java.util.Arrays;
import java.util.List;

public class RandomUtils {

    private static String pickRandomItem(List<String> list) {
        int limit = list.size();
        int randomItem = (int)(Math.random()*((limit)));
        return list.get(randomItem);
    }

    public static String generateUserId() {
        final List<String> userIds = Arrays.asList(
            "b20524ae-befe-11e3-b335-425861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
            "c20524ae-befe-11e3-b335-425861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
            "d20524ae-befe-11e3-b335-425861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
            "f20524ae-befe-11e3-b335-425861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
            "g20524ae-befe-11e3-b335-425861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
            "g20524ae-befe-11e3-b335-425861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
            "g20524ae-befe-11e3-b335-425861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
            "g20524ae-befe-11e3-b335-305861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
            "g20524ae-befe-11e3-b335-625861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
            "g20524ae-befe-11e3-b335-725861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6"
        );

        return pickRandomItem(userIds);
    }

    public static String generateUrl() {
        final List<String> urls = Arrays.asList(
            "http://www.someamazingwebsite.com/county-2014/engine/match/69272 1.html?cluster=undefined;view=comparison;wrappertype=none#live",
            "https://www.google.com/",
            "https://www.example.org/birth/army?adjustment=bee&box=bike",
            "http://boot.example.com/bee/berry",
            "https://example.edu/",
            "https://www.example.org/bridge.html#attraction",
            "http://example.com/",
            "https://www.example.net/"
        );

        return pickRandomItem(urls);
    }

    public static String generateType() {
        final List<String> types = Arrays.asList(
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "PATCH"
        );

        return pickRandomItem(types);
    }
}
