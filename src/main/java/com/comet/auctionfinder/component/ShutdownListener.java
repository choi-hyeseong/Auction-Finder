package com.comet.auctionfinder.component;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private AuctionParser parser;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        parser.quitSelenium();
    }
}
