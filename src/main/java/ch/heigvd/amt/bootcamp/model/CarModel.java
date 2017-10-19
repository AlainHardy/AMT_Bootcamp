package ch.heigvd.amt.bootcamp.model;

import ch.heigvd.amt.bootcamp.services.CarManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Alain
 */
public class CarModel {
   Connection connexion = null;
   
   CarManager cm = new CarManager();
   
   private void connectToDB() throws SQLException {
      InitialContext ctx;
      try {
         ctx = new InitialContext();
         DataSource ds = (DataSource) ctx.lookup("jdbc/bootcamp");
         connexion = ds.getConnection();
      } catch (NamingException ex) {
         Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
      }
      
   }
   
   private void closeConnectionToDb() throws SQLException {
      if(connexion != null) {
         connexion.close();
      }
   }
   
   public void insertRandomCar(int times) {
      try {
         connectToDB();
         int batchSize = 10000;
         int nbOfBatches = (int)Math.ceil((double)times/batchSize);
         int timesToLoop = times < batchSize ? times : batchSize;
         Car c;
         StringBuilder sb = new StringBuilder();
         PreparedStatement insertCars;
         
         
         sb.append("INSERT INTO cars(brand, color, horsepower, price) VALUES ");
         
         for(int i = 0; i < timesToLoop; i++) {
            sb.append("(?, ?, ?, ?)");
            if(i < timesToLoop-1)
               sb.append(",\n");
            
         }
         sb.append(";");
         
         for(int j = 0; j < nbOfBatches; j++) {
            timesToLoop = times < batchSize*(j+1) ? times - batchSize*(j) : batchSize;
            insertCars = connexion.prepareStatement(sb.toString());

            for(int i = 0; i < timesToLoop; i++) {
               c = cm.getRandomCar();
               insertCars.setString(i*4 + 1, c.getBrand());
               insertCars.setString(i*4 + 2, c.getColor());
               insertCars.setInt(i*4 + 3, c.getHorsePower());
               insertCars.setInt(i*4 + 4, c.getPrice());
            }
            insertCars.executeUpdate();
            insertCars.close();
         }
         
         closeConnectionToDb();
      } catch (SQLException ex) {
         Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   
   public void insertCar(String brand, String color, int horsePower, int price) {
      try {
         connectToDB();
         
         String request = "INSERT INTO cars(brand, color, horsepower, price) VALUES (?, ?, ?, ?);";
         
         PreparedStatement insertCar = connexion.prepareStatement(request);
         
         insertCar.setString(1, brand);
         insertCar.setString(2, color);
         insertCar.setInt(3, horsePower);
         insertCar.setInt(4, price);
         
         insertCar.executeUpdate();        
         
         insertCar.close();
         closeConnectionToDb();
      } catch (SQLException ex) {
         Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   
   public void editCar(int id, String brand, String color, int horsePower, int price) {
      try {
         connectToDB();
         
         String request = "UPDATE cars SET brand=?, color=?, horsePower=?, price=? WHERE id = ?";
         
         PreparedStatement editCar = connexion.prepareStatement(request);
         
         editCar.setString(1, brand);
         editCar.setString(2, color);
         editCar.setInt(3, horsePower);
         editCar.setInt(4, price);
         editCar.setInt(5, id);
         
         editCar.executeUpdate();
         
         editCar.close();
         closeConnectionToDb();
      } catch (SQLException ex) {
         Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   
   public List getCar(int id) {
      List res = new ArrayList();
      try {
         connectToDB();
         
         String request = "SELECT * FROM cars WHERE id = ?;";
         
         PreparedStatement getCar = connexion.prepareStatement(request);
         
         getCar.setInt(1, id);
         
         ResultSet rs = getCar.executeQuery();
         rs.first();
         res.add(rs.getInt(1));
         res.add(rs.getString(2));
         res.add(rs.getString(3));
         res.add(rs.getInt(4));
         res.add(rs.getInt(5));         
         
         getCar.close();
         rs.close();
         closeConnectionToDb();
      } catch (SQLException ex) {
         Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
      }      
      
      return res;
   }
   
   public List<List> getCars(int limit, int page) {
      List<List> res = new ArrayList();
      
      try {
         connectToDB();
         
         //SELECT * FROM `myBigTable` WHERE `id` > :OFFSET ORDER BY `id` ASC LIMIT :BATCH_SIZE;
         //String request = "SELECT * FROM cars WHERE id > (?-1)*? ORDER BY id ASC LIMIT ?;";
         String request = "SELECT * FROM cars LIMIT ?,?;";
         
         PreparedStatement getList = connexion.prepareStatement(request);
         
         getList.setInt(1, (page-1)*limit);
         getList.setInt(2, limit);
         
         ResultSet rs = getList.executeQuery();
         
         for(int i = 0; rs.next(); i++) {
            List temp = new ArrayList();
            temp.add(rs.getInt(1));
            temp.add(rs.getString(2));
            temp.add(rs.getString(3));
            temp.add(rs.getInt(4));
            temp.add(rs.getInt(5));            
            
            res.add(temp);            
         }
         
         getList.close();
         rs.close();
         closeConnectionToDb();
      } catch (SQLException ex) {
         Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      return res;
   }
   
   public int getNumberOfCars() {
      int res = 0;
      
      try {
         connectToDB();
         
         String request = "SELECT COUNT(*) FROM cars;";
         
         PreparedStatement getCount = connexion.prepareStatement(request);
         
         ResultSet rs = getCount.executeQuery();
         
         rs.first();         
         res = rs.getInt(1);
         
         getCount.close();
         rs.close();
         closeConnectionToDb();
      } catch (SQLException ex) {
         Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      return res;
   }
   
   public void deleteCar(int id) {
      try {
         connectToDB();
         
         String request = "DELETE FROM cars WHERE id = ?;";
         
         PreparedStatement deleteCar = connexion.prepareStatement(request);
         
         deleteCar.setInt(1, id);
         
         deleteCar.executeUpdate();
         
         deleteCar.close();
         closeConnectionToDb();
      } catch (SQLException ex) {
         Logger.getLogger(CarModel.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
}
