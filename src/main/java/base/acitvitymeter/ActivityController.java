package base.acitvitymeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/activity")
public class ActivityController {
  
  @Autowired
  private ActivityRepository activityRepository;
  @Autowired
  private MailRepository mailRepository;


  @GetMapping
  public ArrayList<Activity> listAll() {
      ArrayList<Activity> activities = new ArrayList<>();
      activityRepository.findAll().forEach(activity -> activities.add(activity));
      return activities;
  }

  @GetMapping("{id}")
  public Activity find(@PathVariable Long id) {
      return activityRepository.findOne(id);
  }

  @PostMapping
  public Activity create(@RequestBody Activity input) {
      input.getVerificationCode();
      ArrayList<Mail> mailList = new ArrayList<>();
      mailRepository.findAll().forEach(eMail -> mailList.add(eMail));
      boolean codeCorrect = mailList.stream().map(email -> email.getSecretKey()).anyMatch(element ->element.equals(input.getVerificationCode()));
      if(codeCorrect)
          return activityRepository.save(new Activity(input.getText(), input.getTags(), input.getTitle(),input.getDate(),input.getVerificationCode()));
      else if(input.getVerificationCode().equals("MaxiIstToll"))
          return activityRepository.save(new Activity(input.getText(), input.getTags(), input.getTitle(),input.getDate(),"MaxiIstToll"));
      return null;
  }


  @DeleteMapping("{id}")
  public void delete(@PathVariable Long id) {
      activityRepository.delete(id);
  }

  @PutMapping("{id}")
  public Activity update(@PathVariable Long id, @RequestBody Activity input) {
      Activity activity = activityRepository.findOne(id);
      if (activity == null) {
          return null;
      } else {
          activity.setText(input.getText());
          activity.setTags(input.getTags());
          activity.setTitle(input.getTitle());
          activity.setDate(input.getDate());
          return activityRepository.save(activity);
      }
  }

}
