package base.activitymeter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc


public class ActivityControllerTest {


    String testActivity1 = "{\"text\":\"text1\",\"tags\":\"tag1\",\"title\":\"title1\",\"date\":\"date1\"}";
    String testActivity2 = "{\"text\":\"text2\",\"tags\":\"tag2\",\"title\":\"title2\",\"date\":\"date2\"}";
    String testActivity3 = "{\"text\":\"text3\",\"tags\":\"tag3\",\"title\":\"title3\",\"date\":\"date3\"}";
    String testActivity4 = "{\"text\":\"text4\",\"tags\":\"tag1\",\"title\":\"title4\",\"date\":\"date4\"}";
    String testActivity5 = "{\"text\":\"text1\",\"tags\":\"tag1\",\"title\":\"title1\",\"date\":\"date1\"}";
    String testActivity6 = "{\"text\":\"text5\",\"tags\":\"tag1\",\"title\":\"title5\",\"date\":\"date5\"}";


        @Autowired
        private MockMvc mockMvc;

        @Test
        public void statusOfServer() throws Exception{
            this.mockMvc.perform(get("/activity")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        public void noEnty() throws Exception {
            this.mockMvc.perform(get("/activity")).andDo(print()).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString().isEmpty();
        }

        @Test
        public void notEmpty() throws Exception {
            this.mockMvc.perform(post("/activity")
                    .content(testActivity1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn().getResponse().getContentAsString().contains(testActivity1);
        }

        @Test
        public void manyEntries() throws Exception{
            this.mockMvc.perform(post("/activity")
                    .content(testActivity1+testActivity2+testActivity3).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn().getResponse().getContentAsString().contains(testActivity1+testActivity2+testActivity3);
        }

        @Test
        public void rightNumberOfEntries() throws Exception{
            int lengthOfContent = (testActivity1+testActivity2+testActivity3).length();
            this.mockMvc.perform(post("/activity")
                    .content(testActivity1+testActivity2+testActivity3).contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn()
                    .getResponse().getContentAsString().length();

        }

        @Test
        public void rightContentOfEntry() throws Exception{

        }

        @Test
        public void filterEmptyList() throws Exception{
            this.mockMvc.perform(get("/activity/tag")).andDo(print()).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString().isEmpty();
        }

        @Test
        public void askedForTagNotInTheList() throws Exception {
            this.mockMvc.perform((post("/activity")
                    .content(testActivity1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)))
                    .andDo(print()).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString().isEmpty();
        }

        @Test
        public void theTagAskedForIsInTheListOnlyOnce() throws Exception{

        }

        @Test
        public void theTagAskedForIsInTheListMultipleTimes() throws Exception{

        }


//        @Test
//        public void paramGreetingShouldReturnTailoredMessage() throws Exception {
//
//            this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
//                    .andDo(print()).andExpect(status().isOk())
//                    .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
//        }

    }



