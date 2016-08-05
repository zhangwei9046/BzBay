package com.bravozulu.db;

import com.bravozulu.core.User;
import com.google.common.base.Preconditions;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {
    public UserDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Optional<User> findByUsername(String username) {
        Query query = Preconditions.checkNotNull(namedQuery("com.bravozulu.core.User.findByUsername"));
        query.setParameter("username", username);
        List<User> users = list(query);
        return users.size() == 0 ? Optional.empty() : Optional.of(users.get(0));
    }

    public User create(User user) { return persist(user); }

    public List<User> findAll() {
        return list(namedQuery("com.bravozulu.core.User.findAll"));
    }

    public void delete(Long userId) {
        User userObj = findById(userId).orElseThrow(() -> new NotFoundException("No such user."));
        currentSession().delete(userObj);
    }

    public User update(User user) {
        return persist(user);
    }
}
