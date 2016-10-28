package facades;


import security.PasswordStorage;

import security.IUserFacade;
import entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Persistence;
import security.IUser;

public class UserFacade implements IUserFacade {
  /*When implementing your own database for this seed, you should NOT touch any of the classes in the security folder
    Make sure your new facade implements IUserFacade and keeps the name UserFacade, and that your Entity User class implements 
    IUser interface, then security should work "out of the box" with users and roles stored in your database */
  
  private final  Map<String, IUser> users = new HashMap<>();
  EntityManagerFactory emf;
    
  public UserFacade() {
   try { //Test Users
    emf = Persistence.createEntityManagerFactory("lam_seedMaven_war_1.0-SNAPSHOTPU"); 
    User user = new User("user", PasswordStorage.createHash("test"));
    user.addRole("User");
    addUser(user);
    
    User admin = new User("admin", PasswordStorage.createHash("test"));
    admin.addRole("Admin");
    addUser(admin);
    
    User both = new User("user_admin",PasswordStorage.createHash("test"));
    both.addRole("User");
    both.addRole("Admin");
    addUser(both);
   } catch(PasswordStorage.CannotPerformOperationException e) {
       
   }
  }
    public UserFacade(EntityManagerFactory emf)
    {
        this.emf = emf;
        
    }
    
    
    @Override
  public IUser getUserByUserId(String id){
    EntityManager em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            User u = em.find(User.class, id);
            //em.remove(em);
            em.getTransaction().commit();
            //System.out.println("person" + p.getFirstName());
            return u;
        } finally
        {
            em.close();
        }
  }
  
  public void addUser(User u) {
            EntityManager em = emf.createEntityManager();
        
        try
        {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            
        } finally
        {
            em.close();
        }
  }
  /*
  Return the Roles if users could be authenticated, otherwise null
  */
    @Override
  public List<String> authenticateUser(String userName, String password){
    IUser user = getUserByName(userName);
    
    try {
        return user != null && PasswordStorage.verifyPassword(password, user.getPassword())?user.getRolesAsStrings():null;
    } catch (Exception e) {
        return null;
    }
  }
  public User getUserByName(String username) {
     EntityManager em = emf.createEntityManager();
        
        try
        {
            em.getTransaction().begin();
            
            Query q = em.createQuery("Select u from User u where u.userName =:name", User.class);
            q.setParameter("name", username);
            
            em.getTransaction().commit();
            User u = (User) q.getSingleResult();
            return u;
        } finally
        {
            em.close();
        }
  }
  
}
