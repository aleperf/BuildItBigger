package com.example.aleperf.joketeller;

import java.util.Random;

public class JokeWiz {
    private final static String[] jokes = {
            "My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.",
            "John : \"I used to think correlation implied causation. Then I took a statistics class and " +
                    "now I don\'t\" Jane: \"Sounds like the class helped.\" John: \"Well, maybe.\"",
            "Q: Why do mathematicians like parks? A: Because of all the natural logs.",
            "Q: Did you hear the one about the statistician? A: Probably.",
            "Old mathematicians never die; they just lose some of their functions.",
            "What is a programmer's favourite hangout place? Foo Bar.",
            "A foo walks into a bar, takes a look around and says \"Hello, World!\"",
            "A programmer had a problem. He decided to use java. He now has a ProblemFactory.",
            "Dear algebra, please stop asking us to find your X. It is never coming back and don't ask Y.",
            "An opinion without 3.14 is just an onion.",
            "You have to be odd to be the number one.",
            "Q: What do you get if you divide the circumference of a jack-o-lantern by its diameter? A: Pumpkin pi.",
            "I put my root beer in a square glass. Now it’s just beer.",
            "Three database admins walked into a bar. A little while later they walked away because they" +
                    " couldn't find a table.",
            "An Android app walks into a bar. Bartender asks, \"Can I get you a drink?\" The app " +
                    "says: \"That's exactly my intent.\"",
            "A fragment walks into a bar, and the bartender asks for an ID. Fragment says, \"I don't " +
                    "have an ID.\" So the bartender says, \"Okay, I'll make a NullPointerException.\"",
            "Q:\"Dad what are clouds made of?\" A: \"Linux servers, mostly\".",
            "There's no place like 127.0.0.1",
            "Are monsters good at math? No, unless you Count Dracula.",
            "Never criticize someone until you've walked a mile in their shoes. " +
                    "That way, when you criticize them, they won't be able to hear you from that far away." +
                    " Plus, you'll have their shoes."


    };


    public String getJoke() {
        Random random = new Random();
        int index = random.nextInt(jokes.length);
        return jokes[index];
    }

}
