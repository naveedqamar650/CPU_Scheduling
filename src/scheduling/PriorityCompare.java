/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.util.Comparator;

// 
// Decompiled by Procyon v0.5.36
// 

class PriorityCompare implements Comparator<Process>
{
    @Override
    public int compare(final Process p1, final Process p2) {
        final int priority = p1.getPriority() - p2.getPriority();
        if (priority != 0) {
            return priority * -1;
        }
        final int arrivalTime = p1.getArrivalTime() - p2.getArrivalTime();
        return arrivalTime;
    }
}