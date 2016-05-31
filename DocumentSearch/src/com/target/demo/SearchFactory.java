package com.target.demo;

/*
 * Factory to construct instances of classes with search specific implementations. 
 */
public class SearchFactory {   
   public static Object get(Class target, String searchMethod) { 
      Object search_instance = null;
      
      // convert to search method specific classname.  eg: a.b.c.MyClass becomes a.b.c.SimpleMyClass or a.b.c.IndexedrMyClass, etc
      String datastore_classname = target.getPackage().getName() +"."+ searchMethod + target.getSimpleName(); 
      
      try {
    	  search_instance = getClassInstance(datastore_classname);
      }
      catch (Exception e) {
          System.err.println(target.getSimpleName() +" subclass not found for "+ search_instance +": "+ e.getMessage());
    	  throw new RuntimeException(e);
      }
      return search_instance;
   }

   private static Object getClassInstance(String target) {
      Object instance = null;
      try {
         Class search_class = Class.forName(target);
         instance = search_class.newInstance();
      }
      catch (Exception e) {
         throw new RuntimeException(e);
      }
      return instance;
   }
}