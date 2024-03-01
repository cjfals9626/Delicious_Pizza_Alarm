package org.spring.pizzarazzi_alarm.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    public void saveEventCache(String emitterId, Object event) {
        eventCache.put(emitterId, event);
    }

    public Map<String, SseEmitter> findAllEmitters() {
        return new HashMap<>(emitters);
    }

    public Map<String, SseEmitter> findAllEmitterStartWithById(Long memberId) {
        return emitters.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    int index = key.indexOf("_");
                    if (index != -1) {
                        key = key.substring(0, index);
                    }
                    long id = Long.parseLong(key);
                    return id == memberId;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findAllEventCacheStartWithById(Long memberId) {
        return eventCache.entrySet().stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    int index = key.indexOf("_");
                    if (index != -1) {
                        key = key.substring(0, index);
                    }
                    long id = Long.parseLong(key);
                    return id == memberId;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }
}