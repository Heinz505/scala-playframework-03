import java.io.*;

interface Bicycle {

    //  wheel revolutions per minute
    void changeCadence(int newValue);

    void changeGear(int newValue);

    void speedUp(int increment);

    void applyBrakes(int decrement);
}


class ACMEBicycle implements Bicycle {

    int cadence = 0;
    int speed = 0;
    int gear = 1;

   // The compiler will now require that methods
   // changeCadence, changeGear, speedUp, and applyBrakes
   // all be implemented. Compilation will fail if those
   // methods are missing from this class.

    static void changeCadence(int newValue) {
         cadence = newValue;
    }

    static void changeGear(int newValue) {
         gear = newValue;
    }

    static void speedUp(int increment) {
         speed = speed + increment;   
    }

    static void applyBrakes(int decrement) {
         speed = speed - decrement;
    }

    static void printStates() {
         System.out.println("cadence:" +
             cadence + " speed:" + speed + " gear:" + gear);
    }
}


public class DisplayOutput {
	public static void main(String args[]){
		ACMEBicycle.printStates();



		
		
	}


}





