package pl.empik.recruitment.user;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUserDto(String id, String login, String name, String type,
                            @JsonProperty("avatar_url") String avatarUrl,
                            @JsonProperty("created_at") Timestamp createdAt,
                            @JsonProperty("public_repos") int publicRepos,
                            long followers)
{}