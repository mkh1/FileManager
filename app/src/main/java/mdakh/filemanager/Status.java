package mdakh.filemanager;

/**
 * Created by mdakh on 5/7/2018.
 */

public class Status {

    public static boolean Equal_value_Files=false;

    public static boolean checkbox=false;

    public static boolean get_Main_list=false;

    public static boolean RTL=false;



    public static class Status_Files{
        private static boolean one=false;
        private static boolean tow=false;
        public static boolean isEqual(){
            if (one&&tow){
                return true;
            }
            else
                return false;
        }
        public static boolean isCahnged(){
            if (one&&!tow){
                return true;
            }
            else
                return false;
        }

        public static void Equal(boolean equal){
            if (equal) {
                one = true;
                tow = true;
            }
            else {
                one = false;
                tow = false;
            }
        }

        public static void Change(boolean changed){
            if (changed) {
                one = true;
                tow = false;
            }
            else {
                one = false;
                tow = false;
            }
        }
    }



}

