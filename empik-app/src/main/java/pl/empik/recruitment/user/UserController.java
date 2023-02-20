package pl.empik.recruitment.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import pl.empik.recruitment.exception.ApplicationTechnicalException;
import pl.empik.recruitment.exception.IncorrectDataException;

@RestController
public class UserController
{
    private final RestTemplate restTemplate;
    private final UserRequestRepository userRequestRepository;

    @Autowired
    public UserController(RestTemplate restTemplate, UserRequestRepository userRequestRepository)
    {
        this.restTemplate = restTemplate;
        this.userRequestRepository = userRequestRepository;
    }

    @GetMapping("/users/{login}")
    UserDto getUser(@PathVariable String login)
    {
        try
        {
            GithubUserDto githubUser = restTemplate.getForObject("https://api.github.com/users/{username}", GithubUserDto.class, login);
            userRequestRepository.incrementRequestCount(login);
            return UserDto.buildFrom(githubUser);
        }
        catch (HttpClientErrorException ex)
        {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND)
            {
                throw new IncorrectDataException("User %s not found".formatted(login));
            }
            throw new ApplicationTechnicalException("Unable to request user information for " + login, ex);
        }
    }
}