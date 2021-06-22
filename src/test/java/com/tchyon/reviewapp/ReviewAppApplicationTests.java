package com.tchyon.reviewapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tchyon.reviewapp.model.User;
import com.tchyon.reviewapp.model.request.PlatformDto;
import com.tchyon.reviewapp.model.request.ReviewReq;
import com.tchyon.reviewapp.model.request.UserAndReviewReq;
import com.tchyon.reviewapp.model.request.VerticalDto;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void add4Platforms() throws Exception {
		List<PlatformDto> platformDtos = Arrays.asList(
				new PlatformDto("Wint Wealth", "Wint Wealth Desciption", null, null, new VerticalDto(null, "Alternative Investment", "Alternative investment description", null)),
				new PlatformDto("BuyUcoin", "BuyUcoin Desciption", null, null, new VerticalDto(null, "Cryptocurreny", "Cryptocurreny description", null)),
				new PlatformDto("5paisa", "5paisa Desciption", null, null, new VerticalDto(null, "Equity", "Equity description", null)),
				new PlatformDto("Grip", "Grip Desciption", null, false, new VerticalDto(null, "Alternative Investment", "Alternative investment description", null)),
				new PlatformDto("Zerodha", "Zerodha Desciption", null, null, new VerticalDto(null, "Equity", "Equity description", null))
		);

		for (PlatformDto platformDto : platformDtos) {
			String platformDtoJson=new ObjectMapper().writeValueAsString(platformDto);
			ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/platform")
					.content(platformDtoJson)
					.contentType("application/json"))
					.andExpect(status().isOk());
			System.out.println(platformDto.getName() + " Platform Created Successfully with Load: " + platformDtoJson);
		}
	}

	@Test
	@Order(2)
	public void createUser() throws Exception {
		List<User> usersTobeCreated = Arrays.asList(
				new User("adam","Adam Eve","9987654786", "adam@gow.com"),
				new User("patrick","Patrick Star","9876453422", "patrick@lio.com"),
				new User("trump","Trump Donald","9999999999", "trump@gmail.com"),
				new User("salman","Salman Khan","9898989898", "salman@outlook.com")
		);

		for ( User user : usersTobeCreated){
			String userJson=new ObjectMapper().writeValueAsString(user);
			ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
					.content(userJson)
					.contentType("application/json"))
					.andExpect(status().isOk());
			System.out.println(user.getName() + " User Created Successfully with Load: " + userJson);
		}
	}

	@Test
	@Order(3)
	public void postSuccessfulReviews() throws Exception{
		List<UserAndReviewReq> reviewReqsToBeSuccess = Arrays.asList(
				new UserAndReviewReq("adam", null, null, "5paisa", 2, "good discount broker in india"),
				new UserAndReviewReq("adam", null, null, "Wint Wealth", 3, "very attractive schemes"),
				new UserAndReviewReq("salman", null, null, "5paisa", 5, "marvellous"),
				new UserAndReviewReq("trump", null, null, "5paisa", 4, "good"),
				new UserAndReviewReq("trump", null, null, "BuyUcoin", 1, "worst"),
				new UserAndReviewReq("adam", null, null, "Zerodha", 5, "no 1 discount broker in india")
		);

		for (UserAndReviewReq userAndReviewReq : reviewReqsToBeSuccess){
			String userAndReviewJson=new ObjectMapper().writeValueAsString(userAndReviewReq);
			ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/review")
					.content(userAndReviewJson)
					.contentType("application/json"))
					.andExpect(status().isOk());
			System.out.println(userAndReviewReq.getUserName() + "'s review posted successfully with load: " + userAndReviewJson);
		}
	}

	@Test
	@Order(4)
	public void reviewNotAllowedForUnReleasedPlatforms() throws Exception{
		UserAndReviewReq userAndReviewReq = new UserAndReviewReq("trump", null, null, "Grip", 5, "new one");
		String userAndReviewJson=new ObjectMapper().writeValueAsString(userAndReviewReq);
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/review")
					.content(userAndReviewJson)
					.contentType("application/json"))
					.andExpect(status().isInternalServerError());
		System.out.println(userAndReviewReq.getUserName() + "'s review failed as unreleased platform review is not allowed.");
	}

	@Test
	@Order(5)
	public void multipleReviewNotAllowedCase() throws Exception{
		UserAndReviewReq userAndReviewReq = new UserAndReviewReq("adam", null, null, "5paisa", 4, "good discount broker in india");

		String userAndReviewJson=new ObjectMapper().writeValueAsString(userAndReviewReq);
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/review")
					.content(userAndReviewJson)
					.contentType("application/json"))
					.andExpect(status().isInternalServerError());
		System.out.println(userAndReviewReq.getUserName() + "'s review failed as a user can't rate same platform multiple times");
	}
}
