package EmployeeHibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;



import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;




public class App {
	public static Scanner scan = new Scanner(System.in);
    public static void main( String[] args )
    {
    	
		
		System.out.println("Welcome to Employee Deatils section 😎");
		
		boolean state = true;
		while(state) {
			System.out.println("Please tell what task you would like to perform =>\n 1- Add\n 2- Update\n 3-  getallEmployee details\n 4- Like\n 5- Logical operator\n");
			int choice = scan.nextInt();
			switch(choice) {
			case 1:
				addEmployee();
				break;
			case 2:
				System.out.println("Enter id of employee to update: ");
				int id = scan.nextInt();
				scan.nextLine();
				System.out.println("Enter designation of employee to update: ");
				String designation1 = scan.next();
				updateEmployee(designation1, id);
				
				break;
			case 3:
				namedQuery();
				break;
			case 4:
				likeOperator();
				break;
			case 5:
				logicalOperator();
				break;
			
			default:
				System.out.println("Exiting bye bye....👋🏻👋🏻");
			}
		}	
    }
 public static  void addEmployee() {
	 SessionFactory sf =new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	 Session s = sf.openSession();
	 try {
	 Transaction t = s.beginTransaction();
	 Employee1 e = new Employee1();
	 Scanner scan = new Scanner(System.in);
	 System.out.println("Enter Id : ");
	 int id = scan.nextInt();
	 e.setEmpId(id);
	 System.out.println("Enter First Name :" );
	 String fname = scan.next();
	 e.setFirstName(fname);
	 System.out.println("Enter Last Name :" );
	 String lname = scan.next();
	 e.setLastName(lname);
	 System.out.println("Enter Designation :" );
	 String designation = scan.next();
	 e.setDesignation(designation);
	s.save(e);
	t.commit();
		 System.out.println("Saved Successfully");
	 }
	 
	 catch (Exception e) {
		System.out.println(e);
	}
	 finally {
		 s.close();
	 }
 }
 public static void updateEmployee(String designation,int id) {
	 SessionFactory sf =new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	 Session s = sf.openSession();
	 try {
		 Transaction t = s.beginTransaction();
		 Query<Employee1> q= s.createQuery("update Employee1 e set e.designation =:n where e.empId = :i");
			q.setParameter("n", designation);
			q.setParameter("i", id);
			int status = q.executeUpdate();
		    System.out.println("Update Status " + status);
//		// Query<Employee1> q= s.createQuery("from Employee1 where id = :id");
//			//q.setParameter("id", id);
//			Employee1 emp = q.getSingleResult();
//			System.out.println("Enter updated details for this selection?\n "+ emp.toString());
		
		 t.commit();
		 System.out.println("Updated Successfully");
	 }
	 catch (Exception e) {
		System.out.println(e);
	}
	 finally {
		s.close();
	}
 }
 public static void namedQuery() {
	 SessionFactory sf =new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	 Session s = sf.openSession();
	 try {
		 Transaction t = s.beginTransaction();
		Query<Employee1> q = s.getNamedQuery("getAllEmployeeDetails");
			
			
			List<Employee1> list = q.getResultList();
			Iterator<Employee1> i = list.iterator();
			while(i.hasNext()){
				System.out.println(i.next());
			}
			t.commit();
	 }
	 catch (Exception e) {
		System.out.println(e);
	}
	 finally {
		s.close();
	}
 }
 public static void likeOperator() {
	 SessionFactory sf =new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	 Session s = sf.openSession();
	 try {
		 Transaction t = s.beginTransaction();
		 CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Employee1> cq = cb.createQuery(Employee1.class);
			Root<Employee1> root = cq.from(Employee1.class);
			cq.select(root).where(cb.like(root.get("firstName").as(String.class),"%a"));
			Query<Employee1> query = s.createQuery(cq);
			List<Employee1> list = query.getResultList();
			for(Employee1 e2 : list) {
				System.out.println("values : " + e2);
			}
			t.commit();
 }
	 catch (Exception e) {
		System.out.println(e);
	}
	 finally {
		s.close();
	}
}
 public static void logicalOperator() {
	 SessionFactory sf =new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	 Session s = sf.openSession();
	 try {
		 Transaction t = s.beginTransaction();
		 CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Employee1> cq = cb.createQuery(Employee1.class);
			Root<Employee1> root = cq.from(Employee1.class);
			cq.select(root).where(
	        		cb.and(
	        				  cb.greaterThan(root.get("empId").as(Integer.class),01),
	        		          cb.like(root.get("lastName").as(String.class),"%s")
	        		       )
	        		);
			Query<Employee1> query = s.createQuery(cq);
			List<Employee1> list = query.getResultList();
			for(Employee1 e2 : list) {
				System.out.println("values : " + e2);
			}
			t.commit();
}
	 catch (Exception e) {
		System.out.println(e);
	}
	 finally {
		s.close();
	}
 }
 }
