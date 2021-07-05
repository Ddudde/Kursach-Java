package ru.mirea.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersImpl {
    private UserRep userRep;

    @Autowired
    public UsersImpl(UserRep userRep) {
        this.userRep = userRep;
    }

    public void add(User user) {
        userRep.save(user);
    }

    public List<User> getAll() {
        return userRep.findAll();
    }

    public void delete(int id) {
        userRep.delete(userRep.getOne(id));
    }

    public User getuser(String log)
    {
        return userRep.findByUsername(log);
    }

    public boolean checkpar(String log, String par)
    {
        User user = userRep.findByUsername(log);
        if (user == null) return false;
        return user.conf_auth(par);
    }
}
