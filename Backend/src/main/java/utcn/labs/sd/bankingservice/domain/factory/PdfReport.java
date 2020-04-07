package utcn.labs.sd.bankingservice.domain.factory;

import com.itextpdf.text.Document;
import com.itextpdf.text.*;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import utcn.labs.sd.bankingservice.domain.data.entity.Activity;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.util.ArrayList;
//import java.util.List;
import static utcn.labs.sd.bankingservice.domain.service.ServiceInterface.isInPeriod;

public class PdfReport implements Report {

    @Override
    public void generateReport(java.util.List<Activity> activityList, String from, String to)
            throws Exception
    {

        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
        document.open();

        List list = new List(List.ORDERED);
        list.setAutoindent(false);
        list.setSymbolIndent(42);
        for (Activity a: activityList)
        {
            if(isInPeriod(from, to, a.getTimestamp()))
            {
                //document.add(new Paragraph(a.toString()));
                list.add(new ListItem(a.toString()));
            }
        }
        document.add(list);

        document.close();
        //pdfWriter.close();

    }

    /*@Override
    public void generateReport(List<Activity> activityList, String from, String to)
    {
        Document document = new Document();
        List list = new List(List.ORDERED);
        list.setAutoindent(false);
        list.setSymbolIndent(42);
        for(Activity a: activityRepository.findAll()){
            if(a.getId_employee()==employee.getId()) {
                Date aDate = dateFormat.parse(a.getDate());
                Timestamp aStamp = new Timestamp(aDate.getTime());
                if(aStamp.after(fromStamp) && aStamp.before(toStamp)){
                    list.add(new ListItem("employeeUsername: "+employee.getUsername()+" did: "+a.getActivity()+" at: "+a.getDate()));
                }
            }
        }


    }*/
}
