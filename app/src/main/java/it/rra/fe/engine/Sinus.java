/**
 *      Sinus.Class v01.00 - 17.03.1999
 *      -------------------------------
 *
 *      Author:     Roberto Rocco Angeloni.
 *      Copyright:  (c) 1999 by Roberto Rocco Angeloni.
 *      Comment:    SinCos Table - Return the value << 14.
 *      To Do:
 *      Future:
 *      History:    - You can't have the palest idea of how much is old!
 *
 */

package it.rra.fe.engine;

public class Sinus {
    /**
     * Tabella dove sono memorizzati i valori frazionari della funzione seno.
     * I valori sono interi shiftati a sinistra di 14 bit.
     */

    private static final int[] table = {
            0,286,572,857,1143,1428,1713,1997,2280,
            2563,2845,3126,3406,3686,3964,4240,4516,
            4790,5063,5334,5604,5872,6138,6402,6664,
            6924,7182,7438,7692,7943,8192,8438,8682,
            8923,9162,9397,9630,9860,10087,10311,10531,
            10749,10963,11174,11381,11585,11786,11982,12176,
            12365,12551,12733,12911,13085,13255,13421,13583,
            13741,13894,14044,14189,14330,14466,14598,14726,
            14849,14962,15082,15191,15296,15396,15491,15582,
            15668,15749,15826,15897,15964,16026,16083,16135,
            16182,16225,16262,16294,16322,16344,16362,16374,
            16382,16383,

            16382,16374,16362,16344,16322,16294,16262,16225,
            16182,
            16135,16083,16026,15964,15897,15826,15749,15668,
            15582,15491,15396,15296,15191,15082,14962,14849,
            14726,14598,14466,14330,14189,14044,13894,13741,
            13583,13421,13255,13085,12911,12733,12551,12365,
            12176,11982,11786,11585,11381,11174,10963,10749,
            10531,10311,10087,9860,9630,9397,9162,8923,
            8682,8438,8192,7943,7692,7438,7182,6924,
            6664,6402,6138,5872,5604,5334,5063,4790,
            4516,4240,3964,3686,3406,3126,2845,2563,
            2280,1997,1713,1428,1143,857,572,286,0,

            -286,-572,-857,-1143,-1428,-1713,-1997,-2280,
            -2563,-2845,-3126,-3406,-3686,-3964,-4240,-4516,
            -4790,-5063,-5334,-5604,-5872,-6138,-6402,-6664,
            -6924,-7182,-7438,-7692,-7943,-8192,-8438,-8682,
            -8923,-9162,-9397,-9630,-9860,-10087,-10311,-10531,
            -10749,-10963,-11174,-11381,-11585,-11786,-11982,-12176,
            -12365,-12551,-12733,-12911,-13085,-13255,-13421,-13583,
            -13741,-13894,-14044,-14189,-14330,-14466,-14598,-14726,
            -14849,-14962,-15082,-15191,-15296,-15396,-15491,-15582,
            -15668,-15749,-15826,-15897,-15964,-16026,-16083,-16135,
            -16182,-16225,-16262,-16294,-16322,-16344,-16362,-16374,
            -16382,-16383,

            -16382,-16374,-16362,-16344,-16322,-16294,-16262,-16225,
            -16182,
            -16135,-16083,-16026,-15964,-15897,-15826,-15749,-15668,
            -15582,-15491,-15396,-15296,-15191,-15082,-14962,-14849,
            -14726,-14598,-14466,-14330,-14189,-14044,-13894,-13741,
            -13583,-13421,-13255,-13085,-12911,-12733,-12551,-12365,
            -12176,-11982,-11786,-11585,-11381,-11174,-10963,-10749,
            -10531,-10311,-10087,-9860,-9630,-9397,-9162,-8923,
            -8682,-8438,-8192,-7943,-7692,-7438,-7182,-6924,
            -6664,-6402,-6138,-5872,-5604,-5334,-5063,-4790,
            -4516,-4240,-3964,-3686,-3406,-3126,-2845,-2563,
            -2280,-1997,-1713,-1428,-1143,-857,-572,-286,0
    };
    /**
     *   Return a fractional value left shifted of 14 bits.
     */
    public static final int sin(int d) {
        return table[d];
    }
    /**
     *   Return a fractional value left shifted of 14 bits.
     */
    public static final int cos(int d) {
        return table[ ( d>=270 ? d-270 : d+90 ) ];
    }
}
