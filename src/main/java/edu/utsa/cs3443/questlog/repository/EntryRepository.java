// src/main/java/edu/utsa/cs3443/questlog/repository/EntryRepository.java
package edu.utsa.cs3443.questlog.repository;

import edu.utsa.cs3443.questlog.model.GameEntry;

import java.io.IOException;
import java.util.List;

public interface EntryRepository {
    List<GameEntry> loadAll() throws IOException;
    void saveAll(List<GameEntry> entries) throws IOException;
}
