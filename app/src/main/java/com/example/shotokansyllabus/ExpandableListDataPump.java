package com.example.shotokansyllabus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String> ninthKyu = new ArrayList<>();
        ninthKyu.add("Basics: Front kick, Down block, Upward block");
        ninthKyu.add("Kata: Taikyoku Shodan");

        List<String> eighthKyu = new ArrayList<>();
        eighthKyu.add("Basics: Side kick, Inside block, Outside block");
        eighthKyu.add("Kata: Heian Shodan");

        List<String> seventhKyu = new ArrayList<>();
        eighthKyu.add("Basics: Side kick, Inside block, Outside block");
        eighthKyu.add("Kata: Heian Nidan");

        List<String> sixthKyu = new ArrayList<>();
        eighthKyu.add("Basics: Side kick, Inside block, Outside block");
        eighthKyu.add("Kata: Heian Sandan");

        List<String> fifthKyu = new ArrayList<>();
        eighthKyu.add("Basics: Side kick, Inside block, Outside block");
        eighthKyu.add("Kata: Heian Yondan");

        // Add more grades...

        expandableListDetail.put("9th Kyu", ninthKyu);
        expandableListDetail.put("8th Kyu", eighthKyu);
        expandableListDetail.put("7th Kyu", seventhKyu);
        expandableListDetail.put("6th Kyu", sixthKyu);
        expandableListDetail.put("5th Kyu", fifthKyu);
        // Add more grades...

        return expandableListDetail;
    }
}
