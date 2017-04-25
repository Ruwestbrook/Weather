package example.com.weather.gson;

/**
 * Created by 14064 on 2017/4/23.
 */

public class Basic {
   public String city;
   public String cnty;
   public String id;
   public double lat;
   public double lon;
   public Update update;
   public  static class Update{
      public String loc;
      public String utc;

      public String getLoc() {
         return loc;
      }

      public void setLoc(String loc) {
         this.loc = loc;
      }

      public String getUtc() {
         return utc;
      }

      public void setUtc(String utc) {
         this.utc = utc;
      }
   }

   public String getCity() {
      return city;
   }

   public void setCity(String ctiy) {
      this.city = ctiy;
   }

   public String getCnty() {
      return cnty;
   }

   public void setCnty(String cnty) {
      this.cnty = cnty;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public double getLat() {
      return lat;
   }

   public void setLat(double lat) {
      this.lat = lat;
   }

   public double getLon() {
      return lon;
   }

   public void setLon(double lon) {
      this.lon = lon;
   }

   public Update getUpdate() {
      return update;
   }

   public void setUpdate(Update update) {
      this.update = update;
   }
}
