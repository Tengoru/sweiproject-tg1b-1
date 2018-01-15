package base.activitymeter;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import base.acitvitymeter.Activity;
import org.json.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;


public class MailControllerTest {

    String testActivity1 = "{\"text\":\"text1\",\"tags\":\"tag1\",\"title\":\"title1\",\"date\":\"date1\",\"verificationCode\":\"MaxiIstToll\"}";
    String testActivity2 = "{\"text\":\"text2\",\"tags\":\"tag2\",\"title\":\"title2\",\"date\":\"date2\",\"verificationCode\":\"MaxiIstToll\"}";
    String testActivity3 = "{\"text\":\"text3\",\"tags\":\"tag3\",\"title\":\"title3\",\"date\":\"date3\",\"verificationCode\":\"MaxiIstToll\"}";
    String testActivity4 = "{\"text\":\"text4\",\"tags\":\"tag1\",\"title\":\"title4\",\"date\":\"date4\",\"verificationCode\":\"MaxiIstToll\"}";
    String testActivity5 = "{\"text\":\"text1\",\"tags\":\"tag1\",\"title\":\"title1\",\"date\":\"date1\",\"verificationCode\":\"MaxiIstToll\"}";
    //String testActivity6 = "{\"text\":\"text5\",\"tags\":\"tag1\",\"title\":\"title5\",\"date\":\"date5\"}";
    private String testTitle = "activity";
    private String testTags = "YIPPIE";
    private String testText = "Max was here!";
    private String testDate = "heute";
    private String idTemplate ="{\"id\":";
    private String verificationCode ="MaxiIstToll";

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void editActivity() throws Exception {
        MvcResult response1 = this.mockMvc.perform(post("/activity")
                .content(testActivity1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        MvcResult updatedResponse = this.mockMvc.perform(put("/activity")
                .content(testActivity2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        String content1 =updatedResponse.getResponse().getContentAsString();
        JSONObject obj1 = new JSONObject(content1);
        int id1 = obj1.getInt("id");



        mockMvc.perform(delete("/activity/" + id1)).andExpect(status().isOk());
    }

}
