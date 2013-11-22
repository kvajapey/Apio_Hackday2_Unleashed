import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: kvajapey
 * Date: 11/22/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataProcessor {

    public static void main(String args[]){

        String fileName;
        double approach, entry, ignitionOn, ignitionOff, exit;


        Scanner scan;
        scan = new Scanner(System.in);

        System.out.print("Please print the filename of input data: ");
        fileName = scan.next();
        System.out.print("Please print the approach time of input data (-1 for none): ");
        approach = scan.nextDouble();
        System.out.print("Please print the entry time of input data (-1 for none): ");
        entry = scan.nextDouble();
        System.out.print("Please print the ignition on time of input data (-1 for none): ");
        ignitionOn = scan.nextDouble();
        System.out.print("Please print the ignition off time of input data (-1 for none): ");
        ignitionOff = scan.nextDouble();
        System.out.print("Please print the exit time of input data (-1 for none): ");
        exit = scan.nextDouble();

        FeatureCalculations fCalc = new FeatureCalculations(fileName, approach, entry, ignitionOn, ignitionOff, exit);

    }
}
