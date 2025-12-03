package edu.utsa.cs3443.questlog.repository;

import edu.utsa.cs3443.questlog.model.User;

import java.io.IOException;
import java.util.List;

public interface UserRepository {

    List<User> loadAll() throws IOException;
    void saveAll(List<User> users) throws IOException;

}
