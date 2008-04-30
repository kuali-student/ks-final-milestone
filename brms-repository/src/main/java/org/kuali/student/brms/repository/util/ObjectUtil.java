package org.kuali.student.brms.repository.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtil {

    /**
     * Private constructor.
     */
    private ObjectUtil() {
    }

    /**
     * Creates a deep copy of an object by serialization.
     * 
     * @param oldObj
     * @return
     * @throws Exception
     */
    public static Object deepCopy( Object obj ) throws Exception
    {
       ObjectOutputStream oos = null;
       ObjectInputStream ois = null;
       try
       {
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          oos = new ObjectOutputStream( bos );
          // Serialize the object
          oos.writeObject( obj );
          oos.flush();
          ByteArrayInputStream bin = new ByteArrayInputStream( bos.toByteArray() );
          ois = new ObjectInputStream( bin );
          // Return the new object
          return ois.readObject();
       }
       catch( Exception e )
       {
           throw new Exception( e );
       }
       finally
       {
          if ( oos != null ) oos.close();
          if ( ois != null ) ois.close();
       }
    }    
}
