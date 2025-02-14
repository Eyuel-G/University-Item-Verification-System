package universityitemverificationsystem2;
public class User {
    private String name;
    private String father_name;
    private String department;
    private int year;
    private int id;

    public User(String name, String father_name, String department, int year, int id) {
        this.name = name;
        this.father_name = father_name;
        this.department = department;
        this.year = year;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFather_Name() {
        return father_name;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public int getYear() {
        return year;
    }
    
    public int getId() {
        return id;
    }
    
    

    @Override
    public String toString() {
        return "Name: " + name + ", Father Name: " + father_name + "Department: " + department + ", Year: " + year + "ID: " + id +"]";
    }
}
