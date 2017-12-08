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

    String testActivity1 = "{\"id\":1,\"text\":\"text1\",\"tags\":\"tag1\",\"title\":\"title1\",\"date\":\"date1\"}";
    String testActivity2 = "{\"id\":2,\"text\":\"text2\",\"tags\":\"tag2\",\"title\":\"title2\",\"date\":\"date2\"}";
    String testActivity3 = "{\"id\":3,\"text\":\"text3\",\"tags\":\"tag3\",\"title\":\"title3\",\"date\":\"date3\"}";
    String testActivity4 = "{\"id\":4,\"text\":\"text4\",\"tags\":\"tag1\",\"title\":\"title4\",\"date\":\"date4\"}";
    String testActivity5 = "{\"id\":5,\"text\":\"text1\",\"tags\":\"tag1\",\"title\":\"title1\",\"date\":\"date1\"}";
    String testActivity6 = "{\"id\":6,\"text\":\"text5\",\"tags\":\"tag1\",\"title\":\"title5\",\"date\":\"date5\"}";
    String testTitle = "activity";
    String testTags = "YIPPIE";
    String testText = "Max was here!";
    String testDate = "heute";
    @Autowired
    private MockMvc mockMvc;

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


        this.mockMvc.perform(post("/activity")
                .content(testActivity2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(testActivity2));

        int id = 2;
        this.mockMvc.perform(delete("/activity/" + id)).andDo(print()).andExpect(status().isOk());
    }

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



//    @Test
//    public void editTest() throws Exception {
//        //funktioniert nicht, da
//
//        this.mockMvc.perform(post("/activity")
//                .content(testActivity1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
//        int id = 1;
//
//        JSONParser parser = new JSONParser();
//        org.json.simple.JSONObject cont = (org.json.simple.JSONObject) parser.parse(content().toString());
//
//
//        String testActivity1Edited = "{\"id\":1,\"text\":\"text2\",\"tags\":\"tag2\",\"title\":\"title2\",\"date\":\"date2\"}";
//        this.mockMvc.perform(put("/activity" + id).content(testActivity2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
//        this.mockMvc.perform(get("/activity"));
//        String retrunedId= cont.get("id").toString();
//        System.out.println("HAAAAAAAAAAAAAAAAAAAAAAAAAAAALLLO " + retrunedId);
//
//        this.mockMvc.perform(delete("/activity/" + id));
//    }

//        @Test
//        public void manyEntries() throws Exception{
//            this.mockMvc.perform(post("/activity")
//                    .content(testActivity1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
//            this.mockMvc.perform(post("/activity")
//                            .content(testActivity2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
//            this.mockMvc.perform(post("/activity")
//                            .content(testActivity3).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
//            this.mockMvc.perform(get("/activity")).andExpect(status().isOk()).andExpect(content().string("[" + testActivity1+","+ testActivity2+","+testActivity3+"]"));
//        }

    //        @Test
//        public void rightNumberOfEntries() throws Exception{
//            int lengthOfContent = (testActivity1+testActivity2+testActivity3).length();
//            this.mockMvc.perform(post("/activity")
//                    .content(testActivity1+testActivity2+testActivity3).contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn()
//                    .getResponse().getContentAsString().length();
//
//        }
//
//        @Test
//        public void rightContentOfEntry() throws Exception{
//
//        }
//
    @Test
    public void filterEmptyList() throws Exception{
        this.mockMvc.perform(get("/activity/filter/tag")).andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().isEmpty();
    }

    @Test
    public void askedForTagNotInTheList() throws Exception {
        this.mockMvc.perform((post("/activity")
                .content(testActivity1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().isEmpty();
        int id = 1;
        this.mockMvc.perform(delete("/activity/" + id)).andDo(print()).andExpect(status().isOk());
    }

//        @Test
//        public void theTagAskedForIsInTheListOnlyOnce() throws Exception{
//
//        }
//
//        @Test
//        public void theTagAskedForIsInTheListMultipleTimes() throws Exception{
//
//        }


//        @Test
//        public void paramGreetingShouldReturnTailoredMessage() throws Exception {
//
//            this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
//                    .andDo(print()).andExpect(status().isOk())
//                    .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
//        }

}



