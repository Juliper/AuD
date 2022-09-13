package h06;

import org.jetbrains.annotations.Nullable;

import java.util.Calendar;

public class MyDate {
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    private final long coefficientYear = 4563766470487201L;
    private final long coefficientMonth = 73232;
    private final long coefficientDay = 4;
    private final long coefficientHour = 1234;
    private final long coefficientMinute = 99998;
    private final long coefficientSum = 98924;

    private final boolean randomBoolean;

    /**
     * Creates a new date consisting of a calendar and a boolean value deciding how to calculate the hash value.
     *
     * @param date          The date.
     * @param randomBoolean The boolean value used to determine the calculation of the hash value.
     */
    public MyDate(Calendar date, boolean randomBoolean) {

        this.year = date.get(Calendar.YEAR);
        this.month = date.get(Calendar.MONTH);
        this.day = date.get(Calendar.DAY_OF_MONTH);
        this.hour = date.get(Calendar.HOUR);
        this.minute = date.get(Calendar.MINUTE);


        this.randomBoolean = randomBoolean;
    }

    /**
     * Returns the year of the date stored in this object.
     * @return The year of the date stored in this object.
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Returns the month of the date stored in this object.
     * @return The month of the date stored in this object.
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * Returns the day of month of the date stored in this object.
     * @return The day of month of the date stored in this object.
     */
    public int getDay()
    {
        return day;
    }

    /**
     * Returns the hour of the date stored in this object.
     * @return The hour of the date stored in this object.
     */
    public int getHour()
    {
        return hour;
    }

    /**
     * Returns the minute of the date stored in this object.
     * @return The minute of the date stored in this object.
     */
    public int getMinute() { return minute; }

    /**
     * Return the boolean value set in the constructor.
     * @return The boolean value set in the constructor.
     */
    public boolean getBool() { return randomBoolean; }

    @Override
    public int hashCode() {
        if (randomBoolean)  {

            return Math.floorMod(
                (getYear() * this.coefficientYear) +
                (getMonth() * this.coefficientMonth) +
                (getDay() * this.coefficientDay) +
                (getHour() * this.coefficientHour) +
                (getMinute() * this.coefficientMinute)
                , Integer.MAX_VALUE);

        }
        else {

            return Math.floorMod(
                ((long) getYear() + getMonth() + getDay() + getHour() + getMinute()) * this.coefficientSum
                , Integer.MAX_VALUE);

        }

    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (!(obj instanceof MyDate))   return false;


        return obj.hashCode() == this.hashCode();

    }
}
