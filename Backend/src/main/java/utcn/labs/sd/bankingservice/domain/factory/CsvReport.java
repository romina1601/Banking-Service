package utcn.labs.sd.bankingservice.domain.factory;

import com.itextpdf.text.Paragraph;
import utcn.labs.sd.bankingservice.domain.data.entity.Activity;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static utcn.labs.sd.bankingservice.domain.service.ServiceInterface.isInPeriod;

public class CsvReport implements Report {

    @Override
    public void generateReport(java.util.List<Activity> activityList, String from, String to)
            throws Exception
    {
        FileWriter writer = new FileWriter("output.csv");
        writer.append("activityId,username,activityName,timestamp,description").append('\n');

        for (Activity a: activityList)
        {
            if(isInPeriod(from, to, a.getTimestamp()))
            {
                writer.append(a.toStringForCSV()).append('\n');
            }
        }

        writer.close();

    }
}
