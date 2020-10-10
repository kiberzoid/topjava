package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    AtomicInteger count = new AtomicInteger(1);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return users.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(count.getAndIncrement());
            users.put(user.getId(), user);
            return user;
        }
        return users.computeIfPresent(user.getId(),(id,oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> userList = new ArrayList<>(users.values());
        userList.sort((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()));
        return userList;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return users.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
