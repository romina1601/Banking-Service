package utcn.labs.sd.bankingservice.domain.factory;

import utcn.labs.sd.bankingservice.domain.data.entity.Activity;

import java.util.ArrayList;
import java.util.List;

public interface Report {
    public void generateReport(List<Activity> activityList, String from, String to)
            throws Exception;
}
