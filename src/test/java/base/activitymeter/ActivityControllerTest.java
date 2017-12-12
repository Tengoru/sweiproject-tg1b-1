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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {

    //String jsonActivity1 = "{\"title\":\"Test\", \"start\":\"12.12.2012\", \"end\":\"14.12.2012\", \"category\":\"cat\", \"department\":\"Fakultät für Design\" ,\"tags\":\"tag\", \"text\":\"Ich bin ein Test\"}";

    String testActivity1 = "{\"text\":\"text1\",\"tags\":\"tag1\",\"title\":\"title1\",\"date\":\"date1\"}";
    String testActivity2 = "{\"text\":\"text2\",\"tags\":\"tag2\",\"title\":\"title2\",\"date\":\"date2\"}";
    String testActivity3 = "{\"text\":\"text3\",\"tags\":\"tag3\",\"title\":\"title3\",\"date\":\"date3\"}";
    String testActivity4 = "{\"text\":\"text4\",\"tags\":\"tag1\",\"title\":\"title4\",\"date\":\"date4\"}";
    String testActivity5 = "{\"text\":\"text1\",\"tags\":\"tag1\",\"title\":\"title1\",\"date\":\"date1\"}";
    String testActivity6 = "{\"text\":\"text5\",\"tags\":\"tag1\",\"title\":\"title5\",\"date\":\"date5\"}";
    String testTitle = "activity";
    String testTags = "YIPPIE";
    String testText = "Max was here!";
    String testDate = "heute";
    String idTemplate ="{\"id\":";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSetDate() throws Exception {

        Activity SUT = new Activity(testTitle,testText,testTags,testDate);

        String want = "morgen";
        SUT.setDate(want);
        String have = SUT.getDate();
        assertEquals(want, have);
    }

    @Test
    public void testSetTitle() throws Exception {

        Activity SUT = new Activity(testTitle,testText,testTags,testDate);

        String want = "Aktivity2";
        SUT.setTitle(want);
        String have = SUT.getTitle();
        assertEquals(want, have);
    }

    @Test
    public void testSetText() throws Exception {

        Activity SUT = new Activity(testTitle,testText,testTags,testDate);

        String want = "Caro was here";
        SUT.setText(want);
        String have = SUT.getText();
        assertEquals(want, have);
    }

    @Test
    public void testSetTag() throws Exception {

        Activity SUT = new Activity(testTitle,testText,testTags,testDate);

        String want = "Juhuuuuu";
        SUT.setTags(want);
        String have = SUT.getTags();
        assertEquals(want, have);
    }

    @Test
    public void testSetTagTwice() throws Exception {

        Activity SUT = new Activity(testTitle,testText,testTags,testDate);

        String want = "Juhuuuuu";
        String inBetween = "OhNein";
        SUT.setTags(inBetween);
        SUT.setTags(want);
        String have = SUT.getTags();
        assertEquals(want, have);
    }

    @Test
    public void statusOfServer() throws Exception {
        this.mockMvc.perform(get("/activity")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void noEntry() throws Exception {
        this.mockMvc.perform(get("/activity")).andExpect(status().isOk()).andExpect(content().string("[]"));
    }

    @Test
    public void filterEmptyList() throws Exception{
        this.mockMvc.perform(get("/activity/filter/tag")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void notEmpty() throws Exception {

        MvcResult response = this.mockMvc.perform(post("/activity")
                .content(testActivity1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        String content = response.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(content);
        int id1 = obj.getInt("id");


        String result1 = testActivity1.substring(1);

        this.mockMvc.perform(get("/activity")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("["+idTemplate+id1+","+result1+"]"));


        mockMvc.perform(delete("/activity/" + id1));

    }

    @Test
    public void postManyTest() throws Exception {
        MvcResult response1 = this.mockMvc.perform(post("/activity")
                .content(testActivity1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
        MvcResult response2 = this.mockMvc.perform(post("/activity")
                .content(testActivity2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        String content1 = response1.getResponse().getContentAsString();
        JSONObject obj1 = new JSONObject(content1);
        int id1 = obj1.getInt("id");
        String content2 = response2.getResponse().getContentAsString();
        JSONObject obj2 = new JSONObject(content2);
        int id2 = obj2.getInt("id");


        mockMvc.perform(delete("/activity/" + id1)).andExpect(status().isOk());
        mockMvc.perform(delete("/activity/" + id2)).andExpect(status().isOk());
    }


    @Test
    public void askedForTagNotInTheList() throws Exception {
        MvcResult response1 = this.mockMvc.perform(post("/activity")
                .content(testActivity1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        String content1 = response1.getResponse().getContentAsString();
        JSONObject obj1 = new JSONObject(content1);
        int id1 = obj1.getInt("id");

        this.mockMvc.perform(get("/activity/filter/nottest1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));


        mockMvc.perform(delete("/activity/" + id1)).andExpect(status().isOk());
    }

    @Test
    public void filterTagWhichIsOnceInTheListTest() throws Exception {
        MvcResult response1 = this.mockMvc.perform(post("/activity")
                .content(testActivity1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
        MvcResult response2 = this.mockMvc.perform(post("/activity")
                .content(testActivity2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
        MvcResult response3 = this.mockMvc.perform(post("/activity")
                .content(testActivity3)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        String content1 = response1.getResponse().getContentAsString();
        JSONObject obj1 = new JSONObject(content1);
        int id1 = obj1.getInt("id");
        String content2 = response2.getResponse().getContentAsString();
        JSONObject obj2 = new JSONObject(content2);
        int id2 = obj2.getInt("id");
        String content3 = response3.getResponse().getContentAsString();
        JSONObject obj3 = new JSONObject(content3);
        int id3 = obj3.getInt("id");


        String result1 = testActivity1.substring(1);

        this.mockMvc.perform(get("/activity/filter/tag1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("["+idTemplate+id1+","+result1+"]"));


        mockMvc.perform(delete("/activity/" + id1)).andExpect(status().isOk());
        mockMvc.perform(delete("/activity/" + id2)).andExpect(status().isOk());
        mockMvc.perform(delete("/activity/" + id3)).andExpect(status().isOk());

    }


    @Test
    public void filterTagWhichIsSeveralTimesInTheListTest() throws Exception {

        MvcResult response1 = this.mockMvc.perform(post("/activity")
                .content(testActivity1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
        MvcResult response2 = this.mockMvc.perform(post("/activity")
                .content(testActivity2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
        MvcResult response3 = this.mockMvc.perform(post("/activity")
                .content(testActivity3)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
        MvcResult response4 = this.mockMvc.perform(post("/activity")
                .content(testActivity4)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();
        MvcResult response5 = this.mockMvc.perform(post("/activity")
                .content(testActivity5)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        String content1 = response1.getResponse().getContentAsString();
        JSONObject obj1 = new JSONObject(content1);
        int id1 = obj1.getInt("id");
        String content2 = response2.getResponse().getContentAsString();
        JSONObject obj2 = new JSONObject(content2);
        int id2 = obj2.getInt("id");
        String content3 = response3.getResponse().getContentAsString();
        JSONObject obj3 = new JSONObject(content3);
        int id3 = obj3.getInt("id");
        String content4 = response4.getResponse().getContentAsString();
        JSONObject obj4 = new JSONObject(content4);
        int id4 = obj4.getInt("id");
        String content5 = response5.getResponse().getContentAsString();
        JSONObject obj5 = new JSONObject(content5);
        int id5 = obj5.getInt("id");



        String result1 = testActivity1.substring(1);
        String result4 = testActivity4.substring(1);
        String result5 = testActivity5.substring(1);


        this.mockMvc.perform(get("/activity/filter/tag1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content()
            .string("["+idTemplate+id1+","+result1+","+idTemplate+id4+","+result4+","+idTemplate+id5+","+result5+"]"));


        mockMvc.perform(delete("/activity/" + id1)).andExpect(status().isOk());
        mockMvc.perform(delete("/activity/" + id2)).andExpect(status().isOk());
        mockMvc.perform(delete("/activity/" + id3)).andExpect(status().isOk());
        mockMvc.perform(delete("/activity/" + id4)).andExpect(status().isOk());
        mockMvc.perform(delete("/activity/" + id5)).andExpect(status().isOk());



    }

        @Test
        public void paramGreetingShouldReturnTailoredMessage() throws Exception {

            this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
        }

}



