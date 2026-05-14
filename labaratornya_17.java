import java.io.*;
import java.util.Scanner;

class Sinus implements Serializable {
    double x;

    Sinus(double x) {
        this.x = x;
    }

    double Result() {
        return this.x - Math.sin(this.x);
    }
}

void main() {
    Scanner scan = new Scanner(System.in);
    String filename = "test.txt";
    File file = new File(filename);
    System.out.println("x:");
    double x = scan.nextDouble();
    Sinus sin = new Sinus(x);
    double y = sin.Result();
    System.out.printf("Result y = %f", y);
    while (true) {
        System.out.print("Enter command: save | upload | exit ");
        String str = scan.next();
        if (str.equalsIgnoreCase("save")) {
            try (ObjectOutputStream objOutStr1 = new ObjectOutputStream(new FileOutputStream(filename))) {
                objOutStr1.writeObject(sin); objOutStr1.writeDouble(y); objOutStr1.writeDouble(x);
                System.out.println("data saved in " + filename);
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        } else if (str.equalsIgnoreCase("upload")) {
            if (!file.exists()) {
                System.out.println("File unknow. Enter save.");
            }
            try (ObjectInputStream objOutStr = new ObjectInputStream(new FileInputStream(filename))) {
                Sinus retSin = (Sinus) objOutStr.readObject();
                System.out.printf("Object: %f", retSin.x);
                System.out.println();
                double retY = objOutStr.readDouble();
                System.out.println(retY);
                double retX = objOutStr.readDouble();
                System.out.println(retX);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
        } else{
            break;
        }
    }
}


