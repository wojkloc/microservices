package pl.wojkloc.microservices.rest;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    private Map<Long, String> userRepository = new HashMap<>();

    @GetMapping("/{id}")
    public Map.Entry<Long, String> getUser(@RequestParam Long id) {
        return userRepository.entrySet().stream()
                .filter(e -> e.getKey() == id).findFirst()
                .orElseThrow(() -> new NoSuchElementException());
    }

    @PostMapping
    public Map.Entry<Long, String> addUser(@RequestBody String username) {
        Long maxIdValue = userRepository.keySet().stream().max(Long::compareTo).orElse(0L);
        userRepository.putIfAbsent(++maxIdValue, username);
        Long finalMaxIdValue = maxIdValue;
        return userRepository.entrySet().stream()
                .filter(e -> e.getKey() == finalMaxIdValue).findFirst()
                .orElseThrow(() -> new NoSuchElementException());
    }

    @GetMapping
    public Map<Long,String> getAllUsers() {
        return userRepository;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@RequestParam Long id) {
        userRepository.remove(id);
    }
}
