package pl.empik.recruitment.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRequestRepository
{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRequestRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void incrementRequestCount(String login)
    {
        String sql = """
                        MERGE INTO USER_REQUEST_COUNT using dual ON (LOGIN = ?)
                        WHEN MATCHED THEN UPDATE SET REQUEST_COUNT = REQUEST_COUNT+1
                        WHEN NOT MATCHED THEN INSERT VALUES (?,1)
                        """;
        jdbcTemplate.update(sql, login, login);
    }

    public int getRequestCount(String login)
    {
        try
        {
            return jdbcTemplate.queryForObject("select REQUEST_COUNT from USER_REQUEST_COUNT where LOGIN=?", Integer.class, login);
        }
        catch (EmptyResultDataAccessException ex)
        {
            return 0;
        }
    }
}