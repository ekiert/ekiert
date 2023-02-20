package pl.empik.recruitment.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import pl.empik.recruitment.exception.IncorrectDataException;
import pl.empik.recruitment.user.GithubUserDto;
import pl.empik.recruitment.user.UserController;
import pl.empik.recruitment.user.UserDto;
import pl.empik.recruitment.user.UserRequestRepository;

@SpringBootTest
class UserControllerTest
{
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    @InjectMocks
    private UserController userController;

    @Autowired
    private UserRequestRepository userRequestRepository;

    @Test
    void testGetUserForCorrectUsername()
    {
        //given
        String login = "octocat";
        GithubUserDto githubUserDto = new GithubUserDto("id1", "", "", "", "", null, 1, 2);
        when(restTemplate.getForObject(any(), any(), eq(login))).thenReturn(githubUserDto);
        int requestCount = userRequestRepository.getRequestCount(login);
        //when
        UserDto user = userController.getUser(login);
        //then
        assertEquals(githubUserDto.id(), user.getId());
        assertEquals(githubUserDto.name(), user.getName());
        assertEquals(9L, user.getCalculations());
        assertEquals(requestCount + 1, userRequestRepository.getRequestCount(login));
    }

    @Test
    void testGetUserForWrongUsername()
    {
        when(restTemplate.getForObject(any(), any(), eq("octocat123"))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        assertThrows(IncorrectDataException.class, () -> userController.getUser("octocat123"));
    }
}