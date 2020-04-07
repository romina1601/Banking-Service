package utcn.labs.sd.bankingservice.domain.data.converter;

import utcn.labs.sd.bankingservice.domain.data.entity.Activity;
import utcn.labs.sd.bankingservice.domain.dto.ActivityDTO;

import java.util.ArrayList;
import java.util.List;

public class ActivityConverter {

    private ActivityConverter() {
    }

    public static ActivityDTO toDto(Activity model) {
        ActivityDTO dto = null;
        if (model != null) {
            dto = new ActivityDTO(model.getActivityId(), model.getUsername(), model.getActivityName(),
                    model.getTimestamp(), model.getDescription());
        }
        return dto;
    }

    public static List<ActivityDTO> toDto(List<Activity> models) {
        List<ActivityDTO> activityDtos = new ArrayList<>();
        if ((models != null) && (!models.isEmpty())) {
            for (Activity model : models) {
                activityDtos.add(toDto(model));
            }
        }
        return activityDtos;
    }
}
