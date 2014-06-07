package com.undancer.breath.bot.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by undancer on 14-5-14.
 */
public class Message {

    private String channel;
    @JsonProperty("icon_emoji")
    private String icon;
    private String username;
    private String text;

    public Message() {
    }

    public Message(String channel, String icon, String username, String text) {
        this.channel = channel;
        this.icon = icon;
        this.username = username;
        this.text = text;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
