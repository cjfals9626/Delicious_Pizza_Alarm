package org.spring.pizzarazzi_alarm.controller;

import lombok.RequiredArgsConstructor;
import org.spring.pizzarazzi_alarm.service.notification.EmitterService;
import org.spring.pizzarazzi_alarm.util.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.spring.pizzarazzi_alarm.util.prefix.ConstPrefix.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/notification")
public class NotificationController {

    private final TokenProvider tokenProvider;
    private final EmitterService emitterService;
    public static final Long DEFAULT_TIMEOUT = 3600L * 1000;

    @GetMapping(value = "/connect", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SseEmitter> subscribe(@RequestHeader(value = AUTHORIZATION) String accessToken,
                                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        Long memberId = Long.valueOf(tokenProvider.getId(tokenProvider.resolveToken(accessToken)));
        return ResponseEntity.ok(emitterService.addEmitter(memberId, lastEventId));
    }
}
