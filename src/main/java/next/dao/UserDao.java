package next.dao;

import core.jdbc.DataAccessException;
import core.jdbc.JdbcTemplate;
import next.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;
    public UserDao(){
        this.jdbcTemplate = new JdbcTemplate();
    }

    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.insert(sql,
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail());
    }

    public void update(User user, String modifyUser) throws DataAccessException {
        String sql = "UPDATE USERS SET name = ?, email = ? WHERE userId = ?";
        jdbcTemplate.update(sql,
                ps -> {
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, modifyUser);
                });
    }

    public List<User> findAll() throws DataAccessException {
        String sql = "SELECT userId, password, name, email FROM USERS";

        return (List<User>) jdbcTemplate.queryForList(sql, rs -> {
            ArrayList<User> userArrayList = new ArrayList<>();
            while (rs.next()){
                User userCreate = new User(rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
                userArrayList.add(userCreate);
            }
            return userArrayList;
        });
    }

    public User findByUserId(String userId) throws DataAccessException{
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
        User paramMap = (User) jdbcTemplate.queryForObject(sql, rs -> {
            User userCreate = new User(rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"));

            return userCreate;
        }, userId);

        return paramMap;
    }

    public User findByUserIdSetter(String userId) throws DataAccessException{
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
        User paramMap = (User) jdbcTemplate.queryForObject(sql, rs -> {
            User userCreate = new User(rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"));

            return userCreate;
        }, ps -> {
            ps.setString(1, userId);
        });

        return paramMap;
    }
}
