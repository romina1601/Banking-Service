package utcn.labs.sd.bankingservice.domain.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface ServiceInterface {

    static String getUser(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(loggedInUser != null)
            username = loggedInUser.getName();
        return username;
    }

    static boolean isInPeriod(String from, String to, String current) throws Exception
    {
        SimpleDateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDateFrom = dateFormatFrom.parse(from);
        Timestamp timestampFrom = new java.sql.Timestamp(parsedDateFrom.getTime());

        to = to + " 23:59:59.999";
        SimpleDateFormat dateFormatTo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDateTo = dateFormatTo.parse(to);
        Timestamp timestampTo= new java.sql.Timestamp(parsedDateTo.getTime());

        SimpleDateFormat dateFormatCurrent = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDateCurrent = dateFormatTo.parse(current);
        Timestamp timestampCurrent= new java.sql.Timestamp(parsedDateCurrent.getTime());

        if(timestampCurrent.after(timestampFrom) && timestampCurrent.before(timestampTo))
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}
