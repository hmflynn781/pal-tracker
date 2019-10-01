package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository {
    private int count;
    private Map<Long, TimeEntry> database;

    public InMemoryTimeEntryRepository() {
        database = new HashMap<>();
        count = 0;
    }

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(++count);
        database.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        return database.get(id);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(database.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(!database.containsKey(id)) { return null; }
        timeEntry.setId(id);
        database.put(id, timeEntry);
        return timeEntry;
    }

    public void delete(long id) {
        database.remove(id);
    }
}
