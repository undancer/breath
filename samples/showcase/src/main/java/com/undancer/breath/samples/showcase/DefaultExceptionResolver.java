package com.undancer.breath.samples.showcase;

import com.undancer.breath.bot.slack.BotUtils;
import com.undancer.breath.core.ExceptionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by undancer on 14-5-22.
 */
@Named
public class DefaultExceptionResolver extends ExceptionResolver {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionResolver.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception throwable) {

        ModelAndView mav = super.resolveException(request, response, handler, throwable);

        if (mav == null) {
            try {
                BotUtils.setUrl("https://boxfish.slack.com/services/hooks/incoming-webhook?token=gWL5P45NRwVXyrozkosr2urj");
                String username = String.format("%s@%s", "粗线鸟", Inet4Address.getLocalHost().getHostAddress());
                BotUtils.say("#bebase", ":ghost:", username, throwable);
            } catch (UnknownHostException e) {

            } finally {
                throwable.printStackTrace();
            }
        }
        return null;
    }
}