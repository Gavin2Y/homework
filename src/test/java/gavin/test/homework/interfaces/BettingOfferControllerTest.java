package gavin.test.homework.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BettingOfferControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sameUserBuyOnce_returnOne() throws Exception {
        String userId = "123";
        String offerId = "offer1";

        // 发起GET请求并验证响应
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId)) // 动态替换路径变量
                .andExpect(status().isOk());// 验证HTTP状态码为200
        String sessionId = result.andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, sessionId).contentType(MediaType.TEXT_PLAIN).content("5")) // 动态替换路径变量
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/{offerId}/highstakes", offerId))
                .andExpect(status().isOk())
                .andExpect(content().string("123=5"));
    }

    @Test
    public void sameUserBuyMultiTimes_returnTheLastOne() throws Exception {
        String userId = "123";
        String offerId = "offer1";

        // 发起GET请求并验证响应
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId)) // 动态替换路径变量
                .andExpect(status().isOk());// 验证HTTP状态码为200
        String sessionId = result.andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, sessionId).contentType(MediaType.TEXT_PLAIN).content("5")) // 动态替换路径变量
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, sessionId).contentType(MediaType.TEXT_PLAIN).content("6")) // 动态替换路径变量
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, sessionId).contentType(MediaType.TEXT_PLAIN).content("100")) // 动态替换路径变量
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, sessionId).contentType(MediaType.TEXT_PLAIN).content("50")) // 动态替换路径变量
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders.get("/{offerId}/highstakes", offerId))
                .andExpect(status().isOk())
                .andExpect(content().string("123=50"));
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void multiUserBuyMultiTimes_returnTop3() throws Exception {
        String userId = "user1";
        String userId2 = "user2";
        String userId3 = "user3";
        String userId4 = "user4";
        String userId5 = "user5";
        String offerId = "offer1";

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId)) // 动态替换路径变量
                .andExpect(status().isOk());// 验证HTTP状态码为200
        String sessionId = result.andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, sessionId).contentType(MediaType.TEXT_PLAIN).content("5")) // 动态替换路径变量
                .andExpect(status().isOk());

        String session2 = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId2)) // 动态替换路径变量
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();// 验证HTTP状态码为200

        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, session2).contentType(MediaType.TEXT_PLAIN).content("7")) // 动态替换路径变量
                .andExpect(status().isOk());

        String session3 = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId3)) // 动态替换路径变量
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();// 验证HTTP状态码为200

        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, session3).contentType(MediaType.TEXT_PLAIN).content("1")) // 动态替换路径变量
                .andExpect(status().isOk());

        String session4 = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId4)) // 动态替换路径变量
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();// 验证HTTP状态码为200

        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, session4).contentType(MediaType.TEXT_PLAIN).content("3")) // 动态替换路径变量
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders.get("/{offerId}/highstakes", offerId))
                .andExpect(status().isOk())
                .andExpect(content().string("user2=7,user1=5,user4=3"));


        String session5 = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId5)) // 动态替换路径变量
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();// 验证HTTP状态码为200

        mockMvc.perform(MockMvcRequestBuilders.post("/{offerId}/stake?sessionkey={sessionId}", offerId, session5).contentType(MediaType.TEXT_PLAIN).content("100")) // 动态替换路径变量
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders.get("/{offerId}/highstakes", offerId))
                .andExpect(status().isOk())
                .andExpect(content().string("user5=100,user2=7,user1=5"));
    }

}