package eu.unicredit.dummy_java_app.controllers;

import eu.unicredit.dummy_java_app.BaseTest;
import eu.unicredit.dummy_java_app.pojos.User;
import eu.unicredit.dummy_java_app.repositories.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UsersControllerTest extends BaseTest {

	@MockBean
	private UserRepository userRepository;

	private static List<User> users;

	@BeforeClass
	public static void beforeClass() {
		users = Arrays.asList(
				new User(12L, "Mario", "Test"),
				new User(7L, "User", "2"));
	}

	@Test
	public void createEndpointShouldReturnTheUserInserted() throws Exception {
		when(userRepository.save(any(User.class))).thenReturn(users.get(0));

		mockMvc
				.perform(post("/users/create")
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(users.get(0))))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(12)))
				.andExpect(jsonPath("$.firstName", is(users.get(0).getFirstName())))
				.andExpect(jsonPath("$.lastName", is(users.get(0).getLastName())));
	}

	@Test
	public void readEndpointShouldReturnAListOfUsers() throws Exception {
		when(userRepository.findAll()).thenReturn(asList(
				users.get(0),
				users.get(1)));

		mockMvc
				.perform(get("/users/read"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].id", is(12)))
				.andExpect(jsonPath("$.[0].firstName", is("Mario")))
				.andExpect(jsonPath("$.[0].lastName", is("Test")))
				.andExpect(jsonPath("$.[1].id", is(7)))
				.andExpect(jsonPath("$.[1].firstName", is("User")))
				.andExpect(jsonPath("$.[1].lastName", is("2")));
	}

	@Test
	public void updateEndpointShouldReturnTheUserUpdated() throws Exception {
		when(userRepository.findOne(any(Long.class))).thenReturn(users.get(0));
		when(userRepository.save(any(User.class))).thenReturn(users.get(0));

		mockMvc
				.perform(post("/users/update/" + users.get(0).getId())
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(users.get(0))))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(12)))
				.andExpect(jsonPath("$.firstName", is(users.get(0).getFirstName())))
				.andExpect(jsonPath("$.lastName", is(users.get(0).getLastName())));
	}

	@Test
	public void updateEndpointShouldReturnNullIfNotFound() throws Exception {
		when(userRepository.findOne(any(Long.class))).thenReturn(null);

		mockMvc
				.perform(post("/users/update/" + users.get(0).getId())
						.contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(users.get(0))))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteEndpointShouldReturnJustOkCode() throws Exception {
		doNothing().when(userRepository).delete(any(Long.class));

		mockMvc
				.perform(post("/users/delete/" + users.get(0).getId()))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
