package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimeEntryRepository {
    InMemoryTimeEntryRepository inMemoryRepository;

    public TimeEntryRepository() {
        this.inMemoryRepository = new InMemoryTimeEntryRepository();
    }

    public TimeEntry create(TimeEntry timeEntry) {
        return this.inMemoryRepository.create(timeEntry);
    }

    public TimeEntry find(long timeEntryId) {
        return this.inMemoryRepository.find(timeEntryId);
    }

    public List<TimeEntry> list() {
        return this.inMemoryRepository.list();
    }

    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
        return this.inMemoryRepository.update(timeEntryId, timeEntry);
    }

    public void delete(long timeEntryId) {
        this.inMemoryRepository.delete(timeEntryId);
    }
}
