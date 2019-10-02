package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        final String sqlInsertStatement = "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.update( connection -> {
                        PreparedStatement ps = connection.prepareStatement(sqlInsertStatement, Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, timeEntry.getProjectId());
                        ps.setLong(2, timeEntry.getUserId());
                        ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                        ps.setInt(4, timeEntry.getHours());
                        return ps;
                    },
                    keyHolder
            );

            timeEntry.setId(keyHolder.getKey().longValue());
            return timeEntry;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        String selectSqlStatement = "SELECT * FROM time_entries WHERE id = ?";
        Object[] args = new Object[] {timeEntryId};
        try {
            return jdbcTemplate.queryForObject(selectSqlStatement, args, new TimeEntryRowMapper());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TimeEntry> list() {
        String selectSqlStatement = "SELECT * FROM time_entries";
        try {
            return jdbcTemplate.query(selectSqlStatement, new TimeEntryRowMapper());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
        String updateSqlStatement = "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours = ? WHERE id = ?";
        try {
            jdbcTemplate.update(updateSqlStatement, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours(), timeEntryId);
            timeEntry.setId(timeEntryId);
            return timeEntry;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(long timeEntryId) {
        String deleteSqlStatement = "DELETE FROM time_entries WHERE id = ?";
        try {
            jdbcTemplate.update(deleteSqlStatement, timeEntryId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
