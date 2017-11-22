package base.activitymeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController


@RequestMapping("/activity")
public class ActivityController {
  
  @Autowired
  private ActivityRepository activityRepository;
  
  // benoetigt fuer jegliche Ansicht
  @GetMapping
  public ArrayList<Activity> showOverview() {
      ArrayList<Activity> activities = new ArrayList<>();
      activityRepository.findAll().forEach(activity -> activities.add(activity));
      return activities;
  }
// benoegtigt fuer Filter
  @GetMapping("{id}")
  public Activity find(@PathVariable Long id) {
      return activityRepository.findOne(id);
  }

// beoetigt fuer post
  @PostMapping
  public Activity post(@RequestBody Activity input,int code) {
      Activity result;
      // wenn code gleich generiertem Code
      if(true)
          result = activityRepository.save(new Activity(input.getText(), input.getTag().getName(), input.getTitle()));
      else
          result = null;
      return result;
  }
  //Methode die dem Benutzer eine mail schickt, ist diese der Hm oder Cal Poly zugehoerig bekommt er einen Code
    // wenn der Code
  private static  int verification(){
      return 0;
  }









// loeschen und aendern sind bei unserem Modell noch nicht vorgesehen
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
          activity.setTag(input.getTag());
          activity.setTitle(input.getTitle());
          return activityRepository.save(activity);
      }
  }

}
