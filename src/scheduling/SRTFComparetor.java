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

class SRTFComparetor implements Comparator<Process>
{
    @Override
    public int compare(final Process p1, final Process p2) {
        final int bt = p1.getRemainingTime() - p2.getRemainingTime();
        if (bt != 0) {
            return bt;
        }
        final int at = p1.getArrivalTime() - p2.getArrivalTime();
        return at;
    }
}