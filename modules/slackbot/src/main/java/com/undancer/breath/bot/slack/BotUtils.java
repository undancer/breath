package com.undancer.breath.bot.slack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.client.util.HttpAsyncClientUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by undancer on 14-5-14.
 */
public class BotUtils {
    private static final AtomicInteger count = new AtomicInteger();
    private static String url;
    private static CloseableHttpAsyncClient client;
    private static ObjectMapper objectMapper = new ObjectMapper();

    private BotUtils() {
    }

    private static void initClient() {
        if (client == null) {
            client = HttpAsyncClientBuilder.create().build();
        }
        if (!client.isRunning()) {
            client.start();
        }
    }

    public static void setUrl(String url) {
        BotUtils.url = url;
    }

    public static void say(String channel, String icon, String username, Throwable throwable) {
        say(channel, icon, username, throwableToString(throwable));
    }


    public static void say(String channel, String icon, String username, String text) {
        say(new Message(channel, icon, username, text));
    }

    public static void say(Message message) {
        try {
            HttpPost request = new HttpPost(url);
            List<NameValuePair> nameValuePairs = Arrays.<NameValuePair>asList(
                    new BasicNameValuePair("payload", objectMapper.writeValueAsString(message))
            );
            HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, Charset.defaultCharset());
            request.setEntity(entity);
            say(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public static void say(HttpUriRequest request) {
        initClient();
        Future<HttpResponse> future = client.execute(request, new InternalFutureCallback());
        if (future != null) {
            count.getAndIncrement();
        }
    }

    public static void close() {
        if (client != null) {
            if (client.isRunning()) {
                while (true) {
                    if (count.get() == 0) {
                        HttpAsyncClientUtils.closeQuietly(client);
                        break;
                    }
                }
            }
        }
    }

    public static String throwableToString(Throwable throwable) {
        try (StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new PrintWriter(stringWriter)) {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class InternalFutureCallback implements FutureCallback<HttpResponse> {

        @Override
        public void completed(HttpResponse result) {
            if (count.getAndDecrement() == 0) {
                BotUtils.close();
            }
        }

        @Override
        public void failed(Exception ex) {
            if (count.getAndDecrement() == 0) {
                BotUtils.close();
            }
        }

        @Override
        public void cancelled() {
            if (count.getAndDecrement() == 0) {
                BotUtils.close();
            }
        }
    }
}
