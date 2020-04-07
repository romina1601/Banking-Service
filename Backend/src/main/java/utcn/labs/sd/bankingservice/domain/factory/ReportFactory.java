package utcn.labs.sd.bankingservice.domain.factory;

public class ReportFactory {

    public Report getreportType(String reportType)
    {
        if (reportType == null)
            return null;
        if(reportType.equalsIgnoreCase("PDF"))
        {
            return new PdfReport();

        }
        else if(reportType.equalsIgnoreCase("CSV"))
        {
            return new CsvReport();
        }

        return null;
    }
}
