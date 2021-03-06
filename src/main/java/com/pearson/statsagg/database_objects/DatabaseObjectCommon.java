package com.pearson.statsagg.database_objects;

import com.pearson.statsagg.utilities.MathUtilities;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeffrey Schmidt
 */
public class DatabaseObjectCommon {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectCommon.class.getName());

    public static final int TIME_UNIT_DAYS = 71;
    public static final int TIME_UNIT_HOURS = 72;
    public static final int TIME_UNIT_MINUTES = 73;
    public static final int TIME_UNIT_SECONDS = 74;
    public static final int TIME_UNIT_MILLISECONDS = 75;
    
    public static final BigDecimal MILLISECONDS_PER_SECOND = new BigDecimal(1000);
    public static final BigDecimal MILLISECONDS_PER_MINUTE = new BigDecimal(60000);
    public static final BigDecimal MILLISECONDS_PER_HOUR = new BigDecimal(3600000);
    public static final BigDecimal MILLISECONDS_PER_DAY = new BigDecimal(86400000);

    public static final int TIME_UNIT_SCALE = 7;
    public static final int TIME_UNIT_PRECISION = 31;
    public static final RoundingMode TIME_UNIT_ROUNDING_MODE = RoundingMode.HALF_UP;
    public static final MathContext TIME_UNIT_MATH_CONTEXT = new MathContext(TIME_UNIT_PRECISION, TIME_UNIT_ROUNDING_MODE);
    
    public static String getTimeUnitStringFromCode(Integer timeUnitCode, boolean outputLowercase) {
        
        if ((timeUnitCode == null)) {
            return null;
        }

        if (timeUnitCode == TIME_UNIT_DAYS) {
            if (outputLowercase) return "days";
            return "Days";
        }
        else if (timeUnitCode == TIME_UNIT_HOURS) {
            if (outputLowercase) return "hours";
            return "Hours";
        }
        else if (timeUnitCode == TIME_UNIT_MINUTES) {
            if (outputLowercase) return "minutes";
            return "Minutes";
        }
        else if (timeUnitCode == TIME_UNIT_SECONDS) {
            if (outputLowercase) return "seconds";
            return "Seconds";
        }
        else logger.warn("Unrecognized time unit");
         
        return null;
    }
    
    public static Integer getTimeUnitCodeFromString(String timeUnit) {
        
        if ((timeUnit == null) || timeUnit.isEmpty()) {
            return null;
        }
        
        if (timeUnit.equalsIgnoreCase("Days")) return TIME_UNIT_DAYS;
        else if (timeUnit.equalsIgnoreCase("Hours")) return TIME_UNIT_HOURS;
        else if (timeUnit.equalsIgnoreCase("Minutes")) return TIME_UNIT_MINUTES;
        else if (timeUnit.equalsIgnoreCase("Seconds")) return TIME_UNIT_SECONDS;
        else logger.warn("Unrecognized time unit code");
        
        return null;
    }
    
    public static BigDecimal getMillisecondValueForTime(BigDecimal time, Integer timeUnitCode) {
        
        if ((time == null) || (timeUnitCode == null)) {
            return null;
        }
        
        if (timeUnitCode == TIME_UNIT_SECONDS) return MathUtilities.smartBigDecimalScaleChange(time.multiply(MILLISECONDS_PER_SECOND), TIME_UNIT_SCALE, TIME_UNIT_ROUNDING_MODE);
        else if (timeUnitCode == TIME_UNIT_MINUTES) return MathUtilities.smartBigDecimalScaleChange(time.multiply(MILLISECONDS_PER_MINUTE), TIME_UNIT_SCALE, TIME_UNIT_ROUNDING_MODE);
        else if (timeUnitCode == TIME_UNIT_HOURS) return MathUtilities.smartBigDecimalScaleChange(time.multiply(MILLISECONDS_PER_HOUR), TIME_UNIT_SCALE, TIME_UNIT_ROUNDING_MODE);
        else if (timeUnitCode == TIME_UNIT_DAYS) return MathUtilities.smartBigDecimalScaleChange(time.multiply(MILLISECONDS_PER_DAY), TIME_UNIT_SCALE, TIME_UNIT_ROUNDING_MODE);
        
        return null;
    }
    
    public static BigDecimal getValueForTimeFromMilliseconds(Long timeInMs, Integer timeUnitCode) {
        
        if ((timeInMs == null) || (timeUnitCode == null)) {
            return null;
        }
        
        BigDecimal timeInMs_BigDecimal = new BigDecimal(timeInMs);
        
        if (timeUnitCode == TIME_UNIT_SECONDS) return timeInMs_BigDecimal.divide(MILLISECONDS_PER_SECOND, TIME_UNIT_SCALE, TIME_UNIT_ROUNDING_MODE).stripTrailingZeros();
        else if (timeUnitCode == TIME_UNIT_MINUTES) return timeInMs_BigDecimal.divide(MILLISECONDS_PER_MINUTE, TIME_UNIT_SCALE, TIME_UNIT_ROUNDING_MODE).stripTrailingZeros();
        else if (timeUnitCode == TIME_UNIT_HOURS) return timeInMs_BigDecimal.divide(MILLISECONDS_PER_HOUR, TIME_UNIT_SCALE, TIME_UNIT_ROUNDING_MODE).stripTrailingZeros();
        else if (timeUnitCode == TIME_UNIT_DAYS) return timeInMs_BigDecimal.divide(MILLISECONDS_PER_DAY, TIME_UNIT_SCALE, TIME_UNIT_ROUNDING_MODE).stripTrailingZeros();
        
        return null;
    }
    
}
