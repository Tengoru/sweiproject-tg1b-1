package base.acitvitymeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by Sümeyye on 08.12.2017.
 */
@RestController
@RequestMapping("activity/filter")
public class ActivityFilterController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping("{tags}")
 public ArrayList<Activity> listAllFilter(@PathVariable String tags) {
      ArrayList<Activity> activities = new ArrayList<>();
      ArrayList<Activity> activitiesFiltered = new ArrayList<>();
      activityRepository.findAll().forEach(activity -> activities.add(activity));
      activities.stream().filter(x->x.getTags().
              equals(tags)).forEach(activity -> activitiesFiltered.add(activity));
      return activitiesFiltered;
    }

}
