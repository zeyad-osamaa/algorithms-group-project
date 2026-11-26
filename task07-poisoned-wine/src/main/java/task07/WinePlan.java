/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task07;

import java.util.List;

/**
 * The result of designWineTests.
 *
 * @param possible    true if the testers can identify the poisoned barrel in time
 * @param testers     number of testers used
 * @param tastingDays number of tasting days used (days 0 .. tastingDays - 1)
 * @param schedule    who tastes which barrels on which day (empty if not possible)
 * @param decoder     turns the observed deaths into the poisoned barrel (null if not possible)
 */
public record WinePlan(boolean possible, int testers, int tastingDays,
                       List<Tasting> schedule, Decoder decoder) {

    public WinePlan {
        schedule = List.copyOf(schedule);
    }
}
