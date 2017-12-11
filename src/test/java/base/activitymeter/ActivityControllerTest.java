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
    public void notEmpty() throws Exception {

        MockHttpServletResponse response1 = mockMvc
                .perform(post("/activity")
                        .content(testActivity1)
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse();

        char id1 = response1.getContentAsString().charAt(6);

        String result1 = testActivity1.substring(1);
        this.mockMvc.perform(get("/activity")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("["+idTemplate+id1+","+result1+"]"));


        mockMvc.perform(delete("/activity/" + id1)).andExpect(status().isOk());

    }

    @Test
    public void postManyTest() throws Exception {
        MockHttpServletResponse response1 = mockMvc
                .perform(post("/activity")
                        .content(testActivity1)
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse();
        MockHttpServletResponse response2 = mockMvc
                .perform(post("/activity")
                        .content(testActivity2)
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse();

        char id1 = response1.getContentAsString().charAt(6);
        char id2 = response2.getContentAsString().charAt(6);
        mockMvc.perform(delete("/activity/" + id1)).andExpect(status().isOk());
        mockMvc.perform(delete("/activity/" + id2)).andExpect(status().isOk());
    }

    //        @Test
//        public void rightNumberOfEntries() throws Exception{
//            int lengthOfContent = (testActivity1+testActivity2+testActivity3).length();
//            this.mockMvc.perform(post("/activity")
//                    .content(testActivity1+testActivity2+testActivity3).contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn()
//                    .getResponse().getContentAsString().length();
//
//        }

    @Test
    public void filterEmptyList() throws Exception{
        this.mockMvc.perform(get("/activity/filter/tag")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void askedForTagNotInTheList() throws Exception {
        MockHttpServletResponse response1 = mockMvc
                .perform(post("/activity")
                        .content(testActivity1)
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse();

        this.mockMvc.perform(get("/activity/filter/nottest1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));

        char id1 = response1.getContentAsString().charAt(6);
        mockMvc.perform(delete("/activity/" + id1));
    }

//    @Test
//    public void filterTagWhichIsOnceInTheListTest() throws Exception {
//        this.mockMvc.perform((post("/activity")
//                .content(testActivity1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
//                .andDo(print()).andExpect(status().isOk());
//        this.mockMvc.perform((post("/activity")
//                .content(testActivity2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
//                .andDo(print()).andExpect(status().isOk());
//        this.mockMvc.perform((post("/activity")
//                .content(testActivity3).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
//                .andDo(print()).andExpect(status().isOk());
//
//        this.mockMvc.perform(get("/activity/filter/tag1")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string("["+testActivity1+"]"));
//
//        int id1 = 1;
//        this.mockMvc.perform(delete("/activity/" + id1)).andDo(print()).andExpect(status().isOk());
//        int id2 = 2;
//        this.mockMvc.perform(delete("/activity/" + id2)).andDo(print()).andExpect(status().isOk());
//        int id3 = 3;
//        this.mockMvc.perform(delete("/activity/" + id3)).andDo(print()).andExpect(status().isOk());
//
//    }
//
//
//    @Test
//    public void filterTagWhichIsSeveralTimesInTheListTest() throws Exception {
//        this.mockMvc.perform((post("/activity")
//                .content(testActivity1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
//                .andDo(print()).andExpect(status().isOk());
//        this.mockMvc.perform((post("/activity")
//                .content(testActivity2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
//                .andDo(print()).andExpect(status().isOk());
//        this.mockMvc.perform((post("/activity")
//                .content(testActivity3).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
//                .andDo(print()).andExpect(status().isOk());
//        this.mockMvc.perform((post("/activity")
//                .content(testActivity4).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
//                .andDo(print()).andExpect(status().isOk());
//        this.mockMvc.perform((post("/activity")
//                .content(testActivity5).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
//                .andDo(print()).andExpect(status().isOk());
//
//        this.mockMvc.perform(get("/activity/filter/tag1")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string("["+testActivity1+","+testActivity4+","+testActivity5+"]"));
//
//        int id1 = 1;
//        this.mockMvc.perform(delete("/activity/" + id1)).andDo(print()).andExpect(status().isOk());
//        int id2 = 2;
//        this.mockMvc.perform(delete("/activity/" + id2)).andDo(print()).andExpect(status().isOk());
//        int id3 = 3;
//        this.mockMvc.perform(delete("/activity/" + id3)).andDo(print()).andExpect(status().isOk());
//        int id4 = 4;
//        this.mockMvc.perform(delete("/activity/" + id4)).andDo(print()).andExpect(status().isOk());
//        int id5 = 5;
//        this.mockMvc.perform(delete("/activity/" + id5)).andDo(print()).andExpect(status().isOk());
//
//
//
//
//
//    }

        @Test
        public void paramGreetingShouldReturnTailoredMessage() throws Exception {

            this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
        }

}



