package com.kingmeter.mvc;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.util.StringUtils;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class CustomDateFormat extends StdDateFormat {

	private String outputDateFormat="yyyy-MM-dd HH:mm:ss.SSS";
	
    public CustomDateFormat(String outputDateFormat) {
		this.outputDateFormat = outputDateFormat;
	}

	@Override
    public Date parse(String dateStr) {
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat sdf  = null;

        if(StringUtils.isEmpty(dateStr)){
            return null;
        }
        if (dateStr.length() == 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr, pos);
        }
        if (dateStr.length() == 16) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.parse(dateStr, pos);
        }
        if (dateStr.length() == 19) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dateStr, pos);
        }
        if (dateStr.length() == 23) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return sdf.parse(dateStr, pos);
        }
        return super.parse(dateStr, pos);
    }

    
    public void setOutputDateFormat(String outputDateFormat) {
		this.outputDateFormat = outputDateFormat;
	}

	@Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition){
        if(date==null)
        	return super.format(date, toAppendTo, fieldPosition);
		SimpleDateFormat sdf = new SimpleDateFormat(outputDateFormat);
        return sdf.format(date, toAppendTo, fieldPosition);
    }

    public CustomDateFormat clone() {
        return new CustomDateFormat(this.outputDateFormat);
    }
}
