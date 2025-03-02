package gavin.test.homework.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sameUserLogin_withinTTL_shouldReturnSame() throws Exception {
        String userId = "123";

        // 发起GET请求并验证响应
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId)) // 动态替换路径变量
                .andExpect(status().isOk());// 验证HTTP状态码为200
        String firstRes = result.andReturn().getResponse().getContentAsString();


        String secondRes = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId)) // 动态替换路径变量
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //5s内应该相等

        assertEquals(firstRes, secondRes);
    }

    @Test
    public void diffUserLogin_withinTTL_shouldReturnDiff() throws Exception {
        String userId1 = "123";
        String userId2 = "1234";

        // 发起GET请求并验证响应
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId1)) // 动态替换路径变量
                .andExpect(status().isOk());// 验证HTTP状态码为200
        String firstRes = result.andReturn().getResponse().getContentAsString();


        String secondRes = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId2)) // 动态替换路径变量
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotEquals(firstRes, secondRes);
    }

    @Test
    public void sameUserLogin_exceedTTL_shouldReturnDiff() throws Exception {
        String userId = "123";

        // 发起GET请求并验证响应
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId)) // 动态替换路径变量
                .andExpect(status().isOk());// 验证HTTP状态码为200
        String firstRes = result.andReturn().getResponse().getContentAsString();

        Thread.sleep(6 * 1000);

        String secondRes = mockMvc.perform(MockMvcRequestBuilders.get("/{userId}/session", userId)) // 动态替换路径变量
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //5s内应该相等

        assertNotEquals(firstRes, secondRes);
    }

}