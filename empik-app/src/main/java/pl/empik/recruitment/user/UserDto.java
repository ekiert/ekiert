package pl.empik.recruitment.user;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class UserDto
{
    private String id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private Timestamp createdAt;
    private long calculations;

    public static UserDto buildFrom(GithubUserDto dto)
    {
        UserDto user = new UserDto();
        user.id = dto.id();
        user.login = dto.login();
        user.name = dto.name();
        user.type = dto.type();
        user.avatarUrl = dto.avatarUrl();
        user.createdAt = dto.createdAt();
        user.calculations = dto.followers() > 0 ? 6 / dto.followers() * (2 + dto.publicRepos()) : 0;
        return user;
    }
}