import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Company {
    private final List<Employee> employeeList = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    public void inputEmployeeData(int amount) {
        for (int i=0; i<amount; i++) {
            System.out.println();
            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            System.out.println();
            System.out.print("Position: ");
            String position = sc.nextLine().trim();
            System.out.println();
            System.out.print("Salary: ");
            int salary = sc.nextInt();
            sc.nextLine();
            employeeList.add(new Employee(name, position, salary));
        }
    }

    public void run(File file, int amount) {
        inputEmployeeData(amount);
        if (initDir(file)) {
            System.out.println("Directory is created");
        }
        try{
            saveEmployeeData(file, employeeList);
            System.out.println();
            List<Employee> deserialized = loadEmployeesData(file);
            showEmployeesInfo(deserialized);
        } catch (IOException e) {
            System.out.println("Cannot write to file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot read from file: " + e.getMessage());
        }
    }

    public void saveEmployeeData(File file, List<Employee> employees) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(employees);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public List<Employee> loadEmployeesData(File file) throws ClassNotFoundException, IOException{
        List<Employee> searchedEmployee;
        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            //noinspection unchecked
            searchedEmployee = (List<Employee>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            if(e instanceof ClassNotFoundException) {
                throw new ClassNotFoundException();
            } else {
                throw new IOException();
            }
        }
        return searchedEmployee;
    }

    public void showEmployeesInfo(List<Employee> employees) {
        for (Employee e : employees) {
            System.out.println("Name: " + e.name());
            System.out.println("Position: " + e.position());
            System.out.println("Salary: " + e.salary());
            System.out.println();
        }
    }

    public boolean initDir(File file){
        return file.getParentFile().mkdirs();
    }
}
