// src/main/java/edu/utsa/cs3443/questlog/service/EntryService.java
package edu.utsa.cs3443.questlog.service;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.repository.CsvEntryRepository;
import edu.utsa.cs3443.questlog.repository.EntryRepository;
import edu.utsa.cs3443.questlog.util.DataPaths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EntryService {

    private static final EntryService INSTANCE =
            new EntryService(new CsvEntryRepository(DataPaths.ENTRIES_PATH));

    private final EntryRepository repository;
    private final ObservableList<GameEntry> entries = FXCollections.observableArrayList();

    private EntryService(EntryRepository repository) {
        this.repository = repository;
        try {
            List<GameEntry> loaded = repository.loadAll();
            entries.addAll(loaded);
        } catch (IOException e) {
            e.printStackTrace();
            // you could also show an Alert here
        }
    }

    public static EntryService getInstance() {
        return INSTANCE;
    }

    public ObservableList<GameEntry> getAllEntries() {
        return entries;
    }

    public Optional<GameEntry> findById(String id) {
        return entries.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public void save(GameEntry entry) {
        if (entry.getId() == null || entry.getId().isEmpty()) {
            entry.setId(UUID.randomUUID().toString());
            entries.add(entry);
        }
        persist();
    }

    public void delete(GameEntry entry) {
        entries.remove(entry);
        persist();
    }

    private void persist() {
        try {
            repository.saveAll(entries);
        } catch (IOException e) {
            e.printStackTrace();
            // optionally show an error dialog
        }
    }
}
