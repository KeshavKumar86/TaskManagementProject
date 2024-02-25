package com.keshav.TaskManagement.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidatorUsingDateFormat implements DateValidator {
    private String dateFormat;
    private static final String DATE_REGEX = "^(\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]))$";
    public  DateValidatorUsingDateFormat(String dateFormat)
    {
        this.dateFormat=dateFormat;
    }
    @Override
    public boolean isValid(String dateString) {
        //validate against regular expression
        boolean isValid=false;

        try{
            Pattern pattern = Pattern.compile(DATE_REGEX);
            Matcher matcher = pattern.matcher(dateString);
            isValid= matcher.matches();
        }
        catch(Exception e)
        {
            throw new RuntimeException("Date is not valid");
        }


//        DateFormat sdf=new SimpleDateFormat(this.dateFormat);
//        sdf.setLenient(false);
//        if(isValid)
//        {
//            try {
//                sdf.parse(dateString);
//            } catch (ParseException e) {
//                isValid=false;
//            }
//        }
        return isValid;
    }
}



