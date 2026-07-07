/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AutoHubUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtil 
{
    // What you show to / accept from the user
    public static final DateTimeFormatter DISPLAY =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateUtil() {}

    // User typed "dd/MM/yyyy" -> LocalDate (ready for the DB)
    public static LocalDate fromDisplay(String text) 
    {
        return LocalDate.parse(text.trim(), DISPLAY);
    }

    // LocalDate from the DB -> "dd/MM/yyyy" for the screen
    public static String toDisplay(LocalDate date) 
    {
        return date.format(DISPLAY);
    }
}
